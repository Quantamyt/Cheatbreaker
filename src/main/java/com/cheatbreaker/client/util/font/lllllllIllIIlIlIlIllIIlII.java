package com.cheatbreaker.client.util.font;

import java.util.concurrent.RecursiveAction;

public class lllllllIllIIlIlIlIllIIlII
        extends RecursiveAction {
    private static final long lIIIIlIIllIIlIIlIIIlIIllI = 1L;
    private final int lIIIIIIIIIlIllIIllIlIIlIl;
    private final int IlllIIIlIlllIllIlIIlllIlI;
    private final Object[] IIIIllIlIIIllIlllIlllllIl;

    public lllllllIllIIlIlIlIllIIlII(Object[] objectArray, int n, int n2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
        this.IIIIllIlIIIllIlllIlllllIl = objectArray;
    }

    @Override
    protected void compute() {
        int n;
        int n2;
        int n3;
        Object[] objectArray = this.IIIIllIlIIIllIlllIlllllIl;
        int n4 = this.IlllIIIlIlllIllIlIIlllIlI - this.lIIIIIIIIIlIllIIllIlIIlIl;
        if (n4 < 8192) {
            IlIIlIlIlIIlllIIIllIIIlII.IIIllIllIlIlllllllIlIlIII(objectArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.IlllIIIlIlllIllIlIIlllIlI);
            return;
        }
        int n5 = this.lIIIIIIIIIlIllIIllIlIIlIl + n4 / 2;
        int n6 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n7 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        int n8 = n4 / 8;
        n6 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, n6, n6 + n8, n6 + 2 * n8);
        n5 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, n5 - n8, n5, n5 + n8);
        n7 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, n7 - 2 * n8, n7 - n8, n7);
        n5 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, n6, n5, n7);
        Object object = objectArray[n5];
        int n9 = n3 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n10 = n2 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        while (true) {
            if (n9 <= n2 && (n = ((Comparable)objectArray[n9]).compareTo(object)) <= 0) {
                if (n == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n3++, n9);
                }
                ++n9;
                continue;
            }
            while (n2 >= n9 && (n = ((Comparable)objectArray[n2]).compareTo(object)) >= 0) {
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
            lllllllIllIIlIlIlIllIIlII.invokeAll(new lllllllIllIIlIlIlIllIIlII(objectArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8), new lllllllIllIIlIlIlIllIIlII(objectArray, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI));
        } else if (n8 > 1) {
            lllllllIllIIlIlIlIllIIlII.invokeAll(new lllllllIllIIlIlIlIllIIlII(objectArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8));
        } else {
            lllllllIllIIlIlIlIllIIlII.invokeAll(new lllllllIllIIlIlIlIllIIlII(objectArray, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI));
        }
    }
}
