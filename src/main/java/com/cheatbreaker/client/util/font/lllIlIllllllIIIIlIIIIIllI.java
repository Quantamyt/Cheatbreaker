package com.cheatbreaker.client.util.font;

import java.util.NoSuchElementException;

class lllIlIllllllIIIIlIIIIIllI
        extends lllllIlIIllIllIIIIllIIlIl {
    final ITestClassFive[] lIIIIlIIllIIlIIlIIIlIIllI;
    int lIIIIIIIIIlIllIIllIlIIlIl;
    int IlllIIIlIlllIllIlIIlllIlI;
    int IIIIllIlIIIllIlllIlllllIl = -1;

    public lllIlIllllllIIIIlIIIIIllI(ITestClassFive[] iTestClassFiveArray, int n, int n2) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = iTestClassFiveArray;
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
        this.lIIIIlIIllIIlIIlIIIlIIllI();
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI() {
        while (this.IlllIIIlIlllIllIlIIlllIlI != 0 && !this.lIIIIlIIllIIlIIlIIIlIIllI[this.lIIIIIIIIIlIllIIllIlIIlIl].hasNext()) {
            --this.IlllIIIlIlllIllIlIIlllIlI;
            ++this.lIIIIIIIIIlIllIIllIlIIlIl;
        }
    }

    @Override
    public boolean hasNext() {
        return this.IlllIIIlIlllIllIlIIlllIlI > 0;
    }

    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.IIIIllIlIIIllIlllIlllllIl = this.lIIIIIIIIIlIllIIllIlIIlIl;
        Object e = this.lIIIIlIIllIIlIIlIIIlIIllI[this.IIIIllIlIIIllIlllIlllllIl].next();
        this.lIIIIlIIllIIlIIlIIIlIIllI();
        return e;
    }

    @Override
    public void remove() {
        if (this.IIIIllIlIIIllIlllIlllllIl == -1) {
            throw new IllegalStateException();
        }
        this.lIIIIlIIllIIlIIlIIIlIIllI[this.IIIIllIlIIIllIlllIlllllIl].remove();
    }

    @Override
    public int lIIIIIIIIIlIllIIllIlIIlIl(int n) {
        this.IIIIllIlIIIllIlllIlllllIl = -1;
        int n2 = 0;
        while (n2 < n && this.IlllIIIlIlllIllIlIIlllIlI != 0) {
            n2 += this.lIIIIlIIllIIlIIlIIIlIIllI[this.lIIIIIIIIIlIllIIllIlIIlIl].lIIIIIIIIIlIllIIllIlIIlIl(n - n2);
            if (this.lIIIIlIIllIIlIIlIIIlIIllI[this.lIIIIIIIIIlIllIIllIlIIlIl].hasNext()) break;
            --this.IlllIIIlIlllIllIlIIlllIlI;
            ++this.lIIIIIIIIIlIllIIllIlIIlIl;
        }
        return n2;
    }
}
