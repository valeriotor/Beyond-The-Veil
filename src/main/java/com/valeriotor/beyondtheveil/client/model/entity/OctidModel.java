package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.ictya.OctidEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class OctidModel extends EntityModel<OctidEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "octid"), "main");
    private final ModelPart Head;
    private final ModelPart TentacleSE;
    private final ModelPart TentacleSW;
    private final ModelPart TentacleNE;
    private final ModelPart TentacleNW;
    private final ModelPart TentacleW;
    private final ModelPart TentacleN;
    private final ModelPart TentacleE;
    private final ModelPart TentacleNW5;

    public OctidModel(ModelPart root) {
        this.Head = root.getChild("Head");
        this.TentacleSE = this.Head.getChild("TentacleSE");
        this.TentacleSW = this.Head.getChild("TentacleSW");
        this.TentacleNE = this.Head.getChild("TentacleNE");
        this.TentacleNW = this.Head.getChild("TentacleNW");
        this.TentacleW = this.Head.getChild("TentacleW");
        this.TentacleN = this.Head.getChild("TentacleN");
        this.TentacleE = this.Head.getChild("TentacleE");
        this.TentacleNW5 = this.Head.getChild("TentacleNW5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.5F, -3.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(1.5F, -5.5F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-2.5F, -5.5F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 30).addBox(-1.2F, -2.0F, -3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 30).addBox(-1.2F, -2.0F, 1.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 30).addBox(-2.2F, -2.0F, -2.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 30).addBox(-2.2F, -2.0F, 0.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 30).addBox(1.2F, -2.0F, -3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 30).addBox(1.2F, -2.0F, 1.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 30).addBox(2.2F, -2.0F, -2.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 30).addBox(2.2F, -2.0F, 0.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 16.5F, 0.5F, 0.5236F, 0.0F, 0.0F));

        PartDefinition TentacleSE = Head.addOrReplaceChild("TentacleSE", CubeListBuilder.create().texOffs(0, 22).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.4F, 0.5F, 1.4F));

        PartDefinition TentacleSW = Head.addOrReplaceChild("TentacleSW", CubeListBuilder.create().texOffs(0, 18).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.4F, 0.5F, 1.4F));

        PartDefinition TentacleNE = Head.addOrReplaceChild("TentacleNE", CubeListBuilder.create().texOffs(0, 10).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.4F, 0.5F, -2.4F));

        PartDefinition TentacleNW = Head.addOrReplaceChild("TentacleNW", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.4F, 0.5F, -2.4F));

        PartDefinition TentacleW = Head.addOrReplaceChild("TentacleW", CubeListBuilder.create().texOffs(4, 14).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.4F, 0.5F, -0.5F));

        PartDefinition TentacleN = Head.addOrReplaceChild("TentacleN", CubeListBuilder.create().texOffs(4, 10).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.5F, -2.4F));

        PartDefinition TentacleE = Head.addOrReplaceChild("TentacleE", CubeListBuilder.create().texOffs(4, 22).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.4F, 0.5F, -0.5F));

        PartDefinition TentacleNW5 = Head.addOrReplaceChild("TentacleNW5", CubeListBuilder.create().texOffs(4, 18).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.5F, 1.4F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(OctidEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
