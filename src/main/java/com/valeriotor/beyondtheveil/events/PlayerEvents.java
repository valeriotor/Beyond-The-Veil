package com.valeriotor.beyondtheveil.events;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capabilities.ResearchProvider;
import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.entities.IPlayerGuardian;
import com.valeriotor.beyondtheveil.events.special.AzacnoParasiteEvents;
import com.valeriotor.beyondtheveil.items.IArtifactItem;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageSyncPlayerRender;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import com.valeriotor.beyondtheveil.world.DimensionRegistry;
import com.valeriotor.beyondtheveil.worship.AzacnoParasite;
import com.valeriotor.beyondtheveil.worship.DGWorshipHelper;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber
public class PlayerEvents {
	
	@SubscribeEvent
	public static void wakeUpEvent(PlayerWakeUpEvent event) {
		if(event.getEntityPlayer().world.getWorldTime()>23900) {
		
		if(event.getEntityPlayer() != null) {
			if(!event.getEntityPlayer().world.isRemote) {
				DreamHandler.chooseDream(event.getEntityPlayer(), 1, true);
				event.getEntityPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger("timesDreamt", 0, false);
			}
		}
		}
		
		
		//IInternalMethodHandler.progressResearch(event.getEntityPlayer(), "FIRSTDREAMS");
		
	}
	
	@SubscribeEvent
	public static void sleepEvent(PlayerSleepInBedEvent event) {
		EntityPlayer p = event.getEntityPlayer();
		if(event.getEntityPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) {
			event.setResult(SleepResult.OTHER_PROBLEM);
			if(!p.world.isRemote) p.sendMessage(new TextComponentTranslation("sleep.transformed"));
		} else if(AzacnoParasiteEvents.parasites.containsKey(event.getEntityPlayer().getPersistentID())) {
			event.setResult(SleepResult.OTHER_PROBLEM);
			if(!p.world.isRemote) p.sendMessage(new TextComponentTranslation("sleep.parasite"));
		}
	}
	
	@SubscribeEvent
	public static void breakingEvent(PlayerEvent.BreakSpeed event) {
		EntityPlayer p = event.getEntityPlayer();
		if(p instanceof EntityPlayer && !(p instanceof FakePlayer)) {
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
			pt = ServerTickEvents.getPlayerTimer("dreamfocus", event.player);
			if(pt != null) {
				pt.terminateEarly();
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
		EntityPlayer p = event.player;
		SyncUtil.syncPlayerData(p);
		if(event.fromDim == DimensionType.NETHER.getId() && event.toDim == DimensionType.OVERWORLD.getId() && !p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("thebeginning")) {
			SyncUtil.addStringDataOnServer(event.player, false, "thebeginning");
			ItemHandlerHelper.giveItemToPlayer(p, new ItemStack(ItemRegistry.necronomicon));
			p.sendMessage(new TextComponentTranslation("beginning.netherreturn"));
		}
		PlayerTimer pt = ServerTickEvents.getPlayerTimer("dreamfocus", p);
		if(pt != null) {
			pt.terminateEarly();
		}
	}
	
	@SubscribeEvent
	public static void playerStartTracking(PlayerEvent.StartTracking event) {
		if(event.getTarget() instanceof EntityPlayer) {
			EntityPlayer target = (EntityPlayer) event.getTarget();
			IPlayerData data = target.getCapability(PlayerDataProvider.PLAYERDATA, null);
			if(data.getString(PlayerDataLib.TRANSFORMED)) {
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncPlayerRender(target.getPersistentID(), true, MessageSyncPlayerRender.Type.DEEPONE), (EntityPlayerMP)event.getEntityPlayer());
			}
			if(AzacnoParasiteEvents.parasites.containsKey(target.getPersistentID()) && AzacnoParasiteEvents.parasites.get(target.getPersistentID()).renderParasite()) {
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncPlayerRender(target.getPersistentID(), true, MessageSyncPlayerRender.Type.PARASITE), (EntityPlayerMP)event.getEntityPlayer());
			}
			if(data.getString(PlayerDataLib.DREAMFOCUS)) {
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncPlayerRender(target.getPersistentID(), true, MessageSyncPlayerRender.Type.DREAMFOCUS), (EntityPlayerMP)event.getEntityPlayer());
			}
				
		}
	}
	
	@SubscribeEvent
	public static void playerStopTracking(PlayerEvent.StopTracking event) {
		if(event.getTarget() instanceof EntityPlayer) {
			EntityPlayer target = (EntityPlayer) event.getTarget();
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncPlayerRender(target.getPersistentID(), false, MessageSyncPlayerRender.Type.DEEPONE), (EntityPlayerMP)event.getEntityPlayer());
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncPlayerRender(target.getPersistentID(), false, MessageSyncPlayerRender.Type.PARASITE), (EntityPlayerMP)event.getEntityPlayer());
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncPlayerRender(target.getPersistentID(), false, MessageSyncPlayerRender.Type.DREAMFOCUS), (EntityPlayerMP)event.getEntityPlayer());
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
	
	@SubscribeEvent
	public static void pickupItemEvent(ItemPickupEvent event) {
		ItemStack stack = event.getStack();
		if(stack.getItem() instanceof IArtifactItem) {
			EntityPlayer p = event.player;
			if(!p.world.isRemote && !(p instanceof FakePlayer))
				((IArtifactItem)stack.getItem()).unlockData(p);
		}
	}
	
	@SubscribeEvent
	public static void itemUseFinish(LivingEntityUseItemEvent.Finish event) {
		EntityLivingBase b = event.getEntityLiving();
		if(b instanceof EntityPlayer && b.dimension == DimensionRegistry.ARCHE.getId() && event.getItem().getItem() instanceof ItemFishFood) {
			EntityPlayer p = (EntityPlayer)event.getEntityLiving();
			if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) {
				ItemStack stack = event.getItem();
				ItemFishFood food = (ItemFishFood)stack.getItem();
				p.getFoodStats().addStats(-food.getHealAmount(stack), -food.getSaturationModifier(stack));
			}
		}
	}
}
