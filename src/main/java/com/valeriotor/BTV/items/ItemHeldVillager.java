package com.valeriotor.BTV.items;

import java.util.List;

import com.valeriotor.BTV.entities.EntityCrawlingVillager;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHeldVillager extends Item {
	
	public ItemHeldVillager(String name) {
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
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				IBlockState b = w.getBlockState(pos.add(x, 1, z));
				if(b.causesSuffocation() || b.isFullBlock()) {
					System.out.println("Fail..");
					return EnumActionResult.FAIL;
				}
			}
		}
		EntityCrawlingVillager worm = new EntityCrawlingVillager(w, !spineless);
		worm.setPosition(pos.getX(), 1+pos.getY(), pos.getZ());
		worm.setProfession(ItemHelper.checkIntTag(player.getHeldItem(hand), "profession", 0));
		w.spawnEntity(worm);
		player.getHeldItem(hand).shrink(1);
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		boolean spineless = ItemHelper.checkBooleanTag(stack, "spineless", false);
		tooltip.add("§5§o" + I18n.format(String.format("iteminfo.held_villager.%s", spineless ? "spineless" : "spineful")));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	
	
	
	

}
