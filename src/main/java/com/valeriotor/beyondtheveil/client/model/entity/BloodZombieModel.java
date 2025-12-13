package com.valeriotor.beyondtheveil.client.model.entity;// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.BloodZombieEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class BloodZombieModel extends AnimatedModel<BloodZombieEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "blood_zombie"), "main");
    private static final String name = "blood_zombie";
    private final ModelPart body;
    private final ModelPart torso;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart right_eye;
    private final ModelPart left_eye;
    private final ModelPart left_arm;
    private final ModelPart bone2;
    private final ModelPart right_arm;
    private final ModelPart bone;
    private final ModelPart legs;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private float partialTick;

    public BloodZombieModel(ModelPart root) {
        super(name);
        this.body = registerAnimatedPart(root, "body");
        this.torso = registerAnimatedPart(this.body, "torso");
        this.head = registerAnimatedPart(this.torso, "head");
        this.jaw = registerAnimatedPart(this.head, "jaw");
        this.right_eye = registerAnimatedPart(this.head, "right_eye");
        this.left_eye = registerAnimatedPart(this.head, "left_eye");
        this.left_arm = registerAnimatedPart(this.torso, "left_arm");
        this.bone2 = registerAnimatedPart(this.left_arm, "bone2");
        this.right_arm = registerAnimatedPart(this.torso, "right_arm");
        this.bone = registerAnimatedPart(this.right_arm, "bone");
        this.legs = registerAnimatedPart(root, "legs");
        this.left_leg = registerAnimatedPart(this.legs, "left_leg");
        this.right_leg = registerAnimatedPart(this.legs, "right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, -9.0F, -3.5F, 10.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.0F, 1.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(30, 33).addBox(-2.0F, -2.0F, -4.75F, 5.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 5.0F, 1.5F, 0.0F, 0.0F, -0.1745F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 47).addBox(-2.25F, -3.5F, -4.75F, 5.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -5.5F, 1.5F, 0.0F, 0.0F, 0.1745F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -14.0F, -4.0F, 9.0F, 15.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, -14.0F, -4.0F, 9.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(0.0F, -3.0F, -4.0F, 9.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(12, 5).addBox(0.0F, -11.0F, 2.0F, 9.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(25, 7).addBox(6.0F, -11.0F, -4.0F, 3.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 51).addBox(0.0F, -11.0F, -3.75F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

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
                .texOffs(0, 0).addBox(-7.0F, -5.0F, -5.0F, 14.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.0F, 1.0F, 0.0F, 0.0F, 0.4363F));

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

        PartDefinition right_eye = head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(45, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -10.5F, -11.75F));

        PartDefinition left_eye = head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(45, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -10.5F, -11.75F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 24).addBox(-1.75F, -2.0F, -1.0F, 5.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -9.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition bone2 = left_arm.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, 0.0F, -4.0F, 5.0F, 21.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 4.0F, -0.7418F, 0.0F, 0.0F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -1.0F, 5.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.829F));

        PartDefinition bone = right_arm.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(19, 0).addBox(-3.1F, -4.0F, -2.25F, 5.0F, 21.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 15.0F, 0.5F, -0.5672F, 0.0F, 0.0F));

        PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-1.0F, -10.0F, 0.0F));

        PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(46, 0).mirror().addBox(-3.0F, -2.0F, -1.0F, 7.0F, 36.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 0.0F, 0.0F));

        PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(46, 0).addBox(-4.0F, -2.0F, -1.0F, 7.0F, 36.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(BloodZombieEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        markDirty();
        resetParts();

        float halfRot = netHeadYaw * ((float) Math.PI / 180F) / 2;
        float halfRotX = headPitch * ((float) Math.PI / 180F) / 2;
        this.head.yRot = halfRot;
        if (netHeadYaw < 0) { // left
            this.left_eye.x = 3.5F - Mth.clamp(Mth.sin(halfRot) * 0.75F * 2, -0.75F / 1.1F, 0);
            this.right_eye.x = -3.5F - Mth.clamp(Mth.sin(halfRot) * 0.25F * 2, -0.25F / 1.1F, 0);
        } else if (netHeadYaw > 0) { // right
            this.left_eye.x = 3.5F - Mth.clamp(Mth.sin(halfRot) * 0.25F * 2, 0, 0.25F / 1.1F);
            this.right_eye.x = -3.5F - Mth.clamp(Mth.sin(halfRot) * 0.75F * 2, 0, 0.75F / 1.1F);
            this.left_eye.y = -10.5F + 5 * Mth.clamp(Mth.sin(halfRotX) * 0.25F * 2, 0, 0.25F / 1.1F);
            this.right_eye.y = -10.5F + 5 * Mth.clamp(Mth.sin(halfRotX) * 0.25F * 2, 0, 0.25F / 1.1F);
        }

        float offset1 = Mth.sin((float) Math.PI * 2 * ageInTicks / (12 * 2.5F)) / 25;
        float offset2 = Mth.sin((float) Math.PI * 2 * ageInTicks / (13 * 1.5F)) / 15;
        float offset3 = Mth.sin((float) Math.PI * 2 * ageInTicks / (14 * 2.5F)) / 25;
        float offset4 = Mth.sin((float) Math.PI * 2 * ageInTicks / (15 * 2.5F)) / 25;
        float offset5 = Mth.sin((float) Math.PI * 2 * ageInTicks / (16 * 2.5F)) / 25;
        float offset6 = Mth.sin((float) Math.PI * 2 * ageInTicks / (17 * 2.5F)) / 25;
        float offset7 = Mth.sin((float) Math.PI * 2 * ageInTicks / (18 * 2.5F)) / 25;
        float offset8 = Mth.sin((float) Math.PI * 2 * ageInTicks / (19 * 2.5F)) / 25;
        float offset9 = Mth.sin((float) Math.PI * 2 * ageInTicks / (20 * 2.5F)) / 25;

        body.zRot = -0.2182F + offset1 / 3F;
        body.xRot = +offset2 / 3F;
        torso.zRot = 0.0873F + offset3 / 3F;
        torso.xRot = offset4 / 3F;
        right_leg.yRot = offset5 / 2F;
        left_leg.yRot = offset4 / 2F;
        right_leg.zRot = offset3 / 30;
        left_leg.zRot = offset2 / 30;
        left_arm.zRot = -0.7854F + offset1;
        right_arm.zRot = 0.829F + offset9;
        bone2.xRot = -0.7418F + offset6 * 3;
        bone.xRot = -0.5672F + offset8 * 3;
        head.yRot += offset7;
        // right_arm.xRot = 1;

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

        body.xRot += Mth.cos(limbSwing * 0.4331F) * 1F * limbSwingAmount / f / 3.5F;


        this.right_leg.xRot = Mth.cos(limbSwing * 0.8662F) * 1F * limbSwingAmount / f;
        this.left_leg.xRot = Mth.cos(limbSwing * 0.8662F + (float) Math.PI) * 1F * limbSwingAmount / f;

        Animation jawAnimation = entity.getJawAnimation();
        if (jawAnimation != null) {
            jawAnimation.apply(partialTick);
        }
        if (entity.isTargeting()) {
            right_arm.xRot = -1 + Mth.cos(limbSwing * 0.9662F) * 1F * limbSwingAmount / f / 5;;
            left_arm.xRot = -1 + Mth.cos(limbSwing * 0.9662F + (float) Math.PI) * 1F * limbSwingAmount / f / 5;
            right_arm.zRot += Mth.cos(limbSwing * 0.9662F) * 1F * limbSwingAmount / f / 5;;
            left_arm.zRot += Mth.cos(limbSwing * 0.9662F + (float) Math.PI) * 1F * limbSwingAmount / f / 5;
        }

    }

    @Override
    public void prepareMobModel(BloodZombieEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        partialTick = pPartialTick;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}