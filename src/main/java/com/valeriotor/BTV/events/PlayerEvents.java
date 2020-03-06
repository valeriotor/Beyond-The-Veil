package com.valeriotor.BTV.events;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.capabilities.ResearchProvider;
import com.valeriotor.BTV.dreaming.DreamHandler;
import com.valeriotor.BTV.entities.IPlayerGuardian;
import com.valeriotor.BTV.events.special.AzacnoParasiteEvents;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.network.MessageSyncParasitePlayer;
import com.valeriotor.BTV.network.MessageSyncTransformedPlayer;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.util.PlayerTimer;
import com.valeriotor.BTV.util.SyncUtil;
import com.valeriotor.BTV.worship.AzacnoParasite;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;
import com.valeriotor.BTV.worship.ActivePowers.TransformDeepOne;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

@Mod.EventBusSubscriber
public class PlayerEvents {
	
	@SubscribeEvent
	public static void wakeUpEvent(PlayerWakeUpEvent event) {
		if(event.getEntityPlayer().world.getWorldTime()>23900) {
		
		if(event.getEntityPlayer() != null) {
			if(!event.getEntityPlayer().world.isRemote)	DreamHandler.chooseDream(event.getEntityPlayer(), 1);
			event.getEntityPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger("timesDreamt", 0, false);
		}
		}
		
		
		//IInternalMethodHandler.progressResearch(event.getEntityPlayer(), "FIRSTDREAMS");
		
	}
	
	@SubscribeEvent
	public static void breakingEvent(PlayerEvent.BreakSpeed event) {
		EntityPlayer p = event.getEntityPlayer();
		if(p instanceof EntityPlayer) {
			if(DGWorshipHelper.hasWaterPowers(p)) {
				if(p.isInsideOfMaterial(Material.WATER)) {
					if(!EnchantmentHelper.getAquaAffinityModifier(p))
						event.setNewSpeed(event.getOriginalSpeed() * 5);					
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void loginEvent(PlayerLoggedInEvent event) {
		if(!event.player.world.isRemote) {
			int parasiteProgress = event.player.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.PARASITE_PROGRESS, 0, false);
			if(parasiteProgress > 0)
				AzacnoParasiteEvents.parasites.put(event.player.getPersistentID(), new AzacnoParasite(event.player, parasiteProgress));
			SyncUtil.syncPlayerData(event.player);
			DGWorshipHelper.calculateModifier(event.player);
		}
	}
	
	@SubscribeEvent
	public static void logoutEvent(PlayerLoggedOutEvent event) {
		if(event.player.world.isRemote) {
			BeyondTheVeil.proxy.renderEvents.transformedPlayers.clear();
			BeyondTheVeil.proxy.renderEvents.covenantPlayers.clear();
			BeyondTheVeil.proxy.renderEvents.parasitePlayers.clear();
		} else {
			DGWorshipHelper.removeModifiers(event.player);
			if(AzacnoParasiteEvents.parasites.containsKey(event.player.getPersistentID())) {
				event.player.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.PARASITE_PROGRESS, AzacnoParasiteEvents.parasites.get(event.player.getPersistentID()).getProgress(), false);
				AzacnoParasiteEvents.parasites.remove(event.player.getPersistentID());
			}
			PlayerTimer pt = ServerTickEvents.getPlayerTimer("bcrown1", event.player);
			if(pt != null) {
				pt.terminateEarly();
				ServerTickEvents.removePlayerTimer("bcrown1", event.player);
				ServerTickEvents.removePlayerTimer("bcrown2", event.player);
			}
		}
	}
	
	@SubscribeEvent
	public static void cloneEvent(PlayerEvent.Clone event) {
		EntityPlayer original = event.getOriginal();
		EntityPlayer player = event.getEntityPlayer();
		
		Set<String> strings = original.getCapability(PlayerDataProvider.PLAYERDATA, null).getStrings(false);
		HashMap<String, Integer> ints = original.getCapability(PlayerDataProvider.PLAYERDATA, null).getInts(false);
		IPlayerData newData = player.getCapability(PlayerDataProvider.PLAYERDATA, null);
		for(String string : strings) {
			newData.addString(string, false);
		}
			
		for(Entry<String, Integer> entry : ints.entrySet()) {
			newData.setInteger(entry.getKey(), entry.getValue(), false);
		}
		player.getCapability(ResearchProvider.RESEARCH, null).putResearches(original.getCapability(ResearchProvider.RESEARCH, null).getResearches());
		
	}
	
	@SubscribeEvent
	public static void respawnEvent(PlayerRespawnEvent event) {
		SyncUtil.syncPlayerData(event.player);
	}
	
	@SubscribeEvent
	public static void changeDimensionEvent(PlayerChangedDimensionEvent event) {
		SyncUtil.syncPlayerData(event.player);
	}
	
	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) event.getEntity();
			IPlayerData cap = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
			BlockPos pos = p.getPosition();
			if(!event.isCanceled() && p.dimension == DimensionType.OVERWORLD.getId()) {
				cap.setInteger(PlayerDataLib.DEATH_X, pos.getX(), false);
				cap.setInteger(PlayerDataLib.DEATH_Y, pos.getY(), false);
				cap.setInteger(PlayerDataLib.DEATH_Z, pos.getZ(), false);
			}
		} else if(event.getEntity() instanceof EntityElderGuardian) {
			Entity e = event.getSource().getTrueSource();
			EntityPlayer p = null;
			if(e instanceof EntityPlayer) {
				p = (EntityPlayer)e;
			} else if(e instanceof IPlayerGuardian) {
				p = ((IPlayerGuardian)e).getMaster();
			}
			if(p != null) {
				if(ResearchUtil.getResearchStage(p, "METAMORPHOSIS") == 1) {
					int val = SyncUtil.incrementIntDataOnServer(p, false, PlayerDataLib.ELDER_GUARDIANS, 1, 1);
					if(val == 3) {
						SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.DAGONQUEST2);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void playerStartTracking(PlayerEvent.StartTracking event) {
		if(event.getTarget() instanceof EntityPlayer) {
			EntityPlayer target = (EntityPlayer) event.getTarget();
			if(target.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) {
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncTransformedPlayer(target.getPersistentID(), true), (EntityPlayerMP)event.getEntityPlayer());
			}
			if(AzacnoParasiteEvents.parasites.containsKey(target.getPersistentID()) && AzacnoParasiteEvents.parasites.get(target.getPersistentID()).renderParasite())
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncParasitePlayer(target.getPersistentID(), true), (EntityPlayerMP)event.getEntityPlayer());
		}
	}
	
	@SubscribeEvent
	public static void playerStopTracking(PlayerEvent.StopTracking event) {
		if(event.getTarget() instanceof EntityPlayer) {
			EntityPlayer target = (EntityPlayer) event.getTarget();
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncTransformedPlayer(target.getPersistentID(), false), (EntityPlayerMP)event.getEntityPlayer());
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncParasitePlayer(target.getPersistentID(), false), (EntityPlayerMP)event.getEntityPlayer());
		}
	}
	
	@SubscribeEvent
	public static void jumpEvent(LivingJumpEvent event) {
		if(event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) event.getEntityLiving();
			if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) {
				if(!p.isSneaking()) p.motionY += 0.275;
			}
		}
	}
}
