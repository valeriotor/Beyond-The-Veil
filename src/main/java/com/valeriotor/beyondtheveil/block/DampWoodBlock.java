package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.event.ServerTickEvents;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.world.saved.HamletSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DampWoodBlock extends Block {

    public DampWoodBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockState blockState = pLevel.getBlockState(pPos.below());
        boolean flag = false;
        for (Direction direction : Direction.values()) {
            if (pLevel.getBlockState(pPos.relative(direction)).is(Blocks.WATER)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            HamletSavedData instance = HamletSavedData.getInstance(pLevel);
            if (instance.isHamletChunk(pPos)) {
                Player nearestPlayer = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 15, p -> true);
                if (nearestPlayer != null && instance.woodCreak(pPos)) {
                    pLevel.playSound(null, pPos, BTVSounds.WOOD_CREAK.get(), SoundSource.BLOCKS, 1, 1);
                }
            }
        }
    }
}
