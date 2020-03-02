package com.valeriotor.BTV.blocks;

import javax.annotation.Nullable;

import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.tileEntities.TileLacrymatory;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockLacrymatory extends ModBlock implements ITileEntityProvider{
	
	private static final AxisAlignedBB BBOX = new AxisAlignedBB(0, 0.0625 * 3, 0, 1, 0.0625 * 13, 1);
	
	public BlockLacrymatory(String name) {
		super(Material.CLAY, name);
		this.setSoundType(SoundType.GLASS);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BBOX;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileLacrymatory();
	}
	
	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(hand != EnumHand.MAIN_HAND) return false;
		IFluidHandler fh = getFluidHandler(w, pos);
		ItemStack stack = p.getHeldItemMainhand();
		if(fh != null) {
			if(stack.isEmpty()) {
				if(w.isRemote) {
					TileLacrymatory tl = (TileLacrymatory) w.getTileEntity(pos);
					p.sendMessage(new TextComponentTranslation("interact.lacrymatory.amount", tl.getAmount()));
				}
			} else {
				boolean success = FluidUtil.interactWithFluidHandler(p, hand, fh);
				return FluidUtil.getFluidHandler(stack) != null;
			}
		}
		return false;
	}
	
	@Nullable
	private IFluidHandler getFluidHandler(IBlockAccess world, BlockPos pos) {
		TileLacrymatory tl = (TileLacrymatory) world.getTileEntity(pos);
		return tl.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}
	

}
