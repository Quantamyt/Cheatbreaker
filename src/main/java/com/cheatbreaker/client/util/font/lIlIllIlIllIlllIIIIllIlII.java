package com.cheatbreaker.client.util.font;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

final class lIlIllIlIllIlllIIIIllIlII
        implements Callable {
    final AtomicInteger lIIIIlIIllIIlIIlIIIlIIllI;
    final int lIIIIIIIIIlIllIIllIlIIlIl;
    final LinkedBlockingQueue IlllIIIlIlllIllIlIIlllIlI;
    final int[] IIIIllIlIIIllIlllIlllllIl;
    final int[] IIIIllIIllIIIIllIllIIIlIl;

    lIlIllIlIllIlllIIIIllIlII(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, int[] nArray, int[] nArray2) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = atomicInteger;
        this.lIIIIIIIIIlIllIIllIlIIlIl = n;
        this.IlllIIIlIlllIllIlIIlllIlI = linkedBlockingQueue;
        this.IIIIllIlIIIllIlllIlllllIl = nArray;
        this.IIIIllIIllIIIIllIllIIIlIl = nArray2;
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
            int[] nArray3 = n5 < 4 ? this.IIIIllIlIIIllIlllIlllllIl : this.IIIIllIIllIIIIllIllIIIlIl;
            int n7 = (3 - n5 % 4) * 8;
            int n8 = n3 + n4;
            while (n8-- != n3) {
                int n9 = nArray3[n8] >>> n7 & 0xFF ^ n6;
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
            n = n3 + n4 - nArray[n8];
            int n11 = -1;
            for (n10 = n3; n10 <= n; n10 += nArray[n11]) {
                int n12 = this.IIIIllIlIIIllIlllIlllllIl[n10];
                int n13 = this.IIIIllIIllIIIIllIllIIIlIl[n10];
                n11 = nArray3[n10] >>> n7 & 0xFF ^ n6;
                if (n10 < n) {
                    while (true) {
                        int n14 = n11;
                        int n15 = nArray2[n14] - 1;
                        nArray2[n14] = n15;
                        int n16 = n15;
                        if (n15 <= n10) break;
                        n11 = nArray3[n16] >>> n7 & 0xFF ^ n6;
                        int n17 = n12;
                        int n18 = n13;
                        n12 = this.IIIIllIlIIIllIlllIlllllIl[n16];
                        n13 = this.IIIIllIIllIIIIllIllIIIlIl[n16];
                        this.IIIIllIlIIIllIlllIlllllIl[n16] = n17;
                        this.IIIIllIIllIIIIllIllIIIlIl[n16] = n18;
                    }
                    this.IIIIllIlIIIllIlllIlllllIl[n10] = n12;
                    this.IIIIllIIllIIIIllIllIIIlIl[n10] = n13;
                }
                if (n5 < 7 && nArray[n11] > 1) {
                    if (nArray[n11] < 1024) {
                        IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(this.IIIIllIlIIIllIlllIlllllIl, this.IIIIllIIllIIIIllIllIIIlIl, n10, n10 + nArray[n11]);
                    } else {
                        this.lIIIIlIIllIIlIIlIIIlIIllI.incrementAndGet();
                        this.IlllIIIlIlllIllIlIIlllIlI.add(new lIIllIlIIlIIIIIlIIlIlIIll(n10, nArray[n11], n5 + 1));
                    }
                }
                nArray[n11] = 0;
            }
            this.lIIIIlIIllIIlIIlIIIlIIllI.decrementAndGet();
        }
    }

    public Object call() throws InterruptedException {
        return this.lIIIIlIIllIIlIIlIIIlIIllI();
    }
}
