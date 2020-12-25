package com.valeriotor.beyondtheveil.entities.models;//Made with Blockbench

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelDeepOneBrute extends ModelBase {
	private final ModelRenderer start;
	private final ModelRenderer higher_body;
	private final ModelRenderer head;
	private final ModelRenderer upper_jaw;
	private final ModelRenderer lowerjaw;
	private final ModelRenderer lower_jaw_left;
	private final ModelRenderer lower_jaw_right;
	private final ModelRenderer lower_jaw_mid;
	private final ModelRenderer head_fin;
	private final ModelRenderer left_head_fin;
	private final ModelRenderer right_head_fin;
	private final ModelRenderer eyes;
	private final ModelRenderer left_arm;
	private final ModelRenderer left_lower_arm;
	private final ModelRenderer right_arm;
	private final ModelRenderer right_lower_arm;
	private final ModelRenderer dorsal_fin;
	private final ModelRenderer left_leg;
	private final ModelRenderer left_lower_leg;
	private final ModelRenderer right_leg;
	private final ModelRenderer right_lower_leg;

	public ModelDeepOneBrute() {
		textureWidth = 128;
		textureHeight = 128;

		start = new ModelRenderer(this);
		start.setRotationPoint(0.0F, 6.0F, 14.0F);
		setRotationAngle(start, 0.3491F, 0.0F, 0.0F);
		start.cubeList.add(new ModelBox(start, 76, 51, -9.0F, -11.0F, -13.0F, 18, 16, 8, 0.0F, false));

		higher_body = new ModelRenderer(this);
		higher_body.setRotationPoint(0.0F, -13.0F, -13.5F);
		setRotationAngle(higher_body, 0.7854F, 0.0F, 0.0F);
		start.addChild(higher_body);
		higher_body.cubeList.add(new ModelBox(higher_body, 0, 28, -12.0F, -8.3743F, -8.0346F, 24, 16, 15, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -5.0F, 0.0F);
		setRotationAngle(head, 0.3491F, 0.0F, 0.0F);
		higher_body.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 59, -7.0F, -10.0F, -6.0F, 14, 9, 12, 0.0F, false));

		upper_jaw = new ModelRenderer(this);
		upper_jaw.setRotationPoint(0.0F, -10.0F, 0.0F);
		setRotationAngle(upper_jaw, 0.0873F, 0.0F, 0.0F);
		head.addChild(upper_jaw);
		upper_jaw.cubeList.add(new ModelBox(upper_jaw, 0, 81, -6.99F, -5.0F, -1.0F, 14, 6, 7, 0.0F, false));

		lowerjaw = new ModelRenderer(this);
		lowerjaw.setRotationPoint(0.0F, -10.0F, -4.0F);
		setRotationAngle(lowerjaw, 0.5236F, 0.0F, 0.0F);
		head.addChild(lowerjaw);

		lower_jaw_left = new ModelRenderer(this);
		lower_jaw_left.setRotationPoint(5.5F, 0.0F, -2.0F);
		lowerjaw.addChild(lower_jaw_left);
		lower_jaw_left.cubeList.add(new ModelBox(lower_jaw_left, 95, 13, -0.5F, -8.0F, 1.0F, 3, 10, 4, 0.0F, false));

		lower_jaw_right = new ModelRenderer(this);
		lower_jaw_right.setRotationPoint(-5.5F, 0.0F, -2.0F);
		lowerjaw.addChild(lower_jaw_right);
		lower_jaw_right.cubeList.add(new ModelBox(lower_jaw_right, 81, 13, -2.5F, -8.0F, 1.0F, 3, 10, 4, 0.0F, false));

		lower_jaw_mid = new ModelRenderer(this);
		lower_jaw_mid.setRotationPoint(0.0F, 1.0F, -4.5F);
		lowerjaw.addChild(lower_jaw_mid);
		lower_jaw_mid.cubeList.add(new ModelBox(lower_jaw_mid, 86, 0, -5.0F, -9.0F, 2.5F, 10, 10, 3, 0.0F, false));

		head_fin = new ModelRenderer(this);
		head_fin.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(head_fin);
		head_fin.cubeList.add(new ModelBox(head_fin, 114, 31, -0.51F, -10.0F, 6.0F, 0, 11, 7, 0.0F, false));

		left_head_fin = new ModelRenderer(this);
		left_head_fin.setRotationPoint(7.0F, -7.0F, 1.0F);
		setRotationAngle(left_head_fin, 0.0F, 0.0F, -0.6109F);
		head.addChild(left_head_fin);
		left_head_fin.cubeList.add(new ModelBox(left_head_fin, 0, 0, 0.0F, 0.0F, -2.0F, 0, 5, 5, 0.0F, false));

		right_head_fin = new ModelRenderer(this);
		right_head_fin.setRotationPoint(-7.0F, -7.0F, 1.0F);
		setRotationAngle(right_head_fin, 0.0F, 0.0F, 0.6109F);
		head.addChild(right_head_fin);
		right_head_fin.cubeList.add(new ModelBox(right_head_fin, 0, 0, 0.0F, 0.0F, -2.0F, 0, 5, 5, 0.0F, false));

		eyes = new ModelRenderer(this);
		eyes.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(eyes);
		eyes.cubeList.add(new ModelBox(eyes, 112, 0, 1.0F, -16.0F, 0.0F, 4, 1, 4, 0.0F, false));
		eyes.cubeList.add(new ModelBox(eyes, 112, 0, -5.0F, -16.0F, 0.0F, 4, 1, 4, 0.0F, false));

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(10.5F, 0.0F, 0.5F);
		setRotationAngle(left_arm, 0.1745F, -0.4363F, 0.0F);
		higher_body.addChild(left_arm);
		left_arm.cubeList.add(new ModelBox(left_arm, 0, 0, -3.5F, -5.0F, -18.5F, 9, 9, 19, 0.0F, false));

		left_lower_arm = new ModelRenderer(this);
		left_lower_arm.setRotationPoint(0.5F, -0.5F, -16.5F);
		setRotationAngle(left_lower_arm, 0.0F, 0.3491F, 0.0F);
		left_arm.addChild(left_lower_arm);
		left_lower_arm.cubeList.add(new ModelBox(left_lower_arm, 0, 105, -2.5F, -3.5F, -15.5F, 7, 7, 16, 0.0F, false));

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(-7.5F, 0.0F, -4.5F);
		setRotationAngle(right_arm, 0.1745F, 0.4363F, 0.0F);
		higher_body.addChild(right_arm);
		right_arm.cubeList.add(new ModelBox(right_arm, 0, 0, -10.0622F, -5.4403F, -16.0028F, 9, 9, 19, 0.0F, false));

		right_lower_arm = new ModelRenderer(this);
		right_lower_arm.setRotationPoint(-3.5F, -0.5F, -11.5F);
		setRotationAngle(right_lower_arm, 0.0F, -0.3491F, 0.0F);
		right_arm.addChild(right_lower_arm);
		right_lower_arm.cubeList.add(new ModelBox(right_lower_arm, 0, 105, -6.5F, -3.9403F, -18.0028F, 7, 7, 16, 0.0F, false));

		dorsal_fin = new ModelRenderer(this);
		dorsal_fin.setRotationPoint(0.0F, 0.0F, 0.0F);
		higher_body.addChild(dorsal_fin);
		dorsal_fin.cubeList.add(new ModelBox(dorsal_fin, 110, 5, -0.5F, -9.0F, 6.0F, 0, 17, 9, 0.0F, false));

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(9.0F, 2.5F, -7.5F);
		setRotationAngle(left_leg, 0.0F, -0.4363F, 0.9599F);
		start.addChild(left_leg);
		left_leg.cubeList.add(new ModelBox(left_leg, 62, 0, -3.8126F, -9.9641F, -1.6548F, 6, 11, 5, 0.0F, false));

		left_lower_leg = new ModelRenderer(this);
		left_lower_leg.setRotationPoint(0.0F, -8.0F, 0.0F);
		setRotationAngle(left_lower_leg, 0.4363F, -0.6109F, 0.0F);
		left_leg.addChild(left_lower_leg);
		left_lower_leg.cubeList.add(new ModelBox(left_lower_leg, 78, 75, -3.0F, -2.0F, -19.0F, 4, 4, 21, 0.0F, false));

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(-9.0F, 2.5F, -7.5F);
		setRotationAngle(right_leg, 0.0F, 0.4363F, -0.9599F);
		start.addChild(right_leg);
		right_leg.cubeList.add(new ModelBox(right_leg, 62, 0, -3.1874F, -9.9641F, -1.6548F, 6, 11, 5, 0.0F, false));

		right_lower_leg = new ModelRenderer(this);
		right_lower_leg.setRotationPoint(18.0F, -8.0F, 0.0F);
		setRotationAngle(right_lower_leg, 0.4363F, 0.6109F, 0.0F);
		right_leg.addChild(right_lower_leg);
		right_lower_leg.cubeList.add(new ModelBox(right_lower_leg, 78, 75, -17.0F, -7.0F, -29.0F, 4, 4, 21, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		start.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}