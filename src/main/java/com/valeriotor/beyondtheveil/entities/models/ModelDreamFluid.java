package com.valeriotor.beyondtheveil.entities.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntToDoubleFunction;

import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamFluid;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModelDreamFluid extends ModelBase{
	public ModelRenderer part0;
	public ModelRenderer part1;
	public ModelRenderer part2;
	public ModelRenderer part3;
	public ModelRenderer part4;
	public ModelRenderer part5;
	public ModelRenderer part6;
	public ModelRenderer part7;
	public ModelRenderer part8;
	public ModelRenderer part9;
	public List<ModelRenderer> parts = new ArrayList<>();
	
	public ModelDreamFluid() {
		this.textureWidth = 16;
		this.textureHeight = 512;
		this.part0 = new ModelRenderer(this, 0, 0);
		this.part0.setRotationPoint(0, 24, 0);
		this.part0.addBox(0, 0, 0, 1, 1, 1);
		this.part1 = new ModelRenderer(this, 0, 0);
		this.part1.setRotationPoint(1, 23, 0);
		this.part1.addBox(0, 0, 0, 1, 1, 1);
		this.part2 = new ModelRenderer(this, 0, 0);
		this.part2.setRotationPoint(-1, 21, 1);
		this.part2.addBox(0, 0, 0, 1, 1, 1);
		this.part3 = new ModelRenderer(this, 0, 0);
		this.part3.setRotationPoint(1, 23, 2);
		this.part3.addBox(0, 0, 0, 1, 1, 1);
		this.part4 = new ModelRenderer(this, 0, 0);
		this.part4.setRotationPoint(-1, 24, 1);
		this.part4.addBox(0, 0, 0, 1, 1, 1);
		this.part5 = new ModelRenderer(this, 0, 0);
		this.part5.setRotationPoint(3, 21, 0);
		this.part5.addBox(0, 0, 0, 1, 1, 1);
		this.part6 = new ModelRenderer(this, 0, 0);
		this.part6.setRotationPoint(1, 25, 2);
		this.part6.addBox(0, 0, 0, 1, 1, 1);
		this.part7 = new ModelRenderer(this, 0, 0);
		this.part7.setRotationPoint(2, 23, -3);
		this.part7.addBox(0, 0, 0, 1, 1, 1);
		this.part8 = new ModelRenderer(this, 0, 0);
		this.part8.setRotationPoint(0, 24, -2);
		this.part8.addBox(0, 0, 0, 1, 1, 1);
		this.part9 = new ModelRenderer(this, 0, 0);
		this.part9.setRotationPoint(1, 24, -1);
		this.part9.addBox(0, 0, 0, 1, 1, 1);
		this.parts.add(part0);
		this.parts.add(part1);
		this.parts.add(part2);
		this.parts.add(part3);
		this.parts.add(part4);
		this.parts.add(part5);
		this.parts.add(part6);
		this.parts.add(part7);
		this.parts.add(part8);
		this.parts.add(part9);
	}
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale) {
		if(entityIn instanceof EntityDreamFluid) {
			byte a = ((EntityDreamFluid)entityIn).getTenths();
			for(byte i = 0; i < a && i < 10; i++) {
				ModelRenderer part = this.parts.get(i);
				part.offsetX = (float) funcs[i&3].applyAsDouble(entityIn.ticksExisted);
				part.offsetY = -(float) funcs[i&3].applyAsDouble(entityIn.ticksExisted) - 1;
				part.offsetZ = (float) funcs[(i+1)&3].applyAsDouble(entityIn.ticksExisted);
				this.parts.get(i).render(scale);
			}
		}
	}
	
	private static IntToDoubleFunction[] funcs = {
			i -> Math.sin(i%40 / 6.366)/20, 
			i -> Math.sin(i%50 / 7.95)/16,
			i -> -Math.sin(i%40 / 6.366)/20, 
			i -> -Math.sin(i%50 / 7.95)/16};
	
}
