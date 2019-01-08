package com.valeriotor.BTV.events;

import java.util.List;

import com.valeriotor.BTV.capabilities.FlagProvider;
import com.valeriotor.BTV.dreams.DreamHandler;
import com.valeriotor.BTV.entities.EntityCanoe;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.world.BiomeRegistry;

import baubles.api.BaublesApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
		if(event.getEntityPlayer()!=null && !k.isResearchKnown("FIRSTDREAMS") && k.isResearchKnown("FIRSTSTEPS")) {
			k.addResearch("!didDream");
			k.sync((EntityPlayerMP)event.getEntityPlayer());
		}else if(event.getEntityPlayer() != null && k.isResearchKnown("FIRSTDREAMS")) {
			if(!event.getEntityPlayer().world.isRemote)	DreamHandler.chooseDream(event.getEntityPlayer(), k);
			event.getEntityPlayer().getCapability(FlagProvider.FLAG_CAP, null).setTimesDreamt(0);
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
		if(event.player.world.getWorldTime() == 10) event.player.getCapability(FlagProvider.FLAG_CAP, null).setTimesDreamt(0); 
		
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
	
	
}
