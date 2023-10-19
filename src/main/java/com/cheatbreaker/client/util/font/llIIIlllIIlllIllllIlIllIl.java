package com.cheatbreaker.client.util.font;

class llIIIlllIIlllIllllIlIllIl
        extends lllllIlIIllIllIIIIllIIlIl {
    final ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI;
    final lllIIIIIlIIIIIIIIlllIIIII lIIIIIIIIIlIllIIllIlIIlIl;

    llIIIlllIIlllIllllIlIllIl(lllIIIIIlIIIIIIIIlllIIIII lllIIIIIlIIIIIIIIlllIIIII2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = lllIIIIIlIIIIIIIIlllIIIII2;
        this.lIIIIlIIllIIlIIlIIIlIIllI = this.lIIIIIIIIIlIllIIllIlIIlIl.lIIIIlIIllIIlIIlIIIlIIllI.IIIIllIlIIIllIlllIlllllIl().lIIIIlIIllIIlIIlIIIlIIllI();
    }

    @Deprecated
    public Object next() {
        return ((llIlIlIlIIlIIllIllIIlllIl)this.lIIIIlIIllIIlIIlIIIlIIllI.next()).getValue();
    }

    @Override
    public boolean hasNext() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI.hasNext();
    }
}
