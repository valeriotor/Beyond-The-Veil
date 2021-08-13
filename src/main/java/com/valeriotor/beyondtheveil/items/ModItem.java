package com.valeriotor.beyondtheveil.items;

import java.util.List;

import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItem extends Item{
	
	public ModItem(String name) {
		this.setRegistryName(References.MODID, name);
		this.setUnlocalizedName(References.MODID + ":" + name);
		this.setCreativeTab(References.BTV_TAB);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		String info = I18n.format(String.format("lore.%s",  this.getUnlocalizedName().substring(5)));
		if(!info.substring(0,4).equals("lore")) tooltip.add(info);
	}
	
}
