# Zestaw 9: Arkanoid

W pliku `Arkanoid.java` znajduje się rozwiązanie większości zadań z serii Arkanoid.

## Opis działania

Początek i koniec gry:

- po uruchomieniu programu kulka natychmiast rozpoczyna ruch
- przegrana jest możliwa po 5 sekundach od rozpoczęcia gry - wcześniej kulka może odbić się od dolnej krawędzi
- po przegraniu (nieodbiciu kulki) wyświetlany zostaje wynik (ilość zbitych kafelków), po 5 sekundach gra się kończy
- wygrana następuje po zbiciu wszystkich kafelków, wówczas wyświetlana jest informacja o wygranej i gra po 5 sekundach kończy się

Belka:

- podąża w osi x za kursorem
- jest podzielona na 5 równych segmentów:
  - skrajne segmenty odbijają kulkę pod kątem ~26.5 deg na zewnątrz
  - środkowy segment odbija kulkę pionowo w górę
  - pozostałe segmenty odbijają kulkę pod kątem 45 deg na zewnątrz
- odbija kulkę tylko w górę - nie da się odbić kulki 'bokiem' belki i przegrać przez to przegrać

Kafelki:

- na początku gry mają przydzielone indywidualne losowo wybrane kolory
- po zbiciu kafelka dodawany jest punkt
- obijają kulkę z każdej strony

Plansza:

- ścianki lewa i prawa odbijają kulkę odpowiednio tylko w prawo i w lewo - pozwala to uniknąć 'utknięcia' kulki w ściance
- górna ścianka odbija standartowo
- dolna ścianka odbija standardowo, lecz
- po 5 sekundach od rozpoczęcia gry przy dolnej krawędzi planszy pojawia się niewidzialna strefa (`GameOverLine`)
- przy kontakcie z `GameOverLine` gra kończy się przegraną
- tło planszy ładowane jest z pliku `bg.png`
- okno planszy jest nieskalowalne

Użytkowanie:

```
javac Arkanoid.java
java Arkanoid
```

Zapakowanie całości do archiwum JAR (daje możliwość uruchomienia poprzez `java -jar Arkanoid.jar`):

```
javac Arkanoid.java
jar cfe Arkanoid.jar Arkanoid *
```
