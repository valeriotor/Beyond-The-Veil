package com.valeriotor.BTV.entities.render;

import org.lwjgl.util.vector.Quaternion;

import com.valeriotor.BTV.entities.models.ModelDeepOne;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderTransformedPlayer extends RenderLivingBase<EntityLivingBase>{

	public static final ResourceLocation deepOneTexture = new ResourceLocation(References.MODID +":textures/entity/deep_one.png");

	
	public RenderTransformedPlayer(RenderManager renderManager) {
		super(renderManager, new ModelDeepOne(), 0.5F);
	}
	
	public void render(EntityPlayer entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		
        GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        if (!this.bindEntityTexture(entity))
        {
            return;
        }
        
		GlStateManager.pushMatrix();
		
		//GlStateManager.scale(1, -1, 1);
        GlStateManager.rotate(180, 0, 0, 1);
        //GlStateManager.rotate(new Quaternion(-1, 0, 0, 0.0F));
        //GlStateManager.
        //GlStateManager.rotate(entity.renderYawOffset, 0, 1, 0);
		GlStateManager.translate(0, -1.5, 0);
		float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
        float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
        float f2 = f1 - f;
        float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        //this.renderLivingAt(entity, x, y, z);
        float f8 = this.handleRotationFloat(entity, partialTicks);
        this.applyRotations(entity, f8, -f, partialTicks);
        float f5 = 0.0F;
        float f6 = 0.0F;

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
        this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
        this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, 0.06F, entity);

		this.mainModel.render(entity, f6, f5, 1, f2, f7, 0.06F);

        GlStateManager.popMatrix();

        GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
	}
	
	@Override
	public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	public ResourceLocation getEntityTexture(EntityLivingBase entity) {
		
		return deepOneTexture;
		
	}
	
	@Override
	public ModelDeepOne getMainModel() {
		return (ModelDeepOne)super.getMainModel();
	}
	

}
