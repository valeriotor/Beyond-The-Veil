package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.ictya.SepiidEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SepiidModel extends EntityModel<SepiidEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "sepiid"), "main");
    private final ModelPart left;
    private final ModelPart right;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart head;
    private final ModelPart eye1;
    private final ModelPart eye2;
    private final ModelPart eye3;
    private final ModelPart eye4;
    private final ModelPart eye5;
    private float partialTick;

    public SepiidModel(ModelPart root) {
        this.left = root.getChild("left");
        this.right = root.getChild("right");
        this.tail1 = root.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.head = root.getChild("head");
        this.eye1 = this.head.getChild("eye1");
        this.eye2 = this.head.getChild("eye2");
        this.eye3 = this.head.getChild("eye3");
        this.eye4 = this.head.getChild("eye4");
        this.eye5 = this.head.getChild("eye5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition left = partdefinition.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 23).addBox(0.0F, -2.0F, 0.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(1, 1).addBox(1.25F, -1.1F, 2.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(-5, 27).addBox(2.0F, -0.6F, 4.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.75F, -7.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition right = partdefinition.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -2.0F, 0.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(1, 1).addBox(-6.25F, -1.1F, 2.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 27).addBox(-6.0F, -0.6F, 4.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.75F, -7.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition tail1 = partdefinition.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, -5.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(18, 9).addBox(-0.53F, -1.0F, 5.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(20, 17).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(24, 24).addBox(-1.0F, -0.75F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.5F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(4, 0).addBox(-1.0F, -3.25F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 17.25F, -7.0F));

        PartDefinition eye1 = head.addOrReplaceChild("eye1", CubeListBuilder.create().texOffs(28, 30).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -3.0F, -0.25F));

        PartDefinition eye2 = head.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(28, 30).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -3.0F, -0.25F));

        PartDefinition eye3 = head.addOrReplaceChild("eye3", CubeListBuilder.create().texOffs(28, 30).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -3.0F, -0.25F));

        PartDefinition eye4 = head.addOrReplaceChild("eye4", CubeListBuilder.create().texOffs(26, 28).addBox(-0.5F, -1.25F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.5F, -0.25F));

        PartDefinition eye5 = head.addOrReplaceChild("eye5", CubeListBuilder.create().texOffs(26, 28).addBox(-0.5F, -1.25F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.5F, -0.25F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(SepiidEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        eye1.xScale = eye1.yScale = eye1.zScale = 0.5F;
        eye2.xScale = eye2.yScale = eye2.zScale = 0.5F;
        eye3.xScale = eye3.yScale = eye3.zScale = 0.5F;
        eye4.xScale = eye4.yScale = eye4.zScale = 0.5F;
        eye5.xScale = eye5.yScale = eye5.zScale = 0.5F;

        float f = 10.0F;
        tail1.xRot = Mth.cos(limbSwing * 0.5662F) * 1F * limbSwingAmount / f;
        tail2.xRot = Mth.cos(limbSwing * 0.5662F) * 1F * limbSwingAmount / f;
        tail3.xRot = Mth.cos(limbSwing * 0.5662F) * 1F * limbSwingAmount / f;

        float offset1 = Mth.sin((float) Math.PI * 2 * ageInTicks / (12 * 3.5F)) / 60;
        tail1.xRot += offset1;
        tail2.xRot += offset1;
        tail3.xRot += offset1;
        float offset2 = Mth.sin((float) Math.PI * 2 * ageInTicks / (15 * 3.5F)) / 60;
        left.xRot = offset2;
        right.xRot = offset2;
        tail1.xRot += headPitch * ((float) Math.PI / 180F);
        left.xRot += headPitch * ((float) Math.PI / 180F);
        right.xRot += headPitch * ((float) Math.PI / 180F);

    }

    @Override
    public void prepareMobModel(SepiidEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
        partialTick = pPartialTick;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}
