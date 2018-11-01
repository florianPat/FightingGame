 

import com.badlogic.gdx.Gdx;

import java.util.concurrent.atomic.AtomicInteger;

public class Utils
{
    private static final AtomicInteger counter = new AtomicInteger();

    public static void log(String msg) { Gdx.app.log("UtilsLog", msg); }

    public static void logBreak(String msg) { Gdx.app.error("UtilsLogBreak", msg); }

    public static void invalidCodePath() { assert(true == false); }

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

