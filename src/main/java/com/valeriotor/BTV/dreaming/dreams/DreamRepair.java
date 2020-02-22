package com.valeriotor.BTV.dreaming.dreams;

import com.valeriotor.BTV.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class DreamRepair extends Dream{

	public DreamRepair(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w) {
		if(!DreamHandler.youDontHaveLevel(p, 2)) return false;
		for(ItemStack item : p.getArmorInventoryList()) {
			repairSingleItem(item, 0.3);
		}
		repairSingleItem(p.getHeldItem(EnumHand.MAIN_HAND), 0.3);
		repairSingleItem(p.getHeldItem(EnumHand.OFF_HAND), 0.3);
		if(DreamHandler.getDreamPowerLevel(p) > 3) {
			for(ItemStack item : p.inventory.mainInventory) {
				repairSingleItem(item, 0.1);
			}
		}
		
		return true;
	}
	
	private void repairSingleItem(ItemStack item, double coefficient) {
		if(item != null && item.isItemStackDamageable()) {
			item.setItemDamage((int) Math.max(0, item.getItemDamage() - item.getMaxDamage()*coefficient));
		}
	}

}
