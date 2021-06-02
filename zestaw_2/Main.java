/*
    Zadania:
        2.1 Ulamek
        2.5 BigLiczba
        2.6 Macierz
*/

class Ulamek {
    int a;
    int b;

    Ulamek(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Zero division error");
        }
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return a + " / " + b;
    }

    double rozwDziesietne() {
        return (double) a / b;
    }

    void odwroc() {
        int tmp = a;
        a = b;
        b = tmp;
    }

    private int nww(int a, int b) {
        if (b > a) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        if (b == 0) {
            return a;
        }
        return nww(b, a % b);
    }

    void skroc() {
        int d = nww(a, b);
        a = a / d;
        b = b / d;
    }

    Ulamek plus(Ulamek ulamek) {
        Ulamek ulamekRet = new Ulamek(ulamek.a*b + a*ulamek.b, b*ulamek.b);
        // ulamekRet.skroc();
        return ulamekRet;
    }

    Ulamek minus(Ulamek ulamek) {
        Ulamek ulamekRet = new Ulamek(a*ulamek.b - ulamek.a*b, b*ulamek.b);
        // ulamekRet.skroc();
        return ulamekRet;
    }

    Ulamek razy(Ulamek ulamek) {
        Ulamek ulamekRet = new Ulamek(a*ulamek.a, b*ulamek.b);
        // ulamekRet.skroc();
        return ulamekRet;
    }
}

public class Main {
    public static void main(String[] args) {
        Ulamek obj, obj2;
        obj = new Ulamek(6, 8);
        System.out.println("obj1: " + obj);
        obj2 = new Ulamek(3, 7);
        System.out.println("obj2: " + obj2);

        double x = obj.rozwDziesietne();
        System.out.println("dziesietne obj: " + x);

        Ulamek obj3 = obj.plus(obj2);
        System.out.println("plus: " + obj3);
        Ulamek obj4 = obj.minus(obj2);
        System.out.println("minus: " + obj4);
        Ulamek obj5 = obj.razy(obj2);
        System.out.println("razy: " + obj5);

        System.out.println("obj1: " + obj);

        obj.odwroc();
        System.out.println("obj1 odwrocony: " + obj);
        obj.skroc();
        System.out.println("obj1 skrocony: " + obj);
    }
}
