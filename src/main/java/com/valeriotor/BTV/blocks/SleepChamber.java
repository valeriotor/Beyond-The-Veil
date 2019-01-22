package com.valeriotor.BTV.blocks;

import java.util.Random;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSetPosition;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class SleepChamber extends Block{
	
	public static final PropertyEnum<SleepChamber.EnumHalf> HALF = PropertyEnum.<SleepChamber.EnumHalf>create("half", SleepChamber.EnumHalf.class);
	
	public SleepChamber(String name) {
		super(Material.IRON);
		this.setRegistryName(References.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, SleepChamber.EnumHalf.BOTTOM));
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(state.getValue(HALF) == SleepChamber.EnumHalf.TOP) pos = pos.down();
		BlockPos pos1 = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
		BTVPacketHandler.INSTANCE.sendToServer(new MessageSetPosition(pos1.toLong()));
		//playerIn.setLocationAndAngles(pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, playerIn.getRotationYawHead(), 0);
		if(worldIn.isRemote) BeyondTheVeil.proxy.openGui(Guis.GuiSleepingChamber);
		return true;
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
	public boolean isOpaqueCube(IBlockState state) {
		return false;
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
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
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
