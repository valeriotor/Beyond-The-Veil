package com.valeriotor.BTV.items;

import java.util.List;

import com.valeriotor.BTV.dreaming.Memory;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMemoryPhial extends ModItem{

	public ItemMemoryPhial(String name) {
		super(name);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		Memory m = Memory.getMemoryFromDataName(ItemHelper.checkStringTag(stack, "memory", "none"));
		if(m != null) {
			tooltip.add(I18n.format("tooltip.memory_phial.stored", I18n.format(m.getLocalizedKey())));
		} else {
			tooltip.add(I18n.format("tooltip.memory_phial.empty"));			
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	public static class MemoryPhialColorHandler implements IItemColor {

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			String memory = ItemHelper.checkStringTag(stack, "memory", "none");
			if(memory.equals("none")) return -1;
			return Memory.getMemoryFromDataName(memory).getColor();
		}
		
	}

}
