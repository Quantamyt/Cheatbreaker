package com.cheatbreaker.client.ui.mainmenu.cosmetics;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.Cosmetic;
import com.cheatbreaker.client.ui.mainmenu.GradientTextButton;
import com.cheatbreaker.client.ui.mainmenu.MainMenuBase;
import com.cheatbreaker.client.ui.mainmenu.cosmetics.element.CosmeticElement;
import com.cheatbreaker.client.ui.mainmenu.menus.MainMenu;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiCosmetics extends MainMenuBase {
    private final List<CosmeticElement> cosmeticList = new ArrayList<>();
    private final ResourceLocation leftArrowIcon = new ResourceLocation("client/icons/left.png");
    private final ResourceLocation rightArrowIcon = new ResourceLocation("client/icons/right.png");
    private final GradientTextButton backButton = new GradientTextButton("BACK");
    private int parsedCosmetics = 0;

    public GuiCosmetics() {
        for (Cosmetic cosmetic : CheatBreaker.getInstance().getCosmeticManager().getFullCosmeticList()) {
            this.cosmeticList.add(new CosmeticElement(cosmetic, 1.0f));
        }
    }

    @Override
    public void drawMenu(float x, float y) {
        super.drawMenu(x, y);
        if (!CheatBreaker.getInstance().getWsNetHandler().isOpen()) {
            CheatBreaker.getInstance().playRegular16px.drawCenteredStringWithShadow("Unable to connect to the server.", this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f - 10.0f, -1);
            CheatBreaker.getInstance().playRegular16px.drawCenteredStringWithShadow("Please try again later.", this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f + 4.0f, -1);
            this.backButton.setElementSize(this.getScaledWidth() / 2.0f - 30.0f, this.getScaledHeight() / 2.0f + 28.0f, 60.0f, 12.0f);
            this.backButton.drawElementHover(x, y, true);
        } else {
            Gui.drawRect(this.getScaledWidth() / 2.0f - 80.0f, this.getScaledHeight() / 2.0f - 78.0f, this.getScaledWidth() / 2.0f + 80.0f, this.getScaledHeight() / 2.0f + 100.0f, 0x2F000000);
            this.backButton.setElementSize(this.getScaledWidth() / 2.0f - 30.0f, this.getScaledHeight() / 2.0f + 105.0f, 60.0f, 12.0f);
            this.backButton.drawElementHover(x, y, true);
            if (this.cosmeticList.isEmpty()) {
                CheatBreaker.getInstance().playRegular16px.drawCenteredString("You don't own any cosmetics.", this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f + 4.0f, -6381922);
            } else {
                CheatBreaker.getInstance().playBold18px.drawCenteredString("Cosmetics (" + this.cosmeticList.size() + ")", this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f - 90.0f, -1);
                int var7 = 0;
                float var8 = 0.0f;
                for (CosmeticElement cosmetic : this.cosmeticList) {
                    if (++var7 - 1 < this.parsedCosmetics * 5 || var7 - 1 >= (this.parsedCosmetics + 1) * 5) continue;
                    cosmetic.setDimensions((int) this.getScaledWidth() / 2 - 76, (int) (this.getScaledHeight()
                            / 2.0f - 72.0f + var8), 152, cosmetic.getHeight());
                    cosmetic.handleDrawElement((int) x, (int) y, 1.0f);
                    var8 += (float) cosmetic.getHeight();
                }
                if (this.cosmeticList.size() > 5) {
                    boolean var11 = x > this.getScaledWidth() / 2.0f - 40.0f && x < this.getScaledWidth() / 2.0f - 1.0f && y > this.getScaledHeight() / 2.0f + 80.0f && y < this.getScaledHeight() / 2.0f + 100.0f;
                    GL11.glColor4f(0.0f, 0.0f, 0.0f, var11 ? 0.45f : 0.25f);
                    RenderUtil.renderIcon(this.leftArrowIcon, 4.0f, this.getScaledWidth() / 2.0f - 10.0f, this.getScaledHeight() / 2.0f + 84.0f);
                    boolean var12 = x > this.getScaledWidth() / 2.0f + 1.0f && x < this.getScaledWidth() / 2.0f + 40.0f && y > this.getScaledHeight() / 2.0f + 80.0f && y < this.getScaledHeight() / 2.0f + 100.0f;
                    GL11.glColor4f(0.0f, 0.0f, 0.0f, var12 ? 0.45f : 0.25f);
                    RenderUtil.renderIcon(this.rightArrowIcon, 4.0f, this.getScaledWidth() / 2.0f + 10.0f, this.getScaledHeight() / 2.0f + 84.0f);
                }
            }
        }
    }

    @Override
    public void mouseClicked(float var1, float var2, int var3) {
        super.mouseClicked(var1, var2, var3);
        if (this.backButton.isMouseInside(var1, var2)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new MainMenu());
        } else {
            if (this.cosmeticList.size() > 5) {
                boolean var5;
                boolean var4 = var1 > this.getScaledWidth() / 2.0f - 40.0f && var1 < this.getScaledWidth() / 2.0f - 1.0f && var2 > this.getScaledHeight() / 2.0f + 80.0f && var2 < this.getScaledHeight() / 2.0f + 100.0f;
                boolean bl = var5 = var1 > this.getScaledWidth() / 2.0f + 1.0f && var1 < this.getScaledWidth() / 2.0f + 40.0f && var2 > this.getScaledHeight() / 2.0f + 80.0f && var2 < this.getScaledHeight() / 2.0f + 100.0f;
                if (this.parsedCosmetics > 0 && var4) {
                    --this.parsedCosmetics;
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
                } else if (var5 && (float) (this.parsedCosmetics + 1) < (float) this.cosmeticList.size() / 5.0f) {
                    ++this.parsedCosmetics;
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
                }
            }
            int var7 = 0;
            for (CosmeticElement var6 : this.cosmeticList) {
                if (++var7 - 1 < this.parsedCosmetics * 5 || var7 - 1 >= (this.parsedCosmetics + 1) * 5) continue;
                var6.handleMouseClick((int) var1, (int) var2, var3);
            }
        }
    }
}
