package com.cheatbreaker.client.util.font;

import java.util.concurrent.RecursiveAction;

public class IIlIIIlIIIIIlIllIlIIlllII
        extends RecursiveAction {
    private static final long lIIIIlIIllIIlIIlIIIlIIllI = 1L;
    private final int lIIIIIIIIIlIllIIllIlIIlIl;
    private final int IlllIIIlIlllIllIlIIlllIlI;
    private final int[] IIIIllIlIIIllIlllIlllllIl;

    public IIlIIIlIIIIIlIllIlIIlllII(int[] nArray, int n, int n2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
        this.IIIIllIlIIIllIlllIlllllIl = nArray;
    }

    @Override
    protected void compute() {
        int n;
        int n2;
        int n3;
        int[] nArray = this.IIIIllIlIIIllIlllIlllllIl;
        int n4 = this.IlllIIIlIlllIllIlIIlllIlI - this.lIIIIIIIIIlIllIIllIlIIlIl;
        if (n4 < 8192) {
            IlllIlIllIlllIlIIlIlllIll.IIIllIllIlIlllllllIlIlIII(nArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.IlllIIIlIlllIllIlIIlllIlI);
            return;
        }
        int n5 = this.lIIIIIIIIIlIllIIllIlIIlIl + n4 / 2;
        int n6 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n7 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        int n8 = n4 / 8;
        n6 = IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, n6, n6 + n8, n6 + 2 * n8);
        n5 = IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, n5 - n8, n5, n5 + n8);
        n7 = IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, n7 - 2 * n8, n7 - n8, n7);
        n5 = IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, n6, n5, n7);
        int n9 = nArray[n5];
        int n10 = n3 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n11 = n2 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        while (true) {
            if (n10 <= n2 && (n = Integer.compare(nArray[n10], n9)) <= 0) {
                if (n == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n3++, n10);
                }
                ++n10;
                continue;
            }
            while (n2 >= n10 && (n = Integer.compare(nArray[n2], n9)) >= 0) {
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
            IIlIIIlIIIIIlIllIlIIlllII.invokeAll(new IIlIIIlIIIIIlIllIlIIlllII(nArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8), new IIlIIIlIIIIIlIllIlIIlllII(nArray, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI));
        } else if (n8 > 1) {
            IIlIIIlIIIIIlIllIlIIlllII.invokeAll(new IIlIIIlIIIIIlIllIlIIlllII(nArray, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8));
        } else {
            IIlIIIlIIIIIlIllIlIIlllII.invokeAll(new IIlIIIlIIIIIlIllIlIIlllII(nArray, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI));
        }
    }
}
