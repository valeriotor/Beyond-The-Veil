package com.valeriotor.BTV.events;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.EntityCanoe;
import com.valeriotor.BTV.events.special.CrawlerWorshipEvents;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.util.SyncUtil;
import com.valeriotor.BTV.world.BiomeRegistry;
import com.valeriotor.BTV.world.HamletList;
import com.valeriotor.BTV.worship.DGWorshipHelper;
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

@Mod.EventBusSubscriber
public class PlayerTickEvents {
	

	@SubscribeEvent
	public static void tickEvent(PlayerTickEvent event) {
		if(event.phase.equals(Phase.END)) {
			EntityPlayer p = event.player;
			IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
			canoeFishing(p, data);
			resetTimesDreamt(p, data);
			waterPowers(p, data);
			decreaseCooldown(p, data);
			if(!p.world.isRemote) {
				CrawlerWorshipEvents.updateWorships(p);
				findHamlet(p, data);
			}
		}
	}
	
	private static void canoeFishing(EntityPlayer p, IPlayerData data) {
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
							if(DGWorshipHelper.researches.get(PlayerDataLib.FISHQUEST).canBeUnlocked(p) && !data.getString(PlayerDataLib.FISHQUEST)) {
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
	
	private static void resetTimesDreamt(EntityPlayer p, IPlayerData data) {
		if(p.world.getWorldTime() == 10) data.setInteger("timesDreamt", 0, false);
		
	}
	
	private static void findHamlet(EntityPlayer p, IPlayerData data) {
		if(p.world.getBiome(p.getPosition()) == BiomeRegistry.innsmouth && !data.getString(PlayerDataLib.FOUND_HAMLET)) {
			BlockPos pos = HamletList.get(p.world).getClosestHamlet(p.getPosition()); 
				if(pos != null && pos.distanceSq(p.getPosition()) < 3600 && ResearchUtil.getResearchStage(p, "FISHINGHAMLET") == 0) {
					SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.FOUND_HAMLET);
				}
		}
	}
	
	private static void waterPowers(EntityPlayer p, IPlayerData data) {
		boolean transformed = data.getString(PlayerDataLib.TRANSFORMED);
		if(ResearchUtil.getResearchStage(p, "BAPTISM") > 1) {
			if(p.isInsideOfMaterial(Material.WATER)) {
				double motX = p.motionX * 1.2;
				double motY = p.motionY * 1.25;
				double motZ = p.motionZ * 1.2;
				boolean flying = p.capabilities.isFlying;
				if(transformed) {
					p.capabilities.isFlying = true;
					p.capabilities.setFlySpeed(0.06F);
					if(!p.isPotionActive(MobEffects.REGENERATION))
						p.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 0, false, false));
				} else if(!flying) {
					if(Math.abs(p.motionX) < 1.3) p.motionX = motX;
					if((p.motionY > 0 || p.isSneaking()) && p.motionY < 1.3) p.motionY = motY;
					if(Math.abs(p.motionZ) < 1.3) p.motionZ = motZ;
				}
				p.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 300, 0, false, false));
				p.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, false, false));
			} else if(transformed) {
				if(p.world.isRemote)
					p.capabilities.isFlying = false;
			}		
		}
		if(transformed) {
			ItemStack stack = p.getHeldItemMainhand();
			if(stack.getItem() != Items.AIR && stack.getItem() != ItemRegistry.slug) {
				ItemStack clone = stack.copy();
				p.dropItem(clone, true);
				stack.setCount(0);
			}
			stack = p.getHeldItemOffhand();
			if(stack.getItem() != Items.AIR && stack.getItem() != ItemRegistry.slug) {
				ItemStack clone = stack.copy();
				p.dropItem(clone, true);
				stack.setCount(0);
			}
		}
		
	}
	
	private static void decreaseCooldown(EntityPlayer p, IPlayerData data) {
		int currentTicks = Worship.getPowerCooldown(p);
		Worship.setPowerCooldown(p, currentTicks-1);
		int selectedBauble = data.getOrSetInteger(PlayerDataLib.SELECTED_BAUBLE, -1, false);
		if(selectedBauble != -1) {
			if(data.getOrSetInteger(
					String.format(PlayerDataLib.BAUBLE_COOLDOWN, selectedBauble), 0, false) <= 0) return;
			data.incrementOrSetInteger(
					String.format(PlayerDataLib.BAUBLE_COOLDOWN, selectedBauble), -1, 0, false);
		}
	}
	
}
