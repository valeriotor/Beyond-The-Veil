package com.valeriotor.BTV.events;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.valeriotor.BTV.entities.render.RenderTransformedPlayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEvents {
	
	public final Set<EntityPlayer> transformedPlayers = new HashSet();
	private final Set<UUID> covenantPlayers = new HashSet<>();
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
	
	
	@SubscribeEvent
	public void entityViewRenderEvent(RenderTickEvent event) {
		if(Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().isGamePaused()) return;
		if(transformedPlayers.contains(Minecraft.getMinecraft().player))
			Minecraft.getMinecraft().player.eyeHeight = 2;
		else
			Minecraft.getMinecraft().player.eyeHeight = 1.62F;
	}
	
	@SubscribeEvent
	public void onOverlayRenderEvent(RenderWorldLastEvent event) {
		if(Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().isGamePaused() || Minecraft.getMinecraft().player.world == null) return;
		renderCovenantPlayers(event);
	}
	
	public void renderCovenantPlayers(RenderWorldLastEvent event) {
		if(covenantPlayers.isEmpty()) return;
		EntityPlayer p = Minecraft.getMinecraft().player;
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		double d0 = p.lastTickPosX + (p.posX - p.lastTickPosX) * (double)event.getPartialTicks();
        double d1 = p.lastTickPosY + (p.posY - p.lastTickPosY) * (double)event.getPartialTicks();
        double d2 = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * (double)event.getPartialTicks();
        float f = renderManager.playerViewY;
        float f1 = renderManager.playerViewX;
        boolean flag1 = renderManager.options.thirdPersonView == 2;
		for(UUID u : covenantPlayers) {
			EntityPlayer e = p.world.getPlayerEntityByUUID(u);
			if(e == null) continue;
			float dist = (float)Math.sqrt(e.getDistanceSq(p));
            EntityRenderer.drawNameplate(Minecraft.getMinecraft().fontRenderer, e.getName(), 10*(float)(e.posX - d0)/dist, 10*(float)(e.posY - d1)/dist, 10*(float)(e.posZ - d2)/dist, 0, f, f1, flag1, false);
        }
	}
	
}