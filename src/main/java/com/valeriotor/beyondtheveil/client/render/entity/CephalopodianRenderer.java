package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.CephalopodianModel;
import com.valeriotor.beyondtheveil.client.model.entity.FletumModel;
import com.valeriotor.beyondtheveil.entity.FletumEntity;
import com.valeriotor.beyondtheveil.entity.ictya.CephalopodianEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class CephalopodianRenderer extends LivingEntityRenderer<CephalopodianEntity, CephalopodianModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/ictya/cephalopodian.png");

    public CephalopodianRenderer(EntityRendererProvider.Context context) {
        super(context, new CephalopodianModel(context.bakeLayer(CephalopodianModel.LAYER_LOCATION)), 0.6F);
    }

    @Override
    protected boolean shouldShowName(CephalopodianEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(CephalopodianEntity pEntity) {
        return TEXTURE;
    }
}
