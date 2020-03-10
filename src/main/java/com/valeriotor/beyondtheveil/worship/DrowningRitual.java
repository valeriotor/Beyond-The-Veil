package com.valeriotor.beyondtheveil.worship;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.events.special.DrowningRitualEvents;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.ritual.MessagePerformHurtAnimation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.BlockPos;

public class DrowningRitual {
	
	public Phase phase = Phase.START;
	private int progress = 480;
	public boolean greatDreamer = false;
	public boolean ancientGods = false;
	public boolean progressing = true;
	private final EntityPlayer p;
	
	public DrowningRitual(EntityPlayer p) {
		this.p = p;
		p.removeActivePotionEffect(MobEffects.WATER_BREATHING);
		p.removeActivePotionEffect(MobEffects.HASTE);
		p.removeActivePotionEffect(MobEffects.REGENERATION);
		
	}
	
	public void update() {
		if(p.getAir() > 10) p.setAir(p.getAir() - 5);
		else p.setAir(10);
		if(this.progressing) {
			this.progress--;
			if((this.progress & 31) == 0) {
				if(this.progress > 0) {
					if(this.phase == Phase.START) {
						if(p.getHealth() > this.progress / 32)
							this.p.setHealth(this.progress/32);
						BTVPacketHandler.INSTANCE.sendTo(new MessagePerformHurtAnimation(MessagePerformHurtAnimation.DROWN), (EntityPlayerMP)p);
					} else if((progress & 63) == 0 && phase == Phase.YOURSELF){
						this.p.setHealth(p.getHealth() + 1);
					}
				}
				FoodStats f = p.getFoodStats();
				if(f.getFoodLevel() > 16) f.setFoodLevel(f.getFoodLevel() - 1);
				p.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 1000, 100));
			}
			if(this.progress == 0) {
				if(this.phase == Phase.START)
					this.phase = this.phase.getNext();
				this.p.setHealth(1);
				this.progressing = false;	
				BlockPos pos = p.getPosition();
				p.openGui(BeyondTheVeil.instance, 2, p.world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		if(!p.isInWater() || p.isDead) DrowningRitualEvents.rituals.remove(p.getPersistentID());
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
		START(480), DEITYCHOOSE(80), DEITYYOURSELFCHOOSE(80), YOURSELF(140), BELIEVE(35);
		
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
