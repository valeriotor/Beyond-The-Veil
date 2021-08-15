package com.valeriotor.beyondtheveil.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.lib.BlockNames;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager.EmeraldForItems;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DampCanopy extends ModBlock{
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
    {
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.DOWN && p_apply_1_ != EnumFacing.UP;
        }
    });
	
	public static final PropertyBool ISFLAT = PropertyBool.create("is_flat");
	public static final PropertyEnum<DampCanopy.EnumShape> SHAPE = PropertyEnum.<DampCanopy.EnumShape>create("shape", DampCanopy.EnumShape.class);

	
	private static final AxisAlignedBB LAYER1_SOUTH = new AxisAlignedBB(0.0D,0.0D,0.0D,1.0D,0.125D,0.125D);
	private static final AxisAlignedBB LAYER2_SOUTH = new AxisAlignedBB(0.0D,0.125D,0.125D,1.0D,0.25D,0.25D);
	private static final AxisAlignedBB LAYER3_SOUTH = new AxisAlignedBB(0.0D,0.25D,0.25D,1.0D,0.375D,0.375D);
	private static final AxisAlignedBB LAYER4_SOUTH = new AxisAlignedBB(0.0D,0.375D,0.375D,1.0D,0.5D,0.5D);
	private static final AxisAlignedBB LAYER5_SOUTH = new AxisAlignedBB(0.0D,0.5D,0.5D,1.0D,0.625D,0.625D);
	private static final AxisAlignedBB LAYER6_SOUTH = new AxisAlignedBB(0.0D,0.625D,0.625D,1.0D,0.75D,0.75D);
	private static final AxisAlignedBB LAYER7_SOUTH = new AxisAlignedBB(0.0D,0.75D,0.75D,1.0D,0.875D,0.875D);
	private static final AxisAlignedBB LAYER8_SOUTH = new AxisAlignedBB(0.0D,0.875D,0.875D,1.0D,1.0D,1.0D);
	
	private static final AxisAlignedBB LAYER1_NORTH = new AxisAlignedBB(0.0D,0.0D,1.0D,1.0D,0.125D,0.875D);
	private static final AxisAlignedBB LAYER2_NORTH = new AxisAlignedBB(0.0D,0.125D,0.875D,1.0D,0.25D,0.75D);
	private static final AxisAlignedBB LAYER3_NORTH = new AxisAlignedBB(0.0D,0.25D,0.75D,1.0D,0.375D,0.625D);
	private static final AxisAlignedBB LAYER4_NORTH = new AxisAlignedBB(0.0D,0.375D,0.625D,1.0D,0.5D,0.5D);
	private static final AxisAlignedBB LAYER5_NORTH = new AxisAlignedBB(0.0D,0.5D,0.5D,1.0D,0.625D,0.375D);
	private static final AxisAlignedBB LAYER6_NORTH = new AxisAlignedBB(0.0D,0.625D,0.375D,1.0D,0.75D,0.25D);
	private static final AxisAlignedBB LAYER7_NORTH = new AxisAlignedBB(0.0D,0.75D,0.25D,1.0D,0.875D,0.125D);
	private static final AxisAlignedBB LAYER8_NORTH = new AxisAlignedBB(0.0D,0.875D,0.125D,1.0D,1.0D,0.0D);
	
	private static final AxisAlignedBB LAYER1_EAST = new AxisAlignedBB(0.0D,0.0D,0.0D,1.0D,0.125D,1.0D);
	private static final AxisAlignedBB LAYER2_EAST = new AxisAlignedBB(0.125D,0.125D,0.0D,1.0D,0.25D,1.0D);
	private static final AxisAlignedBB LAYER3_EAST = new AxisAlignedBB(0.25D,0.25D,0.0D,1.0D,0.375D,1.0D);
	private static final AxisAlignedBB LAYER4_EAST = new AxisAlignedBB(0.375D,0.375D,0.0D,1.0D,0.5D,1.0D);
	private static final AxisAlignedBB LAYER5_EAST = new AxisAlignedBB(0.5D,0.5D,0.0D,1.0D,0.625D,1.0D);
	private static final AxisAlignedBB LAYER6_EAST = new AxisAlignedBB(0.625D,0.625D,0.0D,1.0D,0.75D,1.0D);
	private static final AxisAlignedBB LAYER7_EAST = new AxisAlignedBB(0.75D,0.75D,0.0D,1.0D,0.875D,1.0D);
	private static final AxisAlignedBB LAYER8_EAST = new AxisAlignedBB(0.875D,0.875D,0.0D,1.0D,1.0D,1.0D);
	
	private static final AxisAlignedBB LAYER1_WEST = new AxisAlignedBB(1.0D,0.0D,0.0D,0.875D,0.125D,1.0D);
	private static final AxisAlignedBB LAYER2_WEST = new AxisAlignedBB(0.875D,0.125D,0.0D,0.75D,0.25D,1.0D);
	private static final AxisAlignedBB LAYER3_WEST = new AxisAlignedBB(0.75D,0.25D,0.0D,0.625D,0.375D,1.0D);
	private static final AxisAlignedBB LAYER4_WEST = new AxisAlignedBB(0.625D,0.375D,0.0D,0.5D,0.5D,1.0D);
	private static final AxisAlignedBB LAYER5_WEST = new AxisAlignedBB(0.5D,0.5D,0.0D,0.375D,0.625D,1.0D);
	private static final AxisAlignedBB LAYER6_WEST = new AxisAlignedBB(0.375D,0.625D,0.0D,0.25D,0.75D,1.0D);
	private static final AxisAlignedBB LAYER7_WEST = new AxisAlignedBB(0.25D,0.75D,0.0D,0.125D,0.875D,1.0D);
	private static final AxisAlignedBB LAYER8_WEST = new AxisAlignedBB(0.125D,0.875D,0.0D,0.0D,1.0D,1.0D);
	
	private static final AxisAlignedBB FLAT = new AxisAlignedBB(0.0D,0.0D,0.0D,1.0D,0.125D,1.0D);
	
	private static final AxisAlignedBB LAYER1_SOUTHEAST1 = new AxisAlignedBB(0.0D,0.0D,0.0D,0.125D,0.125D,0.125D);
	private static final AxisAlignedBB LAYER2_SOUTHEAST1 = new AxisAlignedBB(0.0D,0.125D,0.125D,0.25D,0.25D,0.25D);
	private static final AxisAlignedBB LAYER2_SOUTHEAST2 = new AxisAlignedBB(0.125D,0.125D,0.0D,0.25D,0.25D,0.125D);
	private static final AxisAlignedBB LAYER3_SOUTHEAST1 = new AxisAlignedBB(0.0D,0.25D,0.25D,0.375D,0.375D,0.375D);
	private static final AxisAlignedBB LAYER3_SOUTHEAST2 = new AxisAlignedBB(0.25D,0.25D,0.0D,0.375D,0.375D,0.25D);
	private static final AxisAlignedBB LAYER4_SOUTHEAST1 = new AxisAlignedBB(0.0D,0.375D,0.375D,0.5D,0.5D,0.5D);
	private static final AxisAlignedBB LAYER4_SOUTHEAST2 = new AxisAlignedBB(0.375D,0.375D,0.0D,0.5D,0.5D,0.375D);
	private static final AxisAlignedBB LAYER5_SOUTHEAST1 = new AxisAlignedBB(0.0D,0.5D,0.5D,0.625D,0.625D,0.625D);
	private static final AxisAlignedBB LAYER5_SOUTHEAST2 = new AxisAlignedBB(0.5D,0.5D,0.0D,0.625D,0.625D,0.5D);
	private static final AxisAlignedBB LAYER6_SOUTHEAST1 = new AxisAlignedBB(0.0D,0.625D,0.625D,0.75D,0.75D,0.75D);
	private static final AxisAlignedBB LAYER6_SOUTHEAST2 = new AxisAlignedBB(0.625D,0.625D,0.0D,0.75D,0.75D,0.625D);
	private static final AxisAlignedBB LAYER7_SOUTHEAST1 = new AxisAlignedBB(0.0D,0.75D,0.75D,0.875D,0.875D,0.875D);
	private static final AxisAlignedBB LAYER7_SOUTHEAST2 = new AxisAlignedBB(0.75D,0.75D,0.0D,0.875D,0.875D,0.75D);
	private static final AxisAlignedBB LAYER8_SOUTHEAST1 = new AxisAlignedBB(0.0D,0.875D,0.875D,1.0D,1.0D,1.0D);
	private static final AxisAlignedBB LAYER8_SOUTHEAST2 = new AxisAlignedBB(0.875D,0.875D,0.0D,1.0D,1.0D,0.875D);

	private static final AxisAlignedBB LAYER1_SOUTHWEST1 = new AxisAlignedBB(1.0D,0.0D,0.0D,0.875D,0.125D,0.125D);
	private static final AxisAlignedBB LAYER2_SOUTHWEST1 = new AxisAlignedBB(1.0D,0.125D,0.125D,0.75D,0.25D,0.25D);
	private static final AxisAlignedBB LAYER2_SOUTHWEST2 = new AxisAlignedBB(0.875D,0.125D,0.0D,0.75D,0.25D,0.125D);
	private static final AxisAlignedBB LAYER3_SOUTHWEST1 = new AxisAlignedBB(1.0D,0.25D,0.25D,0.625D,0.375D,0.375D);
	private static final AxisAlignedBB LAYER3_SOUTHWEST2 = new AxisAlignedBB(0.75D,0.25D,0.0D,0.625D,0.375D,0.25D);
	private static final AxisAlignedBB LAYER4_SOUTHWEST1 = new AxisAlignedBB(1.0D,0.375D,0.375D,0.5D,0.5D,0.5D);
	private static final AxisAlignedBB LAYER4_SOUTHWEST2 = new AxisAlignedBB(0.625D,0.375D,0.0D,0.5D,0.5D,0.375D);
	private static final AxisAlignedBB LAYER5_SOUTHWEST1 = new AxisAlignedBB(1.0D,0.5D,0.5D,0.375D,0.625D,0.625D);
	private static final AxisAlignedBB LAYER5_SOUTHWEST2 = new AxisAlignedBB(0.5D,0.5D,0.0D,0.375D,0.625D,0.5D);
	private static final AxisAlignedBB LAYER6_SOUTHWEST1 = new AxisAlignedBB(1.0D,0.625D,0.625D,0.25D,0.75D,0.75D);
	private static final AxisAlignedBB LAYER6_SOUTHWEST2 = new AxisAlignedBB(0.375D,0.625D,0.0D,0.25D,0.75D,0.625D);
	private static final AxisAlignedBB LAYER7_SOUTHWEST1 = new AxisAlignedBB(1.0D,0.75D,0.75D,0.125D,0.875D,0.875D);
	private static final AxisAlignedBB LAYER7_SOUTHWEST2 = new AxisAlignedBB(0.25D,0.75D,0.0D,0.125D,0.875D,0.75D);
	private static final AxisAlignedBB LAYER8_SOUTHWEST1 = new AxisAlignedBB(1.0D,0.875D,0.875D,0.0D,1.0D,1.0D);
	private static final AxisAlignedBB LAYER8_SOUTHWEST2 = new AxisAlignedBB(0.125D,0.875D,0.0D,0.0D,1.0D,0.875D);

	
	
	public DampCanopy() {
		super(Material.WOOD, BlockNames.DAMPCANOPY);
		this.setResistance(2000.0F);
		this.setHardness(4.0F);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ISFLAT, false));
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if(state.getValue(ISFLAT)) return FLAT;
		else return FULL_BLOCK_AABB;
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
		DampCanopy.EnumShape shape = state.getValue(SHAPE);
		if(!state.getValue(ISFLAT)) {
		if(shape != DampCanopy.EnumShape.INNER_LEFT && shape != DampCanopy.EnumShape.INNER_RIGHT && shape != DampCanopy.EnumShape.OUTER_LEFT && shape != DampCanopy.EnumShape.OUTER_RIGHT ) {
			if(state.getValue(FACING) == EnumFacing.NORTH) {
				list.add(LAYER1_NORTH);
				list.add(LAYER2_NORTH);
				list.add(LAYER3_NORTH);
				list.add(LAYER4_NORTH);
				list.add(LAYER5_NORTH);
				list.add(LAYER6_NORTH);
				list.add(LAYER7_NORTH);
				list.add(LAYER8_NORTH);
			}else if(state.getValue(FACING) == EnumFacing.EAST) {
				list.add(LAYER1_EAST);
				list.add(LAYER2_EAST);
				list.add(LAYER3_EAST);
				list.add(LAYER4_EAST);
				list.add(LAYER5_EAST);
				list.add(LAYER6_EAST);
				list.add(LAYER7_EAST);
				list.add(LAYER8_EAST);
			}else if(state.getValue(FACING) == EnumFacing.SOUTH) {
				list.add(LAYER1_SOUTH);
				list.add(LAYER2_SOUTH);
				list.add(LAYER3_SOUTH);
				list.add(LAYER4_SOUTH);
				list.add(LAYER5_SOUTH);
				list.add(LAYER6_SOUTH);
				list.add(LAYER7_SOUTH);
				list.add(LAYER8_SOUTH);
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
		}else if(state.getValue(SHAPE) == DampCanopy.EnumShape.INNER_LEFT) {
			if(state.getValue(FACING) == EnumFacing.WEST) {
				list.add(LAYER1_SOUTHWEST1);
				list.add(LAYER2_SOUTHWEST1);
				list.add(LAYER3_SOUTHWEST1);
				list.add(LAYER4_SOUTHWEST1);
				list.add(LAYER5_SOUTHWEST1);
				list.add(LAYER6_SOUTHWEST1);
				list.add(LAYER7_SOUTHWEST1);
				list.add(LAYER8_SOUTHWEST1);
				list.add(LAYER2_SOUTHWEST2);
				list.add(LAYER3_SOUTHWEST2);
				list.add(LAYER4_SOUTHWEST2);
				list.add(LAYER5_SOUTHWEST2);
				list.add(LAYER6_SOUTHWEST2);
				list.add(LAYER7_SOUTHWEST2);
				list.add(LAYER8_SOUTHWEST2);
			}else if(state.getValue(FACING) == EnumFacing.EAST) {
				list.add(LAYER1_SOUTHEAST1);
				list.add(LAYER2_SOUTHEAST1);
				list.add(LAYER3_SOUTHEAST1);
				list.add(LAYER4_SOUTHEAST1);
				list.add(LAYER5_SOUTHEAST1);
				list.add(LAYER6_SOUTHEAST1);
				list.add(LAYER7_SOUTHEAST1);
				list.add(LAYER8_SOUTHEAST1);
				list.add(LAYER2_SOUTHEAST2);
				list.add(LAYER3_SOUTHEAST2);
				list.add(LAYER4_SOUTHEAST2);
				list.add(LAYER5_SOUTHEAST2);
				list.add(LAYER6_SOUTHEAST2);
				list.add(LAYER7_SOUTHEAST2);
				list.add(LAYER8_SOUTHEAST2);
								
			}
		}else if(state.getValue(SHAPE) == DampCanopy.EnumShape.INNER_RIGHT) {
			
		}else if(state.getValue(SHAPE) == DampCanopy.EnumShape.OUTER_LEFT) {
			
		}else if(state.getValue(SHAPE) == DampCanopy.EnumShape.OUTER_RIGHT) {
			
		}
		}
		
		if(state.getValue(ISFLAT)) list.add(FLAT);
		if(list.isEmpty()) list.add(FLAT);
		
		return list;
		
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		/*EnumFacing face = facing;
		if(facing == EnumFacing.UP) {
			if(hitZ > hitX && hitZ > 1-hitX) face = EnumFacing.SOUTH;
			else if(hitZ > 1-hitX && hitZ < hitX) face = EnumFacing.EAST;
			else if(hitZ < hitX && hitZ < 1-hitX) face = EnumFacing.NORTH;
			else if(hitZ > hitX && hitZ < 1-hitX) face = EnumFacing.WEST;
			
			System.out.println(face.getName());
		}
        if (this.canPlaceAt(worldIn, pos, face))
        {
        	System.out.println("Bob");
            return this.getDefaultState().withProperty(FACING, face);
        }
        else
        {
        	System.out.println("Test");
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
            {
                if (this.canPlaceAt(worldIn, pos, enumfacing))
                {
                    return this.getDefaultState().withProperty(FACING, enumfacing);
                }
            }

            return this.getDefaultState();
        }*/
		if(facing!=EnumFacing.UP) {
			if(!placer.isSneaking()) return this.getDefaultState().withProperty(FACING,placer.getHorizontalFacing());
			else return this.getDefaultState().withProperty(ISFLAT, true).withProperty(FACING,placer.getHorizontalFacing());
		}
		if(placer.isSneaking()) return this.getDefaultState().withProperty(FACING,placer.getHorizontalFacing());
		else return this.getDefaultState().withProperty(ISFLAT, true).withProperty(FACING,placer.getHorizontalFacing());
    }
	
	
	
	private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing)
    {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

        if (facing.equals(EnumFacing.UP))
        {
            return true;
        }
        else if (facing != EnumFacing.UP && facing != EnumFacing.DOWN)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
	
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : FACING.getAllowedValues())
        {
            if (this.canPlaceAt(worldIn, pos, enumfacing))
            {
                return true;
            }
        }

        return false;
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState();
		
		switch (meta)
        {
            case 1:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(ISFLAT, false);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(ISFLAT, false);
                break;
            case 3:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(ISFLAT, false);
                break;
            case 4:
            default:	
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(ISFLAT, false);
                break;
            case 5:
            	iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(ISFLAT, true);
            case 6:
            	iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(ISFLAT, true);
            case 7:
            	iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(ISFLAT, true);
            case 8:
            	iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(ISFLAT, true);
            
        }    
            
		return iblockstate;
	}
	
	
	public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        
        if(!state.getValue(ISFLAT)) {
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
        }else if(state.getValue(ISFLAT)) {
        	switch ((EnumFacing)state.getValue(FACING))
            {
                case EAST:
                    i = i | 5;
                    break;
                case SOUTH:
                    i = i | 6;
                    break;
                case WEST:
                    i = i | 7;
                    break;
                case NORTH:
                default:	
                    i = i | 8;
                    break;
            }
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
	
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
		if(state.getValue(ISFLAT) && face == EnumFacing.DOWN) return BlockFaceShape.SOLID;
		else return BlockFaceShape.UNDEFINED;
    }
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING,ISFLAT,SHAPE});
    }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(SHAPE, getActualShape(state, worldIn, pos));
	}
	
	private static DampCanopy.EnumShape getActualShape(IBlockState state1, IBlockAccess w, BlockPos pos){
		EnumFacing ef1 = (EnumFacing)state1.getValue(FACING);
		
		IBlockState state2 = (w.getBlockState(pos.offset(ef1)));
		
		if((state2.getBlock() instanceof DampCanopy || state2.getBlock() instanceof DampCanopyWood) && !state1.getValue(ISFLAT)) {
			EnumFacing ef2 = state2.getValue(FACING);
			if(ef2.getAxis() != ef1.getAxis()) {
				if(ef2 == ef1.rotateYCCW()) {
					return DampCanopy.EnumShape.OUTER_LEFT;
				}
				return DampCanopy.EnumShape.OUTER_RIGHT;
			}
		}
		
		IBlockState state3 = w.getBlockState(pos.offset(ef1.getOpposite()));
		
		if((state3.getBlock() instanceof DampCanopy || state3.getBlock() instanceof DampCanopyWood) && !state1.getValue(ISFLAT)) {
			EnumFacing ef3 = state3.getValue(FACING);
			if(ef3.getAxis() != ef1.getAxis()) {
				if(ef3 == ef1.rotateYCCW()) {
					return DampCanopy.EnumShape.INNER_LEFT;
				}
				return DampCanopy.EnumShape.INNER_RIGHT;
			}
		}
		
		return DampCanopy.EnumShape.STRAIGHT;
	}
	
	
	public static enum EnumShape implements IStringSerializable
    {
        STRAIGHT("straight"),
        INNER_LEFT("inner_left"),
        INNER_RIGHT("inner_right"),
		OUTER_LEFT("outer_left"),
		OUTER_RIGHT("outer_right");

        private final String name;

        private EnumShape(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }
    }
	
}
