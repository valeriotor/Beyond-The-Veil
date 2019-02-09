package com.valeriotor.BTV.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.BlockNames;
import com.valeriotor.BTV.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DampCanopyWood extends ModBlock{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
    {
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.DOWN && p_apply_1_ != EnumFacing.UP;
        }
    });
	
	private static final AxisAlignedBB LAYER1_NORTH = new AxisAlignedBB(0.0D,0.0D,1.0D,1.0D,0.125D,0.0D);
	private static final AxisAlignedBB LAYER2_NORTH = new AxisAlignedBB(0.0D,0.125D,0.875D,1.0D,0.25D,0.0D);
	private static final AxisAlignedBB LAYER3_NORTH = new AxisAlignedBB(0.0D,0.25D,0.75D,1.0D,0.375D,0.0D);
	private static final AxisAlignedBB LAYER4_NORTH = new AxisAlignedBB(0.0D,0.375D,0.625D,1.0D,0.5D,0.0D);
	private static final AxisAlignedBB LAYER5_NORTH = new AxisAlignedBB(0.0D,0.5D,0.5D,1.0D,0.625D,0.0D);
	private static final AxisAlignedBB LAYER6_NORTH = new AxisAlignedBB(0.0D,0.625D,0.375D,1.0D,0.75D,0.0D);
	private static final AxisAlignedBB LAYER7_NORTH = new AxisAlignedBB(0.0D,0.75D,0.25D,1.0D,0.875D,0.0D);
	private static final AxisAlignedBB LAYER8_NORTH = new AxisAlignedBB(0.0D,0.875D,0.125D,1.0D,1.0D,0.0D);
	
	private static final AxisAlignedBB LAYER1_SOUTH = new AxisAlignedBB(0.0D,0.0D,0.0D,1.0D,0.125D,1.0D);
	private static final AxisAlignedBB LAYER2_SOUTH = new AxisAlignedBB(0.0D,0.125D,0.125D,1.0D,0.25D,1.0D);
	private static final AxisAlignedBB LAYER3_SOUTH = new AxisAlignedBB(0.0D,0.25D,0.25D,1.0D,0.375D,1.0D);
	private static final AxisAlignedBB LAYER4_SOUTH = new AxisAlignedBB(0.0D,0.375D,0.375D,1.0D,0.5D,1.0D);
	private static final AxisAlignedBB LAYER5_SOUTH = new AxisAlignedBB(0.0D,0.5D,0.5D,1.0D,0.625D,1.0D);
	private static final AxisAlignedBB LAYER6_SOUTH = new AxisAlignedBB(0.0D,0.625D,0.625D,1.0D,0.75D,1.0D);
	private static final AxisAlignedBB LAYER7_SOUTH = new AxisAlignedBB(0.0D,0.75D,0.75D,1.0D,0.875D,1.0D);
	private static final AxisAlignedBB LAYER8_SOUTH = new AxisAlignedBB(0.0D,0.875D,0.875D,1.0D,1.0D,1.0D);
	
	private static final AxisAlignedBB LAYER1_WEST = new AxisAlignedBB(1.0D,0.0D,0.0D,0.875D,0.125D,1.0D);
	private static final AxisAlignedBB LAYER2_WEST = new AxisAlignedBB(0.875D,0.125D,0.0D,0.75D,0.25D,1.0D);
	private static final AxisAlignedBB LAYER3_WEST = new AxisAlignedBB(0.75D,0.25D,0.0D,0.625D,0.375D,1.0D);
	private static final AxisAlignedBB LAYER4_WEST = new AxisAlignedBB(0.625D,0.375D,0.0D,0.5D,0.5D,1.0D);
	private static final AxisAlignedBB LAYER5_WEST = new AxisAlignedBB(0.5D,0.5D,0.0D,0.375D,0.625D,1.0D);
	private static final AxisAlignedBB LAYER6_WEST = new AxisAlignedBB(0.375D,0.625D,0.0D,0.25D,0.75D,1.0D);
	private static final AxisAlignedBB LAYER7_WEST = new AxisAlignedBB(0.25D,0.75D,0.0D,0.125D,0.875D,1.0D);
	private static final AxisAlignedBB LAYER8_WEST = new AxisAlignedBB(0.125D,0.875D,0.0D,0.0D,1.0D,1.0D);
	
	private static final AxisAlignedBB LAYER1_EAST = new AxisAlignedBB(0.0D,0.0D,0.0D,0.125D,0.125D,1.0D);
	private static final AxisAlignedBB LAYER2_EAST = new AxisAlignedBB(0.125D,0.125D,0.0D,0.25D,0.25D,1.0D);
	private static final AxisAlignedBB LAYER3_EAST = new AxisAlignedBB(0.25D,0.25D,0.0D,0.375D,0.375D,1.0D);
	private static final AxisAlignedBB LAYER4_EAST = new AxisAlignedBB(0.375D,0.375D,0.0D,0.5D,0.5D,1.0D);
	private static final AxisAlignedBB LAYER5_EAST = new AxisAlignedBB(0.5D,0.5D,0.0D,0.625D,0.625D,1.0D);
	private static final AxisAlignedBB LAYER6_EAST = new AxisAlignedBB(0.625D,0.625D,0.0D,0.75D,0.75D,1.0D);
	private static final AxisAlignedBB LAYER7_EAST = new AxisAlignedBB(0.75D,0.75D,0.0D,0.875D,0.875D,1.0D);
	private static final AxisAlignedBB LAYER8_EAST = new AxisAlignedBB(0.875D,0.875D,0.0D,1.0D,1.0D,1.0D);
	
	
	
	public DampCanopyWood() {
		super(Material.WOOD, BlockNames.DAMPCANOPYWOOD);
		this.setResistance(2000.0F);
		this.setHardness(4.0F);
		this.setSoundType(SoundType.WOOD);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		
			for(AxisAlignedBB BBox : getCollisionBoxList(state)) {
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BBox);
			}
			
		
		
	}
	
	public List<AxisAlignedBB> getCollisionBoxList(IBlockState state){
		List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
		
		if(state.getValue(FACING) == EnumFacing.NORTH) {
			list.add(LAYER1_NORTH);
			list.add(LAYER2_NORTH);
			list.add(LAYER3_NORTH);
			list.add(LAYER4_NORTH);
			list.add(LAYER5_NORTH);
			list.add(LAYER6_NORTH);
			list.add(LAYER7_NORTH);
			list.add(LAYER8_NORTH);
		}else if(state.getValue(FACING) == EnumFacing.SOUTH) {
			list.add(LAYER1_SOUTH);
			list.add(LAYER2_SOUTH);
			list.add(LAYER3_SOUTH);
			list.add(LAYER4_SOUTH);
			list.add(LAYER5_SOUTH);
			list.add(LAYER6_SOUTH);
			list.add(LAYER7_SOUTH);
			list.add(LAYER8_SOUTH);
		}else if(state.getValue(FACING) == EnumFacing.EAST) {
			list.add(LAYER1_EAST);
			list.add(LAYER2_EAST);
			list.add(LAYER3_EAST);
			list.add(LAYER4_EAST);
			list.add(LAYER5_EAST);
			list.add(LAYER6_EAST);
			list.add(LAYER7_EAST);
			list.add(LAYER8_EAST);
		}else if(state.getValue(FACING) == EnumFacing.WEST) {
			list.add(LAYER1_WEST);
			list.add(LAYER2_WEST);
			list.add(LAYER3_WEST);
			list.add(LAYER4_WEST);
			list.add(LAYER5_WEST);
			list.add(LAYER6_WEST);
			list.add(LAYER7_WEST);
			list.add(LAYER8_WEST);
		}
		
		return list;
		
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		// TODO Auto-generated method stub
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState();
		
		switch(meta) {
		case 1:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
            break;
        case 2:	
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
            break;
        case 3:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
            break;
        case 4:
        default:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
            break;
		
		}
		return iblockstate;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		 switch ((EnumFacing)state.getValue(FACING))
	        {
	            case EAST:
	                i = i | 1;
	                break;
	            case SOUTH:	
	                i = i | 2;
	                break;
	            case WEST:
	                i = i | 3;
	                break;
	            case NORTH:
	            default:	
	                i = i | 4;
	                break;
	        }
		return i;
	}
	
	public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
	
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
	
	
	 public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		 if(face.getAxis() == Axis.Y) {
			 if(face == EnumFacing.UP) return BlockFaceShape.SOLID;
		 }else {
			 if(face == state.getValue(FACING)) return BlockFaceShape.SOLID;
		 }
		 return BlockFaceShape.UNDEFINED;
	 }
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		//System.out.println(playerIn.getActiveItemStack().getItem());
		
		IBlockState state = worldIn.getBlockState(pos);
		worldIn.setBlockState(pos, BlockRegistry.DampWoodStairs.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		if(!worldIn.isRemote) {
			EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY()+1, pos.getZ(), new ItemStack(BlockRegistry.DampCanopy));
			worldIn.spawnEntity(item);
		}
		worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1F, 1F, false);
		
		
	}
	
}
