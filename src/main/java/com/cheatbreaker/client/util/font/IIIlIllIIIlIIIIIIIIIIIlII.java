package com.cheatbreaker.client.util.font;

import java.util.NoSuchElementException;

class IIIlIllIIIlIIIIIIIIIIIlII
        extends IllIlIllllllIIIIIllIllIlI {
    private final Object[] lIIIIlIIllIIlIIlIIIlIIllI;
    private final int lIIIIIIIIIlIllIIllIlIIlIl;
    private final int IlllIIIlIlllIllIlIIlllIlI;
    private int IIIIllIlIIIllIlllIlllllIl;

    public IIIlIllIIIlIIIIIIIIIIIlII(Object[] objectArray, int n, int n2) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = objectArray;
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
    }

    @Override
    public boolean hasNext() {
        return this.IIIIllIlIIIllIlllIlllllIl < this.IlllIIIlIlllIllIlIIlllIlI;
    }

    @Override
    public boolean hasPrevious() {
        return this.IIIIllIlIIIllIlllIlllllIl > 0;
    }

    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.lIIIIlIIllIIlIIlIIIlIIllI[this.lIIIIIIIIIlIllIIllIlIIlIl + this.IIIIllIlIIIllIlllIlllllIl++];
    }

    @Override
    public Object previous() {
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        }
        return this.lIIIIlIIllIIlIIlIIIlIIllI[this.lIIIIIIIIIlIllIIllIlIIlIl + --this.IIIIllIlIIIllIlllIlllllIl];
    }

    @Override
    public int lIIIIIIIIIlIllIIllIlIIlIl(int n) {
        if (n <= this.IlllIIIlIlllIllIlIIlllIlI - this.IIIIllIlIIIllIlllIlllllIl) {
            this.IIIIllIlIIIllIlllIlllllIl += n;
            return n;
        }
        n = this.IlllIIIlIlllIllIlIIlllIlI - this.IIIIllIlIIIllIlllIlllllIl;
        this.IIIIllIlIIIllIlllIlllllIl = this.IlllIIIlIlllIllIlIIlllIlI;
        return n;
    }

    @Override
    public int lIIIIlIIllIIlIIlIIIlIIllI(int n) {
        if (n <= this.IIIIllIlIIIllIlllIlllllIl) {
            this.IIIIllIlIIIllIlllIlllllIl -= n;
            return n;
        }
        n = this.IIIIllIlIIIllIlllIlllllIl;
        this.IIIIllIlIIIllIlllIlllllIl = 0;
        return n;
    }

    @Override
    public int nextIndex() {
        return this.IIIIllIlIIIllIlllIlllllIl;
    }

    @Override
    public int previousIndex() {
        return this.IIIIllIlIIIllIlllIlllllIl - 1;
    }
}
