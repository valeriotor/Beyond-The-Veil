package com.valeriotor.beyondtheveil.entities.render;

import java.util.function.DoubleUnaryOperator;

import org.lwjgl.opengl.GL11;

import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamFluid;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class RenderDreamFluid extends Render<EntityDreamFluid>{
	protected RenderDreamFluid(RenderManager renderManager) {
		super(renderManager);

	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDreamFluid entity) {
		return null;
	}
	
	@Override
	public void doRender(EntityDreamFluid entity, double x, double y, double z, float entityYaw, float partialTicks) {
		Fluid fluid = FluidRegistry.getFluid(entity.getFluidName());
		if(fluid == null) return;
		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.disableCull();
        GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		BlockModelShapes bm = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
        TextureAtlasSprite still =  bm.getTexture(fluid.getBlock().getDefaultState());
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        int a = 1;
    	double radius = 0.1;
        int existed = entity.ticksExisted;
        RenderHelper.disableStandardItemLighting();
        for(int i = 0; i < entity.getTenths() && i < 10; i+=(++a)) {
        	float xOff = (float) (baseOffsets[a-1][0] + 3*funcs[a-1].applyAsDouble(existed+partialTicks));
        	float yOff = (float) (baseOffsets[a-1][1] - 3*funcs[a-1].applyAsDouble(existed+partialTicks));
        	float zOff = (float) (baseOffsets[a-1][2] + 3*funcs[a&3].applyAsDouble(existed+partialTicks));
        	buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
            buffer.pos(xOff + radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
            buffer.pos(xOff + radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff + radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff + radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
            
            buffer.pos(xOff - radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
            
            buffer.pos(xOff + radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
            buffer.pos(xOff + radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
           
            buffer.pos(xOff + radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
            buffer.pos(xOff + radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
           
            buffer.pos(xOff + radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff + radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
           
            buffer.pos(xOff + radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU( 0), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff - radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV(16)).lightmap(240, 0).endVertex();
            buffer.pos(xOff + radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).tex(still.getInterpolatedU(16), still.getInterpolatedV( 0)).lightmap(240, 0).endVertex();
           tessellator.draw();
        }
        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
	}
	
	
	private static float[][] baseOffsets = {{-0.03F, 0, 0}, {0, 0.03F, 0}, {-0.06F, 0.02F, 0.04F}, {0.03F, 0.06F, 0}};
	private static DoubleUnaryOperator[] funcs = {
												i -> Math.sin(i%40 / 6.366)/20, 
												i -> Math.sin(i%50 / 7.95)/16,
												i -> -Math.sin(i%40 / 6.366)/20, 
												i -> -Math.sin(i%50 / 7.95)/16};
}
