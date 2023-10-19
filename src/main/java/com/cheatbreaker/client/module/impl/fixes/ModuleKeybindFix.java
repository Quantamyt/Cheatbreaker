package com.cheatbreaker.client.module.impl.fixes;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;

/**
 * @Module - Keybind Fix
 * This module allows for keybind persistence in containers, and chat menus.
 *
 * This fix can be seen in Vanilla Minecraft 1.12+.
 */
public class ModuleKeyBindFix {
    private boolean hadAScreen;
    private final Minecraft mc = Minecraft.getMinecraft();

    public ModuleKeyBindFix() {
        CheatBreaker.getInstance().getEventBus().addEvent(TickEvent.class, this::handleTick);
    }

    public void handleTick(TickEvent event) {
        boolean hasScreen = mc.currentScreen != null;
        GlobalSettings globalSettings = CheatBreaker.getInstance().getGlobalSettings();
        if (!hasScreen && hadAScreen && globalSettings.keybindFix.getBooleanValue()) {
            Class<?> gameSettingsClass = GameSettings.class;
            Field[] fields = gameSettingsClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().equals(KeyBinding.class)) {
                    field.setAccessible(true);

                    try {
                        KeyBinding keyBinding = (KeyBinding) field.get(mc.gameSettings);
                        boolean excludeSneak = !globalSettings.excludeSneakKeybind.getBooleanValue() || !keyBinding.getKeyDescription().equalsIgnoreCase("key.sneak");
                        boolean excludeThrow = !globalSettings.excludeThrowKeybind.getBooleanValue() || !keyBinding.getKeyDescription().equalsIgnoreCase("key.use");

                        if (!keyBinding.getKeyDescription().equalsIgnoreCase("key.inventory")
                                && !keyBinding.getKeyDescription().equalsIgnoreCase("key.chat")
                                && !keyBinding.getKeyDescription().equalsIgnoreCase("key.command")
                                && excludeSneak
                                && excludeThrow
                                && keyBinding.getKeyCode() > 0
                                && Keyboard.isKeyDown(keyBinding.getKeyCode())
                        ) {
                            KeyBinding.setKeyBindState(keyBinding.getKeyCode(), true);
                            KeyBinding.onTick(keyBinding.getKeyCode());
                        }
                    } catch (Exception var9) {
                        var9.printStackTrace();
                    }
                }
            }
        }
        this.hadAScreen = hasScreen;
    }
}
