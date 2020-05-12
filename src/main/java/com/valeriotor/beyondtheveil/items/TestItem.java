package com.valeriotor.beyondtheveil.items;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestItem extends Item //implements IHasModel
{
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
	private static final boolean SHOGGOTH = true;
	public void doProcess(BlockPos pos1, BlockPos pos2, World w) {
		
		
		HashMap<String, List<byte[]>> blockMap = new HashMap<>();
		BlockPos center = new BlockPos(Math.ceil(((double)pos1.getX()+pos2.getX())/2), Math.min(pos1.getY(), pos2.getY()) + (SHOGGOTH ? 0 : 1), Math.ceil(((double)pos1.getZ()+pos2.getZ())/2));
		for(int x = Math.min(pos1.getX(), pos2.getX()); x<=Math.max(pos1.getX(), pos2.getX());x++) {
			for(int y = Math.min(pos1.getY(), pos2.getY()); y<=Math.max(pos1.getY(), pos2.getY());y++) {
				for(int z = Math.min(pos1.getZ(), pos2.getZ()); z<=Math.max(pos1.getZ(), pos2.getZ());z++) {
					IBlockState state = w.getBlockState(new BlockPos(x,y,z));
					Block block = w.getBlockState(new BlockPos(x,y,z)).getBlock();
					int meta = block.getMetaFromState(state);
					
					if(y - center.getY() > 3 && block.getUnlocalizedName().substring(5).equals("air")) continue;
					String s = block.getRegistryName().toString();
					if(!blockMap.containsKey(s)) {
						blockMap.put(s, new ArrayList<>());
					}
					blockMap.get(s).add(new byte[]{(byte) (x-center.getX()), (byte) (y-center.getY()), (byte) (z-center.getZ()), (byte) meta});
				}
			}
		}
		JSonStructureBuilder b = new JSonStructureBuilder(blockMap, Math.abs(pos1.getX()-pos2.getX())+1, Math.abs(pos1.getY()-pos2.getY())+1, Math.abs(pos1.getZ()-pos2.getZ())+1);
		try (Writer writer = new FileWriter("StructuralCode.json")) {
		    BeyondTheVeil.gson.toJson(b, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public static class JSonStructureBuilder {
		
		HashMap<String, List<byte[]>> blockMap;
		private int xSize, ySize, zSize;
		public JSonStructureBuilder(HashMap<String, List<byte[]>> blockMap, int xSize, int ySize, int zSize) {
			this.blockMap = blockMap;
			this.xSize = xSize;
			this.ySize = ySize;
			this.zSize = zSize;
		}
		
		public HashMap<Block, byte[][]> getMap(){
			HashMap<Block, byte[][]> map = new HashMap<>();
			for(Entry<String, List<byte[]>> entry : this.blockMap.entrySet()) {
				byte[][] bytes = new byte[entry.getValue().size()][4];
				for(int i = 0; i < entry.getValue().size(); i++) {
					bytes[i] = entry.getValue().get(i);
				}
				map.put(Block.REGISTRY.getObject(new ResourceLocation(entry.getKey())), bytes);
			}
			return map;
		}
		
		public int getXSize() {
			return this.xSize;
		}
		
		public int getYSize() {
			return this.ySize;
		}
		
		public int getZSize() {
			return this.zSize;
		}
	}
	
}
