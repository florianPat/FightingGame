
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class MenuLevel extends Level
{
    public enum LevelComponentName
    {
        MainMenu,
        CreditsMenu,
        SettingsMenu,
        PartyMenu,
        PlayMenu,
        ChooseCharacterMenu
    }

    private Texture menuTex;
    private Sprite menuSprite;
    private String menuTexName;
    private Vector2 worldSize;
    private MenuComponent menuComponent;
    private LevelComponentName levelComponentName;
    private Object menuComponentArg = null;

    public MenuLevel(GameStart screenManager, Vector2 worldSize, LevelComponentName levelComponentName)
    {
        super(screenManager, worldSize);
        this.worldSize = worldSize;
        this.levelComponentName = levelComponentName;
    }

    public MenuLevel(GameStart screenManager, Vector2 worldSize, LevelComponentName levelComponentName,
            Object menuComponentArg)
    {
        this(screenManager, worldSize, levelComponentName);

        if(menuComponentArg != null)
            this.menuComponentArg = menuComponentArg;
    }

    @Override
    public void create()
    {
        switch(levelComponentName)
        {
            case MainMenu:
            {
                menuTexName = "menu/Titelbild.jpg";
                menuComponent = new MainMenuComponent(viewport, worldSize, screenManager);
                break;
            }
            case CreditsMenu:
            {
                menuTexName = "menu/Mitwirkende.jpg";
                menuComponent = new CreditsMenuComponent(viewport, worldSize, screenManager);
                break;
            }
            case SettingsMenu:
            {
                menuTexName = "menu/Einstellungen.png";
                menuComponent = new SettingsMenuComponent(viewport, worldSize, screenManager);
                break;
            }
            case PartyMenu:
            {
                menuTexName = "menu/PartyModus.jpg";
                menuComponent = new PartyMenuComponent(viewport, worldSize, screenManager);
                break;
            }
            case PlayMenu:
            {
                menuTexName = "menu/Spielen.jpg";
                menuComponent = new PlayMenuComponent(viewport, worldSize, screenManager);
                break;
            }
            case ChooseCharacterMenu:
            {
                menuTexName = "menu/SpielfigurenAuswahl.jpg";
                Utils.aassert(menuComponentArg != null);
                menuComponent = new ChooseCharacterMenuComponent(viewport, worldSize, screenManager,
                    menuComponentArg);
                break;
            }
            default:
            {
                Utils.invalidCodePath();
                break;
            }
        }

        assetManager.load(menuTexName, Texture.class);
        assetManager.finishLoading();
        menuTex = assetManager.get(menuTexName);

        menuSprite = new Sprite(menuTex);

        menuComponent.setImgSize(new Vector2(menuSprite.getWidth(), menuSprite.getHeight()));

        Gdx.input.setInputProcessor(menuComponent);
    }

    @Override
    public void render(float dt)
    {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();

        menuSprite.draw(spriteBatch);

        spriteBatch.end();

        menuComponent.debugRenderBtns();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);

        menuSprite.setSize(worldSize.x, worldSize.y);
        menuComponent.recalculateBtnPositions();
    }

    @Override
    public void dispose()
    {
        super.dispose();

        menuTex.dispose();
        menuComponent.dispose();
    }
}
