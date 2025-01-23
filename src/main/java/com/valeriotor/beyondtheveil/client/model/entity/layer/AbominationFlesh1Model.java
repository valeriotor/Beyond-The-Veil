package com.valeriotor.beyondtheveil.client.model.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;
import com.valeriotor.beyondtheveil.entity.LivingAmmunitionEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AbominationFlesh1Model extends AnimatedModel<LivingAmmunitionEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "abomination_flesh_1"), "main");
    private static final String name = "abomination_flesh_1";
    private final ModelPart cube0;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart cube1;
    private final ModelPart cube2;
    private final ModelPart cube3;
    private final ModelPart cube4;
    private final ModelPart cube5;
    private final ModelPart cube6;
    private final ModelPart cube7;
    private final ModelPart cube8;

    public AbominationFlesh1Model(ModelPart root) {
        super(name);
        this.cube0 = registerAnimatedPart("cube0", root.getChild("cube0"));
        this.head = registerAnimatedPart("head", root.getChild("head"));
        this.nose = registerAnimatedPart("nose", this.head.getChild("nose"));
        this.cube1 = registerAnimatedPart("cube1", root.getChild("cube1"));
        this.cube2 = registerAnimatedPart("cube2", root.getChild("cube2"));
        this.cube3 = registerAnimatedPart("cube3", root.getChild("cube3"));
        this.cube4 = registerAnimatedPart("cube4", root.getChild("cube4"));
        this.cube5 = registerAnimatedPart("cube5", root.getChild("cube5"));
        this.cube6 = registerAnimatedPart("cube6", root.getChild("cube6"));
        this.cube7 = registerAnimatedPart("cube7", root.getChild("cube7"));
        this.cube8 = registerAnimatedPart("cube8", root.getChild("cube8"));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition cube0 = partdefinition.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(0, 57).addBox(-4.0F, -3.0F, -6.25F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-7.0F, -3.0F, -6.0F, 13.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -3.0F, 0.0F, 0.3491F, 0.0F, -0.5236F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition cube1 = partdefinition.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(-3, 57).addBox(-2.0F, -3.25F, 3.25F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 56).addBox(1.0F, -2.25F, 3.25F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 57).addBox(2.0F, -3.0F, -5.25F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(-3, 57).addBox(-2.0F, -3.25F, -5.25F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(22, 19).addBox(-6.0F, -3.0F, -5.0F, 10.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube2 = partdefinition.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(36, 21).addBox(-6.0F, -3.0F, -2.0F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -1.0F, 0.0F));

        PartDefinition cube3 = partdefinition.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(0, 60).addBox(-7.0F, -2.0F, -5.25F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(22, 24).addBox(-8.0F, -3.0F, -5.0F, 10.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 6.0F, 0.0F));

        PartDefinition cube4 = partdefinition.addOrReplaceChild("cube4", CubeListBuilder.create().texOffs(2, 56).addBox(-1.0F, -5.0F, -5.25F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 27).addBox(-6.0F, -3.0F, -4.0F, 10.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 0.0F));

        PartDefinition cube5 = partdefinition.addOrReplaceChild("cube5", CubeListBuilder.create().texOffs(28, 28).addBox(-6.0F, -31.0F, -4.0F, 8.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition cube6 = partdefinition.addOrReplaceChild("cube6", CubeListBuilder.create().texOffs(1, 26).addBox(2.0F, -30.0F, -1.0F, 7.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition cube7 = partdefinition.addOrReplaceChild("cube7", CubeListBuilder.create().texOffs(48, 26).addBox(2.0F, -29.0F, -4.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition cube8 = partdefinition.addOrReplaceChild("cube8", CubeListBuilder.create().texOffs(0, 21).addBox(-8.25F, -29.0F, -5.1F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingAmmunitionEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        float offset1 = Mth.sin((float) Math.PI * 2 * ageInTicks / (12 * 1.5F)) / 10;
        float offset2 = Mth.sin((float) Math.PI * 2 * ageInTicks / (13 * 1.5F)) / 10;
        float offset3 = Mth.sin((float) Math.PI * 2 * ageInTicks / (14 * 1.5F)) / 10;
        float offset4 = Mth.sin((float) Math.PI * 2 * ageInTicks / (15 * 1.5F)) / 10;
        float offset5 = Mth.sin((float) Math.PI * 2 * ageInTicks / (16 * 1.5F)) / 10;
        float offset6 = Mth.sin((float) Math.PI * 2 * ageInTicks / (17 * 1.5F)) / 10;
        float offset7 = Mth.sin((float) Math.PI * 2 * ageInTicks / (18 * 1.5F)) / 10;
        float offset8 = Mth.sin((float) Math.PI * 2 * ageInTicks / (19 * 1.5F)) / 10;
        float offset9 = Mth.sin((float) Math.PI * 2 * ageInTicks / (20 * 1.5F)) / 10;
        cube0.x = 0.0F + offset1; cube0.y = 3.0F + offset9;
        cube1.x = 0.0F + offset2; cube1.y = 0.0F + offset1;
        cube2.x = -4.0F + offset3; cube2.y = -1.0F + offset2;
        cube3.x = 3.0F + offset4; cube3.y = 6.0F + offset3;
        cube4.x = 0.0F + offset5; cube4.y = 9.0F + offset4;
        cube5.x = 0.0F + offset6; cube5.y = 25.0F + offset5;
        cube6.x = 0.0F + offset7; cube6.y = 25.0F + offset6;
        cube7.x = 0.0F + offset8; cube7.y = 25.0F + offset7;
        cube8.x = 0.0F + offset9; cube8.y = 25.0F + offset8;
        head.x = -6.0F + offset9; head.y = -3.0F + offset8;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        cube0.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cube1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cube2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cube3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cube4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cube5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cube6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cube7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cube8.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}
