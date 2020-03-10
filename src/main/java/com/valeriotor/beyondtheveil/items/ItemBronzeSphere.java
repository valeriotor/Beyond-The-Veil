package com.valeriotor.beyondtheveil.items;

import java.util.List;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBronzeSphere extends Item implements IArtifactItem{
	
	public ItemBronzeSphere() {
		this.setRegistryName(References.MODID + ":bronze_sphere");
		this.setUnlocalizedName("bronze_sphere");
		this.setCreativeTab(References.BTV_TAB);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(stack.getItem() != this) return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
		if(worldIn.isRemote) return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey("xp")) stack.getTagCompound().setInteger("xp", 0);
		int itemXp = stack.getTagCompound().getInteger("xp");
		int playerXp = playerIn.experienceTotal;
		if(!playerIn.isSneaking()) {
			stack.getTagCompound().setInteger("xp", itemXp + Math.min(playerXp, 100));
			MathHelperBTV.removeExperience(playerIn, 100);
		} else {
			stack.getTagCompound().setInteger("xp", Math.max(0, itemXp - 100));
			playerIn.addExperience(Math.min(itemXp, 100));
			
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("�5�o"+I18n.format("lore." + this.getUnlocalizedName().substring(5)));
		int itemXp = 0;
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("xp")) itemXp = stack.getTagCompound().getInteger("xp");
		tooltip.add(I18n.format("tooltip." + this.getUnlocalizedName().substring(5), itemXp));
	}
	
	
	
}
