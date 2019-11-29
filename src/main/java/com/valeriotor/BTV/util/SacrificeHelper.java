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
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
					return false;
				}
			}
		}
		for(EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos pos1 = pos.offset(facing, 2).offset(facing.rotateYCCW(), 2).up();
			IBlockState state = w.getBlockState(pos1);
			if(state.getBlock() != Blocks.PRISMARINE || state.getProperties().get(BlockPrismarine.VARIANT) != BlockPrismarine.EnumType.BRICKS) {
				return false;
			}
			if(w.getBlockState(pos1.up()).getBlock() != BlockRegistry.BlockHeart)
				return false;
		}
		return true;
	}
	
	public static void doEffect(EntityPlayer p, BlockPos pos) {
		List<EntityItem> items = p.world.getEntities(EntityItem.class, e -> e.getDistanceSq(pos) < 4);
		if(!items.isEmpty()) {
			for(EntityItem item : items) {
				if(useItem(p, pos, item.getItem().getItem())) {
					item.getItem().shrink(1);
					break;
				}
			}
		} else {
			p.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 60*20, 9, false, false));
		}
		removeHearts(p.world, pos);
	}
	
	public static boolean useItem(EntityPlayer p, BlockPos pos, Item item) {
		if(Block.getBlockFromItem(item) == Blocks.PRISMARINE) {
			EntityItem coral_staff = new EntityItem(p.world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ItemRegistry.coral_staff));
			p.world.spawnEntity(coral_staff);
			return true;
		}
		return false;
	}
	
	public static void removeHearts(World w, BlockPos pos) {
		for(EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos pos1 = pos.offset(facing, 2).offset(facing.rotateYCCW(), 2).offset(EnumFacing.UP, 2);
			if(w.getBlockState(pos1).getBlock() == BlockRegistry.BlockHeart) {
				w.setBlockState(pos1, Blocks.AIR.getDefaultState());
			}
		}
	}
	
}
