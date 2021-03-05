package com.valeriotor.beyondtheveil.entities.models;
//Made with Blockbench
//Paste this code into your mod.

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDeepOneMyrmidon extends ModelBase {
	private final ModelRenderer torso0;
	private final ModelRenderer torso1;
	private final ModelRenderer torso2;
	private final ModelRenderer fin2;
	private final ModelRenderer torso3;
	private final ModelRenderer head;
	private final ModelRenderer left_eye;
	private final ModelRenderer right_eye;
	private final ModelRenderer lower_jaw;
	private final ModelRenderer upper_tooth_layer;
	private final ModelRenderer left_arm;
	private final ModelRenderer left_upper_arm;
	private final ModelRenderer left_forearm;
	private final ModelRenderer shield;
	private final ModelRenderer right_superior_arm;
	private final ModelRenderer right_superior_forearm;
	private final ModelRenderer spear;
	private final ModelRenderer fin3;
	private final ModelRenderer right_inferior_arm;
	private final ModelRenderer right_inferior_forearm;
	private final ModelRenderer sword;
	private final ModelRenderer fin1;
	private final ModelRenderer left_leg;
	private final ModelRenderer left_lower_leg;
	private final ModelRenderer left_lowest_leg;
	private final ModelRenderer left_foot;
	private final ModelRenderer right_leg;
	private final ModelRenderer right_lower_leg;
	private final ModelRenderer right_lowest_leg;
	private final ModelRenderer right_foot;

	public ModelDeepOneMyrmidon() {
		textureWidth = 256;
		textureHeight = 128;

		torso0 = new ModelRenderer(this);
		torso0.setRotationPoint(0.0F, 1.0F, 0.0F);
		torso0.cubeList.add(new ModelBox(torso0, 0, 0, -5.5F, -4.0F, 0.0F, 11, 7, 7, 0.0F, false));

		torso1 = new ModelRenderer(this);
		torso1.setRotationPoint(-0.25F, -2.25F, 3.7F);
		setRotationAngle(torso1, 0.0873F, 0.0F, 0.0F);
		torso0.addChild(torso1);
		torso1.cubeList.add(new ModelBox(torso1, 0, 114, -6.25F, -7.25F, -4.3F, 13, 6, 8, 0.0F, false));

		torso2 = new ModelRenderer(this);
		torso2.setRotationPoint(-0.25F, -9.75F, -0.2F);
		setRotationAngle(torso2, 0.0873F, 0.0F, 0.0F);
		torso1.addChild(torso2);
		torso2.cubeList.add(new ModelBox(torso2, 0, 113, -7.0F, -3.0F, -4.5F, 15, 6, 9, 0.0F, false));

		fin2 = new ModelRenderer(this);
		fin2.setRotationPoint(0.5F, 1.0F, 3.5F);
		torso2.addChild(fin2);
		fin2.cubeList.add(new ModelBox(fin2, 96, 41, -0.1F, -8.0F, 1.0F, 0, 10, 7, 0.0F, false));

		torso3 = new ModelRenderer(this);
		torso3.setRotationPoint(0.5F, -8.5F, 2.0F);
		setRotationAngle(torso3, 0.0873F, 0.0F, 0.0F);
		torso2.addChild(torso3);
		torso3.cubeList.add(new ModelBox(torso3, 0, 106, -8.0F, -5.25F, -7.5F, 16, 11, 11, 0.0F, false));
		torso3.cubeList.add(new ModelBox(torso3, 0, 0, -7.0F, -7.0F, -6.0F, 14, 2, 7, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -5.0F, -1.5F);
		torso3.addChild(head);
		head.cubeList.add(new ModelBox(head, 64, 0, -5.0F, -6.0F, -15.0F, 10, 8, 13, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 0, -3.5F, -5.01F, -17.0F, 7, 7, 3, 0.0F, false));

		left_eye = new ModelRenderer(this);
		left_eye.setRotationPoint(3.5F, -3.5F, -9.0F);
		setRotationAngle(left_eye, 0.0F, -0.2618F, 0.0F);
		head.addChild(left_eye);
		left_eye.cubeList.add(new ModelBox(left_eye, 0, 68, -0.5F, -1.51F, -3.0F, 3, 4, 3, 0.0F, false));

		right_eye = new ModelRenderer(this);
		right_eye.setRotationPoint(-4.5F, -3.5F, -9.0F);
		setRotationAngle(right_eye, 0.0F, 0.2618F, 0.0F);
		head.addChild(right_eye);
		right_eye.cubeList.add(new ModelBox(right_eye, 0, 69, -1.5F, -1.51F, -3.0F, 3, 4, 3, 0.0F, false));

		lower_jaw = new ModelRenderer(this);
		lower_jaw.setRotationPoint(0.0F, 3.5F, -3.5F);
		setRotationAngle(lower_jaw, 0.9599F, 0.0F, 0.0F);
		head.addChild(lower_jaw);
		lower_jaw.cubeList.add(new ModelBox(lower_jaw, 72, 34, -5.0F, -3.25F, -8.75F, 10, 1, 9, 0.0F, false));
		lower_jaw.cubeList.add(new ModelBox(lower_jaw, 0, 68, -1.0F, -7.85F, -8.25F, 1, 5, 1, 0.0F, false));
		lower_jaw.cubeList.add(new ModelBox(lower_jaw, 0, 68, 3.0F, -7.75F, -6.25F, 1, 5, 1, 0.0F, false));
		lower_jaw.cubeList.add(new ModelBox(lower_jaw, 0, 68, 3.0F, -7.75F, -4.25F, 1, 5, 1, 0.0F, false));
		lower_jaw.cubeList.add(new ModelBox(lower_jaw, 0, 70, 1.0F, -7.45F, -7.95F, 1, 5, 1, 0.0F, false));
		lower_jaw.cubeList.add(new ModelBox(lower_jaw, 0, 70, -3.0F, -7.26F, -7.75F, 1, 5, 1, 0.0F, false));
		lower_jaw.cubeList.add(new ModelBox(lower_jaw, 0, 70, -4.9F, -7.75F, -7.25F, 1, 5, 1, 0.0F, false));
		lower_jaw.cubeList.add(new ModelBox(lower_jaw, 0, 70, -4.8F, -7.75F, -5.25F, 1, 5, 1, 0.0F, false));
		lower_jaw.cubeList.add(new ModelBox(lower_jaw, 0, 70, 3.0F, -7.75F, -8.35F, 1, 5, 1, 0.0F, false));

		upper_tooth_layer = new ModelRenderer(this);
		upper_tooth_layer.setRotationPoint(-1.0F, 2.0F, -14.0F);
		head.addChild(upper_tooth_layer);
		upper_tooth_layer.cubeList.add(new ModelBox(upper_tooth_layer, 0, 70, -1.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F, false));
		upper_tooth_layer.cubeList.add(new ModelBox(upper_tooth_layer, 0, 70, -4.0F, 0.0F, 1.0F, 1, 3, 1, 0.0F, false));
		upper_tooth_layer.cubeList.add(new ModelBox(upper_tooth_layer, 0, 70, 4.0F, 0.0F, 1.0F, 1, 3, 1, 0.0F, false));
		upper_tooth_layer.cubeList.add(new ModelBox(upper_tooth_layer, 0, 70, 1.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F, false));
		upper_tooth_layer.cubeList.add(new ModelBox(upper_tooth_layer, 0, 70, 3.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F, false));
		upper_tooth_layer.cubeList.add(new ModelBox(upper_tooth_layer, 0, 70, -3.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F, false));

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso3.addChild(left_arm);
		left_arm.cubeList.add(new ModelBox(left_arm, 28, 106, 6.0F, -4.0F, -6.0F, 13, 5, 7, 0.0F, false));

		left_upper_arm = new ModelRenderer(this);
		left_upper_arm.setRotationPoint(11.5F, 1.5F, -2.5F);
		setRotationAngle(left_upper_arm, -0.3491F, 0.0F, -0.2618F);
		left_arm.addChild(left_upper_arm);
		left_upper_arm.cubeList.add(new ModelBox(left_upper_arm, 0, 0, -3.5F, -1.5F, -3.51F, 7, 15, 7, 0.0F, false));

		left_forearm = new ModelRenderer(this);
		left_forearm.setRotationPoint(0.0F, 13.5F, -0.5F);
		setRotationAngle(left_forearm, -1.4835F, 0.0F, 0.0F);
		left_upper_arm.addChild(left_forearm);
		left_forearm.cubeList.add(new ModelBox(left_forearm, 0, 0, -3.0F, -2.5F, -2.5F, 6, 22, 5, 0.0F, false));

		shield = new ModelRenderer(this);
		shield.setRotationPoint(0.0F, 0.0F, 0.0F);
		left_forearm.addChild(shield);
		shield.cubeList.add(new ModelBox(shield, 124, 0, -15.0F, 17.0F, -14.0F, 31, 5, 35, 0.0F, false));
		shield.cubeList.add(new ModelBox(shield, 198, 40, -12.0F, 18.0F, 21.0F, 25, 4, 4, 0.0F, false));

		right_superior_arm = new ModelRenderer(this);
		right_superior_arm.setRotationPoint(-8.0F, 0.0F, 0.0F);
		setRotationAngle(right_superior_arm, 0.0F, 0.0F, -0.5236F);
		torso3.addChild(right_superior_arm);
		right_superior_arm.cubeList.add(new ModelBox(right_superior_arm, 0, 0, -15.0F, -4.0F, -4.0F, 18, 5, 5, 0.0F, false));

		right_superior_forearm = new ModelRenderer(this);
		right_superior_forearm.setRotationPoint(-11.0F, 0.0F, 0.0F);
		setRotationAngle(right_superior_forearm, 0.0F, -0.4363F, 0.0F);
		right_superior_arm.addChild(right_superior_forearm);
		right_superior_forearm.cubeList.add(new ModelBox(right_superior_forearm, 0, 0, -15.0F, -4.01F, -3.0F, 13, 5, 5, 0.0F, false));

		spear = new ModelRenderer(this);
		spear.setRotationPoint(-13.0F, 0.0F, 0.0F);
		right_superior_forearm.addChild(spear);
		spear.cubeList.add(new ModelBox(spear, 92, 46, -4.0F, -3.0F, -40.0F, 3, 3, 79, 0.0F, false));
		spear.cubeList.add(new ModelBox(spear, 0, 49, -4.5F, -3.5F, -43.0F, 4, 4, 4, 0.0F, false));
		spear.cubeList.add(new ModelBox(spear, 0, 47, -4.0F, -3.0F, -45.0F, 3, 3, 5, 0.0F, false));
		spear.cubeList.add(new ModelBox(spear, 0, 51, -3.5F, -2.5F, -48.0F, 2, 2, 3, 0.0F, false));

		fin3 = new ModelRenderer(this);
		fin3.setRotationPoint(0.0F, 0.0F, 4.0F);
		torso3.addChild(fin3);
		fin3.cubeList.add(new ModelBox(fin3, 68, 32, 0.0F, -9.0F, -4.0F, 0, 12, 14, 0.0F, false));
		fin3.cubeList.add(new ModelBox(fin3, 33, 29, -0.1F, -18.0F, -13.0F, 0, 11, 17, 0.0F, false));

		right_inferior_arm = new ModelRenderer(this);
		right_inferior_arm.setRotationPoint(-7.0F, 0.0F, 0.0F);
		setRotationAngle(right_inferior_arm, 0.0F, 0.0F, -0.6109F);
		torso2.addChild(right_inferior_arm);
		right_inferior_arm.cubeList.add(new ModelBox(right_inferior_arm, 0, 0, -12.0F, -2.0F, -1.0F, 15, 5, 5, 0.0F, false));

		right_inferior_forearm = new ModelRenderer(this);
		right_inferior_forearm.setRotationPoint(-11.0F, 0.0F, 0.0F);
		setRotationAngle(right_inferior_forearm, 0.0F, -0.8727F, 0.0F);
		right_inferior_arm.addChild(right_inferior_forearm);
		right_inferior_forearm.cubeList.add(new ModelBox(right_inferior_forearm, 0, 0, -14.0F, -2.1F, -2.0F, 16, 5, 5, 0.0F, false));

		sword = new ModelRenderer(this);
		sword.setRotationPoint(-13.0F, 0.0F, 0.0F);
		setRotationAngle(sword, 0.0F, 0.1745F, 0.0F);
		right_inferior_forearm.addChild(sword);
		sword.cubeList.add(new ModelBox(sword, 166, 120, -3.0F, -1.0F, -2.0F, 3, 3, 5, 0.0F, false));
		sword.cubeList.add(new ModelBox(sword, 168, 122, -4.0F, -2.0F, -3.0F, 5, 5, 1, 0.0F, false));
		sword.cubeList.add(new ModelBox(sword, 0, 46, -3.0F, -0.5F, -21.0F, 3, 2, 18, 0.0F, false));
		sword.cubeList.add(new ModelBox(sword, 0, 53, -2.5F, -0.5F, -30.0F, 2, 2, 9, 0.0F, false));

		fin1 = new ModelRenderer(this);
		fin1.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso1.addChild(fin1);
		fin1.cubeList.add(new ModelBox(fin1, 111, 46, 0.0F, -8.0F, 4.0F, 0, 7, 5, 0.0F, false));

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(1.0F, 1.0F, 0.0F);
		setRotationAngle(left_leg, -0.8727F, -0.2618F, 0.0F);
		left_leg.cubeList.add(new ModelBox(left_leg, 0, 0, 3.0F, -4.0F, -1.0F, 6, 18, 5, 0.0F, false));

		left_lower_leg = new ModelRenderer(this);
		left_lower_leg.setRotationPoint(6.0F, 14.0F, 0.0F);
		setRotationAngle(left_lower_leg, 1.7453F, 0.0F, 0.0F);
		left_leg.addChild(left_lower_leg);
		left_lower_leg.cubeList.add(new ModelBox(left_lower_leg, 0, 0, -2.25F, 0.0F, -1.0F, 5, 17, 5, 0.0F, false));

		left_lowest_leg = new ModelRenderer(this);
		left_lowest_leg.setRotationPoint(0.0F, 17.0F, 1.0F);
		left_lower_leg.addChild(left_lowest_leg);
		left_lowest_leg.cubeList.add(new ModelBox(left_lowest_leg, 62, 59, -1.0F, -1.0F, -5.0F, 3, 3, 13, 0.0F, false));

		left_foot = new ModelRenderer(this);
		left_foot.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(left_foot, 0.6109F, 0.0F, 0.0F);
		left_lowest_leg.addChild(left_foot);
		left_foot.cubeList.add(new ModelBox(left_foot, 44, 58, -3.0F, -7.0F, -5.0F, 7, 8, 2, 0.0F, false));

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(-6.0F, 1.0F, 2.0F);
		setRotationAngle(right_leg, -0.8727F, 0.2618F, 0.0F);
		right_leg.cubeList.add(new ModelBox(right_leg, 0, 0, -2.7956F, -2.8104F, -1.9982F, 6, 18, 5, 0.0F, false));

		right_lower_leg = new ModelRenderer(this);
		right_lower_leg.setRotationPoint(0.2044F, 15.1896F, -0.9982F);
		setRotationAngle(right_lower_leg, 1.7453F, 0.0F, 0.0F);
		right_leg.addChild(right_lower_leg);
		right_lower_leg.cubeList.add(new ModelBox(right_lower_leg, 0, 0, -2.5F, 0.0F, -1.0F, 5, 17, 5, 0.0F, false));

		right_lowest_leg = new ModelRenderer(this);
		right_lowest_leg.setRotationPoint(-0.7044F, 15.8104F, -0.0018F);
		right_lower_leg.addChild(right_lowest_leg);
		right_lowest_leg.cubeList.add(new ModelBox(right_lowest_leg, 62, 59, -1.0F, -1.0F, -5.0F, 3, 3, 13, 0.0F, false));

		right_foot = new ModelRenderer(this);
		right_foot.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(right_foot, 0.6109F, 0.0F, 0.0F);
		right_lowest_leg.addChild(right_foot);
		right_foot.cubeList.add(new ModelBox(right_foot, 44, 58, -3.0F, -7.0F, -5.0F, 7, 8, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		torso0.render(f5);
		left_leg.render(f5);
		right_leg.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}