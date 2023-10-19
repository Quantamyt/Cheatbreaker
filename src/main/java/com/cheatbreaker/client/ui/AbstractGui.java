package com.cheatbreaker.client.ui;

import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractGui extends GuiScreen {
    @Getter
    private float scaledWidth;
    @Getter
    private float scaledHeight;
    protected List<AbstractElement> selectedButton;
    protected List<AbstractElement> eventButton;
    @Getter
    public ScaledResolution scaledResolution;
    protected int lastMouseEvent = 0;

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        this.mc = mc;
        this.fontRendererObj = mc.fontRendererObj;
        this.width = width;
        this.height = height;
        this.buttonList.clear();
        this.scaledResolution = new ScaledResolution(this.mc);
        float scaleFactory = this.getScaleFactor();
        this.scaledWidth = (float) width / scaleFactory;
        this.scaledHeight = (float) height / scaleFactory;
        this.initGui();
    }

    protected void setElements(AbstractElement... var1) {
        this.selectedButton = new ArrayList<>();
        this.selectedButton.addAll(Arrays.asList(var1));
        this.lastMouseEvent = this.selectedButton.size();
    }

    public void addElement(AbstractElement... var1) {
        this.selectedButton.addAll(Arrays.asList(var1));
        this.initGui();
    }

    public void removeElement(AbstractElement... var1) {
        this.selectedButton.removeAll(Arrays.asList(var1));
        this.initGui();
    }

    protected void addElements(AbstractElement... var1) {
        this.eventButton = new ArrayList<>();
        this.eventButton.addAll(Arrays.asList(var1));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float var4 = this.getScaleFactor();
        GL11.glPushMatrix();
        GL11.glScalef(var4, var4, var4);
        this.drawMenu((float) mouseX / var4, (float) mouseY / var4);
        GL11.glPopMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float var4 = this.getScaleFactor();
        this.mouseClicked((float) mouseX / var4, (float) mouseY / var4, mouseButton);
    }

    @Override // Previously known as mouseMovedOrUp()
    protected void mouseReleased(int var1, int var2, int var3) {
        float var4 = this.getScaleFactor();
        this.mouseMovedOrUp((float) var1 / var4, (float) var2 / var4, var3);
    }

    public abstract void drawMenu(float var1, float var2);

    protected abstract void mouseClicked(float var1, float var2, int var3);

    public abstract void mouseMovedOrUp(float var1, float var2, int var3);

    public float getScaleFactor() {
        return 1.0f / (this.scaledResolution.getScaleFactor() / 2.0f);
    }

    protected void closeElements() {
        this.selectedButton.forEach(AbstractElement::handleElementClose);
    }

    protected void updateElements() {
        this.selectedButton.forEach(AbstractElement::handleElementUpdate);
    }

    protected void handleElementKeyTyped(char var1, int var2) {
        for (AbstractElement var4 : this.selectedButton) {
            var4.keyTyped(var1, var2);
        }
    }

    protected void handleElementMouse() {
        this.selectedButton.forEach(AbstractElement::handleElementMouse);
    }

    protected void drawElementHover(float var1, float var2, AbstractElement... var3) {
        List<AbstractElement> var4 = Arrays.asList(var3);
        for (AbstractElement var6 : this.selectedButton) {
            if (var4.contains(var6)) continue;
            var6.drawElementHover(var1, var2, this.mouseClicked(var6, var1, var2));
        }
    }

    protected void onMouseMoved(float var1, float var2, int var3) {
        for (AbstractElement element : selectedButton) {
            if (element.isMouseInside(var1, var2)) {
                element.onMouseMoved(var1, var2, var3, this.mouseClicked(element, var1, var2));
            }
        }
    }

    protected void swapElement(float var1, float var2, int var3, AbstractElement... var4) {
        block4:
        {
            List<AbstractElement> var5 = Arrays.asList(var4);
            AbstractElement var6 = null;
            boolean var7 = false;
            for (AbstractElement var9 : this.selectedButton) {
                if (var5.contains(var9) || !var9.isMouseInside(var1, var2)) continue;
                if (!this.eventButton.contains(var9)) {
                    var6 = var9;
                }
                if (!var9.handleElementMouseClicked(var1, var2, var3, this.mouseClicked(var9, var1, var2, var4)))
                    continue;
                var7 = true;
                break;
            }
            if (var7) break block4;
            if (var6 != null) {
                this.selectedButton.add(this.selectedButton.remove(this.selectedButton.indexOf(var6)));
            }
            for (AbstractElement var9 : this.selectedButton) {
                if (var9.onMouseClick(var1, var2, var3)) break;
            }
        }
    }

    protected boolean mouseClicked(AbstractElement var1, float var2, float var3, AbstractElement... var4) {
        AbstractElement var8;
        List<AbstractElement> var5 = Arrays.asList(var4);
        boolean var6 = true;
        for (int var7 = this.selectedButton.size() - 1; var7 >= 0 && (var8 = this.selectedButton.get(var7)) != var1; --var7) {
            if (var5.contains(var8) || !var8.isMouseInside(var2, var3)) continue;
            var6 = false;
            break;
        }
        return var6;
    }

    public List<AbstractElement> getElements() {
        return this.selectedButton;
    }

    public void setResolution(ScaledResolution var1) {
        this.scaledResolution = var1;
    }
}
