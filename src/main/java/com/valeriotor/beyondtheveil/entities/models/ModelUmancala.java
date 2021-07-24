package com.valeriotor.beyondtheveil.entities.models;//Made with Blockbench
//Paste this code into your mod.

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelUmancala extends ModelBase {
	private final ModelRenderer node;
	private final ModelRenderer head1;
	private final ModelRenderer head2;
	private final ModelRenderer leg1;
	private final ModelRenderer leg11;
	private final ModelRenderer leg12;
	private final ModelRenderer leg2;
	private final ModelRenderer leg21;
	private final ModelRenderer leg22;
	private final ModelRenderer tentUp;
	private final ModelRenderer tentRight;
	private final ModelRenderer tentLeft;
	private final ModelRenderer tentDown;

	public ModelUmancala() {
		textureWidth = 128;
		textureHeight = 64;

		node = new ModelRenderer(this);
		node.setRotationPoint(0.0F, 11.0F, 0.0F);
		node.cubeList.add(new ModelBox(node, 0, 0, -3.5F, -3.0F, 0.0F, 7, 7, 10, 0.0F, false));

		head1 = new ModelRenderer(this);
		head1.setRotationPoint(0.0F, 0.0F, 0.0F);
		node.addChild(head1);
		head1.cubeList.add(new ModelBox(head1, 0, 0, -5.0F, -5.0F, -23.0F, 10, 10, 24, 0.0F, false));
		head1.cubeList.add(new ModelBox(head1, 0, 58, 4.75F, -1.0F, -3.0F, 2, 3, 3, 0.0F, true));
		head1.cubeList.add(new ModelBox(head1, 0, 58, 4.75F, -0.5F, -12.0F, 2, 3, 3, 0.0F, true));
		head1.cubeList.add(new ModelBox(head1, 0, 58, 4.75F, 0.0F, -21.0F, 2, 3, 3, 0.0F, true));
		head1.cubeList.add(new ModelBox(head1, 0, 58, -6.75F, -1.0F, -3.0F, 2, 3, 3, 0.0F, false));
		head1.cubeList.add(new ModelBox(head1, 0, 58, -6.75F, -0.5F, -12.0F, 2, 3, 3, 0.0F, false));
		head1.cubeList.add(new ModelBox(head1, 0, 58, -6.75F, 0.0F, -21.0F, 2, 3, 3, 0.0F, false));

		head2 = new ModelRenderer(this);
		head2.setRotationPoint(0.0F, 0.0F, 0.0F);
		head1.addChild(head2);
		head2.cubeList.add(new ModelBox(head2, 0, 0, -4.5F, -3.0F, -38.0F, 9, 9, 24, 0.0F, false));
		head2.cubeList.add(new ModelBox(head2, 0, 58, 4.0F, 0.75F, -30.0F, 2, 3, 3, 0.0F, true));
		head2.cubeList.add(new ModelBox(head2, 0, 58, -6.0F, 0.75F, -30.0F, 2, 3, 3, 0.0F, false));

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(-2.0F, 0.0F, 9.5F);
		setRotationAngle(leg1, -0.0873F, -0.0873F, 0.0F);
		node.addChild(leg1);
		leg1.cubeList.add(new ModelBox(leg1, 66, 33, -2.0F, -1.0F, 0.0F, 4, 4, 27, 0.0F, false));

		leg11 = new ModelRenderer(this);
		leg11.setRotationPoint(1.0F, 2.0F, 27.0F);
		leg1.addChild(leg11);
		leg11.cubeList.add(new ModelBox(leg11, 70, 35, -2.5F, -2.5F, -1.0F, 3, 3, 26, 0.0F, false));

		leg12 = new ModelRenderer(this);
		leg12.setRotationPoint(0.0F, 0.0F, 21.0F);
		leg11.addChild(leg12);
		leg12.cubeList.add(new ModelBox(leg12, 70, 35, -2.51F, -2.51F, 1.0F, 3, 3, 26, 0.0F, false));

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(-2.0F, 0.0F, 9.5F);
		setRotationAngle(leg2, -0.0873F, 0.0873F, 0.0F);
		node.addChild(leg2);
		leg2.cubeList.add(new ModelBox(leg2, 66, 33, 2.0F, -1.0F, 0.0F, 4, 4, 27, 0.0F, false));

		leg21 = new ModelRenderer(this);
		leg21.setRotationPoint(1.0F, 2.0F, 27.0F);
		leg2.addChild(leg21);
		leg21.cubeList.add(new ModelBox(leg21, 70, 35, 1.5F, -2.5F, -1.0F, 3, 3, 26, 0.0F, false));

		leg22 = new ModelRenderer(this);
		leg22.setRotationPoint(0.0F, 0.0F, 21.0F);
		leg21.addChild(leg22);
		leg22.cubeList.add(new ModelBox(leg22, 70, 35, 1.51F, -2.51F, 1.0F, 3, 3, 26, 0.0F, false));

		tentUp = new ModelRenderer(this);
		tentUp.setRotationPoint(0.0F, -3.0F, 10.0F);
		setRotationAngle(tentUp, 0.4363F, 0.0F, 0.0F);
		node.addChild(tentUp);
		tentUp.cubeList.add(new ModelBox(tentUp, 84, 38, -1.0F, -1.0F, -1.0F, 2, 2, 20, 0.0F, false));

		tentRight = new ModelRenderer(this);
		tentRight.setRotationPoint(-3.0F, 0.5F, 9.0F);
		setRotationAngle(tentRight, 0.0F, -0.4363F, 0.0F);
		node.addChild(tentRight);
		tentRight.cubeList.add(new ModelBox(tentRight, 72, 42, -1.0F, -1.0F, 0.0F, 2, 2, 20, 0.0F, false));

		tentLeft = new ModelRenderer(this);
		tentLeft.setRotationPoint(3.0F, 0.5F, 9.0F);
		setRotationAngle(tentLeft, 0.0F, 0.4363F, 0.0F);
		node.addChild(tentLeft);
		tentLeft.cubeList.add(new ModelBox(tentLeft, 77, 42, -1.0F, -1.0F, -1.25F, 2, 2, 20, 0.0F, false));

		tentDown = new ModelRenderer(this);
		tentDown.setRotationPoint(0.0F, 3.0F, 10.0F);
		setRotationAngle(tentDown, -0.4363F, 0.0F, 0.0F);
		node.addChild(tentDown);
		tentDown.cubeList.add(new ModelBox(tentDown, 72, 42, -1.0F, 0.0F, -1.0F, 2, 2, 20, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		node.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setLivingAnimations(EntityLivingBase e, float limbSwing, float limbSwingAmount, float partialTickTime) {


		float leg1Rot = MathHelper.cos(limbSwing * 0.4F) * limbSwingAmount / 3;
		leg1.rotateAngleX = -0.0873F + leg1Rot;
		leg11.rotateAngleX = leg1Rot;
		leg12.rotateAngleX = leg1Rot;
		float leg2Rot = MathHelper.cos(limbSwing * 0.4F + (float) Math.PI) * limbSwingAmount / 3;
		leg2.rotateAngleX = -0.0873F + leg2Rot;
		leg21.rotateAngleX = leg2Rot;
		leg22.rotateAngleX = leg2Rot;
		float offset = MathHelper.sin(e.ticksExisted%40 / 6.366F)/20;
		leg1.rotateAngleX += offset;
		leg21.rotateAngleX -= offset;
		leg12.rotateAngleX += offset;
		tentUp.rotateAngleX = 0.4363F + offset;
		tentRight.rotateAngleX = + offset;
		tentLeft.rotateAngleY = 0.4363F + offset;
		tentDown.rotateAngleY = + offset;
		head1.rotateAngleX = offset/4;
		offset = (float) Math.sin(e.ticksExisted%50 / 7.95)/16;
		leg2.rotateAngleX -= offset;
		leg11.rotateAngleX += offset;
		leg22.rotateAngleX -= offset;
		tentDown.rotateAngleX = -0.4363F - offset;
		tentLeft.rotateAngleX = - offset;
		tentUp.rotateAngleY = - offset;
		tentRight.rotateAngleY = -0.4363F - offset;
		head2.rotateAngleX = offset/4;
	}
}