package com.valeriotor.beyondtheveil.blocks;

import java.util.Random;

import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.BlockNames;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tileEntities.TileBarrel;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class BlockBarrel extends ModBlock implements ITileEntityProvider{
	
	private static final AxisAlignedBB BBOX = new AxisAlignedBB(0.125,0.0,0.125,0.875,0.875,0.875); 
	
	public BlockBarrel() {
		super(Material.WOOD, BlockNames.BARREL);
		this.setResistance(2000.0F);
		this.setHardness(4.0F);
		this.setSoundType(SoundType.WOOD);
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBarrel();
	}
	
	public TileBarrel getTE(World w, BlockPos pos) {
		return (TileBarrel) w.getTileEntity(pos);
	}
	
	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(hand != EnumHand.MAIN_HAND) return false;
		TileBarrel b = getTE(w, pos);
		if(p.isSneaking()) {
			if(!w.isRemote) ItemHandlerHelper.giveItemToPlayer(p, b.getContents());
			else if(b.getFishCount() > 0) w.playSound(p, pos, SoundEvents.BLOCK_SLIME_FALL, SoundCategory.BLOCKS, 0.1F, 1F);
			return true;
		} else {
			if(!w.isRemote) b.increaseFishCount(p.getHeldItemMainhand());
			else if(b.canIncrease(p.getHeldItemMainhand())) w.playSound(p, pos, SoundEvents.BLOCK_SLIME_FALL, SoundCategory.BLOCKS, 0.1F, 1F);
			return true;
		}
	}
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	
	 @Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		if(worldIn.isRemote) return;
		EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY()+1, pos.getZ(), getTE(worldIn, pos).getContents());
		worldIn.spawnEntity(item);
	}
	 

}
