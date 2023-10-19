package net.minecraft.client.renderer.entity;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.impl.normal.vanilla.ModuleEnchantmentGlint;
import com.cheatbreaker.client.module.impl.normal.vanilla.ModuleHitColor;
import com.cheatbreaker.client.module.impl.normal.vanilla.ModuleNametag;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleNickHider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.util.Random;
import java.util.regex.Pattern;

public abstract class RendererLivingEntity extends Render {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    public ModelBase mainModel;
    private final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-689A-E]");

    /** The model to be used during the render passes. */
    public ModelBase renderPassModel;


    public RendererLivingEntity(ModelBase p_i1261_1_, float p_i1261_2_) {
        this.mainModel = p_i1261_1_;
        this.shadowSize = p_i1261_2_;
    }

    /**
     * Sets the model to be used in the current render pass (the first render pass is done after the primary model is
     * rendered) Args: model
     */
    public void setRenderPassModel(ModelBase p_77042_1_) {
        this.renderPassModel = p_77042_1_;
    }

    /**
     * Returns a rotation angle that is inbetween two other rotation angles. par1 and par2 are the angles between which
     * to interpolate, par3 is probably a float between 0.0 and 1.0 that tells us where "between" the two angles we are.
     * Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
     */
    private float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_) {
        float var4;

        for (var4 = p_77034_2_ - p_77034_1_; var4 < -180.0F; var4 += 360.0F) {
        }

        while (var4 >= 180.0F) {
            var4 -= 360.0F;
        }

        return p_77034_1_ + p_77034_3_ * var4;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        this.mainModel.onGround = this.renderSwingProgress(p_76986_1_, p_76986_9_);

        if (this.renderPassModel != null) {
            this.renderPassModel.onGround = this.mainModel.onGround;
        }

        this.mainModel.isRiding = p_76986_1_.isRiding();

        if (this.renderPassModel != null) {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }

        this.mainModel.isChild = p_76986_1_.isChild();

        if (this.renderPassModel != null) {
            this.renderPassModel.isChild = this.mainModel.isChild;
        }

        try {
            float var10 = this.interpolateRotation(p_76986_1_.prevRenderYawOffset, p_76986_1_.renderYawOffset, p_76986_9_);
            float var11 = this.interpolateRotation(p_76986_1_.prevRotationYawHead, p_76986_1_.rotationYawHead, p_76986_9_);
            float var13;

            if (p_76986_1_.isRiding() && p_76986_1_.ridingEntity instanceof EntityLivingBase) {
                EntityLivingBase var12 = (EntityLivingBase)p_76986_1_.ridingEntity;
                var10 = this.interpolateRotation(var12.prevRenderYawOffset, var12.renderYawOffset, p_76986_9_);
                var13 = MathHelper.wrapAngleTo180_float(var11 - var10);

                if (var13 < -85.0F) {
                    var13 = -85.0F;
                }

                if (var13 >= 85.0F) {
                    var13 = 85.0F;
                }

                var10 = var11 - var13;

                if (var13 * var13 > 2500.0F) {
                    var10 += var13 * 0.2F;
                }
            }

            float var26 = p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_;
            this.renderLivingAt(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_);
            var13 = this.handleRotationFloat(p_76986_1_, p_76986_9_);
            this.rotateCorpse(p_76986_1_, var13, var10, p_76986_9_);
            float var14 = 0.0625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback(p_76986_1_, p_76986_9_);
            GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
            float var15 = p_76986_1_.prevLimbSwingAmount + (p_76986_1_.limbSwingAmount - p_76986_1_.prevLimbSwingAmount) * p_76986_9_;
            float var16 = p_76986_1_.limbSwing - p_76986_1_.limbSwingAmount * (1.0F - p_76986_9_);

            if (p_76986_1_.isChild()) {
                var16 *= 3.0F;
            }

            if (var15 > 1.0F) {
                var15 = 1.0F;
            }

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            this.mainModel.setLivingAnimations(p_76986_1_, var16, var15, p_76986_9_);
            this.renderModel(p_76986_1_, var16, var15, var13, var11 - var10, var26, var14);
            int var18;
            float var19;
            float var20;
            float var22;

            for (int var17 = 0; var17 < 4; ++var17) {
                var18 = this.shouldRenderPass(p_76986_1_, var17, p_76986_9_);

                if (var18 > 0) {
                    this.renderPassModel.setLivingAnimations(p_76986_1_, var16, var15, p_76986_9_);
                    this.renderPassModel.render(p_76986_1_, var16, var15, var13, var11 - var10, var26, var14);

                    if ((var18 & 240) == 16) {
                        this.func_82408_c(p_76986_1_, var17, p_76986_9_);
                        this.renderPassModel.render(p_76986_1_, var16, var15, var13, var11 - var10, var26, var14);
                    }

                    ModuleEnchantmentGlint glintModule = CheatBreaker.getInstance().getModuleManager().enchantmentGlintMod;
                    if ((var18 & 15) == 15 && glintModule.isEnabled() && (Boolean)glintModule.armorGlint.getValue()) {
                        var19 = (float)p_76986_1_.ticksExisted + p_76986_9_;
                        this.bindTexture(RES_ITEM_GLINT);
                        GL11.glEnable(GL11.GL_BLEND);
                        var20 = 0.5F;
                        GL11.glColor4f(var20, var20, var20, 1.0F);
                        GL11.glDepthFunc(GL11.GL_EQUAL);
                        GL11.glDepthMask(false);

                        for (int var21 = 0; var21 < 2; ++var21)
                        {
                            GL11.glDisable(GL11.GL_LIGHTING);
                            int getGlintColor = glintModule.armorGlintColor.getColorValue();
                            float glintA = (float)(getGlintColor >> 24 & 255) / 255.0F;
                            float glintR = (float)(getGlintColor >> 16 & 255) / 255.0F;
                            float glintG = (float)(getGlintColor >> 8 & 255) / 255.0F;
                            float glintB = (float)(getGlintColor & 255) / 255.0F;
                            var22 = glintA * 0.76F;
                            GL11.glColor4f(glintR * var22, glintG * var22, glintB * var22, 1.0F);
                            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                            GL11.glMatrixMode(GL11.GL_TEXTURE);
                            GL11.glLoadIdentity();
                            float var23 = var19 * (0.001F + (float)var21 * 0.003F) * 20.0F;
                            float var24 = 0.33333334F;
                            GL11.glScalef(var24, var24, var24);
                            GL11.glRotatef(30.0F - (float)var21 * 60.0F, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslatef(0.0F, var23, 0.0F);
                            GL11.glMatrixMode(GL11.GL_MODELVIEW);
                            this.renderPassModel.render(p_76986_1_, var16, var15, var13, var11 - var10, var26, var14);
                        }

                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glMatrixMode(GL11.GL_TEXTURE);
                        GL11.glDepthMask(true);
                        GL11.glLoadIdentity();
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        GL11.glEnable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glDepthFunc(GL11.GL_LEQUAL);
                    }

                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                }
            }

            GL11.glDepthMask(true);
            this.renderEquippedItems(p_76986_1_, p_76986_9_);
            ModuleHitColor hitColorModule = CheatBreaker.getInstance().getModuleManager().hitColorMod;
            float var27;
            if ((Boolean)hitColorModule.affectedBrightness.getValue() || !hitColorModule.isEnabled()) {
                var27 = p_76986_1_.getBrightness(p_76986_9_);
            } else {
                var27 = 1.0F;
            }
            var18 = this.getColorMultiplier(p_76986_1_, var27, p_76986_9_);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            if (hitColorModule.toggle.getBooleanValue() || !hitColorModule.isEnabled()) {
                if ((var18 >> 24 & 255) > 0 || p_76986_1_.hurtTime > 0 || p_76986_1_.deathTime > 0) {
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glDepthFunc(GL11.GL_EQUAL);

                    int hitColor = hitColorModule.hitColor.getColorValue();
                    float hitA = (float)(hitColor >> 24 & 255) / 255.0F;
                    if (!hitColorModule.fade.getValue().equals("None")) {
                        float percent = 1.0F - (float)p_76986_1_.hurtTime / 10.0F;
                        if (hitColorModule.fade.getValue().equals("Linear In/Out")) {
                            percent = percent < 0.5F ? percent / 0.5F : (1.0F - percent) / 0.5F;
                        } else if (hitColorModule.fade.getValue().equals("Linear Out")) {
                            percent = (1.0F - percent);
                        }

                        hitA = hitA * percent;
                    }

                    float hitR = (float)(hitColor >> 16 & 255) / 255.0F;
                    float hitG = (float)(hitColor >> 8 & 255) / 255.0F;
                    float hitB = (float)(hitColor & 255) / 255.0F;
                    if (p_76986_1_.hurtTime > 0 || p_76986_1_.deathTime > 0) {
                        if (hitColorModule.isEnabled()) {
                            GL11.glColor4f(var27 * hitR, var27 * hitG, var27 * hitB, hitA);
                        } else {
                            GL11.glColor4f(var27, 0.0F, 0.0F, 0.4F);
                        }
                        this.mainModel.render(p_76986_1_, var16, var15, var13, var11 - var10, var26, var14);
                        if ((Boolean)hitColorModule.affectArmor.getValue() || !hitColorModule.isEnabled()) {
                            for (int var28 = 0; var28 < 4; ++var28)
                            {
                                if (this.inheritRenderPass(p_76986_1_, var28, p_76986_9_) >= 0) {
                                    if (hitColorModule.isEnabled()) {
                                        GL11.glColor4f(var27 * hitR, var27 * hitG, var27 * hitB, hitA);
                                    } else {
                                        GL11.glColor4f(var27, 0.0F, 0.0F, 0.4F);
                                    }
                                    this.renderPassModel.render(p_76986_1_, var16, var15, var13, var11 - var10, var26, var14);
                                }
                            }
                        }
                    }

                    if ((var18 >> 24 & 255) > 0) {
                        var19 = (float)(var18 >> 16 & 255) / 255.0F;
                        var20 = (float)(var18 >> 8 & 255) / 255.0F;
                        float var29 = (float)(var18 & 255) / 255.0F;
                        var22 = (float)(var18 >> 24 & 255) / 255.0F;
                        GL11.glColor4f(var19, var20, var29, var22);
                        this.mainModel.render(p_76986_1_, var16, var15, var13, var11 - var10, var26, var14);

                        for (int var30 = 0; var30 < 4; ++var30) {
                            if (this.inheritRenderPass(p_76986_1_, var30, p_76986_9_) >= 0)
                            {
                                GL11.glColor4f(var19, var20, var29, var22);
                                this.renderPassModel.render(p_76986_1_, var16, var15, var13, var11 - var10, var26, var14);
                            }
                        }
                    }
                }


                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        } catch (Exception var25) {
            logger.error("Couldn't render entity", var25);
        }

        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        this.passSpecialRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_);
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
        this.bindEntityTexture(p_77036_1_);

        if (!p_77036_1_.isInvisible()) {
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        } else if (!p_77036_1_.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
        } else {
            this.mainModel.setRotationAngles(p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_, p_77036_1_);
        }
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(EntityLivingBase p_77039_1_, double p_77039_2_, double p_77039_4_, double p_77039_6_) {
        GL11.glTranslatef((float)p_77039_2_, (float)p_77039_4_, (float)p_77039_6_);
    }

    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        GL11.glRotatef(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);

        if (p_77043_1_.deathTime > 0) {
            float var5 = ((float)p_77043_1_.deathTime + p_77043_4_ - 1.0F) / 20.0F * 1.6F;
            var5 = MathHelper.sqrt_float(var5);

            if (var5 > 1.0F) {
                var5 = 1.0F;
            }

            GL11.glRotatef(var5 * this.getDeathMaxRotation(p_77043_1_), 0.0F, 0.0F, 1.0F);
        } else {
            String var6 = EnumChatFormatting.getTextWithoutFormattingCodes(p_77043_1_.getCommandSenderName());

            if ((var6.equals("Dinnerbone") || var6.equals("Grumm")) && (!(p_77043_1_ instanceof EntityPlayer) || !((EntityPlayer)p_77043_1_).getHideCape())) {
                GL11.glTranslatef(0.0F, p_77043_1_.height + 0.1F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            }
        }
    }

    protected float renderSwingProgress(EntityLivingBase p_77040_1_, float p_77040_2_) {
        return p_77040_1_.getSwingProgress(p_77040_2_);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLivingBase p_77044_1_, float p_77044_2_) {
        return (float)p_77044_1_.ticksExisted + p_77044_2_;
    }

    protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_) {}

    /**
     * renders arrows the Entity has been attacked with, attached to it
     */
    protected void renderArrowsStuckInEntity(EntityLivingBase p_85093_1_, float p_85093_2_) {
        if (CheatBreaker.getInstance().getGlobalSettings().hideStuckArrows.getBooleanValue()) return;
        int var3 = p_85093_1_.getArrowCountInEntity();

        if (var3 > 0) {
            EntityArrow var4 = new EntityArrow(p_85093_1_.worldObj, p_85093_1_.posX, p_85093_1_.posY, p_85093_1_.posZ);
            Random var5 = new Random(p_85093_1_.getEntityId());
//            RenderHelper.disableStandardItemLighting();

            for (int var6 = 0; var6 < var3; ++var6) {
                GL11.glPushMatrix();
                ModelRenderer var7 = this.mainModel.getRandomModelBox(var5);
                ModelBox var8 = (ModelBox)var7.cubeList.get(var5.nextInt(var7.cubeList.size()));
                var7.postRender(0.0625F);
                float var9 = var5.nextFloat();
                float var10 = var5.nextFloat();
                float var11 = var5.nextFloat();
                float var12 = (var8.posX1 + (var8.posX2 - var8.posX1) * var9) / 16.0F;
                float var13 = (var8.posY1 + (var8.posY2 - var8.posY1) * var10) / 16.0F;
                float var14 = (var8.posZ1 + (var8.posZ2 - var8.posZ1) * var11) / 16.0F;
                GL11.glTranslatef(var12, var13, var14);
                var9 = var9 * 2.0F - 1.0F;
                var10 = var10 * 2.0F - 1.0F;
                var11 = var11 * 2.0F - 1.0F;
                var9 *= -1.0F;
                var10 *= -1.0F;
                var11 *= -1.0F;
                float var15 = MathHelper.sqrt_float(var9 * var9 + var11 * var11);
                var4.prevRotationYaw = var4.rotationYaw = (float)(Math.atan2(var9, var11) * 180.0D / Math.PI);
                var4.prevRotationPitch = var4.rotationPitch = (float)(Math.atan2(var10, var15) * 180.0D / Math.PI);
                double var16 = 0.0D;
                double var18 = 0.0D;
                double var20 = 0.0D;
                float var22 = 0.0F;
                this.renderManager.func_147940_a(var4, var16, var18, var20, var22, p_85093_2_);
                GL11.glPopMatrix();
            }

//            RenderHelper.enableStandardItemLighting();
        }
    }

    protected int inheritRenderPass(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_) {
        return this.shouldRenderPass(p_77035_1_, p_77035_2_, p_77035_3_);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    public int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return -1;
    }

    protected void func_82408_c(EntityLivingBase p_82408_1_, int p_82408_2_, float p_82408_3_) {}

    protected float getDeathMaxRotation(EntityLivingBase p_77037_1_) {
        return 90.0F;
    }

    /**
     * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
     */
    protected int getColorMultiplier(EntityLivingBase p_77030_1_, float p_77030_2_, float p_77030_3_) {
        return 0;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    public void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) {}

    /**
     * Passes the specialRender and renders it
     */
    protected void passSpecialRender(EntityLivingBase p_77033_1_, double p_77033_2_, double p_77033_4_, double p_77033_6_) {
        ModuleNametag nametagMod = CheatBreaker.getInstance().getModuleManager().nametagMod;
        boolean modEnabled = nametagMod.isEnabled();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

        if (this.func_110813_b(p_77033_1_) || (p_77033_1_.equals(Minecraft.getMinecraft().thePlayer) && modEnabled && (Boolean) nametagMod.showOwnNametag.getValue())) {
            float var8 = 1.6F;
            float var9 = 0.016666668F * var8;
            double var10 = p_77033_1_.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float var12 = p_77033_1_.isSneaking() ? 32.0F : 64.0F;

            if (!Minecraft.getMinecraft().hideNametags && var10 < (double)(var12 * var12)) {
                String var13 = p_77033_1_.func_145748_c_().getFormattedText();

                if (p_77033_1_.isSneaking()) {
                    FontRenderer var14 = this.getFontRendererFromRenderManager();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)p_77033_2_ + 0.0F, (float)p_77033_4_ + p_77033_1_.height + 0.5F, (float)p_77033_6_);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                    GL11.glScalef(-var9, -var9, var9);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glTranslatef(0.0F, 0.25F / var9, 0.0F);
                    GL11.glDepthMask(false);
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    Tessellator var15 = Tessellator.instance;
                    GL11.glDisable(GL11.GL_TEXTURE_2D);

                    boolean customTextColor = (Boolean) nametagMod.customTextColor.getValue() && modEnabled;
                    String string = customTextColor ? stripColor(var13) : var13;

                    ModuleNickHider nickHider = CheatBreaker.getInstance().getModuleManager().nickHiderMod;
                    if (nickHider.isEnabled() && nickHider.hideRealName.getBooleanValue()) {
                        if (!nickHider.customNameString.getStringValue().equals(Minecraft.getMinecraft().getSession().getUsername())) {
                            string = string.replaceAll(Minecraft.getMinecraft().getSession().getUsername(), nickHider.customNameString.getStringValue());
                        } else {
                            string = string.replaceAll(Minecraft.getMinecraft().getSession().getUsername(), "You");
                        }
                    }

                    int var16 = var14.getStringWidth(string) / 2;
                    if (!modEnabled || (Boolean) nametagMod.background.getValue()) {
                        var15.startDrawingQuads();
                        if (modEnabled) {
                            float alpha = (nametagMod.backgroundColor.getColorValue() >> 24 & 255) / 255.0F;
                            float red = (nametagMod.backgroundColor.getColorValue() >> 16 & 255) / 255.0F;
                            float green = (nametagMod.backgroundColor.getColorValue() >> 8 & 255) / 255.0F;
                            float blue = (nametagMod.backgroundColor.getColorValue() & 255) / 255.0F;
                            var15.setColorRGBA_F(red, green, blue, alpha);
                        } else {
                            var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                        }
                        var15.addVertex(-var16 - 1, -1, 0.0D);
                        var15.addVertex(-var16 - 1, 8, 0.0D);
                        var15.addVertex(var16 + 1, 8, 0.0D);
                        var15.addVertex(var16 + 1, -1, 0.0D);
                        var15.draw();
                    }
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDepthMask(true);

                    var14.drawString(string, -(var14.getStringWidth(string) / 2), 0, customTextColor ? new Color(nametagMod.textColor.getColorValue() >> 16 & 255, nametagMod.textColor.getColorValue() >> 8 & 255, nametagMod.textColor.getColorValue() & 255, 32).getRGB() : 553648127, modEnabled && (Boolean) nametagMod.textShadow.getValue());
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                } else {
                    this.func_96449_a(p_77033_1_, p_77033_2_, p_77033_4_, p_77033_6_, var13, var9, var10);
                }
            }
        }
    }

    protected boolean func_110813_b(EntityLivingBase p_110813_1_) {
        ModuleNametag nametagMod = CheatBreaker.getInstance().getModuleManager().nametagMod;
        boolean modEnabled = nametagMod.isEnabled();
        boolean showTagsF1 = modEnabled && (Boolean) nametagMod.showNametagsInF1.getValue();
        return (Minecraft.isGuiEnabled() || showTagsF1) && p_110813_1_ != this.renderManager.livingPlayer && !p_110813_1_.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) && p_110813_1_.riddenByEntity == null;
    }

    protected void func_96449_a(EntityLivingBase p_96449_1_, double p_96449_2_, double p_96449_4_, double p_96449_6_, String p_96449_8_, float p_96449_9_, double p_96449_10_) {
        if (p_96449_1_.isPlayerSleeping()) {
            this.func_147906_a(p_96449_1_, p_96449_8_, p_96449_2_, p_96449_4_ - 1.5D, p_96449_6_, 64);
        } else {
            this.func_147906_a(p_96449_1_, p_96449_8_, p_96449_2_, p_96449_4_, p_96449_6_, 64);
        }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityLivingBase)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    private String stripColor(String input) {
        return input == null ? null : this.STRIP_COLOR_PATTERN.matcher(input).replaceAll("§r");
    }
}
