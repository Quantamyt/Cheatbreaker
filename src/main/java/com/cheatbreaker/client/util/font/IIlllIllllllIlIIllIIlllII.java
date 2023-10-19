package com.cheatbreaker.client.util.font;

public abstract class IIlllIllllllIlIIllIIlllII
        extends lllllIlIIllIllIIIIllIIlIl
        implements llIIIIlllIIIIIIIllIllllll {
    protected IIlllIllllllIlIIllIIlllII() {
    }

    @Override
    public int lIIIIlIIllIIlIIlIIIlIIllI(int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasPrevious()) {
            this.previous();
        }
        return n - n2 - 1;
    }
}
