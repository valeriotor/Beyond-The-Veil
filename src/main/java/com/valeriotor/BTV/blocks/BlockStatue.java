package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.tileEntities.TileStatue;
import com.valeriotor.BTV.worship.CrawlerWorship.WorshipType;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStatue extends ModBlock implements ITileEntityProvider{
	
	public final WorshipType type;
	private static final double a = 0.0625;
	private static final AxisAlignedBB BBOX = new AxisAlignedBB(a*6, 0, a*6, a*10, a*15, a*10);
	
	public BlockStatue(Material material, String name, WorshipType type) {
		super(material, name);
		this.type = type;
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

}
