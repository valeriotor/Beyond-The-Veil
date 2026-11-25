package com.valeriotor.beyondtheveil.mixin.client;

import com.valeriotor.beyondtheveil.client.util.MixinUtilsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "shouldEntityAppearGlowing", at = @At("RETURN"), cancellable = true)
    public void shouldEntityAppearGlowing(Entity entity, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (MixinUtilsClient.checkRevelationRingGlow(entity)) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }

}
