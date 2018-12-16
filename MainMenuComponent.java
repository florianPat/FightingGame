
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

class MainMenuComponent extends MenuComponent
{
    private Rectangle btns[] = new Rectangle[4];

    public MainMenuComponent(ExtendViewport viewport, GameStart screenManager)
    {
        super(viewport, screenManager);
    }

    public void resetBtns()
    {
        final float btnWidth = 125.0f;
        final float btnHeight = 12.0f;
        final float btnX = 80.0f;

        btns[0] = new Rectangle(btnX, 85.0f, btnWidth, btnHeight);
        btns[1] = new Rectangle(btnX, btns[0].y - btnHeight, btnWidth, btnHeight);
        btns[2] = new Rectangle(btnX, btns[1].y - btnHeight, btnWidth, btnHeight);
        btns[3] = new Rectangle(btnX, btns[2].y - btnHeight, btnWidth, btnHeight);
    }

    @Override
    public void recalculateBtnPositions()
    {
        super.recalculateBtnPositions();

        resetBtns();

        float scaleX = viewport.getWorldWidth() / imgSize.x;
        float scaleY = viewport.getWorldHeight() / imgSize.y;

        for(Rectangle btn : btns)
        {
            Vector2 origin = new Vector2(btn.getX() + (btn.getWidth() / 2.0f),
                                         btn.getY() + (btn.getHeight() / 2.0f));

            Vector2 localSpacePos = new Vector2(-(origin.x - btn.getX()), -(origin.y - btn.getY()));

            btn.setWidth(btn.getWidth() * scaleX);
            btn.setHeight(btn.getHeight() * scaleY);

            localSpacePos.scl(scaleX, scaleY);
            origin.scl(scaleX, scaleY);
            localSpacePos.add(origin);

            btn.setPosition(localSpacePos);
        }
    }

    @Override
    public void debugRenderBtns()
    {
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeRenderer.ShapeType.Line);

        for(Rectangle btn : btns)
        {
            renderer.rect(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight());
        }
        renderer.end();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if(btns[0].contains(viewportPosition))
        {
            screenManager.setScreen(new MenuLevel(screenManager,
                    MenuLevel.LevelComponentName.PlayMenu));
        }
        else if(btns[1].contains(viewportPosition))
        {
            screenManager.setScreen(new MenuLevel(screenManager,
                    MenuLevel.LevelComponentName.PartyMenu));
        }
        else if(btns[2].contains(viewportPosition))
        {
            screenManager.setScreen(new MenuLevel(screenManager,
                    MenuLevel.LevelComponentName.SettingsMenu));
        }
        else if(btns[3].contains(viewportPosition))
        {
            screenManager.setScreen(new MenuLevel(screenManager,
                    MenuLevel.LevelComponentName.CreditsMenu));
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }
}