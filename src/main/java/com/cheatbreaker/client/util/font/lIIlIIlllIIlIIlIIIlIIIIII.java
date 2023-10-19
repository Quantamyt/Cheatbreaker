package com.cheatbreaker.client.util.font;

// Decompiled with: CFR 0.152
// Class Version: 8
import java.util.concurrent.ForkJoinPool;

public class lIIlIIlllIIlIIlIIIlIIIIII {
    public static final int lIIIIlIIllIIlIIlIIIlIIllI = 0x7FFFFFF7;
    private static final int lIIIIIIIIIlIllIIllIlIIlIl = 16;
    private static final int IlllIIIlIlllIllIlIIlllIlI = 16;
    private static final int IIIIllIlIIIllIlllIlllllIl = 8192;
    private static final int IIIIllIIllIIIIllIllIIIlIl = 128;

    private lIIlIIlllIIlIIlIIIlIIIIII() {
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int n, int n2, int n3) {
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Start index (" + n2 + ") is negative");
        }
        if (n2 > n3) {
            throw new IllegalArgumentException("Start index (" + n2 + ") is greater than end index (" + n3 + ")");
        }
        if (n3 > n) {
            throw new ArrayIndexOutOfBoundsException("End index (" + n3 + ") is greater than array length (" + n + ")");
        }
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int n, int n2, int n3) {
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n3 < 0) {
            throw new IllegalArgumentException("Length (" + n3 + ") is negative");
        }
        if (n2 + n3 > n) {
            throw new ArrayIndexOutOfBoundsException("Last index (" + (n2 + n3) + ") is greater than array length (" + n + ")");
        }
    }

    private static void lIIIIlIIllIIlIIlIIIlIIllI(int n, int n2, int n3, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2, IIllIIIIlllllIllIIllIllIl iIllIIIIlllllIllIIllIllIl) {
        int n4;
        int n5;
        if (n >= n2 || n2 >= n3) {
            return;
        }
        if (n3 - n == 2) {
            if (lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n2, n) < 0) {
                iIllIIIIlllllIllIIllIllIl.lIIIIlIIllIIlIIlIIIlIIllI(n, n2);
            }
            return;
        }
        if (n2 - n > n3 - n2) {
            n5 = n + (n2 - n) / 2;
            n4 = lIIlIIlllIIlIIlIIIlIIIIII.lIIIIIIIIIlIllIIllIlIIlIl(n2, n3, n5, lIlIIIIlIlIlllIlIIllIlIll2);
        } else {
            n4 = n2 + (n3 - n2) / 2;
            n5 = lIIlIIlllIIlIIlIIIlIIIIII.IlllIIIlIlllIllIlIIlllIlI(n, n2, n4, lIlIIIIlIlIlllIlIIllIlIll2);
        }
        int n6 = n5;
        int n7 = n2;
        int n8 = n4;
        if (n7 != n6 && n7 != n8) {
            int n9 = n6;
            int n10 = n7;
            while (n9 < --n10) {
                iIllIIIIlllllIllIIllIllIl.lIIIIlIIllIIlIIlIIIlIIllI(n9++, n10);
            }
            n9 = n7;
            n10 = n8;
            while (n9 < --n10) {
                iIllIIIIlllllIllIIllIllIl.lIIIIlIIllIIlIIlIIIlIIllI(n9++, n10);
            }
            n9 = n6;
            n10 = n8;
            while (n9 < --n10) {
                iIllIIIIlllllIllIIllIllIl.lIIIIlIIllIIlIIlIIIlIIllI(n9++, n10);
            }
        }
        n2 = n5 + (n4 - n2);
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(n, n5, n2, lIlIIIIlIlIlllIlIIllIlIll2, iIllIIIIlllllIllIIllIllIl);
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(n2, n4, n3, lIlIIIIlIlIlllIlIIllIlIll2, iIllIIIIlllllIllIIllIllIl);
    }

    private static int lIIIIIIIIIlIllIIllIlIIlIl(int n, int n2, int n3, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        int n4 = n2 - n;
        while (n4 > 0) {
            int n5 = n4 / 2;
            int n6 = n + n5;
            if (lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n6, n3) < 0) {
                n = n6 + 1;
                n4 -= n5 + 1;
                continue;
            }
            n4 = n5;
        }
        return n;
    }

    private static int IlllIIIlIlllIllIlIIlllIlI(int n, int n2, int n3, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        int n4 = n2 - n;
        while (n4 > 0) {
            int n5 = n4 / 2;
            int n6 = n + n5;
            if (lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n3, n6) < 0) {
                n4 = n5;
                continue;
            }
            n = n6 + 1;
            n4 -= n5 + 1;
        }
        return n;
    }

    private static int IIIIllIlIIIllIlllIlllllIl(int n, int n2, int n3, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        int n4 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n, n2);
        int n5 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n, n3);
        int n6 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n2, n3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2, IIllIIIIlllllIllIIllIllIl iIllIIIIlllllIllIIllIllIl) {
        int n3 = n2 - n;
        if (n3 < 16) {
            for (int i = n; i < n2; ++i) {
                for (int j = i; j > n && lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(j - 1, j) > 0; --j) {
                    iIllIIIIlllllIllIIllIllIl.lIIIIlIIllIIlIIlIIIlIIllI(j, j - 1);
                }
            }
            return;
        }
        int n4 = n + n2 >>> 1;
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(n, n4, lIlIIIIlIlIlllIlIIllIlIll2, iIllIIIIlllllIllIIllIllIl);
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(n4, n2, lIlIIIIlIlIlllIlIIllIlIll2, iIllIIIIlllllIllIIllIllIl);
        if (lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n4 - 1, n4) <= 0) {
            return;
        }
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(n, n4, n2, lIlIIIIlIlIlllIlIIllIlIll2, iIllIIIIlllllIllIIllIllIl);
    }

    protected static void lIIIIlIIllIIlIIlIIIlIIllI(IIllIIIIlllllIllIIllIllIl iIllIIIIlllllIllIIllIllIl, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            iIllIIIIlllllIllIIllIllIl.lIIIIlIIllIIlIIlIIIlIIllI(n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2, IIllIIIIlllllIllIIllIllIl iIllIIIIlllllIllIIllIllIl) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new llllIllIlllIIIlIIllIIIlII(n, n2, lIlIIIIlIlIlllIlIIllIlIll2, iIllIIIIlllllIllIIllIllIl));
        forkJoinPool.shutdown();
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2, IIllIIIIlllllIllIIllIllIl iIllIIIIlllllIllIIllIllIl) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            for (int i = n; i < n2; ++i) {
                for (int j = i; j > n && lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(j - 1, j) > 0; --j) {
                    iIllIIIIlllllIllIIllIllIl.lIIIIlIIllIIlIIlIIIlIIllI(j, j - 1);
                }
            }
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            n5 = n6 / 8;
            n8 = lIIlIIlllIIlIIlIIIlIIIIII.IIIIllIlIIIllIlllIlllllIl(n8, n8 + n5, n8 + 2 * n5, lIlIIIIlIlIlllIlIIllIlIll2);
            n7 = lIIlIIlllIIlIIlIIIlIIIIII.IIIIllIlIIIllIlllIlllllIl(n7 - n5, n7, n7 + n5, lIlIIIIlIlIlllIlIIllIlIll2);
            n9 = lIIlIIlllIIlIIlIIIlIIIIII.IIIIllIlIIIllIlllIlllllIl(n9 - 2 * n5, n9 - n5, n9, lIlIIIIlIlIlllIlIIllIlIll2);
        }
        n7 = lIIlIIlllIIlIIlIIIlIIIIII.IIIIllIlIIIllIlllIlllllIl(n8, n7, n9, lIlIIIIlIlIlllIlIIllIlIll2);
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n10, n7)) <= 0) {
                if (n3 == 0) {
                    if (n5 == n7) {
                        n7 = n10;
                    } else if (n10 == n7) {
                        n7 = n5;
                    }
                    iIllIIIIlllllIllIIllIllIl.lIIIIlIIllIIlIIlIIIlIIllI(n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n4, n7)) >= 0) {
                if (n3 == 0) {
                    if (n4 == n7) {
                        n7 = n11;
                    } else if (n11 == n7) {
                        n7 = n4;
                    }
                    iIllIIIIlllllIllIIllIllIl.lIIIIlIIllIIlIIlIIIlIIllI(n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            if (n10 == n7) {
                n7 = n11;
            } else if (n4 == n7) {
                n7 = n4;
            }
            iIllIIIIlllllIllIIllIllIl.lIIIIlIIllIIlIIlIIIlIIllI(n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(iIllIIIIlllllIllIIllIllIl, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(iIllIIIIlllllIllIIllIllIl, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            lIIlIIlllIIlIIlIIIlIIIIII.IlllIIIlIlllIllIlIIlllIlI(n, n + n3, lIlIIIIlIlIlllIlIIllIlIll2, iIllIIIIlllllIllIIllIllIl);
        }
        if ((n3 = n11 - n4) > 1) {
            lIIlIIlllIIlIIlIIIlIIIIII.IlllIIIlIlllIllIlIIlllIlI(n2 - n3, n2, lIlIIIIlIlIlllIlIIllIlIll2, iIllIIIIlllllIllIIllIllIl);
        }
    }

    static int lIIIIlIIllIIlIIlIIIlIIllI(int n, int n2, int n3, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        return lIIlIIlllIIlIIlIIIlIIIIII.IIIIllIlIIIllIlllIlllllIl(n, n2, n3, lIlIIIIlIlIlllIlIIllIlIll2);
    }
}

