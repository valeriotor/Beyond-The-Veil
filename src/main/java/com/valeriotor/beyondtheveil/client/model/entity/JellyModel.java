package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.ictya.JellyEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class JellyModel extends EntityModel<JellyEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "jelly"), "main");
    private final ModelPart bone;
    private final ModelPart T1;
    private final ModelPart T3;
    private final ModelPart T4;
    private final ModelPart T2;

    public JellyModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.T1 = this.bone.getChild("T1");
        this.T3 = this.bone.getChild("T3");
        this.T4 = this.bone.getChild("T4");
        this.T2 = this.bone.getChild("T2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(16, 57).addBox(-3.0F, -11.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(12, 59).addBox(-2.0F, -12.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition T1 = bone.addOrReplaceChild("T1", CubeListBuilder.create().texOffs(5, 32).addBox(-1.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -10.5F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition T3 = bone.addOrReplaceChild("T3", CubeListBuilder.create().texOffs(3, 33).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -10.5F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition T4 = bone.addOrReplaceChild("T4", CubeListBuilder.create().texOffs(5, 32).addBox(0.0F, 0.0F, 1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -10.5F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition T2 = bone.addOrReplaceChild("T2", CubeListBuilder.create().texOffs(1, 32).addBox(0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -10.5F, 0.0F, 0.0F, 0.0F, -0.3491F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(JellyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        T1.xRot = 0.3491F + Mth.cos(limbSwing * 0.0662F) * 0.7F * limbSwingAmount;
        T3.xRot = -0.3491F - Mth.cos(limbSwing * 0.0662F) * 0.7F * limbSwingAmount;
        T4.xRot = 0.3491F + Mth.cos(limbSwing * 0.0662F) * 0.7F * limbSwingAmount;
        T2.xRot = -0.3491F - Mth.cos(limbSwing * 0.0662F) * 0.7F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
