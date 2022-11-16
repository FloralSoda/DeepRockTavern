package net.hydroxa.drgbeer.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.Vec3d;

public class TipsyEffect extends StatusEffect {
    private static final float intensity = 0.0005f;
    protected TipsyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (amplifier >= 40) {
            var duration = entity.getStatusEffect(this).getDuration();
            var radian = Math.PI / (15 * duration);
            double forwardSpeed = entity.world.random.nextFloat(-intensity, intensity) * Math.sin(radian) * amplifier;
            double sideSpeed = entity.world.random.nextFloat(-intensity, intensity) * Math.cos(radian) * amplifier;
            var look = entity.getRotationVector().normalize();
            var side = look.crossProduct(new Vec3d(0, 1, 0));
            look = look.multiply(forwardSpeed);
            side = side.multiply(sideSpeed);
            var nudge = look.add(side);
            entity.addVelocity(nudge.x, 0, nudge.z);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
