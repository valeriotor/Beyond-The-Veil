package com.valeriotor.BTV.blocks;

import java.util.Random;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.gui.Guis;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSleepChamber extends ModBlock{
	
	
	public BlockSleepChamber(String name) {
		super(Material.IRON, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(EnumHalf.HALF, EnumHalf.BOTTOM));
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
		worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(EnumHalf.HALF, EnumHalf.TOP));
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer p,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(state.getValue(EnumHalf.HALF) == EnumHalf.TOP) pos = pos.down();
		p.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
		p.motionX = 0;
		p.motionY = 0;
		p.motionZ = 0;
		//playerIn.setLocationAndAngles(pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, playerIn.getRotationYawHead(), 0);
		if(worldIn.isRemote) BeyondTheVeil.proxy.openGui(Guis.GuiSleepingChamber);
		return true;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(state.getValue(EnumHalf.HALF) == EnumHalf.BOTTOM) return super.getItemDropped(state, rand, fortune);
		else return Items.AIR;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {EnumHalf.HALF});
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
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
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState().withProperty(EnumHalf.HALF, meta == 0 ? EnumHalf.BOTTOM : EnumHalf.TOP);
		
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(EnumHalf.HALF) == EnumHalf.BOTTOM ? 0 : 1;
		
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
	

}
