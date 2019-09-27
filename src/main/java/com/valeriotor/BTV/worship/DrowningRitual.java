package com.valeriotor.BTV.worship;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.ritual.MessagePerformHurtAnimation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

public class DrowningRitual {
	
	public Phase phase = Phase.START;
	private int progress = 320;
	public boolean greatDreamer = false;
	public boolean ancientGods = false;
	public boolean progressing = true;
	private final EntityPlayer p;
	
	public DrowningRitual(EntityPlayer p) {
		this.p = p;
	}
	
	public void update() {
		//System.out.println("Updating..");
		if(this.progressing) {
			//System.out.println("Progressing..");
			this.progress--;
			if((this.progress & 31) == 0 && this.phase == phase.START && this.progress > 0 && p.getHealth() > this.progress / 32) {
				//System.out.println("Damaging..");
				this.p.setHealth(this.progress/32);
				BTVPacketHandler.INSTANCE.sendTo(new MessagePerformHurtAnimation(MessagePerformHurtAnimation.DROWN), (EntityPlayerMP)p);
			}
			if(this.progress == 0) {
				if(this.phase == Phase.START || this.phase == Phase.YOURSELF)
					this.phase = this.phase.getNext();
				
				this.progressing = false;	
				BlockPos pos = p.getPosition();
				p.openGui(BeyondTheVeil.instance, 2, p.world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		
	}
	
	public void setNewPhase(Phase phase) {
		this.phase = phase;
		this.progress = phase.timer;
		this.progressing = true;
	}
	
	public void resetPhase() {
		this.progress = this.phase.timer;
		this.progressing = true;
	}
	
	public static enum Phase {
		START(320), DEITYCHOOSE(80), DEITYYOURSELFCHOOSE(80), YOURSELF(140), BELIEVE(35);
		
		public final int timer;
		
		private Phase(int timer) {
			this.timer = timer;
		}
		
		public Phase getNext() {
			Phase[] phases = this.values();
			if(this.ordinal() < phases.length - 1) return phases[this.ordinal() + 1];
			return null;
		}
		
	}
	
}
