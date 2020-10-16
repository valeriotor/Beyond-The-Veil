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

public class ModelManOWar extends ModelBase {
	private final ModelRenderer Head;
	private final ModelRenderer Body;
	private final ModelRenderer Tentacle11;
	private final ModelRenderer Tentacle12;
	private final ModelRenderer Tentacle13;
	private final ModelRenderer Tentacle21;
	private final ModelRenderer Tentacle22;
	private final ModelRenderer Tentacle23;
	private final ModelRenderer Tentacle31;
	private final ModelRenderer Tentacle32;
	private final ModelRenderer Tentacle33;
	private final ModelRenderer Tentacle41;
	private final ModelRenderer Tentacle42;
	private final ModelRenderer Tentacle43;
	private final ModelRenderer MiniTentacle11;
	private final ModelRenderer MiniTentacle12;
	private final ModelRenderer MiniTentacle21;
	private final ModelRenderer MiniTentacle22;
	private final ModelRenderer DorsalFin;

	public ModelManOWar() {
		textureWidth = 64;
		textureHeight = 64;

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, -20.5F, -15.0F);
		Head.cubeList.add(new ModelBox(Head, 0, 11, -6.0F, -0.5F, -11.0F, 12, 1, 17, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 0, -7.0F, 0.0F, 6.0F, 14, 5, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 26, 22, -7.0F, 0.0F, -12.0F, 14, 5, 1, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 28, 0, 6.0F, 0.0F, -11.0F, 1, 5, 17, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 28, 8, -7.0F, 0.0F, -11.0F, 1, 5, 17, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 34, 11, 6.0F, 5.0F, -11.0F, 1, 3, 11, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 8, 13, -7.0F, 5.0F, -11.0F, 1, 3, 11, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 38, 23, -6.0F, 5.0F, -11.0F, 12, 3, 1, 0.0F, false));

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		Head.addChild(Body);
		Body.cubeList.add(new ModelBox(Body, 0, 46, -6.0F, 1.0F, -2.0F, 12, 8, 10, 0.0F, false));

		Tentacle11 = new ModelRenderer(this);
		Tentacle11.setRotationPoint(0.0F, 8.0F, 0.0F);
		setRotationAngle(Tentacle11, 0.1F, 0.0F, 0.0F);
		Body.addChild(Tentacle11);
		Tentacle11.cubeList.add(new ModelBox(Tentacle11, 56, 49, -1.0F, 0.0F, -1.0F, 2, 13, 2, 0.0F, false));

		Tentacle12 = new ModelRenderer(this);
		Tentacle12.setRotationPoint(0.0F, 12.5F, 0.0F);
		setRotationAngle(Tentacle12, 0.3491F, 0.0F, 0.0F);
		Tentacle11.addChild(Tentacle12);
		Tentacle12.cubeList.add(new ModelBox(Tentacle12, 52, 48, -0.5F, -0.5F, -0.5F, 1, 15, 1, 0.0F, false));

		Tentacle13 = new ModelRenderer(this);
		Tentacle13.setRotationPoint(0.0F, 13.0F, 0.0F);
		setRotationAngle(Tentacle13, 0.3491F, 0.0F, 0.0F);
		Tentacle12.addChild(Tentacle13);
		Tentacle13.cubeList.add(new ModelBox(Tentacle13, 44, 37, -0.45F, 0.0F, -0.5F, 1, 26, 1, 0.0F, false));

		Tentacle21 = new ModelRenderer(this);
		Tentacle21.setRotationPoint(-4.0F, 8.0F, 4.0F);
		setRotationAngle(Tentacle21, 0.1F, 0.0F, 0.0F);
		Body.addChild(Tentacle21);
		Tentacle21.cubeList.add(new ModelBox(Tentacle21, 56, 49, -1.0F, 0.0F, -1.0F, 2, 13, 2, 0.0F, false));

		Tentacle22 = new ModelRenderer(this);
		Tentacle22.setRotationPoint(0.0F, 12.5F, 0.0F);
		setRotationAngle(Tentacle22, 0.3491F, 0.0F, 0.0F);
		Tentacle21.addChild(Tentacle22);
		Tentacle22.cubeList.add(new ModelBox(Tentacle22, 52, 48, -0.5F, -0.5F, -0.5F, 1, 15, 1, 0.0F, false));

		Tentacle23 = new ModelRenderer(this);
		Tentacle23.setRotationPoint(0.0F, 13.0F, 0.0F);
		setRotationAngle(Tentacle23, 0.3491F, 0.0F, 0.0F);
		Tentacle22.addChild(Tentacle23);
		Tentacle23.cubeList.add(new ModelBox(Tentacle23, 44, 37, -0.45F, 0.0F, -0.5F, 1, 26, 1, 0.0F, false));

		Tentacle31 = new ModelRenderer(this);
		Tentacle31.setRotationPoint(4.0F, 8.0F, 4.0F);
		setRotationAngle(Tentacle31, 0.1F, 0.0F, 0.0F);
		Body.addChild(Tentacle31);
		Tentacle31.cubeList.add(new ModelBox(Tentacle31, 56, 49, -1.0F, 0.0F, -1.0F, 2, 13, 2, 0.0F, false));

		Tentacle32 = new ModelRenderer(this);
		Tentacle32.setRotationPoint(0.0F, 12.5F, 0.0F);
		setRotationAngle(Tentacle32, 0.3491F, 0.0F, 0.0F);
		Tentacle31.addChild(Tentacle32);
		Tentacle32.cubeList.add(new ModelBox(Tentacle32, 52, 48, -0.5F, -0.5F, -0.5F, 1, 15, 1, 0.0F, false));

		Tentacle33 = new ModelRenderer(this);
		Tentacle33.setRotationPoint(0.0F, 13.0F, 0.0F);
		setRotationAngle(Tentacle33, 0.3491F, 0.0F, 0.0F);
		Tentacle32.addChild(Tentacle33);
		Tentacle33.cubeList.add(new ModelBox(Tentacle33, 44, 37, -0.45F, 0.0F, -0.5F, 1, 26, 1, 0.0F, false));

		Tentacle41 = new ModelRenderer(this);
		Tentacle41.setRotationPoint(-2.0F, 8.0F, 6.0F);
		setRotationAngle(Tentacle41, 0.1F, 0.0F, 0.0F);
		Body.addChild(Tentacle41);
		Tentacle41.cubeList.add(new ModelBox(Tentacle41, 56, 49, -1.0F, 0.0F, -1.0F, 2, 13, 2, 0.0F, false));

		Tentacle42 = new ModelRenderer(this);
		Tentacle42.setRotationPoint(0.0F, 12.5F, 0.0F);
		setRotationAngle(Tentacle42, 0.3491F, 0.0F, 0.0F);
		Tentacle41.addChild(Tentacle42);
		Tentacle42.cubeList.add(new ModelBox(Tentacle42, 52, 48, -0.5F, -0.5F, -0.5F, 1, 15, 1, 0.0F, false));

		Tentacle43 = new ModelRenderer(this);
		Tentacle43.setRotationPoint(0.0F, 13.0F, 0.0F);
		setRotationAngle(Tentacle43, 0.3491F, 0.0F, 0.0F);
		Tentacle42.addChild(Tentacle43);
		Tentacle43.cubeList.add(new ModelBox(Tentacle43, 44, 37, -0.45F, 0.0F, -0.5F, 1, 26, 1, 0.0F, true));

		MiniTentacle11 = new ModelRenderer(this);
		MiniTentacle11.setRotationPoint(4.0F, 8.0F, 1.0F);
		Body.addChild(MiniTentacle11);
		MiniTentacle11.cubeList.add(new ModelBox(MiniTentacle11, 0, 32, 0.0F, 0.5F, -1.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle11.cubeList.add(new ModelBox(MiniTentacle11, 2, 34, 0.0F, 1.5F, -2.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle11.cubeList.add(new ModelBox(MiniTentacle11, 2, 30, -1.0F, 2.5F, -2.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle11.cubeList.add(new ModelBox(MiniTentacle11, 2, 35, -1.0F, 3.5F, -1.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle11.cubeList.add(new ModelBox(MiniTentacle11, 7, 32, 0.0F, 4.5F, -1.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle11.cubeList.add(new ModelBox(MiniTentacle11, 6, 36, 0.0F, 5.5F, -2.0F, 1, 1, 1, 0.0F, false));

		MiniTentacle12 = new ModelRenderer(this);
		MiniTentacle12.setRotationPoint(0.1667F, 6.5F, -1.0F);
		setRotationAngle(MiniTentacle12, 0.1745F, 0.0F, 0.0F);
		MiniTentacle11.addChild(MiniTentacle12);
		MiniTentacle12.cubeList.add(new ModelBox(MiniTentacle12, 4, 34, -0.1667F, 0.0F, 0.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle12.cubeList.add(new ModelBox(MiniTentacle12, 5, 31, -0.1667F, 1.0F, -1.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle12.cubeList.add(new ModelBox(MiniTentacle12, 0, 37, -1.1667F, 2.0F, -1.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle12.cubeList.add(new ModelBox(MiniTentacle12, 6, 30, -1.1667F, 3.0F, 0.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle12.cubeList.add(new ModelBox(MiniTentacle12, 7, 35, -0.1667F, 4.0F, 0.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle12.cubeList.add(new ModelBox(MiniTentacle12, 2, 35, -0.1667F, 5.0F, -1.0F, 1, 1, 1, 0.0F, false));

		MiniTentacle21 = new ModelRenderer(this);
		MiniTentacle21.setRotationPoint(-3.0F, 8.0F, 3.0F);
		Body.addChild(MiniTentacle21);
		MiniTentacle21.cubeList.add(new ModelBox(MiniTentacle21, 4, 32, 0.0F, 0.5F, -1.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle21.cubeList.add(new ModelBox(MiniTentacle21, 0, 35, 0.0F, 1.5F, -2.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle21.cubeList.add(new ModelBox(MiniTentacle21, 4, 36, -1.0F, 2.5F, -2.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle21.cubeList.add(new ModelBox(MiniTentacle21, 0, 30, -1.0F, 3.5F, -1.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle21.cubeList.add(new ModelBox(MiniTentacle21, 0, 37, 0.0F, 4.5F, -1.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle21.cubeList.add(new ModelBox(MiniTentacle21, 7, 35, 0.0F, 5.5F, -2.0F, 1, 1, 1, 0.0F, false));

		MiniTentacle22 = new ModelRenderer(this);
		MiniTentacle22.setRotationPoint(0.1667F, 6.5F, -1.0F);
		setRotationAngle(MiniTentacle22, 0.1745F, 0.0F, 0.0F);
		MiniTentacle21.addChild(MiniTentacle22);
		MiniTentacle22.cubeList.add(new ModelBox(MiniTentacle22, 7, 36, -0.1667F, 0.0F, 0.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle22.cubeList.add(new ModelBox(MiniTentacle22, 4, 36, -0.1667F, 1.0F, -1.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle22.cubeList.add(new ModelBox(MiniTentacle22, 7, 30, -1.1667F, 2.0F, -1.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle22.cubeList.add(new ModelBox(MiniTentacle22, 6, 35, -1.1667F, 3.0F, 0.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle22.cubeList.add(new ModelBox(MiniTentacle22, 0, 35, -0.1667F, 4.0F, 0.0F, 1, 1, 1, 0.0F, false));
		MiniTentacle22.cubeList.add(new ModelBox(MiniTentacle22, 0, 32, -0.1667F, 5.0F, -1.0F, 1, 1, 1, 0.0F, false));

		DorsalFin = new ModelRenderer(this);
		DorsalFin.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(DorsalFin, -0.1745F, 0.0F, 0.0F);
		Head.addChild(DorsalFin);
		DorsalFin.cubeList.add(new ModelBox(DorsalFin, 35, 12, -1.0F, -4.0F, -6.0F, 2, 5, 11, 0.0F, false));
		DorsalFin.cubeList.add(new ModelBox(DorsalFin, 0, 10, -0.5F, -7.0F, -3.0F, 1, 4, 8, 0.0F, false));
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
		float animTicks = (e.ticksExisted % 200) + partialTickTime;
		float offset = MathHelper.sin(animTicks%40 / 6.366F)/5 * (limbSwingAmount/3+0.15F);
    	Tentacle11.rotateAngleX = 0.1F+offset;
    	Tentacle13.rotateAngleX = 0.3491F-offset*3;
    	Tentacle21.rotateAngleX = 0.1F+offset/2;
    	Tentacle23.rotateAngleX = 0.3491F+offset*3;
    	Tentacle31.rotateAngleX = 0.1F-offset;
    	Tentacle33.rotateAngleX = 0.3491F-offset*3;
    	Tentacle41.rotateAngleX = 0.1F-offset/2;
    	Tentacle43.rotateAngleX = 0.3491F+offset*3;
    	Tentacle11.rotateAngleZ = +offset;
    	Tentacle13.rotateAngleZ = -offset*3;
    	Tentacle21.rotateAngleZ = +offset/2;
    	Tentacle23.rotateAngleZ = +offset*3;
    	Tentacle31.rotateAngleZ = -offset;
    	Tentacle33.rotateAngleZ = -offset*3;
    	Tentacle41.rotateAngleZ = -offset/2;
    	Tentacle43.rotateAngleZ = +offset*3;
    	MiniTentacle11.rotateAngleX = -offset;
    	MiniTentacle22.rotateAngleX = -offset;
    	Body.offsetY = offset;
    	offset = MathHelper.sin(animTicks%50 / 7.95F)/16;
    	Tentacle12.rotateAngleX = 0.3491F + offset;
    	Tentacle22.rotateAngleX = 0.3491F-offset;
    	Tentacle32.rotateAngleX = 0.3491F-offset/2;
    	Tentacle42.rotateAngleX = 0.3491F+offset/2;
    	MiniTentacle21.rotateAngleX = -offset;
    	MiniTentacle12.rotateAngleX = offset;
    	Head.offsetY = -offset;
	}
}