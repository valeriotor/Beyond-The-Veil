package com.valeriotor.BTV.items;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestItem extends Item //implements IHasModel
{
	private static final boolean NECRODEBUG = true;
	Formatter zorro;
	public TestItem() {
		setRegistryName(References.MODID +":testitem");
		setUnlocalizedName("testitem");
		setCreativeTab(References.BTV_TAB);
		setMaxStackSize(1);
	}
	
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(NECRODEBUG) {
			if(worldIn.isRemote) {
				player.openGui(BeyondTheVeil.instance, 5, worldIn, pos.getX(), pos.getY(), pos.getZ());
				return EnumActionResult.SUCCESS;
			} else
				return EnumActionResult.FAIL;
		}
		if(worldIn.isRemote) {
		
		ItemStack stack = player.getHeldItem(hand);
		
		if(!player.getHeldItem(hand).hasTagCompound()) {
			player.getHeldItem(hand).setTagCompound(new NBTTagCompound());
			
		}
		if(!player.getHeldItem(hand).getTagCompound().hasKey("FirstPos")) {
			player.getHeldItem(hand).getTagCompound().setLong("FirstPos", pos.toLong());
		}else {
			BlockPos firstPos = BlockPos.fromLong(player.getHeldItem(hand).getTagCompound().getLong("FirstPos"));
			BlockPos secondPos = pos;
			player.getHeldItem(hand).getTagCompound().removeTag("FirstPos");
			if(Math.abs(secondPos.getX()-firstPos.getX())*Math.abs(secondPos.getY()-firstPos.getY())*Math.abs(secondPos.getZ()-firstPos.getZ())>1000000) {
				System.out.println("Too big dude..");
				return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
			}
			doProcess(firstPos, secondPos, worldIn);
		}
		}
		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	public void doProcess(BlockPos pos1, BlockPos pos2, World w) {
		try {
			zorro = new Formatter("StructuralCode.txt");
		}catch(Exception e) {
			System.out.println("Ahh, sweet child of Kos");
		}
		
		List<ArrayList<int[]>> blocks = new ArrayList();
		List<String> names = new ArrayList();
		BlockPos center = new BlockPos(Math.ceil(((double)pos1.getX()+pos2.getX())/2), Math.min(pos1.getY(), pos2.getY()), Math.ceil(((double)pos1.getZ()+pos2.getZ())/2));
		for(int x = Math.min(pos1.getX(), pos2.getX()); x<=Math.max(pos1.getX(), pos2.getX());x++) {
			for(int y = Math.min(pos1.getY(), pos2.getY()); y<=Math.max(pos1.getY(), pos2.getY());y++) {
				for(int z = Math.min(pos1.getZ(), pos2.getZ()); z<=Math.max(pos1.getZ(), pos2.getZ());z++) {
					IBlockState state = w.getBlockState(new BlockPos(x,y,z));
					Block block = w.getBlockState(new BlockPos(x,y,z)).getBlock();
					int meta = block.getMetaFromState(state);
					if(!names.contains(block.getUnlocalizedName().substring(5))) {
						names.add(block.getUnlocalizedName().substring(5));
						blocks.add(new ArrayList<int[]>());
					}
					int a = names.indexOf(block.getUnlocalizedName().substring(5));
					int[] array = {(x-center.getX()), (y-center.getY()), (z-center.getZ()), meta};
					if(array[1] > 3 && block.getUnlocalizedName().substring(5).equals("air")) continue;
					blocks.get(a).add(array);
				}
			}
		}
		for(int i = 0; i<blocks.size(); i++) {
			zorro.format("byte[][] %s = { %n%n", names.get(i));
			int counter = 0;
			for(int[] miniArray : blocks.get(i)) {
				zorro.format("{%d, %d, %d, %d}, ", miniArray[0], miniArray[1], miniArray[2], miniArray[3]);
				if(++counter%10 == 0) {
					zorro.format("%n");
				}
			}
			zorro.format("%n%n}; %n %n");
		}
		zorro.format("if(facing==EnumFacing.NORTH){%n");
		for(String name : names) {
			zorro.format("for(int[] n : this.%s) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), %s.getStateFromMeta(n[3]));%n", name, getBlockString(name));
		}
		zorro.format("}else if(facing==EnumFacing.EAST){%n");
		for(String name : names) {
			zorro.format("for(int[] n : this.%s) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), %s.getStateFromMeta(n[3]));%n", name, getBlockString(name));
		}
		zorro.format("}else if(facing==EnumFacing.SOUTH){%n");
		for(String name : names) {
			zorro.format("for(int[] n : this.%s) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), %s.getStateFromMeta(n[3]));%n", name, getBlockString(name));
		}
		zorro.format("}else if(facing==EnumFacing.WEST){%n");
		for(String name : names) {
			zorro.format("for(int[] n : this.%s) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), %s.getStateFromMeta(n[3]));%n", name, getBlockString(name));
		}
		zorro.format("}");
		
		
		zorro.close();
	}
	
	private String getBlockString(String name) {
		if(name.equals("air")) return "Blocks.AIR";
		if(name.equals("damp_wood")) return "BlockRegistry.DampWood";
		if(name.equals("damp_canopy")) return "BlockRegistry.DampCanopy"; 
		if(name.equals("worn_bricks")) return "BlockRegistry.WornBricks";
		if(name.equals("dark_sand")) return "state";
		if(name.equals("damp_wood_stairs")) return "BlockRegistry.DampWoodStairs";
		if(name.equals("barrel")) return "BlockRegistry.BlockBarrel";
		if(name.equals("lamp")) return "BlockRegistry.BlockLamp";
		if(name.equals("bricks_blue")) return "BlockRegistry.BricksBlue";
		if(name.equals("damp_canopy_wood")) return "BlockRegistry.DampCanopyWood";
		if(name.equals("damp_log")) return "BlockRegistry.DampLog";
		if(name.equals("worn_brick_stairs")) return "BlockRegistry.WornBrickStairs";
		
		return "Blocks. ";
	}
	
	
	
}
