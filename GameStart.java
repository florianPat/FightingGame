
import com.badlogic.gdx.Game;

public class GameStart extends Game
{
    @Override
    public void create()
    {
        //Set first level!
        setScreen(new TestLevel(this));
    }
}
