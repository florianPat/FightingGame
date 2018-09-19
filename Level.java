

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.assets.loaders.resolvers.*;

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

    public Level(String levelName)
    {
        this.levelName = levelName;
    }

    public abstract void create();

    @Override
    public void show()
    {
        viewport = new ExtendViewport(900, 600);
        spriteBatch = new SpriteBatch();
        gom = new GameObjectManager();
        eventManager = new EventManager();
        physics = new Physics();
        assetManager = new AssetManager();

        create();

        assetManager.finishLoading();

        map = new Tilemap(levelName, assetManager, physics);
    }

    @Override
    public void render(float dt)
    {
        gom.updateActors(dt);
        eventManager.removeListeners();

        physics.update(dt);

        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        spriteBatch.begin();
        map.draw(spriteBatch);
        gom.drawActors();
        spriteBatch.end();
    }

    @Override
    public void dispose()
    {
        assetManager.dispose();
    }

    @Override
    public void resize(int width, int height)
    {
        //viewport.update(width, height);
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