package com.cheatbreaker.client.util.font;

import java.util.concurrent.RecursiveAction;

public class IlIlIIlIllllIllIIlIlIllII
        extends RecursiveAction {
    private static final long lIIIIlIIllIIlIIlIIIlIIllI = 1L;
    private final int lIIIIIIIIIlIllIIllIlIIlIl;
    private final int IlllIIIlIlllIllIlIIlllIlI;
    private final int[] IIIIllIlIIIllIlllIlllllIl;
    private final Object[] IIIIllIIllIIIIllIllIIIlIl;

    public IlIlIIlIllllIllIIlIlIllII(int[] nArray, Object[] objectArray, int n, int n2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
        this.IIIIllIIllIIIIllIllIIIlIl = objectArray;
        this.IIIIllIlIIIllIlllIlllllIl = nArray;
    }

    @Override
    protected void compute() {
        int n;
        int n2;
        int n3;
        Object[] objectArray = this.IIIIllIIllIIIIllIllIIIlIl;
        int n4 = this.IlllIIIlIlllIllIlIIlllIlI - this.lIIIIIIIIIlIllIIllIlIIlIl;
        if (n4 < 8192) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIIllIlIIIllIlllIlllllIl, objectArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.IlllIIIlIlllIllIlIIlllIlI);
            return;
        }
        int n5 = this.lIIIIIIIIIlIllIIllIlIIlIl + n4 / 2;
        int n6 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n7 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        int n8 = n4 / 8;
        n6 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIIllIlIIIllIlllIlllllIl, objectArray, n6, n6 + n8, n6 + 2 * n8);
        n5 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIIllIlIIIllIlllIlllllIl, objectArray, n5 - n8, n5, n5 + n8);
        n7 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIIllIlIIIllIlllIlllllIl, objectArray, n7 - 2 * n8, n7 - n8, n7);
        n5 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIIllIlIIIllIlllIlllllIl, objectArray, n6, n5, n7);
        Object object = objectArray[this.IIIIllIlIIIllIlllIlllllIl[n5]];
        int n9 = n3 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n10 = n2 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        while (true) {
            if (n9 <= n2 && (n = ((Comparable)objectArray[this.IIIIllIlIIIllIlllIlllllIl[n9]]).compareTo(object)) <= 0) {
                if (n == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(this.IIIIllIlIIIllIlllIlllllIl, n3++, n9);
                }
                ++n9;
                continue;
            }
            while (n2 >= n9 && (n = ((Comparable)objectArray[this.IIIIllIlIIIllIlllIlllllIl[n2]]).compareTo(object)) >= 0) {
                if (n == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(this.IIIIllIlIIIllIlllIlllllIl, n2, n10--);
                }
                --n2;
            }
            if (n9 > n2) break;
            IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(this.IIIIllIlIIIllIlllIlllllIl, n9++, n2--);
        }
        n8 = Math.min(n3 - this.lIIIIIIIIIlIllIIllIlIIlIl, n9 - n3);
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(this.IIIIllIlIIIllIlllIlllllIl, this.lIIIIIIIIIlIllIIllIlIIlIl, n9 - n8, n8);
        n8 = Math.min(n10 - n2, this.IlllIIIlIlllIllIlIIlllIlI - n10 - 1);
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(this.IIIIllIlIIIllIlllIlllllIl, n9, this.IlllIIIlIlllIllIlIIlllIlI - n8, n8);
        n8 = n9 - n3;
        n = n10 - n2;
        if (n8 > 1 && n > 1) {
            IlIlIIlIllllIllIIlIlIllII.invokeAll(new IlIlIIlIllllIllIIlIlIllII(this.IIIIllIlIIIllIlllIlllllIl, objectArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8), new IlIlIIlIllllIllIIlIlIllII(this.IIIIllIlIIIllIlllIlllllIl, objectArray, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI));
        } else if (n8 > 1) {
            IlIlIIlIllllIllIIlIlIllII.invokeAll(new IlIlIIlIllllIllIIlIlIllII(this.IIIIllIlIIIllIlllIlllllIl, objectArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8));
        } else {
            IlIlIIlIllllIllIIlIlIllII.invokeAll(new IlIlIIlIllllIllIIlIlIllII(this.IIIIllIlIIIllIlllIlllllIl, objectArray, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI));
        }
    }
}
