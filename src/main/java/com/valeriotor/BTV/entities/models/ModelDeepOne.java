package com.valeriotor.BTV.entities.models;

import com.valeriotor.BTV.entities.EntityDeepOne;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * Deep One.tcn - TechneToTabulaImporter
 * Created using Tabula 7.0.0
 */
public class ModelDeepOne extends ModelBase {
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

    public ModelDeepOne() {
        this.textureWidth = 128;
        this.textureHeight = 32;
        this.LeftLowerLeg = new ModelRenderer(this, 94, 0);
        this.LeftLowerLeg.setRotationPoint(5.0F, 12.0F, -3.5F);
        this.LeftLowerLeg.addBox(-6.5F, 0.0F, 3.0F, 3, 12, 3, 0.0F);
        this.setRotateAngle(LeftLowerLeg, 0.9F, -0.0F, 0.0F);
        this.RightLowerLeg = new ModelRenderer(this, 94, 0);
        this.RightLowerLeg.setRotationPoint(-5.0F, 12.0F, -3.5F);
        this.RightLowerLeg.addBox(3.5F, 0.0F, 3.0F, 3, 12, 3, 0.0F);
        this.setRotateAngle(RightLowerLeg, 0.9F, -0.0F, 0.0F);
        this.LowerBody = new ModelRenderer(this, 94, 0);
        this.LowerBody.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.LowerBody.addBox(-5.0F, 0.0F, -1.0F, 10, 10, 7, 0.0F);
        this.LeftUpperLeg = new ModelRenderer(this, 94, 0);
        this.LeftUpperLeg.setRotationPoint(5.0F, 8.0F, 3.0F);
        this.LeftUpperLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.setRotateAngle(LeftUpperLeg, -0.8726646304130553F, -0.0F, 0.0F);
        this.LeftLowerArm = new ModelRenderer(this, 94, 0);
        this.LeftLowerArm.setRotationPoint(11.800000190734863F, 3.0F, 0.0F);
        this.LeftLowerArm.addBox(-12.4F, 5.0F, 5.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(LeftLowerArm, -0.855108082294464F, -0.0F, 0.0F);
        this.RightUpperLeg = new ModelRenderer(this, 94, 0);
        this.RightUpperLeg.setRotationPoint(-5.0F, 8.0F, 3.0F);
        this.RightUpperLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.setRotateAngle(RightUpperLeg, -0.8726646304130553F, -0.0F, 0.0F);
        this.DorsalFin = new ModelRenderer(this, 52, 0);
        this.DorsalFin.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.DorsalFin.addBox(-0.5F, -11.0F, 0.0F, 1, 11, 3, 0.0F);
        this.setRotateAngle(DorsalFin, 0.40896472334861755F, -0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 52, 16);
        this.Head.setRotationPoint(0.0F, -8.0F, -5.733333110809326F);
        this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.HeadFin = new ModelRenderer(this, 85, 12);
        this.HeadFin.setRotationPoint(0.0F, -21.0F, -2.0F);
        this.HeadFin.addBox(-0.5F, 3.0F, 8.3F, 1, 9, 11, 0.0F);
        this.setRotateAngle(HeadFin, -0.6108652353286743F, -0.0F, 0.0F);
        this.LeftUpperArm = new ModelRenderer(this, 94, 0);
        this.LeftUpperArm.setRotationPoint(7.0F, -7.0F, 0.0F);
        this.LeftUpperArm.addBox(-1.5F, 0.0F, -1.399999976158142F, 3, 12, 3, 0.0F);
        this.setRotateAngle(LeftUpperArm, 0.0F, -0.0F, -0.43633231520652765F);
        this.RightUpperArm = new ModelRenderer(this, 94, 0);
        this.RightUpperArm.setRotationPoint(-7.0F, -7.0F, 0.0F);
        this.RightUpperArm.addBox(-1.5F, 0.0F, -1.399999976158142F, 3, 12, 3, 0.0F);
        this.setRotateAngle(RightUpperArm, 0.0F, -0.0F, 0.43633231520652765F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, -8.0F, -7.0F);
        this.Body.addBox(-7.5F, 0.0F, 0.0F, 15, 13, 11, 0.0F);
        this.setRotateAngle(Body, 0.37178611755371094F, -0.0F, 0.0F);
        this.RightLowerArm = new ModelRenderer(this, 94, 0);
        this.RightLowerArm.setRotationPoint(-11.800000190734863F, 3.0F, 0.0F);
        this.RightLowerArm.addBox(10.5F, 5.0F, 5.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(RightLowerArm, -0.855108082294464F, -0.0F, 0.0F);
        this.RightUpperArm.addChild(RightLowerArm);
        this.LeftUpperArm.addChild(LeftLowerArm);
        this.LeftUpperLeg.addChild(LeftLowerLeg);
        this.RightUpperLeg.addChild(RightLowerLeg);
        this.Head.addChild(HeadFin);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        //this.LeftLowerLeg.render(f5);
        //this.RightLowerLeg.render(f5);
        this.LowerBody.render(f5);
        this.LeftUpperLeg.render(f5);
        //this.LeftLowerArm.render(f5);
        this.RightUpperLeg.render(f5);
        this.DorsalFin.render(f5);
        this.Head.render(f5);
        //this.HeadFin.render(f5);
        this.LeftUpperArm.render(f5);
        this.RightUpperArm.render(f5);
        this.Body.render(f5);
        //this.RightLowerArm.render(f5);
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
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	//super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    	this.LeftUpperLeg.rotateAngleX = -0.6F + MathHelper.cos(limbSwing * 0.662F) * limbSwingAmount;
    	this.RightUpperLeg.rotateAngleX = -0.6F + MathHelper.cos(limbSwing * 0.662F + (float)Math.PI) * limbSwingAmount;
    	
    	this.Head.rotateAngleY = netHeadYaw * 0.01745F;
    	this.Head.rotateAngleX = headPitch * 0.01745F;
    	if(entityIn instanceof EntityPlayer) {
    		if(((EntityPlayer)entityIn).getPrimaryHand() == EnumHandSide.RIGHT)
    			this.RightUpperArm.rotateAngleX = -0.9F + MathHelper.cos(((EntityPlayer)entityIn).swingProgress * 2.5F);
    		else
    			this.LeftUpperArm.rotateAngleX = -0.9F + MathHelper.cos(((EntityPlayer)entityIn).swingProgress * 2.5F);    			
    	}else if(entityIn instanceof EntityDeepOne){
    		
    		switch(((EntityDeepOne)entityIn).getRaisedArm()) {
    			case 0:
    				this.RightUpperArm.rotateAngleX = 0F;
    				this.LeftUpperArm.rotateAngleX = 0F; 
    				break;
    			case 1:	
    				this.RightUpperArm.rotateAngleX = -1.3F;
    				break;
    			case 2:
    				this.LeftUpperArm.rotateAngleX = -1.3F;
    				break;
    			case 3:
    				this.LeftUpperArm.rotateAngleX = -1.3F;
    				this.RightUpperArm.rotateAngleX = -1.3F;
    		}
    		
    	}
    }
}
