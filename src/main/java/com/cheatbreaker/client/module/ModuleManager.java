package com.cheatbreaker.client.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.EventBus;
import com.cheatbreaker.client.event.impl.ConnectEvent;
import com.cheatbreaker.client.event.impl.DisconnectEvent;
import com.cheatbreaker.client.event.impl.KeyboardEvent;
import com.cheatbreaker.client.event.impl.LoadWorldEvent;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.data.SettingType;
import com.cheatbreaker.client.module.impl.fixes.ModuleKeybindFix;
import com.cheatbreaker.client.module.impl.normal.ModuleMiniMap;
import com.cheatbreaker.client.module.impl.normal.ModulePackTweaks;
import com.cheatbreaker.client.module.impl.normal.ModuleTeammates;
import com.cheatbreaker.client.module.impl.normal.hud.*;
import com.cheatbreaker.client.module.impl.normal.hud.armorstatus.ModuleArmorStatus;
import com.cheatbreaker.client.module.impl.normal.hud.chat.ModuleChat;
import com.cheatbreaker.client.module.impl.normal.hud.cooldowns.ModuleCooldowns;
import com.cheatbreaker.client.module.impl.normal.hud.keystrokes.ModuleKeyStrokes;
import com.cheatbreaker.client.module.impl.normal.hud.simple.impl.ModuleFPS;
import com.cheatbreaker.client.module.impl.normal.hud.simple.impl.*;
import com.cheatbreaker.client.module.impl.normal.hud.simple.impl.combat.ModuleCPS;
import com.cheatbreaker.client.module.impl.normal.hud.simple.impl.combat.ModuleComboCounter;
import com.cheatbreaker.client.module.impl.normal.hud.simple.impl.combat.ModuleReachDisplay;
import com.cheatbreaker.client.module.impl.normal.hud.simple.impl.combat.ModuleSprintResetCounter;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleAutoText;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleNickHider;
import com.cheatbreaker.client.module.impl.normal.perspective.ModuleDragToLook;
import com.cheatbreaker.client.module.impl.normal.perspective.ModulePerspective;
import com.cheatbreaker.client.module.impl.normal.perspective.ModuleSnapLook;
import com.cheatbreaker.client.module.impl.normal.shader.ModuleMotionBlur;
import com.cheatbreaker.client.module.impl.normal.vanilla.*;
import com.cheatbreaker.client.module.impl.packmanager.ResourcePackManager;
import com.cheatbreaker.client.module.impl.staff.StaffMod;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleBunnyhop;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleNametags;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleNoclip;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleXray;
import com.cheatbreaker.client.ui.element.type.TextFieldElement;
import com.cheatbreaker.client.ui.element.type.custom.KeybindElement;
import com.cheatbreaker.client.util.voicechat.ModuleVoiceChat;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

public class ModuleManager {
    public final List<AbstractModule> playerMods = new ArrayList<>();
    public final List<StaffMod> staffMods = new ArrayList<>();

    public final Map<Setting, KeybindElement> keybinds = new HashMap<>();

    @Setter public String currentModule;
    @Setter public int lastSettingScrollPos;

    public final ModuleCoordinates coordinatesMod;
    public final ModuleMiniMap miniMapMod;
    public final ModuleToggleSprint toggleSprintMod;
    public final ModulePotionEffects potionEffectsMod;
    public final ModuleArmorStatus armourStatus;
    public final ModuleKeyStrokes keystrokesMod;
    public final ModuleScoreboard scoreboardMod;
    public final ModuleCooldowns cooldownsMod;
    public final ModuleNotifications notificationsMod;
    public final ModuleDirectionHUD directionHUDMod;
    public final ModuleCrosshair crosshairMod;
    public final ModuleEnchantmentGlint enchantmentGlintMod;
    public final ModuleBossBar bossBarMod;

    public final ModulePotionCounter potionCounterMod;
    public final ModuleCPS cpsMod;
    public final ModuleFPS fpsMod;
    public final ModuleMemoryUsage memoryUsageMod;
    public final ModuleComboCounter comboCounterMod;
    public final ModuleSprintResetCounter sprintResetCounterMod;
    public final ModuleReachDisplay reachDisplayMod;
    public final ModuleServerAddress serverAddressMod;
    public final ModulePing pingMod;
    public final ModuleClock clockMod;
    public final ModuleSaturation saturationMod;
    public final ModulePackDisplay packDisplayMod;

    public final ModuleMotionBlur motionBlurMod;
    public final ModuleHitColor hitColorMod;
    public final ModuleParticles particlesMod;
    public final ModuleNametag nametagMod;
    public final ModuleBlockOverlay blockOverlayMod;
    public final ModulePackTweaks packTweaksMod;
    public final ModuleEnvironmentChanger environmentChangerMod;
    public final ModulePlayerList playerListMod;
    public final ModuleChat chatMod;
    public final ModuleNickHider nickHiderMod;
    public final ModuleAutoText autoTextMod;

    public final StaffModuleXray staffModuleXray;
    public final StaffModuleNametags staffModuleNametags;
    public final StaffModuleNoclip staffModuleNoclip;
    public final StaffModuleBunnyhop staffModuleBunnyhop;

    public final ModuleDragToLook dragToLook;
    public final ModuleSnapLook snapLook;
    public final ModuleTeammates teammatesMod;
    public final ModuleVoiceChat voiceChat;
    public final ModulePerspective perspectiveMod;
   // public final FOVChanger fovChangerMod;
    public final ModuleKeybindFix keybindFix;

    public ModuleManager(EventBus eventBus) {
        this.playerMods.add(this.coordinatesMod = new ModuleCoordinates());
        this.playerMods.add(this.miniMapMod = new ModuleMiniMap());
        this.playerMods.add(this.toggleSprintMod = new ModuleToggleSprint());
        this.playerMods.add(this.potionEffectsMod = new ModulePotionEffects());
        this.playerMods.add(this.armourStatus = new ModuleArmorStatus());
        this.playerMods.add(this.keystrokesMod = new ModuleKeyStrokes());
        this.playerMods.add(this.scoreboardMod = new ModuleScoreboard());
        this.playerMods.add(this.playerListMod = new ModulePlayerList());
        this.playerMods.add(this.chatMod = new ModuleChat());
        this.playerMods.add(this.bossBarMod = new ModuleBossBar());
        this.playerMods.add(this.cooldownsMod = new ModuleCooldowns());
        this.playerMods.add(this.notificationsMod = new ModuleNotifications());
        this.playerMods.add(this.directionHUDMod = new ModuleDirectionHUD());

        this.playerMods.add(this.environmentChangerMod = new ModuleEnvironmentChanger());
        this.playerMods.add(this.enchantmentGlintMod = new ModuleEnchantmentGlint());
        this.playerMods.add(this.crosshairMod = new ModuleCrosshair());
        this.playerMods.add(this.hitColorMod = new ModuleHitColor());
        this.playerMods.add(this.particlesMod = new ModuleParticles());
        this.playerMods.add(this.nametagMod = new ModuleNametag());
        this.playerMods.add(this.nickHiderMod = new ModuleNickHider());
        this.playerMods.add(this.autoTextMod = new ModuleAutoText());
        this.playerMods.add(this.blockOverlayMod = new ModuleBlockOverlay());
        this.playerMods.add(this.packTweaksMod = new ModulePackTweaks());
        this.playerMods.add(this.perspectiveMod = new ModulePerspective());
//        this.playerMods.add(this.fovChangerMod = new FOVChanger());

        this.playerMods.add(this.cpsMod = new ModuleCPS());
        this.playerMods.add(this.fpsMod = new ModuleFPS());
        this.playerMods.add(this.memoryUsageMod = new ModuleMemoryUsage());
        this.playerMods.add(this.potionCounterMod = new ModulePotionCounter());
        this.playerMods.add(this.comboCounterMod = new ModuleComboCounter());
        this.playerMods.add(this.sprintResetCounterMod = new ModuleSprintResetCounter());
        this.playerMods.add(this.reachDisplayMod = new ModuleReachDisplay());
        this.playerMods.add(this.serverAddressMod = new ModuleServerAddress());
        this.playerMods.add(this.pingMod = new ModulePing());
        this.playerMods.add(this.clockMod = new ModuleClock());
        this.playerMods.add(this.saturationMod = new ModuleSaturation());
        this.playerMods.add(this.packDisplayMod = new ModulePackDisplay());
        this.playerMods.add(this.motionBlurMod = new ModuleMotionBlur());

        this.staffMods.add(this.staffModuleXray = new StaffModuleXray("xray"));
        this.staffMods.add(this.staffModuleNametags = new StaffModuleNametags("nametags"));
        this.staffMods.add(this.staffModuleNoclip = new StaffModuleNoclip("noclip"));
        this.staffMods.add(this.staffModuleBunnyhop = new StaffModuleBunnyhop("bunnyhop"));
        this.voiceChat = new ModuleVoiceChat();
        this.dragToLook = new ModuleDragToLook();
        this.snapLook = new ModuleSnapLook();
        this.teammatesMod = new ModuleTeammates();
        this.keybindFix = new ModuleKeybindFix();
        this.teammatesMod.setEnabled(true);
        new ResourcePackManager();
        eventBus.addEvent(LoadWorldEvent.class, this::loadWorldEvent);
        eventBus.addEvent(KeyboardEvent.class, this::loadKeyboardEvent);
        eventBus.addEvent(ConnectEvent.class, var1x -> {
            ServerData data = Minecraft.getMinecraft().currentServerData;
            if (data != null) {
                CheatBreaker.getInstance().updateWSServer(data.serverIP, data.domain, data.port);
            } else {
                CheatBreaker.getInstance().updateWSServer("", "", 0);
            }
        });
        eventBus.addEvent(DisconnectEvent.class, var1x -> {
            CheatBreaker.getInstance().updateWSServer("", "", 0);
            for (AbstractModule var3 : this.staffMods) {
                var3.setState(false);
                var3.setStaffModuleEnabled(false);
            }
        });


    }

    private void loadWorldEvent(LoadWorldEvent event) {
        if (this.environmentChangerMod.isEnabled()) this.environmentChangerMod.setWorldTime();
    }

    private void loadKeyboardEvent(KeyboardEvent event) {
        if (event.getPressed() != 0) {
            for (StaffMod staffMod : this.staffMods) {
                if (!staffMod.isStaffModuleEnabled() || (Integer) staffMod.getKeybindSetting().getValue() != event.getPressed())
                    continue;
                staffMod.setState(!staffMod.isEnabled());
            }
        }
    }

    public boolean isBoundToAnother(Setting settingIn, int key) {
        boolean flag = false;
        for (Setting setting : this.keybinds.keySet()) {
            if (setting.getType() == SettingType.INTEGER) {
                // Key code check
                if (setting.isHasKeycode() && key == setting.getKeyCode()) {
                    flag = true;
                }
                // Mouse bind check
                if (setting.isHasMouseBind() && key == setting.getIntegerValue()) {
                    flag = true;
                }
                // General check
                if ((setting.getSettingName().toLowerCase().startsWith("hotkey") || setting.getSettingName().toLowerCase().endsWith("keybind")) && key == setting.getIntegerValue()) {
                    flag = true;
                }
            }
        }

        if (flag)
            CheatBreaker.getInstance().getModuleManager().notificationsMod.send("error", EnumChatFormatting.RED + settingIn.getSettingName() + " is already bound to another keybind!", 5000L);

        return flag;
    }

    public AbstractModule getModByName(String name) {
        return this.playerMods.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Returns if the player is using staff modules or not.
     */
    public boolean isUsingStaffModules() {
        for (AbstractModule module : this.staffMods)
        {
            module.setStaffModuleEnabled(true);
            if (module.isStaffModuleEnabled())
            {
                return false;
            }
        }
        return true;
    }
}
