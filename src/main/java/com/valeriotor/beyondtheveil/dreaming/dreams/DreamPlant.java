package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class DreamPlant extends Dream{

	public DreamPlant(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		int a = SyncUtil.getOrSetIntDataOnServer(p, false, PlayerDataLib.PLANTDREAM, -1);
		if(ResearchUtil.isResearchComplete(p, "GHOSTSEEDS")) {
			for(EnumHand hand : EnumHand.values()) {
				ItemStack stack = getStackFromStackAndGrantResearch(p, p.getHeldItem(hand));
				if(stack != null) {
					p.getHeldItem(hand).shrink(1);
					ItemHandlerHelper.giveItemToPlayer(p, stack);
					return true;
				}
			}
		}
		ItemStack stack = null;
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		switch(a) {
		case -1: 	stack = new ItemStack(Items.APPLE); 						
					break;
		case  0: 	stack = new ItemStack(ItemRegistry.vanilla_weed_seeds); 	
					if(!data.getString("vanillaseeds"))
						SyncUtil.addStringDataOnServer(p, false, "vanillaseeds");
					break;
		case  1: 	stack = new ItemStack(ItemRegistry.redstone_weed_seeds); 	
					if(!data.getString("redstoneseeds"))
						SyncUtil.addStringDataOnServer(p, false, "redstoneseeds");
					break;
		case  2: 
		default: 	stack = new ItemStack(ItemRegistry.ghost_weed_seeds); 		
					if(!data.getString("ghostseeds"))
						SyncUtil.addStringDataOnServer(p, false, "ghostseeds");
					break;
		}
		a++;
		a%=3;
		SyncUtil.addIntDataOnServer(p, false, PlayerDataLib.PLANTDREAM, a);
		ItemHandlerHelper.giveItemToPlayer(p, stack);
		return true;
	}
	
	private ItemStack getStackFromStackAndGrantResearch(EntityPlayer p, ItemStack held) {
		if(held == null) return null;
		Item i = held.getItem();
		if(i == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
			SyncUtil.addStringDataOnServer(p, false, "plantvijhiss");
			return new ItemStack(BlockRegistry.PlantVijhiss);
		}
		if(i == Items.DIAMOND) {
			SyncUtil.addStringDataOnServer(p, false, "plantgenerator");
			return new ItemStack(BlockRegistry.PlantArborealGenerator);
		}
		return null;
	}

}
