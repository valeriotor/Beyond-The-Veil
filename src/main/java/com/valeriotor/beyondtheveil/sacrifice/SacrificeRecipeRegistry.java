package com.valeriotor.beyondtheveil.sacrifice;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.sacrifice.SacrificeRecipe.ItemFunction;
import com.valeriotor.beyondtheveil.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SacrificeRecipeRegistry {
	private static final Map<String, SacrificeRecipe> recipes = new HashMap<>();
	
	public static void registerSacrificeRecipes() {
		recipes.put("sacrifice;bricks", bricks);
		recipes.put("sacrifice;staff", staff);
		recipes.put("sacrifice;undeadsigil", undeadsigils);
		recipes.put("sacrifice;sigil_pathway", pathwaysigil);
		recipes.put("sacrifice;sigil_player", playersigil);
	}
	
	public static ItemStack getItemStackAndUnlockData(ItemStack input, EntityPlayer p) {
		for(Entry<String, SacrificeRecipe> entry : recipes.entrySet()) {
			ItemStack s = entry.getValue().getItemStack(input);
			if(s != null) {
				if(p != null && entry.getValue().getData() != null)
					SyncUtil.addStringDataOnServer(p, false, entry.getValue().getData());
				return s;
			}
		}
		return null;
	}
	
	public static SacrificeRecipe getRecipe(String key) {
		return recipes.get(key);
	}
	
	public static final SacrificeRecipe bricks = new SacrificeRecipe(
			new ItemFunction(Blocks.STONEBRICK, new ItemStack(BlockRegistry.BlockBloodBrick), true),
			new ItemFunction(Blocks.STONE_BRICK_STAIRS, new ItemStack(BlockRegistry.BlockBloodBrickStairs), true),
			new ItemFunction(Blocks.STONE_SLAB, new ItemStack(BlockRegistry.SlabBloodHalf), true)
			);
	
	public static final SacrificeRecipe staff = new SacrificeRecipe(
			"craftedstaff",
			new ItemFunction(Blocks.PRISMARINE, new ItemStack(ItemRegistry.coral_staff), false)
			);
	
	public static final SacrificeRecipe undeadsigils = new SacrificeRecipe(
			new ItemFunction(Items.ROTTEN_FLESH, new ItemStack(ItemRegistry.sigil_zombie), false),
			new ItemFunction(Items.BONE, new ItemStack(ItemRegistry.sigil_skellie), false)
			);
	
	public static final SacrificeRecipe pathwaysigil = new SacrificeRecipe(
			"boundpathway",
			new ItemFunction(Items.COMPASS, new ItemStack(ItemRegistry.sigil_pathway), false)
			);
	
	public static final SacrificeRecipe playersigil = new SacrificeRecipe(
			new ItemFunction(ItemRegistry.spine, new ItemStack(ItemRegistry.sigil_player), false)
			);
	
}
