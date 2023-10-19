package com.cheatbreaker.client.util.font;

import java.util.Iterator;

final class llIIIIlIlIlIlllIIIlllllIl
        extends AbstractClassThree {
    final TestClassFour lIIIIlIIllIIlIIlIIIlIIllI;

    private llIIIIlIlIlIlllIIIlllllIl(TestClassFour testClassFour) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = testClassFour;
    }

    @Override
    public ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI() {
        return new IlIIllllIIllllIIllIIlIIlI(this.lIIIIlIIllIIlIIlIIIlIIllI);
    }

    @Override
    public int size() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI.IIIlIIllllIIllllllIlIIIll;
    }

    @Override
    public boolean contains(Object object) {
        return this.lIIIIlIIllIIlIIlIIIlIIllI.containsKey(object);
    }

    @Override
    public boolean lIIIIIIIIIlIllIIllIlIIlIl(Object object) {
        int n = this.lIIIIlIIllIIlIIlIIIlIIllI.IIIlIIllllIIllllllIlIIIll;
        this.lIIIIlIIllIIlIIlIIIlIIllI.remove(object);
        return this.lIIIIlIIllIIlIIlIIIlIIllI.IIIlIIllllIIllllllIlIIIll != n;
    }

    @Override
    public void clear() {
        this.lIIIIlIIllIIlIIlIIIlIIllI.clear();
    }

    @Override
    public Iterator iterator() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }

    llIIIIlIlIlIlllIIIlllllIl(TestClassFour testClassFour, IlIlIllIIllllIlllIlllIlII ilIlIllIIllllIlllIlllIlII) {
        this(testClassFour);
    }
}