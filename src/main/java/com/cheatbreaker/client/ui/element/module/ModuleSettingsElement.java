package com.cheatbreaker.client.ui.element.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.element.AbstractScrollableElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModuleSettingsElement extends AbstractModulesGuiElement {
    private final int highlightColor;
    public final AbstractModule module;
    public final AbstractScrollableElement scrollbar;
    private int progressiveRect = 0;
    private final ResourceLocation arrowIcon = new ResourceLocation("client/icons/right.png");

    public ModuleSettingsElement(AbstractScrollableElement scrollbar, int highlightColor, AbstractModule module, float scale) {
        super(scale);
        this.scrollbar = scrollbar;
        this.highlightColor = highlightColor;
        this.module = module;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        boolean bl = this.isMouseInside(mouseX, mouseY);
        int n3 = 75;
        Gui.drawRect(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor : CBTheme.lightDullTextColor);
        if (this.scrollbar.hasSettings(this.module)) {
            float f2;
            if (bl) {
                f2 = HudLayoutEditorGui.getFPSTransitionSpeed(790);
                if ((float)this.progressiveRect + f2 < (float)n3) {
                    this.progressiveRect = (int)((float)this.progressiveRect + f2);
                    if (this.progressiveRect > n3) {
                        this.progressiveRect = n3;
                    }
                }
            } else if (this.progressiveRect > 0) {
                f2 = HudLayoutEditorGui.getFPSTransitionSpeed(790);
                this.progressiveRect = (float)this.progressiveRect - f2 < 0.0f ? 0 : (int)((float)this.progressiveRect - f2);
            }
            if (this.progressiveRect > 0) {
                f2 = (float)this.progressiveRect / (float)n3 * 100.0F;
                Gui.drawRect(this.x, (int)((float)this.y + ((float)this.height - (float)this.height * f2 / 100.0F)), this.x + this.width, this.y + this.height, this.highlightColor);
            }
        }
        float darkOrLight = GlobalSettings.darkMode.getBooleanValue() ? 1.0F : 0.0F;
        GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, 0.35f);
        RenderUtil.renderIcon(this.arrowIcon, 2.5F, (float)(this.x + 6), (float)this.y + 6.0F);
        CheatBreaker.getInstance().playBold18px.drawString(this.module.getName().toUpperCase(), (float)this.x + 14.0F, (float)this.y + 3.0F, this.scrollbar.hasSettings(this.module) ? GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor2 : CBTheme.lightDullTextColor2 : GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor : CBTheme.lightDullTextColor);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        double d = this.height - 10;
        double d3 = d / this.scrollbar.scrollHeight * (double) 100;
        double d4 = d / (double) 100 * d3;
        double d5 = (double) this.scrollbar.scrollAmount / 100.0 * d3;
        boolean bl4 = (float) mouseX > (float) (this.x + this.width - 9) * this.scale && (float) mouseX < (float) (this.x + this.width - 3) * this.scale && (double) mouseY > ((double) (this.y + 11) - d5) * (double) this.scale && (double) mouseY < ((double) (this.y + 8) + d4 - d5) * (double) this.scale;
        boolean bl3 = (float) mouseX > (float) (this.x + this.width - 9) * this.scale && (float) mouseX < (float) (this.x + this.width - 3) * this.scale && (float) mouseY > (float) (this.y + 11) * this.scale && (double) mouseY < ((double) (this.y + 6) + d - (double) 3) * (double) this.scale;
        if (button == 0 && bl3 || bl4) {
            this.scrollbar.hovering = true;
        }
        this.scrollbar.handleModuleMouseClick(this.module);
    }
}
