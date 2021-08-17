package com.valeriotor.beyondtheveil.entities.models;

import java.util.EnumMap;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationTemplate.Transformation;
import com.valeriotor.beyondtheveil.entities.EntityShoggoth;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelShoggoth - valeriotor
 * Created using Tabula 7.0.0
 */
public class ModelShoggoth extends ModelAnimated {
    public ModelRenderer mouthBottom;						//0
    public ModelRenderer mouthBack;							//1
    public ModelRenderer mouthLeft;							//2
    public ModelRenderer mouthRight;						//3
    public ModelRenderer mouthUpper;						//4
    public ModelRenderer sideLarge;							//5
    public ModelRenderer sideSmall;							//6
    public ModelRenderer otherChunk;						//7
    public ModelRenderer leftTentacleLower;					//8
    public ModelRenderer eyeTentacleLower;					//9
    public ModelRenderer bottom;							//10
    public ModelRenderer rightTentacleLower;				//11
    public ModelRenderer lowerTooth1;						//12
    public ModelRenderer lowerTooth2;						//13
    public ModelRenderer mouthTentacle1;					//14
    public ModelRenderer mouthTentacle2;					//15
    public ModelRenderer lowerTentacleLip;					//16
    public ModelRenderer upperTentacleLip;					//17
    public ModelRenderer upperTooth1;						//18
    public ModelRenderer upperTooth2;						//19
    public ModelRenderer upperTooth3;						//20
    public ModelRenderer leftTentacleUpper;					//21
    public ModelRenderer eyeTentacleMid;					//22
    public ModelRenderer eyeTentacleUpper;					//23
    public ModelRenderer eye;								//24
    public ModelRenderer bRightTentacle1;					//25
    public ModelRenderer bLeftTentacle1;					//26
    public ModelRenderer bBackTentacle1;					//27
    public ModelRenderer bRightTentacle2;					//28
    public ModelRenderer bRightTentacle3;					//29
    public ModelRenderer bRightTentacle4;					//30
    public ModelRenderer bLeftTentacle2;					//31
    public ModelRenderer bLeftTentacle3;					//32
    public ModelRenderer bLeftTentacle4;					//33
    public ModelRenderer bBackTentacle2;					//34
    public ModelRenderer bBackTentacle3;					//35
    public ModelRenderer bBackTentacle4;					//36
    public ModelRenderer rightTentacleUpper;				//37

    public ModelShoggoth() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.lowerTooth1 = new ModelRenderer(this, 0, 22);
        this.lowerTooth1.setRotationPoint(4.0F, 0.0F, 0.0F);
        this.lowerTooth1.addBox(-2.0F, -24.0F, 0.0F, 5, 24, 4, 0.0F);
        this.sideSmall = new ModelRenderer(this, 0, 0);
        this.sideSmall.setRotationPoint(7.0F, 10.0F, -18.0F);
        this.sideSmall.addBox(0.0F, 0.0F, 0.0F, 22, 7, 18, 0.0F);
        this.lowerTentacleLip = new ModelRenderer(this, 6, 77);
        this.lowerTentacleLip.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.lowerTentacleLip.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 8, 0.0F);
        this.bRightTentacle4 = new ModelRenderer(this, 0, 0);
        this.bRightTentacle4.setRotationPoint(0.0F, 2.0F, -8.0F);
        this.bRightTentacle4.addBox(-3.0F, 0.0F, -8.0F, 6, 6, 8, 0.0F);
        this.bLeftTentacle4 = new ModelRenderer(this, 0, 0);
        this.bLeftTentacle4.setRotationPoint(0.0F, 2.0F, -8.0F);
        this.bLeftTentacle4.addBox(-3.0F, 0.0F, -8.0F, 6, 6, 8, 0.0F);
        this.eyeTentacleLower = new ModelRenderer(this, 0, 0);
        this.eyeTentacleLower.setRotationPoint(0.2F, -13.5F, -7.0F);
        this.eyeTentacleLower.addBox(-3.0F, -13.0F, 0.0F, 6, 13, 6, 0.0F);
        this.mouthBottom = new ModelRenderer(this, 28, 19);
        this.mouthBottom.setRotationPoint(-9.0F, 10.0F, -15.0F);
        this.mouthBottom.addBox(-15.0F, 0.0F, 0.0F, 30, 8, 47, 0.0F);
        this.rightTentacleLower = new ModelRenderer(this, 0, 0);
        this.rightTentacleLower.setRotationPoint(-22.0F, -4.0F, 15.3F);
        this.rightTentacleLower.addBox(-2.0F, -14.0F, 0.0F, 4, 14, 4, 0.0F);
        this.mouthBack = new ModelRenderer(this, 21, 17);
        this.mouthBack.setRotationPoint(-9.0F, -15.0F, 8.0F);
        this.mouthBack.addBox(-14.0F, 0.0F, 0.0F, 28, 25, 25, 0.0F);
        this.otherChunk = new ModelRenderer(this, 0, 0);
        this.otherChunk.setRotationPoint(12.0F, 2.0F, -7.0F);
        this.otherChunk.addBox(-6.0F, 0.0F, 0.0F, 12, 12, 12, 0.0F);
        this.bLeftTentacle3 = new ModelRenderer(this, 0, 0);
        this.bLeftTentacle3.setRotationPoint(0.0F, 1.9F, -15.7F);
        this.bLeftTentacle3.addBox(-4.0F, 0.0F, -10.0F, 8, 7, 10, 0.0F);
        this.bRightTentacle2 = new ModelRenderer(this, 67, 33);
        this.bRightTentacle2.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.bRightTentacle2.addBox(-6.0F, 0.0F, -17.0F, 12, 9, 17, 0.0F);
        this.mouthLeft = new ModelRenderer(this, 0, 0);
        this.mouthLeft.setRotationPoint(4.0F, -15.0F, -15.0F);
        this.mouthLeft.addBox(-2.0F, 0.0F, 0.0F, 4, 25, 23, 0.0F);
        this.bBackTentacle2 = new ModelRenderer(this, 68, 96);
        this.bBackTentacle2.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.bBackTentacle2.addBox(-6.0F, 0.0F, -17.0F, 12, 9, 17, 0.0F);
        this.mouthRight = new ModelRenderer(this, 51, 44);
        this.mouthRight.setRotationPoint(-24.0F, -15.0F, -15.0F);
        this.mouthRight.addBox(0.0F, 0.0F, 0.0F, 4, 25, 23, 0.0F);
        this.bRightTentacle3 = new ModelRenderer(this, 0, 0);
        this.bRightTentacle3.setRotationPoint(0.0F, 1.9F, -15.0F);
        this.bRightTentacle3.addBox(-4.0F, 0.0F, -10.0F, 8, 7, 10, 0.0F);
        this.bLeftTentacle2 = new ModelRenderer(this, 0, 0);
        this.bLeftTentacle2.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.bLeftTentacle2.addBox(-6.0F, 0.0F, -17.0F, 12, 9, 17, 0.0F);
        this.mouthTentacle2 = new ModelRenderer(this, 0, 0);
        this.mouthTentacle2.setRotationPoint(0.0F, -15.3F, 1.0F);
        this.mouthTentacle2.addBox(-1.5F, -10.0F, -0.9F, 3, 10, 3, 0.0F);
        this.bBackTentacle4 = new ModelRenderer(this, 0, 0);
        this.bBackTentacle4.setRotationPoint(0.0F, 2.0F, -8.0F);
        this.bBackTentacle4.addBox(-3.0F, 0.0F, -8.0F, 6, 6, 8, 0.0F);
        this.upperTooth1 = new ModelRenderer(this, 19, 0);
        this.upperTooth1.setRotationPoint(9.0F, 1.0F, 0.0F);
        this.upperTooth1.addBox(-2.0F, 0.0F, 0.0F, 4, 24, 4, 0.0F);
        this.eyeTentacleUpper = new ModelRenderer(this, 0, 0);
        this.eyeTentacleUpper.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.eyeTentacleUpper.addBox(-1.5F, -14.0F, 0.0F, 3, 14, 3, 0.0F);
        this.leftTentacleUpper = new ModelRenderer(this, 0, 0);
        this.leftTentacleUpper.setRotationPoint(0.0F, -14.0F, 0.0F);
        this.leftTentacleUpper.addBox(-1.0F, -12.0F, 0.0F, 2, 12, 2, 0.0F);
        this.bBackTentacle3 = new ModelRenderer(this, 0, 0);
        this.bBackTentacle3.setRotationPoint(0.0F, 1.8F, -15.0F);
        this.bBackTentacle3.addBox(-4.0F, 0.0F, -10.0F, 8, 7, 10, 0.0F);
        this.bottom = new ModelRenderer(this, 50, 15);
        this.bottom.setRotationPoint(0.0F, 17.0F, -21.0F);
        this.bottom.addBox(-27.0F, 0.0F, 0.0F, 52, 8, 52, 0.0F);
        this.bLeftTentacle1 = new ModelRenderer(this, 0, 0);
        this.bLeftTentacle1.setRotationPoint(21.0F, -0.2F, 19.0F);
        this.bLeftTentacle1.addBox(-8.0F, 0.0F, -6.0F, 16, 8, 7, 0.0F);
        this.upperTentacleLip = new ModelRenderer(this, -8, 77);
        this.upperTentacleLip.setRotationPoint(0.0F, -10.0F, -1.0F);
        this.upperTentacleLip.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 8, 0.0F);
        this.eyeTentacleMid = new ModelRenderer(this, 0, 0);
        this.eyeTentacleMid.setRotationPoint(0.0F, -11.0F, 1.5F);
        this.eyeTentacleMid.addBox(-2.0F, -17.0F, 0.0F, 4, 17, 4, 0.0F);
        this.eye = new ModelRenderer(this, 10, 54);
        this.eye.setRotationPoint(0.0F, -10.0F, -1.5F);
        this.eye.addBox(-3.0F, -5.0F, 0.0F, 6, 6, 6, 0.0F);
        this.mouthUpper = new ModelRenderer(this, 0, 0);
        this.mouthUpper.setRotationPoint(-9.0F, -15.0F, -15.0F);
        this.mouthUpper.addBox(-11.0F, 0.0F, 0.0F, 22, 1, 23, 0.0F);
        this.sideLarge = new ModelRenderer(this, 26, 1);
        this.sideLarge.setRotationPoint(6.0F, -1.0F, 0.0F);
        this.sideLarge.addBox(0.0F, 0.0F, 0.0F, 22, 18, 27, 0.0F);
        this.lowerTooth2 = new ModelRenderer(this, 13, 22);
        this.lowerTooth2.setRotationPoint(-5.0F, 0.0F, 0.0F);
        this.lowerTooth2.addBox(-2.0F, -24.0F, 0.0F, 5, 24, 4, 0.0F);
        this.mouthTentacle1 = new ModelRenderer(this, 0, 0);
        this.mouthTentacle1.setRotationPoint(0.0F, 1.0F, 20.0F);
        this.mouthTentacle1.addBox(-2.0F, -16.0F, 0.0F, 4, 16, 4, 0.0F);
        this.leftTentacleLower = new ModelRenderer(this, 0, 0);
        this.leftTentacleLower.setRotationPoint(6.0F, 0.0F, 8.0F);
        this.leftTentacleLower.addBox(-2.0F, -14.0F, 0.0F, 4, 14, 4, 0.0F);
        this.bRightTentacle1 = new ModelRenderer(this, 0, 0);
        this.bRightTentacle1.setRotationPoint(-18.0F, -2.0F, 8.0F);
        this.bRightTentacle1.addBox(-8.0F, 0.0F, -6.0F, 16, 8, 7, 0.0F);
        this.bBackTentacle1 = new ModelRenderer(this, 0, 0);
        this.bBackTentacle1.setRotationPoint(-6.0F, -0.1F, 51.0F);
        this.bBackTentacle1.addBox(-8.0F, 0.0F, -6.0F, 16, 8, 7, 0.0F);
        this.upperTooth2 = new ModelRenderer(this, 19, 0);
        this.upperTooth2.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.upperTooth2.addBox(-2.0F, 0.0F, 0.0F, 4, 24, 4, 0.0F);
        this.rightTentacleUpper = new ModelRenderer(this, 0, 0);
        this.rightTentacleUpper.setRotationPoint(0.0F, -14.0F, 0.0F);
        this.rightTentacleUpper.addBox(-1.0F, -12.0F, 0.0F, 2, 12, 2, 0.0F);
        this.upperTooth3 = new ModelRenderer(this, -13, 0);
        this.upperTooth3.setRotationPoint(-9.0F, 1.0F, 0.0F);
        this.upperTooth3.addBox(-2.0F, 0.0F, 0.0F, 4, 24, 4, 0.0F);
        this.mouthBottom.addChild(this.lowerTooth1);
        this.mouthTentacle2.addChild(this.lowerTentacleLip);
        this.bRightTentacle3.addChild(this.bRightTentacle4);
        this.bLeftTentacle3.addChild(this.bLeftTentacle4);
        this.bLeftTentacle2.addChild(this.bLeftTentacle3);
        this.bRightTentacle1.addChild(this.bRightTentacle2);
        this.bBackTentacle1.addChild(this.bBackTentacle2);
        this.bRightTentacle2.addChild(this.bRightTentacle3);
        this.bLeftTentacle1.addChild(this.bLeftTentacle2);
        this.mouthTentacle1.addChild(this.mouthTentacle2);
        this.bBackTentacle3.addChild(this.bBackTentacle4);
        this.mouthUpper.addChild(this.upperTooth1);
        this.eyeTentacleMid.addChild(this.eyeTentacleUpper);
        this.leftTentacleLower.addChild(this.leftTentacleUpper);
        this.bBackTentacle2.addChild(this.bBackTentacle3);
        this.bottom.addChild(this.bLeftTentacle1);
        this.mouthTentacle2.addChild(this.upperTentacleLip);
        this.eyeTentacleLower.addChild(this.eyeTentacleMid);
        this.eyeTentacleUpper.addChild(this.eye);
        this.mouthBottom.addChild(this.lowerTooth2);
        this.mouthBack.addChild(this.mouthTentacle1);
        this.bottom.addChild(this.bRightTentacle1);
        this.bottom.addChild(this.bBackTentacle1);
        this.mouthUpper.addChild(this.upperTooth2);
        this.rightTentacleLower.addChild(this.rightTentacleUpper);
        this.mouthUpper.addChild(this.upperTooth3);
        bodyParts.add(mouthBottom);
        bodyParts.add(mouthBack);
        bodyParts.add(mouthLeft);
        bodyParts.add(mouthRight);
        bodyParts.add(mouthUpper);
        bodyParts.add(sideLarge);
        bodyParts.add(sideSmall);
        bodyParts.add(otherChunk);
        bodyParts.add(leftTentacleLower);
        bodyParts.add(eyeTentacleLower);
        bodyParts.add(bottom);
        bodyParts.add(rightTentacleLower);
        bodyParts.add(lowerTooth1);
        bodyParts.add(lowerTooth2);
        bodyParts.add(mouthTentacle1);
        bodyParts.add(mouthTentacle2);
        bodyParts.add(lowerTentacleLip);
        bodyParts.add(upperTentacleLip);
        bodyParts.add(upperTooth1);
        bodyParts.add(upperTooth2);
        bodyParts.add(upperTooth3);
        bodyParts.add(leftTentacleUpper);
        bodyParts.add(eyeTentacleMid);
        bodyParts.add(eyeTentacleUpper);
        bodyParts.add(eye);
        bodyParts.add(bRightTentacle1);
        bodyParts.add(bLeftTentacle1);
        bodyParts.add(bBackTentacle1);
        bodyParts.add(bRightTentacle2);
        bodyParts.add(bRightTentacle3);
        bodyParts.add(bRightTentacle4);
        bodyParts.add(bLeftTentacle2);
        bodyParts.add(bLeftTentacle3);
        bodyParts.add(bLeftTentacle4);
        bodyParts.add(bBackTentacle2);
        bodyParts.add(bBackTentacle3);
        bodyParts.add(bBackTentacle4);
        bodyParts.add(rightTentacleUpper);
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
    
    protected void setAngles(boolean addToDefault) {
        this.setRotateAngle(sideSmall, 0.091106186954104F, 0.0F, 0.18203784098300857F, addToDefault);
        this.setRotateAngle(lowerTentacleLip, 0.7285004297824331F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(eyeTentacleLower, 0.27314402793711257F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(rightTentacleLower, 0.0F, 1.6390387005478748F, -1.1838568316277536F, addToDefault);
        this.setRotateAngle(mouthBack, -0.18203784098300857F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(mouthTentacle2, -0.6829473363053812F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(eyeTentacleUpper, 0.8651597102135892F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(leftTentacleUpper, -0.5462880558742251F, 0.0F, 0.27314402793711257F, addToDefault);
        this.setRotateAngle(bLeftTentacle1, 0.0F, -1.1838568316277536F, 0.0F, addToDefault);
        this.setRotateAngle(upperTentacleLip, 1.3203415791337103F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(eyeTentacleMid, 0.27314402793711257F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(eye, 0.136659280431156F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(sideLarge, 0.0F, 0.0F, 0.22759093446006054F, addToDefault);
        this.setRotateAngle(mouthTentacle1, -0.5918411493512771F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(leftTentacleLower, 0.0F, 0.0F, 0.36425021489121656F, addToDefault);
        this.setRotateAngle(bRightTentacle1, 0.0F, 0.7285004297824331F, 0.0F, addToDefault);
        this.setRotateAngle(bBackTentacle1, 0.0F, 2.86844862565268F, 0.0F, addToDefault);
        this.setRotateAngle(rightTentacleUpper, -0.5462880558742251F, 0.0F, 0.27314402793711257F, addToDefault);
        this.setRotateAngle(upperTooth1, 0, 0.0F, 0, addToDefault);
        this.setRotateAngle(upperTooth2, 0, 0.0F, 0, addToDefault);
        this.setRotateAngle(upperTooth3, 0, 0.0F, 0, addToDefault);
        this.setRotateAngle(lowerTooth1, 0, 0.0F, 0, addToDefault);
        this.setRotateAngle(lowerTooth2, 0, 0.0F, 0, addToDefault);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.sideSmall.render(f5);
        this.eyeTentacleLower.render(f5);
        this.mouthBottom.render(f5);
        this.rightTentacleLower.render(f5);
        this.mouthBack.render(f5);
        this.otherChunk.render(f5);
        this.mouthLeft.render(f5);
        this.mouthRight.render(f5);
        this.bottom.render(f5);
        this.mouthUpper.render(f5);
        this.sideLarge.render(f5);
        this.leftTentacleLower.render(f5);
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
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount,
    		float partialTickTime) {
    	this.setAngles(false);
    	// this.resetOffsets(bodyParts); // should be used if I add anims that change offsets
    	EntityShoggoth sh = (EntityShoggoth) entitylivingbaseIn;
    	int animTicks = sh.getAnimTicks();
    	float offset =  MathHelper.sin((animTicks%40 + partialTickTime) / 6.366F)/20 + MathHelper.cos(limbSwing * 0.3F) * limbSwingAmount / 5;
    	mouthBack.offsetY = offset;
    	mouthBottom.offsetY = offset;
    	mouthLeft.offsetY = offset;
    	mouthRight.offsetY = offset;
    	mouthUpper.offsetY = offset;
    	otherChunk.offsetX = offset*2;
    	sideLarge.offsetY = -offset;
    	rightTentacleLower.offsetY = offset;
    	rightTentacleLower.rotateAngleZ = -1.1838568316277536F + offset * 2;
    	leftTentacleLower.offsetY = offset;
    	leftTentacleUpper.rotateAngleZ = 0.27314402793711257F + offset * 4;
    	eyeTentacleLower.offsetY = offset;
    	bRightTentacle1.rotateAngleY = 0.7285004297824331F + offset * 1.5F;
    	bBackTentacle1.rotateAngleY = 2.86844862565268F + offset * 1.5F;
    	bLeftTentacle3.rotateAngleY = offset * 2;
    	bRightTentacle4.rotateAngleY = offset * 2;
    	bBackTentacle4.rotateAngleY = offset * 2;
    	offset = MathHelper.sin((animTicks%50 + partialTickTime) / 7.95F)/16 + MathHelper.cos(limbSwing * 0.3F) * limbSwingAmount / 5;
    	mouthBack.offsetX = offset/2;
    	mouthBottom.offsetX = offset/2;
    	mouthLeft.offsetX = offset/2;
    	mouthRight.offsetX = offset/2;
    	mouthUpper.offsetX = offset/2;
    	otherChunk.offsetY = offset;
    	sideSmall.offsetY = -offset;
    	rightTentacleUpper.rotateAngleZ = 0.27314402793711257F +offset * 4;
    	leftTentacleLower.rotateAngleZ =0.36425021489121656F + offset * 2;
    	eyeTentacleLower.offsetX = offset/2;
    	bLeftTentacle1.rotateAngleY = -1.1838568316277536F + offset * 1.5F;
    	bRightTentacle3.rotateAngleY = offset * 2;
    	bBackTentacle3.rotateAngleY = offset * 2;
    	bLeftTentacle4.rotateAngleY = offset * 2;
    	Animation openMouth = sh.getOpenMouthAnim();
    	if(openMouth != null) openMouth.applyTransformations(bodyParts, partialTickTime);
    	Animation eyeTentacle = sh.getEyeTentacleAnim();
    	if(eyeTentacle != null) eyeTentacle.applyTransformations(bodyParts, partialTickTime);
    }
}
