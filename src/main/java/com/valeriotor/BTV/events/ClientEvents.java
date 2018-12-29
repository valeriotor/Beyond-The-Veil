package com.valeriotor.BTV.events;

import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.proxy.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEvents {
	
	private int lastKeyPressed = -1;
	private int count = 0;
	private KeyBinding binds[] = {
			Minecraft.getMinecraft().gameSettings.keyBindForward,
			Minecraft.getMinecraft().gameSettings.keyBindLeft,
			Minecraft.getMinecraft().gameSettings.keyBindBack,
			Minecraft.getMinecraft().gameSettings.keyBindRight
	};
	
	@SubscribeEvent
	public void clientTickEvent(ClientTickEvent event) {
		if(event.phase.equals(Phase.END)) {
			if(!Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().player != null) {
			if(Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() == ItemRegistry.saw_cleaver) {
				if(ClientProxy.handler.dodge.isPressed() && count > 4) {
					int conto = 0;
					int direction[] = {-1,-1};
					for(int i = 0; i < 4; i++) {
						if(binds[i].isKeyDown()) {
							conto++;
							if(conto == 1) direction[0] = i;
							if(conto == 2) direction[1] = i;
						}
					}
					for(int i = 0; i < conto && i < 3; i++) {
						this.movePlayer(direction[i], 1 / ((float) conto));
					}
					count = 0;
					
				}
				
				
			}
			if(count < 5) {
				count++;
			}
		}
		}
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
		p.motionX = mX * 2 * multiplier;// *(p.isAirBorne ? 1 : 3);
		p.motionZ = mZ * 2 * multiplier;// *(p.isAirBorne ? 1 : 3);
	}
	
	
}
