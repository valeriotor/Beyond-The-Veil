package com.valeriotor.BTV.items;

import java.util.List;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.entities.IPlayerGuardian;
import com.valeriotor.BTV.tileEntities.TileHeart;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemCoralStaff extends ModItem{

	public ItemCoralStaff(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer p, EnumHand handIn) {
		if(worldIn.isRemote) return super.onItemRightClick(worldIn, p, handIn);
		ItemStack stack = p.getHeldItem(handIn);
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		if(p.isSneaking()) {
			if(nbt.hasKey("link")) {
				nbt.removeTag("link");
				p.sendMessage(new TextComponentTranslation("use.coral_staff.clear"));
			}
		} else {
			if(nbt.hasKey("link")) {
				BlockPos pos = BlockPos.fromLong(nbt.getLong("link"));
				p.sendMessage(new TextComponentTranslation("use.coral_staff.lastlinked", pos.getX(), pos.getY(), pos.getZ()));
			}
		}
		return super.onItemRightClick(worldIn, p, handIn);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer p, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = p.getHeldItem(hand);
		if(worldIn.getBlockState(pos).getBlock() == BlockRegistry.BlockHeart) {
			if(stack.getItem() == ItemRegistry.coral_staff) {
				if(worldIn.isRemote) return EnumActionResult.SUCCESS;
				if(p.isSneaking()) {
					NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
					if(nbt.hasKey("link")) {
						BlockPos first = BlockPos.fromLong(nbt.getLong("link"));
						if(first.distanceSq(pos) < 1024) {
							TileEntity te = worldIn.getTileEntity(first);
							if(te instanceof TileHeart) {
								TileHeart th = (TileHeart)te;
								if(!pos.equals(first)) {
									th.setLink(pos);
									p.sendMessage(new TextComponentTranslation("interact.heart.linked", first.getX(), first.getY(), first.getZ()));
								} else {
									th.setLink(null);
									p.sendMessage(new TextComponentTranslation("interact.heart.cleared"));
								}
								nbt.removeTag("link");
							} else {
								p.sendMessage(new TextComponentTranslation("interact.heart.notfound"));
							}
						} else {
							p.sendMessage(new TextComponentTranslation("interact.heart.toofar"));
						}
					} else {
						nbt.setLong("link", pos.toLong());
						p.sendMessage(new TextComponentTranslation("interact.heart.stored"));
					}
				} else {
					TileEntity te = worldIn.getTileEntity(pos);
					if(te instanceof TileHeart) {
						TileHeart th = (TileHeart)te;
						BlockPos link = th.getLink();
						if(link != null) {
							p.sendMessage(new TextComponentTranslation("interact.heart.printlink", link.getX(), link.getY(), link.getZ()));
						} else {
							p.sendMessage(new TextComponentTranslation("interact.heart.nolink"));	
						}
					}
				}
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.PASS;
	}
	
	public void commandUndead(LivingHurtEvent event) {
		if(((EntityPlayer)event.getSource().getTrueSource()).getHeldItemMainhand().getItem() == this) {
			EntityLivingBase hurt = event.getEntityLiving();
			if(!hurt.isEntityUndead()) {
				List<EntityLiving> undead = hurt.world.getEntities(EntityLiving.class, e -> e.getDistance(hurt) < 20 && e.isEntityUndead() && !(e instanceof IPlayerGuardian));
				for(EntityLiving ent : undead) {
					ent.setAttackTarget(hurt);
				}
			}
		}
	}

}
