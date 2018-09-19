
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class AnimationComponent extends Component {

    public static final int ID = Utils.getGUID();

    protected Array<Texture> atlas;
    protected HashMap<String, Animation<Texture>> animation;

    protected Rectangle rect;
    protected Collider collider;
    protected Body body;

    protected float speed = 50.0f;
    protected Texture current;

    public AnimationComponent(EventManager eventManager, AssetManager assetManager, SpriteBatch spriteBatch, Physics physics, Actor owner, String[] textureAtlas) {
        super(ID, eventManager, assetManager, spriteBatch, physics, owner);
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void draw() {
    }
}
