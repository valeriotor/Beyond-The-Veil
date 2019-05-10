package com.valeriotor.BTV.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataHandler;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.dreams.DreamHandler;
import com.valeriotor.BTV.entities.EntityCanoe;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.world.BiomeRegistry;
import com.valeriotor.BTV.world.HamletList;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
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
			if(p.getCapability(DGProvider.LEVEL_CAP, null).getLevel() >= 3) {
				if(p.isInsideOfMaterial(Material.WATER)) {
					if(!EnchantmentHelper.getAquaAffinityModifier(p))
						event.setNewSpeed(event.getOriginalSpeed() * 5);					
				}
			}
		}
	}

	@SubscribeEvent
	public static void damageEvent(LivingDamageEvent event) {
		if(event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer)event.getEntityLiving();
			EnumHand h = EnumHand.MAIN_HAND;
			ItemStack stack = null;
			if(p.getHeldItemMainhand().getItem() == ItemRegistry.flute) {
				stack = p.getHeldItemMainhand();
			}else if(p.getHeldItemOffhand().getItem() == ItemRegistry.flute){
				stack = p.getHeldItemOffhand();
			}
			if(stack != null) {
				if(p.getItemInUseCount() > 0)
				stack.setItemDamage(150);
			}
		}
	}
	
	@SubscribeEvent
	public static void loginEvent(PlayerLoggedInEvent event) {
		if(!event.player.world.isRemote) {			
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("level", event.player.getCapability(DGProvider.LEVEL_CAP, null).getLevel()), (EntityPlayerMP)event.player);
			PlayerDataHandler.syncPlayerData(event.player);
		}
	}
	
	@SubscribeEvent
	public static void cloneEvent(PlayerEvent.Clone event) {
		
		List<String> strings = event.getOriginal().getCapability(PlayerDataProvider.PLAYERDATA, null).getStrings(false);
		HashMap<String, Integer> ints = event.getOriginal().getCapability(PlayerDataProvider.PLAYERDATA, null).getInts(false);
		
		event.getEntityPlayer().getCapability(DGProvider.LEVEL_CAP, null).setLevel(event.getOriginal().getCapability(DGProvider.LEVEL_CAP, null).getLevel());	
		for(String string : strings) {
			event.getEntityPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).addString(string, false);
		}
			
		for(Entry<String, Integer> entry : ints.entrySet()) {
			event.getEntityPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(entry.getKey(), entry.getValue(), false);
		}					
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
}
