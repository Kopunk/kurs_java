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
        Ulamek ulamekRet = new Ulamek(ulamek.a * b + a * ulamek.b, b * ulamek.b);
        // ulamekRet.skroc();
        return ulamekRet;
    }

    Ulamek minus(Ulamek ulamek) {
        Ulamek ulamekRet = new Ulamek(a * ulamek.b - ulamek.a * b, b * ulamek.b);
        // ulamekRet.skroc();
        return ulamekRet;
    }

    Ulamek razy(Ulamek ulamek) {
        Ulamek ulamekRet = new Ulamek(a * ulamek.a, b * ulamek.b);
        // ulamekRet.skroc();
        return ulamekRet;
    }
}

class BigLiczba {
    long x;

    BigLiczba(long x) {
        this.x = x;
    }

    boolean czyPodzielna(long b) {
        return x % b == 0;
    }

    boolean czyPodzielna(BigLiczba b) {
        return czyPodzielna(b.x);
    }

    boolean czyPierwsza() {
        long pierwiastek = (long) java.lang.Math.sqrt(x);
        for (long i = 2; i <= pierwiastek; i++) {
            if (czyPodzielna(i)) {
                return false;
            }
        }
        return true;

    }
}

class Macierz {
    int[][] macierz = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };

    Macierz(int[][] macierz) {
        assert macierz.length != 3 : "Only 3x3 matrices accepted";
        for (int i = 0; i < 3; i++) {
            assert macierz[i].length != 3 : "Only 3x3 matrices accepted";
            for (int j = 0; j < 3; j++) {
                this.macierz[i][j] = macierz[i][j];
            }
        }
    }

    Macierz() {
    }

    public String toString() {
        return "" + macierz[0][0] + "\t" + macierz[0][1] + "\t" + macierz[0][2] + "\n"
            + macierz[1][0] + "\t" + macierz[1][1] + "\t" + macierz[1][2] + "\n"
            + macierz[2][0] + "\t" + macierz[2][1] + "\t" + macierz[2][2];
    }

    void set(int xCoord, int yCoord, int value) {
        assert xCoord >= 0 && xCoord <= 3 : "Accepted x coordinates between 0 and 3";
        assert yCoord >= 0 && yCoord <= 3 : "Accepted y coordinates between 0 and 3";

        this.macierz[yCoord][xCoord] = value;
    }

    int wyznacznik() {
        int value = 0;
        for (int i = 0; i < 3; i++) {
            int tmp = 1;
            for (int j = 0; j < 3; j++) {
                tmp *= macierz[j][(j + i) % 3];
            }
            value += tmp;
        }
        for (int i = 0; i < 3; i++) {
            int tmp = 1;
            for (int j = 3; j > 0; --j) {
                tmp *= macierz[j][(j + i) % 3];
            }
            value -= tmp;
        }
        return value;
    }

    void transpozycja() {
        int tmp;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tmp = macierz[i][j];
                macierz[i][j] = macierz[j][i];
                macierz[j][i] = tmp;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Testowanie klasy Ułamek
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

        // Testowanie klasy BigLiczba
        BigLiczba niePierwszaA = new BigLiczba(252);
        BigLiczba niePierwszaB = new BigLiczba(63);
        BigLiczba pierwszaA = new BigLiczba(251);
        BigLiczba pierwszaB = new BigLiczba(6700417);

        System.out.println("false: " + niePierwszaA.czyPierwsza());
        System.out.println("false: " + niePierwszaB.czyPierwsza());
        System.out.println("true: " + niePierwszaA.czyPodzielna(niePierwszaB));
        System.out.println("true: " + pierwszaA.czyPierwsza());
        System.out.println("false: " + pierwszaA.czyPodzielna(niePierwszaB));
        System.out.println("true: " + pierwszaB.czyPierwsza());

        // Testowanie klasy Macierz
        try {
            int[][] mArrErr1 = { { 1, 2, 3 } };
            new Macierz(mArrErr1);
            System.out.println("Failed to catch exception;");
        } catch (Exception e) {
            System.out.println("Pass: " + e);
        }
        try {
            Macierz m = new Macierz();
            m.set(1, 3, 1);
            System.out.println("Failed to catch exception;");
        } catch (Exception e) {
            System.out.println("Pass: " + e);
        }
        int[][] mArr = { { 4, 2, 1 }, { 5, 2, 1 }, { 6, 4, 1 } };
        Macierz m1 = new Macierz(mArr); // wyznacznik -22
        System.out.println(m1);

        System.out.println("Wyznacznik macierzy -22: " + m1.wyznacznik());

    }
}
