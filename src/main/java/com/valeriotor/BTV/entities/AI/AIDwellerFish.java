package com.valeriotor.BTV.entities.AI;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.entities.EntityHamletDweller;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.tileEntities.TileSlugBait;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class AIDwellerFish extends EntityAIBase{
	
	private EntityHamletDweller dweller;
	private int counter = 0;
	
	public AIDwellerFish(EntityHamletDweller e) {
		this.setMutexBits(1);
		this.dweller = e;
	}
	
	@Override
	public boolean shouldExecute() {
		return dweller.getFishTime() > 0 && dweller.world.getBlockState(dweller.getPosition().down()).getBlock() == BlockRegistry.BlockSlugBait;
	}
	
	@Override
	public void updateTask() {
		if(this.counter == 0) {
			TileEntity te = dweller.world.getTileEntity(dweller.getPosition().down());
			if(te instanceof TileSlugBait) {
				TileSlugBait tsb = (TileSlugBait)te;
				if(tsb.catchSlugForce()) {
					boolean inserted = false;
					ItemStack stack = new ItemStack(ItemRegistry.slug);
					for(EnumFacing f : EnumFacing.HORIZONTALS) {
						BlockPos pos = dweller.getPosition().offset(f);
						if(dweller.getWorld().getBlockState(pos).getBlock() == Blocks.CHEST) {
							TileEntityChest tc = (TileEntityChest) dweller.world.getTileEntity(pos);
							IItemHandler cap = tc.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
							for(int i = 0; i < cap.getSlots(); i++) {
								if(cap.insertItem(i, stack, false).isEmpty()) {
									inserted = true;
									break;
								}
							}
							if(inserted) break;
						}
					}
					if(!inserted) {
						BlockPos pos = dweller.getPosition();
						EntityItem ent = new EntityItem(dweller.world, pos.getX(), pos.getY(), pos.getZ(), stack);
						dweller.world.spawnEntity(ent);
					}
				}
			}
		}
		dweller.decreaseFishTime();
		this.counter++;
		this.counter &= 31;
	}

}
