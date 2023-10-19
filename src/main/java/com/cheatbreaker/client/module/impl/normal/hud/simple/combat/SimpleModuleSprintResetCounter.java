package com.cheatbreaker.client.module.impl.normal.hud.simple.combat;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

/**
 * @Module - ModuleReachDisplay
 * @see AbstractModule
 *
 * This module displays all sorts of reset counts for anything related to sprinting.
 */
public class SimpleModuleSprintResetCounter extends AbstractSimpleCombatHudModule {
    public static List<Long> temphits = new ArrayList<>();
    public static List<Long> wTaps = new ArrayList<>();
    public static List<Long> sTaps = new ArrayList<>();
    public static long lastTapTime;
    public static long lastComboTime;
    public static int lastAttacked = -1;
    public static int lastCombo;
    private boolean pressed;
    private boolean almostw;
    private int keyCode;

    private Setting counterType;
    private int counter;

    public SimpleModuleSprintResetCounter() {
        super("Sprint Reset Counter", "[4 WTaps]");
        this.setDescription("Displays the amount of times you sprint reset.");
        this.setCreators("AgentManny");
    }

    @Override
    public String getValueString() {
        d();
        if (lastAttackTime != 0L || !(Boolean) hideWhenNotAttacking.getValue()) {
            counter = counterType.getValue().equals("Forward") ? wTaps.size() : sTaps.size();
            return counterType.getValue().equals("Higher") ? (counter = Math.max(wTaps.size(), sTaps.size())) + "" : counter + "";
//            return wTaps.size() + "";
        }
        return null;
    }

    @Override
    public String getLabelString() {
        int name = this.keyCode;
        if (counterType.getValue().equals("Higher")) {
            if (sTaps.size() > wTaps.size()) {
                name = this.mc.gameSettings.keyBindBack.getKeyCode();
            } else {
                name = this.mc.gameSettings.keyBindForward.getKeyCode();
            }
        }
        return "" + Keyboard.getKeyName(name) + "Tap" + (counter != 1 ? "s" : "");
    }

    public String getPreviewString() {
        return "4";
    }

    public void getExtraSettings() {
        this.counterType = new Setting(this, "Counter").setValue("Forward").acceptedStringValues("Forward", "Backwards", "Higher");
        this.hideWhenNotAttacking = new Setting(this, "Hide When Not Attacking").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
    }

    public void d() {
        int forwardKeyCode = this.mc.gameSettings.keyBindForward.getKeyCode();
        int backKeyCode = this.mc.gameSettings.keyBindBack.getKeyCode();
        this.keyCode = counterType.getValue().equals("Forward") ? forwardKeyCode : backKeyCode;
        int button = (forwardKeyCode == -99 || forwardKeyCode == -100) ? ((forwardKeyCode == -99) ? 0 : 1) : -1;
        int button2 = (backKeyCode == -99 || backKeyCode == -100) ? ((backKeyCode == -99) ? 0 : 1) : -1;
        boolean wpressed = (mc.currentScreen == null || mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof HudLayoutEditorGui) && ((button != -1) ? Mouse.isButtonDown(button) : Keyboard.isKeyDown(forwardKeyCode));
        boolean spressed = (mc.currentScreen == null || mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof HudLayoutEditorGui) && ((button2 != -1) ? Mouse.isButtonDown(button2) : Keyboard.isKeyDown(backKeyCode));
        if (mc.gameSettings.keyBindForward.isKeyDown() && mc.thePlayer.isSprinting() && temphits.size() > 0) {
            if (spressed) {
                sTaps.add(System.currentTimeMillis());
                temphits.clear();
                return;
            }
            if (wpressed && !this.pressed) {
                this.pressed = true;

                if (this.almostw) wTaps.add(System.currentTimeMillis());
                this.almostw = false;
                lastTapTime = System.currentTimeMillis();
                temphits.clear();
            } else if (this.pressed && !wpressed) {
                this.pressed = false;
                this.almostw = true;
            }
        }

        if (System.currentTimeMillis() - lastAttackTime > 2000L) {
            lastAttackTime = 0L;
            clear();
        }
    }

    public void clear() {
        lastTapTime = 0L;
        wTaps.clear();
        sTaps.clear();
    }
}
