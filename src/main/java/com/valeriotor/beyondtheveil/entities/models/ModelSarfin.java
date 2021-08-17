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

public class ModelSarfin extends ModelBase {
	private final ModelRenderer bone;
	private final ModelRenderer Head;
	private final ModelRenderer Fin;
	private final ModelRenderer Tail;

	public ModelSarfin() {
		textureWidth = 32;
		textureHeight = 32;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 0, 0, -2.0F, -13.0F, -5.0F, 4, 4, 11, 0.0F, false));

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 8, 8, -2.0F, -13.5F, -8.0F, 4, 4, 3, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 0, -2.5F, -12.0F, -8.5F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 0, 1.5F, -12.0F, -8.5F, 1, 1, 1, 0.0F, false));

		Fin = new ModelRenderer(this);
		Fin.setRotationPoint(0.0F, -13.0F, -1.5F);
		setRotationAngle(Fin, -0.4363F, 0.0F, 0.0F);
		bone.addChild(Fin);
		Fin.cubeList.add(new ModelBox(Fin, 0, 15, -0.5F, -4.5F, -2.5F, 1, 6, 5, 0.0F, false));

		Tail = new ModelRenderer(this);
		Tail.setRotationPoint(0.0F, -11.0F, 6.0F);
		bone.addChild(Tail);
		Tail.cubeList.add(new ModelBox(Tail, 6, 6, -1.5F, -1.5F, -0.3F, 3, 3, 6, 0.0F, false));
		Tail.cubeList.add(new ModelBox(Tail, 0, 19, -0.5F, -3.0F, 5.7F, 1, 6, 2, 0.0F, false));
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
    	float offset = MathHelper.sin((e.ticksExisted%40 + partialTickTime) / 6.366F)/30;
    	offset += MathHelper.cos(limbSwing * 0.3F) * limbSwingAmount / 7;
    	Tail.rotateAngleY = offset*3;
	}
}