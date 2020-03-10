package com.valeriotor.beyondtheveil.entities.models;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationTemplate.Transformation;
import com.valeriotor.beyondtheveil.entities.EntityDeepOne;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * Deep One.tcn - TechneToTabulaImporter
 * Created using Tabula 7.0.0
 */
public class ModelDeepOne extends ModelAnimated {
    public ModelRenderer Body;
    public ModelRenderer LowerBody;
    public ModelRenderer LeftUpperLeg;
    public ModelRenderer LeftLowerLeg;
    public ModelRenderer RightUpperLeg;
    public ModelRenderer RightLowerLeg;
    public ModelRenderer Head;
    public ModelRenderer DorsalFin;
    public ModelRenderer LeftUpperArm;
    public ModelRenderer LeftLowerArm;
    public ModelRenderer RightUpperArm;
    public ModelRenderer RightLowerArm;
    public ModelRenderer HeadFin;
    public ModelRenderer LeftMouth;
    public ModelRenderer RightMouth;
    public ModelRenderer BottomMouth;
    public ModelRenderer BackMouth;
    
    
    public ModelDeepOne() {
    	this.textureWidth = 128;
        this.textureHeight = 32;
        this.Head = new ModelRenderer(this, 50, 16);
        this.Head.setRotationPoint(0.0F, -8.0F, -5.73F);
        this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 6, 8, 0.0F);
        this.DorsalFin = new ModelRenderer(this, 52, 2);
        this.DorsalFin.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.DorsalFin.addBox(-0.5F, -11.0F, 0.0F, 1, 11, 3, 0.0F);
        this.LeftUpperLeg = new ModelRenderer(this, 94, 0);
        this.LeftUpperLeg.setRotationPoint(5.0F, 8.0F, 3.0F);
        this.LeftUpperLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.LeftLowerArm = new ModelRenderer(this, 94, 0);
        this.LeftLowerArm.setRotationPoint(11.800000190734863F, 3.0F, 0.0F);
        this.LeftLowerArm.addBox(-12.4F, 5.0F, 5.0F, 2, 11, 2, 0.0F);
        this.LeftMouth = new ModelRenderer(this, 81, 15);
        this.LeftMouth.setRotationPoint(3.0F, -2.0F, 1.0F);
        this.LeftMouth.addBox(0.0F, 0.0F, -5.0F, 1, 2, 5, 0.0F);
        this.BottomMouth = new ModelRenderer(this, 106, 25);
        this.BottomMouth.setRotationPoint(0.0F, -2.0F, 1.0F);
        this.BottomMouth.addBox(-3.0F, 0.0F, -5.0F, 6, 2, 5, 0.0F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, -8.0F, -7.0F);
        this.Body.addBox(-7.5F, 0.0F, 0.0F, 15, 13, 11, 0.0F);
        this.RightLowerLeg = new ModelRenderer(this, 94, 0);
        this.RightLowerLeg.setRotationPoint(-5.0F, 12.0F, -3.5F);
        this.RightLowerLeg.addBox(3.5F, 0.0F, 3.0F, 3, 12, 3, 0.0F);
        this.LeftUpperArm = new ModelRenderer(this, 94, 0);
        this.LeftUpperArm.setRotationPoint(7.0F, -7.0F, 0.0F);
        this.LeftUpperArm.addBox(-1.5F, 0.0F, -1.399999976158142F, 3, 12, 3, 0.0F);
        this.RightMouth = new ModelRenderer(this, 81, 15);
        this.RightMouth.mirror = true;
        this.RightMouth.setRotationPoint(-4.0F, -2.0F, 1.0F);
        this.RightMouth.addBox(0.0F, 0.0F, -5.0F, 1, 2, 5, 0.0F);
        this.LeftLowerLeg = new ModelRenderer(this, 94, 0);
        this.LeftLowerLeg.setRotationPoint(5.0F, 12.0F, -3.5F);
        this.LeftLowerLeg.addBox(-6.5F, 0.0F, 3.0F, 3, 12, 3, 0.0F);
        this.RightUpperLeg = new ModelRenderer(this, 94, 0);
        this.RightUpperLeg.setRotationPoint(-5.0F, 8.0F, 3.0F);
        this.RightUpperLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.BackMouth = new ModelRenderer(this, 106, 20);
        this.BackMouth.setRotationPoint(-4.0F, -2.0F, 1.0F);
        this.BackMouth.addBox(0.0F, 0.0F, 0.0F, 8, 2, 3, 0.0F);
        this.RightUpperArm = new ModelRenderer(this, 94, 0);
        this.RightUpperArm.setRotationPoint(-7.0F, -7.0F, 0.0F);
        this.RightUpperArm.addBox(-1.5F, 0.0F, -1.399999976158142F, 3, 12, 3, 0.0F);
        this.RightLowerArm = new ModelRenderer(this, 94, 0);
        this.RightLowerArm.setRotationPoint(-11.800000190734863F, 3.0F, 0.0F);
        this.RightLowerArm.addBox(10.5F, 5.0F, 5.0F, 2, 11, 2, 0.0F);
        this.HeadFin = new ModelRenderer(this, 82, 11);
        this.HeadFin.setRotationPoint(0.0F, -21.0F, -2.0F);
        this.HeadFin.addBox(-0.5F, 3.0F, 8.3F, 1, 9, 11, 0.0F);
        this.LowerBody = new ModelRenderer(this, 94, 0);
        this.LowerBody.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.LowerBody.addBox(-5.0F, 0.0F, -1.0F, 10, 10, 7, 0.0F);
        this.RightUpperArm.addChild(RightLowerArm);
        this.LeftUpperArm.addChild(LeftLowerArm);
        this.LeftUpperLeg.addChild(LeftLowerLeg);
        this.RightUpperLeg.addChild(RightLowerLeg);
        this.Head.addChild(HeadFin);
        this.Head.addChild(this.LeftMouth);
        this.Head.addChild(this.BottomMouth);
        this.Head.addChild(this.RightMouth);
        this.Head.addChild(this.BackMouth);
    	bodyParts.add(Body);
    	bodyParts.add(LowerBody);
    	bodyParts.add(LeftUpperLeg);
    	bodyParts.add(LeftLowerLeg);
    	bodyParts.add(RightUpperLeg);
    	bodyParts.add(RightLowerLeg);
    	bodyParts.add(Head);
    	bodyParts.add(DorsalFin);
    	bodyParts.add(LeftUpperArm);
    	bodyParts.add(LeftLowerArm);
    	bodyParts.add(RightUpperArm);
    	bodyParts.add(RightLowerArm);
    	bodyParts.add(HeadFin);
    	bodyParts.add(LeftMouth);
    	bodyParts.add(RightMouth);
    	bodyParts.add(BottomMouth);
    	bodyParts.add(BackMouth);
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
        this.LowerBody.render(f5);
        this.LeftUpperLeg.render(f5);
        this.RightUpperLeg.render(f5);
        this.DorsalFin.render(f5);
        this.Head.render(f5);
        this.LeftUpperArm.render(f5);
        this.RightUpperArm.render(f5);
        this.Body.render(f5);
    }
    
    public void setAngles(boolean addToDefault) {
    	this.setRotateAngle(LeftMouth, 0, 0, 0, addToDefault);
    	this.setRotateAngle(RightMouth, 0, 0, 0, addToDefault);
    	this.setRotateAngle(BottomMouth, 0, 0, 0, addToDefault);
    	this.setRotateAngle(LeftLowerLeg, 0.9F, -0.0F, 0.0F, addToDefault);
    	this.setRotateAngle(RightLowerLeg, 0.9F, -0.0F, 0.0F, addToDefault);
    	this.setRotateAngle(LeftUpperLeg, -0.6F, -0.0F, 0.0F, addToDefault);
    	this.setRotateAngle(LeftLowerArm, -0.855108082294464F, -0.0F, 0.0F, addToDefault);
    	this.setRotateAngle(RightUpperLeg, -0.6F, -0.0F, 0.0F, addToDefault);
    	this.setRotateAngle(DorsalFin, 0.40896472334861755F, -0.0F, 0.0F, addToDefault);
    	this.setRotateAngle(HeadFin, -0.6108652353286743F, -0.0F, 0.0F, addToDefault);
    	this.setRotateAngle(LeftUpperArm, 0.0F, -0.0F, -0.43633231520652765F, addToDefault);
    	this.setRotateAngle(RightUpperArm, 0.0F, -0.0F, 0.43633231520652765F, addToDefault);
    	this.setRotateAngle(Body, 0.37178611755371094F, -0.0F, 0.0F, addToDefault);
    	this.setRotateAngle(RightLowerArm, -0.855108082294464F, -0.0F, 0.0F, addToDefault);
    	this.setRotateAngle(Head, 0, 0, 0, addToDefault);
        
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
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	
    	if((entityIn instanceof EntityPlayer && BeyondTheVeil.proxy.cEvents.playerAnimations.get((EntityPlayer)entityIn) == null)
    		|| (entityIn instanceof EntityDeepOne)	&& ((EntityDeepOne)entityIn).getRoarAnim() == null) {
    		this.Head.rotateAngleY = netHeadYaw * 0.01745F;
        	this.Head.rotateAngleX = headPitch * 0.01745F;
    	}
    	//System.out.println((entityIn instanceof EntityPlayer) + " " + this.BackMouth.offsetX + " " + this.BackMouth.offsetY + " " + this.BackMouth.offsetZ);
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
    	this.resetOffsets(bodyParts);
    	if(e instanceof EntityPlayer) {
    		Animation anim = BeyondTheVeil.proxy.cEvents.playerAnimations.get((EntityPlayer)e);
    		if(anim != null && anim.type.modelType instanceof ModelDeepOne) {
    			if(!anim.isDone()) anim.applyTransformations(bodyParts, partialTickTime);
    		}else {
	        	this.LeftUpperLeg.rotateAngleX = -0.6F + MathHelper.cos(limbSwing * 0.662F) * limbSwingAmount;
	        	this.RightUpperLeg.rotateAngleX = -0.6F + MathHelper.cos(limbSwing * 0.662F + (float)Math.PI) * limbSwingAmount;
	    		if(((EntityPlayer)e).getPrimaryHand() == EnumHandSide.RIGHT)
	    			this.RightUpperArm.rotateAngleX = -0.9F + MathHelper.cos(((EntityPlayer)e).swingProgress * 2.5F);
	    		else
	    			this.LeftUpperArm.rotateAngleX = -0.9F + MathHelper.cos(((EntityPlayer)e).swingProgress * 2.5F);  
    		}
    	}else if(e instanceof EntityDeepOne){
    		EntityDeepOne d = (EntityDeepOne)e;
    		Animation anim = d.getRoarAnim();
    		if(anim != null) {
    			if(!anim.isDone()) anim.applyTransformations(bodyParts, partialTickTime);
    		}else {
    			anim = d.getAttackAnimation();
    			if(anim != null)
    				anim.applyTransformations(bodyParts, partialTickTime);
    			this.LeftUpperLeg.rotateAngleX = -0.6F + MathHelper.cos(limbSwing * 0.662F) * limbSwingAmount;
    	    	this.RightUpperLeg.rotateAngleX = -0.6F + MathHelper.cos(limbSwing * 0.662F + (float)Math.PI) * limbSwingAmount;
    		}
    		
    		
    	}
    }
}
