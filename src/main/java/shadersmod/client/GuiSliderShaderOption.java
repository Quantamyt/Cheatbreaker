package shadersmod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GlStateManager;
import net.minecraft.util.MathHelper;

public class GuiSliderShaderOption extends GuiButtonShaderOption {
    private float sliderValue = 1.0F;
    public boolean dragging;
    private ShaderOption shaderOption = null;

    public GuiSliderShaderOption(int buttonId, int x, int y, int w, int h, ShaderOption shaderOption, String text) {
        super(buttonId, x, y, w, h, shaderOption, text);
        this.shaderOption = shaderOption;
        this.sliderValue = shaderOption.getIndexNormalized();
        this.displayString = GuiShaderOptions.getButtonText(shaderOption, this.field_146120_f);
    }

    public int getHoverState(boolean mouseOver) {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.field_146125_m) {
            if (this.dragging) {
                this.sliderValue = (float)(mouseX - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
                this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
                this.shaderOption.setIndexNormalized(this.sliderValue);
                this.sliderValue = this.shaderOption.getIndexNormalized();
                this.displayString = GuiShaderOptions.getButtonText(this.shaderOption, this.field_146120_f);
            }

            mc.getTextureManager().bindTexture(field_146122_a);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.field_146128_h + (int)(this.sliderValue * (float)(this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.field_146128_h + (int)(this.sliderValue * (float)(this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.sliderValue = (float)(mouseX - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
            this.shaderOption.setIndexNormalized(this.sliderValue);
            this.displayString = GuiShaderOptions.getButtonText(this.shaderOption, this.field_146120_f);
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

    public void valueChanged() {
        this.sliderValue = this.shaderOption.getIndexNormalized();
    }
}
