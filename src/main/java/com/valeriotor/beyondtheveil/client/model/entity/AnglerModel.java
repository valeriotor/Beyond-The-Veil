package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.ictya.AnglerEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AnglerModel extends EntityModel<AnglerEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "deep_angler"), "main");
    private final ModelPart Body;
    private final ModelPart Head;
    private final ModelPart LowerJaw;
    private final ModelPart ToothLL;
    private final ModelPart ToothLR;
    private final ModelPart ToothRL;
    private final ModelPart ToothRR;
    private final ModelPart LightStem;
    private final ModelPart LightStem2;
    private final ModelPart Bulb;
    private final ModelPart Tail;
    private final ModelPart Tail2;
    private final ModelPart Tail3;
    private final ModelPart TailFin;
    private final ModelPart LeftFin;
    private final ModelPart RightFin;
    private final ModelPart DorsalFin;

    public AnglerModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Head = this.Body.getChild("Head");
        this.LowerJaw = this.Head.getChild("LowerJaw");
        this.ToothLL = this.LowerJaw.getChild("ToothLL");
        this.ToothLR = this.LowerJaw.getChild("ToothLR");
        this.ToothRL = this.LowerJaw.getChild("ToothRL");
        this.ToothRR = this.LowerJaw.getChild("ToothRR");
        this.LightStem = this.Head.getChild("LightStem");
        this.LightStem2 = this.LightStem.getChild("LightStem2");
        this.Bulb = this.LightStem2.getChild("Bulb");
        this.Tail = this.Body.getChild("Tail");
        this.Tail2 = this.Tail.getChild("Tail2");
        this.Tail3 = this.Tail2.getChild("Tail3");
        this.TailFin = this.Tail3.getChild("TailFin");
        this.LeftFin = this.Body.getChild("LeftFin");
        this.RightFin = this.Body.getChild("RightFin");
        this.DorsalFin = this.Body.getChild("DorsalFin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -16.0F, -6.0F, 8.0F, 9.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(36, 17).addBox(-3.0F, -16.25F, -13.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(3.0F, -15.0F, -12.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-4.0F, -15.0F, -12.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition LowerJaw = Head.addOrReplaceChild("LowerJaw", CubeListBuilder.create().texOffs(24, 31).addBox(-4.5F, -2.0F, -17.5F, 9.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.5F, 1.4F));

        PartDefinition ToothLL = LowerJaw.addOrReplaceChild("ToothLL", CubeListBuilder.create().texOffs(0, 39).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -2.5F, -16.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition ToothLR = LowerJaw.addOrReplaceChild("ToothLR", CubeListBuilder.create().texOffs(0, 39).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2F, -2.5F, -16.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition ToothRL = LowerJaw.addOrReplaceChild("ToothRL", CubeListBuilder.create().texOffs(0, 39).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2F, -2.5F, -16.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition ToothRR = LowerJaw.addOrReplaceChild("ToothRR", CubeListBuilder.create().texOffs(0, 39).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -2.5F, -16.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition LightStem = Head.addOrReplaceChild("LightStem", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, -6.5F, 0.4363F, 0.0F, 0.0F));

        PartDefinition LightStem2 = LightStem.addOrReplaceChild("LightStem2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -4.0F, -0.2F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition Bulb = LightStem2.addOrReplaceChild("Bulb", CubeListBuilder.create().texOffs(0, 35).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(2, 9).addBox(-3.5F, -4.0F, 0.0F, 7.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.5F, 6.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition Tail2 = Tail.addOrReplaceChild("Tail2", CubeListBuilder.create().texOffs(46, 2).addBox(-2.5F, -2.0F, 12.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Tail3 = Tail2.addOrReplaceChild("Tail3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 15.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition TailFin = Tail3.addOrReplaceChild("TailFin", CubeListBuilder.create().texOffs(0, 54).addBox(-0.5F, -3.0F, 17.0F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition LeftFin = Body.addOrReplaceChild("LeftFin", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(31, 60).mirror().addBox(1.75F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, -10.5F, -3.5F, 0.0F, -0.4363F, 0.0F));

        PartDefinition RightFin = Body.addOrReplaceChild("RightFin", CubeListBuilder.create().texOffs(0, 0).addBox(0.1558F, -1.5F, -0.4583F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(31, 60).mirror().addBox(2.4058F, -2.0F, 0.0417F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -10.5F, -3.5F, 0.0F, -2.7053F, 0.0F));

        PartDefinition DorsalFin = Body.addOrReplaceChild("DorsalFin", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -16.0F, 1.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r1 = DorsalFin.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(21, 33).addBox(0.5F, -7.0F, -6.5F, 0.0F, 10.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.8F, 0.0F, -0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(AnglerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        Body.xRot = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void prepareMobModel(AnglerEntity e, float limbSwing, float limbSwingAmount, float partialTickTime) {
        float offset = Mth.sin((e.tickCount % 40 + partialTickTime) / 6.366F) / 30;
        LowerJaw.xRot = offset / 2 + 0.15F;
        offset += Mth.cos(limbSwing * 0.3F) * limbSwingAmount / 7;
        Tail.yRot = offset * 3;
        Tail2.yRot = offset * 3;

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}
