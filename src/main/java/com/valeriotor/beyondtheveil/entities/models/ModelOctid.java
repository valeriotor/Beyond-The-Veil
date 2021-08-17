package com.valeriotor.beyondtheveil.entities.models;
//Made with Blockbench
//Paste this code into your mod.

import org.lwjgl.opengl.GL11;

import com.valeriotor.beyondtheveil.entities.ictya.EntityMuray;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelOctid extends ModelBase {
	private final ModelRenderer Head;
	private final ModelRenderer TentacleSE;
	private final ModelRenderer TentacleSW;
	private final ModelRenderer TentacleNE;
	private final ModelRenderer TentacleNW;
	private final ModelRenderer TentacleW;
	private final ModelRenderer TentacleN;
	private final ModelRenderer TentacleE;
	private final ModelRenderer TentacleS;

	public ModelOctid() {
		textureWidth = 32;
		textureHeight = 32;

		Head = new ModelRenderer(this);
		Head.setRotationPoint(-0.5F, 16.5F, 0.5F);
		setRotationAngle(Head, 0.5236F, 0.0F, 0.0F);
		Head.cubeList.add(new ModelBox(Head, 0, 0, -2.0F, -4.5F, -3.0F, 5, 5, 5, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 29, 1.5F, -5.5F, -1.0F, 2, 2, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 29, -2.5F, -5.5F, -1.0F, 2, 2, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 6, 30, -1.2F, -2.0F, -3.2F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 6, 30, -1.2F, -2.0F, 1.2F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 6, 30, -2.2F, -2.0F, -2.2F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 6, 30, -2.2F, -2.0F, 0.2F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 6, 30, 1.2F, -2.0F, -3.2F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 6, 30, 1.2F, -2.0F, 1.2F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 6, 30, 2.2F, -2.0F, -2.2F, 1, 1, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 6, 30, 2.2F, -2.0F, 0.2F, 1, 1, 1, 0.0F, false));

		TentacleSE = new ModelRenderer(this);
		TentacleSE.setRotationPoint(-1.4F, 0.5F, 1.4F);
		Head.addChild(TentacleSE);
		TentacleSE.cubeList.add(new ModelBox(TentacleSE, 0, 22, -0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F, false));

		TentacleSW = new ModelRenderer(this);
		TentacleSW.setRotationPoint(2.4F, 0.5F, 1.4F);
		Head.addChild(TentacleSW);
		TentacleSW.cubeList.add(new ModelBox(TentacleSW, 0, 18, -0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F, false));

		TentacleNE = new ModelRenderer(this);
		TentacleNE.setRotationPoint(-1.4F, 0.5F, -2.4F);
		Head.addChild(TentacleNE);
		TentacleNE.cubeList.add(new ModelBox(TentacleNE, 0, 10, -0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F, false));

		TentacleNW = new ModelRenderer(this);
		TentacleNW.setRotationPoint(2.4F, 0.5F, -2.4F);
		Head.addChild(TentacleNW);
		TentacleNW.cubeList.add(new ModelBox(TentacleNW, 0, 14, -0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F, false));

		TentacleW = new ModelRenderer(this);
		TentacleW.setRotationPoint(2.4F, 0.5F, -0.5F);
		Head.addChild(TentacleW);
		TentacleW.cubeList.add(new ModelBox(TentacleW, 4, 14, -0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F, false));

		TentacleN = new ModelRenderer(this);
		TentacleN.setRotationPoint(0.5F, 0.5F, -2.4F);
		Head.addChild(TentacleN);
		TentacleN.cubeList.add(new ModelBox(TentacleN, 4, 10, -0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F, false));

		TentacleE = new ModelRenderer(this);
		TentacleE.setRotationPoint(-1.4F, 0.5F, -0.5F);
		Head.addChild(TentacleE);
		TentacleE.cubeList.add(new ModelBox(TentacleE, 4, 22, -0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F, false));

		TentacleS = new ModelRenderer(this);
		TentacleS.setRotationPoint(0.5F, 0.5F, 1.4F);
		Head.addChild(TentacleS);
		TentacleS.cubeList.add(new ModelBox(TentacleS, 4, 18, -0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Head.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase e, float limbSwing, float limbSwingAmount,
			float partialTickTime) {
		float time = (float) (0.08 * Math.PI * e.ticksExisted + partialTickTime);
		float val = (float) Math.atan(MathHelper.cos(time)) * (limbSwingAmount/3+0.15F);
		float val2 = val / MathHelper.SQRT_2;
		TentacleE.rotateAngleZ = val;
		TentacleW.rotateAngleZ = -val;
		TentacleN.rotateAngleX = -val;
		TentacleS.rotateAngleX = val;
		TentacleNW.rotateAngleX = -val2;
		TentacleNE.rotateAngleX = -val2;
		TentacleSW.rotateAngleX = val2;
		TentacleSE.rotateAngleX = val2;
		TentacleNW.rotateAngleZ = -val2;
		TentacleNE.rotateAngleZ = val2;
		TentacleSW.rotateAngleZ = -val2;
		TentacleSE.rotateAngleZ = val2;
	}
}