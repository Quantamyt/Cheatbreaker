package com.cheatbreaker.client.util.font;

import java.util.Iterator;
import java.util.ListIterator;

public class lIIIIlIlIllIIIIIIIlIlIIIl {
    public static final IlIlllIlIIIllIIIlIIllllIl lIIIIlIIllIIlIIlIIIlIIllI = new IlIlllIlIIIllIIIlIIllllIl();

    private lIIIIlIlIllIIIIIIIlIlIIIl() {
    }

    public static llllllllllllIlIIIIIIIIlll lIIIIlIIllIIlIIlIIIlIIllI(Object object) {
        return new IIIIllIIIlllIlIIllIIlllll(object);
    }

    public static llllllllllllIlIIIIIIIIlll lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n, int n2) {
        IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n, n2);
        return new IIIlIllIIIlIIIIIIIIIIIlII(objectArray, n, n2);
    }

    public static llllllllllllIlIIIIIIIIlll lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray) {
        return new IIIlIllIIIlIIIIIIIIIIIlII(objectArray, 0, objectArray.length);
    }

    public static int lIIIIlIIllIIlIIlIIIlIIllI(Iterator iterator, Object[] objectArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > objectArray.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && iterator.hasNext()) {
            objectArray[n++] = iterator.next();
        }
        return n2 - n3 - 1;
    }

    public static int lIIIIlIIllIIlIIlIIIlIIllI(Iterator iterator, Object[] objectArray) {
        return lIIIIlIlIllIIIIIIIlIlIIIl.lIIIIlIIllIIlIIlIIIlIIllI(iterator, objectArray, 0, objectArray.length);
    }

    public static Object[] lIIIIlIIllIIlIIlIIIlIIllI(Iterator iterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        Object[] objectArray = new Object[16];
        int n2 = 0;
        while (n-- != 0 && iterator.hasNext()) {
            if (n2 == objectArray.length) {
                objectArray = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, n2 + 1);
            }
            objectArray[n2++] = iterator.next();
        }
        return IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, n2);
    }

    public static Object[] lIIIIlIIllIIlIIlIIIlIIllI(Iterator iterator) {
        return lIIIIlIlIllIIIIIIIlIlIIIl.lIIIIlIIllIIlIIlIIIlIIllI(iterator, Integer.MAX_VALUE);
    }

    public static int lIIIIlIIllIIlIIlIIIlIIllI(Iterator iterator, ITestClassEight iTestClassEight, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && iterator.hasNext()) {
            iTestClassEight.add(iterator.next());
        }
        return n - n2 - 1;
    }

    public static long lIIIIlIIllIIlIIlIIIlIIllI(Iterator iterator, ITestClassEight iTestClassEight) {
        long l = 0L;
        while (iterator.hasNext()) {
            iTestClassEight.add(iterator.next());
            ++l;
        }
        return l;
    }

    public static int lIIIIIIIIIlIllIIllIlIIlIl(Iterator iterator, ITestClassEight iTestClassEight, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && iterator.hasNext()) {
            iTestClassEight.add(iterator.next());
        }
        return n - n2 - 1;
    }

    public static int lIIIIIIIIIlIllIIllIlIIlIl(Iterator iterator, ITestClassEight iTestClassEight) {
        return lIIIIlIlIllIIIIIIIlIlIIIl.lIIIIIIIIIlIllIIllIlIIlIl(iterator, iTestClassEight, Integer.MAX_VALUE);
    }

    public static lIlllIllIlllIlllIlIIIlIIl lIIIIIIIIIlIllIIllIlIIlIl(Iterator iterator, int n) {
        IIlIIlIIlIlIllIIIIlIIIIIl iIlIIlIIlIlIllIIIIlIIIIIl = new IIlIIlIIlIlIllIIIIlIIIIIl();
        lIIIIlIlIllIIIIIIIlIlIIIl.lIIIIIIIIIlIllIIllIlIIlIl(iterator, iIlIIlIIlIlIllIIIIlIIIIIl, n);
        iIlIIlIIlIlIllIIIIlIIIIIl.IllIIIIIIIlIlIllllIIllIII();
        return iIlIIlIIlIlIllIIIIlIIIIIl;
    }

    public static lIlllIllIlllIlllIlIIIlIIl lIIIIIIIIIlIllIIllIlIIlIl(Iterator iterator) {
        return lIIIIlIlIllIIIIIIIlIlIIIl.lIIIIIIIIIlIllIIllIlIIlIl(iterator, Integer.MAX_VALUE);
    }

    public static ITestClassFive IlllIIIlIlllIllIlIIlllIlI(Iterator iterator) {
        if (iterator instanceof ITestClassFive) {
            return (ITestClassFive)iterator;
        }
        return new IIIlIllllllllIllIlIlIIIII(iterator);
    }

    public static llllllllllllIlIIIIIIIIlll lIIIIlIIllIIlIIlIIIlIIllI(ListIterator listIterator) {
        if (listIterator instanceof llllllllllllIlIIIIIIIIlll) {
            return (llllllllllllIlIIIIIIIIlll)listIterator;
        }
        return new IIIlIIlllIlIIlIIIllIIlIII(listIterator);
    }

    public static ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI(ITestClassFive[] iTestClassFiveArray) {
        return lIIIIlIlIllIIIIIIIlIlIIIl.lIIIIlIIllIIlIIlIIIlIIllI(iTestClassFiveArray, 0, iTestClassFiveArray.length);
    }

    public static ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI(ITestClassFive[] iTestClassFiveArray, int n, int n2) {
        return new lllIlIllllllIIIIlIIIIIllI(iTestClassFiveArray, n, n2);
    }

    public static ITestClassFive lIIIIlIIllIIlIIlIIIlIIllI(ITestClassFive iTestClassFive) {
        return new llllIIIllIlIIIIIllIlllllI(iTestClassFive);
    }

    public static llIIIIlllIIIIIIIllIllllll lIIIIlIIllIIlIIlIIIlIIllI(llIIIIlllIIIIIIIllIllllll llIIIIlllIIIIIIIllIllllll2) {
        return new llIlIIIlIIIlIIIIllllIlIII(llIIIIlllIIIIIIIllIllllll2);
    }

    public static llllllllllllIlIIIIIIIIlll lIIIIlIIllIIlIIlIIIlIIllI(llllllllllllIlIIIIIIIIlll llllllllllllIlIIIIIIIIlll2) {
        return new IIIlIIIlIIlIIIIlIlIllIlIl(llllllllllllIlIIIIIIIIlll2);
    }
}
