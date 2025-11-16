package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.Abomination1Entity;
import com.valeriotor.beyondtheveil.entity.LivingAmmunitionEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class Abomination1Model extends AnimatedModel<LivingEntity> implements VillagerHeadModel{
    // Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "abomination_1"), "main");
    private static final String name = "abomination_1";
    private final ModelPart body;
    private final ModelPart main;
    private final ModelPart bone;
    private final ModelPart bone3;
    private final ModelPart bone4;
    private final ModelPart bone5;
    private final ModelPart bone6;
    private final ModelPart right_arm;
    private final ModelPart right_lower_arm;
    private final ModelPart bone7;
    private final ModelPart left_arm;
    private final ModelPart left_lower_arm;
    private final ModelPart legs;
    private final ModelPart LeftLeg;
    private final ModelPart LeftLowerLeg;
    private final ModelPart RightLeg;
    private final ModelPart RightLowerLeg;

    public Abomination1Model(ModelPart root) {
        super(name);
        this.body = registerAnimatedPart(root, "body");
        this.main = registerAnimatedPart(this.body, "main");
        this.bone = registerAnimatedPart(this.main, "bone");
        this.bone3 = registerAnimatedPart(this.bone, "bone3");
        this.bone4 = registerAnimatedPart(this.bone, "bone4");
        this.bone5 = registerAnimatedPart(this.bone, "bone5");
        this.bone6 = registerAnimatedPart(this.bone, "bone6");
        this.right_arm = registerAnimatedPart(this.bone6, "right_arm");
        this.right_lower_arm = registerAnimatedPart(this.right_arm, "right_lower_arm");
        this.bone7 = registerAnimatedPart(this.bone, "bone7");
        this.left_arm = registerAnimatedPart(this.bone7, "left_arm");
        this.left_lower_arm = registerAnimatedPart(this.left_arm, "left_lower_arm");
        this.legs = registerAnimatedPart(root, "legs");
        this.LeftLeg = registerAnimatedPart(this.legs, "LeftLeg");
        this.LeftLowerLeg = registerAnimatedPart(this.LeftLeg, "LeftLowerLeg");
        this.RightLeg = registerAnimatedPart(this.legs, "RightLeg");
        this.RightLowerLeg = registerAnimatedPart(this.RightLeg, "RightLowerLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition main = body.addOrReplaceChild("main", CubeListBuilder.create().texOffs(26, 26).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition bone = main.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));

        PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.25F, 0.0F, 2.5F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r1 = bone3.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(44, 3).addBox(0.0F, -15.0F, 0.0F, 3.0F, 15.0F, 7.0F, new CubeDeformation(-1.5F)), PartPose.offsetAndRotation(2.0F, 1.0F, 1.25F, -2.6184F, 0.0F, 3.1038F));

        PartDefinition cube_r2 = bone3.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(52, 48).addBox(-2.0F, -15.0F, 0.0F, 5.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5232F, 0.0F, -0.0378F));

        PartDefinition bone4 = bone.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 0.0F, 3.0F, -0.5131F, -0.3463F, 0.538F));

        PartDefinition bone5 = bone.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 0.0F, 3.0F, -0.5131F, 0.3463F, -0.538F));

        PartDefinition bone6 = bone.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(2, 45).mirror().addBox(0.25F, -14.0F, 0.0F, 1.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(41, 7).mirror().addBox(-0.75F, -13.75F, 0.0F, 7.0F, 15.0F, 3.0F, new CubeDeformation(-1.5F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition cube_r3 = bone6.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(41, 7).mirror().addBox(-6.0F, -10.0F, -4.0F, 7.0F, 15.0F, 3.0F, new CubeDeformation(-1.5F)).mirror(false), PartPose.offsetAndRotation(5.25F, -4.0F, 1.0F, 0.0F, -0.2182F, 0.0F));

        PartDefinition cube_r4 = bone6.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(54, 25).mirror().addBox(0.0F, -14.0F, -4.0F, 1.0F, 19.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.0F, -0.2182F, 0.0F));

        PartDefinition right_arm = bone6.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -2.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, -3.0F, 0.0F, -0.3924F, 0.0167F, 0.0403F));

        PartDefinition right_lower_arm = right_arm.addOrReplaceChild("right_lower_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, -1.25F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 7.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition bone7 = bone.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(2, 46).addBox(0.0F, -14.0F, 0.0F, 1.0F, 15.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(41, 7).addBox(-5.5F, -14.0F, -0.5F, 7.0F, 15.0F, 3.0F, new CubeDeformation(-1.5F)), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition cube_r5 = bone7.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(41, 7).addBox(-7.0F, -10.0F, -4.0F, 7.0F, 15.0F, 3.0F, new CubeDeformation(-1.5F)), PartPose.offsetAndRotation(2.25F, -4.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition cube_r6 = bone7.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(54, 25).addBox(-1.0F, -14.0F, -4.0F, 1.0F, 19.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition left_arm = bone7.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-0.75F, -2.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition left_lower_arm = left_arm.addOrReplaceChild("left_lower_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.75F, -1.25F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

        PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition LeftLeg = legs.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-1.0F, 0.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -12.0F, 0.0F, -0.1787F, 0.2148F, -0.0734F));

        PartDefinition LeftLowerLeg = LeftLeg.addOrReplaceChild("LeftLowerLeg", CubeListBuilder.create().texOffs(0, 30).mirror().addBox(-1.0F, -1.0F, -2.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition RightLeg = legs.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -12.0F, 0.0F, 0.1965F, -0.2141F, -0.0074F));

        PartDefinition RightLowerLeg = RightLeg.addOrReplaceChild("RightLowerLeg", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }


    @Override
    public void hatVisible(boolean pVisible) {

    }
}
