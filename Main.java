
/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import com.badlogic.gdx.backends.lwjgl.*;

public class Main
{
    public static void main(String args[])
    {
       new LwjglApplication(new GameStart(), "Krasses Spiel", 900, 600);
    }
}
