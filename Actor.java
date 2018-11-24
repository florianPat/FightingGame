
import java.util.HashMap;
import java.util.*;

public class Actor
{
    private final int id;
    private HashMap<Integer, Component> components;

    public void update(float dt) {
        for(Iterator<Component> it = components.values().iterator(); it.hasNext();)
        {
            it.next().update(dt);
        }
    }

    public void draw() {
        for(Iterator<Component> it = components.values().iterator(); it.hasNext();)
        {
            it.next().draw();
        }
    }

    //Für sorting. Habe das hier aber nicht implementiert. Für eine Bespielimplementation siehe: https://github.com/florianPat/ZeldaLike (Warnung: Code in C++!)
    //NOTE: Siehe auch GameObjectManager!
    //public void sort(std::multimap<gomSort::SortKey, unsigned long long, gomSort::SortCompare>& sortedActors);

    // public long GetActorComponentId(int componentId) {
    //     return ((long)id << 32l) | (long)componentId;
    // }

    /**
    Erstellt einen Actor
    */
    public Actor(int id) {
        this.id = id;
        components = new HashMap<Integer, Component>();
    }

    /**
    Fügt eine Komponente hinzu
    */
    public void addComponent(Component component) {
        if(!components.containsKey(component.getId())) {
            components.put(component.getId(), component);
        }
    }

    /**
    Entfernt eine Komponente
    */
    public void removeComponent(int componentId) {
        components.remove(componentId);
    }

    /**
    Liefert die Id des Actors, welche diesen identifiziert
    */
    public int getId() { return id; };
}
