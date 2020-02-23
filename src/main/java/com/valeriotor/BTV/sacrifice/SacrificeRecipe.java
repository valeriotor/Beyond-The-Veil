package com.valeriotor.BTV.sacrifice;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SacrificeRecipe {
	private final List<ItemFunction> recipes = new ArrayList<>();
	private final String data;
	
	public SacrificeRecipe(ItemFunction... funcs) {
		this(null, funcs);
	}
	
	public SacrificeRecipe(String data, ItemFunction... funcs) {
		for(ItemFunction f : funcs)
			recipes.add(f);
		this.data = data;
	}
	
	public ItemStack getItemStack(ItemStack input) {
		Item i = input.getItem();
		for(ItemFunction f : recipes) {
			if(f.getInput() == i) {
				ItemStack retStack = f.getOutput();
				if(f.isMultiple()) {
					retStack.setCount(retStack.getCount()*input.getCount());
					input.setCount(0);
				} else 
					input.shrink(1);
				return retStack;
			}
		}
		return null;
	}
	
	public ItemStack getKeyStack() {
		return this.recipes.get(0).getOutput();
	}
	
	public Item getInputInSlot(int slot) {
		return this.recipes.get(slot).getInput();
	}
	
	public ItemStack getOutputInSlot(int slot) {
		return this.recipes.get(slot).getOutput();
	}
	
	public boolean isMultipleInSlot(int slot) {
		return this.recipes.get(slot).isMultiple();
	}
	
	public int getSize() {
		return this.recipes.size();
	}
	
	public String getData() {
		return this.data;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(ItemFunction f : this.recipes) {
			sb.append(f.getInput().getUnlocalizedName() + " -> " + f.getOutput() + "\n");
		}
		return sb.toString();
	}
	
	public static class ItemFunction {
		private final Item input;
		private final ItemStack output;
		private final boolean multiple;
		
		public ItemFunction(Block input, ItemStack output, boolean multiple) {
			this(Item.getItemFromBlock(input), output, multiple);
		}
		
		public ItemFunction(Item input, ItemStack output, boolean multiple) {
			this.input = input;
			this.output = output;
			this.multiple = multiple;
		}
		
		public Item getInput() {
			return this.input;
		}
		
		public ItemStack getOutput() {
			return this.output.copy();
		}
		
		public boolean isMultiple() {
			return this.multiple;
		}
	}
	
}
