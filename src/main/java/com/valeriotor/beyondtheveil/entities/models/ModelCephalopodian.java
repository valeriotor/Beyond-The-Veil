package com.valeriotor.beyondtheveil.entities.models;//Made with Blockbench
//Paste this code into your mod.

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.entities.ictya.EntityCephalopodian;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelCephalopodian extends ModelAnimated {
	private final ModelRenderer body;					//0
	private final ModelRenderer front;					//1
	private final ModelRenderer leftpad;				//2
	private final ModelRenderer leftupperpad;			//3
	private final ModelRenderer leftupperspike;			//4
	private final ModelRenderer leftlowerpad;			//5
	private final ModelRenderer leftlowerspike;			//6
	private final ModelRenderer lefteye;				//7
	private final ModelRenderer leftupperlid;			//8
	private final ModelRenderer leftlowerlid;			//9
	private final ModelRenderer leftiris;				//10
	private final ModelRenderer rightpad;				//11
	private final ModelRenderer rightlowerpad;			//12
	private final ModelRenderer rightlowerspike;		//13
	private final ModelRenderer rightupperpad;			//14
	private final ModelRenderer rightupperspike;		//15
	private final ModelRenderer righteye;				//16
	private final ModelRenderer rightupperlid;			//17
	private final ModelRenderer rightlowerlid;			//18
	private final ModelRenderer rightiris;				//19
	private final ModelRenderer node;					//20
	private final ModelRenderer tent1;					//21
	private final ModelRenderer tent11;					//22
	private final ModelRenderer tmouth1;				//23
	private final ModelRenderer tm1upper;				//24
	private final ModelRenderer tm1lower;				//25
	private final ModelRenderer tent3;					//26
	private final ModelRenderer tent31;					//27
	private final ModelRenderer tmouth3;				//28
	private final ModelRenderer tm1upper3;				//29
	private final ModelRenderer tm1lower3;				//30
	private final ModelRenderer tent4;					//31
	private final ModelRenderer tent41;					//32
	private final ModelRenderer tmouth4;				//33
	private final ModelRenderer tm1upper4;				//34
	private final ModelRenderer tm1lower4;				//35
	private final ModelRenderer tent2;					//36
	private final ModelRenderer tent21;					//37
	private final ModelRenderer tmouth2;				//38
	private final ModelRenderer tm1upper2;				//39
	private final ModelRenderer tm1lower2;				//40
	private final ModelRenderer tent5;					//41
	private final ModelRenderer tent51;					//42
	private final ModelRenderer tent52;					//43
	private final ModelRenderer tent6;					//44
	private final ModelRenderer tent61;					//45
	private final ModelRenderer ten62;					//46
	private final ModelRenderer tent7;					//47
	private final ModelRenderer tent71;					//48
	private final ModelRenderer tent72;					//49
	private final ModelRenderer tent8;					//50
	private final ModelRenderer tent81;					//51
	private final ModelRenderer tent82;					//52

	public ModelCephalopodian() {
		textureWidth = 128;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 6.0F, -33.25F);
		body.cubeList.add(new ModelBox(body, 0, 0, -6.0F, -8.0F, -22.75F, 12, 12, 23, 0.0F, false));

		front = new ModelRenderer(this);
		front.setRotationPoint(0.0F, -2.0F, -22.25F);
		body.addChild(front);
		front.cubeList.add(new ModelBox(front, 0, 0, -11.0F, -12.0F, -7.5F, 25, 24, 8, 0.0F, false));

		leftpad = new ModelRenderer(this);
		leftpad.setRotationPoint(11.0F, 0.0F, -3.0F);
		front.addChild(leftpad);
		leftpad.cubeList.add(new ModelBox(leftpad, 0, 0, 2.0F, -19.0F, -5.0F, 24, 37, 6, 0.0F, false));

		leftupperpad = new ModelRenderer(this);
		leftupperpad.setRotationPoint(18.0F, -17.0F, 0.0F);
		leftpad.addChild(leftupperpad);
		leftupperpad.cubeList.add(new ModelBox(leftupperpad, 0, 0, 3.0F, -7.0F, -2.0F, 10, 11, 3, 0.0F, false));

		leftupperspike = new ModelRenderer(this);
		leftupperspike.setRotationPoint(11.1F, -6.1F, 0.0F);
		leftupperpad.addChild(leftupperspike);
		leftupperspike.cubeList.add(new ModelBox(leftupperspike, 90, 0, -1.0F, -1.0F, 0.0F, 2, 2, 17, 0.0F, false));

		leftlowerpad = new ModelRenderer(this);
		leftlowerpad.setRotationPoint(18.0F, 15.0F, 0.0F);
		leftpad.addChild(leftlowerpad);
		leftlowerpad.cubeList.add(new ModelBox(leftlowerpad, 0, 0, 3.0F, -2.0F, -2.0F, 10, 11, 3, 0.0F, false));

		leftlowerspike = new ModelRenderer(this);
		leftlowerspike.setRotationPoint(11.0F, 8.0F, 0.0F);
		leftlowerpad.addChild(leftlowerspike);
		leftlowerspike.cubeList.add(new ModelBox(leftlowerspike, 90, 0, -1.0F, -1.0F, 0.0F, 2, 2, 17, 0.0F, false));

		lefteye = new ModelRenderer(this);
		lefteye.setRotationPoint(13.0F, 0.0F, -1.0F);
		leftpad.addChild(lefteye);

		leftupperlid = new ModelRenderer(this);
		leftupperlid.setRotationPoint(1.0F, -7.0F, -3.0F);
		lefteye.addChild(leftupperlid);
		leftupperlid.cubeList.add(new ModelBox(leftupperlid, 0, 0, -6.0F, -1.0F, -2.0F, 13, 8, 3, 0.0F, false));

		leftlowerlid = new ModelRenderer(this);
		leftlowerlid.setRotationPoint(1.0F, 7.0F, -5.0F);
		lefteye.addChild(leftlowerlid);
		leftlowerlid.cubeList.add(new ModelBox(leftlowerlid, 0, 0, -6.0F, -7.0F, 0.0F, 13, 8, 3, 0.0F, false));

		leftiris = new ModelRenderer(this);
		leftiris.setRotationPoint(0.0F, 0.0F, 0.0F);
		lefteye.addChild(leftiris);
		leftiris.cubeList.add(new ModelBox(leftiris, 102, 48, -3.0F, -5.0F, -4.0F, 10, 10, 3, 0.0F, false));
		leftiris.cubeList.add(new ModelBox(leftiris, 90, 55, 0.0F, -2.0F, -4.9F, 4, 4, 2, 0.0F, false));

		rightpad = new ModelRenderer(this);
		rightpad.setRotationPoint(-6.0F, 0.0F, -3.0F);
		front.addChild(rightpad);
		rightpad.cubeList.add(new ModelBox(rightpad, 0, 0, 2.0F, -19.0F, -5.0F, 24, 37, 6, 0.0F, false));

		rightlowerpad = new ModelRenderer(this);
		rightlowerpad.setRotationPoint(18.0F, -17.0F, 0.0F);
		rightpad.addChild(rightlowerpad);
		rightlowerpad.cubeList.add(new ModelBox(rightlowerpad, 0, 0, 3.0F, -7.0F, -2.0F, 10, 11, 3, 0.0F, false));

		rightlowerspike = new ModelRenderer(this);
		rightlowerspike.setRotationPoint(11.1F, -6.1F, 0.0F);
		rightlowerpad.addChild(rightlowerspike);
		rightlowerspike.cubeList.add(new ModelBox(rightlowerspike, 90, 0, -1.0F, -1.0F, 0.0F, 2, 2, 17, 0.0F, false));

		rightupperpad = new ModelRenderer(this);
		rightupperpad.setRotationPoint(18.0F, 15.0F, 0.0F);
		rightpad.addChild(rightupperpad);
		rightupperpad.cubeList.add(new ModelBox(rightupperpad, 0, 0, 3.0F, -2.0F, -2.0F, 10, 11, 3, 0.0F, false));

		rightupperspike = new ModelRenderer(this);
		rightupperspike.setRotationPoint(11.0F, 8.0F, 0.0F);
		rightupperpad.addChild(rightupperspike);
		rightupperspike.cubeList.add(new ModelBox(rightupperspike, 90, 0, -1.0F, -1.0F, 0.0F, 2, 2, 17, 0.0F, false));

		righteye = new ModelRenderer(this);
		righteye.setRotationPoint(30.0F, 0.0F, -1.0F);
		rightpad.addChild(righteye);

		rightupperlid = new ModelRenderer(this);
		rightupperlid.setRotationPoint(1.0F, -7.0F, -3.0F);
		righteye.addChild(rightupperlid);
		rightupperlid.cubeList.add(new ModelBox(rightupperlid, 0, 0, -23.0F, -1.0F, -2.0F, 13, 8, 3, 0.0F, false));

		rightlowerlid = new ModelRenderer(this);
		rightlowerlid.setRotationPoint(1.0F, 7.0F, -5.0F);
		righteye.addChild(rightlowerlid);
		rightlowerlid.cubeList.add(new ModelBox(rightlowerlid, 0, 0, -23.0F, -7.0F, 0.0F, 13, 8, 3, 0.0F, false));

		rightiris = new ModelRenderer(this);
		rightiris.setRotationPoint(0.0F, 0.0F, 0.0F);
		righteye.addChild(rightiris);
		rightiris.cubeList.add(new ModelBox(rightiris, 102, 48, -20.0F, -5.0F, -4.0F, 10, 10, 3, 0.0F, false));
		rightiris.cubeList.add(new ModelBox(rightiris, 90, 55, -17.0F, -2.0F, -4.9F, 4, 4, 2, 0.0F, false));

		node = new ModelRenderer(this);
		node.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(node);
		node.cubeList.add(new ModelBox(node, 0, 0, -5.0F, -6.0F, -2.0F, 10, 9, 10, 0.0F, false));

		tent1 = new ModelRenderer(this);
		tent1.setRotationPoint(-3.0F, -4.0F, 7.0F);
		node.addChild(tent1);
		tent1.cubeList.add(new ModelBox(tent1, 0, 0, -3.0F, -2.0F, 0.0F, 3, 3, 32, 0.0F, false));

		tent11 = new ModelRenderer(this);
		tent11.setRotationPoint(0.0F, 0.0F, 31.0F);
		tent1.addChild(tent11);
		tent11.cubeList.add(new ModelBox(tent11, 0, 0, -3.0F, -2.0F, -1.0F, 3, 3, 61, 0.0F, false));

		tmouth1 = new ModelRenderer(this);
		tmouth1.setRotationPoint(0.0F, 0.0F, 56.0F);
		tent11.addChild(tmouth1);

		tm1upper = new ModelRenderer(this);
		tm1upper.setRotationPoint(0.0F, 0.0F, 0.0F);
		tmouth1.addChild(tm1upper);
		tm1upper.cubeList.add(new ModelBox(tm1upper, 96, 19, -3.0F, -1.0F, 0.0F, 3, 1, 13, 0.0F, false));

		tm1lower = new ModelRenderer(this);
		tm1lower.setRotationPoint(0.0F, 0.0F, 0.0F);
		tmouth1.addChild(tm1lower);
		tm1lower.cubeList.add(new ModelBox(tm1lower, 96, 19, -3.0F, -1.0F, 0.0F, 3, 1, 13, 0.0F, false));

		tent3 = new ModelRenderer(this);
		tent3.setRotationPoint(4.0F, -3.0F, 7.0F);
		node.addChild(tent3);
		tent3.cubeList.add(new ModelBox(tent3, 0, 0, -3.0F, -2.0F, 0.0F, 3, 3, 32, 0.0F, false));

		tent31 = new ModelRenderer(this);
		tent31.setRotationPoint(0.0F, 0.0F, 31.0F);
		tent3.addChild(tent31);
		tent31.cubeList.add(new ModelBox(tent31, 0, 0, -3.0F, -2.0F, -1.0F, 3, 3, 61, 0.0F, false));

		tmouth3 = new ModelRenderer(this);
		tmouth3.setRotationPoint(0.0F, 0.0F, 58.0F);
		tent31.addChild(tmouth3);

		tm1upper3 = new ModelRenderer(this);
		tm1upper3.setRotationPoint(0.0F, 0.0F, 0.0F);
		tmouth3.addChild(tm1upper3);
		tm1upper3.cubeList.add(new ModelBox(tm1upper3, 96, 19, -2.9F, -1.1F, 0.0F, 3, 1, 13, 0.0F, false));

		tm1lower3 = new ModelRenderer(this);
		tm1lower3.setRotationPoint(0.0F, 0.0F, 0.0F);
		tmouth3.addChild(tm1lower3);
		tm1lower3.cubeList.add(new ModelBox(tm1lower3, 96, 19, -2.9F, -1.1F, 0.0F, 3, 1, 13, 0.0F, false));

		tent4 = new ModelRenderer(this);
		tent4.setRotationPoint(-3.0F, 0.0F, 7.0F);
		node.addChild(tent4);
		tent4.cubeList.add(new ModelBox(tent4, 0, 0, -3.0F, -2.0F, 0.0F, 3, 3, 32, 0.0F, false));

		tent41 = new ModelRenderer(this);
		tent41.setRotationPoint(0.0F, 0.0F, 31.0F);
		tent4.addChild(tent41);
		tent41.cubeList.add(new ModelBox(tent41, 0, 0, -3.0F, -2.0F, -1.0F, 3, 3, 61, 0.0F, false));

		tmouth4 = new ModelRenderer(this);
		tmouth4.setRotationPoint(0.0F, 0.0F, 58.0F);
		tent41.addChild(tmouth4);

		tm1upper4 = new ModelRenderer(this);
		tm1upper4.setRotationPoint(0.0F, 0.0F, 0.0F);
		tmouth4.addChild(tm1upper4);
		tm1upper4.cubeList.add(new ModelBox(tm1upper4, 96, 19, -3.0F, -1.0F, 0.0F, 3, 1, 13, 0.0F, false));

		tm1lower4 = new ModelRenderer(this);
		tm1lower4.setRotationPoint(0.0F, 0.0F, 0.0F);
		tmouth4.addChild(tm1lower4);
		tm1lower4.cubeList.add(new ModelBox(tm1lower4, 96, 19, -3.0F, -1.0F, 0.0F, 3, 1, 13, 0.0F, false));

		tent2 = new ModelRenderer(this);
		tent2.setRotationPoint(3.0F, 0.0F, 7.0F);
		node.addChild(tent2);
		tent2.cubeList.add(new ModelBox(tent2, 0, 0, -3.0F, -2.0F, 0.0F, 3, 3, 32, 0.0F, false));

		tent21 = new ModelRenderer(this);
		tent21.setRotationPoint(0.0F, 0.0F, 31.0F);
		tent2.addChild(tent21);
		tent21.cubeList.add(new ModelBox(tent21, 0, 0, -3.0F, -2.0F, -1.0F, 3, 3, 61, 0.0F, false));

		tmouth2 = new ModelRenderer(this);
		tmouth2.setRotationPoint(0.0F, 0.0F, 58.0F);
		tent21.addChild(tmouth2);

		tm1upper2 = new ModelRenderer(this);
		tm1upper2.setRotationPoint(0.0F, 0.0F, 0.0F);
		tmouth2.addChild(tm1upper2);
		tm1upper2.cubeList.add(new ModelBox(tm1upper2, 96, 19, -3.0F, -1.0F, 0.0F, 3, 1, 13, 0.0F, false));

		tm1lower2 = new ModelRenderer(this);
		tm1lower2.setRotationPoint(0.0F, 0.0F, 0.0F);
		tmouth2.addChild(tm1lower2);
		tm1lower2.cubeList.add(new ModelBox(tm1lower2, 96, 19, -3.0F, -1.0F, 0.0F, 3, 1, 13, 0.0F, false));

		tent5 = new ModelRenderer(this);
		tent5.setRotationPoint(0.0F, -5.0F, 7.5F);
		node.addChild(tent5);
		tent5.cubeList.add(new ModelBox(tent5, 0, 0, -1.0F, -1.0F, -0.5F, 2, 2, 42, 0.0F, false));

		tent51 = new ModelRenderer(this);
		tent51.setRotationPoint(0.0F, 0.0F, 39.0F);
		tent5.addChild(tent51);
		tent51.cubeList.add(new ModelBox(tent51, 0, 0, -0.9F, -1.0F, 0.0F, 2, 2, 42, 0.0F, false));

		tent52 = new ModelRenderer(this);
		tent52.setRotationPoint(0.0F, 0.0F, 39.0F);
		tent51.addChild(tent52);
		tent52.cubeList.add(new ModelBox(tent52, 0, 0, -1.0F, -1.0F, 0.0F, 2, 2, 48, 0.0F, false));

		tent6 = new ModelRenderer(this);
		tent6.setRotationPoint(3.0F, -2.0F, 7.5F);
		node.addChild(tent6);
		tent6.cubeList.add(new ModelBox(tent6, 0, 0, -1.0F, -1.0F, -0.5F, 2, 2, 42, 0.0F, false));

		tent61 = new ModelRenderer(this);
		tent61.setRotationPoint(0.0F, 0.0F, 39.0F);
		tent6.addChild(tent61);
		tent61.cubeList.add(new ModelBox(tent61, 0, 0, -0.9F, -1.0F, 0.0F, 2, 2, 42, 0.0F, false));

		ten62 = new ModelRenderer(this);
		ten62.setRotationPoint(0.0F, 0.0F, 39.0F);
		tent61.addChild(ten62);
		ten62.cubeList.add(new ModelBox(ten62, 0, 0, -1.0F, -1.0F, 0.0F, 2, 2, 48, 0.0F, false));

		tent7 = new ModelRenderer(this);
		tent7.setRotationPoint(-4.0F, -2.0F, 7.5F);
		node.addChild(tent7);
		tent7.cubeList.add(new ModelBox(tent7, 0, 0, -1.0F, -1.0F, -0.5F, 2, 2, 42, 0.0F, false));

		tent71 = new ModelRenderer(this);
		tent71.setRotationPoint(0.0F, 0.0F, 39.0F);
		tent7.addChild(tent71);
		tent71.cubeList.add(new ModelBox(tent71, 0, 0, -1.1F, -1.0F, 0.0F, 2, 2, 42, 0.0F, false));

		tent72 = new ModelRenderer(this);
		tent72.setRotationPoint(0.0F, 0.0F, 39.0F);
		tent71.addChild(tent72);
		tent72.cubeList.add(new ModelBox(tent72, 0, 0, -1.0F, -1.0F, 0.0F, 2, 2, 48, 0.0F, false));

		tent8 = new ModelRenderer(this);
		tent8.setRotationPoint(0.0F, 2.0F, 7.5F);
		node.addChild(tent8);
		tent8.cubeList.add(new ModelBox(tent8, 0, 0, -1.0F, -1.0F, -0.5F, 2, 2, 42, 0.0F, false));

		tent81 = new ModelRenderer(this);
		tent81.setRotationPoint(0.0F, 0.0F, 39.0F);
		tent8.addChild(tent81);
		tent81.cubeList.add(new ModelBox(tent81, 0, 0, -0.9F, -1.0F, 0.0F, 2, 2, 42, 0.0F, false));

		tent82 = new ModelRenderer(this);
		tent82.setRotationPoint(0.0F, 0.0F, 39.0F);
		tent81.addChild(tent82);
		tent82.cubeList.add(new ModelBox(tent82, 0, 0, -1.0F, -1.0F, 0.0F, 2, 2, 48, 0.0F, false));

		bodyParts.add(body);
		bodyParts.add(front);
		bodyParts.add(leftpad);
		bodyParts.add(leftupperpad);
		bodyParts.add(leftupperspike);
		bodyParts.add(leftlowerpad);
		bodyParts.add(leftlowerspike);
		bodyParts.add(lefteye);
		bodyParts.add(leftupperlid);
		bodyParts.add(leftlowerlid);
		bodyParts.add(leftiris);
		bodyParts.add(rightpad);
		bodyParts.add(rightlowerpad);
		bodyParts.add(rightlowerspike);
		bodyParts.add(rightupperpad);
		bodyParts.add(rightupperspike);
		bodyParts.add(righteye);
		bodyParts.add(rightupperlid);
		bodyParts.add(rightlowerlid);
		bodyParts.add(rightiris);
		bodyParts.add(node);
		bodyParts.add(tent1);
		bodyParts.add(tent11);
		bodyParts.add(tmouth1);
		bodyParts.add(tm1upper);
		bodyParts.add(tm1lower);
		bodyParts.add(tent3);
		bodyParts.add(tent31);
		bodyParts.add(tmouth3);
		bodyParts.add(tm1upper3);
		bodyParts.add(tm1lower3);
		bodyParts.add(tent4);
		bodyParts.add(tent41);
		bodyParts.add(tmouth4);
		bodyParts.add(tm1upper4);
		bodyParts.add(tm1lower4);
		bodyParts.add(tent2);
		bodyParts.add(tent21);
		bodyParts.add(tmouth2);
		bodyParts.add(tm1upper2);
		bodyParts.add(tm1lower2);
		bodyParts.add(tent5);
		bodyParts.add(tent51);
		bodyParts.add(tent52);
		bodyParts.add(tent6);
		bodyParts.add(tent61);
		bodyParts.add(ten62);
		bodyParts.add(tent7);
		bodyParts.add(tent71);
		bodyParts.add(tent72);
		bodyParts.add(tent8);
		bodyParts.add(tent81);
		bodyParts.add(tent82);

		setAngles(true);
		fillUpDefaultAngles();
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	protected void setAngles(boolean addToDefault) {
		setRotateAngle(front, 0.0873F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(leftpad, 0.0F, -0.3491F, 0.0F, addToDefault);
		setRotateAngle(leftupperpad, -0.2618F, -0.2618F, 0.0F, addToDefault);
		setRotateAngle(leftupperspike, 0.0F, 0.3491F, 0.0F, addToDefault);
		setRotateAngle(leftlowerpad, 0.2618F, -0.2618F, 0.0F, addToDefault);
		setRotateAngle(leftlowerspike, 0.0F, 0.3491F, 0.0F, addToDefault);
		setRotateAngle(rightpad, 0.0F, -0.3491F, 3.1416F, addToDefault);
		setRotateAngle(rightlowerpad, -0.2618F, -0.2618F, 0.0F, addToDefault);
		setRotateAngle(rightlowerspike, 0.0F, 0.3491F, 0.0F, addToDefault);
		setRotateAngle(rightupperpad, 0.2618F, -0.2618F, 0.0F, addToDefault);
		setRotateAngle(rightupperspike, 0.0F, 0.3491F, 0.0F, addToDefault);
		setRotateAngle(tent1, 0.3491F, -0.3491F, 0.0F, addToDefault);
		setRotateAngle(tent11, -0.3491F, 0.3491F, 0.0F, addToDefault);
		setRotateAngle(tm1upper, 0.5236F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tm1lower, -0.3491F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent3, 0.3491F, -0.3491F, 1.396F, addToDefault);
		setRotateAngle(tent31, -0.3491F, 0.3491F, 0.0F, addToDefault);
		setRotateAngle(tm1upper3, 0.5236F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tm1lower3, -0.3491F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent4, 0.3491F, -0.3491F, -1.745F, addToDefault);
		setRotateAngle(tent41, -0.3491F, 0.3491F, 0.0F, addToDefault);
		setRotateAngle(tm1upper4, 0.5236F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tm1lower4, -0.3491F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent2, 0.3491F, -0.3491F, -3.1416F, addToDefault);
		setRotateAngle(tent21, -0.3491F, 0.3491F, 0.0F, addToDefault);
		setRotateAngle(tm1upper2, 0.5236F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tm1lower2, -0.3491F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent5, 0.2618F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent51, -0.1745F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent52, -0.1745F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent6, 0.2618F, 0.0F, 1.5708F, addToDefault);
		setRotateAngle(tent61, -0.1745F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(ten62, -0.1745F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent7, 0.2618F, 0.0F, -1.5708F, addToDefault);
		setRotateAngle(tent71, -0.1745F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent72, -0.1745F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent8, 0.2618F, 0.0F, 3.1416F, addToDefault);
		setRotateAngle(tent81, -0.1745F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(tent82, -0.1745F, 0.0F, 0.0F, addToDefault);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
		setAngles(false);

		EntityCephalopodian e = (EntityCephalopodian) entitylivingbaseIn;

		float tentacleSwing = (MathHelper.cos(limbSwing*0.4F) + 0.667F)*limbSwingAmount;

		tent1.rotateAngleX = 0.3491F + tentacleSwing*0.5F;
		tent1.rotateAngleY = -0.3491F - tentacleSwing*0.5F;
		tent2.rotateAngleX = 0.3491F + tentacleSwing*0.5F;
		tent2.rotateAngleY = -0.3491F - tentacleSwing*0.5F;
		tent3.rotateAngleX = 0.3491F + tentacleSwing*0.5F;
		tent3.rotateAngleY = -0.3491F - tentacleSwing*0.5F;
		tent4.rotateAngleX = 0.3491F + tentacleSwing*0.5F;
		tent4.rotateAngleY = -0.3491F - tentacleSwing*0.5F;

		tent5.rotateAngleX = 0.2618F + tentacleSwing*0.1F;
		tent6.rotateAngleX = 0.2618F + tentacleSwing*0.1F;
		tent7.rotateAngleX = 0.2618F + tentacleSwing*0.1F;
		tent8.rotateAngleX = 0.2618F + tentacleSwing*0.1F;

		tentacleSwing += 0.333*limbSwingAmount;
		tent11.rotateAngleY = 0.3491F + 2.5F*tentacleSwing*0.3491F/2;
		tent11.rotateAngleX = -0.3491F - 0.85F*tentacleSwing*0.3491F/2;
		tent21.rotateAngleY = 0.3491F + 2.5F*tentacleSwing*0.3491F/2;
		tent21.rotateAngleX = -0.3491F - 0.85F*tentacleSwing*0.3491F/2;
		tent31.rotateAngleY = 0.3491F + 2.5F*tentacleSwing*0.3491F/2;
		tent31.rotateAngleX = -0.3491F - 0.85F*tentacleSwing*0.3491F/2;
		tent41.rotateAngleY = 0.3491F + 2.5F*tentacleSwing*0.3491F/2;
		tent41.rotateAngleX = -0.3491F - 0.85F*tentacleSwing*0.3491F/2;


		float offset = MathHelper.sin((entitylivingbaseIn.ticksExisted%40 + partialTickTime)/ 6.366F)/30;
		tent1.rotateAngleX += offset;
		tent2.rotateAngleY -= offset;
		tent3.rotateAngleY += offset;
		tent4.rotateAngleX -= offset;
		tent5.rotateAngleX += offset;
		tent7.rotateAngleX -= offset;
		tent11.rotateAngleY -= offset/2;
		tent21.rotateAngleX += offset/2;
		tent31.rotateAngleX -= offset/2;
		tent41.rotateAngleY += offset/2;
		tent61.rotateAngleX -= offset/2;
		tent81.rotateAngleX += offset/2;
		leftupperpad.rotateAngleY += offset/2;
		rightupperpad.rotateAngleX -= offset/2;
		leftlowerpad.rotateAngleX += offset/2;
		rightlowerpad.rotateAngleY -= offset/2;
		offset = MathHelper.sin((entitylivingbaseIn.ticksExisted%50 + partialTickTime) / 7.95F)/20;
		tent1.rotateAngleY += offset;
		tent2.rotateAngleX -= offset;
		tent3.rotateAngleX += offset;
		tent4.rotateAngleY -= offset;
		tent6.rotateAngleX += offset;
		tent8.rotateAngleX -= offset;
		tent11.rotateAngleX -= offset/2;
		tent21.rotateAngleY += offset/2;
		tent31.rotateAngleY -= offset/2;
		tent41.rotateAngleX += offset/2;
		tent51.rotateAngleX -= offset/2;
		tent71.rotateAngleX += offset/2;
		leftupperpad.rotateAngleX += offset/2;
		rightupperpad.rotateAngleY -= offset/2;
		leftlowerpad.rotateAngleY += offset/2;
		rightlowerpad.rotateAngleX -= offset/2;

		Animation a = e.getAttackAnimation();
		if(a != null) {
			a.applyTransformations(bodyParts, partialTickTime);
		}

	}

	private float tentacleSwing(float limbSwing, float limbSwingAmount) {
		return (MathHelper.cos(limbSwing*0.4F) + 0.667F)*limbSwingAmount;
	}

}