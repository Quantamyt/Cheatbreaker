package com.cheatbreaker.client.util.font;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

final class lIlllIIllIIIlIIlIIIIIIlIl
        implements Callable {
    final AtomicInteger lIIIIlIIllIIlIIlIIIlIIllI;
    final int lIIIIIIIIIlIllIIllIlIIlIl;
    final LinkedBlockingQueue IlllIIIlIlllIllIlIIlllIlI;
    final int[] IIIIllIlIIIllIlllIlllllIl;
    final int[] IIIIllIIllIIIIllIllIIIlIl;
    final boolean IlIlIIIlllIIIlIlllIlIllIl;
    final int[] IIIllIllIlIlllllllIlIlIII;

    lIlllIIllIIIlIIlIIIIIIlIl(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, int[] nArray, int[] nArray2, boolean bl, int[] nArray3) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = atomicInteger;
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = linkedBlockingQueue;
        this.IIIIllIlIIIllIlllIlllllIl = nArray;
        this.IIIIllIIllIIIIllIllIIIlIl = nArray2;
        this.IlIlIIIlllIIIlIlllIlIllIl = bl;
        this.IIIllIllIlIlllllllIlIlIII = nArray3;
    }

    public Void lIIIIlIIllIIlIIlIIIlIIllI() throws InterruptedException {
        int[] nArray = new int[256];
        int[] nArray2 = new int[256];
        while (true) {
            int n;
            lIIllIlIIlIIIIIlIIlIlIIll lIIllIlIIlIIIIIlIIlIlIIll2;
            if (this.lIIIIlIIllIIlIIlIIIlIIllI.get() == 0) {
                int n2 = this.lIIIIIIIIIlIllIIllIlIIlIl;
                while (n2-- != 0) {
                    this.IlllIIIlIlllIllIlIIlllIlI.add(IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl);
                }
            }
            if ((lIIllIlIIlIIIIIlIIlIlIIll2 = (lIIllIlIIlIIIIIlIIlIlIIll)this.IlllIIIlIlllIllIlIIlllIlI.take()) == IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl) {
                return null;
            }
            int n3 = lIIllIlIIlIIIIIlIIlIlIIll2.lIIIIlIIllIIlIIlIIIlIIllI;
            int n4 = lIIllIlIIlIIIIIlIIlIlIIll2.lIIIIIIIIIlIllIIllIlIIlIl;
            int n5 = lIIllIlIIlIIIIIlIIlIlIIll2.IlllIIIlIlllIllIlIIlllIlI;
            int n6 = n5 % 4 == 0 ? 128 : 0;
            int n7 = (3 - n5 % 4) * 8;
            int n8 = n3 + n4;
            while (n8-- != n3) {
                int n9 = this.IIIIllIlIIIllIlllIlllllIl[this.IIIIllIIllIIIIllIllIIIlIl[n8]] >>> n7 & 0xFF ^ n6;
                nArray[n9] = nArray[n9] + 1;
            }
            n8 = -1;
            int n10 = n3;
            for (n = 0; n < 256; ++n) {
                if (nArray[n] != 0) {
                    n8 = n;
                }
                nArray2[n] = n10 += nArray[n];
            }
            if (this.IlIlIIIlllIIIlIlllIlIllIl) {
                n = n3 + n4;
                while (n-- != n3) {
                    int n11 = this.IIIIllIlIIIllIlllIlllllIl[this.IIIIllIIllIIIIllIllIIIlIl[n]] >>> n7 & 0xFF ^ n6;
                    int n12 = nArray2[n11] - 1;
                    nArray2[n11] = n12;
                    this.IIIllIllIlIlllllllIlIlIII[n12] = this.IIIIllIIllIIIIllIllIIIlIl[n];
                }
                System.arraycopy(this.IIIllIllIlIlllllllIlIlIII, n3, this.IIIIllIIllIIIIllIllIIIlIl, n3, n4);
                n10 = n3;
                for (n = 0; n <= n8; ++n) {
                    if (n5 < 3 && nArray[n] > 1) {
                        if (nArray[n] < 1024) {
                            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIIllIIllIIIIllIllIIIlIl, this.IIIIllIlIIIllIlllIlllllIl, n10, n10 + nArray[n], this.IlIlIIIlllIIIlIlllIlIllIl);
                        } else {
                            this.lIIIIlIIllIIlIIlIIIlIIllI.incrementAndGet();
                            this.IlllIIIlIlllIllIlIIlllIlI.add(new lIIllIlIIlIIIIIlIIlIlIIll(n10, nArray[n], n5 + 1));
                        }
                    }
                    n10 += nArray[n];
                }
                Arrays.fill(nArray, 0);
            } else {
                n = n3 + n4 - nArray[n8];
                int n13 = -1;
                for (n10 = n3; n10 <= n; n10 += nArray[n13]) {
                    int n14 = this.IIIIllIIllIIIIllIllIIIlIl[n10];
                    n13 = this.IIIIllIlIIIllIlllIlllllIl[n14] >>> n7 & 0xFF ^ n6;
                    if (n10 < n) {
                        while (true) {
                            int n15 = n13;
                            int n16 = nArray2[n15] - 1;
                            nArray2[n15] = n16;
                            int n17 = n16;
                            if (n16 <= n10) break;
                            int n18 = n14;
                            n14 = this.IIIIllIIllIIIIllIllIIIlIl[n17];
                            this.IIIIllIIllIIIIllIllIIIlIl[n17] = n18;
                            n13 = this.IIIIllIlIIIllIlllIlllllIl[n14] >>> n7 & 0xFF ^ n6;
                        }
                        this.IIIIllIIllIIIIllIllIIIlIl[n10] = n14;
                    }
                    if (n5 < 3 && nArray[n13] > 1) {
                        if (nArray[n13] < 1024) {
                            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(this.IIIIllIIllIIIIllIllIIIlIl, this.IIIIllIlIIIllIlllIlllllIl, n10, n10 + nArray[n13], this.IlIlIIIlllIIIlIlllIlIllIl);
                        } else {
                            this.lIIIIlIIllIIlIIlIIIlIIllI.incrementAndGet();
                            this.IlllIIIlIlllIllIlIIlllIlI.add(new lIIllIlIIlIIIIIlIIlIlIIll(n10, nArray[n13], n5 + 1));
                        }
                    }
                    nArray[n13] = 0;
                }
            }
            this.lIIIIlIIllIIlIIlIIIlIIllI.decrementAndGet();
        }
    }

    public Object call() throws InterruptedException {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }
}
