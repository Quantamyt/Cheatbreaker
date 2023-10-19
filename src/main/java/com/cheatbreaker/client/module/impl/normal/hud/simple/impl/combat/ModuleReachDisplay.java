package com.cheatbreaker.client.module.impl.normal.hud.simple.impl.combat;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.data.SimpleHudModExclusionRange;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Locale;

/**
 * Displays how far the user hits an entity.
 * @see AbstractModule
 */
public class ModuleReachDisplay extends AbstractSimpleCombatHudModule {
    private Setting trailingZeros;
    private Setting decimals;
    public static double lastRange;

    public ModuleReachDisplay() {
        super("Reach Display", "[2.8 blocks]");
        this.setDescription("Displays how far you hit an entity.");
        this.setCreators("dewgs");
    }

    public void getExtraFormatSettings() {
        this.trailingZeros = new Setting(this, "Show trailing zeros", "Show all trailing zeros.").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Integer) this.decimals.getValue() != 0);
        this.decimals = new Setting(this, "Decimals", "Change how many decimals the mod should display.").setValue(1).setMinMax(0, 16).setCustomizationLevel(CustomizationLevel.MEDIUM);
        super.getExtraFormatSettings();
    }

    public String getValueString() {
        if (this.excludeArrayOptions(this.hideValue, (int) lastRange, this.hiddenValue.getIntegerValue())) {
            return null;
        }
        if (lastAttackTime != 0L || !(Boolean) this.hideWhenNotAttacking.getValue()) {
            return (Boolean) trailingZeros.getValue() ? String.format("%." + decimals.getValue() + "f", lastRange) : new DecimalFormat("#." + String.join("", Collections.nCopies((Integer) decimals.getValue(), "#")), new DecimalFormatSymbols(Locale.ENGLISH)).format(lastRange);
        }
        return null;
    }

    @Override
    public String getLabelString() {
        return "blocks";
    }

    public String getPreviewString() {
        double value = 2.78003410578201503;
        return (Boolean) trailingZeros.getValue() ? String.format("%." + decimals.getValue() + "f", value) : new DecimalFormat("#." + String.join("", Collections.nCopies((Integer) decimals.getValue(), "#")), new DecimalFormatSymbols(Locale.ENGLISH)).format(value);
    }

    public void clear() {
        lastRange = 0.0;
    }

    public SimpleHudModExclusionRange exclusionRange() {
        return new SimpleHudModExclusionRange(0, 2, 5);
    }
}