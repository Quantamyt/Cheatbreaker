package com.cheatbreaker.client.ui.cosmetic;

import com.cheatbreaker.client.ui.util.RenderUtil;
import com.google.common.base.Preconditions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.function.Consumer;

public abstract class WheelGUI extends GuiScreen {
    public static final int maxOptions = 8;
    private final IconButton[] buttons = new IconButton[8];
    private final WheelElement[] elements = new WheelElement[8];
    private final int openMenuButton;
    protected Consumer<IconButton> consumer;
    public int tick = 0;

    public WheelGUI(int openButton, List<IconButton> list) {

        Preconditions.checkNotNull(list, "options");
        Preconditions.checkArgument(list.size() <= 8, "cannot have more than 8 options");
        for (int i = 0; i < list.size(); ++i) {
            this.buttons[i] = list.get(i);
        }
        for (int i = 0; i < this.elements.length; ++i) {
            IconButton iconButton = null;
            if (i < list.size()) {
                iconButton = list.get(i);
            }
            this.elements[i] = new WheelElement(this, i, iconButton);
        }
        this.openMenuButton = openButton;
    }

    @Override
    public void drawScreen(int n, int n2, float f) {
        super.drawScreen(n, n2, f);
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        int n3 = scaledResolution.getScaledWidth();
        int n4 = scaledResolution.getScaledHeight();
        for (WheelElement element : this.elements) {
            if (element == null) continue;
            element.handleElementDraw(n, n2, true);
        }
        float f2 = 10.0f;
        float opacity = (float) this.tick >= f2 ? 1.0f : (float) this.tick / f2;
        GL11.glPushMatrix();
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.5f * opacity);
        RenderUtil.drawCircleWithOutLine((float) n3 / 2.0f, (float) n4 / 2.0f, 90.0, 88.0, 100.0, 100, 100.0);
        RenderUtil.drawCircleWithOutLine((float) n3 / 2.0f, (float) n4 / 2.0f, 20.0, 18.0, 100.0, 100, 100.0);
        GL11.glPopMatrix();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.tick;
        if (!Keyboard.isKeyDown(this.openMenuButton)) {
            if (this.consumer != null) {
                for (int i = 0; i < this.elements.length; ++i) {
                    WheelElement element = this.elements[i];
                    IconButton iconButton = this.buttons[i];
                    ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                    int scaledWidth = scaledResolution.getScaledWidth();
                    int scaledHeight = scaledResolution.getScaledHeight();

                    if (iconButton != null && iconButton.getImage().toString().contains("minecraft:")) {
                        if (!element.isMouseInsideElement((float) Mouse.getX() * scaledWidth / this.mc.displayHeight,
                                (float) Mouse.getY() * scaledHeight / this.mc.displayWidth - 1)) continue;

                        this.consumer.accept(iconButton);
                        break;
                    }
                }
            }
            this.mc.displayGuiScreen(null);
        }
    }
}
