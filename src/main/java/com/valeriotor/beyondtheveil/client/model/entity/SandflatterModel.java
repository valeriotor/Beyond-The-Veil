package com.valeriotor.beyondtheveil.client.model.entity;

// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.ictya.SandflatterEntity;
import com.valeriotor.beyondtheveil.entity.ictya.SeaSnakeEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class SandflatterModel extends AnimatedModel<SandflatterEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "sandflatter"), "main");
    private static final String name = "sandflatter";

    private final ModelPart dorso;
    private final ModelPart dorsoMeat;
    private final ModelPart rightArm1;
    private final ModelPart rightConn1;
    private final ModelPart rightArm11;
    private final ModelPart rightConn11;
    private final ModelPart rightClaw1;
    private final ModelPart rightClaw11;
    private final ModelPart rightClaw12;
    private final ModelPart rightArm2;
    private final ModelPart rightConn2;
    private final ModelPart rightArm21;
    private final ModelPart rightConn21;
    private final ModelPart rightClaw2;
    private final ModelPart rightClaw21;
    private final ModelPart rightClaw22;
    private final ModelPart rightArm3;
    private final ModelPart rightConn3;
    private final ModelPart rightArm31;
    private final ModelPart rightConn31;
    private final ModelPart rightClaw3;
    private final ModelPart rightClaw31;
    private final ModelPart rightClaw32;
    private final ModelPart rightArm4;
    private final ModelPart rightConn4;
    private final ModelPart rightArm41;
    private final ModelPart rightConn41;
    private final ModelPart rightClaw4;
    private final ModelPart rightClaw41;
    private final ModelPart rightClaw42;
    private final ModelPart leftArm1;
    private final ModelPart leftConn1;
    private final ModelPart leftArm11;
    private final ModelPart leftConn11;
    private final ModelPart leftClaw1;
    private final ModelPart leftClaw11;
    private final ModelPart leftClaw12;
    private final ModelPart leftArm2;
    private final ModelPart leftConn2;
    private final ModelPart leftArm21;
    private final ModelPart leftConn21;
    private final ModelPart leftClaw2;
    private final ModelPart leftClaw21;
    private final ModelPart leftClaw22;
    private final ModelPart leftArm3;
    private final ModelPart leftConn3;
    private final ModelPart leftArm31;
    private final ModelPart leftConn31;
    private final ModelPart leftClaw3;
    private final ModelPart leftClaw31;
    private final ModelPart leftClaw32;
    private final ModelPart leftArm4;
    private final ModelPart leftConn4;
    private final ModelPart leftArm41;
    private final ModelPart leftConn41;
    private final ModelPart leftClaw4;
    private final ModelPart leftClaw41;
    private final ModelPart leftClaw42;
    private final ModelPart tail;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart head;
    private final ModelPart rightJaw;
    private final ModelPart rightJaw1;
    private final ModelPart leftJaw;
    private final ModelPart rightJaw2;
    private final ModelPart leftPincerArm;
    private final ModelPart leftPincerForeArm;
    private final ModelPart leftPincer;
    private final ModelPart leftPincerUp;
    private final ModelPart leftPincerLeft;
    private final ModelPart leftPincerRight;
    private final ModelPart rightPincerArm;
    private final ModelPart rightPincerForeArm;
    private final ModelPart rightPincer;
    private final ModelPart rightPincerUp;
    private final ModelPart RightPincerLeft;
    private final ModelPart rightPincerRight;
    private final ModelPart bait;
    private final ModelPart bait2;
    private final ModelPart bait3;
    private float partialTicks;

    public SandflatterModel(ModelPart root) {
        super(name);
        this.dorso = registerAnimatedPart(root, "dorso");
        this.dorsoMeat = registerAnimatedPart(dorso, "dorsoMeat");
        this.rightArm1 = registerAnimatedPart(dorsoMeat, "rightArm1");
        this.rightConn1 = registerAnimatedPart(rightArm1, "rightConn1");
        this.rightArm11 = registerAnimatedPart(rightConn1, "rightArm11");
        this.rightConn11 = registerAnimatedPart(rightArm11, "rightConn11");
        this.rightClaw1 = registerAnimatedPart(rightConn11, "rightClaw1");
        this.rightClaw11 = registerAnimatedPart(rightClaw1, "rightClaw11");
        this.rightClaw12 = registerAnimatedPart(rightClaw11, "rightClaw12");
        this.rightArm2 = registerAnimatedPart(dorsoMeat, "rightArm2");
        this.rightConn2 = registerAnimatedPart(rightArm2, "rightConn2");
        this.rightArm21 = registerAnimatedPart(rightConn2, "rightArm21");
        this.rightConn21 = registerAnimatedPart(rightArm21, "rightConn21");
        this.rightClaw2 = registerAnimatedPart(rightConn21, "rightClaw2");
        this.rightClaw21 = registerAnimatedPart(rightClaw2, "rightClaw21");
        this.rightClaw22 = registerAnimatedPart(rightClaw21, "rightClaw22");
        this.rightArm3 = registerAnimatedPart(dorsoMeat, "rightArm3");
        this.rightConn3 = registerAnimatedPart(rightArm3, "rightConn3");
        this.rightArm31 = registerAnimatedPart(rightConn3, "rightArm31");
        this.rightConn31 = registerAnimatedPart(rightArm31, "rightConn31");
        this.rightClaw3 = registerAnimatedPart(rightConn31, "rightClaw3");
        this.rightClaw31 = registerAnimatedPart(rightClaw3, "rightClaw31");
        this.rightClaw32 = registerAnimatedPart(rightClaw31, "rightClaw32");
        this.rightArm4 = registerAnimatedPart(dorsoMeat, "rightArm4");
        this.rightConn4 = registerAnimatedPart(rightArm4, "rightConn4");
        this.rightArm41 = registerAnimatedPart(rightConn4, "rightArm41");
        this.rightConn41 = registerAnimatedPart(rightArm41, "rightConn41");
        this.rightClaw4 = registerAnimatedPart(rightConn41, "rightClaw4");
        this.rightClaw41 = registerAnimatedPart(rightClaw4, "rightClaw41");
        this.rightClaw42 = registerAnimatedPart(rightClaw41, "rightClaw42");
        this.leftArm1 = registerAnimatedPart(dorsoMeat, "leftArm1");
        this.leftConn1 = registerAnimatedPart(leftArm1, "leftConn1");
        this.leftArm11 = registerAnimatedPart(leftConn1, "leftArm11");
        this.leftConn11 = registerAnimatedPart(leftArm11, "leftConn11");
        this.leftClaw1 = registerAnimatedPart(leftConn11, "leftClaw1");
        this.leftClaw11 = registerAnimatedPart(leftClaw1, "leftClaw11");
        this.leftClaw12 = registerAnimatedPart(leftClaw11, "leftClaw12");
        this.leftArm2 = registerAnimatedPart(dorsoMeat, "leftArm2");
        this.leftConn2 = registerAnimatedPart(leftArm2, "leftConn2");
        this.leftArm21 = registerAnimatedPart(leftConn2, "leftArm21");
        this.leftConn21 = registerAnimatedPart(leftArm21, "leftConn21");
        this.leftClaw2 = registerAnimatedPart(leftConn21, "leftClaw2");
        this.leftClaw21 = registerAnimatedPart(leftClaw2, "leftClaw21");
        this.leftClaw22 = registerAnimatedPart(leftClaw21, "leftClaw22");
        this.leftArm3 = registerAnimatedPart(dorsoMeat, "leftArm3");
        this.leftConn3 = registerAnimatedPart(leftArm3, "leftConn3");
        this.leftArm31 = registerAnimatedPart(leftConn3, "leftArm31");
        this.leftConn31 = registerAnimatedPart(leftArm31, "leftConn31");
        this.leftClaw3 = registerAnimatedPart(leftConn31, "leftClaw3");
        this.leftClaw31 = registerAnimatedPart(leftClaw3, "leftClaw31");
        this.leftClaw32 = registerAnimatedPart(leftClaw31, "leftClaw32");
        this.leftArm4 = registerAnimatedPart(dorsoMeat, "leftArm4");
        this.leftConn4 = registerAnimatedPart(leftArm4, "leftConn4");
        this.leftArm41 = registerAnimatedPart(leftConn4, "leftArm41");
        this.leftConn41 = registerAnimatedPart(leftArm41, "leftConn41");
        this.leftClaw4 = registerAnimatedPart(leftConn41, "leftClaw4");
        this.leftClaw41 = registerAnimatedPart(leftClaw4, "leftClaw41");
        this.leftClaw42 = registerAnimatedPart(leftClaw41, "leftClaw42");
        this.tail = registerAnimatedPart(dorsoMeat, "tail");
        this.tail2 = registerAnimatedPart(tail, "tail2");
        this.tail3 = registerAnimatedPart(tail2, "tail3");
        this.head = registerAnimatedPart(dorsoMeat, "head");
        this.rightJaw = registerAnimatedPart(head, "rightJaw");
        this.rightJaw1 = registerAnimatedPart(rightJaw, "rightJaw1");
        this.leftJaw = registerAnimatedPart(head, "leftJaw");
        this.rightJaw2 = registerAnimatedPart(leftJaw, "rightJaw2");
        this.leftPincerArm = registerAnimatedPart(dorsoMeat, "leftPincerArm");
        this.leftPincerForeArm = registerAnimatedPart(leftPincerArm, "leftPincerForeArm");
        this.leftPincer = registerAnimatedPart(leftPincerForeArm, "leftPincer");
        this.leftPincerUp = registerAnimatedPart(leftPincer, "leftPincerUp");
        this.leftPincerLeft = registerAnimatedPart(leftPincer, "leftPincerLeft");
        this.leftPincerRight = registerAnimatedPart(leftPincer, "leftPincerRight");
        this.rightPincerArm = registerAnimatedPart(dorsoMeat, "rightPincerArm");
        this.rightPincerForeArm = registerAnimatedPart(rightPincerArm, "rightPincerForeArm");
        this.rightPincer = registerAnimatedPart(rightPincerForeArm, "rightPincer");
        this.rightPincerUp = registerAnimatedPart(rightPincer, "rightPincerUp");
        this.RightPincerLeft = registerAnimatedPart(rightPincer, "rightPincerLeft");
        this.rightPincerRight = registerAnimatedPart(rightPincer, "rightPincerRight");
        this.bait = registerAnimatedPart(dorso, "bait");
        this.bait2 = registerAnimatedPart(bait, "bait2");
        this.bait3 = registerAnimatedPart(bait2, "bait3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition dorso = partdefinition.addOrReplaceChild("dorso", CubeListBuilder.create().texOffs(0, 207).addBox(-24.0F, -1.0F, -24.0F, 48.0F, 1.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition dorsoMeat = dorso.addOrReplaceChild("dorsoMeat", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, -1.0F, -20.0F, 40.0F, 6.0F, 40.0F, new CubeDeformation(0.0F))
                .texOffs(0, 125).addBox(-17.0F, 5.0F, -17.0F, 34.0F, 6.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(168, 82).addBox(-14.0F, 1.0F, -30.0F, 28.0F, 11.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition rightArm1 = dorsoMeat.addOrReplaceChild("rightArm1", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, -1.5F, -1.5F, 23.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, 1.5F, -17.5F, 0.0F, -0.4363F, -0.3491F));

        PartDefinition rightConn1 = rightArm1.addOrReplaceChild("rightConn1", CubeListBuilder.create().texOffs(246, 225).addBox(-2.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition rightArm11 = rightConn1.addOrReplaceChild("rightArm11", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -1.5F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition rightConn11 = rightArm11.addOrReplaceChild("rightConn11", CubeListBuilder.create().texOffs(246, 222).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition rightClaw1 = rightConn11.addOrReplaceChild("rightClaw1", CubeListBuilder.create().texOffs(120, 228).addBox(-4.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightClaw11 = rightClaw1.addOrReplaceChild("rightClaw11", CubeListBuilder.create().texOffs(120, 228).addBox(-1.0F, -1.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition rightClaw12 = rightClaw11.addOrReplaceChild("rightClaw12", CubeListBuilder.create().texOffs(120, 228).addBox(-7.75F, -1.25F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition rightArm2 = dorsoMeat.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, -1.5F, -1.5F, 23.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, 1.5F, -8.5F, 0.0F, -0.0873F, -0.3491F));

        PartDefinition rightConn2 = rightArm2.addOrReplaceChild("rightConn2", CubeListBuilder.create().texOffs(246, 224).addBox(-2.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition rightArm21 = rightConn2.addOrReplaceChild("rightArm21", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -1.5F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition rightConn21 = rightArm21.addOrReplaceChild("rightConn21", CubeListBuilder.create().texOffs(245, 221).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition rightClaw2 = rightConn21.addOrReplaceChild("rightClaw2", CubeListBuilder.create().texOffs(125, 239).addBox(-4.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightClaw21 = rightClaw2.addOrReplaceChild("rightClaw21", CubeListBuilder.create().texOffs(125, 239).addBox(-1.0F, -1.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition rightClaw22 = rightClaw21.addOrReplaceChild("rightClaw22", CubeListBuilder.create().texOffs(125, 239).addBox(-7.75F, -1.25F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition rightArm3 = dorsoMeat.addOrReplaceChild("rightArm3", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, -1.5F, -1.5F, 23.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, 1.5F, 4.5F, 0.0F, 0.0873F, -0.3491F));

        PartDefinition rightConn3 = rightArm3.addOrReplaceChild("rightConn3", CubeListBuilder.create().texOffs(241, 224).addBox(-2.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition rightArm31 = rightConn3.addOrReplaceChild("rightArm31", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -1.5F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition rightConn31 = rightArm31.addOrReplaceChild("rightConn31", CubeListBuilder.create().texOffs(246, 226).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition rightClaw3 = rightConn31.addOrReplaceChild("rightClaw3", CubeListBuilder.create().texOffs(125, 228).addBox(-4.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightClaw31 = rightClaw3.addOrReplaceChild("rightClaw31", CubeListBuilder.create().texOffs(125, 228).addBox(-1.0F, -1.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition rightClaw32 = rightClaw31.addOrReplaceChild("rightClaw32", CubeListBuilder.create().texOffs(125, 228).addBox(-7.75F, -1.25F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition rightArm4 = dorsoMeat.addOrReplaceChild("rightArm4", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, -1.5F, -1.5F, 23.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, 1.5F, 17.5F, 0.0F, 0.4363F, -0.3491F));

        PartDefinition rightConn4 = rightArm4.addOrReplaceChild("rightConn4", CubeListBuilder.create().texOffs(246, 221).addBox(-2.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition rightArm41 = rightConn4.addOrReplaceChild("rightArm41", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -1.5F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition rightConn41 = rightArm41.addOrReplaceChild("rightConn41", CubeListBuilder.create().texOffs(246, 222).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition rightClaw4 = rightConn41.addOrReplaceChild("rightClaw4", CubeListBuilder.create().texOffs(128, 224).addBox(-4.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightClaw41 = rightClaw4.addOrReplaceChild("rightClaw41", CubeListBuilder.create().texOffs(128, 224).addBox(-1.0F, -1.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition rightClaw42 = rightClaw41.addOrReplaceChild("rightClaw42", CubeListBuilder.create().texOffs(122, 224).addBox(-7.75F, -1.25F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition leftArm1 = dorsoMeat.addOrReplaceChild("leftArm1", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, -1.5F, -2.5F, 23.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, 1.5F, -16.5F, 0.0F, -2.7053F, 0.3491F));

        PartDefinition leftConn1 = leftArm1.addOrReplaceChild("leftConn1", CubeListBuilder.create().texOffs(246, 225).addBox(-2.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition leftArm11 = leftConn1.addOrReplaceChild("leftArm11", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -1.5F, -2.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition leftConn11 = leftArm11.addOrReplaceChild("leftConn11", CubeListBuilder.create().texOffs(231, 222).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition leftClaw1 = leftConn11.addOrReplaceChild("leftClaw1", CubeListBuilder.create().texOffs(118, 225).addBox(-4.0F, -1.5F, -2.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftClaw11 = leftClaw1.addOrReplaceChild("leftClaw11", CubeListBuilder.create().texOffs(118, 225).addBox(-1.0F, -1.75F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition leftClaw12 = leftClaw11.addOrReplaceChild("leftClaw12", CubeListBuilder.create().texOffs(118, 225).addBox(-7.75F, -1.25F, -1.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition leftArm2 = dorsoMeat.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, -1.5F, -1.5F, 23.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, 1.5F, -8.5F, 0.0F, -3.0543F, 0.3491F));

        PartDefinition leftConn2 = leftArm2.addOrReplaceChild("leftConn2", CubeListBuilder.create().texOffs(246, 226).addBox(-2.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition leftArm21 = leftConn2.addOrReplaceChild("leftArm21", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -1.5F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition leftConn21 = leftArm21.addOrReplaceChild("leftConn21", CubeListBuilder.create().texOffs(243, 225).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition leftClaw2 = leftConn21.addOrReplaceChild("leftClaw2", CubeListBuilder.create().texOffs(120, 224).addBox(-4.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftClaw21 = leftClaw2.addOrReplaceChild("leftClaw21", CubeListBuilder.create().texOffs(120, 224).addBox(-1.0F, -1.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition leftClaw22 = leftClaw21.addOrReplaceChild("leftClaw22", CubeListBuilder.create().texOffs(120, 224).addBox(-7.75F, -1.25F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition leftArm3 = dorsoMeat.addOrReplaceChild("leftArm3", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, -1.5F, -1.5F, 23.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, 1.5F, 4.5F, 0.0F, 3.0543F, 0.3491F));

        PartDefinition leftConn3 = leftArm3.addOrReplaceChild("leftConn3", CubeListBuilder.create().texOffs(246, 226).addBox(-2.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition leftArm31 = leftConn3.addOrReplaceChild("leftArm31", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -1.5F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition leftConn31 = leftArm31.addOrReplaceChild("leftConn31", CubeListBuilder.create().texOffs(237, 224).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition leftClaw3 = leftConn31.addOrReplaceChild("leftClaw3", CubeListBuilder.create().texOffs(119, 224).addBox(-4.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftClaw31 = leftClaw3.addOrReplaceChild("leftClaw31", CubeListBuilder.create().texOffs(119, 224).addBox(-1.0F, -1.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition leftClaw32 = leftClaw31.addOrReplaceChild("leftClaw32", CubeListBuilder.create().texOffs(119, 224).addBox(-7.75F, -1.25F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition leftArm4 = dorsoMeat.addOrReplaceChild("leftArm4", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, -1.5F, -1.5F, 23.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, 1.5F, 17.5F, 0.0F, 2.7053F, 0.3491F));

        PartDefinition leftConn4 = leftArm4.addOrReplaceChild("leftConn4", CubeListBuilder.create().texOffs(238, 222).addBox(-2.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition leftArm41 = leftConn4.addOrReplaceChild("leftArm41", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -1.5F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition leftConn41 = leftArm41.addOrReplaceChild("leftConn41", CubeListBuilder.create().texOffs(240, 222).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition leftClaw4 = leftConn41.addOrReplaceChild("leftClaw4", CubeListBuilder.create().texOffs(123, 223).addBox(-4.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftClaw41 = leftClaw4.addOrReplaceChild("leftClaw41", CubeListBuilder.create().texOffs(123, 223).addBox(-1.0F, -1.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition leftClaw42 = leftClaw41.addOrReplaceChild("leftClaw42", CubeListBuilder.create().texOffs(123, 223).addBox(-7.75F, -1.25F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition tail = dorsoMeat.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(92, 169).addBox(-10.0F, -2.0F, 1.0F, 20.0F, 6.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(0, 171).addBox(-7.0F, 4.0F, 1.0F, 14.0F, 3.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 16.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(144, 207).addBox(-8.0F, -1.0F, 0.0F, 16.0F, 5.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(0, 174).addBox(-5.0F, 4.0F, 0.0F, 10.0F, 2.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 30.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -2.0F, 0.0F, 18.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(154, 0).addBox(-15.0F, 3.0F, 8.0F, 30.0F, 0.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 26.0F));

        PartDefinition head = dorsoMeat.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -3.0F, -9.0F, 16.0F, 11.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, 1.0F, -10.0F, 16.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(240, 248).addBox(6.0F, -4.0F, -11.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(240, 248).addBox(-9.0F, -4.0F, -11.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(244, 250).addBox(-6.0F, 1.0F, -12.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(244, 250).addBox(4.0F, 1.0F, -12.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(248, 252).addBox(1.0F, 5.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(248, 252).addBox(-2.0F, 5.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -28.0F));

        PartDefinition rightJaw = head.addOrReplaceChild("rightJaw", CubeListBuilder.create().texOffs(244, 237).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 7.0F, -6.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition rightJaw1 = rightJaw.addOrReplaceChild("rightJaw1", CubeListBuilder.create().texOffs(248, 237).addBox(-1.5F, -1.75F, -0.75F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition leftJaw = head.addOrReplaceChild("leftJaw", CubeListBuilder.create().texOffs(244, 237).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 7.0F, -6.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition rightJaw2 = leftJaw.addOrReplaceChild("rightJaw2", CubeListBuilder.create().texOffs(248, 236).addBox(-0.4774F, -1.5706F, -0.5F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition leftPincerArm = dorsoMeat.addOrReplaceChild("leftPincerArm", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -2.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 8.0F, -25.0F, 0.0F, 0.3491F, 0.4363F));

        PartDefinition leftPincerForeArm = leftPincerArm.addOrReplaceChild("leftPincerForeArm", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.5F, -1.0F, 19.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -1.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition leftPincer = leftPincerForeArm.addOrReplaceChild("leftPincer", CubeListBuilder.create(), PartPose.offset(17.0F, -1.0F, 1.0F));

        PartDefinition leftPincerUp = leftPincer.addOrReplaceChild("leftPincerUp", CubeListBuilder.create().texOffs(232, 226).addBox(-1.0F, -3.0F, -1.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition leftPincerLeft = leftPincer.addOrReplaceChild("leftPincerLeft", CubeListBuilder.create().texOffs(232, 226).addBox(-1.5F, -4.0F, 0.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.0944F, -0.3491F, 0.0F));

        PartDefinition leftPincerRight = leftPincer.addOrReplaceChild("leftPincerRight", CubeListBuilder.create().texOffs(232, 226).addBox(-1.5F, 0.25F, -1.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.6109F, 0.6109F));

        PartDefinition rightPincerArm = dorsoMeat.addOrReplaceChild("rightPincerArm", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -2.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.0F, 5.0F, -25.0F, 0.0F, 0.3491F, 2.7053F));

        PartDefinition rightPincerForeArm = rightPincerArm.addOrReplaceChild("rightPincerForeArm", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.5F, -1.0F, 19.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -1.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rightPincer = rightPincerForeArm.addOrReplaceChild("rightPincer", CubeListBuilder.create(), PartPose.offsetAndRotation(17.0F, -1.0F, 1.0F, 3.0543F, 0.0F, 0.0F));

        PartDefinition rightPincerUp = rightPincer.addOrReplaceChild("rightPincerUp", CubeListBuilder.create().texOffs(232, 226).addBox(-1.0F, -3.0F, -1.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition rightPincerLeft = rightPincer.addOrReplaceChild("rightPincerLeft", CubeListBuilder.create().texOffs(232, 226).addBox(-1.5F, -4.0F, 0.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.0944F, -0.3491F, 0.0F));

        PartDefinition rightPincerRight = rightPincer.addOrReplaceChild("rightPincerRight", CubeListBuilder.create().texOffs(232, 226).addBox(-1.5F, 0.25F, -1.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.6109F, 0.6109F));

        PartDefinition bait = dorso.addOrReplaceChild("bait", CubeListBuilder.create().texOffs(0, 87).addBox(-1.0F, -26.0F, -1.0F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bait2 = bait.addOrReplaceChild("bait2", CubeListBuilder.create().texOffs(0, 89).addBox(-0.5F, -24.0F, -0.5F, 1.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -25.0F, 0.0F));

        PartDefinition bait3 = bait2.addOrReplaceChild("bait3", CubeListBuilder.create().texOffs(0, 78).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(SandflatterEntity e, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        markDirty();
        resetParts();

        if (e.isAmbushing()) {
            dorso.y = 1.125F;
            leftConn1.zRot = 0.3491F;
            leftConn2.zRot = 0.3491F;
            leftConn3.zRot = 0.3491F;
            leftConn4.zRot = 0.3491F;
            rightConn1.zRot = 0.3491F;
            rightConn2.zRot = 0.3491F;
            rightConn3.zRot = 0.3491F;
            rightConn4.zRot = 0.3491F;
            leftConn11.zRot = 0.3491F;
            leftConn21.zRot = 0.3491F;
            leftConn31.zRot = 0.3491F;
            leftConn41.zRot = 0.3491F;
            rightConn11.zRot = 0.3491F;
            rightConn21.zRot = 0.3491F;
            rightConn31.zRot = 0.3491F;
            rightConn41.zRot = 0.3491F;
            leftArm1.zRot = 0.1F;
            leftArm2.zRot = 0.1F;
            leftArm3.zRot = 0.1F;
            leftArm4.zRot = 0.1F;
            rightArm1.zRot = -0.1F;
            rightArm2.zRot = -0.1F;
            rightArm3.zRot = -0.1F;
            rightArm4.zRot = -0.1F;
            float offset = Mth.sin((e.tickCount % 40 + partialTicks) / 6.366F) / 6;
            bait.xRot = offset;
            bait2.yRot = offset;
            offset = Mth.sin((e.tickCount % 50 + partialTicks) / 7.95F) / 4;
            bait.yRot = offset;
            bait2.xRot = offset;
        } else {
            dorso.y = 0;
            float swing = Mth.cos(limbSwing * 0.662F) * limbSwingAmount;
            tail.xRot = swing / 8;
            tail2.xRot = swing / 16;
            leftArm1.xRot += swing * 1.5 / 8;
            leftArm2.xRot -= swing * 1.5 / 8;
            leftArm3.xRot += swing * 1.5 / 8;
            leftArm4.xRot -= swing * 1.5 / 8;
            rightArm1.xRot += swing * 1.5 / 8;
            rightArm2.xRot -= swing * 1.5 / 8;
            rightArm3.xRot += swing * 1.5 / 8;
            rightArm4.xRot -= swing * 1.5 / 8;
            leftArm1.zRot += swing * 1.5 / 6;
            leftArm2.zRot -= swing * 1.5 / 6;
            leftArm3.zRot += swing * 1.5 / 6;
            leftArm4.zRot -= swing * 1.5 / 6;
            rightArm1.zRot += swing * 1.5 / 6;
            rightArm2.zRot -= swing * 1.5 / 6;
            rightArm3.zRot += swing * 1.5 / 6;
            rightArm4.zRot -= swing * 1.5 / 6;
            dorso.xRot = headPitch * ((float) Math.PI / 180F);
        }

        Animation attackAnimation = e.getAttackAnimation();
        if (attackAnimation != null) {
            attackAnimation.apply(partialTicks);
        }


    }

    @Override
    public void prepareMobModel(SandflatterEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
        partialTicks = pPartialTick;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        dorso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
