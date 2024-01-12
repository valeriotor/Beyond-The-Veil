package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.DeepOneModel;
import com.valeriotor.beyondtheveil.client.model.entity.WeeperModel;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class WeeperRenderer extends LivingEntityRenderer<WeeperEntity, WeeperModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/weeper.png");

    public WeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new WeeperModel(context.bakeLayer(WeeperModel.LAYER_LOCATION)), 1F);
    }

    @Override
    protected boolean shouldShowName(WeeperEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(WeeperEntity pEntity) {
        return TEXTURE;
    }
}
