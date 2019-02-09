package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.lib.BlockNames;
import com.valeriotor.BTV.util.IHasModel;
import com.valeriotor.BTV.util.RegistryHandler;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

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
