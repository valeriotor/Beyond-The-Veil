package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.ManOWarModel;
import com.valeriotor.beyondtheveil.client.model.entity.UmancalaModel;
import com.valeriotor.beyondtheveil.entity.ictya.ManOWarEntity;
import com.valeriotor.beyondtheveil.entity.ictya.UmancalaEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class UmancalaRenderer extends IctyaRenderer<UmancalaEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/ictya/umancala.png");

    public UmancalaRenderer(EntityRendererProvider.Context context) {
        super(context, new UmancalaModel(context.bakeLayer(UmancalaModel.LAYER_LOCATION)), 0.6F, TEXTURE);
    }
}
