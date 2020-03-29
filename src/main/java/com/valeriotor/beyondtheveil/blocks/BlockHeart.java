package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.tileEntities.TileHeart;
import com.valeriotor.beyondtheveil.util.ItemHelper;
import com.valeriotor.beyondtheveil.util.SyncUtil;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHeart extends ModBlock implements ITileEntityProvider{
	
	private static final double a = 0.0625;
	private static final AxisAlignedBB BBOX = new AxisAlignedBB(a*7, 0, a*7, a*9, a*5, a*9);
	
	public BlockHeart(Material materialIn, String name) {
		super(materialIn, name);
		this.setHardness(1);
		this.setSoundType(SoundType.SLIME);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
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
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileHeart();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BBOX;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).isTopSolid();
	}
	
	@Override
	public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if(!w.isRemote) {
			boolean well = BlockRegistry.BlockBloodWell.checkStructure(pos, w);
			if(well) {
				TileEntity te = w.getTileEntity(pos);
				if(te instanceof TileHeart) {
					TileHeart th = (TileHeart) te;
					th.startWell();
					if(placer instanceof EntityPlayer) {
						EntityPlayer p = (EntityPlayer)placer;
						if(ResearchUtil.getResearchStage(p, "BLOODWELL") == 0)
							SyncUtil.addStringDataOnServer(p, false, "builtwell");
					}
					
				}
			}
		}
		super.onBlockPlacedBy(w, pos, state, placer, stack);
	}

}
