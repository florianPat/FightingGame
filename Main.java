
import com.badlogic.gdx.backends.lwjgl.*;

public class Main
{
    public static void main(String args[])
    {
       // 853 : 480 for 16:9 aspect ratio. But we need a smart camera for that to work!
       new LwjglApplication(new GameStart(), "Krasses Spiel", 900, 600);
    }
}
