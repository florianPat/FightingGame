
import com.badlogic.gdx.Game;

public class GameStart extends Game
{
    @Override
    public void create()
    {
        setScreen(new TestLevel());
    }
}
