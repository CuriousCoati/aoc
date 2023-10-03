package de.breyer.aoc.y2015;

import java.lang.reflect.InvocationTargetException;

public class EffectSpell extends Spell {

    private final Class<? extends Effect> effectClass;

    public EffectSpell(int manaCost, Class<? extends Effect> effectClass) {
        super(manaCost);
        this.effectClass = effectClass;
    }

    @Override
    public void cast(RpgCharacter caster, RpgCharacter target) {
        super.cast(caster, target);

        try {
            var effect = effectClass.getDeclaredConstructor().newInstance();
            target.addEffect(effect);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("default constructor missing on " + effectClass.getName(), e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canCast(RpgCharacter caster, RpgCharacter target) {
        var enoughMana = super.canCast(caster, target);
        var effectAlreadyApplied = target.hasEffect(effectClass);
        return enoughMana && !effectAlreadyApplied;
    }
}
