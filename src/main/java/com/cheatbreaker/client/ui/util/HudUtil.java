package com.cheatbreaker.client.ui.util;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomColors;
import org.lwjgl.opengl.GL11;

public final class HudUtil {
    private static final int[] colorCodes = new int[]{0, 170, 43520, 43690, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF, 0, 42, 10752, 10794, 0x2A0000, 0x2A002A, 0x2A2A00, 0x2A2A2A, 0x151515, 1381695, 1392405, 1392447, 4134165, 4134207, 4144917, 0x3F3F3F};

    public static int getColorCode(char c, boolean bl) {
        return colorCodes[bl ? "0123456789abcdef".indexOf(c) : "0123456789abcdef".indexOf(c) + 16];
    }

    public static void drawContinuousTexturedBox(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, float f) {
        HudUtil.drawContinuousTexturedBox(n, n2, n3, n4, n5, n6, n7, n8, n9, n9, n9, n9, f);
    }

    public static void drawContinuousTexturedBox(ResourceLocation resourceLocation, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, float f) {
        HudUtil.drawContinuousTexturedBox(resourceLocation, n, n2, n3, n4, n5, n6, n7, n8, n9, n9, n9, n9, f);
    }

    public static void drawContinuousTexturedBox(ResourceLocation resourceLocation, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12, float f) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        HudUtil.drawContinuousTexturedBox(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, f);
    }

    public static void drawContinuousTexturedBox(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12, float f) {
        int n13;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(770, 771);
        int n14 = n7 - n11 - n12;
        int n15 = n8 - n9 - n10;
        int n16 = n5 - n11 - n12;
        int n17 = n6 - n9 - n10;
        int n18 = n16 / n14;
        int n19 = n16 % n14;
        int n20 = n17 / n15;
        int n21 = n17 % n15;
        HudUtil.drawTexturedModalRect(n, n2, n3, n4, n11, n9, f);
        HudUtil.drawTexturedModalRect(n + n11 + n16, n2, n3 + n11 + n14, n4, n12, n9, f);
        HudUtil.drawTexturedModalRect(n, n2 + n9 + n17, n3, n4 + n9 + n15, n11, n10, f);
        HudUtil.drawTexturedModalRect(n + n11 + n16, n2 + n9 + n17, n3 + n11 + n14, n4 + n9 + n15, n12, n10, f);
        for (n13 = 0; n13 < n18 + (n19 > 0 ? 1 : 0); ++n13) {
            HudUtil.drawTexturedModalRect(n + n11 + n13 * n14, n2, n3 + n11, n4, n13 == n18 ? n19 : n14, n9, f);
            HudUtil.drawTexturedModalRect(n + n11 + n13 * n14, n2 + n9 + n17, n3 + n11, n4 + n9 + n15, n13 == n18 ? n19 : n14, n10, f);
            for (int i = 0; i < n20 + (n21 > 0 ? 1 : 0); ++i) {
                HudUtil.drawTexturedModalRect(n + n11 + n13 * n14, n2 + n9 + i * n15, n3 + n11, n4 + n9, n13 == n18 ? n19 : n14, i == n20 ? n21 : n15, f);
            }
        }
        for (n13 = 0; n13 < n20 + (n21 > 0 ? 1 : 0); ++n13) {
            HudUtil.drawTexturedModalRect(n, n2 + n9 + n13 * n15, n3, n4 + n9, n11, n13 == n20 ? n21 : n15, f);
            HudUtil.drawTexturedModalRect(n + n11 + n16, n2 + n9 + n13 * n15, n3 + n11 + n14, n4 + n9, n12, n13 == n20 ? n21 : n15, f);
        }
    }

    public static void drawTexturedModalRect(int n, int n2, int n3, int n4, int n5, int n6, float f) {
        float f2 = 1.1355932f * 0.0034398322f;
        float f3 = 0.0015345983f * 2.5454545f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(n + 0, n2 + n6, f).tex((float) (n3 + 0) * f2, (float) (n4 + n6) * f3).endVertex();
        worldrenderer.pos(n + n5, n2 + n6, f).tex((float) (n3 + n5) * f2, (float) (n4 + n6) * f3).endVertex();
        worldrenderer.pos(n + n5, n2 + 0, f).tex((float) (n3 + n5) * f2, (float) (n4 + 0) * f3).endVertex();
        worldrenderer.pos(n + 0, n2 + 0, f).tex((float) (n3 + 0) * f2, (float) (n4 + 0) * f3).endVertex();
        tessellator.draw();
    }

    public static void renderItemOverlayIntoGUI(FontRenderer fontRenderer, ItemStack itemStack, int n, int n2) {
        renderItemOverlayIntoGUI(fontRenderer, itemStack, n, n2, true, true);
    }

    public static void renderItemOverlayIntoGUI(FontRenderer fontRenderer, ItemStack itemStack, int n, int n2, boolean bl, boolean bl2) {
        if (itemStack != null && (bl || bl2)) {
            int n3;
            if (itemStack.isItemDamaged() && bl) {
                n3 = (int) Math.round((double) 13 - (double) itemStack.getItemDamage() * (double) 13 / (double) itemStack.getMaxDamage());
                int n4 = (int) Math.round((double) 255 - (double) itemStack.getItemDamage() * (double) 255 / (double) itemStack.getMaxDamage());
                GlStateManager.disableLighting();
                GlStateManager.disableBlend();
                GlStateManager.disableTexture2D();
                Tessellator tessellator = Tessellator.getInstance();
                int n5 = 255 - n4 << 16 | n4 << 8;
                int n6 = (255 - n4) / 4 << 16 | 0x3F00;
                HudUtil.renderQuad(tessellator, n + 2, n2 + 13, 13, 2, 0, 0, 0, 255);
                HudUtil.renderQuad(tessellator, n + 2, n2 + 13, 12, 1, (255 - n4) / 4, 64, 0, 255);
                int j = 255 - n4;
                int k = n4;
                int l = 0;

                if (Config.isCustomColors()) {
                    int i1 = CustomColors.getDurabilityColor(n4);

                    if (i1 >= 0) {
                        j = i1 >> 16 & 255;
                        k = i1 >> 8 & 255;
                        l = i1 >> 0 & 255;
                    }
                }

                HudUtil.renderQuad(tessellator, n + 2, n2 + 13, n3, 1, j, k, l, 255);
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableBlend();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
            if (bl2) {
                n3 = 0;
                if (itemStack.getMaxStackSize() > 1) {
                    n3 = HudUtil.countInInventory(Minecraft.getMinecraft().thePlayer, itemStack.getItem(), itemStack.getItemDamage());
                } else if (itemStack.getItem().equals(Items.bow)) {
                    n3 = HudUtil.countInInventory(Minecraft.getMinecraft().thePlayer, Items.arrow);
                }
                if (n3 > 1) {
                    String string = "" + n3;
                    GlStateManager.disableLighting();
                    GlStateManager.disableBlend();
                    fontRenderer.drawStringWithShadow(string, (float) (n + 19 - 2 - fontRenderer.getStringWidth(string)), (float) (n2 + 6 + 3), 0xFFFFFF);
                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                }
            }
        }
    }

    public static void renderQuad(Tessellator tessellator, int n, int n2, int n3, int n4, int red, int green, int blue, int alpha) {
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(n + 0, n2 + 0, 0.0).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(n + 0, n2 + n4, 0.0).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(n + n3, n2 + n4, 0.0).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(n + n3, n2 + 0, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
    }

    public static int countInInventory(EntityPlayer entityPlayer, Item item) {
        return HudUtil.countInInventory(entityPlayer, item, -1);
    }

    public static int countInInventory(EntityPlayer entityPlayer, Item item, int n) {
        int n2 = 0;
        for (int i = 0; i < entityPlayer.inventory.mainInventory.length; ++i) {
            if (entityPlayer.inventory.mainInventory[i] == null || !item.equals(entityPlayer.inventory.mainInventory[i].getItem()) || n != -1 && entityPlayer.inventory.mainInventory[i].getItemDamage() != n)
                continue;
            n2 += entityPlayer.inventory.mainInventory[i].stackSize;
        }
        return n2;
    }

    public static String getColorCode(String string) {
        return string.replaceAll("(?i)§[0-9a-fklmnor]", "");
    }
}
