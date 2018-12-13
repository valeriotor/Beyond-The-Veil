package com.valeriotor.BTV.dreams;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.blocks.FumeSpreader;
import com.valeriotor.BTV.capabilities.FlagProvider;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageLocalizedMessage;
import com.valeriotor.BTV.world.BiomeRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.research.ResearchCategories;

public class DreamHandler {
	
	public static void chooseDream(EntityPlayer p, IPlayerKnowledge k) {
		BlockPos SpreaderLocation = checkBlocks(p.world,p.getPosition(), BlockRegistry.FumeSpreader.getDefaultState().withProperty(FumeSpreader.ISFULL, true));
		if(SpreaderLocation != null) {
			String aspect = p.world.getTileEntity(SpreaderLocation).getTileData().getString("containing");
			if(aspect.equals("Aer")) {
			dreamWeight(7, 1, 8, 2, 2, 12, k, p, SpreaderLocation);
			}else if(aspect.equals("Ignis")) {
			dreamWeight(7, 4, 3, 9, 7, 2, k, p, SpreaderLocation);	
			}else if(aspect.equals("Ordo")) {
			dreamWeight(4, 2, 12, 9, 3, 2, k, p, SpreaderLocation);	
			}else if(aspect.equals("Perditio")) {
			dreamWeight(2, 10, 1, 3, 8, 8, k, p, SpreaderLocation);
			}else if(aspect.equals("Terra")) {
			dreamWeight(7, 2, 5, 9, 9, 0, k, p, SpreaderLocation);
			}else if(aspect.equals("Aqua")) {
			dreamWeight(4, 13, 8, 2, 1, 4, k, p, SpreaderLocation);
			}else if(aspect.equals("Metallum")) {
			scanGround(p, true, p.world, SpreaderLocation);
			}else if(aspect.equals("Vitreus")) {
			scanGround(p, false, p.world, SpreaderLocation);
			}else if(aspect.equals("Tenebrae")) {
			searchInnsmouth(p, p.world, p.world.rand, SpreaderLocation);
			}else if(!aspect.isEmpty()) {
			dreamWeight(90, 2, 1, 2, 1, 2, k, p, SpreaderLocation);	
			}
			
			p.getCapability(FlagProvider.FLAG_CAP, null).setTimesDreamt(p.getCapability(FlagProvider.FLAG_CAP, null).getTimesDreamt()+1);
		}
	}
	
	public static BlockPos checkBlocks(World world, BlockPos playerPos, IBlockState state) {
		for(int x = -2; x<3; x++) {
			for(int z = -2; z<3; z++) {
				for(int y = -3; y<2; y++) {
					BlockPos posCheck = playerPos.add(x, y, z);
					if(world.getBlockState(posCheck).getBlock() != null) {
						if(world.getBlockState(posCheck) == state) {
							return posCheck;
						}
					}
				}
			}
		}
		return null;
	}
	
	private static void dreamWeight(int bas, int alc, int inf, int art, int gol, int aur, IPlayerKnowledge k, EntityPlayer p, BlockPos s) {
		int r = new Random().nextInt(100);
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
		}else if(r<50) {
			//p.addItemStackToInventory(new ItemStack(Items.ARROW,1));
			didUse=false;
		}else {
			didUse=false;
		}
		if(didUse) {
			IBlockState b = p.world.getBlockState(s);
			p.world.getTileEntity(s).getTileData().removeTag("containing");
			p.world.setBlockState(s, b.getBlock().getStateFromMeta(b.getBlock().getMetaFromState(p.world.getBlockState(s))-5), 2);
			
		}
	}
	
	
	/** Checks a 33*33 area centered on the player, down to bedrock. Counts the number of iron and gold ore blocks
	 * if isMetal is true, or the number of diamond and emerald ore blocks if false. It informs the player directly
	 * in chat of those numbers, as well as the y-level where most were found.
	 * 
	 * @param p The player who just woke up
	 * @param isMetal True for Metallum, false for Vitreus
	 * @param w The world
	 * @param s The location of the Fume Spreader, so that its content may be deleted.
	 * 
	 * @author Valeriotor
	 * 
	 */
	private static void scanGround(EntityPlayer p, boolean isMetal, World w, BlockPos s) {
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
			BTVPacketHandler.INSTANCE.sendTo(new MessageLocalizedMessage(Integer.toString(sum1) + ":|dreams.groundscan.iron:" + (highest1 > 0 ? (":|dreams.groundscan.greatestconcentration:") + Integer.toString(highest1) : "")), (EntityPlayerMP) p);
			BTVPacketHandler.INSTANCE.sendTo(new MessageLocalizedMessage(Integer.toString(sum2) + ":|dreams.groundscan.gold:" + (highest2 > 0 ? (":|dreams.groundscan.greatestconcentration:") + Integer.toString(highest2) : "")), (EntityPlayerMP) p);
		}else {
			BTVPacketHandler.INSTANCE.sendTo(new MessageLocalizedMessage(Integer.toString(sum1) + ":|dreams.groundscan.diamond:" + (highest1 > 0 ? (":|dreams.groundscan.greatestconcentration:") + Integer.toString(highest1) : "")), (EntityPlayerMP) p);
			BTVPacketHandler.INSTANCE.sendTo(new MessageLocalizedMessage(Integer.toString(sum2) + ":|dreams.groundscan.emerald:" + (highest2 > 0 ? (":|dreams.groundscan.greatestconcentration:") + Integer.toString(highest2) : "")), (EntityPlayerMP) p);
		}
		
		IBlockState b = p.world.getBlockState(s);
		p.world.getTileEntity(s).getTileData().removeTag("containing");
		p.world.setBlockState(s, b.getBlock().getStateFromMeta(b.getBlock().getMetaFromState(p.world.getBlockState(s))-5), 2);
	}
	
	private static void searchInnsmouth(EntityPlayer p, World w, Random r, BlockPos s) {
		final BiomeProvider provider = w.getBiomeProvider();
		List<Biome> biomes = new ArrayList<Biome>();
		biomes.add(BiomeRegistry.innsmouth);
		BlockPos pos = provider.findBiomePosition(p.getPosition().getX(), p.getPosition().getZ(), 1200, biomes, r);
		if(pos != null) {
			BTVPacketHandler.INSTANCE.sendTo(new MessageLocalizedMessage("|dreams.biomesearch.innsmouth:" + "x = " + pos.getX() + ", z =" + pos.getZ()), (EntityPlayerMP) p);
			//p.sendMessage(new TextComponentString(BeyondTheVeil.proxy.localizeMessage("dreams.biomesearch.innsmouth") + "x = " + pos.getX() + ", z =" + pos.getZ()));
		}else {
			BTVPacketHandler.INSTANCE.sendTo(new MessageLocalizedMessage("|dreams.biomesearch.fail:"), (EntityPlayerMP) p);
			//p.sendMessage(new TextComponentString(BeyondTheVeil.proxy.localizeMessage("dreams.biomesearch.fail")));
		}
		
		IBlockState b = p.world.getBlockState(s);
		p.world.getTileEntity(s).getTileData().removeTag("containing");
		p.world.setBlockState(s, b.getBlock().getStateFromMeta(b.getBlock().getMetaFromState(p.world.getBlockState(s))-5), 2);
	
	}
}

