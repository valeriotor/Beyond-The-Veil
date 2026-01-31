package com.valeriotor.beyondtheveil.util.timers;

import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;

public class PlaceBlocksTimer extends PlayerTimer {

    private final BlockPos pos1;
    private final BlockPos pos2;
    private final Block toPlace;
    private int inventorySlot = -2;
    private int x;
    private int y;
    private int z;
    private final boolean xP;
    private final boolean yP;
    private final boolean zP;
    private boolean done;

    public PlaceBlocksTimer(BlockPos pos1, BlockPos pos2, Block toPlace) {
        super(10000, "place_blocks", null, new HashMap<>());
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.toPlace = toPlace;
        x = pos1.getX();
        y = pos1.getY();
        z = pos1.getZ();
        xP = pos2.getX() >= pos1.getX();
        yP = pos2.getY() >= pos1.getY();
        zP = pos2.getZ() >= pos1.getZ();
    }

    @Override
    public boolean update(Player player) {
        if (!(x == pos1.getX() && y == pos1.getY() && z == pos1.getZ())) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = player.level().getBlockState(pos);
            if (state.canBeReplaced()) {
                while (inventorySlot < player.getInventory().getContainerSize()) {
                    ItemStack stack = inventorySlot == -2 ? (player.getInventory().getSelected()) : (inventorySlot == -1 ? player.getItemInHand(InteractionHand.OFF_HAND) : player.getInventory().getItem(inventorySlot));
                    if (stack.isEmpty() || stack.getItem() != toPlace.asItem()) {
                        inventorySlot++;
                    } else {
                        player.level().setBlock(pos, Block.byItem(stack.getItem()).defaultBlockState(), 3);
                        stack.shrink(1);
                        break;
                    }
                }
                if (inventorySlot >= player.getInventory().getContainerSize()) {
                    done = true;
                }

            }
        }
        for (int i = 0; i < 20 && !done; i++) { // advance to the next block. If next block is no good, keep advancing until one that is (max 20 steps)
            if (z == pos2.getZ()) {
                z = pos1.getZ();
                if (x == pos2.getX()) {
                    x = pos1.getX();
                    if (y == pos2.getY()) {
                        done = true;
                    } else {
                        y = yP ? y + 1 : y - 1;
                    }
                } else {
                    x = xP ? x + 1 : x - 1;
                }
            } else {
                z = zP ? z + 1 : z - 1;
            }
            BlockState state = player.level().getBlockState(new BlockPos(x, y, z));
            if (state.canBeReplaced()) { // && our block can be placed there?
                break;
            }
        }
        return super.update(player);
    }

    @Override
    public boolean isDone() {
        return done || super.isDone();
    }

    @Override
    public boolean copyOnPlayerClone() {
        return false; // because level will change
    }
}
