package com.cheatbreaker.client.module.data;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * Defines a Setting for CheatBreaker Modules and CheatBreaker Settings.
 */
@Getter
public class Setting {
    private final String settingName;
    private String settingDescription = "";
    private String unit = "";
    private String centerLabel = "";
    private String leftIcon = "";
    private String rightIcon = "";
    private String[] acceptedStringValues;

    private int keyCode; // done since keybind element is so good
    @Setter private boolean hasKeycode;

    @Setter private boolean hasMouseBind;

    private boolean showValue = true;
    public boolean rainbow;
    public boolean speed;

    public int[] color;

    private Object value;
    private Object minimumValue;
    private Object maximumValue;
    private Object acceptedValue;
    private Object customizationLevel;
    private Object[] acceptedValues;

    private Consumer<Object> changeConsumer;

    private Setting parent;

    private BooleanSupplier condition;

    private AbstractModule container;

    public Setting(String settingName) {
        this.settingName = settingName;
    }

    public Setting(AbstractModule container, String settingName) {
        this.container = container;
        container.getSettingsList().add(this);
        this.settingName = settingName;
    }

    public Setting(AbstractModule container, String settingName, String description) {
        this.container = container;
        container.getSettingsList().add(this);
        this.settingName = settingName;
        this.settingDescription = description;
    }

    public Setting(List<Setting> list, String settingName) {
        list.add(this);
        this.settingName = settingName;
    }

    public Setting(List<Setting> list, String settingName, String description) {
        list.add(this);
        this.settingName = settingName;
        this.settingDescription = description;
    }

    /**
     * Returns the color value for text color, background color, etc.
     */
    public int getColorValue() {
        if (this.rainbow) {
            Integer var1 = (Integer) this.value;
            int var2 = var1 >> 24 & 0xFF;
            float var3 = (float) System.nanoTime() / 1.0E10f % 1.0f;
            if (this.speed)
                return var2 << 24 | Color.HSBtoRGB((float) (System.currentTimeMillis() % 1000L) / 1000.0f, 1.0f, 1.0f) & 0xFFFFFF;
            return var2 << 24 | Color.HSBtoRGB(var3, 1.0f, 1.0f) & 0xFFFFFF;
        }
        return (Integer) this.value;
    }

    public Color getColorFromColorValue(int colorValue) {
        float A = (float)(colorValue >> 24 & 255) / 255.0F;
        float R = (float)(colorValue >> 16 & 255) / 255.0F;
        float G = (float)(colorValue >> 8 & 255) / 255.0F;
        float B = (float)(colorValue & 255) / 255.0F;

        return new Color(R, G, B, A);
    }

    /**
     * Casts and returns the value as a boolean.
     */
    public boolean getBooleanValue() {
        return (Boolean) this.value;
    }

    /**
     * Casts and returns the value as a string.
     */
    public String getStringValue() {
        return (String) this.value;
    }

    /**
     * Casts and returns the value as a float.
     */
    public float getFloatValue() {
        return (Float) this.value;
    }

    /**
     * Casts and returns the value as an integer.
     */
    public int getIntegerValue() {
        return (Integer) this.value;
    }

    /**
     * Casts and returns the value as an ArrayList.
     */
    public ArrayList getArrayListValue() {
        return (ArrayList) this.value;
    }

    /**
     * Sets a singular accepted value for a setting.
     */
    public Setting acceptedValue(Object value) {
        this.acceptedValue = value;
        return this;
    }

    /**
     * Gets the parent setting value.
     */
    public boolean getParentValue() {
        return this.parent != null && (Boolean) this.parent.getValue();
    }

    /**
     * Sets the value of a setting.
     */
    public Setting setValue(Object value) {
        return this.updateSettingValue(value, true);
    }

    public Setting setMouseBind(boolean hasMouseBind) {
        this.hasMouseBind = hasMouseBind;
        return this;
    }

    /**
     * Updates a setting value.
     */
    public Setting updateSettingValue(Object value, boolean usingDefaultProfile) {
        if (CheatBreaker.getInstance().getConfigManager().activeProfile != null && CheatBreaker.getInstance().getConfigManager().activeProfile.getName().equals("default")) {
            if (usingDefaultProfile) {
                CheatBreaker.getInstance().getConfigManager().createNewProfile();
            }
        } else if (this.container != null) {
            this.container.getDefaultSettingsValues().add(value);
        }
        this.value = value;
        if (this.changeConsumer != null) {
            this.changeConsumer.accept(value);
        }
        return this;
    }

    /**
     * Sets the accepted String values of a setting.
     */
    public Setting acceptedStringValues(String... values) {
        this.acceptedStringValues = values;
        return this;
    }

    public Setting setKeyCode(int keycode) {
        this.keyCode = keycode;
        this.hasKeycode = true;
        return this;
    }

    /**
     * Sets the accepted values of a setting.
     */
    public Setting acceptedValues(Object... values) {
        this.acceptedValues = values;
        return this;
    }

    /**
     * Gets fired when a setting is changed.
     */
    public Setting onChange(Consumer<Object> var1) {
        this.changeConsumer = var1;
        return this;
    }

    /**
     * Sets min and max slider values
     */
    public Setting setMinMax(Object minValue, Object maxValue) {
        this.minimumValue = minValue;
        this.maximumValue = maxValue;
        return this;
    }

    /**
     * Sets the customization level of the setting.
     */
    public Setting setCustomizationLevel(Object level) {
        this.customizationLevel = level;
        return this;
    }

    /**
     * Sets the setting condition.
     */
    public Setting setCondition(BooleanSupplier condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Sets the parent setting.
     */
    public Setting setParent(Setting parent) {
        if (parent.getType() != SettingType.BOOLEAN) {
            throw new IllegalStateException("Parent can only be boolean.");
        }
        this.parent = parent;
        return this;
    }

    /**
     * Gets the setting type.
     */
    public SettingType getType() {
        if (this.value.getClass().isAssignableFrom(Boolean.class)) {
            return SettingType.BOOLEAN;
        }
        if (this.value.getClass().isAssignableFrom(ArrayList.class) && this.acceptedValues != null && this.acceptedValues.length != 0) {
            return SettingType.ARRAYLIST;
        }
        if (this.value.getClass().isAssignableFrom(String.class)) {
            return this.acceptedStringValues != null && this.acceptedStringValues.length != 0 ? SettingType.STRING_ARRAY : SettingType.STRING;
        }
        if (this.value.getClass().isAssignableFrom(Float.class)) {
            return SettingType.FLOAT;
        }
        if (this.value.getClass().isAssignableFrom(Double.class)) {
            return SettingType.DOUBLE;
        }
        if (this.value.getClass().isAssignableFrom(String[].class)) {
            return SettingType.STRING_ARRAY;
        }
        return this.value.getClass().isAssignableFrom(Integer.class) ? SettingType.INTEGER : null;
    }

    /**
     * Sets unit string.
     */
    public Setting setUnit(String value) {
        this.unit = value;
        return this;
    }

    /**
     * Sets showValue boolean.
     */
    public Setting setShowValue(boolean value) {
        this.showValue = value;
        return this;
    }

    /**
     * Sets the center label of the setting.
     */
    public Setting setCenterLabel(String label) {
        this.centerLabel = label;
        return this;
    }

    /**
     * Sets the setting's icons.
     */
    public Setting setIcons(String leftIcon, String rightIcon) {
        this.leftIcon = leftIcon;
        this.rightIcon = rightIcon;
        return this;
    }
}
