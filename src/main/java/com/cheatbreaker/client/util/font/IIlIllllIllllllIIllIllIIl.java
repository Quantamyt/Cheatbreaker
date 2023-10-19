package com.cheatbreaker.client.util.font;

import java.util.NoSuchElementException;

class IIlIllllIllllllIIllIllIIl
        extends IllIlIllllllIIIIIllIllIlI {
    int lIIIIlIIllIIlIIlIIIlIIllI;
    int lIIIIIIIIIlIllIIllIlIIlIl;
    final int IlllIIIlIlllIllIlIIlllIlI;
    final lIIllIIlIIIIIllIlIIllIIIl IIIIllIlIIIllIlllIlllllIl;

    IIlIllllIllllllIIllIllIIl(lIIllIIlIIIIIllIlIIllIIIl lIIllIIlIIIIIllIlIIllIIIl2, int n) {
        this.IIIIllIlIIIllIlllIlllllIl = lIIllIIlIIIIIllIlIIllIIIl2;
        this.IlllIIIlIlllIllIlIIlllIlI = n;
        this.lIIIIlIIllIIlIIlIIIlIIllI = this.IlllIIIlIlllIllIlIIlllIlI;
        this.lIIIIIIIIIlIllIIllIlIIlIl = -1;
    }

    @Override
    public boolean hasNext() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI < this.IIIIllIlIIIllIlllIlllllIl.size();
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
        return this.IIIIllIlIIIllIlllIlllllIl.get(this.lIIIIIIIIIlIllIIllIlIIlIl);
    }

    @Override
    public Object previous() {
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        }
        this.lIIIIIIIIIlIllIIllIlIIlIl = --this.lIIIIlIIllIIlIIlIIIlIIllI;
        return this.IIIIllIlIIIllIlllIlllllIl.get(this.lIIIIlIIllIIlIIlIIIlIIllI);
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
