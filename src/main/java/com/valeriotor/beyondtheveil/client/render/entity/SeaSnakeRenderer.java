package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.client.model.entity.AnglerModel;
import com.valeriotor.beyondtheveil.client.model.entity.SeaSnakeModel;
import com.valeriotor.beyondtheveil.entity.ictya.AnglerEntity;
import com.valeriotor.beyondtheveil.entity.ictya.SeaSnakeEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SeaSnakeRenderer extends IctyaRenderer<SeaSnakeEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/ictya/sea_snake.png");

    public SeaSnakeRenderer(EntityRendererProvider.Context context) {
        super(context, new SeaSnakeModel(context.bakeLayer(SeaSnakeModel.LAYER_LOCATION)), 0.6F, TEXTURE);
    }
}
