
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils
{
    private static final AtomicInteger counter = new AtomicInteger();

    private static final boolean debug = true;

    /**
    Für android sollte dieses Assert genutzt werden, das "debug" muss aber angepasst sein.
    */
    public static void aassert(boolean exp)
    {
        if(debug && (!exp))
        {
            throw new AssertionError();
        }
    }

    /**
    Etwas in der Konsole ausgeben.
    */
    public static void log(String msg) { Gdx.app.log("UtilsLog", msg); }

    /**
    Einen Fehler in der Konsole ausgeben und das Programm beenden.
    */
    public static void logBreak(String msg, GameStart screenManager)
    {
        Gdx.app.log("UtilsLogBreak", msg);
        invalidCodePath();
    }

    /**
    Sicherstellen, das man nie diese Zeile erreicht (z.B. switch-Statements)
    */
    public static void invalidCodePath() { aassert(false); }

    /**
    Wandelt bytes in Kilobyte um
    */
    public static long Kilobyte(long x) {
        return x * 1024l;
    }

    /**
    Wandelt bytes in Megabyte um
    */
    public static long Megabyte(long x) {
        return x * Kilobyte(x) * 1024l;
    }

    /**
    Wandelt bytes in Gigabyte um
    */
    public static long Gigabyte(long x) {
        return x * Megabyte(x) * 1024l;
    }

    /**
    Liefert eine GlobalUniqueIdentifier
    */
    public static int getGUID() {
        return counter.getAndIncrement();
    }

    /**
    Sollte man benutzen, um einen EventListener zu erstellen (siehe EventManager)
    */
    public static DelegateFunction getDelegateFromFunction(Function function)
    {
        DelegateFunction result = new DelegateFunction(getGUID(), function);
        return result;
    }

    /**
    Preferences bekommen, um Spieldaten zu speichern
    */
    public static Preferences getGlobalPreferences()
    {
        return Gdx.app.getPreferences("preferences");
    }
}

