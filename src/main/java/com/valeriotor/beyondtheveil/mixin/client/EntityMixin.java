package com.valeriotor.beyondtheveil.mixin.client;


import com.valeriotor.beyondtheveil.client.util.MixinUtilsClient;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin extends net.minecraftforge.common.capabilities.CapabilityProvider<Entity> {

    protected EntityMixin(Class<Entity> baseClass) {
        super(baseClass);
    }

    @Inject(method = "isInvisible", at = @At("RETURN"), cancellable = true)
    public void isInvisible(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (MixinUtilsClient.checkRevelationRing()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

}
