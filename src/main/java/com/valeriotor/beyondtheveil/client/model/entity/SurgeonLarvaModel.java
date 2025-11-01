package com.valeriotor.beyondtheveil.client.model.entity;
// Made with Blockbench 5.0.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.SurgeonLarvaEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SurgeonLarvaModel extends EntityModel<SurgeonLarvaEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "surgeon_larva"), "main");
    private final ModelPart front;
    private final ModelPart segment2;
    private final ModelPart segment1;
    private final ModelPart front2;
    private final ModelPart segment9;
    private final ModelPart segment10;
    private final ModelPart segment11;
    private final ModelPart segment12;
    private final ModelPart segment13;
    private final ModelPart back;
    private final ModelPart segment3;
    private final ModelPart segment4;
    private final ModelPart back2;
    private final ModelPart segment5;
    private final ModelPart segment6;
    private final ModelPart segment7;
    private final ModelPart segment8;

    public SurgeonLarvaModel(ModelPart root) {
        this.front = root.getChild("front");
        this.segment2 = this.front.getChild("segment2");
        this.segment1 = this.front.getChild("segment1");
        this.front2 = this.front.getChild("front2");
        this.segment9 = this.front2.getChild("segment9");
        this.segment10 = this.front2.getChild("segment10");
        this.segment11 = this.front2.getChild("segment11");
        this.segment12 = this.front2.getChild("segment12");
        this.segment13 = this.front2.getChild("segment13");
        this.back = root.getChild("back");
        this.segment3 = this.back.getChild("segment3");
        this.segment4 = this.back.getChild("segment4");
        this.back2 = this.back.getChild("back2");
        this.segment5 = this.back2.getChild("segment5");
        this.segment6 = this.back2.getChild("segment6");
        this.segment7 = this.back2.getChild("segment7");
        this.segment8 = this.back2.getChild("segment8");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition front = partdefinition.addOrReplaceChild("front", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 2.0F));

        PartDefinition segment2 = front.addOrReplaceChild("segment2", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.0F, -2.0F, 16.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(8, 21).addBox(-7.5F, -14.5F, 2.0F, 15.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -3.0F));

        PartDefinition segment1 = front.addOrReplaceChild("segment1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.0F, -2.0F, 16.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(8, 21).addBox(-7.5F, -14.5F, 2.0F, 15.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -8.0F));

        PartDefinition front2 = front.addOrReplaceChild("front2", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -9.0F));

        PartDefinition segment9 = front2.addOrReplaceChild("segment9", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.0F, -2.0F, 16.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(8, 21).addBox(-7.5F, -14.5F, 2.0F, 15.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -4.0F));

        PartDefinition segment10 = front2.addOrReplaceChild("segment10", CubeListBuilder.create().texOffs(4, 2).addBox(-7.5F, -14.5F, -2.0F, 15.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 22).addBox(-7.0F, -14.0F, 0.0F, 14.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -7.0F));

        PartDefinition segment11 = front2.addOrReplaceChild("segment11", CubeListBuilder.create().texOffs(4, 2).addBox(-7.5F, -14.5F, -2.0F, 15.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 22).addBox(-7.0F, -14.0F, 0.0F, 14.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5F, -10.0F));

        PartDefinition segment12 = front2.addOrReplaceChild("segment12", CubeListBuilder.create().texOffs(6, 2).addBox(-6.0F, -13.0F, -2.0F, 14.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 23).addBox(-5.5F, -12.5F, 0.0F, 13.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 5.0F, -13.0F));

        PartDefinition segment13 = front2.addOrReplaceChild("segment13", CubeListBuilder.create().texOffs(9, 2).addBox(-3.0F, -10.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(9, 2).addBox(3.0F, -10.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(7, 2).addBox(-1.0F, -10.0F, -2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(1.5F, -9.5F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(0.5F, -9.5F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(-0.5F, -9.5F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(-1.5F, -9.5F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(-1.5F, -6.25F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(-0.5F, -6.25F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(0.5F, -6.25F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(1.5F, -6.25F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(1.75F, -7.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(1.75F, -7.75F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(-2.75F, -7.75F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 48).addBox(-2.75F, -7.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.75F))
                .texOffs(7, 2).addBox(-1.0F, -5.0F, -2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 23).addBox(-5.5F, -12.5F, 0.0F, 13.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 59).addBox(-1.5F, -8.5F, -0.5F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 5.0F, -16.0F));

        PartDefinition back = partdefinition.addOrReplaceChild("back", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, 2.0F));

        PartDefinition segment3 = back.addOrReplaceChild("segment3", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.0F, -2.0F, 16.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(8, 21).addBox(-7.5F, -14.5F, 2.0F, 15.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 2.0F));

        PartDefinition segment4 = back.addOrReplaceChild("segment4", CubeListBuilder.create().texOffs(4, 2).addBox(-7.5F, -14.5F, -2.0F, 15.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 22).addBox(-7.0F, -14.0F, 0.0F, 14.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, 7.0F));

        PartDefinition back2 = back.addOrReplaceChild("back2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 8.0F));

        PartDefinition segment5 = back2.addOrReplaceChild("segment5", CubeListBuilder.create().texOffs(4, 2).addBox(-7.5F, -14.5F, -2.0F, 15.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 22).addBox(-7.0F, -14.0F, 0.0F, 14.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 2.0F));

        PartDefinition segment6 = back2.addOrReplaceChild("segment6", CubeListBuilder.create().texOffs(6, 2).addBox(-6.0F, -13.0F, -2.0F, 14.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 23).addBox(-5.5F, -12.5F, 0.0F, 13.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 5.5F, 5.0F));

        PartDefinition segment7 = back2.addOrReplaceChild("segment7", CubeListBuilder.create().texOffs(6, 2).addBox(-6.0F, -13.0F, -2.0F, 14.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 23).addBox(-5.5F, -12.5F, 0.0F, 13.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 5.5F, 8.0F));

        PartDefinition segment8 = back2.addOrReplaceChild("segment8", CubeListBuilder.create().texOffs(8, 2).addBox(-5.0F, -11.5F, -2.0F, 12.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 2).addBox(-3.0F, -9.5F, 1.0F, 8.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 23).addBox(-4.5F, -11.0F, 0.0F, 11.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 5.75F, 11.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(SurgeonLarvaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float offset1 = Mth.sin((float) Math.PI * 2 * ageInTicks / (5 * 12 * 1.5F)) / 30;
        float offset2 = Mth.sin((float) Math.PI * 2 * ageInTicks / (5 * 18 * 1.5F)) / 30;
        float offset3 = Mth.cos((float) Math.PI * 2 * ageInTicks / (5 * 15 * 1.5F)) / 30;
        float offset4 = Mth.cos((float) Math.PI * 2 * ageInTicks / (5 * 7 * 1.5F)) / 30;
        front.yRot = offset1;
        front2.yRot = offset2;
        back.yRot = offset3;
        back2.yRot = offset4;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        front.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        back.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}