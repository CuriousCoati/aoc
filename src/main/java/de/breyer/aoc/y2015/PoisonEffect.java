package de.breyer.aoc.y2015;

public class PoisonEffect extends Effect {

    public PoisonEffect() {
        super(6);
    }

    private PoisonEffect(int turns) {
        super(turns);
    }

    @Override
    public void apply(RpgCharacter character) {
        super.apply(character);
        character.takeDamage(3);
    }

    @Override
    public Effect copy() {
        return new PoisonEffect(super.turns);
    }
}
