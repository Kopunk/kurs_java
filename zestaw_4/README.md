# Zestaw 4

## Zadania 4.2, 4.4, 4.5

```
javac Main.java
java Main
```

## Zadanie 4.7:

Przykład wykorzystania polimorfizmu i dziedziczenia (i interfejsu) dla określenia typów postaci w grze wideo typu RPG.

```
javac Symulacja.java
java Symulacja
```

Zakładamy 3 klasy postaci: "Warrior", "Archer", "Mage". Interfejs `Fightable` określa reakcje postaci na atak innej klasy postaci. Klasa abstrakcyjna `Soldier` jest 'podstawą' dla zwykłych klas `Warrior`, `Archer`, `Mage`; zawiera metody: konstruktor, `warCry` - zamiast `toString`, `attack`, `defeat`. 

Jak widać dla każdej klasy podane metody zostaly zaimplementowane inaczej, lecz możliwe jest np. utworzenie tablicy typu `Soldier` zawierającej po jednej instancji jej potomków. Przy iteracji po tej tablicy sprawdzenie metody zwraca implementację dla danej klasy potomnej.


