package com.valeriotor.BTV.entities.render;

import com.valeriotor.BTV.entities.EntityCrawlingVillager;
import com.valeriotor.BTV.entities.models.ModelCrawlingVillager;
import com.valeriotor.BTV.entities.models.ModelRegistry;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderCrawlingVillager extends RenderLiving<EntityCrawlingVillager>{
	
	public RenderCrawlingVillager(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.crawling_villager, 0.5F);
	}
	private static final ResourceLocation farmerTex = new ResourceLocation("textures/entity/villager/farmer.png");
	private static final ResourceLocation weeperTex = new ResourceLocation(References.MODID, "textures/entity/weeper.png");
	@Override
	protected ResourceLocation getEntityTexture(EntityCrawlingVillager entity) {
		if(entity.isWeeper()) return weeperTex;
		if(entity.getProfessionID() == 3 || entity.getProfessionID() == 4) return farmerTex;
		//return weeperTex;	
		return entity.getProfession().getSkin();
	}
	
	@Override
	public void doRender(EntityCrawlingVillager entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<EntityCrawlingVillager>(entity, this, partialTicks, x, y, z))) return;
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
        boolean shouldSit = entity.isRiding() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
        this.mainModel.isRiding = shouldSit;
        this.mainModel.isChild = entity.isChild();

        try
        {
            float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f2 = f1 - f;

            if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase)
            {
                EntityLivingBase entitylivingbase = (EntityLivingBase)entity.getRidingEntity();
                f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                f2 = f1 - f;
                float f3 = MathHelper.wrapDegrees(f2);

                if (f3 < -85.0F)
                {
                    f3 = -85.0F;
                }

                if (f3 >= 85.0F)
                {
                    f3 = 85.0F;
                }

                f = f1 - f3;

                if (f3 * f3 > 2500.0F)
                {
                    f += f3 * 0.2F;
                }

                f2 = f1 - f;
            }

            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            this.renderLivingAt(entity, x, y, z);
            float f8 = this.handleRotationFloat(entity, partialTicks);
            this.applyRotations(entity, f8, f, partialTicks);
            float f4 = this.prepareScale(entity, partialTicks);
            float f5 = 0.0F;
            float f6 = 0.0F;
            if(entity.getTicksToFall() > 0) {
            	GlStateManager.rotate(-90*(entity.getTicksToFall()-partialTicks)/EntityCrawlingVillager.DEFAULTTICKSTOFALL, 1, 0, 0);
            	GlStateManager.translate(0, -(entity.getTicksToFall()-partialTicks)/EntityCrawlingVillager.DEFAULTTICKSTOFALL, 0);
            }
            if (!entity.isRiding())
            {
                f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

                if (entity.isChild())
                {
                    f6 *= 3.0F;
                }

                if (f5 > 1.0F)
                {
                    f5 = 1.0F;
                }
                f2 = f1 - f; // Forge: Fix MC-1207
            }

            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
            this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);

            if (this.renderOutlines)
            {
                boolean flag1 = this.setScoreTeamColor(entity);
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entity));

                if (!this.renderMarker)
                {
                    this.renderModel(entity, f6, f5, f8, f2, f7, f4);
                }


                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();

                if (flag1)
                {
                    this.unsetScoreTeamColor();
                }
            }
            else
            {
                boolean flag = this.setDoRenderBrightness(entity, partialTicks);
                this.renderModel(entity, f6, f5, f8, f2, f7, f4);

                if (flag)
                {
                    this.unsetBrightness();
                }

                GlStateManager.depthMask(true);

            }

            GlStateManager.disableRescaleNormal();
        }
        catch (Exception exception)
        {
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        //super.doRender(entity, x, y, z, entityYaw, partialTicks);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<EntityCrawlingVillager>(entity, this, partialTicks, x, y, z));
    
		
		
		
		
	}
	
	
}
