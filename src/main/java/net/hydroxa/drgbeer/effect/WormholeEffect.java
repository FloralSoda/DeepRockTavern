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

public class WormholeEffect extends StatusEffect {
    public WormholeEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity user, AttributeContainer attributes, int amplifier) {
        super.onApplied(user, attributes, amplifier);
        var world = user.getWorld();

        double x = user.getX();
        double y = user.getY();
        double z = user.getZ();
        for (int i = 0; i < 16; ++i) {
            double g = x + (user.getRandom().nextDouble() - 0.5) * 16.0;
            double h = MathHelper.clamp(user.getY() + (user.getRandom().nextInt(16) - 8), world.getBottomY(), (world.getBottomY() + ((ServerWorld)world).getLogicalHeight() - 1));
            double j = z + (user.getRandom().nextDouble() - 0.5) * 16.0;
            if (user.hasVehicle()) {
                user.stopRiding();
            }

            if (!user.teleport(g, h, j, true))
                continue;
            SoundEvent soundEvent = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
            world.playSound(null, x, y, z, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
            user.playSound(soundEvent, 1.0f, 1.0f);
            break;
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
