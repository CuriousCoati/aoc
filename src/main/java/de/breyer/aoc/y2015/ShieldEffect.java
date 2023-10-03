package de.breyer.aoc.y2015;

public class ShieldEffect extends Effect {

    public ShieldEffect() {
        super(6);
    }

    private ShieldEffect(int turns) {
        super(turns);
    }

    @Override
    public void apply(RpgCharacter character) {
        super.apply(character);
        character.setTmpArmor(7);
    }

    @Override
    public Effect copy() {
        return new ShieldEffect(super.turns);
    }
}
