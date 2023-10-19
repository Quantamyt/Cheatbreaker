package com.cheatbreaker.client.module.impl.normal.hud.simple.impl;

import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Displays the current time.
 * @see AbstractSimpleHudModule
 */
public class ModuleClock extends AbstractSimpleHudModule {
    private Setting militaryFormat;
    private Setting meridiem;
    private Setting seconds;

    public ModuleClock() {
        super("Clock", "[11:11 AM]");
        this.setDescription("Displays the current time.");
    }

    public String getValueString() {
        String hour = !this.militaryFormat.getBooleanValue() ? "h" : "HH";
        String minute = ":mm";
        String seconds = this.seconds.getBooleanValue() ? ":ss" : "";
        String meridiem = this.meridiem.getBooleanValue() ? !this.militaryFormat.getBooleanValue() ? " a" : "" : "";
        return new SimpleDateFormat(hour + minute + seconds + meridiem).format(new Date());
    }

    public String getLabelString() {
        return "Time";
    }

    public String getDefaultFormatString() {
        return "%VALUE%";
    }

    public void getExtraFormatSettings() {
        this.militaryFormat = new Setting(this, "Military (24 hour) time", "Show the clock time in military time format.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.meridiem = new Setting(this, "Show Meridiem (AM/PM) Marker", "Show the AM/PM marker.").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> !(Boolean) this.militaryFormat.getValue());
        this.seconds = new Setting(this, "Show Seconds", "Displays the seconds.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
    }
}
