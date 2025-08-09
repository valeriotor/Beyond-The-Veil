package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.ictya.BonecageEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class BonecageModel extends AnimatedModel<BonecageEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "bonecage"), "main");
    private static final String name = "bonecage";
    private final ModelPart head;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart fin1;
    private final ModelPart finUp1;
    private final ModelPart finRight1;
    private final ModelPart finDown1;
    private final ModelPart finLeft1;
    private final ModelPart tail4;
    private final ModelPart tail5;
    private final ModelPart tail6;
    private final ModelPart tail7;
    private final ModelPart fin2;
    private final ModelPart finUp2;
    private final ModelPart finRight2;
    private final ModelPart finDown2;
    private final ModelPart finLeft2;
    private final ModelPart head1;
    private final ModelPart head2;
    private final ModelPart spikeUp;
    private final ModelPart spikeUp1;
    private final ModelPart spikeUpLeft;
    private final ModelPart spikeUpLeft1;
    private final ModelPart spikeLeft;
    private final ModelPart spikeLeft1;
    private final ModelPart spikeDownLeft;
    private final ModelPart spikeDownLeft1;
    private final ModelPart spikeDown;
    private final ModelPart spikeDown1;
    private final ModelPart spikeDownRight;
    private final ModelPart spikeDownRight1;
    private final ModelPart spikeRight;
    private final ModelPart spikeRight1;
    private final ModelPart spikeUpRight;
    private final ModelPart spikeUpRight1;
    private final ModelPart[] toRotate;
    private final ModelPart full;
    private float pPartialTick;

    public BonecageModel(ModelPart root) {
        super(name);
        this.full = registerAnimatedPart(root, "full");
        this.head = registerAnimatedPart(full, "head");
        this.tail1 = registerAnimatedPart(head, "tail1");
        this.tail2 = registerAnimatedPart(tail1, "tail2");
        this.tail3 = registerAnimatedPart(tail2, "tail3");
        this.fin1 = registerAnimatedPart(tail3, "fin1");
        this.finUp1 = registerAnimatedPart(fin1, "finUp1");
        this.finRight1 = registerAnimatedPart(fin1, "finRight1");
        this.finDown1 = registerAnimatedPart(fin1, "finDown1");
        this.finLeft1 = registerAnimatedPart(fin1, "finLeft1");
        this.tail4 = registerAnimatedPart(tail3, "tail4");
        this.tail5 = registerAnimatedPart(tail4, "tail5");
        this.tail6 = registerAnimatedPart(tail5, "tail6");
        this.tail7 = registerAnimatedPart(tail6, "tail7");
        this.fin2 = registerAnimatedPart(tail5, "fin2");
        this.finUp2 = registerAnimatedPart(fin2, "finUp2");
        this.finRight2 = registerAnimatedPart(fin2, "finRight2");
        this.finDown2 = registerAnimatedPart(fin2, "finDown2");
        this.finLeft2 = registerAnimatedPart(fin2, "finLeft2");
        this.head1 = registerAnimatedPart(head, "head1");
        this.head2 = registerAnimatedPart(head1, "head2");
        this.spikeUp = registerAnimatedPart(head2, "spikeUp");
        this.spikeUp1 = registerAnimatedPart(spikeUp, "spikeUp1");
        this.spikeUpLeft = registerAnimatedPart(head2, "spikeUpLeft");
        this.spikeUpLeft1 = registerAnimatedPart(spikeUpLeft, "spikeUpLeft1");
        this.spikeLeft = registerAnimatedPart(head2, "spikeLeft");
        this.spikeLeft1 = registerAnimatedPart(spikeLeft, "spikeLeft1");
        this.spikeDownLeft = registerAnimatedPart(head2, "spikeDownLeft");
        this.spikeDownLeft1 = registerAnimatedPart(spikeDownLeft, "spikeDownLeft1");
        this.spikeDown = registerAnimatedPart(head2, "spikeDown");
        this.spikeDown1 = registerAnimatedPart(spikeDown, "spikeDown1");
        this.spikeDownRight = registerAnimatedPart(head2, "spikeDownRight");
        this.spikeDownRight1 = registerAnimatedPart(spikeDownRight, "spikeDownRight1");
        this.spikeRight = registerAnimatedPart(head2, "spikeRight");
        this.spikeRight1 = registerAnimatedPart(spikeRight, "spikeRight1");
        this.spikeUpRight = registerAnimatedPart(head2, "spikeUpRight");
        this.spikeUpRight1 = registerAnimatedPart(spikeUpRight, "spikeUpRight1");
        toRotate = new ModelPart[]{head, tail1, tail2, tail3, tail4, tail5, tail6, tail7};
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition full = partdefinition.addOrReplaceChild("full", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, 0.0F));

        PartDefinition head = full.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -51.0F));

        PartDefinition tail1 = head.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -0.5F, 16.0F, 16.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -0.5F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(49, 15).addBox(-7.9F, -8.01F, -0.5F, 16.0F, 16.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 21.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(25, 1).addBox(-8.0F, -8.0F, 1.5F, 16.0F, 16.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 19.0F));

        PartDefinition fin1 = tail3.addOrReplaceChild("fin1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -14.5F));

        PartDefinition finUp1 = fin1.addOrReplaceChild("finUp1", CubeListBuilder.create().texOffs(0, 87).addBox(0.0F, -26.0F, 15.0F, 0.0F, 18.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition finRight1 = fin1.addOrReplaceChild("finRight1", CubeListBuilder.create().texOffs(0, 87).addBox(0.0F, -26.0F, 15.0F, 0.0F, 18.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition finDown1 = fin1.addOrReplaceChild("finDown1", CubeListBuilder.create().texOffs(0, 87).addBox(0.0F, -26.0F, 15.0F, 0.0F, 18.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition finLeft1 = fin1.addOrReplaceChild("finLeft1", CubeListBuilder.create().texOffs(0, 87).addBox(0.0F, -26.0F, 15.0F, 0.0F, 18.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(39, 18).addBox(-7.5F, -7.5F, 0.5F, 15.0F, 15.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 24.0F));

        PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(74, 0).addBox(-6.5F, -6.5F, 1.5F, 13.0F, 13.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 22.0F));

        PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(60, 27).addBox(-5.5F, -5.5F, 1.5F, 11.0F, 11.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 23.0F));

        PartDefinition tail7 = tail6.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(27, 27).addBox(-5.0F, -5.0F, 0.5F, 10.0F, 10.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 24.0F));

        PartDefinition fin2 = tail5.addOrReplaceChild("fin2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -9.5F));

        PartDefinition finUp2 = fin2.addOrReplaceChild("finUp2", CubeListBuilder.create().texOffs(210, 0).addBox(0.0F, -24.0F, 15.0F, 0.0F, 18.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition finRight2 = fin2.addOrReplaceChild("finRight2", CubeListBuilder.create().texOffs(210, 0).addBox(0.0F, -24.0F, 15.0F, 0.0F, 18.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition finDown2 = fin2.addOrReplaceChild("finDown2", CubeListBuilder.create().texOffs(210, 0).addBox(0.0F, -23.0F, 15.0F, 0.0F, 18.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition finLeft2 = fin2.addOrReplaceChild("finLeft2", CubeListBuilder.create().texOffs(210, 0).addBox(0.0F, -24.0F, 15.0F, 0.0F, 18.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition head1 = head.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(179, 72).addBox(-6.0F, -6.0F, -20.0F, 12.0F, 12.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.25F, 2.0F));

        PartDefinition head2 = head1.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(183, 102).addBox(-4.0F, -5.0F, -13.0F, 8.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.25F, -19.0F));

        PartDefinition spikeUp = head2.addOrReplaceChild("spikeUp", CubeListBuilder.create().texOffs(92, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -13.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition spikeUp1 = spikeUp.addOrReplaceChild("spikeUp1", CubeListBuilder.create().texOffs(92, 46).addBox(-0.9F, -1.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 80.0F, 3.0543F, 0.0F, 0.0F));

        PartDefinition spikeUpLeft = head2.addOrReplaceChild("spikeUpLeft", CubeListBuilder.create().texOffs(92, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -5.0F, -13.0F, 0.2618F, 0.0F, 0.7854F));

        PartDefinition spikeUpLeft1 = spikeUpLeft.addOrReplaceChild("spikeUpLeft1", CubeListBuilder.create().texOffs(92, 46).addBox(-0.9F, -1.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 80.0F, 3.0543F, 0.0F, 0.0F));

        PartDefinition spikeLeft = head2.addOrReplaceChild("spikeLeft", CubeListBuilder.create().texOffs(92, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.0F, -13.0F, 0.2618F, 0.0F, 1.5708F));

        PartDefinition spikeLeft1 = spikeLeft.addOrReplaceChild("spikeLeft1", CubeListBuilder.create().texOffs(92, 46).addBox(-0.9F, -1.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 80.0F, 3.0543F, 0.0F, 0.0F));

        PartDefinition spikeDownLeft = head2.addOrReplaceChild("spikeDownLeft", CubeListBuilder.create().texOffs(92, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 3.0F, -13.0F, 0.2618F, 0.0F, 2.3562F));

        PartDefinition spikeDownLeft1 = spikeDownLeft.addOrReplaceChild("spikeDownLeft1", CubeListBuilder.create().texOffs(92, 46).addBox(-0.9F, -1.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 80.0F, 3.0543F, 0.0F, 0.0F));

        PartDefinition spikeDown = head2.addOrReplaceChild("spikeDown", CubeListBuilder.create().texOffs(92, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -13.0F, 0.2618F, 0.0F, 3.1416F));

        PartDefinition spikeDown1 = spikeDown.addOrReplaceChild("spikeDown1", CubeListBuilder.create().texOffs(92, 46).addBox(-0.9F, -1.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 80.0F, 3.0543F, 0.0F, 0.0F));

        PartDefinition spikeDownRight = head2.addOrReplaceChild("spikeDownRight", CubeListBuilder.create().texOffs(92, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 3.0F, -13.0F, 0.2618F, 0.0F, -2.3562F));

        PartDefinition spikeDownRight1 = spikeDownRight.addOrReplaceChild("spikeDownRight1", CubeListBuilder.create().texOffs(92, 46).addBox(-1.1F, -1.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 80.0F, 3.0543F, 0.0F, 0.0F));

        PartDefinition spikeRight = head2.addOrReplaceChild("spikeRight", CubeListBuilder.create().texOffs(92, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -1.0F, -13.0F, 0.2618F, 0.0F, -1.5708F));

        PartDefinition spikeRight1 = spikeRight.addOrReplaceChild("spikeRight1", CubeListBuilder.create().texOffs(92, 46).addBox(-1.1F, -1.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 80.0F, 3.0543F, 0.0F, 0.0F));

        PartDefinition spikeUpRight = head2.addOrReplaceChild("spikeUpRight", CubeListBuilder.create().texOffs(92, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -5.0F, -13.0F, 0.2618F, 0.0F, -0.7854F));

        PartDefinition spikeUpRight1 = spikeUpRight.addOrReplaceChild("spikeUpRight1", CubeListBuilder.create().texOffs(92, 46).addBox(-1.1F, -1.0F, 0.0F, 2.0F, 2.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 80.0F, 3.0543F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 128);
    }

    @Override
    public void setupAnim(BonecageEntity e, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        resetParts();
        full.xRot = headPitch * ((float)Math.PI / 180F);
        float frequency = 0.5F;
        float time = (float) (2 * Math.PI * (e.tickCount + pPartialTick) / 20 * frequency);
        for (int i = 0; i < toRotate.length; i++) {
            ModelPart part = toRotate[i];
            part.yRot = Mth.cos(time + (float) (2 * i * Math.PI / 7)) * (limbSwingAmount / 3 + 0.06F);
            part.xRot = Mth.cos(time + (float) (2 * (i + 1) * Math.PI / 7)) * (limbSwingAmount / 6 + 0.025F);
        }

        Animation attackAnimation = e.getAttackAnimation();
        if (attackAnimation != null) {
            attackAnimation.apply(pPartialTick);
        }
    }

    @Override
    public void prepareMobModel(BonecageEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
        this.pPartialTick = pPartialTick;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        full.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}
