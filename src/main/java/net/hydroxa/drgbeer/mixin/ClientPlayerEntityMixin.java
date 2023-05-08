package net.hydroxa.drgbeer.mixin;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hydroxa.drgbeer.effect.ModEffects;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(value= EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "isSneaking()Z",at = @At("RETURN"), cancellable = true)
    public void forceSneak(CallbackInfoReturnable<Boolean> callback) {
        if (hasStatusEffect(ModEffects.FREEZE) || hasStatusEffect(ModEffects.BLACKOUT))
            callback.setReturnValue(false);
        else if (hasStatusEffect(ModEffects.NO_RAIN))
            callback.setReturnValue(true);
    }

    @Inject(method = "canMoveVoluntarily()Z",at=@At("RETURN"),cancellable = true)
    public void freezeMovement(CallbackInfoReturnable<Boolean> callback) {
        if (hasStatusEffect(ModEffects.FREEZE) || hasStatusEffect(ModEffects.BLACKOUT))
            callback.setReturnValue(false);
    }
}
