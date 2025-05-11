package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.client.model.entity.DeepOneModel;
import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class DeepOneRenderer extends LivingEntityRenderer<LivingEntity, DeepOneModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/deep_one.png");

    public DeepOneRenderer(EntityRendererProvider.Context context) {
        super(context, new DeepOneModel(context.bakeLayer(DeepOneModel.LAYER_LOCATION)), 1F);
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
    protected void setupRotations(LivingEntity pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        float f = pEntityLiving.getSwimAmount(pPartialTicks);
        if (f >= 0.0) {
            float f3 = pEntityLiving.isInWater() || pEntityLiving.isInFluidType((fluidType, height) -> pEntityLiving.canSwimInFluidType(fluidType)) ? -70.0F - pEntityLiving.getXRot() : -70.0F;
            float f4 = Mth.lerp(f, 0.1F, f3);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f4));
            if (pEntityLiving.isVisuallySwimming()) {
                pPoseStack.translate(0.0F, -1.0F, 0.3F);
            }
        }
    }

    @Override
    public void render(LivingEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(LivingEntity pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        if (pLivingEntity instanceof DeepOneEntity deepOne) {
            if (deepOne.getContactMove() != 0) {
                return RenderType.entityTranslucent(getTextureLocation(deepOne));
            }
        }
        return super.getRenderType(pLivingEntity, pBodyVisible, pTranslucent, pGlowing);
    }
}
