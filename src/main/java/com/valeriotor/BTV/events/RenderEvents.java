package com.valeriotor.BTV.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.render.RenderTransformedPlayer;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.baubles.MessageRevelationRingToServer;
import com.valeriotor.BTV.util.MathHelper;

import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEvents {
	
	public final Set<EntityPlayer> transformedPlayers = new HashSet();
	public HashMap<String, BlockPos> covenantPlayers = new HashMap();
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
	public void livingRenderEvent(RenderLivingEvent.Pre event) {
		EntityPlayer p = Minecraft.getMinecraft().player;
		if(p == null || Minecraft.getMinecraft().isGamePaused()) return;
		revelationRing(p, event);
	}
	
	
	private List<Integer> invisibleEnts = new ArrayList<>();	
	private void revelationRing(EntityPlayer p, RenderLivingEvent.Pre event) {
		int slot = BaublesApi.isBaubleEquipped(p, ItemRegistry.revelation_ring);
		if(slot == -1) return;
		EntityLivingBase e = event.getEntity();
		if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, slot), 0, false) == 1) {
			if(e.isInvisible()) {
				invisibleEnts.add(e.getEntityId());
				e.setInvisible(false);
			}
		} else {
			if(!invisibleEnts.isEmpty()) {
				cleanseList();
			}
		}
	}
	
	public void cleanseList() {
		invisibleEnts.forEach(i -> BTVPacketHandler.INSTANCE.sendToServer(new MessageRevelationRingToServer(i)));
		invisibleEnts.clear();
	}
	
	public void invisibilificationator(int id) {
		if(Minecraft.getMinecraft().player == null) return;
		Entity e = Minecraft.getMinecraft().player.world.getEntityByID(id);
		if(e != null) e.setInvisible(true);
	}
	
	private List<Integer> glowingEnts = new ArrayList();
	public void glowificator(int id) {
		if(Minecraft.getMinecraft().player == null) return;
		Entity e = Minecraft.getMinecraft().player.world.getEntityByID(id);
		if(e != null) {
			e.setGlowing(true);
			glowingEnts.add(id);
		}
	}
	
	public void deGlowificator() {
		if(Minecraft.getMinecraft().player != null) {
			for(Integer i : glowingEnts) {
				Entity e = Minecraft.getMinecraft().player.world.getEntityByID(i);
				if(e != null) {
					e.setGlowing(false);
				}
			}
		}
		glowingEnts.clear();
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
        BlockPos nearestPos = MathHelper.minimumLookAngle(p.getPosition(), p.getLookVec(), covenantPlayers, Math.PI / 16);
		for(HashMap.Entry<String, BlockPos> e : covenantPlayers.entrySet()) {
			BlockPos pos = e.getValue();
			if(e.getValue() == null) continue;
			float dist = (float)Math.sqrt(p.getDistanceSq(pos)) * 1.2F;
			float offset = 1.5F;
        	if(pos.equals(nearestPos)) {
        		dist *= 1.5;
        		offset += 0.04;
        	}
        	GlStateManager.pushMatrix();
        	//GlStateManager.scale(3, 3, 3);
            drawNameplate(Minecraft.getMinecraft().fontRenderer, e.getKey(), (float)(pos.getX() - d0)/dist, (float)(pos.getY() - d1)/dist + offset, (float)(pos.getZ() - d2)/dist, 0, f, f1, flag1, pos.equals(nearestPos));
            GlStateManager.popMatrix();
		}
	}
	
	
	// Literally copied and pasted from EntityRenderer, only changing the enableDepth call, the isSneaking parameter (to isLooking) and the scale factors
	private static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isLooking)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.0025F, -0.0025F, 0.0025F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int i = fontRendererIn.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)(-i - 1), (double)(-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((double)(-i - 1), (double)(8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((double)(i + 1), (double)(8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((double)(i + 1), (double)(-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();

        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, 553648127);
        

        GlStateManager.depthMask(true);
        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, isLooking ? -1000 : -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();

        GlStateManager.enableDepth();
    }

	
}