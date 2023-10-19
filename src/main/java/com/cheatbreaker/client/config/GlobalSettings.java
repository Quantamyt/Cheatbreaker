package com.cheatbreaker.client.config;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.audio.music.util.DashUtil;
import com.cheatbreaker.client.ui.element.module.ModuleListElement;
import com.cheatbreaker.client.ui.element.type.ColorPickerColorElement;
import com.cheatbreaker.client.ui.mainmenu.MainMenuBase;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.util.CBDebugOutput;
import com.cheatbreaker.client.util.render.serverlist.UnsafeServerAction;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.src.Config;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GlobalSettings {
    public KeyBinding keyBindOpenMenu;
    public KeyBinding keyBindDragToLook;
    public KeyBinding keyBindBackLook;
    public KeyBinding keyBindFrontLook;
    public KeyBinding keyBindEmote;

    public final List<Setting> settingsList = new ArrayList<>();
    @Getter private final List<String[]> pinnedServers = new ArrayList<>();
    @Getter private final List<String[]> unsafeServers = new ArrayList<>();

    public boolean isDebug = false;

    public String webapiCrashReportUpload = "http://server.noxiuam.gq/crashReport";
    public String webapiDebugUpload = "http://moosecbapi.000webhostapp.com/debug-upload.php";
    public String webapiCosmetics = "https://cheatbreaker.com/api/cosmetic/";
    public String webapiCosmeticsAll = "https://cheatbreaker.com/api/cosmetic/all";
    public String mojangStatusURL = "https://mojan.ga/api/check"; // Previously https://status.mojang.com/check, however, that server is no longer available.

    public int sessionCheckInteral = 60;

    public boolean SHOW_MODIFIERS = true;

    private final Setting debugSettingsLabel;
    public Setting showDebugOutput;

    private final Setting audioSettingsLabel;
    //    public Setting microphone;
    public Setting radioVolume;
    //    public Setting microphoneVolume;
//    public Setting speakerVolume;
    public Setting pinRadio;
    public Setting muteCBSounds;

    private final Setting fpsBoostSettingsLabel;
    public Setting enableFpsBoost;
    public Setting slowChunkLoading;
    public Setting fullBright;
    public Setting entityShadows;
    public Setting hideGroundedArrows;
    public Setting hideFoliage;
    public Setting hideStuckArrows;
    public Setting hideMovingArrows;
    public Setting hidePlacedSkulls;

    private final Setting fpsLimitSettingsLabel;
    public Setting useCustomFPSLimiter;
    public Setting customFPSLimit;
    public Setting limitWhenUnfocused;
    public Setting unfocusedFPS;
    //    public Setting limitWhenInactive;
//    public Setting inactiveFPS;
    public Setting mainMenuFPS;

    private final Setting teamViewLabel;
    public Setting enableTeamView;
    public Setting showDistance;
    public Setting showOffScreenMarker;

//    private final Setting generalSettingsLabel;

    private final Setting displaySettingsLabel;
    public Setting borderlessFullscreen;
    public Setting unfullscreenWhenUnfocused;

    private final Setting hudEditorSettingsLabel;
    public Setting compactMode;
    public static Setting darkMode;
    public Setting customizationLevel;
    public Setting snapModules;
    public Setting snappingStrength;
    public Setting showModName;
//    public Setting useBackgroundOverlay;

    private final Setting renderSettingsLabel;
    public Setting showHudInDebug;
    public Setting showPotionInfo;
    public Setting achievements;
    public Setting shiftPotionInfo;
    public Setting guiBlur;
    public Setting containerBackground;
    public Setting bottomSkyLayer;

    private final Setting streamerModeSettingsLabel;
    public Setting streamerMode;
    public Setting disableModMenuKeybind;
    public Setting notifyWhenModMenuKeybindPressed;
    public Setting holdDownModsGameMenuButton;
    public Setting holdDuration;

    private final Setting packSettingsLabel;
    public Setting packMenu;
    public Setting widePackMenu;
    public Setting transparentBackground;
    public Setting packFolderInfo;
    public Setting packFolderIcons;
    public Setting packIcons;
    public Setting packDescriptions;
    public Setting packSearchBar;
    public Setting packSortMethod;
    public Setting packSmoothScrolling;
    public Setting packListBackgroundColor;

    private final Setting scalingSettingsLabel;
    public Setting modScale;
    public Setting modScaleMultiplier;
    public Setting hotbarScale;
    public Setting hotbarScaleMultiplier;
    public Setting scaleModsDownSmallerResoltion;

    private final Setting screenshotSettingsLabel;
    public Setting shutterSound;
    public Setting sendScreenshotMessage;
    public Setting compactOptions;
    public Setting copyAutomatically;
    public Setting openOption;
    public Setting copyOption;
    public Setting uploadOption;

    private final Setting keybindHandlingSettingsLabel;
    public Setting keybindFix;
    public Setting excludeSneakKeybind;
    public Setting excludeThrowKeybind;

    private final Setting cosmeticSettingsLabel;
    public Setting showOptifineCapes;
    public Setting showOptifineHats;
    public Setting showCheatBreakerCapes;
    public Setting showCheatBreakerWings;

    private final Setting rpcSettings;
    public Setting showRPC;
    public Setting showServer;
    public Setting showAccount;

    private final Setting colorSettingsLabel;
    public Setting resetColors;
    public Setting defaultColor;
    public Setting emoteRadialColor;
    @Getter @Setter private Setting currentMenuSetting;

    public List<ColorPickerColorElement> favouriteColors = new ArrayList<>();
    public List<ColorPickerColorElement> recentColors = new ArrayList<>();

    public GlobalSettings() {
//        String[] microphones = CheatBreaker.getMicrophoneList();
//        StringBuilder availableMicrophones = new StringBuilder();
//        for (String microphone : microphones) {
//            availableMicrophones.append("\n- ").append(microphone);
//        }
        this.debugSettingsLabel = new Setting(this.settingsList, "label").setValue("Debug Settings").setCondition(() -> CBDebugOutput.uuids.contains(Minecraft.getMinecraft().getSession().getUniqueID()));
        this.showDebugOutput = new Setting(this.settingsList, "Show Debug Output", "Show any debug information.").setValue(false).setCondition(() -> CBDebugOutput.uuids.contains(Minecraft.getMinecraft().getSession().getUniqueID()));
        this.audioSettingsLabel = new Setting(this.settingsList, "label").setValue("Audio Settings");
//        this.microphone = microphones.length > 0 ? new Setting(this.settingsList, "Microphone", "Change the microphone for voice chat." +
//                "\n" +
//                "\nAvailable microphones:" + availableMicrophones).setValue(microphones[0]).acceptedValues(microphones).onChange(var0 -> {
//            try {
//                System.out.println(CheatBreaker.getInstance().getLoggerPrefix() + "Updated audio device!");
//                Message.h(CheatBreaker.getMicrophone((String)var0));
//            } catch (UnsatisfiedLinkError ignored) {}
//        }) : new Setting(this.settingsList, "Microphone", "Change the microphone for voice chat (none detected).").setValue("Unknown").acceptedValue("Unknown").onChange(var0 -> {
//            try {
//                System.out.println(CheatBreaker.getInstance().getLoggerPrefix() + "Updated audio device!");
//                Message.h(CheatBreaker.getMicrophone((String)var0));
//            } catch (UnsatisfiedLinkError ignored) {}
//        });
        this.muteCBSounds = new Setting(this.settingsList, "Mute CheatBreaker sounds", "Mute notification sounds generated from CheatBreaker.").setValue(false);
        this.pinRadio = new Setting(this.settingsList, "Pin Radio Player", "Pin the radio to the HUD.").setValue(false);
        this.radioVolume = new Setting(this.settingsList, "Radio Volume", "Adjusts the volume of the Dash Radio.").onChange(value -> {
            if (DashUtil.isActive()) {
                DashUtil.getPlayer().setFloatControlValue(Float.parseFloat(value.toString()));
            }
        }).setValue(85).setMinMax(0, 100).setUnit("%").setCenterLabel("MEDIUM");
//        this.microphoneVolume = new Setting(this.settingsList, "Microphone Volume", "Adjust the input Microphone volume.").onChange(volume -> {
//            try {
//                Message.l(Float.parseFloat(volume.toString()));
//            } catch (UnsatisfiedLinkError ignored) {}
//        }).setValue(70.0F).setMinMax(0.0F, 100.0F).setUnit("%").setCenterLabel("MEDIUM");
//        this.speakerVolume = new Setting(this.settingsList, "Speaker Volume", "Adjust the speaker volume for voice chat.").onChange(var0 -> {
//            try {
//                float volume = 20000.0f / (float)(20000.0F - Math.max(0.0F, Math.min(19500.0F, (Float)var0 * 195.0F)));
//                Message.m(volume);
//            } catch (UnsatisfiedLinkError ignored) {}
//        }).setValue(85.0F).setMinMax(0.0F, 100.0F).setUnit("%").setCenterLabel("MEDIUM");

        this.fpsBoostSettingsLabel = new Setting(this.settingsList, "label").setValue("FPS Boost");
        this.enableFpsBoost = new Setting(this.settingsList, "Enable FPS Boost", "Enables all FPS boost settings in this category.").setValue(true);
        this.slowChunkLoading = new Setting(this.settingsList, "Slow chunk loading", "Determines how fast chunks should load as a percentage." +
                "\n§e§oHigher values may increase performance!" +
                "\n§6§oHigher values will make chunks load slower." +
                "\n" +
                "\n§276% - 100%:§r Slowest chunk loading speed (Higher FPS)." +
                "\n§a51% - 75%:§r Slow chunk loading speed (High FPS)." +
                "\n§e50%:§r Medium chunk loading speed (Medium FPS)." +
                "\n§626% - 49%:§r Fast chunk loading speed (Low FPS)." +
                "\n§c0.1% - 25%:§r Faster chunk loading speed (Lower FPS)." +
                "\n§b0%:§r Vanilla chunk loading speed (Lowest FPS).").setMinMax(0.0f, 100.0f).setValue(70.0f).setUnit("%").setCenterLabel("MEDIUM").setIcons("fps-inverse-52", "fps-52").setCondition(() -> (Boolean) this.enableFpsBoost.getValue());
        this.fullBright = new Setting(this.settingsList, "Fullbright", "Set the brightness/gamma to the max.").setValue(true).setCondition(() -> (Boolean) this.enableFpsBoost.getValue());
        this.entityShadows = new Setting(this.settingsList, "Entity Shadows", "Draws a shadow below entities.").setValue(true);
        this.hideGroundedArrows = new Setting(this.settingsList, "Hide Grounded Arrows").setValue(false);
        this.hideFoliage = new Setting(this.settingsList, "Hide Foliage", "Hides things like tall grass, flowers, and shrubs").setValue(false).onChange(e -> CheatBreaker.getInstance().getMc().renderGlobal.loadRenderers());
        this.hideStuckArrows = new Setting(this.settingsList, "Hide Stuck Arrows").setValue(false);
        this.hideMovingArrows = new Setting(this.settingsList, "Hide Moving Arrows").setValue(false);
        this.hidePlacedSkulls = new Setting(this.settingsList, "Hide Placed Skulls").setValue(false);
        this.fpsLimitSettingsLabel = new Setting(this.settingsList, "label").setValue("FPS Limiting Settings");
        this.useCustomFPSLimiter = new Setting(this.settingsList, "Use Custom FPS Limiter", "Overrides the default FPS limiter with a custom one." +
                "\n§e§oWill not override when VSync is enabled.").setValue(false);
        this.limitWhenUnfocused = new Setting(this.settingsList, "Limit When Game is Unfocused", "Limits the FPS when you are not interacting with the game.").setValue(false);
        this.customFPSLimit = new Setting(this.settingsList, "Maximum FPS").setMinMax(5, 1000).setValue(390).setUnit(" FPS").setIcons("fps-inverse-52", "fps-52").setCondition(() -> (Boolean) this.useCustomFPSLimiter.getValue());
        this.unfocusedFPS = new Setting(this.settingsList, "Unfocused FPS").setMinMax(1, 390).setValue(60).setUnit(" FPS").setIcons("fps-inverse-52", "fps-52").setCondition(() -> (Boolean) this.limitWhenUnfocused.getValue());
//        this.limitWhenInactive = new Setting(this.settingsList, "Limit When Inactive", "Limits the FPS when your player is focused on the game but is not interacting for a certain period of time.").setValue(false);
//        this.inactiveFPS = new Setting(this.settingsList, "Inactive FPS").setMinMax(15, 390).setValue(60).setUnit(" FPS").setIcons("fps-inverse-52", "fps-52").setCondition(() -> (Boolean) this.limitWhenInactive.getValue());
        this.mainMenuFPS = new Setting(this.settingsList, "Main Menu FPS").setMinMax(30, 500).setValue(90).setUnit(" FPS").setIcons("fps-inverse-52", "fps-52");

        this.teamViewLabel = new Setting(this.settingsList, "label").setValue("Team View Settings");
        this.enableTeamView = new Setting(this.settingsList, "Enable Team View", "Enables the Team View.").setValue(true);
        this.showOffScreenMarker = new Setting(this.settingsList, "Show off-screen marker", "Show a marker above players.").setValue(true).setCondition(() -> (Boolean) this.enableTeamView.getValue());
        this.showDistance = new Setting(this.settingsList, "Show distance", "Show the distance amount above a your teammates' heads.").setValue(true).setCondition(() -> (Boolean) this.enableTeamView.getValue());

//        this.generalSettingsLabel = new Setting(this.settingsList, "label").setValue("General Settings");
        this.currentMenuSetting = new Setting(this.settingsList, "Current Menu", "Sets which main menu should show." +
                "\n" +
                "\n0: 2017" +
                "\n1: 2018" +
                "\n2: 2016").setMinMax(0, 2).setValue(1);
//        this.lookView = new Setting(this.settingsList, "Look View").setValue("Third").acceptedStringValues("Third", "Reverse", "First");

        this.displaySettingsLabel = new Setting(this.settingsList, "label").setValue("Display Settings");
        this.borderlessFullscreen = new Setting(this.settingsList, "Borderless Fullscreen").setValue(false).onChange(value -> {
            if (Minecraft.getMinecraft().isFullScreen() && Minecraft.getMinecraft().currentScreen instanceof HudLayoutEditorGui) {
                Minecraft.getMinecraft().toggleFullscreen();
                Minecraft.getMinecraft().toggleFullscreen();

                HudLayoutEditorGui hudEditor = new HudLayoutEditorGui();
                Minecraft.getMinecraft().displayGuiScreen(hudEditor);
                hudEditor.currentScrollableElement = hudEditor.settingsElement;
                ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).resetColor = true;
                HudLayoutEditorGui.instance.settingsElement.scrollAmount = CheatBreaker.getInstance().getModuleManager().lastSettingScrollPos;
            }
        });
        this.unfullscreenWhenUnfocused = new Setting(this.settingsList, "Unfullscreen when Unfocused").setValue(false);

        this.hudEditorSettingsLabel = new Setting(this.settingsList, "label").setValue("Hud Editor Settings");
        this.compactMode = new Setting(this.settingsList, "Mod List").setValue("Normal").acceptedStringValues("Normal", "Compact").setCustomizationLevel(CustomizationLevel.ADVANCED).onChange(value -> {
            if (Minecraft.getMinecraft().currentScreen instanceof HudLayoutEditorGui) {
                HudLayoutEditorGui hudEditor = new HudLayoutEditorGui();
                Minecraft.getMinecraft().displayGuiScreen(hudEditor);
                hudEditor.currentScrollableElement = hudEditor.modulesElement;
            }
        });
        this.darkMode = new Setting(this.settingsList, "Dark Mode", "Switch between light and dark mode.").setValue(false);
        this.customizationLevel = new Setting(this.settingsList, "Customization Level").setValue("Simple").acceptedStringValues("Simple", "Medium", "Advanced");
        this.showModName = new Setting(this.settingsList, "Show Mod Name (GUI)").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
//        this.useBackgroundOverlay = new Setting(this.settingsList, "Use Background overlay (GUI)").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.snapModules = new Setting(this.settingsList, "Snap mods to other mods (GUI)").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.snappingStrength = new Setting(this.settingsList, "Snapping Strength").setValue(2.0F).setMinMax(1.0F, 10.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.snapModules.getValue());

        this.renderSettingsLabel = new Setting(this.settingsList, "label").setValue("Render Settings");
        this.showPotionInfo = new Setting(this.settingsList, "Show Potion info in inventory").setValue(true).setCondition(() -> CheatBreaker.getInstance().getModuleManager().potionEffectsMod.isEnabled());
        this.shiftPotionInfo = new Setting(this.settingsList, "Potion info shifts inventory", "Choose to make the potion info shift the inventory position.").setValue(false);
        this.achievements = new Setting(this.settingsList, "Show Achievements").setValue(true);
        this.showHudInDebug = new Setting(this.settingsList, "Show HUD while in debug view", "Show the CheatBreaker HUD when the debug view is open.").setValue(false);
        this.guiBlur = new Setting(this.settingsList, "GUI Blur", "Blurs the menus.").onChange(value -> {
            Minecraft.getMinecraft().entityRenderer.loadGuiBlurShader();
        }).setValue(false);
        this.containerBackground = new Setting(this.settingsList, "Container Background").setValue("CheatBreaker").acceptedStringValues("Vanilla", "CheatBreaker", "None");
        this.bottomSkyLayer = new Setting(this.settingsList, "Show Bottom Sky Layer").setValue("No Custom Sky").acceptedStringValues("OFF", "No Custom Sky", "ON").setCondition(Config::isSkyEnabled);

        this.streamerModeSettingsLabel = new Setting(this.settingsList, "label").setValue("Streamer Mode Settings");
        this.streamerMode = new Setting(this.settingsList, "Streamer Mode", "Enables Streamer Mode Features").setValue(false);
        this.disableModMenuKeybind = new Setting(this.settingsList, "Disable Mod Menu Keybind", "Disables the Mod Menu keybind from opening the Mod Menu when Streamer Mode is enabled.").setValue(true).setCondition(() -> this.streamerMode.getBooleanValue());
        this.notifyWhenModMenuKeybindPressed = new Setting(this.settingsList, "Notify when pressed", "Notifies when the player presses the Mod Menu keybind while disabled.").setValue(true).setCondition(() -> this.streamerMode.getBooleanValue() && this.disableModMenuKeybind.getBooleanValue());
        this.holdDownModsGameMenuButton = new Setting(this.settingsList, "Require Mods Button Held in Game Menu", "Requires the mouse to be held for a certain period of time when clicking on Mods.").setValue(true).setCondition(() -> this.streamerMode.getBooleanValue());
        this.holdDuration = new Setting(this.settingsList, "Hold Duration", "The amount of time the Mods button must be held down before entering the Mod Menu.").setValue(5.0F).setMinMax(1.0F, 10.0F).setUnit("s").setCondition(() -> this.streamerMode.getBooleanValue() && this.holdDownModsGameMenuButton.getBooleanValue());

        this.packSettingsLabel = new Setting(this.settingsList, "label").setValue("Resource Pack Settings").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.widePackMenu = new Setting(this.settingsList, "Wide Pack Menu").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.transparentBackground = new Setting(this.settingsList, "Transparent background", "Remove the dirt background in the resource pack menu.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.packFolderInfo = new Setting(this.settingsList, "Show Pack Folder Information", "Show the ").setValue(true).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.packFolderIcons = new Setting(this.settingsList, "Show Pack Folder Icons").setValue(true).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.packIcons = new Setting(this.settingsList, "Show Pack Icons").setValue(true).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.packDescriptions = new Setting(this.settingsList, "Show Pack Descriptions").setValue(true).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.packSearchBar = new Setting(this.settingsList, "Show Search Bar").setValue(true).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.packSortMethod = new Setting(this.settingsList, "Sort Method", "Sort packs in a specific order." +
                "\n" +
                "\n§bA-Z:§r Sort packs alphabetically starting from A to Z." +
                "\n§bZ-A:§r Sort packs alphabetically in reverse starting from Z to A.").setValue("A-Z").acceptedStringValues("A-Z", "Z-A").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.packSmoothScrolling = new Setting(this.settingsList, "Smooth Scrolling", "Makes scrolling smooth").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.packListBackgroundColor = new Setting(this.settingsList, "List Background color", "Change the background color in the resource pack lists.").setValue(0x80000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.MEDIUM);

        this.keybindHandlingSettingsLabel = new Setting(this.settingsList, "label").setValue("Keybind Handling Settings");
        this.keybindFix = new Setting(this.settingsList, "Modern Keybind Handling").setValue(true);
        this.excludeSneakKeybind = new Setting(this.settingsList, "Exclude Sneak Keybind").setValue(true).setCondition(() -> (Boolean) this.keybindFix.getValue());
        this.excludeThrowKeybind = new Setting(this.settingsList, "Exclude Block/Throw Keybind").setValue(true).setCondition(() -> (Boolean) this.keybindFix.getValue());

        this.scalingSettingsLabel = new Setting(this.settingsList, "label").setValue("Scaling Settings");
        this.modScale = new Setting(this.settingsList, "Mod Scale").setValue("Default").acceptedStringValues("Default", "Small", "Normal", "Large", "Auto");
        this.modScaleMultiplier = new Setting(this.settingsList, "Mod Scale Multiplier").setValue(1.0F).setMinMax(0.25F, 2.0F).setUnit("x");
        this.hotbarScale = new Setting(this.settingsList, "Hotbar Scale").setValue("Default").acceptedStringValues("Default", "Small", "Normal", "Large", "Auto");
        this.hotbarScaleMultiplier = new Setting(this.settingsList, "Hotbar Scale Multiplier").setValue(1.0F).setMinMax(0.25F, 2.0F).setUnit("x");
        this.scaleModsDownSmallerResoltion = new Setting(this.settingsList, "Use Legacy Scaling").setValue(false);

        this.screenshotSettingsLabel = new Setting(this.settingsList, "label").setValue("Screenshot Settings").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.shutterSound = new Setting(this.settingsList, "Make Shutter Sound").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.copyAutomatically = new Setting(this.settingsList, "Copy Screenshot Automatically").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.sendScreenshotMessage = new Setting(this.settingsList, "Send Message").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.compactOptions = new Setting(this.settingsList, "Compact Options").setValue(false).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.sendScreenshotMessage.getValue());
        this.openOption = new Setting(this.settingsList, "Show Open Option").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.sendScreenshotMessage.getValue());
        this.copyOption = new Setting(this.settingsList, "Show Copy Option").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.sendScreenshotMessage.getValue());
        this.uploadOption = new Setting(this.settingsList, "Show Upload Option").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.sendScreenshotMessage.getValue());

        this.cosmeticSettingsLabel = new Setting(this.settingsList, "label").setValue("Cosmetic Settings");
        this.showOptifineCapes = new Setting(this.settingsList, "Show OptiFine Capes").setValue(true);
        this.showOptifineHats = new Setting(this.settingsList, "Show OptiFine Hats").setValue(true);
        this.showCheatBreakerCapes = new Setting(this.settingsList, "Show CheatBreaker Capes").setValue(true);
        this.showCheatBreakerWings = new Setting(this.settingsList, "Show CheatBreaker Wings").setValue(true);

        String os = System.getProperty("os.name").toLowerCase();
        this.rpcSettings = (new Setting(this.settingsList, "label")).setValue("Discord Rich Presence Settings").setCondition(() -> os.contains("win"));
        this.showRPC = (new Setting(this.settingsList, "Show Discord Rich Presence")).setValue(true).setCondition(() -> os.contains("win")).onChange(value -> {
            if (os.contains("win") && Minecraft.getMinecraft().currentScreen instanceof HudLayoutEditorGui) {
                try {
                    if ((Boolean) value) {
                        CheatBreaker.getInstance().connectDiscordIPC();
                    } else {
                        CheatBreaker.getInstance().getRpcClient().close();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        this.showServer = (new Setting(this.settingsList, "Show Active Server")).setValue(true).setCondition(() -> os.contains("win") && this.showRPC.getBooleanValue()).onChange(value -> this.updateRPC());
        this.showAccount = (new Setting(this.settingsList, "Show Active Account")).setValue(true).setCondition(() -> os.contains("win") && this.showRPC.getBooleanValue()).onChange(value -> this.updateRPC());

        this.colorSettingsLabel = new Setting(this.settingsList, "label").setValue("Color Settings");
        this.resetColors = new Setting(this.settingsList, "Apply Default Color When Enabling Mods").setValue(true);
        this.defaultColor = new Setting(this.settingsList, "Default color", "Change the default color that will be displayed when mods are enabled.").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.emoteRadialColor = new Setting(this.settingsList, "Emote Radial color", "Change the color for the highlighted radial in emotes.").setValue(0x8033334D).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.MEDIUM);
//        this.pinnedServers.add(new String[]{"Minemen Club [NA]", "na.minemen.club"});
//        this.pinnedServers.add(new String[]{"Minemen Club [EU]", "eu.minemen.club"});
        this.unsafeServers.add(new String[]{"warnedserver.us", "This server is a test IP for warned servers", String.valueOf(UnsafeServerAction.WARN)});
        this.unsafeServers.add(new String[]{"blockedserver.us", "This server is a test IP for blocked servers", String.valueOf(UnsafeServerAction.BLOCK)});

        this.unsafeServers.add(new String[]{"bridge.rip", "Bridge Network is unsafe.", String.valueOf(UnsafeServerAction.WARN)});
        this.unsafeServers.add(new String[]{"potpvp.com", "Potpvp is unsafe.", String.valueOf(UnsafeServerAction.WARN)});

        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
//        this.keyBindVoicePushToTalk = new KeyBinding("Voice Chat", 47, "CheatBreaker Client", true);
        this.keyBindOpenMenu = new KeyBinding("Open Menu", 54, "CheatBreaker Client", true);
//        this.keyBindOpenVoiceMenu = new KeyBinding("Open Voice Menu", 25, "CheatBreaker Client", true);
        this.keyBindDragToLook = new KeyBinding("Drag to look", 56, "CheatBreaker Client", true);
        this.keyBindBackLook = new KeyBinding("Back look", 0, "CheatBreaker Client", true);
        this.keyBindFrontLook = new KeyBinding("Front look", 0, "CheatBreaker Client", true);
       this.keyBindEmote = new KeyBinding("Emote", Keyboard.KEY_B, "CheatBreaker Client", true);
        gameSettings.keyBindings = ArrayUtils.addAll(gameSettings.keyBindings, /*this.keyBindVoicePushToTalk, */this.keyBindOpenMenu, /*this.keyBindOpenVoiceMenu, */this.keyBindDragToLook, this.keyBindBackLook, this.keyBindFrontLook, this.keyBindEmote );
    }

    public MainMenuBase.MenuTypes getCurrentMenu() {
        return MainMenuBase.MenuTypes.values()[(int) currentMenuSetting.getValue()];
    }

    public boolean isFavouriteColor(int colorValue) {
        ColorPickerColorElement var3;
        Iterator<ColorPickerColorElement> var2 = this.favouriteColors.iterator();
        do {
            if (!var2.hasNext()) {
                return false;
            }
            var3 = var2.next();
        } while (var3.color != colorValue);
        return true;
    }

    public void removeFavouriteColor(int colorValue) {
        this.favouriteColors.removeIf(var1x -> var1x.color == colorValue);
    }

    private void updateRPC() {
        try {
            if (Minecraft.getMinecraft().currentScreen instanceof HudLayoutEditorGui) {
                String account = this.showAccount.getBooleanValue() ? Minecraft.getMinecraft().getSession().getUsername() : null;
                CheatBreaker.getInstance().updateServerInfo(account);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
