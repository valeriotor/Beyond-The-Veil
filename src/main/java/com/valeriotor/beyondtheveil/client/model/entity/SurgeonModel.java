package com.valeriotor.beyondtheveil.client.model.entity;
// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SurgeonModel extends AnimatedModel<SurgeonEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "surgeon"), "main");
    private static final String name = "surgeon";
    private final ModelPart body;
    private final ModelPart LowerBody1;
    private final ModelPart LowerBody2;
    private final ModelPart LowerBody3;
    private final ModelPart RightFrontUpperLeg;
    private final ModelPart RightFrontLowerLeg;
    private final ModelPart RightMidUpperLeg;
    private final ModelPart RightMidLowerLeg;
    private final ModelPart LowerBody;
    private final ModelPart UpperBody;
    private final ModelPart Neck;
    private final ModelPart MainHead;
    private final ModelPart FrontHead1;
    private final ModelPart FrontHead2;
    private final ModelPart Eye8;
    private final ModelPart Eye9;
    private final ModelPart Eye13;
    private final ModelPart Eye18;
    private final ModelPart BackHead1;
    private final ModelPart BackHead2;
    private final ModelPart BackHead3;
    private final ModelPart Eye1;
    private final ModelPart Eye2;
    private final ModelPart Eye3;
    private final ModelPart Eye4;
    private final ModelPart Eye5;
    private final ModelPart Eye6;
    private final ModelPart Eye7;
    private final ModelPart Eye10;
    private final ModelPart Eye11;
    private final ModelPart Eye12;
    private final ModelPart Eye14;
    private final ModelPart Eye15;
    private final ModelPart Eye16;
    private final ModelPart Eye17;
    private final ModelPart RightUpperArm;
    private final ModelPart RightLowerArm;
    private final ModelPart RightUpperTentacle1;
    private final ModelPart RightLowerTentacle1;
    private final ModelPart RightUpperTentacle2;
    private final ModelPart RightLowerTentacle2;
    private final ModelPart RightUpperTentacle3;
    private final ModelPart RightLowerTentacle3;
    private final ModelPart LeftUpperArm;
    private final ModelPart LeftLowerArm;
    private final ModelPart LeftUpperTentacle1;
    private final ModelPart LeftLowerTentacle1;
    private final ModelPart LeftUpperTentacle2;
    private final ModelPart LeftLowerTentacle2;
    private final ModelPart LeftUpperTentacle3;
    private final ModelPart LeftLowerTentacle3;
    private final ModelPart RightBackUpperLeg;
    private final ModelPart RightBackLowerLeg;
    private final ModelPart LeftFrontUpperLeg;
    private final ModelPart LeftFrontLowerLeg;
    private final ModelPart LeftMidUpperLeg;
    private final ModelPart LeftMidLowerLeg;
    private final ModelPart LeftBackUpperLeg;
    private final ModelPart LeftBackLowerLeg;
    private float pPartialTick;

    public SurgeonModel(ModelPart root) {
        super(name);
        this.body = registerAnimatedPart(root, "root");
        this.LowerBody1 = registerAnimatedPart(body, "LowerBody1");
        this.LowerBody2 = registerAnimatedPart(LowerBody1, "LowerBody2");
        this.LowerBody3 = registerAnimatedPart(LowerBody2, "LowerBody3");
        this.RightFrontUpperLeg = registerAnimatedPart(LowerBody1, "RightFrontUpperLeg");
        this.RightFrontLowerLeg = registerAnimatedPart(RightFrontUpperLeg, "RightFrontLowerLeg");
        this.RightMidUpperLeg = registerAnimatedPart(LowerBody1, "RightMidUpperLeg");
        this.RightMidLowerLeg = registerAnimatedPart(RightMidUpperLeg, "RightMidLowerLeg");
        this.LowerBody = registerAnimatedPart(LowerBody1, "LowerBody");
        this.UpperBody = registerAnimatedPart(LowerBody, "UpperBody");
        this.Neck = registerAnimatedPart(UpperBody, "Neck");
        this.MainHead = registerAnimatedPart(Neck, "MainHead");
        this.FrontHead1 = registerAnimatedPart(MainHead, "FrontHead1");
        this.FrontHead2 = registerAnimatedPart(FrontHead1, "FrontHead2");
        this.Eye8 = registerAnimatedPart(FrontHead2, "Eye8");
        this.Eye9 = registerAnimatedPart(FrontHead2, "Eye9");
        this.Eye13 = registerAnimatedPart(FrontHead2, "Eye13");
        this.Eye18 = registerAnimatedPart(FrontHead2, "Eye18");
        this.BackHead1 = registerAnimatedPart(MainHead, "BackHead1");
        this.BackHead2 = registerAnimatedPart(BackHead1, "BackHead2");
        this.BackHead3 = registerAnimatedPart(BackHead2, "BackHead3");
        this.Eye1 = registerAnimatedPart(MainHead, "Eye1");
        this.Eye2 = registerAnimatedPart(MainHead, "Eye2");
        this.Eye3 = registerAnimatedPart(MainHead, "Eye3");
        this.Eye4 = registerAnimatedPart(MainHead, "Eye4");
        this.Eye5 = registerAnimatedPart(MainHead, "Eye5");
        this.Eye6 = registerAnimatedPart(MainHead, "Eye6");
        this.Eye7 = registerAnimatedPart(MainHead, "Eye7");
        this.Eye10 = registerAnimatedPart(MainHead, "Eye10");
        this.Eye11 = registerAnimatedPart(MainHead, "Eye11");
        this.Eye12 = registerAnimatedPart(MainHead, "Eye12");
        this.Eye14 = registerAnimatedPart(MainHead, "Eye14");
        this.Eye15 = registerAnimatedPart(MainHead, "Eye15");
        this.Eye16 = registerAnimatedPart(MainHead, "Eye16");
        this.Eye17 = registerAnimatedPart(MainHead, "Eye17");
        this.RightUpperArm = registerAnimatedPart(UpperBody, "RightUpperArm");
        this.RightLowerArm = registerAnimatedPart(RightUpperArm, "RightLowerArm");
        this.RightUpperTentacle1 = registerAnimatedPart(RightLowerArm, "RightUpperTentacle1");
        this.RightLowerTentacle1 = registerAnimatedPart(RightUpperTentacle1, "RightLowerTentacle1");
        this.RightUpperTentacle2 = registerAnimatedPart(RightLowerArm, "RightUpperTentacle2");
        this.RightLowerTentacle2 = registerAnimatedPart(RightUpperTentacle2, "RightLowerTentacle2");
        this.RightUpperTentacle3 = registerAnimatedPart(RightLowerArm, "RightUpperTentacle3");
        this.RightLowerTentacle3 = registerAnimatedPart(RightUpperTentacle3, "RightLowerTentacle3");
        this.LeftUpperArm = registerAnimatedPart(UpperBody, "LeftUpperArm");
        this.LeftLowerArm = registerAnimatedPart(LeftUpperArm, "LeftLowerArm");
        this.LeftUpperTentacle1 = registerAnimatedPart(LeftLowerArm, "LeftUpperTentacle1");
        this.LeftLowerTentacle1 = registerAnimatedPart(LeftUpperTentacle1, "LeftLowerTentacle1");
        this.LeftUpperTentacle2 = registerAnimatedPart(LeftLowerArm, "LeftUpperTentacle2");
        this.LeftLowerTentacle2 = registerAnimatedPart(LeftUpperTentacle2, "LeftLowerTentacle2");
        this.LeftUpperTentacle3 = registerAnimatedPart(LeftLowerArm, "LeftUpperTentacle3");
        this.LeftLowerTentacle3 = registerAnimatedPart(LeftUpperTentacle3, "LeftLowerTentacle3");
        this.RightBackUpperLeg = registerAnimatedPart(LowerBody1, "RightBackUpperLeg");
        this.RightBackLowerLeg = registerAnimatedPart(RightBackUpperLeg, "RightBackLowerLeg");
        this.LeftFrontUpperLeg = registerAnimatedPart(LowerBody1, "LeftFrontUpperLeg");
        this.LeftFrontLowerLeg = registerAnimatedPart(LeftFrontUpperLeg, "LeftFrontLowerLeg");
        this.LeftMidUpperLeg = registerAnimatedPart(LowerBody1, "LeftMidUpperLeg");
        this.LeftMidLowerLeg = registerAnimatedPart(LeftMidUpperLeg, "LeftMidLowerLeg");
        this.LeftBackUpperLeg = registerAnimatedPart(LowerBody1, "LeftBackUpperLeg");
        this.LeftBackLowerLeg = registerAnimatedPart(LeftBackUpperLeg, "LeftBackLowerLeg");
    }

    public static LayerDefinition createBodyLayer() {


        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition LowerBody1 = root.addOrReplaceChild("LowerBody1", CubeListBuilder.create().texOffs(0, 34).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 10.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, -0.1367F, 0.0F, 0.0F));

        PartDefinition LowerBody2 = LowerBody1.addOrReplaceChild("LowerBody2", CubeListBuilder.create().texOffs(60, 44).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.6F, 18.9F, -0.3643F, 0.0F, 0.0F));

        PartDefinition LowerBody3 = LowerBody2.addOrReplaceChild("LowerBody3", CubeListBuilder.create().texOffs(88, 41).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 10.0F, -0.2731F, 0.0F, 0.0F));

        PartDefinition RightFrontUpperLeg = LowerBody1.addOrReplaceChild("RightFrontUpperLeg", CubeListBuilder.create().texOffs(0, 41).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.7741F));

        PartDefinition RightFrontLowerLeg = RightFrontUpperLeg.addOrReplaceChild("RightFrontLowerLeg", CubeListBuilder.create().texOffs(40, 36).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.1F, 0.0F, 0.0911F, 0.0F, -0.7285F));

        PartDefinition RightMidUpperLeg = LowerBody1.addOrReplaceChild("RightMidUpperLeg", CubeListBuilder.create().texOffs(0, 41).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 6.6F, 0.2276F, 0.0F, 0.7741F));

        PartDefinition RightMidLowerLeg = RightMidUpperLeg.addOrReplaceChild("RightMidLowerLeg", CubeListBuilder.create().texOffs(40, 36).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.1F, 0.1F, 0.0F, 0.0F, -0.7285F));

        PartDefinition LowerBody = LowerBody1.addOrReplaceChild("LowerBody", CubeListBuilder.create().texOffs(96, 0).addBox(-4.5F, -12.0F, -4.0F, 9.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 3.0F));

        PartDefinition UpperBody = LowerBody.addOrReplaceChild("UpperBody", CubeListBuilder.create().texOffs(56, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.0F, -7.0F, 0.182F, 0.0F, 0.0F));

        PartDefinition Neck = UpperBody.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(33, 0).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.9F, 0.9F, 0.1367F, 0.0F, 0.0F));

        PartDefinition MainHead = Neck.addOrReplaceChild("MainHead", CubeListBuilder.create().texOffs(0, 2).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -2.0F));

        PartDefinition FrontHead1 = MainHead.addOrReplaceChild("FrontHead1", CubeListBuilder.create().texOffs(52, 33).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -1.0F));

        PartDefinition FrontHead2 = FrontHead1.addOrReplaceChild("FrontHead2", CubeListBuilder.create().texOffs(72, 33).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -1.0F));

        PartDefinition Eye8 = FrontHead2.addOrReplaceChild("Eye8", CubeListBuilder.create().texOffs(122, 61).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 1.0F, -0.9F));

        PartDefinition Eye9 = FrontHead2.addOrReplaceChild("Eye9", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.9F, 5.0F, -1.0F));

        PartDefinition Eye13 = FrontHead2.addOrReplaceChild("Eye13", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 1.0F, -1.0F));

        PartDefinition Eye18 = FrontHead2.addOrReplaceChild("Eye18", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 4.4F, -1.0F));

        PartDefinition BackHead1 = MainHead.addOrReplaceChild("BackHead1", CubeListBuilder.create().texOffs(12, 22).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 9.3F, -0.0911F, 0.0F, 0.0F));

        PartDefinition BackHead2 = BackHead1.addOrReplaceChild("BackHead2", CubeListBuilder.create().texOffs(36, 22).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 2.3F, -0.182F, 0.0F, 0.0F));

        PartDefinition BackHead3 = BackHead2.addOrReplaceChild("BackHead3", CubeListBuilder.create().texOffs(56, 23).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 2.7F, -0.2731F, 0.0F, 0.0F));

        PartDefinition Eye1 = MainHead.addOrReplaceChild("Eye1", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 3.0F, 2.0F));

        PartDefinition Eye2 = MainHead.addOrReplaceChild("Eye2", CubeListBuilder.create().texOffs(122, 59).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 6.4F, 5.0F));

        PartDefinition Eye3 = MainHead.addOrReplaceChild("Eye3", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 7.0F));

        PartDefinition Eye4 = MainHead.addOrReplaceChild("Eye4", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 5.0F, 1.0F));

        PartDefinition Eye5 = MainHead.addOrReplaceChild("Eye5", CubeListBuilder.create().texOffs(120, 59).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.2F, 3.9F));

        PartDefinition Eye6 = MainHead.addOrReplaceChild("Eye6", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 7.0F, 5.0F));

        PartDefinition Eye7 = MainHead.addOrReplaceChild("Eye7", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 5.0F, 7.0F));

        PartDefinition Eye10 = MainHead.addOrReplaceChild("Eye10", CubeListBuilder.create().texOffs(117, 59).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 8.0F));

        PartDefinition Eye11 = MainHead.addOrReplaceChild("Eye11", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 3.7F, 4.6F));

        PartDefinition Eye12 = MainHead.addOrReplaceChild("Eye12", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 6.6F, 2.0F));

        PartDefinition Eye14 = MainHead.addOrReplaceChild("Eye14", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, 3.0F));

        PartDefinition Eye15 = MainHead.addOrReplaceChild("Eye15", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 6.0F));

        PartDefinition Eye16 = MainHead.addOrReplaceChild("Eye16", CubeListBuilder.create().texOffs(117, 59).addBox(-1.5F, 0.0F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.4F, -1.0F, 1.3F));

        PartDefinition Eye17 = MainHead.addOrReplaceChild("Eye17", CubeListBuilder.create().texOffs(117, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.7F, -1.0F, 5.9F));

        PartDefinition RightUpperArm = UpperBody.addOrReplaceChild("RightUpperArm", CubeListBuilder.create().texOffs(104, 21).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3F, 1.7F, 2.3F, 0.2731F, 0.0F, 0.5463F));

        PartDefinition RightLowerArm = RightUpperArm.addOrReplaceChild("RightLowerArm", CubeListBuilder.create().texOffs(116, 21).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.6F, 3.1F, -2.4586F, 0.0F, -0.5463F));

        PartDefinition RightUpperTentacle1 = RightLowerArm.addOrReplaceChild("RightUpperTentacle1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.3F, 2.3F, 1.457F, -0.5463F, 0.0911F));

        PartDefinition RightLowerTentacle1 = RightUpperTentacle1.addOrReplaceChild("RightLowerTentacle1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, 9.6F, 0.0F, 0.6735F, 0.6295F, -0.4457F));

        PartDefinition RightUpperTentacle2 = RightLowerArm.addOrReplaceChild("RightUpperTentacle2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.3F, 1.7F, 1.7757F, 0.6374F, 0.0F));

        PartDefinition RightLowerTentacle2 = RightUpperTentacle2.addOrReplaceChild("RightLowerTentacle2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 9.3F, -0.1F, 0.1261F, -0.4387F, 0.8255F));

        PartDefinition RightUpperTentacle3 = RightLowerArm.addOrReplaceChild("RightUpperTentacle3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, 2.0F, 2.3675F, 0.0F, 0.0F));

        PartDefinition RightLowerTentacle3 = RightUpperTentacle3.addOrReplaceChild("RightLowerTentacle3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.3F, 0.0F, -0.7057F, 0.0295F, 0.1276F));

        PartDefinition LeftUpperArm = UpperBody.addOrReplaceChild("LeftUpperArm", CubeListBuilder.create().texOffs(104, 21).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.3F, 1.7F, 2.3F, 0.2731F, 0.0F, -0.5463F));

        PartDefinition LeftLowerArm = LeftUpperArm.addOrReplaceChild("LeftLowerArm", CubeListBuilder.create().texOffs(116, 21).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.6F, 3.1F, -2.4586F, 0.0F, 0.5463F));

        PartDefinition LeftUpperTentacle1 = LeftLowerArm.addOrReplaceChild("LeftUpperTentacle1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.3F, 2.3F, 1.457F, -0.5463F, -0.0911F));

        PartDefinition LeftLowerTentacle1 = LeftUpperTentacle1.addOrReplaceChild("LeftLowerTentacle1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, 9.6F, 0.0F, 0.5237F, 0.7531F, -0.6797F));

        PartDefinition LeftUpperTentacle2 = LeftLowerArm.addOrReplaceChild("LeftUpperTentacle2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.3F, 1.7F, 1.7757F, 0.6374F, 0.0F));

        PartDefinition LeftLowerTentacle2 = LeftUpperTentacle2.addOrReplaceChild("LeftLowerTentacle2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 9.3F, -0.1F, 0.1056F, -0.4438F, 0.8734F));

        PartDefinition LeftUpperTentacle3 = LeftLowerArm.addOrReplaceChild("LeftUpperTentacle3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, 2.0F, 2.3675F, 0.0F, 0.0F));

        PartDefinition LeftLowerTentacle3 = LeftUpperTentacle3.addOrReplaceChild("LeftLowerTentacle3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.3F, 0.0F, -0.8821F, 0.0F, 0.0F));

        PartDefinition RightBackUpperLeg = LowerBody1.addOrReplaceChild("RightBackUpperLeg", CubeListBuilder.create().texOffs(0, 41).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 16.0F, 0.2276F, 0.0F, 0.7741F));

        PartDefinition RightBackLowerLeg = RightBackUpperLeg.addOrReplaceChild("RightBackLowerLeg", CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.1F, 0.1F, 0.0F, 0.0F, -0.7285F));

        PartDefinition LeftFrontUpperLeg = LowerBody1.addOrReplaceChild("LeftFrontUpperLeg", CubeListBuilder.create().texOffs(0, 41).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 3.0F, 0.0F, 0.0F, 0.0F, -0.7679F));

        PartDefinition LeftFrontLowerLeg = LeftFrontUpperLeg.addOrReplaceChild("LeftFrontLowerLeg", CubeListBuilder.create().texOffs(40, 36).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.1F, 0.0F, 0.1047F, 0.0F, 0.7285F));

        PartDefinition LeftMidUpperLeg = LowerBody1.addOrReplaceChild("LeftMidUpperLeg", CubeListBuilder.create().texOffs(0, 41).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 3.0F, 6.6F, 0.2269F, 0.0F, -0.7741F));

        PartDefinition LeftMidLowerLeg = LeftMidUpperLeg.addOrReplaceChild("LeftMidLowerLeg", CubeListBuilder.create().texOffs(40, 36).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.1F, 0.1F, 0.0F, 0.0F, 0.7285F));

        PartDefinition LeftBackUpperLeg = LowerBody1.addOrReplaceChild("LeftBackUpperLeg", CubeListBuilder.create().texOffs(0, 41).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 3.0F, 16.0F, 0.2276F, 0.0F, -0.7741F));

        PartDefinition LeftBackLowerLeg = LeftBackUpperLeg.addOrReplaceChild("LeftBackLowerLeg", CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.1F, 0.1F, 0.0F, 0.0F, 0.7285F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(SurgeonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        resetParts();
        float f = 2;
        RightFrontUpperLeg.xRot = 0.0F + Mth.cos(limbSwing * 0.8662F) * 1F * limbSwingAmount / f;
        RightMidUpperLeg.xRot = 0.2276F + Mth.sin(limbSwing * 0.8662F) * 1F * limbSwingAmount / f;
        RightBackUpperLeg.xRot = 0.2276F + Mth.cos(limbSwing * 0.8662F) * 1F * limbSwingAmount / f;
        LeftFrontUpperLeg.xRot = Mth.sin(limbSwing * 0.8662F) * 1F * limbSwingAmount / f;
        LeftMidUpperLeg.xRot = 0.2269F + Mth.cos(limbSwing * 0.8662F) * 1F * limbSwingAmount / f;
        LeftBackUpperLeg.xRot = 0.2276F + Mth.sin(limbSwing * 0.8662F) * 1F * limbSwingAmount / f;

        //RightFrontUpperLeg.zRot = 0.7741F;
        //RightMidUpperLeg.zRot = 0.7741F;
        //RightBackUpperLeg.zRot = 0.7741F;
        //RightFrontLowerLeg.zRot = -1.9741F;
        //RightMidLowerLeg.zRot = -1.9741F;
        //RightBackLowerLeg.zRot = -1.9741F;
        //LeftFrontUpperLeg.zRot = -0.7679F;
        //LeftMidUpperLeg.zRot = -0.7741F;
        //LeftBackUpperLeg.zRot = -0.7741F;
        //LeftFrontLowerLeg.zRot = 1.9679F;
        //LeftMidLowerLeg.zRot = 1.9741F;
        //LeftBackLowerLeg.zRot = 1.9741F;
        //body.y = 10F;
        //LowerBody.xRot = 1.6F;
        //UpperBody.xRot = 0.182F;
        if(entity.isPerformingSurgery()) {
            RightUpperArm.xRot = -0.12731F;
            RightLowerArm.xRot = -1.4586F;
        }

        float offset = Mth.sin((ageInTicks) % 40 / 6.366F) / 20;
        this.LowerBody.y = 5+offset/7;
        this.LowerBody2.xRot = -0.36425021489121656F + offset;
        this.LowerBody3.yRot = offset;
        this.Neck.zRot = offset/7;
        this.Eye1.x = 5.0F + offset/7;
        this.Eye4.x = -5.0F + offset/7;
        this.Eye6.x = -5.0F+offset/7;
        this.Eye8.z = -0.9F+offset/7;
        this.Eye10.x = -5+offset/7;
        this.Eye12.x = 5+offset/7;
        this.Eye13.z = -1+offset/7;
        this.Eye15.y = -0.8F-offset/7;
        this.Eye17.y = -1+offset/7;
        this.Eye18.z = -1-offset/7;
        offset = Mth.sin((ageInTicks)%50 / 7.95F)/16;
        this.UpperBody.xRot =  0.18203784098300857F + offset/3;
        this.Eye2.x = 5.0F + offset/7;
        this.Eye1.y = 3.0F + offset/7;
        this.Eye3.x = 5.0F-offset/7;
        this.Eye5.x = -5.0F-offset/7;
        this.Eye6.y = 7.0F+offset/7;
        this.Eye7.x = -5.2F-offset/7;
        this.Eye9.z = -1+offset/7;
        this.Eye11.x = 5+offset/7;
        this.Eye16.y = -0.8F+offset/7;
        this.Eye14.y = -1+offset/7;
        this.LowerBody2.yRot = offset;
        //this.LeftUpperTentacle2.xRot = 1.7756979809790308F + offset*1.5F;
        //this.RightUpperTentacle1.xRot = 1.4570008595648662F + offset*1.5F;
        
        Animation anim = entity.getMainAnimation();
        if (anim != null) {
            anim.apply(pPartialTick);
        }

        
    }

    @Override
    public void prepareMobModel(SurgeonEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        this.pPartialTick = pPartialTick;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}