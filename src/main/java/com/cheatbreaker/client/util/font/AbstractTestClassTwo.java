package com.cheatbreaker.client.util.font;

import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractTestClassTwo
        extends AbstractCollection
        implements ITestClassEight {
    protected AbstractTestClassTwo() {
    }

    @Override
    public Object[] toArray() {
        Object[] objectArray = new Object[this.size()];
        lIIIIlIlIllIIIIIIIlIlIIIl.lIIIIlIIllIIlIIlIIIlIIllI((Iterator)this.lIIIIlIIllIIlIIlIIIlIIllI(), objectArray);
        return objectArray;
    }

    @Override
    public Object[] toArray(Object[] objectArray) {
        int n = this.size();
        if (objectArray.length < n) {
            objectArray = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), n);
        }
        lIIIIlIlIllIIIIIIIlIlIIIl.lIIIIlIIllIIlIIlIIIlIIllI((Iterator)this.lIIIIlIIllIIlIIlIIIlIIllI(), objectArray);
        if (n < objectArray.length) {
            objectArray[n] = null;
        }
        return objectArray;
    }

    public boolean addAll(Collection collection) {
        boolean bl = false;
        Iterator iterator = collection.iterator();
        int n = collection.size();
        while (n-- != 0) {
            if (!this.add(iterator.next())) continue;
            bl = true;
        }
        return bl;
    }

    public boolean add(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public ITestClassFive IlllIIIlIlllIllIlIIlllIlI() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }

    @Override
    public abstract ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI();

    @Override
    public boolean remove(Object object) {
        return this.lIIIIIIIIIlIllIIllIlIIlIl(object);
    }

    public boolean lIIIIIIIIIlIllIIllIlIIlIl(Object object) {
        ITestClassFive iTestClassFive = this.lIIIIlIIllIIlIIlIIIlIIllI();
        while (iTestClassFive.hasNext()) {
            if (object != iTestClassFive.next()) continue;
            iTestClassFive.remove();
            return true;
        }
        return false;
    }

    public boolean containsAll(Collection collection) {
        int n = collection.size();
        Iterator iterator = collection.iterator();
        while (n-- != 0) {
            if (this.contains(iterator.next())) continue;
            return false;
        }
        return true;
    }

    public boolean retainAll(Collection collection) {
        boolean bl = false;
        int n = this.size();
        ITestClassFive iTestClassFive = this.lIIIIlIIllIIlIIlIIIlIIllI();
        while (n-- != 0) {
            if (collection.contains(iTestClassFive.next())) continue;
            iTestClassFive.remove();
            bl = true;
        }
        return bl;
    }

    public boolean removeAll(Collection collection) {
        boolean bl = false;
        int n = collection.size();
        Iterator iterator = collection.iterator();
        while (n-- != 0) {
            if (!this.remove(iterator.next())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ITestClassFive iTestClassFive = this.lIIIIlIIllIIlIIlIIIlIIllI();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Object e = iTestClassFive.next();
            if (this == e) {
                stringBuilder.append("(this collection)");
                continue;
            }
            stringBuilder.append(String.valueOf(e));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public Iterator iterator() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }
}
