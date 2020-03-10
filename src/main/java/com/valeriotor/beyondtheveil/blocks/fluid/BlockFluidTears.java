package com.valeriotor.beyondtheveil.blocks.fluid;

import com.valeriotor.beyondtheveil.fluids.ModFluids;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidTears extends BlockFluidClassic{

	public BlockFluidTears() {
		super(ModFluids.tears, Material.WATER);
		this.setRegistryName("tears");
		this.setUnlocalizedName("tears");
	}
	
	@SideOnly(Side.CLIENT)
	public void render() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
	}

}
