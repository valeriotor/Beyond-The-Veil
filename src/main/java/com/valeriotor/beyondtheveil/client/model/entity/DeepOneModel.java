package com.valeriotor.beyondtheveil.client.model.entity;// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class DeepOneModel extends EntityModel<LivingEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "deep_one"), "main");
    private final ModelPart main_body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart right_ear_fin1;
    private final ModelPart left_ear_fin1;
    private final ModelPart left_ear_fin2;
    private final ModelPart right_ear_fin2;
    private final ModelPart forehead;
    private final ModelPart forehead2;
    private final ModelPart forehead3;
    private final ModelPart lower_jaw;
    private final ModelPart right_arm;
    private final ModelPart right_arm2;
    private final ModelPart right_hand;
    private final ModelPart left_arm;
    private final ModelPart left_arm2;
    private final ModelPart left_hand;
    private final ModelPart right_leg;
    private final ModelPart right_leg2;
    private final ModelPart right_foot;
    private final ModelPart right_foot_base;
    private final ModelPart left_leg;
    private final ModelPart left_leg2;
    private final ModelPart left_foot;
    private final ModelPart left_foot_base;

    public DeepOneModel(ModelPart root) {
        this.main_body = root.getChild("main_body");
        this.neck = main_body.getChild("neck");
        this.head = neck.getChild("head");
        this.right_ear_fin1 = head.getChild("right_ear_fin1");
        this.left_ear_fin1 = head.getChild("left_ear_fin1");
        this.left_ear_fin2 = head.getChild("left_ear_fin2");
        this.right_ear_fin2 = head.getChild("right_ear_fin2");
        this.forehead = head.getChild("forehead");
        this.forehead2 = forehead.getChild("forehead2");
        this.forehead3 = forehead2.getChild("forehead3");
        this.lower_jaw = head.getChild("lower_jaw");
        this.right_arm = main_body.getChild("right_arm");
        this.right_arm2 = right_arm.getChild("right_arm2");
        this.right_hand = null;//right_arm2.getChild("right_hand");
        //right_hand.visible = false;
        this.left_arm = main_body.getChild("left_arm");
        this.left_arm2 = left_arm.getChild("left_arm2");
        this.left_hand = null;//left_arm2.getChild("left_hand");
        //left_hand.visible = false;
        this.right_leg = root.getChild("right_leg");
        this.right_leg2 = right_leg.getChild("right_leg2");
        this.right_foot = right_leg2.getChild("right_foot");
        this.right_foot_base = right_foot.getChild("right_foot_base");
        this.left_leg = root.getChild("left_leg");
        this.left_leg2 = left_leg.getChild("left_leg2");
        this.left_foot = left_leg2.getChild("left_foot");
        this.left_foot_base = left_foot.getChild("left_foot_base");
    }

    public static LayerDefinition createBodyLayer() {




        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main_body = partdefinition.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(230, 144).addBox(0.0F, -27.5F, 3.5F, 0.0F, 28.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(3, 146).addBox(-0.5F, -11.5F, 9.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1719F, 9.2152F));

        PartDefinition cube_r1 = main_body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(27, 63).addBox(0.0F, -10.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -20.5F, 6.0F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r2 = main_body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 161).addBox(-5.0F, -4.0F, -2.0F, 12.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 5.3281F, 3.7848F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r3 = main_body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 142).addBox(-6.025F, -5.0F, -2.0F, 14.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.3281F, 3.7848F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r4 = main_body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 120).addBox(-6.0F, -5.0F, -2.0F, 14.0F, 15.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.6719F, 3.7848F, -1.5272F, 0.0F, 0.0F));

        PartDefinition cube_r5 = main_body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 99).addBox(-7.0F, -5.0F, -2.0F, 15.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -8.6719F, 3.7848F, -1.4399F, 0.0F, 0.0F));

        PartDefinition cube_r6 = main_body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 75).addBox(-8.0F, -5.0F, -2.0F, 16.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.6719F, 2.7848F, -1.2654F, 0.0F, 0.0F));

        PartDefinition cube_r7 = main_body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 53).addBox(-9.0F, -5.0F, -2.0F, 17.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -20.6719F, 0.7848F, -1.0472F, 0.0F, 0.0F));

        PartDefinition neck = main_body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 195).addBox(-6.5F, -5.6624F, -6.8391F, 13.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(218, 118).addBox(0.0F, -15.49F, -5.0F, 0.0F, 11.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.0095F, -2.3761F));

        PartDefinition cube_r8 = neck.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, -9.0F, -1.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -5.5F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r9 = neck.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 177).addBox(-8.0F, -5.0F, -1.0F, 15.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.6624F, -0.8391F, -0.3491F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 212).addBox(-5.5F, -2.6F, -8.0F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(234, 117).addBox(-0.5F, -10.5F, -8.0F, 0.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 250).addBox(-6.0F, -0.5F, -8.1F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 250).addBox(3.0F, -0.5F, -8.1F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(7, 12).addBox(-5.0F, 6.4F, -3.0F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -4.1624F, -6.8391F));

        PartDefinition cube_r10 = head.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(28, 64).addBox(0.0F, -6.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.5F, -3.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition right_ear_fin1 = head.addOrReplaceChild("right_ear_fin1", CubeListBuilder.create().texOffs(18, 252).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, 1.5F, -4.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r11 = right_ear_fin1.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(34, 252).addBox(0.5F, -0.8214F, 0.883F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r12 = right_ear_fin1.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(38, 252).addBox(0.5F, -1.8214F, 0.117F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r13 = right_ear_fin1.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(10, 252).addBox(-0.5F, -0.8214F, -0.383F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r14 = right_ear_fin1.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(26, 252).addBox(-0.5F, -0.8214F, 0.383F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition left_ear_fin1 = head.addOrReplaceChild("left_ear_fin1", CubeListBuilder.create().texOffs(18, 252).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.75F, 1.5F, -4.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r15 = left_ear_fin1.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(34, 252).addBox(0.5F, -0.8214F, 0.883F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r16 = left_ear_fin1.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(38, 252).addBox(0.5F, -1.8214F, 0.117F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r17 = left_ear_fin1.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(10, 252).addBox(-0.5F, -0.8214F, -0.383F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r18 = left_ear_fin1.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(26, 252).addBox(-0.5F, -0.8214F, 0.383F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition left_ear_fin2 = head.addOrReplaceChild("left_ear_fin2", CubeListBuilder.create().texOffs(18, 252).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.75F, 1.5F, -2.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r19 = left_ear_fin2.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(34, 252).addBox(0.5F, -0.8214F, 0.883F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r20 = left_ear_fin2.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(38, 252).addBox(0.5F, -1.8214F, 0.117F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r21 = left_ear_fin2.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(10, 252).addBox(-0.5F, -0.8214F, -0.383F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r22 = left_ear_fin2.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(26, 252).addBox(-0.5F, -0.8214F, 0.383F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition right_ear_fin2 = head.addOrReplaceChild("right_ear_fin2", CubeListBuilder.create().texOffs(18, 252).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, 1.5F, -2.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r23 = right_ear_fin2.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(34, 252).addBox(0.5F, -0.8214F, 0.883F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r24 = right_ear_fin2.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(38, 252).addBox(0.5F, -1.8214F, 0.117F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r25 = right_ear_fin2.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(10, 252).addBox(-0.5F, -0.8214F, -0.383F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r26 = right_ear_fin2.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(26, 252).addBox(-0.5F, -0.8214F, 0.383F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition forehead = head.addOrReplaceChild("forehead", CubeListBuilder.create().texOffs(0, 230).addBox(-5.0F, -0.1768F, -3.8232F, 9.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -8.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition forehead2 = forehead.addOrReplaceChild("forehead2", CubeListBuilder.create().texOffs(26, 230).addBox(-4.5F, -0.1057F, -2.7734F, 8.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition forehead3 = forehead2.addOrReplaceChild("forehead3", CubeListBuilder.create().texOffs(48, 230).addBox(-4.4F, -0.0109F, -2.7502F, 8.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition lower_jaw = head.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(3, 8).addBox(-5.0F, -0.5F, -6.0F, 9.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(3, 20).addBox(-3.0F, 1.5F, -4.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.5F, -0.5F, -8.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -4.0F));

        PartDefinition right_arm = main_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(236, 0).addBox(-3.2686F, -2.4217F, -2.75F, 5.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -16.0F, 0.75F, 0.0F, 0.0F, 0.5672F));

        PartDefinition right_arm2 = right_arm.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(240, 25).addBox(-2.7686F, -2.1947F, -2.374F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, -0.25F, -1.0908F, 0.0F, 0.0F));

        PartDefinition left_arm = main_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(236, 0).addBox(-2.7314F, -2.4217F, -2.75F, 5.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -16.0F, 0.75F, 0.0F, 0.0F, -0.5672F));

        PartDefinition left_arm2 = left_arm.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(240, 25).addBox(-2.2314F, -2.1947F, -2.374F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, -0.25F, -1.0908F, 0.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(232, 50).addBox(-5.0F, -2.2686F, -4.4217F, 6.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 2.1719F, 14.2152F, -1.0036F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(238, 76).addBox(-2.0F, -1.3686F, -2.1622F, 4.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 17.0F, -1.0F, 1.7453F, 0.0F, 0.0F));

        PartDefinition right_foot = right_leg2.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(232, 95).addBox(-1.5F, -2.3686F, -4.6622F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, -0.5F));

        PartDefinition right_foot_base = right_foot.addOrReplaceChild("right_foot_base", CubeListBuilder.create().texOffs(236, 107).addBox(-3.701F, -2.45F, 1.616F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(244, 114).addBox(-1.701F, -1.45F, -2.384F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.201F, -1.9219F, -8.0812F, -0.7418F, 0.0F, 0.0F));

        PartDefinition cube_r27 = right_foot_base.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(244, 124).addBox(-2.0F, -0.46F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r28 = right_foot_base.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(244, 120).addBox(-2.0F, -0.46F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4019F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r29 = right_foot_base.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(244, 114).addBox(-0.5F, -0.45F, -4.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.799F, -1.0F, 1.616F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r30 = right_foot_base.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(244, 114).addBox(-0.5F, -0.45F, -4.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.201F, -1.0F, 1.616F, 0.0F, 0.5236F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(232, 50).addBox(-5.0F, -2.2686F, -4.4217F, 6.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 2.1719F, 14.2152F, -1.0036F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(238, 76).addBox(-2.0F, -1.3686F, -2.1622F, 4.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 17.0F, -1.0F, 1.7453F, 0.0F, 0.0F));

        PartDefinition left_foot = left_leg2.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(232, 95).addBox(-1.5F, -2.3686F, -4.6622F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, -0.5F));

        PartDefinition left_foot_base = left_foot.addOrReplaceChild("left_foot_base", CubeListBuilder.create().texOffs(236, 107).addBox(-3.701F, -2.45F, 1.616F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(244, 114).addBox(-1.701F, -1.45F, -2.384F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.201F, -1.9219F, -8.0812F, -0.7418F, 0.0F, 0.0F));

        PartDefinition cube_r31 = left_foot_base.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(244, 124).addBox(-2.0F, -0.46F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r32 = left_foot_base.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(244, 120).addBox(-2.0F, -0.46F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4019F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r33 = left_foot_base.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(244, 114).addBox(-0.5F, -0.45F, -4.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.799F, -1.0F, 1.616F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r34 = left_foot_base.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(244, 114).addBox(-0.5F, -0.45F, -4.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.201F, -1.0F, 1.616F, 0.0F, 0.5236F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        main_body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}