package com.valeriotor.BTV.items;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.Comparators;
import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.dreaming.DreamRegistry;
import com.valeriotor.BTV.dreaming.dreams.AbstractDream;
import com.valeriotor.BTV.gui.container.GuiContainerHandler;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.items.ItemsTC;

public class ItemDreamBottle extends Item{
	
	public ItemDreamBottle(String name) {
		this.setMaxStackSize(1);
		this.setCreativeTab(References.BTV_TAB);
		setRegistryName(References.MODID, name);
		setUnlocalizedName(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(worldIn.isRemote) return super.onItemRightClick(worldIn, playerIn, handIn);
		if(handIn == EnumHand.MAIN_HAND) {
			if(!playerIn.isSneaking()) {
				BlockPos pos = playerIn.getPosition();
				playerIn.openGui(BeyondTheVeil.instance, GuiContainerHandler.DREAM_BOTTLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
			} else {
				dream(playerIn);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public void dream(EntityPlayer playerIn) {
		ItemStack stack = playerIn.getHeldItemMainhand();
		Map<Integer, AbstractDream> dreams = new HashMap<>();
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		for(int i = 0; i < (this == ItemRegistry.dream_bottle ? 4 : 1); i++) {
			if(nbt.hasKey(String.format("slot%d", i))) {
				ItemStack stack2 = new ItemStack(nbt.getCompoundTag(String.format("slot%d", i)));
				if(stack2.getItem() == ItemsTC.crystalEssence)
					dreams.put(i, DreamRegistry.dreams.get(AspectHelper.getObjectAspects(stack2).getAspects()[0].getName()));
			}
		}
		SortedSet<Map.Entry<Integer, AbstractDream>> set = new TreeSet<Map.Entry<Integer, AbstractDream>>(Comparator.comparingInt(e -> -e.getValue().priority));
		set.addAll(dreams.entrySet());
		for(Entry<Integer, AbstractDream> entry : set) {
			if(entry.getValue().activate(playerIn, playerIn.world, ThaumcraftCapabilities.getKnowledge(playerIn)))
				nbt.removeTag(String.format("slot%d", entry.getKey().intValue()));
		}
	}
	
}
