package com.cheatbreaker.client.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.EventBus;
import com.cheatbreaker.client.event.impl.keyboard.KeyboardEvent;
import com.cheatbreaker.client.event.impl.network.ConnectEvent;
import com.cheatbreaker.client.event.impl.network.DisconnectEvent;
import com.cheatbreaker.client.event.impl.network.LoadWorldEvent;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.data.SettingType;
import com.cheatbreaker.client.module.impl.disallowed.DisallowedModManager;
import com.cheatbreaker.client.module.impl.fixes.ModuleKeyBindFix;
import com.cheatbreaker.client.module.impl.normal.hud.simple.combat.*;
import com.cheatbreaker.client.module.impl.normal.hypixel.*;
import com.cheatbreaker.client.module.impl.normal.animation.ModuleOneSevenVisuals;
import com.cheatbreaker.client.module.impl.normal.hud.*;
import com.cheatbreaker.client.module.impl.normal.hud.armorstatus.ModuleArmorStatus;
import com.cheatbreaker.client.module.impl.normal.hud.chat.ModuleChat;
import com.cheatbreaker.client.module.impl.normal.hud.cooldowns.ModuleCooldowns;
import com.cheatbreaker.client.module.impl.normal.hud.keystrokes.ModuleKeyStrokes;
import com.cheatbreaker.client.module.impl.normal.hud.simple.module.*;
import com.cheatbreaker.client.module.impl.normal.hypixel.ModuleNickHider;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleAutoText;
import com.cheatbreaker.client.module.impl.normal.misc.ModulePackTweaks;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleTeamMates;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleZansMiniMap;
import com.cheatbreaker.client.module.impl.normal.perspective.ModuleDragToLook;
import com.cheatbreaker.client.module.impl.normal.perspective.ModulePerspective;
import com.cheatbreaker.client.module.impl.normal.perspective.ModuleSnapLook;
import com.cheatbreaker.client.module.impl.normal.shader.ModuleMotionBlur;
import com.cheatbreaker.client.module.impl.normal.vanilla.*;
import com.cheatbreaker.client.module.impl.packmanager.ResourcePackManager;
import com.cheatbreaker.client.module.impl.staff.StaffMod;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleBunnyhop;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleNameTags;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleNoClip;
import com.cheatbreaker.client.module.impl.staff.impl.StaffModuleXray;
import com.cheatbreaker.client.ui.element.type.custom.KeybindElement;
import com.cheatbreaker.client.util.voicechat.ModuleVoiceChat;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ModuleManager {
    public final List<AbstractModule> playerMods = new ArrayList<>();
    public final List<StaffMod> staffMods = new ArrayList<>();

    public final Map<Setting, KeybindElement> keybinds = new HashMap<>();

    @Setter public String currentModule;
    @Setter public int lastSettingScrollPos;

    public final ModuleCoordinates coordinatesMod;
    public final ModuleZansMiniMap miniMapMod;
    public final SimpleModuleToggleSprint toggleSprintMod;
    public final SimpleModuleHearingAssistance hearingAssistance;
    public final ModulePotionEffects potionEffectsMod;
    public final ModuleArmorStatus armourStatus;
    public final ModuleKeyStrokes keystrokesMod;
    public final ModuleScoreboard scoreboardMod;
    public final ModuleCooldowns coolDownsMod;
    public final ModuleNotifications notificationsMod;
    public final ModuleDirectionHUD directionHUDMod;
    public final ModuleCrosshair crosshairMod;
    public final ModuleEnchantmentGlint enchantmentGlintMod;
    public final ModuleBossBar bossBarMod;

    public final SimpleModulePotionCounter potionCounterMod;
    public final SimpleModuleCPS cpsMod;
    public final SimpleModuleFPS fpsMod;
    public final SimpleModuleMemoryUsage memoryUsageMod;
    public final SimpleModuleComboCounter comboCounterMod;
    public final SimpleModuleSprintResetCounter sprintResetCounterMod;
    public final SimpleModuleReachDisplay reachDisplayMod;
    public final SimpleModuleServerAddress serverAddressMod;
    public final SimpleModulePing pingMod;
    public final SimpleModuleClock clockMod;
    public final SimpleModuleSaturation saturationMod;
    public final SimpleModulePackDisplay packDisplayMod;
    public final SimpleModuleFlickTracker flickTrackerMod;

    public final ModuleMotionBlur motionBlurMod;
    public final ModuleHitColor hitColorMod;
    public final ModuleParticles particlesMod;
    public final ModuleNameTag nameTagMod;
    public final ModuleBlockOverlay blockOverlayMod;
    public final ModulePackTweaks packTweaksMod;
    public final ModuleEnvironmentChanger environmentChangerMod;
//    public final ModulePlayerList playerListMod;
    public final ModuleChat chatMod;
    public final CollectionModuleHypixel hypixelMod;
    public final ModuleOneSevenVisuals animationsMod;
    public final ModuleNickHider nickHiderMod;
    public final ModuleAutoText autoTextMod;
    public final ModuleTNTTimer tntTimerMod;

    public final StaffModuleXray xray;
    public final StaffModuleNameTags staffNameTags;
    public final StaffModuleNoClip staffNoClip;
    public final StaffModuleBunnyhop staffBunnyhop;

    public final ModuleDragToLook dragToLook;
    public final ModuleSnapLook snapLook;
    public final ModuleTeamMates teammatesMod;
    public final ModuleVoiceChat voiceChat;
    public final ModulePerspective perspectiveMod;
    public final ModuleKeyBindFix keybindFix;
    public final ModuleHitboxes hitboxesMod;

    public final DisallowedModManager disallowedModManager;

    public ModuleManager(EventBus eventManager) {
        CheatBreaker.getInstance().logger.info(CheatBreaker.getInstance().loggerPrefix + "Created Mod Manager");

        this.playerMods.add(this.coordinatesMod = new ModuleCoordinates());
        this.playerMods.add(this.animationsMod = new ModuleOneSevenVisuals());
        this.playerMods.add(this.miniMapMod = new ModuleZansMiniMap());
        this.playerMods.add(this.toggleSprintMod = new SimpleModuleToggleSprint());
        this.playerMods.add(this.potionEffectsMod = new ModulePotionEffects());
        this.playerMods.add(this.armourStatus = new ModuleArmorStatus());
        this.playerMods.add(this.keystrokesMod = new ModuleKeyStrokes());
        this.playerMods.add(this.scoreboardMod = new ModuleScoreboard());
//        this.playerMods.add(this.playerListMod = new ModulePlayerList());
        this.playerMods.add(this.chatMod = new ModuleChat());
        this.playerMods.add(this.bossBarMod = new ModuleBossBar());
        this.playerMods.add(this.coolDownsMod = new ModuleCooldowns());
        this.playerMods.add(this.notificationsMod = new ModuleNotifications());
        this.playerMods.add(this.directionHUDMod = new ModuleDirectionHUD());
        this.playerMods.add(this.environmentChangerMod = new ModuleEnvironmentChanger());
        this.playerMods.add(this.enchantmentGlintMod = new ModuleEnchantmentGlint());
        this.playerMods.add(this.crosshairMod = new ModuleCrosshair());
        this.playerMods.add(this.hitColorMod = new ModuleHitColor());
        this.playerMods.add(this.particlesMod = new ModuleParticles());
        this.playerMods.add(this.nameTagMod = new ModuleNameTag());
        this.playerMods.add(this.nickHiderMod = new ModuleNickHider());
        this.playerMods.add(this.autoTextMod = new ModuleAutoText());
        this.playerMods.add(this.tntTimerMod = new ModuleTNTTimer());
        this.playerMods.add(this.blockOverlayMod = new ModuleBlockOverlay());
        this.playerMods.add(this.packTweaksMod = new ModulePackTweaks());
        this.playerMods.add(this.perspectiveMod = new ModulePerspective());
        this.playerMods.add(this.hitboxesMod = new ModuleHitboxes());
        this.playerMods.add(this.hearingAssistance = new SimpleModuleHearingAssistance());
        this.playerMods.add(this.hypixelMod = new CollectionModuleHypixel());

        new Timer().scheduleAtFixedRate(new AutoTipThread(), TimeUnit.SECONDS.toMillis(15L), TimeUnit.MINUTES.toMillis(1L));

        this.playerMods.add(this.cpsMod = new SimpleModuleCPS());
        this.playerMods.add(this.fpsMod = new SimpleModuleFPS());
        this.playerMods.add(this.memoryUsageMod = new SimpleModuleMemoryUsage());
        this.playerMods.add(this.potionCounterMod = new SimpleModulePotionCounter());
        this.playerMods.add(this.comboCounterMod = new SimpleModuleComboCounter());
        this.playerMods.add(this.sprintResetCounterMod = new SimpleModuleSprintResetCounter());
        this.playerMods.add(this.reachDisplayMod = new SimpleModuleReachDisplay());
        this.playerMods.add(this.serverAddressMod = new SimpleModuleServerAddress());
        this.playerMods.add(this.pingMod = new SimpleModulePing());
        this.playerMods.add(this.clockMod = new SimpleModuleClock());
        this.playerMods.add(this.saturationMod = new SimpleModuleSaturation());
        this.playerMods.add(this.flickTrackerMod = new SimpleModuleFlickTracker());
        this.playerMods.add(this.packDisplayMod = new SimpleModulePackDisplay());
        //this.playerMods.add(this.flickTrackerMod = new SimpleFlickTrackerModule());
        this.playerMods.add(this.motionBlurMod = new ModuleMotionBlur());

        this.staffMods.add(this.xray = new StaffModuleXray());
        this.staffMods.add(this.staffNameTags = new StaffModuleNameTags());
        this.staffMods.add(this.staffNoClip = new StaffModuleNoClip());
        this.staffMods.add(this.staffBunnyhop = new StaffModuleBunnyhop());

        this.voiceChat = new ModuleVoiceChat();
        this.dragToLook = new ModuleDragToLook();
        this.snapLook = new ModuleSnapLook();
        this.teammatesMod = new ModuleTeamMates();
        this.keybindFix = new ModuleKeyBindFix();

        this.teammatesMod.setEnabled(true);
        new ResourcePackManager();

        disallowedModManager = new DisallowedModManager();
        disallowedModManager.startup();

        eventManager.addEvent(LoadWorldEvent.class, this::setWorldTime);
        eventManager.addEvent(KeyboardEvent.class, this::handleStaffModKeybind);
        eventManager.addEvent(ConnectEvent.class, ignored -> {
            ServerData serverData = Minecraft.getMinecraft().currentServerData;
            if (serverData != null) {
                CheatBreaker.getInstance().syncCurrentServerWithAssetServer(serverData.serverIP, serverData.domain, serverData.port);
            }
        });

        eventManager.addEvent(DisconnectEvent.class, ignored -> {
            CheatBreaker.getInstance().syncCurrentServerWithAssetServer("", "", 0);
            for (AbstractModule var3 : this.staffMods) {
                var3.setState(false);
                var3.setStaffModuleEnabled(false);
            }
        });
    }

    private void setWorldTime(LoadWorldEvent event) {
        if (this.environmentChangerMod.isEnabled()) this.environmentChangerMod.setWorldTime();
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
                if ((setting.getSettingName().toLowerCase().startsWith("hot key") || setting.getSettingName().toLowerCase().endsWith("keybind")) && key == setting.getIntegerValue()) {
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

    private void handleStaffModKeybind(KeyboardEvent event) {
        if (event.getPressed() != 0) {
            for (StaffMod staffMod : this.staffMods) {
                if (!staffMod.isStaffModuleEnabled() || (Integer) staffMod.getKeybindSetting().getValue() != event.getPressed())
                    continue;
                staffMod.setState(!staffMod.isEnabled());
            }
        }
    }
}
