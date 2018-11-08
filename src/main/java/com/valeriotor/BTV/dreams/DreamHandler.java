package com.valeriotor.BTV.dreams;

import java.util.Random;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.blocks.FumeSpreader;
import com.valeriotor.BTV.research.BTVTab;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.internal.IInternalMethodHandler;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;

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
			}else if(!aspect.isEmpty()) {
			dreamWeight(90, 2, 1, 2, 1, 2, k, p, SpreaderLocation);	
			}
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
	
	public static void dreamWeight(int bas, int alc, int inf, int art, int gol, int aur, IPlayerKnowledge k, EntityPlayer p, BlockPos s) {
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
}

