package com.valeriotor.BTV.multiblock;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MultiblockSchematic {

	public String unlockName;
	public String key;
	public String[][] strings;
	public HashMap<String, String> map;
	private transient ItemStack[][][] stacks;
	private transient ItemStack keyStack;
	private transient int sideSize;
	
	public boolean process() {
		sideSize = strings[0].length;
		stacks = new ItemStack[strings.length][sideSize][sideSize];
		HashMap<Character, ItemStack> newMap = new HashMap<>();
		for(Entry<String,String> entry : map.entrySet()) {
			String[] ss = entry.getValue().split(";");
			int amount = 1, metadata = 0;
			if(ss.length > 1) {
				amount = Integer.parseInt(ss[1]);
				if(ss.length > 2) {
					metadata = Integer.parseInt(ss[2]);
				}
			}
			newMap.put(entry.getKey().charAt(0), new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ss[0])), amount, metadata));
		}
		for(int i = 0; i < strings.length; i++) {
			String[] layer = strings[i];
			for(int j = 0; j < layer.length; j++) {
				String s = layer[j];
				for(int k = 0; k < s.length(); k++) {
					char c = s.charAt(k);
					if(c != ' ') {
						stacks[i][j][k] = newMap.get(c);
					} else {
						stacks[i][j][k] = ItemStack.EMPTY;
					}
				}
			}
		}
		String[] ss = key.split(";");
		int amount = 1;
		if(ss.length > 1)
			amount = Integer.parseInt(ss[1]);
		this.keyStack = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(ss[0])), amount);
		map = null;
		strings = null;
		key = null;
		return true;
	}
	
	public ItemStack[][][] getSchematic(){
		return this.stacks;
	}
	
	public String getLocalizedName() {
		return I18n.format(unlockName);
	}
	
	public ItemStack getKeyStack() {
		return this.keyStack;
	}
	
	public boolean checksOutBottomNorthWest(World w, BlockPos pos) {
		for(int x = 0; x < sideSize; x++) {
			for(int y = 0; y < stacks.length; y++) {
				for(int z = 0; z < sideSize; z++) {
					ItemStack stack = stacks[y][x][z];
					if(!stack.isEmpty() && stack.getItem() != Items.AIR) {
						IBlockState s = w.getBlockState(pos.add(x, y, z));
						if(stack.getItem() != Item.getItemFromBlock(s.getBlock()) || stack.getMetadata() != s.getBlock().damageDropped(s)) {
							return false;
						}
					}
				}
			}	
		}
		return true;
	}
	
	public boolean checksOutBottomCenter(World w, BlockPos pos) {
		return this.checksOutBottomNorthWest(w, pos.add(-sideSize/2, 0, -sideSize/2));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(ItemStack[][] layer : stacks) {
			for(ItemStack[] line : layer) {
				for(ItemStack stack : line) {
					if(stack != null)
						sb.append(I18n.format(stack.getUnlocalizedName()) + " ");
					else
						sb.append("minecraft:air ");
				}
				sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
