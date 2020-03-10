package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.mojang.authlib.GameProfile;
import com.valeriotor.beyondtheveil.util.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class DreamBeheading extends Dream{

	public DreamBeheading(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		return activatePlayer(p, p, w);
	}
	
	@Override
	public boolean activatePlayer(EntityPlayer caster, EntityPlayer target, World w) {
		if(target == null) return false;
		ItemStack skull = new ItemStack(Items.SKULL, 1, 3);
		GameProfile g = target.getGameProfile();
		if(g == null) return false;
		ItemHelper.checkTagCompound(skull).setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), g));
		ItemHandlerHelper.giveItemToPlayer(caster, skull);
		return true;
	}

}
