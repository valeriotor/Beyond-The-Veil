package com.valeriotor.beyondtheveil.client.model.entity;// Made with Blockbench 4.9.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WeeperModel extends AnimatedModel<WeeperEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "weeper"), "main");
	private static final String name = "weeper";
	private final ModelPart body;
	private final ModelPart upper_body_1;
	private final ModelPart upper_body_2;
	private final ModelPart legs;
	private final ModelPart head1;
	private final ModelPart head2;
	private final ModelPart blob1;
	private final ModelPart blob2;
	private final ModelPart blob3;
	private final ModelPart blob4;
	private final ModelPart blob5;
	private final ModelPart blob6;
	private final ModelPart blob7;
	private final ModelPart blob8;
	private final ModelPart blob9;
	private final ModelPart right_arm1;
	private final ModelPart right_arm2;
	private final ModelPart left_arm1;
	private final ModelPart left_arm2;

	public WeeperModel(ModelPart root) {
		super(name);
		this.body = registerAnimatedPart("body", root.getChild("body"));
		this.legs = registerAnimatedPart("legs", root.getChild("legs"));
		this.upper_body_1 = registerAnimatedPart("upper_body_1", body.getChild("upper_body_1"));
		this.upper_body_2 = registerAnimatedPart("upper_body_2", upper_body_1.getChild("upper_body_2"));
		this.head1 = registerAnimatedPart("head1", upper_body_2.getChild("head1"));
		this.head2 = registerAnimatedPart("head2", head1.getChild("head2"));
		this.blob1 = registerAnimatedPart("blob1", head2.getChild("blob1"));
		this.blob2 = registerAnimatedPart("blob2", head2.getChild("blob2"));
		this.blob3 = registerAnimatedPart("blob3", head2.getChild("blob3"));
		this.blob4 = registerAnimatedPart("blob4", head2.getChild("blob4"));
		this.blob5 = registerAnimatedPart("blob5", head2.getChild("blob5"));
		this.blob6 = registerAnimatedPart("blob6", head2.getChild("blob6"));
		this.blob7 = registerAnimatedPart("blob7", head2.getChild("blob7"));
		this.blob8 = registerAnimatedPart("blob8", head2.getChild("blob8"));
		this.blob9 = registerAnimatedPart("blob9", head2.getChild("blob9"));
		this.right_arm1 = registerAnimatedPart("right_arm1", upper_body_2.getChild("right_arm1"));
		this.right_arm2 = registerAnimatedPart("right_arm2", right_arm1.getChild("right_arm2"));
		this.left_arm1 = registerAnimatedPart("left_arm1", upper_body_2.getChild("left_arm1"));
		this.left_arm2 = registerAnimatedPart("left_arm2", left_arm1.getChild("left_arm2"));
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -2.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 13.0F, 2.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition upper_body_1 = body.addOrReplaceChild("upper_body_1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -3.0F, -1.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition upper_body_2 = upper_body_1.addOrReplaceChild("upper_body_2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -7.0F, -2.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube_r1 = upper_body_2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 7).addBox(-2.6F, 1.5F, -4.2F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 5).addBox(-2.6F, 0.0F, -4.2F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 3).addBox(-2.6F, -1.5F, -4.2F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 1).addBox(-2.6F, -3.0F, -4.2F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, 0.0F, 0.0698F, 0.0F));

		PartDefinition cube_r2 = upper_body_2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(3, 7).addBox(0.6F, 1.5F, -4.2F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(3, 5).addBox(0.6F, 0.0F, -4.2F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(3, 3).addBox(0.6F, -1.5F, -4.2F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(3, 1).addBox(0.6F, -3.0F, -4.2F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, 0.0F, -0.0698F, 0.0F));

		PartDefinition head1 = upper_body_2.addOrReplaceChild("head1", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));

		PartDefinition cube_r3 = head1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.25F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition head2 = head1.addOrReplaceChild("head2", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition blob1 = head2.addOrReplaceChild("blob1", CubeListBuilder.create().texOffs(0, 53).addBox(-5.25F, -4.75F, -4.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition blob2 = head2.addOrReplaceChild("blob2", CubeListBuilder.create().texOffs(0, 53).addBox(-5.25F, -4.75F, -4.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -0.25F, 1.0F));

		PartDefinition blob3 = head2.addOrReplaceChild("blob3", CubeListBuilder.create().texOffs(0, 57).addBox(-5.25F, -2.75F, -4.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -1.5F, 4.0F));

		PartDefinition blob4 = head2.addOrReplaceChild("blob4", CubeListBuilder.create().texOffs(0, 57).addBox(-4.25F, -2.75F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -3.5F, 2.0F));

		PartDefinition blob5 = head2.addOrReplaceChild("blob5", CubeListBuilder.create().texOffs(0, 57).addBox(-4.25F, -2.75F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.5F, -1.0F));

		PartDefinition blob6 = head2.addOrReplaceChild("blob6", CubeListBuilder.create().texOffs(0, 56).addBox(-4.25F, -2.75F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, 3.25F));

		PartDefinition blob7 = head2.addOrReplaceChild("blob7", CubeListBuilder.create().texOffs(0, 56).addBox(-4.25F, -2.75F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, -0.75F, -0.75F));

		PartDefinition blob8 = head2.addOrReplaceChild("blob8", CubeListBuilder.create().texOffs(0, 57).addBox(-4.25F, -2.75F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, -0.75F, 2.25F));

		PartDefinition blob9 = head2.addOrReplaceChild("blob9", CubeListBuilder.create().texOffs(0, 57).addBox(-4.25F, -3.5F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.25F, 1.75F));

		PartDefinition right_arm1 = upper_body_2.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.75F, -5.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.51F, 0.0F, -7.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.25F, -0.25F, 0.8727F, 0.0F, 0.0F));

		PartDefinition left_arm1 = upper_body_2.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.75F, -5.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.51F, 0.0F, -7.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.25F, -0.25F, 0.8727F, 0.0F, 0.0F));

		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, 1.5F));

		PartDefinition left_leg1 = legs.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, -2.2309F, -0.5565F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, -0.75F, -0.3927F, 0.0F, 0.0F));

		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(1, 1).addBox(-0.49F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.2691F, -0.0565F, 0.6545F, 0.0F, 0.0F));

		PartDefinition right_leg1 = legs.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, -2.2309F, -0.5565F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -0.75F, -0.3927F, 0.0F, 0.0F));

		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(1, 1).addBox(-0.49F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.2691F, -0.0565F, 0.6545F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(WeeperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float offset1 = Mth.sin((float)Math.PI*2*ageInTicks/(12*1.5F))/15;
		float offset2 = Mth.sin((float)Math.PI*2*ageInTicks/(13*1.5F))/15;
		float offset3 = Mth.sin((float)Math.PI*2*ageInTicks/(14*1.5F))/15;
		float offset4 = Mth.sin((float)Math.PI*2*ageInTicks/(15*1.5F))/15;
		float offset5 = Mth.sin((float)Math.PI*2*ageInTicks/(16*1.5F))/15;
		float offset6 = Mth.sin((float)Math.PI*2*ageInTicks/(17*1.5F))/15;
		float offset7 = Mth.sin((float)Math.PI*2*ageInTicks/(18*1.5F))/15;
		float offset8 = Mth.sin((float)Math.PI*2*ageInTicks/(19*1.5F))/15;
		float offset9 = Mth.sin((float)Math.PI*2*ageInTicks/(20*1.5F))/15;

		blob1.x = 0.0F + offset1;
		blob2.x = 5.0F - offset2;
		blob3.x = 3.0F + offset3;
		blob4.x = 3.0F - offset4;
		blob5.x = 3.0F + offset5;
		blob6.x = 3.0F - offset6;
		blob7.x = -0.75F + offset7;
		blob8.x = -0.75F - offset8;
		blob9.x = 5.0F + offset9;

		blob1.y = 0.0F + offset3;
		blob2.y = -0.25F - offset4;
		blob3.y = -1.5F + offset5;
		blob4.y = -3.5F - offset6;
		blob5.y = 0.5F + offset7;
		blob6.y = 1.0F - offset8;
		blob7.y = -0.75F + offset9;
		blob8.y = -0.75F - offset1;
		blob9.y = 2.25F + offset2;

		blob1.z = 0.0F + offset2;
		blob2.z = 1.0F - offset3;
		blob3.z = 4.0F + offset4;
		blob4.z = 2.0F - offset5;
		blob5.z = -1.0F + offset6;
		blob6.z = 3.25F - offset7;
		blob7.z = -0.75F + offset8;
		blob8.z = 2.25F - offset9;
		blob9.z = 1.75F + offset1;

		//body.xRot = (float) -0.3054F;
		//upper_body_1.xRot = (float) 0.1745F;
		//upper_body_1.yRot = (float) 0.1745F;
		//upper_body_2.xRot = (float) -0.3054F;
		//upper_body_2.yRot = (float) 0.3054F;

		//body.xRot = (float) -0.3054F;
		//upper_body_1.xRot = (float) 0.1745F;
		//upper_body_1.yRot = (float) -0.345F;
		//upper_body_2.xRot = (float) -0.6054F;
		//upper_body_2.yRot = (float) -0.5054F;
		//head1.xRot = (float) 0.5F;

		//body.xRot = (float) -0.3054F;
		//upper_body_1.xRot = (float) 1.1745F;
		//upper_body_1.yRot = (float) 0.1745F;
		//upper_body_2.xRot = (float) -0.3054F;
		//upper_body_2.yRot = (float) 0.3054F;

		//body.xRot = (float) 0;
		//upper_body_1.xRot = (float) 0.1745F;
		//upper_body_1.yRot = (float) 0.5;
		//upper_body_2.xRot = (float) -0.3054F;
		//upper_body_2.yRot = (float) 0.5F;
		//head1.xRot = (float) 0F;


		//right_arm1.xRot = (float) 0;
		//right_arm1.yRot = (float) 0;
		//right_arm1.zRot = (float) 0.5;
		//right_arm2.xRot = (float) 0.8727F;
		//right_arm2.xRot = (float) 1;


		//left_arm1.xRot = (float) 0;
		//left_arm1.yRot = (float) 3;
		//left_arm1.zRot = (float) -0.5;
		//left_arm2.xRot = (float) 0.8727F;
		//left_arm2.xRot = (float) 0;


	}

	@Override
	public void prepareMobModel(WeeperEntity entity, float pLimbSwing, float pLimbSwingAmount, float pa) {
		resetParts();
		Animation explodingAnimation = entity.getExplodingAnimation();
		if (explodingAnimation != null) {
			explodingAnimation.apply(pa);
		};
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}