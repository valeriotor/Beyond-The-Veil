package com.valeriotor.beyondtheveil.tileEntities.renderers;

import com.valeriotor.beyondtheveil.tileEntities.TileMegydrea;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fluids.Fluid;

public class TESRMegydrea extends FastTESR<TileMegydrea>{
	// 4 - 6 
	// 11 - 26
	private static final double min = 0.0625*4;
	private static final double max = 0.0625*12;
	private static final double minHeight = 0.0625*6;
	private static final double maxHeight = 0.0625*26;
	private static final double height = maxHeight - minHeight;
	@Override
	public void renderTileEntityFast(TileMegydrea te, double x, double y, double z, float partialTicks,
			int destroyStage, float partial, BufferBuilder buffer) {
		Fluid f = te.getFluid();
		if(f == null) return;
        buffer.setTranslation(x, y, z);
		double height = te.getAmount()*this.height/40000;
		int upCombined = getWorld().getCombinedLight(te.getPos().up(), 0);
        int upLMa = upCombined >> 16 & 65535;
        int upLMb = upCombined & 65535;
		BlockModelShapes bm = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
        TextureAtlasSprite tex =  bm.getTexture(f.getBlock().getDefaultState());
        
        buffer.pos(max, minHeight + height, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(max, minHeight + height, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(max, minHeight, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(max, minHeight, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        
        buffer.pos(min, minHeight + height, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight + height, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        
        buffer.pos(max, minHeight + height, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(max, minHeight + height, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight + height, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight + height, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
       
        buffer.pos(max, minHeight, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(max, minHeight, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
       
        buffer.pos(max, minHeight + height, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight + height, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(max, minHeight, max).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
       
        buffer.pos(max, minHeight + height, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight + height, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(min, minHeight, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(max, minHeight, min).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
       
		
	}

}
