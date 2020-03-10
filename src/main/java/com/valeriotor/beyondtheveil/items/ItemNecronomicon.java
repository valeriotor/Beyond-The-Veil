package com.valeriotor.beyondtheveil.items;

import com.valeriotor.beyondtheveil.BeyondTheVeil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemNecronomicon extends ModItem{

	public ItemNecronomicon(String name) {
		super(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		BlockPos pos = player.getPosition();
		if(worldIn.isRemote) {
			player.openGui(BeyondTheVeil.instance, 5, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return super.onItemRightClick(worldIn, player, handIn);
	}

}
