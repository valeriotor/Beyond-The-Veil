package com.valeriotor.beyondtheveil.client.model.entity;
// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.CanoeEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class CanoeModel extends EntityModel<CanoeEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "canoe"), "main");
    private final ModelPart root;
    private final ModelPart Bottom;
    private final ModelPart LeftSide;
    private final ModelPart RightSide;
    private final ModelPart shape4;
    private final ModelPart shape4_1;
    private final ModelPart shape6;
    private final ModelPart shape7;
    private final ModelPart shape7_1;
    private final ModelPart shape9;
    private final ModelPart shape10;
    private final ModelPart shape11;
    private final ModelPart shape10_1;
    private final ModelPart shape4_2;
    private final ModelPart shape11_1;
    private final ModelPart shape15;

    public CanoeModel(ModelPart root) {
        this.root = root.getChild("root");
        this.Bottom = this.root.getChild("Bottom");
        this.LeftSide = this.root.getChild("LeftSide");
        this.RightSide = this.root.getChild("RightSide");
        this.shape4 = this.root.getChild("shape4");
        this.shape4_1 = this.root.getChild("shape4_1");
        this.shape6 = this.root.getChild("shape6");
        this.shape7 = this.root.getChild("shape7");
        this.shape7_1 = this.root.getChild("shape7_1");
        this.shape9 = this.root.getChild("shape9");
        this.shape10 = this.root.getChild("shape10");
        this.shape11 = this.root.getChild("shape11");
        this.shape10_1 = this.root.getChild("shape10_1");
        this.shape4_2 = this.root.getChild("shape4_2");
        this.shape11_1 = this.root.getChild("shape11_1");
        this.shape15 = this.root.getChild("shape15");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Bottom = root.addOrReplaceChild("Bottom", CubeListBuilder.create().texOffs(128, 0).addBox(-10.0F, 0.0F, -22.5F, 20.0F, 1.0F, 45.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition LeftSide = root.addOrReplaceChild("LeftSide", CubeListBuilder.create().texOffs(0, 7).addBox(-0.5F, 0.0F, -22.5F, 1.0F, 11.0F, 45.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 9.0F, 0.0F));

        PartDefinition RightSide = root.addOrReplaceChild("RightSide", CubeListBuilder.create().texOffs(0, 7).addBox(-0.5F, 0.0F, -22.5F, 1.0F, 11.0F, 45.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 9.0F, 0.0F));

        PartDefinition shape4 = root.addOrReplaceChild("shape4", CubeListBuilder.create().texOffs(200, 52).addBox(-20.0F, 0.0F, 0.0F, 20.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 9.0F, 22.5F));

        PartDefinition shape4_1 = root.addOrReplaceChild("shape4_1", CubeListBuilder.create().texOffs(200, 52).addBox(-20.0F, 0.0F, 0.0F, 20.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 9.0F, -23.5F));

        PartDefinition shape6 = root.addOrReplaceChild("shape6", CubeListBuilder.create().texOffs(70, 0).addBox(-8.0F, 0.0F, -11.0F, 16.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -22.4F));

        PartDefinition shape7 = root.addOrReplaceChild("shape7", CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 9.0F, -33.5F));

        PartDefinition shape7_1 = root.addOrReplaceChild("shape7_1", CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 9.0F, -33.5F));

        PartDefinition shape9 = root.addOrReplaceChild("shape9", CubeListBuilder.create().texOffs(80, 24).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 23.0F));

        PartDefinition shape10 = root.addOrReplaceChild("shape10", CubeListBuilder.create().texOffs(135, 28).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 33.0F));

        PartDefinition shape11 = root.addOrReplaceChild("shape11", CubeListBuilder.create().texOffs(120, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 40.0F));

        PartDefinition shape10_1 = root.addOrReplaceChild("shape10_1", CubeListBuilder.create().texOffs(135, 28).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -40.5F));

        PartDefinition shape4_2 = root.addOrReplaceChild("shape4_2", CubeListBuilder.create().texOffs(120, 14).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -33.5F));

        PartDefinition shape11_1 = root.addOrReplaceChild("shape11_1", CubeListBuilder.create().texOffs(120, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -45.5F));

        PartDefinition shape15 = root.addOrReplaceChild("shape15", CubeListBuilder.create().texOffs(0, 12).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, -11.0F));

        return LayerDefinition.create(meshdefinition, 256, 64);
    }

    @Override
    public void setupAnim(CanoeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}