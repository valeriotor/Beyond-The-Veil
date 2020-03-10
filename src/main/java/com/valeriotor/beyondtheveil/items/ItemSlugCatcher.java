package com.valeriotor.beyondtheveil.items;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tileEntities.TileSlugBait;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemSlugCatcher extends Item{
	public ItemSlugCatcher() {
		this.setMaxStackSize(1);
		this.setCreativeTab(References.BTV_TAB);
		setRegistryName(References.MODID +":slugcatcher");
		setUnlocalizedName("slugcatcher");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote) {
			RayTraceResult result = this.rayTrace2(worldIn, playerIn, true, 16);
			BlockPos pos = result.getBlockPos();
			for(int i = -6; i <= 6; i++) {
				for(int j = -6; j <= 6; j++) {
					BlockPos newPos = new BlockPos((int)pos.getX()+i, (int) pos.getY() ,(int)pos.getZ()+j);
					if(worldIn.getBlockState(newPos) == BlockRegistry.BlockSlugBait.getDefaultState()) {
						if(((TileSlugBait)worldIn.getTileEntity(newPos)).catchSlug(pos.getX(), pos.getZ())) {
							Vec3d vec = this.rayTrace3(worldIn, playerIn, false, 1);
							BlockPos pos2 = new BlockPos(vec.x, vec.y, vec.z);
							EntityItem item = new EntityItem(worldIn, pos2.getX()+0.5, pos2.getY(), pos2.getZ()+0.5, new ItemStack(ItemRegistry.slug, 1));
							worldIn.spawnEntity(item);
						}
						break;
					}
				}
			}
			
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
		//return new ActionResult(EnumActionResult.SUCCESS, new ItemStack(this));
	}
	
	//Just copying the rayTrace method from Item
	private RayTraceResult rayTrace2(World worldIn, EntityPlayer playerIn, boolean useLiquids, double d3)
    {
        float f = playerIn.rotationPitch;
        float f1 = playerIn.rotationYaw;
        double d0 = playerIn.posX;
        double d1 = playerIn.posY + (double)playerIn.getEyeHeight();
        double d2 = playerIn.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
        return worldIn.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
    }
	
	private Vec3d rayTrace3(World worldIn, EntityPlayer playerIn, boolean useLiquids, double d3)
    {
        float f = playerIn.rotationPitch;
        float f1 = playerIn.rotationYaw;
        double d0 = playerIn.posX;
        double d1 = playerIn.posY + (double)playerIn.getEyeHeight();
        double d2 = playerIn.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
        return vec3d1;
    }
}
