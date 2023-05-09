package com.valeriotor.beyondtheveil.client.model.entity;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BloodWraithModel extends EntityModel<LivingEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "bloodwraithmodel"), "main");
	private final ModelPart head;
	private final ModelPart cloak_top;
	private final ModelPart body;

	public BloodWraithModel(ModelPart root) {
		this.head = root.getChild("head");
		this.cloak_top = root.getChild("cloak_top");
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(0.6667F, -6.125F, -2.5F, 4.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.3333F, -6.125F, 7.5F, 8.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.3333F, -6.125F, -10.5F, 8.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(90, 95).addBox(-3.3333F, -10.625F, -7.5F, 4.0F, 18.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(74, 0).addBox(-15.3333F, -10.875F, -7.5F, 12.0F, 19.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(25, 107).addBox(-12.3333F, 8.125F, -5.5F, 9.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.4167F, -9.125F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.4167F, -9.125F, 3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.4167F, 3.875F, 3.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.4167F, -9.125F, -9.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.4167F, 3.875F, -9.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.0833F, 2.875F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(106, 74).addBox(-17.0F, -12.0F, 8.0F, 11.0F, 21.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(96, 34).addBox(-17.0F, -12.0F, -8.0F, 0.0F, 24.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(90, 74).addBox(-17.0F, -12.0F, -8.0F, 11.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(106, 74).addBox(-17.0F, -12.0F, -8.0F, 11.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3333F, -65.875F, -12.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(94, 76).addBox(-4.5F, 0.0F, -7.0F, 9.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -11.25F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(54, 114).addBox(-0.5F, 0.0F, -5.51F, 7.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(58, 117).addBox(6.25F, -0.01F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(11.25F, -2.0F, -2.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(11.25F, -2.0F, -1.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(11.25F, -2.0F, 1.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(11.25F, -2.0F, 0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 117).addBox(-4.0F, -10.0F, -5.49F, 9.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 117).addBox(-3.99F, -10.0F, 5.49F, 9.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8333F, 13.375F, 0.0F, 0.0F, 0.0F, 0.8727F));

		PartDefinition cube_r2 = jaw.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(7, 7).addBox(-0.5F, -2.0F, -1.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(1.0F, -2.0F, -1.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(3.0F, -2.0F, -0.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(5.0F, -2.0F, -0.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(6.95F, -2.1F, -0.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, 0.25F, -3.5F, 0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r3 = jaw.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(7, 7).addBox(-0.5F, -2.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(1.0F, -2.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(3.0F, -2.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(5.0F, -2.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(6.95F, -2.1F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, 0.25F, 3.25F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cloak_top = partdefinition.addOrReplaceChild("cloak_top", CubeListBuilder.create(), PartPose.offset(-0.1323F, -55.1702F, 4.4441F));

		PartDefinition cube_r4 = cloak_top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 79).addBox(-8.5F, -0.5F, 0.0F, 17.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6323F, 0.6702F, 0.0559F, 0.2182F, 0.0F, 0.0F));

		PartDefinition cloak_left_1 = cloak_top.addOrReplaceChild("cloak_left_1", CubeListBuilder.create().texOffs(2, 79).addBox(-0.5F, -13.5F, 0.0F, 15.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.6323F, 13.362F, 2.8696F, 0.2739F, 0.6357F, 0.1648F));

		PartDefinition cloak_left_2 = cloak_left_1.addOrReplaceChild("cloak_left_2", CubeListBuilder.create(), PartPose.offsetAndRotation(14.0F, 0.0F, 0.0F, 0.1282F, 0.8958F, 0.2059F));

		PartDefinition cube_r5 = cloak_left_2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(2, 79).addBox(-0.5F, -13.5F, 0.0F, 15.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition cloak_left_1_down = cloak_left_1.addOrReplaceChild("cloak_left_1_down", CubeListBuilder.create().texOffs(2, 79).addBox(-7.5F, 0.5F, 0.0F, 15.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 13.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cloak_right_1 = cloak_top.addOrReplaceChild("cloak_right_1", CubeListBuilder.create().texOffs(2, 79).addBox(-15.0F, -13.5F, 0.0F, 15.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.8677F, 13.362F, 2.8696F, 0.2739F, -0.6357F, -0.1648F));

		PartDefinition cloak_right_2 = cloak_right_1.addOrReplaceChild("cloak_right_2", CubeListBuilder.create(), PartPose.offsetAndRotation(-14.0F, 0.0F, 0.0F, 0.1668F, -0.8081F, -0.2006F));

		PartDefinition cube_r6 = cloak_right_2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(2, 79).addBox(-15.0F, -13.5F, 0.0F, 15.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition cloak_right_1_down = cloak_right_1.addOrReplaceChild("cloak_right_1_down", CubeListBuilder.create().texOffs(2, 79).addBox(-7.5F, -0.5F, 0.0F, 15.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 14.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cloak_center_1 = cloak_top.addOrReplaceChild("cloak_center_1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6323F, 25.722F, 5.7135F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r7 = cloak_center_1.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 79).addBox(-9.25F, -13.5F, 0.0F, 17.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 3.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition cloak_center_2 = cloak_center_1.addOrReplaceChild("cloak_center_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 25.0F, 5.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r8 = cloak_center_2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 79).addBox(-9.25F, -13.5F, 0.0F, 17.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 3.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.9074F, -2.6014F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 37.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(6, 1).addBox(-1.5F, 30.0F, -1.75F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(1, 0).addBox(-1.5F, 36.0F, -1.75F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(1, 1).addBox(-1.5F, 24.0F, -1.75F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(2, 0).addBox(-1.5F, 18.0F, -1.75F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(3, 0).addBox(-1.5F, 11.0F, -1.75F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.5F, 5.0F, -1.75F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -50.0926F, -0.3986F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(-0.0161F, -7.0F, 3.7291F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(7.9839F, -7.0F, -6.2709F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(7.9839F, 5.0F, -6.2709F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.0161F, 1.0F, 3.7291F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(7.9839F, 1.0F, -6.2709F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.0161F, -3.0F, 3.7291F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(7.9839F, -3.0F, -6.2709F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.0161F, 5.0F, 3.7291F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0284F, 21.0F, -3.8126F, 0.0133F, 0.1748F, 3.1023F));

		PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 14.0F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(6.0F, 14.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, 10.0F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(6.0F, 10.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, 6.0F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(6.0F, 6.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(6.0F, 2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, 2.0F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 12.0F, 0.0F, 0.0F, 0.3054F, 0.0F));

		PartDefinition clavicle = body.addOrReplaceChild("clavicle", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -1.9974F, -1.0014F, 21.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition left_arm = clavicle.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 0.0F, 0.0F, -0.4363F, 0.0F, -0.3054F));

		PartDefinition left_arm2 = left_arm.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 27.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition right_arm = clavicle.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.3054F));

		PartDefinition right_arm2 = right_arm.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 27.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		alpha = 0.85F;
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		cloak_top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}