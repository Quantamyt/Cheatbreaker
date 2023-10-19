package com.cheatbreaker.client.util.font;

public abstract class lllllIlIIllIllIIIIllIIlIl
        implements ITestClassFive {
    protected lllllIlIIllIllIIIIllIIlIl() {
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lIIIIIIIIIlIllIIllIlIIlIl(int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.next();
        }
        return n - n2 - 1;
    }
}
