package com.cheatbreaker.client.util.font;

import java.util.Collection;
import java.util.Iterator;

public interface ITestClassEight
        extends ITestClassNine,
        Collection {
    @Override
    public ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI();

    @Deprecated
    public ITestClassFive IlllIIIlIlllIllIlIIlllIlI();

    public Object[] toArray(Object[] var1);

    @Override
    default public Iterator iterator() {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }
}
