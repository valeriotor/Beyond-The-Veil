package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.ictya.SeaSnakeEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SeaSnakeModel extends AnimatedModel<SeaSnakeEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "muray"), "main");
    private static final String name = "sea_snake";
    private final ModelPart Tail;
    private final ModelPart Tail2;
    private final ModelPart Tail3;
    private final ModelPart Tail4;
    private final ModelPart Tail5;
    private final ModelPart Tail6;
    private final ModelPart Tail7;
    private final ModelPart Head;
    private final ModelPart RightMouth;
    private final ModelPart LowerMouth;
    private final ModelPart LeftMouth;
    private final ModelPart full;
    private float partialTick;

    public SeaSnakeModel(ModelPart root) {
        super(name);
        this.full = root.getChild("full");
        this.Tail = full.getChild("Tail");
        this.Tail2 = this.Tail.getChild("Tail2");
        this.Tail3 = this.Tail2.getChild("Tail3");
        this.Tail4 = this.Tail3.getChild("Tail4");
        this.Tail5 = this.Tail4.getChild("Tail5");
        this.Tail6 = this.Tail5.getChild("Tail6");
        this.Tail7 = this.Tail6.getChild("Tail7");
        this.Head = full.getChild("Head");
        this.RightMouth = this.Head.getChild("RightMouth");
        this.LowerMouth = this.Head.getChild("LowerMouth");
        this.LeftMouth = this.Head.getChild("LeftMouth");
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition full = partdefinition.addOrReplaceChild("full", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition Tail = full.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(14, 1).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.6F, -0.6F, -16.25F));

        PartDefinition Tail2 = Tail.addOrReplaceChild("Tail2", CubeListBuilder.create().texOffs(14, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, 0.1F, 5.25F));

        PartDefinition Tail3 = Tail2.addOrReplaceChild("Tail3", CubeListBuilder.create().texOffs(13, 0).mirror().addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.1F, 0.1F, 4.75F));

        PartDefinition Tail4 = Tail3.addOrReplaceChild("Tail4", CubeListBuilder.create().texOffs(13, 1).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(1, 10).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1F, -0.1F, 5.0F));

        PartDefinition Tail5 = Tail4.addOrReplaceChild("Tail5", CubeListBuilder.create().texOffs(14, 1).mirror().addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(6, 10).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1F, -0.1F, 5.0F));

        PartDefinition Tail6 = Tail5.addOrReplaceChild("Tail6", CubeListBuilder.create().texOffs(13, 1).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(0.0F, -4.0F, -0.25F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, 0.1F, 4.25F));

        PartDefinition Tail7 = Tail6.addOrReplaceChild("Tail7", CubeListBuilder.create().texOffs(14, 1).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(13, 10).addBox(-0.1F, -4.1F, 0.75F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, 0.1F, 5.0F));

        PartDefinition Head = full.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -1.75F, -7.25F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 22).addBox(1.5F, -1.0F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-1.5F, -1.0F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.25F, -14.75F, 0.0873F, 0.0F, 0.0F));

        PartDefinition RightMouth = Head.addOrReplaceChild("RightMouth", CubeListBuilder.create().texOffs(0, 25).mirror().addBox(-1.0F, 0.25F, -7.25F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition LowerMouth = Head.addOrReplaceChild("LowerMouth", CubeListBuilder.create().texOffs(18, 25).addBox(0.0F, 0.25F, -7.25F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition LeftMouth = Head.addOrReplaceChild("LeftMouth", CubeListBuilder.create().texOffs(0, 25).addBox(1.0F, 0.25F, -7.25F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(SeaSnakeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        resetParts();
        if (entity.isAggressive()) {
            //setOpenMouth();
        }

        full.xRot = headPitch * ((float)Math.PI / 180F);
        float time = (float) (0.08 * Math.PI * (entity.tickCount + partialTick));
        this.Tail.yRot = (float) Math.atan(Mth.cos(time + (float) (2 * 0 * Math.PI / 7))) * (limbSwingAmount / 3 + 0.15F);
        this.Tail2.yRot = (float) Math.atan(Mth.cos(time + (float) (2 * 1 * Math.PI / 7))) * (limbSwingAmount / 3 + 0.15F);
        this.Tail3.yRot = (float) Math.atan(Mth.cos(time + (float) (2 * 2 * Math.PI / 7))) * (limbSwingAmount / 3 + 0.15F);
        this.Tail4.yRot = (float) Math.atan(Mth.cos(time + (float) (2 * 3 * Math.PI / 7))) * (limbSwingAmount / 3 + 0.15F);
        this.Tail5.yRot = (float) Math.atan(Mth.cos(time + (float) (2 * 4 * Math.PI / 7))) * (limbSwingAmount / 3 + 0.15F);
        this.Tail6.yRot = (float) Math.atan(Mth.cos(time + (float) (2 * 5 * Math.PI / 7))) * (limbSwingAmount / 3 + 0.15F);
        this.Tail7.yRot = (float) Math.atan(Mth.cos(time + (float) (2 * 6 * Math.PI / 7))) * (limbSwingAmount / 3 + 0.15F);
        //Head.xRot = headPitch * ((float)Math.PI / 180F);
        //Tail.xRot = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void prepareMobModel(SeaSnakeEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
        partialTick = pPartialTick;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        full.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    private void setOpenMouth() {
        RightMouth.yRot = 0.4F;
        LeftMouth.yRot = -0.4F;
        RightMouth.xRot = 0.4F;
        LeftMouth.xRot = 0.4F;
        LowerMouth.xRot = 0.8F;
    }

}
