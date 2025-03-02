package com.valeriotor.beyondtheveil.client.model.entity;// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.FletumEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FletumModel extends EntityModel<FletumEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "fletum"), "main");
	private final ModelPart head3;
	private final ModelPart head4;
	private final ModelPart blob1;
	private final ModelPart blob2;
	private final ModelPart blob3;
	private final ModelPart blob4;
	private final ModelPart blob5;
	private final ModelPart blob6;
	private final ModelPart blob7;
	private final ModelPart blob8;

	public FletumModel(ModelPart root) {
		this.head3 = root.getChild("head3");
		this.head4 = this.head3.getChild("head4");
		this.blob1 = this.head4.getChild("blob1");
		this.blob2 = this.head4.getChild("blob2");
		this.blob3 = this.head4.getChild("blob3");
		this.blob4 = this.head4.getChild("blob4");
		this.blob5 = this.head4.getChild("blob5");
		this.blob6 = this.head4.getChild("blob6");
		this.blob7 = this.head4.getChild("blob7");
		this.blob8 = this.head4.getChild("blob8");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head3 = partdefinition.addOrReplaceChild("head3", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 1.0F));

		PartDefinition head4 = head3.addOrReplaceChild("head4", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition blob1 = head4.addOrReplaceChild("blob1", CubeListBuilder.create().texOffs(0, 54).addBox(-5.25F, -3.75F, -4.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition blob2 = head4.addOrReplaceChild("blob2", CubeListBuilder.create().texOffs(0, 54).addBox(-5.25F, -3.75F, -4.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -0.25F, 1.0F));

		PartDefinition blob3 = head4.addOrReplaceChild("blob3", CubeListBuilder.create().texOffs(0, 57).addBox(-5.25F, -2.75F, -4.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -1.5F, 4.0F));

		PartDefinition blob4 = head4.addOrReplaceChild("blob4", CubeListBuilder.create().texOffs(0, 57).addBox(-4.25F, -2.75F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.5F, 2.0F));

		PartDefinition blob5 = head4.addOrReplaceChild("blob5", CubeListBuilder.create().texOffs(0, 57).addBox(-4.25F, -2.75F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.5F, -1.0F));

		PartDefinition blob6 = head4.addOrReplaceChild("blob6", CubeListBuilder.create().texOffs(0, 56).addBox(-4.25F, -2.75F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, 3.25F));

		PartDefinition blob7 = head4.addOrReplaceChild("blob7", CubeListBuilder.create().texOffs(0, 56).addBox(-4.25F, -2.75F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, -0.75F, -0.75F));

		PartDefinition blob8 = head4.addOrReplaceChild("blob8", CubeListBuilder.create().texOffs(0, 57).addBox(-4.25F, -2.75F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, -0.75F, 2.25F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void prepareMobModel(FletumEntity entity, float pLimbSwing, float pLimbSwingAmount, float pa) {
		float ageInTicks = entity.tickCount + pa;
		float offset1 = Mth.sin((float) Math.PI * 2 * ageInTicks / (12 * 1.5F)) / 15;
		float offset2 = Mth.sin((float) Math.PI * 2 * ageInTicks / (13 * 1.5F)) / 15;
		float offset3 = Mth.sin((float) Math.PI * 2 * ageInTicks / (14 * 1.5F)) / 15;
		float offset4 = Mth.sin((float) Math.PI * 2 * ageInTicks / (15 * 1.5F)) / 15;
		float offset5 = Mth.sin((float) Math.PI * 2 * ageInTicks / (16 * 1.5F)) / 15;
		float offset6 = Mth.sin((float) Math.PI * 2 * ageInTicks / (17 * 1.5F)) / 15;
		float offset7 = Mth.sin((float) Math.PI * 2 * ageInTicks / (18 * 1.5F)) / 15;
		float offset8 = Mth.sin((float) Math.PI * 2 * ageInTicks / (19 * 1.5F)) / 15;
		float offset9 = Mth.sin((float) Math.PI * 2 * ageInTicks / (20 * 1.5F)) / 15;

		blob1.x = 0.0F + offset1;
		blob2.x = 5.0F - offset2;
		blob3.x = 3.0F + offset3;
		blob4.x = 3.0F - offset4;
		blob5.x = 3.0F + offset5;
		blob6.x = 3.0F - offset6;
		blob7.x = -0.75F + offset7;
		blob8.x = -0.75F - offset8;

		blob1.y = 0.0F + offset3;
		blob2.y = -0.25F - offset4;
		blob3.y = -1.5F + offset5;
		blob4.y = -3.5F - offset6;
		blob5.y = 0.5F + offset7;
		blob6.y = 1.0F - offset8;
		blob7.y = -0.75F + offset9;
		blob8.y = -0.75F - offset1;

		blob1.z = 0.0F + offset2;
		blob2.z = 1.0F - offset3;
		blob3.z = 4.0F + offset4;
		blob4.z = 2.0F - offset5;
		blob5.z = -1.0F + offset6;
		blob6.z = 3.25F - offset7;
		blob7.z = -0.75F + offset8;
		blob8.z = 2.25F - offset9;
	}

	@Override
	public void setupAnim(FletumEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}