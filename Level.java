
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.Application.ApplicationType;

public abstract class Level implements Screen
{
    protected OrthographicCamera camera;
    /**
    Benutzen, um die worldWidth / worldHeight zu bekommen (oder über die Kanera
    viewportWidth / viewportHeight) !!!WICHTIG!!!: Nicht cachen (also in eine
    Variable im Konstruktor speichern. Dort wurde resize noch nicht
    aufgerufen und die Werte sind 0!!!!)
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
    private final Vector2 worldSize = new Vector2(900.0f, 600.0f);

    public Level(GameStart screenManager)
    {
        this.screenManager = screenManager;
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
    }

    /**
    Returned ob das Spiel auf einem mobilen Gerät ausgeführt wird.
    */
    protected boolean onMobile()
    {
        return Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS;
    }

    /**
    Wird aufgerufen, wenn die App pausiert (z.B. Fenster minimiert) (siehe libGdx-Wiki!)
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