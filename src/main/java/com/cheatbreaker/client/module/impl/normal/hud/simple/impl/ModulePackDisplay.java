package com.cheatbreaker.client.module.impl.normal.hud.simple.impl;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.TickEvent;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import com.cheatbreaker.client.module.impl.normal.hud.simple.data.SimpleHudModSize;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

import java.util.regex.Pattern;

/**
 * Displays the player's active Resource Pack(s).
 * @see AbstractSimpleHudModule
 */
public class ModulePackDisplay extends AbstractSimpleHudModule {
    //    private Setting description; TODO: Set up Pack Display to show the description
    private Setting zipRemover;
    private Setting prefixRemover;
    private Setting stripColorCodes;

    public Setting order;
    private Setting rotationOrder;
    private Setting rotationInterval;

    private final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-9A-F]");
    private final Pattern STRIP_COLOR_PATTERN2 = Pattern.compile("(?i)" + '§' + "[1-69A-E]");

    private int ticks = 0;
    public int currentPack = 0;


    public ModulePackDisplay() {
        super("Pack Display", "Default");
        this.setDescription("Displays your active Resource Pack(s).");
        this.setCreators("canelex");
        this.addEvent(TickEvent.class, this::onTick);
    }

    private void onTick(TickEvent event) {
        ++this.ticks;
        if (this.order.getValue().equals("Rotate")) {
            if (this.ticks > this.rotationInterval.getFloatValue() * 20) {
                int max = Config.getResourcePackNames().split(",").length - 1;
                switch (this.rotationOrder.getStringValue()) {
                    case "Top-Bottom":
                        --this.currentPack;
                        if (this.currentPack < 0) {
                            this.currentPack = max;
                        }
                        break;
                    case "Bottom-Top":
                        ++this.currentPack;
                        if (this.currentPack > max) {
                            this.currentPack = 0;
                        }
                        break;
                    case "Randomized":
                        this.currentPack = (int)(Math.random() * (max + 1));
                }
                SimpleReloadableResourceManager.updatepack();
                this.ticks = 0;
            }
        }
    }

    @Override
    public String getValueString() {
        String[] packs = Config.getResourcePackNames().split(",");
        String counter = packs[packs.length - 1];
        if (this.order.getValue().equals("Bottom")) {
            counter = packs[0];
        } else if (this.order.getValue().equals("Rotate")) {
            counter = packs[this.currentPack];
        }
        if (this.zipRemover.getBooleanValue()) {
            counter = counter.replace(".zip", "");
        }
        if (this.prefixRemover.getBooleanValue()) {
            counter = counter.substring(counter.indexOf("!") + 1).substring(counter.indexOf("#") + 1).trim();
            if (counter.substring(0, 1).contains("§") && counter.substring(2, 3).contains(" ")) {
                counter = counter.replaceFirst(" ", "");
            }

        }
        return !this.stripColorCodes.getValue().equals("OFF") ? stripColor(counter) : counter;
    }

    @Override
    public String getLabelString() {
        return "Pack";
    }

    public String getDefaultFormatString() {
        return "%VALUE%";
    }

    public boolean isBracketsEnabledByDefault() {
        return false;
    }

    public ResourceLocation getIconTexture() {
        return CheatBreaker.icontexture;
    }

    public SimpleHudModSize backgroundDimensionValues() {
        return new SimpleHudModSize(10, 16, 64, 40, 56, 80);
    }

    private String stripColor(String input) {
        return input == null ? null : (this.stripColorCodes.getValue().equals("ALL") ? STRIP_COLOR_PATTERN : STRIP_COLOR_PATTERN2).matcher(input).replaceAll("§r");
    }

    public void getExtraSettings() {
        this.order = new Setting(this, "Pack Order").setValue("Bottom").acceptedStringValues("Bottom", "Top", /*"All", */"Rotate").setCondition(() -> Config.getResourcePackNames().split(",").length != 1).onChange((v) -> SimpleReloadableResourceManager.updatepack());
        this.rotationOrder = new Setting(this, "Rotation Order").setValue("Top-Bottom").acceptedStringValues("Top-Bottom", "Bottom-Top", "Randomized").setCondition(() -> this.order.getValue().equals("Rotate") && Config.getResourcePackNames().split(",").length != 1);
        this.rotationInterval = new Setting(this, "Rotation Interval").setValue(10.0F).setMinMax(1.0F, 60.0F).setUnit("s").setCondition(() -> this.order.getValue().equals("Rotate") && Config.getResourcePackNames().split(",").length != 1);
    }

    public void getExtraFormatSettings() {
        //this.description = new Setting(this, "Show description").setValue(true);
        this.zipRemover = new Setting(this, "Remove zip string", "Removes the \".zip\" part of a pack name if the file is in a zip.").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.prefixRemover = new Setting(this, "Remove prefixes", "Removes ! and # prefixes from a pack.").setValue(true).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.stripColorCodes = new Setting(this, "Strip Color Formatting", "Remove all color formatting so you can set your own color.").setValue("OFF").acceptedStringValues("OFF", "Not Neutral", "ALL").setCustomizationLevel(CustomizationLevel.MEDIUM);
    }
}
