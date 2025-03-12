package com.valeriotor.beyondtheveil.client.model.entity;

// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class BloodCultistModel extends AnimatedModel<BloodCultistEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "blood_cultist"), "main");
    private static final String name = "blood_cultist";
    private final ModelPart legs;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart body;
    private final ModelPart upper_body;
    private final ModelPart head;
    private final ModelPart left_arm;
    private final ModelPart left_lower_arm;
    private final ModelPart right_arm;
    private final ModelPart right_lower_arm;
    private final ModelPart knife;

    public BloodCultistModel(ModelPart root) {
        super(name);
        this.legs = registerAnimatedPart("legs", root.getChild("legs"));
        this.right_leg = registerAnimatedPart("right_leg", legs.getChild("right_leg"));
        this.left_leg = registerAnimatedPart("left_leg", legs.getChild("left_leg"));
        this.body = registerAnimatedPart("body", root.getChild("body"));
        this.upper_body = registerAnimatedPart("upper_body", body.getChild("upper_body"));
        this.head = registerAnimatedPart("head", upper_body.getChild("head"));
        this.left_arm = registerAnimatedPart("left_arm", upper_body.getChild("left_arm"));
        this.left_lower_arm = registerAnimatedPart("left_lower_arm", left_arm.getChild("left_lower_arm"));
        this.right_arm = registerAnimatedPart("right_arm", upper_body.getChild("right_arm"));
        this.right_lower_arm = registerAnimatedPart("right_lower_arm", right_arm.getChild("right_lower_arm"));
        this.knife = registerAnimatedPart("knife", right_lower_arm.getChild("knife"), false);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create().texOffs(46, 0).addBox(-7.0F, -12.0F, -2.5F, 4.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(-3.0F, -12.0F, -2.5F, 4.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 24.0F, 0.0F));

        PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 1).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -12.0F, 0.0F));

        PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(29, 1).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -12.0F, -1.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 34).addBox(-3.99F, -2.5F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition upper_body = body.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 48).addBox(-5.0F, -13.0F, -2.75F, 10.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(34, 30).addBox(-4.5F, -4.0F, -2.75F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition head = upper_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(39, 51).addBox(-3.5F, -7.0F, -2.75F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(40, 51).addBox(-3.0F, -7.25F, -2.75F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(59, 39).addBox(2.5F, -6.75F, -3.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(45, 55).addBox(-2.5F, -6.75F, -3.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 55).addBox(-1.5F, -6.5F, -3.45F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(45, 55).addBox(-2.5F, -5.75F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(45, 55).addBox(1.5F, -5.75F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(59, 39).mirror().addBox(-3.5F, -6.75F, -3.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 7).addBox(-3.0F, -7.0F, 2.25F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-2.5F, -6.0F, 3.25F, 5.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(-1.5F, -4.0F, 4.25F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, -1.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition left_arm = upper_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(50, 17).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -9.0F, 0.0F, -1.1345F, 0.0F, 0.0F));

        PartDefinition left_lower_arm = left_arm.addOrReplaceChild("left_lower_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-7.05F, -2.0F, -1.5F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 6.0F, 0.0F));

        PartDefinition right_arm = upper_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(36, 17).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -9.0F, 0.0F, -1.1345F, 0.0F, 0.0F));

        PartDefinition right_lower_arm = right_arm.addOrReplaceChild("right_lower_arm", CubeListBuilder.create().texOffs(0, 41).addBox(-0.95F, -2.0F, -1.5F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 6.0F, 0.0F));

        PartDefinition knife = right_lower_arm.addOrReplaceChild("knife", CubeListBuilder.create().texOffs(16, 23).addBox(1.0F, -1.0F, -1.75F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 20).addBox(0.0F, -1.0F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 20).addBox(1.99F, -1.0F, -0.75F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 0.0F, 0.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(BloodCultistEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float MAX = 0.5F;
        this.right_leg.xRot = Mth.clamp(Mth.cos(limbSwing * 0.6662F) * 1F * limbSwingAmount / 0.9F, -MAX, MAX);
        this.left_leg.xRot = Mth.clamp(Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1F * limbSwingAmount / 0.9F, -MAX, MAX);
    }

    @Override
    public void prepareMobModel(BloodCultistEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        resetParts();
        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
        if (pEntity.getHeldVillager() != null) {
            left_arm.xRot = -0.5F;
            left_arm.yRot = 0.5F;
            left_lower_arm.zRot = 2F;
            left_lower_arm.xRot = -1F;
            left_lower_arm.yRot = 4.11F;
            right_arm.xRot = -0.5F;
            right_arm.yRot = -0.5F;
            right_lower_arm.zRot = -2F;
            right_lower_arm.xRot = -1F;
            right_lower_arm.yRot = -4.11F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
