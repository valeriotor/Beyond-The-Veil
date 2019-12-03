package com.valeriotor.BTV.items;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.entities.EntityBloodZombie;
import com.valeriotor.BTV.tileEntities.TileBloodWell;
import com.valeriotor.BTV.tileEntities.TileBloodWell.BloodMobs;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemBloodSigil extends ModItem{
	
	private final BloodMobs type;
	
	public ItemBloodSigil(String name, BloodMobs type) {
		super(name);
		this.type = type;
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer p, World w, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = p.getHeldItem(hand);
		if(w.getBlockState(pos).getBlock() == BlockRegistry.BlockBloodWell) {
			if(w.isRemote) return EnumActionResult.SUCCESS;
			NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
			int count = ItemHelper.checkIntTag(stack, "num", 0);
			TileBloodWell tbw = (TileBloodWell) w.getTileEntity(pos);
			if(tbw == null) return EnumActionResult.FAIL;
			if(p.isSneaking()) {
				if(count > 0) {
					tbw.addMob(type);
					p.sendMessage(new TextComponentTranslation("use.blood_sigil.amount", --count));
					nbt.setInteger("num", count);
				} else 
					p.sendMessage(new TextComponentTranslation("use.blood_sigil.emptysigil"));
			} else {
				if(tbw.takeMob(type)) {
					p.sendMessage(new TextComponentTranslation("use.blood_sigil.amount", ++count));
					nbt.setInteger("num", count);
				} else 
					p.sendMessage(new TextComponentTranslation("use.blood_sigil.emptywell"));
			}
			return EnumActionResult.SUCCESS;
		} else {
			if(facing == EnumFacing.UP) {
				if(w.isRemote) return EnumActionResult.SUCCESS;
				int count = ItemHelper.checkIntTag(stack, "num", 0);
					if(count > 0) {
						p.sendMessage(new TextComponentTranslation("use.blood_sigil.amount", --count));
						ItemHelper.checkTagCompound(stack).setInteger("num", count - 1);
						EntityLiving e = (EntityLiving) this.type.getEntity(w, p);
						e.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
						w.spawnEntity(e);
						return EnumActionResult.SUCCESS;
				}
			}
		}
		return super.onItemUse(p, w, pos, hand, facing, hitX, hitY, hitZ);
	}

}
