

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.assets.loaders.resolvers.*;
import com.badlogic.gdx.math.Vector2;

public abstract class Level implements Screen
{
    protected ExtendViewport viewport;
    protected SpriteBatch spriteBatch;
    protected GameObjectManager gom;
    protected EventManager eventManager;
    protected AssetManager assetManager;
    protected Tilemap map;
    protected String levelName;
    protected Physics physics;
    protected GameStart screenManager;
    protected Vector2 worldSize;
    protected OnScreenControls onScreenControls;

    public Level(String levelName, GameStart screenManager, Vector2 worldSize)
    {
        this.screenManager = screenManager;
        this.levelName = levelName;
        this.worldSize = worldSize;
    }

    public abstract void create();

    @Override
    public void show()
    {
        viewport = new ExtendViewport(worldSize.x, worldSize.y);
        spriteBatch = new SpriteBatch();
        gom = new GameObjectManager();
        eventManager = new EventManager();
        physics = new Physics();
        assetManager = new AssetManager();

        onScreenControls = new OnScreenControls();
        Gdx.input.setInputProcessor(onScreenControls);

        create();

        map = new Tilemap(levelName, assetManager, physics);

        assetManager.finishLoading();
    }

    @Override
    public void render(float dt)
    {
        gom.updateActors(dt);
        eventManager.removeListeners();

        physics.update(dt);

        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        // viewport.apply();
        // spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        map.draw(spriteBatch);
        gom.drawActors();
        spriteBatch.end();

        onScreenControls.render();
    }

    @Override
    public void dispose()
    {
        spriteBatch.dispose();
        assetManager.dispose();
        physics.dispose();
    }

    @Override
    public void resize(int width, int height)
    {
        //NOTE: all update, apply and setProjectionMatrix calls do not work properly :/
        // viewport.update(width, height, true);
        // onScreenControls.viewport.update(width, height, true);
        // onScreenControls.recalculateButtonPositions();
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
    }
}