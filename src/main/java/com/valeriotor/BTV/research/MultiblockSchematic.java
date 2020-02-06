package com.valeriotor.BTV.research;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

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
