package com.cheatbreaker.client.module.impl.normal.hud.simple.impl;

import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import com.cheatbreaker.client.module.impl.normal.hud.simple.data.SimpleHudModSize;
import com.cheatbreaker.client.ui.module.GuiAnchor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Locale;

/**
 * Displays your current food saturation level.
 * @see AbstractSimpleHudModule
 */
public class ModuleSaturation extends AbstractSimpleHudModule {
    public Setting amountColors;
    private Setting highestAmountColor;
    private Setting highAmountColor;
    private Setting mediumAmountColor;
    private Setting lowAmountColor;
    private Setting emptyAmountColor;
    private Setting hideinCM;
    private Setting trailingZeros;
    private Setting decimals;

    public ModuleSaturation() {
        super("Saturation");
        this.setDescription("Displays your current food saturation level.");
        this.setCreators("Eum3");
        this.setDefaultAnchor(GuiAnchor.MIDDLE_BOTTOM_RIGHT);
        this.setDefaultTranslations(-15.0F, -35.0F);
    }

    public String level() {
        double saturationLevel = mc.thePlayer.getFoodStats().getSaturationLevel();
        int dec = (Integer) decimals.getValue();
        String number = String.join("", Collections.nCopies(dec, "#"));
        return !(Boolean) trailingZeros.getValue() && dec != 0 ?
                new DecimalFormat("#." + number, new DecimalFormatSymbols(Locale.ENGLISH)).format(saturationLevel) :
                String.format("%." + dec + "f", saturationLevel);
    }

    @Override
    public String getValueString() {
        boolean creativeModeCheck = mc.playerController.isNotCreative() || !(Boolean) hideinCM.getValue();
        if (this.excludeArrayOptions(this.hideValue, (int) mc.thePlayer.getFoodStats().getSaturationLevel(), this.hiddenValue.getIntegerValue())) {
            return null;
        }
        if (creativeModeCheck) {
            return level();
        }
        return null;
    }

    public String getPreviewString() {
        return level();
    }

    @Override
    public String getLabelString() {
        return "Saturation";
    }

    public String getDefaultFormatString() {
        return "%VALUE%";
    }

    public boolean isBracketsEnabledByDefault() {
        return false;
    }

    public int getColor() {
        double saturationLevel = mc.thePlayer.getFoodStats().getSaturationLevel();
        if ((Boolean) amountColors.getValue()) {
            if (saturationLevel <= 0) {
                return emptyAmountColor.getColorValue();
            } else if (saturationLevel <= 5) {
                return lowAmountColor.getColorValue();
            } else if (saturationLevel <= 10) {
                return mediumAmountColor.getColorValue();
            } else if (saturationLevel <= 15) {
                return highAmountColor.getColorValue();
            }
            return highestAmountColor.getColorValue();
        }
        return textColor.getColorValue();
    }

    public void getExtraSettings() {
        this.hideinCM = new Setting(this, "Hide in Creative Mode", "Hide the mod in Creative mode.").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.amountColors = new Setting(this, "Color Based on Amount", "Make the text color be based off the amount of saturation levels you have.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
    }

    public void getExtraFormatSettings() {
        this.trailingZeros = new Setting(this, "Show trailing zeros", "Show all trailing zeros.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Integer) this.decimals.getValue() != 0);
        this.decimals = new Setting(this, "Decimals", "Change how many decimals the mod should display.").setValue(0).setMinMax(0, 17).setCustomizationLevel(CustomizationLevel.MEDIUM);
    }

    public void getExtraColorSettings() {
        this.highestAmountColor = new Setting(this, "Highest Amount Color", "The text color that will show when the saturation level is over 15.").setValue(-2097185).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.amountColors.getValue());
        this.highAmountColor = new Setting(this, "High Amount Color", "The text color that will show when the saturation level is between 11-15.").setValue(-4522079).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.amountColors.getValue());
        this.mediumAmountColor = new Setting(this, "Medium Amount Color", "The text color that will show when the saturation level is between 6-10.").setValue(-103).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.amountColors.getValue());
        this.lowAmountColor = new Setting(this, "Low Amount Color", "The text color that will show when the saturation level is between 1-5.").setValue(-48574).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.amountColors.getValue());
        this.emptyAmountColor = new Setting(this, "Empty Amount Color", "The text color that will show when the saturation level is empty.").setValue(-5627615).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> (Boolean) this.amountColors.getValue());
    }

    @Override
    public SimpleHudModSize backgroundDimensionValues() {
        return new SimpleHudModSize(10, 18, 24, 18, 30, 80);
    }
}

