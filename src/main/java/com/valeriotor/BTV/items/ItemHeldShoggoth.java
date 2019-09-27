package com.valeriotor.BTV.items;

import com.valeriotor.BTV.entities.EntityShoggoth;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHeldShoggoth extends ModItem{

	public ItemHeldShoggoth(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(facing != EnumFacing.UP) return EnumActionResult.FAIL;
		if(w.isRemote) return EnumActionResult.SUCCESS;
		
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				IBlockState b = w.getBlockState(pos.add(x, 1, z));
				if(b.causesSuffocation() || b.isFullBlock()) {
					return EnumActionResult.FAIL;
				}
			}
		}
		EntityShoggoth shoggoth = new EntityShoggoth(w, player);
		shoggoth.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
		w.spawnEntity(shoggoth);
		
		return EnumActionResult.SUCCESS;
	}

}
