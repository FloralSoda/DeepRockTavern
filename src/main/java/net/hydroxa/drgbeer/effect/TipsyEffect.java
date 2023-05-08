package net.hydroxa.drgbeer.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class TipsyEffect extends StatusEffect {
    private static final float VARIATION_PER_LEVEL = 0.05f;
    public static final int TIME_PER_LEVEL = 480;
    public static final int TICKS_PER_DIRECTION = 10;
    public static final float ANGLE_DEVIATION = (float) Math.toRadians(45);
    public static final int MIN_AMPLIFIER_TIPSY = 40;

    private Vec3d currentDirection = new Vec3d(1, 0, 0);
    private Vec3d tendTowards = new Vec3d(0, 0, 1);

    protected TipsyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity user, AttributeContainer attributes, int amplifier) {
        super.onApplied(user,attributes,amplifier);
        alterDirection(user);
    }

    private void alterDirection(LivingEntity entity) {
        var lookVec = entity.getHorizontalFacing().getUnitVector();
        tendTowards = new Vec3d(lookVec.getZ(), 0, lookVec.getX());
        float theta = entity.world.random.nextFloat(-ANGLE_DEVIATION, ANGLE_DEVIATION);
        tendTowards = tendTowards.rotateY(theta).normalize();
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (amplifier >= MIN_AMPLIFIER_TIPSY) {
            var duration = entity.getStatusEffect(this).getDuration();
            amplifier = (int) Math.ceil((float)duration / TIME_PER_LEVEL) - (MIN_AMPLIFIER_TIPSY - 1);

            if (duration % TICKS_PER_DIRECTION == 0) {
                alterDirection(entity);
            }
            currentDirection = currentDirection.add(tendTowards.multiply(1d / TICKS_PER_DIRECTION)).normalize().multiply(0.02f * amplifier * VARIATION_PER_LEVEL);

            entity.addVelocity(currentDirection.x, 0, currentDirection.z);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
