package com.valeriotor.BTV.items;

import java.util.List;
import java.util.UUID;

import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBloodSigilPlayer extends ModItem{

	public ItemBloodSigilPlayer(String name) {
		super(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		if(!nbt.hasKey("playername")) {
			tooltip.add(I18n.format("tooltip.blood_sigil_player.noplayer"));
		} else {
			String name = nbt.getString("playername");
			tooltip.add(I18n.format("tooltip.blood_sigil_player.player", name));
		}
	}

}
