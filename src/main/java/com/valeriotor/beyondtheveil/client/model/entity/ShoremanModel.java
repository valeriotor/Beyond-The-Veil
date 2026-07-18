package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ShoremanModel extends AnimatedModel<ShoremanEntity>{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "shoreman"), "main");
    private static final String name = "shoreman";
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart LeftEye;
    private final ModelPart RightEye;
    private final ModelPart arms;
    private final ModelPart RightLeg;
    private final ModelPart LeftLeg;
    private float partialTicks;

    public ShoremanModel(ModelPart root) {
        super(name);
        this.body = registerAnimatedPart(root, "body");
        this.head = registerAnimatedPart(this.body, "head");
        this.nose = registerAnimatedPart(this.head, "nose");
        this.LeftEye = registerAnimatedPart(this.head, "LeftEye");
        this.RightEye = registerAnimatedPart(this.head, "RightEye");
        this.arms = registerAnimatedPart(this.body, "arms");
        this.RightLeg = registerAnimatedPart(this.body, "RightLeg");
        this.LeftLeg = registerAnimatedPart(this.body, "LeftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(28, 50).addBox(-2.75F, -7.5F, -6.32F, 10.0F, 10.0F, 4.0F, new CubeDeformation(-2.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition LeftEye = head.addOrReplaceChild("LeftEye", CubeListBuilder.create().texOffs(60, 62).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.75F, -3.75F, -3.55F));

        PartDefinition RightEye = head.addOrReplaceChild("RightEye", CubeListBuilder.create().texOffs(60, 62).addBox(-0.25F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -3.75F, -3.55F));

        PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition RightLeg = body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

        PartDefinition LeftLeg = body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    public void setupAnim(ShoremanEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        resetParts();
        float halfRot = pNetHeadYaw * ((float) Math.PI / 180F) / 2;
        this.head.yRot = halfRot;
        if (pEntity.getProfession() != ShoremanEntity.ShoremanProfession.LIGHTHOUSE_KEEPER && pNetHeadYaw != 0) {
            if (pNetHeadYaw < 0) { // left
                this.LeftEye.x = 1.75F - Mth.clamp(Mth.sin(halfRot) * 0.75F * 2, -0.75F / 2, 0);
                this.RightEye.x = -2.0F - Mth.clamp(Mth.sin(halfRot) * 0.25F * 2, -0.25F / 2, 0);
            } else if (pNetHeadYaw > 0) { // right
                this.LeftEye.x = 1.75F - Mth.clamp(Mth.sin(halfRot) * 0.25F * 2, 0, 0.25F / 2);
                this.RightEye.x = -2.0F - Mth.clamp(Mth.sin(halfRot) * 0.75F * 2, 0, 0.75F / 2);
            }
        } else {
            this.LeftEye.x = 1.75F;
            this.RightEye.x = -2.0F;
        }
        this.head.xRot = 0;
        this.head.zRot = 0.0F;

        this.RightLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount * 0.5F;
        this.LeftLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount * 0.5F;
        //this.RightLeg.yRot = 0.0F;
        //this.LeftLeg.yRot = 0.0F;
        //this.head.yRot = 0.5F;
        //this.head.xRot = 0.25F;
        //this.head.zRot = -0.125F;
        //this.LeftEye.x = 1.75F + 0.25F;
        //this.RightEye.x = -2.0F + 0.125F;
        //this.LeftEye.y = -3.75F - 0.25F;
        //this.RightEye.y = -3.75F - 0.25F;
        //this.LeftEye.x = 1.75F - 0.125F;
        //this.RightEye.x = -2.0F - 0.25F;
        //this.LeftEye.y = -3.75F - 0.325F;
        //this.RightEye.y = -3.75F - 0.325F;
        if (pEntity.getVehicle() instanceof BloodCultistEntity) {
            head.zRot = -0.3F;
            head.xRot = -0.05F;
            LeftEye.x = 1.6F;
            RightEye.x = -2.25F;
            LeftEye.y = -4.125F;
            RightEye.y = -4.125F;
            markDirty();
        } else if (pEntity.getPassengers().isEmpty() || !(pEntity.getFirstPassenger() instanceof Player)){
            Animation dialogueAnimation = pEntity.getDialogueAnimation();
            if (dialogueAnimation != null) {
                dialogueAnimation.apply(partialTicks);
            }
            if (pEntity.getFinishDialogueAnimation() != null) {
                pEntity.getFinishDialogueAnimation().apply(partialTicks);
                pEntity.stopDialogueAnimation();
            }

        }

        if (pEntity.getDeathAnimation() != null) {
            pEntity.getDeathAnimation().apply(partialTicks);
        }
        head.xRot += pHeadPitch * ((float) Math.PI / 180F) + Mth.sin(pAgeInTicks * 2 * Mth.PI / 4.5F / 20) * 0.02F;
        body.y = 24 + Mth.cos(pAgeInTicks * 2 * Mth.PI / 4.5F / 20) * 0.04F;
        arms.xRot = -0.7854F + Mth.cos(pAgeInTicks * 2 * Mth.PI / 4.5F / 20) * 0.03F;
    }

    @Override
    public void prepareMobModel(ShoremanEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
        partialTicks = pPartialTick;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
