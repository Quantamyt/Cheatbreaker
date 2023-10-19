package com.cheatbreaker.client.util.font;

import java.util.Iterator;

class TestClassEleven
        extends AbstractClassThree {
    final TestClassFive lIIIIlIIllIIlIIlIIIlIIllI;

    TestClassEleven(TestClassFive testClassFive) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = testClassFive;
    }

    @Override
    public boolean contains(Object object) {
        return this.lIIIIlIIllIIlIIlIIIlIIllI.containsKey(object);
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
        return new IlIlIIIIIIIlIIllIIIIllIII(this);
    }

    @Override
    public Iterator iterator() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }
}
