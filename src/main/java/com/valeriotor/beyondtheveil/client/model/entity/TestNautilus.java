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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class TestNautilus<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "testnautilus2"), "main");
	private final ModelPart rotate_fix;
	private final ModelPart top;
	private final ModelPart left_side;
	private final ModelPart right_side;
	private final ModelPart bb_main;

	public TestNautilus(ModelPart root) {
		this.rotate_fix = root.getChild("rotate_fix");
		this.top = root.getChild("top");
		this.left_side = root.getChild("left_side");
		this.right_side = root.getChild("right_side");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rotate_fix = partdefinition.addOrReplaceChild("rotate_fix", CubeListBuilder.create().texOffs(303, 308).addBox(9.0F, -4.0F, -31.0F, 9.0F, 2.0F, 62.0F, new CubeDeformation(0.0F))
				.texOffs(197, 403).addBox(9.0F, -5.0F, -30.0F, 9.0F, 1.0F, 60.0F, new CubeDeformation(0.0F))
				.texOffs(203, 415).addBox(18.0F, -5.0F, -24.0F, 12.0F, 1.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(303, 372).addBox(9.0F, -64.0F, -31.0F, 9.0F, 2.0F, 62.0F, new CubeDeformation(0.0F))
				.texOffs(0, 322).addBox(9.0F, -62.0F, 29.0F, 9.0F, 58.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 322).mirror().addBox(9.0F, -62.0F, -31.0F, 9.0F, 58.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(171, 316).addBox(7.0F, -2.0F, -33.0F, 2.0F, 2.0F, 66.0F, new CubeDeformation(0.0F))
				.texOffs(171, 316).addBox(7.0F, -66.0F, -33.0F, 2.0F, 2.0F, 66.0F, new CubeDeformation(0.0F))
				.texOffs(22, 322).addBox(7.0F, -64.0F, 31.0F, 2.0F, 62.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(22, 322).mirror().addBox(7.0F, -64.0F, -33.0F, 2.0F, 62.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 256).mirror().addBox(-46.0F, -3.0F, -32.0F, 53.0F, 2.0F, 64.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 256).mirror().addBox(-46.0F, -65.0F, -32.0F, 53.0F, 2.0F, 64.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(303, 455).addBox(-55.0F, -4.0F, 39.0F, 72.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(295, 444).addBox(-55.0F, -2.0F, 37.0F, 72.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(309, 455).addBox(-55.0F, 0.0F, 35.0F, 72.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(309, 455).addBox(-55.0F, 0.0F, 46.0F, 72.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(303, 455).addBox(-54.0F, 7.0F, 39.0F, 71.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(325, 447).addBox(17.0F, -1.0F, 38.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(325, 447).addBox(-57.0F, -1.0F, 38.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(325, 447).addBox(-57.0F, -1.0F, -45.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(303, 455).addBox(-55.0F, -4.0F, -44.0F, 72.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(295, 444).addBox(-55.0F, -2.0F, -46.0F, 72.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(309, 455).addBox(-55.0F, 0.0F, -37.0F, 72.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(303, 455).addBox(-54.0F, 7.0F, -44.0F, 71.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(309, 455).addBox(-55.0F, 0.0F, -48.0F, 72.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(325, 447).addBox(17.0F, -1.0F, -45.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(304, 160).addBox(12.0F, -11.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(232, 160).addBox(11.0F, -15.0F, -6.0F, 9.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(214, 145).addBox(8.0F, -26.0F, -6.0F, 3.0F, 15.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r1 = rotate_fix.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(276, 446).addBox(-2.5F, -11.0F, -2.0F, 5.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(276, 446).addBox(-38.5F, -11.0F, -2.0F, 5.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.0F, -39.0F, -0.9599F, 0.0F, 0.0F));

		PartDefinition cube_r2 = rotate_fix.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(276, 446).addBox(-38.5F, -11.0F, -2.0F, 5.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(276, 446).addBox(-2.5F, -11.0F, -2.0F, 5.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.0F, 38.0F, 0.9599F, 0.0F, 0.0F));

		PartDefinition front = rotate_fix.addOrReplaceChild("front", CubeListBuilder.create(), PartPose.offset(-0.5F, -3.0F, -39.0F));

		PartDefinition glass = front.addOrReplaceChild("glass", CubeListBuilder.create().texOffs(36, 112).addBox(41.5F, -61.0F, 27.0F, 2.0F, 58.0F, 24.0F, new CubeDeformation(0.0F))
				.texOffs(312, 238).addBox(16.5F, -60.0F, 7.0F, 27.0F, 0.0F, 63.0F, new CubeDeformation(0.0F))
				.texOffs(286, 177).addBox(16.5F, -0.8F, 7.0F, 27.0F, 0.0F, 63.0F, new CubeDeformation(0.0F))
				.texOffs(0, 15).addBox(30.5F, -13.73F, 27.0F, 14.0F, 13.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r3 = glass.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 4).addBox(3.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(1.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.9536F, -13.25F, 15.6924F, 0.3491F, 2.7489F, 0.0F));

		PartDefinition cube_r4 = glass.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 4).addBox(-4.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.11F, -13.25F, 16.9333F, 0.3491F, 2.7489F, 0.0F));

		PartDefinition cube_r5 = glass.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(3.5F, -0.75F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(1.5F, -0.75F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, -0.75F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.6575F, -13.5F, 21.2357F, 0.3491F, 2.7489F, 0.0F));

		PartDefinition cube_r6 = glass.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 79).addBox(-8.0F, -6.5F, -8.0F, 16.0F, 13.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.2606F, -7.25F, 18.4852F, 0.0F, 2.7489F, 0.0F));

		PartDefinition cube_r7 = glass.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 57).addBox(-4.0F, -12.51F, -2.3F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 52).addBox(-8.0F, -6.51F, -7.3F, 16.0F, 13.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(33.6399F, -7.23F, 24.733F, 0.0F, 2.2689F, 0.0F));

		PartDefinition cube_r8 = glass.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(31, 461).addBox(2.0F, -5.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(17, 463).addBox(2.5F, -2.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(31, 461).addBox(-3.0F, -5.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(17, 463).addBox(-2.5F, -2.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(33.2252F, -13.24F, 50.9609F, -0.2618F, 0.8727F, 0.0F));

		PartDefinition cube_r9 = glass.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 456).addBox(0.51F, -37.99F, -12.3F, 16.0F, 13.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(242, 340).addBox(0.5F, -85.21F, -28.0F, 1.0F, 1.0F, 29.0F, new CubeDeformation(0.0F))
				.texOffs(0, 134).addBox(0.5F, -85.25F, -1.0F, 16.0F, 58.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(32.0F, 24.25F, 63.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition cube_r10 = glass.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 486).addBox(5.49F, -38.5F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 486).addBox(7.49F, -38.5F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 488).addBox(3.49F, -38.5F, -7.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 484).addBox(3.49F, -38.5F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 484).addBox(3.49F, -38.5F, -5.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 484).addBox(7.49F, -38.5F, -5.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 488).addBox(5.49F, -38.5F, -5.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 488).addBox(5.49F, -38.5F, -7.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 486).addBox(7.49F, -38.5F, -7.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 483).addBox(0.49F, -38.0F, -14.75F, 16.0F, 13.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 134).addBox(0.5F, -85.25F, -1.0F, 16.0F, 58.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 24.25F, 69.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r11 = glass.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(242, 340).addBox(-0.1567F, -0.5F, -2.4633F, 1.0F, 1.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(40.943F, -60.4F, 27.9343F, 0.0F, -1.1345F, 0.0F));

		PartDefinition cube_r12 = glass.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(242, 340).addBox(0.5F, -29.9F, -1.0F, 1.0F, 1.0F, 29.0F, new CubeDeformation(0.0F))
				.texOffs(0, 134).addBox(0.5F, -30.0F, -1.0F, 16.0F, 58.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(32.0F, -31.0F, 15.0F, 0.0F, -0.8727F, 0.0F));

		PartDefinition cube_r13 = glass.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 134).addBox(0.5F, -31.0F, -1.0F, 16.0F, 58.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -30.0F, 9.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition cube_r14 = glass.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(242, 340).addBox(-0.1567F, -0.5F, -26.5367F, 1.0F, 1.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(40.943F, -60.46F, 50.0657F, 0.0F, 1.1345F, 0.0F));

		PartDefinition back = rotate_fix.addOrReplaceChild("back", CubeListBuilder.create().texOffs(404, 112).addBox(-4.1111F, -26.0F, -26.0F, 2.0F, 52.0F, 52.0F, new CubeDeformation(0.0F))
				.texOffs(396, 0).addBox(-2.1111F, -28.0F, -28.0F, 2.0F, 56.0F, 56.0F, new CubeDeformation(0.0F))
				.texOffs(30, 380).mirror().addBox(-0.1111F, -28.0F, -30.0F, 4.0F, 56.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(42, 384).addBox(-0.1111F, 28.0F, -30.0F, 4.0F, 2.0F, 60.0F, new CubeDeformation(0.0F))
				.texOffs(30, 322).mirror().addBox(-0.1111F, -28.0F, 28.0F, 4.0F, 56.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(42, 322).addBox(-0.1111F, -30.0F, -30.0F, 4.0F, 2.0F, 60.0F, new CubeDeformation(0.0F))
				.texOffs(430, 216).addBox(-5.0F, -21.0F, -20.0F, 1.0F, 40.0F, 40.0F, new CubeDeformation(0.0F))
				.texOffs(438, 296).addBox(-6.0F, -19.0F, -18.0F, 1.0F, 36.0F, 36.0F, new CubeDeformation(0.0F))
				.texOffs(446, 368).addBox(-7.0F, -17.0F, -16.0F, 1.0F, 32.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(458, 432).addBox(-8.0F, -14.0F, -13.0F, 1.0F, 26.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(79, 476).addBox(-11.0F, -10.0F, -9.0F, 3.0F, 18.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(123, 484).addBox(-15.0F, -8.0F, -7.0F, 4.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-49.8889F, -33.0F, 0.0F));

		PartDefinition propeller = back.addOrReplaceChild("propeller", CubeListBuilder.create().texOffs(160, 476).addBox(1.0F, -9.0F, -9.0F, 3.0F, 18.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(281, 492).addBox(-4.0F, -9.0F, 7.0F, 5.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(281, 492).addBox(-4.0F, -9.0F, -9.0F, 5.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(244, 486).addBox(-3.0F, -4.0F, -4.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(244, 486).addBox(-3.0F, -4.0F, 2.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(203, 496).addBox(-4.0F, -9.0F, -7.0F, 5.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(242, 496).addBox(-4.0F, 7.0F, -7.0F, 5.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(240, 490).addBox(-3.0F, -4.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(240, 490).addBox(-3.0F, 2.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(296, 494).addBox(-4.0F, -26.0F, 6.0F, 7.0F, 17.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(296, 494).addBox(-4.0F, 9.0F, -7.0F, 7.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-19.0F, -1.0F, 0.0F));

		PartDefinition cube_r15 = propeller.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(313, 477).mirror().addBox(2.0F, -16.0F, -18.0F, 1.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.0F, 10.0F, -1.5184F, 0.0F, 0.0F));

		PartDefinition cube_r16 = propeller.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(313, 477).addBox(2.0F, -1.0F, 0.0F, 1.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -10.0F, -1.5184F, 0.0F, 0.0F));

		PartDefinition cube_r17 = propeller.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(296, 494).addBox(-4.0F, 9.0F, -7.0F, 7.0F, 17.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(296, 494).addBox(-4.0F, -26.0F, 6.0F, 7.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r18 = propeller.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(313, 477).mirror().addBox(2.0F, -16.0F, -18.0F, 1.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -10.0F, 7.0F, 0.0524F, 0.0F, 0.0F));

		PartDefinition cube_r19 = propeller.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(313, 477).addBox(2.0F, -1.0F, 0.0F, 1.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, -7.0F, 0.0524F, 0.0F, 0.0F));

		PartDefinition left_propeller = rotate_fix.addOrReplaceChild("left_propeller", CubeListBuilder.create().texOffs(438, 489).addBox(-36.0F, -37.0F, 35.0F, 26.0F, 12.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(368, 476).addBox(-43.0F, -37.0F, 35.0F, 7.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(368, 476).addBox(-43.0F, -26.0F, 35.0F, 7.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(404, 477).addBox(-43.0F, -36.0F, 45.0F, 7.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(63, 63).addBox(-41.0F, -32.0F, 39.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 3.0F, -1.0F));

		PartDefinition left_propeller_texture = left_propeller.addOrReplaceChild("left_propeller_texture", CubeListBuilder.create().texOffs(0, 377).addBox(0.0F, -4.5F, -4.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-40.0F, -31.5F, 40.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r20 = left_propeller_texture.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 377).addBox(0.0F, -4.5F, -4.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.0944F, 0.0F, 0.0F));

		PartDefinition cube_r21 = left_propeller_texture.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 377).addBox(0.0F, -4.5F, -4.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.0944F, 0.0F, 0.0F));

		PartDefinition right_propeller = rotate_fix.addOrReplaceChild("right_propeller", CubeListBuilder.create().texOffs(364, 489).mirror().addBox(0.0F, -5.9F, -4.6F, 26.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(368, 464).mirror().addBox(-7.0F, -5.9F, -4.6F, 7.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(368, 464).mirror().addBox(-7.0F, 5.1F, -4.6F, 7.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(404, 477).mirror().addBox(-7.0F, -4.9F, -4.6F, 7.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(63, 63).addBox(-5.0F, -0.9F, 0.9F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-28.0F, -28.1F, -40.4F));

		PartDefinition right_propeller_texture = right_propeller.addOrReplaceChild("right_propeller_texture", CubeListBuilder.create().texOffs(0, 377).addBox(0.0F, -4.5F, -4.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -0.4F, 1.4F, 0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r22 = right_propeller_texture.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 377).addBox(0.0F, -4.5F, -4.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.0944F, 0.0F, 0.0F));

		PartDefinition cube_r23 = right_propeller_texture.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 377).addBox(0.0F, -4.5F, -4.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.0944F, 0.0F, 0.0F));

		PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(150, 8).addBox(-20.0F, -75.0F, 11.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(132, 8).addBox(0.0F, -75.0F, 11.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(132, 17).addBox(0.0F, -75.0F, 22.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(150, 17).addBox(-20.0F, -75.0F, 22.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(154, 0).mirror().addBox(-20.0F, -75.0F, 9.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(154, 4).mirror().addBox(-20.0F, -75.0F, 29.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(132, 4).addBox(-7.0F, -75.0F, 29.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(132, 0).mirror().addBox(-7.0F, -75.0F, 9.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(176, 0).addBox(-11.0F, -75.0F, 9.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(176, 9).addBox(-11.0F, -75.0F, 24.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(53, 0).addBox(-22.0F, -67.0F, 7.0F, 26.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(77, 28).addBox(-20.0F, -69.0F, 9.0F, 22.0F, 2.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(61, 53).addBox(-19.0F, -72.0F, 10.0F, 20.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(198, 20).addBox(-13.0F, -75.0F, 16.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(201, 21).addBox(-12.0F, -77.0F, 17.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, 0.0F));

		PartDefinition cube_r24 = top.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(176, 27).addBox(-2.5F, -1.0F, -17.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(176, 18).addBox(-2.5F, -1.0F, -2.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -74.0F, 20.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition left_side = partdefinition.addOrReplaceChild("left_side", CubeListBuilder.create().texOffs(146, 143).addBox(-2.0F, -30.0F, -26.5F, 2.0F, 60.0F, 53.0F, new CubeDeformation(0.0F))
				.texOffs(146, 37).addBox(0.0F, -26.0F, -26.5F, 2.0F, 52.0F, 53.0F, new CubeDeformation(0.0F))
				.texOffs(238, 0).addBox(2.0F, -15.0F, 15.0F, 2.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(246, 16).addBox(2.0F, -25.0F, 10.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, -9.0F, 19.5F));

		PartDefinition right_side = partdefinition.addOrReplaceChild("right_side", CubeListBuilder.create().texOffs(146, 143).mirror().addBox(-2.0F, -30.0F, -26.5F, 2.0F, 60.0F, 53.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(146, 37).mirror().addBox(-4.0F, -26.0F, -26.5F, 2.0F, 52.0F, 53.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-30.0F, -9.0F, 19.5F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(272, 114).addBox(-30.0F, -63.0F, 9.0F, 60.0F, 60.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(336, 29).addBox(-30.0F, -63.0F, -17.0F, 2.0F, 60.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(336, 29).addBox(27.0F, -63.0F, -17.0F, 2.0F, 60.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(297, 0).addBox(-32.0F, -3.1F, -9.0F, 59.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		rotate_fix.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_side.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_side.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}