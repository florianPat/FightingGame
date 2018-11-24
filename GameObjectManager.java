
import java.util.HashMap;
import java.util.*;

public class GameObjectManager
{
    private HashMap<Integer, Actor> actors;
    //std::multimap<gomSort::SortKey, unsigned long long, gomSort::SortCompare> sortedActors;
    private ArrayList<Integer> destroyActorQueue;

    public GameObjectManager() {
        actors = new HashMap<Integer, Actor>();
        // multimap
        destroyActorQueue = new ArrayList<Integer>();
    }

    /**
    Ein neues GameObjekt, einen neuen Actor hinzufügen
    */
    public Actor addActor() {
        int id = Utils.getGUID();
        Actor actor = new Actor(id);
        actors.put(id, actor);
        return actor;
    }

    /**
    Entfernt ein GameObjekt/Actor aus dem System. Es ist sozusagen gelöscht!
    */
    public void destroyActor(int actorId) {
        destroyActorQueue.add(actorId);
    }

    public void updateActors(float dt) {
        for(Iterator<Actor> it = actors.values().iterator(); it.hasNext();)
        {
            it.next().update(dt);
        }

        destroyActors();
    }

    public void drawActors()
    {
        for(Iterator<Actor> it = actors.values().iterator(); it.hasNext();)
        {
            it.next().draw();
        }
    }

    //public void sortActors();

    /**
    Liefert einen Actor aufgrund dessen Id
    */
    public Actor getActor(int actorId)
    {
        return actors.get(actorId);
    }

    private void destroyActors()
    {
        if(!destroyActorQueue.isEmpty())
        {
            for(Integer it : destroyActorQueue)
            {
                Actor actorIt = actors.get(it);
                actorIt = null;
                actors.remove(it);
            }
            destroyActorQueue.clear();
        }
    }

    //Für sorting. Habe das hier aber nicht implementiert. Für eine Bespielimplementation siehe: https://github.com/florianPat/ZeldaLike (Warnung: Code in C++!)
    //Siehe auch Actor.java!
    // private int getActorId(long id)
    // {
    //     return (int)(id >> 32l);
    // }

    // private int getComponentId(long id)
    // {
    //     return (int)(id & 0xffffffffl);
    // }
}
