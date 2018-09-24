
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.Input.Keys;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TestLevel extends Level
{
    public TestLevel(GameStart screenManager)
    {
        super("maps/map2.txt", screenManager);
    }
    
    private void createPlayer(String playerName, int n)
    {
        String[] textureAtlas = new String[9];
        for(int i = 0; i < textureAtlas.length; ++i)
        {
            textureAtlas[i] = playerName + "/" + (i+1) + ".png";
        }
        Actor actor = gom.addActor();
        actor.addComponent(new PlayerComponent(eventManager, assetManager, spriteBatch, physics, actor, textureAtlas, n));
    }

    @Override
    public void create()
    {
        createPlayer("player1", 0);
        createPlayer("player1", 1);
    }

    @Override
    public void render(float dt)
    {
        gom.updateActors(dt);
        physics.update(dt);

        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        spriteBatch.begin();
        map.draw(spriteBatch);
        gom.drawActors();
        spriteBatch.end();

        physics.debugRenderBodies();
    }
}
