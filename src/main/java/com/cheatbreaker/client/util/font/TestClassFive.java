package com.cheatbreaker.client.util.font;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
public abstract class TestClassFive
        extends AbstractTestClassOne
        implements Serializable,
        ITestClassFour {
    private static final long lIIIIIIIIIlIllIIllIlIIlIl = -4940583368468432370L;

    protected TestClassFive() {
    }

    @Override
    public boolean containsValue(Object object) {
        return this.IlllIIIlIlllIllIlIIlllIlI().contains(object);
    }

    @Override
    public boolean containsKey(Object object) {
        return this.lIIIIIIIIIlIllIIllIlIIlIl().contains(object);
    }

    public void putAll(Map map) {
        int n = map.size();
        Iterator iterator = map.entrySet().iterator();
        if (map instanceof ITestClassFour) {
            while (n-- != 0) {
                llIlIlIlIIlIIllIllIIlllIl llIlIlIlIIlIIllIllIIlllIl2 = (llIlIlIlIIlIIllIllIIlllIl)iterator.next();
                this.put(llIlIlIlIIlIIllIllIIlllIl2.getKey(), llIlIlIlIIlIIllIllIIlllIl2.getValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry entry = (Entry) iterator.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ITestClassSeven lIIIIIIIIIlIllIIllIlIIlIl() {
        return new TestClassEleven(this);
    }

    @Override
    public ITestClassEight IlllIIIlIlllIllIlIIlllIlI() {
        return new lllIIIIIlIIIIIIIIlllIIIII(this);
    }

    @Override
    public ITestClassSeven IIIIllIlIIIllIlllIlllllIl() {
        return this.IIIIllIIllIIIIllIllIIIlIl();
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ITestClassFive iTestClassFive = this.IIIIllIlIIIllIlllIlllllIl().lIIIIlIIllIIlIIlIIIlIIllI();
        while (n2-- != 0) {
            n += ((Map.Entry)iTestClassFive.next()).hashCode();
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Map)) {
            return false;
        }
        Map map = (Map)object;
        if (map.size() != this.size()) {
            return false;
        }
        return this.IIIIllIlIIIllIlllIlllllIl().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ITestClassFive iTestClassFive = this.IIIIllIlIIIllIlllIlllllIl().lIIIIlIIllIIlIIlIIIlIIllI();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            llIlIlIlIIlIIllIllIIlllIl llIlIlIlIIlIIllIllIIlllIl2 = (llIlIlIlIIlIIllIllIIlllIl)iTestClassFive.next();
            if (this == llIlIlIlIIlIIllIllIIlllIl2.getKey()) {
                stringBuilder.append("(this map)");
            } else {
                stringBuilder.append(String.valueOf(llIlIlIlIIlIIllIllIIlllIl2.getKey()));
            }
            stringBuilder.append("=>");
            if (this == llIlIlIlIIlIIllIllIIlllIl2.getValue()) {
                stringBuilder.append("(this map)");
                continue;
            }
            stringBuilder.append(String.valueOf(llIlIlIlIIlIIllIllIIlllIl2.getValue()));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public Set entrySet() {
        return this.IIIIllIlIIIllIlllIlllllIl();
    }

    @Override
    public Collection values() {
        return this.IlllIIIlIlllIllIlIIlllIlI();
    }

    @Override
    public Set keySet() {
        return this.lIIIIIIIIIlIllIIllIlIIlIl();
    }
}
