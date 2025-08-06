package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.AnglerModel;
import com.valeriotor.beyondtheveil.client.model.entity.ManOWarModel;
import com.valeriotor.beyondtheveil.entity.ictya.AnglerEntity;
import com.valeriotor.beyondtheveil.entity.ictya.ManOWarEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ManOWarRenderer extends IctyaRenderer<ManOWarEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/ictya/man_o_war.png");

    public ManOWarRenderer(EntityRendererProvider.Context context) {
        super(context, new ManOWarModel(context.bakeLayer(ManOWarModel.LAYER_LOCATION)), 0.6F, TEXTURE);
    }
}
