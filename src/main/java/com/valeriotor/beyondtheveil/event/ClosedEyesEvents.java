package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClosedEyesEvents {

    @SubscribeEvent
    public static void onFillBucketEvent(FillBucketEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp && DataUtil.getBoolean(sp, PlayerDataLib.REMINISCING) && event.getTarget() != null) {
            if (event.getTarget() instanceof BlockHitResult bhr && event.getEmptyBucket().getItem() == Items.BUCKET) {
                BlockPos prePos = bhr.getBlockPos();
                BlockState preState = event.getLevel().getBlockState(prePos);
                Level level = event.getLevel();
                Block block = preState.getBlock();
                if (block == Blocks.WATER && block instanceof BucketPickup bp) {
                    bp.pickupBlock(level, prePos, preState);
                    event.setFilledBucket(event.getEmptyBucket().copy());
                    event.setResult(Event.Result.ALLOW);
                    DataUtil.incrementOrSetInteger(sp, PlayerDataLib.STORED_WATER, 1, 1, false);
                } else {
                    Direction direction = bhr.getDirection();
                    BlockPos pos = canBlockContainWater(level, prePos, preState) ? prePos : prePos.relative(direction);
                    if (placeStoredWater(event.getEntity(), level, pos, bhr)) {
                        DataUtil.incrementOrSetInteger(event.getEntity(), PlayerDataLib.STORED_WATER, -1, 0, false);
                        event.setFilledBucket(event.getEmptyBucket().copy());
                        event.setResult(Event.Result.ALLOW);
                    }
                }
            }
        }
    }


    public static boolean placeStoredWater(Player player, Level level, BlockPos pos, BlockHitResult result) {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        boolean flag = state.isAir() || state.canBeReplaced(Fluids.WATER) || canBlockContainWater(level, pos, state);
        if (!flag) {
            return result != null && placeStoredWater(player, level, result.getBlockPos().relative(result.getDirection()), (BlockHitResult)null);
        } else if (block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).canPlaceLiquid(level, pos, state, Fluids.WATER)) {
            ((LiquidBlockContainer)block).placeLiquid(level, pos, state, Fluids.WATER.getSource(false));
            playEmptySound(player, level, pos);
            return true;
        } else {
            if (!level.isClientSide && flag && !state.liquid()) {
                level.destroyBlock(pos, true);
            }

            if (!level.setBlock(pos, Fluids.WATER.defaultFluidState().createLegacyBlock(), 11) && !state.getFluidState().isSource()) {
                return false;
            } else {
                playEmptySound(player, level, pos);
                return true;
            }
        }
    }

    private static void playEmptySound(@Nullable Player pPlayer, LevelAccessor pLevel, BlockPos pPos) {
        SoundEvent soundevent = Fluids.WATER.getFluidType().getSound(pPlayer, pLevel, pPos, net.minecraftforge.common.SoundActions.BUCKET_EMPTY);
        if(soundevent == null) soundevent = SoundEvents.BUCKET_EMPTY;
        pLevel.playSound(pPlayer, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
        pLevel.gameEvent(pPlayer, GameEvent.FLUID_PLACE, pPos);
    }

    private static boolean canBlockContainWater(Level worldIn, BlockPos posIn, BlockState blockstate) {
        return blockstate.getBlock() instanceof LiquidBlockContainer lbc && lbc.canPlaceLiquid(worldIn, posIn, blockstate, Fluids.WATER);
    }

}
