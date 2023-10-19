package com.cheatbreaker.client.ui.mainmenu.menus;


import com.cheatbreaker.client.ui.mainmenu.MainMenuBase;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;


public class CBOldMenu extends GuiMainMenu {
    private double eventButton;
    private float lastMouseEvent = 0.0f;
    private final ResourceLocation logoOuter = new ResourceLocation("client/logo_outer.png");
    private final ResourceLocation logoInner = new ResourceLocation("client/logo_inner.png");

    @Override
    public void initGui() {
        int n = this.height / 4 + 48;
        this.addSingleplayerMultiplayerButtons(n, 24);
        super.initGui();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.eventButton += 0.06283185307179587;
        this.lastMouseEvent = (float) ((Math.sin(this.eventButton) / 2.0 + 0.5) * 180.0);
    }

    @Override
    protected void keyTyped(char c, int n) throws IOException {

        if (n == 1 && Minecraft.getMinecraft().currentScreen instanceof CBOldMenu) {
            return;
        }

        super.keyTyped(c, n);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
//        Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 20, 0.0f);
        Gui.drawRect(this.width / 2F - 71, this.height / 4F - 40, this.width / 2F + 71, this.height / 4F + 110, -1342177281);
        Gui.drawRect(this.width / 2F - 73, this.height / 4F - 42, this.width / 2F - 71, this.height / 4F + 112, 0x3FFFFFFF);
        Gui.drawRect(this.width / 2F + 71, this.height / 4F - 42, this.width / 2F + 73, this.height / 4F + 112, 0x3FFFFFFF);
        Gui.drawRect(this.width / 2F - 71, this.height / 4F + 110, this.width / 2F + 71, this.height / 4F + 112, 0x3FFFFFFF);
        Gui.drawRect(this.width / 2F - 71, this.height / 4F - 42, this.width / 2F + 71, this.height / 4F - 40, 0x3FFFFFFF);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.9f);
        GL11.glPushMatrix();
        float f2 = 0.8648649f * 0.7515625f;
        GL11.glScalef(f2, f2, f2);
        GL11.glPushMatrix();
        GL11.glTranslatef(((float) (this.width / 2) - 40.0F * f2) / f2, ((float) (this.height / 4) - 40.0F * f2) / f2, 0.0f);
        int n3 = 40;
        GL11.glTranslatef(n3, n3, n3);
        GL11.glRotatef(this.lastMouseEvent, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-n3, -n3, -n3);
        RenderUtil.renderIcon(this.logoOuter, (float) n3, 0.0f, 0.0f);
        GL11.glPopMatrix();
        RenderUtil.renderIcon(this.logoInner, 40.0F, ((float) (this.width / 2) - 40.0F * f2) / f2, ((float) (this.height / 4) - 39.0F * f2) / f2);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
//        tessellator.setColorOpaque_I(-1);
        super.lIIIIIIIIIlIllIIllIlIIlIl(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= 7 && mouseX <= 39 && mouseY >= 5 && mouseY <= 20) {
            MainMenuBase.switchMenu();
        }
    }
}
