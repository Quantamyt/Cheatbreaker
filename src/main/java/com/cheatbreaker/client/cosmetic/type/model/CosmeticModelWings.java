package com.cheatbreaker.client.cosmetic.type.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CosmeticModelWings extends ModelBase {
    private ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
    private ModelRenderer wing;
    private ModelRenderer wingTip;

    public CosmeticModelWings(float f) {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.setTextureOffset("wing.skin", -56, 88);
        this.setTextureOffset("wingtip.skin", -56, 144);
        this.setTextureOffset("wing.bone", 112, 88);
        this.setTextureOffset("wingtip.bone", 112, 136);
        this.wing = new ModelRenderer(this, "wing");
        this.wing.setRotationPoint(-12, 5, 2.0f);
        this.wing.addBox("bone", (float)-56, (float)-4, (float)-4, 56, 8, 8);
        this.wing.addBox("skin", (float)-56, 0.0f, 2.0f, 56, 0, 56);
        this.wingTip = new ModelRenderer(this, "wingtip");
        this.wingTip.setRotationPoint(-56, 0.0f, 0.0f);
        this.wingTip.addBox("bone", (float)-56, (float)-2, (float)-2, 56, 4, 4);
        this.wingTip.addBox("skin", (float)-56, 0.0f, 2.0f, 56, 0, 56);
        this.wing.addChild(this.wingTip);
    }

    public void render(Entity entity, float f, float f2, float f3, float f4, float f5, float f6, float f7, ResourceLocation resourceLocation) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer entityPlayer = (EntityPlayer)entity;
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glPushMatrix();
        GL11.glScaled(f7, f7, f7);
        GL11.glRotatef(15, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.5f, 0.25f);
        float f8 = (float)(System.currentTimeMillis() % 2000L) / 2000.0f * (float)Math.PI * 2.0f;
        for (int i = 0; i < 2; ++i) {
            GL11.glEnable(2884);
            this.wing.rotateAngleX = 1.1f * -0.11363636f - (float)Math.cos(f8) * (0.175f * 1.1428572f);
            this.wing.rotateAngleY = 1.069853f * 0.7010309f;
            this.wing.rotateAngleZ = (float)(Math.sin(f8) + 0.3577586111305871 * (double)0.3493976f) * (0.8f * 1.0f);
            this.wingTip.rotateAngleZ = (float)(Math.sin(f8 + 2.0f) + 0.9117646813392639 * 0.5483871115358021) * (2.2258065f * 0.33695653f);
            this.wing.render(f6);
            GL11.glScalef(-1, 1.0f, 1.0f);
            if (i != 0) continue;
            GL11.glCullFace(1028);
        }
        GL11.glPopMatrix();
        GL11.glCullFace(1029);
        GL11.glDisable(2884);
    }
}
