package com.valeriotor.BTV.items;

import java.util.List;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.entities.EntityBloodZombie;
import com.valeriotor.BTV.tileEntities.TileBloodWell;
import com.valeriotor.BTV.tileEntities.TileBloodWell.BloodMobs;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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
						ItemHelper.checkTagCompound(stack).setInteger("num", count);
						EntityLiving e = (EntityLiving) this.type.getEntity(w, p);
						e.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
						w.spawnEntity(e);
						return EnumActionResult.SUCCESS;
				}
			}
		}
		return super.onItemUse(p, w, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		String key = String.format("tooltip.blood_sigil_%s.amount", this.type.name().toLowerCase());
		tooltip.add(I18n.format(key, ItemHelper.checkIntTag(stack, "num", 0))); 
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
