package com.cheatbreaker.client.ui.element.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.element.AbstractScrollableElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.ui.util.font.CBFontRenderer;
import lombok.Setter;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModulesGuiButtonElement extends AbstractModulesGuiElement {
    public int color;
    @Setter public boolean conflict;
    @Setter public boolean smallIcon;
    @Setter public boolean mediumIcon;
    public String optionString;
    public final AbstractScrollableElement scrollableElement;
    private final CBFontRenderer font;
    public boolean moduleAllowedToAddToHud = true;
    private int timing = 0;
    private boolean background;

    public ModulesGuiButtonElement(CBFontRenderer font, AbstractScrollableElement scroll, String string, int x, int y, int x2, int y2, int color, float scale) {
        super(scale);
        this.optionString = string;
        this.setDimensions(x, y, x2, y2);
        this.color = color;
        this.scrollableElement = scroll;
        this.font = font;
        this.background = true;
    }

    public ModulesGuiButtonElement(AbstractScrollableElement scroll, String string, int x, int y, int x2, int y2, int color, float scale) {
        this(CheatBreaker.getInstance().playBold22px, scroll, string, x, y, x2, y2, color, scale);
        this.background = true;
    }

    public ModulesGuiButtonElement(AbstractScrollableElement scroll, String string, int x, int y, int x2, int y2, int color, float scale, boolean background) {
        this(CheatBreaker.getInstance().playBold22px, scroll, string, x, y, x2, y2, color, scale);
        this.background = background;
    }

    public ModulesGuiButtonElement(CBFontRenderer font, AbstractScrollableElement scroll, String string, int x, int y, int x2, int y2, int color, float scale, boolean background) {
        this(font, scroll, string, x, y, x2, y2, color, scale);
        this.background = background;
    }

    @Override
    public void handleDrawElement(int mouseX, int n2, float partialTicks) {
        float f2;
        boolean bl = this.isMouseInside(mouseX, n2);
        int n3 = 120;
        if (bl && this.moduleAllowedToAddToHud) {
            if (this.background) {
                Gui.drawRect(this.x - 2, this.y - 2, this.x + this.width + 2, this.y + this.height + 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor3 : CBTheme.lightBackgroundColor3);
            }
            f2 = HudLayoutEditorGui.getFPSTransitionSpeed(790);
            this.timing = (float)this.timing + f2 < (float)n3 ? (int)((float)this.timing + f2) : n3;
        } else if (this.timing > 0) {
            f2 = HudLayoutEditorGui.getFPSTransitionSpeed(790);
            this.timing = (float)this.timing - f2 < 0.0f ? 0 : (int)((float)this.timing - f2);
        }

        if (this.background) {
            if (this.moduleAllowedToAddToHud) {
                Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor : CBTheme.lightBackgroundColor);
            } else {
                Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor2 : CBTheme.lightBackgroundColor2);
            }
        }

        if (this.timing > 0) {
            f2 = (float)this.timing / (float)n3 * 100.0F;
            Gui.drawRect(this.x, (int)((float)this.y + ((float)this.height - (float)this.height * f2 / 100.0F)), this.x + this.width, this.y + this.height, this.color);
        }

        if (this.optionString.contains(".png")) {
            float iconScale = this.smallIcon ? 3.5F : 8F;
            float iconX = this.mediumIcon ? (this.x + 2F) : this.smallIcon ? (float)(this.x + 1.6) : (float)(this.x + 6);
            float iconY = this.mediumIcon ? (this.y + 2F) : this.smallIcon ? (float)(this.y + 1.7) : (float)(this.y + 6);

            GL11.glPushMatrix();
            float darkOrLight = GlobalSettings.darkMode.getBooleanValue() ? 1.0F : 0.0F;
            GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, 0.47368422f * 0.9499999f);
            RenderUtil.renderIcon(new ResourceLocation("client/icons/" + this.optionString), iconScale,  iconX, iconY);
            GL11.glPopMatrix();
        } else {
            f2 = this.font == CheatBreaker.getInstance().playBold22px ? 2.0f : 0.54545456f * 0.9166667f;
            this.font.drawCenteredString(this.optionString.toUpperCase(), this.x + this.width / 2, (float)(this.y + this.height / 2 - this.font.getHeight()) + f2, (this.conflict ? 11141120 : GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor));
        }
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
    }

    public int getWidth() {
        return this.width;
    }

    public void allowAddToHud(boolean module) {
        this.moduleAllowedToAddToHud = module;
    }
}
