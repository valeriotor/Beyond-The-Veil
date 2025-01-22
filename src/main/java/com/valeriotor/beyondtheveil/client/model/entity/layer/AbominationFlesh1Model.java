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

public class AbominationFlesh1Model extends AnimatedModel<LivingAmmunitionEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "abomination_flesh_1"), "main");
    private static final String name = "abomination_flesh_1";
    private final ModelPart bb_main;
    private final ModelPart head;
    private final ModelPart nose;

    public AbominationFlesh1Model(ModelPart root) {
        super(name);
        this.bb_main = registerAnimatedPart("bb_main", root.getChild("bb_main"));
        this.head = registerAnimatedPart("head", root.getChild("head"));
        this.nose = registerAnimatedPart("nose", head.getChild("nose"));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -4.0F, 0.0F, 0.3491F, 0.0F, -0.5236F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 18).addBox(-7.0F, -25.0F, -6.0F, 13.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(22, 19).addBox(-6.0F, -28.0F, -5.0F, 10.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(36, 21).addBox(-10.0F, -29.0F, -2.0F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(22, 24).addBox(-3.0F, -22.0F, -5.0F, 10.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 27).addBox(-6.0F, -19.0F, -4.0F, 10.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(28, 28).addBox(-6.0F, -31.0F, -4.0F, 8.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(1, 26).addBox(2.0F, -30.0F, -1.0F, 7.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(48, 26).addBox(2.0F, -29.0F, -4.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-8.25F, -29.0F, -5.1F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingAmmunitionEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}
