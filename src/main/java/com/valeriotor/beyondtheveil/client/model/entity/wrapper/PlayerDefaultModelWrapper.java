package com.valeriotor.beyondtheveil.client.model.entity.wrapper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.player.Player;

public class PlayerDefaultModelWrapper extends AnimatedModel<Player> {

    public PlayerDefaultModelWrapper(String name, PlayerModel<?> model) {
        super(name);
        registerAnimatedPart("head", model.head);
        registerAnimatedPart("hat", model.hat);
        registerAnimatedPart("body", model.body);
        registerAnimatedPart("rightArm", model.rightArm);
        registerAnimatedPart("leftArm", model.leftArm);
        registerAnimatedPart("rightLeg", model.rightLeg);
        registerAnimatedPart("leftLeg", model.leftLeg);
    }

    @Override
    public void setupAnim(Player pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {

    }
}
