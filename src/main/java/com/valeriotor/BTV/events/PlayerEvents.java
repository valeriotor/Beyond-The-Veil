package com.valeriotor.BTV.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.dreams.DreamHandler;
import com.valeriotor.BTV.entities.EntityCanoe;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.world.BiomeRegistry;

import baubles.api.BaublesApi;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
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
	public static void tickEvent(PlayerTickEvent event) {
		if(event.phase.equals(Phase.END)) {
		//Canoe Gifts
		if(!event.player.world.isRemote && event.player.getRidingEntity() instanceof EntityCanoe && (event.player.world.getBiome(event.player.getPosition()) == Biomes.OCEAN 
		   || event.player.world.getBiome(event.player.getPosition()) == BiomeRegistry.innsmouth || event.player.world.getBiome(event.player.getPosition()) == Biomes.DEEP_OCEAN)) {
			if(Math.abs(event.player.motionX) > 0.01 || Math.abs(event.player.motionZ) > 0.01) {
				if((event.player.world.getWorldTime() & 127) == event.player.world.rand.nextInt(128)) {
					double angle = event.player.world.rand.nextDouble()*2*Math.PI;
					double x = Math.sin(angle);
					double z = Math.cos(angle);
					EntityItem fish = new EntityItem(event.player.world, event.player.getPosition().getX() + x, event.player.getPosition().getY(), event.player.getPosition().getZ() + z, new ItemStack(Items.FISH));
					fish.motionX = -x + event.player.motionX;
					fish.motionZ = -z + event.player.motionZ;
					event.player.world.spawnEntity(fish);
				}
			}
		}
		
		// Reset times dreamt
		if(event.player.world.getWorldTime() == 10) event.player.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger("timesDreamt", 0, false);; 
		
		// Wolf Medallion Events
		if(!event.player.world.isRemote && BaublesApi.isBaubleEquipped(event.player, ItemRegistry.wolf_medallion) == 0) {
			ItemRegistry.wolf_medallion.tickEvent(event);
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
			List<String> strings = event.player.getCapability(PlayerDataProvider.PLAYERDATA, null).getStrings(false);
			HashMap<String, Integer> ints = event.player.getCapability(PlayerDataProvider.PLAYERDATA, null).getInts(false);
			
			for(String string : strings) {
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient(string), (EntityPlayerMP)event.player);
			}
				
			for(Entry<String, Integer> entry : ints.entrySet()) {
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient(entry.getKey(), entry.getValue()), (EntityPlayerMP)event.player);	
			}
		}
	}
	
	@SubscribeEvent
	public static void cloneEvent(PlayerEvent.Clone event) {
		
		List<String> strings = event.getOriginal().getCapability(PlayerDataProvider.PLAYERDATA, null).getStrings(false);
		HashMap<String, Integer> ints = event.getOriginal().getCapability(PlayerDataProvider.PLAYERDATA, null).getInts(false);
			
		for(String string : strings) {
			event.getEntityPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).addString(string, false);
		}
			
		for(Entry<String, Integer> entry : ints.entrySet()) {
			event.getEntityPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(entry.getKey(), entry.getValue(), false);
		}
			
		
	}
}
