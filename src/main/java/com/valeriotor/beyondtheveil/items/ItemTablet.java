package com.valeriotor.beyondtheveil.items;

import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.gui.Guis;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.SyncUtil;

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
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemTablet extends ModItem{
	
	public ItemTablet(String name) {
		super(name);
		setHasSubtypes(true);
		setMaxDamage(0);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(stack.getMetadata() == 1) {
			if(!worldIn.isRemote) {
				if(ResearchUtil.learn(playerIn)) {
					stack.shrink(1);
				}
			}
			return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
		}
		if(!worldIn.isRemote) SyncUtil.addStringDataOnServer(playerIn, false, "solvedTablet");
		
		if(!playerIn.getHeldItem(handIn).hasTagCompound()) {
			playerIn.getHeldItem(handIn).setTagCompound(new NBTTagCompound());
			
		}
		if(worldIn.isRemote) BeyondTheVeil.proxy.openGui(Guis.GuiTablet);
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getItem() == ItemRegistry.tablet) {
			tooltip.add(I18n.format("lore." + this.getUnlocalizedName().substring(5)));
		}
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == CreativeTabs.MISC || tab == CreativeTabs.SEARCH) {
			items.add(new ItemStack(this, 1, 0));
		}
	}
	
	
	
	
	

}
