package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class RpgCharacter {

    @Getter
    private final int damage;
    private final int armor;

    @Getter
    private int hitPoints;
    @Getter
    private int mana;
    @Setter
    private int tmpArmor;

    private final List<Effect> effects = new ArrayList<>();

    public RpgCharacter(int hitPoints, int damage, int armor, int mana) {
        this.hitPoints = hitPoints;
        this.damage = damage;
        this.armor = armor;
        this.mana = mana;
    }

    public RpgCharacter(RpgCharacter character) {
        this.hitPoints = character.hitPoints;
        this.damage = character.damage;
        this.armor = character.armor;
        this.tmpArmor = character.tmpArmor;
        this.mana = character.mana;
        character.effects.stream().map(Effect::copy).forEach(this::addEffect);
    }

    public void takeDamage(int damage) {
        hitPoints -= damage;
    }

    public void castSpell(Spell spell) {
        mana -= spell.getManaCost();
    }

    public void heal(int hitPoints) {
        this.hitPoints += hitPoints;
    }

    public int getArmor() {
        return armor + tmpArmor;
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void applyEffects() {
        tmpArmor = 0;
        List<Effect> wornOffEffects = new ArrayList<>();
        for (Effect effect : effects) {
            effect.apply(this);
            if (effect.isWornOff()) {
                wornOffEffects.add(effect);
            }
        }
        effects.removeAll(wornOffEffects);
    }

    public void recharge(int mana) {
        this.mana += mana;
    }

    public boolean hasEffect(Class<? extends Effect> effectClass) {
        return effects.stream().anyMatch(effect -> effect.getClass() == effectClass);
    }

    public boolean isDead() {
        return hitPoints <= 0;
    }
}
