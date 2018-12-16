
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.math.Rectangle;

abstract class MenuComponent extends InputAdapter
{
    protected ExtendViewport viewport;
    protected ShapeRenderer renderer;
    protected Vector2 imgSize = null;
    protected GameStart screenManager;
    protected Object componentArg = null;

    public MenuComponent(ExtendViewport viewport, GameStart screenManager)
    {
        this.viewport = viewport;
        this.imgSize = imgSize;
        renderer = new ShapeRenderer();
        this.screenManager = screenManager;
    }

    public MenuComponent(ExtendViewport viewport, GameStart screenManager,
            Object componentArg)
    {
        this(viewport, screenManager);

        this.componentArg = componentArg;
    }

    public void setImgSize(Vector2 imgSizeIn)
    {
        imgSize = imgSizeIn;
    }

    public void recalculateBtnPositions()
    {
        Utils.aassert(imgSize != null);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    abstract public void debugRenderBtns();

    public void dispose()
    {
        renderer.dispose();
    }
}