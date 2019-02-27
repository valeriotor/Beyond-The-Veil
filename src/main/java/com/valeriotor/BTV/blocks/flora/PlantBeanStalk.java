package com.valeriotor.BTV.blocks.flora;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.lib.BlockNames;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PlantBeanStalk extends BlockPlant implements IMutationCatalyst{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
    {
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.DOWN && p_apply_1_ != EnumFacing.UP;
        }
    });
	
	public static final PropertyBool SOURCE = PropertyBool.create("source");
	
	private static final AxisAlignedBB BBox = new AxisAlignedBB(0.3F, 0.0F, 0.3F, 0.69F, 1.0F, 0.69F);
	
	
	public PlantBeanStalk(String name) {
		super(Material.PLANTS, name);
		this.setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SOURCE, true));
		this.spreadChance = 10;
		this.spreadMinMutation = 1000;
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer p,
			EnumHand h, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			if(h == EnumHand.MAIN_HAND  && p.getHeldItem(h).getItem() == Items.AIR) {
				this.grow(worldIn, pos);
				return true;
			}
		}
		return super.onBlockActivated(worldIn, pos, state, p, h, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean spread(World w, BlockPos pos, int mutation, String aspect, float multiplier) {
		if(super.spread(w, pos, mutation, aspect, multiplier)) {
			return true;
		}
		int random = w.rand.nextInt(1000);
		if(this.spreadChance*multiplier >= random) {
			this.grow(w, pos);
			return true;
		}
		return false;
		
	}
	
	public void grow(World w, BlockPos pos) {
		while(w.getBlockState(pos.up()).getBlock().equals(this)) {
			pos = pos.up();
		}
		if(w.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
			w.setBlockState(pos.up(), this.getDefaultState().withProperty(SOURCE, false).withProperty(FACING, w.getBlockState(pos).getValue(FACING).rotateYCCW()));
		}
	}
	
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		if(state.getValue(SOURCE)) super.getDrops(drops, world, pos, state, fortune);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		int y = 0;
		BlockPos pos1 = pos;
		while(worldIn.getBlockState(pos1.up()).getBlock() == BlockRegistry.PlantBeanStalk) {
			pos1 = pos1.up();
			y++;
		}
		for(int i = y; i > 0; i--) {
			worldIn.setBlockToAir(pos.offset(EnumFacing.UP, i));
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		if(side != EnumFacing.UP) return false;
		Block block = worldIn.getBlockState(pos.down()).getBlock();
		
		if(block == Blocks.DIRT || block == Blocks.GRASS || block == Blocks.MYCELIUM)
			return true;
		
		return false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		Block b = worldIn.getBlockState(pos.down()).getBlock();
		if(b != Blocks.DIRT && b != Blocks.GRASS && b != Blocks.MYCELIUM && b != this) {
			worldIn.destroyBlock(pos, true);
		}
	}
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, SOURCE});
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		state = state.withProperty(FACING, EnumFacing.getFront((meta & 3) + 2));
		
		state = state.withProperty(SOURCE, (meta & 4) == 0);
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(FACING).getIndex() - 2;
		if(!state.getValue(SOURCE)) meta |= 4;
		return meta;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BBox;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BBox);
	}
	
	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
		return true;
	}

	@Override
	public int mutationIncrease() {
		return 2;
	}

	
	
}
