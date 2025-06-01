package com.valeriotor.beyondtheveil.client.model.entity.layer;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class NautilusCreakModel<T extends NautilusEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "nautilus_creak"), "main");
    private final ModelPart creak1;
    private final ModelPart creak2;
    private final ModelPart creak3;
    private final ModelPart bone;
    private boolean showCreak1;
    private boolean showCreak2;
    private boolean showCreak3;

    public NautilusCreakModel(ModelPart root) {
        this.creak1 = root.getChild("creak1");
        this.creak2 = root.getChild("creak2");
        this.creak3 = root.getChild("creak3");
        this.bone = this.creak3.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {


        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition creak1 = partdefinition.addOrReplaceChild("creak1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0621F, -2.0F, -3.9674F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0621F, -6.0F, -5.9674F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, -8.0F, -38.0F, 0.0F, -0.8727F, 0.0F));

        PartDefinition cube_r1 = creak1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -8.5F, 0.7826F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r2 = creak1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -5.5F, 0.7826F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r3 = creak1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, 8.75F, 1.7826F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r4 = creak1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, 9.25F, 2.0326F, 1.309F, 0.0F, 0.0F));

        PartDefinition cube_r5 = creak1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -18.75F, 4.5326F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r6 = creak1.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, -2.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -16.25F, 2.5326F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r7 = creak1.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -18.0F, 6.0326F, 1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r8 = creak1.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -23.75F, 4.5326F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r9 = creak1.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, 4.25F, 1.0326F, 1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r10 = creak1.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, -4).addBox(0.96F, 0.0F, -3.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, 8.75F, -3.7174F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r11 = creak1.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, 9.25F, -0.2174F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r12 = creak1.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, -4).addBox(0.96F, 0.0F, -3.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -11.25F, -2.9674F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r13 = creak1.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -17.75F, -2.9674F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r14 = creak1.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -13.0F, 0.7826F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r15 = creak1.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -18.0F, 2.5326F, -1.9635F, 0.0F, 0.0F));

        PartDefinition cube_r16 = creak1.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, -2).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -10.75F, 0.2826F, -1.8762F, 0.0F, 0.0F));

        PartDefinition cube_r17 = creak1.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -9.75F, -4.9674F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r18 = creak1.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -5.75F, -2.9674F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r19 = creak1.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -14.75F, 1.5326F, -1.7453F, 0.0F, 0.0F));

        PartDefinition cube_r20 = creak1.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -10.75F, 0.5326F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r21 = creak1.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, -2).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -6.75F, 2.2826F, -1.8762F, 0.0F, 0.0F));

        PartDefinition cube_r22 = creak1.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -1.0F, 0.7826F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r23 = creak1.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, 3.5F, -0.4674F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r24 = creak1.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -1.5F, -0.4674F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r25 = creak1.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, -4.0F, 2.0326F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r26 = creak1.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(0, -1).addBox(0.96F, -2.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8979F, 6.0F, -2.4674F, 0.2182F, 0.0F, 0.0F));

        PartDefinition creak2 = partdefinition.addOrReplaceChild("creak2", CubeListBuilder.create(), PartPose.offsetAndRotation(18.25F, -17.5F, -37.5F, 0.0F, 0.8727F, 0.0F));

        PartDefinition cube_r27 = creak2.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 1.0F, 1.4187F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r28 = creak2.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 4.0F, 1.4187F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r29 = creak2.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 5.5F, -4.0813F, -1.0908F, 0.0F, 0.0F));

        PartDefinition cube_r30 = creak2.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 8.75F, 2.6687F, 1.309F, 0.0F, 0.0F));

        PartDefinition cube_r31 = creak2.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -5.25F, 5.9187F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r32 = creak2.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, -2.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -1.25F, -4.3313F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r33 = creak2.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -3.0F, 0.1687F, 1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r34 = creak2.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 1.5F, 6.4187F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r35 = creak2.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -6.0F, 1.4187F, 1.6144F, 0.0F, 0.0F));

        PartDefinition cube_r36 = creak2.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 7.5F, -4.3313F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r37 = creak2.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 9.0F, 1.4187F, -1.7017F, 0.0F, 0.0F));

        PartDefinition cube_r38 = creak2.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(269, -4).addBox(0.96F, 0.0F, -3.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -1.75F, -2.3313F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r39 = creak2.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -3.75F, -2.8313F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r40 = creak2.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(274, 1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -4.75F, 3.4187F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r41 = creak2.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -4.0F, 4.1687F, -2.2253F, 0.0F, 0.0F));

        PartDefinition cube_r42 = creak2.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 5.0F, -2.3313F, 0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r43 = creak2.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(271, -2).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -1.25F, 0.9187F, -1.8762F, 0.0F, 0.0F));

        PartDefinition cube_r44 = creak2.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -0.25F, -4.3313F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r45 = creak2.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -0.25F, 2.6687F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r46 = creak2.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 2.75F, 2.6687F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r47 = creak2.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -5.25F, 2.1687F, -1.7453F, 0.0F, 0.0F));

        PartDefinition cube_r48 = creak2.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -1.25F, 1.1687F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r49 = creak2.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(271, -2).addBox(0.0F, -0.5F, -2.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2361F, 7.7339F, 4.1901F, -1.8762F, 0.0F, 0.0F));

        PartDefinition cube_r50 = creak2.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(274, 1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 4.5F, 1.4187F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r51 = creak2.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -6.25F, -7.3313F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r52 = creak2.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 3.5F, 0.4187F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r53 = creak2.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 5.0F, 2.6687F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r54 = creak2.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, -2.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, -1.25F, -8.5813F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r55 = creak2.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7239F, 9.0F, 0.4187F, 0.4363F, 0.0F, 0.0F));

        PartDefinition creak3 = partdefinition.addOrReplaceChild("creak3", CubeListBuilder.create().texOffs(273, 0).addBox(-0.4442F, 5.0F, -0.6977F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, -42.75F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r56 = creak3.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -1.5F, 4.0523F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r57 = creak3.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 1.5F, 4.0523F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r58 = creak3.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 15.75F, 5.0523F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r59 = creak3.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 16.25F, 5.3023F, 1.309F, 0.0F, 0.0F));

        PartDefinition cube_r60 = creak3.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -11.75F, 7.8023F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r61 = creak3.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, -2.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -9.25F, 5.8023F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r62 = creak3.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -11.0F, 9.3023F, 1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r63 = creak3.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -16.75F, 7.8023F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r64 = creak3.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 11.25F, 4.3023F, 1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r65 = creak3.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(269, -4).addBox(0.96F, 0.0F, -3.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 15.75F, -0.4477F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r66 = creak3.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 16.25F, 3.0523F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r67 = creak3.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(269, -4).addBox(0.96F, 0.0F, -3.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -5.75F, 0.3023F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r68 = creak3.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -10.75F, 0.3023F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r69 = creak3.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(274, 1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -6.0F, 4.0523F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r70 = creak3.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -11.0F, 5.8023F, -1.9635F, 0.0F, 0.0F));

        PartDefinition cube_r71 = creak3.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 2.5F, 0.5523F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r72 = creak3.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(271, -2).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -3.75F, 3.5523F, -1.8762F, 0.0F, 0.0F));

        PartDefinition cube_r73 = creak3.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -2.75F, -1.6977F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r74 = creak3.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 2.75F, 2.0523F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r75 = creak3.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -7.25F, 4.8023F, -1.9635F, 0.0F, 0.0F));

        PartDefinition cube_r76 = creak3.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -3.75F, 3.8023F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r77 = creak3.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(271, -2).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 0.25F, 5.5523F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r78 = creak3.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(274, 1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 5.75F, 4.0523F, 1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r79 = creak3.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 10.5F, 2.8023F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r80 = creak3.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 5.5F, 2.8023F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r81 = creak3.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 3.0F, 5.3023F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r82 = creak3.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, -2.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, 13.0F, 0.8023F, 0.2182F, 0.0F, 0.0F));

        PartDefinition bone = creak3.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, 6.5F, -4.75F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4042F, -1.5F, -4.9477F, -3.0543F, 0.0F, 0.0F));

        PartDefinition cube_r83 = bone.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r84 = bone.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r85 = bone.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.25F, 1.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r86 = bone.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.75F, 1.25F, 1.309F, 0.0F, 0.0F));

        PartDefinition cube_r87 = bone.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.25F, 3.75F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r88 = bone.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, -2.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.75F, 1.75F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r89 = bone.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.5F, 5.25F, 1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r90 = bone.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.25F, 3.75F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r91 = bone.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.75F, 0.25F, 1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r92 = bone.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(269, -4).addBox(0.96F, 0.0F, -3.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.25F, -4.5F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r93 = bone.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.75F, -1.0F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r94 = bone.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(269, -4).addBox(0.96F, 0.0F, -3.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.25F, -3.75F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r95 = bone.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.25F, -3.75F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r96 = bone.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(274, 1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r97 = bone.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.5F, 1.75F, -1.9635F, 0.0F, 0.0F));

        PartDefinition cube_r98 = bone.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -3.5F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r99 = bone.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(271, -2).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.25F, -0.5F, -1.8762F, 0.0F, 0.0F));

        PartDefinition cube_r100 = bone.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, -5.75F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r101 = bone.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.25F, -2.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r102 = bone.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.75F, 0.75F, -1.9635F, 0.0F, 0.0F));

        PartDefinition cube_r103 = bone.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.25F, -0.25F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r104 = bone.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(271, -2).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.75F, 1.5F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r105 = bone.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(274, 1).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.25F, 0.0F, 1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r106 = bone.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, -1.25F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r107 = bone.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -1.25F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r108 = bone.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(273, 0).addBox(0.96F, -2.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 1.25F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r109 = bone.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(272, -1).addBox(0.96F, -2.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.5F, -3.25F, 0.2182F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(NautilusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        showCreak2 = pEntity.getDamage() >= NautilusEntity.TOTAL_HEALTH - 81;
        showCreak1 = pEntity.getDamage() >= NautilusEntity.TOTAL_HEALTH - 54;
        showCreak3 = pEntity.getDamage() >= NautilusEntity.TOTAL_HEALTH - 27;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        creak1.xScale = creak1.yScale = creak1.zScale = 0.55F;
        creak2.xScale = creak2.yScale = creak2.zScale = 0.55F;
        creak3.xScale = creak3.yScale = creak3.zScale = 0.85F;
        if (showCreak1) {
            creak1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        if (showCreak2) {
            creak2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        if (showCreak3) {
            creak3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }
}