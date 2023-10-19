package com.cheatbreaker.client.util.font;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class lIIllIIlIIIIIllIlIIllIIIl
        extends AbstractTestClassTwo
        implements lIlllIllIlllIlllIlIIIlIIl,
        llIlIIllIIlIIIllIlIIIllII {
    protected lIIllIIlIIIIIllIlIIllIIIl() {
    }

    protected void lIIIIIIIIIlIllIIllIlIIlIl(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than list size (" + this.size() + ")");
        }
    }

    protected void IlllIIIlIlllIllIlIIlllIlI(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n >= this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size() + ")");
        }
    }

    public void add(int n, Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(Object object) {
        this.add(this.size(), object);
        return true;
    }

    public Object remove(int n) {
        throw new UnsupportedOperationException();
    }

    public Object set(int n, Object object) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int n, Collection collection) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        int n2 = collection.size();
        if (n2 == 0) {
            return false;
        }
        Iterator iterator = collection.iterator();
        while (n2-- != 0) {
            this.add(n++, iterator.next());
        }
        return true;
    }

    @Override
    public boolean addAll(Collection collection) {
        return this.addAll(this.size(), collection);
    }

    @Override
    @Deprecated
    public llllllllllllIlIIIIIIIIlll IIIIllIlIIIllIlllIlllllIl() {
        return this.IlIlIIIlllIIIlIlllIlIllIl();
    }

    @Override
    @Deprecated
    public llllllllllllIlIIIIIIIIlll IIIIllIlIIIllIlllIlllllIl(int n) {
        return this.IIIIllIIllIIIIllIllIIIlIl(n);
    }

    @Override
    public llllllllllllIlIIIIIIIIlll IIIIllIIllIIIIllIllIIIlIl() {
        return this.IlIlIIIlllIIIlIlllIlIllIl();
    }

    @Override
    public llllllllllllIlIIIIIIIIlll IlIlIIIlllIIIlIlllIlIllIl() {
        return this.IIIIllIIllIIIIllIllIIIlIl(0);
    }

    @Override
    public llllllllllllIlIIIIIIIIlll IIIIllIIllIIIIllIllIIIlIl(int n) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        return new IIlIllllIllllllIIllIllIIl(this, n);
    }

    @Override
    public boolean contains(Object object) {
        return this.indexOf(object) >= 0;
    }

    @Override
    public int indexOf(Object object) {
        llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll2 = this.IlIlIIIlllIIIlIlllIlIllIl();
        while (llllllllllllIlIIIIIIIIlll2.hasNext()) {
            Object e = llllllllllllIlIIIIIIIIlll2.next();
            if (!(object == null ? e == null : object.equals(e))) continue;
            return llllllllllllIlIIIIIIIIlll2.previousIndex();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll2 = this.IIIIllIIllIIIIllIllIIIlIl(this.size());
        while (llllllllllllIlIIIIIIIIlll2.hasPrevious()) {
            Object e = llllllllllllIlIIIIIIIIlll2.previous();
            if (!(object == null ? e == null : object.equals(e))) continue;
            return llllllllllllIlIIIIIIIIlll2.nextIndex();
        }
        return -1;
    }

    @Override
    public void IlIlIIIlllIIIlIlllIlIllIl(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add((Object)null);
            }
        } else {
            while (n2-- != n) {
                this.remove(n2);
            }
        }
    }

    @Override
    public lIlllIllIlllIlllIlIIIlIIl lIIIIlIIllIIlIIlIIIlIIllI(int n, int n2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        this.lIIIIIIIIIlIllIIllIlIIlIl(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new lIllIlllllIlIlIIllIIIllII(this, n, n2);
    }

    @Override
    @Deprecated
    public lIlllIllIlllIlllIlIIIlIIl lIIIIIIIIIlIllIIllIlIIlIl(int n, int n2) {
        return this.lIIIIlIIllIIlIIlIIIlIIllI(n, n2);
    }

    @Override
    public void IlllIIIlIlllIllIlIIlllIlI(int n, int n2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n2);
        llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll2 = this.IIIIllIIllIIIIllIllIIIlIl(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            llllllllllllIlIIIIIIIIlll2.next();
            llllllllllllIlIIIIIIIIlll2.remove();
        }
    }

    @Override
    public void lIIIIlIIllIIlIIlIIIlIIllI(int n, Object[] objectArray, int n2, int n3) {
        this.lIIIIIIIIIlIllIIllIlIIlIl(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > objectArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + objectArray.length + ")");
        }
        while (n3-- != 0) {
            this.add(n++, objectArray[n2++]);
        }
    }

    @Override
    public void lIIIIlIIllIIlIIlIIIlIIllI(int n, Object[] objectArray) {
        this.lIIIIlIIllIIlIIlIIIlIIllI(n, objectArray, 0, objectArray.length);
    }

    @Override
    public void lIIIIIIIIIlIllIIllIlIIlIl(int n, Object[] objectArray, int n2, int n3) {
        llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll2 = this.IIIIllIIllIIIIllIllIIIlIl(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > objectArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + objectArray.length + ")");
        }
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        while (n3-- != 0) {
            objectArray[n2++] = llllllllllllIlIIIIIIIIlll2.next();
        }
    }

    private boolean lIIIIlIIllIIlIIlIIIlIIllI(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof List)) {
            return false;
        }
        List list = (List)object;
        int n = this.size();
        if (n != list.size()) {
            return false;
        }
        llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll2 = this.IlIlIIIlllIIIlIlllIlIllIl();
        ListIterator listIterator = list.listIterator();
        while (n-- != 0) {
            if (this.lIIIIlIIllIIlIIlIIIlIIllI(llllllllllllIlIIIIIIIIlll2.next(), listIterator.next())) continue;
            return false;
        }
        return true;
    }

    public int lIIIIlIIllIIlIIlIIIlIIllI(List list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof lIlllIllIlllIlllIlIIIlIIl) {
            llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll2 = this.IlIlIIIlllIIIlIlllIlIllIl();
            llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll3 = ((lIlllIllIlllIlllIlIIIlIIl)list).IlIlIIIlllIIIlIlllIlIllIl();
            while (llllllllllllIlIIIIIIIIlll2.hasNext() && llllllllllllIlIIIIIIIIlll3.hasNext()) {
                Object e;
                Object e2 = llllllllllllIlIIIIIIIIlll2.next();
                int n = ((Comparable)e2).compareTo(e = llllllllllllIlIIIIIIIIlll3.next());
                if (n == 0) continue;
                return n;
            }
            return llllllllllllIlIIIIIIIIlll3.hasNext() ? -1 : (llllllllllllIlIIIIIIIIlll2.hasNext() ? 1 : 0);
        }
        llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll4 = this.IlIlIIIlllIIIlIlllIlIllIl();
        ListIterator listIterator = list.listIterator();
        while (llllllllllllIlIIIIIIIIlll4.hasNext() && listIterator.hasNext()) {
            int n = ((Comparable)llllllllllllIlIIIIIIIIlll4.next()).compareTo(listIterator.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator.hasNext() ? -1 : (llllllllllllIlIIIIIIIIlll4.hasNext() ? 1 : 0);
    }

    @Override
    public int hashCode() {
        llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll2 = this.IIIIllIIllIIIIllIllIIIlIl();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            Object e = llllllllllllIlIIIIIIIIlll2.next();
            n = 31 * n + (e == null ? 0 : e.hashCode());
        }
        return n;
    }

    @Override
    public void lIIIIlIIllIIlIIlIIIlIIllI(Object object) {
        this.add(object);
    }

    @Override
    public Object g_() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.remove(this.size() - 1);
    }

    @Override
    public Object lIIIIIIIIIlIllIIllIlIIlIl() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.get(this.size() - 1);
    }

    @Override
    public Object lIIIIlIIllIIlIIlIIIlIIllI(int n) {
        return this.get(this.size() - 1 - n);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll2 = this.IIIIllIIllIIIIllIllIIIlIl();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Object e = llllllllllllIlIIIIIIIIlll2.next();
            if (this == e) {
                stringBuilder.append("(this list)");
                continue;
            }
            stringBuilder.append(String.valueOf(e));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI() {
        return this.IIIIllIIllIIIIllIllIIIlIl();
    }

    @Override
    public Iterator iterator() {
        return this.IIIIllIIllIIIIllIllIIIlIl();
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
        return this.IlIlIIIlllIIIlIlllIlIllIl();
    }

    public int compareTo(Object object) {
        return this.lIIIIlIIllIIlIIlIIIlIIllI((List)object);
    }
}
