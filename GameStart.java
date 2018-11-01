 

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

public class GameStart extends Game
{
    private Vector2 worldSize;

    public GameStart(Vector2 worldSize)
    {
        this.worldSize = worldSize;
    }

    @Override
    public void create()
    {
        //Set first level!
        setScreen(new MenuLevel("menu/Titelbild.jpg", this, worldSize, MenuLevel.LevelComponentName.MainMenu));
        //setScreen(new TestLevel(this, worldSize));
    }
}
