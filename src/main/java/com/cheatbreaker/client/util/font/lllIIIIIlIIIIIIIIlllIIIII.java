package com.cheatbreaker.client.util.font;

import java.util.Iterator;

class lllIIIIIlIIIIIIIIlllIIIII
        extends AbstractTestClassTwo {
    final TestClassFive lIIIIlIIllIIlIIlIIIlIIllI;

    lllIIIIIlIIIIIIIIlllIIIII(TestClassFive testClassFive) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = testClassFive;
    }

    @Override
    public boolean contains(Object object) {
        return this.lIIIIlIIllIIlIIlIIIlIIllI.containsValue(object);
    }

    @Override
    public int size() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI.size();
    }

    @Override
    public void clear() {
        this.lIIIIlIIllIIlIIlIIIlIIllI.clear();
    }

    @Override
    public ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI() {
        return new llIIIlllIIlllIllllIlIllIl(this);
    }

    @Override
    public Iterator iterator() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }
}
