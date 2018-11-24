
PROJECT TITLE: Stepaluja-Fight
PURPOSE OF PROJECT: school project. A beat` em up game with libgdx and own "engine" around that
VERSION or DATE: 0.6.1.4
HOW TO START THIS PROJECT: run the static method main in the class Main
AUTHORS: Luis, Stefan, Jannes, Florian
USER INSTRUCTIONS: player 1 controls: arrow keys (left, right) to move, up for jumping, down for hit. player 2 : a and d for moving, w for jumping, s for hit

Nur Methoden, welche innerhalb des Javadocs dokumentiert sind, und der Engine angehöhren (die Klassen die hier beschrieben werden), sollten aufgerufen werden. Die anderen werden intern von der Engine benutzt!
Beschreibung der einzelnen Klassen:

- Actor:
Eine Kollektion von Komponenten, ein "GameObject" (z.B. ein Spieler, ein Item...)

- Body:
Ein Objekt (ein Rechteck, ein Kreis, ein gedrehtes Rechteck), welches mit anderen kollidiert
Das Objekt muss erst im Physik-System geadded werden!

- Collider:
Ein Collider, welcher einem Body im Physik-System hinzugefügt wird.

- Component:
Eine Komponente, welche einem Actor hinzugefügt wird. Muss überschrieben werden!

- EventData:
Von dieser Klasse erben, ein neues Feld eventId mit Utils.getGUID() initialisieren, super damit aufrufen und die eigenen Daten hinzufügen. Für Beispiele kann man DeadEventData oder SmashEventData heranziehen.

- EventManager:
System nutzen, um andere Komponenten von einem Event wissen zu lassen
Für ein Beispiel, wie es genutzt wird, siehe AnotherTestComponent

- Function:
Man sollte diese Klasse bzw. Methode überschreiben, und das eventData zu der EventData-Klasse casten, mit welcher diese später mit TriggerEvent aufgerufen wird

- GameObjectManager:
Verwaltet alle GameObjecte / Actors
Für ein Beispiel siehe TestLevel

- Level
Von dieser Klasse erben, um ein Level zu erstellen (ein Menü, einen "Spielescreen", usw...)
Man sollte die Felder dieser Klasse benutzen, um das Spiel zu entwickeln. Dieses ganze Drum-herum-Gerüst ist der Service für das Spiel, die Engine halt.
Felder werden innerhalb von Level erklärt!

- OnScreenControls
Ein "OnScreen Controller". Das Feld input sollte genutzt werden, um die Eingaben zu bekommen.

- Physics
Physik-System, welches Objekte miteinander kollidieren lässt
Für ein Beispiel siehe PlayerComponent oder Tilemap!

- Tilemap
Erstellt eine Tilemap von einer Textdatei. Jede Zahl entspricht einem Tile. Bestimmte Tiles werden als Collider im Physik-System regestriert
Man kann auch die Tilemap Klasse von Libgdx benutzen, um Tiled Tilemaps zu laden. Dort werden dann aber keine Collider für bestimmte Tiles erstellt.

- Utils
Utils-Methoden, welche man vielleicht gebrauchen kann. Siehe auch die MathUtils LibGdx-Klasse

- DelegateFunction
Wird bei dem EventManager-System genutzt