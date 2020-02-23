package com.valeriotor.BTV.items;

import java.util.List;

import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemBloodSigilPathway extends ModItem{

	public ItemBloodSigilPathway(String name) {
		super(name);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 64;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(ItemHelper.checkTagCompound(stack).hasKey("path"))
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		if(!nbt.hasKey("path")) {
			nbt.setLong("path", entityLiving.getPosition().toLong());
			if(entityLiving instanceof EntityPlayer && !worldIn.isRemote) {
				((EntityPlayer)entityLiving).sendMessage(new TextComponentTranslation("use.blood_sigil.path"));
			}
		}
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		if(!nbt.hasKey("path")) {
			tooltip.add(I18n.format("tooltip.blood_sigil_pathway.nopath"));
		} else {
			BlockPos pos = BlockPos.fromLong(nbt.getLong("path"));
			tooltip.add(I18n.format("tooltip.blood_sigil_pathway.path", pos.getX(), pos.getY(), pos.getZ()));
		}
	}

}
