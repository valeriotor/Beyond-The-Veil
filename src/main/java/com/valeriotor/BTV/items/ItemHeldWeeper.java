package com.valeriotor.BTV.items;

import com.valeriotor.BTV.entities.EntityWeeper;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHeldWeeper extends Item {
	
	public ItemHeldWeeper(String name) {
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(References.BTV_TAB);
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(facing != EnumFacing.UP) return EnumActionResult.FAIL;
		if(w.isRemote) return EnumActionResult.SUCCESS;
		boolean spineless = ItemHelper.checkBooleanTag(player.getHeldItem(hand), "spineless", false);
		boolean heartless = ItemHelper.checkBooleanTag(player.getHeldItem(hand), "heartless", false);
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				IBlockState b = w.getBlockState(pos.add(x, 1, z));
				if(b.causesSuffocation() || b.isFullBlock()) {
					return EnumActionResult.FAIL;
				}
			}
		}
		EntityWeeper weeper = new EntityWeeper(w, spineless);
		weeper.setPosition(pos.getX(), 1+pos.getY(), pos.getZ());
		weeper.setHeartless(heartless);
		w.spawnEntity(weeper);
		player.getHeldItem(hand).shrink(1);
		return super.onItemUse(player, w, pos, hand, facing, hitX, hitY, hitZ);
	}
	
}
