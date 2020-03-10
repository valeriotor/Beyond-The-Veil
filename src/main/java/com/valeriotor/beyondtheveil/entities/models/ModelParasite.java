package com.valeriotor.beyondtheveil.entities.models;

import com.valeriotor.beyondtheveil.BeyondTheVeil;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;

/**
 * ModelPlayer - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelParasite extends ModelBase {
    public ModelRenderer shape15;
    public ModelRenderer shape16;
    public ModelRenderer shape17;
    public ModelRenderer shape16_1;

    public ModelParasite() {
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.shape17 = new ModelRenderer(this, 0, 0);
        this.shape17.setRotationPoint(-0.3F, -1.8F, 0.7F);
        this.shape17.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(shape17, 0.0F, 0.31869712141416456F, 0.0F);
        this.shape16_1 = new ModelRenderer(this, 0, 0);
        this.shape16_1.setRotationPoint(-1.0F, -6.1F, -0.8F);
        this.shape16_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(shape16_1, 1.0927506446736497F, -0.9560913642424937F, 0.0F);
        this.shape16 = new ModelRenderer(this, 0, 0);
        this.shape16.setRotationPoint(0.2F, -4.0F, -0.9F);
        this.shape16.addBox(0.0F, 0.0F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(shape16, -0.5918411493512771F, 0.0F, -0.31869712141416456F);
        this.shape15 = new ModelRenderer(this, 0, 0);
        this.shape15.setRotationPoint(-4.57F, 0.7F, 0.0F);
        this.shape15.addBox(-1.5F, -6.0F, -1.5F, 3, 6, 3, 0.0F);
        this.setRotateAngle(shape15, -0.091106186954104F, -0.091106186954104F, -0.5009094953223726F);
        this.shape15.addChild(this.shape17);
        this.shape15.addChild(this.shape16_1);
        this.shape15.addChild(this.shape16);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape15.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount,
    		float partialTickTime) {
    	float animTicks = BeyondTheVeil.proxy.cEvents.getAnimationCounter() % 200 + partialTickTime;
    	float offset = (float) Math.sin(animTicks%40 / 6.366)/20;
    	this.shape17.rotateAngleX = offset * 5;
    	this.shape16.rotateAngleZ = -0.31869712141416456F  + offset * 5;
    	this.shape15.rotateAngleZ = -0.5009094953223726F + offset;
    	this.shape16_1.rotateAngleX = 1.0927506446736497F + offset * 5;
    	offset = (float) Math.sin(animTicks%50 / 7.95)/16;
    	this.shape17.rotateAngleZ = offset * 5;
    	this.shape16.rotateAngleX = -0.5918411493512771F + offset * 5;
    	this.shape15.rotateAngleX = -0.091106186954104F + offset;
    	this.shape16_1.rotateAngleZ = offset * 5;
    	super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
    }
}
