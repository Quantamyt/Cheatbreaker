package com.cheatbreaker.client.audio.music.elements;

import com.cheatbreaker.client.*;
import com.cheatbreaker.client.audio.music.data.Station;
import com.cheatbreaker.client.audio.music.util.DashUtil;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RadioStationElement extends AbstractElement {
    private final Station station;
    private final ResourceLocation starIcon = new ResourceLocation("client/icons/star-21.png");
    private final ResourceLocation starFilledIcon = new ResourceLocation("client/icons/star-filled-21.png");
    private final RadioElement parent;

    public RadioStationElement(RadioElement radioElement, Station station) {
        this.parent = radioElement;
        this.station = station;
    }

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        if (this.isMouseInsideElement(f, f2) && bl) {
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + 22.0f, this.yPosition + this.height, -13158601);
        } else if (this.isMouseInside(f, f2) && bl) {
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -13158601);
        }
        boolean favorite = this.station.isFavourite();
        if (favorite) {
            GL11.glColor4f(1.1833333f * 0.8028169f, 0.75956047f * 0.9479167f, 0.14558825f * 1.030303f, 1.0f);
        } else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        boolean active = CheatBreaker.getInstance().getDashManager().getCurrentStation() == this.station;
        RenderUtil.renderIcon(favorite ? this.starFilledIcon : this.starIcon, 5.0f, this.xPosition + 6.0f, this.yPosition + 5.0f);
        CheatBreaker.getInstance().playRegular14px.drawString(this.station.getName(), this.xPosition + 24.0f, this.yPosition + 0.627451f * 2.390625f, active ? -13369549 : -1);
        CheatBreaker.getInstance().playRegular14px.drawString(this.station.getGenre(), this.xPosition + 24.0f, this.yPosition + 2.375f * 4.0f, -1342177281);
    }

    private boolean isMouseInsideElement(float f, float f2) {
        return this.isMouseInside(f, f2) && f < this.xPosition + 22.0f;
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        if (this.isMouseInsideElement(f, f2) && bl) {
            this.station.setFavourite(!this.station.isFavourite());
            this.parent.updateElementSize();
            return true;
        }
                if (this.isMouseInside(f, f2) && bl) {
            if (DashUtil.isActive()) {
                DashUtil.end();
            }
            this.station.play = true;
            CheatBreaker.getInstance().getDashManager().getDashQueueThread().offerStation(this.station);
            CheatBreaker.getInstance().getDashManager().setCurrentStation(this.station);
        }
        return false;
    }

    public Station getStation() {
        return this.station;
    }
}
