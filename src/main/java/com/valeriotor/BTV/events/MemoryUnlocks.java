package com.valeriotor.BTV.events;

import java.util.List;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.dreaming.Memory;
import com.valeriotor.BTV.lib.PlayerDataLib;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MemoryUnlocks {
	
	@SubscribeEvent
	public static void change(SaplingGrowTreeEvent event) {
		if(event.getResult() == Result.DEFAULT || event.getResult() == Result.ALLOW) {
			World w = event.getWorld();
			if(w.isRemote) return;
			IBlockState state = w.getBlockState(event.getPos());
			if(state.getBlock() instanceof BlockSapling) {
				List<EntityPlayer> players = w.getPlayers(EntityPlayer.class, p -> p.getDistanceSq(event.getPos()) < 25);
				for(EntityPlayer p : players) {
					if(!Memory.CHANGE.isUnlocked(p) && Memory.CHANGE.isUnlockable(p)) {
						IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
						int amount = data.getOrSetInteger(PlayerDataLib.SAPLINGS_SEEN, 0, false);
						if(amount > 15) {
							Memory.CHANGE.unlock(p);
						} else {
							data.incrementOrSetInteger(PlayerDataLib.SAPLINGS_SEEN, 1, 1, false);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent 
	public static void power(LivingDeathEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		if(!(e instanceof EntityPlayer) && (e.getMaxHealth() >= 100 || !e.isNonBoss()) && event.getSource().getTrueSource() instanceof EntityPlayer) {
			Memory.POWER.unlock((EntityPlayer)event.getSource().getTrueSource());
		}
	}
	
	@SubscribeEvent
	public static void sentience(PlayerInteractEvent.RightClickItem event) {
		EntityPlayer p = event.getEntityPlayer();
		ItemStack stack = p.getHeldItem(event.getHand());
		if(!p.world.isRemote && stack.getItem() == Items.WRITTEN_BOOK) {
			Memory.SENTIENCE.unlock(p);
		}
	}
	
}
