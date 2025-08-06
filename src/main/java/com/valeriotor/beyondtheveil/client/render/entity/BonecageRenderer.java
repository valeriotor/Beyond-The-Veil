package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.BonecageModel;
import com.valeriotor.beyondtheveil.client.model.entity.ManOWarModel;
import com.valeriotor.beyondtheveil.entity.ictya.BonecageEntity;
import com.valeriotor.beyondtheveil.entity.ictya.ManOWarEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BonecageRenderer extends IctyaRenderer<BonecageEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/ictya/bonecage.png");

    public BonecageRenderer(EntityRendererProvider.Context context) {
        super(context, new BonecageModel(context.bakeLayer(BonecageModel.LAYER_LOCATION)), 0.6F, TEXTURE);
    }
}
