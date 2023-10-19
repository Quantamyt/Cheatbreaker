package com.cheatbreaker.client.module.impl.normal.hud.simple.impl;

import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import com.cheatbreaker.client.module.impl.normal.hud.simple.data.SimpleHudModSize;
import net.minecraft.MinecraftMovementInputHelper;

/**
 * Allows the user's sprint and sneak keys to be toggleable.
 * @see AbstractSimpleHudModule
 */
public class ModuleToggleSprint extends AbstractSimpleHudModule {
    public static Setting toggleSprint;
    public static Setting toggleSneak;
    public static Setting doubleTap;
    public static Setting flyBoost;
    private Setting flyBoostLabel;
    public static Setting flyBoostAmount;
    public Setting flyBoostString;
    public Setting flyString;
    public Setting ridingString;
    public Setting decendString;
    public Setting dismountString;
    public Setting sneakHeldString;
    public Setting sprintHeldString;
    public Setting sprintVanillaString;
    public Setting sprintToggledString;
    public Setting sneakToggledString;
    public static boolean buggedSprint = false;

    public ModuleToggleSprint() {
        super("ToggleSprint",  "[Sprinting (Toggled)]", 1.0F, false, false);
        this.setDefaultState(false);
        this.notRenderHUD = false;
        this.setDescription("Makes your sprint and sneak keys toggleable.");
        this.setCreators("deeznueces");
    }

    public String getValueString() {
        if (!MinecraftMovementInputHelper.toggleSprintString.isEmpty()) {
            return MinecraftMovementInputHelper.toggleSprintString;
        }
        return null;
    }

    public String getPreviewString() {
        return this.sprintToggledString.getValue().toString();
    }

    public String getLabelString() {
        return "Sprint Status";
    }

    public String getDefaultFormatString() {
        return "%VALUE%";
    }

    public SimpleHudModSize backgroundDimensionValues() {
        return new SimpleHudModSize(10, 13, 24, 40, 56, 120);
    }

    public void getExtraSettings() {
        new Setting(this, "label").setValue("Toggle Sprint/Sneak Options").setCustomizationLevel(CustomizationLevel.MEDIUM);
        toggleSprint = new Setting(this, "Toggle Sprint", "Makes sprinting toggleable instead of held down.").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        toggleSneak = new Setting(this, "Toggle Sneak", "Makes sneaking toggleable instead of held down.").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        doubleTap = new Setting(this, "Double Tap", "Determine if double tapping the moving forward key should make the player start sprinting.").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);

        this.flyBoostLabel = new Setting(this, "label").setValue("Fly Boost");
        flyBoost = new Setting(this, "Fly Boost", "Determine if pressing the sprint key should boost the fly speed.").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        flyBoostAmount = new Setting(this, "Fly Boost Amount", "Change the boost amount.").setValue(4.0F).setMinMax(1.0F, 10.0F).setUnit("x").setCondition(() -> (Boolean) flyBoost.getValue()).setCustomizationLevel(CustomizationLevel.SIMPLE);
    }

    public void getExtraFormatSettings() {
        this.flyBoostString = new Setting(this, "Fly Boost String").setValue("Flying (%BOOST%x boost)").setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.flyString = new Setting(this, "Fly String").setValue("Flying").setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.ridingString = new Setting(this, "Riding String").setValue("Riding").setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.decendString = new Setting(this, "Descend String").setValue("Descending").setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.dismountString = new Setting(this, "Dismount String").setValue("Dismounting").setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.sneakHeldString = new Setting(this, "Sneaking String").setValue("Sneaking (Key Held)").setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.sprintHeldString = new Setting(this, "Sprinting Held String").setValue("Sprinting (Key Held)").setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.sprintVanillaString = new Setting(this, "Sprinting Vanilla String").setValue("Sprinting (Vanilla)").setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.sprintToggledString = new Setting(this, "Sprinting Toggle String").setValue("Sprinting (Toggled)").setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.sneakToggledString = new Setting(this, "Sneaking Toggle String").setValue("Sneaking (Toggled)").setCustomizationLevel(CustomizationLevel.ADVANCED);
    }
}
