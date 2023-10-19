package com.cheatbreaker.client.module.impl.normal.hud.simple.module;

import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Module - ModuleClock
 * @see AbstractSimpleHudModule
 *
 * This module shows the current time.
 */
public class SimpleModuleClock extends AbstractSimpleHudModule {
    private Setting militaryFormat;
    private Setting meridiem;
    private Setting seconds;

    public SimpleModuleClock() {
        super("Clock", "[11:11 AM]");
        setDescription("Displays your current time.");
    }

    @Override
    public String getValueString() {
        return new SimpleDateFormat((!(Boolean) militaryFormat.getValue() ? "h" : "HH")
                + ":mm" + ((Boolean) seconds.getValue() ? ":ss" : "")
                + ((Boolean) meridiem.getValue() ? !(Boolean) militaryFormat.getValue() ? " a" : "" : "")).format(new Date());
    }

    @Override
    public String getLabelString() {
        return "Time";
    }

    public String customString() {
        return "%VALUE%";
    }

    public void getExtraFormatSettings() {
        this.militaryFormat = new Setting(this, "24 hour/military time", "Show the clock time in military time format.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.meridiem = new Setting(this, "Show AM/PM", "Show the AM/PM marker.").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> !(Boolean) this.militaryFormat.getValue());
        this.seconds = new Setting(this, "Show seconds", "Displays the seconds.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
    }
}
