package com.valeriotor.beyondtheveil.world.feature.arche;

import com.mojang.serialization.Codec;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.BlackKelpBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BlackKelpFeature extends Feature<NoneFeatureConfiguration> {
    public BlackKelpFeature(Codec<NoneFeatureConfiguration> p_66219_) {
        super(p_66219_);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159956_) {
        int i = 0;
        WorldGenLevel worldgenlevel = p_159956_.level();
        BlockPos blockpos1 = p_159956_.origin();
        RandomSource randomsource = p_159956_.random();
        if (worldgenlevel.getBlockState(blockpos1).is(Blocks.WATER)) {
            BlockState blockstate = Registration.BLACK_KELP.get().defaultBlockState();
            BlockState blockstate1 = Registration.BLACK_KELP_PLANT.get().defaultBlockState();
            int k = 1 + randomsource.nextInt(10);

            for(int l = 0; l <= k; ++l) {
                if (worldgenlevel.getBlockState(blockpos1).is(Blocks.WATER) && worldgenlevel.getBlockState(blockpos1.above()).is(Blocks.WATER) && blockstate1.canSurvive(worldgenlevel, blockpos1)) {
                    if (l == k) {
                        worldgenlevel.setBlock(blockpos1, blockstate.setValue(BlackKelpBlock.AGE, Integer.valueOf(randomsource.nextInt(4) + 20)), 2);
                        ++i;
                    } else {
                        worldgenlevel.setBlock(blockpos1, blockstate1, 2);
                    }
                } else if (l > 0) {
                    BlockPos blockpos2 = blockpos1.below();
                    if (blockstate.canSurvive(worldgenlevel, blockpos2) && !worldgenlevel.getBlockState(blockpos2.below()).is(Registration.BLACK_KELP.get())) {
                        worldgenlevel.setBlock(blockpos2, blockstate.setValue(BlackKelpBlock.AGE, Integer.valueOf(randomsource.nextInt(4) + 20)), 2);
                        ++i;
                    }
                    break;
                }

                blockpos1 = blockpos1.above();
            }
        }

        return i > 0;
    }
}
