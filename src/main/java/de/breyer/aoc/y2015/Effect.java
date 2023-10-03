package de.breyer.aoc.y2015;

public abstract class Effect {

    protected int turns;

    public Effect(int turns) {
        this.turns = turns;
    }

    public void apply(RpgCharacter character) {
        turns--;
    }

    public boolean isWornOff() {
        return turns <= 0;
    }

    public abstract Effect copy();
}
