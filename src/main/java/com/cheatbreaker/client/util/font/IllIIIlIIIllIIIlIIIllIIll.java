package com.cheatbreaker.client.util.font;

import java.util.Map;

final class IllIIIlIIIllIIIlIIIllIIll
        implements Map.Entry,
        llIlIlIlIIlIIllIllIIlllIl {
    int lIIIIlIIllIIlIIlIIIlIIllI;
    final TestClassFour lIIIIIIIIIlIllIIllIlIIlIl;

    IllIIIlIIIllIIIlIIIllIIll(TestClassFour testClassFour, int n) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = testClassFour;
        this.lIIIIlIIllIIlIIlIIIlIIllI = n;
    }

    IllIIIlIIIllIIIlIIIllIIll(TestClassFour testClassFour) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = testClassFour;
    }

    public Object getKey() {
        return this.lIIIIIIIIIlIllIIllIlIIlIl.IlllIllIlIIIIlIIlIIllIIIl[this.lIIIIlIIllIIlIIlIIIlIIllI];
    }

    public Object getValue() {
        return this.lIIIIIIIIIlIllIIllIlIIlIl.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI];
    }

    public Object setValue(Object object) {
        Object object2 = this.lIIIIIIIIIlIllIIllIlIIlIl.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI];
        this.lIIIIIIIIIlIllIIllIlIIlIl.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI] = object;
        return object2;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry)object;
        return (this.lIIIIIIIIIlIllIIllIlIIlIl.IlllIllIlIIIIlIIlIIllIIIl[this.lIIIIlIIllIIlIIlIIIlIIllI] == null ? entry.getKey() == null : this.lIIIIIIIIIlIllIIllIlIIlIl.IlllIllIlIIIIlIIlIIllIIIl[this.lIIIIlIIllIIlIIlIIIlIIllI].equals(entry.getKey())) && (this.lIIIIIIIIIlIllIIllIlIIlIl.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI] == null ? entry.getValue() == null : this.lIIIIIIIIIlIllIIllIlIIlIl.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI].equals(entry.getValue()));
    }

    @Override
    public int hashCode() {
        return (this.lIIIIIIIIIlIllIIllIlIIlIl.IlllIllIlIIIIlIIlIIllIIIl[this.lIIIIlIIllIIlIIlIIIlIIllI] == null ? 0 : this.lIIIIIIIIIlIllIIllIlIIlIl.IlllIllIlIIIIlIIlIIllIIIl[this.lIIIIlIIllIIlIIlIIIlIIllI].hashCode()) ^ (this.lIIIIIIIIIlIllIIllIlIIlIl.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI] == null ? 0 : this.lIIIIIIIIIlIllIIllIlIIlIl.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI].hashCode());
    }

    public String toString() {
        return this.lIIIIIIIIIlIllIIllIlIIlIl.IlllIllIlIIIIlIIlIIllIIIl[this.lIIIIlIIllIIlIIlIIIlIIllI] + "=>" + this.lIIIIIIIIIlIllIIllIlIIlIl.IlIlllIIIIllIllllIllIIlIl[this.lIIIIlIIllIIlIIlIIIlIIllI];
    }
}
