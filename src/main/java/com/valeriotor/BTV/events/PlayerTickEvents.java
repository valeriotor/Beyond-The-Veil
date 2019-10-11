package com.valeriotor.BTV.events;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.EntityCanoe;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.world.BiomeRegistry;
import com.valeriotor.BTV.world.HamletList;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;
import com.valeriotor.BTV.worship.Worship;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

@Mod.EventBusSubscriber
public class PlayerTickEvents {
	

	@SubscribeEvent
	public static void tickEvent(PlayerTickEvent event) {
		if(event.phase.equals(Phase.END)) {
			EntityPlayer p = event.player;
			canoeFishing(p);
			resetTimesDreamt(p);
			findHamlet(p);
			waterPowers(p);
			decreaseCooldown(p);
		}
	}
	
	private static void canoeFishing(EntityPlayer p) {
		if(!p.world.isRemote && p.getRidingEntity() instanceof EntityCanoe && (p.world.getBiome(p.getPosition()) == Biomes.OCEAN 
				   || p.world.getBiome(p.getPosition()) == BiomeRegistry.innsmouth || p.world.getBiome(p.getPosition()) == Biomes.DEEP_OCEAN)) {
					if(Math.abs(p.motionX) > 0.01 || Math.abs(p.motionZ) > 0.01) {
						if((p.world.getWorldTime() & 127) == p.world.rand.nextInt(128)) {
							double angle = p.world.rand.nextDouble()*2*Math.PI;
							double x = Math.sin(angle);
							double z = Math.cos(angle);
							EntityItem fish = new EntityItem(p.world, p.getPosition().getX() + x, p.getPosition().getY(), p.getPosition().getZ() + z, new ItemStack(Items.FISH));
							fish.motionX = -x + p.motionX;
							fish.motionZ = -z + p.motionZ;
							p.world.spawnEntity(fish);
							IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
							if(DGWorshipHelper.researches.get(PlayerDataLib.FISHQUEST).canBeUnlocked(ThaumcraftCapabilities.getKnowledge(p)) && !data.getString(PlayerDataLib.FISHQUEST)) {
								int currentFish = data.getOrSetInteger(PlayerDataLib.FISH_CANOE, 0, false);
								if(currentFish <= 15) {
									data.incrementOrSetInteger(PlayerDataLib.FISH_CANOE, 1, 1, false);
								}else{
									data.addString(PlayerDataLib.FISHQUEST, false);
									BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient(PlayerDataLib.FISHQUEST), (EntityPlayerMP) p);
								}
							}
						}
					}
				}
	}
	
	private static void resetTimesDreamt(EntityPlayer p) {
		if(p.world.getWorldTime() == 10) p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger("timesDreamt", 0, false);
		
	}
	
	private static void findHamlet(EntityPlayer p) {
		if(!p.world.isRemote && p.world.getBiome(p.getPosition()) == BiomeRegistry.innsmouth && !p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.FOUND_HAMLET)) {
			BlockPos pos = HamletList.get(p.world).getClosestHamlet(p.getPosition()); 
				if(pos != null && pos.distanceSq(p.getPosition()) < 3600 && ThaumcraftCapabilities.getKnowledge(p).isResearchKnown("FISHINGHAMLET")) {
					p.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(PlayerDataLib.FOUND_HAMLET, false);
					ThaumcraftApi.internalMethods.progressResearch(p, "m_FindHamlet");
				}
		}
	}
	
	private static void waterPowers(EntityPlayer p) {
		
		if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.RITUALQUEST)) {
			// TODO Not apply this when transformed
			if(p.isInsideOfMaterial(Material.WATER)) {
				double motX = p.motionX * 1.2;
				double motY = p.motionY * 1.25;
				double motZ = p.motionZ * 1.2;
				boolean flying = p.capabilities.isFlying;
				if(!flying) {
					if(Math.abs(p.motionX) < 1.3) p.motionX = motX;
					if((p.motionY > 0 || p.isSneaking()) && p.motionY < 1.3) p.motionY = motY;
					if(Math.abs(p.motionZ) < 1.3) p.motionZ = motZ;
				}
				p.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 300, 0, false, false));
				p.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, false, false));
			}
		}
		
	}
	
	private static void decreaseCooldown(EntityPlayer p) {
		int currentTicks = Worship.getPowerCooldown(p);
		Worship.setPowerCooldown(p, currentTicks-1);
		int selectedBauble = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.SELECTED_BAUBLE, -1, false);
		if(selectedBauble != -1) {
			if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(
					String.format(PlayerDataLib.BAUBLE_COOLDOWN, selectedBauble), 0, false) <= 0) return;
			p.getCapability(PlayerDataProvider.PLAYERDATA, null).incrementOrSetInteger(
					String.format(PlayerDataLib.BAUBLE_COOLDOWN, selectedBauble), -1, 0, false);
		}
	}
	
}
