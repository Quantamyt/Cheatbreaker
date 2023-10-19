package com.cheatbreaker.client.util.font;

import java.util.NoSuchElementException;

class llIllIIllIllIIlIIIIllIIII {
    int lIIIIIIIIIlIllIIllIlIIlIl;
    int IlllIIIlIlllIllIlIIlllIlI;
    int IIIIllIlIIIllIlllIlllllIl;
    boolean IIIIllIIllIIIIllIllIIIlIl;
    IIlIIlIIlIlIllIIIIlIIIIIl IlIlIIIlllIIIlIlllIlIllIl;
    final TestClassFour IIIllIllIlIlllllllIlIlIII;

    private llIllIIllIllIIlIIIIllIIII(TestClassFour testClassFour) {
        this.IIIllIllIlIlllllllIlIlIII = testClassFour;
        this.lIIIIIIIIIlIllIIllIlIIlIl = this.IIIllIllIlIlllllllIlIlIII.IIIlllIIIllIllIlIIIIIIlII;
        this.IlllIIIlIlllIllIlIIlllIlI = -1;
        this.IIIIllIlIIIllIlllIlllllIl = this.IIIllIllIlIlllllllIlIlIII.IIIlIIllllIIllllllIlIIIll;
        this.IIIIllIIllIIIIllIllIIIlIl = this.IIIllIllIlIlllllllIlIlIII.lIIlIlIllIIlIIIlIIIlllIII;
    }

    public boolean hasNext() {
        return this.IIIIllIlIIIllIlllIlllllIl != 0;
    }

    public int lIIIIIIIIIlIllIIllIlIIlIl() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        --this.IIIIllIlIIIllIlllIlllllIl;
        if (this.IIIIllIIllIIIIllIllIIIlIl) {
            this.IIIIllIIllIIIIllIllIIIlIl = false;
            this.IlllIIIlIlllIllIlIIlllIlI = this.IIIllIllIlIlllllllIlIlIII.IIIlllIIIllIllIlIIIIIIlII;
            return this.IlllIIIlIlllIllIlIIlllIlI;
        }
        Object[] objectArray = this.IIIllIllIlIlllllllIlIlIII.IlllIllIlIIIIlIIlIIllIIIl;
        do {
            if (--this.lIIIIIIIIIlIllIIllIlIIlIl >= 0) continue;
            this.IlllIIIlIlllIllIlIIlllIlI = Integer.MIN_VALUE;
            Object object = this.IlIlIIIlllIIIlIlllIlIllIl.get(-this.lIIIIIIIIIlIllIIllIlIIlIl - 1);
            int n = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(object.hashCode()) & this.IIIllIllIlIlllllllIlIlIII.llIIlllIIIIlllIllIlIlllIl;
            while (!object.equals(objectArray[n])) {
                n = n + 1 & this.IIIllIllIlIlllllllIlIlIII.llIIlllIIIIlllIllIlIlllIl;
            }
            return n;
        } while (objectArray[this.lIIIIIIIIIlIllIIllIlIIlIl] == null);
        this.IlllIIIlIlllIllIlIIlllIlI = this.lIIIIIIIIIlIllIIllIlIIlIl;
        return this.IlllIIIlIlllIllIlIIlllIlI;
    }

    private final void lIIIIlIIllIIlIIlIIIlIIllI(int n) {
        Object[] objectArray = this.IIIllIllIlIlllllllIlIlIII.IlllIllIlIIIIlIIlIIllIIIl;
        while (true) {
            Object object;
            int n2 = n;
            n = n2 + 1 & this.IIIllIllIlIlllllllIlIlIII.llIIlllIIIIlllIllIlIlllIl;
            while (true) {
                if ((object = objectArray[n]) == null) {
                    objectArray[n2] = null;
                    this.IIIllIllIlIlllllllIlIlIII.IlIlllIIIIllIllllIllIIlIl[n2] = null;
                    return;
                }
                int n3 = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(object.hashCode()) & this.IIIllIllIlIlllllllIlIlIII.llIIlllIIIIlllIllIlIlllIl;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.IIIllIllIlIlllllllIlIlIII.llIIlllIIIIlllIllIlIlllIl;
            }
            if (n < n2) {
                if (this.IlIlIIIlllIIIlIlllIlIllIl == null) {
                    this.IlIlIIIlllIIIlIlllIlIllIl = new IIlIIlIIlIlIllIIIIlIIIIIl(2);
                }
                this.IlIlIIIlllIIIlIlllIlIllIl.add(objectArray[n]);
            }
            objectArray[n2] = object;
            this.IIIllIllIlIlllllllIlIlIII.IlIlllIIIIllIllllIllIIlIl[n2] = this.IIIllIllIlIlllllllIlIlIII.IlIlllIIIIllIllllIllIIlIl[n];
        }
    }

    public void remove() {
        if (this.IlllIIIlIlllIllIlIIlllIlI == -1) {
            throw new IllegalStateException();
        }
        if (this.IlllIIIlIlllIllIlIIlllIlI == this.IIIllIllIlIlllllllIlIlIII.IIIlllIIIllIllIlIIIIIIlII) {
            this.IIIllIllIlIlllllllIlIlIII.lIIlIlIllIIlIIIlIIIlllIII = false;
            this.IIIllIllIlIlllllllIlIlIII.IlllIllIlIIIIlIIlIIllIIIl[this.IIIllIllIlIlllllllIlIlIII.IIIlllIIIllIllIlIIIIIIlII] = null;
            this.IIIllIllIlIlllllllIlIlIII.IlIlllIIIIllIllllIllIIlIl[this.IIIllIllIlIlllllllIlIlIII.IIIlllIIIllIllIlIIIIIIlII] = null;
        } else if (this.lIIIIIIIIIlIllIIllIlIIlIl >= 0) {
            this.lIIIIlIIllIIlIIlIIIlIIllI(this.IlllIIIlIlllIllIlIIlllIlI);
        } else {
            this.IIIllIllIlIlllllllIlIlIII.remove(this.IlIlIIIlllIIIlIlllIlIllIl.set(-this.lIIIIIIIIIlIllIIllIlIIlIl - 1, (Object)null));
            this.IlllIIIlIlllIllIlIIlllIlI = -1;
            return;
        }
        --this.IIIllIllIlIlllllllIlIlIII.IIIlIIllllIIllllllIlIIIll;
        this.IlllIIIlIlllIllIlIIlllIlI = -1;
    }

    public int lIIIIIIIIIlIllIIllIlIIlIl(int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.lIIIIIIIIIlIllIIllIlIIlIl();
        }
        return n - n2 - 1;
    }

    llIllIIllIllIIlIIIIllIIII(TestClassFour testClassFour, IlIlIllIIllllIlllIlllIlII ilIlIllIIllllIlllIlllIlII) {
        this(testClassFour);
    }
}
