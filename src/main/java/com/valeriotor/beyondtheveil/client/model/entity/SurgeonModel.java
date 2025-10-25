package com.valeriotor.beyondtheveil.client.model.entity;
// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.entity.ictya.SepiidEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
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
    private final ModelPart root;
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

    public SurgeonModel(ModelPart root) {
        super(name);
        this.root = root.getChild("root");
        this.LowerBody1 = this.root.getChild("LowerBody1");
        this.LowerBody2 = this.LowerBody1.getChild("LowerBody2");
        this.LowerBody3 = this.LowerBody2.getChild("LowerBody3");
        this.RightFrontUpperLeg = this.LowerBody1.getChild("RightFrontUpperLeg");
        this.RightFrontLowerLeg = this.RightFrontUpperLeg.getChild("RightFrontLowerLeg");
        this.RightMidUpperLeg = this.LowerBody1.getChild("RightMidUpperLeg");
        this.RightMidLowerLeg = this.RightMidUpperLeg.getChild("RightMidLowerLeg");
        this.LowerBody = this.LowerBody1.getChild("LowerBody");
        this.UpperBody = this.LowerBody.getChild("UpperBody");
        this.Neck = this.UpperBody.getChild("Neck");
        this.MainHead = this.Neck.getChild("MainHead");
        this.FrontHead1 = this.MainHead.getChild("FrontHead1");
        this.FrontHead2 = this.FrontHead1.getChild("FrontHead2");
        this.Eye8 = this.FrontHead2.getChild("Eye8");
        this.Eye9 = this.FrontHead2.getChild("Eye9");
        this.Eye13 = this.FrontHead2.getChild("Eye13");
        this.Eye18 = this.FrontHead2.getChild("Eye18");
        this.BackHead1 = this.MainHead.getChild("BackHead1");
        this.BackHead2 = this.BackHead1.getChild("BackHead2");
        this.BackHead3 = this.BackHead2.getChild("BackHead3");
        this.Eye1 = this.MainHead.getChild("Eye1");
        this.Eye2 = this.MainHead.getChild("Eye2");
        this.Eye3 = this.MainHead.getChild("Eye3");
        this.Eye4 = this.MainHead.getChild("Eye4");
        this.Eye5 = this.MainHead.getChild("Eye5");
        this.Eye6 = this.MainHead.getChild("Eye6");
        this.Eye7 = this.MainHead.getChild("Eye7");
        this.Eye10 = this.MainHead.getChild("Eye10");
        this.Eye11 = this.MainHead.getChild("Eye11");
        this.Eye12 = this.MainHead.getChild("Eye12");
        this.Eye14 = this.MainHead.getChild("Eye14");
        this.Eye15 = this.MainHead.getChild("Eye15");
        this.Eye16 = this.MainHead.getChild("Eye16");
        this.Eye17 = this.MainHead.getChild("Eye17");
        this.RightUpperArm = this.UpperBody.getChild("RightUpperArm");
        this.RightLowerArm = this.RightUpperArm.getChild("RightLowerArm");
        this.RightUpperTentacle1 = this.RightLowerArm.getChild("RightUpperTentacle1");
        this.RightLowerTentacle1 = this.RightUpperTentacle1.getChild("RightLowerTentacle1");
        this.RightUpperTentacle2 = this.RightLowerArm.getChild("RightUpperTentacle2");
        this.RightLowerTentacle2 = this.RightUpperTentacle2.getChild("RightLowerTentacle2");
        this.RightUpperTentacle3 = this.RightLowerArm.getChild("RightUpperTentacle3");
        this.RightLowerTentacle3 = this.RightUpperTentacle3.getChild("RightLowerTentacle3");
        this.LeftUpperArm = this.UpperBody.getChild("LeftUpperArm");
        this.LeftLowerArm = this.LeftUpperArm.getChild("LeftLowerArm");
        this.LeftUpperTentacle1 = this.LeftLowerArm.getChild("LeftUpperTentacle1");
        this.LeftLowerTentacle1 = this.LeftUpperTentacle1.getChild("LeftLowerTentacle1");
        this.LeftUpperTentacle2 = this.LeftLowerArm.getChild("LeftUpperTentacle2");
        this.LeftLowerTentacle2 = this.LeftUpperTentacle2.getChild("LeftLowerTentacle2");
        this.LeftUpperTentacle3 = this.LeftLowerArm.getChild("LeftUpperTentacle3");
        this.LeftLowerTentacle3 = this.LeftUpperTentacle3.getChild("LeftLowerTentacle3");
        this.RightBackUpperLeg = this.LowerBody1.getChild("RightBackUpperLeg");
        this.RightBackLowerLeg = this.RightBackUpperLeg.getChild("RightBackLowerLeg");
        this.LeftFrontUpperLeg = this.LowerBody1.getChild("LeftFrontUpperLeg");
        this.LeftFrontLowerLeg = this.LeftFrontUpperLeg.getChild("LeftFrontLowerLeg");
        this.LeftMidUpperLeg = this.LowerBody1.getChild("LeftMidUpperLeg");
        this.LeftMidLowerLeg = this.LeftMidUpperLeg.getChild("LeftMidLowerLeg");
        this.LeftBackUpperLeg = this.LowerBody1.getChild("LeftBackUpperLeg");
        this.LeftBackLowerLeg = this.LeftBackUpperLeg.getChild("LeftBackLowerLeg");
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

        PartDefinition LowerBody = LowerBody1.addOrReplaceChild("LowerBody", CubeListBuilder.create().texOffs(96, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -1.0F));

        PartDefinition UpperBody = LowerBody.addOrReplaceChild("UpperBody", CubeListBuilder.create().texOffs(56, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -3.0F, 0.182F, 0.0F, 0.0F));

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

        PartDefinition RightLowerTentacle1 = RightUpperTentacle1.addOrReplaceChild("RightLowerTentacle1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, 9.6F, 0.0F, 0.8652F, 0.2276F, 0.0F));

        PartDefinition RightUpperTentacle2 = RightLowerArm.addOrReplaceChild("RightUpperTentacle2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.3F, 1.7F, 1.7757F, 0.6374F, 0.0F));

        PartDefinition RightLowerTentacle2 = RightUpperTentacle2.addOrReplaceChild("RightLowerTentacle2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 9.3F, -0.1F, 0.4554F, 0.0F, -0.4554F));

        PartDefinition RightUpperTentacle3 = RightLowerArm.addOrReplaceChild("RightUpperTentacle3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, 2.0F, 2.3675F, 0.0F, 0.0F));

        PartDefinition RightLowerTentacle3 = RightUpperTentacle3.addOrReplaceChild("RightLowerTentacle3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.3F, 0.0F, -0.2276F, 0.0F, 0.0F));

        PartDefinition LeftUpperArm = UpperBody.addOrReplaceChild("LeftUpperArm", CubeListBuilder.create().texOffs(104, 21).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.3F, 1.7F, 2.3F, 0.2731F, 0.0F, -0.5463F));

        PartDefinition LeftLowerArm = LeftUpperArm.addOrReplaceChild("LeftLowerArm", CubeListBuilder.create().texOffs(116, 21).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.6F, 3.1F, -2.4586F, 0.0F, 0.5463F));

        PartDefinition LeftUpperTentacle1 = LeftLowerArm.addOrReplaceChild("LeftUpperTentacle1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.3F, 2.3F, 1.457F, -0.5463F, -0.0911F));

        PartDefinition LeftLowerTentacle1 = LeftUpperTentacle1.addOrReplaceChild("LeftLowerTentacle1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, 9.6F, 0.0F, 0.8652F, 0.2276F, 0.0F));

        PartDefinition LeftUpperTentacle2 = LeftLowerArm.addOrReplaceChild("LeftUpperTentacle2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.3F, 1.7F, 1.7757F, 0.6374F, 0.0F));

        PartDefinition LeftLowerTentacle2 = LeftUpperTentacle2.addOrReplaceChild("LeftLowerTentacle2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 9.3F, -0.1F, 0.4554F, 0.0F, -0.4554F));

        PartDefinition LeftUpperTentacle3 = LeftLowerArm.addOrReplaceChild("LeftUpperTentacle3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, 2.0F, 2.3675F, 0.0F, 0.0F));

        PartDefinition LeftLowerTentacle3 = LeftUpperTentacle3.addOrReplaceChild("LeftLowerTentacle3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.3F, 0.0F, -0.2276F, 0.0F, 0.0F));

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

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}