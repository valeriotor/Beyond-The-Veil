package com.valeriotor.BTV.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemHelper {
	
	public static NBTTagCompound checkTagCompound(ItemStack stack) {
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound();
	}
	
	public static boolean checkBooleanTag(ItemStack stack, String key, boolean defaultValue) {
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key)) {
			stack.getTagCompound().setBoolean(key, defaultValue);
			return defaultValue;
		}
		return stack.getTagCompound().getBoolean(key);
	}
	
	public static int checkIntTag(ItemStack stack, String key, int defaultValue) {
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key)) {
			stack.getTagCompound().setInteger(key, Integer.valueOf(defaultValue));
			return defaultValue;
		}
		return stack.getTagCompound().getInteger(key);
	}
	
}
