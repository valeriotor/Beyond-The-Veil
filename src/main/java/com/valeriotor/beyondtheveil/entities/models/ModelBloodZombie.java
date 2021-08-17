package com.valeriotor.beyondtheveil.entities.models;

import java.util.EnumMap;
import java.util.List;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationTemplate.Transformation;
import com.valeriotor.beyondtheveil.entities.EntityBloodZombie;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * BloodZombie - valeriotor
 * Created using Tabula 7.0.0
 */
public class ModelBloodZombie extends ModelAnimated {
    public ModelRenderer body;					//  0
    public ModelRenderer upper_body;			//  1
    public ModelRenderer left_leg;				//  2
    public ModelRenderer right_leg;				//  3
    public ModelRenderer left_foreleg;			//  4
    public ModelRenderer right_foreleg;			//  5
    public ModelRenderer left_arm;				//  6
    public ModelRenderer right_arm;				//  7
    public ModelRenderer head;					//  8
    public ModelRenderer left_forearm;			//  9
    public ModelRenderer right_forearm;			// 10
    public ModelRenderer jaw;					// 11

    public ModelBloodZombie() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.jaw = new ModelRenderer(this, 32, 0);
        this.jaw.setRotationPoint(0.0F, 6.6F, 0.0F);
        this.jaw.addBox(-4.0F, 0.0F, -8.0F, 8, 1, 8, 0.0F);
        this.left_leg = new ModelRenderer(this, 48, 50);
        this.left_leg.setRotationPoint(3.8F, 12.0F, 1.8F);
        this.left_leg.addBox(-2.0F, 0.0F, 0.0F, 4, 10, 4, 0.0F);
        this.head = new ModelRenderer(this, 32, 9);
        this.head.setRotationPoint(0.0F, -3.0F, 5.3F);
        this.head.addBox(-4.0F, 0.0F, -8.0F, 8, 7, 8, 0.0F);
        this.left_forearm = new ModelRenderer(this, 40, 28);
        this.left_forearm.setRotationPoint(-0.4F, 9.8F, 0.0F);
        this.left_forearm.addBox(-1.0F, 0.0F, 0.0F, 3, 12, 3, 0.0F);
        this.upper_body = new ModelRenderer(this, 0, 24);
        this.upper_body.setRotationPoint(0.0F, -8.0F, -7.4F);
        this.upper_body.addBox(-5.5F, 0.0F, 0.0F, 11, 11, 7, 0.0F);
        this.right_leg = new ModelRenderer(this, 48, 50);
        this.right_leg.setRotationPoint(-3.8F, 12.0F, 1.8F);
        this.right_leg.addBox(-2.0F, 0.0F, 0.0F, 4, 10, 4, 0.0F);
        this.right_foreleg = new ModelRenderer(this, 0, 0);
        this.right_foreleg.setRotationPoint(0.1F, 10.0F, 0.0F);
        this.right_foreleg.addBox(-2.0F, 0.0F, 0.0F, 4, 9, 4, 0.0F);
        this.right_forearm = new ModelRenderer(this, 40, 28);
        this.right_forearm.setRotationPoint(-0.4F, 9.8F, 0.0F);
        this.right_forearm.addBox(-1.0F, 0.0F, 0.0F, 3, 12, 3, 0.0F);
        this.left_arm = new ModelRenderer(this, 52, 28);
        this.left_arm.setRotationPoint(4.2F, 4.8F, 3.8F);
        this.left_arm.addBox(-1.5F, 0.0F, 0.0F, 3, 12, 3, 0.0F);
        this.left_foreleg = new ModelRenderer(this, 0, 0);
        this.left_foreleg.setRotationPoint(-0.1F, 10.0F, 0.0F);
        this.left_foreleg.addBox(-2.0F, 0.0F, 0.0F, 4, 9, 4, 0.0F);
        this.right_arm = new ModelRenderer(this, 52, 28);
        this.right_arm.setRotationPoint(-4.2F, 4.8F, 3.8F);
        this.right_arm.addBox(-1.5F, 0.0F, 0.0F, 3, 12, 3, 0.0F);
        this.body = new ModelRenderer(this, 0, 42);
        this.body.setRotationPoint(0.0F, -5.0F, -1.0F);
        this.body.addBox(-5.0F, 0.0F, 0.0F, 10, 15, 7, 0.0F);
        this.head.addChild(this.jaw);
        this.body.addChild(this.left_leg);
        this.upper_body.addChild(this.head);
        this.left_arm.addChild(this.left_forearm);
        this.body.addChild(this.right_leg);
        this.right_leg.addChild(this.right_foreleg);
        this.right_arm.addChild(this.right_forearm);
        this.upper_body.addChild(this.left_arm);
        this.left_leg.addChild(this.left_foreleg);
        this.upper_body.addChild(this.right_arm);
        bodyParts.add(body);
        bodyParts.add(upper_body);
        bodyParts.add(left_leg);
        bodyParts.add(right_leg);
        bodyParts.add(left_foreleg);
        bodyParts.add(right_foreleg);
        bodyParts.add(left_arm);
        bodyParts.add(right_arm);
        bodyParts.add(head);
        bodyParts.add(left_forearm);
        bodyParts.add(right_forearm);
        bodyParts.add(jaw);
        this.setAngles(true);
        for(ModelRenderer mr : bodyParts) {
    		if(!defaultAngles.containsKey(mr)) {
    			EnumMap<Transformation, Float> map = new EnumMap<>(Transformation.class);
    	        map.put(Transformation.ROTX, 0F);
    	        map.put(Transformation.ROTY, 0F);
    	        map.put(Transformation.ROTZ, 0F);
    	        defaultAngles.put(mr, map);
    		}
    	}
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.upper_body.render(f5);
        this.body.render(f5);
    }
    
    protected void setAngles(boolean addToDefault) {
        this.setRotateAngle(jaw, 0.22759093446006054F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(left_leg, -0.40980330836826856F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(head, -0.7285004297824331F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(left_forearm, -0.7285004297824331F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(upper_body, 0.9105382707654417F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(right_leg, -0.40980330836826856F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(right_foreleg, 0.4553564018453205F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(right_forearm, -0.7285004297824331F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(left_arm, -0.8651597102135892F, -0.22759093446006054F, -0.6829473363053812F, addToDefault);
        this.setRotateAngle(left_foreleg, 0.4553564018453205F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(right_arm, -0.8651597102135892F, 0.22759093446006054F, 0.5637413483941685F, addToDefault);
        this.setRotateAngle(body, 0.18203784098300857F, 0.0F, 0.0F, addToDefault);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z, boolean addToDefault) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
        if(addToDefault) {
	        EnumMap<Transformation, Float> map = new EnumMap<>(Transformation.class);
	        map.put(Transformation.ROTX, x);
	        map.put(Transformation.ROTY, y);
	        map.put(Transformation.ROTZ, z);
	        defaultAngles.put(modelRenderer, map);
        }
    }
    
    public void resetOffsets(List<ModelRenderer> list) {
    	for(ModelRenderer mr : list) {
    		mr.offsetX = 0;
    		mr.offsetY = 0;
    		mr.offsetZ = 0;
    	}
    }
    
    @Override
    public void setLivingAnimations(EntityLivingBase e, float limbSwing, float limbSwingAmount,
    		float partialTickTime) {
    	this.setAngles(false);
    	EntityBloodZombie z = (EntityBloodZombie)e;
    	this.resetOffsets(bodyParts);
    	int animTicks = z.getAnimCounter();
    	this.left_leg.rotateAngleX = -0.6F + MathHelper.cos(limbSwing * 0.662F) * limbSwingAmount;
    	this.right_leg.rotateAngleX = -0.6F + MathHelper.cos(limbSwing * 0.662F + (float)Math.PI) * limbSwingAmount;
    	this.jaw.rotateAngleX = 0.22759093446006054F + MathHelper.sin((animTicks%40 + partialTickTime)/ 6.366F)/20;
    	float offset = (float) MathHelper.sin((animTicks%50 + partialTickTime) / 7.95F)/96;
    	this.right_arm.offsetY = offset;
    	this.left_arm.offsetY = offset;
    	this.upper_body.offsetY = offset / 2;
    	this.upper_body.rotateAngleX = 0.9105382707654417F + offset;
    	Animation attack = z.getAttackAnimation();
    	if(attack != null) {
    		attack.applyTransformations(bodyParts, partialTickTime);
    	} else {
	    	Animation idle = z.getIdleAnimation();
	    	if(idle != null) {
	    		idle.applyTransformations(bodyParts, partialTickTime);
	    	}
    	}
    	super.setLivingAnimations(e, limbSwing, limbSwingAmount, partialTickTime);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
    		float headPitch, float scaleFactor, Entity entityIn) {
    	if(((EntityBloodZombie)entityIn).getIdleAnimation() == null) {
    		this.head.rotateAngleY = netHeadYaw * 0.007F;
        	this.head.rotateAngleX = -0.7285004297824331F + headPitch * 0.007F;
    	}
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    }
}
