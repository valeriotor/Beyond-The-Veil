package com.valeriotor.BTV.blocks;

import java.util.Random;

import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.tileEntities.TileBarrel;

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

public class BlockBarrel extends Block implements ITileEntityProvider{
	
	private static final AxisAlignedBB BBOX = new AxisAlignedBB(0.125,0.0,0.125,0.875,0.875,0.875); 
	public static final PropertyEnum<BlockBarrel.EnumFullness> FULLNESS = PropertyEnum.<BlockBarrel.EnumFullness>create("fullness", BlockBarrel.EnumFullness.class);
	
	public BlockBarrel() {
		super(Material.WOOD);
		this.setResistance(2000.0F);
		this.setHardness(4.0F);
		setRegistryName(References.MODID + ":barrel");
		setUnlocalizedName("barrel");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FULLNESS, EnumFullness.EMPTY));
	}
	
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		// TODO Auto-generated method stub
		return BBOX;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		// TODO Auto-generated method stub
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
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FULLNESS});
	}
	
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		switch(meta) {
		case 0: state = state.withProperty(FULLNESS, EnumFullness.EMPTY);
				break;
		case 1:	state = state.withProperty(FULLNESS, EnumFullness.FISH1);
				break;
		case 2:	state = state.withProperty(FULLNESS, EnumFullness.FISH2);
				break;
		case 3:	state = state.withProperty(FULLNESS, EnumFullness.FISH3);
				break;
		case 4:	state = state.withProperty(FULLNESS, EnumFullness.FISH4);
				break;
		case 5:	state = state.withProperty(FULLNESS, EnumFullness.SLUG1);
				break;
		case 6:	state = state.withProperty(FULLNESS, EnumFullness.SLUG2);
				break;
		case 7:	state = state.withProperty(FULLNESS, EnumFullness.SLUG3);
				break;
		case 8:	state = state.withProperty(FULLNESS, EnumFullness.SLUG4);
				break;		
		}
		
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		switch(state.getValue(FULLNESS)) {
		case EMPTY: meta = 0;
					break;
		case FISH1: meta = 1;
					break;
		case FISH2: meta = 2;
					break;
		case FISH3: meta = 3;
					break;
		case FISH4: meta = 4;
					break;
		case SLUG1: meta = 5;
					break;
		case SLUG2: meta = 6;
					break;
		case SLUG3: meta = 7;
					break;
		case SLUG4: meta = 8;
					break;
			
		
		}
		return meta;
	}
	
	
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBarrel();
	}
	
	public TileBarrel getTE(World w, BlockPos pos) {
		return (TileBarrel) w.getTileEntity(pos);
	}
	
	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(playerIn.isSneaking()) {
			if(getTE(w, pos).getFishType() != 0) {
				boolean shouldFix = false;
				int a = getTE(w, pos).getFishCount();
				int type = getTE(w, pos).getFishType();
			 	if(a == 17 || a == 33 || a == 49) {
			 		a-=1;
			 		shouldFix = true;
			 	}
				 getTE(w, pos).setFishCount(getTE(w, pos).getFishCount() - 1);
				playerIn.addItemStackToInventory(fishItemFromType(getTE(w, pos).getFishType()));
				playerIn.sendMessage(new TextComponentString( String.valueOf(getTE(w, pos).getFishCount())));
				setState(getTE(w, pos).getFishCount(), w, pos, state, false);
				
				if(shouldFix) {
					getTE(w, pos).setFishCount(a);
					getTE(w, pos).setFishType(type);
				}
				
				if(a == 1) getTE(w, pos).setFishType(0);
				
			}
			return true;
		}
		
		if(!playerIn.isSneaking() && ((playerIn.getHeldItem(hand).getItem() == Items.FISH && playerIn.getHeldItem(hand).getMetadata() == 0) 
			|| playerIn.getHeldItem(hand).getItem() == ItemRegistry.slug)) {
			
			if(getTE(w, pos).getFishType() == 0) {
				getTE(w, pos).setFishType(fishTypeFromItem(playerIn.getHeldItem(hand)));
			}else if(getTE(w, pos).getFishType() != fishTypeFromItem(playerIn.getHeldItem(hand))) {
				return true;
			}
			if(getTE(w, pos).getFishCount() > 63) {
				return true;
			}
			//if(!w.isRemote) {
			playerIn.getHeldItem(hand).shrink(1);
			getTE(w, pos).setFishCount(getTE(w, pos).getFishCount()+1);
			playerIn.sendMessage(new TextComponentString( String.valueOf(getTE(w, pos).getFishCount())));
			int type = getTE(w, pos).getFishType();
			setState(getTE(w, pos).getFishCount(), w, pos, state, true);
			getTE(w, pos).setFishType(type);
			//}
			w.playSound(playerIn, pos, SoundEvents.BLOCK_SLIME_FALL, SoundCategory.BLOCKS, 0.1F, 1F);
			return true;
		}
		
		return false;
	}
	
	public int fishTypeFromItem(ItemStack stack) {
		if(stack.getItem() == Items.FISH && stack.getMetadata() == 0) {
			return 1;
		}else if(stack.getItem() == ItemRegistry.slug) {
			return 2;
		}
		return 0;
	}
	
	public ItemStack fishItemFromType(int type) {
		if(type == 1) {
			return new ItemStack(Items.FISH, 1, 0);
		}else if(type == 2) {
			return new ItemStack(ItemRegistry.slug, 1);
		}
		return null;
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	private void setState(int count, World w, BlockPos pos, IBlockState state, boolean increasing) {
		int type = getTE(w, pos).getFishType();
		if(increasing) {
		if(getTE(w, pos).getFishCount() > 48) {
			w.setBlockState(pos, state.withProperty(FULLNESS, type == 1 ? EnumFullness.FISH4 : EnumFullness.SLUG4));
			if(getTE(w, pos).getFishCount()<50 && increasing) getTE(w, pos).setFishCount(49);
		}else if(getTE(w, pos).getFishCount() > 32) {
			w.setBlockState(pos, state.withProperty(FULLNESS, type == 1 ? EnumFullness.FISH3 : EnumFullness.SLUG3));
			if(getTE(w, pos).getFishCount()<34) getTE(w, pos).setFishCount(33);
		}else if(getTE(w, pos).getFishCount() > 16) {
			w.setBlockState(pos, state.withProperty(FULLNESS, type == 1 ? EnumFullness.FISH2 : EnumFullness.SLUG2));
			if(getTE(w, pos).getFishCount()<18) getTE(w, pos).setFishCount(17);
		}else if(getTE(w, pos).getFishCount() > 0) {
			w.setBlockState(pos, state.withProperty(FULLNESS, type == 1 ? EnumFullness.FISH1 : EnumFullness.SLUG1));
			if(getTE(w, pos).getFishCount()<2) getTE(w, pos).setFishCount(1);
		}
		}else {
		if(getTE(w, pos).getFishCount() == 16) {
			w.setBlockState(pos, state.withProperty(FULLNESS, type == 1 ? EnumFullness.FISH1 : EnumFullness.SLUG1));
		}else if(getTE(w, pos).getFishCount() == 32) {
			w.setBlockState(pos, state.withProperty(FULLNESS, type == 1 ? EnumFullness.FISH2 : EnumFullness.SLUG2));
		}else if(getTE(w, pos).getFishCount() == 48) {
			w.setBlockState(pos, state.withProperty(FULLNESS, type == 1 ? EnumFullness.FISH3 : EnumFullness.SLUG3));
		}else if(getTE(w, pos).getFishCount() == 0) {
			w.setBlockState(pos, state.withProperty(FULLNESS, EnumFullness.EMPTY));
		}
		}
	}
	
	 @Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		if(worldIn.isRemote) return;
		if(this.getTE(worldIn, pos).getFishType() == 1) {
				EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY()+1, pos.getZ(), new ItemStack(Items.FISH, this.getTE(worldIn, pos).getFishCount()));
				worldIn.spawnEntity(item);
			
		}else if(this.getTE(worldIn, pos).getFishType() == 2) {
			EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY()+1, pos.getZ(), new ItemStack(ItemRegistry.slug, this.getTE(worldIn, pos).getFishCount()));
			worldIn.spawnEntity(item);
		}
	}
	 
	 
	 
	 
	
	public static enum EnumFullness implements IStringSerializable
    {
        EMPTY("empty"),
        FISH1("fish1"),
        FISH2("fish2"),
		FISH3("fish3"),
		FISH4("fish4"),
        SLUG1("slug1"),
        SLUG2("slug2"),
		SLUG3("slug3"),
		SLUG4("slug4");

        private final String name;

        private EnumFullness(String name)
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
