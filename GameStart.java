
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

public class GameStart extends Game
{
    public GameStart()
    {
    }

    /**
    Hier wird das erste Level gesetzt!
    */
    @Override
    public void create()
    {
        setScreen(new MenuLevel(this, MenuLevel.LevelComponentName.MainMenu));
    }
}
