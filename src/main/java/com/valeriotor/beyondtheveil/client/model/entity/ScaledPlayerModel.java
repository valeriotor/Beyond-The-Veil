package com.valeriotor.beyondtheveil.client.model.entity;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class ScaledPlayerModel extends HumanoidModel<LivingEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "scaled_player"), "main");

    public ScaledPlayerModel(ModelPart root) {
        super(root, RenderType::entityTranslucent);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
        PartDefinition root = mesh.getRoot();
        PartDefinition Head = root.getChild("head");
        PartDefinition Body = root.getChild("body");
        PartDefinition Right_Arm = root.getChild("right_arm");
        PartDefinition Left_Arm = root.getChild("left_arm");
        PartDefinition spike6 = Head.addOrReplaceChild("spike6", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-2.0F, -5.75F, -4.0F, 0.0F, -1.5708F, 0.5672F));

        PartDefinition spike7 = Head.addOrReplaceChild("spike7", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(2.75F, -3.75F, -4.0F, 0.0F, -1.5708F, -0.5672F));

        PartDefinition spike5 = Body.addOrReplaceChild("spike5", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-2.0F, 2.0F, -2.25F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spike8 = Body.addOrReplaceChild("spike8", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition spike9 = Body.addOrReplaceChild("spike9", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(2.0F, 4.75F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition spike10 = Body.addOrReplaceChild("spike10", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-3.0F, 6.5F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition spike11 = Body.addOrReplaceChild("spike11", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(1.25F, 8.5F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition spike12 = Body.addOrReplaceChild("spike12", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-1.75F, 10.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition spike1 = Right_Arm.addOrReplaceChild("spike1", CubeListBuilder.create().texOffs(24, 0).addBox(-5.5F, -1.5F, 0.9167F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offset(-0.5F, 1.5F, -2.1667F));

        PartDefinition spike2 = Right_Arm.addOrReplaceChild("spike2", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-3.0F, 6.5F, -1.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition spike3 = Left_Arm.addOrReplaceChild("spike3", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(3.0F, 2.5F, -1.5F, 0.0F, -2.3562F, 0.0F));

        PartDefinition spike4 = Left_Arm.addOrReplaceChild("spike4", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(3.0F, 8.75F, 1.5F, 0.0F, 3.1416F, 0.0F));

        PartDefinition hole3 = Head.addOrReplaceChild("hole3", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.25F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -1.75F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -3.0F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(52, 32).addBox(-2.125F, -2.75F, -1.07F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offset(3.0F, -2.5F, 3.25F));

        PartDefinition hole4 = Head.addOrReplaceChild("hole4", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.25F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -1.75F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -3.0F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(52, 32).addBox(-2.125F, -2.75F, -1.07F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(-0.75F, -7.5F, 1.5F, 1.5708F, 0.0F, 0.0F));

        PartDefinition hole5 = Head.addOrReplaceChild("hole5", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.25F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -1.75F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -3.0F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(52, 32).addBox(-2.125F, -2.75F, -1.07F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(-3.5F, -4.0F, 0.5F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition hole1 = Body.addOrReplaceChild("hole1", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.25F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -1.75F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -3.0F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(52, 32).addBox(-2.125F, -2.75F, -1.07F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offset(3.25F, 3.0F, 1.25F));

        PartDefinition hole2 = Body.addOrReplaceChild("hole2", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.25F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -1.75F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -3.0F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(52, 32).addBox(-2.125F, -2.75F, -1.07F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offset(0.25F, 6.75F, 1.25F));

        PartDefinition hole6 = Body.addOrReplaceChild("hole6", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.25F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -1.75F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -3.0F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(52, 32).addBox(-2.125F, -2.75F, -1.07F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(3.5F, 7.5F, -1.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition hole7 = Body.addOrReplaceChild("hole7", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.25F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -1.75F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -3.0F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(52, 32).addBox(-2.125F, -2.75F, -1.07F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(1.75F, 5.5F, -1.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition hole8 = Body.addOrReplaceChild("hole8", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.25F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -1.75F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(8, 0).addBox(-2.125F, -3.0F, -0.97F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F))
                .texOffs(52, 32).addBox(-2.125F, -2.75F, -1.07F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(-5.25F, 1.25F, -1.0F, 3.1416F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }
}
