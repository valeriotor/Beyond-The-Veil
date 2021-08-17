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

public class ModelJelly extends ModelBase {
	private final ModelRenderer bone;
	private final ModelRenderer T1;
	private final ModelRenderer T3;
	private final ModelRenderer T4;
	private final ModelRenderer T2;

	public ModelJelly() {
		textureWidth = 64;
		textureHeight = 64;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 16, 57, -3.0F, -11.0F, -3.0F, 6, 1, 6, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 12, 59, -2.0F, -12.0F, -2.0F, 4, 1, 4, 0.0F, false));

		T1 = new ModelRenderer(this);
		T1.setRotationPoint(-0.5F, -10.5F, 0.0F);
		setRotationAngle(T1, 0.0F, 0.0F, 0.3491F);
		bone.addChild(T1);
		T1.cubeList.add(new ModelBox(T1, 5, 32, -1.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F, false));

		T3 = new ModelRenderer(this);
		T3.setRotationPoint(-0.5F, -10.5F, 0.0F);
		setRotationAngle(T3, -0.3491F, 0.0F, 0.0F);
		bone.addChild(T3);
		T3.cubeList.add(new ModelBox(T3, 3, 33, 0.0F, 0.0F, -2.0F, 1, 5, 1, 0.0F, false));

		T4 = new ModelRenderer(this);
		T4.setRotationPoint(-0.5F, -10.5F, 0.0F);
		setRotationAngle(T4, 0.3491F, 0.0F, 0.0F);
		bone.addChild(T4);
		T4.cubeList.add(new ModelBox(T4, 5, 32, 0.0F, 0.0F, 1.0F, 1, 5, 1, 0.0F, false));

		T2 = new ModelRenderer(this);
		T2.setRotationPoint(0.5F, -10.5F, 0.0F);
		setRotationAngle(T2, 0.0F, 0.0F, -0.3491F);
		bone.addChild(T2);
		T2.cubeList.add(new ModelBox(T2, 1, 32, 0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bone.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase e, float limbSwing, float limbSwingAmount,
			float partialTickTime) {
		float animTicks = (e.ticksExisted % 200) + partialTickTime;
		float offset = MathHelper.sin(animTicks%40 / 6.366F)/5 * (limbSwingAmount/3+0.15F);
		T1.rotateAngleZ = 0.3491F + offset/2;
		T2.rotateAngleZ = -0.3491F - offset/2;
		T3.rotateAngleX = -0.3491F - offset/2;
		T4.rotateAngleX = 0.3491F + offset/2;
	}
	
}