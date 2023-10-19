package com.cheatbreaker.client.config;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.audio.music.data.Station;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.data.SettingType;
import com.cheatbreaker.client.module.impl.staff.StaffMod;
import com.cheatbreaker.client.ui.element.profile.ProfilesListElement;
import com.cheatbreaker.client.ui.element.type.ColorPickerColorElement;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class ConfigManager {

    private final List<ResourceLocation> presetLocations = new ArrayList<>();
    public List<Profile> moduleProfiles = new ArrayList<>();
    public Profile activeProfile;

    private final File configDir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION.replaceAll("\\.", "-"));
    public final File profileDir = new File(this.configDir + File.separator + "profiles");
    private final File globalConfig = new File(this.configDir + File.separator + "global.cfg");
    private final File mutesConfig = new File(this.configDir + File.separator + "mutes.cfg");
    private final File defaultConfig = new File(this.configDir + File.separator + "default.cfg");

    /**
     * Updates the current profile.
     */
    public void updateProfile() {
        if (this.createRequiredFiles()) {
//            CheatBreaker.getInstance().getModuleManager().miniMapMod.getVoxelMap().getMapOptions().saveAll();
            this.updateGlobalConfiguration(this.globalConfig);
            this.updateMutesConfiguration(this.mutesConfig);
            this.updateProfile(CheatBreaker.getInstance().getConfigManager().activeProfile.getName());
        }
    }

    /**
     * Returns if the player is using staff modules or not.
     */
    public boolean isUsingStaffModules() {
        for (final AbstractModule cbModule : CheatBreaker.getInstance().getModuleManager().staffMods) {
            cbModule.setStaffModuleEnabled(true);
            if (cbModule.isStaffModuleEnabled()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates the default Configuration Preset.
     */
    public void createDefaultConfigurationPresets() {
        File profileDir = this.profileDir;
        if (profileDir.exists() || profileDir.mkdirs()) {
            for (ResourceLocation presets : this.presetLocations) {
                String presetsName = presets.getResourcePath().replaceAll("([a-zA-Z0-9/]+)/", "");
                File presetsFile = new File(profileDir, presetsName);
                if (presetsFile.exists()) continue;
                try {
                    InputStream presetInputStream = Minecraft.getMinecraft().getResourceManager().getResource(presets).getInputStream();
                    Files.copy(presetInputStream, presetsFile.toPath());
                    presetInputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        CheatBreaker.getInstance().logger.info(CheatBreaker.getInstance().loggerPrefix + "Created default configuration presets");
    }

    /**
     * Reads the current profile.
     */
    public void readCurrentProfile() {
        if (this.createRequiredFiles()) {
            this.readGlobalConfiguration(this.globalConfig);
            this.readMutesConfiguration(this.mutesConfig);
            if (CheatBreaker.getInstance().getConfigManager().activeProfile == null) {
                CheatBreaker.getInstance().getConfigManager().activeProfile = this.moduleProfiles.get(0);
            } else {
                this.readProfile(CheatBreaker.getInstance().getConfigManager().activeProfile.getName());
            }
//            CheatBreaker.getInstance().getModuleManager().miniMapMod.getVoxelMap().getMapOptions().loadAll();
        }
    }

    /**
     * Creates the required files for configs.
     */
    private boolean createRequiredFiles() {
        try {
            return !(!this.configDir.exists() && !this.configDir.mkdirs() || !this.defaultConfig.exists() && !this.defaultConfig.createNewFile() || !this.globalConfig.exists() && !this.globalConfig.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Gets the most recently created profile name.
     */
    private String getNewProfileName(String name) {
        File clientDir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION.replaceAll("\\.", "-"));
        File profilesDir = new File(clientDir + File.separator + "profiles");
        if ((profilesDir.exists() || profilesDir.mkdirs()) && new File(profilesDir + File.separator + name + ".cfg").exists()) {
            return this.getNewProfileName(name + "1");
        }
        return name;
    }

    /**
     * Loads all module profiles.
     */
    public void loadProfiles() {
        this.moduleProfiles.add(new Profile("default", true));
        File profilesDir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION.replaceAll("\\.", "-") + File.separator + "profiles");
        if (profilesDir.exists()) {
            for (File profile : Objects.requireNonNull(profilesDir.listFiles())) {
                Minecraft.getMinecraft().cbLoadingScreen.addPhase();
                if (!profile.getName().endsWith(".cfg")) continue;
                this.moduleProfiles.add(new Profile(profile.getName().replace(".cfg", ""), false));
            }
        }

        CheatBreaker.getInstance().logger.info(CheatBreaker.getInstance().loggerPrefix + "Loaded " + this.moduleProfiles.size() + " custom profiles");
    }

    /**
     * Creates a new profile.
     */
    public void createNewProfile() {
        if (this.activeProfile == this.moduleProfiles.get(0)) {
            Profile defaultProfile;
            CheatBreaker.getInstance().getConfigManager().activeProfile = defaultProfile = new Profile(this.getNewProfileName("Profile 1"), false);
            this.moduleProfiles.add(defaultProfile);
            CheatBreaker.getInstance().configManager.updateProfile();
            Minecraft var2 = Minecraft.getMinecraft();
            if (var2.currentScreen instanceof HudLayoutEditorGui) {
                ProfilesListElement var3 = (ProfilesListElement) ((HudLayoutEditorGui) var2.currentScreen).profilesElement;
                var3.loadProfiles();
            }
        }
    }

    /**
     * Reads a profile.
     */
    public void readProfile(String string) {
        if (string.equalsIgnoreCase("default")) {
            this.activeProfile = this.moduleProfiles.get(0);
            for (AbstractModule module : CheatBreaker.getInstance().getModuleManager().playerMods) {
                module.setState(module.defaultState);
                module.setWasRenderHud(module.defaultWasRenderHud);
                module.setAnchor(module.defaultGuiAnchor);
                module.setTranslations(module.defaultXTranslation, module.defaultYTranslation);
                module.setRenderHud(module.defaultRenderHud);
                for (int i = 0; i < module.getSettingsList().size(); ++i) {
                    try {
                        module.getSettingsList().get(i).updateSettingValue(module.getDefaultSettingsValues().get(i), false);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            return;
        }

        File file = new File(this.configDir + File.separator + "profiles");
        File file2 = file.exists() || file.mkdirs() ? new File(file + File.separator + string + ".cfg") : null;

        if (!file2.exists()) {
            this.updateProfile(string);
            return;
        }

        ArrayList<AbstractModule> arrayList = new ArrayList<AbstractModule>();
        arrayList.addAll(CheatBreaker.getInstance().getModuleManager().playerMods);
        arrayList.addAll(CheatBreaker.getInstance().getModuleManager().staffMods);

        try {
            String string2;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
            AbstractModule module = null;
            block34: while ((string2 = bufferedReader.readLine()) != null) {
                try {
                    String[] split;
                    if (string2.startsWith("#") || string2.length() == 0) continue;
                    if (string2.startsWith("[")) {
                        for (AbstractModule object222 : arrayList) {
                            if (!("[" + object222.getName() + "]").equalsIgnoreCase(string2)) continue;
                            module = object222;
                            continue block34;
                        }
                        continue;
                    }
                    if (module == null) continue;
                    if (string2.startsWith("-")) {
                        split = string2.replaceFirst("-", "").split("=", 2);
                        if (split.length != 2) continue;
                        try {
                            block12 : switch (split[0]) {
                                case "State":
                                    if (module.isStaffModule()) break;
                                    module.setState(Boolean.parseBoolean(split[1]));
                                    break;
                                case "WasRenderHUD":
                                    module.setWasRenderHud(Boolean.parseBoolean(split[1]));
                                    break;
                                case "RenderHUD":
                                    module.setRenderHud(Boolean.parseBoolean(split[1]));
                                    break;
                                case "Position":
                                    if (module.getGuiAnchor() == null) break;
                                    for (GuiAnchor cBGuiAnchor : GuiAnchor.values()) {
                                        if (!cBGuiAnchor.getLabel().equalsIgnoreCase(split[1])) continue;
                                        module.setAnchor(cBGuiAnchor);
                                        break block12;
                                    }
                                    continue block34;
                                case "xTranslation":
                                    module.setXTranslation(Float.parseFloat(split[1]));
                                    break;
                                case "yTranslation":
                                    module.setYTranslation(Float.parseFloat(split[1]));
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        continue;
                    }

                    split = string2.split("=", 2);
                    if (split.length != 2) continue;
                    if (module == CheatBreaker.getInstance().getModuleManager().autoTextMod) {
                        for (int j = 0; j < 50; j++) {
                            if (split[1].contains("keycode:")) {
                                boolean mouseFlag = false;
                                Setting setting;
                                String val = "";

                                if (split[1].contains("mouse:")) {
                                    val = StringUtils.substringAfter(split[1], ";mouse:");
                                    mouseFlag = true;
                                }

                                String key = StringUtils.substringAfter(split[1], ";keycode:").replaceAll(";mouse:" + val, "");

                                Object[] arrobject = split[1].split(";");
                                String value = (String) arrobject[0];
                                value = value.replaceAll(";mouse:" + val, "");

                                if (Integer.parseInt(key) == 0 && Boolean.parseBoolean(val)) {
                                    mouseFlag = false;
                                }

                                if (!CheatBreaker.getInstance().getModuleManager().autoTextMod.containsSettingByName(split[0])) {
                                    setting = new Setting(CheatBreaker.getInstance().getModuleManager().autoTextMod.getSettingsList(), split[0]);
                                    setting.setValue(value).setKeyCode(Integer.parseInt(key));
                                    setting.setHasMouseBind(mouseFlag);
                                }
                            }
                        }
                    }

                    for (Setting setting : module.getSettingsList()) {
                        if (setting.getSettingName().equalsIgnoreCase("label") || !setting.getSettingName().equalsIgnoreCase(split[0]))
                            continue;


                        if (module == CheatBreaker.getInstance().getModuleManager().autoTextMod) {
                            break;
                        }

                        if (setting.getSettingName().endsWith("Keybind") && split[1].contains("mouse:")) {
                            boolean val = Boolean.parseBoolean(StringUtils.substringAfter(split[1], ";mouse:"));

                            Object[] arrobject = split[1].split(";");
                            String value = (String) arrobject[0];

                            System.out.println("mouse boolean: " + val);
                            System.out.println("value: " + value);

                            if (Integer.parseInt(value) == 0 && val) {
                                setting.setHasMouseBind(false); // prevent possible issues
                            }

                            setting.setValue(Integer.parseInt(value)).setHasMouseBind(val);
                            break;
                        }

                        try {
                            switch (setting.getType()) {
                                case BOOLEAN:
                                    setting.setValue(Boolean.parseBoolean(split[1]));
                                    break;
                                case INTEGER:
                                    if (module.isStaffModule() && setting == ((StaffMod)module).getKeybindSetting()) {
                                        ((StaffMod)module).getKeybindSetting().setValue(Integer.parseInt(split[1]));
                                        break;
                                    }

                                    if (split[1].contains("rainbow")) {
                                        Object[] arrobject = split[1].split(";");
                                        int n = Integer.parseInt((String) arrobject[0]);
                                        setting.rainbow = true;
                                        if (n > (Integer)setting.getMaximumValue() || n < (Integer)setting.getMinimumValue()) continue block34;
                                        setting.setValue(n);
                                        break;
                                    }

                                    if (setting.getSettingName().endsWith("Keybind")) {
                                        setting.setValue(Integer.parseInt(split[1]));
                                        break;
                                    }

                                    int n = Integer.parseInt(split[1]);
                                    setting.rainbow = false;
                                    if (n > (Integer)setting.getMaximumValue() || n < (Integer)setting.getMinimumValue()) continue block34;
                                    setting.setValue(n);
                                    break;
                                case FLOAT:
                                    float f = Float.parseFloat(split[1]);
                                    if (!(f <= (Float) setting.getMaximumValue()) || !(f >= (Float) setting.getMinimumValue())) break;
                                    setting.setValue(f);
                                    break;
                                case DOUBLE:
                                    double d = Double.parseDouble(split[1]);
                                    if (!(d <= (Double)setting.getMaximumValue()) || !(d >= (Double)setting.getMinimumValue())) break;
                                    setting.setValue(d);
                                    break;
                                case ARRAYLIST:
                                    ArrayList<String> strList = new ArrayList<>(Arrays.asList(split[1].replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",")));
                                    List<Integer> listOfInteger = strList.stream().map(Integer::parseInt).collect(Collectors.toList());
                                    setting.setValue(listOfInteger);
                                    break;
                                case STRING_ARRAY:
                                    boolean bl = false;
                                    for (String value : setting.getAcceptedStringValues()) {
                                        if (!value.equalsIgnoreCase(split[1])) continue;
                                        bl = true;
                                    }
                                    if (!bl) break;
                                    setting.setValue(split[1]);
                                    break;
                                case STRING:
                                    if (setting.getSettingName().equalsIgnoreCase("label")) break;

                                    if (setting == CheatBreaker.getInstance().getModuleManager().toggleSprintMod.flyBoostString) {
                                        split[1] = split[1].replaceAll("%FPS%", "%BOOST%");
                                    }

                                    setting.setValue(split[1].replaceAll("&([abcdefghijklmrABCDEFGHIJKLMNR0-9])|(&$)", "§$1"));
                            }
                            continue block34;
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            if (setting != CheatBreaker.getInstance().getModuleManager().keystrokesMod.boxSize) continue;
                            CheatBreaker.getInstance().getModuleManager().keystrokesMod.updateKeyElements();
                            Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages();
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            bufferedReader.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        this.updateProfile(string);
    }

    /**
     * Reads the global settings configuration file.
     */
    public void readGlobalConfiguration(File file) {
        if (!file.exists()) {
            this.updateGlobalConfiguration(file);
            return;
        }
        try {
            String string;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            block21: while ((string = bufferedReader.readLine()) != null) {
                try {
                    File profileFile;
                    String[] arrString;
                    if (string.startsWith("#") || string.length() == 0 || (arrString = string.split("=", 2)).length != 2) continue;

                    if (arrString[0].equalsIgnoreCase("FavoriteColors")) {
                        String[] declaration = arrString[1].split(",");
                        for (String object2 : declaration) {
                            try {
                                CheatBreaker.getInstance().getGlobalSettings().favouriteColors.add(new ColorPickerColorElement(1.0f, Integer.parseInt(object2), 1.0f));
                            } catch (NumberFormatException numberFormatException) {
                                numberFormatException.printStackTrace();
                            }
                        }
                        continue;
                    }

                    if (arrString[0].equalsIgnoreCase("FavoriteStations")) {
                        String[] declaration = arrString[1].split(",");
                        for (String object2 : declaration) {
                            try {
                                for (Station station : CheatBreaker.getInstance().getDashManager().getStations()) {
                                    if (!station.getName().equalsIgnoreCase(object2)) continue;
                                    station.setFavourite(true);
                                }
                            } catch (NumberFormatException numberFormatException) {
                                numberFormatException.printStackTrace();
                            }
                        }
                        continue;
                    }
                    if (arrString[0].equalsIgnoreCase("XrayBlocks")) {
                        CheatBreaker.getInstance().getModuleManager().xray.getBlocks().clear();
                        String[] declaration = arrString[1].split(",");
                        for (String object2 : declaration) {
                            try {
                                CheatBreaker.getInstance().getModuleManager().xray.getBlocks().add(Integer.parseInt(object2));
                            } catch (NumberFormatException numberFormatException) {
                                numberFormatException.printStackTrace();
                            }
                        }
                        continue;
                    }

                    if (arrString[0].startsWith("key_")) {
                        for (KeyBinding keyBinding : Minecraft.getMinecraft().gameSettings.keyBindings) {
                            if (!keyBinding.isCheatBreakerKeybind || !arrString[0].equalsIgnoreCase("key_" + keyBinding.getKeyDescription())) continue;
                            keyBinding.setKeyCode(Integer.parseInt(arrString[1]));
                        }
                        continue;
                    }

                    if (arrString[0].equalsIgnoreCase("ProfileIndexes")) {
                        String[] declaration = arrString[1].split("]\\[");
                        for (String object2 : declaration) {
                            object2 = object2.replaceFirst("\\[", "");
                            String[] arrstring2 = object2.split(",", 2);
                            try {
                                int n = Integer.parseInt(arrstring2[1]);
                                for (Profile profile : this.moduleProfiles) {
                                    if (n == 0 || !profile.getName().equalsIgnoreCase(arrstring2[0])) continue;
                                    profile.index = n;
                                }
                            } catch (NumberFormatException numberFormatException) {

                            }
                        }
                        continue;
                    }
                    if (arrString[0].equalsIgnoreCase("ActiveProfile")) {
                        profileFile = null;
                        File file2 = new File(this.configDir + File.separator + "profiles");
                        if (file2.exists() || file2.mkdirs()) {
                            profileFile = new File(file2 + File.separator + arrString[1] + ".cfg");
                        }
                        if (profileFile == null || !profileFile.exists()) continue;
                        Profile object3 = null;
                        for (Profile object2 : this.moduleProfiles) {
                            if (!arrString[1].equalsIgnoreCase(object2.getName())) continue;
                            object3 = object2;
                        }
                        if (object3 == null || object3.getName().equalsIgnoreCase("default")) continue;
                        this.activeProfile = object3;
                        continue;
                    }
                    for (Setting setting : CheatBreaker.getInstance().getGlobalSettings().settingsList) {
                        if (setting.getSettingName().equalsIgnoreCase("label") || !setting.getSettingName().equalsIgnoreCase(arrString[0])) continue;
                        try {
                            switch (setting.getType()) {
                                case BOOLEAN:
                                    setting.setValue(Boolean.parseBoolean(arrString[1]));
                                    break;
                                case INTEGER:
                                    if (arrString[1].contains("rainbow")) {
                                        String[] arrstring3 = arrString[1].split(";");
                                        int n = Integer.parseInt(arrstring3[0]);
                                        setting.rainbow = true;
                                        if (n > (Integer)setting.getMaximumValue() || n < (Integer)setting.getMinimumValue()) continue block21;
                                        setting.setValue(n);
                                        break;
                                    }
                                    int n = Integer.parseInt(arrString[1]);
                                    setting.rainbow = false;
                                    if (n > (Integer)setting.getMaximumValue() || n < (Integer)setting.getMinimumValue()) continue block21;
                                    setting.setValue(n);
                                    break;
                                case FLOAT:
                                    float f = Float.parseFloat(arrString[1]);
                                    if (!(f <= (Float) setting.getMaximumValue()) || !(f >= (Float) setting.getMinimumValue())) break;
                                    setting.setValue(f);
                                    break;
                                case DOUBLE:
                                    double d = Double.parseDouble(arrString[1]);
                                    if (!(d <= (Double)setting.getMaximumValue()) || !(d >= (Double)setting.getMinimumValue())) break;
                                    setting.setValue(d);
                                    break;
                                case ARRAYLIST:
                                    ArrayList<String> strList = new ArrayList<>(Arrays.asList(arrString[1].split(",")));
                                    List<Integer> listOfInteger = strList.stream().map(Integer::parseInt).collect(Collectors.toList());
                                    setting.setValue(listOfInteger);
                                    break;
                                case STRING_ARRAY:
                                    boolean bl = false;
                                    for (String acceptedValue : setting.getAcceptedStringValues()) {
                                        if (!acceptedValue.equalsIgnoreCase(arrString[1])) continue;
                                        bl = true;
                                    }
                                    if (!bl) break;
                                    setting.setValue(arrString[1]);
                            }
                            continue block21;
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            bufferedReader.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        this.updateGlobalConfiguration(file);
    }

    /**
     * Writes to the global configuration file.
     */
    public void updateGlobalConfiguration(File file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("################################");
            bufferedWriter.newLine();
            bufferedWriter.write("# MC_Client: GLOBAL SETTINGS");
            bufferedWriter.newLine();
            bufferedWriter.write("################################");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            if (this.activeProfile != null && !this.activeProfile.getName().equals("default")) {
                bufferedWriter.write("ActiveProfile=" + this.activeProfile.getName());
                bufferedWriter.newLine();
            }
            for (Setting setting : CheatBreaker.getInstance().getGlobalSettings().settingsList) {
                if (setting.getSettingName().equalsIgnoreCase("label")) continue;
                if (setting.rainbow) {
                    bufferedWriter.write(setting.getSettingName() + "=" + setting.getValue() + ";rainbow");
                } else {
                    bufferedWriter.write(setting.getSettingName() + "=" + setting.getValue());
                }
                bufferedWriter.newLine();
            }
            for (KeyBinding keyBind : Minecraft.getMinecraft().gameSettings.keyBindings) {
                if (!(keyBind.isCheatBreakerKeybind)) continue;
                bufferedWriter.write("key_" + keyBind.getKeyDescription() + "=" + keyBind.getKeyCode());
                bufferedWriter.newLine();
            }
            String object3 = "";
            for (ColorPickerColorElement colorPickerColorElement : CheatBreaker.getInstance().getGlobalSettings().favouriteColors) {
                object3 = object3 + colorPickerColorElement.color + (CheatBreaker.getInstance().getGlobalSettings().favouriteColors.indexOf(colorPickerColorElement) == CheatBreaker.getInstance().getGlobalSettings().favouriteColors.size() - 1 ? "" : ",");
            }
            if (!object3.equals("")) {
                bufferedWriter.write("FavoriteColors=" + object3);
                bufferedWriter.newLine();
            }
            StringBuilder stations = new StringBuilder();
            for (Station station : CheatBreaker.getInstance().getDashManager().getStations()) {
                if (!station.isFavourite()) continue;
                if (stations.length() != 0) {
                    stations.append(",");
                }
                stations.append(station.getName());
            }
            if (!stations.toString().equals("")) {
                bufferedWriter.write("FavoriteStations=" + stations);
                bufferedWriter.newLine();
            }
            StringBuilder selectedXRayBlocks = new StringBuilder();
            for (int blocks : CheatBreaker.getInstance().getModuleManager().xray.getBlocks()) {
                if (selectedXRayBlocks.length() != 0) {
                    selectedXRayBlocks.append(",");
                }
                selectedXRayBlocks.append(blocks);
            }
            bufferedWriter.write("XrayBlocks=" + selectedXRayBlocks);
            bufferedWriter.newLine();
            bufferedWriter.write("ProfileIndexes=");
            for (Profile profile : this.moduleProfiles) {
                bufferedWriter.write("[" + profile.getName() + "," + profile.index + "]");
            }
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    /**
     * Reads the muted configuration file.
     * This is used for the Voice Chat integration.
     */
    public void readMutesConfiguration(File file) {
        try {
            String string;
            if (!file.exists()) {
                this.updateMutesConfiguration(file);
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((string = bufferedReader.readLine()) != null) {
                try {
                    UUID uUID = UUID.fromString(string);
                    CheatBreaker.getInstance().getCBNetHandler().getPlayersInVoiceChannel().add(uUID);
                } catch (Exception ignored) {
                }
            }
        } catch (IOException ignored) {

        }
    }

    /**
     * Writes to the muted configuration file.
     * This is used for the Voice Chat integration.
     */
    public void updateMutesConfiguration(File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (UUID uUID : CheatBreaker.getInstance().getCBNetHandler().getPlayersInVoiceChannel()) {
                bufferedWriter.write(uUID.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ignored) {

        }
    }

    /**
     * Reads the muted configuration file.
     * This is used for the Voice Chat integration.
     */
    public void updateProfile(String profileName) {
        if (profileName.equalsIgnoreCase("default")) {
            return;
        }
        File file = new File(this.configDir + File.separator + "profiles");
        File file2 = file.exists() || file.mkdirs() ? new File(file + File.separator + profileName + ".cfg") : null;
        ArrayList<AbstractModule> modules = new ArrayList<>();
        modules.addAll(CheatBreaker.getInstance().getModuleManager().playerMods);
        modules.addAll(CheatBreaker.getInstance().getModuleManager().staffMods);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file2));
            writer.write("################################");
            writer.newLine();
            writer.write("# MC_Client: MODULE SETTINGS");
            writer.newLine();
            writer.write("################################");
            writer.newLine();
            writer.newLine();
            for (AbstractModule module : modules) {
                writer.write("[" + module.getName() + "]");
                writer.newLine();
                writer.write("-State=" + module.isEnabled());
                writer.newLine();
                writer.write("-WasRenderHUD=" + module.isWasRenderHud());
                writer.newLine();
                if (module.getGuiAnchor() != null) {
                    writer.write("-Position=" + module.getGuiAnchor().getLabel());
                    writer.newLine();
                }
                writer.write("-xTranslation=" + module.getXTranslation());
                writer.newLine();
                writer.write("-yTranslation=" + module.getYTranslation());
                writer.newLine();
                writer.write("-RenderHUD=" + module.isRenderHud());
                writer.newLine();
                for (Setting setting : module.getSettingsList()) {
                    if (setting == CheatBreaker.getInstance().getModuleManager().coordinatesMod.customLine) {
                        writer.write("# Customize your HUD info display string.");
                        writer.newLine();
                        writer.write("# (& color formatting is allowed)");
                        writer.newLine();
                        writer.write("# Optional uses:");
                        writer.newLine();
                        writer.write("# %FPS% - Display current FPS.");
                        writer.newLine();
                        writer.write("# %DIR% - Display current look direction.");
                        writer.newLine();
                        writer.write("# %CPS% - Display CPS.");
                        writer.newLine();
                        writer.write("# %COORDS% - Display coordinates.");
                        writer.newLine();
                        writer.write("# %IP% - Current server IP.");
                        writer.newLine();
                        writer.write("# %X% - Your X location.");
                        writer.newLine();
                        writer.write("# %Y% - Your (foot) Y location.");
                        writer.newLine();
                        writer.write("# %Z% - Your Z location.");
                        writer.newLine();
                        writer.write("# %NL% - Break into a new line.");
                        writer.newLine();
                    }
                    if (setting.getSettingName().equalsIgnoreCase("label")) continue;
                    if (setting.getType() == SettingType.STRING && setting.getSettingName().startsWith("Hot key")) {
                        writer.write(setting.getSettingName() + "=" + (setting.getValue() + (setting.isHasKeycode() ? ";keycode:" + setting.getKeyCode() : "") + (setting.isHasMouseBind() ? ";mouse:" + setting.isHasMouseBind() : "")).replaceAll("§", "&"));
                    } else if (setting.getType() == SettingType.INTEGER && setting.getSettingName().endsWith("Keybind") && setting.getSettingName().contains("Toggle")) {
                        writer.write(setting.getSettingName() + "=" + setting.getValue() + (setting.isHasMouseBind() ? ";mouse:" + setting.isHasMouseBind() : ""));
                    } else if (setting.rainbow) {
                        writer.write(setting.getSettingName() + "=" + setting.getValue() + ";rainbow");
                    } else {
                        writer.write(setting.getSettingName() + "=" + setting.getValue());
                    }
                    writer.newLine();
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}
