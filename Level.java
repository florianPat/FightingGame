
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public abstract class Level implements Screen
{
    protected OrthographicCamera camera;
    /**
    Benutzen, um die worldWidth / worldHeight zu bekommen
    */
    protected ExtendViewport viewport;
    /**
    Rendern von Texturen und Sprites
    */
    protected SpriteBatch spriteBatch;
    protected GameObjectManager gom;
    protected EventManager eventManager;
    /**
    Sollte in der überschriebenen Methode create benutzt werden, um z.B. Texturen zu laden
    */
    protected AssetManager assetManager;
    protected Physics physics;
    /**
    Wie der Name schon vermuten lässt, werden hier die Level mit der Methode setScreen gewechselt (z.B. von einem Menü zum "richtigen" Spiel)
    */
    protected GameStart screenManager;
    /**
    Siehe Erklärung unter Main!
    */
    protected Vector2 worldSize;

    public Level(GameStart screenManager, Vector2 worldSize)
    {
        this.screenManager = screenManager;
        this.worldSize = worldSize;
    }

    /**
    Überschreiebn, um alle Felder der Klasse zu initialisieren. Man sollte es nicht im Konstruktor, sondern hier machen!
    */
    public abstract void create();

    @Override
    public void show()
    {
        viewport = new ExtendViewport(worldSize.x, worldSize.y);
        camera = new OrthographicCamera();
        viewport.setCamera(camera);
        spriteBatch = new SpriteBatch();
        gom = new GameObjectManager();
        eventManager = new EventManager();
        physics = new Physics();
        assetManager = new AssetManager();

        create();

        assetManager.finishLoading();
    }

    /**
    Das Level wird geupdated und gerendert
    */
    @Override
    public void render(float dt)
    {
        gom.updateActors(dt);
        eventManager.removeListeners();

        physics.update(dt);

        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        gom.drawActors();
        spriteBatch.end();
    }

    /**
    Alle Felder, welche eine dispose-Methode haben, sollten hier disposed werden (z.B. Textures...)
    */
    @Override
    public void dispose()
    {
        spriteBatch.dispose();
        assetManager.dispose();
        physics.dispose();
    }

    /**
    Wird aufgerufen, wenn das Level erstellt wird oder wenn das Fenster in der Größe verändert wird
    Sollte überschrieben werden, wenn man z.B. Berechnungen mit der worldSize macht. Sollten hier neu berechnet werden
    */
    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);

        worldSize.x = viewport.getWorldWidth();
        worldSize.y = viewport.getWorldHeight();
    }

    /**
    Wird aufgerufen, wenn die App pausiert (siehe libGdx-Wiki!)
    */
    @Override
    public void pause()
    {
    }

    /**
    Wird aufgerufen, wenn die App wieder in den Vordergrund tritt (siehe libGdx-Wiki!)
    */
    @Override
    public void resume()
    {
    }

    /**
    Wird aufgerufen, wenn das Level zerstört wird
    */
    @Override
    public void hide()
    {
    }
}