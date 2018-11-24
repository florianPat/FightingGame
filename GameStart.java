
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

public class GameStart extends Game
{
    private Vector2 worldSize;

    public GameStart(Vector2 worldSize)
    {
        this.worldSize = worldSize;
    }

    /**
    Hier wird das erste Level gesetzt!
    */
    @Override
    public void create()
    {
        setScreen(new MenuLevel("menu/Titelbild.jpg", this, worldSize, MenuLevel.LevelComponentName.MainMenu));
    }
}
