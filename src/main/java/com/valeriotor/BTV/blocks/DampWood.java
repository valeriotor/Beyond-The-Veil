package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.lib.BlockNames;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DampWood extends ModBlock //implements IHasModel
{

	public DampWood() {
		super(Material.WOOD, BlockNames.DAMPWOOD);
		this.setResistance(2000.0F);
		this.setHardness(4.0F);
		this.setSoundType(SoundType.WOOD);
	}

	//@Override
	//public void registerModels() {
	//	RegistryHandler.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		
	//}
	
}
