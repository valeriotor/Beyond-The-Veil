package com.valeriotor.beyondtheveil.tileEntities.renderers;

import org.lwjgl.opengl.GL11;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.tileEntities.TileHeart;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.animation.FastTESR;

public class TESRHeart extends FastTESR<TileHeart>{
	
	public static final VertexFormat POSITION_TEX_LMAP =
			new VertexFormat()
					.addElement(DefaultVertexFormats.POSITION_3F)
					.addElement(DefaultVertexFormats.TEX_2F)
					.addElement(DefaultVertexFormats.TEX_2S);
	private static final double height = 0.0625 * 2;
	
	
	@Override
	public void renderTileEntityFast(TileHeart te, double x, double y, double z, float partialTicks, int destroyStage,
			float partial, BufferBuilder buffer) {
		int counter = te.getAnimCounter();
		int upCombined = getWorld().getCombinedLight(te.getPos().up(), 0);
        int upLMa = upCombined >> 16 & 65535;
        int upLMb = upCombined & 65535;
        buffer.setTranslation(x, y, z);
		BlockModelShapes bm = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
        TextureAtlasSprite tex =  bm.getTexture(BlockRegistry.BlockHeart.getDefaultState());
        double radius = 0.05 + 0.15625 / 120 * (Math.abs(counter - 15 + partialTicks) + 3.5);
        buffer.pos(0.4675 + radius, height + radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 + radius, height + radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 + radius, height - radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 + radius, height - radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        
        buffer.pos(0.4675 - radius, height + radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height + radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height - radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height - radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        
        buffer.pos(0.4675 + radius, height + radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 + radius, height + radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height + radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height + radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
       
        buffer.pos(0.4675 + radius, height - radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 + radius, height - radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height - radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height - radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
       
        buffer.pos(0.4675 + radius, height + radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height + radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height - radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 + radius, height - radius, 0.5325 + radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
       
        buffer.pos(0.4675 + radius, height + radius, 0.535 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height + radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 - radius, height - radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(3), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
        buffer.pos(0.4675 + radius, height - radius, 0.5325 - radius).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(4)).lightmap(upLMa, upLMb).endVertex();
       
        int well = te.getWellCounter();
        if(well > -1) {
	        tex = bm.getTexture(BlockRegistry.BlockBloodWell.getDefaultState());
	        
	        buffer.pos(-1, (60 - well + partialTicks) / 64D, -1).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
	        buffer.pos(2, (60 - well + partialTicks) / 64D, -1).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(16), tex.getInterpolatedV(0)).lightmap(upLMa, upLMb).endVertex();
	        buffer.pos(2, (60 - well + partialTicks) / 64D, 2).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(16), tex.getInterpolatedV(16)).lightmap(upLMa, upLMb).endVertex();
	        buffer.pos(-1, (60 - well + partialTicks) / 64D, 2).color(1, 1, 1, 1F).tex(tex.getInterpolatedU(0), tex.getInterpolatedV(16)).lightmap(upLMa, upLMb).endVertex();
        }
	}

}
