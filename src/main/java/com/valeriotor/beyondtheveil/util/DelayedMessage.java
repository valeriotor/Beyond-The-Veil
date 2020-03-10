package com.valeriotor.beyondtheveil.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

public class DelayedMessage {
	
	public int ticksToSend;
	public ITextComponent message;
	public EntityPlayer receiver;
	
	public DelayedMessage(int ticksToSend, ITextComponent message, EntityPlayer receiver) {
		this.ticksToSend = ticksToSend;
		this.message = message;
		this.receiver = receiver;
	}
	
	public boolean update() {
		this.ticksToSend--;
		if(this.ticksToSend <= 0) {
			receiver.sendMessage(message);
			return true;
		}
		return false;
	}
	
}
