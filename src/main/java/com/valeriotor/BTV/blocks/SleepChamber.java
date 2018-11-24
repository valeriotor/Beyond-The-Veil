package com.valeriotor.BTV.blocks;

import java.util.Random;

import com.valeriotor.BTV.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SleepChamber extends Block{
	
	public static final PropertyEnum<SleepChamber.EnumHalf> HALF = PropertyEnum.<SleepChamber.EnumHalf>create("half", SleepChamber.EnumHalf.class);
	
	public SleepChamber() {
		super(Material.IRON);
		this.setRegistryName(References.MODID + ":sleep_chamber");
		this.setUnlocalizedName("sleep_chamber");
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, SleepChamber.EnumHalf.BOTTOM));
		this.setHardness(20F);
		this.setResistance(30F);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		boolean flag = false;
		if(worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR) flag = true;
		return super.canPlaceBlockAt(worldIn, pos) && flag;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, SleepChamber.EnumHalf.TOP));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(state.getValue(HALF) == SleepChamber.EnumHalf.BOTTOM) {
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
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(state.getValue(HALF) == SleepChamber.EnumHalf.BOTTOM) return super.getItemDropped(state, rand, fortune);
		else return Items.AIR;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {HALF});
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
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState().withProperty(HALF, meta == 0 ? SleepChamber.EnumHalf.BOTTOM : SleepChamber.EnumHalf.TOP);
		
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(HALF) == SleepChamber.EnumHalf.BOTTOM ? 0 : 1;
		
		return meta;
	}
	
	public enum EnumHalf implements IStringSerializable{
		TOP("top"),
		BOTTOM("bottom");

		private final String name;

        private EnumHalf(String name)
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
