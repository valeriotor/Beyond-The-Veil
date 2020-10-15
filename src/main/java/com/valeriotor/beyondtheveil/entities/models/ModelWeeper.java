package com.valeriotor.beyondtheveil.entities.models;

import com.valeriotor.beyondtheveil.entities.EntityWeeper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 7.0.0
 */
public class ModelWeeper extends ModelBase {
    public ModelRenderer field_78190_c0;
    public ModelRenderer field_78190_c1;
    public ModelRenderer field_78190_c2;
    public ModelRenderer field_78188_e;
    public ModelRenderer field_78189_b0;
    public ModelRenderer field_78189_b1;
    public ModelRenderer field_78187_d;
    public ModelRenderer shape10;
    public ModelRenderer shape10_1;
    public ModelRenderer shape10_2;

    public ModelWeeper() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.field_78187_d = new ModelRenderer(this, 0, 22);
        this.field_78187_d.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.field_78187_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.field_78190_c0 = new ModelRenderer(this, 44, 22);
        this.field_78190_c0.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.field_78190_c0.addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(field_78190_c0, -0.75F, 0.0F, 0.0F);
        this.field_78188_e = new ModelRenderer(this, 0, 22);
        this.field_78188_e.mirror = true;
        this.field_78188_e.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.field_78188_e.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.shape10 = new ModelRenderer(this, 0, 0);
        this.shape10.setRotationPoint(-2.5F, -10.5F, -2.4F);
        this.shape10.addBox(0.0F, 0.0F, 0.0F, 10, 10, 9, 0.0F);
        this.field_78189_b1 = new ModelRenderer(this, 0, 38);
        this.field_78189_b1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.field_78189_b1.addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.5F);
        this.shape10_2 = new ModelRenderer(this, 0, 0);
        this.shape10_2.setRotationPoint(-5.0F, -7.2F, -6.0F);
        this.shape10_2.addBox(0.0F, 0.0F, 0.0F, 10, 10, 9, 0.0F);
        this.field_78190_c1 = new ModelRenderer(this, 44, 22);
        this.field_78190_c1.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.field_78190_c1.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(field_78190_c1, -0.75F, 0.0F, 0.0F);
        this.shape10_1 = new ModelRenderer(this, 0, 0);
        this.shape10_1.setRotationPoint(-7.8F, -9.7F, -1.2F);
        this.shape10_1.addBox(0.0F, 0.0F, 0.0F, 8, 9, 9, 0.0F);
        this.field_78190_c2 = new ModelRenderer(this, 40, 38);
        this.field_78190_c2.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.field_78190_c2.addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, 0.0F);
        this.setRotateAngle(field_78190_c2, -0.75F, 0.0F, 0.0F);
        this.field_78189_b0 = new ModelRenderer(this, 16, 20);
        this.field_78189_b0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.field_78189_b0.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.field_78187_d.render(f5);
        this.field_78190_c0.render(f5);
        this.field_78188_e.render(f5);
        this.shape10.render(f5);
        this.field_78189_b1.render(f5);
        this.shape10_2.render(f5);
        this.field_78190_c1.render(f5);
        this.shape10_1.render(f5);
        this.field_78190_c2.render(f5);
        this.field_78189_b0.render(f5);
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
    	if(!(e instanceof EntityWeeper)) return;
    	EntityWeeper weeper = (EntityWeeper)e;
    	float animTicks = weeper.getAnimationTicks();
    	float offset = MathHelper.sin(animTicks%40 / 6.366F)/20;
    	this.shape10.offsetX = offset;
    	this.shape10_1.offsetY = offset;
    	this.shape10_2.offsetX = -offset/2;
    	this.shape10_1.offsetZ = offset/2;
    	offset = MathHelper.sin(animTicks%50 / 7.95F)/16;
    	this.shape10.offsetY = offset;
    	this.shape10_1.offsetX = offset;
    	this.shape10_2.offsetY = offset;
    	this.shape10.offsetZ = -offset/2;
    	this.shape10_2.offsetZ = offset/2;
		this.field_78187_d.rotateAngleX = MathHelper.cos(limbSwing * 0.662F) * limbSwingAmount/2;
    	this.field_78188_e.rotateAngleX = MathHelper.cos(limbSwing * 0.662F + (float)Math.PI) * limbSwingAmount/2;
    	super.setLivingAnimations(e, limbSwing, limbSwingAmount, partialTickTime);
    }
}
