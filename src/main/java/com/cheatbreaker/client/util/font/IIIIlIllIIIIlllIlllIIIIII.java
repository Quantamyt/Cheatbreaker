package com.cheatbreaker.client.util.font;

import java.util.concurrent.RecursiveAction;

public class IIIIlIllIIIIlllIlllIIIIII
        extends RecursiveAction {
    private static final long lIIIIlIIllIIlIIlIIIlIIllI = 1L;
    private final int lIIIIIIIIIlIllIIllIlIIlIl;
    private final int IlllIIIlIlllIllIlIIlllIlI;
    private final Object[] IIIIllIlIIIllIlllIlllllIl;
    private final Object[] IIIIllIIllIIIIllIllIIIlIl;

    public IIIIlIllIIIIlllIlllIIIIII(Object[] objectArray, Object[] objectArray2, int n, int n2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
        this.IIIIllIlIIIllIlllIlllllIl = objectArray;
        this.IIIIllIIllIIIIllIllIIIlIl = objectArray2;
    }

    @Override
    protected void compute() {
        int n;
        int n2;
        int n3;
        Object[] objectArray = this.IIIIllIlIIIllIlllIlllllIl;
        Object[] objectArray2 = this.IIIIllIIllIIIIllIllIIIlIl;
        int n4 = this.IlllIIIlIlllIllIlIIlllIlI - this.lIIIIIIIIIlIllIIllIlIIlIl;
        if (n4 < 8192) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, objectArray2, this.lIIIIIIIIIlIllIIllIlIIlIl, this.IlllIIIlIlllIllIlIIlllIlI);
            return;
        }
        int n5 = this.lIIIIIIIIIlIllIIllIlIIlIl + n4 / 2;
        int n6 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n7 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        int n8 = n4 / 8;
        n6 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, objectArray2, n6, n6 + n8, n6 + 2 * n8);
        n5 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, objectArray2, n5 - n8, n5, n5 + n8);
        n7 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, objectArray2, n7 - 2 * n8, n7 - n8, n7);
        n5 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, objectArray2, n6, n5, n7);
        Object object = objectArray[n5];
        Object object2 = objectArray2[n5];
        int n9 = n3 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n10 = n2 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        while (true) {
            int n11;
            if (n9 <= n2 && (n = (n11 = ((Comparable)objectArray[n9]).compareTo(object)) == 0 ? ((Comparable)objectArray2[n9]).compareTo(object2) : n11) <= 0) {
                if (n == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, objectArray2, n3++, n9);
                }
                ++n9;
                continue;
            }
            while (n2 >= n9 && (n = (n11 = ((Comparable)objectArray[n2]).compareTo(object)) == 0 ? ((Comparable)objectArray2[n2]).compareTo(object2) : n11) >= 0) {
                if (n == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, objectArray2, n2, n10--);
                }
                --n2;
            }
            if (n9 > n2) break;
            IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, objectArray2, n9++, n2--);
        }
        n8 = Math.min(n3 - this.lIIIIIIIIIlIllIIllIlIIlIl, n9 - n3);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, objectArray2, this.lIIIIIIIIIlIllIIllIlIIlIl, n9 - n8, n8);
        n8 = Math.min(n10 - n2, this.IlllIIIlIlllIllIlIIlllIlI - n10 - 1);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, objectArray2, n9, this.IlllIIIlIlllIllIlIIlllIlI - n8, n8);
        n8 = n9 - n3;
        n = n10 - n2;
        if (n8 > 1 && n > 1) {
            IIIIlIllIIIIlllIlllIIIIII.invokeAll(new IIIIlIllIIIIlllIlllIIIIII(objectArray, objectArray2, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8), new IIIIlIllIIIIlllIlllIIIIII(objectArray, objectArray2, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI));
        } else if (n8 > 1) {
            IIIIlIllIIIIlllIlllIIIIII.invokeAll(new IIIIlIllIIIIlllIlllIIIIII(objectArray, objectArray2, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8));
        } else {
            IIIIlIllIIIIlllIlllIIIIII.invokeAll(new IIIIlIllIIIIlllIlllIIIIII(objectArray, objectArray2, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI));
        }
    }
}
