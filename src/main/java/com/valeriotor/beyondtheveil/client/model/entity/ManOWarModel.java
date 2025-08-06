package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.ictya.ManOWarEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class ManOWarModel extends EntityModel<ManOWarEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "man_o_war"), "main");
    private final ModelPart Head;
    private final ModelPart Body;
    private final ModelPart Tentacle11;
    private final ModelPart Tentacle12;
    private final ModelPart Tentacle13;
    private final ModelPart Tentacle21;
    private final ModelPart Tentacle22;
    private final ModelPart Tentacle23;
    private final ModelPart Tentacle31;
    private final ModelPart Tentacle32;
    private final ModelPart Tentacle33;
    private final ModelPart Tentacle41;
    private final ModelPart Tentacle42;
    private final ModelPart Tentacle43;
    private final ModelPart MiniTentacle11;
    private final ModelPart MiniTentacle12;
    private final ModelPart MiniTentacle21;
    private final ModelPart MiniTentacle22;
    private final ModelPart DorsalFin;

    public ManOWarModel(ModelPart root) {
        this.Head = root.getChild("Head");
        this.Body = this.Head.getChild("Body");
        this.Tentacle11 = this.Body.getChild("Tentacle11");
        this.Tentacle12 = this.Tentacle11.getChild("Tentacle12");
        this.Tentacle13 = this.Tentacle12.getChild("Tentacle13");
        this.Tentacle21 = this.Body.getChild("Tentacle21");
        this.Tentacle22 = this.Tentacle21.getChild("Tentacle22");
        this.Tentacle23 = this.Tentacle22.getChild("Tentacle23");
        this.Tentacle31 = this.Body.getChild("Tentacle31");
        this.Tentacle32 = this.Tentacle31.getChild("Tentacle32");
        this.Tentacle33 = this.Tentacle32.getChild("Tentacle33");
        this.Tentacle41 = this.Body.getChild("Tentacle41");
        this.Tentacle42 = this.Tentacle41.getChild("Tentacle42");
        this.Tentacle43 = this.Tentacle42.getChild("Tentacle43");
        this.MiniTentacle11 = this.Body.getChild("MiniTentacle11");
        this.MiniTentacle12 = this.MiniTentacle11.getChild("MiniTentacle12");
        this.MiniTentacle21 = this.Body.getChild("MiniTentacle21");
        this.MiniTentacle22 = this.MiniTentacle21.getChild("MiniTentacle22");
        this.DorsalFin = this.Head.getChild("DorsalFin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 11).addBox(-6.0F, -0.5F, -11.0F, 12.0F, 1.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, 0.0F, 6.0F, 14.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 22).addBox(-7.0F, 0.0F, -12.0F, 14.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(6.0F, 0.0F, -11.0F, 1.0F, 5.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(28, 8).addBox(-7.0F, 0.0F, -11.0F, 1.0F, 5.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(34, 11).addBox(6.0F, 5.0F, -11.0F, 1.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(8, 13).addBox(-7.0F, 5.0F, -11.0F, 1.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(38, 23).addBox(-6.0F, 5.0F, -11.0F, 12.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.5F, -15.0F));

        PartDefinition Body = Head.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 46).addBox(-6.0F, 1.0F, -2.0F, 12.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Tentacle11 = Body.addOrReplaceChild("Tentacle11", CubeListBuilder.create().texOffs(56, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition Tentacle12 = Tentacle11.addOrReplaceChild("Tentacle12", CubeListBuilder.create().texOffs(52, 48).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.5F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Tentacle13 = Tentacle12.addOrReplaceChild("Tentacle13", CubeListBuilder.create().texOffs(44, 37).addBox(-0.45F, 0.0F, -0.5F, 1.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Tentacle21 = Body.addOrReplaceChild("Tentacle21", CubeListBuilder.create().texOffs(56, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 8.0F, 4.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition Tentacle22 = Tentacle21.addOrReplaceChild("Tentacle22", CubeListBuilder.create().texOffs(52, 48).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.5F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Tentacle23 = Tentacle22.addOrReplaceChild("Tentacle23", CubeListBuilder.create().texOffs(44, 37).addBox(-0.45F, 0.0F, -0.5F, 1.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Tentacle31 = Body.addOrReplaceChild("Tentacle31", CubeListBuilder.create().texOffs(56, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 8.0F, 4.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition Tentacle32 = Tentacle31.addOrReplaceChild("Tentacle32", CubeListBuilder.create().texOffs(52, 48).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.5F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Tentacle33 = Tentacle32.addOrReplaceChild("Tentacle33", CubeListBuilder.create().texOffs(44, 37).addBox(-0.45F, 0.0F, -0.5F, 1.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Tentacle41 = Body.addOrReplaceChild("Tentacle41", CubeListBuilder.create().texOffs(56, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 8.0F, 6.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition Tentacle42 = Tentacle41.addOrReplaceChild("Tentacle42", CubeListBuilder.create().texOffs(52, 48).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.5F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Tentacle43 = Tentacle42.addOrReplaceChild("Tentacle43", CubeListBuilder.create().texOffs(44, 37).mirror().addBox(-0.45F, 0.0F, -0.5F, 1.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition MiniTentacle11 = Body.addOrReplaceChild("MiniTentacle11", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, 0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(2, 34).addBox(0.0F, 1.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(2, 30).addBox(-1.0F, 2.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(2, 35).addBox(-1.0F, 3.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 32).addBox(0.0F, 4.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 36).addBox(0.0F, 5.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 8.0F, 1.0F));

        PartDefinition MiniTentacle12 = MiniTentacle11.addOrReplaceChild("MiniTentacle12", CubeListBuilder.create().texOffs(4, 34).addBox(-0.1667F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 31).addBox(-0.1667F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(-1.1667F, 2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 30).addBox(-1.1667F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 35).addBox(-0.1667F, 4.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(2, 35).addBox(-0.1667F, 5.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1667F, 6.5F, -1.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition MiniTentacle21 = Body.addOrReplaceChild("MiniTentacle21", CubeListBuilder.create().texOffs(4, 32).addBox(0.0F, 0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 35).addBox(0.0F, 1.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 36).addBox(-1.0F, 2.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-1.0F, 3.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(0.0F, 4.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 35).addBox(0.0F, 5.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 8.0F, 3.0F));

        PartDefinition MiniTentacle22 = MiniTentacle21.addOrReplaceChild("MiniTentacle22", CubeListBuilder.create().texOffs(7, 36).addBox(-0.1667F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 36).addBox(-0.1667F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 30).addBox(-1.1667F, 2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 35).addBox(-1.1667F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 35).addBox(-0.1667F, 4.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-0.1667F, 5.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1667F, 6.5F, -1.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition DorsalFin = Head.addOrReplaceChild("DorsalFin", CubeListBuilder.create().texOffs(35, 12).addBox(-1.0F, -4.0F, -6.0F, 2.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-0.5F, -7.0F, -3.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(ManOWarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
