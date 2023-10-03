package de.breyer.aoc.y2015;

public class Drain extends Spell {

    public Drain() {
        super(73);
    }

    @Override
    public void cast(RpgCharacter caster, RpgCharacter target) {
        super.cast(caster, target);
        target.takeDamage(2);
        caster.heal(2);
    }
}
