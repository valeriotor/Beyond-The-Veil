package com.valeriotor.BTV.items;

import com.valeriotor.BTV.lib.References;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemDrink extends Item{
	public ItemDrink(String name) {
		setRegistryName(References.MODID + ":" + name);
		setUnlocalizedName(name);
		this.setCreativeTab(References.BTV_TAB);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if(stack.getItem() == ItemRegistry.cup) return stack;
		if(entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300));
			player.getFoodStats().addStats(6, 4);
			
			if (player == null || !player.capabilities.isCreativeMode)
	        {
	            stack.shrink(1);
	            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegistry.cup, 1));
	        }
			
			if (player instanceof EntityPlayerMP)
	        {
	            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)player, stack);
	        }
			
			if (player != null)
	        {
	            player.addStat(StatList.getObjectUseStats(this));
	        }
			
		}	
		
		return stack;
	}
	
    public EnumAction getItemUseAction(ItemStack stack)
    {
    	if(stack.getItem() == ItemRegistry.cup) return EnumAction.NONE;
        return EnumAction.DRINK;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	if(playerIn.getHeldItem(handIn).getItem() == ItemRegistry.cup) return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
    
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }
    
    @Override
    public int getItemBurnTime(ItemStack itemStack) {
    	if(itemStack.getItem() == ItemRegistry.cup) return 300;
    	return super.getItemBurnTime(itemStack);
    }
}
