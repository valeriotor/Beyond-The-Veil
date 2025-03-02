package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.client.model.entity.FletumModel;
import com.valeriotor.beyondtheveil.client.model.entity.WeeperModel;
import com.valeriotor.beyondtheveil.entity.FletumEntity;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class FletumRenderer extends LivingEntityRenderer<FletumEntity, FletumModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/weeper.png");

    public FletumRenderer(EntityRendererProvider.Context context) {
        super(context, new FletumModel(context.bakeLayer(FletumModel.LAYER_LOCATION)), 0.6F);
    }

    @Override
    protected boolean shouldShowName(FletumEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(FletumEntity pEntity) {
        return TEXTURE;
    }
}
