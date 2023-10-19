package com.cheatbreaker.client.ui.mainmenu;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.ui.fading.ColorFade;

public class GradientTextButton extends AbstractElement {
    private String string;
    private final ColorFade outlineColor;
    private final ColorFade topGradientColor;
    private final ColorFade bottomGradientColor;
    private int[] colorArray;

    public GradientTextButton(String string) {
        this.string = string;
        this.outlineColor = new ColorFade(-14277082, -11493284);
        this.topGradientColor = new ColorFade(-13487566, -10176146);
        this.bottomGradientColor = new ColorFade(-14013910, -11164318);
    }

    // These names are not finalized. These will be named at another time. This is only to ensure that the obfuscation namings are dealt with first.
    public void buttonColor1() {
        this.setColorArray(new int[]{-11119018, -11493284, -10329502, -10176146, -11579569, -11164318});
    }

    public void buttonColor2() {
        this.setColorArray(new int[]{-11493284, -11493284, -10176146, -10176146, -11164318, -11164318});
    }

    public void buttonColor3() {
        this.setColorArray(new int[]{-14277082, -11493284, -13487566, -10176146, -14013910, -11164318});
    }

    private void setColorArray(int[] colorArray) {
        this.colorArray = colorArray;
    }

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        boolean bl2 = bl && this.isMouseInside(f, f2);
        if (this.colorArray != null && this.outlineColor.isOver()) {
            this.outlineColor.setStartColor(this.colorArray[0]);
            this.outlineColor.setEndColor(this.colorArray[1]);
            this.topGradientColor.setStartColor(this.colorArray[2]);
            this.topGradientColor.setEndColor(this.colorArray[3]);
            this.bottomGradientColor.setStartColor(this.colorArray[4]);
            this.bottomGradientColor.setEndColor(this.colorArray[5]);
            this.colorArray = null;
        }
        RenderUtil.drawGradientRectWithOutline(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.outlineColor.getColor(bl2).getRGB(), this.topGradientColor.getColor(bl2).getRGB(), this.bottomGradientColor.getColor(bl2).getRGB());
        CheatBreaker.getInstance().robotoRegular13px.drawCenteredString(this.string, this.xPosition + this.width / 2.0f, this.yPosition + 2.0f, -1);
    }

    public void drawElement(float f, float f2, boolean hovering) {
        this.handleElementDraw(f, f2, hovering);
    }


    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        return false;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
