package com.cheatbreaker.client.util.font;

import java.util.Comparator;
import java.util.concurrent.RecursiveAction;

public class IIllIlIIIlllllIIllllIllll
        extends RecursiveAction {
    private static final long lIIIIlIIllIIlIIlIIIlIIllI = 1L;
    private final int lIIIIIIIIIlIllIIllIlIIlIl;
    private final int IlllIIIlIlllIllIlIIlllIlI;
    private final Object[] IIIIllIlIIIllIlllIlllllIl;
    private final Comparator IIIIllIIllIIIIllIllIIIlIl;

    public IIllIlIIIlllllIIllllIllll(Object[] objectArray, int n, int n2, Comparator comparator) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
        this.IIIIllIlIIIllIlllIlllllIl = objectArray;
        this.IIIIllIIllIIIIllIllIIIlIl = comparator;
    }

    @Override
    protected void compute() {
        int n;
        int n2;
        int n3;
        Object[] objectArray = this.IIIIllIlIIIllIlllIlllllIl;
        int n4 = this.IlllIIIlIlllIllIlIIlllIlI - this.lIIIIIIIIIlIllIIllIlIIlIl;
        if (n4 < 8192) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.IlllIIIlIlllIllIlIIlllIlI, this.IIIIllIIllIIIIllIllIIIlIl);
            return;
        }
        int n5 = this.lIIIIIIIIIlIllIIllIlIIlIl + n4 / 2;
        int n6 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n7 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        int n8 = n4 / 8;
        n6 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n6, n6 + n8, n6 + 2 * n8, this.IIIIllIIllIIIIllIllIIIlIl);
        n5 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n5 - n8, n5, n5 + n8, this.IIIIllIIllIIIIllIllIIIlIl);
        n7 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n7 - 2 * n8, n7 - n8, n7, this.IIIIllIIllIIIIllIllIIIlIl);
        n5 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n6, n5, n7, this.IIIIllIIllIIIIllIllIIIlIl);
        Object object = objectArray[n5];
        int n9 = n3 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n10 = n2 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        while (true) {
            if (n9 <= n2 && (n = this.IIIIllIIllIIIIllIllIIIlIl.compare(objectArray[n9], object)) <= 0) {
                if (n == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n3++, n9);
                }
                ++n9;
                continue;
            }
            while (n2 >= n9 && (n = this.IIIIllIIllIIIIllIllIIIlIl.compare(objectArray[n2], object)) >= 0) {
                if (n == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n2, n10--);
                }
                --n2;
            }
            if (n9 > n2) break;
            IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n9++, n2--);
        }
        n8 = Math.min(n3 - this.lIIIIIIIIIlIllIIllIlIIlIl, n9 - n3);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, this.lIIIIIIIIIlIllIIllIlIIlIl, n9 - n8, n8);
        n8 = Math.min(n10 - n2, this.IlllIIIlIlllIllIlIIlllIlI - n10 - 1);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n9, this.IlllIIIlIlllIllIlIIlllIlI - n8, n8);
        n8 = n9 - n3;
        n = n10 - n2;
        if (n8 > 1 && n > 1) {
            IIllIlIIIlllllIIllllIllll.invokeAll(new IIllIlIIIlllllIIllllIllll(objectArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8, this.IIIIllIIllIIIIllIllIIIlIl), new IIllIlIIIlllllIIllllIllll(objectArray, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI, this.IIIIllIIllIIIIllIllIIIlIl));
        } else if (n8 > 1) {
            IIllIlIIIlllllIIllllIllll.invokeAll(new IIllIlIIIlllllIIllllIllll(objectArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8, this.IIIIllIIllIIIIllIllIIIlIl));
        } else {
            IIllIlIIIlllllIIllllIllll.invokeAll(new IIllIlIIIlllllIIllllIllll(objectArray, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI, this.IIIIllIIllIIIIllIllIIIlIl));
        }
    }
}
