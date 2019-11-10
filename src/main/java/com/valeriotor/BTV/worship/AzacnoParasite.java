package com.valeriotor.BTV.worship;

import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncParasitePlayer;
import com.valeriotor.BTV.potions.PotionRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.WorldServer;
import thaumcraft.common.lib.network.misc.PacketSealFilterToClient;

public class AzacnoParasite {
	
	private final EntityPlayer player;
	private int progress = 0;
	private int phase = 0;
	
	public AzacnoParasite(EntityPlayer player) {
		this.player = player;
	}
	
	public AzacnoParasite(EntityPlayer player, int progress) {
		this.player = player;
		this.progress = progress;
		this.phase = progress / 2500;
		if(this.renderParasite()) {
			updatePlayers(true);
		}
	}
	
	public boolean update() {
		if(progress < 0)
			return true;
		if(player.isDead)
			return true;
		
		if(progress < 500 * 20) {
			progress++;
			if(progress % 2500 == 0) {
				phase++;
				if(phase == 2) {
					updatePlayers(true);
				}
			}
		}
		
		if(phase >= 4)
			player.addPotionEffect(new PotionEffect(PotionRegistry.terror, 15 * 20 + 15, 3, true, false));
		
		int random = player.world.rand.nextInt(20000);
		if(random < 10 && phase >= 0)
			player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 10 * 20, 4));
		else if(random < 20 && phase >= 1)
			player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 10 * 20, 3));
		else if(random < 30 && phase >= 2) {
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30));
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 30));
		}
		else if(random < 40 && phase >= 3)
			player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 2));
		
		return false;
	}
	
	public void damageParasite(int amount) {
		this.progress -= amount;
		boolean rendersNow = this.renderParasite();
		this.phase = this.progress / 2500;
		if(rendersNow && !this.renderParasite()) {
			updatePlayers(false);
		}
	}
	
	public boolean renderParasite() {
		return phase >= 2;
	}
	
	public int getPhase() {
		return this.phase;
	}
	
	public int getProgress() {
		return this.progress;
	}
	
	private void updatePlayers(boolean add) {
		((WorldServer)player.world).getEntityTracker().sendToTrackingAndSelf(player, BTVPacketHandler.INSTANCE.getPacketFrom(new MessageSyncParasitePlayer(player.getPersistentID(), add)));
	}
}
