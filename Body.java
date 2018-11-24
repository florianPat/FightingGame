
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Body
{
    public class TriggerInformation
    {
        public String triggerElementCollision = "";
        public Physics.TriggerBodyPart triggerBodyPart = Physics.TriggerBodyPart.NONE;
    }

    public boolean isActive = true;
    public boolean isStatic;
    public boolean isTrigger;
    public boolean triggered = false;
    public TriggerInformation triggerInformation = new TriggerInformation();
    public Vector2 pos;
    public String id;
    public ArrayList<PhysicsElement> physicsElments;

    /**
    Sollte angepasst werden, um das Objekt zu bewegen
    Andere Felder sollte man nicht anfassen!
    */
    public Vector2 vel = new Vector2();

    /**
    @param pos Gibt die position des Objekts an
    @param name Name des Objektes, um es eindeutig zu identifizieren
    @param collider Der Collider, welcher auch als Referenz gehalten werden sollte, um ihn zu verändern
    @param collisionId Eine Liste von Strings von Ids von Objekten, mit welchen dieses Objekt kollidieren soll
    @param isTrigger Ob dieses Objekt als Trigger dient, also nicht wirklich kollidiert sondern nur sagt, ob ein Objekt mit diesem kollidiert
    @param isStatic Wenn sich das Objekt nicht bewegen wird, sollte das hier "true" sein. Dann gibts Optimierungen!
    */
    public Body(Vector2 pos, String name, Collider collider, ArrayList<String> collisionId, boolean isTrigger, boolean isStatic)
    {
        this.isStatic = isStatic;
        this.isTrigger = isTrigger;
        this.pos = pos;
        this.id = name;
        physicsElments = new ArrayList<PhysicsElement>();

        PhysicsElement physicsElement = new PhysicsElement();
        physicsElement.collisionIds = collisionId;
        physicsElement.collider = collider;

        physicsElments.add(physicsElement);
    }

    /**
    Sollte genutzt werden, wenn man nur statische Objekte erstellen will (z.B. Wände)
    So tauchen unter einem Namen viele Collider auf.
    Wenn man also alle Wände in einen Body packt (mit diesem Konstruktor) muss man bei collisionIds einen anderen Objektes nur eine id angeben, statt mehreren
    */
    public Body(String name, ArrayList<Collider> colliders, boolean isTrigger)
    {
        this.isStatic = true;
        this.isTrigger = isTrigger;
        this.pos = new Vector2();
        physicsElments = new ArrayList<PhysicsElement>();

        for(Collider it : colliders)
        {
            PhysicsElement physicsElement = new PhysicsElement();
            physicsElement.collisionIds = new ArrayList<String>();
            physicsElement.collider = it;

            physicsElments.add(physicsElement);
        }
    }

    /**
    Kollidiert ein anderes Objekt mit diesem (wird nur gesetzt, wenn das Objekt ein Trigger ist)
    */
    public boolean getIsTriggered()
    {
        return triggered;
    }

    /**
    In der Render-Methode sollte diese Methode aufgerufen werden, um die Position nach dem hinzurechnen der vel und nach kollisions-Checks zu bekommen.
    */
    public Vector2 getPos()
    {
        assert(!isStatic);

        return pos;
    }

    //TODO: Change collider based on new postion!
    /**
    Benutzen, wenn man die Position nicht durch die vel setzt. Man muss aber beachten, das auch bei jedem Collider die position geupdated werden muss!
    */
    public void setPos(Vector2 newPos)
    {
        assert(!isStatic);

        pos = newPos;
    }

    /**
    Wo die Kollision stattgefunden hat (wenn z.B. Shoes herauskommt könnte man auch sagen: das Objekt steht auf dem Boden)
    */
    TriggerInformation getTriggerInformation()
    {
        return triggerInformation;
    }

    /**
    Liefert die Id des Bodies zurück
    */
    String getId()
    {
        return id;
    }

    /**
    @param active Wenn true, wird das Objekt im Kollisions-Check beachtet; wenn false, wird es ignoriert, als sei es gar nicht da
    */
    void setIsActive(boolean active)
    {
        isActive = active;
    }

    /**
    Liefert, ob der Body "aktiv" ist. (Siehe setIsActive!)
    */
    boolean getIsActive()
    {
        return isActive;
    }
}