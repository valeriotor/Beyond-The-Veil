package com.valeriotor.beyondtheveil.mixin;

import com.valeriotor.beyondtheveil.client.util.MixinUtilsClient;
import com.valeriotor.beyondtheveil.util.MixinUtils;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AgeableMob {


    @Shadow
    private int foodLevel;

    protected VillagerMixin(EntityType<? extends AgeableMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    private int countFoodPointsInInventory() {
        throw new AssertionError();
    }

    @Inject(method = "canBreed", at = @At("RETURN"), cancellable = true)
    public void canBreed(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (MixinUtils.villagerHasPheromones(this)) {
            boolean b = this.foodLevel + this.countFoodPointsInInventory() >= 12 && !this.isSleeping() && this.getAge() >= 0 && this.getAge() <= 5000;
            callbackInfoReturnable.setReturnValue(b);
        }
    }
}
