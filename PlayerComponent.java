
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

public class PlayerComponent extends AnimationComponent {
    private float stateTime = 0.0f;

    private enum WalkState
    {
        LEFT,
        RIGHT
    }
    WalkState walkState = WalkState.RIGHT;

    private enum JumpState
    {
        JUMPING,
        FALLING,
        NONE
    }
    private JumpState jumpState = JumpState.NONE;
    private float jumpTimer = 0.0f;
    private float maxJumpTime = 1.0f;
    
    private float smashTimer = 0.0f;
    private float maxSmashTimer = 0.25f;
    private JumpState smashState = JumpState.NONE;
    
    private Texture textureSmash;
    private Rectangle rectSmash;
    private Collider colliderSmash;
    private Body bodySmash;
    private Vector2 size;
    private float yOffset;
    private Vector2 offset;
    
    // order left, top, right, bottom
    private int input[] = new int[4];

    public PlayerComponent(EventManager eventManager, AssetManager assetManager, SpriteBatch spriteBatch, Physics physics, Actor owner, String[] textureAtlas, int n) {
        super(eventManager, assetManager, spriteBatch, physics, owner, textureAtlas);

        //Load textures
        for(int i = 0; i < textureAtlas.length; ++i)
        {
            assetManager.load(textureAtlas[i], Texture.class);
        }
        assetManager.finishLoading();

        animation = new HashMap<String, Animation<Texture>>();

        //create left-walk
        atlas = new Array<Texture>();
        for(int i = 3; i < 6; ++i)
        {
            Texture t = assetManager.get(textureAtlas[i]);
            atlas.add(t);
        }
        animation.put("left-walk", new Animation<Texture>(0.3f, atlas, Animation.PlayMode.LOOP));

        //create right-walk
        atlas.clear();
        for(int i = 6; i < 9; ++i)
        {
            Texture t = assetManager.get(textureAtlas[i]);
            atlas.add(t);
        }
        animation.put("right-walk", new Animation<Texture>(0.3f, atlas, Animation.PlayMode.LOOP));
        
        textureSmash = assetManager.get(textureAtlas[8]);

        //create body
        rect = new Rectangle();
        collider = new Collider(rect);
        ArrayList<String> s = new ArrayList<String>();
        s.add("Ground");
        s.add("PlayerSmashTrigger" + (n == 0 ? 1 : 0));
        body = new Body(new Vector2(450 + (n == 0 ? 0 : 100), 600-32), "Player" + n, collider, s, false, false);
        physics.addElement(body);
        
        size = new Vector2(16.0f, 16.0f);
        rectSmash = new Rectangle(0.0f, 0.0f, size.x, size.y);
        colliderSmash = new Collider(rectSmash);
        ArrayList<String> sSmash = new ArrayList<String>();
        bodySmash = new Body(new Vector2(rectSmash.getX(), rectSmash.getY()), "PlayerSmashTrigger" + n, colliderSmash, sSmash, true, false);
        bodySmash.setIsActive(false);
        physics.addElement(bodySmash);
        
        yOffset = 8.0f;
        offset = new Vector2(0.0f, yOffset);
        
        if(n == 0)
        {
            input[0] = Input.Keys.LEFT;
            input[1] = Input.Keys.UP;
            input[2] = Input.Keys.RIGHT;
            input[3] = Input.Keys.DOWN;
        }
        else
        {
            input[0] = Input.Keys.A;
            input[1] = Input.Keys.W;
            input[2] = Input.Keys.D;
            input[3] = Input.Keys.S;
        }
    }

    @Override
    public void update(float dt) {
        //move
        body.vel.x = 0.0f;
        body.vel.y += physics.gravity;

        stateTime += dt;
        current = null;

        if(Gdx.input.isKeyPressed(input[0]) && body.pos.x >= 0)
        {
            body.vel.x = -speed;
            if(jumpState == JumpState.NONE)
            {
                assert(animation.containsKey("left-walk"));
                current = animation.get("left-walk").getKeyFrame(stateTime, true);
            }
            walkState = WalkState.LEFT;
            offset.x = 0.0f;
        }
        if(Gdx.input.isKeyPressed(input[2]))
        {
            body.vel.x = speed;
            if(jumpState == JumpState.NONE)
            {
                assert (animation.containsKey("right-walk"));
                current = animation.get("right-walk").getKeyFrame(stateTime, true);
            }
            walkState = WalkState.RIGHT;
            offset.x = 16.0f;
        }
        //TODO: Implement isGrounded in Body
        if(Gdx.input.isKeyPressed(input[1]))
        {
            if(jumpState == JumpState.NONE)
            {
                jumpState = JumpState.JUMPING;
            }
        }

        if(jumpState == JumpState.JUMPING)
        {
            jumpTimer += dt;
            body.vel.y = speed;
            if(jumpTimer >= maxJumpTime)
            {
                jumpTimer = 0.0f;
                jumpState = JumpState.FALLING;
            }
        }
        else if(jumpState == JumpState.FALLING)
        {
            if(body.triggerInformation.triggerBodyPart == Physics.TriggerBodyPart.SHOES)
            {
                jumpState = JumpState.NONE;
            }
        }

        //fighting
        if(Gdx.input.isKeyPressed(input[3]))
        {
            if(smashState == JumpState.NONE)
            {
                current = textureSmash;
                bodySmash.setIsActive(true);
                smashState = JumpState.JUMPING;
            }
        }
        else if(smashState == JumpState.JUMPING)
        {
            current = textureSmash;
            smashTimer += dt;
            if(smashTimer > maxSmashTimer)
            {
                smashTimer = 0.0f;
                smashState = JumpState.NONE;
                bodySmash.setIsActive(false);
            }
        }
        
        if(body.getIsTriggered())
        {
            System.out.println("Bamm");
        }

        //set frame
        Vector2 added = body.pos.add(body.vel.scl(dt));
        offset.y = yOffset;

        if(current == null || false)
        {
            switch (walkState)
            {
                case LEFT:
                {
                    assert(animation.containsKey("left-walk"));
                    current = animation.get("left-walk").getKeyFrame(0.0f);
                    offset.x = 0.0f;
                    break;
                }
                case RIGHT:
                {
                    assert(animation.containsKey("right-walk"));
                    current = animation.get("right-walk").getKeyFrame(0.0f);
                    offset.x = 16.0f;
                    break;
                }
                default:
                {
                    assert(false == true);
                }
            }
        }

        //apply updated body to physics
        physics.applySpriteToBoundingBox(current, collider, added);
        collider.updateRectCollider();
        
        offset.add(added);
        bodySmash.pos = offset;
        rectSmash.x = offset.x;
        rectSmash.y = offset.y;
        colliderSmash.updateRectCollider();
    }

    @Override
    public void draw() {
        spriteBatch.draw(current, body.pos.x, body.pos.y);
    }
}
