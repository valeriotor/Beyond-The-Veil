package com.valeriotor.BTV.items;

import java.util.List;

import javax.annotation.Nullable;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

public class ItemTablet extends Item{
	
	public ItemTablet(String name) {
		setRegistryName(References.MODID + ":" + name);
		setUnlocalizedName(name);
		setHasSubtypes(true);
		setMaxDamage(0);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.MISC);
		this.addPropertyOverride(new ResourceLocation("finished"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
            	if(!stack.hasTagCompound()) return 0;
            	if(!stack.getTagCompound().hasKey("finished")) return 0;
                return (entityIn != null && stack.getTagCompound().getBoolean("finished")) ? 1 : 0;
            }
        });
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(stack.getItem() == ItemRegistry.tablet) {
			IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(playerIn);
			
			if(!k.isResearchKnown("inscriptions"))
				ThaumcraftApi.internalMethods.progressResearch(playerIn, "inscriptions");
			
			if(!playerIn.getHeldItem(handIn).hasTagCompound()) {
				playerIn.getHeldItem(handIn).setTagCompound(new NBTTagCompound());
				
			}
			if(!stack.getTagCompound().hasKey("finished")) stack.getTagCompound().setBoolean("finished", false);
			if(worldIn.isRemote) BeyondTheVeil.proxy.openGui(Guis.GuiTablet);
			return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getItem() == ItemRegistry.tablet) {
			tooltip.add("§5§o"+I18n.format("lore." + this.getUnlocalizedName().substring(5)));
		}
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == CreativeTabs.MISC || tab == CreativeTabs.SEARCH) {
			items.add(new ItemStack(this, 1, 0));
		}
	}
	
	
	
	
	

}
