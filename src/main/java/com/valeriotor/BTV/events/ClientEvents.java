package com.valeriotor.BTV.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.potions.PotionRegistry;
import com.valeriotor.BTV.proxy.ClientProxy;

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
	
	private int sawcleaverCount = 0;
	private KeyBinding binds[] = {
			Minecraft.getMinecraft().gameSettings.keyBindForward,
			Minecraft.getMinecraft().gameSettings.keyBindLeft,
			Minecraft.getMinecraft().gameSettings.keyBindBack,
			Minecraft.getMinecraft().gameSettings.keyBindRight
	};               
	private int soundCounter = 0;
	private int revelationRingCounter = 12000;
	
	@SubscribeEvent
	public void clientTickEvent(ClientTickEvent event) {
		if(event.phase.equals(Phase.END)) {
			EntityPlayerSP p = Minecraft.getMinecraft().player;
			if(!Minecraft.getMinecraft().isGamePaused() && p != null) {
				sawCleaverDodge(p);
				playerAnimationUpdate();
			}
				
			if(soundCounter > 0) {
				soundCounter--;
			}	
			
			if(revelationRingCounter > 0) {
				revelationRingCounter--;
			} else {
				revelationRingCounter = 12000;
				BeyondTheVeil.proxy.renderEvents.cleanseList();
			}
			
			
		}
	}
	
	public void sawCleaverDodge(EntityPlayer p) {
		if(p.getHeldItemMainhand().getItem() == ItemRegistry.saw_cleaver && !p.isInsideOfMaterial(Material.WATER) && !p.isInWater() && !p.isInLava() && !p.capabilities.isFlying) {
			Block block = p.world.getBlockState(p.getPosition().down()).getBlock();
			if(ClientProxy.handler.dodge.isPressed() && sawcleaverCount < 1 && block != Blocks.WATER && block != Blocks.AIR) {
				int conto = 0;
				int direction[] = {-1,-1};
				for(int i = 0; i < 4; i++) {
					if(binds[i].isKeyDown()) {
						conto++;
						if(conto > 0) direction[0] = i;
						if(conto == 2) direction[1] = i;
					}
				}
					for(int i = 0; i < conto && i < 3; i++) {
						this.movePlayer(direction[i], 1 / ((float) conto));
					}
					sawcleaverCount = 0;	
				}  
			}
			if(sawcleaverCount > 0) sawcleaverCount--;
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
	
	public void movePlayer(int direction, float multiplier) {
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
		p.motionX += mX * 2 * multiplier;// *(p.isAirBorne ? 1 : 3);
		p.motionZ += mZ * 2 * multiplier;// *(p.isAirBorne ? 1 : 3);
	}	
	
}
