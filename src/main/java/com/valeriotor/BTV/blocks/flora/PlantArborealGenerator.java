package com.valeriotor.BTV.blocks.flora;

import java.util.Random;

import com.valeriotor.BTV.blocks.BlockSleepChamber;
import com.valeriotor.BTV.blocks.EnumHalf;
import com.valeriotor.BTV.tileEntities.TileArborealGeneratorBottom;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlantArborealGenerator extends BlockTallPlant implements ITileEntityProvider, IMutationCatalyst{
	
	
	public PlantArborealGenerator(String name) {
		super(Material.PLANTS, name);
		this.setSoundType(SoundType.PLANT);
		this.spreadChance = 5;
		this.spreadMinMutation = 1000;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(meta == 0) return new TileArborealGeneratorBottom();
		return null;
	}

	@Override
	public int mutationIncrease() {
		return 12;
	}
	
	
	

}
