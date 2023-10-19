package com.cheatbreaker.client.module.impl.normal.perspective;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModulePerspective extends AbstractModule {
    private final Setting thirdPersonOptionsLabel;
    public Setting toggleFrontCamera;
    public Setting toggleBackCamera;
//    public Setting keyBindBackCamera;
//    public Setting keyBindFrontCamera;

    private final Setting dragToLookOptionsLabel;
    public Setting lookView;
    public Setting toggleDragToLook;
    public Setting pitchLock;
    public Setting invertPitch;
    public Setting invertYaw;
//    public Setting keyBindDragToLook;

    private final Setting viewBobbingOptionsLabel;
    public Setting bobScreen;
    public Setting bobHand;
    public Setting bobHandWhileHoldingMap;
    public Setting bobWhileSprinting;
    public Setting screenBobbingIntensity;
    public Setting handBobbingIntensity;

    private final Setting cameraOptionsLabel;
    public Setting damageAffectsCamera;
    public Setting hurtCameraIntensity;

    public Setting staticSwiftness;
    //public Setting defaultFOV;
    public Setting aimingMultiplier;

    private float prevValue = 0.0F;

    public ModulePerspective() {
        super("Perspective");
        this.setDescription("Allows you to change how your perspective looks.");
        this.setDefaultState(true);
        this.setPreviewLabel("Perspective", 1.0F);
        this.thirdPersonOptionsLabel = new Setting(this, "label").setValue("Third Person Options");
        this.toggleBackCamera = new Setting(this, "Toggle Back Camera", "Make the back camera keybind toggled instead of held.").setValue(false);
        this.toggleFrontCamera = new Setting(this, "Toggle Front Camera", "Make the front camera keybind toggled instead of held.").setValue(false);
//        this.keyBindBackCamera = new Setting(this, "Back Camera Keybind", "The key to use the Back Camera.").setValue(0);
//        this.keyBindFrontCamera = new Setting(this, "Front Camera Keybind", "The key to use the Front Camera.").setValue(0);

        this.dragToLookOptionsLabel = new Setting(this, "label").setValue("Drag to Look Options");
        this.lookView = new Setting(this, "Look View", "Change which perspective mode to use when toggling Drag to Look.").setValue("Third").acceptedStringValues("Third", "Reverse", "First");
        this.toggleDragToLook = new Setting(this, "Toggle Drag to Look", "Make the Drag to Look keybind toggled instead of held.").setValue(false);
        this.pitchLock = new Setting(this, "Pitch Lock", "Toggle the pitch axis limitation.").setValue(true);
        this.invertPitch = new Setting(this, "Invert Pitch (Up and Down)", "Invert the rotation pitch when moving the camera.").setValue(false);
        this.invertYaw = new Setting(this, "Invert Yaw (Left and Right)", "Invert the rotation yaw when moving the camera.").setValue(false);
//        this.keyBindDragToLook = new Setting(this, "Drag to Look Keybind", "The key to use Drag to Look.").setValue(Keyboard.KEY_LMENU);

        this.viewBobbingOptionsLabel = new Setting(this, "label").setValue("View Bobbing Options");
        this.bobScreen = new Setting(this, "Bob Screen", "Bob the camera screen when moving.").setValue(true);
        this.bobHand = new Setting(this, "Bob Hand", "Bob the hand when moving.").setValue(true);
        this.bobWhileSprinting = new Setting(this, "Bob Only While Sprinting", "Bob the hand only when sprinting.").setValue("OFF").acceptedStringValues("OFF", "ON", "Only Hand", "Only Screen");
        this.screenBobbingIntensity = new Setting(this, "Screen Bobbing Intensity").setUnit("%").setValue(100.0F).setMinMax(0.0F, 200.0F);
        this.handBobbingIntensity = new Setting(this, "Hand Bobbing Intensity").setUnit("%").setValue(100.0F).setMinMax(0.0F, 200.0F);

        this.cameraOptionsLabel = new Setting(this, "label").setValue("Camera Options");
        this.damageAffectsCamera = new Setting(this, "Damage affects camera").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.hurtCameraIntensity = new Setting(this, "Hurt Camera Intensity").setUnit("%").setValue(14.0F).setMinMax(5.0F, 35.0F).setCondition(() -> this.damageAffectsCamera.getBooleanValue());
        // fov changer
        this.staticSwiftness = new Setting(this, "Static Swiftness", "Determines if your FOV changes as you move.").setValue(false);
        //this.defaultFOV = new Setting(this, "Default FOV").setUnit(" FOV").setValue(30).setMinMax(30, 110);
        this.aimingMultiplier = new Setting(this, "Aiming Multiplier").setUnit("x").setValue(0.15F).setMinMax(0.15F, 0.75F);
    }

    /**
     * Setups all the GL settings for view bobbing. Args: partialTickTime
     */
    public void setupViewBobbing(float par1, Setting bobElement) {
        if (this.mc.renderViewEntity instanceof EntityPlayer && bobElement.getBooleanValue()) {
            EntityPlayer var2 = (EntityPlayer)this.mc.renderViewEntity;

            boolean sprintingToggleCriteria = this.bobWhileSprinting.getValue().equals("Only Screen");
            float intensity = this.screenBobbingIntensity.getFloatValue() / 100.0F;
            if (bobElement == this.bobHand) {
                sprintingToggleCriteria = this.bobWhileSprinting.getValue().equals("Only Hand");
                intensity = this.handBobbingIntensity.getFloatValue() / 100.0F;
            }

            float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
            float var4 = -(var2.distanceWalkedModified + var3 * par1);
            float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * par1;
            float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * par1;

            float var22 = this.bobWhileSprinting.getValue().equals("ON") || sprintingToggleCriteria ? this.prevValue : var5;
            if (this.bobWhileSprinting.getValue().equals("ON") || sprintingToggleCriteria) {
                if (var2.isSprinting()) {
                    this.prevValue = var5;
                } else {
                    this.prevValue -= 0.00009;
                }

                if (this.prevValue < 0.0F) {
                    return;
                }

            }

            GL11.glTranslatef(MathHelper.sin(var4 * (float)Math.PI) * var22 * 0.5F * intensity, -Math.abs(MathHelper.cos(var4 * (float)Math.PI) * var22 * intensity), 0.0F);
            GL11.glRotatef(MathHelper.sin(var4 * (float)Math.PI) * var22 * 3.0F * intensity, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(Math.abs(MathHelper.cos(var4 * (float)Math.PI - 0.2F) * var22) * 5.0F * intensity, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var6, 1.0F, 0.0F, 0.0F);
        }
    }
}
