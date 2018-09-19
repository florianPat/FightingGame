 


/**
 * Write a description of class Utils here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.concurrent.atomic.AtomicInteger;

public class Utils
{
    private static final AtomicInteger counter = new AtomicInteger();

    public static void utilsLog(String msg) {
        System.err.println(msg);
    }

    public static long Kilobyte(long x) {
        return x * 1024l;
    }

    public static long Megabyte(long x) {
        return x * Kilobyte(x) * 1024l;
    }

    public static long Gigabyte(long x) {
        return x * Megabyte(x) * 1024l;
    }

    public static int getGUID() {
        return counter.getAndIncrement();
    }

    public static float lerp(float v0, float v1, float t) {
        return (1 - t) * v0 + t * v1;
    }

    public static DelegateFunction getDelegateFromFunction(Function function)
    {
        DelegateFunction result = new DelegateFunction(getGUID(), function);
        return result;
    }
}

