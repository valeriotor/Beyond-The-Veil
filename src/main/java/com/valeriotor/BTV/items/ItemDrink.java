package com.valeriotor.BTV.items;

import com.valeriotor.BTV.lib.References;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemDrink extends ItemFood{
	public ItemDrink(String name, int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		setRegistryName(References.MODID + ":" + name);
		setUnlocalizedName(name);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300), 1.0F);
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
}
