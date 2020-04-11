package com.valeriotor.beyondtheveil.entities.render;

import java.util.function.IntToDoubleFunction;

import org.lwjgl.opengl.GL11;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamFluid;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class RenderDreamFluid extends Render<EntityDreamFluid>{
	
	protected RenderDreamFluid(RenderManager renderManager) {
		super(renderManager);
	}

	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/fluids/tears_still.png");
	@Override
	protected ResourceLocation getEntityTexture(EntityDreamFluid entity) {
		/*String s = entity.getFluidName();
		if(!s.equals("null")) {
			Fluid f = FluidRegistry.getFluid(s);
			if(f != null) {
				//ResourceLocation location = new ResourceLocation(f.getStill().getResourceDomain(), "textures/" +f.getStill().getResourcePath().concat(".png"));
				return location;
			}
		}*/
		return texture;
	}
	
	@Override
	public void doRender(EntityDreamFluid entity, double x, double y, double z, float entityYaw, float partialTicks) {
		Fluid fluid = FluidRegistry.getFluid(entity.getFluidName());
		if(fluid == null) return;
		GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        int color = fluid.getColor();
        ResourceLocation r = fluid.getStill();
        this.bindTexture(new ResourceLocation(r.getResourceDomain(), "textures/" + r.getResourcePath() + ".png"));
        BlockModelShapes bm = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
        TextureAtlasSprite tex =  bm.getTexture(fluid.getBlock().getDefaultState());
        
        //System.out.println(tex.getIconHeight() + " " + tex.getIconWidth());
        //Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(name, texture)
        //Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(fluid.getBlock().getDefaultState());
        //System.out.println((color >> 16) + " " + ((color >> 8) & 255) + " " + (color & 255));
        ModelRegistry.dream_fluid.render(entity, 0, 0, 0, 0, 0, 0.06F);
        /*GlStateManager.disableCull();
        GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);
        this.bindTexture(texture);
		BlockModelShapes bm = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
        TextureAtlasSprite tex =  bm.getTexture(BlockRegistry.BlockCurtain.getDefaultState());
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        int a = 1;
    	double radius = 0.0625;
        int existed = entity.ticksExisted;
        for(int i = 0; i < entity.getTenths() && i < 10; i+=(++a)) {
        	float xOff = (float) (baseOffsets[a-1][0] + 2*funcs[a-1].applyAsDouble(existed));
        	float yOff = (float) (baseOffsets[a-1][1] - 2*funcs[a-1].applyAsDouble(existed));
        	float zOff = (float) (baseOffsets[a-1][2] + 2*funcs[a-1].applyAsDouble(existed));
        	buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(xOff + radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff + radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff + radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff + radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
            
            buffer.pos(xOff - radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
            
            buffer.pos(xOff + radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff + radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
           
            buffer.pos(xOff + radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff + radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
           
            buffer.pos(xOff + radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff + radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).endVertex();
           
            buffer.pos(xOff + radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
            buffer.pos(xOff + radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).endVertex();
           tessellator.draw();
        }
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();*/
        GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	private static float[][] baseOffsets = {{-0.03F, 0, 0}, {0, 0.03F, 0}, {-0.06F, 0.02F, 0.04F}, {0.03F, 0.06F, 0}};
	private static IntToDoubleFunction[] funcs = {
												i -> Math.sin(i%40 / 6.366)/20, 
												i -> Math.sin(i%50 / 7.95)/16,
												i -> -Math.sin(i%40 / 6.366)/20, 
												i -> -Math.sin(i%50 / 7.95)/16};
}
