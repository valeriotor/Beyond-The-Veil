package com.valeriotor.beyondtheveil.surgery.surgeon;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BellData {

    private final List<BlockPos> inputPods = new ArrayList<>();
    private final List<BlockPos> outputPods = new ArrayList<>();
    private final List<BlockPos> inputSpots = new ArrayList<>();
    private final List<BlockPos> outputSpots = new ArrayList<>();
    private final List<BlockPos> inputContainers = new ArrayList<>();
    private final List<BlockPos> outputContainers = new ArrayList<>();
    private BlockPos surgicalBE;

    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        saveList(inputPods, "inputPods", tag);
        saveList(outputPods, "outputPods", tag);
        saveList(inputSpots, "inputSpots", tag);
        saveList(outputSpots, "outputSpots", tag);
        saveList(inputContainers, "inputContainers", tag);
        saveList(outputContainers, "outputContainers", tag);
        if (surgicalBE != null) {
            tag.putLong("surgicalBE", surgicalBE.asLong());
        }
        return tag;
    }

    private void saveList(List<BlockPos> pos, String name, CompoundTag tag) {
        tag.putLongArray(name, pos.stream().map(BlockPos::asLong).toList());
    }

    public void loadNBT(CompoundTag tag) {
        loadList(inputPods, "inputPods", tag);
        loadList(outputPods, "outputPods", tag);
        loadList(inputSpots, "inputSpots", tag);
        loadList(outputSpots, "outputSpots", tag);
        loadList(inputContainers, "inputContainers", tag);
        loadList(outputContainers, "outputContainers", tag);
        if (tag.contains("surgicalBE")) {
            surgicalBE = BlockPos.of(tag.getLong("surgicalBE"));
        }
    }

    private void loadList(List<BlockPos> pos, String name, CompoundTag tag) {
        pos.clear();
        pos.addAll(Arrays.stream(tag.getLongArray(name)).mapToObj(BlockPos::of).toList());
    }

}
