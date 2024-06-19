package com.valeriotor.beyondtheveil.client.model.entity;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.LivingAmmunitionEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class LivingAmmunitionModel extends AnimatedModel<LivingAmmunitionEntity> implements HeadedModel, VillagerHeadModel {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "living_ammunition"), "main");
    private static final String name = "living_ammunition";

    private final ModelPart body;
    private final ModelPart legs;
    private final ModelPart main;
    private final ModelPart cloth;
    private final ModelPart head;
    private final ModelPart helmet;
    private final ModelPart brim;
    private final ModelPart nose;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightLowerLeg;
    private final ModelPart leftLowerLeg;
    private final ModelPart arms;

    public LivingAmmunitionModel(ModelPart root) {
        super(name);
        this.body = registerAnimatedPart("body", root.getChild("body"));
        this.legs = registerAnimatedPart("legs", root.getChild("legs"));
        this.cloth = registerAnimatedPart("cloth", body.getChild("cloth"));
        this.main = registerAnimatedPart("main", body.getChild("main"));
        this.head = registerAnimatedPart("head", body.getChild("head"));
        this.helmet = registerAnimatedPart("helmet", head.getChild("helmet"));
        this.brim = registerAnimatedPart("brim", head.getChild("brim"));
        this.nose = registerAnimatedPart("nose", head.getChild("nose"));
        this.rightLeg = registerAnimatedPart("rightLeg", legs.getChild("RightLeg"));
        this.leftLeg = registerAnimatedPart("leftLeg", legs.getChild("LeftLeg"));
        this.rightLowerLeg = registerAnimatedPart("rightLowerLeg", rightLeg.getChild("RightLowerLeg"));
        this.leftLowerLeg = registerAnimatedPart("leftLowerLeg", leftLeg.getChild("LeftLowerLeg"));
        this.arms = registerAnimatedPart("arms", body.getChild("arms"));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition main = body.addOrReplaceChild("main", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cloth = body.addOrReplaceChild("cloth", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition brim = head.addOrReplaceChild("brim", CubeListBuilder.create().texOffs(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition LeftLeg = legs.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -12.0F, 0.0F, -0.1745F, 0.0F, -0.0349F));

        PartDefinition LeftLowerLeg = LeftLeg.addOrReplaceChild("LeftLowerLeg", CubeListBuilder.create().texOffs(0, 30).mirror().addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(4, 22).addBox(-2.0F, 12.01F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition RightLeg = legs.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -12.0F, 0.0F, 0.192F, 0.0F, 0.0349F));

        PartDefinition RightLowerLeg = RightLeg.addOrReplaceChild("RightLowerLeg", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(4, 22).mirror().addBox(-2.0F, 12.01F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingAmmunitionEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        //body.xRot = - Mth.PI * 45 / 36;
        //legs.xRot += Mth.PI * 5 / 36;
        //head.xRot = - Mth.PI * 15 / 36;
        //rightLeg.xRot = Mth.PI * 10 / 36;
        //arms.xRot = -1.75F;
        //cloth.visible = false;
        //main.visible = false;
        //body.y -= 20;
        //body.z += 90;
        //leftLeg.z -= 5.5F;
        //rightLeg.z -= 5.5F;
        //leftLeg.y += 2;
        //rightLeg.y += 2;
        //main.xScale = 1.25F;
        //main.yScale = 1.25F;
        //main.y = 5;
        //main.zScale = 1.25F;
    }

    @Override
    public void prepareMobModel(LivingAmmunitionEntity entity, float limbSwing, float limbSwingAmount, float pPartialTick) {
        resetParts();
        float ageInTicks = entity.tickCount + pPartialTick;
        float offset1 = Mth.sin((float) Math.PI * 2 * ageInTicks / (24 * 1.5F)) / 15;
        float offset2 = Mth.sin((float) Math.PI * 2 * ageInTicks / (13 * 1.5F)) / 45;
        float offset3 = Mth.sin((float) Math.PI * 2 * ageInTicks / (17 * 1.5F)) / 45;
        head.zRot = 0.5236F + offset1;
        body.xRot = offset2;
        body.zRot = offset3;

        boolean flag = entity.getFallFlyingTicks() > 4;

        float f = 1.0F;
        if (flag) {
            f = (float) entity.getDeltaMovement().lengthSqr();
            f /= 0.2F;
            f *= f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        if(entity.getExplodingAnimation() == null) {
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1F * limbSwingAmount / f;
            this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1F * limbSwingAmount / f;
            head.zRot += Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1F * limbSwingAmount / f / 4.5;
            head.xRot += Mth.cos(limbSwing * 0.5662F + 1) * 1F * limbSwingAmount / f / 4.5;
        }

        Animation explodingAnimation = entity.getExplodingAnimation();
        if (explodingAnimation != null) {
            explodingAnimation.apply(pPartialTick);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getHead() {
        return head;
    }

    @Override
    public void hatVisible(boolean pVisible) {
        head.visible = pVisible;
        helmet.visible = pVisible;
        brim.visible = pVisible;
    }
}