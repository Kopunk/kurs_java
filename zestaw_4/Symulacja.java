/**
 * Zad 4.7 Dziedziczenie i Polimorfizm
 */

interface Fightable {
    public int attackRangeDefense(int attack);

    public int attackMagicDefense(int attack);

    public int attackCloseDefense(int attack);
}

abstract class Soldier implements Fightable {
    final String clan;
    int health;
    String name;
    private boolean isDefeated = false;

    Soldier(String clan, int health, String name) {
        this.clan = clan;
        this.health = health;
        this.name = name;
    }

    public void warCry() {
        if (isDefeated)
            System.out.println("< " + name + " Defeated >");
        else if (this instanceof Warrior)
            System.out.println("I'm " + name + ". I fight for " + clan + "!");
        else if (this instanceof Mage)
            System.out.println("I'm " + name + ". My magic serves " + clan + "!");
        else if (this instanceof Archer)
            System.out.println("I'm " + name + ". Archer from " + clan + "!");
    }

    public void attack(Soldier enemy) throws Exception {
        if (isDefeated) {
            throw new Exception("Can't attack! " + this.getClass().getName() + " " + name + " is defeated!");
        }
        System.out.println("< " + this.getClass().getName() + " " + name + " attacks " + enemy.getClass().getName()
                + " " + enemy.name + " >");
    }

    void defeat() {
        if (health <= 0) {
            isDefeated = true;
            System.out.println("< " + name + " has been defeated! >");
        }
    }
}

class Warrior extends Soldier {
    final int attackPoints = 50;

    Warrior(String clan, int health, String name) {
        super(clan, health, name);
    }

    public void attack(Soldier enemy) {
        try {
            super.attack(enemy);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        health -= enemy.attackCloseDefense(attackPoints);
        defeat();
    }

    public int attackCloseDefense(int attack) {
        health -= attack;
        System.out.println("< Warrior " + name + " takes " + attack + " damage, fights back for 1 >");
        defeat();
        return 1;
    }

    public int attackRangeDefense(int attack) {
        health -= attack;
        System.out.println("< Warrior " + name + " takes " + attack + " damage, can't fight back range >");
        defeat();
        return 0;
    }

    public int attackMagicDefense(int attack) {
        health = health / 2;
        System.out.println("< Warrior " + name + " gets health halved by magical attack, can't fight back >");
        defeat();
        return 0;
    }
}

class Mage extends Soldier {
    final int attackPoints = 30;

    Mage(String clan, int health, String name) {
        super(clan, health, name);
    }

    public void attack(Soldier enemy) {
        try {
            super.attack(enemy);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        health -= enemy.attackMagicDefense(attackPoints);
        defeat();
    }

    public int attackCloseDefense(int attack) {
        health -= 2 * attack;
        System.out.println("< Mage " + name + " takes " + 2 * attack + " damage, fights back for 10 >");
        defeat();
        return 10;
    }

    public int attackMagicDefense(int attack) {
        health -= attack;
        System.out.println("< Mage " + name + " takes " + attack + " damage, can't fight back magic >");
        defeat();
        return 0;
    }

    public int attackRangeDefense(int attack) {
        health -= (attack / 2);
        System.out.println("< Mage " + name + " takes half of " + attack + " damage, can't fight back range >");
        defeat();
        return 0;
    }
}

class Archer extends Soldier {
    final int attackPoints = 40;

    Archer(String clan, int health, String name) {
        super(clan, health, name);
    }

    public void attack(Soldier enemy) {
        try {
            super.attack(enemy);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        health -= enemy.attackRangeDefense(attackPoints);
        defeat();
    }

    public int attackCloseDefense(int attack) {
        health -= attack;
        System.out.println("< Archer " + name + " takes " + attack + " damage, fights back for 1 >");
        defeat();
        return 1;
    }

    public int attackRangeDefense(int attack) {
        health -= attack;
        System.out.println("< Archer " + name + " takes " + attack + " damage, can't fight back range >");
        defeat();
        return 0;
    }

    public int attackMagicDefense(int attack) {
        health -= attack;
        System.out.println("< Archer " + name + " takes " + attack + " damage, can't fight back magic >");
        defeat();
        return 0;
    }
}

public class Symulacja {
    public static void main(String[] args) throws Exception {
        // proste operacje przy dziedziczeniu i polimorfizmie
        Warrior w1 = new Warrior("Kingdom", 100, "SwordsmanMan");
        w1.warCry();
        Soldier s1 = w1;
        s1.warCry();
        System.out.println(s1.getClass().getName());

        System.out.println("\n=============\n");

        Soldier m1 = new Mage("Islands", 90, "MagicPerson");
        m1.warCry();

        m1.attack(w1);

        w1.attack(m1);

        m1.attack(w1);

        System.out.println("\n=============\n");

        Archer a1 = new Archer("Kingdom", 40, "Sparrow");
        a1.warCry();

        System.out.println("\n=============\n");

        // przykład polimorfizmu
        Soldier[] army = {w1, m1, a1};
        for (Soldier soldier : army) {
            soldier.warCry();
        }
    }
}
