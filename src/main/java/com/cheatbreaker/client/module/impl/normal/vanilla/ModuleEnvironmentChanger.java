package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.event.impl.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.util.ResourceLocation;

import java.util.Date;

public class ModuleEnvironmentChanger extends AbstractModule {

    public Setting worldTime;
    public Setting timeType;

    public Setting allowThroughBlocks;
    public Setting customWeather;
    public Setting weatherType;

    public Date date;

    public ModuleEnvironmentChanger() {
        super("Environment Changer");
        this.setDescription("Allows you to change the environment around you to your liking.");
        this.setCreators("Fyu (Time Changer)", "Sk1er (Snow)");
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/env-changer.png"), 32, 32);

        new Setting(this, "label").setValue("Time Settings");
        this.timeType = new Setting(this, "Time Type").setValue("Server").acceptedStringValues("Server", "Real Time", "Static").onChange((value) -> this.setWorldTime());
        this.worldTime = new Setting(this, "World Time").setValue(-14490).setMinMax(-22880, -6100).setShowValue(false).setCondition(() -> this.timeType.getValue().equals("Static")).onChange((value) -> this.mc.theWorld.setWorldTime(Integer.parseInt(value.toString())));

        new Setting(this, "label").setValue("Weather Settings");
        this.allowThroughBlocks = new Setting(this, "Allow Through Blocks").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.customWeather = new Setting(this, "Custom Weather").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.weatherType = new Setting(this, "Current Weather").setValue("Clear").acceptedStringValues("Clear", "Rain", "Snow").setCondition(() -> this.customWeather.getBooleanValue()).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.addEvent(TickEvent.class, this::onTick);
    }

    private void onTick(TickEvent event) {
        if (this.mc.theWorld != null && this.timeType.getValue().equals("Real Time")) {
            this.mc.theWorld.setWorldTime(this.getRealWorldTime());
        }
    }

    public boolean isSnowOrNone() {
        return this.weatherType.getStringValue().equals("Clear") || this.weatherType.getStringValue().equals("Snow");
    }

    // gets the real time converted into Minecraft time
    private int getRealWorldTime() {
        if (this.date == null) this.date = new Date();
        this.date.setTime(System.currentTimeMillis());
        int hour = this.date.getHours() - 6;
        if (hour <= 0) hour += 24;
        return (int)(hour * 1000 + (this.date.getSeconds() + this.date.getMinutes() * 60) / 3.6);
    }

    public void setWorldTime() {
        if (this.mc.theWorld == null) return;

        if (this.timeType.getValue().equals("Real Time")) {
            this.mc.theWorld.setWorldTime(this.getRealWorldTime());
        } else if (this.timeType.getValue().equals("Static")) {
            this.mc.theWorld.setWorldTime(this.worldTime.getIntegerValue());
        }
    }
}
