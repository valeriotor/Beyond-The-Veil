package com.valeriotor.beyondtheveil.mixin.client;

import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> extends AgeableListModel<T> {

    @Shadow
    private boolean slim;

    @Inject(method = "setupAnim*", at = @At("RETURN"))
    private void animateModel(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {
        if (entityIn instanceof Player p) {
            if (slim) {
                Animation playerSlimAnimation = ClientData.getInstance().getPlayerAnimation(p.getUUID(), AnimatedModel.playerSlimModel);
                if (playerSlimAnimation != null) {
                    playerSlimAnimation.apply((float) (ageInTicks - Math.floor(ageInTicks)));
                }
            } else {
                Animation playerAnimation = ClientData.getInstance().getPlayerAnimation(p.getUUID(), AnimatedModel.playerModel);
                if (playerAnimation != null) {
                    playerAnimation.apply((float) (ageInTicks - Math.floor(ageInTicks)));
                }
            }
        }
    }
}
