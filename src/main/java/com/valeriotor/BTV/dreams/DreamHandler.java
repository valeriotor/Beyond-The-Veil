package com.valeriotor.BTV.dreams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.blocks.FumeSpreader;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.world.BiomeRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
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
		List<BlockPos> SpreaderLocations = checkBlocks(p.world,p.getPosition(), BlockRegistry.FumeSpreader.getDefaultState().withProperty(FumeSpreader.ISFULL, true), times);
		boolean increaseTimesDreamt = false;
		
		// Made a "helper" string list so that in the future I may make special dreams based on aspect combos, without/before processing the single dreams.
		List<String> aspects = Lists.newArrayList();
		Iterator<BlockPos> iter = SpreaderLocations.iterator();
		while(iter.hasNext()) {
			BlockPos pos = iter.next();
			String aspect = p.world.getTileEntity(pos).getTileData().getString("containing");
			if(aspect == null) iter.remove();
			else aspects.add(aspect);
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
		
		if(increaseTimesDreamt) p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger("timesDreamt", p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger("timesDreamt", 0, false)+1, false);
		
	}
	
	private static boolean processDream(String aspect, IPlayerKnowledge k, EntityPlayer p) {
		switch (aspect) {
			case "Aer": return dreamWeight(7, 1, 8, 2, 2, 12, k, p);
			case "Ignis": return dreamWeight(7, 4, 3, 9, 7, 2, k, p);
			case "Ordo": return dreamWeight(4, 2, 12, 9, 3, 2, k, p);	
			case "Perditio": return dreamWeight(2, 10, 1, 3, 8, 8, k, p);
			case "Terra": return dreamWeight(7, 2, 5, 9, 9, 0, k, p);
			case "Aqua": return dreamWeight(4, 13, 8, 2, 1, 4, k, p);
			case "Metallum": return scanGround(p, true, p.world);
			case "Vitreus": return scanGround(p, false, p.world);
			case "Tenebrae": return searchInnsmouth(p, p.world, p.world.rand);
			case "Cognitio": return searchVillage(p, p.world);
			case "Vinculum": return extendEffects(p, p.world);
			case "Permutatio": return changeEffects(p, p.world);
			case "Potentia": return amplifyEffects(p, p.world);
			case "Humanus": return searchPlayer(p, p.world);
			case "Instrumentum": return getPlayerItem(p, p.world);
			default: dreamWeight(90, 2, 1, 2, 1, 2, k, p);	
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
	
	private static boolean dreamWeight(int bas, int alc, int inf, int art, int gol, int aur, IPlayerKnowledge k, EntityPlayer p) {
		int r = new Random().nextInt(40);
		boolean didUse = true;
		if(r<bas) {
			k.addKnowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("BASICS"), 10);
		}else if(r<alc+bas) {
			k.addKnowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 10);
		}else if(r<inf+alc+bas) {
			k.addKnowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("INFUSION"), 10);
		}else if(r<art+inf+alc+bas) {
			k.addKnowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ARTIFICE"), 10);
		}else if(r<gol+art+inf+alc+bas) {
			k.addKnowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("GOLEMANCY"), 10);
		}else if(r<aur+gol+art+inf+alc+bas) {
			k.addKnowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("AUROMANCY"), 10);
		}else if(r<40) {
			didUse=false;
		}else {
			didUse=false;
		}
		return didUse;
		//ThaumcraftApi.internalMethods.addKnowledge(p, EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("BEYOND_THE_VEIL"), 1);
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
		for(int y = p.getPosition().getY()-1; y>=0; y--) {
			for(int x = p.getPosition().getX()-16; x<p.getPosition().getX()+16; x++) {
				for(int z = p.getPosition().getX()-16; z<p.getPosition().getX()+16; z++) {
					IBlockState state = w.getBlockState(new BlockPos(x, y, z));
					if(isMetal) {
						// TODO: Get OreDictionary compatibility
						if(state == Blocks.IRON_ORE.getDefaultState()) {
							tracker1[y]++;
							sum1++;
						}else if(state == Blocks.GOLD_ORE.getDefaultState()) {
							tracker2[y]++;
							sum2++;
						}
					}else {
						// TODO: Get OreDictionary compatibility
						if(state == Blocks.DIAMOND_ORE.getDefaultState()) {
							tracker1[y]++;
							sum1++;
						}else if(state == Blocks.EMERALD_ORE.getDefaultState()) {
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
			p.sendMessage(new TextComponentTranslation("dreams.groundscan.iron",new Object[] {Integer.valueOf(sum1), highest1 > 0 ? (new TextComponentTranslation("dreams.groundscan.greatestconcentration", new Object[] {Integer.valueOf(highest1)})).getUnformattedComponentText() : ""}));
			p.sendMessage(new TextComponentTranslation("dreams.groundscan.gold", new Object[] {Integer.valueOf(sum2), highest2 > 0 ? (new TextComponentTranslation("dreams.groundscan.greatestconcentration", new Object[] {Integer.valueOf(highest2)})).getUnformattedComponentText() : ""}));
			}else {
			p.sendMessage(new TextComponentTranslation("dreams.groundscan.diamond",new Object[] {Integer.valueOf(sum1), highest1 > 0 ? (new TextComponentTranslation("dreams.groundscan.greatestconcentration", new Object[] {Integer.valueOf(highest1)})).getUnformattedComponentText() : ""}));
			p.sendMessage(new TextComponentTranslation("dreams.groundscan.emerald", new Object[] {Integer.valueOf(sum2), highest2 > 0 ? (new TextComponentTranslation("dreams.groundscan.greatestconcentration", new Object[] {Integer.valueOf(highest2)})).getUnformattedComponentText() : ""}));
			}
		return true;
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
		final BiomeProvider provider = w.getBiomeProvider();
		List<Biome> biomes = new ArrayList<Biome>();
		biomes.add(BiomeRegistry.innsmouth);
		BlockPos pos = provider.findBiomePosition(p.getPosition().getX(), p.getPosition().getZ(), 1200, biomes, r);
		if(pos != null) {
			p.sendMessage(new TextComponentTranslation("dreams.biomesearch.innsmouth", new Object[] {pos.getX(), pos.getZ()}));
			return true;
		}else {
			p.sendMessage(new TextComponentTranslation("dreams.biomesearch.fail"));
			return false;
		}
		
	
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
		List<EntityPlayerMP> list = w.getPlayers(EntityPlayerMP.class, player -> !player.equals(p));
		if(list.size() > 0) {
			EntityPlayerMP target =	list.get(w.rand.nextInt(list.size()));
			p.sendMessage(new TextComponentTranslation("dreams.playersearch.success", w.rand.nextBoolean() ? target.getPosition().getX() : target.getPosition().getZ()));
			return true;
		}else {
			if(!knowsDream(p, "metallum")) p.sendMessage(new TextComponentTranslation("dreams.maybeinthefuture"));
			else {
				p.sendMessage(new TextComponentTranslation("dreams.playersearch.fail"));
				return true;
			}
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
		List<EntityPlayerMP> list = w.getPlayers(EntityPlayerMP.class, player -> !player.equals(p));
		if(list.size() > 0) {
			EntityPlayerMP target =	list.get(w.rand.nextInt(list.size()));
			ItemStack stack = target.getHeldItemMainhand();
			if(stack != null && !stack.getDisplayName().equals("Air")) {
				p.sendMessage(new TextComponentTranslation("dreams.playeritem.success", stack.getDisplayName()));
			}else {
				p.sendMessage(new TextComponentTranslation("dreams.playeritem.mildsuccess"));
			}
			
			return true;
		}else {
			if(!knowsDream(p, "metallum")) p.sendMessage(new TextComponentTranslation("dreams.maybeinthefuture"));
			else {
				p.sendMessage(new TextComponentTranslation("dreams.playersearch.fail"));
				return true;
			}
			return false;
		}
	}
	
	// ***************************************** EFFECT DREAMS ***************************************** \\
	
	/** Extends the duration of all effects on the player by 3000 ticks (= 150 seconds).
	 * 
	 * @param p The player who just woke up
	 * @param w The world
	 * 
	 * @return True if successful
	 */
	private static boolean extendEffects(EntityPlayer p, World w) {
		Collection<PotionEffect> effects = p.getActivePotionEffects();
		effects.forEach(effect -> p.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration()+3000)));
		
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
		Collection<PotionEffect> effects = p.getActivePotionEffects();
		effects.forEach(effect -> p.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getAmplifier()+1)));
		
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
		Collection<PotionEffect> effects = p.getActivePotionEffects();
		List<PotionEffect> negativeEffects = Lists.newArrayList();
		effects.forEach(effect -> {
			if(effect.getPotion().isBadEffect()) negativeEffects.add(effect);
		});
		negativeEffects.forEach(effect ->{
			Potion newPot = getPositiveCounterpart(effect.getPotion());
			if(newPot != null) p.addPotionEffect(new PotionEffect(getPositiveCounterpart(effect.getPotion()), effect.getDuration(), effect.getAmplifier()));
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
			ThaumcraftApi.internalMethods.progressResearch(p, String.format("%sDream", aspect));
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
	
	/** Checks if the player already has the "research" (not a true entry) corresponding to that aspect.
	 *
	 * @param p The player who just woke up
	 * @param aspect The aspect that influenced the dream
	 * 
	 * @return True if the dream's known.
	 */
	private static boolean knowsDream(EntityPlayer p, String aspect) {
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
		if(k.isResearchKnown(aspect+"Dream")) return true;
		return false;
	}
}

