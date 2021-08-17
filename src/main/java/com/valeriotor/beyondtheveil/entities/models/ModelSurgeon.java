package com.valeriotor.beyondtheveil.entities.models;

import java.util.EnumMap;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationTemplate.Transformation;
import com.valeriotor.beyondtheveil.entities.EntitySurgeon;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * surgeon - valeriotor
 * Created using Tabula 7.0.0
 */
public class ModelSurgeon extends ModelAnimated {
    public ModelRenderer LowerBody1;						// 0
    public ModelRenderer LowerBody2;						// 1
    public ModelRenderer RightFrontUpperLeg;				// 2
    public ModelRenderer RightMidUpperLeg;					// 3
    public ModelRenderer LowerBody;							// 4
    public ModelRenderer RightBackUpperLeg;					// 5
    public ModelRenderer LeftFrontUpperLeg;					// 6
    public ModelRenderer LeftMidUpperLeg;					// 7
    public ModelRenderer LeftBackUpperLeg;					// 8
    public ModelRenderer LowerBody3;						// 9
    public ModelRenderer RightFrontLowerLeg;				// 10
    public ModelRenderer RightMidLowerLeg;					// 11
    public ModelRenderer UpperBody;							// 12
    public ModelRenderer Neck;								// 13
    public ModelRenderer RightUpperArm;						// 14
    public ModelRenderer LeftUpperArm;						// 15
    public ModelRenderer MainHead;							// 16
    public ModelRenderer FrontHead1;						// 17
    public ModelRenderer BackHead1;							// 18
    public ModelRenderer Eye1;								// 19
    public ModelRenderer Eye2;								// 20
    public ModelRenderer Eye3;								// 21
    public ModelRenderer Eye4;								// 22
    public ModelRenderer Eye5;								// 23
    public ModelRenderer Eye6;								// 24
    public ModelRenderer Eye7;								// 25
    public ModelRenderer Eye10;								// 26
    public ModelRenderer Eye11;								// 27
    public ModelRenderer Eye12;								// 28
    public ModelRenderer Eye14;								// 29
    public ModelRenderer Eye15;								// 30
    public ModelRenderer Eye16;								// 31
    public ModelRenderer Eye17;								// 32
    public ModelRenderer FrontHead2;						// 33
    public ModelRenderer Eye8;								// 34
    public ModelRenderer Eye9;								// 35
    public ModelRenderer Eye13;								// 36
    public ModelRenderer Eye18;								// 37
    public ModelRenderer BackHead2;							// 38
    public ModelRenderer BackHead3;							// 39
    public ModelRenderer RightLowerArm;						// 40
    public ModelRenderer RightUpperTentacle1;				// 41
    public ModelRenderer RightUpperTentacle2;				// 42
    public ModelRenderer RightUpperTentacle3;				// 43
    public ModelRenderer RightLowerTentacle1;				// 44
    public ModelRenderer RightLowerTentacle2;				// 45
    public ModelRenderer RightLowerTentacle3;				// 46
    public ModelRenderer LeftLowerArm;						// 47
    public ModelRenderer LeftUpperTentacle1;				// 48
    public ModelRenderer LeftUpperTentacle2;				// 49
    public ModelRenderer LeftUpperTentacle3;				// 50
    public ModelRenderer LeftLowerTentacle1;				// 51
    public ModelRenderer LeftLowerTentacle2;				// 52
    public ModelRenderer LeftLowerTentacle3;				// 53
    public ModelRenderer RightBackLowerLeg;					// 54
    public ModelRenderer LeftFrontLowerLeg;					// 55
    public ModelRenderer LeftMidLowerLeg;					// 56
    public ModelRenderer LeftBackLowerLeg;					// 57

    public ModelSurgeon() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.UpperBody = new ModelRenderer(this, 56, 0);
        this.UpperBody.setRotationPoint(0.0F, -8.0F, -3.0F);
        this.UpperBody.addBox(-5.0F, 0.0F, 0.0F, 10, 12, 10, 0.0F);
        this.LeftUpperTentacle1 = new ModelRenderer(this, 0, 0);
        this.LeftUpperTentacle1.setRotationPoint(0.0F, 17.3F, 2.3F);
        this.LeftUpperTentacle1.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.Eye7 = new ModelRenderer(this, 117, 61);
        this.Eye7.setRotationPoint(5.0F, 5.0F, 7.0F);
        this.Eye7.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.Eye11 = new ModelRenderer(this, 117, 61);
        this.Eye11.setRotationPoint(-5.0F, 3.7F, 4.6F);
        this.Eye11.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.LeftBackLowerLeg = new ModelRenderer(this, 0, 25);
        this.LeftBackLowerLeg.setRotationPoint(0.0F, 9.1F, 0.1F);
        this.LeftBackLowerLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 13, 3, 0.0F);
        this.BackHead3 = new ModelRenderer(this, 56, 23);
        this.BackHead3.setRotationPoint(0.0F, 0.5F, 2.7F);
        this.BackHead3.addBox(-3.0F, 0.0F, 0.0F, 6, 6, 3, 0.0F);
        this.RightBackLowerLeg = new ModelRenderer(this, 0, 25);
        this.RightBackLowerLeg.setRotationPoint(0.0F, 9.1F, 0.1F);
        this.RightBackLowerLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 13, 3, 0.0F);
        this.BackHead1 = new ModelRenderer(this, 12, 22);
        this.BackHead1.setRotationPoint(0.0F, 0.5F, 9.3F);
        this.BackHead1.addBox(-4.5F, 0.0F, 0.0F, 9, 9, 3, 0.0F);
        this.Neck = new ModelRenderer(this, 33, 0);
        this.Neck.setRotationPoint(0.0F, -0.9F, 0.9F);
        this.Neck.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 6, 0.0F);
        this.LeftMidLowerLeg = new ModelRenderer(this, 40, 36);
        this.LeftMidLowerLeg.setRotationPoint(0.0F, 9.1F, 0.1F);
        this.LeftMidLowerLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 15, 3, 0.0F);
        this.LeftLowerTentacle3 = new ModelRenderer(this, 0, 0);
        this.LeftLowerTentacle3.setRotationPoint(0.0F, 9.3F, 0.0F);
        this.LeftLowerTentacle3.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.Eye1 = new ModelRenderer(this, 117, 61);
        this.Eye1.setRotationPoint(-5.0F, 3.0F, 2.0F);
        this.Eye1.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.RightUpperTentacle3 = new ModelRenderer(this, 0, 0);
        this.RightUpperTentacle3.setRotationPoint(0.0F, 17.0F, 2.0F);
        this.RightUpperTentacle3.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.Eye18 = new ModelRenderer(this, 117, 61);
        this.Eye18.setRotationPoint(-0.9F, 4.4F, -1.0F);
        this.Eye18.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.RightLowerTentacle1 = new ModelRenderer(this, 0, 0);
        this.RightLowerTentacle1.setRotationPoint(0.2F, 9.6F, 0.0F);
        this.RightLowerTentacle1.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.Eye14 = new ModelRenderer(this, 117, 61);
        this.Eye14.setRotationPoint(-2.0F, -1.0F, 3.0F);
        this.Eye14.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.RightLowerTentacle2 = new ModelRenderer(this, 0, 0);
        this.RightLowerTentacle2.setRotationPoint(-0.2F, 9.3F, -0.1F);
        this.RightLowerTentacle2.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.MainHead = new ModelRenderer(this, 0, 2);
        this.MainHead.setRotationPoint(0.0F, -10.0F, -2.0F);
        this.MainHead.addBox(-5.0F, 0.0F, 0.0F, 10, 10, 10, 0.0F);
        this.Eye5 = new ModelRenderer(this, 120, 59);
        this.Eye5.setRotationPoint(5.0F, 2.2F, 3.9F);
        this.Eye5.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.Eye17 = new ModelRenderer(this, 117, 61);
        this.Eye17.setRotationPoint(-0.7F, -1.0F, 5.9F);
        this.Eye17.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.Eye3 = new ModelRenderer(this, 117, 61);
        this.Eye3.setRotationPoint(-5.0F, 2.0F, 7.0F);
        this.Eye3.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.FrontHead2 = new ModelRenderer(this, 72, 33);
        this.FrontHead2.setRotationPoint(0.0F, 1.0F, -1.0F);
        this.FrontHead2.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 1, 0.0F);
        this.BackHead2 = new ModelRenderer(this, 36, 22);
        this.BackHead2.setRotationPoint(0.0F, 1.0F, 2.3F);
        this.BackHead2.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 3, 0.0F);
        this.LeftUpperTentacle2 = new ModelRenderer(this, 0, 0);
        this.LeftUpperTentacle2.setRotationPoint(0.0F, 17.3F, 1.7F);
        this.LeftUpperTentacle2.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.RightLowerTentacle3 = new ModelRenderer(this, 0, 0);
        this.RightLowerTentacle3.setRotationPoint(0.0F, 9.3F, 0.0F);
        this.RightLowerTentacle3.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.LeftLowerArm = new ModelRenderer(this, 116, 21);
        this.LeftLowerArm.setRotationPoint(0.0F, 13.6F, 3.1F);
        this.LeftLowerArm.addBox(-1.5F, 0.0F, 0.0F, 3, 18, 3, 0.0F);
        this.Eye13 = new ModelRenderer(this, 117, 61);
        this.Eye13.setRotationPoint(2.0F, 1.0F, -1.0F);
        this.Eye13.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.LowerBody3 = new ModelRenderer(this, 88, 41);
        this.LowerBody3.setRotationPoint(0.0F, 1.0F, 10.0F);
        this.LowerBody3.addBox(-3.0F, 0.0F, 0.0F, 6, 6, 9, 0.0F);
        this.RightFrontLowerLeg = new ModelRenderer(this, 40, 36);
        this.RightFrontLowerLeg.setRotationPoint(0.0F, 9.1F, 0.0F);
        this.RightFrontLowerLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 15, 3, 0.0F);
        this.LeftFrontUpperLeg = new ModelRenderer(this, 0, 41);
        this.LeftFrontUpperLeg.setRotationPoint(4.0F, 3.0F, 0.0F);
        this.LeftFrontUpperLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.LeftUpperArm = new ModelRenderer(this, 104, 21);
        this.LeftUpperArm.setRotationPoint(3.7F, 1.7F, 2.3F);
        this.LeftUpperArm.addBox(-1.5F, 0.0F, 0.0F, 3, 15, 3, 0.0F);
        this.FrontHead1 = new ModelRenderer(this, 52, 33);
        this.FrontHead1.setRotationPoint(0.0F, 0.5F, -1.0F);
        this.FrontHead1.addBox(-4.5F, 0.0F, 0.0F, 9, 9, 1, 0.0F);
        this.LowerBody2 = new ModelRenderer(this, 60, 44);
        this.LowerBody2.setRotationPoint(0.0F, 0.6F, 18.9F);
        this.LowerBody2.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 12, 0.0F);
        this.RightUpperTentacle1 = new ModelRenderer(this, 0, 0);
        this.RightUpperTentacle1.setRotationPoint(0.0F, 17.3F, 2.3F);
        this.RightUpperTentacle1.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.RightBackUpperLeg = new ModelRenderer(this, 0, 41);
        this.RightBackUpperLeg.setRotationPoint(-4.0F, 3.0F, 16.0F);
        this.RightBackUpperLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.RightFrontUpperLeg = new ModelRenderer(this, 0, 41);
        this.RightFrontUpperLeg.setRotationPoint(-4.0F, 3.0F, 0.0F);
        this.RightFrontUpperLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.Eye16 = new ModelRenderer(this, 117, 59);
        this.Eye16.setRotationPoint(1.4F, -1.0F, 1.3F);
        this.Eye16.addBox(-0.5F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
        this.Eye15 = new ModelRenderer(this, 117, 61);
        this.Eye15.setRotationPoint(2.0F, -1.0F, 6.0F);
        this.Eye15.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.LeftFrontLowerLeg = new ModelRenderer(this, 40, 36);
        this.LeftFrontLowerLeg.setRotationPoint(0.0F, 9.1F, 0.0F);
        this.LeftFrontLowerLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 15, 3, 0.0F);
        this.RightMidUpperLeg = new ModelRenderer(this, 0, 41);
        this.RightMidUpperLeg.setRotationPoint(-4.0F, 3.0F, 6.6F);
        this.RightMidUpperLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.RightLowerArm = new ModelRenderer(this, 116, 21);
        this.RightLowerArm.setRotationPoint(0.0F, 13.6F, 3.1F);
        this.RightLowerArm.addBox(-1.5F, 0.0F, 0.0F, 3, 18, 3, 0.0F);
        this.Eye10 = new ModelRenderer(this, 117, 59);
        this.Eye10.setRotationPoint(5.0F, 2.0F, 8.0F);
        this.Eye10.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.LeftMidUpperLeg = new ModelRenderer(this, 0, 41);
        this.LeftMidUpperLeg.setRotationPoint(4.0F, 3.0F, 6.6F);
        this.LeftMidUpperLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.LowerBody1 = new ModelRenderer(this, 0, 34);
        this.LowerBody1.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.LowerBody1.addBox(-5.0F, 0.0F, 0.0F, 10, 10, 20, 0.0F);
        this.LeftUpperTentacle3 = new ModelRenderer(this, 0, 0);
        this.LeftUpperTentacle3.setRotationPoint(0.0F, 17.0F, 2.0F);
        this.LeftUpperTentacle3.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.RightMidLowerLeg = new ModelRenderer(this, 40, 36);
        this.RightMidLowerLeg.setRotationPoint(0.0F, 9.1F, 0.1F);
        this.RightMidLowerLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 15, 3, 0.0F);
        this.Eye4 = new ModelRenderer(this, 117, 61);
        this.Eye4.setRotationPoint(5.0F, 5.0F, 1.0F);
        this.Eye4.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.Eye6 = new ModelRenderer(this, 117, 61);
        this.Eye6.setRotationPoint(5.0F, 7.0F, 5.0F);
        this.Eye6.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.LeftBackUpperLeg = new ModelRenderer(this, 0, 41);
        this.LeftBackUpperLeg.setRotationPoint(4.0F, 3.0F, 16.0F);
        this.LeftBackUpperLeg.addBox(-1.5F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.Eye9 = new ModelRenderer(this, 117, 61);
        this.Eye9.setRotationPoint(2.9F, 5.0F, -1.0F);
        this.Eye9.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.LeftLowerTentacle2 = new ModelRenderer(this, 0, 0);
        this.LeftLowerTentacle2.setRotationPoint(-0.2F, 9.3F, -0.1F);
        this.LeftLowerTentacle2.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.RightUpperArm = new ModelRenderer(this, 104, 21);
        this.RightUpperArm.setRotationPoint(-3.7F, 1.7F, 2.3F);
        this.RightUpperArm.addBox(-1.5F, 0.0F, 0.0F, 3, 15, 3, 0.0F);
        this.Eye2 = new ModelRenderer(this, 122, 59);
        this.Eye2.setRotationPoint(-5.0F, 6.4F, 5.0F);
        this.Eye2.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.RightUpperTentacle2 = new ModelRenderer(this, 0, 0);
        this.RightUpperTentacle2.setRotationPoint(0.0F, 17.3F, 1.7F);
        this.RightUpperTentacle2.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.LowerBody = new ModelRenderer(this, 96, 0);
        this.LowerBody.setRotationPoint(0.0F, -7.0F, -1.0F);
        this.LowerBody.addBox(-4.5F, 0.0F, 0.0F, 9, 14, 7, 0.0F);
        this.Eye12 = new ModelRenderer(this, 117, 61);
        this.Eye12.setRotationPoint(-5.0F, 6.6F, 2.0F);
        this.Eye12.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.LeftLowerTentacle1 = new ModelRenderer(this, 0, 0);
        this.LeftLowerTentacle1.setRotationPoint(0.2F, 9.6F, 0.0F);
        this.LeftLowerTentacle1.addBox(-0.5F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.Eye8 = new ModelRenderer(this, 122, 61);
        this.Eye8.setRotationPoint(-2.0F, 1.0F, -0.9F);
        this.Eye8.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
        this.LowerBody.addChild(this.UpperBody);
        this.LeftLowerArm.addChild(this.LeftUpperTentacle1);
        this.MainHead.addChild(this.Eye7);
        this.MainHead.addChild(this.Eye11);
        this.LeftBackUpperLeg.addChild(this.LeftBackLowerLeg);
        this.BackHead2.addChild(this.BackHead3);
        this.RightBackUpperLeg.addChild(this.RightBackLowerLeg);
        this.MainHead.addChild(this.BackHead1);
        this.UpperBody.addChild(this.Neck);
        this.LeftMidUpperLeg.addChild(this.LeftMidLowerLeg);
        this.LeftUpperTentacle3.addChild(this.LeftLowerTentacle3);
        this.MainHead.addChild(this.Eye1);
        this.RightLowerArm.addChild(this.RightUpperTentacle3);
        this.FrontHead2.addChild(this.Eye18);
        this.RightUpperTentacle1.addChild(this.RightLowerTentacle1);
        this.MainHead.addChild(this.Eye14);
        this.RightUpperTentacle2.addChild(this.RightLowerTentacle2);
        this.Neck.addChild(this.MainHead);
        this.MainHead.addChild(this.Eye5);
        this.MainHead.addChild(this.Eye17);
        this.MainHead.addChild(this.Eye3);
        this.FrontHead1.addChild(this.FrontHead2);
        this.BackHead1.addChild(this.BackHead2);
        this.LeftLowerArm.addChild(this.LeftUpperTentacle2);
        this.RightUpperTentacle3.addChild(this.RightLowerTentacle3);
        this.LeftUpperArm.addChild(this.LeftLowerArm);
        this.FrontHead2.addChild(this.Eye13);
        this.LowerBody2.addChild(this.LowerBody3);
        this.RightFrontUpperLeg.addChild(this.RightFrontLowerLeg);
        this.LowerBody1.addChild(this.LeftFrontUpperLeg);
        this.UpperBody.addChild(this.LeftUpperArm);
        this.MainHead.addChild(this.FrontHead1);
        this.LowerBody1.addChild(this.LowerBody2);
        this.RightLowerArm.addChild(this.RightUpperTentacle1);
        this.LowerBody1.addChild(this.RightBackUpperLeg);
        this.LowerBody1.addChild(this.RightFrontUpperLeg);
        this.MainHead.addChild(this.Eye16);
        this.MainHead.addChild(this.Eye15);
        this.LeftFrontUpperLeg.addChild(this.LeftFrontLowerLeg);
        this.LowerBody1.addChild(this.RightMidUpperLeg);
        this.RightUpperArm.addChild(this.RightLowerArm);
        this.MainHead.addChild(this.Eye10);
        this.LowerBody1.addChild(this.LeftMidUpperLeg);
        this.LeftLowerArm.addChild(this.LeftUpperTentacle3);
        this.RightMidUpperLeg.addChild(this.RightMidLowerLeg);
        this.MainHead.addChild(this.Eye4);
        this.MainHead.addChild(this.Eye6);
        this.LowerBody1.addChild(this.LeftBackUpperLeg);
        this.FrontHead2.addChild(this.Eye9);
        this.LeftUpperTentacle2.addChild(this.LeftLowerTentacle2);
        this.UpperBody.addChild(this.RightUpperArm);
        this.MainHead.addChild(this.Eye2);
        this.RightLowerArm.addChild(this.RightUpperTentacle2);
        this.LowerBody1.addChild(this.LowerBody);
        this.MainHead.addChild(this.Eye12);
        this.LeftUpperTentacle1.addChild(this.LeftLowerTentacle1);
        this.FrontHead2.addChild(this.Eye8);
        bodyParts.add(LowerBody1);
        bodyParts.add(LowerBody2);
        bodyParts.add(RightFrontUpperLeg);
        bodyParts.add(RightMidUpperLeg);
        bodyParts.add(LowerBody);
        bodyParts.add(RightBackUpperLeg);
        bodyParts.add(LeftFrontUpperLeg);
        bodyParts.add(LeftMidUpperLeg);
        bodyParts.add(LeftBackUpperLeg);
        bodyParts.add(LowerBody3);
        bodyParts.add(RightFrontLowerLeg);
        bodyParts.add(RightMidLowerLeg);
        bodyParts.add(UpperBody);
        bodyParts.add(Neck);
        bodyParts.add(RightUpperArm);
        bodyParts.add(LeftUpperArm);
        bodyParts.add(MainHead);
        bodyParts.add(FrontHead1);
        bodyParts.add(BackHead1);
        bodyParts.add(Eye1);
        bodyParts.add(Eye2);
        bodyParts.add(Eye3);
        bodyParts.add(Eye4);
        bodyParts.add(Eye5);
        bodyParts.add(Eye6);
        bodyParts.add(Eye7);
        bodyParts.add(Eye10);
        bodyParts.add(Eye11);
        bodyParts.add(Eye12);
        bodyParts.add(Eye14);
        bodyParts.add(Eye15);
        bodyParts.add(Eye16);
        bodyParts.add(Eye17);
        bodyParts.add(FrontHead2);
        bodyParts.add(Eye8);
        bodyParts.add(Eye9);
        bodyParts.add(Eye13);
        bodyParts.add(Eye18);
        bodyParts.add(BackHead2);
        bodyParts.add(BackHead3);
        bodyParts.add(RightLowerArm);
        bodyParts.add(RightUpperTentacle1);
        bodyParts.add(RightUpperTentacle2);
        bodyParts.add(RightUpperTentacle3);
        bodyParts.add(RightLowerTentacle1);
        bodyParts.add(RightLowerTentacle2);
        bodyParts.add(RightLowerTentacle3);
        bodyParts.add(LeftLowerArm);
        bodyParts.add(LeftUpperTentacle1);
        bodyParts.add(LeftUpperTentacle2);
        bodyParts.add(LeftUpperTentacle3);
        bodyParts.add(LeftLowerTentacle1);
        bodyParts.add(LeftLowerTentacle2);
        bodyParts.add(LeftLowerTentacle3);
        bodyParts.add(RightBackLowerLeg);
        bodyParts.add(LeftFrontLowerLeg);
        bodyParts.add(LeftMidLowerLeg);
        bodyParts.add(LeftBackLowerLeg);
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
        this.setRotateAngle(UpperBody, 0.18203784098300857F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(LeftUpperTentacle1, 1.4570008595648662F, 0.5462880558742251F, -0.091106186954104F, addToDefault);
        this.setRotateAngle(LeftBackLowerLeg, 0.0F, 0.0F, 0.7285004297824331F, addToDefault);
        this.setRotateAngle(BackHead3, -0.27314402793711257F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(RightBackLowerLeg, 0.0F, 0.0F, -0.7285004297824331F, addToDefault);
        this.setRotateAngle(BackHead1, -0.091106186954104F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(Neck, 0.136659280431156F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(LeftMidLowerLeg, 0.0F, 0.0F, 0.7285004297824331F, addToDefault);
        this.setRotateAngle(LeftLowerTentacle3, -0.22759093446006054F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(RightUpperTentacle3, 2.367539130330308F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(RightLowerTentacle1, 0.8651597102135892F, -0.22759093446006054F, 0.0F, addToDefault);
        this.setRotateAngle(RightLowerTentacle2, 0.4553564018453205F, 0.0F, -0.4553564018453205F, addToDefault);
        this.setRotateAngle(BackHead2, -0.18203784098300857F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(LeftUpperTentacle2, 1.7756979809790308F, -0.6373942428283291F, 0.0F, addToDefault);
        this.setRotateAngle(RightLowerTentacle3, -0.22759093446006054F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(LeftLowerArm, -2.4586453172844123F, 0.0F, 0.5462880558742251F, addToDefault);
        this.setRotateAngle(LowerBody3, -0.27314402793711257F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(RightFrontLowerLeg, 0.091106186954104F, 0.0F, -0.7285004297824331F, addToDefault);
        this.setRotateAngle(LeftFrontUpperLeg, 0.0F, 0.0F, -0.767944870877505F, addToDefault);
        this.setRotateAngle(LeftUpperArm, 0.27314402793711257F, 0.0F, -0.5462880558742251F, addToDefault);
        this.setRotateAngle(LowerBody2, -0.36425021489121656F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(RightUpperTentacle1, 1.4570008595648662F, 0.5462880558742251F, 0.091106186954104F, addToDefault);
        this.setRotateAngle(RightBackUpperLeg, 0.22759093446006054F, 0.0F, 0.7740535232594852F, addToDefault);
        this.setRotateAngle(RightFrontUpperLeg, 0.0F, 0.0F, 0.7740535232594852F, addToDefault);
        this.setRotateAngle(LeftFrontLowerLeg, 0.10471975511965977F, 0.0F, 0.7285004297824331F, addToDefault);
        this.setRotateAngle(RightMidUpperLeg, 0.22759093446006054F, 0.0F, 0.7740535232594852F, addToDefault);
        this.setRotateAngle(RightLowerArm, -2.4586453172844123F, 0.0F, -0.5462880558742251F, addToDefault);
        this.setRotateAngle(LeftMidUpperLeg, 0.22689280275926282F, 0.0F, -0.7740535232594852F, addToDefault);
        this.setRotateAngle(LowerBody1, -0.136659280431156F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(LeftUpperTentacle3, 2.367539130330308F, 0.0F, 0.0F, addToDefault);
        this.setRotateAngle(RightMidLowerLeg, 0.0F, 0.0F, -0.7285004297824331F, addToDefault);
        this.setRotateAngle(LeftBackUpperLeg, 0.22759093446006054F, 0.0F, -0.7740535232594852F, addToDefault);
        this.setRotateAngle(LeftLowerTentacle2, 0.4553564018453205F, 0.0F, -0.4553564018453205F, addToDefault);
        this.setRotateAngle(RightUpperArm, 0.27314402793711257F, 0.0F, 0.5462880558742251F, addToDefault);
        this.setRotateAngle(RightUpperTentacle2, 1.7756979809790308F, -0.6373942428283291F, 0.0F, addToDefault);
        this.setRotateAngle(LeftLowerTentacle1, 0.8651597102135892F, -0.22759093446006054F, 0.0F, addToDefault);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.LowerBody1.render(f5);
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
    	EntitySurgeon e = (EntitySurgeon) entitylivingbaseIn;
    	this.LeftFrontUpperLeg.rotateAngleX = 0.4F*MathHelper.cos(limbSwing * 0.662F) * limbSwingAmount;
    	this.RightFrontUpperLeg.rotateAngleX = 0.4F*MathHelper.cos(limbSwing * 0.662F + (float)Math.PI) * limbSwingAmount;
    	this.RightMidUpperLeg.rotateAngleX = 0.22759093446006054F + 0.4F*MathHelper.cos(limbSwing * 0.662F) * limbSwingAmount;
    	this.LeftMidUpperLeg.rotateAngleX = 0.22689280275926282F + 0.4F*MathHelper.cos(limbSwing * 0.662F + (float)Math.PI) * limbSwingAmount;
    	this.LeftBackUpperLeg.rotateAngleX = 0.22759093446006054F + 0.4F*MathHelper.cos(limbSwing * 0.662F) * limbSwingAmount;
    	this.RightBackUpperLeg.rotateAngleX = 0.22759093446006054F + 0.4F*MathHelper.cos(limbSwing * 0.662F + (float)Math.PI) * limbSwingAmount;
    	float offset = MathHelper.sin((e.ticksExisted+partialTickTime)%40 / 6.366F)/20;
    	this.LowerBody.offsetY = offset/7;
    	this.LowerBody2.rotateAngleX = -0.36425021489121656F + offset;
    	this.LowerBody3.rotateAngleY = offset;
    	this.LeftUpperTentacle1.rotateAngleX = 1.4570008595648662F + offset*1.5F;
    	this.Neck.rotateAngleZ = offset/7;
    	this.RightUpperTentacle2.rotateAngleX = 1.7756979809790308F - offset*1.5F;
    	this.Eye1.offsetX = offset/7;
    	this.Eye4.offsetX = offset/7;
    	this.Eye6.offsetX = offset/7;
    	this.Eye8.offsetZ = offset/7;
    	this.Eye10.offsetX = offset/7;
    	this.Eye12.offsetX = offset/7;
    	this.Eye13.offsetZ = offset/7;
    	this.Eye15.offsetY = 0.0125F-offset/7;
    	this.Eye17.offsetY = 0.0125F+offset/7;
    	this.Eye18.offsetZ = -offset/7;
    	offset = MathHelper.sin((e.ticksExisted+partialTickTime)%50 / 7.95F)/16;
    	this.UpperBody.rotateAngleX =  0.18203784098300857F + offset/3;
    	this.Eye2.offsetX = offset/7;
    	this.Eye1.offsetY = offset/7;
    	this.Eye3.offsetX = -offset/7;
    	this.Eye5.offsetX = -offset/7;
    	this.Eye6.offsetY = offset/7;
    	this.Eye7.offsetX = -offset/7;
    	this.Eye9.offsetZ = offset/7;
    	this.Eye11.offsetX = offset/7;
    	this.Eye16.offsetY = 0.0125F+offset/7;
    	this.Eye14.offsetY = 0.0125F+offset/7;
    	this.LowerBody2.rotateAngleY = offset;
    	this.LeftUpperTentacle2.rotateAngleX = 1.7756979809790308F + offset*1.5F;
    	this.RightUpperTentacle1.rotateAngleX = 1.4570008595648662F + offset*1.5F;
    	Animation anim = e.getAttackAnimation();
    	if(anim != null) {
    		anim.applyTransformations(bodyParts, partialTickTime);
    	} else {
    		anim = e.getSurgeryAnimation();
    		if(anim != null) {
        		anim.applyTransformations(bodyParts, partialTickTime);
        	}
    	}
    	super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
    }
}
