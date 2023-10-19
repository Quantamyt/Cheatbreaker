package com.cheatbreaker.client.util.font;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.RandomAccess;

public class IIlIIlIIlIlIllIIIIlIIIIIl
        extends lIIllIIlIIIIIllIlIIllIIIl
        implements Serializable,
        Cloneable,
        RandomAccess {
    private static final long IIIIllIIllIIIIllIllIIIlIl = -7046029254386353131L;
    public static final int lIIIIlIIllIIlIIlIIIlIIllI = 16;
    protected final boolean lIIIIIIIIIlIllIIllIlIIlIl;
    protected transient Object[] IlllIIIlIlllIllIlIIlllIlI;
    protected int IIIIllIlIIIllIlllIlllllIl;
    private static final boolean IlIlIIIlllIIIlIlllIlIllIl = false;

    protected IIlIIlIIlIlIllIIIIlIIIIIl(Object[] objectArray, boolean bl) {
        this.IlllIIIlIlllIllIlIIlllIlI = objectArray;
        this.lIIIIIIIIIlIllIIllIlIIlIl = true;
    }

    public IIlIIlIIlIlIllIIIIlIIIIIl(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.IlllIIIlIlllIllIlIIlllIlI = new Object[n];
        this.lIIIIIIIIIlIllIIllIlIIlIl = false;
    }

    public IIlIIlIIlIlIllIIIIlIIIIIl() {
        this(16);
    }

    public IIlIIlIIlIlIllIIIIlIIIIIl(Collection collection) {
        this(collection.size());
        this.IIIIllIlIIIllIlllIlllllIl = lIIIIlIlIllIIIIIIIlIlIIIl.lIIIIlIIllIIlIIlIIIlIIllI(collection.iterator(), this.IlllIIIlIlllIllIlIIlllIlI);
    }

    public IIlIIlIIlIlIllIIIIlIIIIIl(ITestClassEight iTestClassEight) {
        this(iTestClassEight.size());
        this.IIIIllIlIIIllIlllIlllllIl = lIIIIlIlIllIIIIIIIlIlIIIl.lIIIIlIIllIIlIIlIIIlIIllI((Iterator)iTestClassEight.lIIIIlIIllIIlIIlIIIlIIllI(), this.IlllIIIlIlllIllIlIIlllIlI);
    }

    public IIlIIlIIlIlIllIIIIlIIIIIl(lIlllIllIlllIlllIlIIIlIIl lIlllIllIlllIlllIlIIIlIIl2) {
        this(lIlllIllIlllIlllIlIIIlIIl2.size());
        this.IIIIllIlIIIllIlllIlllllIl = lIlllIllIlllIlllIlIIIlIIl2.size();
        lIlllIllIlllIlllIlIIIlIIl2.lIIIIIIIIIlIllIIllIlIIlIl(0, this.IlllIIIlIlllIllIlIIlllIlI, 0, this.IIIIllIlIIIllIlllIlllllIl);
    }

    public IIlIIlIIlIlIllIIIIlIIIIIl(Object[] objectArray) {
        this(objectArray, 0, objectArray.length);
    }

    public IIlIIlIIlIlIllIIIIlIIIIIl(Object[] objectArray, int n, int n2) {
        this(n2);
        System.arraycopy(objectArray, n, this.IlllIIIlIlllIllIlIIlllIlI, 0, n2);
        this.IIIIllIlIIIllIlllIlllllIl = n2;
    }

    public IIlIIlIIlIlIllIIIIlIIIIIl(Iterator iterator) {
        this();
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }

    public IIlIIlIIlIlIllIIIIlIIIIIl(ITestClassFive iTestClassFive) {
        this();
        while (iTestClassFive.hasNext()) {
            this.add(iTestClassFive.next());
        }
    }

    public Object[] IIIllIllIlIlllllllIlIlIII() {
        return this.IlllIIIlIlllIllIlIIlllIlI;
    }

    public static IIlIIlIIlIlIllIIIIlIIIIIl lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n) {
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + objectArray.length + ")");
        }
        IIlIIlIIlIlIllIIIIlIIIIIl iIlIIlIIlIlIllIIIIlIIIIIl = new IIlIIlIIlIlIllIIIIlIIIIIl(objectArray, false);
        iIlIIlIIlIlIllIIIIlIIIIIl.IIIIllIlIIIllIlllIlllllIl = n;
        return iIlIIlIIlIlIllIIIIlIIIIIl;
    }

    public static IIlIIlIIlIlIllIIIIlIIIIIl lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray) {
        return IIlIIlIIlIlIllIIIIlIIIIIl.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, objectArray.length);
    }

    public void IIIllIllIlIlllllllIlIlIII(int n) {
        if (this.lIIIIIIIIIlIllIIllIlIIlIl) {
            this.IlllIIIlIlllIllIlIIlllIlI = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(this.IlllIIIlIlllIllIlIIlllIlI, n, this.IIIIllIlIIIllIlllIlllllIl);
        } else if (n > this.IlllIIIlIlllIllIlIIlllIlI.length) {
            Object[] objectArray = new Object[n];
            System.arraycopy(this.IlllIIIlIlllIllIlIIlllIlI, 0, objectArray, 0, this.IIIIllIlIIIllIlllIlllllIl);
            this.IlllIIIlIlllIllIlIIlllIlI = objectArray;
        }
    }

    private void lIIIIllIIlIlIllIIIlIllIlI(int n) {
        if (this.lIIIIIIIIIlIllIIllIlIIlIl) {
            this.IlllIIIlIlllIllIlIIlllIlI = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(this.IlllIIIlIlllIllIlIIlllIlI, n, this.IIIIllIlIIIllIlllIlllllIl);
        } else if (n > this.IlllIIIlIlllIllIlIIlllIlI.length) {
            int n2 = (int)Math.max(Math.min(2L * (long)this.IlllIIIlIlllIllIlIIlllIlI.length, 0x7FFFFFF7L), (long)n);
            Object[] objectArray = new Object[n2];
            System.arraycopy(this.IlllIIIlIlllIllIlIIlllIlI, 0, objectArray, 0, this.IIIIllIlIIIllIlllIlllllIl);
            this.IlllIIIlIlllIllIlIIlllIlI = objectArray;
        }
    }

    @Override
    public void add(int n, Object object) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        this.lIIIIllIIlIlIllIIIlIllIlI(this.IIIIllIlIIIllIlllIlllllIl + 1);
        if (n != this.IIIIllIlIIIllIlllIlllllIl) {
            System.arraycopy(this.IlllIIIlIlllIllIlIIlllIlI, n, this.IlllIIIlIlllIllIlIIlllIlI, n + 1, this.IIIIllIlIIIllIlllIlllllIl - n);
        }
        this.IlllIIIlIlllIllIlIIlllIlI[n] = object;
        ++this.IIIIllIlIIIllIlllIlllllIl;
    }

    @Override
    public boolean add(Object object) {
        this.lIIIIllIIlIlIllIIIlIllIlI(this.IIIIllIlIIIllIlllIlllllIl + 1);
        this.IlllIIIlIlllIllIlIIlllIlI[this.IIIIllIlIIIllIlllIlllllIl++] = object;
        return true;
    }

    public Object get(int n) {
        if (n >= this.IIIIllIlIIIllIlllIlllllIl) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.IIIIllIlIIIllIlllIlllllIl + ")");
        }
        return this.IlllIIIlIlllIllIlIIlllIlI[n];
    }

    @Override
    public int indexOf(Object object) {
        for (int i = 0; i < this.IIIIllIlIIIllIlllIlllllIl; ++i) {
            if (!(object == null ? this.IlllIIIlIlllIllIlIIlllIlI[i] == null : object.equals(this.IlllIIIlIlllIllIlIIlllIlI[i]))) continue;
            return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        int n = this.IIIIllIlIIIllIlllIlllllIl;
        while (n-- != 0) {
            if (!(object == null ? this.IlllIIIlIlllIllIlIIlllIlI[n] == null : object.equals(this.IlllIIIlIlllIllIlIIlllIlI[n]))) continue;
            return n;
        }
        return -1;
    }

    @Override
    public Object remove(int n) {
        if (n >= this.IIIIllIlIIIllIlllIlllllIl) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.IIIIllIlIIIllIlllIlllllIl + ")");
        }
        Object object = this.IlllIIIlIlllIllIlIIlllIlI[n];
        --this.IIIIllIlIIIllIlllIlllllIl;
        if (n != this.IIIIllIlIIIllIlllIlllllIl) {
            System.arraycopy(this.IlllIIIlIlllIllIlIIlllIlI, n + 1, this.IlllIIIlIlllIllIlIIlllIlI, n, this.IIIIllIlIIIllIlllIlllllIl - n);
        }
        this.IlllIIIlIlllIllIlIIlllIlI[this.IIIIllIlIIIllIlllIlllllIl] = null;
        return object;
    }

    @Override
    public boolean lIIIIIIIIIlIllIIllIlIIlIl(Object object) {
        int n = this.indexOf(object);
        if (n == -1) {
            return false;
        }
        this.remove(n);
        return true;
    }

    @Override
    public boolean remove(Object object) {
        return this.lIIIIIIIIIlIllIIllIlIIlIl(object);
    }

    @Override
    public Object set(int n, Object object) {
        if (n >= this.IIIIllIlIIIllIlllIlllllIl) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.IIIIllIlIIIllIlllIlllllIl + ")");
        }
        Object object2 = this.IlllIIIlIlllIllIlIIlllIlI[n];
        this.IlllIIIlIlllIllIlIIlllIlI[n] = object;
        return object2;
    }

    @Override
    public void clear() {
        Arrays.fill(this.IlllIIIlIlllIllIlIIlllIlI, 0, this.IIIIllIlIIIllIlllIlllllIl, null);
        this.IIIIllIlIIIllIlllIlllllIl = 0;
    }

    @Override
    public int size() {
        return this.IIIIllIlIIIllIlllIlllllIl;
    }

    @Override
    public void IlIlIIIlllIIIlIlllIlIllIl(int n) {
        if (n > this.IlllIIIlIlllIllIlIIlllIlI.length) {
            this.IIIllIllIlIlllllllIlIlIII(n);
        }
        if (n > this.IIIIllIlIIIllIlllIlllllIl) {
            Arrays.fill(this.IlllIIIlIlllIllIlIIlllIlI, this.IIIIllIlIIIllIlllIlllllIl, n, null);
        } else {
            Arrays.fill(this.IlllIIIlIlllIllIlIIlllIlI, n, this.IIIIllIlIIIllIlllIlllllIl, null);
        }
        this.IIIIllIlIIIllIlllIlllllIl = n;
    }

    @Override
    public boolean isEmpty() {
        return this.IIIIllIlIIIllIlllIlllllIl == 0;
    }

    public void IllIIIIIIIlIlIllllIIllIII() {
        this.IllIIIIIIIlIlIllllIIllIII(0);
    }

    public void IllIIIIIIIlIlIllllIIllIII(int n) {
        if (n >= this.IlllIIIlIlllIllIlIIlllIlI.length || this.IIIIllIlIIIllIlllIlllllIl == this.IlllIIIlIlllIllIlIIlllIlI.length) {
            return;
        }
        Object[] objectArray = new Object[Math.max(n, this.IIIIllIlIIIllIlllIlllllIl)];
        System.arraycopy(this.IlllIIIlIlllIllIlIIlllIlI, 0, objectArray, 0, this.IIIIllIlIIIllIlllIlllllIl);
        this.IlllIIIlIlllIllIlIIlllIlI = objectArray;
    }

    @Override
    public void lIIIIIIIIIlIllIIllIlIIlIl(int n, Object[] objectArray, int n2, int n3) {
        IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n2, n3);
        System.arraycopy(this.IlllIIIlIlllIllIlIIlllIlI, n, objectArray, n2, n3);
    }

    @Override
    public void IlllIIIlIlllIllIlIIlllIlI(int n, int n2) {
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIIllIlIIIllIlllIlllllIl, n, n2);
        System.arraycopy(this.IlllIIIlIlllIllIlIIlllIlI, n2, this.IlllIIIlIlllIllIlIIlllIlI, n, this.IIIIllIlIIIllIlllIlllllIl - n2);
        this.IIIIllIlIIIllIlllIlllllIl -= n2 - n;
        int n3 = n2 - n;
        while (n3-- != 0) {
            this.IlllIIIlIlllIllIlIIlllIlI[this.IIIIllIlIIIllIlllIlllllIl + n3] = null;
        }
    }

    @Override
    public void lIIIIlIIllIIlIIlIIIlIIllI(int n, Object[] objectArray, int n2, int n3) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n2, n3);
        this.lIIIIllIIlIlIllIIIlIllIlI(this.IIIIllIlIIIllIlllIlllllIl + n3);
        System.arraycopy(this.IlllIIIlIlllIllIlIIlllIlI, n, this.IlllIIIlIlllIllIlIIlllIlI, n + n3, this.IIIIllIlIIIllIlllIlllllIl - n);
        System.arraycopy(objectArray, n2, this.IlllIIIlIlllIllIlIIlllIlI, n, n3);
        this.IIIIllIlIIIllIlllIlllllIl += n3;
    }

    @Override
    public boolean removeAll(Collection collection) {
        int n;
        Object[] objectArray = this.IlllIIIlIlllIllIlIIlllIlI;
        int n2 = 0;
        for (n = 0; n < this.IIIIllIlIIIllIlllIlllllIl; n += 1) {
            if (collection.contains(objectArray[n])) continue;
            objectArray[n2++] = objectArray[n];
        }
        Arrays.fill(objectArray, n2, this.IIIIllIlIIIllIlllIlllllIl, null);
        n = this.IIIIllIlIIIllIlllIlllllIl != n2 ? 1 : 0;
        this.IIIIllIlIIIllIlllIlllllIl = n2;
        return n != 0;
    }

    @Override
    public llllllllllllIlIIIIIIIIlll IIIIllIIllIIIIllIllIIIlIl(int n) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        return new llIIlIlIIIlIlIIlIllllIllI(this, n);
    }

    public IIlIIlIIlIlIllIIIIlIIIIIl lIIIIllIIlIlIllIIIlIllIlI() {
        IIlIIlIIlIlIllIIIIlIIIIIl iIlIIlIIlIlIllIIIIlIIIIIl = new IIlIIlIIlIlIllIIIIlIIIIIl(this.IIIIllIlIIIllIlllIlllllIl);
        System.arraycopy(this.IlllIIIlIlllIllIlIIlllIlI, 0, iIlIIlIIlIlIllIIIIlIIIIIl.IlllIIIlIlllIllIlIIlllIlI, 0, this.IIIIllIlIIIllIlllIlllllIl);
        iIlIIlIIlIlIllIIIIlIIIIIl.IIIIllIlIIIllIlllIlllllIl = this.IIIIllIlIIIllIlllIlllllIl;
        return iIlIIlIIlIlIllIIIIlIIIIIl;
    }

    private boolean lIIIIlIIllIIlIIlIIIlIIllI(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    public boolean lIIIIlIIllIIlIIlIIIlIIllI(IIlIIlIIlIlIllIIIIlIIIIIl iIlIIlIIlIlIllIIIIlIIIIIl) {
        if (iIlIIlIIlIlIllIIIIlIIIIIl == this) {
            return true;
        }
        int n = this.size();
        if (n != iIlIIlIIlIlIllIIIIlIIIIIl.size()) {
            return false;
        }
        Object[] objectArray = this.IlllIIIlIlllIllIlIIlllIlI;
        Object[] objectArray2 = iIlIIlIIlIlIllIIIIlIIIIIl.IlllIIIlIlllIllIlIIlllIlI;
        while (n-- != 0) {
            if (this.lIIIIlIIllIIlIIlIIIlIIllI(objectArray[n], objectArray2[n])) continue;
            return false;
        }
        return true;
    }

    public int lIIIIIIIIIlIllIIllIlIIlIl(IIlIIlIIlIlIllIIIIlIIIIIl iIlIIlIIlIlIllIIIIlIIIIIl) {
        int n;
        int n2 = this.size();
        int n3 = iIlIIlIIlIlIllIIIIlIIIIIl.size();
        Object[] objectArray = this.IlllIIIlIlllIllIlIIlllIlI;
        Object[] objectArray2 = iIlIIlIIlIlIllIIIIlIIIIIl.IlllIIIlIlllIllIlIIlllIlI;
        for (n = 0; n < n2 && n < n3; ++n) {
            Object object = objectArray[n];
            Object object2 = objectArray2[n];
            int n4 = ((Comparable)object).compareTo(object2);
            if (n4 == 0) continue;
            return n4;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.IIIIllIlIIIllIlllIlllllIl; ++i) {
            objectOutputStream.writeObject(this.IlllIIIlIlllIllIlIIlllIlI[i]);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.IlllIIIlIlllIllIlIIlllIlI = new Object[this.IIIIllIlIIIllIlllIlllllIl];
        for (int i = 0; i < this.IIIIllIlIIIllIlllIlllllIl; ++i) {
            this.IlllIIIlIlllIllIlIIlllIlI[i] = objectInputStream.readObject();
        }
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.IIIIllIIllIIIIllIllIIIlIl(n);
    }

    public Object clone() {
        return this.lIIIIllIIlIlIllIIIlIllIlI();
    }
}
