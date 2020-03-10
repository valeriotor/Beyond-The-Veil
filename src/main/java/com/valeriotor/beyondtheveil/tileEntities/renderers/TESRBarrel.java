package com.valeriotor.beyondtheveil.tileEntities.renderers;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tileEntities.TileBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.animation.FastTESR;

public class TESRBarrel extends FastTESR<TileBarrel>{


	private static final TextureAtlasSprite tex = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("beyondtheveil:blocks/barrel_top");
	@Override
	public void renderTileEntityFast(TileBarrel te, double x, double y, double z, float partialTicks, int destroyStage,
			float partial, BufferBuilder buffer) {
		int count = te.getFishCount();
		if(count > 0) {
			int upCombined = getWorld().getCombinedLight(te.getPos().up(), 0);
            int upLMa = upCombined >> 16 & 65535;
            int upLMb = upCombined & 65535;

            buffer.setTranslation(x, y, z);
	
            TextureAtlasSprite still = tex;
            int a, b;
            switch(te.getFishType()) {
			case COD: 		a = 0;
							b = 0;
							break;
			case SLUGS: 	a = 5;
							b = 0;
							break;
			case PUFFERFISH:a = 10;
						 	b = 0;
						 	break;
			case CLOWNFISH: a = 0;
							b = 5;
							break;
			case SALMON: 	a = 5;
							b = 5;
							break;
			default: 		a = 0;
							b = 0;
            }
            
            if(still != null) {
            	double i = 0.0625;
            	double posY = 0.0625 + count / (double)TileBarrel.MAX_COUNT * 0.75;
            	buffer.pos( i * 3, posY,  i * 3).color(1, 1, 1, 0.8F).tex(still.getInterpolatedU( a), still.getInterpolatedV( b)).lightmap(upLMa, upLMb).endVertex();
                buffer.pos(i * 13, posY,  i * 3).color(1, 1, 1, 0.8F).tex(still.getInterpolatedU(a + 5), still.getInterpolatedV( b)).lightmap(upLMa, upLMb).endVertex();
                buffer.pos(i * 13, posY, i * 13).color(1, 1, 1, 0.8F).tex(still.getInterpolatedU(a + 5), still.getInterpolatedV(b + 5)).lightmap(upLMa, upLMb).endVertex();
                buffer.pos( i * 3, posY, i * 13).color(1, 1, 1, 0.8F).tex(still.getInterpolatedU( a), still.getInterpolatedV(b + 5)).lightmap(upLMa, upLMb).endVertex();
                
            }
		}
	}

}
