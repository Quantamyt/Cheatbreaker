package com.cheatbreaker.client.audio.music.elements;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.audio.music.data.Station;
import com.cheatbreaker.client.audio.music.util.DashUtil;
import com.cheatbreaker.client.ui.element.DraggableElement;
import com.cheatbreaker.client.ui.element.type.FlatButtonElement;
import com.cheatbreaker.client.ui.element.type.HorizontalSliderElement;
import com.cheatbreaker.client.ui.element.type.InputFieldElement;
import com.cheatbreaker.client.ui.element.type.ScrollableElement;
import com.cheatbreaker.client.ui.fading.MinMaxFade;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RadioElement extends DraggableElement {
    private final ResourceLocation dashIcon = new ResourceLocation("client/dash-logo-54.png");
    private final ResourceLocation playIcon = new ResourceLocation("client/icons/play-24.png");
    private final List<RadioStationElement> radioStationElements = new ArrayList<>();
    private final MinMaxFade fadeTime = new MinMaxFade(300L);

    private float newHeight;

    private boolean isFading;

    private final HorizontalSliderElement volumeSlider;
    private final ScrollableElement scrollbar;
    private final InputFieldElement filterTextField;
    private final FlatButtonElement pinButton;

    public RadioElement() {
        this.volumeSlider = new HorizontalSliderElement(CheatBreaker.getInstance().getGlobalSettings().radioVolume);
        this.scrollbar = new ScrollableElement(this);
        this.filterTextField = new InputFieldElement(this.cb.playRegular14px, "Filter", -11842741, -11842741);
        this.pinButton = new FlatButtonElement((Boolean) this.cb.getGlobalSettings().pinRadio.getValue() ? "Unpin" : "Pin");

        for (Station station : CheatBreaker.getInstance().getDashManager().getStations()) {
            this.radioStationElements.add(new RadioStationElement(this, station));
        }

    }

    /**
     * Updates the element size.
     */
    public void updateElementSize() {
        this.setElementSize(this.xPosition, this.yPosition, this.width, this.height);
    }

    /**
     * Handles the Mouse Clicked event.
     */
    private boolean handleElementMouseClicked(RadioStationElement radioStationElement) {
        return this.filterTextField.getText().equals("") || radioStationElement.getStation().getName().toLowerCase().startsWith(this.filterTextField.getText().toLowerCase()) || radioStationElement.getStation().getGenre().toLowerCase().startsWith(this.filterTextField.getText().toLowerCase());
    }

    /**
     * Sets the element size.
     */
    public void setElementSize(float x, float y, float width, float height) {
        super.setElementSize(x, y, width, height);
        if (this.newHeight == 0.0f) {
            this.newHeight = height;
        }

        this.radioStationElements.sort((var0, var1x) -> {
            if (var0.getStation().isFavourite() && !var1x.getStation().isFavourite()) {
                return -1;
            } else {
                return !var0.getStation().isFavourite() && var1x.getStation().isFavourite() ? 1 : 0;
            }
        });
        this.volumeSlider.setElementSize(x, y + this.newHeight, width, 8.0F);
        this.filterTextField.setElementSize(x, y + this.newHeight + 8.0F, width - 30.0F, 13.0F);
        this.pinButton.setElementSize(x + width - 30.0F, y + this.newHeight + 8.0F, 30.0F, 13.0F);
        this.scrollbar.setElementSize(x + width - 5.0F, y + this.newHeight + 21.0F, 5.0F, 99.0F);
        int var5 = 0;
        boolean var6 = true;

        for (RadioStationElement station : this.radioStationElements) {
            if (this.handleElementMouseClicked(station)) {
                float var9 = y + 21.0F + this.newHeight + (float) var5;
                station.setElementSize(x, var9, width - 5.0F, 20.0F);
                var5 += 20;
            }
        }

        this.scrollbar.setScrollAmount((float) var5);
    }

    /**
     * Draws the entire element.
     */
    protected void handleElementDraw(float var1, float var2, boolean var3) {
        this.onDrag(var1, var2);
        Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.newHeight, -14540254);
        Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.newHeight, this.yPosition + this.newHeight, -1);
        Station currentStation = CheatBreaker.getInstance().getDashManager().getCurrentStation();
        if (currentStation != null) {
            if (currentStation.currentResource == null && !currentStation.getCoverURL().equals("")) {
                if (currentStation.previousResource != null) {
                    this.mc.getTextureManager().deleteTexture(currentStation.previousResource);
                    currentStation.previousResource = null;
                }

                currentStation.currentResource = new ResourceLocation("client/songs/" + currentStation.getTitle());
                ThreadDownloadImageData var6 = new ThreadDownloadImageData(null, currentStation.getCoverURL(), this.dashIcon, null);
                Minecraft.getMinecraft().renderEngine.loadTexture(currentStation.currentResource, var6);
            }

            ResourceLocation var5 = currentStation.currentResource == null ? this.dashIcon : currentStation.currentResource;
            RenderUtil.renderIcon(var5, this.newHeight / 2.0F, this.xPosition, this.yPosition);
            float var10 = this.xPosition + 50.0F;
            if (this.mc.currentScreen != OverlayGui.getInstance()) {
                var10 = this.xPosition + 34.0F;
            } else {
                boolean var7 = this.isMouseInside(var1, var2) && var1 > this.xPosition + 34.0F && var1 < this.xPosition + 44.0F && var2 < this.yPosition + this.newHeight;
                if (!DashUtil.isActive()) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, var7 ? 1.0F : 0.29885057F * 2.6769233F);
                    RenderUtil.renderIcon(this.playIcon, 6.0F, this.xPosition + 34.0F, this.yPosition + 6.9339623F * 1.0816326F);
                } else {
                    Gui.drawRect(this.xPosition + 36.0F, this.yPosition + 9.0F, this.xPosition + 38.0F, this.yPosition + this.newHeight - 11.0F, var7 ? -1 : -1342177281);
                    Gui.drawRect(this.xPosition + 40.0F, this.yPosition + 9.0F, this.xPosition + 42.0F, this.yPosition + this.newHeight - 11.0F, var7 ? -1 : -1342177281);
                }
            }

            String var11 = currentStation.getTitle();
            float var10003;
            if ((float) CheatBreaker.getInstance().playRegular16px.getStringWidth(var11) > this.width - 52.0F) {
                var10003 = this.yPosition + 4.0F;
                CheatBreaker.getInstance().playRegular12px.drawString(var11, var10, var10003, -1);
            } else {
                var10003 = this.yPosition + 4.0F;
                CheatBreaker.getInstance().playRegular16px.drawString(var11, var10, var10003, -1);
            }

            CheatBreaker.getInstance().playRegular12px.drawString(currentStation.getArtist(), var10, this.yPosition + 14.0F, -1342177281);
        }

        float var9 = this.fadeTime.inOutFade(this.isMouseInside(var1, var2) && var3);
        if (this.fadeTime.isZeroOrLess()) {
            this.setElementSize(this.xPosition, this.yPosition, this.width, this.newHeight + 120.0F * var9);
            this.isFading = true;
        } else if (!this.fadeTime.isZeroOrLess() && !this.isMouseInside(var1, var2)) {
            this.isFading = false;
        }

        if (this.isFading) {
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            OverlayGui var13 = OverlayGui.getInstance();
            RenderUtil.startScissorBox((int) this.xPosition, (int) (this.yPosition + this.newHeight), (int) (this.xPosition + this.width), (int) (this.yPosition + this.newHeight + (this.height - this.newHeight) * var9), (float) ((int) ((float) var13.getScaledResolution().getScaleFactor() * var13.getScaleFactor())), (int) var13.getScaledHeight());
            Gui.drawRect(this.xPosition, this.yPosition + this.newHeight, this.xPosition + this.width, this.yPosition + this.height, -14540254);
            this.scrollbar.drawScrollable(var1, var2, var3);
            Iterator<RadioStationElement> var12 = this.radioStationElements.iterator();

            while (true) {
                RadioStationElement var8;
                do {
                    if (!var12.hasNext()) {
                        this.scrollbar.handleElementDraw(var1, var2, var3);
                        this.volumeSlider.drawElementHover(var1, var2, var3);
                        this.filterTextField.handleElementDraw(var1, var2, var3);
                        this.pinButton.handleElementDraw(var1, var2, var3);
                        GL11.glDisable(3089);
                        GL11.glPopMatrix();
                        return;
                    }

                    var8 = var12.next();
                } while (!this.handleElementMouseClicked(var8));

                var8.handleElementDraw(var1, var2 - this.scrollbar.getPosition(), var3 && !this.scrollbar.isButtonHeld() && !this.scrollbar.isMouseInside(var1, var2));
            }
        }
    }

    public void handleElementMouse() {
        this.scrollbar.handleElementMouse();
    }

    public void handleElementUpdate() {
        this.filterTextField.handleElementUpdate();
        this.pinButton.handleElementUpdate();
    }

    public void handleElementClose() {
        this.filterTextField.handleElementClose();
        this.pinButton.handleElementClose();
    }

    public void keyTyped(char var1, int var2) {
        this.filterTextField.keyTyped(var1, var2);
        this.pinButton.keyTyped(var1, var2);
        this.scrollbar.keyTyped(var1, var2);
        if (this.filterTextField.isFocused()) {
            this.updateElementSize();
        }

    }

    public boolean onMouseClick(float var1, float var2, int var3) {
        if (!this.filterTextField.isMouseInside(var1, var2) && this.filterTextField.isFocused()) {
            this.filterTextField.setFocused(false);
        }

        return false;
    }

    public boolean handleElementMouseClicked(float var1, float var2, int var3, boolean var4) {
        this.filterTextField.handleElementMouseClicked(var1, var2, var3, var4);
        if (this.filterTextField.isFocused() && var3 == 1 && this.filterTextField.getText().equals("")) {
            this.updateElementSize();
        }

        if (!var4) {
            return false;
        } else {
            boolean var5 = this.isMouseInside(var1, var2) && var1 > this.xPosition + 34.0F && var1 < this.xPosition + 44.0F && var2 < this.yPosition + this.newHeight;
            if (var5) {
                if (!DashUtil.isActive()) {
                    CheatBreaker.getInstance().getDashManager().getCurrentStation().endStream();
                } else {
                    DashUtil.end();
                }
            }

            float var6 = this.fadeTime.inOutFade(this.isMouseInside(var1, var2) && var4);
            if (this.fadeTime.isHovered()) {
                this.volumeSlider.handleElementMouseClicked(var1, var2, var3, var4);
                this.scrollbar.handleElementMouseClicked(var1, var2, var3, var4);
                this.filterTextField.handleElementMouseClicked(var1, var2, var3, var4);
                this.pinButton.handleElementMouseClicked(var1, var2, var3, var4);
                boolean var7 = var1 > (float) ((int) this.xPosition) && var1 < (float) ((int) (this.xPosition + this.width)) && var2 > (float) ((int) (this.yPosition + this.newHeight + 21.0F)) && var2 < (float) ((int) (this.yPosition + this.newHeight + 21.0F + (this.height - this.newHeight - 21.0F) * var6));
                if (var7) {
                    for (RadioStationElement var9 : this.radioStationElements) {
                        if (this.handleElementMouseClicked(var9) && var9.handleElementMouseClicked(var1, var2 - this.scrollbar.getPosition(), var3, var4)) {
                            break;
                        }
                    }
                }

                if (this.pinButton.isMouseInside(var1, var2)) {
                    this.cb.getGlobalSettings().pinRadio.setValue(!(Boolean) this.cb.getGlobalSettings().pinRadio.getValue());
                    this.pinButton.setText((Boolean) this.cb.getGlobalSettings().pinRadio.getValue() ? "Unpin" : "Pin");
                }
            }

            if (this.isMouseInside(var1, var2) && var2 < this.yPosition + this.newHeight && !var5 && !this.volumeSlider.isMouseInside(var1, var2) && !this.scrollbar.isMouseInside(var1, var2)) {
                this.setPosition(var1, var2);
            }

            return super.handleElementMouseClicked(var1, var2, var3, var4);
        }
    }
}

