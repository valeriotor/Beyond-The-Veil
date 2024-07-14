package com.valeriotor.beyondtheveil.client.model.entity;// Made with Blockbench 4.8.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CrawlerModel extends AnimatedModel<CrawlerEntity> implements HeadedModel, VillagerHeadModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "crawler_model"), "main");
	private static final String name = "crawler";
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart helmet;
	private final ModelPart brim;
	private final ModelPart nose;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	private final ModelPart arms;

	public CrawlerModel(ModelPart root) {
		super(name);
		this.body = registerAnimatedPart("body", root.getChild("body"));
		this.head = registerAnimatedPart("head", body.getChild("head"));
		this.helmet = registerAnimatedPart("helmet", head.getChild("helmet"));
		this.brim = registerAnimatedPart("brim", head.getChild("brim"));
		this.nose = registerAnimatedPart("nose", head.getChild("nose"));
		this.rightLeg = registerAnimatedPart("rightLeg", body.getChild("RightLeg"));
		this.leftLeg = registerAnimatedPart("leftLeg", body.getChild("LeftLeg"));
		this.arms = registerAnimatedPart("arms", body.getChild("arms"));
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, -7.5F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 38).addBox(-4.0F, -7.5F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 20.75F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -1.3526F, 0.0F, 0.0F));

		PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(32, 0).addBox(-3.9F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition brim = head.addOrReplaceChild("brim", CubeListBuilder.create().texOffs(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -5.5F, 0.0F, -2.3998F, 0.0F, 0.0F));

		PartDefinition RightLeg = body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 4.5F, 0.0F, -0.1745F, 0.0F, 0.0349F));

		PartDefinition LeftLeg = body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 4.5F, 0.0F, -0.1745F, 0.0F, -0.0349F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(CrawlerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		int crawling = entity.getCrawling();
		if(!entity.isSurgeryPatient()) {
			float armSwing = crawling < -1 || entity.isSurgeryPatient() ? 0 : Mth.cos((float) ((crawling - ageInTicks + Math.floor(ageInTicks)) * 2 * Math.PI / 20));
			arms.xRot = -2.3998F - armSwing;
			if (entity.isHeld()) {
				head.xRot = 1;
				arms.xRot = -1;
				leftLeg.xRot = -1;
				rightLeg.xRot = -1;
				leftLeg.z = 0.5F;
				rightLeg.z = 0.5F;
			}
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void prepareMobModel(CrawlerEntity entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
		resetParts();
		LocalPlayer p = Minecraft.getInstance().player;
		float ageInTicks = entity.isHeld() || entity.isSurgeryPatient() ? (p != null ? p.tickCount + pPartialTick : 0) : entity.tickCount + pPartialTick;

		if (entity.isSurgeryPatient()) {
			SurgicalLocation exposedLocation = entity.getPatientStatus().getExposedLocation();
			if (exposedLocation == SurgicalLocation.BACK) {
				head.xRot = 0;
				if (entity.getPatientStatus().isDead()) {
					head.yRot = 0.9F;
					leftLeg.yRot = -0.5F;
					rightLeg.yRot = 0.5F;
				}
			} else if (exposedLocation == SurgicalLocation.CHEST) {
				head.xRot = 0;
				arms.xRot = 0;
				leftLeg.xRot = 0.1745F;
				rightLeg.xRot = 0.1745F;
				if (entity.getPatientStatus().isDead()) {
					head.yRot = 0.9F;
					leftLeg.yRot = 0.5F;
					rightLeg.yRot = -0.5F;
				}
			} else if (exposedLocation == SurgicalLocation.SKULL) {
				head.xRot = -0.54104F;
				arms.xRot = -2.7F;
				nose.visible = false;
				double waterAmount = entity.getPatientStatus().getWaterAmount();
				float rescale = (float) (waterAmount / 2250);
				if (waterAmount > 100) {
					rescale += Mth.sin((float) Math.PI * 2 * ageInTicks / (12 * 1.5F)) / 150;
					head.yRot = (float) (Mth.sin((float) Math.PI * 2 * ageInTicks / (3.5F)) / (65));
					if (waterAmount > 498) {
						rescale *= 2;
					}
				}
				head.xScale = 1 + rescale;
				head.yScale = 1 + rescale;
				head.zScale = 1 + rescale;
				if (entity.getPatientStatus().isDead()) {
					head.xRot = 0;
				}
			}
			Animation painAnimation = entity.getPainAnimation();
			Animation deathAnimation = entity.getDeathAnimation();
			if (painAnimation != null && !entity.getPatientStatus().isDead()) {
				painAnimation.apply(pPartialTick);
			} else if (deathAnimation != null) {
				deathAnimation.apply(pPartialTick);
			}
		}

	}

	@Override
	public ModelPart getHead() {
		return head;
	}

	@Override
	public void hatVisible(boolean pVisible) {
		head.visible = pVisible;
		helmet.visible = pVisible;
		brim.visible = pVisible;
	}
}