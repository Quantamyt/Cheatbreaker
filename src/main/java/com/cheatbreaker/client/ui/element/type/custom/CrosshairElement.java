package com.cheatbreaker.client.ui.element.type.custom;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CrosshairElement extends AbstractModulesGuiElement {
    public CrosshairElement(float scaleFactor) {
        super(scaleFactor);
        this.height = 50;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(this.x + (this.width / 2 - 15) - 41, this.y + 4, this.x + (this.width / 2 - 15) + 41, this.y + 51, -16777216);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderIcon(new ResourceLocation("client/defaults/crosshair_" + CheatBreaker.getInstance().getModuleManager().crosshairMod.previewBackground.getStringValue().toLowerCase() + ".png"), (float)(this.x + (this.width / 2 - 15) - 40), (float)(this.y + 5), (float)80, 45);
        Gui.zLevel = 0;
        CheatBreaker.getInstance().getModuleManager().crosshairMod.drawCrosshair(this.x + this.width / 2 - 15, this.y + this.height / 2 + 3, false);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
    }
}
