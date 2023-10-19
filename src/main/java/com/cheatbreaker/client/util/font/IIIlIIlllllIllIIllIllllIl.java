package com.cheatbreaker.client.util.font;

class IIIlIIlllllIllIIllIllllIl
        extends llIllIIllIllIIlIIIIllIIII
        implements ITestClassFive {
    private final IllIIIlIIIllIIIlIIIllIIll IllIIIIIIIlIlIllllIIllIII;
    final TestClassFour lIIIIlIIllIIlIIlIIIlIIllI;

    private IIIlIIlllllIllIIllIllllIl(TestClassFour testClassFour) {
        super(testClassFour, null);
        this.lIIIIlIIllIIlIIlIIIlIIllI = testClassFour;
        this.IllIIIIIIIlIlIllllIIllIII = new IllIIIlIIIllIIIlIIIllIIll(this.lIIIIlIIllIIlIIlIIIlIIllI);
    }

    public IllIIIlIIIllIIIlIIIllIIll lIIIIlIIllIIlIIlIIIlIIllI() {
        this.IllIIIIIIIlIlIllllIIllIII.lIIIIlIIllIIlIIlIIIlIIllI = this.lIIIIIIIIIlIllIIllIlIIlIl();
        return this.IllIIIIIIIlIlIllllIIllIII;
    }

    public Object next() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }

    IIIlIIlllllIllIIllIllllIl(TestClassFour testClassFour, IlIlIllIIllllIlllIlllIlII ilIlIllIIllllIlllIlllIlII) {
        this(testClassFour);
    }
}
