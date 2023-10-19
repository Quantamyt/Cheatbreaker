package com.cheatbreaker.client.util.font;

import java.util.NoSuchElementException;

class IIIIllIIIlllIlIIllIIlllll
        extends IllIlIllllllIIIIIllIllIlI {
    private final Object lIIIIlIIllIIlIIlIIIlIIllI;
    private int lIIIIIIIIIlIllIIllIlIIlIl;

    public IIIIllIIIlllIlIIllIIlllll(Object object) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = object;
    }

    @Override
    public boolean hasNext() {
        return this.lIIIIIIIIIlIllIIllIlIIlIl == 0;
    }

    @Override
    public boolean hasPrevious() {
        return this.lIIIIIIIIIlIllIIllIlIIlIl == 1;
    }

    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.lIIIIIIIIIlIllIIllIlIIlIl = 1;
        return this.lIIIIlIIllIIlIIlIIIlIIllI;
    }

    @Override
    public Object previous() {
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        }
        this.lIIIIIIIIIlIllIIllIlIIlIl = 0;
        return this.lIIIIlIIllIIlIIlIIIlIIllI;
    }

    @Override
    public int nextIndex() {
        return this.lIIIIIIIIIlIllIIllIlIIlIl;
    }

    @Override
    public int previousIndex() {
        return this.lIIIIIIIIIlIllIIllIlIIlIl - 1;
    }
}
