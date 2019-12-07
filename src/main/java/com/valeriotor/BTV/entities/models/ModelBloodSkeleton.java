package com.valeriotor.BTV.entities.models;

import com.valeriotor.BTV.entities.EntityBloodSkeleton;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * BloodSkeleton - valeriotor
 * Created using Tabula 7.0.0
 */
public class ModelBloodSkeleton extends ModelBase {
    public ModelRenderer clavicle;
    public ModelRenderer spine;
    public ModelRenderer pelvis;
    public ModelRenderer left_thigh;
    public ModelRenderer right_thigh;
    public ModelRenderer left_arm;
    public ModelRenderer right_arm;
    public ModelRenderer head;
    public ModelRenderer left_forearm;
    public ModelRenderer right_forearm;
    public ModelRenderer shape29;
    public ModelRenderer rib_left_first;
    public ModelRenderer rib_left_second;
    public ModelRenderer rib_left_third;
    public ModelRenderer rib_left_fourth;
    public ModelRenderer rib_right_first;
    public ModelRenderer rib_right_second;
    public ModelRenderer rib_right_third;
    public ModelRenderer rib_right_fourth;
    public ModelRenderer rib2_left_first;
    public ModelRenderer rib2_left_second;
    public ModelRenderer rib2_left_third;
    public ModelRenderer rib2_left_fourth;
    public ModelRenderer rib2_right_first;
    public ModelRenderer rib2_right_second;
    public ModelRenderer rib2_right_third;
    public ModelRenderer rib2_right_fourth;
    public ModelRenderer left_leg;
    public ModelRenderer right_leg;

    public ModelBloodSkeleton() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.rib_left_second = new ModelRenderer(this, 32, 0);
        this.rib_left_second.setRotationPoint(0.1F, 3.0F, 1.0F);
        this.rib_left_second.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.rib_left_first = new ModelRenderer(this, 32, 8);
        this.rib_left_first.setRotationPoint(0.1F, 1.5F, 1.0F);
        this.rib_left_first.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.rib_right_second = new ModelRenderer(this, 32, 8);
        this.rib_right_second.setRotationPoint(-0.1F, 3.0F, 1.0F);
        this.rib_right_second.addBox(-5.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.right_arm = new ModelRenderer(this, 40, 16);
        this.right_arm.setRotationPoint(-6.6F, 0.1F, -0.1F);
        this.right_arm.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.rib2_right_second = new ModelRenderer(this, 0, 0);
        this.rib2_right_second.setRotationPoint(-5.0F, 0.0F, -1.0F);
        this.rib2_right_second.addBox(0.0F, 0.0F, -3.0F, 1, 1, 4, 0.0F);
        this.right_forearm = new ModelRenderer(this, 48, 16);
        this.right_forearm.setRotationPoint(0.1F, 8.7F, 0.1F);
        this.right_forearm.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.shape29 = new ModelRenderer(this, 8, 0);
        this.shape29.setRotationPoint(0.0F, 6.0F, 7.0F);
        this.shape29.addBox(-3.5F, 0.0F, -7.0F, 7, 1, 7, 0.0F);
        this.rib_right_fourth = new ModelRenderer(this, 32, 8);
        this.rib_right_fourth.setRotationPoint(-0.1F, 6.0F, 1.0F);
        this.rib_right_fourth.addBox(-5.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.pelvis = new ModelRenderer(this, 36, 12);
        this.pelvis.setRotationPoint(-4.0F, 6.0F, 1.0F);
        this.pelvis.addBox(0.0F, 0.0F, 0.0F, 8, 2, 2, 0.0F);
        this.rib2_right_third = new ModelRenderer(this, 16, 8);
        this.rib2_right_third.setRotationPoint(-5.0F, 0.0F, -1.0F);
        this.rib2_right_third.addBox(0.0F, 0.0F, -3.0F, 1, 1, 4, 0.0F);
        this.rib_right_third = new ModelRenderer(this, 32, 0);
        this.rib_right_third.setRotationPoint(-0.1F, 4.5F, 1.0F);
        this.rib_right_third.addBox(-5.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.clavicle = new ModelRenderer(this, 32, 28);
        this.clavicle.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.clavicle.addBox(-7.0F, 0.0F, 0.0F, 14, 2, 2, 0.0F);
        this.rib2_left_second = new ModelRenderer(this, 16, 8);
        this.rib2_left_second.setRotationPoint(4.0F, 0.0F, -1.0F);
        this.rib2_left_second.addBox(0.0F, 0.0F, -3.0F, 1, 1, 4, 0.0F);
        this.rib2_right_fourth = new ModelRenderer(this, 0, 0);
        this.rib2_right_fourth.setRotationPoint(-5.0F, 0.0F, -1.0F);
        this.rib2_right_fourth.addBox(0.0F, 0.0F, -3.0F, 1, 1, 4, 0.0F);
        this.left_forearm = new ModelRenderer(this, 48, 16);
        this.left_forearm.setRotationPoint(-0.1F, 8.7F, 0.1F);
        this.left_forearm.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.rib_left_third = new ModelRenderer(this, 32, 8);
        this.rib_left_third.setRotationPoint(0.1F, 4.5F, 1.0F);
        this.rib_left_third.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.rib_left_fourth = new ModelRenderer(this, 32, 0);
        this.rib_left_fourth.setRotationPoint(0.1F, 6.0F, 1.0F);
        this.rib_left_fourth.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.left_arm = new ModelRenderer(this, 40, 16);
        this.left_arm.setRotationPoint(6.6F, 0.1F, -0.1F);
        this.left_arm.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.rib2_left_fourth = new ModelRenderer(this, 16, 8);
        this.rib2_left_fourth.setRotationPoint(4.0F, 0.0F, -1.0F);
        this.rib2_left_fourth.addBox(0.0F, 0.0F, -3.0F, 1, 1, 4, 0.0F);
        this.rib_right_first = new ModelRenderer(this, 32, 0);
        this.rib_right_first.setRotationPoint(-0.1F, 1.5F, 1.0F);
        this.rib_right_first.addBox(-5.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.head = new ModelRenderer(this, 0, 18);
        this.head.setRotationPoint(0.0F, -6.8F, -4.0F);
        this.head.addBox(-3.5F, 0.0F, 0.0F, 7, 6, 7, 0.0F);
        this.left_leg = new ModelRenderer(this, 0, 6);
        this.left_leg.setRotationPoint(-0.1F, 7.0F, -0.2F);
        this.left_leg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(left_leg, 0.27314402793711257F, 0.0F, 0.0F);
        this.rib2_left_first = new ModelRenderer(this, 0, 0);
        this.rib2_left_first.setRotationPoint(4.0F, 0.0F, -1.0F);
        this.rib2_left_first.addBox(0.0F, 0.0F, -3.0F, 1, 1, 4, 0.0F);
        this.right_leg = new ModelRenderer(this, 0, 6);
        this.right_leg.setRotationPoint(0.1F, 7.0F, -0.2F);
        this.right_leg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.spine = new ModelRenderer(this, 56, 11);
        this.spine.setRotationPoint(0.0F, -9.0F, 1.0F);
        this.spine.addBox(-1.0F, 0.0F, 0.0F, 2, 15, 2, 0.0F);
        this.rib2_left_third = new ModelRenderer(this, 0, 0);
        this.rib2_left_third.setRotationPoint(4.0F, 0.0F, -1.0F);
        this.rib2_left_third.addBox(0.0F, 0.0F, -3.0F, 1, 1, 4, 0.0F);
        this.rib2_right_first = new ModelRenderer(this, 16, 8);
        this.rib2_right_first.setRotationPoint(-5.0F, 0.0F, -1.0F);
        this.rib2_right_first.addBox(0.0F, 0.0F, -3.0F, 1, 1, 4, 0.0F);
        this.left_thigh = new ModelRenderer(this, 56, 0);
        this.left_thigh.setRotationPoint(3.0F, 7.5F, 1.0F);
        this.left_thigh.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.right_thigh = new ModelRenderer(this, 56, 0);
        this.right_thigh.setRotationPoint(-3.0F, 7.5F, 1.0F);
        this.right_thigh.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.spine.addChild(this.rib_left_second);
        this.spine.addChild(this.rib_left_first);
        this.spine.addChild(this.rib_right_second);
        this.clavicle.addChild(this.right_arm);
        this.rib_right_second.addChild(this.rib2_right_second);
        this.right_arm.addChild(this.right_forearm);
        this.head.addChild(this.shape29);
        this.spine.addChild(this.rib_right_fourth);
        this.rib_right_third.addChild(this.rib2_right_third);
        this.spine.addChild(this.rib_right_third);
        this.rib_left_second.addChild(this.rib2_left_second);
        this.rib_right_fourth.addChild(this.rib2_right_fourth);
        this.left_arm.addChild(this.left_forearm);
        this.spine.addChild(this.rib_left_third);
        this.spine.addChild(this.rib_left_fourth);
        this.clavicle.addChild(this.left_arm);
        this.rib_left_fourth.addChild(this.rib2_left_fourth);
        this.spine.addChild(this.rib_right_first);
        this.clavicle.addChild(this.head);
        this.left_thigh.addChild(this.left_leg);
        this.rib_left_first.addChild(this.rib2_left_first);
        this.right_thigh.addChild(this.right_leg);
        this.rib_left_third.addChild(this.rib2_left_third);
        this.rib_right_first.addChild(this.rib2_right_first);
        this.setAngles(true);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.pelvis.render(f5);
        this.clavicle.render(f5);
        this.spine.render(f5);
        this.left_thigh.render(f5);
        this.right_thigh.render(f5);
    }
    
    public void setAngles(boolean addToDefault) {
        this.setRotateAngle(rib_left_second, 0.0F, 0.5235987755982988F, 0.0F);
        this.setRotateAngle(rib_left_first, 0.0F, 0.5385161049805922F, 0.0F);
        this.setRotateAngle(rib_right_second, 0.0F, -0.5235987755982988F, 0.0F);
        this.setRotateAngle(right_forearm, -0.136659280431156F, 0.0F, 0.0F);
        this.setRotateAngle(shape29, 0.08203784098300857F, 0.0F, 0.0F);
        this.setRotateAngle(rib_right_fourth, 0.0F, -0.5235987755982988F, 0.0F);
        this.setRotateAngle(rib_right_third, 0.0F, -0.5235987755982988F, 0.0F);
        this.setRotateAngle(left_forearm, -0.136659280431156F, 0.0F, 0.0F);
        this.setRotateAngle(rib_left_third, 0.0F, 0.5235987755982988F, 0.0F);
        this.setRotateAngle(rib_left_fourth, 0.0F, 0.5235987755982988F, 0.0F);
        this.setRotateAngle(rib_right_first, 0.0F, -0.5235987755982988F, 0.0F);
        this.setRotateAngle(right_leg, 0.27314402793711257F, 0.0F, 0.0F);
        this.setRotateAngle(left_thigh, -0.18203784098300857F, 0.0F, 0.0F);
        this.setRotateAngle(right_thigh, -0.19198621771937624F, 0.0F, 0.0F);
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
    	int animTicks = ((EntityBloodSkeleton)e).getAnimCounter();
    	this.left_thigh.rotateAngleX = -0.18203784098300857F + MathHelper.cos(limbSwing * 0.662F) * limbSwingAmount;
    	this.right_thigh.rotateAngleX = -0.18203784098300857F + MathHelper.cos(limbSwing * 0.662F + (float)Math.PI) * limbSwingAmount;
    	//this.shape29.rotateAngleX = 0.08203784098300857F + (float) Math.sin((animTicks%40 + partialTickTime)/ 6.366)/20;
    	super.setLivingAnimations(e, limbSwing, limbSwingAmount, partialTickTime);
    }
}
