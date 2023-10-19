package com.cheatbreaker.client.util.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TestClassOne {
    private static final TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
    private final int lIIIIIIIIIlIllIIllIlIIlIl;
    private final ResourceLocation resourceLocation;

    public TestClassOne(int n, ResourceLocation resourceLocation) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.resourceLocation = resourceLocation;
    }

    public void lIIIIlIIllIIlIIlIIIlIIllI() {
        if (this.resourceLocation == null) {
            GL11.glDisable(3553);
        } else {
            renderEngine.bindTexture(this.resourceLocation);
        }
        GL11.glCallList(this.lIIIIIIIIIlIllIIllIlIIlIl);
        if (this.resourceLocation == null) {
            GL11.glEnable(3553);
        }
    }

    public void lIIIIIIIIIlIllIIllIlIIlIl() {
        GL11.glDeleteLists(this.lIIIIIIIIIlIllIIllIlIIlIl, 1);
    }
}
