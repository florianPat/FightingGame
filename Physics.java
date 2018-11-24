
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Physics
{
    public enum TriggerBodyPart
    {
        NONE,
        HEAD,
        SHOES,
        LEFT,
        RIGHT
    };

    public final float gravity = -152.2f;
    private HashMap<String, Body> bodies;
    private ShapeRenderer shapeRenderer;

    private void handleCollision(Body itBody, Body collisionElementBody, Collider bodyCollider, final Collider elementCollider)
    {
        if (itBody.isTrigger || collisionElementBody.isTrigger)
        {
            if (bodyCollider.intersects(elementCollider))
            {
                itBody.triggered = true;
                itBody.triggerInformation.triggerElementCollision = collisionElementBody.id;
                return;
            }
        }

        Vector2 minTransVec = bodyCollider.collide(elementCollider);
        if (minTransVec != null)
        {
            if (minTransVec.x > 0.0f)
            {
                itBody.vel.x = 0;
                itBody.triggerInformation.triggerBodyPart = TriggerBodyPart.LEFT;
            }
            else if (minTransVec.x < 0.0f)
            {
                itBody.vel.x = 0;
                itBody.triggerInformation.triggerBodyPart = TriggerBodyPart.RIGHT;
            }
            if (minTransVec.y > 0.0f)
            {
                itBody.vel.y = 0;
                itBody.triggerInformation.triggerBodyPart = TriggerBodyPart.SHOES;
            }
            else if (minTransVec.y < 0.0f)
            {
                itBody.vel.y = 0;
                itBody.triggerInformation.triggerBodyPart = TriggerBodyPart.HEAD;
            }

            itBody.pos.add(minTransVec);
        }
    }

    public Physics()
    {
        bodies = new HashMap<String, Body>();
        shapeRenderer = new ShapeRenderer();
    }

    public void update(float dt)
    {
        for (Iterator<Body> iterator = bodies.values().iterator(); iterator.hasNext();)
        {
            Body it = iterator.next();
            if (it.isActive && (!it.isStatic))
            {
                it.triggered = false;
                it.triggerInformation.triggerElementCollision = "";
                it.triggerInformation.triggerBodyPart = TriggerBodyPart.NONE;

                for (String collisionIdIt : it.physicsElments.get(0).collisionIds)
                {
                    Body collideElementIt = bodies.get(collisionIdIt);
                    Utils.aassert(collideElementIt != null);
                    if (collideElementIt.getIsActive())
                    {
                        Body collideElementBody = collideElementIt;
                        Body itBody = it;

                        Collider bodyRect = itBody.physicsElments.get(0).collider;

                        Collider elementRect = collideElementBody.physicsElments.get(0).collider;
                        if (collideElementBody.isStatic)
                        {
                            for (PhysicsElement collideElementPhysicsElementIt : collideElementBody.physicsElments)
                            {
                                elementRect = collideElementPhysicsElementIt.collider;

                                handleCollision(itBody, collideElementBody, bodyRect, elementRect);
                            }
                        }
                        else
                            handleCollision(itBody, collideElementBody, bodyRect, elementRect);
                    }
                }

                it.pos.add(it.vel.scl(dt));
            }
        }
    }

    /**
    Alle Kollider werden gezeichnet, um zu sehen, wo Sie in der Spielwelt wirklich sind
    */
    public void debugRenderBodies(Viewport viewport)
    {
        if(!bodies.isEmpty())
        {
            shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            for (Iterator<Body> iterator = bodies.values().iterator(); iterator.hasNext();)
            {
                Body it = iterator.next();
                if (/*(!it.isStatic || it.isTrigger)*/true)
                {
                    for(PhysicsElement physicsElement : it.physicsElments)
                    {
                        Collider collider = physicsElement.collider;
                        switch(collider.getType())
                        {
                            case rect:
                            {
                                Rectangle rect = collider.unionCollider.rect;
                                shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                                break;
                            }
                            case circle:
                            {
                                Circle circle = collider.unionCollider.circle;
                                shapeRenderer.circle(circle.x, circle.y, circle.radius);
                                break;
                            }
                            default:
                            {
                                Utils.invalidCodePath();
                            }
                        }
                    }
                }
            }

            shapeRenderer.end();
        }
    }

    /**
    Disposed das Physik-System
    */
    public void dispose()
    {
        shapeRenderer.dispose();
    }

    /**
    Fügt das Objekt dem System hinzu, damit es beim Kollisions-Check berücksichtigt wird
    */
    void addElement(Body body)
    {
        if(bodies.containsKey(body.id))
        {
            Utils.aassert(body.isStatic);

            if(bodies.containsValue(body))
            {
                Utils.log("Physics.addElement: tried to add an object twice!!");
            }
            else
            {
                Body toAddTo = bodies.get(body.id);
                toAddTo.physicsElments.addAll(body.physicsElments);
            }
        }
        else
        {
            Body pref = bodies.put(body.id, body);
            Utils.aassert(pref == null);
        }
    }

    /**
    Entfernt ein Objekt aus dem System, wenn man es nicht mehr braucht (wenn z.B. ein Gegner "zerstört" ist und das Component gelöscht wird)
    */
    boolean removeElementById(String id)
    {
        return (bodies.remove(id) != null);
    }

    /**Wenn die vel gesetzt ist, ist es noch nicht getan. Die pos des Sprites und die Größe wird auf den Collider übertragen.
    Die position des Sprites sollte vorher aufgrund der vel geupdated werden
    */
    public static void applySpriteToBoundingBox(Sprite sprite, Collider boundingBox)
    {
        Utils.aassert (boundingBox.getType() == Collider.Type.rect);

        boundingBox.unionCollider.rect.x = sprite.getX();
        boundingBox.unionCollider.rect.y = sprite.getY();
        boundingBox.unionCollider.rect.width = sprite.getWidth();
        boundingBox.unionCollider.rect.height = sprite.getHeight();
    }

    /**
    Wie applySpriteToBoundingBox, bloß das die Position nicht die des Sprites, sondern der Vektor2 ist
    */
    public static void applySpriteToBoundingBox(Texture texture, Collider boundingBox, Vector2 pos)
    {
        Utils.aassert (boundingBox.getType() == Collider.Type.rect);

        boundingBox.unionCollider.rect.x = pos.x;
        boundingBox.unionCollider.rect.y = pos.y;
        boundingBox.unionCollider.rect.width = texture.getWidth();
        boundingBox.unionCollider.rect.height = texture.getHeight();
    }

    /**
    Liefert alle BodyId`s, welche den string enthalten
    */
    ArrayList<String> getAllCollisionIdsWhichContain(String string)
    {
        ArrayList<String> result = new ArrayList<String>();

        for(Iterator<Body> iterator = bodies.values().iterator(); iterator.hasNext();)
        {
            Body body = iterator.next();

            if(body.getId().contains(string))
            {
                String substr = body.getId().replace(string, "");
                boolean onlyNumbers = true;
                for(int i = 0; i < substr.length(); ++i)
                {
                    char c = substr.charAt(i);

                    if (c >= 48 && c <= 57)
                    {
                        continue;
                    }
                    else
                    {
                        onlyNumbers = false;
                        break;
                    }
                }

                if(onlyNumbers)
                {
                    result.add(body.getId());
                }
            }
        }

        if(result.isEmpty())
        {
            return null;
        }
        else
        {
            return result;
        }
    }
}