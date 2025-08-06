package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.ictya.UmancalaEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class UmancalaModel extends EntityModel<UmancalaEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "umancala"), "main");
    private final ModelPart node;
    private final ModelPart head1;
    private final ModelPart head2;
    private final ModelPart leg1;
    private final ModelPart leg11;
    private final ModelPart leg12;
    private final ModelPart leg2;
    private final ModelPart leg21;
    private final ModelPart leg22;
    private final ModelPart tentUp;
    private final ModelPart tentRight;
    private final ModelPart tentLeft;
    private final ModelPart tentDown;

    public UmancalaModel(ModelPart root) {
        this.node = root.getChild("node");
        this.head1 = this.node.getChild("head1");
        this.head2 = this.head1.getChild("head2");
        this.leg1 = this.node.getChild("leg1");
        this.leg11 = this.leg1.getChild("leg11");
        this.leg12 = this.leg11.getChild("leg12");
        this.leg2 = this.node.getChild("leg2");
        this.leg21 = this.leg2.getChild("leg21");
        this.leg22 = this.leg21.getChild("leg22");
        this.tentUp = this.node.getChild("tentUp");
        this.tentRight = this.node.getChild("tentRight");
        this.tentLeft = this.node.getChild("tentLeft");
        this.tentDown = this.node.getChild("tentDown");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition node = partdefinition.addOrReplaceChild("node", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, 0.0F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 0.0F));

        PartDefinition head1 = node.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -23.0F, 10.0F, 10.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(0, 58).mirror().addBox(4.75F, -1.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 58).mirror().addBox(4.75F, -0.5F, -12.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 58).mirror().addBox(4.75F, 0.0F, -21.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 58).addBox(-6.75F, -1.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 58).addBox(-6.75F, -0.5F, -12.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 58).addBox(-6.75F, 0.0F, -21.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head2 = head1.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -3.0F, -38.0F, 9.0F, 9.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(0, 58).mirror().addBox(4.0F, 0.75F, -30.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 58).addBox(-6.0F, 0.75F, -30.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg1 = node.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(66, 33).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 4.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 9.5F, -0.0873F, -0.0873F, 0.0F));

        PartDefinition leg11 = leg1.addOrReplaceChild("leg11", CubeListBuilder.create().texOffs(70, 35).addBox(-2.5F, -2.5F, -1.0F, 3.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, 27.0F));

        PartDefinition leg12 = leg11.addOrReplaceChild("leg12", CubeListBuilder.create().texOffs(70, 35).addBox(-2.51F, -2.51F, 1.0F, 3.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 21.0F));

        PartDefinition leg2 = node.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(66, 33).addBox(2.0F, -1.0F, 0.0F, 4.0F, 4.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 9.5F, -0.0873F, 0.0873F, 0.0F));

        PartDefinition leg21 = leg2.addOrReplaceChild("leg21", CubeListBuilder.create().texOffs(70, 35).addBox(1.5F, -2.5F, -1.0F, 3.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, 27.0F));

        PartDefinition leg22 = leg21.addOrReplaceChild("leg22", CubeListBuilder.create().texOffs(70, 35).addBox(1.51F, -2.51F, 1.0F, 3.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 21.0F));

        PartDefinition tentUp = node.addOrReplaceChild("tentUp", CubeListBuilder.create().texOffs(84, 38).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 10.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition tentRight = node.addOrReplaceChild("tentRight", CubeListBuilder.create().texOffs(72, 42).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.5F, 9.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition tentLeft = node.addOrReplaceChild("tentLeft", CubeListBuilder.create().texOffs(77, 42).addBox(-1.0F, -1.0F, -1.25F, 2.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.5F, 9.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition tentDown = node.addOrReplaceChild("tentDown", CubeListBuilder.create().texOffs(72, 42).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 10.0F, -0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(UmancalaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        node.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
