package com.valeriotor.beyondtheveil.entities.models;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.entities.ictya.EntityMuray;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelMuray extends ModelAnimated {
	private final ModelRenderer Tail;
	private final ModelRenderer Tail2;
	private final ModelRenderer Tail3;
	private final ModelRenderer Tail4;
	private final ModelRenderer Tail5;
	private final ModelRenderer Tail6;
	private final ModelRenderer Tail7;
	private final ModelRenderer Head;
	private final ModelRenderer RightMouth;	//  8
	private final ModelRenderer LowerMouth;	//  9
	private final ModelRenderer LeftMouth;	// 10

	public ModelMuray() {
		textureWidth = 32;
		textureHeight = 32;

		Tail = new ModelRenderer(this);
		Tail.setRotationPoint(-0.6F, 13.4F, -16.25F);
		Tail.cubeList.add(new ModelBox(Tail, 14, 1, -1.5F, -1.5F, 0.0F, 3, 3, 6, 0.0F, false));

		Tail2 = new ModelRenderer(this);
		Tail2.setRotationPoint(0.1F, 0.1F, 5.25F);
		Tail.addChild(Tail2);
		Tail2.cubeList.add(new ModelBox(Tail2, 14, 0, -1.5F, -1.5F, 0.0F, 3, 3, 6, 0.0F, false));

		Tail3 = new ModelRenderer(this);
		Tail3.setRotationPoint(0.1F, 0.1F, 4.75F);
		Tail2.addChild(Tail3);
		Tail3.cubeList.add(new ModelBox(Tail3, 13, 0, -1.5F, -1.5F, 0.0F, 3, 3, 6, 0.0F, true));

		Tail4 = new ModelRenderer(this);
		Tail4.setRotationPoint(-0.1F, -0.1F, 5.0F);
		Tail3.addChild(Tail4);
		Tail4.cubeList.add(new ModelBox(Tail4, 13, 1, -1.5F, -1.5F, 0.0F, 3, 3, 6, 0.0F, false));
		Tail4.cubeList.add(new ModelBox(Tail4, 0, 10, -0.5F, -4.0F, 0.0F, 1, 4, 6, 0.0F, false));

		Tail5 = new ModelRenderer(this);
		Tail5.setRotationPoint(-0.1F, -0.1F, 5.0F);
		Tail4.addChild(Tail5);
		Tail5.cubeList.add(new ModelBox(Tail5, 14, 1, -1.5F, -1.5F, 0.0F, 3, 3, 6, 0.0F, true));
		Tail5.cubeList.add(new ModelBox(Tail5, 5, 10, -0.5F, -4.0F, 0.0F, 1, 4, 6, 0.0F, false));

		Tail6 = new ModelRenderer(this);
		Tail6.setRotationPoint(0.1F, 0.1F, 4.25F);
		Tail5.addChild(Tail6);
		Tail6.cubeList.add(new ModelBox(Tail6, 13, 1, -1.5F, -1.5F, 0.0F, 3, 3, 6, 0.0F, false));
		Tail6.cubeList.add(new ModelBox(Tail6, 9, 10, -0.5F, -4.0F, -0.25F, 1, 4, 6, 0.0F, false));

		Tail7 = new ModelRenderer(this);
		Tail7.setRotationPoint(0.1F, 0.1F, 5.0F);
		Tail6.addChild(Tail7);
		Tail7.cubeList.add(new ModelBox(Tail7, 14, 1, -1.5F, -1.5F, 0.0F, 3, 3, 6, 0.0F, false));
		Tail7.cubeList.add(new ModelBox(Tail7, 12, 10, -0.6F, -4.1F, 0.75F, 1, 4, 6, 0.0F, false));

		Head = new ModelRenderer(this);
		Head.setRotationPoint(-1.0F, 13.75F, -14.75F);
		Head.cubeList.add(new ModelBox(Head, 0, 0, -1.0F, -1.75F, -7.25F, 3, 2, 7, 0.0F, true));
		Head.cubeList.add(new ModelBox(Head, 0, 22, 1.5F, -1.0F, -5.25F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 22, -1.5F, -1.0F, -5.25F, 1, 1, 1, 0.0F, false));

		RightMouth = new ModelRenderer(this);
		RightMouth.setRotationPoint(0.0F, 0.0F, 0.0F);
		Head.addChild(RightMouth);
		RightMouth.cubeList.add(new ModelBox(RightMouth, 0, 25, -1.0F, 0.25F, -7.25F, 1, 1, 6, 0.0F, true));

		LowerMouth = new ModelRenderer(this);
		LowerMouth.setRotationPoint(0.0F, 0.0F, 0.0F);
		Head.addChild(LowerMouth);
		LowerMouth.cubeList.add(new ModelBox(LowerMouth, 18, 25, 0.0F, 0.25F, -7.25F, 1, 1, 6, 0.0F, false));

		LeftMouth = new ModelRenderer(this);
		LeftMouth.setRotationPoint(0.0F, 0.0F, 0.0F);
		Head.addChild(LeftMouth);
		LeftMouth.cubeList.add(new ModelBox(LeftMouth, 0, 25, 1.0F, 0.25F, -7.25F, 1, 1, 6, 0.0F, false));
		
		bodyParts.add(Tail);
		bodyParts.add(Tail2);
		bodyParts.add(Tail3);
		bodyParts.add(Tail4);
		bodyParts.add(Tail5);
		bodyParts.add(Tail6);
		bodyParts.add(Tail7);
		bodyParts.add(Head);
		bodyParts.add(RightMouth);
		bodyParts.add(LowerMouth);
		bodyParts.add(LeftMouth);
		setAngles(true);
		fillUpDefaultAngles();
		
	}
	
	@Override
	protected void setAngles(boolean addToDefault) {
		setRotateAngle(Head, 0.0873F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(RightMouth, 0.0F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(LeftMouth, 0.0F, 0.0F, 0.0F, addToDefault);
		setRotateAngle(LowerMouth, 0.0F, 0.0F, 0.0F, addToDefault);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Tail.render(f5);
		Head.render(f5);
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase ent, float limbSwing, float limbSwingAmount,
			float partialTicks) {
		this.setAngles(false);
		if(((EntityMuray)ent).isAttacking())
			setOpenMouth();
		
		float time = (float) (0.08 * Math.PI * (((EntityMuray)ent).ticksExisted + partialTicks));
		for(int i = 0; i < 7; i++) {
			bodyParts.get(i).rotateAngleY = (float) Math.atan(MathHelper.cos(time + (float)(2*i*Math.PI/7))) * (limbSwingAmount/3+0.15F);
		}
		
		Animation mouthAnim = ((EntityMuray)ent).getAttackAnimation();
		if(mouthAnim != null)
			mouthAnim.applyTransformations(bodyParts, partialTicks);
	}
	
	private void setOpenMouth() {
		RightMouth.rotateAngleY = 0.4F;
		LeftMouth.rotateAngleY = -0.4F;
		RightMouth.rotateAngleX = 0.4F;
		LeftMouth.rotateAngleX = 0.4F;
		LowerMouth.rotateAngleX = 0.8F;
	}
	
}