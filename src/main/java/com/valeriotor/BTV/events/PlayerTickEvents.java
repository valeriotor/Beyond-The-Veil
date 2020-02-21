package com.valeriotor.BTV.events;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.EntityCanoe;
import com.valeriotor.BTV.entities.EntityDeepOne;
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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
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
				spawnDeepOnes(p, data);
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
		if(data.getString(PlayerDataLib.RITUALQUEST)) {
			if(p.isInsideOfMaterial(Material.WATER)) {
				double motX = p.motionX * 1.2;
				double motY = p.motionY * 1.25;
				double motZ = p.motionZ * 1.2;
				boolean flying = p.capabilities.isFlying;
				if(transformed) {
					p.capabilities.isFlying = true;
					p.capabilities.setFlySpeed(0.06F);
					if(!p.isPotionActive(MobEffects.REGENERATION))
						p.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 1, false, false));
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
			if((p.world.getWorldTime() & 31) == 0)
				for(int i = 0; i < 4; i++) {
					p.dropItem(p.inventory.armorInventory.get(i), true);
					p.inventory.armorInventory.set(i, ItemStack.EMPTY);
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
	
	private static void spawnDeepOnes(EntityPlayer p, IPlayerData data) {
		if(ResearchUtil.isResearchComplete(p, "IDOL")) return;
		BlockPos pos = p.getPosition();
		Biome b = p.world.getBiome(pos);
		if(b == Biomes.OCEAN || b == Biomes.DEEP_OCEAN || b == Biomes.BEACH) {
			int val = data.getOrSetInteger(PlayerDataLib.CURSE, 0, false);
			if(val > 0) {
				if(p.world.rand.nextInt(8000) == 0) {
					boolean flag = false;
					for(int i = 0; i < (val+5) / 5; i++) {
						flag = false;
						for(int x = -20; x <= 20 && !flag; x += 4) {
							for(int z = -20; z <= 20 && !flag; z += 4) {
								BlockPos newPos = pos.add(x, 0, z);
								while(newPos.getY() > 20 && p.world.getBlockState(newPos.down()).getBlock() == Blocks.AIR)
									newPos = newPos.down();
								if(canSpawnDeepOne(p.world, newPos)) {
									EntityDeepOne deepOne = new EntityDeepOne(p.world);
									deepOne.setPosition(newPos.getX(), newPos.getY(), newPos.getZ());
									p.world.spawnEntity(deepOne);
									data.incrementOrSetInteger(PlayerDataLib.CURSE, -1, 0, false);
									flag = true;
								}
							}
						}
					}
				}
			}
		}
	}
	
	private static boolean canSpawnDeepOne(World w, BlockPos pos) {
		for(int x = -1; x < 1; x++) {
			for(int z = -1; z < 1; z++) {
				for(int y = 0; y < 3; y++) {
					Block b = w.getBlockState(pos.add(x, y, z)).getBlock();
					if(b != Blocks.AIR && b != Blocks.WATER) {
						return false;
					}
				}
			}
		}
		if(w.getBlockState(pos.down()).getBlock() == Blocks.AIR) return false;
		return true;
	}
	
}
