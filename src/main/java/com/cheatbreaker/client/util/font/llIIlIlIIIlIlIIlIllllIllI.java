package com.cheatbreaker.client.util.font;

import java.util.NoSuchElementException;

class llIIlIlIIIlIlIIlIllllIllI
        extends IllIlIllllllIIIIIllIllIlI {
    int lIIIIlIIllIIlIIlIIIlIIllI;
    int lIIIIIIIIIlIllIIllIlIIlIl;
    final int IlllIIIlIlllIllIlIIlllIlI;
    final IIlIIlIIlIlIllIIIIlIIIIIl IIIIllIlIIIllIlllIlllllIl;

    llIIlIlIIIlIlIIlIllllIllI(IIlIIlIIlIlIllIIIIlIIIIIl iIlIIlIIlIlIllIIIIlIIIIIl, int n) {
        this.IIIIllIlIIIllIlllIlllllIl = iIlIIlIIlIlIllIIIIlIIIIIl;
        this.IlllIIIlIlllIllIlIIlllIlI = n;
        this.lIIIIlIIllIIlIIlIIIlIIllI = this.IlllIIIlIlllIllIlIIlllIlI;
        this.lIIIIIIIIIlIllIIllIlIIlIl = -1;
    }

    @Override
    public boolean hasNext() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI < this.IIIIllIlIIIllIlllIlllllIl.IIIIllIlIIIllIlllIlllllIl;
    }

    @Override
    public boolean hasPrevious() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI > 0;
    }

    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.lIIIIIIIIIlIllIIllIlIIlIl = this.lIIIIlIIllIIlIIlIIIlIIllI++;
        return this.IIIIllIlIIIllIlllIlllllIl.IlllIIIlIlllIllIlIIlllIlI[this.lIIIIIIIIIlIllIIllIlIIlIl];
    }

    @Override
    public Object previous() {
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        }
        this.lIIIIIIIIIlIllIIllIlIIlIl = --this.lIIIIlIIllIIlIIlIIIlIIllI;
        return this.IIIIllIlIIIllIlllIlllllIl.IlllIIIlIlllIllIlIIlllIlI[this.lIIIIlIIllIIlIIlIIIlIIllI];
    }

    @Override
    public int nextIndex() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI;
    }

    @Override
    public int previousIndex() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI - 1;
    }

    @Override
    public void add(Object object) {
        this.IIIIllIlIIIllIlllIlllllIl.add(this.lIIIIlIIllIIlIIlIIIlIIllI++, object);
        this.lIIIIIIIIIlIllIIllIlIIlIl = -1;
    }

    @Override
    public void set(Object object) {
        if (this.lIIIIIIIIIlIllIIllIlIIlIl == -1) {
            throw new IllegalStateException();
        }
        this.IIIIllIlIIIllIlllIlllllIl.set(this.lIIIIIIIIIlIllIIllIlIIlIl, object);
    }

    @Override
    public void remove() {
        if (this.lIIIIIIIIIlIllIIllIlIIlIl == -1) {
            throw new IllegalStateException();
        }
        this.IIIIllIlIIIllIlllIlllllIl.remove(this.lIIIIIIIIIlIllIIllIlIIlIl);
        if (this.lIIIIIIIIIlIllIIllIlIIlIl < this.lIIIIlIIllIIlIIlIIIlIIllI) {
            --this.lIIIIlIIllIIlIIlIIIlIIllI;
        }
        this.lIIIIIIIIIlIllIIllIlIIlIl = -1;
    }
}
