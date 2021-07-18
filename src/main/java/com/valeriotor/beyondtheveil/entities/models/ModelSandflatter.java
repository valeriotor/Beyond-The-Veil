package com.valeriotor.beyondtheveil.entities.models;//Made with Blockbench
//Paste this code into your mod.

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.entities.ictya.EntitySandflatter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelSandflatter extends ModelAnimated {
	private final ModelRenderer dorso;						//0
	private final ModelRenderer dorsoMeat;					//1
	private final ModelRenderer rightArm1;					//2
	private final ModelRenderer rightConn1;					//3
	private final ModelRenderer rightArm11;					//4
	private final ModelRenderer rightConn11;				//5
	private final ModelRenderer rightClaw1;					//6
	private final ModelRenderer rightClaw11;				//7
	private final ModelRenderer rightClaw12;				//8
	private final ModelRenderer rightArm2;					//9
	private final ModelRenderer rightConn2;					//10
	private final ModelRenderer rightArm21;					//11
	private final ModelRenderer rightConn21;				//12
	private final ModelRenderer rightClaw2;					//13
	private final ModelRenderer rightClaw21;				//14
	private final ModelRenderer rightClaw22;				//15
	private final ModelRenderer rightArm3;					//16
	private final ModelRenderer rightConn3;					//17
	private final ModelRenderer rightArm31;					//18
	private final ModelRenderer rightConn31;				//19
	private final ModelRenderer rightClaw3;					//20
	private final ModelRenderer rightClaw31;				//21
	private final ModelRenderer rightClaw32;				//22
	private final ModelRenderer rightArm4;					//23
	private final ModelRenderer rightConn4;					//24
	private final ModelRenderer rightArm41;					//25
	private final ModelRenderer rightConn41;				//26
	private final ModelRenderer rightClaw4;					//27
	private final ModelRenderer rightClaw41;				//28
	private final ModelRenderer rightClaw42;				//29
	private final ModelRenderer leftArm1;					//30
	private final ModelRenderer leftConn1;					//31
	private final ModelRenderer leftArm11;					//32
	private final ModelRenderer leftConn11;					//33
	private final ModelRenderer leftClaw1;					//34
	private final ModelRenderer leftClaw11;					//35
	private final ModelRenderer leftClaw12;					//36
	private final ModelRenderer leftArm2;					//37
	private final ModelRenderer leftConn2;					//38
	private final ModelRenderer leftArm21;					//39
	private final ModelRenderer leftConn21;					//40
	private final ModelRenderer leftClaw2;					//41
	private final ModelRenderer leftClaw21;					//42
	private final ModelRenderer leftClaw22;					//43
	private final ModelRenderer leftArm3;					//44
	private final ModelRenderer leftConn3;					//45
	private final ModelRenderer leftArm31;					//46
	private final ModelRenderer leftConn31;					//47
	private final ModelRenderer leftClaw3;					//48
	private final ModelRenderer leftClaw31;					//49
	private final ModelRenderer leftClaw32;					//50
	private final ModelRenderer leftArm4;					//51
	private final ModelRenderer leftConn4;					//52
	private final ModelRenderer leftArm41;					//53
	private final ModelRenderer leftConn41;					//54
	private final ModelRenderer leftClaw4;					//55
	private final ModelRenderer leftClaw41;					//56
	private final ModelRenderer leftClaw42;					//57
	private final ModelRenderer tail;						//58
	private final ModelRenderer tail2;						//59
	private final ModelRenderer tail3;						//60
	private final ModelRenderer head;						//61
	private final ModelRenderer rightJaw;					//62
	private final ModelRenderer rightJaw1;					//63
	private final ModelRenderer leftJaw;					//64
	private final ModelRenderer rightJaw2;					//65
	private final ModelRenderer leftPincerArm;				//66
	private final ModelRenderer leftPincerForeArm;			//67
	private final ModelRenderer leftPincer;					//68
	private final ModelRenderer leftPincerUp;				//69
	private final ModelRenderer leftPincerLeft;				//70
	private final ModelRenderer leftPincerRight;			//71
	private final ModelRenderer rightPincerArm;				//72
	private final ModelRenderer rightPincerForeArm;			//73
	private final ModelRenderer rightPincer;				//74
	private final ModelRenderer rightPincerUp;				//75
	private final ModelRenderer RightPincerLeft;			//76
	private final ModelRenderer rightPincerRight;			//77
	private final ModelRenderer bait;						//78
	private final ModelRenderer bait2;						//79
	private final ModelRenderer bait3;						//80

	public ModelSandflatter() {
		textureWidth = 256;
		textureHeight = 256;

		dorso = new ModelRenderer(this);
		dorso.setRotationPoint(0.0F, 7.0F, 0.0F);
		dorso.cubeList.add(new ModelBox(dorso, 0, 207, -24.0F, -1.0F, -24.0F, 48, 1, 48, 0.0F, false));

		dorsoMeat = new ModelRenderer(this);
		dorsoMeat.setRotationPoint(0.0F, 1.0F, 0.0F);
		dorso.addChild(dorsoMeat);
		dorsoMeat.cubeList.add(new ModelBox(dorsoMeat, 0, 0, -20.0F, -1.0F, -20.0F, 40, 6, 40, 0.0F, false));
		dorsoMeat.cubeList.add(new ModelBox(dorsoMeat, 0, 125, -17.0F, 5.0F, -17.0F, 34, 6, 34, 0.0F, false));
		dorsoMeat.cubeList.add(new ModelBox(dorsoMeat, 168, 82, -14.0F, 1.0F, -30.0F, 28, 11, 16, 0.0F, false));

		rightArm1 = new ModelRenderer(this);
		rightArm1.setRotationPoint(-19.0F, 1.5F, -17.5F);
		dorsoMeat.addChild(rightArm1);
		rightArm1.cubeList.add(new ModelBox(rightArm1, 0, 0, -21.0F, -1.5F, -1.5F, 23, 3, 3, 0.0F, false));

		rightConn1 = new ModelRenderer(this);
		rightConn1.setRotationPoint(-20.5F, 0.0F, 0.0F);
		rightArm1.addChild(rightConn1);
		rightConn1.cubeList.add(new ModelBox(rightConn1, 246, 225, -2.5F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		rightArm11 = new ModelRenderer(this);
		rightArm11.setRotationPoint(-1.0F, 0.0F, 0.0F);
		rightConn1.addChild(rightArm11);
		rightArm11.cubeList.add(new ModelBox(rightArm11, 0, 0, -10.5F, -1.5F, -1.5F, 10, 3, 3, 0.0F, false));

		rightConn11 = new ModelRenderer(this);
		rightConn11.setRotationPoint(-10.0F, 0.0F, 0.0F);
		rightArm11.addChild(rightConn11);
		rightConn11.cubeList.add(new ModelBox(rightConn11, 246, 222, -2.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		rightClaw1 = new ModelRenderer(this);
		rightClaw1.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightConn11.addChild(rightClaw1);
		rightClaw1.cubeList.add(new ModelBox(rightClaw1, 120, 228, -4.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F, false));

		rightClaw11 = new ModelRenderer(this);
		rightClaw11.setRotationPoint(-5.0F, 0.5F, 0.0F);
		rightClaw1.addChild(rightClaw11);
		rightClaw11.cubeList.add(new ModelBox(rightClaw11, 120, 228, -1.0F, -1.75F, -1.0F, 3, 2, 2, 0.0F, false));

		rightClaw12 = new ModelRenderer(this);
		rightClaw12.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightClaw11.addChild(rightClaw12);
		rightClaw12.cubeList.add(new ModelBox(rightClaw12, 120, 228, -7.75F, -1.25F, -0.5F, 7, 1, 1, 0.0F, false));

		rightArm2 = new ModelRenderer(this);
		rightArm2.setRotationPoint(-19.0F, 1.5F, -8.5F);
		dorsoMeat.addChild(rightArm2);
		rightArm2.cubeList.add(new ModelBox(rightArm2, 0, 0, -21.0F, -1.5F, -1.5F, 23, 3, 3, 0.0F, false));

		rightConn2 = new ModelRenderer(this);
		rightConn2.setRotationPoint(-20.5F, 0.0F, 0.0F);
		rightArm2.addChild(rightConn2);
		rightConn2.cubeList.add(new ModelBox(rightConn2, 246, 224, -2.5F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		rightArm21 = new ModelRenderer(this);
		rightArm21.setRotationPoint(-1.0F, 0.0F, 0.0F);
		rightConn2.addChild(rightArm21);
		rightArm21.cubeList.add(new ModelBox(rightArm21, 0, 0, -10.5F, -1.5F, -1.5F, 10, 3, 3, 0.0F, false));

		rightConn21 = new ModelRenderer(this);
		rightConn21.setRotationPoint(-10.0F, 0.0F, 0.0F);
		rightArm21.addChild(rightConn21);
		rightConn21.cubeList.add(new ModelBox(rightConn21, 245, 221, -2.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		rightClaw2 = new ModelRenderer(this);
		rightClaw2.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightConn21.addChild(rightClaw2);
		rightClaw2.cubeList.add(new ModelBox(rightClaw2, 125, 239, -4.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F, false));

		rightClaw21 = new ModelRenderer(this);
		rightClaw21.setRotationPoint(-5.0F, 0.5F, 0.0F);
		rightClaw2.addChild(rightClaw21);
		rightClaw21.cubeList.add(new ModelBox(rightClaw21, 125, 239, -1.0F, -1.75F, -1.0F, 3, 2, 2, 0.0F, false));

		rightClaw22 = new ModelRenderer(this);
		rightClaw22.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightClaw21.addChild(rightClaw22);
		rightClaw22.cubeList.add(new ModelBox(rightClaw22, 125, 239, -7.75F, -1.25F, -0.5F, 7, 1, 1, 0.0F, false));

		rightArm3 = new ModelRenderer(this);
		rightArm3.setRotationPoint(-19.0F, 1.5F, 4.5F);
		dorsoMeat.addChild(rightArm3);
		rightArm3.cubeList.add(new ModelBox(rightArm3, 0, 0, -21.0F, -1.5F, -1.5F, 23, 3, 3, 0.0F, false));

		rightConn3 = new ModelRenderer(this);
		rightConn3.setRotationPoint(-20.5F, 0.0F, 0.0F);
		rightArm3.addChild(rightConn3);
		rightConn3.cubeList.add(new ModelBox(rightConn3, 241, 224, -2.5F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		rightArm31 = new ModelRenderer(this);
		rightArm31.setRotationPoint(-1.0F, 0.0F, 0.0F);
		rightConn3.addChild(rightArm31);
		rightArm31.cubeList.add(new ModelBox(rightArm31, 0, 0, -10.5F, -1.5F, -1.5F, 10, 3, 3, 0.0F, false));

		rightConn31 = new ModelRenderer(this);
		rightConn31.setRotationPoint(-10.0F, 0.0F, 0.0F);
		rightArm31.addChild(rightConn31);
		rightConn31.cubeList.add(new ModelBox(rightConn31, 246, 226, -2.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		rightClaw3 = new ModelRenderer(this);
		rightClaw3.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightConn31.addChild(rightClaw3);
		rightClaw3.cubeList.add(new ModelBox(rightClaw3, 125, 228, -4.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F, false));

		rightClaw31 = new ModelRenderer(this);
		rightClaw31.setRotationPoint(-5.0F, 0.5F, 0.0F);
		rightClaw3.addChild(rightClaw31);
		rightClaw31.cubeList.add(new ModelBox(rightClaw31, 125, 228, -1.0F, -1.75F, -1.0F, 3, 2, 2, 0.0F, false));

		rightClaw32 = new ModelRenderer(this);
		rightClaw32.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightClaw31.addChild(rightClaw32);
		rightClaw32.cubeList.add(new ModelBox(rightClaw32, 125, 228, -7.75F, -1.25F, -0.5F, 7, 1, 1, 0.0F, false));

		rightArm4 = new ModelRenderer(this);
		rightArm4.setRotationPoint(-19.0F, 1.5F, 17.5F);
		dorsoMeat.addChild(rightArm4);
		rightArm4.cubeList.add(new ModelBox(rightArm4, 0, 0, -21.0F, -1.5F, -1.5F, 23, 3, 3, 0.0F, false));

		rightConn4 = new ModelRenderer(this);
		rightConn4.setRotationPoint(-20.5F, 0.0F, 0.0F);
		rightArm4.addChild(rightConn4);
		rightConn4.cubeList.add(new ModelBox(rightConn4, 246, 221, -2.5F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		rightArm41 = new ModelRenderer(this);
		rightArm41.setRotationPoint(-1.0F, 0.0F, 0.0F);
		rightConn4.addChild(rightArm41);
		rightArm41.cubeList.add(new ModelBox(rightArm41, 0, 0, -10.5F, -1.5F, -1.5F, 10, 3, 3, 0.0F, false));

		rightConn41 = new ModelRenderer(this);
		rightConn41.setRotationPoint(-10.0F, 0.0F, 0.0F);
		rightArm41.addChild(rightConn41);
		rightConn41.cubeList.add(new ModelBox(rightConn41, 246, 222, -2.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		rightClaw4 = new ModelRenderer(this);
		rightClaw4.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightConn41.addChild(rightClaw4);
		rightClaw4.cubeList.add(new ModelBox(rightClaw4, 128, 224, -4.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F, false));

		rightClaw41 = new ModelRenderer(this);
		rightClaw41.setRotationPoint(-5.0F, 0.5F, 0.0F);
		rightClaw4.addChild(rightClaw41);
		rightClaw41.cubeList.add(new ModelBox(rightClaw41, 128, 224, -1.0F, -1.75F, -1.0F, 3, 2, 2, 0.0F, false));

		rightClaw42 = new ModelRenderer(this);
		rightClaw42.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightClaw41.addChild(rightClaw42);
		rightClaw42.cubeList.add(new ModelBox(rightClaw42, 122, 224, -7.75F, -1.25F, -0.5F, 7, 1, 1, 0.0F, false));

		leftArm1 = new ModelRenderer(this);
		leftArm1.setRotationPoint(17.0F, 1.5F, -16.5F);
		dorsoMeat.addChild(leftArm1);
		leftArm1.cubeList.add(new ModelBox(leftArm1, 0, 0, -21.0F, -1.5F, -2.5F, 23, 3, 3, 0.0F, false));

		leftConn1 = new ModelRenderer(this);
		leftConn1.setRotationPoint(-20.5F, 0.0F, 0.0F);
		leftArm1.addChild(leftConn1);
		leftConn1.cubeList.add(new ModelBox(leftConn1, 246, 225, -2.5F, -1.0F, -2.0F, 3, 2, 2, 0.0F, false));

		leftArm11 = new ModelRenderer(this);
		leftArm11.setRotationPoint(-1.0F, 0.0F, 0.0F);
		leftConn1.addChild(leftArm11);
		leftArm11.cubeList.add(new ModelBox(leftArm11, 0, 0, -10.5F, -1.5F, -2.5F, 10, 3, 3, 0.0F, false));

		leftConn11 = new ModelRenderer(this);
		leftConn11.setRotationPoint(-10.0F, 0.0F, 0.0F);
		leftArm11.addChild(leftConn11);
		leftConn11.cubeList.add(new ModelBox(leftConn11, 231, 222, -2.0F, -1.0F, -2.0F, 3, 2, 2, 0.0F, false));

		leftClaw1 = new ModelRenderer(this);
		leftClaw1.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftConn11.addChild(leftClaw1);
		leftClaw1.cubeList.add(new ModelBox(leftClaw1, 118, 225, -4.0F, -1.5F, -2.5F, 2, 3, 3, 0.0F, false));

		leftClaw11 = new ModelRenderer(this);
		leftClaw11.setRotationPoint(-5.0F, 0.5F, 0.0F);
		leftClaw1.addChild(leftClaw11);
		leftClaw11.cubeList.add(new ModelBox(leftClaw11, 118, 225, -1.0F, -1.75F, -2.0F, 3, 2, 2, 0.0F, false));

		leftClaw12 = new ModelRenderer(this);
		leftClaw12.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftClaw11.addChild(leftClaw12);
		leftClaw12.cubeList.add(new ModelBox(leftClaw12, 118, 225, -7.75F, -1.25F, -1.5F, 7, 1, 1, 0.0F, false));

		leftArm2 = new ModelRenderer(this);
		leftArm2.setRotationPoint(17.0F, 1.5F, -8.5F);
		dorsoMeat.addChild(leftArm2);
		leftArm2.cubeList.add(new ModelBox(leftArm2, 0, 0, -21.0F, -1.5F, -1.5F, 23, 3, 3, 0.0F, false));

		leftConn2 = new ModelRenderer(this);
		leftConn2.setRotationPoint(-20.5F, 0.0F, 0.0F);
		leftArm2.addChild(leftConn2);
		leftConn2.cubeList.add(new ModelBox(leftConn2, 246, 226, -2.5F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		leftArm21 = new ModelRenderer(this);
		leftArm21.setRotationPoint(-1.0F, 0.0F, 0.0F);
		leftConn2.addChild(leftArm21);
		leftArm21.cubeList.add(new ModelBox(leftArm21, 0, 0, -10.5F, -1.5F, -1.5F, 10, 3, 3, 0.0F, false));

		leftConn21 = new ModelRenderer(this);
		leftConn21.setRotationPoint(-10.0F, 0.0F, 0.0F);
		leftArm21.addChild(leftConn21);
		leftConn21.cubeList.add(new ModelBox(leftConn21, 243, 225, -2.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		leftClaw2 = new ModelRenderer(this);
		leftClaw2.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftConn21.addChild(leftClaw2);
		leftClaw2.cubeList.add(new ModelBox(leftClaw2, 120, 224, -4.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F, false));

		leftClaw21 = new ModelRenderer(this);
		leftClaw21.setRotationPoint(-5.0F, 0.5F, 0.0F);
		leftClaw2.addChild(leftClaw21);
		leftClaw21.cubeList.add(new ModelBox(leftClaw21, 120, 224, -1.0F, -1.75F, -1.0F, 3, 2, 2, 0.0F, false));

		leftClaw22 = new ModelRenderer(this);
		leftClaw22.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftClaw21.addChild(leftClaw22);
		leftClaw22.cubeList.add(new ModelBox(leftClaw22, 120, 224, -7.75F, -1.25F, -0.5F, 7, 1, 1, 0.0F, false));

		leftArm3 = new ModelRenderer(this);
		leftArm3.setRotationPoint(17.0F, 1.5F, 4.5F);
		dorsoMeat.addChild(leftArm3);
		leftArm3.cubeList.add(new ModelBox(leftArm3, 0, 0, -21.0F, -1.5F, -1.5F, 23, 3, 3, 0.0F, false));

		leftConn3 = new ModelRenderer(this);
		leftConn3.setRotationPoint(-20.5F, 0.0F, 0.0F);
		leftArm3.addChild(leftConn3);
		leftConn3.cubeList.add(new ModelBox(leftConn3, 246, 226, -2.5F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		leftArm31 = new ModelRenderer(this);
		leftArm31.setRotationPoint(-1.0F, 0.0F, 0.0F);
		leftConn3.addChild(leftArm31);
		leftArm31.cubeList.add(new ModelBox(leftArm31, 0, 0, -10.5F, -1.5F, -1.5F, 10, 3, 3, 0.0F, false));

		leftConn31 = new ModelRenderer(this);
		leftConn31.setRotationPoint(-10.0F, 0.0F, 0.0F);
		leftArm31.addChild(leftConn31);
		leftConn31.cubeList.add(new ModelBox(leftConn31, 237, 224, -2.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		leftClaw3 = new ModelRenderer(this);
		leftClaw3.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftConn31.addChild(leftClaw3);
		leftClaw3.cubeList.add(new ModelBox(leftClaw3, 119, 224, -4.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F, false));

		leftClaw31 = new ModelRenderer(this);
		leftClaw31.setRotationPoint(-5.0F, 0.5F, 0.0F);
		leftClaw3.addChild(leftClaw31);
		leftClaw31.cubeList.add(new ModelBox(leftClaw31, 119, 224, -1.0F, -1.75F, -1.0F, 3, 2, 2, 0.0F, false));

		leftClaw32 = new ModelRenderer(this);
		leftClaw32.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftClaw31.addChild(leftClaw32);
		leftClaw32.cubeList.add(new ModelBox(leftClaw32, 119, 224, -7.75F, -1.25F, -0.5F, 7, 1, 1, 0.0F, false));

		leftArm4 = new ModelRenderer(this);
		leftArm4.setRotationPoint(17.0F, 1.5F, 17.5F);
		dorsoMeat.addChild(leftArm4);
		leftArm4.cubeList.add(new ModelBox(leftArm4, 0, 0, -21.0F, -1.5F, -1.5F, 23, 3, 3, 0.0F, false));

		leftConn4 = new ModelRenderer(this);
		leftConn4.setRotationPoint(-20.5F, 0.0F, 0.0F);
		leftArm4.addChild(leftConn4);
		leftConn4.cubeList.add(new ModelBox(leftConn4, 238, 222, -2.5F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		leftArm41 = new ModelRenderer(this);
		leftArm41.setRotationPoint(-1.0F, 0.0F, 0.0F);
		leftConn4.addChild(leftArm41);
		leftArm41.cubeList.add(new ModelBox(leftArm41, 0, 0, -10.5F, -1.5F, -1.5F, 10, 3, 3, 0.0F, false));

		leftConn41 = new ModelRenderer(this);
		leftConn41.setRotationPoint(-10.0F, 0.0F, 0.0F);
		leftArm41.addChild(leftConn41);
		leftConn41.cubeList.add(new ModelBox(leftConn41, 240, 222, -2.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));

		leftClaw4 = new ModelRenderer(this);
		leftClaw4.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftConn41.addChild(leftClaw4);
		leftClaw4.cubeList.add(new ModelBox(leftClaw4, 123, 223, -4.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F, false));

		leftClaw41 = new ModelRenderer(this);
		leftClaw41.setRotationPoint(-5.0F, 0.5F, 0.0F);
		leftClaw4.addChild(leftClaw41);
		leftClaw41.cubeList.add(new ModelBox(leftClaw41, 123, 223, -1.0F, -1.75F, -1.0F, 3, 2, 2, 0.0F, false));

		leftClaw42 = new ModelRenderer(this);
		leftClaw42.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftClaw41.addChild(leftClaw42);
		leftClaw42.cubeList.add(new ModelBox(leftClaw42, 123, 223, -7.75F, -1.25F, -0.5F, 7, 1, 1, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 2.0F, 16.0F);
		dorsoMeat.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 92, 169, -10.0F, -2.0F, 1.0F, 20, 6, 32, 0.0F, false));
		tail.cubeList.add(new ModelBox(tail, 0, 171, -7.0F, 4.0F, 1.0F, 14, 3, 32, 0.0F, false));

		tail2 = new ModelRenderer(this);
		tail2.setRotationPoint(0.0F, 0.0F, 30.0F);
		tail.addChild(tail2);
		tail2.cubeList.add(new ModelBox(tail2, 144, 207, -8.0F, -1.0F, 0.0F, 16, 5, 30, 0.0F, false));
		tail2.cubeList.add(new ModelBox(tail2, 0, 174, -5.0F, 4.0F, 0.0F, 10, 2, 30, 0.0F, false));

		tail3 = new ModelRenderer(this);
		tail3.setRotationPoint(0.0F, 0.0F, 26.0F);
		tail2.addChild(tail3);
		tail3.cubeList.add(new ModelBox(tail3, 0, 0, -9.0F, -2.0F, 0.0F, 18, 9, 11, 0.0F, false));
		tail3.cubeList.add(new ModelBox(tail3, 154, 0, -15.0F, 3.0F, 8.0F, 30, 0, 21, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 5.0F, -28.0F);
		dorsoMeat.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 0, -8.0F, -3.0F, -9.0F, 16, 11, 10, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 0, -8.0F, 1.0F, -10.0F, 16, 7, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 240, 248, 6.0F, -4.0F, -11.0F, 4, 4, 4, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 240, 248, -9.0F, -4.0F, -11.0F, 4, 4, 4, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 244, 250, -6.0F, 1.0F, -12.0F, 3, 3, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 244, 250, 4.0F, 1.0F, -12.0F, 3, 3, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 248, 252, 1.0F, 5.0F, -12.0F, 2, 2, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 248, 252, -2.0F, 5.0F, -12.0F, 2, 2, 2, 0.0F, false));

		rightJaw = new ModelRenderer(this);
		rightJaw.setRotationPoint(-4.0F, 7.0F, -6.0F);
		head.addChild(rightJaw);
		rightJaw.cubeList.add(new ModelBox(rightJaw, 244, 237, -2.0F, -1.0F, -1.0F, 3, 6, 3, 0.0F, false));

		rightJaw1 = new ModelRenderer(this);
		rightJaw1.setRotationPoint(0.0F, 4.0F, 0.0F);
		rightJaw.addChild(rightJaw1);
		rightJaw1.cubeList.add(new ModelBox(rightJaw1, 248, 237, -1.5F, -1.75F, -0.75F, 2, 7, 2, 0.0F, false));

		leftJaw = new ModelRenderer(this);
		leftJaw.setRotationPoint(5.0F, 7.0F, -6.0F);
		head.addChild(leftJaw);
		leftJaw.cubeList.add(new ModelBox(leftJaw, 244, 237, -2.0F, -1.0F, -1.0F, 3, 6, 3, 0.0F, false));

		rightJaw2 = new ModelRenderer(this);
		rightJaw2.setRotationPoint(-1.0F, 4.0F, 0.0F);
		leftJaw.addChild(rightJaw2);
		rightJaw2.cubeList.add(new ModelBox(rightJaw2, 248, 236, -0.4774F, -1.5706F, -0.5F, 2, 7, 2, 0.0F, false));

		leftPincerArm = new ModelRenderer(this);
		leftPincerArm.setRotationPoint(13.0F, 8.0F, -25.0F);
		dorsoMeat.addChild(leftPincerArm);
		leftPincerArm.cubeList.add(new ModelBox(leftPincerArm, 0, 0, -3.0F, -4.0F, -2.0F, 24, 4, 4, 0.0F, false));

		leftPincerForeArm = new ModelRenderer(this);
		leftPincerForeArm.setRotationPoint(18.0F, -1.0F, 0.0F);
		leftPincerArm.addChild(leftPincerForeArm);
		leftPincerForeArm.cubeList.add(new ModelBox(leftPincerForeArm, 0, 0, -1.0F, -2.5F, -1.0F, 19, 4, 4, 0.0F, false));

		leftPincer = new ModelRenderer(this);
		leftPincer.setRotationPoint(17.0F, -1.0F, 1.0F);
		leftPincerForeArm.addChild(leftPincer);

		leftPincerUp = new ModelRenderer(this);
		leftPincerUp.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftPincer.addChild(leftPincerUp);
		leftPincerUp.cubeList.add(new ModelBox(leftPincerUp, 232, 226, -1.0F, -3.0F, -1.0F, 10, 3, 2, 0.0F, false));

		leftPincerLeft = new ModelRenderer(this);
		leftPincerLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftPincer.addChild(leftPincerLeft);
		leftPincerLeft.cubeList.add(new ModelBox(leftPincerLeft, 232, 226, -1.5F, -4.0F, 0.0F, 10, 3, 2, 0.0F, false));

		leftPincerRight = new ModelRenderer(this);
		leftPincerRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftPincer.addChild(leftPincerRight);
		leftPincerRight.cubeList.add(new ModelBox(leftPincerRight, 232, 226, -1.5F, 0.25F, -1.0F, 10, 3, 2, 0.0F, false));

		rightPincerArm = new ModelRenderer(this);
		rightPincerArm.setRotationPoint(-17.0F, 5.0F, -25.0F);
		dorsoMeat.addChild(rightPincerArm);
		rightPincerArm.cubeList.add(new ModelBox(rightPincerArm, 0, 0, -3.0F, -4.0F, -2.0F, 24, 4, 4, 0.0F, false));

		rightPincerForeArm = new ModelRenderer(this);
		rightPincerForeArm.setRotationPoint(18.0F, -1.0F, 0.0F);
		rightPincerArm.addChild(rightPincerForeArm);
		rightPincerForeArm.cubeList.add(new ModelBox(rightPincerForeArm, 0, 0, -1.0F, -2.5F, -1.0F, 19, 4, 4, 0.0F, false));

		rightPincer = new ModelRenderer(this);
		rightPincer.setRotationPoint(17.0F, -1.0F, 1.0F);
		rightPincerForeArm.addChild(rightPincer);

		rightPincerUp = new ModelRenderer(this);
		rightPincerUp.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightPincer.addChild(rightPincerUp);
		rightPincerUp.cubeList.add(new ModelBox(rightPincerUp, 232, 226, -1.0F, -3.0F, -1.0F, 10, 3, 2, 0.0F, false));

		RightPincerLeft = new ModelRenderer(this);
		RightPincerLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightPincer.addChild(RightPincerLeft);
		RightPincerLeft.cubeList.add(new ModelBox(RightPincerLeft, 232, 226, -1.5F, -4.0F, 0.0F, 10, 3, 2, 0.0F, false));

		rightPincerRight = new ModelRenderer(this);
		rightPincerRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightPincer.addChild(rightPincerRight);
		rightPincerRight.cubeList.add(new ModelBox(rightPincerRight, 232, 226, -1.5F, 0.25F, -1.0F, 10, 3, 2, 0.0F, false));

		bait = new ModelRenderer(this);
		bait.setRotationPoint(0.0F, 0.0F, 0.0F);
		dorso.addChild(bait);
		bait.cubeList.add(new ModelBox(bait, 0, 87, -1.0F, -26.0F, -1.0F, 2, 27, 2, 0.0F, false));

		bait2 = new ModelRenderer(this);
		bait2.setRotationPoint(0.0F, -25.0F, 0.0F);
		bait.addChild(bait2);
		bait2.cubeList.add(new ModelBox(bait2, 0, 89, -0.5F, -24.0F, -0.5F, 1, 24, 1, 0.0F, false));

		bait3 = new ModelRenderer(this);
		bait3.setRotationPoint(0.0F, -24.0F, 0.0F);
		bait2.addChild(bait3);
		bait3.cubeList.add(new ModelBox(bait3, 0, 78, -2.0F, -3.0F, -2.0F, 4, 4, 4, 0.0F, false));

		bodyParts.add(dorso);
		bodyParts.add(dorsoMeat);
		bodyParts.add(rightArm1);
		bodyParts.add(rightConn1);
		bodyParts.add(rightArm11);
		bodyParts.add(rightConn11);
		bodyParts.add(rightClaw1);
		bodyParts.add(rightClaw11);
		bodyParts.add(rightClaw12);
		bodyParts.add(rightArm2);
		bodyParts.add(rightConn2);
		bodyParts.add(rightArm21);
		bodyParts.add(rightConn21);
		bodyParts.add(rightClaw2);
		bodyParts.add(rightClaw21);
		bodyParts.add(rightClaw22);
		bodyParts.add(rightArm3);
		bodyParts.add(rightConn3);
		bodyParts.add(rightArm31);
		bodyParts.add(rightConn31);
		bodyParts.add(rightClaw3);
		bodyParts.add(rightClaw31);
		bodyParts.add(rightClaw32);
		bodyParts.add(rightArm4);
		bodyParts.add(rightConn4);
		bodyParts.add(rightArm41);
		bodyParts.add(rightConn41);
		bodyParts.add(rightClaw4);
		bodyParts.add(rightClaw41);
		bodyParts.add(rightClaw42);
		bodyParts.add(leftArm1);
		bodyParts.add(leftConn1);
		bodyParts.add(leftArm11);
		bodyParts.add(leftConn11);
		bodyParts.add(leftClaw1);
		bodyParts.add(leftClaw11);
		bodyParts.add(leftClaw12);
		bodyParts.add(leftArm2);
		bodyParts.add(leftConn2);
		bodyParts.add(leftArm21);
		bodyParts.add(leftConn21);
		bodyParts.add(leftClaw2);
		bodyParts.add(leftClaw21);
		bodyParts.add(leftClaw22);
		bodyParts.add(leftArm3);
		bodyParts.add(leftConn3);
		bodyParts.add(leftArm31);
		bodyParts.add(leftConn31);
		bodyParts.add(leftClaw3);
		bodyParts.add(leftClaw31);
		bodyParts.add(leftClaw32);
		bodyParts.add(leftArm4);
		bodyParts.add(leftConn4);
		bodyParts.add(leftArm41);
		bodyParts.add(leftConn41);
		bodyParts.add(leftClaw4);
		bodyParts.add(leftClaw41);
		bodyParts.add(leftClaw42);
		bodyParts.add(tail);
		bodyParts.add(tail2);
		bodyParts.add(tail3);
		bodyParts.add(head);
		bodyParts.add(rightJaw);
		bodyParts.add(rightJaw1);
		bodyParts.add(leftJaw);
		bodyParts.add(rightJaw2);
		bodyParts.add(leftPincerArm);
		bodyParts.add(leftPincerForeArm);
		bodyParts.add(leftPincer);
		bodyParts.add(leftPincerUp);
		bodyParts.add(leftPincerLeft);
		bodyParts.add(leftPincerRight);
		bodyParts.add(rightPincerArm);
		bodyParts.add(rightPincerForeArm);
		bodyParts.add(rightPincer);
		bodyParts.add(rightPincerUp);
		bodyParts.add(RightPincerLeft);
		bodyParts.add(rightPincerRight);
		bodyParts.add(bait);
		bodyParts.add(bait2);
		bodyParts.add(bait3);

		setAngles(true);
		fillUpDefaultAngles();
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		dorso.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}


	@Override
	protected void setAngles(boolean addToDefault) {
		setRotateAngle(rightArm1, 0.0F, -0.4363F, -0.3491F, addToDefault);
		setRotateAngle(rightConn1, 0.0F, 0.0F, -0.3491F, addToDefault);
		setRotateAngle(rightArm11, 0.0F, 0.0F, -0.1745F, addToDefault);
		setRotateAngle(rightClaw11, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(rightClaw12, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(rightArm2, 0.0F, -0.0873F, -0.3491F, addToDefault);
		setRotateAngle(rightConn2, 0.0F, 0.0F, -0.3491F, addToDefault);
		setRotateAngle(rightArm21, 0.0F, 0.0F, -0.1745F, addToDefault);
		setRotateAngle(rightClaw21, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(rightClaw22, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(rightArm3, 0.0F, 0.0873F, -0.3491F, addToDefault);
		setRotateAngle(rightConn3, 0.0F, 0.0F, -0.3491F, addToDefault);
		setRotateAngle(rightArm31, 0.0F, 0.0F, -0.1745F, addToDefault);
		setRotateAngle(rightClaw31, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(rightClaw32, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(rightArm4, 0.0F, 0.4363F, -0.3491F, addToDefault);
		setRotateAngle(rightConn4, 0.0F, 0.0F, -0.3491F, addToDefault);
		setRotateAngle(rightArm41, 0.0F, 0.0F, -0.1745F, addToDefault);
		setRotateAngle(rightClaw41, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(rightClaw42, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(leftArm1, 0.0F, -2.7053F, 0.3491F, addToDefault);
		setRotateAngle(leftConn1, 0.0F, 0.0F, -0.3491F, addToDefault);
		setRotateAngle(leftArm11, 0.0F, 0.0F, -0.1745F, addToDefault);
		setRotateAngle(leftClaw11, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(leftClaw12, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(leftArm2, 0.0F, -3.0543F, 0.3491F, addToDefault);
		setRotateAngle(leftConn2, 0.0F, 0.0F, -0.3491F, addToDefault);
		setRotateAngle(leftArm21, 0.0F, 0.0F, -0.1745F, addToDefault);
		setRotateAngle(leftClaw21, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(leftClaw22, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(leftArm3, 0.0F, 3.0543F, 0.3491F, addToDefault);
		setRotateAngle(leftConn3, 0.0F, 0.0F, -0.3491F, addToDefault);
		setRotateAngle(leftArm31, 0.0F, 0.0F, -0.1745F, addToDefault);
		setRotateAngle(leftClaw31, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(leftClaw32, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(leftArm4, 0.0F, 2.7053F, 0.3491F, addToDefault);
		setRotateAngle(leftConn4, 0.0F, 0.0F, -0.3491F, addToDefault);
		setRotateAngle(leftArm41, 0.0F, 0.0F, -0.1745F, addToDefault);
		setRotateAngle(leftClaw41, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(leftClaw42, 0.0F, 0.0F, 0.1745F, addToDefault);
		setRotateAngle(tail, -0.2618F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tail2, -0.1745F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(rightJaw, 0.0F, 0.0F, -0.0873F, addToDefault);
		setRotateAngle(rightJaw1, 0.0F, 0.0F, -0.2618F, addToDefault);
		setRotateAngle(leftJaw, 0.0F, 0.0F, 0.0873F, addToDefault);
		setRotateAngle(rightJaw2, 0.0F, 0.0F, 0.2618F, addToDefault);
		setRotateAngle(leftPincerArm, 0.0F, 0.3491F, 0.4363F, addToDefault);
		setRotateAngle(leftPincerForeArm, 0.0F, 1.0472F, 0.0F, addToDefault);
		setRotateAngle(leftPincerUp, 0.0F, 0.0F, -0.5236F, addToDefault);
		setRotateAngle(leftPincerLeft, -2.0944F, -0.3491F, 0.0F, addToDefault);
		setRotateAngle(leftPincerRight, -0.5236F, 0.6109F, 0.6109F, addToDefault);
		setRotateAngle(rightPincerArm, 0.0F, 0.3491F, 2.7053F, addToDefault);
		setRotateAngle(rightPincerForeArm, 0.0F, 1.0472F, 0.0F, addToDefault);
		setRotateAngle(rightPincer, 3.0543F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(rightPincerUp, 0.0F, 0.0F, -0.5236F, addToDefault);
		setRotateAngle(RightPincerLeft, -2.0944F, -0.3491F, 0.0F, addToDefault);
		setRotateAngle(rightPincerRight, -0.5236F, 0.6109F, 0.6109F, addToDefault);
		setRotateAngle(leftConn11, 0.0F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(leftConn21, 0.0F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(leftConn31, 0.0F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(leftConn41, 0.0F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(rightConn11, 0.0F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(rightConn21, 0.0F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(rightConn31, 0.0F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(rightConn41, 0.0F, 0.0F, 0.0F, addToDefault);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
		setAngles(false);
		dorso.offsetY = 1.125F;

		EntitySandflatter e = (EntitySandflatter) entitylivingbaseIn;

		// waiting stance
//		leftConn1.rotateAngleZ = 0.3491F;
//		leftConn2.rotateAngleZ = 0.3491F;
//		leftConn3.rotateAngleZ = 0.3491F;
//		leftConn4.rotateAngleZ = 0.3491F;
//		rightConn1.rotateAngleZ = 0.3491F;
//		rightConn2.rotateAngleZ = 0.3491F;
//		rightConn3.rotateAngleZ = 0.3491F;
//		rightConn4.rotateAngleZ = 0.3491F;
//		leftConn11.rotateAngleZ = 0.3491F;
//		leftConn21.rotateAngleZ = 0.3491F;
//		leftConn31.rotateAngleZ = 0.3491F;
//		leftConn41.rotateAngleZ = 0.3491F;
//		rightConn11.rotateAngleZ = 0.3491F;
//		rightConn21.rotateAngleZ = 0.3491F;
//		rightConn31.rotateAngleZ = 0.3491F;
//		rightConn41.rotateAngleZ = 0.3491F;
//		leftArm1.rotateAngleZ = 0.1F;
//		leftArm2.rotateAngleZ = 0.1F;
//		leftArm3.rotateAngleZ = 0.1F;
//		leftArm4.rotateAngleZ = 0.1F;
//		rightArm1.rotateAngleZ = -0.1F;
//		rightArm2.rotateAngleZ = -0.1F;
//		rightArm3.rotateAngleZ = -0.1F;
//		rightArm4.rotateAngleZ = -0.1F;


		// almost final phase
//		leftArm1.rotateAngleZ = -1.3491F;
//		leftArm2.rotateAngleZ = -1.3491F;
//		leftArm3.rotateAngleZ = -1.3491F;
//		leftArm4.rotateAngleZ = -1.3491F;
//		rightArm1.rotateAngleZ = 1.3491F;
//		rightArm2.rotateAngleZ = 1.3491F;
//		rightArm3.rotateAngleZ = 1.3491F;
//		rightArm4.rotateAngleZ = 1.3491F;
//		leftConn1.rotateAngleZ = 0.6491F;
//		leftConn2.rotateAngleZ = 0.6491F;
//		leftConn3.rotateAngleZ = 0.6491F;
//		leftConn4.rotateAngleZ = 0.6491F;
//		rightConn1.rotateAngleZ = 0.6491F;
//		rightConn2.rotateAngleZ = 0.6491F;
//		rightConn3.rotateAngleZ = 0.6491F;
//		rightConn4.rotateAngleZ = 0.6491F;

		Animation a = e.getAttackAnimation();
		if(a != null) {
			a.applyTransformations(bodyParts, partialTickTime);

		}
	}

}