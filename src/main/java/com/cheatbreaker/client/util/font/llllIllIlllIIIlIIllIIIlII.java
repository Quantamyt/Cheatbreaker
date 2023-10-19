package com.cheatbreaker.client.util.font;

import java.util.concurrent.RecursiveAction;

public class llllIllIlllIIIlIIllIIIlII
        extends RecursiveAction {
    private static final long lIIIIlIIllIIlIIlIIIlIIllI = 1L;
    private final int lIIIIIIIIIlIllIIllIlIIlIl;
    private final int IlllIIIlIlllIllIlIIlllIlI;
    private final lIlIIIIlIlIlllIlIIllIlIll IIIIllIlIIIllIlllIlllllIl;
    private final IIllIIIIlllllIllIIllIllIl IIIIllIIllIIIIllIllIIIlIl;

    public llllIllIlllIIIlIIllIIIlII(int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2, IIllIIIIlllllIllIIllIllIl iIllIIIIlllllIllIIllIllIl) {
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = n2;
        this.IIIIllIlIIIllIlllIlllllIl = lIlIIIIlIlIlllIlIIllIlIll2;
        this.IIIIllIIllIIIIllIllIIIlIl = iIllIIIIlllllIllIIllIllIl;
    }

    @Override
    protected void compute() {
        int n;
        int n2;
        int n3;
        int n4 = this.IlllIIIlIlllIllIlIIlllIlI - this.lIIIIIIIIIlIllIIllIlIIlIl;
        if (n4 < 8192) {
            lIIlIIlllIIlIIlIIIlIIIIII.IlllIIIlIlllIllIlIIlllIlI(this.lIIIIIIIIIlIllIIllIlIIlIl, this.IlllIIIlIlllIllIlIIlllIlI, this.IIIIllIlIIIllIlllIlllllIl, this.IIIIllIIllIIIIllIllIIIlIl);
            return;
        }
        int n5 = this.lIIIIIIIIIlIllIIllIlIIlIl + n4 / 2;
        int n6 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n7 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        int n8 = n4 / 8;
        n6 = lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(n6, n6 + n8, n6 + 2 * n8, this.IIIIllIlIIIllIlllIlllllIl);
        n5 = lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(n5 - n8, n5, n5 + n8, this.IIIIllIlIIIllIlllIlllllIl);
        n7 = lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(n7 - 2 * n8, n7 - n8, n7, this.IIIIllIlIIIllIlllIlllllIl);
        n5 = lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(n6, n5, n7, this.IIIIllIlIIIllIlllIlllllIl);
        int n9 = n3 = this.lIIIIIIIIIlIllIIllIlIIlIl;
        int n10 = n2 = this.IlllIIIlIlllIllIlIIlllIlI - 1;
        while (true) {
            if (n9 <= n2 && (n = this.IIIIllIlIIIllIlllIlllllIl.lIIIIlIIllIIlIIlIIIlIIllI(n9, n5)) <= 0) {
                if (n == 0) {
                    if (n3 == n5) {
                        n5 = n9;
                    } else if (n9 == n5) {
                        n5 = n3;
                    }
                    this.IIIIllIIllIIIIllIllIIIlIl.lIIIIlIIllIIlIIlIIIlIIllI(n3++, n9);
                }
                ++n9;
                continue;
            }
            while (n2 >= n9 && (n = this.IIIIllIlIIIllIlllIlllllIl.lIIIIlIIllIIlIIlIIIlIIllI(n2, n5)) >= 0) {
                if (n == 0) {
                    if (n2 == n5) {
                        n5 = n10;
                    } else if (n10 == n5) {
                        n5 = n2;
                    }
                    this.IIIIllIIllIIIIllIllIIIlIl.lIIIIlIIllIIlIIlIIIlIIllI(n2, n10--);
                }
                --n2;
            }
            if (n9 > n2) break;
            if (n9 == n5) {
                n5 = n10;
            } else if (n2 == n5) {
                n5 = n2;
            }
            this.IIIIllIIllIIIIllIllIIIlIl.lIIIIlIIllIIlIIlIIIlIIllI(n9++, n2--);
        }
        n8 = Math.min(n3 - this.lIIIIIIIIIlIllIIllIlIIlIl, n9 - n3);
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIIllIIllIIIIllIllIIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl, n9 - n8, n8);
        n8 = Math.min(n10 - n2, this.IlllIIIlIlllIllIlIIlllIlI - n10 - 1);
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIIllIIllIIIIllIllIIIlIl, n9, this.IlllIIIlIlllIllIlIIlllIlI - n8, n8);
        n8 = n9 - n3;
        n = n10 - n2;
        if (n8 > 1 && n > 1) {
            llllIllIlllIIIlIIllIIIlII.invokeAll(new llllIllIlllIIIlIIllIIIlII(this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8, this.IIIIllIlIIIllIlllIlllllIl, this.IIIIllIIllIIIIllIllIIIlIl), new llllIllIlllIIIlIIllIIIlII(this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI, this.IIIIllIlIIIllIlllIlllllIl, this.IIIIllIIllIIIIllIllIIIlIl));
        } else if (n8 > 1) {
            llllIllIlllIIIlIIllIIIlII.invokeAll(new llllIllIlllIIIlIIllIIIlII(this.lIIIIIIIIIlIllIIllIlIIlIl, this.lIIIIIIIIIlIllIIllIlIIlIl + n8, this.IIIIllIlIIIllIlllIlllllIl, this.IIIIllIIllIIIIllIllIIIlIl));
        } else {
            llllIllIlllIIIlIIllIIIlII.invokeAll(new llllIllIlllIIIlIIllIIIlII(this.IlllIIIlIlllIllIlIIlllIlI - n, this.IlllIIIlIlllIllIlIIlllIlI, this.IIIIllIlIIIllIlllIlllllIl, this.IIIIllIIllIIIIllIllIIIlIl));
        }
    }
}
