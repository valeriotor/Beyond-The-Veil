package com.valeriotor.BTV.events;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.dreaming.DreamHandler;
import com.valeriotor.BTV.events.special.AzacnoParasiteEvents;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.network.MessageSyncParasitePlayer;
import com.valeriotor.BTV.network.MessageSyncTransformedPlayer;
import com.valeriotor.BTV.util.PlayerTimer;
import com.valeriotor.BTV.util.SyncUtil;
import com.valeriotor.BTV.worship.AzacnoParasite;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

@Mod.EventBusSubscriber
public class PlayerEvents {
	
	@SubscribeEvent
	public static void wakeUpEvent(PlayerWakeUpEvent event) {
		if(event.getEntityPlayer().world.getWorldTime()>23900) {
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(event.getEntityPlayer());
		
		if(event.getEntityPlayer() != null && k.isResearchComplete("FIRSTDREAMS")) {
			if(!event.getEntityPlayer().world.isRemote)	DreamHandler.chooseDream(event.getEntityPlayer(), k, 1);
			event.getEntityPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger("timesDreamt", 0, false);
		}
		}
		
		
		//IInternalMethodHandler.progressResearch(event.getEntityPlayer(), "FIRSTDREAMS");
		
	}
	
	@SubscribeEvent
	public static void breakingEvent(PlayerEvent.BreakSpeed event) {
		EntityPlayer p = event.getEntityPlayer();
		if(p instanceof EntityPlayer && p != null) {
			if(Deities.GREATDREAMER.cap(p).getLevel() >= 3) {
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
			event.player.getCapability(PlayerDataProvider.PLAYERDATA, null).removeInteger(PlayerDataLib.PARASITE_PROGRESS);
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("level", Deities.GREATDREAMER.cap(event.player).getLevel()), (EntityPlayerMP)event.player);
			SyncUtil.syncPlayerData(event.player);
			DGWorshipHelper.calculateModifier(event.player, ThaumcraftCapabilities.getKnowledge(event.player));
			BeyondTheVeil.proxy.researchEvents.checkResearches(event.player);
		}
	}
	
	@SubscribeEvent
	public static void logoutEvent(PlayerLoggedOutEvent event) {
		if(event.player.world.isRemote) {
			BeyondTheVeil.proxy.renderEvents.transformedPlayers.clear();
			BeyondTheVeil.proxy.renderEvents.covenantPlayers.clear();
			BeyondTheVeil.proxy.renderEvents.parasitePlayers.clear();
		} else {
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
		
		Deities.GREATDREAMER.cap(player).setLevel(Deities.GREATDREAMER.cap(original).getLevel());	
		for(String string : strings) {
			player.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(string, false);
		}
			
		for(Entry<String, Integer> entry : ints.entrySet()) {
			player.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(entry.getKey(), entry.getValue(), false);
		}
		SyncUtil.syncCapabilityData(player);
	}
	
	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) event.getEntity();
			IPlayerData cap = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
			BlockPos pos = p.getPosition();
			if(!event.isCanceled()) {
				cap.setInteger(PlayerDataLib.DEATH_X, pos.getX(), false);
				cap.setInteger(PlayerDataLib.DEATH_Y, pos.getY(), false);
				cap.setInteger(PlayerDataLib.DEATH_Z, pos.getZ(), false);
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
	public static void itemPickupEvent(ItemPickupEvent event) {
		// THIS IS JUST FOR DEBUG PURPOSES, WILL BE DIFFERENT ON RELEASE
		EntityPlayer p = event.player;
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
		ItemStack stack = event.getStack();
		if(Block.getBlockFromItem(stack.getItem()) == BlockRegistry.BlockWateryCradle && !k.isResearchComplete("WATERYCRADLE")) {
			ThaumcraftApi.internalMethods.progressResearch(p, "craftedcradle");
		} else if(Block.getBlockFromItem(stack.getItem()) == BlockRegistry.BlockLacrymatory && !k.isResearchComplete("WEEPERS")) {
			ThaumcraftApi.internalMethods.progressResearch(p, "craftedlacrymatory");
		}
	}
}
