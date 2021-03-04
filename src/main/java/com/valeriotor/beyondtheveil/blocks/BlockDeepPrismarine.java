package com.valeriotor.beyondtheveil.blocks;

import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockPrismarine.EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

import static net.minecraft.block.BlockPrismarine.VARIANT;

public class BlockDeepPrismarine extends ModBlock{

    public BlockDeepPrismarine(String name) {
        super(Material.ROCK, name);
        setBlockUnbreakable();
        setHardness(6000001.0F);
        setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumType.ROUGH));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return (state.getValue(VARIANT)).getMetadata();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(VARIANT)).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
    }
}
