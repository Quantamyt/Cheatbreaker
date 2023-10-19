package com.cheatbreaker.client.module.impl.normal.animation.util;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.impl.normal.animation.ModuleOneSevenVisuals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

/**
 * @ModuleUtility - AnimationHandler
 * @see ModuleOneSevenVisuals
 *
 * This class handles animations, sword blocking, etc.
 */
public class AnimationHandler {
    public static final AnimationHandler INSTANCE = new AnimationHandler();
    private final Minecraft mc = Minecraft.getMinecraft();
    public float prevSwingProgress;
    public float swingProgress;
    private int swingProgressInt;
    private boolean isSwingInProgress;

    public AnimationHandler() {
        CheatBreaker.getInstance().getEventBus().addEvent(TickEvent.class, this::onTick);
    }

    public float getSwingProgress(float partialTickTime) {
        float currentProgress = this.swingProgress - this.prevSwingProgress;

        if (!this.isSwingInProgress) {
            return this.mc.thePlayer.getSwingProgress(partialTickTime);
        }

        if (currentProgress < 0.0f) {
            currentProgress += 1.0f;
        }

        return this.prevSwingProgress + currentProgress * partialTickTime;
    }

    private int getArmSwingAnimationEnd(EntityPlayerSP player) {
        return player.isPotionActive(Potion.digSpeed) ? 5 - player.getActivePotionEffect(Potion.digSpeed).getAmplifier() : (player.isPotionActive(Potion.digSlowdown) ? 8 + player.getActivePotionEffect(Potion.digSlowdown).getAmplifier() * 2 : 6);
    }

    private void updateSwingProgress() {
        EntityPlayerSP player = this.mc.thePlayer;

        if (player == null) {
            return;
        }

        this.prevSwingProgress = this.swingProgress;
        int max = this.getArmSwingAnimationEnd(player);

        if (ModuleOneSevenVisuals.punching.getBooleanValue() && this.mc.gameSettings.keyBindAttack.isKeyDown() && this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (!this.isSwingInProgress || this.swingProgressInt >= max >> 1 || this.swingProgressInt < 0)) {
            this.isSwingInProgress = true;
            this.swingProgressInt = -1;
        }

        if (this.isSwingInProgress) {
            ++this.swingProgressInt;

            if (this.swingProgressInt >= max) {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }

        } else {
            this.swingProgressInt = 0;
        }

        this.swingProgress = (float) this.swingProgressInt / (float) max;
    }

    public void onTick(TickEvent event) {
        this.updateSwingProgress();
    }

    public boolean renderItemInFirstPerson(ItemRenderer renderer, ItemStack stack, float equipProgress, float partialTicks) {
        if (stack == null) {
            return false;
        }

        Item item = stack.getItem();
        if (item == Items.filled_map || this.mc.getRenderItem().shouldRenderItemIn3D(stack)) {
            return false;
        }

        EnumAction action = stack.getItemUseAction();
        if (item == Items.fishing_rod && !ModuleOneSevenVisuals.oldRod.getBooleanValue() || action == EnumAction.NONE && !ModuleOneSevenVisuals.oldModel.getBooleanValue() || action == EnumAction.BLOCK && !ModuleOneSevenVisuals.oldSwordBlock.getBooleanValue() || action == EnumAction.BOW && !ModuleOneSevenVisuals.oldBow.getBooleanValue()) {
            return false;
        }

        EntityPlayerSP player = this.mc.thePlayer;
        float var4 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;

        GlStateManager.pushMatrix();
        GlStateManager.rotate(var4, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();

        float pitch = player.prevRenderArmPitch + (player.renderArmPitch - player.prevRenderArmPitch) * partialTicks;
        float yaw = player.prevRenderArmYaw + (player.renderArmYaw - player.prevRenderArmYaw) * partialTicks;
        GlStateManager.rotate((player.rotationPitch - pitch) * 0.1f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate((player.rotationYaw - yaw) * 0.1f, 0.0f, 1.0f, 0.0f);
        GlStateManager.enableRescaleNormal();

        if (item instanceof ItemCloth) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }

        int i = this.mc.theWorld.getCombinedLight(new BlockPos(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ), 0);
        float brightnessX = i & 0xFFFF;
        float brightnessY = i >> 16;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessX, brightnessY);

        int rgb = item.getColorFromItemStack(stack, 0);
        float red = (float) (rgb >> 16 & 0xFF) / 255.0f;
        float green = (float) (rgb >> 8 & 0xFF) / 255.0f;
        float blue = (float) (rgb & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, 1.0f);

        GlStateManager.pushMatrix();
        int useCount = player.getItemInUseCount();
        float swingProgress = this.getSwingProgress(partialTicks);
        boolean blockHitOverride = false;

        if (ModuleOneSevenVisuals.punching.getBooleanValue() && useCount <= 0 && this.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                if (!Minecraft.getMinecraft().getCurrentServerData().serverIP.contains("minemen.club") && !Minecraft.getMinecraft().getCurrentServerData().serverIP.contains("icantjoinlmfao.club") && !Minecraft.getMinecraft().getCurrentServerData().serverIP.contains("mineman.club")) {
                    boolean block = action == EnumAction.BLOCK;
                    boolean consume = false;

                    if (item instanceof ItemFood && player.canEat(((ItemFood) item).isAlwaysEdible())) {
                        consume = action == EnumAction.EAT || action == EnumAction.DRINK;
                    }

                    if (block || consume) {
                        blockHitOverride = true;
                    }
                }
            } else {
                boolean block = action == EnumAction.BLOCK;
                boolean consume = false;

                if (item instanceof ItemFood && player.canEat(((ItemFood) item).isAlwaysEdible())) {
                    consume = action == EnumAction.EAT || action == EnumAction.DRINK;
                }

                if (block || consume) {
                    blockHitOverride = true;
                }
            }

        }

        if ((useCount > 0 || blockHitOverride) && action != EnumAction.NONE && this.mc.thePlayer.isUsingItem()) {
            switch (action) {
                case EAT:
                case DRINK:
                    this.doConsumeAnimation(stack, useCount, partialTicks);
                    this.doEquipAndSwingTransform(equipProgress, ModuleOneSevenVisuals.oldBlockHitting.getBooleanValue() ? swingProgress : 0.0f);
                    break;
                case BLOCK:
                    this.doEquipAndSwingTransform(equipProgress, ModuleOneSevenVisuals.oldBlockHitting.getBooleanValue() ? swingProgress : 0.0f);
                    this.doSwordBlockAnimation();
                    break;
                case BOW:
                    this.doEquipAndSwingTransform(equipProgress, ModuleOneSevenVisuals.oldBlockHitting.getBooleanValue() ? swingProgress : 0.0f);
                    this.doBowAnimation(stack, useCount, partialTicks);
            }
        } else {
            this.doSwingTranslation(swingProgress);
            this.doEquipAndSwingTransform(equipProgress, swingProgress);
        }

        if (item.shouldRotateAroundWhenRendering()) {
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        }

        if (this.doFirstPersonTransform(stack)) {
            renderer.renderItem(player, stack, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else {
            renderer.renderItem(player, stack, ItemCameraTransforms.TransformType.NONE);
        }

        GlStateManager.popMatrix();

        if (item instanceof ItemCloth) {
            GlStateManager.disableBlend();
        }

        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        return true;
    }

    public void doSwordBlock3rdPersonTransform() {
        if (ModuleOneSevenVisuals.oldSwordBlock3.getBooleanValue()) {
            GlStateManager.translate(-0.15f, -0.2f, 0.0f);
            GlStateManager.rotate(70.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(0.119f, 0.2f, -0.024f);
        }
    }

    private boolean doFirstPersonTransform(ItemStack stack) {

        switch (stack.getItemUseAction()) {
            case BOW: {
                if (ModuleOneSevenVisuals.oldBow.getBooleanValue()) break;
                return true;
            }
            case EAT:
            case DRINK: {
                if (ModuleOneSevenVisuals.oldEating.getBooleanValue()) break;
                return true;
            }
            case BLOCK: {
                if (ModuleOneSevenVisuals.oldSwordBlock.getBooleanValue()) break;
                return true;
            }
            case NONE: {
                if (ModuleOneSevenVisuals.oldModel.getBooleanValue()) break;
                return true;
            }
        }

        GlStateManager.translate(0.58800083f, 0.36999986f, -0.77000016f);
        GlStateManager.translate(0.0f, -0.3f, 0.0f);
        GlStateManager.scale(1.5f, 1.5f, 1.5f);
        GlStateManager.rotate(50.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(335.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-0.9375f, -0.0625f, 0.0f);
        GlStateManager.scale(-2.0f, 2.0f, -2.0f);

        if (this.mc.getRenderItem().shouldRenderItemIn3D(stack)) {
            GlStateManager.scale(0.58823526f, 0.58823526f, 0.58823526f);
            GlStateManager.rotate(-25.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.25f, -0.125f);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            return true;
        }

        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        return false;
    }

    private void doConsumeAnimation(ItemStack stack, int useCount, float partialTicks) {

        if (ModuleOneSevenVisuals.oldEating.getBooleanValue()) {
            float useAmount = (float) useCount - partialTicks + 1.0f;
            float useAmountNorm = 1.0f - useAmount / (float) stack.getMaxItemUseDuration();
            float useAmountPow = 1.0f - useAmountNorm;

            useAmountPow = useAmountPow * useAmountPow * useAmountPow;
            useAmountPow = useAmountPow * useAmountPow * useAmountPow;
            useAmountPow = useAmountPow * useAmountPow * useAmountPow;

            float useAmountFinal = 1.0f - useAmountPow;

            GlStateManager.translate(0.0f, MathHelper.abs(MathHelper.cos(useAmount / 4.0f * (float) Math.PI) * 0.1f) * (float) ((double) useAmountNorm > 0.2 ? 1 : 0), 0.0f);
            GlStateManager.translate(useAmountFinal * 0.6f, -useAmountFinal * 0.5f, 0.0f);
            GlStateManager.rotate(useAmountFinal * 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(useAmountFinal * 10.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(useAmountFinal * 30.0f, 0.0f, 0.0f, 1.0f);
        } else {
            float f = (float) useCount - partialTicks + 1.0f;
            float f1 = f / (float) stack.getMaxItemUseDuration();
            float f2 = MathHelper.abs(MathHelper.cos(f / 4.0f * (float) Math.PI) * 0.1f);

            if (f1 >= 0.8f) {
                f2 = 0.0f;
            }

            GlStateManager.translate(0.0f, f2, 0.0f);
            float f3 = 1.0f - (float) Math.pow(f1, 27.0);

            GlStateManager.translate(f3 * 0.6f, f3 * -0.5f, f3 * 0.0f);
            GlStateManager.rotate(f3 * 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(f3 * 10.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(f3 * 30.0f, 0.0f, 0.0f, 1.0f);
        }

    }

    private void doSwingTranslation(float swingProgress) {
        float swingProgress2 = MathHelper.sin(swingProgress * (float) Math.PI);
        float swingProgress3 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);

        GlStateManager.translate(-swingProgress3 * 0.4f, MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI * 2.0f) * 0.2f, -swingProgress2 * 0.2f);
    }

    private void doEquipAndSwingTransform(float equipProgress, float swingProgress) {
        GlStateManager.translate(0.56f, -0.52f - (1.0f - equipProgress) * 0.6f, -0.72f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);

        float swingProgress2 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float swingProgress3 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);

        GlStateManager.rotate(-swingProgress2 * 20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-swingProgress3 * 20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-swingProgress3 * 80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }

    private void doSwordBlockAnimation() {
        GlStateManager.translate(-0.5f, 0.2f, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
    }

    private void doBowAnimation(ItemStack stack, int useCount, float partialTicks) {
        GlStateManager.rotate(-18.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-12.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-8.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-0.9f, 0.2f, 0.0f);

        float totalPullback = (float) stack.getMaxItemUseDuration() - ((float) useCount - partialTicks + 1.0f);
        float pullbackNorm = totalPullback / 20.0f;
        pullbackNorm = (pullbackNorm * pullbackNorm + pullbackNorm * 2.0f) / 3.0f;

        if (pullbackNorm > 1.0f) {
            pullbackNorm = 1.0f;
        }

        if (pullbackNorm > 0.1f) {
            GlStateManager.translate(0.0f, MathHelper.sin((totalPullback - 0.1f) * 1.3f) * 0.01f * (pullbackNorm - 0.1f), 0.0f);
        }

        GlStateManager.translate(0.0f, 0.0f, pullbackNorm * 0.1f);

        if (ModuleOneSevenVisuals.oldBow.getBooleanValue()) {
            GlStateManager.rotate(-335.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(-50.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, 0.5f, 0.0f);
        }

        float zScale = 1.0f + pullbackNorm * 0.2f;
        GlStateManager.scale(1.0f, 1.0f, zScale);

        if (ModuleOneSevenVisuals.oldBow.getBooleanValue()) {
            GlStateManager.translate(0.0f, -0.5f, 0.0f);
            GlStateManager.rotate(50.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(335.0f, 0.0f, 0.0f, 1.0f);
        }
    }
}

