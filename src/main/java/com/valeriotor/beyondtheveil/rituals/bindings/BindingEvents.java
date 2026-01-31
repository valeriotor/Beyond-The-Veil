package com.valeriotor.beyondtheveil.rituals.bindings;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerData;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.timers.PlaceBlocksTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;

public class BindingEvents {

    public static InteractionResult useFistOnBlock(UseOnContext pContext) {
        if (pContext.getPlayer() instanceof ServerPlayer sp) {
            BindingData data = DataUtil.getBindingData(sp);
            BlockPos clickedPos = pContext.getClickedPos();
            BlockPos posInFront = clickedPos.relative(pContext.getClickedFace());
            BlockState stateInFront = pContext.getLevel().getBlockState(posInFront);
            if (data != null) {
                if (data.getBinding() == Binding.OVERWORLD) {
                    if (stateInFront.canBeReplaced()) {
                        if (data.getOverworldPos1() == null) {
                            data.setOverworldPos1(posInFront);
                        } else if (data.getOverworldPos2() == null) {
                            data.setOverworldPos2(posInFront);
                        } else {
                            data.setOverworldPos1(null);
                            data.setOverworldPos2(null);
                        }
                        DataUtil.syncBindingData(sp);
                    }
                }
            } else {
                PlayerData data1 = sp.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get();
                data1.setBindingData(new BindingData(Binding.OVERWORLD));
            }
        }
        return InteractionResult.CONSUME;
    }

    public static boolean useFistInAir() {

        return false;
    }

    public static void placeBlock(BlockEvent.EntityPlaceEvent event, ServerPlayer sp) {
        BindingData data = DataUtil.getBindingData(sp);
        if (data != null) {
            LevelAccessor level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState placedBlock = event.getPlacedBlock();
            if (data.getBinding() == Binding.OVERWORLD) {
                BlockPos overworldPos1 = data.getOverworldPos1();
                BlockPos overworldPos2 = data.getOverworldPos2();
                if (overworldPos1 != null && overworldPos2 != null) {
                    BlockPos pos1 = null, pos2 = null;
                    if (pos.equals(overworldPos1)) {
                        pos1 = overworldPos1;
                        pos2 = overworldPos2;
                    } else if (pos.equals(overworldPos2)) {
                        pos1 = overworldPos2;
                        pos2 = overworldPos1;
                    }
                    if (pos1 != null && !pos1.equals(pos2)) {
                        PlayerTimerData.for_(sp).addTimer(new PlaceBlocksTimer(pos1, pos2, placedBlock.getBlock()));
                        //boolean hasItems = true;
                        //int visitingSlot = -2;
                        //for (int x = pos1.getX(); hasItems; x = (pos1.getX() >= pos2.getX() ? x - 1 : x + 1)) {
                        //    for (int z = pos1.getZ(); hasItems; z = (pos1.getZ() >= pos2.getZ() ? z - 1 : z + 1)) {
                        //        for (int y = pos1.getY(); hasItems; y = (pos1.getY() >= pos2.getY() ? y - 1 : y + 1)) {
                        //            BlockPos toPlacePos = new BlockPos(x, y, z);
                        //            if (level.getBlockState(toPlacePos).canBeReplaced() && !(x == pos1.getX() && y == pos1.getY() && z == pos1.getZ())) {
                        //                while (visitingSlot < sp.getInventory().getContainerSize()) {
                        //                    ItemStack stack = visitingSlot == -2 ? (sp.getInventory().getSelected()) : (visitingSlot == -1 ? sp.getItemInHand(InteractionHand.OFF_HAND) : sp.getInventory().getItem(visitingSlot));
                        //                    if (stack.isEmpty() || stack.getItem() != placedBlock.getBlock().asItem()) {
                        //                        visitingSlot++;
                        //                    } else {
                        //                        level.setBlock(toPlacePos, Block.byItem(stack.getItem()).defaultBlockState(), 3);
                        //                        stack.shrink(1);
                        //                        break;
                        //                    }
                        //                }
                        //                if (visitingSlot == sp.getInventory().getContainerSize()) {
                        //                    hasItems = false;
                        //                }
                        //            }


                        //            if (y == pos2.getY()) break;
                        //        }
                        //        if (z == pos2.getZ()) break;
                        //    }
                        //    if (x == pos2.getX()) break;
                        //}
                    }
                }
            }
        }
    }


}
