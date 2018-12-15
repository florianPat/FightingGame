
PROJECT TITLE: Stepaluja-Fight
PURPOSE OF PROJECT: school project. A beat` em up game with libgdx and own "engine" around that
VERSION or DATE: 0.6.1.4
HOW TO START THIS PROJECT: run the static method main in the class Main
AUTHORS: Luis, Stefan, Jannes, Florian
USER INSTRUCTIONS: player 1 controls: arrow keys (left, right) to move, up for jumping, down for hit. player 2 : a and d for moving, w for jumping, s for hit

----------------------------------------------------------------

Wichtig vorweg: In BlueJ kann der Fehler "no OpenGL Context" auftreten. Dann muss
Bluej neu gestartet werden und alles funktioniert wieder!

Nur Methoden, welche innerhalb des Javadocs dokumentiert sind, und der Engine angehöhren (die Klassen die hier beschrieben werden), sollten aufgerufen werden. Die anderen werden intern von der Engine benutzt! (Oder sind "Spiel-Klassen")
Beschreibung der einzelnen Klassen:

- Actor:
Eine Kollektion von Komponenten, ein "GameObject" (z.B. ein Spieler, ein Item...)

- Body:
Ein Objekt (ein Rechteck, ein Kreis, ein gedrehtes Rechteck), welches mit anderen kollidiert
Das Objekt muss erst im Physik-System geadded werden!

- Collider:
Ein Collider, welcher einem Body im Physik-System hinzugefügt wird.

- Component:
Eine Komponente, welche einem Actor hinzugefügt wird. Muss von geerbt werden!

- EventData:
Von dieser Klasse erben, ein neues Feld eventId mit Utils.getGUID() initialisieren, super damit aufrufen und die eigenen Daten hinzufügen. Für Beispiele kann man DeadEventData oder SmashEventData heranziehen.

- EventManager:
System nutzen, um andere Komponenten von einem Event wissen zu lassen
Für ein Beispiel, siehe AnotherTestComponent

- Function:
Man sollte diese bzw. Methode überschreiben wenn man von der Klasse erbt, und das eventData zu der EventData-Klasse casten, mit welcher diese später mit TriggerEvent aufgerufen wird und dann das tun was bei dem Event passieren soll.

- GameObjectManager:
Verwaltet alle GameObjecte / Actors
Für ein Beispiel siehe TestLevel

- Level
Von dieser Klasse erben, um ein Level zu erstellen (ein Menü, einen "Spielescreen", usw...)
Man sollte die Felder dieser Klasse benutzen, um das Spiel zu entwickeln. Dieses ganze Drum-herum-Gerüst ist der Service für das Spiel, die Engine halt.
Felder werden innerhalb von Level.java erklärt!

- OnScreenControls
Ein "OnScreen Controller". Für ein Beispiel siehe TestLevel.java

- Physics
Physik-System, welches Objekte miteinander kollidieren lässt
Für ein Beispiel siehe PlayerComponent oder Tilemap!

- Tilemap
Erstellt eine Tilemap von einer Textdatei. Jede Zahl entspricht einem Tile. Bestimmte Tiles werden als Collider im Physik-System regestriert
Man kann auch die Tilemap Klasse von Libgdx benutzen, um Tiled Tilemaps zu laden. Dort werden dann aber keine Collider für bestimmte Tiles erstellt.

- Utils
Utils-Methoden, welche man vielleicht gebrauchen kann. Siehe auch die MathUtils LibGdx-Klasse

- DelegateFunction
Wird bei dem EventManager-System genutzt. Man sollte die Utils-Methode benutzen, um diese Klasse zu instanzieren und sie dann im EventManager in addListener als Parameter übergeben.
Für ein Beispiel siehe AnotherTestComponent oder PlayerComponent oder TestLevel

- MenuLevel / MenuComponent
Damit werden Menüs erstellt!

Alle anderen Klassen sollten dem eigentlichen Spiel angehören, wenn ich nichts vergessen habe.

----------------------------------------------------------------------

LibGdx im Allgemeinen:
Man erbt von der Klasse Game und in der überschriebenen Methode create legt man das
erste Level/den ersten Screen mit der Methode setScreen() fest. Dieses Objekt wird
auch benutzt, um den Screen später zu verändern/einen neuen zu setzten.
Die einzelnen Screens implementieren das Interface Screen oder erben von der Klasse
ScreenAdapter. In der letzteren Version muss man nur die Methoden überschreiben,
die man wirklich braucht. Die Klasse Level gibt ein paar Infos über die Methoden.
Die Felder, die in der Klasse Level sind und von LibGdx bereitgestellt werden, werde
ich nun erklären (Wichtig: Siehe LibGdx Dokumentation für mehr Details!):

- Viewport / Camera:
Mathematisch gesehen setzt das Viewport die ProjektionMatrix. Deswegen muss im
Spritebatch auch diese Matrix gesetzt werden (setProjectionMatrix viewport.getCamera().combined)). Man kann eine OrthographicCamera setzten,
was das "Fenster" in die Spielwelt darstellt. In der überschriebenen Methode resize
von Screen muss der Viewport geupdated werden, da Mathe (wenn man mehr wissen will
sollte man sich die Orthographic Projection angucken (#Google hilft)).
Die OrthographicCamera lässt einen zoomen und die Position der Kamera verändern,
um z.B. den Spieler zu zentrieren (für ein Beispiel siehe PlayerComponent).
Es gibt verschiedene Viewports: Man gibt eine Resolution an, auf welcher das Spiel
basiert. In diesem Fall ist das 900x600. Wenn das Fenster nun aber nicht diese
Größe hat, muss das Bild vergrößert oder verkleinert werden und es
verändert sich vielleicht das Seitenverhältnis, was die Grafiken verzehrt. Um
keine unschönen Effekte entstehen zu lassen gibt es so verschieden Herangehensweisen,
dieses Problem zu lösen. Fit z.B. behält das Seitenverhältnis bei, lässt aber schwarze Balken entstehen.
Extend dagegen steigert die "Größe" des Bildes, um keine schwarzen Balken entstehen zu lassen.
So muss man die Methode getWorldWidth() benutzten, um die richtige worldWidth zu bekommen. Das sind nun nicht mehr die eben gennanten 900. Das ist besonders bei z.B. UI positionierungen wichtig, wenn man Elemente am Rechten Rand positionieren will.

- AssetManager:
Mit load() wird das Laden des Assets !!gestartet!!. Mit get() bekommt man das Asset, aber
es gibt erst keinen Fehler, wenn das Asset geladen wurde. Das Laden passiert auf einem
anderen Thread, also parallel. Deshalb sollte man finishLoading() benutzten, bevor man
get() benutzt. Ich habe Texturen und Music/Sounds geladen.

- SpriteBatch:
Gdx.gl.glClearColor( 0, 0, 0, 1 );
Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
Diese beiden Methoden lassen das Fenster "schwarz werden". Das hat einen guten Grund:
Ein Frame geht volgendermaßen: Man updated die Position eines Objektes mit der deltaTime
(dt: Das ist die Zeit die der letzte Zeit zum rendern/updaten gebraucht hat.
0.16ms bedeutet 60fps, da so 60 frames in der Sekunde entstehen). Siehe
PlayerComponent für ein updaten. Dann wird spriteBatch.begin() aufgerufen, dann werden alle
Objekte gezeichnet und dann wird spriteBatch.end() aufgerufen, was die Objekte wirklich
auf den Bildschrim bringt. Das zuletzt gezeichnete Objekt verdeckt das
davor gezeichnete Objekt. Dies nennt man auch Painters Algorithm (siehe Google :))

- Animation
Wie das grundlegend funktioniert sieht man sehr gut an einem Flipbook (#Google).
Im Konstruktor legt man fest, wie lange ein Bild angezeigt wird, bevor das nächste gezeigt
wird. Ist diese Zeit nicht zu lange sieht es für das Auge so aus, als ob es ein bewegtes
Bild ist. Dann gibt man noch ein Array von Textures an, welche zu der Animation gehöhren.
Für den letzten Parameter siehe die LibGdx-Wiki!
Für ein Beispiel siehe AnimationComponent.

Man kann noch die TiledMap-Klasse benutzten, wenn man diesen Mapeditor verwenden möchte.