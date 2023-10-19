package com.cheatbreaker.client.ui.element;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.element.type.FlatButtonElement;
import com.cheatbreaker.client.ui.element.type.ScrollableElement;
import com.cheatbreaker.client.ui.fading.CosineFade;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.cheatbreaker.client.util.friend.data.Friend;
import com.cheatbreaker.client.util.thread.AliasesThread;
import lombok.Getter;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class AliasesElement extends DraggableElement {
    private final ScrollableElement scrollbar;
    @Getter
    private final Friend friend;
    private final FlatButtonElement closeButton;
    private final CosineFade fade;
    @Getter
    private final List<String> aliases = new ArrayList<>();

    public AliasesElement(Friend friend) {
        this.scrollbar = new ScrollableElement(this);
        this.friend = friend;
        this.closeButton = new FlatButtonElement("X");
        this.fade = new CosineFade(1500L);
        this.fade.startAnimation();
        this.fade.loopAnimation();
        new AliasesThread(this).start();
    }

    private float fadeAmount() {
        return this.fade.getFadeAmount() * 2.0f - 1.0f;
    }

    @Override
    public void setElementSize(float f, float y, float width, float height) {
        super.setElementSize(f, y, width, height);
        this.scrollbar.setElementSize(f + width - (float) 4, y, (float) 4, height);
        this.scrollbar.setScrollAmount(height);
        this.closeButton.setElementSize(f + width - (float) 12, y + 2.0f, (float) 10, 10);
    }

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        this.onDrag(f, f2);
        this.scrollbar.drawScrollable(f, f2, bl);
        Gui.drawBoxWithOutLine(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 0.06666667f * 7.5f, -16777216, -14869219);
        CheatBreaker.getInstance().playRegular14px.drawString(this.friend.getName(), this.xPosition + (float) 4, this.yPosition + (float) 4, -1);
        Gui.drawRect(this.xPosition + (float) 3, this.yPosition + 15.0f, this.xPosition + this.width - (float) 3, this.yPosition + 0.9791667f * 15.829787f, 0x2FFFFFFF);
        if (this.aliases.isEmpty()) {
            Gui.drawRect(this.xPosition + (float) 4, this.yPosition + this.height - (float) 9, this.xPosition + this.width - (float) 4, this.yPosition + this.height - (float) 5, -13158601);
            float f3 = this.xPosition + this.width / 2.0f - (float) 10 + (this.width - 28.0f) * this.fadeAmount() / 2.0f;
            Gui.drawRect(f3, this.yPosition + this.height - (float) 9, f3 + 20.0f, this.yPosition + this.height - (float) 5, -4180940);
        }
        int n = 0;
        for (String string : this.aliases) {
            CheatBreaker.getInstance().playRegular14px.drawString(string, this.xPosition + 4.0f, this.yPosition + 18.0f + (float) (n * 10), -1);
            ++n;
        }
        this.scrollbar.handleElementDraw(f, f2, bl);
        this.closeButton.handleElementDraw(f, f2, bl);
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        this.scrollbar.handleElementMouseClicked(f, f2, n, bl);
        this.closeButton.handleElementMouseClicked(f, f2, n, bl);
        if (this.closeButton.isMouseInside(f, f2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            OverlayGui.getInstance().removeElement(this);
            return true;
        }
        if (this.isMouseInside(f, f2)) {
            this.setPosition(f, f2);
        }
        return false;
    }
}
