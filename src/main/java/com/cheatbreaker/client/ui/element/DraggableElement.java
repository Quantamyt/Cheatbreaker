package com.cheatbreaker.client.ui.element;

import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.sun.javafx.geom.Vec2d;
import java.util.concurrent.atomic.AtomicBoolean;
import org.lwjgl.input.Mouse;

public abstract class DraggableElement extends AbstractElement {
    private final Vec2d position = new Vec2d();
    private final AtomicBoolean dragging = new AtomicBoolean();

    protected void onDrag(float f, float f2) {
        if (this.dragging.get()) {
            if (!Mouse.isButtonDown(0)) {
                this.dragging.set(false);
                return;
            }
            double d = (double)f - this.position.x;
            double d2 = (double)f2 - this.position.y;
            this.setElementSize((float)d, (float)d2, this.width, this.height);
        }
    }

    protected void setPosition(float f, float f2) {
        this.position.set(f - this.xPosition, f2 - this.yPosition);
        this.dragging.set(true);
    }

    protected void getPosition() {
        if (this.dragging.get()) {
            this.dragging.set(false);
        }
    }
}
