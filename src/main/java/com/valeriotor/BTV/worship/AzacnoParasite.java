package com.valeriotor.BTV.worship;

import java.util.UUID;

import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessagePlaySound;
import com.valeriotor.BTV.network.MessageSyncParasitePlayer;
import com.valeriotor.BTV.potions.PotionRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class AzacnoParasite {
	
	private final UUID player;
	private final MinecraftServer server;
	private int progress = 0;
	private int phase = 0;
	
	public AzacnoParasite(EntityPlayer player) {
		this.player = player.getPersistentID();
		this.server = player.getServer();
	}
	
	public AzacnoParasite(EntityPlayer player, int progress) {
		this.player = player.getPersistentID();
		this.server = player.getServer();
		this.progress = progress;
		this.phase = progress / 2500;
		if(this.renderParasite()) {
			updatePlayers(true);
		}
	}
	
	public boolean update() {
		if(progress < 0)
			return true;
		EntityPlayer p = this.getPlayer();
		if(p == null || p.isDead) {
			return false;
		}
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
			p.addPotionEffect(new PotionEffect(PotionRegistry.terror, 15 * 20 + 15, 3, true, false));
		
		int random = p.world.rand.nextInt(20000);
		if(random < 10 && phase >= 0)
			p.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 10 * 20, 4));
		else if(random < 20 && phase >= 1)
			p.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 10 * 20, 3));
		else if(random < 30 && phase >= 2) {
			p.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 50));
			p.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 50));
		}
		else if(random < 40 && phase >= 3)
			p.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE));
		
		return false;
	}
	
	public void damageParasite(int amount) {
		this.progress -= amount * 120;
		boolean rendersNow = this.renderParasite();
		this.phase = this.progress / 2500;
		if(rendersNow) {
			if(!this.renderParasite()) {
				BTVPacketHandler.INSTANCE.sendTo(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.parasiteDeath), this.getPlayer().getPosition().toLong()), (EntityPlayerMP) this.getPlayer());
				BTVPacketHandler.INSTANCE.sendToAllTracking(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.parasiteDeath), this.getPlayer().getPosition().toLong()), this.getPlayer());
				updatePlayers(false);
			} else {
				if(this.getPlayer().world.rand.nextBoolean()) {
					BTVPacketHandler.INSTANCE.sendTo(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.parasiteHurt), this.getPlayer().getPosition().toLong()), (EntityPlayerMP) this.getPlayer());
					BTVPacketHandler.INSTANCE.sendToAllTracking(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.parasiteHurt), this.getPlayer().getPosition().toLong()), this.getPlayer());
				}
			}	
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
		((WorldServer)this.getPlayer().world).getEntityTracker().sendToTrackingAndSelf(this.getPlayer(), BTVPacketHandler.INSTANCE.getPacketFrom(new MessageSyncParasitePlayer(player, add)));
	}
	
	public EntityPlayer getPlayer() {
		return this.server.getPlayerList().getPlayerByUUID(player);
	}
}
