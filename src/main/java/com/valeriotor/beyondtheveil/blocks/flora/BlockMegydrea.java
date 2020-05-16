package com.valeriotor.beyondtheveil.blocks.flora;

import javax.annotation.Nullable;

import com.valeriotor.beyondtheveil.blocks.EnumHalf;
import com.valeriotor.beyondtheveil.tileEntities.TileMegydrea;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockMegydrea extends BlockTallPlant implements ITileEntityProvider{

	private static final double min = 0.0625*3;
	private static final double max = 0.0625*13;
	private static final double minHeight = 0.0625*6;
	private static final double maxHeight = 0.0625*10;
	private static final AxisAlignedBB BOTTOMBOX = new AxisAlignedBB(min, minHeight, min, max, 1, max);
	private static final AxisAlignedBB TOPBOX = new AxisAlignedBB(min, 0, min, max, maxHeight, max);
	
	public BlockMegydrea(String name) {
		super(Material.WOOD, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(EnumHalf.HALF, EnumHalf.TOP));
		this.setSoundType(SoundType.PLANT);
	}
	
	@Override
	public boolean spread(World w, BlockPos pos, int mutation, float multiplier) {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World w, BlockPos pos) {
		Block b = w.getBlockState(pos.up()).getBlock();
		if(w.getBlockState(pos.up()).isSideSolid(w, pos.up(), EnumFacing.DOWN) && w.isAirBlock(pos.down())) return true;
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos.down(), this.getDefaultState().withProperty(EnumHalf.HALF, EnumHalf.BOTTOM));
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(EnumHalf.HALF, EnumHalf.TOP);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(state.getValue(EnumHalf.HALF) == EnumHalf.BOTTOM) {
			if(worldIn.getBlockState(pos.up()).getBlock() != this) {
				worldIn.setBlockToAir(pos);
			}
		}else {
			if(worldIn.getBlockState(pos.down()).getBlock() != this) {
				worldIn.setBlockToAir(pos);
			} else if(!worldIn.getBlockState(pos.up()).isSideSolid(worldIn, pos.up(), EnumFacing.DOWN)) {
				this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(hand != EnumHand.MAIN_HAND) return false;
		if(state.getValue(EnumHalf.HALF) == EnumHalf.TOP) {
			pos = pos.down();
			state = w.getBlockState(pos);
		}
		IFluidHandler fh = getFluidHandler(w, pos);
		ItemStack stack = p.getHeldItemMainhand();
		if(fh != null) {
			if(!stack.isEmpty()) {
				boolean success = FluidUtil.interactWithFluidHandler(p, hand, fh);
				return FluidUtil.getFluidHandler(stack) != null;
			}
		}
		return false;
	}
	
	@Nullable
	private IFluidHandler getFluidHandler(IBlockAccess world, BlockPos pos) {
		TileMegydrea tl = (TileMegydrea) world.getTileEntity(pos);
		return tl.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return (layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return meta == 1 ? null : new TileMegydrea();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(EnumHalf.HALF) == EnumHalf.TOP ? TOPBOX : BOTTOMBOX;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return blockState.getValue(EnumHalf.HALF) == EnumHalf.TOP ? TOPBOX : BOTTOMBOX;
	}

}
