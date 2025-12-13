package com.valeriotor.beyondtheveil.client.model.entity;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.Abomination1Entity;
import com.valeriotor.beyondtheveil.entity.Abomination2Entity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class Abomination2Model extends AnimatedModel<LivingEntity> implements VillagerHeadModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "abomination_2"), "main");
    private static final String name = "abomination_2";
    private final ModelPart main_body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart flesh;
    private final ModelPart eye_front_bottom;
    private final ModelPart eye_left;
    private final ModelPart eye_front_mid;
    private final ModelPart eye_back_up;
    private final ModelPart eye_front_up;
    private final ModelPart eye_back_bottom;
    private final ModelPart eye_back_mid;
    private final ModelPart tendril_left_2;
    private final ModelPart tendril_left_2_lower;
    private final ModelPart tendril_left_1;
    private final ModelPart tendril_left_1_lower;
    private final ModelPart tendril_left_3;
    private final ModelPart tendril_left_3_lower;
    private final ModelPart tendril_right_1;
    private final ModelPart tendril_right_1_lower;
    private final ModelPart tendril_right_2;
    private final ModelPart tendril_right_2_lower;
    private final ModelPart tendril_right_3;
    private final ModelPart tendril_right_3_lower;
    private final ModelPart left_arm;
    private final ModelPart left_lower_arm;
    private final ModelPart right_arm;
    private final ModelPart right_lower_arm;
    private final ModelPart right_leg;
    private final ModelPart right_leg_lower;
    private final ModelPart left_leg;
    private final ModelPart left_leg_lower;

    public Abomination2Model(ModelPart root) {
        super(name);
        this.main_body = registerAnimatedPart(root, "main_body");
        this.neck = registerAnimatedPart(this.main_body, "neck");
        this.head = registerAnimatedPart(this.neck, "head");
        this.flesh = registerAnimatedPart(this.head, "flesh");
        this.eye_front_bottom = registerAnimatedPart(this.head, "eye_front_bottom");
        this.eye_left = registerAnimatedPart(this.head, "eye_left");
        this.eye_front_mid = registerAnimatedPart(this.head, "eye_front_mid");
        this.eye_back_up = registerAnimatedPart(this.head, "eye_back_up");
        this.eye_front_up = registerAnimatedPart(this.head, "eye_front_up");
        this.eye_back_bottom = registerAnimatedPart(this.head, "eye_back_bottom");
        this.eye_back_mid = registerAnimatedPart(this.head, "eye_back_mid");
        this.tendril_left_2 = registerAnimatedPart(this.head, "tendril_left_2");
        this.tendril_left_2_lower = registerAnimatedPart(this.tendril_left_2, "tendril_left_2_lower");
        this.tendril_left_1 = registerAnimatedPart(this.head, "tendril_left_1");
        this.tendril_left_1_lower = registerAnimatedPart(this.tendril_left_1, "tendril_left_1_lower");
        this.tendril_left_3 = registerAnimatedPart(this.head, "tendril_left_3");
        this.tendril_left_3_lower = registerAnimatedPart(this.tendril_left_3, "tendril_left_3_lower");
        this.tendril_right_1 = registerAnimatedPart(this.head, "tendril_right_1");
        this.tendril_right_1_lower = registerAnimatedPart(this.tendril_right_1, "tendril_right_1_lower");
        this.tendril_right_2 = registerAnimatedPart(this.head, "tendril_right_2");
        this.tendril_right_2_lower = registerAnimatedPart(this.tendril_right_2, "tendril_right_2_lower");
        this.tendril_right_3 = registerAnimatedPart(this.head, "tendril_right_3");
        this.tendril_right_3_lower = registerAnimatedPart(this.tendril_right_3, "tendril_right_3_lower");
        this.left_arm = registerAnimatedPart(this.main_body, "left_arm");
        this.left_lower_arm = registerAnimatedPart(this.left_arm, "left_lower_arm");
        this.right_arm = registerAnimatedPart(this.main_body, "right_arm");
        this.right_lower_arm = registerAnimatedPart(this.right_arm, "right_lower_arm");
        this.right_leg = registerAnimatedPart(root, "right_leg");
        this.right_leg_lower = registerAnimatedPart(this.right_leg, "right_leg_lower");
        this.left_leg = registerAnimatedPart(root, "left_leg");
        this.left_leg_lower = registerAnimatedPart(this.left_leg, "left_leg_lower");
    }

    public static LayerDefinition createBodyLayer() {



        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main_body = partdefinition.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(32, 19).addBox(-4.0F, -14.0F, -2.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 9).addBox(-5.0F, -11.0F, -3.0F, 10.0F, 4.0F, 8.0F, new CubeDeformation(-0.3F))
                .texOffs(32, 21).addBox(-4.5F, -7.75F, -2.5F, 9.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(36, 14).addBox(-4.0F, -4.0F, -2.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

        PartDefinition neck = main_body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(40, 0).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 1.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition flesh = head.addOrReplaceChild("flesh", CubeListBuilder.create().texOffs(3, 0).addBox(-5.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-10.0F, -4.5F, -4.25F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-13.0F, -5.5F, -4.75F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-13.0F, -11.5F, 1.5F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-4.0F, -11.5F, -1.75F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-12.0F, -10.5F, -1.75F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-7.0F, -12.5F, -3.75F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-12.25F, -10.75F, -5.5F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-8.0F, -11.25F, 1.25F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-4.0F, -5.5F, -4.75F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-4.5F, -6.0F, 0.25F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(2, 2).addBox(-11.25F, -5.75F, -1.0F, 8.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.25F, -9.25F, -3.0F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-15.25F, -9.25F, -6.0F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -10.0F, 0.0F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.25F, -9.75F, -5.75F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(3, 3).addBox(-8.0F, -4.75F, -0.75F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 1.0F, -0.75F));

        PartDefinition eye_front_bottom = head.addOrReplaceChild("eye_front_bottom", CubeListBuilder.create(), PartPose.offset(2.1995F, -2.2345F, -4.586F));

        PartDefinition cube_r1 = eye_front_bottom.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 52).addBox(-4.0F, -4.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(0.8005F, 1.2345F, -0.914F, 0.2212F, 0.1693F, 0.0475F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create(), PartPose.offset(-6.9012F, -9.0F, 2.1753F));

        PartDefinition cube_r2 = eye_left.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 52).addBox(-4.0F, -4.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-1.3488F, 1.0F, -0.4253F, 0.0F, 2.0508F, 0.0F));

        PartDefinition eye_front_mid = head.addOrReplaceChild("eye_front_mid", CubeListBuilder.create(), PartPose.offset(4.25F, -5.0F, -3.634F));

        PartDefinition cube_r3 = eye_front_mid.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 52).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, -0.866F, 0.0F, -0.5236F, 0.0F));

        PartDefinition eye_back_up = head.addOrReplaceChild("eye_back_up", CubeListBuilder.create(), PartPose.offset(4.4956F, -8.0F, 5.1073F));

        PartDefinition cube_r4 = eye_back_up.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 52).addBox(-4.0F, -4.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-0.2456F, 1.0F, 1.3927F, 0.0F, -2.5307F, 0.0F));

        PartDefinition eye_front_up = head.addOrReplaceChild("eye_front_up", CubeListBuilder.create(), PartPose.offset(-1.9571F, -9.0F, -5.0253F));

        PartDefinition cube_r5 = eye_front_up.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 52).addBox(-4.0F, -4.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(0.7071F, 1.0F, -1.2247F, 0.0F, 0.2618F, 0.0F));

        PartDefinition eye_back_bottom = head.addOrReplaceChild("eye_back_bottom", CubeListBuilder.create(), PartPose.offset(4.1389F, -1.9284F, 3.9395F));

        PartDefinition cube_r6 = eye_back_bottom.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 52).addBox(-4.0F, -4.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-1.1389F, 1.1784F, 0.5605F, -2.5049F, -0.3741F, -2.9216F));

        PartDefinition eye_back_mid = head.addOrReplaceChild("eye_back_mid", CubeListBuilder.create(), PartPose.offset(-5.1711F, -6.25F, 5.2663F));

        PartDefinition cube_r7 = eye_back_mid.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 52).addBox(-4.0F, -4.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-1.3289F, 1.0F, 0.4837F, 0.0F, 2.7053F, 0.0F));

        PartDefinition tendril_left_2 = head.addOrReplaceChild("tendril_left_2", CubeListBuilder.create(), PartPose.offset(7.0F, -2.5F, 0.0F));

        PartDefinition cube_r8 = tendril_left_2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(1, 0).addBox(0.0F, -2.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition tendril_left_2_lower = tendril_left_2.addOrReplaceChild("tendril_left_2_lower", CubeListBuilder.create().texOffs(1, 0).addBox(-0.5F, -0.5F, -0.8F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 5.5F, -0.25F));

        PartDefinition tendril_left_1 = head.addOrReplaceChild("tendril_left_1", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -2.5F, -3.0F, 0.0F, 0.7418F, 0.0F));

        PartDefinition cube_r9 = tendril_left_1.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(5, 0).addBox(0.0F, -2.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition tendril_left_1_lower = tendril_left_1.addOrReplaceChild("tendril_left_1_lower", CubeListBuilder.create().texOffs(5, 0).addBox(-0.5F, -0.5F, -0.8F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 5.5F, -0.25F));

        PartDefinition tendril_left_3 = head.addOrReplaceChild("tendril_left_3", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -2.5F, 4.0F, 0.0F, -0.829F, 0.0F));

        PartDefinition cube_r10 = tendril_left_3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(17, 0).addBox(0.0F, -2.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition tendril_left_3_lower = tendril_left_3.addOrReplaceChild("tendril_left_3_lower", CubeListBuilder.create().texOffs(17, 0).addBox(-0.5F, -0.5F, -0.8F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 5.5F, -0.25F));

        PartDefinition tendril_right_1 = head.addOrReplaceChild("tendril_right_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, -2.5F, -3.0F, 0.0F, 2.3998F, 0.0F));

        PartDefinition cube_r11 = tendril_right_1.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(5, 0).addBox(0.0F, -2.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition tendril_right_1_lower = tendril_right_1.addOrReplaceChild("tendril_right_1_lower", CubeListBuilder.create().texOffs(5, 0).addBox(-0.5F, -0.5F, -0.7F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 5.5F, -0.25F));

        PartDefinition tendril_right_2 = head.addOrReplaceChild("tendril_right_2", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, -2.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r12 = tendril_right_2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(14, 0).addBox(0.0F, -2.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition tendril_right_2_lower = tendril_right_2.addOrReplaceChild("tendril_right_2_lower", CubeListBuilder.create().texOffs(14, 0).addBox(-0.5F, -0.5F, -0.7F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 5.5F, -0.25F));

        PartDefinition tendril_right_3 = head.addOrReplaceChild("tendril_right_3", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.0F, -2.5F, 3.0F, 0.0F, -2.4435F, 0.0F));

        PartDefinition cube_r13 = tendril_right_3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(19, 0).addBox(0.0F, -2.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition tendril_right_3_lower = tendril_right_3.addOrReplaceChild("tendril_right_3_lower", CubeListBuilder.create().texOffs(19, 0).addBox(-0.5F, -0.5F, -0.7F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 5.5F, -0.25F));

        PartDefinition left_arm = main_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.75F, -10.0F, 1.0F, -1.5265F, 0.1744F, -0.0796F));

        PartDefinition left_lower_arm = left_arm.addOrReplaceChild("left_lower_arm", CubeListBuilder.create().texOffs(50, 20).addBox(-1.0F, -1.0F, -1.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 1.5272F));

        PartDefinition right_arm = main_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.75F, -10.0F, 1.0F, -0.829F, 0.0F, 0.0873F));

        PartDefinition right_lower_arm = right_arm.addOrReplaceChild("right_lower_arm", CubeListBuilder.create().texOffs(40, 19).addBox(-1.0F, -1.0F, -1.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, -1.6581F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(52, 20).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 12.5F, 0.5F, -0.2153F, -0.2073F, 0.1023F));

        PartDefinition right_leg_lower = right_leg.addOrReplaceChild("right_leg_lower", CubeListBuilder.create().texOffs(52, 19).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.25F, -0.25F, 0.4363F, 0.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(49, 19).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 12.5F, 0.5F, -0.1745F, 0.2618F, 0.0F));

        PartDefinition left_leg_lower = left_leg.addOrReplaceChild("left_leg_lower", CubeListBuilder.create().texOffs(52, 23).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.25F, -0.25F, 0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void prepareMobModel(LivingEntity entity, float limbSwing, float limbSwingAmount, float pPartialTick) {
        resetParts();
        float ageInTicks = entity.tickCount + pPartialTick;
        float offset1 = Mth.sin((float) Math.PI * 2 * ageInTicks / (24 * 1.5F)) / 15;
        float offset2 = Mth.sin((float) Math.PI * 2 * ageInTicks / (13 * 1.5F)) / 45;
        float offset3 = Mth.sin((float) Math.PI * 2 * ageInTicks / (17 * 1.5F)) / 45;
        float offset4 = Mth.sin((float) Math.PI * 2 * ageInTicks / (21 * 1.5F)) / 45;
        //head.zRot = 0.5236F + offset1;
        main_body.xRot = offset2;
        main_body.zRot = offset3;
        head.xRot = offset4 * 6 * 0.8F;
        head.zRot = offset1 * 2 * 0.8F;
        tendril_left_2.zRot = offset1 * 1.1F;
        tendril_left_1.zRot = offset3 * 3 * 1.1F;
        tendril_left_3.zRot = offset2 * 3 * 1.1F;
        tendril_right_1.zRot = offset2 * 3 * 1.1F;
        tendril_right_2.zRot = offset4 * 3 * 1.1F;
        tendril_right_3.zRot = offset3 * 3 * 1.1F;

        eye_front_bottom.yRot = offset1 * 2 / 3;
        eye_front_bottom.xRot = offset2 * 2;
        eye_left.yRot = offset3 * 2;
        eye_left.xRot = offset4 * 2;
        eye_front_mid.yRot = offset2 * 2;
        eye_front_mid.xRot = offset3 * 2;
        eye_back_up.yRot = offset4 * 2;
        eye_back_up.xRot = offset1 * 2 / 3;
        eye_front_up.yRot = offset4 * 2;
        eye_front_up.xRot = offset1 * 2 / 3;
        eye_back_bottom.yRot = offset3 * 2;
        eye_back_bottom.xRot = offset4 * 2;
        eye_back_mid.yRot = offset2 * 2;
        eye_back_mid.xRot = offset3 * 2;
        //head.xScale = 1.5F;
        //head.yScale = 1.5F;
        //head.zScale = 1.5F;
        //tendril_left_2.xScale = tendril_left_2.yScale = tendril_left_2.zScale = 0.75F;
        //tendril_left_1.xScale = tendril_left_1.yScale = tendril_left_1.zScale = 0.75F;
        //tendril_left_3.xScale = tendril_left_3.yScale = tendril_left_3.zScale = 0.75F;
        //tendril_right_1.xScale = tendril_right_1.yScale = tendril_right_1.zScale = 0.75F;
        //tendril_right_2.xScale = tendril_right_2.yScale = tendril_right_2.zScale = 0.75F;
        //tendril_right_3.xScale = tendril_right_3.yScale = tendril_right_3.zScale = 0.75F;
        //main_body.y = 17;
        //main_body.z = -5;
        //left_leg.y = 17;
        //left_leg.z = -5;
        //left_leg_lower.xRot = 1.5F;
        //right_leg.y = 17;
        //right_leg.z = -5;
        //right_leg_lower.xRot = 1.5F;
        //eye_front_bottom.z = -230;
        //eye_front_mid.z = -230;
        //eye_front_mid.y = -130;
        //eye_front_up.z = -130;
        //eye_front_up.y = -230;
        //eye_back_bottom.z = 230;
        //eye_back_mid.z = 230;
        //eye_back_mid.y = -130;
        //eye_back_up.z = 130;
        //eye_back_up.y = -230;
        //eye_left.x = -230;
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

        if (entity instanceof Abomination2Entity e) {
            if (e.getExplodingAnimation() == null) {
                this.right_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1F * limbSwingAmount / f / 1.4F;
                this.left_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1F * limbSwingAmount / f / 1.4F;
                main_body.zRot += Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1F * limbSwingAmount / f / 9.5;
                main_body.xRot += Mth.cos(limbSwing * 0.5662F + 1) * 1F * limbSwingAmount / f / 9.5;
            }

            Animation explodingAnimation = e.getExplodingAnimation();
            if (explodingAnimation != null) {
                explodingAnimation.apply(pPartialTick);
            }

        } else if (entity instanceof Player player) {
            this.right_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1F * limbSwingAmount / f / 1.4F;
            this.left_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1F * limbSwingAmount / f / 1.4F;
            main_body.zRot += Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1F * limbSwingAmount / f / 9.5;
            main_body.xRot += Mth.cos(limbSwing * 0.5662F + 1) * 1F * limbSwingAmount / f / 9.5;
            //main.xRot = 1;
            Animation anim = ClientData.getInstance().getPlayerAnimation(player.getUUID(), this);
            if (anim != null) {
                anim.apply(pPartialTick);
            }
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        main_body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void hatVisible(boolean pVisible) {

    }
}