package com.valeriotor.beyondtheveil.mixin;

import com.valeriotor.beyondtheveil.event.PlayerEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(Entity.class)
public abstract class PlayerMixin extends net.minecraftforge.common.capabilities.CapabilityProvider<Entity> {


    @Shadow
    private EntityDimensions dimensions;
    @Shadow
    private float eyeHeight;

    protected PlayerMixin(Class<Entity> baseClass) {
        super(baseClass);
    }


    @Shadow
    protected void reapplyPosition(){}

    @Inject(method = "refreshDimensions", at = @At("RETURN"))
    private void crawlingPlayerSize(CallbackInfo info) {
        BiConsumer<EntityDimensions, Float> updater = (entityDimensions, eyeHeight) -> {
            this.dimensions = entityDimensions;
            this.eyeHeight = eyeHeight;
            reapplyPosition();
        };
        PlayerEvents.sizeEvent(updater, this);
    }
}
