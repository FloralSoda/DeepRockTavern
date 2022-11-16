package net.hydroxa.drgbeer.mixin;

import net.hydroxa.drgbeer.effect.FartyEffect;
import net.hydroxa.drgbeer.effect.ModEffects;
import net.hydroxa.drgbeer.sound.ModSounds;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "setYaw(F)V",at=@At("HEAD"),cancellable = true)
    public void freezeCameraYaw(CallbackInfo callback) {
        if ((Object)this instanceof ClientPlayerEntity cpe) {
            if (cpe.hasStatusEffect(ModEffects.FREEZE) || cpe.hasStatusEffect(ModEffects.BLACKOUT))
                callback.cancel();
        }
    }
    @Inject(method = "setPitch(F)V",at=@At("HEAD"),cancellable = true)
    public void freezeCameraPitch(CallbackInfo callback) {
        if ((Object) this instanceof ClientPlayerEntity cpe) {
            if (cpe.hasStatusEffect(ModEffects.FREEZE) || cpe.hasStatusEffect(ModEffects.BLACKOUT))
                callback.cancel();
        }
    }

    @Inject(method = "setSneaking(Z)V", at = @At("HEAD"))
    public void fartSneak(boolean setSneak, CallbackInfo cbi) {
        if ((Object)this instanceof ClientPlayerEntity cpe) {
            if (setSneak && cpe.hasStatusEffect(ModEffects.FARTY)) {
                cpe.playSound(ModSounds.FART, 1, cpe.world.random.nextFloat(0.5f, 2f));
                cpe.addVelocity(0, FartyEffect.thrust * FartyEffect.crouchMultiplier, 0);
            }
        }
    }
}
