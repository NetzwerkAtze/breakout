# 🧱 Breakout — Entwicklungs-Backlog

Ein lebendes Backlog für mein JavaFX-Breakout-Projekt. Wird regelmäßig erweitert, nie als "fertig" betrachtet.

**Aktueller Stand des Projekts (Referenz):** `Main`, `Game`, `GameRenderer`, `InputHandler`, `LevelConfig` (Record), `GameObject` → `Ball`/`Paddle`/`Brick`/`PowerUp`. Mehrere Bälle (`List<Ball>`), `BallState`-Enum (FREE/STICKING), `PowerUpState`-Enum (WAITING/FALLING/ACTIVE/EXPIRED/REMOVED), vier Power-up-Typen (BIGGER_PADDLE, BIGGER_BALL, ANOTHER_BALL, STICKY_PADDLE), Level-System mit `LevelConfig`-Liste, Leben, Score, Sieg/Niederlage-Screens, Restart, `resolveCollision` mit Überlappungs-Korrektur.

> Wo ich Annahmen über deine aktuelle Implementierung treffen musste, ohne sie 100% sicher zu kennen, markiere ich das mit **(Annahme)** — pass die Aufgabe an deine tatsächliche Struktur an.

---

# 📝 Neue Ideen / Inbox

Hier landen neue Ideen, Bugs und Verbesserungen zunächst ungeordnet, bevor sie einsortiert werden.

- [ ] ...
- [ ] ...
- [ ] ...

---

# 🔄 Aktuell bearbeiten

*(1–3 Aufgaben, an denen ich gerade arbeite)*

- [ ] ...

---

# ✅ Abgeschlossen (Meilensteine)

- [x] Grundspiel: Ball, Paddle, Bricks, Kollision, Game Loop
- [x] Model/View-Trennung (GameObject-Hierarchie ↔ JavaFX-Shapes)
- [x] AABB-Kollision mit Richtungserkennung (oben/unten vs. seitlich, inkl. Ecktreffer)
- [x] Positions-Korrektur nach Kollision (Anti-Glitch, `resolveCollision`)
- [x] Trigonometrischer Abprallwinkel am Paddle (konstante Ballgeschwindigkeit)
- [x] Refactoring: Main/Game/GameRenderer/InputHandler getrennt
- [x] Lebenssystem, Score, Restart
- [x] Mehrstufiges Level-System mit `LevelConfig`
- [x] Power-up-Grundgerüst mit `PowerUpState`-Enum
- [x] Multiball (mehrere Bälle gleichzeitig, `List<Ball>`)
- [x] Sticky Paddle mit Stacking-Dauer

---

# 🎮 Gameplay

### [ ] Combo-/Multiplikator-System
**Priorität:** 🟡 POLISH
**Aufwand:** M
**Bereich:** Gameplay / Score
**Lernziel:** Zustandsmodellierung, Zeit-/Frame-basierte Zähler

**Ziel:** Trifft der Ball mehrere Bricks hintereinander ohne das Paddle zu berühren, steigt ein Multiplikator, der den Score pro Brick erhöht.

**Hinweise:**
- Ein Zähler in `Game`, der bei jedem Brick-Treffer hochgeht und bei Paddle-Kontakt (oder nach X Frames Inaktivität) zurückgesetzt wird.
- Überlege: Soll der Multiplikator linear oder gedeckelt wachsen?

**Betroffene Bereiche:** `Game` (Score-Logik), `GameRenderer` (Anzeige des aktuellen Multiplikators).

**Fertig wenn:** Schneller Brick-Chain-Treffer gibt sichtbar mehr Punkte als vereinzelte Treffer.

---

### [ ] Paddle-Geschwindigkeit als eigener, einstellbarer Wert
**Priorität:** 🟢 OPTIONAL
**Aufwand:** XS
**Bereich:** Gameplay / Paddle

**Ziel:** `vx` des Paddles nicht mehr hart im Konstruktor-Aufruf verdrahten, sondern z. B. pro Level/Schwierigkeitsgrad konfigurierbar machen.

**Hinweise:** Passt gut in `LevelConfig`, falls du dort weitere Felder ergänzt (siehe Aufgabe "LevelConfig erweitern" unten).

---

### [ ] Schwierigkeitsgrad-Auswahl (Easy/Normal/Hard)
**Priorität:** 🟢 OPTIONAL
**Aufwand:** M
**Bereich:** Gameplay / Progression

**Ziel:** Vor Spielstart wählbar, beeinflusst z. B. Ballgeschwindigkeit, Paddle-Breite, Anzahl Leben, Power-up-Dropchance.

**Hinweise:**
- Könnte ein weiterer Record/Enum sein (`Difficulty`), der mehrere Parameter bündelt — ähnliches Muster wie `LevelConfig`.
- Wo würde die Auswahl stattfinden? Braucht vermutlich einen einfachen Vor-Spiel-Screen (siehe UI/UX-Bereich).

**Abhängigkeit:** Sinnvoll nach einem einfachen Hauptmenü (siehe UI/UX).

---

### [ ] Brick-Verschiebung / bewegliche Bricks (fortgeschritten)
**Priorität:** 🔵 EXPERIMENT
**Aufwand:** L
**Bereich:** Gameplay / Bricks
**Lernziel:** Bewegungslogik auf bereits statische Objekte anwenden

**Ziel:** In späteren Leveln bewegen sich einzelne Bricks horizontal hin und her.

**Hinweise:**
- `Brick` bräuchte dann analog zum Paddle eine `vx` und Grenzprüfung.
- Kollisionslogik (`collidesWith`, `resolveCollision`) sollte unverändert funktionieren, da sie generisch auf `GameObject` arbeitet — guter Test, ob deine Architektur das wirklich hergibt, ohne Sonderfälle zu brauchen.

**Mögliche Stolperfallen:** Ball kann theoretisch "durchrutschen", wenn sich Brick und Ball im selben Frame stark gegeneinander bewegen (Tunneling) — bei geringen Geschwindigkeiten vernachlässigbar.

---

### [ ] Zeitlimit pro Level (optional)
**Priorität:** 🔵 EXPERIMENT
**Aufwand:** S
**Bereich:** Gameplay

**Ziel:** Ein Level muss innerhalb einer bestimmten Zeit geschafft werden, sonst automatischer Lebensverlust.

**Hinweise:** Nutze den `now`-Parameter aus `handle(long now)` (Nanosekunden) statt Frame-Zählung — genauer und framerate-unabhängig. Guter erster Schritt weg von reinem Frame-Counting hin zu echter Zeitmessung.

---

# 🧱 Bricks

### [ ] Mehrschichtige Bricks (mehrere Treffer nötig)
**Priorität:** 🟠 IMPORTANT
**Aufwand:** M
**Bereich:** Bricks
**Lernziel:** Zustand statt reinem Boolean — von `isDestroyed` zu einem Zähler

**Ziel:** Manche Bricks brauchen 2–3 Treffer, bevor sie zerstört werden, und ändern dabei ihre Farbe (z. B. dunkler werdend).

**Warum:** Aktuell ist `Brick.isDestroyed` ein reiner Boolean. Für mehrere Treffer brauchst du einen Fortschrittszähler (`hitsRemaining`), ähnlich wie ihr das bei `PowerUp.duration` schon gemacht habt.

**Hinweise:**
- Überlege: Bleibt `isDestroyed()` als abgeleiteter Getter bestehen (`hitsRemaining <= 0`), oder ersetzt du ihn ganz?
- Woher weiß der `GameRenderer`, welche Farbe er bei welchem Resthealth zeichnen soll? Könnte eine Methode wie `brick.getColor()` sein, die abhängig vom Zustand eine abgestufte Farbe zurückgibt.

**Betroffene Bereiche:** `Brick`, `Game.update()` (Kollisionsblock), `GameRenderer`.

**Mögliche Stolperfallen:** Score-Vergabe — gibt's Punkte bei jedem Treffer oder nur beim finalen Zerstören?

**Fertig wenn:** Ein Brick hält sichtbar mehrere Treffer aus, bevor er verschwindet.

---

### [ ] Unzerstörbare Bricks
**Priorität:** 🟡 POLISH
**Aufwand:** S
**Bereich:** Bricks

**Ziel:** Ein Brick-Typ, der nie zerstört werden kann, nur als Hindernis für den Ball dient.

**Hinweise:** Einfachste Umsetzung: ein `boolean indestructible`-Feld, das in `Game.update()` beim Treffer verhindert, dass `destroy()` aufgerufen wird (Abprall passiert trotzdem normal).

**Wichtig:** Muss aus der `allMatch(Brick::isDestroyed)`-Sieg-Prüfung ausgeschlossen werden, sonst ist das Level nie gewinnbar! Guter Test deines Verständnisses von Streams/Lambda-Bedingungen.

---

### [ ] Explosive Bricks (Kettenreaktion)
**Priorität:** 🔵 EXPERIMENT
**Aufwand:** L
**Bereich:** Bricks
**Lernziel:** Rekursion oder iterative Breitensuche über Nachbarobjekte

**Ziel:** Ein Brick-Typ, der beim Zerstören auch benachbarte Bricks zerstört (ggf. mit Kettenreaktion).

**Hinweise:**
- Du brauchst eine Methode "finde alle Bricks, die direkt neben diesem liegen" — wie würdest du "Nachbarschaft" definieren, basierend auf Position/Raster-Koordinaten?
- Vorsicht bei Kettenreaktionen: Wenn du naiv rekursiv alle Nachbarn zerstörst, kannst du versehentlich einen Brick mehrfach verarbeiten — brauchst du eine "bereits besucht"-Markierung?

---

### [ ] Brick-Punktewerte variieren
**Priorität:** 🟡 POLISH
**Aufwand:** XS
**Bereich:** Bricks / Score

**Ziel:** Nicht jeder Brick gibt gleich viele Punkte (z. B. nach Zeilen-Position gestaffelt).

**Hinweise:** `Brick` bräuchte ein `pointValue`-Feld, das beim Erzeugen gesetzt wird (z. B. abhängig von `row`, ähnlich wie ihr das schon bei `rowColors` macht).

---

# 🏓 Paddle & Ball

### [ ] Tunneling-Schutz bei sehr hoher Ballgeschwindigkeit
**Priorität:** 🟠 IMPORTANT
**Aufwand:** M
**Bereich:** Ball / Physik
**Lernziel:** Swept Collision Detection (Grundidee), numerische Grenzfälle

**Ziel:** Verhindern, dass ein sehr schneller Ball ein dünnes Objekt (Brick, Paddle) "überspringt", ohne eine Kollision auszulösen.

**Warum:** Bei eurer aktuellen AABB-Kollision wird nur **nach** der Bewegung geprüft, ob sich der Ball gerade überlappt — bei hoher Geschwindigkeit kann der Ball in einem Frame komplett durch ein dünnes Objekt hindurchspringen.

**Hinweise:**
- Einfachste Gegenmaßnahme: Obergrenze für `vx`/`vy` einführen.
- Fortgeschrittener: Vor der Bewegung prüfen, ob die geplante Bewegungsstrecke ein Objekt schneidet (Raycasting-artig) — das ist ein eigenes, spannendes Thema, kein Muss für den Anfang.

**Fertig wenn:** Auch bei deutlich erhöhter `maxVx`/Ballgeschwindigkeit werden keine Bricks "übersprungen".

---

### [ ] Maximalgeschwindigkeit nach Power-up-Stacking begrenzen
**Priorität:** 🟠 IMPORTANT
**Aufwand:** S
**Bereich:** Ball / Power-ups

**Ziel:** Verhindern, dass sich die Ballgeschwindigkeit durch wiederholte Effekte unkontrolliert aufschaukelt (falls du je ein "Ball schneller"-Power-up einführst).

**Hinweise:** Eine Ober-/Untergrenze (`minSpeed`/`maxSpeed`) direkt in `Ball`, die bei jeder Geschwindigkeitsänderung geklemmt wird (`Math.min`/`Math.max`).

**Abhängigkeit:** Relevant, sobald es ein geschwindigkeitsänderndes Power-up gibt.

---

### [ ] Paddle-Ball-Reibung / "English" beim Treffer während Paddle-Bewegung
**Priorität:** 🔵 EXPERIMENT
**Aufwand:** M
**Bereich:** Ball / Paddle / Physik

**Ziel:** Bewegt sich das Paddle beim Treffen des Balls gerade selbst (links/rechts), bekommt der Ball einen zusätzlichen seitlichen Impuls — fühlt sich "lebendiger" an.

**Hinweise:** Du müsstest die aktuelle Paddle-Bewegungsrichtung (`moveLeft`/`moveRight` bzw. deren Game-Pendant) zum Zeitpunkt der Kollision mit in die `vx`-Berechnung einfließen lassen, zusätzlich zur bestehenden Trefferpositions-Formel.

---

### [ ] Ball-Trail / Bewegungsspur (visuell)
**Priorität:** 🟡 POLISH
**Aufwand:** M
**Bereich:** Ball / Visuals

**Ziel:** Eine kurze, verblassende Spur hinter dem Ball zur besseren Sichtbarkeit bei hoher Geschwindigkeit.

**Hinweise:** Mehrere halbtransparente Circle-Kopien an vorherigen Positionen, die mit der Zeit verblassen und verschwinden — braucht eine kleine, begrenzte Liste "letzte N Positionen" pro Ball.

---

### [ ] Paddle-Kollision mit anderen Bällen bei Multiball synchron halten
**Priorität:** 🟠 IMPORTANT
**Aufwand:** S
**Bereich:** Ball / Code-Review

**Ziel:** Nochmal gezielt durchtesten: Verhalten sich alle Bälle bei Multiball konsistent bei Wand-, Brick- und Paddle-Kollision (inkl. Sticky, inkl. Winkel-Abprall)?

**Hinweise:** Gezielter Test: Zwei Bälle gleichzeitig ans Paddle, einer trifft mittig, einer am Rand — verhält sich jeder unabhängig korrekt?

---

# ⚡ Power-Ups

### [ ] Power-Down einführen (negativer Effekt)
**Priorität:** 🟠 IMPORTANT
**Aufwand:** M
**Bereich:** Power-ups
**Lernziel:** Bestehende Enum-/State-Architektur auf einen "negativen" Fall anwenden

**Ziel:** Mindestens ein Power-**Down** (z. B. `SMALLER_PADDLE` oder `FASTER_BALL`), das das Spiel schwerer macht, statt leichter.

**Warum:** Testet, ob dein `PowerUpType`-System wirklich für beliebige Effekte erweiterbar ist, nicht nur für positive.

**Hinweise:** Technisch läuft das über denselben `PowerUpType`-Enum + `applyEffect`/`removeEffect` — nur die Richtung des Effekts kehrt sich um. Guter Test für Aufgabe "Power-up-Logik zentralisieren" weiter unten.

**Betroffene Bereiche:** `PowerUp.PowerUpType`, `Game.applyEffect`/`removeEffect`, `Game.generatePowerUps`.

---

### [ ] Power-up-Logik zentralisieren (Refactoring)
**Priorität:** 🟠 IMPORTANT
**Aufwand:** M
**Bereich:** Power-ups / Codequalität
**Lernziel:** Switch-Expressions, evtl. Strategy-Pattern-Grundidee

**Ziel:** Prüfe, wie viele `if/else if`-Ketten über `PowerUpType` verteilt sind (`applyEffect`, `removeEffect`, `generatePowerUps`, evtl. `GameRenderer`). Für jeden neuen Typ musstest du bisher an mehreren Stellen Code ergänzen.

**Warum:** Das ist ein klassisches Erweiterbarkeits-Problem. Wenn du einen neuen Power-up-Typ hinzufügst und an 4 verschiedenen Stellen im Code etwas ändern musst, ist das fehleranfällig (leicht, eine Stelle zu vergessen).

**Hinweise:**
- Eine Idee: Jeder `PowerUpType` bekommt in einem `switch`-Ausdruck (Java 14+, falls deine Java-Version das hergibt) an **einer zentralen Stelle** definiert, was beim Aktivieren/Deaktivieren passiert.
- Fortgeschrittenere Idee (nicht zwingend nötig für euer aktuelles Projekt, aber lehrreich zu kennen): Ein **Strategy Pattern**, bei dem jeder Power-up-Typ eine eigene kleine Klasse mit `apply(Game game)`/`remove(Game game)`-Methode hätte. Lohnt sich nur, wenn die Einzel-Effekte wirklich komplex werden — bei 4-6 einfachen Effekten ist ein zentraler `switch` vermutlich klarer und weniger Overhead. Erst einführen, wenn der `switch` unübersichtlich wird, nicht vorab.

**Mögliche Stolperfallen:** Nicht vorschnell überengineeren — bewerte ehrlich, ob der aktuelle Umfang (4 Typen) das Pattern schon rechtfertigt, oder ob ein sauber strukturierter `switch` reicht.

**Fertig wenn:** Ein neuer Power-up-Typ lässt sich mit möglichst wenigen, klar lokalisierten Änderungen hinzufügen.

---

### [ ] Visuelles Feedback für aktive Power-ups (Status-Anzeige)
**Priorität:** 🟠 IMPORTANT
**Aufwand:** S
**Bereich:** Power-ups / UI

**Ziel:** Kleine Icons/Text zeigen an, welche Power-ups gerade aktiv sind und wie viel Restzeit sie haben.

**Hinweise:** Du hast `duration`/`maxDuration` in `PowerUp` schon — ein einfacher Fortschrittsbalken (`Rectangle`, dessen Breite sich mit dem Verhältnis `duration/maxDuration` ändert) wäre ein guter erster Schritt.

**Betroffene Bereiche:** `GameRenderer`.

---

### [ ] Power-up-Kollisionsbox überprüfen/vereinheitlichen
**Priorität:** 🟡 POLISH
**Aufwand:** XS
**Bereich:** Power-ups / Codequalität

**Ziel:** Die Power-up-Größe (`radius`-Parameter im Konstruktor) ist aktuell eine Magic Number beim Aufruf. In eine benannte Konstante auslagern.

---

### [ ] Seltenere, mächtigere Power-ups ("Rare Drop")
**Priorität:** 🟢 OPTIONAL
**Aufwand:** S
**Bereich:** Power-ups / Balancing

**Ziel:** Ein besonders starker, aber seltener Power-up-Typ (z. B. "nächste 3 Treffer zerstören Bricks sofort, egal wie viele Treffer nötig wären").

**Abhängigkeit:** Sinnvoll nach "Mehrschichtige Bricks".

---

### [ ] Gleichzeitige Power-up-Konflikte bewusst durchspielen & dokumentieren
**Priorität:** 🟠 IMPORTANT
**Aufwand:** S
**Bereich:** Power-ups / Testing

**Ziel:** Gezielt testen: Was passiert, wenn `BIGGER_BALL` aktiv ist und währenddessen `BIGGER_PADDLE` eingesammelt wird, dann `BIGGER_BALL` abläuft, dann... (verschiedene Kombinationen)?

**Hinweise:** Schreib dir eine kleine Liste von Testszenarien (siehe auch Bug-Sammlung unten) und geh sie bewusst durch. Notier gefundene Probleme direkt in der Inbox oben.

---

# 🧠 Game Logic

### [ ] Spielzustände als echtes Enum statt verteilter Booleans
**Priorität:** 🟠 IMPORTANT
**Aufwand:** M
**Bereich:** Game Logic / Codequalität
**Lernziel:** State-Machine-Prinzip (wie bei `PowerUpState`) auf Ebene des Gesamtspiels anwenden

**Ziel:** Prüfe, ob `Game` aktuell mehrere Booleans hat, die eigentlich einen gemeinsamen Zustand beschreiben (**Annahme**: etwas wie `idle`, `levelWon`, `lives == 0`, evtl. weitere Flags für Pause o.ä.).

**Warum:** Ihr habt genau dieses Muster schon einmal erfolgreich bei `PowerUpState` angewendet (vier Booleans → ein Enum). Die gleiche Frage lohnt sich auf Ebene des gesamten Spielzustands: Könnte ein `GameState`-Enum (`RUNNING, WAITING_FOR_LAUNCH, LEVEL_COMPLETE, GAME_OVER, PAUSED, ...`) klarer sein als mehrere unabhängige Booleans?

**Hinweise:**
- Zuerst: Liste alle Boolean-Felder in `Game` auf, die den "Gesamtzustand" betreffen (nicht z. B. `sticky`, das ist ein Power-up-Detail, kein Spielzustand).
- Überlege für jede Kombination: Ist sie sinnvoll, oder könnten theoretisch unsinnige Kombinationen entstehen (wie damals bei `isFalling=true, used=true`)?

**Mögliche Stolperfallen:** Nicht alles muss ins Enum — manche Flags (wie `sticky`) gehören zu einem anderen Konzept (Power-up-Zustand) und sollten nicht vermischt werden.

**Fertig wenn:** Der Gesamt-Spielzustand ist an einer Stelle klar benannt auslesbar, ohne mehrere Booleans kombinieren zu müssen.

---

### [ ] Pause-Funktion
**Priorität:** 🟠 IMPORTANT
**Aufwand:** S
**Bereich:** Game Logic / UX

**Ziel:** Eine Taste (z. B. P) pausiert/setzt das Spiel fort.

**Hinweise:**
- `AnimationTimer` hat `start()`/`stop()` — ihr kennt das schon aus Game Over.
- Wichtig: Bei reinem Tastendruck-Toggle (statt Pressed/Released-Flag) darauf achten, dass ein gehaltener Tastendruck nicht 60×/Sekunde hin- und herschaltet — nutzt ihr `setOnKeyPressed` (feuert nur einmal pro Druck) oder eine dauerhaft gesetzte Boolean-Prüfung in der Update-Schleife?

**Mögliche Stolperfallen:** Was passiert mit laufenden Power-up-Timern während der Pause — zählen die während der Pause weiter (vermutlich nicht gewünscht)? Guter Eintrag für die Bug-Sammlung unten.

---

### [ ] Highscore-Speicherung (Datei-I/O)
**Priorität:** 🟠 IMPORTANT
**Aufwand:** M
**Bereich:** Game Logic / Persistenz
**Lernziel:** Datei-I/O (`java.nio.file.Files` oder `FileWriter`/`FileReader`), Exception Handling

**Ziel:** Der beste bisherige Score wird in einer lokalen Datei gespeichert und beim nächsten Start geladen.

**Hinweise:**
- Recherchiere den Unterschied zwischen `java.nio.file.Files` (moderner) und klassischem `FileWriter`/`FileReader`.
- Was passiert, wenn die Datei beim allerersten Start noch nicht existiert? Welche Exception könnte das auslösen, wie fängst du sie ab?
- Wo in `Main`/`Game` müsste der Score beim Game Over verglichen und ggf. neu gespeichert werden?

**Betroffene Bereiche:** Neue kleine Klasse, z. B. `HighscoreManager`, `Main` (Laden beim Start), `Game`/`GameRenderer` (Vergleich und Anzeige).

**Fertig wenn:** Highscore übersteht einen Programmneustart.

---

### [ ] Mehrere Highscores (Top 5 / Top 10)
**Priorität:** 🟢 OPTIONAL
**Aufwand:** M
**Bereich:** Game Logic / Persistenz
**Lernziel:** Sortierte Collections (`TreeSet`, oder `List` + `Collections.sort`)

**Abhängigkeit:** Baut auf "Highscore-Speicherung" auf.

---

### [ ] Level aus Datei laden statt hartcodiert
**Priorität:** 🟡 POLISH
**Aufwand:** L
**Bereich:** Game Logic / Content
**Lernziel:** Datei-Parsing, Trennung von Code und Content

**Ziel:** `LevelConfig`-Werte nicht mehr direkt im Java-Code anlegen (`levels.add(new LevelConfig(...))`), sondern aus einer Textdatei oder JSON-ähnlichem Format einlesen.

**Hinweise:**
- Für den Einstieg reicht ein simples Textformat (z. B. eine Zeile pro Level mit Werten durch Komma getrennt).
- Falls du individuelle Brick-Formen willst (siehe frühere Diskussion zu `boolean[][]`-Layouts): Ein Level als Textzeilen mit `X`/`.`-Mustern ist eine beliebte, gut lesbare Technik.

**Abhängigkeit:** Sinnvoll, falls du "individuelle Level-Formen" (siehe Bricks-Bereich, falls dort ergänzt) weiterverfolgst.

---

### [ ] LevelConfig erweitern (mehr als nur maxCol/maxRow)
**Priorität:** 🟡 POLISH
**Aufwand:** S
**Bereich:** Game Logic / Level-System

**Ziel:** `LevelConfig`-Record um weitere Parameter ergänzen, z. B. Ballgeschwindigkeit, Power-up-Dropchance, Startleben pro Level.

**Hinweise:** Records sind unveränderlich (immutable) — jedes neue Feld bedeutet, dass du alle bestehenden `new LevelConfig(...)`-Aufrufe anpassen musst. Guter, risikoarmer Übungsfall für den Umgang mit Records.

---

### [ ] Gesamt-Game-State nach kompletter Niederlage sauberer behandeln
**Priorität:** 🟡 POLISH
**Aufwand:** S
**Bereich:** Game Logic / Edge Cases

**Ziel:** Überprüfen: Was passiert aktuell, wenn der Spieler nach dem letzten Level verliert (nicht gewinnt) — unterscheidet sich das UI-Feedback sinnvoll vom "Victory nach letztem Level"-Fall?

---

# 🎨 Visuals & Effects

### [ ] Partikeleffekt bei Brick-Zerstörung
**Priorität:** 🟡 POLISH
**Aufwand:** M
**Bereich:** Visuals
**Lernziel:** Kurzlebige Objekte verwalten (Spawn, Update, automatisches Entfernen nach Ablauf)

**Ziel:** Beim Zerstören eines Bricks fliegen kurz ein paar kleine, farbige Partikel auseinander und verblassen.

**Hinweise:**
- Ähnliches Muster wie bei Power-ups: eine `List<Particle>`, jedes Partikel hat Position, Geschwindigkeit, "Lebenszeit".
- Wichtig: Partikel müssen nach Ablauf sauber aus Liste **und** `root.getChildren()` entfernt werden — ihr kennt das Muster schon von Bällen/Power-ups.

**Mögliche Stolperfallen:** Bei vielen gleichzeitigen Partikeln (z. B. durch Kettenreaktion) auf Performance achten.

---

### [ ] Übergangsanimation beim Levelwechsel
**Priorität:** 🟢 OPTIONAL
**Aufwand:** M
**Bereich:** Visuals / UX

**Ziel:** Statt hartem Schnitt ein kurzes Fade-out/Fade-in beim Laden eines neuen Levels.

**Hinweise:** JavaFX bringt `FadeTransition` mit — eine fertige Klasse, die du auf ein beliebiges `Node` anwenden kannst, ohne die Opacity manuell pro Frame zu ändern.

---

### [ ] Bildschirm-Shake bei starkem Treffer/Game Over
**Priorität:** 🟢 OPTIONAL
**Aufwand:** S
**Bereich:** Visuals

**Hinweise:** Kurzes, zufälliges Verschieben der `Scene`/`Pane`-Position über wenige Frames, dann zurück zur Ausgangsposition.

---

### [ ] Dynamische Hintergrundfarbe/Gradient pro Level
**Priorität:** 🟢 OPTIONAL
**Aufwand:** S
**Bereich:** Visuals

---

### [ ] Paddle-/Ball-Farbthemes
**Priorität:** 🔵 EXPERIMENT
**Aufwand:** S
**Bereich:** Visuals / Settings

---

# 🔊 Audio

### [ ] Soundeffekte für Kernereignisse
**Priorität:** 🟠 IMPORTANT
**Aufwand:** M
**Bereich:** Audio
**Lernziel:** `javafx.scene.media.AudioClip`, Maven-Resource-Ordner

**Ziel:** Sound bei Brick-Treffer, Paddle-Treffer, Power-up-Einsammeln, Game Over, Level-Sieg.

**Hinweise:**
- `AudioClip`-Objekte **einmalig** laden (z. B. als Felder in `Game` oder `GameRenderer`), nicht bei jedem Treffer neu von der Festplatte lesen — das wäre unnötig langsam.
- Dateien gehören nach Maven-Konvention in `src/main/resources` (neuer Ordner, analog zu `src/main/java`). Laden über `getClass().getResource(...)`.
- Kostenlose, kurze Effekte z. B. von freesound.org.

**Betroffene Bereiche:** Vermutlich `Game` (weiß, wann was passiert) oder `GameRenderer` — überlege dir eine Begründung, wo Sound-Logik besser aufgehoben ist.

**Fertig wenn:** Mindestens 3 unterschiedliche Ereignisse haben hörbares Feedback.

---

### [ ] Hintergrundmusik mit Lautstärkeregelung
**Priorität:** 🟢 OPTIONAL
**Aufwand:** M
**Bereich:** Audio
**Lernziel:** `MediaPlayer` (für längere, loopende Audiodateien, im Unterschied zu `AudioClip`)

**Abhängigkeit:** Sinnvoll nach "Soundeffekte für Kernereignisse".

---

### [ ] Globale Mute-Taste
**Priorität:** 🟡 POLISH
**Aufwand:** XS
**Bereich:** Audio / UX

---

# 🖥️ UI/UX

### [ ] Hauptmenü vor Spielstart
**Priorität:** 🟠 IMPORTANT
**Aufwand:** L
**Bereich:** UI/UX
**Lernziel:** Mehrere "Screens"/Scenes verwalten

**Ziel:** Statt direktem Spielstart ein einfaches Menü (Start, evtl. Schwierigkeit, evtl. Highscore-Anzeige, Beenden).

**Hinweise:**
- Überlege: Nutzt du eine zweite `Scene`, die du bei `primaryStage.setScene(...)` austauschst, oder blendest du alles innerhalb derselben `Pane` ein/aus?
- Guter Moment, um grundsätzlich über "mehrere Spielzustände, die sich stark unterscheiden" nachzudenken — Bezug zur Aufgabe "Spielzustände als Enum" oben.

**Betroffene Bereiche:** `Main` (deutlich größerer Umbau als bisherige Features).

---

### [ ] Controls-Übersicht im Spiel anzeigbar
**Priorität:** 🟡 POLISH
**Aufwand:** S
**Bereich:** UI/UX

**Ziel:** Eine Taste (z. B. H für "Help") blendet eine Übersicht der Steuerung ein/aus.

---

### [ ] Bessere Game-Over/Victory-Screens (mit Score-Zusammenfassung)
**Priorität:** 🟡 POLISH
**Aufwand:** S
**Bereich:** UI/UX

**Ziel:** Statt nur "You Lost!" z. B. auch finalen Score, erreichtes Level, evtl. Vergleich zum Highscore anzeigen.

**Abhängigkeit:** Sinnvoll nach Highscore-Speicherung.

---

### [ ] Fenstergrößen-Unabhängigkeit / Resize-Verhalten prüfen
**Priorität:** 🟢 OPTIONAL
**Aufwand:** L
**Bereich:** UI/UX
**Lernziel:** Responsive Layout-Berechnung

**Ziel:** Prüfen, was passiert, wenn das Fenster in der Größe verändert wird (aktuell vermutlich fixe Pixelwerte überall) — Bricks, Paddle-Grenzen, Textpositionen correct?

**Mögliche Stolperfallen:** Sehr viele eurer Berechnungen nutzen `scene.getWidth()`/`getHeight()` direkt — bei Resize ändern sich diese Werte, aber bereits erzeugte Objekte (Bricks) behalten ihre ursprüngliche Position. Größerer, nicht trivialer Umbau.

---

# ⚙️ Codequalität

### [ ] God-Class-Check: `Game`
**Priorität:** 🟠 IMPORTANT
**Aufwand:** M
**Bereich:** Codequalität / Architektur
**Lernziel:** Verantwortlichkeiten erkennen und trennen (Single Responsibility Principle)

**Ziel:** Ehrlich bewerten: Wie viele unterschiedliche Verantwortlichkeiten trägt `Game` aktuell (**Annahme**: Ball-Physik-Orchestrierung, Brick-Verwaltung, Power-up-Verwaltung, Leben/Score, Levelstatus)?

**Warum:** Eine Klasse, die "alles" macht, wird mit jedem neuen Feature schwerer zu überblicken — ihr merkt das vermutlich schon an der Länge von `update()`.

**Hinweise:**
- Könnte z. B. die Power-up-Verwaltung (Erzeugen, Kollision, Effekt-Anwendung) in eine eigene Klasse wie `PowerUpManager` wandern, die `Game` nur noch aufruft?
- Nicht zwingend sofort umsetzen — erstmal nur ehrlich einschätzen und aufschreiben, welche Verantwortlichkeiten du siehst.

**Mögliche Stolperfallen:** Übertriebene Aufteilung kann die Nachvollziehbarkeit auch verschlechtern — nicht jede Methode braucht eine eigene Klasse. Ziel ist Klarheit, nicht Zersplitterung um ihrer selbst willen.

**Fertig wenn:** Du hast eine schriftliche, begründete Einschätzung, ob/wo sich eine Aufteilung lohnt — Umsetzung kann ein eigener, späterer Punkt sein.

---

### [ ] Magic Numbers systematisch durchgehen
**Priorität:** 🟡 POLISH
**Aufwand:** S
**Bereich:** Codequalität

**Ziel:** Werte wie Fenstergröße, Brick-Höhe, Power-up-Radius, `maxAngle`, Abstände etc. in benannte Konstanten überführen (`static final`), statt verstreuter Zahlen im Code.

**Hinweise:** Grep/Suche im Projekt nach nackten Zahlen in Methodenaufrufen — guter Weg, um Kandidaten zu finden.

---

### [ ] Javadoc-Konsistenz-Check
**Priorität:** 🟢 OPTIONAL
**Aufwand:** S
**Bereich:** Codequalität / Dokumentation

**Ziel:** Alle öffentlichen Klassen und nicht-trivialen Methoden haben ein kurzes Javadoc — konsistent mit dem, was ihr bei `resolveCollision` schon vorbildlich gemacht habt.

---

### [ ] Konsistente Namensgebung prüfen
**Priorität:** 🟢 OPTIONAL
**Aufwand:** S
**Bereich:** Codequalität

**Ziel:** Durchgehen, ob Namen wie `isLevelWon()` vs. `gameOver()` vs. `hasWon()` (falls mehrere ähnliche Methoden über die Zeit entstanden sind) konsistent und eindeutig benannt sind.

---

### [ ] Konstruktor-Parameterlisten überprüfen
**Priorität:** 🟡 POLISH
**Aufwand:** S
**Bereich:** Codequalität

**Ziel:** Manche Konstruktoren (z. B. `PowerUp`, `Game`) haben recht viele Parameter. Prüfen, ob ein **Builder-Pattern** oder zumindest gruppierte Parameterobjekte (ähnlich `LevelConfig`) die Lesbarkeit verbessern würden.

**Hinweise:** Nicht sofort umbauen — erst bewerten, ob die aktuelle Parameterzahl wirklich unübersichtlich ist oder noch gut lesbar.

---

### [ ] Tote/ungenutzte Methoden und Felder aufspüren
**Priorität:** 🟢 OPTIONAL
**Aufwand:** XS
**Bereich:** Codequalität

**Hinweise:** IntelliJ markiert ungenutzte `private` Methoden/Felder meist automatisch gelb — einmal bewusst durchs Projekt scrollen.

---

# 🧪 Testing & Debugging

### [ ] Erste einfache Unit-Tests für reine Logik-Klassen
**Priorität:** 🟠 IMPORTANT
**Aufwand:** M
**Bereich:** Testing
**Lernziel:** JUnit-Grundlagen

**Ziel:** Für Klassen **ohne** JavaFX-Abhängigkeit (z. B. `GameObject.collidesWith()`, `Ball.hitsOnY()`/`hitsEdge()`) erste automatisierte Tests schreiben.

**Warum:** Genau diese Klassen sind dafür geeignet, weil ihr sie bewusst JavaFX-frei gehalten habt (eure frühe Design-Entscheidung zahlt sich hier aus!).

**Hinweise:**
- JUnit 5 Dependency in die `pom.xml` (ähnlich wie damals JavaFX).
- Einfachster erster Test: Zwei `GameObject`-Instanzen mit bekannten Koordinaten erzeugen, `collidesWith()` aufrufen, erwartetes Ergebnis mit `assertTrue`/`assertFalse` prüfen.
- Teste bewusst auch Grenzfälle: exakt berührende Objekte, keine Überlappung, vollständige Überlappung.

**Betroffene Bereiche:** Neuer Ordner `src/test/java` (Maven-Konvention, analog zu `src/main/java`).

**Fertig wenn:** Mindestens 5–8 Tests für `collidesWith`/`hitsOnY`/`hitsEdge` laufen grün.

---

### [ ] Debug-Modus / Cheat-Tasten für Entwicklung
**Priorität:** 🟡 POLISH
**Aufwand:** S
**Bereich:** Testing & Debugging / Entwicklerwerkzeuge

**Ziel:** Tastenkombination, die z. B. sofort zum nächsten Level springt, unendlich Leben gibt, oder alle Bricks zerstört — nur zum eigenen Testen.

**Hinweise:** Eindeutig als Debug-Feature kennzeichnen (z. B. Kommentar `// DEBUG ONLY`), damit es nicht versehentlich im "fertigen" Spiel aktiv bleibt oder dokumentiere es explizit in der README unter "bekannte Debug-Funktionen".

---

### [ ] Hitbox-Visualisierung (Debug-Overlay)
**Priorität:** 🟢 OPTIONAL
**Aufwand:** S
**Bereich:** Testing & Debugging

**Ziel:** Per Tastendruck umschaltbar: Alle Kollisionsboxen (Ball, Paddle, Bricks) werden als dünne Umrisslinien sichtbar — hilft beim Debuggen von Kollisionsproblemen.

---

### [ ] Bewusstes Durchspielen der Edge-Case-Liste (siehe unten)
**Priorität:** 🔴 CORE
**Aufwand:** M
**Bereich:** Testing & Debugging

**Ziel:** Die Bug-/Edge-Case-Sammlung weiter unten gezielt durchgehen und jeden Punkt einmal bewusst provozieren/testen.

---

# 🚀 Performance

### [ ] Kollisionsprüfung bei vielen Objekten überdenken
**Priorität:** 🟢 OPTIONAL
**Aufwand:** L
**Bereich:** Performance
**Lernziel:** Räumliche Partitionierung (Grundidee), Komplexitätsbetrachtung

**Ziel:** Aktuell prüft vermutlich jeder Ball gegen jeden Brick einzeln (**Annahme**, verschachtelte Schleife) — bei z. B. 10 Bällen × 80 Bricks sind das 800 Prüfungen pro Frame.

**Warum:** Bei eurem aktuellen Spielumfang vermutlich noch kein echtes Problem, aber ein gutes Thema, um die Grundidee von Performance-Überlegungen zu verstehen, bevor es wirklich nötig wird.

**Hinweise:**
- Miss erstmal, ob es überhaupt ein Problem ist (z. B. grob die Frame-Zeit loggen), bevor du optimierst — "premature optimization" vermeiden.
- Falls doch relevant: Räumliches Grid/Partitionierung ist ein eigenes, spannendes Thema (nicht trivial, gut als spätere Lernaufgabe).

**Fertig wenn:** Du hast eine begründete Einschätzung, ob Optimierung hier aktuell überhaupt nötig ist.

---

### [ ] Unnötige Objekterzeugung pro Frame vermeiden
**Priorität:** 🟢 OPTIONAL
**Aufwand:** S
**Bereich:** Performance

**Ziel:** Durchsuchen, ob irgendwo in `update()`-Methoden (die 60×/Sekunde laufen) unnötig neue Objekte erzeugt werden, die auch einmalig erzeugt und wiederverwendet werden könnten.

---

# 🧰 Entwicklerwerkzeuge

### [ ] Logging statt/zusätzlich zu System.out
**Priorität:** 🟢 OPTIONAL
**Aufwand:** S
**Bereich:** Entwicklerwerkzeuge
**Lernziel:** Logging-Frameworks (z. B. java.util.logging oder SLF4J)

**Ziel:** Für Debug-Zwecke ein einfaches Logging statt verstreuter `System.out.println`-Aufrufe.

---

### [ ] Konfigurierbare Startwerte (z. B. via Properties-Datei)
**Priorität:** 🟢 OPTIONAL
**Aufwand:** M
**Bereich:** Entwicklerwerkzeuge / Konfiguration

**Ziel:** Fenstergröße, Startleben, etc. nicht mehr hartcodiert im Java-Code, sondern aus einer einfachen Konfigurationsdatei lesbar.

---

# 📚 Lernen (projektübergreifende Lernziele)

Diese Punkte sind keine eigenständigen Features, sondern bewusste Gelegenheiten, Konzepte zu vertiefen, während du am Projekt arbeitest.

- [ ] **Lernziel: Streams/Lambda vertiefen** — Suche aktiv nach 2–3 Stellen im Code, wo eine klassische Schleife durch einen Stream-Ausdruck ersetzt werden könnte (z. B. Filtern, Zählen, Summieren über Listen von Bricks/Power-ups/Bällen).
- [ ] **Lernziel: Records vertiefen** — Nutze `LevelConfig` als Vorbild für ein zweites, sinnvolles Record in deinem Projekt (z. B. ein `Difficulty`-Record, siehe Gameplay-Bereich).
- [ ] **Lernziel: Enums mit Verhalten** — Java erlaubt, dass Enum-Konstanten eigene Methodenimplementierungen haben. Probiere das an einer kleinen, isolierten Stelle aus (z. B. ob `PowerUpType` eine Methode `defaultColor()` bekommen könnte, die pro Konstante unterschiedlich ist).
- [ ] **Lernziel: Interfaces mit echtem Nutzen** — Bevor du ein Interface einführst (z. B. `Collidable`), formuliere konkret: Welches Problem löst es, das die aktuelle Vererbung nicht löst? Nur einführen, wenn die Antwort klar ist.
- [ ] **Lernziel: Exception Handling** — Spätestens bei Datei-I/O (Highscore) bewusst mit Checked/Unchecked Exceptions auseinandersetzen.
- [ ] **Lernziel: Git-Branching** — Für ein größeres Feature (z. B. Hauptmenü) bewusst einen eigenen Branch nutzen, statt direkt auf `main` zu arbeiten, und am Ende einen sauberen Merge/Pull-Request-Workflow üben (auch solo sinnvoll zu üben).

---

# 🌐 Git & Projektpräsentation

### [ ] README umfassend aktualisieren
**Priorität:** 🟠 IMPORTANT
**Aufwand:** M
**Bereich:** Projektpräsentation

**Ziel:** README um Feature-Liste, Screenshots/GIF, Architekturüberblick (kurz), Steuerung, bekannte Einschränkungen erweitern — über die ursprüngliche Minimal-Version hinaus.

**Hinweise:** Ein kurzes GIF vom Gameplay (z. B. mit einem kostenlosen Screen-Recorder) wirkt auf GitHub deutlich überzeugender als reiner Text.

---

### [ ] Architekturübersicht dokumentieren
**Priorität:** 🟡 POLISH
**Aufwand:** S
**Bereich:** Projektpräsentation / Dokumentation

**Ziel:** Eine kurze schriftliche oder diagrammatische Übersicht: Wie hängen `Main`, `Game`, `GameRenderer`, `InputHandler` und die `GameObject`-Hierarchie zusammen?

**Hinweise:** Muss kein professionelles UML sein — auch eine simple Textbox-Skizze (ähnlich den Klassendiagrammen, die ihr beim Planen schon genutzt habt) ist völlig ausreichend und zeigt Planungsbewusstsein.

---

### [ ] Bekannte Einschränkungen/Bugs dokumentieren
**Priorität:** 🟡 POLISH
**Aufwand:** XS
**Bereich:** Projektpräsentation

**Ziel:** Ehrliche kurze Liste in der README: Was funktioniert (noch) nicht perfekt? Zeigt Reflexionsfähigkeit, wirkt professioneller als Verschweigen.

---

### [ ] Commit-Historie reflektieren
**Priorität:** 🟢 OPTIONAL
**Aufwand:** S
**Bereich:** Projektpräsentation / Git

**Ziel:** Rückblickend schauen: Sind Commit-Nachrichten aussagekräftig? Für zukünftige Commits bewusst auf klare, kurze Beschreibungen achten (z. B. "Fix: Sticky-Paddle-Stacking-Bug" statt "Updates").

---

### [ ] Releases/Versionstags setzen
**Priorität:** 🟢 OPTIONAL
**Aufwand:** XS
**Bereich:** Projektpräsentation / Git

**Ziel:** Bei größeren Meilensteinen (z. B. "Multiball funktioniert") einen Git-Tag setzen (`v0.3-multiball` o. ä.) — übt Versionsdenken.

---

# 💡 Ideen / Experimente

Unausgereifte, spielerische Ideen — nicht notwendig, aber interessant.

- [ ] **Boss-Level:** Ein großer, mehrschichtiger Sonder-Brick als Abschluss eines Levels.
- [ ] **Zeitlupe-Power-up:** Verlangsamt kurzzeitig den Ball (statt Paddle/Ball zu verändern) für mehr Kontrolle.
- [ ] **Zufalls-Level-Generator:** Statt fester `LevelConfig`-Liste zufällig generierte Brick-Muster.
- [ ] **Zwei-Spieler-Modus (lokal, geteiltes Paddle/Tastatur):** Größeres Experiment, würde Input-Handling grundlegend verändern.
- [ ] **Magnet-Paddle:** Fängt den Ball kurz ein, bevor er wieder abprallt — ähnlich zu Sticky, aber automatisch statt manuell ausgelöst.
- [ ] **Gegnerische bewegliche Hindernisse** zusätzlich zu Bricks.
- [ ] **Tägliche Challenge/Seed-basiertes Level** für Wiederspielbarkeit.

---

# 🐛 Bug- & Edge-Case-Sammlung

Eine Sammlung von Szenarien, die bewusst getestet werden sollten. Nicht alle sind zwangsläufig aktuell fehlerhaft — als Checkliste gedacht.

## Ball-Verhalten
- [ ] Ball bewegt sich extrem langsam (z. B. nach ungünstiger Winkel-Berechnung nahe 0) — bleibt er spielbar, oder wirkt er "stecken geblieben"?
- [ ] Ball bewegt sich extrem schnell (z. B. nach mehrfachem `BIGGER_BALL` oder theoretischem Speed-Power-up) — Tunneling-Gefahr (siehe Aufgabe oben)
- [ ] Ball trifft exakt eine Ecke eines Objekts (Grenzfall eurer `hitsEdge()`-Toleranzprüfung)
- [ ] Ball trifft zwei Objekte im selben Frame (z. B. zwei eng benachbarte Bricks) — wird nur eine Kollision behandelt (durch euer `break`), ist das immer korrekt?
- [ ] Ball verlässt das sichtbare Spielfeld seitlich bei extrem hoher Geschwindigkeit, bevor die Wandprüfung greift
- [ ] Ball bleibt zwischen zwei eng beieinanderliegenden Objekten "hängen" (oszilliert hin und her)

## Multiball-spezifisch
- [ ] Alle Bälle fliegen im selben Frame unten raus (bereits einmal gemeinsam durchgerechnet — nochmal nach größeren Änderungen verifizieren)
- [ ] Ein neuer Ball wird exakt an einer Wand/einem Brick erzeugt (durch `ANOTHER_BALL`-Spawn-Position) — kollidiert er sofort im ersten Frame?
- [ ] Sehr viele gleichzeitige Bälle (z. B. 5+) — bleibt Performance/Übersichtlichkeit okay?

## Power-up-spezifisch
- [ ] Zwei verschiedene Power-ups gleichzeitig aktiv (z. B. `BIGGER_BALL` + `BIGGER_PADDLE`) — beeinflussen sie sich gegenseitig unerwünscht?
- [ ] Gleicher Power-up-Typ mehrfach hintereinander eingesammelt, kurz bevor der erste abläuft — korrektes Stacking-Verhalten (ihr habt das bei Sticky bereits gelöst — gilt dieselbe Lösung auch implizit für `BIGGER_PADDLE`/`BIGGER_BALL`, oder könnten die sich anders verhalten?)
- [ ] Power-up fällt exakt auf Paddle-Position, während Paddle sich bewegt — wird die Kollision zuverlässig erkannt?
- [ ] Reset (`R`) während ein Power-up gerade `FALLING` ist (noch nicht eingesammelt)
- [ ] Levelwechsel während ein Power-up-Effekt noch aktiv ist — wird der Effekt korrekt zurückgesetzt, oder "vererbt" er sich unerwünscht ins nächste Level?
- [ ] Power-up fällt an den allerletzten Frames, bevor `levelWon` ausgelöst wird — wird es noch korrekt behandelt oder "verschluckt"?

## Paddle-spezifisch
- [ ] Paddle exakt an der Wand, während `BIGGER_PADDLE` abläuft (Breite schrumpft) — bleibt die Position plausibel, oder "springt" das Paddle?
- [ ] Sehr schnelles, wiederholtes Tastendrücken (Links/Rechts abwechselnd) — bleibt die Bewegung sauber, keine Ruckler?
- [ ] Mehrere Tasten gleichzeitig gedrückt (Links + Rechts gleichzeitig) — alter, bereits diskutierter Fall; nach größeren Umbauten nochmal verifizieren

## Timing/Zustand
- [ ] Spielpause (falls implementiert) während ein Power-up-Timer läuft — zählt die Dauer während der Pause weiter?
- [ ] Reset während `idle == true` (Ball wartet auf Start) — bleibt das Verhalten sinnvoll?
- [ ] Game Over wird genau im selben Frame wie Level-Sieg ausgelöst (z. B. letzter Brick zerstört UND letztes Leben gleichzeitig verloren, falls das überhaupt möglich ist) — welcher Zustand "gewinnt"?

## Technisch/Java-spezifisch
- [ ] `ConcurrentModificationException` nach größeren Umbauten erneut gezielt testen (jede neue Liste, über die iteriert UND verändert wird, ist ein Kandidat)
- [ ] `null`-Zugriffe: Gibt es Stellen, an denen eine Map (`brickPowerUpMap`, `ballMap`, etc.) für einen Key abgefragt wird, der nicht existiert?
- [ ] Leere Listen: Was passiert, wenn `bricks` leer wäre (z. B. `LevelConfig(0, 0)` aus Versehen)? Bricht `allMatch()` auf einer leeren Liste anders als erwartet (Sonderfall: `allMatch` auf leerer Collection liefert `true` — ist euch das bewusst?)

## UI/Darstellung
- [ ] Sehr lange Zahlen bei Score (z. B. 5-stellig) — bleibt die Text-Anzeige lesbar positioniert?
- [ ] Doppelte Shape-Erzeugung nach mehrfachem schnellen Leveltwechsel (R + Enter schnell hintereinander gedrückt)

---

*Letzte Struktur-Überarbeitung: bei Bedarf selbst Datum eintragen, wenn du die Datei pflegst.*