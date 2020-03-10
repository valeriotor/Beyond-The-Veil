package com.valeriotor.beyondtheveil.blocks.flora;

import java.util.List;
import java.util.Random;

import com.valeriotor.beyondtheveil.blocks.ModBlock;
import com.valeriotor.beyondtheveil.items.ItemRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRedstoneGrass extends ModBlock{
	
	public static final PropertyEnum<BlockRedstoneGrass.RedstoneLevels> POWER = PropertyEnum.create("power", BlockRedstoneGrass.RedstoneLevels.class);
	
	public static final AxisAlignedBB BBOX = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.9325, 1.0);
	
	public BlockRedstoneGrass(String name) {
		super(Material.GRASS, name);
		this.setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(POWER, RedstoneLevels.NONE));
		this.setHardness(0.6F);		
	}
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		drops.add(new ItemStack(Blocks.DIRT));
		drops.add(new ItemStack(ItemRegistry.redstone_weed_seeds));
	}
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return state.getValue(POWER) != RedstoneLevels.NONE;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		
		return blockState.getValue(POWER).getPower();
	}
	
	@Override
	public int tickRate(World worldIn) {
		return 20;
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		List<EntityLivingBase> entities = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
		if(state.getValue(POWER) != RedstoneLevels.NONE && entities.isEmpty()) worldIn.setBlockState(pos, state.withProperty(POWER, RedstoneLevels.NONE));
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity e) {
		RedstoneLevels type = RedstoneLevels.NONE;
		if(e instanceof EntityPlayer) type = RedstoneLevels.PLAYER;
		else if(e instanceof EntityAnimal) type = RedstoneLevels.PASSIVE;
		else type = RedstoneLevels.HOSTILE;
		worldIn.setBlockState(pos, state.withProperty(POWER, type));
		worldIn.scheduleUpdate(new BlockPos(pos), this, this.tickRate(worldIn));
	}
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {POWER});
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		switch(meta) {
			case 1: state = state.withProperty(POWER, RedstoneLevels.PASSIVE);
					break;
			case 2: state = state.withProperty(POWER, RedstoneLevels.HOSTILE);
				break;
			case 3: state = state.withProperty(POWER, RedstoneLevels.PLAYER);
				break;
		}
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWER).ordinal();
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return BBOX;
	}
	
	public enum RedstoneLevels implements IStringSerializable{
		NONE("none", 0),
		PASSIVE("passive", 5),
		HOSTILE("hostile", 10),
		PLAYER("player", 15);
		
		private String name;
		private int power;
		
		private RedstoneLevels(String name, int power) {
			this.name= name;
			this.power = power;
		}
		
		@Override
		public String getName() {
			return this.name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		public int getPower() {
			return this.power;
		}
	}
}
