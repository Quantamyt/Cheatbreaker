package com.cheatbreaker.client.util.font;

import java.util.Iterator;
import java.util.Map;

final class TestClassTen
        extends AbstractClassThree
        implements ITestClassSix {
    final TestClassFour lIIIIlIIllIIlIIlIIIlIIllI;

    private TestClassTen(TestClassFour testClassFour) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = testClassFour;
    }

    @Override
    public ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI() {
        return new IlIlIIlIIlllIlIlllIIlIIII(this.lIIIIlIIllIIlIIlIIIlIIllI, null);
    }

    @Override
    public ITestClassFive lIIIIIIIIIlIllIIllIlIIlIl() {
        return new IIIlIIlllllIllIIllIllllIl(this.lIIIIlIIllIIlIIlIIIlIIllI, null);
    }

    @Override
    public boolean contains(Object object) {
        if (!(object instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry)object;
        Object k = entry.getKey();
        Object v = entry.getValue();
        if (k == null) {
            return this.lIIIIlIIllIIlIIlIIIlIIllI.lIIlIlIllIIlIIIlIIIlllIII && (this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI.IIIlllIIIllIllIlIIIIIIlII] == null ? v == null : this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI.IIIlllIIIllIllIlIIIIIIlII].equals(v));
        }
        Object[] objectArray = this.lIIIIlIIllIIlIIlIIIlIIllI.IlllIllIlIIIIlIIlIIllIIIl;
        int n = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(k.hashCode()) & this.lIIIIlIIllIIlIIlIIIlIIllI.llIIlllIIIIlllIllIlIlllIl;
        Object object2 = objectArray[n];
        if (object2 == null) {
            return false;
        }
        if (k.equals(object2)) {
            return this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[n] == null ? v == null : this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[n].equals(v);
        }
        do {
            if ((object2 = objectArray[n = n + 1 & this.lIIIIlIIllIIlIIlIIIlIIllI.llIIlllIIIIlllIllIlIlllIl]) != null) continue;
            return false;
        } while (!k.equals(object2));
        return this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[n] == null ? v == null : this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[n].equals(v);
    }

    @Override
    public boolean lIIIIIIIIIlIllIIllIlIIlIl(Object object) {
        if (!(object instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry)object;
        Object k = entry.getKey();
        Object v = entry.getValue();
        if (k == null) {
            if (this.lIIIIlIIllIIlIIlIIIlIIllI.lIIlIlIllIIlIIIlIIIlllIII && (this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI.IIIlllIIIllIllIlIIIIIIlII] == null ? v == null : this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI.IIIlllIIIllIllIlIIIIIIlII].equals(v))) {
                TestClassFour.lIIIIlIIllIIlIIlIIIlIIllI(this.lIIIIlIIllIIlIIlIIIlIIllI);
                return true;
            }
            return false;
        }
        Object[] objectArray = this.lIIIIlIIllIIlIIlIIIlIIllI.IlllIllIlIIIIlIIlIIllIIIl;
        int n = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(k.hashCode()) & this.lIIIIlIIllIIlIIlIIIlIIllI.llIIlllIIIIlllIllIlIlllIl;
        Object object2 = objectArray[n];
        if (object2 == null) {
            return false;
        }
        if (object2.equals(k)) {
            if (this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[n] == null ? v == null : this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[n].equals(v)) {
                TestClassFour.lIIIIlIIllIIlIIlIIIlIIllI(this.lIIIIlIIllIIlIIlIIIlIIllI, n);
                return true;
            }
            return false;
        }
        do {
            if ((object2 = objectArray[n = n + 1 & this.lIIIIlIIllIIlIIlIIIlIIllI.llIIlllIIIIlllIllIlIlllIl]) != null) continue;
            return false;
        } while (!object2.equals(k) || !(this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[n] == null ? v == null : this.lIIIIlIIllIIlIIlIIIlIIllI.IlIlllIIIIllIllllIllIIlIl[n].equals(v)));
        TestClassFour.lIIIIlIIllIIlIIlIIIlIIllI(this.lIIIIlIIllIIlIIlIIIlIIllI, n);
        return true;
    }

    @Override
    public int size() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI.IIIlIIllllIIllllllIlIIIll;
    }

    @Override
    public void clear() {
        this.lIIIIlIIllIIlIIlIIIlIIllI.clear();
    }

    @Override
    public Iterator iterator() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }

    TestClassTen(TestClassFour testClassFour, IlIlIllIIllllIlllIlllIlII ilIlIllIIllllIlllIlllIlII) {
        this(testClassFour);
    }
}
