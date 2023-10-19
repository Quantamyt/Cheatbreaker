package com.cheatbreaker.client.util.font;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class IlIIlIlIlIIlllIIIllIIIlII {
    public static final Object[] lIIIIlIIllIIlIIlIIIlIIllI = new Object[0];
    private static final int IlllIIIlIlllIllIlIIlllIlI = 16;
    private static final int IIIIllIlIIIllIlllIlllllIl = 8192;
    private static final int IIIIllIIllIIIIllIllIIIlIl = 128;
    private static final int IlIlIIIlllIIIlIlllIlIllIl = 16;
    public static final lllIIIIIIllIlllIlIIlIlIll lIIIIIIIIIlIllIIllIlIIlIl = new IlllllIlIIIIllIIIlIlIlIII(null);

    private IlIIlIlIlIIlllIIIllIIIlII() {
    }

    private static Object[] IIIIllIIllIIIIllIllIIIlIl(Object[] objectArray, int n) {
        Class<?> clazz = objectArray.getClass();
        if (clazz == Object[].class) {
            return n == 0 ? lIIIIlIIllIIlIIlIIIlIIllI : new Object[n];
        }
        return (Object[])Array.newInstance(clazz.getComponentType(), n);
    }

    public static Object[] lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n) {
        if (n > objectArray.length) {
            Object[] objectArray2 = IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n);
            System.arraycopy(objectArray, 0, objectArray2, 0, objectArray.length);
            return objectArray2;
        }
        return objectArray;
    }

    public static Object[] lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n, int n2) {
        if (n > objectArray.length) {
            Object[] objectArray2 = IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n);
            System.arraycopy(objectArray, 0, objectArray2, 0, n2);
            return objectArray2;
        }
        return objectArray;
    }

    public static Object[] lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, int n) {
        if (n > objectArray.length) {
            int n2 = (int)Math.max(Math.min(2L * (long)objectArray.length, 0x7FFFFFF7L), (long)n);
            Object[] objectArray2 = IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n2);
            System.arraycopy(objectArray, 0, objectArray2, 0, objectArray.length);
            return objectArray2;
        }
        return objectArray;
    }

    public static Object[] lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, int n, int n2) {
        if (n > objectArray.length) {
            int n3 = (int)Math.max(Math.min(2L * (long)objectArray.length, 0x7FFFFFF7L), (long)n);
            Object[] objectArray2 = IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n3);
            System.arraycopy(objectArray, 0, objectArray2, 0, n2);
            return objectArray2;
        }
        return objectArray;
    }

    public static Object[] IlllIIIlIlllIllIlIIlllIlI(Object[] objectArray, int n) {
        if (n >= objectArray.length) {
            return objectArray;
        }
        Object[] objectArray2 = IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n);
        System.arraycopy(objectArray, 0, objectArray2, 0, n);
        return objectArray2;
    }

    public static Object[] IIIIllIlIIIllIlllIlllllIl(Object[] objectArray, int n) {
        if (n == objectArray.length) {
            return objectArray;
        }
        if (n < objectArray.length) {
            return IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, n);
        }
        return IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n);
    }

    public static Object[] IlllIIIlIlllIllIlIIlllIlI(Object[] objectArray, int n, int n2) {
        IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n, n2);
        Object[] objectArray2 = IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n2);
        System.arraycopy(objectArray, n, objectArray2, 0, n2);
        return objectArray2;
    }

    public static Object[] lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray) {
        return (Object[])objectArray.clone();
    }

    @Deprecated
    public static void lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, Object object) {
        int n = objectArray.length;
        while (n-- != 0) {
            objectArray[n] = object;
        }
    }

    @Deprecated
    public static void lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n, int n2, Object object) {
        IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(objectArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                objectArray[n2] = object;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                objectArray[i] = object;
            }
        }
    }

    @Deprecated
    public static boolean lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, Object[] objectArray2) {
        int n = objectArray.length;
        if (n != objectArray2.length) {
            return false;
        }
        while (n-- != 0) {
            if (objectArray[n] != null ? objectArray[n].equals(objectArray2[n]) : objectArray2[n] == null) continue;
            return false;
        }
        return true;
    }

    public static void IIIIllIlIIIllIlllIlllllIl(Object[] objectArray, int n, int n2) {
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray.length, n, n2);
    }

    public static void IIIIllIIllIIIIllIllIIIlIl(Object[] objectArray, int n, int n2) {
        lIIlIIlllIIlIIlIIIlIIIIII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray.length, n, n2);
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, Object[] objectArray2) {
        if (objectArray.length != objectArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + objectArray.length + " != " + objectArray2.length);
        }
    }

    public static void IlIlIIIlllIIIlIlllIlIllIl(Object[] objectArray, int n, int n2) {
        Object object = objectArray[n];
        objectArray[n] = objectArray[n2];
        objectArray[n2] = object;
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static int lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, int n, int n2, int n3, Comparator comparator) {
        int n4 = comparator.compare(objectArray[n], objectArray[n2]);
        int n5 = comparator.compare(objectArray[n], objectArray[n3]);
        int n6 = comparator.compare(objectArray[n2], objectArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void IIIIllIlIIIllIlllIlllllIl(Object[] objectArray, int n, int n2, Comparator comparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (comparator.compare(objectArray[j], objectArray[n3]) >= 0) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            Object object = objectArray[i];
            objectArray[i] = objectArray[n3];
            objectArray[n3] = object;
        }
    }

    private static void IIIIllIIllIIIIllIllIIIlIl(Object[] objectArray, int n, int n2, Comparator comparator) {
        int n3 = n;
        while (++n3 < n2) {
            Object object = objectArray[n3];
            int n4 = n3;
            Object object2 = objectArray[n4 - 1];
            while (comparator.compare(object, object2) < 0) {
                objectArray[n4] = object2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                object2 = objectArray[--n4 - 1];
            }
            objectArray[n4] = object;
        }
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n, int n2, Comparator comparator) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(objectArray, n, n2, comparator);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, n8, n8 + n10, n8 + 2 * n10, comparator);
            n7 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, n7 - n10, n7, n7 + n10, comparator);
            n9 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, n9 - 2 * n10, n9 - n10, n9, comparator);
        }
        n7 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, n8, n7, n9, comparator);
        Object object = objectArray[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = comparator.compare(objectArray[n11], object)) <= 0) {
                if (n3 == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = comparator.compare(objectArray[n4], object)) >= 0) {
                if (n3 == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n, n + n3, comparator);
        }
        if ((n3 = n12 - n4) > 1) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n2 - n3, n2, comparator);
        }
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, Comparator comparator) {
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, 0, objectArray.length, comparator);
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, int n, int n2, Comparator comparator) {
        if (n2 - n < 8192) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n, n2, comparator);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new IIllIlIIIlllllIIllllIllll(objectArray, n, n2, comparator));
            forkJoinPool.shutdown();
        }
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, Comparator comparator) {
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, 0, objectArray.length, comparator);
    }

    private static int IlllIIIlIlllIllIlIIlllIlI(Object[] objectArray, int n, int n2, int n3) {
        int n4 = ((Comparable)objectArray[n]).compareTo(objectArray[n2]);
        int n5 = ((Comparable)objectArray[n]).compareTo(objectArray[n3]);
        int n6 = ((Comparable)objectArray[n2]).compareTo(objectArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void IlIlllIIIIllIllllIllIIlIl(Object[] objectArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (((Comparable)objectArray[j]).compareTo(objectArray[n3]) >= 0) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            Object object = objectArray[i];
            objectArray[i] = objectArray[n3];
            objectArray[n3] = object;
        }
    }

    private static void llIIlllIIIIlllIllIlIlllIl(Object[] objectArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            Object object = objectArray[n3];
            int n4 = n3;
            Object object2 = objectArray[n4 - 1];
            while (((Comparable)object).compareTo(object2) < 0) {
                objectArray[n4] = object2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                object2 = objectArray[--n4 - 1];
            }
            objectArray[n4] = object;
        }
    }

    public static void IIIllIllIlIlllllllIlIlIII(Object[] objectArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            IlIIlIlIlIIlllIIIllIIIlII.IlIlllIIIIllIllllIllIIlIl(objectArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, n8, n8 + n10, n8 + 2 * n10);
            n7 = IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, n7 - n10, n7, n7 + n10);
            n9 = IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, n8, n7, n9);
        Object object = objectArray[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = ((Comparable)objectArray[n11]).compareTo(object)) <= 0) {
                if (n3 == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = ((Comparable)objectArray[n4]).compareTo(object)) >= 0) {
                if (n3 == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            IlIIlIlIlIIlllIIIllIIIlII.IlIlIIIlllIIIlIlllIlIllIl(objectArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            IlIIlIlIlIIlllIIIllIIIlII.IIIllIllIlIlllllllIlIlIII(objectArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            IlIIlIlIlIIlllIIIllIIIlII.IIIllIllIlIlllllllIlIlIII(objectArray, n2 - n3, n2);
        }
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray) {
        IlIIlIlIlIIlllIIIllIIIlII.IIIllIllIlIlllllllIlIlIII(objectArray, 0, objectArray.length);
    }

    public static void IllIIIIIIIlIlIllllIIllIII(Object[] objectArray, int n, int n2) {
        if (n2 - n < 8192) {
            IlIIlIlIlIIlllIIIllIIIlII.IIIllIllIlIlllllllIlIlIII(objectArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new lllllllIllIIlIlIlIllIIlII(objectArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(Object[] objectArray) {
        IlIIlIlIlIIlllIIIllIIIlII.IllIIIIIIIlIlIllllIIllIII(objectArray, 0, objectArray.length);
    }

    private static int lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, Object[] objectArray, int n, int n2, int n3) {
        Object object = objectArray[nArray[n]];
        Object object2 = objectArray[nArray[n2]];
        Object object3 = objectArray[nArray[n3]];
        int n4 = ((Comparable)object).compareTo(object2);
        int n5 = ((Comparable)object).compareTo(object3);
        int n6 = ((Comparable)object2).compareTo(object3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void IIIIllIlIIIllIlllIlllllIl(int[] nArray, Object[] objectArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (((Comparable)objectArray[n4]).compareTo(objectArray[n6]) < 0) {
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

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, Object[] objectArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(nArray, objectArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(nArray, objectArray, n8, n8 + n10, n8 + 2 * n10);
            n7 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(nArray, objectArray, n7 - n10, n7, n7 + n10);
            n9 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(nArray, objectArray, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(nArray, objectArray, n8, n7, n9);
        Object object = objectArray[nArray[n7]];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = ((Comparable)objectArray[nArray[n11]]).compareTo(object)) <= 0) {
                if (n3 == 0) {
                    IlllIlIllIlllIlIIlIlllIll.IlIlIIIlllIIIlIlllIlIllIl(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = ((Comparable)objectArray[nArray[n4]]).compareTo(object)) >= 0) {
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
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(nArray, objectArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(nArray, objectArray, n2 - n3, n2);
        }
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, Object[] objectArray) {
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(nArray, objectArray, 0, objectArray.length);
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, Object[] objectArray, int n, int n2) {
        if (n2 - n < 8192) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(nArray, objectArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new IlIlIIlIllllIllIIlIlIllII(nArray, objectArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(int[] nArray, Object[] objectArray) {
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(nArray, objectArray, 0, objectArray.length);
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(int[] nArray, Object[] objectArray, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (objectArray[nArray[i]] == objectArray[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IlllIlIllIlllIlIIlIlllIll.IllIIIIIIIlIlIllllIIllIII(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IlllIlIllIlllIlIIlIlllIll.IllIIIIIIIlIlIllllIIllIII(nArray, n3, n2);
        }
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(int[] nArray, Object[] objectArray) {
        IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(nArray, objectArray, 0, nArray.length);
    }

    private static int IlllIIIlIlllIllIlIIlllIlI(Object[] objectArray, Object[] objectArray2, int n, int n2, int n3) {
        int n4;
        int n5 = ((Comparable)objectArray[n]).compareTo(objectArray[n2]);
        int n6 = n5 == 0 ? ((Comparable)objectArray2[n]).compareTo(objectArray2[n2]) : n5;
        n5 = ((Comparable)objectArray[n]).compareTo(objectArray[n3]);
        int n7 = n5 == 0 ? ((Comparable)objectArray2[n]).compareTo(objectArray2[n3]) : n5;
        n5 = ((Comparable)objectArray[n2]).compareTo(objectArray[n3]);
        int n8 = n4 = n5 == 0 ? ((Comparable)objectArray2[n2]).compareTo(objectArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static void IIIIllIlIIIllIlllIlllllIl(Object[] objectArray, Object[] objectArray2, int n, int n2) {
        Object object = objectArray[n];
        Object object2 = objectArray2[n];
        objectArray[n] = objectArray[n2];
        objectArray2[n] = objectArray2[n2];
        objectArray[n2] = object;
        objectArray2[n2] = object2;
    }

    private static void IIIIllIlIIIllIlllIlllllIl(Object[] objectArray, Object[] objectArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(objectArray, objectArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static void IIIIllIIllIIIIllIllIIIlIl(Object[] objectArray, Object[] objectArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                int n4 = ((Comparable)objectArray[j]).compareTo(objectArray[n3]);
                if (n4 >= 0 && (n4 != 0 || ((Comparable)objectArray2[j]).compareTo(objectArray2[n3]) >= 0)) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            Object object = objectArray[i];
            objectArray[i] = objectArray[n3];
            objectArray[n3] = object;
            object = objectArray2[i];
            objectArray2[i] = objectArray2[n3];
            objectArray2[n3] = object;
        }
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, Object[] objectArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, objectArray2, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, objectArray2, n8, n8 + n10, n8 + 2 * n10);
            n7 = IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, objectArray2, n7 - n10, n7, n7 + n10);
            n9 = IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, objectArray2, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, objectArray2, n8, n7, n9);
        Object object = objectArray[n7];
        Object object2 = objectArray2[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            int n13;
            if (n11 <= n4 && (n3 = (n13 = ((Comparable)objectArray[n11]).compareTo(object)) == 0 ? ((Comparable)objectArray2[n11]).compareTo(object2) : n13) <= 0) {
                if (n3 == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(objectArray, objectArray2, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = (n13 = ((Comparable)objectArray[n4]).compareTo(object)) == 0 ? ((Comparable)objectArray2[n4]).compareTo(object2) : n13) >= 0) {
                if (n3 == 0) {
                    IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(objectArray, objectArray2, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(objectArray, objectArray2, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(objectArray, objectArray2, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(objectArray, objectArray2, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, objectArray2, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, objectArray2, n2 - n3, n2);
        }
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(Object[] objectArray, Object[] objectArray2) {
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, objectArray2);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, objectArray2, 0, objectArray.length);
    }

    public static void lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, Object[] objectArray2, int n, int n2) {
        if (n2 - n < 8192) {
            IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, objectArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new IIIIlIllIIIIlllIlllIIIIII(objectArray, objectArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static void IIIIllIlIIIllIlllIlllllIl(Object[] objectArray, Object[] objectArray2) {
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, objectArray2);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, objectArray2, 0, objectArray.length);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n, int n2, Object[] objectArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            IlIIlIlIlIIlllIIIllIIIlII.llIIlllIIIIlllIllIlIlllIl(objectArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray2, n, n4, objectArray);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray2, n4, n2, objectArray);
        if (((Comparable)objectArray2[n4 - 1]).compareTo(objectArray2[n4]) <= 0) {
            System.arraycopy(objectArray2, n, objectArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            objectArray[i] = n6 >= n2 || n5 < n4 && ((Comparable)objectArray2[n5]).compareTo(objectArray2[n6]) <= 0 ? objectArray2[n5++] : objectArray2[n6++];
        }
    }

    public static void lIIIIllIIlIlIllIIIlIllIlI(Object[] objectArray, int n, int n2) {
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n, n2, (Object[])objectArray.clone());
    }

    public static void IIIIllIlIIIllIlllIlllllIl(Object[] objectArray) {
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIllIIlIlIllIIIlIllIlI(objectArray, 0, objectArray.length);
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n, int n2, Comparator comparator, Object[] objectArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            IlIIlIlIlIIlllIIIllIIIlII.IIIIllIIllIIIIllIllIIIlIl(objectArray, n, n2, comparator);
            return;
        }
        int n4 = n + n2 >>> 1;
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray2, n, n4, comparator, objectArray);
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray2, n4, n2, comparator, objectArray);
        if (comparator.compare(objectArray2[n4 - 1], objectArray2[n4]) <= 0) {
            System.arraycopy(objectArray2, n, objectArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            objectArray[i] = n6 >= n2 || n5 < n4 && comparator.compare(objectArray2[n5], objectArray2[n6]) <= 0 ? objectArray2[n5++] : objectArray2[n6++];
        }
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(Object[] objectArray, int n, int n2, Comparator comparator) {
        IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, n, n2, comparator, (Object[])objectArray.clone());
    }

    public static void IlllIIIlIlllIllIlIIlllIlI(Object[] objectArray, Comparator comparator) {
        IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, 0, objectArray.length, comparator);
    }

    public static int lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, int n, int n2, Object object) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            Object object2 = objectArray[n3];
            int n4 = ((Comparable)object2).compareTo(object);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 > 0) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    public static int lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, Object object) {
        return IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, 0, objectArray.length, object);
    }

    public static int lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n, int n2, Object object, Comparator comparator) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            Object object2 = objectArray[n3];
            int n4 = comparator.compare(object2, object);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 > 0) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    public static int lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, Object object, Comparator comparator) {
        return IlIIlIlIlIIlllIIIllIIIlII.lIIIIlIIllIIlIIlIIIlIIllI(objectArray, 0, objectArray.length, object, comparator);
    }

    public static Object[] lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n, int n2, Random random) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random.nextInt(n3 + 1);
            Object object = objectArray[n + n3];
            objectArray[n + n3] = objectArray[n + n4];
            objectArray[n + n4] = object;
        }
        return objectArray;
    }

    public static Object[] lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, Random random) {
        int n = objectArray.length;
        while (n-- != 0) {
            int n2 = random.nextInt(n + 1);
            Object object = objectArray[n];
            objectArray[n] = objectArray[n2];
            objectArray[n2] = object;
        }
        return objectArray;
    }

    public static Object[] IIIIllIIllIIIIllIllIIIlIl(Object[] objectArray) {
        int n = objectArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            Object object = objectArray[n - n2 - 1];
            objectArray[n - n2 - 1] = objectArray[n2];
            objectArray[n2] = object;
        }
        return objectArray;
    }

    public static Object[] IlllIllIlIIIIlIIlIIllIIIl(Object[] objectArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            Object object = objectArray[n + n3 - n4 - 1];
            objectArray[n + n3 - n4 - 1] = objectArray[n + n4];
            objectArray[n + n4] = object;
        }
        return objectArray;
    }

    static int lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, int n, int n2, int n3, Comparator comparator) {
        return IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(objectArray, n, n2, n3, comparator);
    }

    static int lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, int n, int n2, int n3) {
        return IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, n, n2, n3);
    }

    static int lIIIIlIIllIIlIIlIIIlIIllI(int[] nArray, Object[] objectArray, int n, int n2, int n3) {
        return IlIIlIlIlIIlllIIIllIIIlII.lIIIIIIIIIlIllIIllIlIIlIl(nArray, objectArray, n, n2, n3);
    }

    static int lIIIIlIIllIIlIIlIIIlIIllI(Object[] objectArray, Object[] objectArray2, int n, int n2, int n3) {
        return IlIIlIlIlIIlllIIIllIIIlII.IlllIIIlIlllIllIlIIlllIlI(objectArray, objectArray2, n, n2, n3);
    }

    static void IlllIIIlIlllIllIlIIlllIlI(Object[] objectArray, Object[] objectArray2, int n, int n2) {
        IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(objectArray, objectArray2, n, n2);
    }

    static void lIIIIIIIIIlIllIIllIlIIlIl(Object[] objectArray, Object[] objectArray2, int n, int n2, int n3) {
        IlIIlIlIlIIlllIIIllIIIlII.IIIIllIlIIIllIlllIlllllIl(objectArray, objectArray2, n, n2, n3);
    }
}
