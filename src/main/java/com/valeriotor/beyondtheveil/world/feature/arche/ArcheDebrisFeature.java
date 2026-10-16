package com.valeriotor.beyondtheveil.world.feature.arche;

import com.mojang.serialization.Codec;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.DebrisBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class ArcheDebrisFeature extends Feature<ProbabilityFeatureConfiguration> {
    public ArcheDebrisFeature(Codec<ProbabilityFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> p_160210_) {
        ProbabilityFeatureConfiguration randompatchconfiguration = p_160210_.config();
        BlockPos blockpos = p_160210_.origin();
        RandomSource randomsource = p_160210_.random();
        if (randomsource.nextDouble() > randompatchconfiguration.probability) {
            return false;
        }
        WorldGenLevel worldgenlevel = p_160210_.level();
        BlockPos.MutableBlockPos m = new BlockPos.MutableBlockPos();
        float v = randomsource.nextFloat();
        Block block = v < 0.4 ? Registration.DEBRIS_GOLD.get() : (v < 0.75 ? Registration.DEBRIS_DIAMOND.get() : Registration.DEBRIS_NETHERITE.get());
        boolean flag = false;

        for (int manhattan = 0; manhattan < 3; manhattan++) {
            for (int x = -manhattan; x <= manhattan; x++) {
                boolean edge = x == -manhattan || x == manhattan;
                int z;
                if (edge) z = 0;
                else z = manhattan - Math.abs(x);
                m.setWithOffset(blockpos, x, 0, z);
                flag |= placeBlock(manhattan, worldgenlevel, m, block.defaultBlockState(), randomsource);
                if (z != 0) {
                    m.setWithOffset(blockpos, x, 0, -z);
                    flag |= placeBlock(manhattan, worldgenlevel, m, block.defaultBlockState(), randomsource);
                }
            }
        }

        return flag;
    }

    private boolean placeBlock(int manhattan, WorldGenLevel worldgenlevel, BlockPos.MutableBlockPos pos, BlockState state, RandomSource randomsource) {
        if (worldgenlevel.getBlockState(pos.below()).is(Blocks.WATER)) {
            pos.move(Direction.DOWN);
        }
        if (!worldgenlevel.getBlockState(pos).is(Blocks.WATER)) {
            pos.move(Direction.UP);
        }
        if (worldgenlevel.getBlockState(pos).is(Blocks.WATER) && worldgenlevel.getBlockState(pos.below()).is(Registration.DARK_SAND.get())) {
            int height = 1 + randomsource.nextInt(2) - manhattan;
            if (height > -1) {
                worldgenlevel.setBlock(pos, state.setValue(DebrisBlock.HEIGHT, height).setValue(BlockStateProperties.WATERLOGGED, true), 2);
                return true;
            }
        }
        return false;
    }
}
