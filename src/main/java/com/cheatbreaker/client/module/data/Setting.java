package com.cheatbreaker.client.module.data;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@Getter @ToString
public class Setting {
    private final String settingName;
    private String settingDescription = "";
    private Object value;
    private String defaultStringValue;

    /* AutoText required things vars */
    private int keyCode;
    @Setter private boolean hasKeycode;

    @Setter private boolean hasMouseBind;

    private Object minimumValue;
    private Object maximumValue;
    private Object acceptedValue;
    private Object customizationLevel;
    private Setting parent;
    private BooleanSupplier condition;
    private String[] acceptedStringValues;
    private Object[] acceptedValues;
    private Consumer<Object> changeConsumer;
    private AbstractModule container;
    public boolean rainbow;
    public boolean speed;
    public int[] color;

    private String unit = "";
    private String centerLabel = "";
    private String leftIcon = "";
    private String rightIcon = "";
    private boolean showValue = true;

    public Setting(String settingName) {
        this.settingName = settingName;
    }

    public Setting(AbstractModule container, String settingName) {
        this.container = container;
        container.getSettingsList().add(this);
        this.settingName = settingName;
    }

    public Setting(AbstractModule container, String settingName, String description) {
        this(container, settingName);
        this.settingDescription = description;
    }

    public Setting(List<Setting> list, String settingName) {
        list.add(this);
        this.settingName = settingName;
    }

    public Setting(List<Setting> list, String settingName, String description) {
        this(list, settingName);
        this.settingDescription = description;
    }

    public int getColorValue() {
        if (this.rainbow) {
            Integer var1 = (Integer)this.value;
            int var2 = var1 >> 24 & 0xFF;
            float var3 = (float)System.nanoTime() / 1.0E10f % 1.0f;
            if (this.speed) return var2 << 24 | Color.HSBtoRGB((float)(System.currentTimeMillis() % 1000L) / 1000.0f, 1.0f, 1.0f) & 0xFFFFFF;
            return var2 << 24 | Color.HSBtoRGB(var3, 1.0f, 1.0f) & 0xFFFFFF;
        }
        return (Integer)this.value;
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

    public Setting acceptedValue(Object value) {
        this.acceptedValue = value;
        return this;
    }

    public boolean getParentValue() {
        return this.parent != null && (Boolean) this.parent.getValue();
    }

    public Setting setValue(Object value) {
        return this.updateSettingValue(value, true);
    }

    public Setting setDefaultStringValue(String defaultStringValue) {
        this.defaultStringValue = defaultStringValue;
        return this;
    }

    public Setting setKeyCode(int keycode) {
        this.keyCode = keycode;
        this.hasKeycode = true;
        return this;
    }

    public Setting setMouseBind(boolean hasMouseBind) {
        this.hasMouseBind = hasMouseBind;
        return this;
    }

    public Setting updateSettingValue(Object value, boolean usingDefaultProfile) {
        if (CheatBreaker.getInstance().activeProfile != null && CheatBreaker.getInstance().activeProfile.getName().equals("default")) {
            if (usingDefaultProfile) {
                CheatBreaker.getInstance().createNewProfile();
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

    public Setting acceptedStringValues(String ... values) {
        this.acceptedStringValues = values;
        return this;
    }

    public Setting acceptedValues(Object ... values) {
        this.acceptedValues = values;
        return this;
    }

    public Setting onChange(Consumer<Object> var1) {
        this.changeConsumer = var1;
        return this;
    }

    public Setting setMinMax(Object minValue, Object maxValue) {
        this.minimumValue = minValue;
        this.maximumValue = maxValue;
        return this;
    }

    public Setting setCustomizationLevel(Object level) {
        this.customizationLevel = level;
        return this;
    }

    public Setting setCondition(BooleanSupplier condition) {
        this.condition = condition;
        return this;
    }

    public Setting setParent(Setting parent) {
        if (parent.getType() != SettingType.BOOLEAN) {
            throw new IllegalStateException("Parent can only be boolean.");
        }
        this.parent = parent;
        return this;
    }

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

    public Setting setUnit(String value) {
        this.unit = value;
        return this;
    }

    public Setting setShowValue(boolean value) {
        this.showValue = value;
        return this;
    }

    public Setting setCenterLabel(String label) {
        this.centerLabel = label;
        return this;
    }

    public Setting setIcons(String leftIcon, String rightIcon) {
        this.leftIcon = leftIcon;
        this.rightIcon = rightIcon;
        return this;
    }
}
