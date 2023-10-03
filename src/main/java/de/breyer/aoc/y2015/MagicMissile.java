package de.breyer.aoc.y2015;

public class MagicMissile extends Spell {

    public MagicMissile() {
        super(53);
    }

    @Override
    public void cast(RpgCharacter caster, RpgCharacter target) {
        super.cast(caster, target);
        target.takeDamage(4);
    }
}
