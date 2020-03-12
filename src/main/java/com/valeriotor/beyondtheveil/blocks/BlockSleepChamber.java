package com.valeriotor.beyondtheveil.blocks;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.gui.Guis;
import com.valeriotor.beyondtheveil.gui.container.GuiContainerHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSleepChamber extends ModBlock{
	public static final PropertyBool OPEN = PropertyBool.create("open");
	public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
    {
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.DOWN && p_apply_1_ != EnumFacing.UP;
        }
    });
	public static final double a = 0.0625;
	public static final AxisAlignedBB FLAT_BOTTOM = new AxisAlignedBB(0,0,0,1,a,1);
	public static final AxisAlignedBB FLAT_TOP = new AxisAlignedBB(0,1-a,0,1,1,1);
	public static final AxisAlignedBB NORTH = new AxisAlignedBB(0,a,0,1,1-a,a);
	public static final AxisAlignedBB EAST = new AxisAlignedBB(1-a,a,0,1,1-a,1);
	public static final AxisAlignedBB SOUTH = new AxisAlignedBB(0,a,1-a,1,1-a,1);
	public static final AxisAlignedBB WEST = new AxisAlignedBB(0,a,0,a,1-a,1);
	private static final EnumMap<EnumFacing, AxisAlignedBB> bbs = new EnumMap<>(EnumFacing.class);
	static {
		bbs.put(EnumFacing.NORTH, NORTH);
		bbs.put(EnumFacing.EAST, EAST);
		bbs.put(EnumFacing.SOUTH, SOUTH);
		bbs.put(EnumFacing.WEST, WEST);
	}
	
	public BlockSleepChamber(String name) {
		super(Material.WOOD, name);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(EnumHalf.HALF, EnumHalf.BOTTOM)
				.withProperty(OPEN, false)
				.withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(20F);
		this.setResistance(30F);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (pos.getY() >= worldIn.getHeight() - 1)
        {
            return false;
        }
		
		return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(EnumHalf.HALF, EnumHalf.TOP).withProperty(FACING, state.getValue(FACING)));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(state.getValue(EnumHalf.HALF) == EnumHalf.BOTTOM) {
			if(worldIn.getBlockState(pos.up()).getBlock() != this) {
				worldIn.setBlockToAir(pos);
				this.dropBlockAsItem(worldIn, pos, state, 0);
			}
		}else {
			if(worldIn.getBlockState(pos.down()).getBlock() != this) {
				worldIn.setBlockToAir(pos);
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(state.getValue(EnumHalf.HALF) == EnumHalf.TOP) pos = pos.down();
		if(w.isRemote) {
			if(!state.getValue(OPEN))
				w.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1, 1, false);
			else {
				w.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.BLOCKS, 1, 1, false);
				EntityPlayer dreamer = getPlayerInside(w, pos);
				if(dreamer != null) {
					dreamer.openGui(BeyondTheVeil.instance, GuiContainerHandler.SLEEP_CHAMBER, w, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}
		w.setBlockState(pos, state.withProperty(OPEN, !state.getValue(OPEN)).withProperty(EnumHalf.HALF, EnumHalf.BOTTOM));
		w.setBlockState(pos.up(), state.withProperty(OPEN, !state.getValue(OPEN)).withProperty(EnumHalf.HALF, EnumHalf.TOP));
		return true;
	}
	
	public EntityPlayer getPlayerInside(World w, BlockPos pos) {
		List<EntityPlayer> ps = w.getPlayers(EntityPlayer.class, p -> p.getDistanceSq(pos) < 2);
		for(EntityPlayer p : ps) {
			if(p.posX > pos.getX() && p.posX < pos.getX()+1 && p.posY > pos.getY() && p.posY < pos.getY()+1 && p.posZ > pos.getZ() && p.posZ < pos.getZ()+1) {
				return p;
			}
		}
		return null;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(state.getValue(EnumHalf.HALF) == EnumHalf.BOTTOM) return super.getItemDropped(state, rand, fortune);
		else return Items.AIR;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {EnumHalf.HALF, OPEN, FACING});
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) {
		return state.getValue(OPEN);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return state.getValue(OPEN);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState()
				.withProperty(FACING, EnumFacing.HORIZONTALS[meta & 3])
				.withProperty(OPEN, (meta & 7) / 4 == 1)
				.withProperty(EnumHalf.HALF, meta/8 == 1 ? EnumHalf.TOP : EnumHalf.BOTTOM);
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(FACING).getHorizontalIndex();
		if(state.getValue(OPEN))
			meta += 4;
		if(state.getValue(EnumHalf.HALF) == EnumHalf.TOP)
			meta += 8;
		return meta;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		for(AxisAlignedBB bbox : getCollisionBoxList(state)) {
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, bbox);
		}
	}
	
	private List<AxisAlignedBB> getCollisionBoxList(IBlockState state){
		List<AxisAlignedBB> collidingBoxes = new ArrayList<>();
		if(state.getValue(EnumHalf.HALF) == EnumHalf.TOP) 
			collidingBoxes.add(FLAT_TOP);
		else
			collidingBoxes.add(FLAT_BOTTOM);
			
		for(Entry<EnumFacing, AxisAlignedBB> entry : bbs.entrySet()) {
			if(!state.getValue(OPEN) || state.getValue(FACING) != entry.getKey()) {
				collidingBoxes.add(entry.getValue());
			} 			
		}
		return collidingBoxes;
	}
	

}
