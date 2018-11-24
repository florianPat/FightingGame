
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.MathUtils;

public class Collider
{
    public enum Type
    {
        rect,
        circle
    };

    private Type type;

    public class UnionCollider
    {
        public Rectangle rect = null;
        public Circle circle = null;
        private Polygon polygon = new Polygon();
    };

    public UnionCollider unionCollider;

    /**
    Erstellt einen neuen Collider, welcher die Form eines Rechtecks hat
    */
    public Collider(Rectangle rect)
    {
        type = Type.rect;
        unionCollider = new UnionCollider();

        unionCollider.rect = rect;

        updateRectCollider();
    }

    /**
    Erstellt einen neuen Collider, welcher die Form eines Kreises hat
    */
    public Collider(Circle circle)
    {
        type = Type.circle;
        unionCollider = new UnionCollider();

        unionCollider.circle = circle;

        updateCircleCollider();
    }

    /**
    Liefert den Typ der Form zurück
    */
    public Type getType()
    {
        return type;
    }

    /**
    Default-Konstruktor
    */
    public Collider()
    {
        type = Type.rect;
        unionCollider = new UnionCollider();
    }

    /**
    Nachdem bei dem Rechteck, mit welchen der Collider erstellt wurde, etwas geändert wurde, muss diese Methode aufgerufen werden, um den Collider davon wissen zu lassen
    */
    public void updateRectCollider()
    {
        assert(type == Type.rect);

        float[] vertices = new float[4 * 2];

        vertices[0] = unionCollider.rect.getX();
        vertices[1] = unionCollider.rect.getY();

        vertices[2] = unionCollider.rect.getX() + unionCollider.rect.getWidth();
        vertices[3] = unionCollider.rect.getY();

        vertices[4] = unionCollider.rect.getX() + unionCollider.rect.getWidth();
        vertices[5] = unionCollider.rect.getY() + unionCollider.rect.getHeight();

        vertices[6] = unionCollider.rect.getX();
        vertices[7] = unionCollider.rect.getY() + unionCollider.rect.getHeight();

        unionCollider.polygon.setVertices(vertices);
    }

    /**
    Siehe updateRectCollider, nur wenn das Objekt (der Typ), ein Kreis ist
    */
    public void updateCircleCollider()
    {
        assert(type == Type.circle);

        //TODO: Ensure not to much segments!
        int segments = Math.max(1, (int)(6 * (float)Math.cbrt(unionCollider.circle.radius)));

        float vertices[] = new float[segments * 2];

        float x = unionCollider.circle.x;
        float y = unionCollider.circle.y;

        float angle = 2 * MathUtils.PI / segments;
        float cos = MathUtils.cos(angle);
        float sin = MathUtils.sin(angle);
        float cx = unionCollider.circle.radius, cy = 0;

        for (int i = 0, j = 0; i < segments; i++)
        {
            float temp = cx;
            cx = cos * cx - sin * cy;
            cy = sin * temp + cos * cy;

            vertices[j++] = x + cx;
            vertices[j++] = y + cy;
        }

        unionCollider.polygon.setVertices(vertices);
    }

    /**
    Kollidieren die beiden Collider miteinander?
    */
    public boolean intersects(Collider other)
    {
        if(other.type == Type.rect && type == Type.rect)
        {
            return Intersector.overlaps(unionCollider.rect, other.unionCollider.rect);
        }
        else if(other.type == Type.circle && type == Type.circle)
        {
            return Intersector.overlaps(unionCollider.circle, other.unionCollider.circle);
        }
        else
        {
            if(other.type == Type.rect && type == Type.circle)
            {
                return Intersector.overlaps(unionCollider.circle, other.unionCollider.rect);
            }
            else if(other.type == Type.circle && type == Type.rect)
            {
                return Intersector.overlaps(other.unionCollider.circle, unionCollider.rect);
            }
            else
            {
                Utils.invalidCodePath();
            }
        }

        Utils.invalidCodePath();
        return true;
    }

    /**
    Liefert den Vector2 zurück, um welchen dieser Collider translatet (Position verschieben) werden muss, damit die Collider nicht mehr kollidieren.
    */
    public Vector2 collide(Collider other)
    {
        Vector2 minTransVec = null;
        Intersector.MinimumTranslationVector mtv = new  Intersector.MinimumTranslationVector();
        boolean result =  Intersector.overlapConvexPolygons(unionCollider.polygon, other.unionCollider.polygon, mtv);
        if(result)
        {
            minTransVec = new Vector2(mtv.normal.scl(mtv.depth));
        }
        return minTransVec;
    }
}