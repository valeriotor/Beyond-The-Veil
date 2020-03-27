package com.valeriotor.beyondtheveil.events;

import java.util.HashMap;
import java.util.Map;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.dweller.DwellerDialogue;
import com.valeriotor.beyondtheveil.gui.GuiDialogueDweller;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageSawCleaverToServer;
import com.valeriotor.beyondtheveil.proxy.ClientProxy;
import com.valeriotor.beyondtheveil.util.CameraRotatorClient;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEvents {
	
	public final Map<EntityPlayer, Animation> playerAnimations = new HashMap<>();
	public CameraRotatorClient cameraRotator = null;
	
	private int sawcleaverCount = 0;
	private KeyBinding binds[] = {
			Minecraft.getMinecraft().gameSettings.keyBindForward,
			Minecraft.getMinecraft().gameSettings.keyBindLeft,
			Minecraft.getMinecraft().gameSettings.keyBindBack,
			Minecraft.getMinecraft().gameSettings.keyBindRight
	};               
	private int soundCounter = 0;
	private int revelationRingCounter = 12000;
	private int wolfMedallionCounter = -1;
	/**A counter for generic ticking animations */
	private int animationCounter = 0;
	private int genericCounter = 0;
	
	@SubscribeEvent
	public void clientTickEvent(ClientTickEvent event) {
		if(event.phase.equals(Phase.END)) {
			EntityPlayerSP p = Minecraft.getMinecraft().player;
			if(!Minecraft.getMinecraft().isGamePaused() && p != null) {
				sawCleaverDodge(p);
				playerAnimationUpdate();
				updateAnimationCounter();
			}
				
			if(soundCounter > 0) {
				soundCounter--;
			}	
			
			updateRevelationRing();
			updateWolfMedallion();
			if(cameraRotator != null)
				if(cameraRotator.update())
					cameraRotator = null;
			
			this.genericCounter++;
			this.genericCounter &= 32767;
			if((this.genericCounter & 255) == 0) {
				if(!(Minecraft.getMinecraft().currentScreen instanceof GuiDialogueDweller)) {
					DwellerDialogue.removeInstance();
				}
			}
		}
	}
	
	public void startWolfMedallionCounter() {
		this.wolfMedallionCounter = 20 * 8;
	}
	
	public void sawCleaverDodge(EntityPlayer p) {
		if(ClientProxy.handler.dodge.isPressed()) 
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSawCleaverToServer());
	}
	
	public void playerAnimationUpdate() {
		playerAnimations.entrySet().forEach(e -> e.getValue().update());
		playerAnimations.entrySet().removeIf(e -> e.getValue().isDone());
	}
	
	
	@SubscribeEvent
	public void soundEvent(PlaySoundEvent event) {
		if(this.soundCounter > 0) {
			event.setResultSound(null);
		}
	}
	
	public void muteSounds(int ticks) {
		Minecraft.getMinecraft().getSoundHandler().stopSounds();
		this.soundCounter = ticks;
	}
	
	public void movePlayer() {
		int conto = 0;
		int direction[] = {-1,-1};
		boolean[] binds = this.getArrowKeys();
		for(int i = 0; i < 4; i++) {
			if(binds[i]) {
				conto++;
				if(conto > 0) direction[0] = i;
				if(conto == 2) direction[1] = i;
			}
		}
		for(int i = 0; i < conto && i < 3; i++) {
			movePlayer_internal(direction[i], 1 / ((float) conto));
		}
	}
	
	private void movePlayer_internal(int direction, float multiplier) {
		EntityPlayer p = Minecraft.getMinecraft().player;
		float mX = (float) -Math.sin(p.rotationYawHead*2*Math.PI/360);
		float mZ = (float) Math.cos(p.rotationYawHead*2*Math.PI/360);
		float tmp = mX;
		switch(direction) {
		case 0: break;
		case 1:	
			mX = mZ;
			mZ = -tmp;
			break;
		case 2:
			mX = -mX;
			mZ = -mZ;
			break;
		case 3:
			mX = -mZ;
			mZ = tmp;
		}
		p.motionX += mX * 1.5 * multiplier;// *(p.isAirBorne ? 1 : 3);
		p.motionZ += mZ * 1.5 * multiplier;// *(p.isAirBorne ? 1 : 3);
	}	
	
	private void updateRevelationRing() {
		if(revelationRingCounter > 0) {
			revelationRingCounter--;
		} else {
			revelationRingCounter = 12000;
			BeyondTheVeil.proxy.renderEvents.cleanseList();
		}
	}
	
	private void updateWolfMedallion() {
		if(wolfMedallionCounter > 0) {
			wolfMedallionCounter--;
			if(wolfMedallionCounter == 0) {
				wolfMedallionCounter--;
				BeyondTheVeil.proxy.renderEvents.deGlowificator();
			}
		}
	}
	
	private void updateAnimationCounter() {
		this.animationCounter++;
		this.animationCounter &= 1023;
	}
	
	public int getAnimationCounter() {
		return this.animationCounter;
	}
	
	public boolean[] getArrowKeys() {
		return new boolean[] {binds[0].isKeyDown(), binds[1].isKeyDown(), binds[2].isKeyDown(), binds[3].isKeyDown()};
	}
	
}
