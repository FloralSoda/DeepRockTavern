package net.hydroxa.drgbeer.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;

public class ExplosiveEffect extends StatusEffect {
    protected ExplosiveEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public final int EXPLOSION_DELAY_MIN = 20;
    public final int EXPLOSION_DELAY_MAX = 60;
    private int nextExplosion = 1000000000;

    @Override
    public void onApplied(LivingEntity user, AttributeContainer attributes, int amplifier) {
        nextExplosion = user.getStatusEffect(this).getDuration();
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        int duration = entity.getStatusEffect(this).getDuration();
        if (duration <= nextExplosion) {
            if (duration + EXPLOSION_DELAY_MAX >= nextExplosion || nextExplosion == 1000000000) {
                nextExplosion = duration - (entity.world.random.nextInt(EXPLOSION_DELAY_MIN, EXPLOSION_DELAY_MAX) / (amplifier+1));
            }
            Vec3d explosionLoc = new Vec3d(entity.getX() + entity.world.random.nextFloat(-2, 2), entity.getY(), entity.getZ() + entity.world.random.nextFloat(-2, 2));
            entity.world.createExplosion(entity, explosionLoc.x, explosionLoc.y, explosionLoc.z, 1.0f, amplifier > 100 ? Explosion.DestructionType.BREAK : Explosion.DestructionType.NONE);
            Vec3d propulsion = new Vec3d(entity.getX() - explosionLoc.x, 1, entity.getZ() - explosionLoc.z).multiply(0.2);
            entity.addVelocity(propulsion.x, propulsion.y, propulsion.z);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
