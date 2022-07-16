package com.valeriotor.beyondtheveil.client.model.entity;// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class DeepOneModel extends EntityModel<LivingEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "deep_one"), "main");
    private final ModelPart main_body;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart upper_body;
    private final ModelPart upper_body2;
    private final ModelPart head;
    private final ModelPart left_lower_jaw;
    private final ModelPart right_lower_jaw;

    public DeepOneModel(ModelPart root) {
        this.main_body = root.getChild("main_body");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
        this.upper_body = main_body.getChild("upper_body");
        this.upper_body2 = upper_body.getChild("upper_body2");
        this.head = upper_body2.getChild("head");
        this.left_lower_jaw = head.getChild("left_lower_jaw");
        this.right_lower_jaw = head.getChild("right_lower_jaw");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main_body = partdefinition.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(0, 36).addBox(-5.0F, 2.75F, 0.5F, 10.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(50, 26).addBox(0.0F, 6.0F, 6.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -1.0F));

        PartDefinition upper_body = main_body.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(26, 34).addBox(0.0F, -18.8889F, -7.3223F, 0.0F, 11.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(40, 25).addBox(-0.01F, -8.8889F, 2.6777F, 0.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.1111F, 1.8223F));

        PartDefinition cube_r1 = upper_body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(9, 0).addBox(-6.0F, -3.5F, -5.5F, 14.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.3195F, -0.8712F, 0.2618F, 0.0F, 0.0F));

        PartDefinition upper_body2 = upper_body.addOrReplaceChild("upper_body2", CubeListBuilder.create().texOffs(0, 24).addBox(-4.5F, -2.0F, -4.5F, 9.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(40, 15).addBox(-3.5F, 2.0F, -6.5F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.8889F, -1.3223F));

        PartDefinition head = upper_body2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 0).addBox(-4.0F, -4.0F, -9.5F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-3.5F, -3.0F, -10.5F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-3.0F, -0.25F, -13.0F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(15, 58).addBox(-0.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -0.5F, -7.5F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(15, 58).addBox(-1.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -0.5F, -7.5F, 0.0F, 0.3491F, 0.0F));

        PartDefinition left_lower_jaw = head.addOrReplaceChild("left_lower_jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, -6.01F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-2.5F, 0.99F, -6.01F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 5.0F, -6.5F));

        PartDefinition left_lower_jaw_end = left_lower_jaw.addOrReplaceChild("left_lower_jaw_end", CubeListBuilder.create().texOffs(44, 21).addBox(-2.0F, -2.0F, -0.02F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 1.0F, -6.0F));

        PartDefinition right_lower_jaw = head.addOrReplaceChild("right_lower_jaw", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.5F, -1.0F, -6.0F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 10).addBox(1.5F, 1.0F, -6.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 5.0F, -6.5F));

        PartDefinition right_lower_jaw_end = right_lower_jaw.addOrReplaceChild("right_lower_jaw_end", CubeListBuilder.create().texOffs(43, 21).addBox(2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 1.0F, -6.0F));

        PartDefinition left_arm = upper_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, -1.0F, -13.0F, 5.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(48, 19).addBox(2.5F, 2.5F, -17.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -1.8889F, -0.8223F, 1.6035F, -0.0349F, -0.2618F));

        PartDefinition left_forearm = left_arm.addOrReplaceChild("left_forearm", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, -2.0F, -14.0F, 4.0F, 4.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 2.5F, -12.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition left_arm2 = upper_body.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(0, 18).addBox(-5.0F, -1.0F, -13.0F, 5.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(48, 19).addBox(-2.5F, 2.5F, -17.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -1.8889F, -0.8223F, 1.8168F, 0.0349F, 0.2618F));

        PartDefinition left_forearm2 = left_arm2.addOrReplaceChild("left_forearm2", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, -2.0F, -14.0F, 4.0F, 4.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 2.5F, -12.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(4.0F, 4.5F, 2.0F));

        PartDefinition cube_r4 = left_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 24).addBox(-2.5F, -2.5F, -2.0F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, 0.0F, 0.0F));

        PartDefinition lower_left_leg = left_leg.addOrReplaceChild("lower_left_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, -5.5F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r5 = lower_left_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(22, 35).addBox(-1.5F, -1.0F, 0.0F, 4.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.5F, -1.5F, -0.0436F, 0.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 4.5F, 2.0F));

        PartDefinition cube_r6 = right_leg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 25).addBox(-2.5F, -2.5F, -2.0F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, 0.0F, 0.0F));

        PartDefinition lower_right_leg = right_leg.addOrReplaceChild("lower_right_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, -5.5F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r7 = lower_right_leg.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(21, 34).addBox(-1.5F, -1.0F, 0.0F, 4.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.5F, -1.5F, -0.0436F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //left_lower_jaw.xRot = (float) (32.5 * Math.PI / 180);
        //right_lower_jaw.xRot = (float) (32.5 * Math.PI / 180);
        //left_lower_jaw.yRot = (float) (-37.5 * Math.PI / 180);
        //right_lower_jaw.yRot = (float) (37.5 * Math.PI / 180);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        main_body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}