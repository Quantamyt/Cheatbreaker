package com.cheatbreaker.client.util.font;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class IlllIlIllIlllIlIIlIlllIll {
    public static final int[] lIIIIlIIllIIlIIlIIIlIIllI = new int[0];
    private static final int IIIIllIlIIIllIlllIlllllIl = 16;
    private static final int IIIIllIIllIIIIllIllIIIlIl = 8192;
    private static final int IlIlIIIlllIIIlIlllIlIllIl = 128;
    private static final int IIIllIllIlIlllllllIlIlIII = 16;
    private static final int IllIIIIIIIlIlIllllIIllIII = 8;
    private static final int lIIIIllIIlIlIllIIIlIllIlI = 255;
    private static final int IlllIllIlIIIIlIIlIIllIIIl = 4;
    private static final int IlIlllIIIIllIllllIllIIlIl = 1024;
    private static final int llIIlllIIIIlllIllIlIlllIl = 1024;
    protected static final lIIllIlIIlIIIIIlIIlIlIIll lIIIIIIIIIlIllIIllIlIIlIl = new lIIllIlIIlIIIIIlIIlIlIIll(-1, -1, -1);
    public static final lllIIIIIIllIlllIlIIlIlIll IlllIIIlIlllIllIlIIlllIlI = new IllIlIIIIIlIlllllIllIllII(null);

    private IlllIlIllIlllIlIIlIlllIll() {
    }

    public static int[] lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int n) {
        if (n > nArray.length) {
            int[] nArray2 = new int[n];
            System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
            return nArray2;
        }
        return nArray;
    }

    public static int[] lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int n, int n2) {
        if (n > nArray.length) {
            int[] nArray2 = new int[n];
            System.arraycopy(nArray, 0, nArray2, 0, n2);
            return nArray2;
        }
        return nArray;
    }

    public static int[] lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, int n) {
        if (n > nArray.length) {
            int n2 = (int)Math.max(Math.min(2L * (long)nArray.length, 0x7FFFFFF7L), (long)n);
            int[] nArray2 = new int[n2];
            System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
            return nArray2;
        }
        return nArray;
    }

    public static int[] lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, int n, int n2) {
        if (n > nArray.length) {
            int n3 = (int)Math.max(Math.min(2L * (long)nArray.length, 0x7FFFFFF7L), (long)n);
            int[] nArray2 = new int[n3];
            System.arraycopy(nArray, 0, nArray2, 0, n2);
            return nArray2;
        }
        return nArray;
    }

    public static int[] IlllIIIlIlllIllIlIIlllIlI(int[] nArray, int n) {
        if (n >= nArray.length) {
            return nArray;
        }
        int[] nArray2 = n == 0 ? lIIIIlIIllIIlIIlIIIlIIllI : new int[n];
        System.arraycopy(nArray, 0, nArray2, 0, n);
        return nArray2;
    }

    public static int[] IIIIllIlIIIllIlllIlllllIl(int[] nArray, int n) {
        if (n == nArray.length) {
            return nArray;
        }
        if (n < nArray.length) {
            return IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, n);
        }
        return IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, n);
    }

    public static int[] IlllIIIlIlllIllIlIIlllIlI(int[] nArray, int n, int n2) {
        IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, n, n2);
        int[] nArray2 = n2 == 0 ? lIIIIlIIllIIlIIlIIIlIIllI : new int[n2];
        System.arraycopy(nArray, n, nArray2, 0, n2);
        return nArray2;
    }

    public static int[] lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray) {
        return (int[])nArray.clone();
    }

    @Deprecated
    public static void IIIIllIIllIIIIllIllIIIlIl(int[] nArray, int n) {
        int n2 = nArray.length;
        while (n2-- != 0) {
            nArray[n2] = n;
        }
    }

    @Deprecated
    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int n, int n2, int n3) {
        IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                nArray[n2] = n3;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                nArray[i] = n3;
            }
        }
    }

    @Deprecated
    public static boolean lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int[] nArray2) {
        int n = nArray.length;
        if (n != nArray2.length) {
            return false;
        }
        while (n-- != 0) {
            if (nArray[n] == nArray2[n]) continue;
            return false;
        }
        return true;
    }

    public static void IIIIllIlIIIllIlllIlllllIl(int[] nArray, int n, int n2) {
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(nArray.length, n, n2);
    }

    public static void IIIIllIIllIIIIllIllIIIlIl(int[] nArray, int n, int n2) {
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIIIIIIlIllIIllIlIIlIl(nArray.length, n, n2);
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, int[] nArray2) {
        if (nArray.length != nArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + nArray.length + " != " + nArray2.length);
        }
    }

    public static void IlIlIIIlllIIIlIlllIlIllIl(int[] nArray, int n, int n2) {
        int n3 = nArray[n];
        nArray[n] = nArray[n2];
        nArray[n2] = n3;
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static int IlllIIIlIlllIllIlIIlllIlI(int[] nArray, int n, int n2, int n3, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        int n4 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(nArray[n], nArray[n2]);
        int n5 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(nArray[n], nArray[n3]);
        int n6 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(nArray[n2], nArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void IIIIllIlIIIllIlllIlllllIl(int[] nArray, int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(nArray[n3], nArray[n4]) >= 0) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = nArray[i];
            nArray[i] = nArray[n4];
            nArray[n4] = n3;
        }
    }

    private static void IIIIllIIllIIIIllIllIIIlIl(int[] nArray, int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n4, n6) < 0) {
                nArray[n5] = n6;
                if (n == n5 - 1) {
                    --n5;
                    break;
                }
                n6 = nArray[--n5 - 1];
            }
            nArray[n5] = n4;
        }
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, n, n2, lIlIIIIlIlIlllIlIIllIlIll2);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, n9, n9 + n6, n9 + 2 * n6, lIlIIIIlIlIlllIlIIllIlIll2);
            n8 = IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, n8 - n6, n8, n8 + n6, lIlIIIIlIlIlllIlIIllIlIll2);
            n10 = IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, n10 - 2 * n6, n10 - n6, n10, lIlIIIIlIlIlllIlIIllIlIll2);
        }
        n8 = IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, n9, n8, n10, lIlIIIIlIlIlllIlIIllIlIll2);
        n6 = nArray[n8];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(nArray[n11], n6)) <= 0) {
                if (n3 == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(nArray[n4], n6)) >= 0) {
                if (n3 == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, n, n + n3, lIlIIIIlIlIlllIlIIllIlIll2);
        }
        if ((n3 = n12 - n4) > 1) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, n2 - n3, n2, lIlIIIIlIlIlllIlIIllIlIll2);
        }
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, 0, nArray.length, lIlIIIIlIlIlllIlIIllIlIll2);
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        if (n2 - n < 8192) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, n, n2, lIlIIIIlIlIlllIlIIllIlIll2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new IIllIlllIIlllllIllIIIllII(nArray, n, n2, lIlIIIIlIlIlllIlIIllIlIll2));
            forkJoinPool.shutdown();
        }
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, 0, nArray.length, lIlIIIIlIlIlllIlIIllIlIll2);
    }

    private static int IIIIllIIllIIIIllIllIIIlIl(int[] nArray, int n, int n2, int n3) {
        int n4 = Integer.compare(nArray[n], nArray[n2]);
        int n5 = Integer.compare(nArray[n], nArray[n3]);
        int n6 = Integer.compare(nArray[n2], nArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void lIIlIlIllIIlIIIlIIIlllIII(int[] nArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (nArray[n3] >= nArray[n4]) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = nArray[i];
            nArray[i] = nArray[n4];
            nArray[n4] = n3;
        }
    }

    private static void IIIlllIIIllIllIlIIIIIIlII(int[] nArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (n4 < n6) {
                nArray[n5] = n6;
                if (n == n5 - 1) {
                    --n5;
                    break;
                }
                n6 = nArray[--n5 - 1];
            }
            nArray[n5] = n4;
        }
    }

    public static void IIIllIllIlIlllllllIlIlIII(int[] nArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            IlllIlIllIlllIlIIlIlllIll.lIIlIlIllIIlIIIlIIIlllIII(nArray, n, n2);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, n9, n9 + n6, n9 + 2 * n6);
            n8 = IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, n8 - n6, n8, n8 + n6);
            n10 = IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, n10 - 2 * n6, n10 - n6, n10);
        }
        n8 = IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, n9, n8, n10);
        n6 = nArray[n8];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Integer.compare(nArray[n11], n6)) <= 0) {
                if (n3 == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Integer.compare(nArray[n4], n6)) >= 0) {
                if (n3 == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            IlllIlIllIlllIlIIlIlllIll.IIIllIllIlIlllllllIlIlIII(nArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            IlllIlIllIlllIlIIlIlllIll.IIIllIllIlIlllllllIlIlIII(nArray, n2 - n3, n2);
        }
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray) {
        IlllIlIllIlllIlIIlIlllIll.IIIllIllIlIlllllllIlIlIII(nArray, 0, nArray.length);
    }

    public static void IllIIIIIIIlIlIllllIIllIII(int[] nArray, int n, int n2) {
        if (n2 - n < 8192) {
            IlllIlIllIlllIlIIlIlllIll.IIIllIllIlIlllllllIlIlIII(nArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new IIlIIIlIIIIIlIllIlIIlllII(nArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(int[] nArray) {
        IlllIlIllIlllIlIIlIlllIll.IllIIIIIIIlIlIllllIIllIII(nArray, 0, nArray.length);
    }

    private static int IIIIllIlIIIllIlllIlllllIl(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        int n4 = nArray2[nArray[n]];
        int n5 = nArray2[nArray[n2]];
        int n6 = nArray2[nArray[n3]];
        int n7 = Integer.compare(n4, n5);
        int n8 = Integer.compare(n4, n6);
        int n9 = Integer.compare(n5, n6);
        return n7 < 0 ? (n9 < 0 ? n2 : (n8 < 0 ? n3 : n)) : (n9 > 0 ? n2 : (n8 > 0 ? n3 : n));
    }

    private static void lIIIIllIIlIlIllIIIlIllIlI(int[] nArray, int[] nArray2, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (nArray2[n4] < nArray2[n6]) {
                nArray[n5] = n6;
                if (n == n5 - 1) {
                    --n5;
                    break;
                }
                n6 = nArray[--n5 - 1];
            }
            nArray[n5] = n4;
        }
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int[] nArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIllIIlIlIllIIIlIllIlI(nArray, nArray2, n, n2);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, n9, n9 + n6, n9 + 2 * n6);
            n8 = IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, n8 - n6, n8, n8 + n6);
            n10 = IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, n10 - 2 * n6, n10 - n6, n10);
        }
        n8 = IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, n9, n8, n10);
        n6 = nArray2[nArray[n8]];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Integer.compare(nArray2[nArray[n11]], n6)) <= 0) {
                if (n3 == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Integer.compare(nArray2[nArray[n4]], n6)) >= 0) {
                if (n3 == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, nArray2, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, nArray2, n2 - n3, n2);
        }
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(int[] nArray, int[] nArray2) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, nArray2, 0, nArray2.length);
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, int[] nArray2, int n, int n2) {
        if (n2 - n < 8192) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, nArray2, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new llllIllllIlIIllIIIIIIIIll(nArray, nArray2, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void IIIIllIlIIIllIlllIlllllIl(int[] nArray, int[] nArray2) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, nArray2, 0, nArray2.length);
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(int[] nArray, int[] nArray2, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (nArray2[nArray[i]] == nArray2[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IlllIlIllIlllIlIIlIlllIll.IllIIIIIIIlIlIllllIIllIII(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IlllIlIllIlllIlIIlIlllIll.IllIIIIIIIlIlIllllIIllIII(nArray, n3, n2);
        }
    }

    public static void IIIIllIIllIIIIllIllIIIlIl(int[] nArray, int[] nArray2) {
        IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, nArray2, 0, nArray.length);
    }

    private static int IIIIllIIllIIIIllIllIIIlIl(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        int n4;
        int n5 = Integer.compare(nArray[n], nArray[n2]);
        int n6 = n5 == 0 ? Integer.compare(nArray2[n], nArray2[n2]) : n5;
        n5 = Integer.compare(nArray[n], nArray[n3]);
        int n7 = n5 == 0 ? Integer.compare(nArray2[n], nArray2[n3]) : n5;
        n5 = Integer.compare(nArray[n2], nArray[n3]);
        int n8 = n4 = n5 == 0 ? Integer.compare(nArray2[n2], nArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static void IlllIllIlIIIIlIIlIIllIIIl(int[] nArray, int[] nArray2, int n, int n2) {
        int n3 = nArray[n];
        int n4 = nArray2[n];
        nArray[n] = nArray[n2];
        nArray2[n] = nArray2[n2];
        nArray[n2] = n3;
        nArray2[n2] = n4;
    }

    private static void IlIlIIIlllIIIlIlllIlIllIl(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            IlllIlIllIlllIlIIlIlllIll.IlllIllIlIIIIlIIlIIllIIIl(nArray, nArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static void IlIlllIIIIllIllllIllIIlIl(int[] nArray, int[] nArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                int n5 = Integer.compare(nArray[n3], nArray[n4]);
                if (n5 >= 0 && (n5 != 0 || nArray2[n3] >= nArray2[n4])) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = nArray[i];
            nArray[i] = nArray[n4];
            nArray[n4] = n3;
            n3 = nArray2[i];
            nArray2[i] = nArray2[n4];
            nArray2[n4] = n3;
        }
    }

    public static void IIIIllIlIIIllIlllIlllllIl(int[] nArray, int[] nArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            IlllIlIllIlllIlIIlIlllIll.IlIlllIIIIllIllllIllIIlIl(nArray, nArray2, n, n2);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, nArray2, n9, n9 + n6, n9 + 2 * n6);
            n8 = IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, nArray2, n8 - n6, n8, n8 + n6);
            n10 = IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, nArray2, n10 - 2 * n6, n10 - n6, n10);
        }
        n8 = IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, nArray2, n9, n8, n10);
        n6 = nArray[n8];
        int n11 = nArray2[n8];
        int n12 = n5 = n;
        int n13 = n4 = n2 - 1;
        while (true) {
            int n14;
            if (n12 <= n4 && (n3 = (n14 = Integer.compare(nArray[n12], n6)) == 0 ? Integer.compare(nArray2[n12], n11) : n14) <= 0) {
                if (n3 == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlllIllIlIIIIlIIlIIllIIIl(nArray, nArray2, n5++, n12);
                }
                ++n12;
                continue;
            }
            while (n4 >= n12 && (n3 = (n14 = Integer.compare(nArray[n4], n6)) == 0 ? Integer.compare(nArray2[n4], n11) : n14) >= 0) {
                if (n3 == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlllIllIlIIIIlIIlIIllIIIl(nArray, nArray2, n4, n13--);
                }
                --n4;
            }
            if (n12 > n4) break;
            IlllIlIllIlllIlIIlIlllIll.IlllIllIlIIIIlIIlIIllIIIl(nArray, nArray2, n12++, n4--);
        }
        n3 = Math.min(n5 - n, n12 - n5);
        IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, nArray2, n, n12 - n3, n3);
        n3 = Math.min(n13 - n4, n2 - n13 - 1);
        IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, nArray2, n12, n2 - n3, n3);
        n3 = n12 - n5;
        if (n3 > 1) {
            IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, n, n + n3);
        }
        if ((n3 = n13 - n4) > 1) {
            IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, n2 - n3, n2);
        }
    }

    public static void IlIlIIIlllIIIlIlllIlIllIl(int[] nArray, int[] nArray2) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, nArray2);
        IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, 0, nArray.length);
    }

    public static void IIIIllIIllIIIIllIllIIIlIl(int[] nArray, int[] nArray2, int n, int n2) {
        if (n2 - n < 8192) {
            IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new IIIllIIlIIlIIIIIlIllIIIIl(nArray, nArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static void IIIllIllIlIlllllllIlIlIII(int[] nArray, int[] nArray2) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, nArray2);
        IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, nArray2, 0, nArray.length);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int n, int n2, int[] nArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            IlllIlIllIlllIlIIlIlllIll.IIIlllIIIllIllIlIIIIIIlII(nArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray2, n, n4, nArray);
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray2, n4, n2, nArray);
        if (nArray2[n4 - 1] <= nArray2[n4]) {
            System.arraycopy(nArray2, n, nArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            nArray[i] = n6 >= n2 || n5 < n4 && nArray2[n5] <= nArray2[n6] ? nArray2[n5++] : nArray2[n6++];
        }
    }

    public static void lIIIIllIIlIlIllIIIlIllIlI(int[] nArray, int n, int n2) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, n, n2, (int[])nArray.clone());
    }

    public static void IIIIllIlIIIllIlllIlllllIl(int[] nArray) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIllIIlIlIllIIIlIllIlI(nArray, 0, nArray.length);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2, int[] nArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, n, n2, lIlIIIIlIlIlllIlIIllIlIll2);
            return;
        }
        int n4 = n + n2 >>> 1;
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray2, n, n4, lIlIIIIlIlIlllIlIIllIlIll2, nArray);
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray2, n4, n2, lIlIIIIlIlIlllIlIIllIlIll2, nArray);
        if (lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(nArray2[n4 - 1], nArray2[n4]) <= 0) {
            System.arraycopy(nArray2, n, nArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            nArray[i] = n6 >= n2 || n5 < n4 && lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(nArray2[n5], nArray2[n6]) <= 0 ? nArray2[n5++] : nArray2[n6++];
        }
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(int[] nArray, int n, int n2, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, n, n2, lIlIIIIlIlIlllIlIIllIlIll2, (int[])nArray.clone());
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(int[] nArray, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, 0, nArray.length, lIlIIIIlIlIlllIlIIllIlIll2);
    }

    public static int IlllIIIlIlllIllIlIIlllIlI(int[] nArray, int n, int n2, int n3) {
        --n2;
        while (n <= n2) {
            int n4 = n + n2 >>> 1;
            int n5 = nArray[n4];
            if (n5 < n3) {
                n = n4 + 1;
                continue;
            }
            if (n5 > n3) {
                n2 = n4 - 1;
                continue;
            }
            return n4;
        }
        return -(n + 1);
    }

    public static int IlIlIIIlllIIIlIlllIlIllIl(int[] nArray, int n) {
        return IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, 0, nArray.length, n);
    }

    public static int lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int n, int n2, int n3, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        --n2;
        while (n <= n2) {
            int n4 = n + n2 >>> 1;
            int n5 = nArray[n4];
            int n6 = lIlIIIIlIlIlllIlIIllIlIll2.lIIIIlIIllIIlIIlIIIlIIllI(n5, n3);
            if (n6 < 0) {
                n = n4 + 1;
                continue;
            }
            if (n6 > 0) {
                n2 = n4 - 1;
                continue;
            }
            return n4;
        }
        return -(n + 1);
    }

    public static int lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int n, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        return IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, 0, nArray.length, n, lIlIIIIlIlIlllIlIIllIlIll2);
    }

    public static void IIIIllIIllIIIIllIllIIIlIl(int[] nArray) {
        IlllIlIllIlllIlIIlIlllIll.IlllIllIlIIIIlIIlIIllIIIl(nArray, 0, nArray.length);
    }

    public static void IlllIllIlIIIIlIIlIIllIIIl(int[] nArray, int n, int n2) {
        if (n2 - n < 1024) {
            IlllIlIllIlllIlIIlIlllIll.IIIllIllIlIlllllllIlIlIII(nArray, n, n2);
            return;
        }
        int n3 = 3;
        int n4 = 766;
        int n5 = 0;
        int[] nArray2 = new int[766];
        int[] nArray3 = new int[766];
        int[] nArray4 = new int[766];
        nArray2[n5] = n;
        nArray3[n5] = n2 - n;
        nArray4[n5++] = 0;
        int[] nArray5 = new int[256];
        int[] nArray6 = new int[256];
        while (n5 > 0) {
            int n6;
            int n7 = nArray2[--n5];
            int n8 = nArray3[n5];
            int n9 = nArray4[n5];
            int n10 = n9 % 4 == 0 ? 128 : 0;
            int n11 = (3 - n9 % 4) * 8;
            int n12 = n7 + n8;
            while (n12-- != n7) {
                int n13 = nArray[n12] >>> n11 & 0xFF ^ n10;
                nArray5[n13] = nArray5[n13] + 1;
            }
            n12 = -1;
            int n14 = n7;
            for (n6 = 0; n6 < 256; ++n6) {
                if (nArray5[n6] != 0) {
                    n12 = n6;
                }
                nArray6[n6] = n14 += nArray5[n6];
            }
            n6 = n7 + n8 - nArray5[n12];
            int n15 = -1;
            for (n14 = n7; n14 <= n6; n14 += nArray5[n15]) {
                int n16 = nArray[n14];
                n15 = n16 >>> n11 & 0xFF ^ n10;
                if (n14 < n6) {
                    while (true) {
                        int n17 = n15;
                        int n18 = nArray6[n17] - 1;
                        nArray6[n17] = n18;
                        int n19 = n18;
                        if (n18 <= n14) break;
                        int n20 = n16;
                        n16 = nArray[n19];
                        nArray[n19] = n20;
                        n15 = n16 >>> n11 & 0xFF ^ n10;
                    }
                    nArray[n14] = n16;
                }
                if (n9 < 3 && nArray5[n15] > 1) {
                    if (nArray5[n15] < 1024) {
                        IlllIlIllIlllIlIIlIlllIll.IIIllIllIlIlllllllIlIlIII(nArray, n14, n14 + nArray5[n15]);
                    } else {
                        nArray2[n5] = n14;
                        nArray3[n5] = nArray5[n15];
                        nArray4[n5++] = n9 + 1;
                    }
                }
                nArray5[n15] = 0;
            }
        }
    }

    public static void IlIlllIIIIllIllllIllIIlIl(int[] nArray, int n, int n2) {
        if (n2 - n < 1024) {
            IlllIlIllIlllIlIIlIlllIll.IIIllIllIlIlllllllIlIlIII(nArray, n, n2);
            return;
        }
        int n3 = 3;
        LinkedBlockingQueue<lIIllIlIIlIIIIIlIIlIlIIll> linkedBlockingQueue = new LinkedBlockingQueue<lIIllIlIIlIIIIIlIIlIlIIll>();
        linkedBlockingQueue.add(new lIIllIlIIlIIIIIlIIlIlIIll(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n4 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n4, Executors.defaultThreadFactory());
        ExecutorCompletionService executorCompletionService = new ExecutorCompletionService(executorService);
        int n5 = n4;
        while (n5-- != 0) {
            executorCompletionService.submit(new lllllllllIllllIIllIIlIIII(atomicInteger, n4, linkedBlockingQueue, nArray));
        }
        Throwable throwable = null;
        int n6 = n4;
        while (n6-- != 0) {
            try {
                executorCompletionService.take().get();
            } catch (Exception exception) {
                throwable = exception.getCause();
            }
        }
        executorService.shutdown();
        if (throwable != null) {
            throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
        }
    }

    public static void IlIlIIIlllIIIlIlllIlIllIl(int[] nArray) {
        IlllIlIllIlllIlIIlIlllIll.IlIlllIIIIllIllllIllIIlIl(nArray, 0, nArray.length);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int[] nArray2, boolean bl) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, nArray2, 0, nArray.length, bl);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int[] nArray2, int n, int n2, boolean bl) {
        int[] nArray3;
        if (n2 - n < 1024) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIllIIlIlIllIIIlIllIlI(nArray, nArray2, n, n2);
            return;
        }
        int n3 = 3;
        int n4 = 766;
        int n5 = 0;
        int[] nArray4 = new int[766];
        int[] nArray5 = new int[766];
        int[] nArray6 = new int[766];
        nArray4[n5] = n;
        nArray5[n5] = n2 - n;
        nArray6[n5++] = 0;
        int[] nArray7 = new int[256];
        int[] nArray8 = new int[256];
        int[] nArray9 = nArray3 = bl ? new int[nArray.length] : null;
        while (n5 > 0) {
            int n6;
            int n7;
            int n8 = nArray4[--n5];
            int n9 = nArray5[n5];
            int n10 = nArray6[n5];
            int n11 = n10 % 4 == 0 ? 128 : 0;
            int n12 = (3 - n10 % 4) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = nArray2[nArray[n13]] >>> n12 & 0xFF ^ n11;
                nArray7[n14] = nArray7[n14] + 1;
            }
            n13 = -1;
            int n15 = n7 = bl ? 0 : n8;
            for (n6 = 0; n6 < 256; ++n6) {
                if (nArray7[n6] != 0) {
                    n13 = n6;
                }
                nArray8[n6] = n7 += nArray7[n6];
            }
            if (bl) {
                n6 = n8 + n9;
                while (n6-- != n8) {
                    int n16 = nArray2[nArray[n6]] >>> n12 & 0xFF ^ n11;
                    int n17 = nArray8[n16] - 1;
                    nArray8[n16] = n17;
                    nArray3[n17] = nArray[n6];
                }
                System.arraycopy(nArray3, 0, nArray, n8, n9);
                n7 = n8;
                for (n6 = 0; n6 <= n13; ++n6) {
                    if (n10 < 3 && nArray7[n6] > 1) {
                        if (nArray7[n6] < 1024) {
                            IlllIlIllIlllIlIIlIlllIll.lIIIIllIIlIlIllIIIlIllIlI(nArray, nArray2, n7, n7 + nArray7[n6]);
                        } else {
                            nArray4[n5] = n7;
                            nArray5[n5] = nArray7[n6];
                            nArray6[n5++] = n10 + 1;
                        }
                    }
                    n7 += nArray7[n6];
                }
                Arrays.fill(nArray7, 0);
                continue;
            }
            n6 = n8 + n9 - nArray7[n13];
            int n18 = -1;
            for (n7 = n8; n7 <= n6; n7 += nArray7[n18]) {
                int n19 = nArray[n7];
                n18 = nArray2[n19] >>> n12 & 0xFF ^ n11;
                if (n7 < n6) {
                    while (true) {
                        int n20 = n18;
                        int n21 = nArray8[n20] - 1;
                        nArray8[n20] = n21;
                        int n22 = n21;
                        if (n21 <= n7) break;
                        int n23 = n19;
                        n19 = nArray[n22];
                        nArray[n22] = n23;
                        n18 = nArray2[n19] >>> n12 & 0xFF ^ n11;
                    }
                    nArray[n7] = n19;
                }
                if (n10 < 3 && nArray7[n18] > 1) {
                    if (nArray7[n18] < 1024) {
                        IlllIlIllIlllIlIIlIlllIll.lIIIIllIIlIlIllIIIlIllIlI(nArray, nArray2, n7, n7 + nArray7[n18]);
                    } else {
                        nArray4[n5] = n7;
                        nArray5[n5] = nArray7[n18];
                        nArray6[n5++] = n10 + 1;
                    }
                }
                nArray7[n18] = 0;
            }
        }
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, int[] nArray2, int n, int n2, boolean bl) {
        if (n2 - n < 1024) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, nArray2, n, n2, bl);
            return;
        }
        int n3 = 3;
        LinkedBlockingQueue<lIIllIlIIlIIIIIlIIlIlIIll> linkedBlockingQueue = new LinkedBlockingQueue<lIIllIlIIlIIIIIlIIlIlIIll>();
        linkedBlockingQueue.add(new lIIllIlIIlIIIIIlIIlIlIIll(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n4 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n4, Executors.defaultThreadFactory());
        ExecutorCompletionService executorCompletionService = new ExecutorCompletionService(executorService);
        int[] nArray3 = bl ? new int[nArray.length] : null;
        int n5 = n4;
        while (n5-- != 0) {
            executorCompletionService.submit(new lIlllIIllIIIlIIlIIIIIIlIl(atomicInteger, n4, linkedBlockingQueue, nArray2, nArray, bl, nArray3));
        }
        Throwable throwable = null;
        int n6 = n4;
        while (n6-- != 0) {
            try {
                executorCompletionService.take().get();
            } catch (Exception exception) {
                throwable = exception.getCause();
            }
        }
        executorService.shutdown();
        if (throwable != null) {
            throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
        }
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, int[] nArray2, boolean bl) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, nArray2, 0, nArray2.length, bl);
    }

    public static void IllIIIIIIIlIlIllllIIllIII(int[] nArray, int[] nArray2) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, nArray2);
        IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, nArray2, 0, nArray.length);
    }

    public static void IlIlIIIlllIIIlIlllIlIllIl(int[] nArray, int[] nArray2, int n, int n2) {
        if (n2 - n < 1024) {
            IlllIlIllIlllIlIIlIlllIll.IlIlllIIIIllIllllIllIIlIl(nArray, nArray2, n, n2);
            return;
        }
        int n3 = 2;
        int n4 = 7;
        int n5 = 1786;
        int n6 = 0;
        int[] nArray3 = new int[1786];
        int[] nArray4 = new int[1786];
        int[] nArray5 = new int[1786];
        nArray3[n6] = n;
        nArray4[n6] = n2 - n;
        nArray5[n6++] = 0;
        int[] nArray6 = new int[256];
        int[] nArray7 = new int[256];
        while (n6 > 0) {
            int n7;
            int n8 = nArray3[--n6];
            int n9 = nArray4[n6];
            int n10 = nArray5[n6];
            int n11 = n10 % 4 == 0 ? 128 : 0;
            int[] nArray8 = n10 < 4 ? nArray : nArray2;
            int n12 = (3 - n10 % 4) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = nArray8[n13] >>> n12 & 0xFF ^ n11;
                nArray6[n14] = nArray6[n14] + 1;
            }
            n13 = -1;
            int n15 = n8;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray6[n7] != 0) {
                    n13 = n7;
                }
                nArray7[n7] = n15 += nArray6[n7];
            }
            n7 = n8 + n9 - nArray6[n13];
            int n16 = -1;
            for (n15 = n8; n15 <= n7; n15 += nArray6[n16]) {
                int n17 = nArray[n15];
                int n18 = nArray2[n15];
                n16 = nArray8[n15] >>> n12 & 0xFF ^ n11;
                if (n15 < n7) {
                    while (true) {
                        int n19 = n16;
                        int n20 = nArray7[n19] - 1;
                        nArray7[n19] = n20;
                        int n21 = n20;
                        if (n20 <= n15) break;
                        n16 = nArray8[n21] >>> n12 & 0xFF ^ n11;
                        int n22 = n17;
                        n17 = nArray[n21];
                        nArray[n21] = n22;
                        n22 = n18;
                        n18 = nArray2[n21];
                        nArray2[n21] = n22;
                    }
                    nArray[n15] = n17;
                    nArray2[n15] = n18;
                }
                if (n10 < 7 && nArray6[n16] > 1) {
                    if (nArray6[n16] < 1024) {
                        IlllIlIllIlllIlIIlIlllIll.IlIlllIIIIllIllllIllIIlIl(nArray, nArray2, n15, n15 + nArray6[n16]);
                    } else {
                        nArray3[n6] = n15;
                        nArray4[n6] = nArray6[n16];
                        nArray5[n6++] = n10 + 1;
                    }
                }
                nArray6[n16] = 0;
            }
        }
    }

    public static void IIIllIllIlIlllllllIlIlIII(int[] nArray, int[] nArray2, int n, int n2) {
        if (n2 - n < 1024) {
            IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, n, n2);
            return;
        }
        int n3 = 2;
        if (nArray.length != nArray2.length) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        int n4 = 7;
        LinkedBlockingQueue<lIIllIlIIlIIIIIlIIlIlIIll> linkedBlockingQueue = new LinkedBlockingQueue<lIIllIlIIlIIIIIlIIlIlIIll>();
        linkedBlockingQueue.add(new lIIllIlIIlIIIIIlIIlIlIIll(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n5 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n5, Executors.defaultThreadFactory());
        ExecutorCompletionService executorCompletionService = new ExecutorCompletionService(executorService);
        int n6 = n5;
        while (n6-- != 0) {
            executorCompletionService.submit(new lIlIllIlIllIlllIIIIllIlII(atomicInteger, n5, linkedBlockingQueue, nArray, nArray2));
        }
        Throwable throwable = null;
        int n7 = n5;
        while (n7-- != 0) {
            try {
                executorCompletionService.take().get();
            } catch (Exception exception) {
                throwable = exception.getCause();
            }
        }
        executorService.shutdown();
        if (throwable != null) {
            throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
        }
    }

    public static void lIIIIllIIlIlIllIIIlIllIlI(int[] nArray, int[] nArray2) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray, nArray2);
        IlllIlIllIlllIlIIlIlllIll.IIIllIllIlIlllllllIlIlIII(nArray, nArray2, 0, nArray.length);
    }

    private static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int[] nArray2, int[] nArray3, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (nArray2[n4] < nArray2[n6] || nArray2[n4] == nArray2[n6] && nArray3[n4] < nArray3[n6]) {
                nArray[n5] = n6;
                if (n == n5 - 1) {
                    --n5;
                    break;
                }
                n6 = nArray[--n5 - 1];
            }
            nArray[n5] = n4;
        }
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int[] nArray2, int[] nArray3, boolean bl) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIIIIIIlIllIIllIlIIlIl(nArray2, nArray3);
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, nArray2, nArray3, 0, nArray2.length, bl);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int[] nArray2, int[] nArray3, int n, int n2, boolean bl) {
        int[] nArray4;
        if (n2 - n < 1024) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, nArray2, nArray3, n, n2);
            return;
        }
        int n3 = 2;
        int n4 = 7;
        int n5 = 1786;
        int n6 = 0;
        int[] nArray5 = new int[1786];
        int[] nArray6 = new int[1786];
        int[] nArray7 = new int[1786];
        nArray5[n6] = n;
        nArray6[n6] = n2 - n;
        nArray7[n6++] = 0;
        int[] nArray8 = new int[256];
        int[] nArray9 = new int[256];
        int[] nArray10 = nArray4 = bl ? new int[nArray.length] : null;
        while (n6 > 0) {
            int n7;
            int n8;
            int n9 = nArray5[--n6];
            int n10 = nArray6[n6];
            int n11 = nArray7[n6];
            int n12 = n11 % 4 == 0 ? 128 : 0;
            int[] nArray11 = n11 < 4 ? nArray2 : nArray3;
            int n13 = (3 - n11 % 4) * 8;
            int n14 = n9 + n10;
            while (n14-- != n9) {
                int n15 = nArray11[nArray[n14]] >>> n13 & 0xFF ^ n12;
                nArray8[n15] = nArray8[n15] + 1;
            }
            n14 = -1;
            int n16 = n8 = bl ? 0 : n9;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray8[n7] != 0) {
                    n14 = n7;
                }
                nArray9[n7] = n8 += nArray8[n7];
            }
            if (bl) {
                n7 = n9 + n10;
                while (n7-- != n9) {
                    int n17 = nArray11[nArray[n7]] >>> n13 & 0xFF ^ n12;
                    int n18 = nArray9[n17] - 1;
                    nArray9[n17] = n18;
                    nArray4[n18] = nArray[n7];
                }
                System.arraycopy(nArray4, 0, nArray, n9, n10);
                n8 = n9;
                for (n7 = 0; n7 < 256; ++n7) {
                    if (n11 < 7 && nArray8[n7] > 1) {
                        if (nArray8[n7] < 1024) {
                            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, nArray2, nArray3, n8, n8 + nArray8[n7]);
                        } else {
                            nArray5[n6] = n8;
                            nArray6[n6] = nArray8[n7];
                            nArray7[n6++] = n11 + 1;
                        }
                    }
                    n8 += nArray8[n7];
                }
                Arrays.fill(nArray8, 0);
                continue;
            }
            n7 = n9 + n10 - nArray8[n14];
            int n19 = -1;
            for (n8 = n9; n8 <= n7; n8 += nArray8[n19]) {
                int n20 = nArray[n8];
                n19 = nArray11[n20] >>> n13 & 0xFF ^ n12;
                if (n8 < n7) {
                    while (true) {
                        int n21 = n19;
                        int n22 = nArray9[n21] - 1;
                        nArray9[n21] = n22;
                        int n23 = n22;
                        if (n22 <= n8) break;
                        int n24 = n20;
                        n20 = nArray[n23];
                        nArray[n23] = n24;
                        n19 = nArray11[n20] >>> n13 & 0xFF ^ n12;
                    }
                    nArray[n8] = n20;
                }
                if (n11 < 7 && nArray8[n19] > 1) {
                    if (nArray8[n19] < 1024) {
                        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, nArray2, nArray3, n8, n8 + nArray8[n19]);
                    } else {
                        nArray5[n6] = n8;
                        nArray6[n6] = nArray8[n19];
                        nArray7[n6++] = n11 + 1;
                    }
                }
                nArray8[n19] = 0;
            }
        }
    }

    private static void lIIIIlIIllIIlIIlIIIlIIllI(int[][] nArray, int n, int n2, int n3) {
        int n4 = nArray.length;
        int n5 = n3 / 4;
        for (int i = n; i < n2 - 1; ++i) {
            int n6;
            int n7;
            int n8 = i;
            block1: for (n7 = i + 1; n7 < n2; ++n7) {
                for (n6 = n5; n6 < n4; ++n6) {
                    if (nArray[n6][n7] < nArray[n6][n8]) {
                        n8 = n7;
                        continue block1;
                    }
                    if (nArray[n6][n7] > nArray[n6][n8]) continue block1;
                }
            }
            if (n8 == i) continue;
            n7 = n4;
            while (n7-- != 0) {
                n6 = nArray[n7][i];
                nArray[n7][i] = nArray[n7][n8];
                nArray[n7][n8] = n6;
            }
        }
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[][] nArray) {
        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, 0, nArray[0].length);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[][] nArray, int n, int n2) {
        if (n2 - n < 1024) {
            IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, n, n2, 0);
            return;
        }
        int n3 = nArray.length;
        int n4 = 4 * n3 - 1;
        int n5 = n3;
        int n6 = nArray[0].length;
        while (n5-- != 0) {
            if (nArray[n5].length == n6) continue;
            throw new IllegalArgumentException("The array of index " + n5 + " has not the same length of the array of index 0.");
        }
        n5 = 255 * (n3 * 4 - 1) + 1;
        n6 = 0;
        int[] nArray2 = new int[n5];
        int[] nArray3 = new int[n5];
        int[] nArray4 = new int[n5];
        nArray2[n6] = n;
        nArray3[n6] = n2 - n;
        nArray4[n6++] = 0;
        int[] nArray5 = new int[256];
        int[] nArray6 = new int[256];
        int[] nArray7 = new int[n3];
        while (n6 > 0) {
            int n7;
            int n8 = nArray2[--n6];
            int n9 = nArray3[n6];
            int n10 = nArray4[n6];
            int n11 = n10 % 4 == 0 ? 128 : 0;
            int[] nArray8 = nArray[n10 / 4];
            int n12 = (3 - n10 % 4) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = nArray8[n13] >>> n12 & 0xFF ^ n11;
                nArray5[n14] = nArray5[n14] + 1;
            }
            n13 = -1;
            int n15 = n8;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray5[n7] != 0) {
                    n13 = n7;
                }
                nArray6[n7] = n15 += nArray5[n7];
            }
            n7 = n8 + n9 - nArray5[n13];
            int n16 = -1;
            for (n15 = n8; n15 <= n7; n15 += nArray5[n16]) {
                int n17 = n3;
                while (n17-- != 0) {
                    nArray7[n17] = nArray[n17][n15];
                }
                n16 = nArray8[n15] >>> n12 & 0xFF ^ n11;
                if (n15 < n7) {
                    block6: while (true) {
                        int n18 = n16;
                        int n19 = nArray6[n18] - 1;
                        nArray6[n18] = n19;
                        int n20 = n19;
                        if (n19 <= n15) break;
                        n16 = nArray8[n20] >>> n12 & 0xFF ^ n11;
                        n17 = n3;
                        while (true) {
                            if (n17-- == 0) continue block6;
                            int n21 = nArray7[n17];
                            nArray7[n17] = nArray[n17][n20];
                            nArray[n17][n20] = n21;
                        }
                    }
                    n17 = n3;
                    while (n17-- != 0) {
                        nArray[n17][n15] = nArray7[n17];
                    }
                }
                if (n10 < n4 && nArray5[n16] > 1) {
                    if (nArray5[n16] < 1024) {
                        IlllIlIllIlllIlIIlIlllIll.lIIIIlIIllIIlIIlIIIlIIllI(nArray, n15, n15 + nArray5[n16], n10 + 1);
                    } else {
                        nArray2[n6] = n15;
                        nArray3[n6] = nArray5[n16];
                        nArray4[n6++] = n10 + 1;
                    }
                }
                nArray5[n16] = 0;
            }
        }
    }

    public static int[] lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int n, int n2, Random random) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random.nextInt(n3 + 1);
            int n5 = nArray[n + n3];
            nArray[n + n3] = nArray[n + n4];
            nArray[n + n4] = n5;
        }
        return nArray;
    }

    public static int[] lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, Random random) {
        int n = nArray.length;
        while (n-- != 0) {
            int n2 = random.nextInt(n + 1);
            int n3 = nArray[n];
            nArray[n] = nArray[n2];
            nArray[n2] = n3;
        }
        return nArray;
    }

    public static int[] IIIllIllIlIlllllllIlIlIII(int[] nArray) {
        int n = nArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            int n3 = nArray[n - n2 - 1];
            nArray[n - n2 - 1] = nArray[n2];
            nArray[n2] = n3;
        }
        return nArray;
    }

    public static int[] llIIlllIIIIlllIllIlIlllIl(int[] nArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            int n5 = nArray[n + n3 - n4 - 1];
            nArray[n + n3 - n4 - 1] = nArray[n + n4];
            nArray[n + n4] = n5;
        }
        return nArray;
    }

    static int lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, int n, int n2, int n3, lIlIIIIlIlIlllIlIIllIlIll lIlIIIIlIlIlllIlIIllIlIll2) {
        return IlllIlIllIlllIlIIlIlllIll.IlllIIIlIlllIllIlIIlllIlI(nArray, n, n2, n3, lIlIIIIlIlIlllIlIIllIlIll2);
    }

    static int IIIIllIlIIIllIlllIlllllIl(int[] nArray, int n, int n2, int n3) {
        return IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, n, n2, n3);
    }

    static int lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        return IlllIlIllIlllIlIIlIlllIll.IIIIllIlIIIllIlllIlllllIl(nArray, nArray2, n, n2, n3);
    }

    static int lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        return IlllIlIllIlllIlIIlIlllIll.IIIIllIIllIIIIllIllIIIlIl(nArray, nArray2, n, n2, n3);
    }

    static void IllIIIIIIIlIlIllllIIllIII(int[] nArray, int[] nArray2, int n, int n2) {
        IlllIlIllIlllIlIIlIlllIll.IlllIllIlIIIIlIIlIIllIIIl(nArray, nArray2, n, n2);
    }

    static void IlllIIIlIlllIllIlIIlllIlI(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, nArray2, n, n2, n3);
    }
}