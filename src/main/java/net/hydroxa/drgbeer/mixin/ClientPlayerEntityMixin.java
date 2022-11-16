package net.hydroxa.drgbeer.mixin;

import com.mojang.authlib.GameProfile;
import net.hydroxa.drgbeer.DRGBeerMod;
import net.hydroxa.drgbeer.effect.ModEffects;
import net.hydroxa.drgbeer.sound.ModSounds;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "isSneaking()Z",at = @At("RETURN"), cancellable = true)
    public void forceSneak(CallbackInfoReturnable<Boolean> callback) {
        ClientPlayerEntity cpe = (ClientPlayerEntity)(Object)this;
        if (cpe.hasStatusEffect(ModEffects.FREEZE) || cpe.hasStatusEffect(ModEffects.BLACKOUT))
            callback.setReturnValue(false);
        else if (cpe.hasStatusEffect(ModEffects.NO_RAIN))
            callback.setReturnValue(true);
    }

    @Inject(method = "canMoveVoluntarily()Z",at=@At("RETURN"),cancellable = true)
    public void freezeMovement(CallbackInfoReturnable<Boolean> callback) {
        ClientPlayerEntity cpe = (ClientPlayerEntity)(Object)this;
        if (cpe.hasStatusEffect(ModEffects.FREEZE) || cpe.hasStatusEffect(ModEffects.BLACKOUT))
            callback.setReturnValue(false);
    }
}
