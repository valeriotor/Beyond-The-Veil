package com.valeriotor.BTV.blocks;

import java.util.List;

import com.valeriotor.BTV.tileEntities.TileWateryCradle;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWateryCradle extends ModBlock implements ITileEntityProvider{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", f -> f != EnumFacing.DOWN && f != EnumFacing.UP);
	public static final PropertyEnum<EnumPart> PART = PropertyEnum.<BlockWateryCradle.EnumPart>create("part", BlockWateryCradle.EnumPart.class);
	public static final AxisAlignedBB BBOX = new AxisAlignedBB(0.0,0.0,0.0,1,0.875,1);
	
	public BlockWateryCradle(String name) {
		super(Material.IRON, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PART, EnumPart.STRUCTURE));
		this.setHardness(30);
		this.setResistance(300);
	}
	
	
	
	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(state.getValue(PART) == EnumPart.STRUCTURE) {
			for(EnumFacing dir : EnumFacing.VALUES) {
				for(int i = 1; i <= 2; i++) {
					if(w.getBlockState(pos.offset(dir, i)).getBlock() == this && w.getBlockState(pos.offset(dir, i)).getValue(PART) == EnumPart.STRUCTURE) {
						if(i == 2) {
							for(int j = 0; j < 3; j++) {
								w.setBlockState(pos.offset(dir, j), this.getDefaultState().withProperty(FACING, dir).withProperty(PART, EnumPart.values()[j+1]));
								if(j == 2) return true;
							}
						}
					} else break;
				}
			}
		}
		return super.onBlockActivated(w, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World w, BlockPos pos, Block blockIn, BlockPos fromPos) {
		EnumPart value = state.getValue(PART);
		EnumFacing facing = state.getValue(FACING);
		if(value == EnumPart.HEAD) {
			IBlockState state2 = w.getBlockState(pos.offset(facing));
			if(state2.getBlock() != this || state2.getValue(PART) != EnumPart.BODY) {
				w.destroyBlock(pos, true);
			}
		} else if(value == EnumPart.TAIL) {
			IBlockState state2 = w.getBlockState(pos.offset(facing.getOpposite()));
			if(state2.getBlock() != this || state2.getValue(PART) != EnumPart.BODY) {
				w.destroyBlock(pos, true);
			}
		} else if(value == EnumPart.BODY) {
			IBlockState state2 = w.getBlockState(pos.offset(facing));
			IBlockState state3 = w.getBlockState(pos.offset(facing.getOpposite()));
			if(state2.getBlock() != this || state2.getValue(PART) != EnumPart.TAIL
			|| state3.getBlock() != this || state3.getValue(PART) != EnumPart.HEAD) {
				w.destroyBlock(pos, true);
			}
		}
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BBOX;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return BBOX;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(meta / 4 == 1) {
			return new TileWateryCradle();
		}
		return null;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING, PART});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		state = state.withProperty(FACING, EnumFacing.getHorizontal(meta % 4));
		state = state.withProperty(PART, EnumPart.values()[meta/4]);
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		switch(state.getValue(PART).partName) {
		case "head": meta = 4;
		break;
		case "body": meta = 8;
		break;
		case "tail": meta = 12;
		break;
		}
		meta += state.getValue(FACING).getHorizontalIndex();
		return meta;
	}
	
	public static enum EnumPart implements IStringSerializable {
		STRUCTURE("structure"),
		HEAD("head"),
		BODY("body"),
		TAIL("tail");
		
		private String partName;
		
		private EnumPart(String name) {
			this.partName = name;
		}
		
		@Override
		public String getName() {
			return this.partName;
		}
		
		@Override
		public String toString() {
			return this.partName;
		}
		
	}

}
