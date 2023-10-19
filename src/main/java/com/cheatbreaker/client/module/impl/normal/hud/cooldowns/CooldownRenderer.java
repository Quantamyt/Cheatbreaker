package com.cheatbreaker.client.module.impl.normal.hud.cooldowns;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.armorstatus.ModuleArmorStatus;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class CooldownRenderer {
    private final String cooldownID;
    private final int itemID;
    private long duration;
    private long time;
    private final Minecraft mc = Minecraft.getMinecraft();
    private final ItemStack itemStack;

    public CooldownRenderer(String cooldownID, int itemID, long duration) {
        this.cooldownID = cooldownID;
        this.itemID = itemID;
        this.duration = duration;
        this.time = System.currentTimeMillis();
        this.itemStack = new ItemStack(Item.getItemById(itemID));
    }

    public void render(Setting theme, float x, float y, int color) {
        int radius = 17;
        GL11.glPushMatrix();
        float f4 = ModuleArmorStatus.renderItem.zLevel;
        ModuleArmorStatus.renderItem.zLevel = -50;
        float scale = 1.35F;
        GL11.glTranslatef(-0.5F, -1, 0.0f);
        GL11.glScalef(scale, scale, scale);
        RenderHelper.enableStandardItemLighting();
        ModuleArmorStatus.renderItem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), this.itemStack, (int)((x + (float)(radius / 2)) / scale), (int)((y + (float)(radius / 2)) / scale));
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
        ModuleArmorStatus.renderItem.zLevel = f4;
        double d = this.duration - (System.currentTimeMillis() - this.time);
        if (d <= 0.0) {
            return;
        }
        if (((String)theme.getValue()).equalsIgnoreCase("Bright")) {
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.2F);
            RenderUtil.drawCircleWithOutLine(x + (float)radius, y + (float)radius, radius, 0.0, (float)this.duration / 3.95f, (int)this.duration, d);
            GL11.glColor4f(0.9F, 0.9F, 0.9F, 1.0F);
            RenderUtil.drawCircleWithOutLine(x + (float)radius, y + (float)radius, (float)radius + 0.1f, radius - 2, (float)this.duration / 3.95f, (int)this.duration, this.duration);
            GL11.glColor4f(0.35F, 0.35F, 0.35F, 0.6F);
            RenderUtil.drawCircleWithOutLine(x + (float)radius, y + (float)radius, (float)radius + 0.1f, radius - 2, (float)this.duration / 3.95f, (int)this.duration, d);
        } else if (((String)theme.getValue()).equalsIgnoreCase("Dark")) {
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.2F);
            RenderUtil.drawCircle(x + (float)radius, y + (float)radius, radius);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.2F);
            RenderUtil.drawCircleWithOutLine(x + (float)radius, y + (float)radius, radius, 0.0, (float)this.duration / 3.95f, (int)this.duration, d);
            GL11.glColor4f(0.0F, 0.9F, 0.0F, 1.0F);
            RenderUtil.drawCircleWithOutLine(x + (float)radius, y + (float)radius, (float)radius + 0.1f, radius - 2, (float)this.duration / 3.95f, (int)this.duration, this.duration);
            GL11.glColor4f(0.0F, 0.5F, 0.0F, 1.0F);
            RenderUtil.drawCircleWithOutLine(x + (float)radius, y + (float)radius, (float)radius + 0.1f, radius - 2, (float)this.duration / 3.95f, (int)this.duration, d);
        } else if (((String)theme.getValue()).equalsIgnoreCase("Colored")) {
            float opacity = (float)(color >> 24 & 0xFF) / (float)255;
            float red = (float)(color >> 16 & 0xFF) / (float)255;
            float green = (float)(color >> 8 & 0xFF) / (float)255;
            float blue = (float)(color & 0xFF) / (float)255;
            GL11.glColor4f(red, green, blue, 0.15F * opacity);
            RenderUtil.drawCircle(x + (float)radius, y + (float)radius, radius);
            GL11.glColor4f(red, green, blue, 0.25F * opacity);
            RenderUtil.drawCircleWithOutLine(x + (float)radius, y + (float)radius, radius, 0.0, (float)this.duration / 3.95f, (int)this.duration, d);
            GL11.glColor4f(red, green, blue, opacity);
            RenderUtil.drawCircleWithOutLine(x + (float)radius, y + (float)radius, (float)radius + 0.1f, radius - 2, (float)this.duration / 3.95f, (int)this.duration, this.duration);
            GL11.glColor4f(red, green, blue, 0.15F * opacity);
            RenderUtil.drawCircleWithOutLine(x + (float)radius, y + (float)radius, (float)radius + 0.1f, radius - 2, (float)this.duration / 3.95f, (int)this.duration, d);
        }
        String string = String.format("%." + CheatBreaker.getInstance().getModuleManager().cooldownsMod.decimals.getValue() + "f", d / 1000.0);
        CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredStringWithShadow(string, x + (float)radius - CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth(string) / 2.0f, y + (float)(radius / 2) + (float)4, -1, 0x6F000000);
    }

    public boolean isTimeOver() {
        return this.time < System.currentTimeMillis() - this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void getTime() {
        this.time = System.currentTimeMillis();
    }

    public String getCooldownID() {
        return this.cooldownID;
    }

    public long getDuration() {
        return this.duration;
    }

    public int getItemID() {
        return this.itemID;
    }
}
