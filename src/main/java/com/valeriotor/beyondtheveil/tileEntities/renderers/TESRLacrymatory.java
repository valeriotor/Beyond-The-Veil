package com.valeriotor.beyondtheveil.tileEntities.renderers;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tileEntities.TileLacrymatory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.animation.FastTESR;

public class TESRLacrymatory extends FastTESR<TileLacrymatory>{
	
	public static final VertexFormat POSITION_TEX_LMAP =
			new VertexFormat()
					.addElement(DefaultVertexFormats.POSITION_3F)
					.addElement(DefaultVertexFormats.TEX_2F)
					.addElement(DefaultVertexFormats.TEX_2S);
	
	private static final ResourceLocation tex = new ResourceLocation(References.MODID, "textures/fluids/simple_icon.png");
	

	@Override
	public void renderTileEntityFast(TileLacrymatory te, double x, double y, double z, float partialTicks,
			int destroyStage, float partial, BufferBuilder buffer) {
        int amount = te.getAmount();
        
        if (amount > 0)
        {
        	int upCombined = getWorld().getCombinedLight(te.getPos().up(), 0);
            int upLMa = upCombined >> 16 & 65535;
            int upLMb = upCombined & 65535;

            buffer.setTranslation(x, y, z);

            BlockModelShapes bm = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
            TextureAtlasSprite still =  bm.getTexture(BlockRegistry.BlockFluidTears.getDefaultState()); 

            double posY = (float) amount / 4000 * 10 / 16 + 0.0625 * 3;
            //buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.pos( 0.0625, posY,  0.0625).color(1, 1, 1, 0.8F).tex(still.getInterpolatedU( 1), still.getInterpolatedV( 1)).lightmap(upLMa, upLMb).endVertex();
            buffer.pos(0.0625 * 15, posY,  0.0625).color(1, 1, 1, 0.8F).tex(still.getInterpolatedU(15), still.getInterpolatedV( 1)).lightmap(upLMa, upLMb).endVertex();
            buffer.pos(0.0625 * 15, posY, 0.0625 * 15).color(1, 1, 1, 0.8F).tex(still.getInterpolatedU(15), still.getInterpolatedV(15)).lightmap(upLMa, upLMb).endVertex();
            buffer.pos( 0.0625, posY, 0.0625 * 15).color(1, 1, 1, 0.8F).tex(still.getInterpolatedU( 1), still.getInterpolatedV(15)).lightmap(upLMa, upLMb).endVertex();
            
        }
		
	}

}
