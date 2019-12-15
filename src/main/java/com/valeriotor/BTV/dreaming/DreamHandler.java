package com.valeriotor.BTV.dreaming;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.blocks.BlockFumeSpreader;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.dreaming.dreams.AbstractDream;
import com.valeriotor.BTV.events.ServerTickEvents;
import com.valeriotor.BTV.events.special.CrawlerWorshipEvents;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageRemoveStringToClient;
import com.valeriotor.BTV.util.SyncUtil;
import com.valeriotor.BTV.worship.CrawlerWorship;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

public class DreamHandler {
	
	public static void chooseDream(EntityPlayer p, IPlayerKnowledge k,  int times) {
		List<BlockPos> SpreaderLocations = checkBlocks(p.world,p.getPosition(), BlockRegistry.FumeSpreader.getDefaultState().withProperty(BlockFumeSpreader.ISFULL, true), times);
		if(SpreaderLocations.isEmpty()) return;
		
		boolean increaseTimesDreamt = false;
		
		Map<BlockPos, AbstractDream> dreams = SpreaderLocations.stream()
												.filter(pos -> p.world.getTileEntity(pos).getTileData().hasKey("containing"))
												.filter(pos -> DreamRegistry.dreams.containsKey(p.world.getTileEntity(pos).getTileData().getString("containing")))
												.sorted(Comparator.comparingInt(pos -> DreamRegistry.dreams.get(p.world.getTileEntity(pos).getTileData().getString("containing")).priority)) 
												.collect(Collectors.toMap(pos -> pos, pos -> DreamRegistry.dreams.get(p.world.getTileEntity(pos).getTileData().getString("containing")), (x,y) -> x, LinkedHashMap::new));
		
		for(Entry<BlockPos, AbstractDream> entry : dreams.entrySet()) {
			BlockPos pos = entry.getKey();
			boolean emptySpreader = entry.getValue().activate(p, p.world, k);
			if(emptySpreader) increaseTimesDreamt = true;
			
			if(emptySpreader) {
				IBlockState b = p.world.getBlockState(pos);
				unlockResearch(p, p.world.getTileEntity(pos).getTileData().getString("containing").toLowerCase()); 
				p.world.getTileEntity(pos).getTileData().removeTag("containing");
				p.world.setBlockState(pos, b.getBlock().getStateFromMeta(b.getBlock().getMetaFromState(p.world.getBlockState(pos))-5), 2);				
			}
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
	
	/** Checks if the player already has the "research" (not a true entry) corresponding to that aspect.
	 *  If not, it gives him the research as well as a status message.
	 *  
	 * @param p The player who just woke up
	 * @param aspect The aspect that influenced the dream
	 */
	private static void unlockResearch(EntityPlayer p, String aspect) {
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
		if(!k.isResearchKnown(aspect+"Dream")) {
			if(!isLongDream(aspect))
				ThaumcraftApi.internalMethods.progressResearch(p, String.format("%sDream", aspect));
			if(aspect.equals("tenebrae") || aspect.equals("vacuos"))
				ThaumcraftApi.internalMethods.progressResearch(p, String.format("f_%sDream", aspect.substring(0, 1).toUpperCase().concat(aspect.substring(1))));
			if(!aspect.equals("alienis"))
				p.sendStatusMessage(new TextComponentTranslation(String.format("research.%s.unlock", aspect)), true);
			if(!k.isResearchKnown("f_EffectDream")) {
				if(aspect.equals("potentia") || aspect.equals("vinculum") || aspect.equals("permutatio")) {
					ThaumcraftApi.internalMethods.progressResearch(p, "f_EffectDream");
				}
				
			}
			if(!k.isResearchKnown("f_HumanDream")) {
				if(aspect.equals("humanus") || aspect.equals("instrumentum")) {
					ThaumcraftApi.internalMethods.progressResearch(p, "f_HumanDream");
				}
			}
			
		}
	}
	
	/** Checks whether the chosen aspect causes a long Dream. If so, "aspectDream" should not be unlocked
	 *  immediately, but only when the Dream ends.
	 * 
	 */
	private static boolean isLongDream(String aspect) {
		if(aspect.equals("alienis")) return true;
		return false;
	}
	
	/** Checks if the player already has the "research" (not a true entry) corresponding to that aspect.
	 *
	 * @param p The player who just woke up
	 * @param aspect The aspect that influenced the dream
	 * 
	 * @return True if the dream's known.
	 */
	public static boolean knowsDream(EntityPlayer p, String aspect) {
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
		if(k.isResearchKnown(aspect+"Dream")) return true;
		return false;
	}
	
	/** Checks whether the Dreamer has successfully dreamt with a certain aspect. If not, sends a message to the
	 *  player telling them they should try again in the future.
	 * 
	 */
	public static boolean youDontKnowDream(EntityPlayer p, String aspect) {
		if(!knowsDream(p, aspect)) {
			p.sendMessage(new TextComponentTranslation("dreams.maybeinthefuture"));
			return false;
		}
		else return true;
	}
	
	/** Checks whether the player's DGLevel is greater than or equal to the lvl parameter. If not, sends a message
	 *  to the player telling them they're not strong enough.
	 * 
	 * @return true if the player's DG Level (without Vacuos) >= lvl
	 */
	public static boolean youDontHaveLevel(EntityPlayer p, int lvl) {
		if(getDreamingGodLevel(p) < lvl) {
			p.sendMessage(new TextComponentTranslation("dreams.lowlevel"));
			return false;
		}
		return true;
	}
	
	/** Returns the player's Dreaming God level *with* Vacuos bonus (and automatically removes it if so).
	 */
	public static int getDreamLevel(EntityPlayer p) {
		int lvl = getDreamingGodLevel(p);
		if(hasDreamtOfVoid(p)) lvl++;
		CrawlerWorship cw = CrawlerWorshipEvents.getWorship(p);
		if(cw != null) lvl += cw.getDreamBonusStrength();
		return lvl;
	}
	
	/** Returns the player's Dreaming God level *without* Vacuos bonus.
	 */
	public static int getDreamingGodLevel(EntityPlayer p) {
		return Math.min(Deities.GREATDREAMER.cap(p).getLevel(), DGWorshipHelper.MAX_LEVEL);
	}
	
	/** Gets the difference in DreamingGod level between two players, one of whom is making an offensive Dream and the other
	 *  is defending. Doesn't count Vacuos Dreams.
	 * 
	 * @return The difference in DreamingGod level
	 */
	public static int getDreamAttack(EntityPlayer attacker, EntityPlayer target) {
		return getDreamingGodLevel(attacker) - getDreamingGodLevel(target);
	}
	
	/** Check whether the Dreamer has recently dreamt with Vacuos (possibly even in this Dreaming session) by checking
	 *  PlayerData. If so, it removes the String from PlayerData.
	 * 
	 * @param p The Dreamer
	 */
	public static boolean hasDreamtOfVoid(EntityPlayer p) {
		boolean flag = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("vacuos");
		if(flag) {
			p.getCapability(PlayerDataProvider.PLAYERDATA, null).removeString("vacuos");
			BTVPacketHandler.INSTANCE.sendTo(new MessageRemoveStringToClient("vacuos"), (EntityPlayerMP)p);
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

