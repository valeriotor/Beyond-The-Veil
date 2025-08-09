package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.AdelineModel;
import com.valeriotor.beyondtheveil.client.model.entity.AnglerModel;
import com.valeriotor.beyondtheveil.entity.ictya.AdelineEntity;
import com.valeriotor.beyondtheveil.entity.ictya.AnglerEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class AdelineRenderer extends IctyaRenderer<AdelineEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/ictya/adeline.png");

    public AdelineRenderer(EntityRendererProvider.Context context) {
        super(context, new AdelineModel(context.bakeLayer(AdelineModel.LAYER_LOCATION)), 0.6F, TEXTURE);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(AdelineEntity pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        return RenderType.entityTranslucent(TEXTURE);
    }

}
