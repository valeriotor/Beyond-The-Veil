package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.SepiidModel;
import com.valeriotor.beyondtheveil.entity.ictya.SepiidEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SepiidRenderer extends IctyaRenderer<SepiidEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/ictya/sepiid.png");

    public SepiidRenderer(EntityRendererProvider.Context context) {
        super(context, new SepiidModel(context.bakeLayer(SepiidModel.LAYER_LOCATION)), 0.6F, TEXTURE);
    }
}
