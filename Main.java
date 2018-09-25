
import com.badlogic.gdx.backends.lwjgl.*;
import com.badlogic.gdx.math.Vector2;

public class Main
{
    public static void main(String args[])
    {
        final Vector2 worldSize = new Vector2(900.0f, 600.0f);
       // 853 : 480 for 16:9 aspect ratio. But we need a smart camera for that to work!
       new LwjglApplication(new GameStart(worldSize), "Krasses Spiel", (int)worldSize.x, (int)worldSize.y);
    }
}
