package com.valeriotor.beyondtheveil.client.model.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.entity.LivingAmmunitionEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class BrokenBodyModel extends AnimatedModel<LivingAmmunitionEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "brokenbodylayer"), "main");
    private static final String name = "broken_body";
    private final ModelPart lower;
    private final ModelPart upper;
    private final ModelPart upper_visible;
    private final ModelPart head;

    public BrokenBodyModel(ModelPart root) {
        super(name);
        this.lower = registerAnimatedPart("lower", root.getChild("lower"), false);
        this.upper = registerAnimatedPart("upper", root.getChild("upper"), false);
        this.upper_visible = registerAnimatedPart("upper_visible", root.getChild("upper_visible"), true);
        this.head = registerAnimatedPart("head", upper_visible.getChild("head"), true);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition lower = partdefinition.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(16, 38).addBox(-4.0F, -16.0F, -3.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition upper = partdefinition.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition upper_visible = partdefinition.addOrReplaceChild("upper_visible", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = upper_visible.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.5F, -4.25F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.0F, -4.5F, -4.25F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        lower.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        upper.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        upper_visible.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void prepareMobModel(LivingAmmunitionEntity entity, float limbSwing, float limbSwingAmount, float pPartialTick) {
        resetParts();
        float ageInTicks = entity.tickCount + pPartialTick;
        float offset1 = Mth.sin((float) Math.PI * 2 * ageInTicks / (24 * 1.5F)) / 15;
        float offset2 = Mth.sin((float) Math.PI * 2 * ageInTicks / (13 * 1.5F)) / 45;
        float offset3 = Mth.sin((float) Math.PI * 2 * ageInTicks / (17 * 1.5F)) / 45;
        head.zRot = 0.5236F + offset1;
        upper_visible.xRot = offset2;
        upper_visible.zRot = offset3;

        boolean flag = entity.getFallFlyingTicks() > 4;

        float f = 1.0F;
        if (flag) {
            f = (float) entity.getDeltaMovement().lengthSqr();
            f /= 0.2F;
            f *= f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        if(entity.getExplodingAnimation() == null) {
            head.zRot += Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1F * limbSwingAmount / f / 4.5;
            head.xRot += Mth.cos(limbSwing * 0.5662F + 1) * 1F * limbSwingAmount / f / 4.5;
        }


        Animation explodingAnimationBrokenBody = entity.getExplodingAnimationBrokenBody();
        if (explodingAnimationBrokenBody != null) {
            explodingAnimationBrokenBody.apply(pPartialTick);
        }
        //lower.xRot += Mth.PI * 5 / 36;
        //upper.xRot = - Mth.PI * 45 / 36;
        //upper.y -= 20;
        //upper.z += 90;
        //lower.visible = true;
        //upper.visible = true;
    }

    @Override
    public void setupAnim(LivingAmmunitionEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }
}
