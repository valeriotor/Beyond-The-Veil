package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ArborealGeneratorBE extends BlockEntity {

    private int count = 0;
    private int[] sideCount = {0, 0, 0, 0};
    public ArborealGeneratorBE(BlockPos pPos, BlockState pBlockState) {
        super(BTVBlockEntities.ARBOREAL_GENERATOR_BE.get(), pPos, pBlockState);
    }

    public void tickServer() {
        count++;
        if (count % 250 == 0 && level != null) {
            for (int i = 0; i < 4; i++) {
                Direction direction = Direction.from2DDataValue(i);
                Block side = level.getBlockState(worldPosition.relative(direction)).getBlock();
                Block down = level.getBlockState(worldPosition.relative(direction).below()).getBlock();
                if ((side == Blocks.AIR || side == Registration.SAPLING_SHRUB.get()) && (down == Blocks.DIRT || down == Blocks.GRASS_BLOCK)) {
                    sideCount[i]++;
                } else {
                    sideCount[i] = 0;
                }
                if (sideCount[i] >= 490 || level.random.nextInt(500) + 20 < sideCount[i]) {
                    progress(worldPosition.relative(direction));
                }
            }
        }
    }

    private void progress(BlockPos relative) {
        Block b = level.getBlockState(relative).getBlock();
        if (b == Blocks.AIR) {
            level.setBlock(relative, Registration.SAPLING_SHRUB.get().defaultBlockState(), 3);
        } else {
            Iterable<Holder<Block>> tagOrEmpty = BuiltInRegistries.BLOCK.getTagOrEmpty(BlockTags.SAPLINGS);
            List<Block> saplings = new ArrayList<>();
            for (Holder<Block> blockHolder : tagOrEmpty) {
                saplings.add(blockHolder.get());
            }
            if (!saplings.isEmpty()) {
                Block block = saplings.get(level.getRandom().nextInt(saplings.size()));
                level.setBlock(relative, block.defaultBlockState(), 3);
            }
        }
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("count", count);
        pTag.putIntArray("sideCount", sideCount);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        sideCount = pTag.getIntArray("sideCount");
        count = pTag.getInt("count");
    }
}
