package de.breyer.aoc.y2015;

public class RechargeEffect extends Effect {

    public RechargeEffect() {
        super(5);
    }

    private RechargeEffect(int turns) {
        super(turns);
    }

    @Override
    public void apply(RpgCharacter character) {
        super.apply(character);
        character.recharge(101);
    }

    @Override
    public Effect copy() {
        return new RechargeEffect(super.turns);
    }
}
