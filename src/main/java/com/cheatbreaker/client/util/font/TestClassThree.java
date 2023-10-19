package com.cheatbreaker.client.util.font;

// Decompiled with: CFR 0.152
// Class Version: 8
public class TestClassThree {
    private String lIIIIlIIllIIlIIlIIIlIIllI;
    private int lIIIIIIIIIlIllIIllIlIIlIl;
    private boolean IlllIIIlIlllIllIlIIlllIlI;

    public TestClassThree(String string, int n, boolean bl) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = string;
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = bl;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        TestClassThree testClassThree = (TestClassThree)object;
        return this.lIIIIIIIIIlIllIIllIlIIlIl == testClassThree.lIIIIIIIIIlIllIIllIlIIlIl && this.IlllIIIlIlllIllIlIIlllIlI == testClassThree.IlllIIIlIlllIllIlIIlllIlI && this.lIIIIlIIllIIlIIlIIIlIIllI.equals(testClassThree.lIIIIlIIllIIlIIlIIIlIIllI);
    }

    public int hashCode() {
        int n = this.lIIIIlIIllIIlIIlIIIlIIllI.hashCode();
        n = 31 * n + this.lIIIIIIIIIlIllIIllIlIIlIl;
        n = 31 * n + (this.IlllIIIlIlllIllIlIIlllIlI ? 1 : 0);
        return n;
    }

    static String lIIIIlIIllIIlIIlIIIlIIllI(TestClassThree testClassThree, String string) {
        testClassThree.lIIIIlIIllIIlIIlIIIlIIllI = string;
        return testClassThree.lIIIIlIIllIIlIIlIIIlIIllI;
    }

    static int lIIIIlIIllIIlIIlIIIlIIllI(TestClassThree testClassThree, int n) {
        testClassThree.lIIIIIIIIIlIllIIllIlIIlIl = n;
        return testClassThree.lIIIIIIIIIlIllIIllIlIIlIl;
    }

    static boolean lIIIIlIIllIIlIIlIIIlIIllI(TestClassThree testClassThree, boolean bl) {
        testClassThree.IlllIIIlIlllIllIlIIlllIlI = bl;
        return testClassThree.IlllIIIlIlllIllIlIIlllIlI;
    }
}

