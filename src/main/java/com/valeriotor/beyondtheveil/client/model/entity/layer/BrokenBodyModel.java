package com.valeriotor.beyondtheveil.client.model.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.entity.LivingAmmunitionEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class BrokenBodyModel extends AnimatedModel<LivingAmmunitionEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "brokenbodylayer"), "main");
    private static final String name = "broken_body";
    private final ModelPart lower;
    private final ModelPart upper;
    private final ModelPart upper_visible;
    private final ModelPart head;
    private final ModelPart tr1;
    private final ModelPart tr6;
    private final ModelPart tr2;
    private final ModelPart tr3;
    private final ModelPart tr4;
    private final ModelPart tr5;
    private final ModelPart tl1;
    private final ModelPart tl6;
    private final ModelPart tl2;
    private final ModelPart tl3;
    private final ModelPart tl4;
    private final ModelPart tl5;
    private final ModelPart fake_skin1;
    private final ModelPart fake_skin2;
    private final ModelPart[] teeth;

    public BrokenBodyModel(ModelPart root) {
        super(name);
        this.lower = registerAnimatedPart(root, "lower", false);
        this.upper = registerAnimatedPart(root, "upper", false);
        this.upper_visible = registerAnimatedPart(root, "upper_visible");
        this.head = registerAnimatedPart(this.upper_visible, "head");
        this.tr1 = registerAnimatedPart(this.head, "tr1");
        this.tr6 = registerAnimatedPart(this.head, "tr6");
        this.tr2 = registerAnimatedPart(this.head, "tr2");
        this.tr3 = registerAnimatedPart(this.head, "tr3");
        this.tr4 = registerAnimatedPart(this.head, "tr4");
        this.tr5 = registerAnimatedPart(this.head, "tr5");
        this.tl1 = registerAnimatedPart(this.head, "tl1");
        this.tl6 = registerAnimatedPart(this.head, "tl6");
        this.tl2 = registerAnimatedPart(this.head, "tl2");
        this.tl3 = registerAnimatedPart(this.head, "tl3");
        this.tl4 = registerAnimatedPart(this.head, "tl4");
        this.tl5 = registerAnimatedPart(this.head, "tl5");
        this.fake_skin1 = registerAnimatedPart(root, "fake_skin1");
        this.fake_skin2 = registerAnimatedPart(fake_skin1, "fake_skin2");
        teeth = new ModelPart[]{tr1, tr6, tr2, tr3, tr4, tr5, tl1, tl6, tl2, tl3, tl4, tl5};
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition lower = partdefinition.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(16, 38).addBox(-4.0F, -16.0F, -3.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition upper = partdefinition.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition upper_visible = partdefinition.addOrReplaceChild("upper_visible", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = upper_visible.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 58).addBox(-3.0F, -8.85F, -2.5F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(1, 58).addBox(-2.0F, -9.35F, -2.45F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(5, 55).addBox(-1.75F, -7.9F, 1.75F, 3.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 56).addBox(0.0F, -3.1F, -1.95F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -5.0F, -0.5F, 0.0F, 0.1309F, -0.3491F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 53).addBox(0.0F, -6.1F, -2.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, 0.0F, -0.5F, 0.0F, 0.1309F, 0.0F));

        PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 59).addBox(-1.0F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 2.0F, 0.2174F, 0.4802F, 0.4461F));

        PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(3, 59).addBox(0.0F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 2.0F, 0.2174F, -0.4802F, -0.4461F));

        PartDefinition cube_r5 = head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(2, 55).addBox(0.0F, -6.0F, -3.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r6 = head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 56).addBox(-3.0F, -2.1F, -1.95F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.0F, -0.5F, 0.0F, -0.1309F, 0.3491F));

        PartDefinition cube_r7 = head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 53).addBox(-3.0F, -6.1F, -2.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 0.0F, -0.5F, 0.0F, -0.1309F, 0.0F));

        PartDefinition cube_r8 = head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(4, 55).addBox(-1.0F, 0.0F, -2.9F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 2.0F, -0.0752F, 0.5187F, -0.1509F));

        PartDefinition cube_r9 = head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(4, 55).addBox(-1.0F, -6.0F, -3.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition tr1 = head.addOrReplaceChild("tr1", CubeListBuilder.create().texOffs(1, 1).addBox(-0.25F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.25F, -5.5F, -2.0F));

        PartDefinition tr6 = head.addOrReplaceChild("tr6", CubeListBuilder.create().texOffs(1, 1).addBox(-0.25F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, -6.5F, -1.75F, 0.0F, 0.0F, 0.1745F));

        PartDefinition tr2 = head.addOrReplaceChild("tr2", CubeListBuilder.create().texOffs(1, 1).addBox(-0.25F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.25F, -4.5F, -1.75F));

        PartDefinition tr3 = head.addOrReplaceChild("tr3", CubeListBuilder.create().texOffs(1, 1).addBox(-0.25F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.25F, -3.5F, -2.0F));

        PartDefinition tr4 = head.addOrReplaceChild("tr4", CubeListBuilder.create().texOffs(1, 1).addBox(-0.25F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.25F, -2.5F, -1.75F));

        PartDefinition tr5 = head.addOrReplaceChild("tr5", CubeListBuilder.create().texOffs(1, 1).addBox(-0.25F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.25F, -1.5F, -2.0F));

        PartDefinition tl1 = head.addOrReplaceChild("tl1", CubeListBuilder.create().texOffs(1, 1).addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, -5.75F, -1.75F));

        PartDefinition tl6 = head.addOrReplaceChild("tl6", CubeListBuilder.create().texOffs(1, 1).addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -6.75F, -2.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition tl2 = head.addOrReplaceChild("tl2", CubeListBuilder.create().texOffs(1, 1).addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, -4.75F, -2.0F));

        PartDefinition tl3 = head.addOrReplaceChild("tl3", CubeListBuilder.create().texOffs(1, 1).addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, -3.75F, -1.75F));

        PartDefinition tl4 = head.addOrReplaceChild("tl4", CubeListBuilder.create().texOffs(1, 1).addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, -2.75F, -2.0F));

        PartDefinition tl5 = head.addOrReplaceChild("tl5", CubeListBuilder.create().texOffs(1, 1).addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, -1.75F, -1.75F));

        PartDefinition fake_skin1 = partdefinition.addOrReplaceChild("fake_skin1", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition fake_skin2 = fake_skin1.addOrReplaceChild("fake_skin2", CubeListBuilder.create().texOffs(0, 56).addBox(-3.0F, -24.1F, -3.25F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(2, 54).addBox(-2.0F, -22.1F, -3.25F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 54).addBox(-1.0F, -21.1F, -3.25F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        lower.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        upper.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        upper_visible.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        fake_skin1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void prepareMobModel(LivingAmmunitionEntity entity, float limbSwing, float limbSwingAmount, float pPartialTick) {
        markDirty();
        resetParts();
        float ageInTicks = entity.tickCount + pPartialTick;
        float offset1 = Mth.sin((float) Math.PI * 2 * ageInTicks / (24 * 1.5F)) / 15;
        float offset2 = Mth.sin((float) Math.PI * 2 * ageInTicks / (13 * 1.5F)) / 45;
        float offset3 = Mth.sin((float) Math.PI * 2 * ageInTicks / (17 * 1.5F)) / 45;
        head.zRot = 0.5236F + offset1;
        upper_visible.xRot = offset2;
        upper_visible.zRot = offset3;
        fake_skin1.xRot = offset2;
        fake_skin1.zRot = offset3;

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

        if (entity.getExplodingAnimation() == null) {
            head.zRot += Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1F * limbSwingAmount / f / 4.5;
            head.xRot += Mth.cos(limbSwing * 0.5662F + 1) * 1F * limbSwingAmount / f / 4.5;
        }


        Animation explodingAnimationBrokenBody = entity.getExplodingAnimationBrokenBody();
        if (explodingAnimationBrokenBody != null) {
            explodingAnimationBrokenBody.apply(pPartialTick);
        }
        //lower.xRot += Mth.PI * 5 / 36;
        //upper.xRot = - Mth.PI * 45 / 36;
        //upper.y -= 20;
        //upper.z += 90;
        //lower.visible = true;
        //upper.visible = true;
        for (int i = 0; i < teeth.length; i++) {
            ModelPart tooth = teeth[i];
            tooth.xScale = 1;
            tooth.yScale = tooth.zScale = 0.45F;
            tooth.yRot = switch (i % 3) {
                case 0 -> offset1;
                case 1 -> offset2 * 3;
                default -> offset3 * 3;
            } * 4;

        }
    }

    @Override
    public void setupAnim(LivingAmmunitionEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }
}
