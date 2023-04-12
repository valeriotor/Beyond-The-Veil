package com.valeriotor.beyondtheveil.client.model.entity;// Made with Blockbench 4.6.5
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

public class BloodZombieModel extends EntityModel<LivingEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "blood_zombie"), "main");
	private final ModelPart body;
	private final ModelPart legs;

	public BloodZombieModel(ModelPart root) {
		this.body = root.getChild("body");
		this.legs = root.getChild("legs");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, -9.0F, -3.5F, 10.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 1.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(30, 33).addBox(-2.0F, -2.0F, -4.75F, 5.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 5.0F, 1.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 47).addBox(-2.25F, -3.5F, -4.75F, 5.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -5.5F, 1.5F, 0.0F, 0.0F, 0.1745F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -5.0F, -4.0F, 9.0F, 15.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.0F, -5.0F, -4.0F, 9.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 3).addBox(0.0F, 6.0F, -4.0F, 9.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(12, 5).addBox(0.0F, -2.0F, 2.0F, 9.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(25, 7).addBox(6.0F, -2.0F, -4.0F, 3.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 51).addBox(0.0F, -2.0F, -3.75F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -16.0F, -12.25F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 0).addBox(6.0F, -16.0F, -12.25F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 8).addBox(1.0F, -8.0F, -12.25F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(1, 8).addBox(-6.0F, -8.0F, -12.25F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(7, 3).addBox(-1.0F, -13.0F, -12.25F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(7, 3).addBox(-1.0F, -6.0F, -12.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 3).addBox(-2.0F, -13.0F, -12.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(9, 3).addBox(1.0F, -13.0F, -12.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 3).addBox(5.0F, -13.0F, -12.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 7).addBox(5.0F, -9.0F, -12.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(9, 6).addBox(1.0F, -9.0F, -12.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 6).addBox(-2.0F, -9.0F, -12.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(2, 6).addBox(-6.0F, -9.0F, -12.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(2, 2).addBox(-6.0F, -13.0F, -12.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(1, 0).addBox(-6.0F, -16.0F, -12.25F, 12.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 39).addBox(-6.99F, -16.01F, -12.1F, 10.0F, 11.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(80, 39).addBox(-6.99F, -17.01F, -12.1F, 10.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(92, 7).addBox(3.01F, -11.01F, -12.1F, 4.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(106, 27).addBox(3.01F, -16.01F, -12.1F, 4.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(106, 27).addBox(3.01F, -17.01F, -12.1F, 4.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-7.0F, -5.0F, -5.0F, 14.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(45, 0).addBox(-4.0F, -11.0F, -12.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(45, 0).addBox(3.0F, -11.0F, -12.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 1.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(74, 8).addBox(-3.0F, -1.5F, -2.5F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, -1.5F, 0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(124, 0).addBox(0.0086F, -1.6305F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -5.0F, -10.5F, 0.121F, -0.05F, 0.3897F));

		PartDefinition cube_r5 = head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(124, 0).addBox(0.0086F, -0.6305F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -5.0F, -10.5F, 0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r6 = head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(124, 0).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -5.0F, -10.5F, 0.1298F, 0.017F, -0.1298F));

		PartDefinition cube_r7 = head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(124, 0).addBox(1.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -5.0F, -10.5F, 0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r8 = head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(124, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -5.0F, -10.5F, -0.1745F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(64, 27).addBox(-5.99F, -2.0F, -8.0F, 12.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(70, 52).addBox(6.0F, -7.0F, -3.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(70, 52).addBox(-6.0F, -7.0F, -3.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -4.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r9 = jaw.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(124, 0).addBox(2.75F, -4.5F, -6.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r10 = jaw.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(121, 5).addBox(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.8436F, -2.0407F, -3.5F, 0.0F, 0.0F, -0.2182F));

		PartDefinition cube_r11 = jaw.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(121, 5).addBox(-2.0436F, -3.499F, 2.25F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6492F, -1.3678F, -6.25F, 0.0F, 0.0F, 0.0436F));

		PartDefinition cube_r12 = jaw.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(121, 5).addBox(-2.5436F, -3.499F, 0.25F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(121, 5).addBox(-0.5436F, -3.499F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6492F, -1.3678F, -6.25F, 0.3054F, 0.0F, 0.0436F));

		PartDefinition cube_r13 = jaw.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(121, 5).addBox(-1.25F, -4.75F, -6.25F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(124, 4).addBox(2.0F, -4.75F, -7.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition cube_r14 = jaw.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(121, 5).addBox(0.75F, -4.25F, -7.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r15 = jaw.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(121, 0).addBox(4.0F, -5.0F, -7.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 24).addBox(-1.75F, -2.0F, -1.0F, 5.0F, 33.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -1.0F, 5.0F, 33.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-1.0F, -10.0F, 0.0F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(46, 0).mirror().addBox(-3.0F, -2.0F, -1.0F, 7.0F, 36.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 0.0F, 0.0F));

		PartDefinition left_leg2 = legs.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(46, 0).addBox(-4.0F, -2.0F, -1.0F, 7.0F, 36.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}