package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.util.ResourceLocation;

/**
 * @Module - ModuleHitColor
 * @see AbstractModule
 *
 * Changes the Vanilla Minecraft entity hit color.
 */
public class ModuleHitColor extends AbstractModule {
    public Setting affectArmor;
    public Setting affectedBrightness;
    public Setting hitColor;
    public Setting fade;

    public ModuleHitColor() {
        super("Hit Color");
        this.setDefaultState(true);

        new Setting(this, "label").setValue("General Options");
        affectArmor = new Setting(this, "Affect Armor").setValue(true);
        affectedBrightness = new Setting(this, "Affected by brightness").setValue(true);
        fade = new Setting(this, "Animation Type").setValue("None").acceptedStringValues("None", "Linear In/Out", "Linear Out");

        // All Color options
        new Setting(this, "label").setValue("Color Options");
        hitColor = new Setting(this, "Hit Color").setValue(0x66FF0000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.setDescription("Customize the damage tint overlay on entities.");
        this.setCreators("aycy (Fade animation)");
        this.setAliases("Damage Tint");
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/hittint.png"), 32, 32);
    }
}