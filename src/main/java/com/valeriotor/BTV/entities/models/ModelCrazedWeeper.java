package com.valeriotor.BTV.entities.models;

import com.valeriotor.BTV.entities.EntityCrazedWeeper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 7.0.0
 */
public class ModelCrazedWeeper extends ModelBase {
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer LeftLeg;
    public ModelRenderer CentralBody;
    public ModelRenderer Body;
    public ModelRenderer RightLeg;
    public ModelRenderer HeadLeft;
    public ModelRenderer HeadRight;
    public ModelRenderer HeadFront;
    public ModelRenderer HeadSpine;
    public ModelRenderer RightTentacle11;
    public ModelRenderer RightTentacle21;
    public ModelRenderer RightTentacle12;
    public ModelRenderer RightTentacle22;
    public ModelRenderer LeftTentacle11;
    public ModelRenderer LeftTentacle21;
    public ModelRenderer LeftTentacle12;
    public ModelRenderer LeftTentacle22;
    public ModelRenderer HeadTentacle1;
    public ModelRenderer HeadTentacle2;
    public ModelRenderer HeadTentacle3;

    public ModelCrazedWeeper() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.LeftTentacle11 = new ModelRenderer(this, 56, 54);
        this.LeftTentacle11.setRotationPoint(0.0F, 10.0F, 1.0F);
        this.LeftTentacle11.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.LeftTentacle22 = new ModelRenderer(this, 56, 54);
        this.LeftTentacle22.setRotationPoint(0.0F, 5.4F, 0.0F);
        this.LeftTentacle22.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.RightLeg = new ModelRenderer(this, 0, 22);
        this.RightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.RightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.HeadTentacle1 = new ModelRenderer(this, 56, 54);
        this.HeadTentacle1.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.HeadTentacle1.addBox(0.0F, -9.0F, 0.0F, 1, 9, 1, 0.0F);
        this.HeadTentacle3 = new ModelRenderer(this, 56, 54);
        this.HeadTentacle3.setRotationPoint(1.0F, -3.0F, 0.0F);
        this.HeadTentacle3.addBox(-1.0F, -9.0F, -1.0F, 1, 9, 1, 0.0F);
        this.HeadTentacle2 = new ModelRenderer(this, 56, 54);
        this.HeadTentacle2.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.HeadTentacle2.addBox(-1.0F, -9.0F, -1.0F, 1, 9, 1, 0.0F);
        this.RightArm = new ModelRenderer(this, 44, 7);
        this.RightArm.setRotationPoint(-5.5F, 1.0F, -1.0F);
        this.RightArm.addBox(-2.0F, 0.0F, 0.0F, 4, 11, 4, 0.0F);
        this.HeadRight = new ModelRenderer(this, 0, 0);
        this.HeadRight.setRotationPoint(-10.8F, -5.7F, 1.8F);
        this.HeadRight.addBox(0.0F, 0.0F, 0.0F, 8, 9, 9, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 0, 22);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.LeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.LeftTentacle12 = new ModelRenderer(this, 56, 54);
        this.LeftTentacle12.setRotationPoint(0.0F, 5.4F, 0.0F);
        this.LeftTentacle12.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.HeadLeft = new ModelRenderer(this, 0, 0);
        this.HeadLeft.setRotationPoint(0.7F, -7.5F, 2.6F);
        this.HeadLeft.addBox(0.0F, 0.0F, -1.0F, 8, 9, 8, 0.0F);
        this.RightTentacle21 = new ModelRenderer(this, 56, 54);
        this.RightTentacle21.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.RightTentacle21.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.LeftTentacle21 = new ModelRenderer(this, 56, 54);
        this.LeftTentacle21.setRotationPoint(-1.5F, 10.0F, 1.5F);
        this.LeftTentacle21.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.Body = new ModelRenderer(this, 0, 38);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.5F);
        this.RightTentacle12 = new ModelRenderer(this, 56, 54);
        this.RightTentacle12.setRotationPoint(0.0F, 5.4F, 0.0F);
        this.RightTentacle12.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.HeadFront = new ModelRenderer(this, 0, 0);
        this.HeadFront.setRotationPoint(-5.0F, -2.2F, -11.0F);
        this.HeadFront.addBox(0.0F, 0.0F, 0.0F, 8, 8, 8, 0.0F);
        this.RightTentacle11 = new ModelRenderer(this, 56, 54);
        this.RightTentacle11.setRotationPoint(1.0F, 10.3F, 0.5F);
        this.RightTentacle11.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.LeftArm = new ModelRenderer(this, 44, 7);
        this.LeftArm.setRotationPoint(5.0F, 0.8F, -1.0F);
        this.LeftArm.addBox(-2.0F, 0.0F, 0.0F, 4, 11, 4, 0.0F);
        this.HeadSpine = new ModelRenderer(this, 56, 54);
        this.HeadSpine.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.HeadSpine.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.RightTentacle22 = new ModelRenderer(this, 56, 54);
        this.RightTentacle22.setRotationPoint(0.0F, 5.4F, 0.0F);
        this.RightTentacle22.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.CentralBody = new ModelRenderer(this, 16, 20);
        this.CentralBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.CentralBody.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.LeftArm.addChild(this.LeftTentacle11);
        this.LeftTentacle21.addChild(this.LeftTentacle22);
        this.HeadSpine.addChild(this.HeadTentacle1);
        this.HeadSpine.addChild(this.HeadTentacle3);
        this.HeadSpine.addChild(this.HeadTentacle2);
        this.LeftTentacle11.addChild(this.LeftTentacle12);
        this.RightArm.addChild(this.RightTentacle21);
        this.LeftArm.addChild(this.LeftTentacle21);
        this.RightTentacle11.addChild(this.RightTentacle12);
        this.RightArm.addChild(this.RightTentacle11);
        this.RightTentacle21.addChild(this.RightTentacle22);
        this.setAngles();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.RightLeg.render(f5);
        this.RightArm.render(f5);
        this.HeadRight.render(f5);
        this.LeftLeg.render(f5);
        this.HeadLeft.render(f5);
        this.Body.render(f5);
        this.HeadFront.render(f5);
        this.LeftArm.render(f5);
        this.HeadSpine.render(f5);
        this.CentralBody.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setAngles() {
        this.setRotateAngle(LeftTentacle11, -0.27314402793711257F, 0.0F, 0.0F);
        this.setRotateAngle(LeftTentacle22, 0.8196066167365371F, 0.0F, 0.6829473363053812F);
        this.setRotateAngle(HeadTentacle1, -0.22759093446006054F, 0.0F, 0.18203784098300857F);
        this.setRotateAngle(HeadTentacle3, 0.40980330836826856F, 0.0F, 0.091106186954104F);
        this.setRotateAngle(HeadTentacle2, 0.18203784098300857F, 0.0F, -0.40980330836826856F);
        this.setRotateAngle(RightArm, -0.7464773210779748F, 0.6806784082777886F, 0.0F);
        this.setRotateAngle(LeftTentacle12, 0.7740535232594852F, 0.0F, 0.0F);
        this.setRotateAngle(RightTentacle21, -0.27314402793711257F, 0.0F, 0.0F);
        this.setRotateAngle(LeftTentacle21, 0.0F, 0.22759093446006054F, 0.22759093446006054F);
        this.setRotateAngle(RightTentacle12, 0.7740535232594852F, 0.0F, 0.0F);
        this.setRotateAngle(RightTentacle11, 0.0F, -0.22689280275926282F, -0.22689280275926282F);
        this.setRotateAngle(LeftArm, -0.7499679795819634F, -0.6829473363053812F, 0.0F);
        this.setRotateAngle(RightTentacle22, 0.8196066167365371F, 0.0F, 0.6829473363053812F);
    }
    
    @Override
    public void setLivingAnimations(EntityLivingBase e, float limbSwing, float limbSwingAmount,
    		float partialTickTime) {
    	if(!(e instanceof EntityCrazedWeeper)) return;
    	EntityCrazedWeeper weeper = (EntityCrazedWeeper)e;
    	float animTicks = weeper.getAnimationTicks();
    	float offset = (float) Math.sin(animTicks%40 / 6.366)/20;
    	this.HeadFront.offsetX = offset/2;
    	this.HeadLeft.offsetY = offset/2;
    	this.HeadRight.offsetX = -offset/4;
    	this.HeadLeft.offsetZ = offset/4;
    	this.LeftArm.rotateAngleX = -0.7499679795819634F + offset;
    	this.LeftTentacle11.rotateAngleZ = -offset;
    	this.LeftTentacle22.rotateAngleX = 0.8196066167365371F + offset;
    	this.HeadTentacle1.rotateAngleX = -0.22759093446006054F - offset;
    	this.HeadTentacle3.rotateAngleZ = 0.091106186954104F + offset;
    	this.LeftTentacle12.rotateAngleX = 0.7740535232594852F + offset;
    	this.HeadTentacle2.rotateAngleX = 0.18203784098300857F + offset;
    	this.LeftTentacle21.rotateAngleX = offset;
    	this.RightTentacle11.rotateAngleX = -offset;
    	this.RightTentacle12.rotateAngleZ = -offset;
    	this.RightTentacle21.rotateAngleZ = 0.22759093446006054F + offset;
    	this.RightTentacle22.rotateAngleX = 0.8196066167365371F - offset;
    	offset = (float) Math.sin(animTicks%50 / 7.95)/16;
    	this.HeadFront.offsetY = offset/2;
    	this.HeadLeft.offsetX = offset/2;
    	this.HeadRight.offsetY = offset/2;
    	this.HeadFront.offsetZ = -offset/4;
    	this.HeadRight.offsetZ = offset/4;
    	this.LeftTentacle11.rotateAngleX = -0.27314402793711257F + offset;
    	this.LeftTentacle22.rotateAngleZ = 0.6829473363053812F + offset;
    	this.HeadTentacle1.rotateAngleZ = 0.18203784098300857F + offset;
    	this.HeadTentacle3.rotateAngleX = 0.40980330836826856F + offset;
    	this.LeftTentacle12.rotateAngleZ = offset;
    	this.HeadTentacle2.rotateAngleZ = -0.40980330836826856F - offset;
    	this.LeftTentacle21.rotateAngleZ = 0.22759093446006054F + offset;
    	this.RightTentacle11.rotateAngleZ = -0.22689280275926282F + offset;
    	this.RightTentacle12.rotateAngleX = 0.7740535232594852F + offset;
    	this.RightTentacle21.rotateAngleX = -offset;
    	this.RightTentacle22.rotateAngleZ = 0.6829473363053812F + offset;
		this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.662F) * limbSwingAmount/2;
    	this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.662F + (float)Math.PI) * limbSwingAmount/2;
    	super.setLivingAnimations(e, limbSwing, limbSwingAmount, partialTickTime);
    }
}
