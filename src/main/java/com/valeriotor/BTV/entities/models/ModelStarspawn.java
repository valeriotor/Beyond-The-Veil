package com.valeriotor.BTV.entities.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * NewProject - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelStarspawn extends ModelBase {
    public ModelRenderer UpperBody;
    public ModelRenderer MidBody;
    public ModelRenderer LowerBody;
    public ModelRenderer LeftUpperLeg;
    public ModelRenderer RightUpperLeg;
    public ModelRenderer LeftUpperArm;
    public ModelRenderer headMain;
    public ModelRenderer RightUpperArm;
    public ModelRenderer shape15;
    public ModelRenderer shape16;
    public ModelRenderer headBack;
    public ModelRenderer headFront;
    public ModelRenderer tentacle1;
    public ModelRenderer tentacle2;
    public ModelRenderer tentacle3;
    public ModelRenderer tentacle4;
    public ModelRenderer tentacle5;
    public ModelRenderer tentacle6;
    public ModelRenderer tentacle7;
    public ModelRenderer tentacle7_1;
    public ModelRenderer tentacle7_2;
    public ModelRenderer tentacle7_3;
    public ModelRenderer shape15_1;
    public ModelRenderer shape16_1;
    public ModelRenderer LeftLowerLeg;
    public ModelRenderer LeftFoot;
    public ModelRenderer LeftHeel;
    public ModelRenderer RightLowerLeg;
    public ModelRenderer RightFoot;
    public ModelRenderer RightHeel;

    public ModelStarspawn() {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.shape15 = new ModelRenderer(this, 117, 145);
        this.shape15.setRotationPoint(1.0F, 6.0F, 40.0F);
        this.shape15.addBox(0.0F, 0.0F, 0.0F, 6, 39, 6, 0.0F);
        this.setRotateAngle(shape15, 0.8651597102135892F, 0.0F, 0.0F);
        this.tentacle3 = new ModelRenderer(this, 0, 0);
        this.tentacle3.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.tentacle3.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.setRotateAngle(tentacle3, -0.9948376736367678F, 0.0F, 0.0F);
        this.RightUpperArm = new ModelRenderer(this, 143, 145);
        this.RightUpperArm.setRotationPoint(-7.2F, 12.0F, 17.0F);
        this.RightUpperArm.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 48, 0.0F);
        this.setRotateAngle(RightUpperArm, -2.443460952792061F, 0.5759586531581287F, 0.0F);
        this.headBack = new ModelRenderer(this, 165, 0);
        this.headBack.setRotationPoint(0.0F, 2.0F, 48.0F);
        this.headBack.addBox(-12.0F, 0.0F, 0.0F, 24, 24, 21, 0.0F);
        this.RightLowerLeg = new ModelRenderer(this, 153, 52);
        this.RightLowerLeg.setRotationPoint(0.0F, 39.0F, 1.0F);
        this.RightLowerLeg.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 43, 0.0F);
        this.setRotateAngle(RightLowerLeg, 0.4553564018453205F, 0.0F, 0.0F);
        this.tentacle1 = new ModelRenderer(this, 0, 0);
        this.tentacle1.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.tentacle1.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.setRotateAngle(tentacle1, -1.0016444577195458F, 0.0F, 0.0F);
        this.tentacle7 = new ModelRenderer(this, 0, 0);
        this.tentacle7.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.tentacle7.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.setRotateAngle(tentacle7, -0.9948376736367678F, 0.0F, 0.0F);
        this.shape16 = new ModelRenderer(this, 143, 145);
        this.shape16.setRotationPoint(3.0F, 39.0F, 5.0F);
        this.shape16.addBox(-3.0F, 0.0F, 0.0F, 6, 6, 46, 0.0F);
        this.setRotateAngle(shape16, -2.4586453172844123F, 0.0F, 0.0F);
        this.headFront = new ModelRenderer(this, 176, 215);
        this.headFront.setRotationPoint(0.0F, 6.0F, -15.0F);
        this.headFront.addBox(-12.0F, 0.0F, 0.0F, 24, 24, 16, 0.0F);
        this.setRotateAngle(headFront, 0.22759093446006054F, 0.0F, 0.0F);
        this.shape15_1 = new ModelRenderer(this, 117, 145);
        this.shape15_1.setRotationPoint(1.0F, 6.0F, 40.0F);
        this.shape15_1.addBox(-3.0F, 0.0F, 0.0F, 6, 39, 6, 0.0F);
        this.setRotateAngle(shape15_1, 0.8651597102135892F, 0.0F, 0.0F);
        this.MidBody = new ModelRenderer(this, 0, 206);
        this.MidBody.setRotationPoint(0.0F, -60.7F, 8.8F);
        this.MidBody.addBox(-12.0F, 0.0F, 0.0F, 24, 29, 20, 0.0F);
        this.setRotateAngle(MidBody, 0.4553564018453205F, 0.0F, 0.0F);
        this.LeftHeel = new ModelRenderer(this, 0, 30);
        this.LeftHeel.setRotationPoint(0.0F, 3.0F, 40.0F);
        this.LeftHeel.addBox(-3.0F, 0.0F, 0.0F, 8, 4, 17, 0.0F);
        this.setRotateAngle(LeftHeel, 0.5462880558742251F, 0.0F, 0.0F);
        this.LeftLowerLeg = new ModelRenderer(this, 153, 52);
        this.LeftLowerLeg.setRotationPoint(0.0F, 39.0F, 1.0F);
        this.LeftLowerLeg.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 43, 0.0F);
        this.setRotateAngle(LeftLowerLeg, 0.4553564018453205F, 0.0F, 0.0F);
        this.LowerBody = new ModelRenderer(this, 88, 206);
        this.LowerBody.setRotationPoint(0.0F, -40.1F, 19.8F);
        this.LowerBody.addBox(-12.0F, 0.0F, 0.0F, 24, 29, 20, 0.0F);
        this.setRotateAngle(LowerBody, 0.18203784098300857F, 0.0F, 0.0F);
        this.tentacle5 = new ModelRenderer(this, 0, 0);
        this.tentacle5.setRotationPoint(-8.0F, 9.0F, 0.0F);
        this.tentacle5.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.setRotateAngle(tentacle5, -0.9948376736367678F, 0.0F, 0.0F);
        this.tentacle6 = new ModelRenderer(this, 0, 0);
        this.tentacle6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tentacle6.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.setRotateAngle(tentacle6, -0.9948376736367678F, 0.0F, 0.0F);
        this.RightFoot = new ModelRenderer(this, 100, 0);
        this.RightFoot.setRotationPoint(0.0F, 3.0F, 40.0F);
        this.RightFoot.addBox(-4.0F, 0.0F, 0.0F, 8, 21, 4, 0.0F);
        this.setRotateAngle(RightFoot, 0.27314402793711257F, 0.0F, 0.0F);
        this.LeftUpperArm = new ModelRenderer(this, 143, 145);
        this.LeftUpperArm.setRotationPoint(7.2F, 12.0F, 17.0F);
        this.LeftUpperArm.addBox(0.0F, 0.0F, 0.0F, 8, 8, 48, 0.0F);
        this.setRotateAngle(LeftUpperArm, -2.4586453172844123F, -0.5918411493512771F, 0.0F);
        this.tentacle7_1 = new ModelRenderer(this, 0, 0);
        this.tentacle7_1.setRotationPoint(7.0F, 5.0F, 0.0F);
        this.tentacle7_1.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.setRotateAngle(tentacle7_1, -0.9948376736367678F, 0.0F, 0.0F);
        this.tentacle4 = new ModelRenderer(this, 0, 0);
        this.tentacle4.setRotationPoint(-8.0F, 0.0F, 0.0F);
        this.tentacle4.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.setRotateAngle(tentacle4, -0.9948376736367678F, 0.0F, 0.0F);
        this.tentacle7_3 = new ModelRenderer(this, 0, 0);
        this.tentacle7_3.setRotationPoint(-8.0F, 12.0F, 0.0F);
        this.tentacle7_3.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.setRotateAngle(tentacle7_3, -0.9948376736367678F, 0.0F, 0.0F);
        this.shape16_1 = new ModelRenderer(this, 143, 145);
        this.shape16_1.setRotationPoint(0.6F, 39.0F, 5.0F);
        this.shape16_1.addBox(-3.0F, 0.0F, 0.0F, 6, 6, 46, 0.0F);
        this.setRotateAngle(shape16_1, -2.4586453172844123F, 0.0F, 0.0F);
        this.UpperBody = new ModelRenderer(this, 0, 142);
        this.UpperBody.setRotationPoint(0.0F, -75.0F, -14.0F);
        this.UpperBody.addBox(-15.0F, 0.0F, 0.0F, 30, 35, 27, 0.0F);
        this.setRotateAngle(UpperBody, 0.9105382707654417F, 0.0F, 0.0F);
        this.headMain = new ModelRenderer(this, 0, 62);
        this.headMain.setRotationPoint(0.0F, -30.0F, -11.0F);
        this.headMain.addBox(-14.0F, 0.0F, 0.0F, 28, 30, 48, 0.0F);
        this.setRotateAngle(headMain, -0.091106186954104F, 0.0F, 0.0F);
        this.LeftFoot = new ModelRenderer(this, 100, 0);
        this.LeftFoot.setRotationPoint(0.0F, 3.0F, 40.0F);
        this.LeftFoot.addBox(-4.0F, 0.0F, 0.0F, 8, 21, 4, 0.0F);
        this.setRotateAngle(LeftFoot, 0.27314402793711257F, 0.0F, 0.0F);
        this.RightHeel = new ModelRenderer(this, 0, 30);
        this.RightHeel.setRotationPoint(0.0F, 3.0F, 40.0F);
        this.RightHeel.addBox(-3.0F, 0.0F, 0.0F, 8, 4, 17, 0.0F);
        this.setRotateAngle(RightHeel, 0.5462880558742251F, 0.0F, 0.0F);
        this.LeftUpperLeg = new ModelRenderer(this, 110, 25);
        this.LeftUpperLeg.setRotationPoint(12.0F, -20.0F, 39.0F);
        this.LeftUpperLeg.addBox(-6.0F, 0.0F, 0.0F, 12, 42, 12, 0.0F);
        this.setRotateAngle(LeftUpperLeg, -1.7756979809790308F, -0.22759093446006054F, 0.0F);
        this.tentacle2 = new ModelRenderer(this, 0, 0);
        this.tentacle2.setRotationPoint(7.0F, 0.0F, 0.0F);
        this.tentacle2.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.setRotateAngle(tentacle2, -0.9948376736367678F, 0.0F, 0.0F);
        this.RightUpperLeg = new ModelRenderer(this, 110, 25);
        this.RightUpperLeg.setRotationPoint(-12.0F, -20.0F, 39.0F);
        this.RightUpperLeg.addBox(-6.0F, 0.0F, 0.0F, 12, 42, 12, 0.0F);
        this.setRotateAngle(RightUpperLeg, -1.7756979809790308F, 0.22759093446006054F, 0.0F);
        this.tentacle7_2 = new ModelRenderer(this, 0, 0);
        this.tentacle7_2.setRotationPoint(7.0F, 10.0F, 0.0F);
        this.tentacle7_2.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.setRotateAngle(tentacle7_2, -0.9948376736367678F, 0.0F, 0.0F);
        this.LeftUpperArm.addChild(this.shape15);
        this.headFront.addChild(this.tentacle3);
        this.UpperBody.addChild(this.RightUpperArm);
        this.headMain.addChild(this.headBack);
        this.RightUpperLeg.addChild(this.RightLowerLeg);
        this.headFront.addChild(this.tentacle1);
        this.headFront.addChild(this.tentacle7);
        this.shape15.addChild(this.shape16);
        this.headMain.addChild(this.headFront);
        this.RightUpperArm.addChild(this.shape15_1);
        this.LeftLowerLeg.addChild(this.LeftHeel);
        this.LeftUpperLeg.addChild(this.LeftLowerLeg);
        this.headFront.addChild(this.tentacle5);
        this.headFront.addChild(this.tentacle6);
        this.RightLowerLeg.addChild(this.RightFoot);
        this.UpperBody.addChild(this.LeftUpperArm);
        this.headFront.addChild(this.tentacle7_1);
        this.headFront.addChild(this.tentacle4);
        this.headFront.addChild(this.tentacle7_3);
        this.shape15_1.addChild(this.shape16_1);
        this.UpperBody.addChild(this.headMain);
        this.LeftLowerLeg.addChild(this.LeftFoot);
        this.RightLowerLeg.addChild(this.RightHeel);
        this.headFront.addChild(this.tentacle2);
        this.headFront.addChild(this.tentacle7_2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.MidBody.render(f5);
        this.LowerBody.render(f5);
        this.UpperBody.render(f5);
        this.LeftUpperLeg.render(f5);
        this.RightUpperLeg.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
