package com.valeriotor.beyondtheveil.entities.models;

import com.valeriotor.beyondtheveil.entities.EntityFletum;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelVillager - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelFletum extends ModelBase {
    public ModelRenderer shape10;
    public ModelRenderer shape10_1;
    public ModelRenderer shape10_2;

    public ModelFletum() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.shape10 = new ModelRenderer(this, 0, 0);
        this.shape10.setRotationPoint(-2.5F, 13.8F, -2.4F);
        this.shape10.addBox(0.0F, 0.0F, 0.0F, 10, 10, 9, 0.0F);
        this.shape10_2 = new ModelRenderer(this, 0, 0);
        this.shape10_2.setRotationPoint(-5.0F, 15.2F, -6.0F);
        this.shape10_2.addBox(0.0F, 0.0F, 0.0F, 10, 10, 9, 0.0F);
        this.shape10_1 = new ModelRenderer(this, 0, 0);
        this.shape10_1.setRotationPoint(-7.8F, 15.2F, -1.2F);
        this.shape10_1.addBox(0.0F, 0.0F, 0.0F, 8, 9, 9, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape10.render(f5);
        this.shape10_2.render(f5);
        this.shape10_1.render(f5);
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
    public void setLivingAnimations(EntityLivingBase e, float limbSwing, float limbSwingAmount,
    		float partialTickTime) {
    	if(!(e instanceof EntityFletum)) return;
    	EntityFletum weeper = (EntityFletum)e;
    	float animTicks = weeper.getAnimationTicks();
    	float offset = MathHelper.sin((animTicks%40 + partialTickTime) / 6.366F)/20;
    	this.shape10.offsetX = offset;
    	this.shape10_1.offsetY = offset/6;
    	this.shape10_2.offsetX = -offset/2;
    	this.shape10_1.offsetZ = offset/2;
    	offset = MathHelper.sin((animTicks%50 + partialTickTime) / 7.95F)/16;
    	this.shape10_1.offsetX = offset;
    	this.shape10_2.offsetY = offset/6;
    	this.shape10.offsetZ = -offset/2;
    	this.shape10_2.offsetZ = offset/2;
    }
}
