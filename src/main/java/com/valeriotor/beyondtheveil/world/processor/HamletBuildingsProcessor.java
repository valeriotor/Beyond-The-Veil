package com.valeriotor.beyondtheveil.world.processor;

import com.mojang.serialization.Codec;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.world.structures.DeepCityStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class HamletBuildingsProcessor extends StructureProcessor {

    private static final HamletBuildingsProcessor INSTANCE = new HamletBuildingsProcessor();

    public static final Codec<HamletBuildingsProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader pLevel, BlockPos pOffset, BlockPos pPos, StructureTemplate.StructureBlockInfo pBlockInfo, StructureTemplate.StructureBlockInfo pRelativeBlockInfo, StructurePlaceSettings pSettings) {
        BlockState blockstate = pLevel.getBlockState(pRelativeBlockInfo.pos());

        if (pRelativeBlockInfo.state().getBlock() == Registration.DARK_SAND.get() && (blockstate.getBlock() == Blocks.WATER || blockstate.getBlock() == Registration.DAMP_WOOD.get())) {
            return new StructureTemplate.StructureBlockInfo(pRelativeBlockInfo.pos(), Registration.DAMP_WOOD.get().defaultBlockState(), pBlockInfo.nbt());
        }
        if (pRelativeBlockInfo.state().getBlock() == Registration.DARK_SAND.get() && (blockstate.getBlock() == Registration.BLUE_BRICKS.get())) {
            return new StructureTemplate.StructureBlockInfo(pRelativeBlockInfo.pos(), Registration.BLUE_BRICKS.get().defaultBlockState(), pBlockInfo.nbt());
        }
        return super.processBlock(pLevel, pOffset, pPos, pBlockInfo, pRelativeBlockInfo, pSettings);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return Registration.HAMLET_BUILDINGS_PROCESSOR.get();
    }
}
