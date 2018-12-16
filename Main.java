
import com.badlogic.gdx.backends.lwjgl.*;
import com.badlogic.gdx.math.Vector2;

public class Main
{
    /**Diese Methode startet das ganze Spiel!
    Der Vector2 worldSize gibt die virtuelle Breite und Hoehe des Spiels an.
    Diese ändert sich aber durch das ExtendViewport, also immer die worldWidth und worldHeight des viewports nehmen!
    (getWorldWidth() / getWorldHeight())
    */
    public static void main(String args[])
    {
        final Vector2 worldSize = new Vector2(900.0f, 600.0f);
        new LwjglApplication(new GameStart(), "Krasses Spiel", (int)worldSize.x, (int)worldSize.y);
    }
}
