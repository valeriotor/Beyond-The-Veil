package com.valeriotor.beyondtheveil.client.model.entity;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class TestNautilus extends EntityModel<LivingEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "nautilus"), "main");
	private final ModelPart bb_main;

	public TestNautilus(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(168, 0).addBox(22.0F, -54.0F, -21.0F, 2.0F, 42.0F, 42.0F, new CubeDeformation(0.0F))
		.texOffs(160, 1).addBox(20.0F, -12.0F, -23.0F, 2.0F, 2.0F, 46.0F, new CubeDeformation(0.0F))
		.texOffs(130, 16).addBox(20.0F, -56.0F, -23.0F, 2.0F, 2.0F, 46.0F, new CubeDeformation(0.0F))
		.texOffs(248, 0).addBox(20.0F, -54.0F, 21.0F, 2.0F, 42.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(234, 0).addBox(20.0F, -54.0F, -23.0F, 2.0F, 42.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(152, 0).addBox(18.0F, -10.0F, -25.0F, 2.0F, 2.0F, 50.0F, new CubeDeformation(0.0F))
		.texOffs(126, 12).addBox(18.0F, -58.0F, -25.0F, 2.0F, 2.0F, 50.0F, new CubeDeformation(0.0F))
		.texOffs(204, 0).addBox(18.0F, -56.0F, 23.0F, 2.0F, 46.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(234, 0).addBox(18.0F, -56.0F, -25.0F, 2.0F, 46.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(108, 8).addBox(15.0F, -8.0F, -27.0F, 3.0F, 2.0F, 54.0F, new CubeDeformation(0.0F))
		.texOffs(122, 8).addBox(15.0F, -60.0F, -27.0F, 3.0F, 2.0F, 54.0F, new CubeDeformation(0.0F))
		.texOffs(204, 0).addBox(15.0F, -58.0F, 25.0F, 3.0F, 50.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(234, 0).addBox(15.0F, -58.0F, -27.0F, 3.0F, 50.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(204, 0).addBox(10.0F, -60.0F, 27.0F, 5.0F, 54.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(118, 4).addBox(10.0F, -62.0F, -29.0F, 5.0F, 2.0F, 58.0F, new CubeDeformation(0.0F))
		.texOffs(104, 4).addBox(10.0F, -6.0F, -29.0F, 5.0F, 2.0F, 58.0F, new CubeDeformation(0.0F))
		.texOffs(234, 0).addBox(10.0F, -60.0F, -29.0F, 5.0F, 54.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(204, 0).addBox(1.0F, -62.0F, 29.0F, 9.0F, 58.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(100, 0).addBox(1.0F, -4.0F, -31.0F, 9.0F, 2.0F, 62.0F, new CubeDeformation(0.0F))
		.texOffs(234, 0).addBox(1.0F, -62.0F, -31.0F, 9.0F, 58.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(114, 0).addBox(1.0F, -64.0F, -31.0F, 9.0F, 2.0F, 62.0F, new CubeDeformation(0.0F))
		.texOffs(0, 153).addBox(-1.0F, -2.0F, -33.0F, 2.0F, 2.0F, 66.0F, new CubeDeformation(0.0F))
		.texOffs(0, 99).addBox(-1.0F, -66.0F, -33.0F, 2.0F, 2.0F, 66.0F, new CubeDeformation(0.0F))
		.texOffs(69, 106).addBox(-1.0F, -64.0F, 31.0F, 2.0F, 62.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(227, 117).addBox(-1.0F, -64.0F, -33.0F, 2.0F, 62.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(17, 112).addBox(-54.0F, -3.0F, -32.0F, 53.0F, 2.0F, 64.0F, new CubeDeformation(0.0F))
		.texOffs(17, 112).addBox(-54.0F, -65.0F, -32.0F, 53.0F, 2.0F, 64.0F, new CubeDeformation(0.0F))
		.texOffs(146, 194).mirror().addBox(-54.0F, -63.0F, 30.0F, 53.0F, 60.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(146, 194).addBox(-54.0F, -63.0F, -32.0F, 53.0F, 60.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 127).addBox(-58.0F, -5.0F, -30.0F, 4.0F, 2.0F, 60.0F, new CubeDeformation(0.0F))
		.texOffs(0, 127).addBox(-58.0F, -63.0F, -30.0F, 4.0F, 2.0F, 60.0F, new CubeDeformation(0.0F))
		.texOffs(35, 170).addBox(-58.0F, -61.0F, 28.0F, 4.0F, 56.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 183).addBox(-58.0F, -61.0F, -30.0F, 4.0F, 56.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 131).addBox(-60.0F, -7.0F, -28.0F, 2.0F, 2.0F, 56.0F, new CubeDeformation(0.0F))
		.texOffs(4, 131).addBox(-60.0F, -61.0F, -28.0F, 2.0F, 2.0F, 56.0F, new CubeDeformation(0.0F))
		.texOffs(44, 114).addBox(-60.0F, -59.0F, 26.0F, 2.0F, 52.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(25, 122).addBox(-60.0F, -59.0F, -28.0F, 2.0F, 52.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 152).addBox(-62.0F, -59.0F, -26.0F, 2.0F, 52.0F, 52.0F, new CubeDeformation(0.0F))
		.texOffs(94, 109).addBox(-63.0F, -2.0F, -46.0F, 72.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(105, 100).addBox(-63.0F, 0.0F, -37.0F, 72.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(105, 100).addBox(-63.0F, 0.0F, -48.0F, 72.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(102, 97).addBox(-63.0F, -4.0F, -44.0F, 72.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(102, 97).addBox(-63.0F, 7.0F, -44.0F, 72.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 106).addBox(-65.0F, -1.0F, -45.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 106).addBox(9.0F, -1.0F, -45.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 106).addBox(-65.0F, -1.0F, 38.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 106).addBox(9.0F, -1.0F, 38.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(102, 97).addBox(-63.0F, 7.0F, 39.0F, 72.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(105, 100).addBox(-63.0F, 0.0F, 46.0F, 72.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(105, 100).addBox(-63.0F, 0.0F, 35.0F, 72.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(94, 109).addBox(-63.0F, -2.0F, 37.0F, 72.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(102, 97).addBox(-63.0F, -4.0F, 39.0F, 72.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 130).addBox(-38.5F, -11.0F, -2.0F, 5.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 130).addBox(-2.5F, -11.0F, -2.0F, 5.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -3.0F, -39.0F, -0.9599F, 0.0F, 0.0F));

		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 130).addBox(-38.5F, -11.0F, -2.0F, 5.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 130).addBox(-2.5F, -11.0F, -2.0F, 5.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -3.0F, 38.0F, 0.9599F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}