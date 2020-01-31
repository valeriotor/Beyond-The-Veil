package com.valeriotor.BTV.blocks;



import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.events.ServerTickEvents;
import com.valeriotor.BTV.lib.BlockNames;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.util.DelayedMessage;
import com.valeriotor.BTV.util.SyncUtil;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;
import com.valeriotor.BTV.worship.Worship;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockIdol extends ModBlock{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
    {
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.DOWN && p_apply_1_ != EnumFacing.UP;
        }
    });
	
	public BlockIdol() {
		super(Material.ROCK, BlockNames.IDOL);
        this.setBlockUnbreakable();
        this.setResistance(6000001.0F);
		
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
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		switch(meta) {
			case 0:
				state = state.withProperty(FACING, EnumFacing.SOUTH);
				break;
			case 1:
				state = state.withProperty(FACING, EnumFacing.WEST);
				break;
			case 2:
				state = state.withProperty(FACING, EnumFacing.NORTH);
				break;
			case 3:
				state = state.withProperty(FACING, EnumFacing.EAST);
				break;	
			
		}
		
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(FACING).getHorizontalIndex();
		
		return meta;
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
        return BlockFaceShape.UNDEFINED;
    }
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
	
	private static final AxisAlignedBB BBOX_BASE = new AxisAlignedBB(0.1875D,0.0D,0.1875D,0.8125D,0.25D,0.8125D);
	private static final AxisAlignedBB BBOX_NORTH = new AxisAlignedBB(0.1875D,0.0D,0.1875D,0.8125D,0.875D,0.6875D);
	private static final AxisAlignedBB BBOX_WEST = new AxisAlignedBB(0.1875D,0.0D,0.1875D,0.6875D,0.875D,0.8125D);
	private static final AxisAlignedBB BBOX_SOUTH = new AxisAlignedBB(0.1875D,0.0D,0.3125D,0.8125D,0.875D,0.8125D);
	private static final AxisAlignedBB BBOX_EAST = new AxisAlignedBB(0.3125D,0.0D,0.1875D,0.8125D,0.875D,0.8125D);
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch(state.getValue(FACING)) {
		case NORTH: return BBOX_NORTH;
		case WEST: return BBOX_WEST;
		case SOUTH: return BBOX_SOUTH;
		case EAST: return BBOX_EAST;
		default: return BBOX_NORTH;
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		
		if(!ResearchUtil.isResearchVisible(playerIn, "IDOL")) {
			if(!worldIn.isRemote) playerIn.sendMessage(new TextComponentTranslation("interact.idol.notyet"));
			return true;
		}
		if(!worldIn.isRemote) {
			if(ResearchUtil.getResearchStage(playerIn, "IDOL") == 0) SyncUtil.addStringDataOnServer(playerIn, false, "IdolInteract");
			if(Worship.getSelectedDeity(playerIn) != Deities.GREATDREAMER) Worship.setSelectedDeity(playerIn, Deities.GREATDREAMER);
			DGWorshipHelper.levelUp(playerIn);
		} else {
			BeyondTheVeil.proxy.cEvents.muteSounds(100);
		}
		return true;
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		// WIP
		if(!worldIn.isRemote) ServerTickEvents.addMessage(new DelayedMessage(60, new TextComponentTranslation("innervoice.idol"), playerIn));
		super.onBlockClicked(worldIn, pos, playerIn);
	}
	
	
}
