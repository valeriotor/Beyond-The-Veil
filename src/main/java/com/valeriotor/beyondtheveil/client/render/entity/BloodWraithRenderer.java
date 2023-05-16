package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.BloodSkeletonCrawlingModel;
import com.valeriotor.beyondtheveil.client.model.entity.BloodWraithModel;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class BloodWraithRenderer extends LivingEntityRenderer<LivingEntity, BloodWraithModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/blood_wraith.png");

    public BloodWraithRenderer(EntityRendererProvider.Context context) {
        super(context, new BloodWraithModel(context.bakeLayer(BloodWraithModel.LAYER_LOCATION)), 0F);
    }

    @Override
    protected boolean shouldShowName(LivingEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntity pEntity) {
        return TEXTURE;
    }


    @Override
    protected void scale(LivingEntity pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        pMatrixStack.scale(0.4F, 0.4F, 0.4F);
        super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
    }


    @Nullable
    @Override
    protected RenderType getRenderType(LivingEntity pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        return RenderType.entityTranslucent(TEXTURE);
    }

    // Enable transparency
    /*@Nullable
    protected RenderType func_230042_a_(ShadowEntity p_230042_1_, boolean p_230042_2_, boolean p_230042_3_) {
        ResourceLocation resourcelocation = this.getEntityTexture(p_230042_1_);
        if (p_230042_3_) {
            return RenderType.getEntityTranslucent(resourcelocation);
        } else if (p_230042_2_) {
            return RenderType.getEntityTranslucent(resourcelocation);
        } else {
            return p_230042_1_.isGlowing() ? RenderType.getOutline(resourcelocation) : null;
        }
    }*/
}
