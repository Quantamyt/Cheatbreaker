package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.ui.mainmenu.AbstractElement;

import java.util.ArrayList;
import java.util.List;

public class ElementListElement<T extends AbstractElement> extends AbstractElement {
    protected final List<T> elements = new ArrayList();

    public ElementListElement(List list) {
        this.elements.addAll(list);
    }

    @Override
    public void handleElementDraw(float f, float f2, boolean bl) {
        for (AbstractElement abstractElement : this.elements) {
            abstractElement.drawElementHover(f, f2, bl);
        }
    }

    @Override
    public void handleElementClose() {
        this.elements.forEach(AbstractElement::handleElementClose);
    }

    @Override
    public void handleElementUpdate() {
        this.elements.forEach(AbstractElement::handleElementUpdate);
    }

    @Override
    public void keyTyped(char c, int n) {
        this.elements.forEach(abstractElement -> abstractElement.keyTyped(c, n));
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        boolean bl2 = false;
        for (AbstractElement abstractElement : this.elements) {
            if (bl2) break;
            bl2 = abstractElement.handleElementMouseClicked(f, f2, n, bl);
        }
        return bl2;
    }

    @Override
    public boolean onMouseMoved(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        boolean bl2 = false;
        for (AbstractElement abstractElement : this.elements) {
            if (bl2) break;
            bl2 = abstractElement.onMouseMoved(f, f2, n, bl);
        }
        return bl2;
    }

    public List<T> getElements() {
        return this.elements;
    }
}
