package net.minecraft.client.renderer.entity;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.impl.normal.ModulePackTweaks;
import com.cheatbreaker.client.module.impl.normal.misc.ModuleNickHider;
import com.cheatbreaker.client.module.impl.normal.vanilla.ModuleNametag;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.cosmetic.profile.ProfileHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import shadersmod.client.Shaders;

import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

public abstract class Render {
    private static final ResourceLocation shadowTextures = new ResourceLocation("textures/misc/shadow.png");
    protected RenderManager renderManager;
    protected RenderBlocks field_147909_c = new RenderBlocks();
    protected float shadowSize;

    /**
     * Determines the darkness of the object's shadow. Higher value makes a darker shadow.
     */
    protected float shadowOpaque = 1.0F;
    private boolean field_147908_f = false;

    private final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-689A-E]");

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public abstract void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_);

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public abstract ResourceLocation getEntityTexture(Entity p_110775_1_);

    public boolean func_147905_a() {
        return this.field_147908_f;
    }

    protected void bindEntityTexture(Entity par1Entity) {
        this.bindTexture(this.getEntityTexture(par1Entity));
    }

    protected void bindTexture(ResourceLocation par1ResourceLocation) {
        this.renderManager.renderEngine.bindTexture(par1ResourceLocation);
    }

    /**
     * Renders fire on top of the entity. Args: entity, x, y, z, partialTickTime
     */
    private void renderEntityOnFire(Entity par1Entity, double par2, double par4, double par6, float par8) {
        GL11.glDisable(GL11.GL_LIGHTING);
        IIcon var9 = Blocks.fire.func_149840_c(0);
        IIcon var10 = Blocks.fire.func_149840_c(1);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        float var11 = par1Entity.width * 1.4F;
        GL11.glScalef(var11, var11, var11);
        Tessellator var12 = Tessellator.instance;
        float var13 = 0.5F;
        float var14 = 0.0F;
        float var15 = par1Entity.height / var11;
        float var16 = (float)(par1Entity.posY - par1Entity.boundingBox.minY);
        GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.0F, -0.3F + (float)((int)var15) * 0.02F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var17 = 0.0F;
        int var18 = 0;
        var12.startDrawingQuads();

        while (var15 > 0.0F) {
            IIcon var19 = var18 % 2 == 0 ? var9 : var10;
            this.bindTexture(TextureMap.locationBlocksTexture);
            float var20 = var19.getMinU();
            float var21 = var19.getMinV();
            float var22 = var19.getMaxU();
            float var23 = var19.getMaxV();

            if (var18 / 2 % 2 == 0) {
                float var24 = var22;
                var22 = var20;
                var20 = var24;
            }

            var12.addVertexWithUV((double)(var13 - var14), (double)(0.0F - var16), (double)var17, (double)var22, (double)var23);
            var12.addVertexWithUV((double)(-var13 - var14), (double)(0.0F - var16), (double)var17, (double)var20, (double)var23);
            var12.addVertexWithUV((double)(-var13 - var14), (double)(1.4F - var16), (double)var17, (double)var20, (double)var21);
            var12.addVertexWithUV((double)(var13 - var14), (double)(1.4F - var16), (double)var17, (double)var22, (double)var21);
            var15 -= 0.45F;
            var16 -= 0.45F;
            var13 *= 0.9F;
            var17 += 0.03F;
            ++var18;
        }

        var12.draw();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    /**
     * Renders the entity shadows at the position, shadow alpha and partialTickTime. Args: entity, x, y, z, shadowAlpha,
     * partialTickTime
     */
    private void renderShadow(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.renderManager.renderEngine.bindTexture(shadowTextures);
            World var10 = this.getWorldFromRenderManager();
            GL11.glDepthMask(false);
            float var11 = this.shadowSize;

            if (par1Entity instanceof EntityLiving) {
                EntityLiving var35 = (EntityLiving)par1Entity;
                var11 *= var35.getRenderSizeModifier();

                if (var35.isChild()) {
                    var11 *= 0.5F;
                }
            }

            double var351 = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * (double)par9;
            double var14 = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * (double)par9 + (double)par1Entity.getShadowSize();
            double var16 = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * (double)par9;
            int var18 = MathHelper.floor_double(var351 - (double)var11);
            int var19 = MathHelper.floor_double(var351 + (double)var11);
            int var20 = MathHelper.floor_double(var14 - (double)var11);
            int var21 = MathHelper.floor_double(var14);
            int var22 = MathHelper.floor_double(var16 - (double)var11);
            int var23 = MathHelper.floor_double(var16 + (double)var11);
            double var24 = par2 - var351;
            double var26 = par4 - var14;
            double var28 = par6 - var16;
            Tessellator var30 = Tessellator.instance;
            var30.startDrawingQuads();

            for (int var31 = var18; var31 <= var19; ++var31) {
                for (int var32 = var20; var32 <= var21; ++var32) {
                    for (int var33 = var22; var33 <= var23; ++var33) {
                        Block var34 = var10.getBlock(var31, var32 - 1, var33);

                        if (var34.getMaterial() != Material.air && var10.getBlockLightValue(var31, var32, var33) > 3) {
                            this.func_147907_a(var34, par2, par4 + (double)par1Entity.getShadowSize(), par6, var31, var32, var33, par8, var11, var24, var26 + (double)par1Entity.getShadowSize(), var28);
                        }
                    }
                }
            }

            var30.draw();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
        }
    }

    /**
     * Returns the render manager's world object
     */
    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
    }

    private void func_147907_a(Block p_147907_1_, double p_147907_2_, double p_147907_4_, double p_147907_6_, int p_147907_8_, int p_147907_9_, int p_147907_10_, float p_147907_11_, float p_147907_12_, double p_147907_13_, double p_147907_15_, double p_147907_17_) {
        Tessellator var19 = Tessellator.instance;

        if (p_147907_1_.renderAsNormalBlock()) {
            double var20 = ((double)p_147907_11_ - (p_147907_4_ - ((double)p_147907_9_ + p_147907_15_)) / 2.0D) * 0.5D * (double)this.getWorldFromRenderManager().getLightBrightness(p_147907_8_, p_147907_9_, p_147907_10_);

            if (var20 >= 0.0D) {
                if (var20 > 1.0D) {
                    var20 = 1.0D;
                }

                var19.setColorRGBA_F(1.0F, 1.0F, 1.0F, (float)var20);
                double var22 = (double)p_147907_8_ + p_147907_1_.getBlockBoundsMinX() + p_147907_13_;
                double var24 = (double)p_147907_8_ + p_147907_1_.getBlockBoundsMaxX() + p_147907_13_;
                double var26 = (double)p_147907_9_ + p_147907_1_.getBlockBoundsMinY() + p_147907_15_ + 0.015625D;
                double var28 = (double)p_147907_10_ + p_147907_1_.getBlockBoundsMinZ() + p_147907_17_;
                double var30 = (double)p_147907_10_ + p_147907_1_.getBlockBoundsMaxZ() + p_147907_17_;
                float var32 = (float)((p_147907_2_ - var22) / 2.0D / (double)p_147907_12_ + 0.5D);
                float var33 = (float)((p_147907_2_ - var24) / 2.0D / (double)p_147907_12_ + 0.5D);
                float var34 = (float)((p_147907_6_ - var28) / 2.0D / (double)p_147907_12_ + 0.5D);
                float var35 = (float)((p_147907_6_ - var30) / 2.0D / (double)p_147907_12_ + 0.5D);
                var19.addVertexWithUV(var22, var26, var28, (double)var32, (double)var34);
                var19.addVertexWithUV(var22, var26, var30, (double)var32, (double)var35);
                var19.addVertexWithUV(var24, var26, var30, (double)var33, (double)var35);
                var19.addVertexWithUV(var24, var26, var28, (double)var33, (double)var34);
            }
        }
    }

    /**
     * Renders a white box with the bounds of the AABB translated by the offset. Args: aabb, x, y, z
     */
    public static void renderOffsetAABB(AxisAlignedBB par0AxisAlignedBB, double par1, double par3, double par5) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator var7 = Tessellator.instance;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        var7.startDrawingQuads();
        var7.setTranslation(par1, par3, par5);
        var7.setNormal(0.0F, 0.0F, -1.0F);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.setNormal(0.0F, 0.0F, 1.0F);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.setNormal(0.0F, -1.0F, 0.0F);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.setNormal(0.0F, 1.0F, 0.0F);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.setNormal(-1.0F, 0.0F, 0.0F);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.setNormal(1.0F, 0.0F, 0.0F);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.setTranslation(0.0D, 0.0D, 0.0D);
        var7.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    /**
     * Adds to the tesselator a box using the aabb for the bounds. Args: aabb
     */
    public static void renderAABB(AxisAlignedBB par0AxisAlignedBB) {
        Tessellator var1 = Tessellator.instance;
        var1.startDrawingQuads();
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.draw();
    }

    /**
     * Sets the RenderManager.
     */
    public void setRenderManager(RenderManager par1RenderManager) {
        this.renderManager = par1RenderManager;
    }

    /**
     * Renders the entity's shadow and fire (if its on fire). Args: entity, x, y, z, yaw, partialTickTime
     */
    public void doRenderShadowAndFire(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        if (/*this.renderManager.options.fancyGraphics && */this.shadowSize > 0.0F && !par1Entity.isInvisible() && (Boolean) CheatBreaker.getInstance().getGlobalSettings().entityShadows.getValue()) {
            double var10 = this.renderManager.getDistanceToCamera(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
            float var12 = (float)((1.0D - var10 / 256.0D) * (double)this.shadowOpaque);

            if (var12 > 0.0F)
            {
                this.renderShadow(par1Entity, par2, par4, par6, var12, par9);
            }
        }

        ModulePackTweaks pt = CheatBreaker.getInstance().getModuleManager().packTweaksMod;
        boolean ptIsEnabled = pt.isEnabled();
        boolean renderOverlay = ptIsEnabled ? (Boolean) pt.fireOnEntities.getValue() : true;
        if (par1Entity.canRenderOnFire() && renderOverlay) {
            this.renderEntityOnFire(par1Entity, par2, par4, par6, par9);
        }
    }

    /**
     * Returns the font renderer from the set render manager
     */
    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }

    public void updateIcons(IIconRegister par1IconRegister) {}

    protected void func_147906_a(Entity entity, String string, double d, double d2, double d3, int n) {
        double var10 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);

        if (var10 <= (n * n)) {
            if (CheatBreaker.getInstance().getCbNetHandler().getNametagsMap().containsKey(entity.getUniqueID())) {
                List<String> list = CheatBreaker.getInstance().getCbNetHandler().getNametagsMap().get(entity.getUniqueID());
                int n2 = 0;
                for (String string2 : list) {
                    this.boogaloo(-n2, entity, string2, d, d2, d3, n);
                    ++n2;
                }
            } else boogaloo(0.0, entity, string, d, d2, d3, n);
        }
    }

    public void boogaloo(double d, Entity p_147906_1_, String p_147906_2_, double p_147906_3_, double p_147906_5_, double p_147906_7_, int p_147906_9_) {
        ModuleNametag mod = CheatBreaker.getInstance().getModuleManager().nametagMod;
        ProfileHandler profileHandler = CheatBreaker.getInstance().getProfileHandler();
        boolean modEnabled = mod.isEnabled();
        boolean showIcon = modEnabled && (Boolean) mod.showCheatBreakerLogo.getValue() && mod.meetsIconRequirements(p_147906_2_) && profileHandler.validate(p_147906_1_.getUniqueID().toString(), false) && profileHandler.validate(p_147906_2_, true);
        int color = profileHandler.getProfile(p_147906_1_.getUniqueID().toString()) == null ? 0 : profileHandler.getProfile(p_147906_1_.getUniqueID().toString()).getColor();
        int color2 = profileHandler.getProfile(p_147906_1_.getUniqueID().toString()) == null ? 0 : profileHandler.getProfile(p_147906_1_.getUniqueID().toString()).getColor2();

        float colorR = (float)(color >> 16 & 255) / 255.0F;
        float colorG = (float)(color >> 8 & 255) / 255.0F;
        float colorB = (float)(color & 255) / 255.0F;

        float color2R = (float)(color2 >> 16 & 255) / 255.0F;
        float color2G = (float)(color2 >> 8 & 255) / 255.0F;
        float color2B = (float)(color2 & 255) / 255.0F;

        double var10 = p_147906_1_.getDistanceSqToEntity(this.renderManager.livingPlayer);

        if (var10 <= (double) (p_147906_9_ * p_147906_9_)) {
            FontRenderer var12 = this.getFontRendererFromRenderManager();
            float var13 = 1.6F;
            float var14 = 0.016666668F * var13;
            GL11.glPushMatrix();
//            GL11.glTranslatef((float) p_147906_3_ + 0.0F, (float) p_147906_5_ + p_147906_1_.height + 0.5F, (float) p_147906_7_);
            GL11.glTranslatef((float)p_147906_3_ + 0.0f, (float)(p_147906_5_ + p_147906_1_.height + 0.5F - d / 3), (float)p_147906_7_);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-var14, -var14, var14);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            Tessellator var15 = Tessellator.instance;
            byte var16 = 0;
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            boolean customTextColor = (Boolean) mod.customTextColor.getValue() && modEnabled;
            String string = customTextColor ? stripColor(p_147906_2_) : p_147906_2_;

            ModuleNickHider nickHider = CheatBreaker.getInstance().getModuleManager().nickHiderMod;
            if (nickHider.isEnabled() && nickHider.hideRealName.getBooleanValue()) {
                if (!nickHider.customNameString.getStringValue().equals(Minecraft.getMinecraft().getSession().getUsername())) {
                    string = string.replaceAll(Minecraft.getMinecraft().getSession().getUsername(), nickHider.customNameString.getStringValue());
                } else {
                    string = string.replaceAll(Minecraft.getMinecraft().getSession().getUsername(), "You");
                }
            }

            int var17 = var12.getStringWidth(string) / 2;
            int toAdd = (showIcon ? string.contains(" ") ? 8 : 7 : 0);
            var17 = var17 + toAdd;

            if (!modEnabled || (Boolean) mod.background.getValue()) {
                var15.startDrawingQuads();
                if (modEnabled) {
                    float alpha = (mod.backgroundColor.getColorValue() >> 24 & 255) / 255.0F;
                    float red = (mod.backgroundColor.getColorValue() >> 16 & 255) / 255.0F;
                    float green = (mod.backgroundColor.getColorValue() >> 8 & 255) / 255.0F;
                    float blue = (mod.backgroundColor.getColorValue() & 255) / 255.0F;
                    var15.setColorRGBA_F(red, green, blue, alpha);
                } else {
                    var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                }
                var15.addVertex((-var17 - 1), (-1 + var16), 0.0D);
                var15.addVertex((-var17 - 1), (8 + var16), 0.0D);
                var15.addVertex((var17 + 1), (8 + var16), 0.0D);
                var15.addVertex((var17 + 1), (-1 + var16), 0.0D);
                var15.draw();
            }
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            var12.drawString(string, -(var12.getStringWidth(string) / 2 - toAdd), var16, customTextColor ? new Color(mod.textColor.getColorValue() >> 16 & 255, mod.textColor.getColorValue() >> 8 & 255, mod.textColor.getColorValue() & 255, 32).getRGB() : 553648127, modEnabled && (Boolean) mod.textShadow.getValue());
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            if (showIcon) {
                if (mod.textShadow.getBooleanValue()) {
                    GL11.glColor4f(colorR / 4.0F, colorG / 4.0F, colorB / 4.0F, 1.0f); // Change for WS. DIVIDED BY 4.0F IS REQUIRED
                    RenderUtil.renderIcon(new ResourceLocation("client/logo_white_1.png"), (float)(-(var17 * 2) + 12) / 2.0f - 5.0f, 1.0F, 13.0f, 7.0f);
                    GL11.glColor4f(color2R / 4.0F, color2G / 4.0F, color2B / 4.0F, 1.0f); // Change for WS. DIVIDED BY 4.0F IS REQUIRED
                    RenderUtil.renderIcon(new ResourceLocation("client/logo_white_2.png"), (float)(-(var17 * 2) + 12) / 2.0f - 5.0f, 1.0F, 13.0f, 7.0f);
                }
                GL11.glColor4f(colorR, colorG, colorB, 1.0f); // Change for WS
                RenderUtil.renderIcon(new ResourceLocation("client/logo_white_1.png"), (float)(-(var17 * 2) + 12) / 2.0f - 6.0f, 0.0F, 13.0f, 7.0f);
                GL11.glColor4f(color2R, color2G, color2B, 1.0f); // Change for WS
                RenderUtil.renderIcon(new ResourceLocation("client/logo_white_2.png"), (float)(-(var17 * 2) + 12) / 2.0f - 6.0f, 0.0F, 13.0f, 7.0f);
            }
            var12.drawString(string, -(var12.getStringWidth(string) / 2 - toAdd), var16, customTextColor ? mod.textColor.getColorValue() : -1, modEnabled && (Boolean) mod.textShadow.getValue());
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }

    private String stripColor(String input) {
        return input == null ? null : this.STRIP_COLOR_PATTERN.matcher(input).replaceAll("§r");
    }
}
