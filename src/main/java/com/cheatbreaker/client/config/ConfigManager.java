package com.cheatbreaker.client.config;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.data.SettingType;
import com.cheatbreaker.client.module.impl.staff.StaffMod;
import com.cheatbreaker.client.audio.music.data.Station;
import com.cheatbreaker.client.ui.element.type.ColorPickerColorElement;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.src.Config;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConfigManager {
    private final File configDir;
    private final File globalConfig;
    private final File mutesConfig;
    private final File defaultConfig;
    public final File profileDir;

    public ConfigManager() {
        this.configDir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "cheatbreaker-client-" + Config.MC_VERSION + "-" + CheatBreaker.getInstance().getGitBranch());
        this.globalConfig = new File(this.configDir + File.separator + "global.cfg");
        this.mutesConfig = new File(this.configDir + File.separator + "mutes.cfg");
        this.defaultConfig = new File(this.configDir + File.separator + "default.cfg");
        this.profileDir = new File(this.configDir + File.separator + "profiles");
    }

    public void write() {
        if (this.createRequiredFiles()) {
            CheatBreaker.getInstance().getModuleManager().miniMapMod.getVoxelMap().getMapOptions().saveAll();
            this.writeGlobalConfig(this.globalConfig);
            this.writeMutesConfig(this.mutesConfig);
            this.writeProfile(CheatBreaker.getInstance().activeProfile.getName());
        }
    }

    public void read() {
        if (this.createRequiredFiles()) {
            this.redGlobalConfig(this.globalConfig);
            this.readMutesConfig(this.mutesConfig);
            if (CheatBreaker.getInstance().activeProfile == null) {
                CheatBreaker.getInstance().activeProfile = CheatBreaker.getInstance().profiles.get(0);
            } else {
                this.readProfile(CheatBreaker.getInstance().activeProfile.getName());
            }
            CheatBreaker.getInstance().getModuleManager().miniMapMod.getVoxelMap().getMapOptions().loadAll();
        }
    }

    private boolean createRequiredFiles() {
        try {
            return !(!this.configDir.exists() && !this.configDir.mkdirs() || !this.defaultConfig.exists() && !this.defaultConfig.createNewFile() || !this.globalConfig.exists() && !this.globalConfig.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /*
     * Could not resolve type clashes
     */
    public void readProfile(String string) {
        if (string.equalsIgnoreCase("default")) {
            CheatBreaker.getInstance().activeProfile = CheatBreaker.getInstance().profiles.get(0);
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
            this.writeProfile(string);
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
                    for (Setting setting : module.getSettingsList()) {
                        if (setting.getSettingName().equalsIgnoreCase("label") || !setting.getSettingName().equalsIgnoreCase(split[0]))
                            continue;

                        if (split[1].contains("keycode:")) {
                            String val = "";

                            if (split[1].contains("mouse:")) {
                                val = StringUtils.substringAfter(split[1], ";mouse:");
                                setting.setHasMouseBind(true);
                            }

                            String key = StringUtils.substringAfter(split[1], ";keycode:").replaceAll(";mouse:" + val, "");

                            Object[] arrobject = split[1].split(";");
                            String value = (String) arrobject[0];
                            value = value.replaceAll(";mouse:" + val, "");

                            if (Integer.parseInt(key) == 0 && Boolean.parseBoolean(val)) {
                                setting.setHasMouseBind(false);
                            }

                            setting.setValue(value).setKeyCode(Integer.parseInt(key)).setHasKeycode(true);
                            break;
                        }

                        if (setting.getSettingName().endsWith("Keybind") && split[1].contains("mouse:")) {
                            boolean val = Boolean.parseBoolean(StringUtils.substringAfter(split[1], ";mouse:"));

                            Object[] arrobject = split[1].split(";");
                            String value = (String) arrobject[0];

//                            System.out.println("mouse boolean: " + val);
//                            System.out.println("value: " + value);

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
                            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146245_b();
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
        this.writeProfile(string);
    }

    /*
     * Could not resolve type clashes
     */
    public void redGlobalConfig(File file) {
        if (!file.exists()) {
            this.writeGlobalConfig(file);
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
                        CheatBreaker.getInstance().getModuleManager().staffModuleXray.getBlocks().clear();
                        String[] declaration = arrString[1].split(",");
                        for (String object2 : declaration) {
                            try {
                                CheatBreaker.getInstance().getModuleManager().staffModuleXray.getBlocks().add(Integer.parseInt(object2));
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
                                for (Profile profile : CheatBreaker.getInstance().profiles) {
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
                        for (Profile object2 : CheatBreaker.getInstance().profiles) {
                            if (!arrString[1].equalsIgnoreCase(object2.getName())) continue;
                            object3 = object2;
                        }
                        if (object3 == null || object3.getName().equalsIgnoreCase("default")) continue;
                        CheatBreaker.getInstance().activeProfile = object3;
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
        this.writeGlobalConfig(file);
    }

    /*
     * Could not resolve type clashes
     */
    public void writeGlobalConfig(File file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("################################");
            bufferedWriter.newLine();
            bufferedWriter.write("# MC_Client: GLOBAL SETTINGS");
            bufferedWriter.newLine();
            bufferedWriter.write("################################");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            if (CheatBreaker.getInstance().activeProfile != null && !CheatBreaker.getInstance().activeProfile.getName().equals("default")) {
                bufferedWriter.write("ActiveProfile=" + CheatBreaker.getInstance().activeProfile.getName());
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
            for (int blocks : CheatBreaker.getInstance().getModuleManager().staffModuleXray.getBlocks()) {
                if (selectedXRayBlocks.length() != 0) {
                    selectedXRayBlocks.append(",");
                }
                selectedXRayBlocks.append(blocks);
            }
            bufferedWriter.write("XrayBlocks=" + selectedXRayBlocks);
            bufferedWriter.newLine();
            bufferedWriter.write("ProfileIndexes=");
            for (Profile profile : CheatBreaker.getInstance().profiles) {
                bufferedWriter.write("[" + profile.getName() + "," + profile.index + "]");
            }
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void readMutesConfig(File file) {
        try {
            String string;
            if (!file.exists()) {
                this.writeMutesConfig(file);
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((string = bufferedReader.readLine()) != null) {
                try {
                    UUID uUID = UUID.fromString(string);
                    CheatBreaker.getInstance().getCbNetHandler().getVoiceUsers().add(uUID);
                } catch (Exception ignored) {}
            }
        } catch (IOException ignored) {

        }
    }

    public void writeMutesConfig(File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (UUID uUID : CheatBreaker.getInstance().getCbNetHandler().getVoiceUsers()) {
                bufferedWriter.write(uUID.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ignored) {

        }
    }

    public void writeProfile(String string) {
        if (string.equalsIgnoreCase("default")) {
            return;
        }
        File file = new File(this.configDir + File.separator + "profiles");
        File file2 = file.exists() || file.mkdirs() ? new File(file + File.separator + string + ".cfg") : null;
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
                    if (setting.getType() == SettingType.STRING) {
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
