package com.valeriotor.beyondtheveil.items;

import java.util.List;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.EntityCrawlingVillager;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.sacrifice.SacrificeHelper;
import com.valeriotor.beyondtheveil.util.ItemHelper;

import net.minecraft.block.Block;
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
		boolean heartless = ItemHelper.checkBooleanTag(player.getHeldItem(hand), "heartless", false);
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				IBlockState b = w.getBlockState(pos.add(x, 1, z));
				if(b.causesSuffocation() || b.isFullBlock()) {
					return EnumActionResult.FAIL;
				}
			}
		}
		EntityCrawlingVillager worm = new EntityCrawlingVillager(w, !spineless, heartless);
		Block block = w.getBlockState(pos).getBlock();
		boolean sacrifice = false;
		if(block == BlockRegistry.BlockSacrificeAltar && SacrificeHelper.checkStructure(player, pos) && heartless) {
			sacrifice = true;
			worm.setAltar(pos);
		}
		if(sacrifice ||  block != BlockRegistry.BlockSacrificeAltar || !heartless) {
			worm.setPosition(pos.getX()+0.5, 1+pos.getY(), pos.getZ()+0.5);
			worm.setProfession(ItemHelper.checkIntTag(player.getHeldItem(hand), "profession", 0));
			worm.setMaster(player);
			w.spawnEntity(worm);
			player.getHeldItem(hand).shrink(1);
			return EnumActionResult.SUCCESS;
		}
		else
			return EnumActionResult.FAIL;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		boolean spineless = ItemHelper.checkBooleanTag(stack, "spineless", false);
		boolean heartless = ItemHelper.checkBooleanTag(stack, "heartless", false);
		tooltip.add(I18n.format(String.format("tooltip.held_villager.%s%s", spineless ? "spineless" : "spineful", heartless ? "heartless" : "")));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	
	
	
	

}
