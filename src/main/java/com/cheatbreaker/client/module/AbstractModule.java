package com.cheatbreaker.client.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.EventBus;
import com.cheatbreaker.client.event.impl.ClickEvent;
import com.cheatbreaker.client.event.impl.KeyboardEvent;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.PreviewType;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.*;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.*;
import java.util.function.Consumer;

@Getter @Setter
public abstract class AbstractModule {
    protected Minecraft mc = Minecraft.getMinecraft();
    private boolean staffModule = false;
    private boolean staffModuleEnabled = false;
    private final String name;
    private String description;
    private List<String> creators;
    private boolean enabled = false;
    public boolean defaultState = false;
    public GuiAnchor guiAnchor;
    public GuiAnchor defaultGuiAnchor;
    private float xTranslation = 0.0f;
    public float defaultXTranslation = 0.0f;
    private float yTranslation = 0.0f;
    public float defaultYTranslation = 0.0f;
    private boolean renderHud = true;
    public boolean defaultRenderHud = true;
    private boolean wasRenderHud = false;
    public boolean defaultWasRenderHud = false;
    public boolean hiddenFromHud = false;
    public float width = 0.0f;
    public float height = 0.0f;
    public boolean notRenderHUD = true;
    private final List<Setting> settingsList;
    private final List<Object> defaultSettingsValues;
    public Setting scale;
    public Setting guiScale;
    public Setting toggleKeybind;
    public Setting hideFromHUDKeybind;
    public Setting tempHideFromHUDKeybind;
    public String defaultGuiScale;
    private final Map<Class<? extends EventBus.Event>, Consumer> eventMap = new HashMap<>();
    private PreviewType previewType;
    private ResourceLocation previewIcon;
    private float previewIconWidth;
    private float previewIconHeight;
    private float previewLabelSize;
    private String previewLabel;
    protected String[] excludeOptions = new String[] {"OFF", "Above", "Below", "At", "Not At"};

    public AbstractModule(String name) {
        this.name = name;
        this.settingsList = new ArrayList<>();
        this.defaultSettingsValues = new ArrayList<>();
        this.defaultGuiScale = "Global";
        this.scale = new Setting(this, "Scale", "Change the scale of the mod.").setValue(1.0f).setMinMax(0.5f, 1.5f).setUnit("x").setCenterLabel("1.0x");
        this.guiScale = new Setting(this, "GUI Scale", "Change the scale of the mod.").setValue(defaultGuiScale).acceptedStringValues("Global", "Default", "Small", "Normal", "Large", "Auto").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.toggleKeybind = new Setting(this, "Toggle Mod Keybind").setValue(0).setMouseBind(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.hideFromHUDKeybind = new Setting(this, "Toggle HUD Keybind").setValue(0).setMouseBind(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.tempHideFromHUDKeybind = new Setting(this, "Temp Toggle HUD Keybind").setValue(0).setMouseBind(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.registerStaticEvent(KeyboardEvent.class, this::onKeyPress);
        this.registerStaticEvent(ClickEvent.class, this::onMousePress);
    }

    public AbstractModule(String name, String defaultGuiScale) {
        this.name = name;
        this.settingsList = new ArrayList<>();
        this.defaultSettingsValues = new ArrayList<>();
        this.defaultGuiScale = defaultGuiScale;
        this.scale = new Setting(this, "Scale", "Change the scale of the mod.").setValue(1.0f).setMinMax(0.5f, 1.5f).setUnit("x").setCenterLabel("1.0x");
        this.guiScale = new Setting(this, "GUI Scale", "Change the scale of the mod.").setValue(defaultGuiScale).acceptedStringValues("Global", "Default", "Small", "Normal", "Large", "Auto").setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.toggleKeybind = new Setting(this, "Toggle Mod Keybind").setValue(0).setMouseBind(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.hideFromHUDKeybind = new Setting(this, "Toggle HUD Keybind").setValue(0).setMouseBind(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.tempHideFromHUDKeybind = new Setting(this, "Temp Toggle HUD Keybind").setValue(0).setMouseBind(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.registerStaticEvent(KeyboardEvent.class, this::onKeyPress);
        this.registerStaticEvent(ClickEvent.class, this::onMousePress);
    }

    protected <T extends EventBus.Event> void addEvent(Class<T> eventClass, Consumer<T> consumer) {
        this.eventMap.put(eventClass, consumer);
    }

    protected <T extends EventBus.Event> void registerStaticEvent(Class<T> eventClass, Consumer<T> consumer) {
        CheatBreaker.getInstance().getEventBus().addEvent(eventClass, consumer);
    }

    protected void addAllEvents() {
        for (Map.Entry<Class<? extends EventBus.Event>, Consumer> entry : this.eventMap.entrySet()) {
            CheatBreaker.getInstance().getEventBus().addEvent((Class<? extends EventBus.Event>)entry.getKey(), entry.getValue());
        }
    }

    protected void removeAllEvents() {
        for (Map.Entry<Class<? extends EventBus.Event>, Consumer> entry : this.eventMap.entrySet()) {
            CheatBreaker.getInstance().getEventBus().removeEvent((Class<? extends EventBus.Event>)entry.getKey(), entry.getValue());
        }
    }

    public void setState(boolean state) {
        if (state != this.defaultState) {
            CheatBreaker.getInstance().createNewProfile();
        }
        if (state) {
            if (!this.enabled) {
                this.enabled = true;
                this.addAllEvents();
            }
        } else if (this.enabled) {
            this.enabled = false;
            this.removeAllEvents();
        }
    }

    public void setDefaultState(boolean state) {
        if (state) {
            if (!this.enabled) {
                this.enabled = true;
                this.addAllEvents();
            }
        } else if (this.enabled) {
            this.enabled = false;
            this.removeAllEvents();
        }
        this.defaultState = this.enabled;
    }

    public void setTranslations(float x, float y) {
        this.xTranslation = x;
        this.yTranslation = y;
    }

    public void setDefaultTranslations(float x, float y) {
        this.xTranslation = x;
        this.yTranslation = y;
        this.defaultXTranslation = x;
        this.defaultYTranslation = y;
    }

    public void setDimensions(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setStaffModuleEnabled(boolean bl) {
        this.staffModuleEnabled = bl;
        if (!bl && this.isEnabled()) {
            this.setState(false);
        }
    }

    // Master toggle key bind logic (keyboard)
    private void onKeyPress(KeyboardEvent event) {
        if (event.getPressed() == 0) return;
        if (event.getPressed() == this.toggleKeybind.getIntegerValue() && !this.toggleKeybind.isHasMouseBind()) {
            this.setState(!this.enabled);
        }
        if (event.getPressed() == this.hideFromHUDKeybind.getIntegerValue() && !this.hideFromHUDKeybind.isHasMouseBind() && !this.notRenderHUD) {
            this.setRenderHud(!this.renderHud);
        }
        if (event.getPressed() == this.tempHideFromHUDKeybind.getIntegerValue() && !this.tempHideFromHUDKeybind.isHasMouseBind()) {
            this.setHiddenFromHud(!this.hiddenFromHud);
        }
    }

    // Master toggle key bind logic (mice)
    private void onMousePress(ClickEvent event) {
        if (event.getMouseButton() == 0) return; // DO NOT REMOVE IT CAUSES SO MANY PROBLEMS
        if (event.getMouseButton() == this.toggleKeybind.getIntegerValue() && this.toggleKeybind.isHasMouseBind()) {
            this.setState(!this.enabled);
        }
        if (event.getMouseButton() == this.hideFromHUDKeybind.getIntegerValue() && this.hideFromHUDKeybind.isHasMouseBind() && !this.notRenderHUD) {
            this.setRenderHud(!this.renderHud);
        }
        if (event.getMouseButton() == this.tempHideFromHUDKeybind.getIntegerValue() && this.tempHideFromHUDKeybind.isHasMouseBind()) {
            this.setHiddenFromHud(!this.hiddenFromHud);
        }
        Setting nameTagsKeybind = CheatBreaker.getInstance().getModuleManager().nametagMod.hideNamePlatesKeybind;
        if (event.getMouseButton() == nameTagsKeybind.getIntegerValue() && nameTagsKeybind.isHasMouseBind()) {
            Minecraft.getMinecraft().hideNametags = !Minecraft.getMinecraft().hideNametags;
        }
    }

    public void setCreators(String ... creators) {
        this.creators = Arrays.asList(creators);
    }

    public void scaleAndTranslate(ScaledResolution scaledResolution) {
        this.scaleAndTranslate(scaledResolution, this.width, this.height);
    }
    
    public float masterScale() {
        float var15 = 1.0f;
        if (this.guiScale.getValue().equals("Global")) {
            switch (CheatBreaker.getInstance().getGlobalSettings().modScale.getStringValue()) {
                case "Small":
                    var15 *= 0.5F / CheatBreaker.getScaleFactor();
                    break;
                case "Normal":
                    var15 /= CheatBreaker.getScaleFactor();
                    break;
                case "Large":
                    var15 *= 1.5F / CheatBreaker.getScaleFactor();
                    break;
                case "Auto":
                    var15 *= 2.0F / CheatBreaker.getScaleFactor();
            }
        } else {
            switch (this.guiScale.getStringValue()) {
                case "Small":
                    var15 *= 0.5F / CheatBreaker.getScaleFactor();
                    break;
                case "Normal":
                    var15 /= CheatBreaker.getScaleFactor();
                    break;
                case "Large":
                    var15 *= 1.5F / CheatBreaker.getScaleFactor();
                    break;
                case "Auto":
                    var15 *= 2.0F / CheatBreaker.getScaleFactor();
            }
        }
        return this.scale.getFloatValue() * CheatBreaker.getInstance().getGlobalSettings().modScaleMultiplier.getFloatValue() * var15;
    }

    public void scaleAndTranslate(ScaledResolution scaledResolution, float width, float height) {
        float f3 = 0.0f;
        float f4 = 0.0f;
        float scale = this.masterScale();
        GL11.glScalef(scale, scale, scale);
        width *= scale;
        height *= scale;
        switch (this.guiAnchor) {
            case LEFT_TOP:
                f3 = 2.0f;
                f4 = 2.0f;
                break;
            case LEFT_MIDDLE:
                f3 = 2.0f;
                f4 = (float)(scaledResolution.getScaledHeight() / 2) - height / 2.0f;
                break;
            case LEFT_BOTTOM:
                f4 = (float)scaledResolution.getScaledHeight() - height - 2.0f;
                f3 = 2.0f;
                break;
            case MIDDLE_TOP:
                f3 = (float)(scaledResolution.getScaledWidth() / 2) - width / 2.0f;
                f4 = 2.0f;
                break;
            case MIDDLE_MIDDLE:
                f3 = (float)(scaledResolution.getScaledWidth() / 2) - width / 2.0f;
                f4 = (float)(scaledResolution.getScaledHeight() / 2) - height / 2.0f;
                break;
            case MIDDLE_BOTTOM_LEFT:
                f3 = (float)(scaledResolution.getScaledWidth() / 2) - width;
                f4 = (float)scaledResolution.getScaledHeight() - height - 2.0f;
                break;
            case MIDDLE_BOTTOM_RIGHT:
                f3 = scaledResolution.getScaledWidth() / 2;
                f4 = (float)scaledResolution.getScaledHeight() - height - 2.0f;
                break;
            case RIGHT_TOP:
                f3 = (float)scaledResolution.getScaledWidth() - width - 2.0f;
                f4 = 2.0f;
                break;
            case RIGHT_MIDDLE:
                f3 = (float)scaledResolution.getScaledWidth() - width;
                f4 = (float)(scaledResolution.getScaledHeight() / 2) - height / 2.0f;
                break;
            case RIGHT_BOTTOM:
                f3 = (float)scaledResolution.getScaledWidth() - width;
                f4 = (float)scaledResolution.getScaledHeight() - height;

        }
        GL11.glTranslatef(f3 / scale, f4 / scale, 0.0f);
        GL11.glTranslatef(this.xTranslation / scale, this.yTranslation / scale, 0.0f);
    }

    public float[] getScaledPoints(ScaledResolution scaledResolution, boolean bl) {
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = this.width * (Float) this.masterScale();
        float f4 = this.height * (Float) this.masterScale();
        switch (this.guiAnchor) {
            case LEFT_TOP:
                f = 2.0f;
                f2 = 2.0f;
                break;
            case LEFT_MIDDLE:
                f = 2.0f;
                f2 = (float)(scaledResolution.getScaledHeight() / 2) - f4 / 2.0f;
                break;
            case LEFT_BOTTOM:
                f2 = (float)scaledResolution.getScaledHeight() - f4 - 2.0f;
                f = 2.0f;
                break;
            case MIDDLE_TOP:
                f = (float)(scaledResolution.getScaledWidth() / 2) - f3 / 2.0f;
                f2 = 2.0f;
                break;
            case MIDDLE_MIDDLE:
                f = (float)(scaledResolution.getScaledWidth() / 2) - f3 / 2.0f;
                f2 = (float)(scaledResolution.getScaledHeight() / 2) - f4 / 2.0f;
                break;
            case MIDDLE_BOTTOM_LEFT:
                f = (float)(scaledResolution.getScaledWidth() / 2) - f3;
                f2 = (float)scaledResolution.getScaledHeight() - f4 - 2.0f;
                break;
            case MIDDLE_BOTTOM_RIGHT:
                f = scaledResolution.getScaledWidth() / 2;
                f2 = (float)scaledResolution.getScaledHeight() - f4 - 2.0f;
                break;
            case RIGHT_TOP:
                f = (float)scaledResolution.getScaledWidth() - f3 - 2.0f;
                f2 = 2.0f;
                break;
            case RIGHT_MIDDLE:
                f = (float)scaledResolution.getScaledWidth() - f3;
                f2 = (float)(scaledResolution.getScaledHeight() / 2) - f4 / 2.0f;
                break;
            case RIGHT_BOTTOM:
                f = (float)scaledResolution.getScaledWidth() - f3;
                f2 = (float)scaledResolution.getScaledHeight() - f4;
        }
        return new float[]{(f + (bl ? this.xTranslation : 0.0f)) / (Float) this.masterScale(), (f2 + (bl ? this.yTranslation : 0.0f)) / (Float) this.masterScale()};
    }

    public void setAnchor(GuiAnchor anchor) {
        if (anchor != this.defaultGuiAnchor) {
            CheatBreaker.getInstance().createNewProfile();
        }
        this.guiAnchor = anchor;
    }

    public void setDefaultAnchor(GuiAnchor anchor) {
        this.guiAnchor = anchor;
        this.defaultGuiAnchor = anchor;
    }

    public Position getPosition() {
        return AnchorHelper.getHorizontalPositionEnum(this.guiAnchor);
    }

    protected void setPreviewIcon(ResourceLocation resourceLocation, int width, int height) {
        this.previewType = PreviewType.ICON;
        this.previewIcon = resourceLocation;
        this.previewIconWidth = width;
        this.previewIconHeight = height;
    }

    protected void setPreviewLabel(String label, float size) {
        this.previewType = PreviewType.LABEL;
        this.previewLabel = label;
        this.previewLabelSize = size;
    }

    public void setRenderHud(boolean bl) {
        if (bl != this.defaultRenderHud) {
            CheatBreaker.getInstance().createNewProfile();
        }

        this.renderHud = bl;
    }

    public void setDefaultRenderHud(boolean bl) {
        this.renderHud = bl;
        this.defaultRenderHud = bl;
    }

    protected boolean excludeArrayOptions(Setting setting, int realValue, float sliderValue) {
        switch (setting.getStringValue()) {
            case "Above": return realValue > sliderValue;
            case "Below": return realValue < sliderValue;
            case "At": return realValue == sliderValue;
            case "Not At": return realValue != sliderValue;
            default: return false;
        }
    }
}
