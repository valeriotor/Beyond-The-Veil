package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.model.entity.ShoremanModel;
import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.EnumMap;
import java.util.Map;

public class ShoremanRenderer extends LivingEntityRenderer<ShoremanEntity, ShoremanModel> {

    public static final Map<ShoremanEntity.ShoremanProfession, ResourceLocation> TEXTURES = new EnumMap<ShoremanEntity.ShoremanProfession, ResourceLocation>(ShoremanEntity.ShoremanProfession.class);
    private static final ResourceLocation KEEPER_ARCHE_ENDING_TEXTURE = new ResourceLocation(References.MODID, "textures/entity/shoreman/lighthouse_keeper_arche.png");
    private static final ResourceLocation KEEPER_DEAD_TEXTURE = new ResourceLocation(References.MODID, "textures/entity/shoreman/lighthouse_keeper_memory.png");

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
        super(context, new ShoremanModel(context.bakeLayer(ShoremanModel.LAYER_LOCATION)), 0.3F);
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
        Entity entity = pEntity.getLeashHolder();
        if (entity != null) {
            this.renderLeash(pEntity, pPartialTicks, pPoseStack, pBuffer, entity);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ShoremanEntity pEntity) {
        if (pEntity.isShowFins()) {
            return KEEPER_ARCHE_ENDING_TEXTURE;
        }
        LocalPlayer p = Minecraft.getInstance().player;
        if (pEntity.getProfession() == ShoremanEntity.ShoremanProfession.LIGHTHOUSE_KEEPER && p != null)
            if (DataUtil.noMoreKeeper(p)) {
                return KEEPER_DEAD_TEXTURE;
            }
        return TEXTURES.get(pEntity.getProfession());
    }

    @Nullable
    @Override
    protected RenderType getRenderType(ShoremanEntity pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        //return super.getRenderType(pLivingEntity, pBodyVisible, pTranslucent, pGlowing);
        return RenderType.entityTranslucent(getTextureLocation(pLivingEntity));
    }

    private <E extends Entity> void renderLeash(ShoremanEntity pEntityLiving, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, E pLeashHolder) {
        pPoseStack.pushPose();
        Vec3 vec3 = pLeashHolder.getRopeHoldPosition(pPartialTicks);
        double d0 = (double)(Mth.lerp(pPartialTicks, pEntityLiving.yBodyRotO, pEntityLiving.yBodyRot) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
        Vec3 vec31 = pEntityLiving.getLeashOffset(pPartialTicks);
        double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
        double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
        double d3 = Mth.lerp((double)pPartialTicks, pEntityLiving.xo, pEntityLiving.getX()) + d1;
        double d4 = Mth.lerp((double)pPartialTicks, pEntityLiving.yo, pEntityLiving.getY()) + vec31.y;
        double d5 = Mth.lerp((double)pPartialTicks, pEntityLiving.zo, pEntityLiving.getZ()) + d2;
        pPoseStack.translate(d1, vec31.y, d2);
        float f = (float)(vec3.x - d3);
        float f1 = (float)(vec3.y - d4);
        float f2 = (float)(vec3.z - d5);
        float f3 = 0.025F;
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.leash());
        Matrix4f matrix4f = pPoseStack.last().pose();
        float f4 = Mth.invSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        BlockPos blockpos = BlockPos.containing(pEntityLiving.getEyePosition(pPartialTicks));
        BlockPos blockpos1 = BlockPos.containing(pLeashHolder.getEyePosition(pPartialTicks));
        int i = this.getBlockLightLevel(pEntityLiving, blockpos);
        int j = pEntityLiving.isOnFire() ? 15 : pEntityLiving.level().getBrightness(LightLayer.BLOCK, blockpos1);
        int k = pEntityLiving.level().getBrightness(LightLayer.SKY, blockpos);
        int l = pEntityLiving.level().getBrightness(LightLayer.SKY, blockpos1);

        for(int i1 = 0; i1 <= 24; ++i1) {
            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
        }

        for(int j1 = 24; j1 >= 0; --j1) {
            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
        }

        pPoseStack.popPose();
    }

    private static void addVertexPair(VertexConsumer pConsumer, Matrix4f pMatrix, float p_174310_, float p_174311_, float p_174312_, int pEntityBlockLightLevel, int pLeashHolderBlockLightLevel, int pEntitySkyLightLevel, int pLeashHolderSkyLightLevel, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int pIndex, boolean p_174322_) {
        float f = (float)pIndex / 24.0F;
        int i = (int)Mth.lerp(f, (float)pEntityBlockLightLevel, (float)pLeashHolderBlockLightLevel);
        int j = (int)Mth.lerp(f, (float)pEntitySkyLightLevel, (float)pLeashHolderSkyLightLevel);
        int k = LightTexture.pack(i, j);
        float f1 = pIndex % 2 == (p_174322_ ? 1 : 0) ? 0.7F : 1.0F;
        float f2 = 0.5F * f1;
        float f3 = 0.4F * f1;
        float f4 = 0.3F * f1;
        float f5 = p_174310_ * f;
        float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
        float f7 = p_174312_ * f;
        pConsumer.vertex(pMatrix, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
        pConsumer.vertex(pMatrix, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
    }
}
