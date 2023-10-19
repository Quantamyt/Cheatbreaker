package com.cheatbreaker.client.module.impl.normal.animation;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;

/**
 * @Module - ModuleOneSevenVisuals
 * @see AbstractModule
 *
 * This mod reverts all changed visuals to their 1.7 counterparts.
 */
public class ModuleOneSevenVisuals extends AbstractModule {
    public static Setting oldModel;
    public static Setting oldBow;
    public static Setting oldSwordBlock;
    public static Setting oldRod;
    public static Setting oldSwordBlock3;
    public static Setting oldEating;
    public static Setting oldBlockHitting;
    public static Setting smoothSneaking;
    public static Setting longSneaking;
    public static Setting redArmor;
    public static Setting punching;
    public static Setting itemSwitch;
    public static Setting oldHealth;
    public static Setting oldDebugScreen;
    public static Setting oldTab;
    public static Setting oldDebugHitbox;

    public ModuleOneSevenVisuals() {
        super("1.7 Animations");
        this.setDescription("Revert certain visuals to appear like their 1.7 counterpart.");
        this.setPreviewLabel("1.7 <-> 1.8", 1.0f);
        this.setCreators("OrangeMarshall");
        this.setAliases("1.7 Visuals", "Old Animations");
        this.setDefaultState(true);

        new Setting(this, "label").setValue("Position Settings");
        oldModel = new Setting(this, "1.7 Item Positions", "Change all item models to be in the same position as 1.7.").setValue(true);
        oldBow = new Setting(this, "1.7 Bow Pullback", "Change the bow pullback animation to be like 1.7.").setValue(true);
        oldSwordBlock = new Setting(this, "1.7 Block Animation", "Change the sword block animation to be like 1.7.").setValue(true);
        oldRod = new Setting(this, "1.7 Rod Position", "Change all item models to be in the same position as 1.7.").setValue(true);
        oldSwordBlock3 = new Setting(this, "1.7 3rd Person Block Animation", "Change the 3rd person blocking animation to be like 1.7.").setValue(true);

        new Setting(this, "label").setValue("Interaction Settings");
        oldEating = new Setting(this, "Consume Animation", "Change the eating and drinking animation to look like 1.7.").setValue(true);
        oldBlockHitting = new Setting(this, "Block-Hitting Animation", "Makes block hitting look much smoother, like it did in 1.7.").setValue(true);
        smoothSneaking = new Setting(this, "Smooth Sneaking", "Makes the transition between sneaking/not sneaking smooth.\\n§eCombine with longer unsneak to match 1.7.").setValue(true);
        longSneaking = new Setting(this, "Longer Unsneak", "Makes moving up take longer than moving down\\n§eCombine with smooth sneaking to match 1.7.").setValue(true).setCondition(() -> smoothSneaking.getBooleanValue());
        punching = new Setting(this, "Punching During Usage", "Allows you to punch blocks whilst using an item.\\n§eVisual only.").setValue(true);
        itemSwitch = new Setting(this, "Item Switching Animation", "Stop held items from playing the switching animation when right clicking on blocks.").setValue(true);
        redArmor = new Setting(this, "1.7 Hit Color Brightness", "Set the hit color to use 1.7's brightness system, darkening the hit color.").setValue(true);

        new Setting(this, "label").setValue("HUD Settings");
        oldHealth = new Setting(this, "Remove Health Bar Flashing", "Stops your health bar flashing when you take damage.").setValue(true);
//        oldDebugScreen = new Setting(this, "1.7 Debug Screen (Not implemented)", "Remove the boxes in the debug screen.").setValue(true);
    }
}
