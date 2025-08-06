package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.OctidModel;
import com.valeriotor.beyondtheveil.client.model.entity.UmancalaModel;
import com.valeriotor.beyondtheveil.entity.ictya.OctidEntity;
import com.valeriotor.beyondtheveil.entity.ictya.UmancalaEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class OctidRenderer extends IctyaRenderer<OctidEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/ictya/octid.png");

    public OctidRenderer(EntityRendererProvider.Context context) {
        super(context, new OctidModel(context.bakeLayer(OctidModel.LAYER_LOCATION)), 0.6F, TEXTURE);
    }
}
