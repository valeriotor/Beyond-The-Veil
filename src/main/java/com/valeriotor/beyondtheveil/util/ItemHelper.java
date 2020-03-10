package com.valeriotor.beyondtheveil.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemHelper {
	
	public static NBTTagCompound checkTagCompound(ItemStack stack) {
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound();
	}
	
	public static NBTTagCompound checkTagCompound(NBTTagCompound nbt, String key) {
		if(!nbt.hasKey(key)) nbt.setTag(key, new NBTTagCompound());
		return (NBTTagCompound) nbt.getTag(key);
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
	
	public static long checkLongTag(ItemStack stack, String key, long defaultValue) {
		if(ItemHelper.checkTagCompound(stack).hasKey(key)) {
			stack.getTagCompound().setLong(key, Long.valueOf(defaultValue));
			return defaultValue;
		}
		return stack.getTagCompound().getLong(key);	
	}
	
	public static String checkStringTag(ItemStack stack, String key, String defaultValue) {
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		if(!nbt.hasKey(key)) {
			nbt.setString(key, defaultValue);
			return defaultValue;
		}
		return nbt.getString(key);
		
	}
	
}
