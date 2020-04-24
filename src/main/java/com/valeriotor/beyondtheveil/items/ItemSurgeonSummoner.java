package com.valeriotor.beyondtheveil.items;

import com.valeriotor.beyondtheveil.entities.EntitySurgeon;
import com.valeriotor.beyondtheveil.util.ItemHelper;

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
import net.minecraftforge.items.CapabilityItemHandler;

public class ItemSurgeonSummoner extends ModItem{

	public ItemSurgeonSummoner(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if(stack.getItem() == this) {
			if(!player.world.isRemote) {
				TileEntity te = worldIn.getTileEntity(pos);
				if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
					player.sendMessage(new TextComponentTranslation("use.surgeon_summoner.boundcontainer"));
					ItemHelper.checkTagCompound(stack).setLong("container_pos", pos.toLong());
				} else if(player.isSneaking()) {
					NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
					if(nbt.hasKey("container_pos")) {
						ItemHelper.checkTagCompound(stack).removeTag("container_pos");
						player.sendMessage(new TextComponentTranslation("use.surgeon_summoner.unboundcontainer"));
					}
				} else if(facing == EnumFacing.UP) {
					for(int x = -1; x <= 1; x++) {
						for(int y = 1; y <= 4; y++) {
							for(int z = -1; z <= 1; z++) {
								if(!worldIn.isAirBlock(pos.add(x, y, z))) {
									player.sendMessage(new TextComponentTranslation("use.surgeon_summoner.nospace"));
									return EnumActionResult.SUCCESS;
								}
							}
						}
					}
					EntitySurgeon surgeon = new EntitySurgeon(worldIn);
					surgeon.setPosition(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5);
					surgeon.setMaster(player);
					NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
					if(nbt.hasKey("container_pos")) {
						surgeon.setContainer(BlockPos.fromLong(nbt.getLong("container_pos")));
					}
					worldIn.spawnEntity(surgeon);
					stack.shrink(1);
				}
			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(stack.getItem() == this) {
			if(player.isSneaking() && !worldIn.isRemote) {
				NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
				if(nbt.hasKey("container_pos")) {
					ItemHelper.checkTagCompound(stack).removeTag("container_pos");
					player.sendMessage(new TextComponentTranslation("use.surgeon_summoner.unboundcontainer"));
				}
			}
		}
		return super.onItemRightClick(worldIn, player, hand);
	}

}
