package com.cheatbreaker.client.util.font;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public abstract class AbstractClassThree
        extends AbstractTestClassTwo
        implements ITestClassSeven,
        Cloneable {
    protected AbstractClassThree() {
    }

    @Override
    public abstract ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI();

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        Set set = (Set)object;
        if (set.size() != this.size()) {
            return false;
        }
        return this.containsAll((Collection)set);
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ITestClassFive iTestClassFive = this.lIIIIlIIllIIlIIlIIIlIIllI();
        while (n2-- != 0) {
            Object e = iTestClassFive.next();
            n += e == null ? 0 : e.hashCode();
        }
        return n;
    }

    @Override
    public Iterator iterator() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }
}
