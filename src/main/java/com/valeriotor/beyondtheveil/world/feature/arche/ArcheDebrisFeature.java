package com.valeriotor.beyondtheveil.world.feature.arche;

import com.mojang.serialization.Codec;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.DebrisBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
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
        int i = 0;
        BlockPos.MutableBlockPos m = new BlockPos.MutableBlockPos();
        int j = 2 + 1;
        int k = 1 + 1;

        for (int l = 0; l < 12; ++l) {
            m.setWithOffset(blockpos, randomsource.nextInt(j) - randomsource.nextInt(j), randomsource.nextInt(k) - randomsource.nextInt(k), randomsource.nextInt(j) - randomsource.nextInt(j));
            if (worldgenlevel.getBlockState(m).is(Blocks.WATER) && worldgenlevel.getBlockState(m.above()).is(Blocks.WATER) && worldgenlevel.getBlockState(m.below()).is(Registration.DARK_SAND.get())) {
                worldgenlevel.setBlock(m, Registration.DEBRIS_NETHERITE.get().defaultBlockState().setValue(DebrisBlock.HEIGHT, randomsource.nextInt(3)).setValue(BlockStateProperties.WATERLOGGED, true), 2);
                ++i;
            }
        }

        return i > 0;
    }
}
