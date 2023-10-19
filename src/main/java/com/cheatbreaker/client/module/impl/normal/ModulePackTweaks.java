package com.cheatbreaker.client.module.impl.normal;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.util.ResourceLocation;

public class ModulePackTweaks extends AbstractModule {

    public Setting clearGlass;

    public Setting coloredString;
    public Setting coloredStringColor;

    public Setting firstPersonFire;
    public Setting firstPersonFireOpacity;
    public Setting firstPersonFireHeight;
    public Setting fireOnEntities;

    public Setting forceVignette;
    public Setting vignetteOpacity;
    public Setting vignetteMinOpacity;
    public Setting vignetteMaxOpacity;
    public Setting vignetteOpacityMultiplier;

    public Setting pumpkinOverlay;
    public Setting pumpkinOverlayOpacity;

    public Setting transparentInventory;
    public Setting tooltips;

    public Setting faceCamera;
    public Setting yRotation;

    public ModulePackTweaks() {
        super("Pack Tweaks");
        this.setDescription("Change how specific textures appear.");
        this.setDefaultState(true);

        new Setting(this, "label").setValue("Glass Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.clearGlass = new Setting(this, "Clear Glass", "Removes the glass texture." +
                "\n" +
                "\n§bOFF:§r No glass textures are removed." +
                "\n§bREGULAR:§r Only normal glass texture is removed." +
                "\n§bALL:§r Normal and stained glass textures are removed.").setValue("OFF").acceptedStringValues("OFF", "REGULAR", "ALL").onChange(value -> {
            this.mc.renderGlobal.loadRenderers();
        }).setCustomizationLevel(CustomizationLevel.SIMPLE);

        new Setting(this, "label").setValue("String Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.coloredString = new Setting(this, "Colored String", "Show a solid color line instead of the texture pack's string texture.").setValue(false).onChange(value -> {
            this.mc.renderGlobal.loadRenderers();
        }).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.coloredStringColor = new Setting(this, "Colored String Color").setValue(0xFFFF0000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.coloredString.getValue());

        new Setting(this, "label").setValue("Fire Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.firstPersonFire = new Setting(this, "First Person Fire").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.firstPersonFireOpacity = new Setting(this, "Fire Opacity").setValue(90.0f).setMinMax(0.0f, 100.0f).setUnit("%").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.firstPersonFire.getValue());
        this.firstPersonFireHeight = new Setting(this, "Fire Height").setValue(1.0f).setMinMax(0.0f, 2.0f).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.firstPersonFire.getValue());
        this.fireOnEntities = new Setting(this, "Fire on Entities").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);

        new Setting(this, "label").setValue("Vignette Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.forceVignette = new Setting(this, "Vignette Type").setValue("Vanilla").acceptedStringValues("Vanilla", "Static", "Amplified").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.vignetteOpacity = new Setting(this, "Vignette Opacity").setValue(75.0f).setMinMax(0.0f, 100.0f).setUnit("%").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.forceVignette.getValue().equals("Static"));
        this.vignetteMinOpacity = new Setting(this, "Vignette Minimum Opacity").setValue(0.0F).setMinMax(0.0f, 100.0f).setUnit("%").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.forceVignette.getValue().equals("Amplified"));
        this.vignetteMaxOpacity = new Setting(this, "Vignette Maximum Opacity").setValue(100.0F).setMinMax(0.0f, 100.0f).setUnit("%").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.forceVignette.getValue().equals("Amplified"));
        this.vignetteOpacityMultiplier = new Setting(this, "Vignette Opacity Multiplier").setValue(1.0f).setMinMax(0.1f, 10.0f).setUnit("x").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.forceVignette.getValue().equals("Amplified"));

        new Setting(this, "label").setValue("Pumpkin Overlay Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.pumpkinOverlay = new Setting(this, "Show Pumpkin Overlay").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.pumpkinOverlayOpacity = new Setting(this, "Pumpkin Overlay Opacity").setValue(100.0f).setMinMax(0.0f, 100.0f).setUnit("%").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.pumpkinOverlay.getValue());

        new Setting(this, "label").setValue("Inventory Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.transparentInventory = new Setting(this, "Transparent Inventory").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.tooltips = new Setting(this, "Show Inventory Tooltips").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);

        new Setting(this, "label").setValue("Dropped Item Options").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.yRotation = new Setting(this, "Rotate towards camera").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.faceCamera = new Setting(this, "3D Face player").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);

        this.setPreviewIcon(new ResourceLocation("client/icons/mods/packtweaks.png"), 32, 32);
    }


}
