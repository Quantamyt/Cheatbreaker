package com.cheatbreaker.client.util.font;

import java.util.concurrent.RecursiveAction;

public class IIIllIIlIIlIIIIIlIllIIIIl
        extends RecursiveAction {
    private static final long lIIIIlIIllIIlIIlIIIlIIllI = 1L;
    private final int lIIIIIIIIIlIllIIllIlIIlIl;
    private final int IlllIIIlIlllIllIlIIlllIlI;
    private final int[] IIIIllIlIIIllIlllIlllllIl;
    private final int[] IIIIllIIllIIIIllIllIIIlIl;

    public IIIllIIlIIlIIIIIlIllIIIIl(int[] nArray, int[] nArray2, int n, int n2) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
        this.IIIIllIlIIIllIlllIlllllIl = nArray;
        this.IIIIllIIllIIIIllIllIIIlIl = nArray2;
    }

    @Override
    protected void compute() {
        int n;
        int n2;
        int n3;
        int[] nArray = this.IIIIllIlIIIllIlllIlllllIl;
        int[] nArray2 = this.IIIIllIIllIIIIllIllIIIlIl;
        int n4 = this.IlllIIIlIlllIllIlIIlllIlI - this.lIIIIIIIIIlIllIIllIlIIlIl;
        if (n4 < 8192) {
            IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, this.lIIIIIIIIIlIllIIllIlIIlIl, this.IlllIIIlIlllIllIlIIlllIlI);
            return;
        }
        int n5 = this.lIIIIIIIIIlIllIIllIlIIlIl + n4 / 2;
        int n6 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n7 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        int n8 = n4 / 8;
        n6 = IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, nArray2, n6, n6 + n8, n6 + 2 * n8);
        n5 = IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, nArray2, n5 - n8, n5, n5 + n8);
        n7 = IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, nArray2, n7 - 2 * n8, n7 - n8, n7);
        n5 = IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, nArray2, n6, n5, n7);
        int n9 = nArray[n5];
        int n10 = nArray2[n5];
        int n11 = n3 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n12 = n2 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        while (true) {
            int n13;
            if (n11 <= n2 && (n = (n13 = Integer.compare(nArray[n11], n9)) == 0 ? Integer.compare(nArray2[n11], n10) : n13) <= 0) {
                if (n == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IllIIIIIIIlIlIllllIIllIII(nArray, nArray2, n3++, n11);
                }
                ++n11;
                continue;
            }
            while (n2 >= n11 && (n = (n13 = Integer.compare(nArray[n2], n9)) == 0 ? Integer.compare(nArray2[n2], n10) : n13) >= 0) {
                if (n == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IllIIIIIIIlIlIllllIIllIII(nArray, nArray2, n2, n12--);
                }
                --n2;
            }
            if (n11 > n2) break;
            IlllIlIllIlllIlIIlIlllIll.IllIIIIIIIlIlIllllIIllIII(nArray, nArray2, n11++, n2--);
        }
        n8 = Math.min(n3 - this.lIIIIIIIIIlIllIIllIlIIlIl, n11 - n3);
        IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, nArray2, this.lIIIIIIIIIlIllIIllIlIIlIl, n11 - n8, n8);
        n8 = Math.min(n12 - n2, this.IlllIIIlIlllIllIlIIlllIlI - n12 - 1);
        IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, nArray2, n11, this.IlllIIIlIlllIllIlIIlllIlI - n8, n8);
        n8 = n11 - n3;
        n = n12 - n2;
        if (n8 > 1 && n > 1) {
            IIIllIIlIIlIIIIIlIllIIIIl.invokeAll(new IIIllIIlIIlIIIIIlIllIIIIl(nArray, nArray2, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8), new IIIllIIlIIlIIIIIlIllIIIIl(nArray, nArray2, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI));
        } else if (n8 > 1) {
            IIIllIIlIIlIIIIIlIllIIIIl.invokeAll(new IIIllIIlIIlIIIIIlIllIIIIl(nArray, nArray2, this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8));
        } else {
            IIIllIIlIIlIIIIIlIllIIIIl.invokeAll(new IIIllIIlIIlIIIIIlIllIIIIl(nArray, nArray2, this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI));
        }
    }
}
