package com.valeriotor.BTV.util;

import java.util.List;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.items.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class SacrificeHelper extends TileEntity{
	
	public static boolean checkStructure(EntityPlayer p, BlockPos pos) {
		World w = p.world;
		if(w.getBlockState(pos).getBlock() != BlockRegistry.BlockSacrificeAltar) return false;
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				if(x == 0 && z == 0) continue;
				IBlockState state = w.getBlockState(pos.add(x, 0, z));
				if(state.getBlock() != Blocks.PRISMARINE || state.getProperties().get(BlockPrismarine.VARIANT) != BlockPrismarine.EnumType.DARK) {
					incompleteStructureMessage(p);
					return false;
				}
			}
		}
		for(EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos pos1 = pos.offset(facing, 2).offset(facing.rotateYCCW(), 2).up();
			IBlockState state = w.getBlockState(pos1);
			if(state.getBlock() != Blocks.PRISMARINE || state.getProperties().get(BlockPrismarine.VARIANT) != BlockPrismarine.EnumType.BRICKS) {
				incompleteStructureMessage(p);
				return false;
			}
			if(w.getBlockState(pos1.up()).getBlock() != BlockRegistry.BlockHeart) {
				incompleteStructureMessage(p);
				return false;
			}
		}
		return true;
	}
	
	private static void incompleteStructureMessage(EntityPlayer p) {
		p.sendMessage(new TextComponentTranslation("interact.sacrificial_altar.incomplete"));
	}
	
	public static void doEffect(EntityPlayer p, BlockPos pos) {
		List<EntityItem> items = p.world.getEntities(EntityItem.class, e -> e.getDistanceSq(pos) < 4);
		boolean used = false;
		if(!items.isEmpty()) {
			for(EntityItem item : items) {
				ItemStack stack = useItem(p, pos, item.getItem());
				if(stack != null) {
					EntityItem ent = new EntityItem(p.world, pos.getX(), pos.getY() + 1, pos.getZ(), stack);
					p.world.spawnEntity(ent);
					used = true;
					break;
				}
			}
		} else {
			p.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 60*20, 9, false, false));
			used = true;
		}
		if(used) removeHearts(p.world, pos);
	}
	
	public static void doEffectKnife(EntityPlayer p, ItemStack stack, BlockPos pos) {
		if(stack.getItem() == Items.AIR) {
			p.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 60*20, 9, false, false));
			removeHearts(p.world, pos);
		} else {
			ItemStack newStack = useItem(p, pos, stack);
			if(newStack != null) {
				p.addItemStackToInventory(newStack);
				removeHearts(p.world, pos);
			}
		}
	}
	
	public static ItemStack useItem(EntityPlayer p, BlockPos pos, ItemStack item) {
		if(Block.getBlockFromItem(item.getItem()) == Blocks.PRISMARINE) {
			item.shrink(1);
			return new ItemStack(ItemRegistry.coral_staff);
		} else if(getBricks(Block.getBlockFromItem(item.getItem())) != null) {
			ItemStack stack = new ItemStack(getBricks(Block.getBlockFromItem(item.getItem())), item.getCount());;
			item.setCount(0);
			return stack;
		}
		return null;
	}
	
	public static void removeHearts(World w, BlockPos pos) {
		for(EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos pos1 = pos.offset(facing, 2).offset(facing.rotateYCCW(), 2).offset(EnumFacing.UP, 2);
			if(w.getBlockState(pos1).getBlock() == BlockRegistry.BlockHeart) {
				w.setBlockState(pos1, Blocks.AIR.getDefaultState());
			}
		}
	}
	
	public static Block getBricks(Block b) {
		if(b == Blocks.STONEBRICK) return BlockRegistry.BlockBloodBrick;
		if(b == Blocks.STONE_BRICK_STAIRS) return BlockRegistry.BlockBloodBrickStairs;
		if(b == Blocks.STONE_SLAB) return BlockRegistry.SlabBloodHalf;
		return null;
	}
	
}
