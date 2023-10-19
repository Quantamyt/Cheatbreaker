package com.cheatbreaker.client.ui.mainmenu.buttons;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.mainmenu.cosmetics.element.CosmeticElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.cosmetic.Cosmetic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class MainMenuCosmeticsMenu extends GuiMainMenu {
    private final List cosmetics = new ArrayList();
    private final ResourceLocation lastMouseEvent = new ResourceLocation("client/icons/left.png");
    private final ResourceLocation field_146298_h = new ResourceLocation("client/icons/right.png");
    private int IIIIllIIllIIIIllIllIIIlIl = 0;

    public MainMenuCosmeticsMenu() {
        for (Cosmetic cosmetic : CheatBreaker.getInstance().getCosmeticManager().getFullCosmeticList()) {
            this.cosmetics.add(new CosmeticElement(cosmetic, 1.0f));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!CheatBreaker.getInstance().getWSNetHandler().isOpen()) {
            CheatBreaker.getInstance().playRegular16px.drawCenteredStringWithShadow("Unable to connect to the server.", this.width / 2, this.height / 2 - 10, -1);
            CheatBreaker.getInstance().playRegular16px.drawCenteredStringWithShadow("Please try again later.", this.width / 2, this.height / 2 + 4, -1);
        } else {
            RenderUtil.drawRoundedRect(this.width / 2 - 80, this.height / 2 - 78, this.width / 2 + 80, this.height / 2 + 100, 14, -1342177281);
            if (this.cosmetics.isEmpty()) {
                CheatBreaker.getInstance().playRegular16px.drawCenteredString("You don't own any cosmetics.", this.width / 2, this.height / 2 + 4, -6381922);
            } else {
                float f2 = this.height / 2 - 68;
                float f3 = this.height / 2 + 92;
                float f4 = this.width / 2 + 68;
                float f5 = this.width / 2 + 74;
                CheatBreaker.getInstance().playBold18px.drawCenteredString("Cosmetics (" + this.cosmetics.size() + ")", this.width / 2, this.height / 2 - 90, -1);
                int n3 = 0;
                float f6 = 0.0f;
                for (Object cosmeticListElementObj : this.cosmetics) {
                    CosmeticElement cosmeticListElement = (CosmeticElement) cosmeticListElementObj;
                    if (++n3 - 1 < this.IIIIllIIllIIIIllIllIIIlIl * 5 || n3 - 1 >= (this.IIIIllIIllIIIIllIllIIIlIl + 1) * 5) continue;
                    cosmeticListElement.setDimensions(this.width / 2 - 76, (int)((float)(this.height / 2 - 72) + f6), 152, cosmeticListElement.getHeight());
                    cosmeticListElement.handleDrawElement(mouseX, mouseY, partialTicks);
                    f6 += (float)cosmeticListElement.getHeight();
                }
                if (this.cosmetics.size() > 5) {
                    boolean bl = mouseX > this.width / 2 - 40 && mouseX < this.width / 2 - 1 && mouseY > this.height / 2 + 80 && mouseY < this.height / 2 + 100;
                    GL11.glColor4f(0.0f, 0.0f, 0.0f, bl ? 0.20624998f * 2.1818182f : 7.111111f * 0.03515625f);
                    RenderUtil.renderIcon(this.lastMouseEvent, (float)4, (float)(this.width / 2 - 10), (float)(this.height / 2 + 84));
                    boolean bl2 = mouseX > this.width / 2 + 1 && mouseX < this.width / 2 + 40 && mouseY > this.height / 2 + 80 && mouseY < this.height / 2 + 100;
                    GL11.glColor4f(0.0f, 0.0f, 0.0f, bl2 ? 0.6042857f * 0.7446808f : 0.041666668f * 6.0f);
                    RenderUtil.renderIcon(this.field_146298_h, (float)4, (float)(this.width / 2 + 10), (float)(this.height / 2 + 84));
                }
            }
        }
        this.lIIIIIIIIIlIllIIllIlIIlIl(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int n4;
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.cosmetics.size() > 5) {
            boolean bl;
            n4 = mouseX > this.width / 2 - 40 && mouseX < this.width / 2 - 1 && mouseY > this.height / 2 + 80 && mouseY < this.height / 2 + 100 ? 1 : 0;
            boolean bl2 = bl = mouseX > this.width / 2 + 1 && mouseX < this.width / 2 + 40 && mouseY > this.height / 2 + 80 && mouseY < this.height / 2 + 100;
            if (this.IIIIllIIllIIIIllIllIIIlIl > 0 && n4 != 0) {
                --this.IIIIllIIllIIIIllIllIIIlIl;
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            } else if (bl && (float)(this.IIIIllIIllIIIIllIllIIIlIl + 1) < (float)this.cosmetics.size() / (float)5) {
                ++this.IIIIllIIllIIIIllIllIIIlIl;
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            }
        }
        n4 = 0;
        for (Object cosmeticListElement : this.cosmetics) {
            if (++n4 - 1 < this.IIIIllIIllIIIIllIllIIIlIl * 5 || n4 - 1 >= (this.IIIIllIIllIIIIllIllIIIlIl + 1) * 5) continue;
            ((CosmeticElement)cosmeticListElement).handleMouseClick(mouseX, mouseY, mouseButton);
        }
    }
}
