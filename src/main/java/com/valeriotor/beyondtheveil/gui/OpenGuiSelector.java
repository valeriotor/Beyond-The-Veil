package com.valeriotor.beyondtheveil.gui;

import java.util.function.Supplier;

import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageActivateBauble;
import com.valeriotor.beyondtheveil.network.MessageActivatePower;
import com.valeriotor.beyondtheveil.proxy.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class OpenGuiSelector {
	
	private final Minecraft mc;
	
	public OpenGuiSelector(Minecraft mc) {
		this.mc = mc;
	}
	
	@SubscribeEvent
	public void inputRead(ClientTickEvent event) {
		if(event.phase.equals(Phase.END)) {
			EntityPlayerSP p = Minecraft.getMinecraft().player;
			if(!this.mc.isGamePaused() && p != null && this.mc.currentScreen == null) {
				for(GuiType type : GuiType.values()) {
					this.doCheck(type);
				}
			}
				
		}
	}
	
	private void doCheck(GuiType type) {
		if(type.getKey().isKeyDown()) {
			type.timePressed++;
			if(type.timePressed >= 15) {
				type.timePressed = 0;
				this.mc.displayGuiScreen(type.getGui());
			}
		} else {
			if(type.timePressed >= 1) {
				type.timePressed = 0;
				BTVPacketHandler.INSTANCE.sendToServer(type.getMessage());
			}
		}
	}
	
	private enum GuiType{
		ACTIVATEBAUBLE(ClientProxy.handler.activeBauble, GuiActiveBauble::new, MessageActivateBauble::new),
		ACTIVATEPOWER(ClientProxy.handler.power, GuiDeityPowers::new, MessageActivatePower::new);
		
		private final KeyBinding key;
		private final Supplier<GuiScreen> guiSupplier;
		private final Supplier<IMessage> messageSupplier;
		public int timePressed = 0;
		
		private GuiType(KeyBinding key, Supplier<GuiScreen> guiSupplier, Supplier<IMessage> messageSupplier) {
			this.key = key;
			this.guiSupplier = guiSupplier;
			this.messageSupplier = messageSupplier;
		}
		
		public KeyBinding getKey() {return this.key;}
		public GuiScreen getGui() {return this.guiSupplier.get();}
		public IMessage getMessage() {return this.messageSupplier.get();}
	}

}
