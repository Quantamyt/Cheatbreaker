package com.cheatbreaker.client.util.font;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class lIllIlllllIlIlIIllIIIllII
        extends lIIllIIlIIIIIllIlIIllIIIl
        implements Serializable {
    private static final long IIIIllIlIIIllIlllIlllllIl = -7046029254386353129L;
    protected final lIlllIllIlllIlllIlIIIlIIl lIIIIlIIllIIlIIlIIIlIIllI;
    protected final int lIIIIIIIIIlIllIIllIlIIlIl;
    protected int IlllIIIlIlllIllIlIIlllIlI;
    private static final boolean IIIIllIIllIIIIllIllIIIlIl = false;

    public lIllIlllllIlIlIIllIIIllII(lIlllIllIlllIlllIlIIIlIIl lIlllIllIlllIlllIlIIIlIIl2, int n, int n2) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = lIlllIllIlllIlllIlIIIlIIl2;
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
    }

    private void IIIllIllIlIlllllllIlIlIII() {
    }

    @Override
    public boolean add(Object object) {
        this.lIIIIlIIllIIlIIlIIIlIIllI.add(this.IlllIIIlIlllIllIlIIlllIlI, object);
        ++this.IlllIIIlIlllIllIlIIlllIlI;
        return true;
    }

    @Override
    public void add(int n, Object object) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        this.lIIIIlIIllIIlIIlIIIlIIllI.add(this.lIIIIIIIIIlIllIIllIlIIlIl + n, object);
        ++this.IlllIIIlIlllIllIlIIlllIlI;
    }

    @Override
    public boolean addAll(int n, Collection collection) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        this.IlllIIIlIlllIllIlIIlllIlI += collection.size();
        return this.lIIIIlIIllIIlIIlIIIlIIllI.addAll(this.lIIIIIIIIIlIllIIllIlIIlIl + n, collection);
    }

    public Object get(int n) {
        this.IlllIIIlIlllIllIlIIlllIlI(n);
        return this.lIIIIlIIllIIlIIlIIIlIIllI.get(this.lIIIIIIIIIlIllIIllIlIIlIl + n);
    }

    @Override
    public Object remove(int n) {
        this.IlllIIIlIlllIllIlIIlllIlI(n);
        --this.IlllIIIlIlllIllIlIIlllIlI;
        return this.lIIIIlIIllIIlIIlIIIlIIllI.remove(this.lIIIIIIIIIlIllIIllIlIIlIl + n);
    }

    @Override
    public Object set(int n, Object object) {
        this.IlllIIIlIlllIllIlIIlllIlI(n);
        return this.lIIIIlIIllIIlIIlIIIlIIllI.set(this.lIIIIIIIIIlIllIIllIlIIlIl + n, object);
    }

    @Override
    public void clear() {
        this.IlllIIIlIlllIllIlIIlllIlI(0, this.size());
    }

    @Override
    public int size() {
        return this.IlllIIIlIlllIllIlIIlllIlI - this.lIIIIIIIIIlIllIIllIlIIlIl;
    }

    @Override
    public void lIIIIIIIIIlIllIIllIlIIlIl(int n, Object[] objectArray, int n2, int n3) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
        }
        this.lIIIIlIIllIIlIIlIIIlIIllI.lIIIIIIIIIlIllIIllIlIIlIl(this.lIIIIIIIIIlIllIIllIlIIlIl + n, objectArray, n2, n3);
    }

    @Override
    public void IlllIIIlIlllIllIlIIlllIlI(int n, int n2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        this.lIIIIIIIIIlIllIIllIlIIlIl(n2);
        this.lIIIIlIIllIIlIIlIIIlIIllI.IlllIIIlIlllIllIlIIlllIlI(this.lIIIIIIIIIlIllIIllIlIIlIl + n, this.lIIIIIIIIIlIllIIllIlIIlIl + n2);
        this.IlllIIIlIlllIllIlIIlllIlI -= n2 - n;
    }

    @Override
    public void lIIIIlIIllIIlIIlIIIlIIllI(int n, Object[] objectArray, int n2, int n3) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        this.lIIIIlIIllIIlIIlIIIlIIllI.lIIIIlIIllIIlIIlIIIlIIllI(this.lIIIIIIIIIlIllIIllIlIIlIl + n, objectArray, n2, n3);
        this.IlllIIIlIlllIllIlIIlllIlI += n3;
    }

    @Override
    public llllllllllllIlIIIIIIIIlll IIIIllIIllIIIIllIllIIIlIl(int n) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        return new IlllIlIlIlIIIllIIllIlIIII(this, n);
    }

    @Override
    public lIlllIllIlllIlllIlIIIlIIl lIIIIlIIllIIlIIlIIIlIIllI(int n, int n2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        this.lIIIIIIIIIlIllIIllIlIIlIl(n2);
        if (n > n2) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new lIllIlllllIlIlIIllIIIllII(this, n, n2);
    }

    @Override
    public boolean remove(Object object) {
        int n = this.indexOf(object);
        if (n == -1) {
            return false;
        }
        this.remove(n);
        return true;
    }

    @Override
    public List subList(int n, int n2) {
        return this.lIIIIlIIllIIlIIlIIIlIIllI(n, n2);
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.IIIIllIIllIIIIllIllIIIlIl(n);
    }

    @Override
    public ListIterator listIterator() {
        return super.IlIlIIIlllIIIlIlllIlIllIl();
    }

    @Override
    public Iterator iterator() {
        return super.IIIIllIIllIIIIllIllIIIlIl();
    }

    @Override
    public int compareTo(Object object) {
        return super.lIIIIlIIllIIlIIlIIIlIIllI((List)object);
    }

    @Override
    public ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI() {
        return super.IIIIllIIllIIIIllIllIIIlIl();
    }
}
