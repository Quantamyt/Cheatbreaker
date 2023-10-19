package com.cheatbreaker.client.ui.element.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.ModuleManager;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.data.SettingType;
import com.cheatbreaker.client.module.impl.staff.StaffMod;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.element.AbstractScrollableElement;
import com.cheatbreaker.client.ui.element.type.*;
import com.cheatbreaker.client.ui.element.type.custom.*;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import javax.xml.crypto.dsig.keyinfo.KeyName;
import javax.xml.soap.Text;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ModuleListElement extends AbstractScrollableElement {
    private final int highlightColor;
    protected final List<ModuleSettingsElement> moduleSetting;
    private final boolean isStaffMods;
    private final GlobalSettingsElement globalSettings;
    public AbstractScrollableElement scrollableElement;
    public boolean resetColor = false;
    public AbstractModule module;
    private final ModulesGuiButtonElement backButton;
    private final ModulesGuiButtonElement resetSettingsButton;
    private ModulesGuiButtonElement resetPositionButton = null;
    private final ModulesGuiButtonElement applyToAllTextButton;
    private ModulesGuiButtonElement addOneButton;

    private final Map<Object, ArrayList<AbstractModulesGuiElement>> moduleElementListMap;
    private final List<AbstractModulesGuiElement> settingElement;

    public ModuleListElement(List modules, float scaleFactor, int n, int n2, int n3, int n4) {
        super(scaleFactor, n, n2, n3, n4);
        this.isStaffMods = modules == CheatBreaker.getInstance().getModuleManager().staffMods;
        this.highlightColor = -12418828;
        this.globalSettings = new GlobalSettingsElement(this, this.highlightColor, scaleFactor);
        this.moduleSetting = new ArrayList<>();

        for (Object module : modules) {
            if (((AbstractModule)module).isStaffModule() && !((AbstractModule)module).isStaffModuleEnabled()) continue;
            this.moduleSetting.add(new ModuleSettingsElement(this, this.highlightColor, (AbstractModule)module, scaleFactor));
            if (((AbstractModule) module).getGuiAnchor() != null) {
                this.resetPositionButton = new ModulesGuiButtonElement(CheatBreaker.getInstance().ubuntuMedium16px, null, "Reset Position", this.x + 2, this.y + 4, 14, 14, this.highlightColor, scaleFactor);
            }
        }

        this.backButton = new ModulesGuiButtonElement(null, "arrow-64.png", this.x + 2, this.y + 4, 28, 28, this.highlightColor, scaleFactor, false);
        this.resetSettingsButton = new ModulesGuiButtonElement(CheatBreaker.getInstance().ubuntuMedium16px, null, "Reset Settings", this.x + 2, this.y + 4, 14, 14, this.highlightColor, scaleFactor);
        this.module = null;
        this.moduleElementListMap = new HashMap<>();

        for (Object module : modules) {
            if (((AbstractModule)module).isStaffModule() && !((AbstractModule)module).isStaffModuleEnabled() || module == CheatBreaker.getInstance().getModuleManager().miniMapMod) continue;
            ArrayList<AbstractModulesGuiElement> elements = new ArrayList<>();
            for (Setting setting : ((AbstractModule)module).getSettingsList()) {
                switch (setting.getType()) {
                    case BOOLEAN:
                        //if (!setting.getParentValue()) break;
                        elements.add(new ToggleElement(setting, scaleFactor));
                        break;
                    case DOUBLE:
                    case INTEGER:
                    case FLOAT:
                        if (((AbstractModule)module).isStaffModule() && setting == ((StaffMod)module).getKeybindSetting() || ((AbstractModule)module).isStaffModule() && setting == ((AbstractModule)module).scale) break;
                        if (((AbstractModule)module).guiAnchor == null && !(module == CheatBreaker.getInstance().getModuleManager().crosshairMod) && setting == ((AbstractModule)module).scale) break;
                        //if (!setting.getParentValue()) break;
                        if (setting.getType().equals(SettingType.INTEGER) && setting.getSettingName().toLowerCase().contains("color")) {
                            elements.add(new ColorPickerElement(setting, scaleFactor));
                            break;
                        }

                        if (setting.getType().equals(SettingType.INTEGER) && setting.getSettingName().endsWith("Keybind")) {
                            if (((AbstractModule)module).guiAnchor == null && !(module == CheatBreaker.getInstance().getModuleManager().crosshairMod) && setting == ((AbstractModule)module).tempHideFromHUDKeybind) break;
                            if (((AbstractModule)module).notRenderHUD && !(module == CheatBreaker.getInstance().getModuleManager().crosshairMod) && setting == ((AbstractModule)module).hideFromHUDKeybind) break;
                            KeybindElement elementToAdd = new KeybindElement(setting, scaleFactor);
                            elements.add(elementToAdd);
                            CheatBreaker.getInstance().getModuleManager().keybinds.put(setting, elementToAdd);
                            break;
                        }

                        elements.add(new NewSliderElement(setting, scaleFactor));
                        break;
                    case ARRAYLIST:
                        elements.add(new ExcludePotionsElement(setting, scaleFactor));
                        break;
                    case STRING_ARRAY:
                        if (((AbstractModule)module).guiAnchor == null && !(module == CheatBreaker.getInstance().getModuleManager().crosshairMod) && setting == ((AbstractModule)module).guiScale) break;
                        elements.add(new ChoiceElement(setting, scaleFactor));
                        break;
                    case STRING:
                        if (setting.getSettingName().endsWith("String") || setting.getSettingName().contains("Background)")) {
                            TextFieldElement elementToAdd = new TextFieldElement(setting, scaleFactor);
                            elements.add(elementToAdd);
                            break;
                        }

                        if (setting.getSettingName().startsWith("Hotkey") && (setting.isHasKeycode() || setting.isHasMouseBind())) {
                            TextFieldElement elementToAdd = new TextFieldElement(setting, scaleFactor);
                            elements.add(elementToAdd);
                            break;
                        }

                        if (!setting.getSettingName().equalsIgnoreCase("label")) break;
                        elements.add(new LabelElement(setting, scaleFactor));
                        if (!CheatBreaker.getInstance().getModuleManager().crosshairMod.crosshairPreviewLabel.getValue().equals(setting.getValue())) break;
                        elements.add(new CrosshairElement(scaleFactor));
                }
            }

//            if (module == CheatBreaker.getInstance().getModuleManager().potionEffectsMod) {
//                elements.add(new ExcludePotionsElement(CheatBreaker.getInstance().getModuleManager().potionEffectsMod.getEffects(), "Exclude Specific Effects", scaleFactor));
//            }

            if (((AbstractModule)module).isStaffModule()) {
                elements.add(new KeybindElement(((StaffMod)module).getKeybindSetting(), scaleFactor));
                if (module == CheatBreaker.getInstance().getModuleManager().staffModuleXray) {
                    elements.add(new XRayOptionsElement(CheatBreaker.getInstance().getModuleManager().staffModuleXray.getBlocks(), "Blocks", scaleFactor));
                }
            }
            this.moduleElementListMap.put(module, elements);
        }

        this.settingElement = new ArrayList<>();

        for (Setting setting : CheatBreaker.getInstance().getGlobalSettings().settingsList) {
            switch (setting.getType()) {
                case BOOLEAN:
                    this.settingElement.add(new ToggleElement(setting, scaleFactor));
                    break;
                case DOUBLE:
                case INTEGER:
                case FLOAT:
                    if (setting.getType().equals(SettingType.INTEGER) && setting.getSettingName().toLowerCase().contains("color")) {
                        this.settingElement.add(new ColorPickerElement(setting, scaleFactor));
                        break;
                    }

                    if (setting == CheatBreaker.getInstance().getGlobalSettings().getCurrentMenuSetting()) continue;
                    this.settingElement.add(new NewSliderElement(setting, scaleFactor));
                    break;
                case STRING_ARRAY:
                    this.settingElement.add(new ChoiceElement(setting, scaleFactor));
                    break;
                case ARRAYLIST:
                    break;
                case STRING:
                    if (!setting.getSettingName().equalsIgnoreCase("label")) break;
                    this.settingElement.add(new LabelElement(setting, scaleFactor));
            }
        }

        int n5 = 25;
        for (AbstractModulesGuiElement setting : this.settingElement) {
            n5 += setting.getHeight();
        }

        this.applyToAllTextButton = new ModulesGuiButtonElement(CheatBreaker.getInstance().playBold18px, null, "Apply to all text", this.x + n3 - 120, this.y + n5 + 4, 110, 28, -12418828, scaleFactor);
        this.addOneButton = new ModulesGuiButtonElement(CheatBreaker.getInstance().playBold18px, null, "Add One", this.x + n3 - 120, this.y + n5 + 4, 110, 28, -13916106, scaleFactor);
    }

    public boolean shouldHide(Setting setting) {
        if (CheatBreaker.getInstance().getGlobalSettings().customizationLevel.getValue().equals("Simple")) {
            if (setting.getCustomizationLevel() != null) return !setting.getCustomizationLevel().equals(CustomizationLevel.SIMPLE);
        }

        if (CheatBreaker.getInstance().getGlobalSettings().customizationLevel.getValue().equals("Medium")) {
            if (setting.getCustomizationLevel() != null) return !setting.getCustomizationLevel().equals(CustomizationLevel.SIMPLE) && !setting.getCustomizationLevel().equals(CustomizationLevel.MEDIUM);
        }

        if (setting.getParent() == null) {
            return false;
        }

        if (setting.getParent().getValue() == null) {
            return false;
        }

        if (!(setting.getParent().getValue() instanceof Boolean)) {
            return false;
        }

        return !((Boolean) setting.getParent().getValue());
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRoundedRect(this.x, this.y, this.x + this.width, this.y + this.height + 2, 8.0D, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor4 : CBTheme.lightBackgroundColor4);
        this.onScroll(mouseX, mouseY);
        CheatBreaker.getInstance().getModuleManager().setLastSettingScrollPos(this.scrollAmount);
        if (this.module == null && !this.resetColor) {
            this.scrollHeight = 72;
            if (!this.isStaffMods) {
                this.globalSettings.setDimensions(this.x + 4, this.y + 4, this.width - 12, 18);
                this.globalSettings.yOffset = this.scrollAmount;
                this.globalSettings.handleDrawElement(mouseX, mouseY, partialTicks);
                this.scrollHeight += globalSettings.getHeight();
            }

            for (int i = 0; i < this.moduleSetting.size(); ++i) {
                ModuleSettingsElement moduleSettingsElement = this.moduleSetting.get(i);
                moduleSettingsElement.setDimensions(this.x + 4, this.y + (this.isStaffMods ? 4 : 24) + i * 20, this.width - 12, 18);
                moduleSettingsElement.yOffset = this.scrollAmount;
                moduleSettingsElement.handleDrawElement(mouseX, mouseY, partialTicks);
                this.scrollHeight += moduleSettingsElement.getHeight();
            }

        } else if (this.resetColor && !this.isStaffMods) {
            Gui.drawRect(this.x + 32, this.y + 4, this.x + 33, this.y + Math.max(this.height, this.scrollHeight) - 4, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor : CBTheme.lightDullTextColor);
            this.scrollHeight = 25;
            this.backButton.setDimensions(this.x + 2, this.y + 2, 28, 28);
            this.backButton.yOffset = this.scrollAmount;
            this.backButton.handleDrawElement(mouseX, mouseY, partialTicks);
            CheatBreaker.getInstance().ubuntuMedium16px.drawString("CheatBreaker Settings".toUpperCase(), this.x + 38, (float) (this.y + 6), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor4 : CBTheme.lightTextColor4);
            Gui.drawRect(this.x + 38, this.y + 17, this.x + this.width - 6, this.y + 18, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor : CBTheme.lightDullTextColor);
            int n3 = 0;
            for (AbstractModulesGuiElement settingElement : this.settingElement) {
                if (settingElement.setting != null && settingElement.shouldHide(settingElement.setting)) continue;
                settingElement.setDimensions(this.x + 38, this.y + 22 + n3, this.width - 40, settingElement.getHeight());
                settingElement.yOffset = this.scrollAmount;
                settingElement.handleDrawElement(mouseX, mouseY, partialTicks);
                n3 += 2 + settingElement.getHeight();
                this.scrollHeight += 2 + settingElement.getHeight();
            }
            this.applyToAllTextButton.yOffset = this.scrollAmount;
            this.applyToAllTextButton.setDimensions(this.x + this.width - 118, this.y + this.scrollHeight, 100, 20);
            this.applyToAllTextButton.handleDrawElement(mouseX, mouseY, partialTicks);
            this.scrollHeight += 24;
        } else {
            Gui.drawRect(this.x + 32, this.y + 4, this.x + 33, this.y + Math.max(this.height, this.scrollHeight) - 4, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor : CBTheme.lightDullTextColor);
            this.scrollHeight = 37;
            this.backButton.setDimensions(this.x + 2, this.y + 2, 28, 28);
            this.backButton.yOffset = this.scrollAmount;
            this.backButton.handleDrawElement(mouseX, mouseY, partialTicks);
            this.resetSettingsButton.setDimensions(this.x + this.width - 80, this.y + 7, 70, 10);
            this.resetSettingsButton.yOffset = this.scrollAmount;
            this.resetSettingsButton.handleDrawElement(mouseX, mouseY, partialTicks);

            if (this.module.getGuiAnchor() != null) {
                this.resetPositionButton.setDimensions(this.x + this.width - 150, this.y + 7, 70, 10);
                this.resetPositionButton.yOffset = this.scrollAmount;
                this.resetPositionButton.handleDrawElement(mouseX, mouseY, partialTicks);
            }

            CheatBreaker.getInstance().getModuleManager().setCurrentModule(this.module.getName());
            CheatBreaker.getInstance().ubuntuMedium16px.drawString((this.module.getName() + " Settings").toUpperCase(), this.x + 38, (float)(this.y + 6), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor4 : CBTheme.lightTextColor4);
            Gui.drawRect(this.x + 38, this.y + 17, this.x + this.width - 12, this.y + 18, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor : CBTheme.lightDullTextColor);
            if (module.getDescription() != null) {
                CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.module.getDescription(), this.x + 38, (float)(this.y + 19), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor4 : CBTheme.lightTextColor4);
                if (module.getCreators() != null) {
                    CheatBreaker.getInstance().ubuntuMedium16px.drawString("Original Creator" + (this.module.getCreators().size() != 1 ? "s: " : ": ") + String.join(", ", this.module.getCreators()), this.x + 38, (float)(this.y + 28), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor4 : CBTheme.lightTextColor4);
                    Gui.drawRect(this.x + 38, this.y + 39, this.x + this.width - 12, this.y + 40, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor : CBTheme.lightDullTextColor);
                } else {
                    Gui.drawRect(this.x + 38, this.y + 30, this.x + this.width - 12, this.y + 31, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor : CBTheme.lightDullTextColor);
                }
            }

            if (this.module == CheatBreaker.getInstance().getModuleManager().miniMapMod) {
                try {
                    String keybind = Keyboard.getKeyName(CheatBreaker.getInstance().getModuleManager().miniMapMod.getVoxelMap().getMapOptions().keyBindMenu.getKeyCode());
                    CheatBreaker.getInstance().ubuntuMedium16px.drawString(("PRESS '" + keybind + "' INGAME FOR ZAN'S MINIMAP OPTIONS.").toUpperCase(), this.x + 38, (float)(this.y + 44), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
                } catch (Exception exception) {
                    CheatBreaker.getInstance().ubuntuMedium16px.drawString("PRESS 'M' INGAME FOR ZAN'S MINIMAP OPTIONS.", this.x + 38, (float)(this.y + 44), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
                }
                this.onGuiDraw(mouseX, mouseY);
                return;
            }

            if (this.module.getSettingsList().isEmpty()) {
                CheatBreaker.getInstance().ubuntuMedium16px.drawString((this.module.getName().toUpperCase() + " DOES NOT HAVE ANY OPTIONS.").toUpperCase(), this.x + 38, (float)(this.y + (this.module.getDescription() == null ? 22 : this.module.getCreators() == null ? 35 : 44)), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
            }

            int n4 = 0;
            for (AbstractModulesGuiElement setting : this.moduleElementListMap.get(this.module)) {
                if (setting.setting != null && setting.shouldHide(setting.setting)) continue;
                setting.setDimensions(this.x + 38, this.y + (this.module.getDescription() == null ? 22 : this.module.getCreators() == null ? 35 : 44) + n4, this.width - 40, setting.getHeight());
                setting.yOffset = this.scrollAmount;
                setting.handleDrawElement(mouseX, mouseY, partialTicks);
                n4 += 2 + setting.getHeight();
                this.scrollHeight += 2 + setting.getHeight();
            }

            if (module.getDescription() != null) {
                if (module.getCreators() != null) this.scrollHeight += 13;
            } else {
                this.scrollHeight -= 13;
            }

//            if (this.module == CheatBreaker.getInstance().getModuleManager().autoHotKeyText) {
//                this.addOneButton.yOffset = this.scrollAmount;
//                this.addOneButton.setDimensions(this.x + this.width - 118, this.y + this.scrollHeight, 100, 20);
//                this.addOneButton.handleDrawElement(mouseX, mouseY, partialTicks);
//                this.scrollHeight += 24;
//            }

        }
        this.onGuiDraw(mouseX, mouseY);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        if (this.module == null && !this.resetColor) {
            if (this.globalSettings.isMouseInside(mouseX, mouseY) && !this.isStaffMods) {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                this.resetColor = true;
                this.scrollAmount = 0;
                this.startPosition = 0.0;
                this.yOffset = 0;
            } else {
                for (ModuleSettingsElement moduleSetting : this.moduleSetting) {
                    if (moduleSetting.isMouseInside(mouseX, mouseY)) {
                        if (!this.hasSettings(moduleSetting.module)) {
                            continue;
                        }
                        moduleSetting.handleMouseClick(mouseX, mouseY, button);
                    }
                }
            }
        } else if (!this.backButton.isMouseInside(mouseX, mouseY)) {
            if (this.resetSettingsButton.isMouseInside(mouseX, mouseY)) {
                ModuleManager moduleManager = CheatBreaker.getInstance().getModuleManager();
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));

                for (int i = 0; i < this.module.getSettingsList().size(); ++i) {
                    try {
                        Setting modSetting = this.module.getSettingsList().get(i);

                        // Reset keybind text and value async
                        for (Setting setting : moduleManager.keybinds.keySet()) {
                            if (setting.getSettingName().endsWith("Keybind")) {
                                for (KeybindElement element : moduleManager.keybinds.values()) {
                                    setting.setHasMouseBind(false);
                                    setting.setValue(0);
                                    element.getButton().optionString = "None";
                                }
                            }
                        }

                        if (this.module.getSettingsList().get(i).isHasKeycode() || this.module.getSettingsList().get(i).isHasMouseBind()) {
                            modSetting.setHasMouseBind(false);
                            modSetting.setKeyCode(0);
                        }
                        
                        modSetting.updateSettingValue(this.module.getDefaultSettingsValues().get(i), false);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

                moduleManager.keystrokesMod.updateKeyElements();
                HudLayoutEditorGui hudEditor = new HudLayoutEditorGui();
                Minecraft.getMinecraft().displayGuiScreen(hudEditor);
                (hudEditor).currentScrollableElement = (hudEditor).modulesElement;
                ModulePreviewElement.instance.sendBackToModuleList(moduleManager.getModByName(moduleManager.currentModule));
            }

            try {
                if (this.resetPositionButton.isMouseInside(mouseX, mouseY)) {
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                    this.module.setTranslations(this.module.defaultXTranslation, this.module.defaultYTranslation);
                    this.module.setAnchor(this.module.getDefaultGuiAnchor());
                }
            } catch (NullPointerException ignored) { }

//            if (this.addOneButton.isMouseInside(mouseX, mouseY) && this.module == CheatBreaker.getInstance().getModuleManager().autoHotKeyText) {
//                CheatBreaker.getInstance().getModuleManager().autoHotKeyText.addOne();
//                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
//            }

            if (this.module != null && this.moduleElementListMap.containsKey(this.module)) {
                for (AbstractModulesGuiElement abstractModulesGuiElement : this.moduleElementListMap.get(this.module)) {
                    if (!abstractModulesGuiElement.isMouseInside(mouseX, mouseY)) {
                        continue;
                    }
                    abstractModulesGuiElement.handleMouseClick(mouseX, mouseY, button);
                }
            } else if (this.resetColor) {
                if (this.applyToAllTextButton.isMouseInside(mouseX, mouseY)) {
                    for (AbstractModule module : CheatBreaker.getInstance().getModuleManager().playerMods) {
                        for (Setting setting : module.getSettingsList()) {
                            if (setting.getType() == SettingType.INTEGER && setting.getSettingName().toLowerCase().contains("color")) {
                                if (setting.getSettingName().toLowerCase().contains("background")) {
                                    continue;
                                }
                                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                                setting.setValue(CheatBreaker.getInstance().getGlobalSettings().defaultColor.getColorValue());
                            }
                        }
                    }
                } else {
                    for (AbstractModulesGuiElement settingElement : this.settingElement) {
                        if (!settingElement.isMouseInside(mouseX, mouseY)) {
                            continue;
                        }
                        settingElement.handleMouseClick(mouseX, mouseY, button);
                    }
                }
            }
        } else {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.module = null;
            this.resetColor = false;
            if (this.scrollableElement != null) {
                HudLayoutEditorGui.instance.currentScrollableElement = this.scrollableElement;
            }
        }
        double d = this.height - 10;
        double d2 = this.scrollHeight;
        double d3 = d / d2 * (double)100;
        double d4 = d / (double)100 * d3;
        double d5 = (double)this.scrollAmount / 100.0 * d3;
        boolean bl4 = (float)mouseX > (float)(this.x + this.width - 9) * this.scale && (float)mouseX < (float)(this.x + this.width - 3) * this.scale && (double)mouseY > ((double)(this.y + 11) - d5) * (double)this.scale && (double)mouseY < ((double)(this.y + 8) + d4 - d5) * (double)this.scale;
        boolean bl3 = (float)mouseX > (float)(this.x + this.width - 9) * this.scale && (float)mouseX < (float)(this.x + this.width - 3) * this.scale && (float)mouseY > (float)(this.y + 11) * this.scale && (double)mouseY < ((double)(this.y + 6) + d - (double)3) * (double)this.scale;
        if (button == 0 && bl3 || bl4) {
            this.hovering = true;
        }
    }

    @Override
    public boolean hasSettings(AbstractModule module) {
        return !module.getSettingsList().isEmpty() || module.getName().contains("Zans");
    }

    @Override
    public void handleModuleMouseClick(AbstractModule module) {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
        this.scrollAmount = 0;
        this.startPosition = 0.0;
        this.yOffset = 0;
        this.module = module;
        this.scrollableElement = null;
    }
}
