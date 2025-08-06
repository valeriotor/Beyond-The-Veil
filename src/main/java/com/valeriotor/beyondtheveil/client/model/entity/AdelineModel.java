package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.ictya.AdelineEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class AdelineModel extends EntityModel<AdelineEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "adeline"), "main");
    private final ModelPart body;
    private final ModelPart body1;
    private final ModelPart body2;
    private final ModelPart head1;
    private final ModelPart spikeUp;
    private final ModelPart spikeUp1;
    private final ModelPart spikeRight;
    private final ModelPart spikeRight1;
    private final ModelPart spikeDown;
    private final ModelPart spikeDown1;
    private final ModelPart spikeLeft;
    private final ModelPart spikeLeft1;

    public AdelineModel(ModelPart root) {
        this.body = root.getChild("body");
        this.body1 = this.body.getChild("body1");
        this.body2 = this.body1.getChild("body2");
        this.head1 = this.body.getChild("head1");
        this.spikeUp = this.head1.getChild("spikeUp");
        this.spikeUp1 = this.spikeUp.getChild("spikeUp1");
        this.spikeRight = this.head1.getChild("spikeRight");
        this.spikeRight1 = this.spikeRight.getChild("spikeRight1");
        this.spikeDown = this.head1.getChild("spikeDown");
        this.spikeDown1 = this.spikeDown.getChild("spikeDown1");
        this.spikeLeft = this.head1.getChild("spikeLeft");
        this.spikeLeft1 = this.spikeLeft.getChild("spikeLeft1");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -10.0F, -12.5F, 20.0F, 20.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, -0.5F));

        PartDefinition body1 = body.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -6.0F, 0.0F, 12.0F, 12.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 16.0F));

        PartDefinition head1 = body.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -17.0F, 10.0F, 10.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -10.0F));

        PartDefinition spikeUp = head1.addOrReplaceChild("spikeUp", CubeListBuilder.create().texOffs(0, 54).addBox(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -17.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition spikeUp1 = spikeUp.addOrReplaceChild("spikeUp1", CubeListBuilder.create().texOffs(0, 54).addBox(-0.5F, -1.0F, -6.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition spikeRight = head1.addOrReplaceChild("spikeRight", CubeListBuilder.create().texOffs(0, 54).addBox(-1.0F, -7.0F, 0.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -0.5F, -17.0F, -0.6109F, 0.0F, -1.5708F));

        PartDefinition spikeRight1 = spikeRight.addOrReplaceChild("spikeRight1", CubeListBuilder.create().texOffs(0, 54).addBox(-6.0F, 4.0F, -6.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -12.0F, 0.0F));

        PartDefinition spikeDown = head1.addOrReplaceChild("spikeDown", CubeListBuilder.create().texOffs(0, 54).addBox(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -17.0F, -0.6109F, 0.0F, 3.1416F));

        PartDefinition spikeDown1 = spikeDown.addOrReplaceChild("spikeDown1", CubeListBuilder.create().texOffs(0, 54).addBox(-6.0F, 4.0F, -6.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, -12.0F, 0.0F));

        PartDefinition spikeLeft = head1.addOrReplaceChild("spikeLeft", CubeListBuilder.create().texOffs(0, 54).addBox(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, -17.0F, -0.6109F, 0.0F, 1.5708F));

        PartDefinition spikeLeft1 = spikeLeft.addOrReplaceChild("spikeLeft1", CubeListBuilder.create().texOffs(0, 54).addBox(-1.0F, -1.0F, -6.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -7.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(AdelineEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}
