package com.cheatbreaker.client.util.font;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class TestClassFour
        extends TestClassFive
        implements ITestClass,
        Serializable,
        Cloneable {
    private static final long IlIIlIIIIlIIIIllllIIlIllI = 0L;
    private static final boolean lIIlIIllIIIIIlIllIIIIllII = false;
    protected transient Object[] IlllIllIlIIIIlIIlIIllIIIl;
    protected transient Object[] IlIlllIIIIllIllllIllIIlIl;
    protected transient int llIIlllIIIIlllIllIlIlllIl;
    protected transient boolean lIIlIlIllIIlIIIlIIIlllIII;
    protected transient int IIIlllIIIllIllIlIIIIIIlII;
    protected transient int llIlIIIlIIIIlIlllIlIIIIll;
    protected int IIIlIIllllIIllllllIlIIIll;
    protected final float lllIIIIIlIllIlIIIllllllII;
    protected transient ITestClassSix lIIIIIllllIIIIlIlIIIIlIlI;
    protected transient ITestClassSeven IIIIIIlIlIlIllllllIlllIlI;
    protected transient ITestClassEight IllIllIIIlIIlllIIIllIllII;

    public TestClassFour(int n, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }
        if (n < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.lllIIIIIlIllIlIIIllllllII = f;
        this.IIIlllIIIllIllIlIIIIIIlII = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(n, f);
        this.llIIlllIIIIlllIllIlIlllIl = this.IIIlllIIIllIllIlIIIIIIlII - 1;
        this.llIlIIIlIIIIlIlllIlIIIIll = TestClassSeven.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIlllIIIllIllIlIIIIIIlII, f);
        this.IlllIllIlIIIIlIIlIIllIIIl = new Object[this.IIIlllIIIllIllIlIIIIIIlII + 1];
        this.IlIlllIIIIllIllllIllIIlIl = new Object[this.IIIlllIIIllIllIlIIIIIIlII + 1];
    }

    public TestClassFour(int n) {
        this(n, 2.55f * 0.29411766f);
    }

    public TestClassFour() {
        this(16, 3.0f * 0.25f);
    }

    public TestClassFour(Map map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public TestClassFour(Map map) {
        this(map, 0.25252524f * 2.97f);
    }

    public TestClassFour(ITestClassFour iTestClassFour, float f) {
        this(iTestClassFour.size(), f);
        this.putAll((Map)iTestClassFour);
    }

    public TestClassFour(ITestClassFour iTestClassFour) {
        this(iTestClassFour, 0.28723404f * 2.6111112f);
    }

    public TestClassFour(Object[] objectArray, Object[] objectArray2, float f) {
        this(objectArray.length, f);
        if (objectArray.length != objectArray2.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + objectArray.length + " and " + objectArray2.length + ")");
        }
        for (int i = 0; i < objectArray.length; ++i) {
            this.put(objectArray[i], objectArray2[i]);
        }
    }

    public TestClassFour(Object[] objectArray, Object[] objectArray2) {
        this(objectArray, objectArray2, 7.285714f * 0.10294118f);
    }

    private int IlIlllIIIIllIllllIllIIlIl() {
        return this.lIIlIlIllIIlIIIlIIIlllIII ? this.IIIlIIllllIIllllllIlIIIll - 1 : this.IIIlIIllllIIllllllIlIIIll;
    }

    private void IIIIllIIllIIIIllIllIIIlIl(int n) {
        int n2 = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(n, this.lllIIIIIlIllIlIIIllllllII);
        if (n2 > this.IIIlllIIIllIllIlIIIIIIlII) {
            this.IIIIllIlIIIllIlllIlllllIl(n2);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(long l) {
        int n = (int)Math.min(0x40000000L, Math.max(2L, TestClassSeven.IIIIllIIllIIIIllIllIIIlIl((long)Math.ceil((float)l / this.lllIIIIIlIllIlIIIllllllII))));
        if (n > this.IIIlllIIIllIllIlIIIIIIlII) {
            this.IIIIllIlIIIllIlllIlllllIl(n);
        }
    }

    private Object IlIlIIIlllIIIlIlllIlIllIl(int n) {
        Object object = this.IlIlllIIIIllIllllIllIIlIl[n];
        this.IlIlllIIIIllIllllIllIIlIl[n] = null;
        --this.IIIlIIllllIIllllllIlIIIll;
        this.lIIIIlIIllIIlIIlIIIlIIllI(n);
        if (this.IIIlIIllllIIllllllIlIIIll < this.llIlIIIlIIIIlIlllIlIIIIll / 4 && this.IIIlllIIIllIllIlIIIIIIlII > 16) {
            this.IIIIllIlIIIllIlllIlllllIl(this.IIIlllIIIllIllIlIIIIIIlII / 2);
        }
        return object;
    }

    private Object llIIlllIIIIlllIllIlIlllIl() {
        this.lIIlIlIllIIlIIIlIIIlllIII = false;
        this.IlllIllIlIIIIlIIlIIllIIIl[this.IIIlllIIIllIllIlIIIIIIlII] = null;
        Object object = this.IlIlllIIIIllIllllIllIIlIl[this.IIIlllIIIllIllIlIIIIIIlII];
        this.IlIlllIIIIllIllllIllIIlIl[this.IIIlllIIIllIllIlIIIIIIlII] = null;
        --this.IIIlIIllllIIllllllIlIIIll;
        if (this.IIIlIIllllIIllllllIlIIIll < this.llIlIIIlIIIIlIlllIlIIIIll / 4 && this.IIIlllIIIllIllIlIIIIIIlII > 16) {
            this.IIIIllIlIIIllIlllIlllllIl(this.IIIlllIIIllIllIlIIIIIIlII / 2);
        }
        return object;
    }

    @Override
    public void putAll(Map map) {
        if ((double)this.lllIIIIIlIllIlIIIllllllII <= 1.4347826272868933 * 0.3484848439693451) {
            this.IIIIllIIllIIIIllIllIIIlIl(map.size());
        } else {
            this.lIIIIlIIllIIlIIlIIIlIIllI((long)(this.size() + map.size()));
        }
        super.putAll(map);
    }

    private int lIIIIlIIllIIlIIlIIIlIIllI(Object object, Object object2) {
        int n;
        if (object == null) {
            if (this.lIIlIlIllIIlIIIlIIIlllIII) {
                return this.IIIlllIIIllIllIlIIIIIIlII;
            }
            this.lIIlIlIllIIlIIIlIIIlllIII = true;
            n = this.IIIlllIIIllIllIlIIIIIIlII;
        } else {
            Object[] objectArray = this.IlllIllIlIIIIlIIlIIllIIIl;
            n = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(object.hashCode()) & this.llIIlllIIIIlllIllIlIlllIl;
            Object object3 = objectArray[n];
            if (object3 != null) {
                if (object3.equals(object)) {
                    return n;
                }
                while ((object3 = objectArray[n = n + 1 & this.llIIlllIIIIlllIllIlIlllIl]) != null) {
                    if (!object3.equals(object)) continue;
                    return n;
                }
            }
        }
        this.IlllIllIlIIIIlIIlIIllIIIl[n] = object;
        this.IlIlllIIIIllIllllIllIIlIl[n] = object2;
        if (this.IIIlIIllllIIllllllIlIIIll++ >= this.llIlIIIlIIIIlIlllIlIIIIll) {
            this.IIIIllIlIIIllIlllIlllllIl(TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(this.IIIlIIllllIIllllllIlIIIll + 1, this.lllIIIIIlIllIlIIIllllllII));
        }
        return -1;
    }

    @Override
    public Object put(Object object, Object object2) {
        int n = this.lIIIIlIIllIIlIIlIIIlIIllI(object, object2);
        if (n < 0) {
            return this.a_;
        }
        Object object3 = this.IlIlllIIIIllIllllIllIIlIl[n];
        this.IlIlllIIIIllIllllIllIIlIl[n] = object2;
        return object3;
    }

    protected final void lIIIIlIIllIIlIIlIIIlIIllI(int n) {
        Object[] objectArray = this.IlllIllIlIIIIlIIlIIllIIIl;
        while (true) {
            Object object;
            int n2 = n;
            n = n2 + 1 & this.llIIlllIIIIlllIllIlIlllIl;
            while (true) {
                if ((object = objectArray[n]) == null) {
                    objectArray[n2] = null;
                    this.IlIlllIIIIllIllllIllIIlIl[n2] = null;
                    return;
                }
                int n3 = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(object.hashCode()) & this.llIIlllIIIIlllIllIlIlllIl;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.llIIlllIIIIlllIllIlIlllIl;
            }
            objectArray[n2] = object;
            this.IlIlllIIIIllIllllIllIIlIl[n2] = this.IlIlllIIIIllIllllIllIIlIl[n];
        }
    }

    @Override
    public Object remove(Object object) {
        if (object == null) {
            if (this.lIIlIlIllIIlIIIlIIIlllIII) {
                return this.llIIlllIIIIlllIllIlIlllIl();
            }
            return this.a_;
        }
        Object[] objectArray = this.IlllIllIlIIIIlIIlIIllIIIl;
        int n = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(object.hashCode()) & this.llIIlllIIIIlllIllIlIlllIl;
        Object object2 = objectArray[n];
        if (object2 == null) {
            return this.a_;
        }
        if (object.equals(object2)) {
            return this.IlIlIIIlllIIIlIlllIlIllIl(n);
        }
        do {
            if ((object2 = objectArray[n = n + 1 & this.llIIlllIIIIlllIllIlIlllIl]) != null) continue;
            return this.a_;
        } while (!object.equals(object2));
        return this.IlIlIIIlllIIIlIlllIlIllIl(n);
    }

    @Override
    public Object get(Object object) {
        if (object == null) {
            return this.lIIlIlIllIIlIIIlIIIlllIII ? this.IlIlllIIIIllIllllIllIIlIl[this.IIIlllIIIllIllIlIIIIIIlII] : this.a_;
        }
        Object[] objectArray = this.IlllIllIlIIIIlIIlIIllIIIl;
        int n = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(object.hashCode()) & this.llIIlllIIIIlllIllIlIlllIl;
        Object object2 = objectArray[n];
        if (object2 == null) {
            return this.a_;
        }
        if (object.equals(object2)) {
            return this.IlIlllIIIIllIllllIllIIlIl[n];
        }
        do {
            if ((object2 = objectArray[n = n + 1 & this.llIIlllIIIIlllIllIlIlllIl]) != null) continue;
            return this.a_;
        } while (!object.equals(object2));
        return this.IlIlllIIIIllIllllIllIIlIl[n];
    }

    @Override
    public boolean containsKey(Object object) {
        if (object == null) {
            return this.lIIlIlIllIIlIIIlIIIlllIII;
        }
        Object[] objectArray = this.IlllIllIlIIIIlIIlIIllIIIl;
        int n = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(object.hashCode()) & this.llIIlllIIIIlllIllIlIlllIl;
        Object object2 = objectArray[n];
        if (object2 == null) {
            return false;
        }
        if (object.equals(object2)) {
            return true;
        }
        do {
            if ((object2 = objectArray[n = n + 1 & this.llIIlllIIIIlllIllIlIlllIl]) != null) continue;
            return false;
        } while (!object.equals(object2));
        return true;
    }

    @Override
    public boolean containsValue(Object object) {
        Object[] objectArray = this.IlIlllIIIIllIllllIllIIlIl;
        Object[] objectArray2 = this.IlllIllIlIIIIlIIlIIllIIIl;
        if (this.lIIlIlIllIIlIIIlIIIlllIII && (objectArray[this.IIIlllIIIllIllIlIIIIIIlII] == null ? object == null : objectArray[this.IIIlllIIIllIllIlIIIIIIlII].equals(object))) {
            return true;
        }
        int n = this.IIIlllIIIllIllIlIIIIIIlII;
        while (n-- != 0) {
            if (objectArray2[n] == null || !(objectArray[n] == null ? object == null : objectArray[n].equals(object))) continue;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        if (this.IIIlIIllllIIllllllIlIIIll == 0) {
            return;
        }
        this.IIIlIIllllIIllllllIlIIIll = 0;
        this.lIIlIlIllIIlIIIlIIIlllIII = false;
        Arrays.fill(this.IlllIllIlIIIIlIIlIIllIIIl, null);
        Arrays.fill(this.IlIlllIIIIllIllllIllIIlIl, null);
    }

    @Override
    public int size() {
        return this.IIIlIIllllIIllllllIlIIIll;
    }

    @Override
    public boolean isEmpty() {
        return this.IIIlIIllllIIllllllIlIIIll == 0;
    }

    @Deprecated
    public void lIIIIIIIIIlIllIIllIlIIlIl(int n) {
    }

    @Deprecated
    public int IlIlIIIlllIIIlIlllIlIllIl() {
        return 16;
    }

    public ITestClassSix IIIllIllIlIlllllllIlIlIII() {
        if (this.lIIIIIllllIIIIlIlIIIIlIlI == null) {
            this.lIIIIIllllIIIIlIlIIIIlIlI = new TestClassTen(this, null);
        }
        return this.lIIIIIllllIIIIlIlIIIIlIlI;
    }

    @Override
    public ITestClassSeven lIIIIIIIIIlIllIIllIlIIlIl() {
        if (this.IIIIIIlIlIlIllllllIlllIlI == null) {
            this.IIIIIIlIlIlIllllllIlllIlI = new llIIIIlIlIlIlllIIIlllllIl(this, null);
        }
        return this.IIIIIIlIlIlIllllllIlllIlI;
    }

    @Override
    public ITestClassEight IlllIIIlIlllIllIlIIlllIlI() {
        if (this.IllIllIIIlIIlllIIIllIllII == null) {
            this.IllIllIIIlIIlllIIIllIllII = new IlIlIllIIllllIlllIlllIlII(this);
        }
        return this.IllIllIIIlIIlllIIIllIllII;
    }

    @Deprecated
    public boolean IllIIIIIIIlIlIllllIIllIII() {
        return true;
    }

    public boolean lIIIIllIIlIlIllIIIlIllIlI() {
        int n = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(this.IIIlIIllllIIllllllIlIIIll, this.lllIIIIIlIllIlIIIllllllII);
        if (n >= this.IIIlllIIIllIllIlIIIIIIlII || this.IIIlIIllllIIllllllIlIIIll > TestClassSeven.lIIIIlIIllIIlIIlIIIlIIllI(n, this.lllIIIIIlIllIlIIIllllllII)) {
            return true;
        }
        try {
            this.IIIIllIlIIIllIlllIlllllIl(n);
        } catch (OutOfMemoryError outOfMemoryError) {
            return false;
        }
        return true;
    }

    public boolean IlllIIIlIlllIllIlIIlllIlI(int n) {
        int n2 = TestClassSeven.IIIIllIlIIIllIlllIlllllIl((int)Math.ceil((float)n / this.lllIIIIIlIllIlIIIllllllII));
        if (n2 >= n || this.IIIlIIllllIIllllllIlIIIll > TestClassSeven.lIIIIlIIllIIlIIlIIIlIIllI(n2, this.lllIIIIIlIllIlIIIllllllII)) {
            return true;
        }
        try {
            this.IIIIllIlIIIllIlllIlllllIl(n2);
        } catch (OutOfMemoryError outOfMemoryError) {
            return false;
        }
        return true;
    }

    protected void IIIIllIlIIIllIlllIlllllIl(int n) {
        Object[] objectArray = this.IlllIllIlIIIIlIIlIIllIIIl;
        Object[] objectArray2 = this.IlIlllIIIIllIllllIllIIlIl;
        int n2 = n - 1;
        Object[] objectArray3 = new Object[n + 1];
        Object[] objectArray4 = new Object[n + 1];
        int n3 = this.IIIlllIIIllIllIlIIIIIIlII;
        int n4 = this.IlIlllIIIIllIllllIllIIlIl();
        while (n4-- != 0) {
            while (objectArray[--n3] == null) {
            }
            int n5 = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(objectArray[n3].hashCode()) & n2;
            if (objectArray3[n5] != null) {
                while (objectArray3[n5 = n5 + 1 & n2] != null) {
                }
            }
            objectArray3[n5] = objectArray[n3];
            objectArray4[n5] = objectArray2[n3];
        }
        objectArray4[n] = objectArray2[this.IIIlllIIIllIllIlIIIIIIlII];
        this.IIIlllIIIllIllIlIIIIIIlII = n;
        this.llIIlllIIIIlllIllIlIlllIl = n2;
        this.llIlIIIlIIIIlIlllIlIIIIll = TestClassSeven.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIlllIIIllIllIlIIIIIIlII, this.lllIIIIIlIllIlIIIllllllII);
        this.IlllIllIlIIIIlIIlIIllIIIl = objectArray3;
        this.IlIlllIIIIllIllllIllIIlIl = objectArray4;
    }

    public TestClassFour IlllIllIlIIIIlIIlIIllIIIl() {
        TestClassFour testClassFour;
        try {
            testClassFour = (TestClassFour)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        testClassFour.IIIIIIlIlIlIllllllIlllIlI = null;
        testClassFour.IllIllIIIlIIlllIIIllIllII = null;
        testClassFour.lIIIIIllllIIIIlIlIIIIlIlI = null;
        testClassFour.lIIlIlIllIIlIIIlIIIlllIII = this.lIIlIlIllIIlIIIlIIIlllIII;
        testClassFour.IlllIllIlIIIIlIIlIIllIIIl = (Object[])this.IlllIllIlIIIIlIIlIIllIIIl.clone();
        testClassFour.IlIlllIIIIllIllllIllIIlIl = (Object[])this.IlIlllIIIIllIllllIllIIlIl.clone();
        return testClassFour;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.IlIlllIIIIllIllllIllIIlIl();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (this.IlllIllIlIIIIlIIlIIllIIIl[n3] == null) {
                ++n3;
            }
            if (this != this.IlllIllIlIIIIlIIlIIllIIIl[n3]) {
                n4 = this.IlllIllIlIIIIlIIlIIllIIIl[n3].hashCode();
            }
            if (this != this.IlIlllIIIIllIllllIllIIlIl[n3]) {
                n4 ^= this.IlIlllIIIIllIllllIllIIlIl[n3] == null ? 0 : this.IlIlllIIIIllIllllIllIIlIl[n3].hashCode();
            }
            n += n4;
            ++n3;
        }
        if (this.lIIlIlIllIIlIIIlIIIlllIII) {
            n += this.IlIlllIIIIllIllllIllIIlIl[this.IIIlllIIIllIllIlIIIIIIlII] == null ? 0 : this.IlIlllIIIIllIllllIllIIlIl[this.IIIlllIIIllIllIlIIIIIIlII].hashCode();
        }
        return n;
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ObjectOutputStream objectOutputStream) throws IOException {
        Object[] objectArray = this.IlllIllIlIIIIlIIlIIllIIIl;
        Object[] objectArray2 = this.IlIlllIIIIllIllllIllIIlIl;
        llIllIIllIllIIlIIIIllIIII llIllIIllIllIIlIIIIllIIII2 = new llIllIIllIllIIlIIIIllIIII(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.IIIlIIllllIIllllllIlIIIll;
        while (n-- != 0) {
            int n2 = llIllIIllIllIIlIIIIllIIII2.lIIIIIIIIIlIllIIllIlIIlIl();
            objectOutputStream.writeObject(objectArray[n2]);
            objectOutputStream.writeObject(objectArray2[n2]);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.IIIlllIIIllIllIlIIIIIIlII = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(this.IIIlIIllllIIllllllIlIIIll, this.lllIIIIIlIllIlIIIllllllII);
        this.llIlIIIlIIIIlIlllIlIIIIll = TestClassSeven.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIlllIIIllIllIlIIIIIIlII, this.lllIIIIIlIllIlIIIllllllII);
        this.llIIlllIIIIlllIllIlIlllIl = this.IIIlllIIIllIllIlIIIIIIlII - 1;
        this.IlllIllIlIIIIlIIlIIllIIIl = new Object[this.IIIlllIIIllIllIlIIIIIIlII + 1];
        Object[] objectArray = this.IlllIllIlIIIIlIIlIIllIIIl;
        this.IlIlllIIIIllIllllIllIIlIl = new Object[this.IIIlllIIIllIllIlIIIIIIlII + 1];
        Object[] objectArray2 = this.IlIlllIIIIllIllllIllIIlIl;
        int n = this.IIIlIIllllIIllllllIlIIIll;
        while (n-- != 0) {
            int n2;
            Object object = objectInputStream.readObject();
            Object object2 = objectInputStream.readObject();
            if (object == null) {
                n2 = this.IIIlllIIIllIllIlIIIIIIlII;
                this.lIIlIlIllIIlIIIlIIIlllIII = true;
            } else {
                n2 = TestClassSeven.lIIIIIIIIIlIllIIllIlIIlIl(object.hashCode()) & this.llIIlllIIIIlllIllIlIlllIl;
                while (objectArray[n2] != null) {
                    n2 = n2 + 1 & this.llIIlllIIIIlllIllIlIlllIl;
                }
            }
            objectArray[n2] = object;
            objectArray2[n2] = object2;
        }
    }

    private void lIIlIlIllIIlIIIlIIIlllIII() {
    }

    @Override
    public ITestClassSeven IIIIllIIllIIIIllIllIIIlIl() {
        return this.IIIllIllIlIlllllllIlIlIII();
    }

    @Override
    public Collection values() {
        return this.IlllIIIlIlllIllIlIIlllIlI();
    }

    @Override
    public Set keySet() {
        return this.lIIIIIIIIIlIllIIllIlIIlIl();
    }

    public Object clone() {
        return this.IlllIllIlIIIIlIIlIIllIIIl();
    }

    static Object lIIIIlIIllIIlIIlIIIlIIllI(TestClassFour testClassFour) {
        return testClassFour.llIIlllIIIIlllIllIlIlllIl();
    }

    static Object lIIIIlIIllIIlIIlIIIlIIllI(TestClassFour testClassFour, int n) {
        return testClassFour.IlIlIIIlllIIIlIlllIlIllIl(n);
    }
}
