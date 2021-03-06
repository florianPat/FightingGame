
import com.badlogic.gdx.math.Vector2;

public class TestLevel extends TileMapLevel
{
    private final int playerCount = 2;
    private Function deadFlaggedFunction;
    private HeartComponent[] heartComponents;
    private char[] playerNumbers;

    public TestLevel(GameStart screenManager, char player0, char player1)
    {
        super("maps/map2.txt", screenManager);

        playerNumbers = new char[playerCount];
        playerNumbers[0] = player0;
        playerNumbers[1] = player1;
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);

        for(HeartComponent hc : heartComponents)
        {
            hc.viewport.update(width, height, true);
            hc.recalculateHeartPos();
        }
    }

    private void createPlayer(String playerName, int n)
    {
        String[] textureAtlas = new String[11];
        for(int i = 0; i < textureAtlas.length; ++i)
        {
            textureAtlas[i] = playerName + "/" + (i+1) + ".png";
        }
        Actor actor = gom.addActor();
        heartComponents[n] = new HeartComponent(assetManager, spriteBatch, n);
        actor.addComponent(new PlayerComponent(eventManager, assetManager, spriteBatch, physics,
                actor, textureAtlas, n, onScreenControls.input, camera,
                map.getWidth(), map.getHeight(), heartComponents[n], 'O'));
    }

    @Override
    public void create()
    {
        super.create();

        heartComponents = new HeartComponent[playerCount];

        for(int i = 0; i < playerCount; ++i)
        {
            createPlayer("player" + playerNumbers[i], i);
        }

        deadFlaggedFunction = new Function() {
            @Override
            public void Event(EventData eventData) {
                Utils.aassert(eventData instanceof DeadEventData);
                DeadEventData event = (DeadEventData) eventData;

                int playerId = event.getPlayerId();

                Utils.log("Player " + playerId + " is dead!");
                // screenManager.setScreen(new GameOverScreen(playerId));
                screenManager.setScreen(new MenuLevel(screenManager,
                    MenuLevel.LevelComponentName.MainMenu));
            }
        };

        eventManager.addListener(DeadEventData.eventId, Utils.getDelegateFromFunction(deadFlaggedFunction));
    }

    @Override
    public void render(float dt)
    {
        super.render(dt);

        for(HeartComponent hc : heartComponents)
        {
            hc.draw();
        }
    }
}
