package com.valeriotor.BTV.dreams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.blocks.BlockFumeSpreader;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageOpenGuiToClient;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.util.DGWorshipHelper;
import com.valeriotor.BTV.util.WorldHelper;
import com.valeriotor.BTV.world.BiomeRegistry;
import com.valeriotor.BTV.world.HamletList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.research.ResearchCategories;

public class DreamHandler {
	
	public static void chooseDream(EntityPlayer p, IPlayerKnowledge k,  int times) {
		List<BlockPos> SpreaderLocations = checkBlocks(p.world,p.getPosition(), BlockRegistry.FumeSpreader.getDefaultState().withProperty(BlockFumeSpreader.ISFULL, true), times);
		if(SpreaderLocations.isEmpty()) return;
		
		boolean increaseTimesDreamt = false;
		boolean vacuos = false;
		boolean alienisDream = false;
		
		// Made a "helper" string list so that in the future I may make special dreams based on aspect combos, without/before processing the single dreams.
		List<String> aspects = Lists.newArrayList();
		Iterator<BlockPos> iter = SpreaderLocations.iterator();
		while(iter.hasNext()) {
			BlockPos pos = iter.next();
			String aspect = p.world.getTileEntity(pos).getTileData().getString("containing");
			if(aspect == null) iter.remove();
			else aspects.add(aspect);
		}
		if(aspects.contains("Vacuos") && k.isResearchKnown("SLEEPCHAMBER") && k.isResearchKnown("HUMANDREAMS")) {
			vacuos = true;
			p.getCapability(PlayerDataProvider.PLAYERDATA, null).addString("vacuos", true);
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("vacuos"), (EntityPlayerMP)p);
		}
		if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("vacuos") && aspects.contains("Alienis")) {
			alienisDream = true;
		}
		
		for(int i = 0; i < SpreaderLocations.size(); i++) {
			BlockPos pos = SpreaderLocations.get(i);
			String aspect = aspects.get(i);
			if(pos == null) break;
			if(aspect == null) continue;
			
			boolean emptySpreader = processDream(aspect, k, p);
			if(emptySpreader) increaseTimesDreamt = true;
			
			if(emptySpreader) {
				IBlockState b = p.world.getBlockState(pos);
				p.world.getTileEntity(pos).getTileData().removeTag("containing");
				p.world.setBlockState(pos, b.getBlock().getStateFromMeta(b.getBlock().getMetaFromState(p.world.getBlockState(pos))-5), 2);
				unlockResearch(p, aspect.toLowerCase()); 
			}
		}
		if(increaseTimesDreamt) p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.TIMESDREAMT, p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.TIMESDREAMT, 0, false)+1, false);
		//if(!alienisDream) BTVPacketHandler.INSTANCE.sendTo(new MessageOpenGuiToClient(Guis.GuiEmpty), (EntityPlayerMP)p);
	}
	
	private static boolean processDream(String aspect, IPlayerKnowledge k, EntityPlayer p) {
		switch (aspect) {
			case "Aer": return dreamWeight(new int[] {7, 1, 8, 2, 2, 12}, k, p);
			case "Ignis": return dreamWeight(new int[] {7, 4, 3, 9, 7, 2}, k, p);
			case "Ordo": return dreamWeight(new int[] {4, 2, 12, 9, 3, 2}, k, p);	
			case "Perditio": return dreamWeight(new int[] {2, 10, 1, 3, 8, 8}, k, p);
			case "Terra": return dreamWeight(new int[] {7, 2, 5, 9, 9, 0}, k, p);
			case "Aqua": return dreamWeight(new int[] {4, 13, 8, 2, 1, 4}, k, p);
			case "Metallum": return scanGround(p, true, p.world);
			case "Vitreus": return scanGround(p, false, p.world);
			case "Tenebrae": return searchInnsmouth(p, p.world, p.world.rand);
			case "Cognitio": return searchVillage(p, p.world);
			case "Vinculum": return extendEffects(p, p.world);
			case "Permutatio": return changeEffects(p, p.world);
			case "Potentia": return amplifyEffects(p, p.world);
			case "Humanus": return searchPlayer(p, p.world);
			case "Instrumentum": return getPlayerItem(p, p.world);
			case "Vacuos": return true;
			case "Alienis": return contactUnknown(p, k, p.world);
			case "Bestia": return HigherDreams.findAnimal(p, p.world);
			case "Mortuus": return HigherDreams.playerDeath(p, p.world);
			default: dreamWeight(new int[] {9, 2, 1, 2, 1, 2}, k, p);	// TODO: Add "return" here
		}
		return false;
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
	
	// ***************************************** BASIC KNOWLEDGE DREAMS ***************************************** \\
	
	private static boolean dreamWeight(int[] knowledge, IPlayerKnowledge k, EntityPlayer p) {
		int lvl = getDreamingGodLevel(p);
		if(lvl >= 1) {
			int max = 0;
			int maxPos = 0;
			for(int i = 0; i < 6; i++) {
				if(max < knowledge[i]) {
					max = knowledge[i];
					maxPos = i;
				}
			}
			knowledge[maxPos] += 8;
			if(p.world.rand.nextInt(50 - 4 * lvl) == 0)
				k.addKnowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory(getCategoryName(maxPos)), 16);
		}
		int r = p.world.rand.nextInt(/*getDreamingGodLevel(p) >= 1 ? 32 : */40);
		boolean didUse = true;
		int sum = 0;
		for(int i = 0; i < 6; i++) {
			sum += knowledge[i];
			if(r < sum) {
				k.addKnowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory(getCategoryName(i)), getKnowledgeByLevel(lvl));
				break;
			}
		}
		if(r >= sum) didUse = false;
		return didUse;
		//ThaumcraftApi.internalMethods.addKnowledge(p, EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("BEYOND_THE_VEIL"), 1);
	}
	
	private static String getCategoryName(int index) {
		switch(index) {
			case 0: return "BASICS";
			case 1: return "AlCHEMY";
			case 2: return "INFUSION";
			case 3: return "ARTIFICE";
			case 4: return "GOLEMANCY";
			default: return "AUROMANCY";
		}
	}
	
	private static int getKnowledgeByLevel(int lvl) {
		return 10 + 2 * lvl;
	}
	
	// ***************************************** SCAN GROUND DREAM ***************************************** \\
	
	
	/** Checks a 33*33 area centered on the player, down to bedrock. Counts the number of iron and gold ore blocks
	 * if isMetal is true, or the number of diamond and emerald ore blocks if false. It informs the player directly
	 * in chat of those numbers, as well as the y-level where most were found.
	 * 
	 * @param p The player who just woke up
	 * @param isMetal True for Metallum, false for Vitreus
	 * @param w The world
	 * 
	 * @return True if successful
	 * 
	 */
	private static boolean scanGround(EntityPlayer p, boolean isMetal, World w) {
		int[] tracker1 = new int[p.getPosition().getY()];
		int[] tracker2 = new int[p.getPosition().getY()];
		int sum1 = 0;
		int sum2 = 0;
		BlockPos coord1 = null;
		BlockPos coord2 = null;
		for(int y = p.getPosition().getY()-1; y>=0; y--) {
			for(int x = p.getPosition().getX()-16; x<p.getPosition().getX()+16; x++) {
				for(int z = p.getPosition().getX()-16; z<p.getPosition().getX()+16; z++) {
					IBlockState state = w.getBlockState(new BlockPos(x, y, z));
					if(isMetal) {
						// TODO: Get OreDictionary compatibility
						if(state == Blocks.IRON_ORE.getDefaultState()) {
							if(coord1 == null && getDreamingGodLevel(p) >= 1) coord1 = new BlockPos(x, y, z);
							tracker1[y]++;
							sum1++;
						}else if(state == Blocks.GOLD_ORE.getDefaultState()) {
							if(coord2 == null && getDreamingGodLevel(p) >= 1) coord2 = new BlockPos(x, y, z);
							tracker2[y]++;
							sum2++;
						}
					}else {
						// TODO: Get OreDictionary compatibility
						if(state == Blocks.DIAMOND_ORE.getDefaultState()) {
							if(coord1 == null && getDreamingGodLevel(p) >= 1) coord1 = new BlockPos(x, y, z);
							tracker1[y]++;
							sum1++;
						}else if(state == Blocks.EMERALD_ORE.getDefaultState()) {
							if(coord2 == null && getDreamingGodLevel(p) >= 1) coord2 = new BlockPos(x, y, z);
							tracker2[y]++;
							sum2++;
						}
					}
				}
			}
		}
		int max1 = 0;
		int highest1 = 0;
		for(int i = 0; i < tracker1.length; i++) {
			if(max1 < tracker1[i]) {
				max1 = tracker1[i];
				highest1 = i;
			}
		}
		
		int max2 = 0;
		int highest2 = 0;
		for(int i = 0; i < tracker2.length; i++) {
			if(max2 < tracker2[i]) {
				max2 = tracker2[i];
				highest2 = i;
			}
		}
		
		if(isMetal) {
				p.sendMessage(getGroundScanMessage("iron", sum1, highest1, coord1));
				p.sendMessage(getGroundScanMessage("gold", sum2, highest2, coord2));
			}else {
				p.sendMessage(getGroundScanMessage("diamond", sum1, highest1, coord1));
				p.sendMessage(getGroundScanMessage("emerald", sum2, highest2, coord2));
			}
		return true;
	}
	
	private static TextComponentTranslation getGroundScanMessage(String ore, int sum, int highest, BlockPos coord) {
		return new TextComponentTranslation(String.format("dreams.groundscan.%s", ore), new Object[] {Integer.valueOf(sum), getGroundScanConcentration(highest), getGroundScanBlockMessage(coord)});
	}
	
	private static String getGroundScanConcentration(int highest) {
		if(highest > 0)
			return new TextComponentTranslation("dreams.groundscan.greatestconcentration", new Object[] {Integer.valueOf(highest)}).getUnformattedComponentText();
		return "";
	}
	
	private static String getGroundScanBlockMessage(BlockPos coord) {
		if(coord != null)
			return new TextComponentTranslation("dreams.groundscan.block", new Object[] {Integer.valueOf(coord.getX()), Integer.valueOf(coord.getY()), Integer.valueOf(coord.getZ())}).getUnformattedComponentText();
		return "";
	}
	
	
	// ***************************************** SEARCH BIOME/STRUCTURE DREAMS ***************************************** \\
	
	/** Attempts to locate a Voided Biome within a 1200 block radius. The presence of a hamlet is not guaranteed. 
	 * It then informs the player of the coordinates, if they aren't null.
	 * 
	 * @param p The player who just woke up
	 * @param w The world
	 * @param r A Random, usually world.rand
	 * 
	 * @return True if successful
	 */
	private static boolean searchInnsmouth(EntityPlayer p, World w, Random r) {
		if(!youDontKnowDream(p, "alienis")) return false;
		boolean flag = false;
		BlockPos pos = WorldHelper.findClosestBiomeOfType(BiomeRegistry.innsmouth, p.getPosition(), w, 6000);
		if(pos != null) {
			p.sendMessage(new TextComponentTranslation("dreams.biomesearch.innsmouth", new Object[] {pos.getX(), pos.getZ()}));			
			flag = true;
		} else {
			p.sendMessage(new TextComponentTranslation("dreams.biomesearch.fail"));
		}
		
		if(hasDreamtOfVoid(p)) {
			BlockPos hamletPos = HamletList.get(w).getClosestHamlet(p.getPosition());
			if(hamletPos != null) {
				p.sendMessage(new TextComponentTranslation("dreams.biomesearch.hamlet", new Object[] {Integer.valueOf(hamletPos.getX()), Integer.valueOf(hamletPos.getZ())}));
				flag = true;
			}
		}
		
		return flag;
		
	
	}
	
	/** Attempts to locate a Village, using the same method of the /locate command.
	 * It then informs the player of the coordinates, if they aren't null.
	 * 
	 * @param p The player who just woke up
	 * @param w The world
	 * 
	 * @return True if successful
	 */
	private static boolean searchVillage(EntityPlayer p, World w) {
		BlockPos pos = w.findNearestStructure("Village", p.getPosition(), false);
		if(pos != null) p.sendMessage(new TextComponentTranslation("dreams.villagesearch.success", new Object[] {pos.getX(), pos.getZ()}));
		else p.sendMessage(new TextComponentTranslation("dreams.villagesearch.fail"));
		
		return true;
	}
	
	// ***************************************** MULTIPLAYER DREAMS ***************************************** \\
	
	/** Searches a player other than the dreamer, and, if found, tells the dreamer either the x or z
	 *  coordinate of the other player. 
	 * 
	 * @param p The player who just woke up
	 * @param w The world
	 * 
	 * @return True if successful
	 */
	private static boolean searchPlayer(EntityPlayer p, World w) {
		// if(p.getCapability(DGProvider.LEVEL_CAP, null).getLevel() < ??) // For later use
		if(!youDontKnowDream(p, "metallum")) return false;
		List<EntityPlayerMP> list = copyPlayerList(w.getMinecraftServer().getPlayerList().getPlayers(), (EntityPlayerMP)p);
		if(!list.isEmpty()) {
			EntityPlayerMP target =	list.get(w.rand.nextInt(list.size()));
			DimensionType.getById(target.world.provider.getDimension()).getName();
			int attack = getDreamAttack(p, target);
			if(attack == 0)
				p.sendMessage(new TextComponentTranslation("dreams.playersearch.success", w.rand.nextBoolean() ? target.getPosition().getX() : target.getPosition().getZ()));
			else if(attack >= 1)
				p.sendMessage(new TextComponentTranslation("dreams.playersearch.success2", new Object[] {target.getPosition().getX(), target.getPosition().getZ()}));
			if(attack >= 2) 
				p.sendMessage(new TextComponentTranslation("dreams.playersearch.name", target.getName()));
			return true;
		}else {
			p.sendMessage(new TextComponentTranslation("dreams.playersearch.fail"));
			return false;
		}
	}
	
	/** Searches a player other than the dreamer, and, if found, tells the dreamer the item that
	 *  the other player is wielding, if they're wielding any.
	 * 
	 * @param p The player who just woke up
	 * @param w The world
	 * 
	 * @return True if successful
	 */
	private static boolean getPlayerItem(EntityPlayer p, World w) {
		if(!youDontKnowDream(p, "metallum")) return false;
		List<EntityPlayerMP> list = copyPlayerList(w.getMinecraftServer().getPlayerList().getPlayers(), (EntityPlayerMP)p);
		if(!list.isEmpty()) {
			EntityPlayerMP target =	list.get(w.rand.nextInt(list.size()));
			ItemStack stack = target.getHeldItemMainhand();
			if(stack != null && !stack.getDisplayName().equals("Air")) {
				p.sendMessage(new TextComponentTranslation("dreams.playeritem.success", stack.getDisplayName()));
			}else {
				p.sendMessage(new TextComponentTranslation("dreams.playeritem.mildsuccess"));
			}
			int attack = getDreamAttack(p, target);
			if(attack >= 1) {
				String name = target.getName();
				if(attack == 1)
					p.sendMessage(new TextComponentTranslation("dreams.playeritem.firstletter", name.substring(0, 1)));
				else
					p.sendMessage(new TextComponentTranslation("dreams.playersearch.name", name));
					
			}
			
			return true;
		}else {
			p.sendMessage(new TextComponentTranslation("dreams.playersearch.fail"));
			return false;
		}
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
		List<EntityPlayerMP> listOut = Lists.newArrayList();
		listIn.forEach(p -> {
			boolean flag = true;
			for(EntityPlayerMP player : excludedPlayers) {
				if(player.equals(p)) {
					flag = false;
					break;
				}
			}
			if(flag) listOut.add(p);
		});
		return listOut;
	}
	
	// ***************************************** EFFECT DREAMS ***************************************** \\
	
	/** Extends the duration of all effects on the player by 3000 ticks (= 150 seconds) plus 500 ticks (= 25 seconds) for each Dreaming God level
	 *  and for the Vacuos Dream.
	 * 
	 * @param p The player who just woke up
	 * @param w The world
	 * 
	 * @return True if successful
	 */
	private static boolean extendEffects(EntityPlayer p, World w) {
		if(!youDontKnowDream(p, "metallum")) return false;
		int lvl = getDreamLevel(p);
		Collection<PotionEffect> effects = p.getActivePotionEffects();
		effects.forEach(effect -> p.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration()+3000+500*lvl)));
		
		return true;
	}
	
	/** Increases the amplifier of all effects on the player by 1.
	 * 
	 * @param p The player who just woke up
	 * @param w The world
	 * 
	 * @return True if successful
	 */
	private static boolean amplifyEffects(EntityPlayer p, World w) {
		if(!youDontKnowDream(p, "metallum")) return false;
		int lvl = getDreamLevel(p);
		int increase = 1 + (lvl+2)/3;
		Collection<PotionEffect> effects = p.getActivePotionEffects();
		effects.forEach(effect -> p.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getPotion() == MobEffects.RESISTANCE ? Math.max(3, effect.getAmplifier() + increase) : effect.getAmplifier()+increase)));
		
		return true;
	}
	
	/** Finds all negative effects on the player (through Potion.isBadEffect()) and either changes them
	 *  into their positive counterpart (only Vanilla ones) or simply removes them
	 * 
	 * @param p The player who just woke up
	 * @param w The world
	 * 
	 * @return True if successful
	 */
	private static boolean changeEffects(EntityPlayer p, World w) {
		if(!youDontKnowDream(p, "metallum")) return false;
		int lvl = getDreamLevel(p);
		Collection<PotionEffect> effects = p.getActivePotionEffects();
		List<PotionEffect> negativeEffects = Lists.newArrayList();
		effects.forEach(effect -> {
			if(effect.getPotion().isBadEffect()) negativeEffects.add(effect);
		});
		negativeEffects.forEach(effect ->{
			Potion newPot = getPositiveCounterpart(effect.getPotion());
			if(newPot != null) p.addPotionEffect(new PotionEffect(getPositiveCounterpart(effect.getPotion()), effect.getDuration()+300*lvl, effect.getAmplifier()+lvl/4));
			p.removePotionEffect(effect.getPotion());
		});
		
		return true;
	}
	
	/** Provides the positive counterparts for some negative Vanilla effects, used by
	 *  changeEffects
	 * 
	 * @param p The player who just woke up
	 * @param w The world
	 * 
	 * @return True if successful
	 */
	private static Potion getPositiveCounterpart(Potion p) {
		if(p == MobEffects.BLINDNESS) return MobEffects.NIGHT_VISION;
		else if(p == MobEffects.HUNGER) return MobEffects.SATURATION;
		else if(p == MobEffects.POISON) return MobEffects.REGENERATION;
		else if(p == MobEffects.MINING_FATIGUE) return MobEffects.HASTE;
		else if(p == MobEffects.WITHER) return MobEffects.REGENERATION;
		else if(p == MobEffects.SLOWNESS) return MobEffects.SPEED;
		else if(p == MobEffects.WEAKNESS) return MobEffects.STRENGTH;
		else if(p == MobEffects.UNLUCK) return MobEffects.LUCK;
		return null;
	}
	
	// ***************************************** STORY DREAMS ***************************************** \\
	
	private static boolean contactUnknown(EntityPlayer p, IPlayerKnowledge k, World w) {
		if(!knowsDream(p, "vacuos")) {
			p.sendMessage(new TextComponentTranslation("dreams.alienis.almostthere1"));
			p.sendMessage(new TextComponentTranslation("dreams.alienis.almostthere2"));
			return false;
		}
		boolean knowsAlienis = knowsDream(p, "alienis");
		if(!hasDreamtOfVoid(p) /*|| knowsAlienis*/) {
			if(!knowsAlienis) {
				p.sendMessage(new TextComponentTranslation("dreams.alienis.needvacuos"));
				return false;
			} else {
				return searchStronghold(p, w);
			}
		}
		BTVPacketHandler.INSTANCE.sendTo(new MessageOpenGuiToClient(Guis.GuiAlienisDream), (EntityPlayerMP)p);
		return true;
	}
	
	private static boolean searchStronghold(EntityPlayer p, World w) {
		BlockPos pos = w.findNearestStructure("Stronghold", p.getPosition(), false);
		if(pos != null) p.sendMessage(new TextComponentTranslation("dreams.alienissearch.success", new Object[] {pos.getX(), pos.getZ()}));
		else p.sendMessage(new TextComponentTranslation("dreams.alienissearch.fail"));
		
		return true;
	}
	
	
	// ***************************************** HELPER METHODS ***************************************** \\
	
	
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
		return lvl;
	}
	
	/** Returns the player's Dreaming God level *without* Vacuos bonus.
	 */
	public static int getDreamingGodLevel(EntityPlayer p) {
		return Math.min(p.getCapability(DGProvider.LEVEL_CAP, null).getLevel(), DGWorshipHelper.MAX_LEVEL);
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
		if(flag) p.getCapability(PlayerDataProvider.PLAYERDATA, null).removeString("vacuos");
		return flag;
	}
	
	
	
}

