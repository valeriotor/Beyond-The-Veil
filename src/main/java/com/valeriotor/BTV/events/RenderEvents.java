package com.valeriotor.BTV.events;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.valeriotor.BTV.entities.render.RenderTransformedPlayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
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
		double x = p.posX - Minecraft.getMinecraft().player.posX;
		double y = p.posY - Minecraft.getMinecraft().player.posY;
		double z = p.posZ - Minecraft.getMinecraft().player.posZ;
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
		// Only do this for transformed players
		// NOTE: If player is in first person and event.player == Minecraft.getMC.player, then don't render!!
		if(transformedPlayers.contains(p)) {
			event.setCanceled(true);
			deepOne.render(p, (double)-x, (double)-y, (double)z, p.rotationYaw, event.getPartialRenderTick());
		}
	}
}