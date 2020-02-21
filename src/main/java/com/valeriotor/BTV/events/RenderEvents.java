package com.valeriotor.BTV.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.google.common.base.MoreObjects;
import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.render.RenderParasite;
import com.valeriotor.BTV.entities.render.RenderTransformedPlayer;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.baubles.MessageRevelationRingToServer;
import com.valeriotor.BTV.util.CameraRotatorClient;
import com.valeriotor.BTV.util.MathHelperBTV;

import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderHandEvent;
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
	public final Set<EntityPlayer> parasitePlayers = new HashSet();
	public HashMap<String, BlockPos> covenantPlayers = new HashMap();
	private static final RenderTransformedPlayer deepOne = new RenderTransformedPlayer(Minecraft.getMinecraft().getRenderManager());
	private static final RenderParasite parasite = new RenderParasite(Minecraft.getMinecraft().getRenderManager());
	private final Minecraft mc;
	private final RenderManager renderManager;
	
	public RenderEvents() {
		this.mc = Minecraft.getMinecraft();
		this.renderManager = mc.getRenderManager();
	}
	
	@SubscribeEvent
	public void onPlayerRenderEvent(RenderPlayerEvent.Pre event) {
		EntityPlayer p = event.getEntityPlayer();
		if(transformedPlayers.contains(p)) {
			GlStateManager.enableBlend();
			GlStateManager.disableAlpha();
			GlStateManager.blendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
	        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
	        double d0 = p.lastTickPosX + (p.posX - p.lastTickPosX) * (double)event.getPartialRenderTick();
	        double d1 = p.lastTickPosY + (p.posY - p.lastTickPosY) * (double)event.getPartialRenderTick();
	        double d2 = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * (double)event.getPartialRenderTick();
	        double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)event.getPartialRenderTick();
	        double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)event.getPartialRenderTick();
	        double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)event.getPartialRenderTick();
			event.setCanceled(true);
			deepOne.render((AbstractClientPlayer)p, d3-d0, d4-d1, d2-d5, p.rotationYaw, event.getPartialRenderTick());
		} else if(parasitePlayers.contains(p)){
	        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
	        double d0 = p.lastTickPosX + (p.posX - p.lastTickPosX) * (double)event.getPartialRenderTick();
	        double d1 = p.lastTickPosY + (p.posY - p.lastTickPosY) * (double)event.getPartialRenderTick();
	        double d2 = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * (double)event.getPartialRenderTick();
	        double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)event.getPartialRenderTick();
	        double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)event.getPartialRenderTick();
	        double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)event.getPartialRenderTick();
			parasite.render((AbstractClientPlayer)p, d3-d0, d4-d1 + 0.07, d2-d5, p.rotationYaw, event.getPartialRenderTick());
	         if(p.canRenderOnFire()) {
	        	 for(int i = 0; i < 9; i++) {
	        		 double sin = Math.sin(p.rotationYaw * Math.PI / 180);
	        		 double cos = Math.cos(p.rotationYaw * Math.PI / 180);
	        		 p.world.spawnParticle(EnumParticleTypes.FLAME, p.posX - cos / 2.5 + cos * (i%3) / 7, p.posY + 1.55, p.posZ - sin / 2.5 + sin * (i/3) / 7, 0, 0, 0);
	        	 }
	        } else {
	        	p.world.spawnParticle(EnumParticleTypes.REDSTONE, p.posX - Math.cos(p.rotationYaw * Math.PI / 180) / 5, p.posY + 1.5, p.posZ - Math.sin(p.rotationYaw * Math.PI / 180) / 5, 0.5, 0.03, 0);
	        }
		}
	}
	
	
	@SubscribeEvent
	public void entityViewRenderEvent(RenderTickEvent event) {
		if(Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().isGamePaused()) return;
		CameraRotatorClient cr = BeyondTheVeil.proxy.cEvents.cameraRotator;
		if(cr != null) {
			Minecraft.getMinecraft().player.rotationYaw = cr.getYaw(event.renderTickTime);
			Minecraft.getMinecraft().player.rotationPitch = cr.getPitch(event.renderTickTime);
		}
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
		if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, slot), 1, false) == 1) {
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
		if(!Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().player != null)
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
        BlockPos nearestPos = MathHelperBTV.minimumLookAngle(p.getPosition(), p.getLookVec(), covenantPlayers, Math.PI / 16);
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

	@SubscribeEvent
	public void renderHand(RenderHandEvent event) {
		if(Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)
				&& mc.gameSettings.thirdPersonView == 0) {
			event.setCanceled(true);
	        AbstractClientPlayer abstractclientplayer = mc.player;
	        float partialTicks = event.getPartialTicks();
	        float f = 1.0F;
	        float p_187456_2_ = abstractclientplayer.getSwingProgress(partialTicks);
	        float p_187456_1_ = 0;
	        float f1 = MathHelper.sqrt(p_187456_2_);
	        float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
	        float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
	        float f4 = -0.4F * MathHelper.sin(p_187456_2_ * (float)Math.PI);
	        GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + p_187456_1_ * -0.6F, f4 + -0.71999997F);
	        GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
	        float f5 = MathHelper.sin(p_187456_2_ * p_187456_2_ * (float)Math.PI);
	        float f6 = MathHelper.sin(f1 * (float)Math.PI);
	        GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(RenderTransformedPlayer.deepOneTexture);
	        GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);
	        GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
	        GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
	        GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);
	        GlStateManager.disableCull();		
	        
	        deepOne.renderRightArm(abstractclientplayer);
	        
	        GlStateManager.enableCull();
	        
		}
	}
	
}