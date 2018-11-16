package com.valeriotor.BTV.events;

import com.valeriotor.BTV.dreams.DreamHandler;
import com.valeriotor.BTV.entities.EntityCanoe;
import com.valeriotor.BTV.world.BiomeRegistry;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

@Mod.EventBusSubscriber
public class PlayerEvents {
	
	@SubscribeEvent
	public static void wakeUpEvent(PlayerWakeUpEvent event) {

		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(event.getEntityPlayer());
		if(event.getEntityPlayer()!=null && !k.isResearchKnown("FIRSTDREAMS") && k.isResearchKnown("FIRSTSTEPS") && event.getEntityPlayer().world.getWorldTime()>23900) {
			k.addResearch("!didDream");
			k.sync((EntityPlayerMP)event.getEntityPlayer());
		}else if(event.getEntityPlayer() != null && k.isResearchKnown("FIRSTDREAMS") && event.getEntityPlayer().world.getWorldTime()>23900) {
			DreamHandler.chooseDream(event.getEntityPlayer(), k);
		}
		
		
		
		//IInternalMethodHandler.progressResearch(event.getEntityPlayer(), "FIRSTDREAMS");
		
	}
	
	@SubscribeEvent
	public static void tickevent(PlayerTickEvent event) {
		
		//Canoe Gifts
		if(event.player.getRidingEntity() instanceof EntityCanoe && (event.player.world.getBiome(event.player.getPosition()) == Biomes.OCEAN 
		   || event.player.world.getBiome(event.player.getPosition()) == BiomeRegistry.innsmouth || event.player.world.getBiome(event.player.getPosition()) == Biomes.DEEP_OCEAN)) {
			if(Math.abs(event.player.motionX) > 0.01 || Math.abs(event.player.motionZ) > 0.01) {
				if((event.player.world.getWorldTime() & 127) == event.player.world.rand.nextInt(128)) {
					if(!event.player.world.isRemote) event.player.sendMessage(new TextComponentString("Success!"));
					double angle = event.player.world.rand.nextDouble()*2*Math.PI;
					double x = Math.sin(angle)*0.8D;
					double z = Math.cos(angle)*0.8D;
					EntityItem fish = new EntityItem(event.player.world, event.player.getPosition().getX() + x, event.player.getPosition().getY(), event.player.getPosition().getZ() + z, new ItemStack(Items.FISH));
					fish.motionX = -x + event.player.motionX;
					fish.motionZ = -z + event.player.motionZ;
					event.player.world.spawnEntity(fish);
				}
			}
		}
	}
	
	
}
