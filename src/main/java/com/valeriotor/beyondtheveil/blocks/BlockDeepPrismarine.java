package com.valeriotor.beyondtheveil.blocks;

import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockPrismarine.EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;

import static net.minecraft.block.BlockPrismarine.VARIANT;

public class BlockDeepPrismarine extends ModBlock{

    public BlockDeepPrismarine(String name) {
        super(Material.ROCK, name);
        setBlockUnbreakable();
        setResistance(6000001.0F);
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

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return false;
    }
}
