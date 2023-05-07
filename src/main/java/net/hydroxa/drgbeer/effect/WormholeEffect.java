package net.hydroxa.drgbeer.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.UUID;

public class WormholeEffect extends StatusEffect {
    public WormholeEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public static final double rangeMult = 0.1;
    public static final double defaultRange = 16;

    public HashMap<UUID, Vec3d> warpPositions = new HashMap<>();

    @Override
    public void onApplied(LivingEntity user, AttributeContainer attributes, int amplifier) {
        super.onApplied(user, attributes, amplifier);
        var world = user.getWorld();

        double x = user.getX();
        double y = user.getY();
        double z = user.getZ();

        double warpRange = defaultRange + (rangeMult * amplifier);

        warpPositions.put(user.getUuid(), new Vec3d(x, y, z));

        for (int i = 0; i < warpRange; ++i) {
            double newX = x + (user.getRandom().nextDouble() - 0.5) * warpRange;
            double newY = MathHelper.clamp(user.getY() + (user.getRandom().nextInt(16) - 8), world.getBottomY(), (world.getBottomY() + ((ServerWorld)world).getLogicalHeight() - 1));
            double newZ = z + (user.getRandom().nextDouble() - 0.5) * warpRange;
            if (user.hasVehicle()) {
                user.stopRiding();
            }

            if (!user.teleport(newX, newY, newZ, true))
                continue;
            SoundEvent soundEvent = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
            world.playSound(null, x, y, z, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
            user.playSound(soundEvent, 1.0f, 1.0f);
            break;
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        Vec3d destination = warpPositions.get(entity.getUuid());
        warpPositions.remove(entity.getUuid());

        entity.teleport(destination.x, destination.y, destination.z, true);
        SoundEvent soundEvent = entity instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
        entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
        entity.playSound(soundEvent, 1.0f, 1.0f);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
