

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

    public Collider(Rectangle rect)
    {
        type = Type.rect;
        unionCollider = new UnionCollider();

        unionCollider.rect = rect;

        updateRectCollider(this);
    }

    public Collider(Circle circle)
    {
        type = Type.circle;
        unionCollider = new UnionCollider();

        unionCollider.circle = circle;

        updateCircleCollider(this);
    }

    public Type getType()
    {
        return type;
    }

    public Collider()
    {
        type = Type.rect;
        unionCollider = new UnionCollider();
    }

    public static void updateRectCollider(Collider collider)
    {
        assert(collider.type == Type.rect);

        float[] vertices = new float[4 * 2];

        vertices[0] = collider.unionCollider.rect.getX();
        vertices[1] = collider.unionCollider.rect.getY();

        vertices[2] = collider.unionCollider.rect.getX() + collider.unionCollider.rect.getWidth();
        vertices[3] = collider.unionCollider.rect.getY();

        vertices[4] = collider.unionCollider.rect.getX() + collider.unionCollider.rect.getWidth();
        vertices[5] = collider.unionCollider.rect.getY() + collider.unionCollider.rect.getHeight();

        vertices[6] = collider.unionCollider.rect.getX();
        vertices[7] = collider.unionCollider.rect.getY() + collider.unionCollider.rect.getHeight();

        collider.unionCollider.polygon.setVertices(vertices);
    }

    public static void updateCircleCollider(Collider colliderr)
    {
        assert(colliderr.type == Type.circle);

        UnionCollider collider = colliderr.unionCollider;

        //TODO: Ensure not to much segments!
        int segments = Math.max(1, (int)(6 * (float)Math.cbrt(collider.circle.radius)));

        float vertices[] = new float[segments * 2];

        float x = collider.circle.x;
        float y = collider.circle.y;

        float angle = 2 * MathUtils.PI / segments;
        float cos = MathUtils.cos(angle);
        float sin = MathUtils.sin(angle);
        float cx = collider.circle.radius, cy = 0;

        for (int i = 0, j = 0; i < segments; i++)
        {
            float temp = cx;
            cx = cos * cx - sin * cy;
            cy = sin * temp + cos * cy;

            vertices[j++] = x + cx;
            vertices[j++] = y + cy;
        }

        collider.polygon.setVertices(vertices);
    }

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
                assert true;
            }
        }

        assert(true);
        return true;
    }

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