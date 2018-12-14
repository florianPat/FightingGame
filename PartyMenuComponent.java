
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class PartyMenuComponent extends MenuBtnsBackComponent
{
    public PartyMenuComponent(ExtendViewport viewport, Vector2 worldSize, Vector2 imgSize,
                             GameStart screenManager)
    {
        super(viewport, worldSize, imgSize, screenManager);

        btns = new Rectangle[1];
    }

    @Override
    public void resetBtns()
    {
        super.resetBtns();

        btns[0] = new Rectangle(43.0f, 45.0f, 40.0f, 40.0f);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        super.touchUp(screenX, screenY, pointer, button);

        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if(btns[0].contains(viewportPosition))
        {
            //TODO: Über Wlan mit Freunden!
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }
}