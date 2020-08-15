package com.valeriotor.beyondtheveil.items;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.gui.container.GuiContainerHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlackMirror extends ModItem{

	public ItemBlackMirror(String name) {
		super(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer p, EnumHand handIn) {
		if(worldIn.isRemote) {
			BlockPos pos = p.getPosition();
			p.openGui(BeyondTheVeil.instance, GuiContainerHandler.BLACK_MIRROR, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, p.getHeldItem(handIn));
	}

}
