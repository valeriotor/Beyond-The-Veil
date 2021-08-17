package com.valeriotor.beyondtheveil.entities.models;

//Made with Blockbench
//Paste this code into your mod.

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelDeepAngler extends ModelBase {
	private final ModelRenderer Body;
	private final ModelRenderer Head;
	private final ModelRenderer LowerJaw;
	private final ModelRenderer ToothLL;
	private final ModelRenderer ToothLR;
	private final ModelRenderer ToothRL;
	private final ModelRenderer ToothRR;
	private final ModelRenderer LightStem;
	private final ModelRenderer LightStem2;
	private final ModelRenderer Bulb;
	private final ModelRenderer Tail;
	private final ModelRenderer Tail2;
	private final ModelRenderer Tail3;
	private final ModelRenderer TailFin;
	private final ModelRenderer LeftFin;
	private final ModelRenderer RightFin;
	private final ModelRenderer DorsalFin;

	public ModelDeepAngler() {
		textureWidth = 64;
		textureHeight = 64;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 24.0F, 0.0F);
		Body.cubeList.add(new ModelBox(Body, 0, 0, -4.0F, -16.0F, -6.0F, 8, 9, 13, 0.0F, false));

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 36, 17, -3.0F, -16.25F, -13.0F, 6, 6, 8, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 31, 3.0F, -15.0F, -12.0F, 1, 2, 2, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 30, -4.0F, -15.0F, -12.0F, 1, 2, 2, 0.0F, false));

		LowerJaw = new ModelRenderer(this);
		LowerJaw.setRotationPoint(0.0F, -8.5F, 1.4F);
		Head.addChild(LowerJaw);
		LowerJaw.cubeList.add(new ModelBox(LowerJaw, 24, 31, -4.5F, -2.0F, -17.5F, 9, 4, 11, 0.0F, false));

		ToothLL = new ModelRenderer(this);
		ToothLL.setRotationPoint(3.5F, -2.5F, -16.5F);
		setRotationAngle(ToothLL, 0.2618F, 0.0F, 0.0F);
		LowerJaw.addChild(ToothLL);
		ToothLL.cubeList.add(new ModelBox(ToothLL, 0, 39, -0.5F, -3.5F, -0.5F, 1, 5, 1, 0.0F, false));

		ToothLR = new ModelRenderer(this);
		ToothLR.setRotationPoint(1.2F, -2.5F, -16.5F);
		setRotationAngle(ToothLR, 0.2618F, 0.0F, 0.0F);
		LowerJaw.addChild(ToothLR);
		ToothLR.cubeList.add(new ModelBox(ToothLR, 0, 39, -0.5F, -3.5F, -0.5F, 1, 5, 1, 0.0F, false));

		ToothRL = new ModelRenderer(this);
		ToothRL.setRotationPoint(-1.2F, -2.5F, -16.5F);
		setRotationAngle(ToothRL, 0.2618F, 0.0F, 0.0F);
		LowerJaw.addChild(ToothRL);
		ToothRL.cubeList.add(new ModelBox(ToothRL, 0, 39, -0.5F, -3.5F, -0.5F, 1, 5, 1, 0.0F, false));

		ToothRR = new ModelRenderer(this);
		ToothRR.setRotationPoint(-3.5F, -2.5F, -16.5F);
		setRotationAngle(ToothRR, 0.2618F, 0.0F, 0.0F);
		LowerJaw.addChild(ToothRR);
		ToothRR.cubeList.add(new ModelBox(ToothRR, 0, 39, -0.5F, -3.5F, -0.5F, 1, 5, 1, 0.0F, false));

		LightStem = new ModelRenderer(this);
		LightStem.setRotationPoint(0.0F, -15.0F, -6.5F);
		setRotationAngle(LightStem, 0.4363F, 0.0F, 0.0F);
		Head.addChild(LightStem);
		LightStem.cubeList.add(new ModelBox(LightStem, 0, 0, -0.5F, -5.0F, -0.5F, 1, 4, 1, 0.0F, false));

		LightStem2 = new ModelRenderer(this);
		LightStem2.setRotationPoint(0.0F, -4.0F, 0.0F);
		setRotationAngle(LightStem2, 0.5236F, 0.0F, 0.0F);
		LightStem.addChild(LightStem2);
		LightStem2.cubeList.add(new ModelBox(LightStem2, 0, 0, -0.5F, -4.0F, -0.2F, 1, 4, 1, 0.0F, false));

		Bulb = new ModelRenderer(this);
		Bulb.setRotationPoint(0.0F, -4.0F, 0.0F);
		setRotationAngle(Bulb, 0.6109F, 0.0F, 0.0F);
		LightStem2.addChild(Bulb);
		Bulb.cubeList.add(new ModelBox(Bulb, 0, 35, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F, false));

		Tail = new ModelRenderer(this);
		Tail.setRotationPoint(0.0F, -11.5F, 6.0F);
		setRotationAngle(Tail, -0.0873F, 0.0F, 0.0F);
		Body.addChild(Tail);
		Tail.cubeList.add(new ModelBox(Tail, 2, 9, -3.5F, -4.0F, 0.0F, 7, 8, 12, 0.0F, false));

		Tail2 = new ModelRenderer(this);
		Tail2.setRotationPoint(0.0F, 0.0F, 0.0F);
		Tail.addChild(Tail2);
		Tail2.cubeList.add(new ModelBox(Tail2, 46, 2, -2.5F, -2.0F, 12.0F, 5, 5, 4, 0.0F, false));

		Tail3 = new ModelRenderer(this);
		Tail3.setRotationPoint(0.0F, 0.0F, 0.0F);
		Tail2.addChild(Tail3);
		Tail3.cubeList.add(new ModelBox(Tail3, 0, 0, -1.0F, -1.0F, 15.0F, 2, 3, 3, 0.0F, false));

		TailFin = new ModelRenderer(this);
		TailFin.setRotationPoint(0.0F, 0.0F, 0.0F);
		Tail3.addChild(TailFin);
		TailFin.cubeList.add(new ModelBox(TailFin, 0, 54, -0.5F, -3.0F, 17.0F, 1, 7, 3, 0.0F, false));

		LeftFin = new ModelRenderer(this);
		LeftFin.setRotationPoint(3.5F, -10.5F, -3.5F);
		setRotationAngle(LeftFin, 0.0F, -0.4363F, 0.0F);
		Body.addChild(LeftFin);
		LeftFin.cubeList.add(new ModelBox(LeftFin, 0, 0, -0.5F, -1.5F, -0.5F, 3, 3, 1, 0.0F, false));
		LeftFin.cubeList.add(new ModelBox(LeftFin, 0, 59, 2.5F, -2.0F, -0.5F, 4, 4, 1, 0.0F, false));

		RightFin = new ModelRenderer(this);
		RightFin.setRotationPoint(-3.5F, -10.5F, -3.5F);
		setRotationAngle(RightFin, 0.0F, -2.7053F, 0.0F);
		Body.addChild(RightFin);
		RightFin.cubeList.add(new ModelBox(RightFin, 0, 0, 0.1558F, -1.5F, -0.4583F, 3, 3, 1, 0.0F, false));
		RightFin.cubeList.add(new ModelBox(RightFin, 0, 59, 3.1558F, -2.0F, -0.4583F, 4, 4, 1, 0.0F, false));

		DorsalFin = new ModelRenderer(this);
		DorsalFin.setRotationPoint(0.0F, -16.0F, 1.5F);
		setRotationAngle(DorsalFin, 0.2618F, 0.0F, 0.0F);
		Body.addChild(DorsalFin);
		DorsalFin.cubeList.add(new ModelBox(DorsalFin, 0, 51, -0.5F, -2.8F, -4.5F, 1, 4, 9, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Body.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase e, float limbSwing, float limbSwingAmount,
			float partialTickTime) {
    	float offset = MathHelper.sin((e.ticksExisted%40 + partialTickTime) / 6.366F)/30;
    	LowerJaw.rotateAngleX = offset/2 + 0.15F;
    	offset += MathHelper.cos(limbSwing * 0.3F) * limbSwingAmount / 7;
    	Tail.rotateAngleY = offset*3;
    	Tail2.rotateAngleY = offset*3;
	}
}