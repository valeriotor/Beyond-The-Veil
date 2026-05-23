package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.ShoremanModel;
import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public class ShoremanRenderer extends LivingEntityRenderer<ShoremanEntity, ShoremanModel> {

    public static final Map<ShoremanEntity.ShoremanProfession, ResourceLocation> TEXTURES = new EnumMap<ShoremanEntity.ShoremanProfession, ResourceLocation>(ShoremanEntity.ShoremanProfession.class);

    static {
        TEXTURES.put(ShoremanEntity.ShoremanProfession.BARTENDER, new ResourceLocation(References.MODID, "textures/entity/shoreman/bartender.png"));
        TEXTURES.put(ShoremanEntity.ShoremanProfession.CARPENTER, new ResourceLocation(References.MODID, "textures/entity/shoreman/carpenter.png"));
        TEXTURES.put(ShoremanEntity.ShoremanProfession.CLERK, new ResourceLocation(References.MODID, "textures/entity/shoreman/clerk.png"));
        TEXTURES.put(ShoremanEntity.ShoremanProfession.DRUNK, new ResourceLocation(References.MODID, "textures/entity/shoreman/drunk.png"));
        TEXTURES.put(ShoremanEntity.ShoremanProfession.FISHERMAN, new ResourceLocation(References.MODID, "textures/entity/shoreman/fisherman.png"));
        TEXTURES.put(ShoremanEntity.ShoremanProfession.LIGHTHOUSE_KEEPER, new ResourceLocation(References.MODID, "textures/entity/shoreman/lighthouse_keeper.png"));
        TEXTURES.put(ShoremanEntity.ShoremanProfession.MINER, new ResourceLocation(References.MODID, "textures/entity/shoreman/miner.png"));
        TEXTURES.put(ShoremanEntity.ShoremanProfession.SCHOLAR, new ResourceLocation(References.MODID, "textures/entity/shoreman/scholar.png"));
        TEXTURES.put(ShoremanEntity.ShoremanProfession.SMITH, new ResourceLocation(References.MODID, "textures/entity/shoreman/smith.png"));
    }



    public ShoremanRenderer(EntityRendererProvider.Context context) {
        super(context, new ShoremanModel(context.bakeLayer(ShoremanModel.LAYER_LOCATION)), 1F);
    }

    @Override
    protected boolean shouldShowName(ShoremanEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public void render(ShoremanEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.scale(1.05F, 1.05F, 1.05F);
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ShoremanEntity pEntity) {
        return TEXTURES.get(pEntity.getProfession());
    }

    @Nullable
    @Override
    protected RenderType getRenderType(ShoremanEntity pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        //return super.getRenderType(pLivingEntity, pBodyVisible, pTranslucent, pGlowing);
        return RenderType.entityTranslucent(getTextureLocation(pLivingEntity));
    }
}
