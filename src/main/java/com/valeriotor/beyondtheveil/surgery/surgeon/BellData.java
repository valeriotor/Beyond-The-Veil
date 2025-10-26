package com.valeriotor.beyondtheveil.surgery.surgeon;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BellData {

    private final List<BlockPos> inputPods = new ArrayList<>();
    private final List<BlockPos> outputPods = new ArrayList<>();
    private final List<BlockPos> inputSpots = new ArrayList<>();
    private final List<BlockPos> outputSpots = new ArrayList<>();
    private final List<BlockPos> inputContainers = new ArrayList<>();
    private final List<BlockPos> outputContainers = new ArrayList<>();
    private BlockPos surgicalBE;


    public List<BlockPos> inputPods() {
        return new ArrayList<>(inputPods);
    }

    public List<BlockPos> outputPods() {
        return new ArrayList<>(outputPods);
    }

    public List<BlockPos> inputSpots() {
        return new ArrayList<>(inputSpots);
    }

    public List<BlockPos> outputSpots() {
        return new ArrayList<>(outputSpots);
    }

    public List<BlockPos> inputContainers() {
        return new ArrayList<>(inputContainers);
    }

    public List<BlockPos> outputContainers() {
        return new ArrayList<>(outputContainers);
    }

    public BlockPos getSurgicalBE() { // TODO if this is null then surgeon progress should not start
        return surgicalBE;
    }

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

    public void addPosition(BlockPos pos, PositionType type) {
        List<BlockPos> list = switch (type) {
            case INPUT_PODS -> inputPods;
            case OUTPUT_PODS -> outputPods;
            case INPUT_SPOTS -> inputSpots;
            case OUTPUT_SPOTS -> outputSpots;
            case INPUT_CONTAINERS -> inputContainers;
            case OUTPUT_CONTAINERS -> outputContainers;
            case BE -> null;
        };
        if (list != null) {
            if (list.contains(pos)) {
                list.remove(pos);
            } else {
                list.add(pos);
            }
        } else if (type == PositionType.BE) {
            if (Objects.equals(surgicalBE, pos)) {
                surgicalBE = null;
            } else {
                surgicalBE = pos;
            }
        }
    }

    public enum PositionType {
        INPUT_PODS, OUTPUT_PODS, INPUT_SPOTS, OUTPUT_SPOTS, INPUT_CONTAINERS, OUTPUT_CONTAINERS, BE
    }

}
