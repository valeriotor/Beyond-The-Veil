package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ShoremanModel extends AnimatedModel<ShoremanEntity>{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "shoreman"), "main");
    private static final String name = "shoreman";
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart LeftEye;
    private final ModelPart RightEye;
    private final ModelPart arms;
    private final ModelPart RightLeg;
    private final ModelPart LeftLeg;

    public ShoremanModel(ModelPart root) {
        super(name);
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.nose = this.head.getChild("nose");
        this.LeftEye = this.head.getChild("LeftEye");
        this.RightEye = this.head.getChild("RightEye");
        this.arms = this.body.getChild("arms");
        this.RightLeg = this.body.getChild("RightLeg");
        this.LeftLeg = this.body.getChild("LeftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition LeftEye = head.addOrReplaceChild("LeftEye", CubeListBuilder.create().texOffs(60, 62).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.75F, -3.75F, -3.55F));

        PartDefinition RightEye = head.addOrReplaceChild("RightEye", CubeListBuilder.create().texOffs(60, 62).addBox(-0.25F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -3.75F, -3.55F));

        PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition RightLeg = body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

        PartDefinition LeftLeg = body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    public void setupAnim(ShoremanEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        float halfRot = pNetHeadYaw * ((float) Math.PI / 180F) / 2;
        this.head.yRot = halfRot;
        if (pEntity.getProfession() != ShoremanEntity.ShoremanProfession.LIGHTHOUSE_KEEPER && pNetHeadYaw != 0) {
            if (pNetHeadYaw < 0) { // left
                this.LeftEye.x = 1.75F - Mth.clamp(Mth.sin(halfRot) * 0.75F * 2, -0.75F / 2, 0);
                this.RightEye.x = -2.0F - Mth.clamp(Mth.sin(halfRot) * 0.25F * 2, -0.25F / 2, 0);
            } else if (pNetHeadYaw > 0) { // right
                this.LeftEye.x = 1.75F - Mth.clamp(Mth.sin(halfRot) * 0.25F * 2, 0, 0.25F / 2);
                this.RightEye.x = -2.0F - Mth.clamp(Mth.sin(halfRot) * 0.75F * 2, 0, 0.75F / 2);
            }
        } else {
            this.LeftEye.x = 1.75F;
            this.RightEye.x = -2.0F;
        }
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
        this.head.zRot = 0.0F;

        this.RightLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount * 0.5F;
        this.LeftLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 1.4F * pLimbSwingAmount * 0.5F;
        this.RightLeg.yRot = 0.0F;
        this.LeftLeg.yRot = 0.0F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
