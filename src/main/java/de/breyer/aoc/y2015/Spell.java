package de.breyer.aoc.y2015;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Spell {

    private final int manaCost;

    public int getManaCost() {
        return manaCost;
    }

    public void cast(RpgCharacter caster, RpgCharacter target) {
        caster.castSpell(this);
    }

    public boolean canCast(RpgCharacter caster, RpgCharacter target) {
        return caster.getMana() >= manaCost;
    }
}
