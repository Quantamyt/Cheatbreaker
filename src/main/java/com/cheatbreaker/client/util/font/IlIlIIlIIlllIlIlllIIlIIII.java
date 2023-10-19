package com.cheatbreaker.client.util.font;

class IlIlIIlIIlllIlIlllIIlIIII
        extends llIllIIllIllIIlIIIIllIIII
        implements ITestClassFive {
    private IllIIIlIIIllIIIlIIIllIIll IllIIIIIIIlIlIllllIIllIII;
    final TestClassFour lIIIIlIIllIIlIIlIIIlIIllI;

    private IlIlIIlIIlllIlIlllIIlIIII(TestClassFour testClassFour) {
        super(testClassFour, null);
        this.lIIIIlIIllIIlIIlIIIlIIllI = testClassFour;
    }

    public llIlIlIlIIlIIllIllIIlllIl lIIIIlIIllIIlIIlIIIlIIllI() {
        this.IllIIIIIIIlIlIllllIIllIII = new IllIIIlIIIllIIIlIIIllIIll(this.lIIIIlIIllIIlIIlIIIlIIllI, this.lIIIIIIIIIlIllIIllIlIIlIl());
        return this.IllIIIIIIIlIlIllllIIllIII;
    }

    @Override
    public void remove() {
        super.remove();
        this.IllIIIIIIIlIlIllllIIllIII.lIIIIlIIllIIlIIlIIIlIIllI = -1;
    }

    public Object next() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }

    IlIlIIlIIlllIlIlllIIlIIII(TestClassFour testClassFour, IlIlIllIIllllIlllIlllIlII ilIlIllIIllllIlllIlllIlII) {
        this(testClassFour);
    }
}
