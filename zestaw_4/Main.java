/*
 * Zadania: 
 * 4.2 i 4.3 Prostokąt z wierzchołkiem 
 *  ODP: Konstruktory nie są dziedziczone ale mogą być wywoływane przez podklasy przy pomocy super()
 * 4.4 Klasy abstrakcyjne 
 * 4.5 Interfejsy
 * 4.7 Polimorfizm - W PLIKU Symulacja.java
 */

import java.awt.Rectangle;
import java.awt.Point;

// ===== 4.2 & 4.3 =====
class ProstokatRectangle extends Rectangle {
    ProstokatRectangle(int a, int b) {
        super(a, b);
    }

    /**
     * Zaimplementowany konstruktor z wierzchołkiem i wymiarami
     */
    ProstokatRectangle(Point wierzcholek, int dlugosc, int szerokosc) {
        super(wierzcholek);
        this.setSize(dlugosc, szerokosc);
    }

    void info() {
        System.out.println(this);
    }

    boolean czyPrzylega(ProstokatRectangle p) {
        /**
         * korzystamy z faktu, że zwracany przez intersection Rectangle może mieć ujemne
         * wymiary
         */
        Rectangle r = this.intersection(p);
        if ((r.getWidth() > 0 && r.getHeight() == 0) || (r.getWidth() == 0 && r.getHeight() > 0)) {
            return true;
        }
        return false;
    }
}

// ===== 4.4 =====
abstract class Figura { // nie mozna tworzyc instancji tej klasy
    abstract double pole(); // metoda abstrakcyjna

    abstract double obwod();

    void info() {
        System.out.println(this);
    }
}

class Okrag extends Figura {
    double promien;

    Okrag(double promien) {
        this.promien = promien;
    }

    double pole() {
        return 3.14 * promien * promien;
    }

    double obwod() {
        return 2 * 3.14 * promien;
    }

    public String toString() {
        return "okrag o pr. " + promien;
    }
}

class Prostokat extends Figura {
    double dlugosc;
    double szerokosc;

    Prostokat(double dlugosc, double szerokosc) {
        this.dlugosc = dlugosc;
        this.szerokosc = szerokosc;
    }

    double pole() {
        return dlugosc * szerokosc;
    }

    double obwod() {
        return 2 * dlugosc + 2 * szerokosc;
    }

    public String toString() {
        return "prostokat o wym. " + dlugosc + " na " + szerokosc;
    }
}

class TrojkatRownoboczny extends Figura {
    double podstawa;

    TrojkatRownoboczny(double podstawa) {
        this.podstawa = podstawa;
    }

    double pole() {
        return podstawa * podstawa * 1.732 / 4;
    }

    double obwod() {
        return podstawa * 3;
    }

    public String toString() {
        return "trójkąt rówoboczny o wym. podst. " + podstawa;
    }
}

class Kwadrat extends Prostokat {
    double dlugosc;

    Kwadrat(double dlugosc) {
        super(dlugosc, dlugosc);
        this.dlugosc = dlugosc;
    }

    public String toString() {
        return "kwadrat o wym. boku " + dlugosc;
    }
}

// ===== 4.5 =====
class Osoba {
    String imie;
    String nazwisko;
    int rokUrodzenia;

    Osoba(String imie, String nazwisko, int rokUrodzenia) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.rokUrodzenia = rokUrodzenia;
    }

    public String toString() {
        return "Osoba: " + imie + " " + nazwisko + " rok ur: " + rokUrodzenia;
    }
}

interface Przeszukiwalne {
    boolean czyPasuje(String wzorzec);
}

abstract class Dokument implements Przeszukiwalne {
    Osoba osoba;

    Dokument(Osoba osoba) {
        this.osoba = osoba;
    }

    public boolean czyPasuje(String wzorzec) {
        if (wzorzec.equalsIgnoreCase(osoba.imie) || wzorzec.equalsIgnoreCase(osoba.nazwisko)) {
            return true;
        }
        return false;
    }

    public boolean czyPasuje(int wzorzec) {
        if (wzorzec == osoba.rokUrodzenia) {
            return true;
        }
        return false;
    }
}

class Paszport extends Dokument {
    Paszport(Osoba osoba) {
        super(osoba);
    }

    public String toString() {
        return "Paszport: " + osoba.toString();
    }
}

class DowodOsobisty extends Dokument {
    DowodOsobisty(Osoba osoba) {
        super(osoba);
    }

    public String toString() {
        return "Dowód osobisty: " + osoba.toString();
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("\n===== 4.2 =====");
        ProstokatRectangle a = new ProstokatRectangle(new Point(3, 2), 3, 4);
        a.info();
        System.out.println("\n===== 4.3 =====");
        ProstokatRectangle b = new ProstokatRectangle(3, 2);
        System.out.println(b);
        System.out.println("czy przylega (nie) " + a.czyPrzylega(b));
        b.setLocation(new Point(6, 1));
        System.out.println("czy przylega (tak) " + a.czyPrzylega(b));

        System.out.println("\n===== 4.4 =====");
        Figura zo = new Okrag(2);
        zo.info();

        Figura[] af = { new Prostokat(3, 5), new Okrag(8), new Okrag(3) };

        Figura x;
        double suma = 0;

        for (int i = 0; i < af.length; i++) {
            x = af[i];
            x.info();
            suma = suma + x.pole();
        }

        System.out.println("suma pol figur: " + suma);

        // dodano trójkąt równoboczny i kwadrat
        TrojkatRownoboczny tr = new TrojkatRownoboczny(2.5);
        tr.info();
        System.out.println("pole: " + tr.pole() + "\nobwod: " + tr.obwod());

        Kwadrat kw = new Kwadrat(3.5);
        kw.info();
        System.out.println("pole: " + kw.pole() + "\nobwod: " + kw.obwod());
        System.out.println("szerokość kwadratu: " + kw.szerokosc);

        System.out.println("\n===== 4.5 =====");
        Paszport p1 = new Paszport(new Osoba("Jan", "Nowak", 1994));
        Paszport p2 = new Paszport(new Osoba("Jan", "Duda", 1999));
        DowodOsobisty p3 = new DowodOsobisty(new Osoba("Agnieszka", "Górniak", 2000));
        Paszport p4 = new Paszport(new Osoba("Marian", "Nowak", 2000));
        Dokument[] bazaDanych = { p1, p2, p3, p4 };

        // szukamy "Górniak"
        Dokument z;
        String wzorzec = "Górniak";
        for (int i = 0; i < bazaDanych.length; i++) {
            z = bazaDanych[i];
            if (z.czyPasuje(wzorzec))
                System.out.println("znaleziono: " + z);
        }

        // szukamy Jana
        wzorzec = "Jan";
        for (int i = 0; i < bazaDanych.length; i++) {
            z = bazaDanych[i];
            if (z.czyPasuje(wzorzec))
                System.out.println("znaleziono: " + z);
        }

        // szukamy osób rocznika 2000
        int wzorzecInt = 2000;
        for (int i = 0; i < bazaDanych.length; i++) {
            z = bazaDanych[i];
            if (z.czyPasuje(wzorzecInt))
                System.out.println("znaleziono: " + z);
        }
    }
}
