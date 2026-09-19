package com.valeriotor.beyondtheveil.mixin;

import com.valeriotor.beyondtheveil.util.MixinUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Mob {

    protected CreeperMixin(EntityType<? extends Creeper> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "registerGoals", at = @At("RETURN"))
    private void fearConvalescents(CallbackInfo info) {
        MixinUtils.creeperFearConvalescents(this);
    }

}
