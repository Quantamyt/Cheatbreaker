package com.cheatbreaker.client.ui.mainmenu.cosmetics.element;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.Cosmetic;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CosmeticElement extends AbstractModulesGuiElement {
    private final Cosmetic cosmetic;

    public CosmeticElement(Cosmetic var1, float var2) {
        super(var2);
        this.height = 30;
        this.cosmetic = var1;
    }

    @Override
    public void handleDrawElement(int mouseX, int var2, float partialTicks) {
        boolean hovered = mouseX > this.x && mouseX < this.x + this.width && var2 > this.y && var2 < this.y + this.height;
        if (hovered) {
            Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, 0x2F000000);
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.cosmetic.getType().getTypeName().equals("cape")) {
            Minecraft.getMinecraft().renderEngine.bindTexture(this.cosmetic.getLocation());
            GL11.glPushMatrix();
            GL11.glTranslatef(this.x + 20, this.y + 7, 0.0f);
            GL11.glScalef(0.25f, 0.13f, 0.25f);
            RenderUtil.drawTexturedModalRect(0.0f, 0.0f, 2.0f, 7.0f, 44, 120);
            GL11.glPopMatrix();
        } else {
            try {
                RenderUtil.renderIcon(this.cosmetic.getPreviewLocation(), 8.0f, (float) (this.x + 20), (float) (this.y + 7));
            } catch (Exception ignored) {}
        }

        CheatBreaker.getInstance().playRegular16px.drawString(this.cosmetic.getName().replace("_", " "), this.x + 42, (float) (this.y + this.height / 2 - 5), -1342177281);
        if (this.cosmetic.isEquipped()) {
            GL11.glColor4f(0.0f, 0.8f, 0.0f, 0.45f);
        } else {
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.25f);
        }

        RenderUtil.drawCircle(this.x + 8, this.y + this.height / 2F, 3.0);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        boolean mouseHovered = mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
        if (mouseHovered) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            if (this.cosmetic.isEquipped()) {
                this.cosmetic.setEquipped(false);
            } else if (this.cosmetic.getType() == Cosmetic.CosmeticType.CAPE) {
                this.cosmetic.setEquipped(true);
                for (Cosmetic var6 : CheatBreaker.getInstance().getCosmeticManager().getFullCosmeticList()) {
                    if (var6 == this.cosmetic || !var6.getType().equals(Cosmetic.CosmeticType.CAPE)) continue;
                    var6.setEquipped(false);
                }
                this.cosmetic.setEquipped(true);
            } else {
                this.cosmetic.setEquipped(true);
                for (Cosmetic var6 : CheatBreaker.getInstance().getCosmeticManager().getFullCosmeticList()) {
                    if (var6 == this.cosmetic || var6.getType() == Cosmetic.CosmeticType.CAPE) continue;
                    var6.setEquipped(false);
                }
                this.cosmetic.setEquipped(true);
            }

            CheatBreaker.getInstance().getWsNetHandler().sendClientCosmetics();
        }
    }
}