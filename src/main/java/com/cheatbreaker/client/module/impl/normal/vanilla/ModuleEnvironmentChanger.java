package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.event.impl.keyboard.KeyboardEvent;
import com.cheatbreaker.client.event.impl.mouse.ClickEvent;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.util.ResourceLocation;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ModuleEnvironmentChanger extends AbstractModule {

    public Setting worldTime;
    public Setting timeType;

    public Setting allowThroughBlocks;
    public Setting customWeather;
    public Setting weatherType;

    public Setting timeMultiplierType;
    public Setting timeMultiplierDelay;
    public Setting timeMultiplierAmount;

    public Setting increaseDecreaseAmount;
    public Setting increaseStaticTimeKeybind;
    public Setting decreaseStaticTimeKeybind;

    public Date date;

    public ModuleEnvironmentChanger() {
        super("Environment Changer");
        this.setDescription("Allows you to change the environment around you to your liking.");
        this.setCreators("Fyu (Time Changer)", "Sk1er (Snow)");
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/env-changer.png"), 32, 32);

        new Setting(this, "label").setValue("Time Settings");
        this.timeType = new Setting(this, "Time Type").setValue("Server").acceptedStringValues("Server", "Real Time", "Static").onChange((value) -> this.setWorldTime());
        this.worldTime = new Setting(this, "World Time").setValue(-14490).setMinMax(-22880, -6100).setShowValue(false).setCondition(() -> this.timeType.getValue().equals("Static")).onChange((value) -> {
            if (this.mc.theWorld != null)
                this.mc.theWorld.setWorldTime(Integer.parseInt(value.toString()));
        });

        this.timeMultiplierType = new Setting(this, "Time Multiplier Type").setValue("Vanilla").acceptedStringValues("Vanilla", "Accelerate", "Decelerate").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.timeType.getValue().equals("Static"));
        this.timeMultiplierDelay = new Setting(this, "Time Multiplier Delay").setUnit("ms").setValue(300).setMinMax(300, 5000).setCustomizationLevel(CustomizationLevel.ADVANCED).setCondition(() -> this.timeType.getValue().equals("Static"));
        this.timeMultiplierAmount = new Setting(this, "Time Multiplier Amount").setValue(0).setMinMax(0, 10).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> this.timeType.getValue().equals("Static"));

        this.increaseDecreaseAmount = new Setting(this, "Increase/Decrease Amount").setValue(100).setMinMax(1, 1000).setCondition(() -> this.timeType.getStringValue().equalsIgnoreCase("Static")).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.increaseStaticTimeKeybind = new Setting(this, "Increase Time Keybind").setValue(0).setMouseBind(false).setCondition(() -> this.timeType.getStringValue().equalsIgnoreCase("Static")).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.decreaseStaticTimeKeybind = new Setting(this, "Decrease Time Keybind").setValue(0).setMouseBind(false).setCondition(() -> this.timeType.getStringValue().equalsIgnoreCase("Static")).setCustomizationLevel(CustomizationLevel.MEDIUM);

        new Setting(this, "label").setValue("Weather Settings");
        this.allowThroughBlocks = new Setting(this, "Allow Through Blocks").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.customWeather = new Setting(this, "Custom Weather").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.weatherType = new Setting(this, "Current Weather").setValue("Clear").acceptedStringValues("Clear", "Rain", "Snow").setCondition(() -> this.customWeather.getBooleanValue()).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.addEvent(TickEvent.class, this::onTick);
        this.addEvent(KeyboardEvent.class, this::onKeyboardPress);
        this.addEvent(ClickEvent.class, this::onMousebindPress);

        Runnable increaseRate = () -> {
            if (this.timeType.getStringValue().equalsIgnoreCase("Static") && this.mc.theWorld != null) {
                String type = this.timeMultiplierType.getStringValue();
                if (type.equalsIgnoreCase("Vanilla")) return;

                if (type.equalsIgnoreCase("Accelerate")) {
                    this.mc.theWorld.setWorldTime(this.mc.theWorld.getWorldTime() * this.timeMultiplierAmount.getIntegerValue());
                }

                if (type.equalsIgnoreCase("Decelerate")) {
                    this.mc.theWorld.setWorldTime(this.mc.theWorld.getWorldTime() / this.timeMultiplierAmount.getIntegerValue());
                }
                try {
                    Thread.sleep(this.timeMultiplierDelay.getIntegerValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(increaseRate, 1, 1, TimeUnit.MILLISECONDS);
    }

    private void onTick(TickEvent event) {
        if (this.mc.theWorld != null && this.timeType.getValue().equals("Real Time")) {
            this.mc.theWorld.setWorldTime(this.getRealWorldTime());
        }
    }

    public boolean isSnowOrNone() {
        return this.weatherType.getStringValue().equals("Clear") || this.weatherType.getStringValue().equals("Snow");
    }

    private void onKeyboardPress(KeyboardEvent event) {
        // amounts
        long increaseAmount = Integer.parseInt(this.worldTime.getValue().toString()) + this.increaseDecreaseAmount.getIntegerValue();
        long decreaseAmount = Integer.parseInt(this.worldTime.getValue().toString()) - this.increaseDecreaseAmount.getIntegerValue();

        if (this.timeType.getStringValue().equalsIgnoreCase("Static")) {
            if (event.getPressed() == this.increaseStaticTimeKeybind.getIntegerValue()) {
                if (this.mc.theWorld != null) {
                    if (increaseAmount > -6100) {
                        return;
                    }
//                    System.out.println("increaseAmount > -6100=" + (increaseAmount > -6100));
//                    System.out.println("getValue=" + Integer.parseInt(this.worldTime.getValue().toString()));
//                    System.out.println("getWorldTime=" + this.mc.theWorld.getWorldTime());
                    this.worldTime.setValue(Integer.parseInt(this.worldTime.getValue().toString()) + this.increaseDecreaseAmount.getIntegerValue());
                    this.mc.theWorld.setWorldTime(increaseAmount);
                }
            }

            if (event.getPressed() == this.decreaseStaticTimeKeybind.getIntegerValue()) {
                if (this.mc.theWorld != null) {
                    if (decreaseAmount < -22880) {
                        return;
                    }
//                    System.out.println("decreaseAmount > -22880=" + (-decreaseAmount > -22880));
//                    System.out.println("getValue=" + -Integer.parseInt(this.worldTime.getValue().toString()));
//                    System.out.println("getWorldTime=" + -this.mc.theWorld.getWorldTime());
                    this.worldTime.setValue(Integer.parseInt(this.worldTime.getValue().toString()) - this.increaseDecreaseAmount.getIntegerValue());
                    this.mc.theWorld.setWorldTime(decreaseAmount);
                }
            }
        }
    }

    private void onMousebindPress(ClickEvent event) {
        if (this.timeType.getStringValue().equalsIgnoreCase("Static")) {
            if (event.getMouseButton() == this.increaseStaticTimeKeybind.getIntegerValue()) {
                this.worldTime.setValue(this.worldTime.getIntegerValue() + 100);
            }
            if (event.getMouseButton() == this.increaseStaticTimeKeybind.getIntegerValue()) {
                this.worldTime.setValue(this.worldTime.getIntegerValue() - 100);
            }
        }
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
