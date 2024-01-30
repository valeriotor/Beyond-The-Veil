package com.valeriotor.beyondtheveil.client.model.entity.layer;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.PatientCondition;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;


public class ChestWoundModel<T extends Entity & SurgeryPatient> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "chestwound"), "main");
    private final ModelPart wound;
    private final ModelPart heart;

    public ChestWoundModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.wound = root.getChild("wound");
        this.heart = root.getChild("heart");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition wound = partdefinition.addOrReplaceChild("wound", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.24F, -1.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.5F, -4.0F));

        PartDefinition heart = partdefinition.addOrReplaceChild("heart", CubeListBuilder.create().texOffs(0, 5).addBox(0.5F, -0.15F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.5F, -4.0F));

        return LayerDefinition.create(meshdefinition, 16, 8);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        int modulus = (Mth.floor(ageInTicks)) & 31;
        float floatingModulus = modulus + (ageInTicks - Mth.floor(ageInTicks));
        float offset;
        if (modulus <= 19) {
            offset = 0;
        } else if(modulus <= 22) {
            offset = (floatingModulus - 20) / 3;
        } else if(modulus <= 25) {
            offset = 1 - ((floatingModulus - 23) / 3);
        } else if(modulus <= 28) {
            offset = (floatingModulus - 26) / 3;
        } else {
            offset = 1 - ((floatingModulus - 29) / 3);
        }
        offset *= 0.2F;
        heart.y = 23.5F + (entity.getPatientStatus().getCondition() != PatientCondition.DEAD ? offset : 0);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        wound.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        heart.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }


}