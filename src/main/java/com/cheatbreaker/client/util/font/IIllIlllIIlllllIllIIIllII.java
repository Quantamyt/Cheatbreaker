package com.cheatbreaker.client.util.font;

import java.util.concurrent.RecursiveAction;

public class IIllIlllIIlllllIllIIIllII
        extends RecursiveAction {
    private static final long lIIIIlIIllIIlIIlIIIlIIllI = 1L;
    private final int lIIIIIIIIIlIllIIllIlIIlIl;
    private final int IlllIIIlIlllIllIlIIlllIlI;
    private final int[] IIIIllIlIIIllIlllIlllllIl;
    private final lIlIIIIlIlIlllIlIIllIlIll IIIIllIIllIIIIllIllIIIlIl;

    public IIllIlllIIlllllIllIIIllII(int[] nArray, int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
        this.IIIIllIlIIIllIlllIlllllIl = nArray;
        this.IIIIllIIllIIIIllIllIIIlIl = lIlIIIIlIlIlllIlIIllIlIll2;
    }

    @Override
    protected void compute() {
        int n;
        int n2;
        int n3;
        int[] nArray = this.IIIIllIlIIIllIlllIlllllIl;
        int n4 = this.IlllIIIlIlllIllIlIIlllIlI - this.lIIIIIIIIIlIllIIllIlIIlIl;
        if (n4 < 8192) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.IlllIIIlIlllIllIlIIlllIlI, this.IIIIllIIllIIIIllIllIIIlIl);
            return;
        }
        int n5 = this.lIIIIIIIIIlIllIIllIlIIlIl + n4 / 2;
        int n6 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n7 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        int n8 = n4 / 8;
        n6 = IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n6, n6 + n8, n6 + 2 * n8, this.IIIIllIIllIIIIllIllIIIlIl);
        n5 = IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n5 - n8, n5, n5 + n8, this.IIIIllIIllIIIIllIllIIIlIl);
        n7 = IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n7 - 2 * n8, n7 - n8, n7, this.IIIIllIIllIIIIllIllIIIlIl);
        n5 = IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n6, n5, n7, this.IIIIllIIllIIIIllIllIIIlIl);
        int n9 = nArray[n5];
        int n10 = n3 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n11 = n2 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        while (true) {
            if (n10 <= n2 && (n = this.IIIIllIIllIIIIllIllIIIlIl.lIIIIlIIllIIlIIlIIIlIIllI(nArray[n10], n9)) <= 0) {
                if (n == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n3++, n10);
                }
                ++n10;
                continue;
            }
            while (n2 >= n10 && (n = this.IIIIllIIllIIIIllIllIIIlIl.lIIIIlIIllIIlIIlIIIlIIllI(nArray[n2], n9)) >= 0) {
                if (n == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n2, n11--);
                }
                --n2;
            }
            if (n10 > n2) break;
            IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n10++, n2--);
        }
        n8 = Math.min(n3 - this.lIIIIIIIIIlIllIIllIlIIlIl, n10 - n3);
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, this.lIIIIIIIIIlIllIIllIlIIlIl, n10 - n8, n8);
        n8 = Math.min(n11 - n2, this.IlllIIIlIlllIllIlIIlllIlI - n11 - 1);
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n10, this.IlllIIIlIlllIllIlIIlllIlI - n8, n8);
        n8 = n10 - n3;
        n = n11 - n2;
        if (n8 > 1 && n > 1) {
            IIllIlllIIlllllIllIIIllII.invokeAll(new IIllIlllIIlllllIllIIIllII(nArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8, this.IIIIllIIllIIIIllIllIIIlIl), new IIllIlllIIlllllIllIIIllII(nArray, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI, this.IIIIllIIllIIIIllIllIIIlIl));
        } else if (n8 > 1) {
            IIllIlllIIlllllIllIIIllII.invokeAll(new IIllIlllIIlllllIllIIIllII(nArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8, this.IIIIllIIllIIIIllIllIIIlIl));
        } else {
            IIllIlllIIlllllIllIIIllII.invokeAll(new IIllIlllIIlllllIllIIIllII(nArray, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI, this.IIIIllIIllIIIIllIllIIIlIl));
        }
    }
}
