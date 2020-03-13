package com.valeriotor.beyondtheveil.dreaming;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.blocks.BlockFumeSpreader;
import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.dreaming.dreams.Dream;
import com.valeriotor.beyondtheveil.events.special.CrawlerWorshipEvents;
import com.valeriotor.beyondtheveil.gui.Guis;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageOpenGuiToClient;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship;
import com.valeriotor.beyondtheveil.worship.DGWorshipHelper;
import com.valeriotor.beyondtheveil.worship.Deities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DreamHandler {
	
	public static void chooseDream(EntityPlayer p, int times) {
		List<BlockPos> SpreaderLocations = checkBlocks(p.world,p.getPosition(), BlockRegistry.FumeSpreader.getDefaultState().withProperty(BlockFumeSpreader.ISFULL, true), times);
		if(SpreaderLocations.isEmpty()) return;
		
		boolean increaseTimesDreamt = false, eldritchDream = false;
		TreeMap<Memory, BlockPos> memories = new TreeMap<>(Comparator.comparingInt(m -> DreamRegistry.dreams.get(m).priority));
		for(BlockPos pos : SpreaderLocations) {
			Memory m = BlockFumeSpreader.getTE(p.world, pos).getMemory();
			if(m.isUnlocked(p) && DreamRegistry.dreams.containsKey(m)) {
				memories.put(m, pos);
				boolean voidDream = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.VOID) || memories.containsKey(Memory.VOID);
				if(m == Memory.ELDRITCH && voidDream) eldritchDream = true;
			}
		}
		
		
		for(Entry<Memory, BlockPos> entry : memories.entrySet()) {
			BlockPos pos = entry.getValue();
			Dream d = DreamRegistry.dreams.get(entry.getKey());
			boolean emptySpreader = d.activate(p, p.world);
			if(emptySpreader) increaseTimesDreamt = true;
			boolean longDream = isLongDream(entry.getKey());
			if(emptySpreader) {
				IBlockState b = p.world.getBlockState(pos);
				if(!longDream) unlockResearch(p, BlockFumeSpreader.getTE(p.world, pos).getMemory().name().toLowerCase()); 
				BlockFumeSpreader.getTE(p.world, pos).setMemory(null);
				p.world.setBlockState(pos, p.world.getBlockState(pos).withProperty(BlockFumeSpreader.ISFULL, false));			
			} else if(entry.getKey() == Memory.ELDRITCH)
				eldritchDream = false;
		}

		if(!eldritchDream) {
			BTVPacketHandler.INSTANCE.sendTo(new MessageOpenGuiToClient(Guis.GuiEmpty), (EntityPlayerMP)p);
		}
		
		if(increaseTimesDreamt) {
			p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.TIMESDREAMT, p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.TIMESDREAMT, 0, false)+1, false);		
			SyncUtil.syncCapabilityData(p);
		}
		
		
	}
	
	public static List<BlockPos> checkBlocks(World world, BlockPos playerPos, IBlockState state, int n) {
		if(n < 1) return null;
		List<BlockPos> poss = Lists.newArrayList();
		int i = 0;
		for(int x = -2; x<3; x++) {
			for(int z = -2; z<3; z++) {
				for(int y = -3; y<2; y++) {
					BlockPos posCheck = playerPos.add(x, y, z);
					if(world.getBlockState(posCheck).getBlock() != null) {
						if(world.getBlockState(posCheck) == state) {
							poss.add(posCheck);
							if(i >= n) return poss;
						}
					}
				}
			}
		}
		return poss;
	}
	
	/** Copies a list of EntityPlayerMP (presumably gained from MinecraftServer.getPlayerList().getPlayers()) into a new list
	 *  for safe handling
	 * 
	 * @param listIn The input list
	 * @param excludedPlayers Any players that should be excluded in the copying (generally the Dreamer)
	 * 
	 * @return The new list
	 */
	public static List<EntityPlayerMP> copyPlayerList(List<EntityPlayerMP> listIn, EntityPlayerMP... excludedPlayers){
		List<EntityPlayerMP> listOut = listIn.stream().filter(p -> arrayContainsPlayer(p, excludedPlayers)).collect(Collectors.toList());
		return listOut;
	}
	
	public static boolean arrayContainsPlayer(EntityPlayerMP p, EntityPlayerMP[] array) {
		for(EntityPlayerMP player : array) {
			if(p.equals(player)) return false;
		}
		return true;
	}
	
	/** Checks if the player already has the "research" (not a true entry) corresponding to that Memory.
	 *  If not, it gives him the research as well as a status message.
	 *  
	 * @param p The player who just woke up
	 * @param name The memory that influenced the dream
	 */
	private static void unlockResearch(EntityPlayer p, String name) {
		SyncUtil.addStringDataOnServer(p, false, name.concat("Dream"));
	}
	
	/** Checks whether the chosen Memory causes a long Dream. If so, "memoryDream" should not be unlocked
	 *  immediately, but only when the Dream ends.
	 * 
	 */
	private static boolean isLongDream(Memory m) {
		switch(m) {
		case ELDRITCH: 	return true;
		default:		return false;
		}
	}
	
	/** Checks whether the player's DGLevel is greater than or equal to the lvl parameter. If not, sends a message
	 *  to the player telling them they're not strong enough.
	 * 
	 * @return true if the player's DG Level (without Vacuos) >= lvl
	 */
	public static boolean youDontHaveLevel(EntityPlayer p, int lvl) {
		if(getDreamPowerLevel(p) < lvl) {
			p.sendMessage(new TextComponentTranslation("dreams.lowlevel"));
			return false;
		}
		return true;
	}
	
	/** Returns the player's Dreaming God level *with* Vacuos bonus (and automatically removes it if so).
	 */
	public static int getDreamLevel(EntityPlayer p) {
		int lvl = getDreamPowerLevel(p);
		if(hasDreamtOfVoid(p)) lvl++;
		CrawlerWorship cw = CrawlerWorshipEvents.getWorship(p);
		if(cw != null) lvl += cw.getDreamBonusStrength();
		return lvl;
	}
	
	/** Returns the player's Dreaming God level *without* Vacuos bonus.
	 */
	public static int getDreamPowerLevel(EntityPlayer p) {
		return DGWorshipHelper.getDreamPower(p);
	}
	
	/** Gets the difference in DreamingGod level between two players, one of whom is making an offensive Dream and the other
	 *  is defending. Doesn't count Vacuos Dreams.
	 * 
	 * @return The difference in DreamingGod level
	 */
	public static int getDreamAttack(EntityPlayer attacker, EntityPlayer target) {
		return getDreamPowerLevel(attacker) - getDreamPowerLevel(target);
	}
	
	/** Check whether the Dreamer has recently dreamt with Vacuos (possibly even in this Dreaming session) by checking
	 *  PlayerData. If so, it removes the String from PlayerData.
	 * 
	 * @param p The Dreamer
	 */
	public static boolean hasDreamtOfVoid(EntityPlayer p) {
		boolean flag = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.VOID);
		if(flag) {
			SyncUtil.removeStringDataOnServer(p, PlayerDataLib.VOID);
		}
		return flag;
	}
	
	public static int getMaxDreamsPerDay(EntityPlayer p, Block b) {
		if(b == BlockRegistry.SleepChamber) return 1;
		if(b == BlockRegistry.SleepChamberAdvanced) return 2;
		return 0;
	}
	
	public static int getMaxDreamsPerTime(EntityPlayer p, Block b) {
		if(b == BlockRegistry.SleepChamber) return 1;
		if(b == BlockRegistry.SleepChamberAdvanced) return 2;
		return 0;
	}
	
}

