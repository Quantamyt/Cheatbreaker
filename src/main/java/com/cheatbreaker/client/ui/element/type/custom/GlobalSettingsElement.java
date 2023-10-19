package com.cheatbreaker.client.ui.element.type.custom;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.element.AbstractScrollableElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GlobalSettingsElement extends AbstractModulesGuiElement {
    private final int highlightColor;
    private final AbstractScrollableElement scrollbar;
    private int progressiveRect = 0;
    private final ResourceLocation arrowIcon = new ResourceLocation("client/icons/right.png");

    public GlobalSettingsElement(AbstractScrollableElement scrollbar, int highlightColor, float scale) {
        super(scale);
        this.scrollbar = scrollbar;
        this.highlightColor = highlightColor;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        boolean bl = this.isMouseInside(mouseX, mouseY);
        int n3 = 75;
        Gui.drawRect(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor : CBTheme.lightDullTextColor);
        float f2 = HudLayoutEditorGui.getFPSTransitionSpeed(790);
        if (bl) {
            if (this.progressiveRect < n3) {
                this.progressiveRect = (int) ((float) this.progressiveRect + f2);
                if (this.progressiveRect > n3) {
                    this.progressiveRect = n3;
                }
            }
        } else if (this.progressiveRect > 0) {
            this.progressiveRect = (float) this.progressiveRect - f2 < 0.0f ? 0 : (int) ((float) this.progressiveRect - f2);
        }
        if (this.progressiveRect > 0) {
            float f3 = (float) this.progressiveRect / (float) n3 * 100.0F;
            Gui.drawRect(this.x, (int) ((float) this.y + ((float) this.height - (float) this.height * f3 / (float) 100)), this.x + this.width, this.y + this.height, this.highlightColor);
        }
        float darkOrLight = GlobalSettings.darkMode.getBooleanValue() ? 1.0F : 0.0F;
        GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, 1.4666667f * 0.23863636f);
        RenderUtil.renderIcon(this.arrowIcon, 2.2f * 1.1363636f, (float) (this.x + 6), (float) this.y + (float) 6);
        CheatBreaker.getInstance().playBold18px.drawString("CheatBreaker Settings".toUpperCase(), (float)this.x + 14.0F, (float)this.y + 3.0F, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor2 : CBTheme.lightDullTextColor2);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
    }
}
