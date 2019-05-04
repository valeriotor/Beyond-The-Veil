package com.valeriotor.BTV.blocks;



import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.lib.BlockNames;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.util.DGWorshipHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

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
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.1875D,0.0D,0.1875D,0.8125D,0.875D,0.8125D);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(playerIn);
		if(!k.isResearchKnown("CRYSTALDREAMS")) return true; // TODO: Change required research
		if(!worldIn.isRemote) {
			if(playerIn.getCapability(DGProvider.LEVEL_CAP, null).getLevel() == 0) {
				playerIn.getCapability(DGProvider.LEVEL_CAP, null).addLevel();
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("level", playerIn.getCapability(DGProvider.LEVEL_CAP, null).getLevel()), (EntityPlayerMP)playerIn);
			} else {
				DGWorshipHelper.levelUp(playerIn);
			}
			// DEBUG playerIn.sendMessage(new TextComponentString(Integer.toString(playerIn.getCapability(DGProvider.LEVEL_CAP, null).getLevel()))); // TODO: Delete this
		} else {
			BeyondTheVeil.proxy.cEvents.muteSounds(100);
		}
		return true;
	}
	
	
}
