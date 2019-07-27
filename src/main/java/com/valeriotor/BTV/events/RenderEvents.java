package com.valeriotor.BTV.events;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.valeriotor.BTV.entities.render.RenderTransformedPlayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEvents {
	
	public final Set<EntityPlayer> transformedPlayers = new HashSet();
	private static final RenderTransformedPlayer deepOne = new RenderTransformedPlayer(Minecraft.getMinecraft().getRenderManager());
	
	
	@SubscribeEvent
	public void onPlayerRenderEvent(RenderPlayerEvent.Pre event) {
		EntityPlayer p = event.getEntityPlayer();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
		if(transformedPlayers.contains(p)) {
	        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
	        double d0 = p.lastTickPosX + (p.posX - p.lastTickPosX) * (double)event.getPartialRenderTick();
	        double d1 = p.lastTickPosY + (p.posY - p.lastTickPosY) * (double)event.getPartialRenderTick();
	        double d2 = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * (double)event.getPartialRenderTick();
	        double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)event.getPartialRenderTick();
	        double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)event.getPartialRenderTick();
	        double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)event.getPartialRenderTick();
			event.setCanceled(true);
			deepOne.render((AbstractClientPlayer)p, d3-d0, d4-d1, d2-d5, p.rotationYaw, event.getPartialRenderTick());
		}
	}
}