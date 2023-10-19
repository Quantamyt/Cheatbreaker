package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.util.ResourceLocation;

public class ModuleHitColor extends AbstractModule {
    public Setting toggle;
    public Setting affectArmor;
    public Setting affectedBrightness;
    public Setting hitColor;
    public Setting fade;


    public ModuleHitColor() {
        super("Hit Color");
        this.setDefaultState(true);

        this.toggle = new Setting(this, "Enable Hit Color").setValue(true);
        new Setting(this, "label").setValue("General Options").setCondition(this.toggle::getBooleanValue);
        this.affectArmor = new Setting(this, "Affect Armor").setValue(true).setCondition(this.toggle::getBooleanValue);
        this.affectedBrightness = new Setting(this, "Affected by brightness").setValue(true).setCondition(this.toggle::getBooleanValue);
        this.fade = new Setting(this, "Animation Type").setValue("None").acceptedStringValues("None", "Linear In/Out", "Linear Out").setCondition(this.toggle::getBooleanValue);

        // All Color options
        new Setting(this, "label").setValue("Color Options").setCondition(this.toggle::getBooleanValue);
        this.hitColor = new Setting(this, "Hit Color").setValue(0x66FF0000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(this.toggle::getBooleanValue);
        setDescription("Customize the damage tint overlay on entities.");
        setCreators("aycy (Fade animation)");
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/hittint.png"), 32, 32);
    }
}