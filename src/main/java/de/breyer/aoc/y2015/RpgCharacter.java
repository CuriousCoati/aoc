package de.breyer.aoc.y2015;

public class RpgCharacter {

    private int hitPoints;
    private final int damage;
    int armor;

    public int getHitPoints() {
        return hitPoints;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmor() {
        return armor;
    }

    public RpgCharacter(int hitPoints, int damage, int armor) {
        this.hitPoints = hitPoints;
        this.damage = damage;
        this.armor = armor;
    }

    public void takeDamage(int damage) {
        hitPoints -= damage;
    }

}
