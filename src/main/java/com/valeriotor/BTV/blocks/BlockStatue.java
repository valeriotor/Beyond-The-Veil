package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.tileEntities.TileStatue;
import com.valeriotor.BTV.worship.CrawlerWorship.WorshipType;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static com.valeriotor.BTV.blocks.BlockProperties.HORIZONTAL_FACING;;

public class BlockStatue extends ModBlock implements ITileEntityProvider{
	
	public final WorshipType type;
	private static final double a = 0.0625;
	private static final AxisAlignedBB BBOX = new AxisAlignedBB(a*6, 0, a*6, a*10, a*15, a*10);
	
	public BlockStatue(Material material, String name, WorshipType type) {
		super(material, name);
		this.type = type;
		this.setDefaultState(this.blockState.getBaseState().withProperty(HORIZONTAL_FACING, EnumFacing.SOUTH));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileStatue(type);
	}
	
	@Override
	public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if(placer instanceof EntityPlayer) {
			TileEntity te = w.getTileEntity(pos);
			if(te instanceof TileStatue) {
				((TileStatue)te).setMaster((EntityPlayer)placer);
			}
		}
		super.onBlockPlacedBy(w, pos, state, placer, stack);
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		return this.getDefaultState().withProperty(HORIZONTAL_FACING, placer.getHorizontalFacing());
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		switch(meta) {
			case 0:
				state = state.withProperty(HORIZONTAL_FACING, EnumFacing.SOUTH);
				break;
			case 1:
				state = state.withProperty(HORIZONTAL_FACING, EnumFacing.WEST);
				break;
			case 2:
				state = state.withProperty(HORIZONTAL_FACING, EnumFacing.NORTH);
				break;
			case 3:
				state = state.withProperty(HORIZONTAL_FACING, EnumFacing.EAST);
				break;	
		}		
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(HORIZONTAL_FACING).getHorizontalIndex();
	}
	
	public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(HORIZONTAL_FACING, rot.rotate((EnumFacing)state.getValue(HORIZONTAL_FACING)));
    }
	
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(HORIZONTAL_FACING)));
    }
	
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {HORIZONTAL_FACING});
    }

}
