package net.hydroxa.drgbeer.effect;

import net.hydroxa.drgbeer.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class NoRainEffect extends StatusEffect {
    public NoRainEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        super.applyInstantEffect(source, attacker, target, amplifier, proximity);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.world.isClient()) {
            ServerWorld sw = Objects.requireNonNull(entity.world.getServer()).getWorld(entity.world.getRegistryKey());
            if (sw != null && sw.isRaining()) {
                int rainDuration = 18000;
                sw.setWeather(sw.random.nextInt(rainDuration, rainDuration * 10) * amplifier, 0, false, false);
            }
        }

        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        int ticksPerFart = 3;
        float fartIntensity = 0.5f;
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        var look = entity.getRotationVector().multiply(-1);
        var fartVector = look.subtract(0,look.y,0).normalize().multiply(fartIntensity).add(entity.world.random.nextDouble(0.1,0.4), 0 ,entity.world.random.nextDouble(0.1,0.4));
        entity.world.addParticle(ParticleTypes.FLAME, x, y + (entity.hasStatusEffect(ModEffects.BLACKOUT) ? 0.5 : 1), z, fartVector.x, entity.world.random.nextFloat(-1f,-0.4f) * (entity.hasStatusEffect(ModEffects.BLACKOUT) ? -1 : 1), fartVector.z);

        if (entity.getStatusEffect(this).getDuration() % ticksPerFart == 0) {
            entity.playSound(ModSounds.FART, 0.6f, entity.world.random.nextFloat(0.8f, 1.5f));
            entity.addVelocity(0,fartIntensity/2f,0);
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
