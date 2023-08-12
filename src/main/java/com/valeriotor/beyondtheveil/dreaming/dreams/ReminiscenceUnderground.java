package com.valeriotor.beyondtheveil.dreaming.dreams;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ReminiscenceUnderground extends Reminiscence{

    private final Map<Integer, String[][]> layers;
    private final Set<String> countedBlocks;

    public ReminiscenceUnderground() {
        layers = new HashMap<>();
        countedBlocks = new HashSet<>();
    }

    public ReminiscenceUnderground(Map<Integer, String[][]> layers, Set<String> countedBlocks) {
        this.layers = layers;
        this.countedBlocks = countedBlocks;
    }

    @Override
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        int radius = 0;
        for (Entry<Integer, String[][]> entry : layers.entrySet()) {
            CompoundTag layerTag = new CompoundTag();
            tag.put(String.valueOf(entry.getKey()), layerTag);
            radius = (entry.getValue().length-1)/2;
            for (int i = 0; i < entry.getValue().length; i++) {
                CompoundTag rowTag = new CompoundTag();
                layerTag.put(String.valueOf(i), rowTag);
                String[] row = entry.getValue()[i];
                for (int j = 0; j < row.length; j++) {
                    rowTag.putString(String.valueOf(j), row[j]);
                }
            }
        }
        tag.putInt("radius", radius);
        CompoundTag countedBlocks = new CompoundTag();
        tag.put("counted", countedBlocks);
        for (String countedBlock : this.countedBlocks) {
            countedBlocks.putBoolean(countedBlock, true);
        }
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        layers.clear();
        int radius = tag.getInt("radius");
        for (String key : tag.getAllKeys()) {
            if ("radius".equals(key) || "counted".equals(key)) {
                continue;
            }
            String[][] layer = new String[2 * radius + 1][2 * radius + 1];
            layers.put(Integer.parseInt(key), layer);
            CompoundTag layerTag = tag.getCompound(key);
            for (String rowIndex : layerTag.getAllKeys()) {
                CompoundTag rowTag = layerTag.getCompound(rowIndex);
                for (String columnIndex : rowTag.getAllKeys()) {
                    layer[Integer.parseInt(rowIndex)][Integer.parseInt(columnIndex)] = rowTag.getString(columnIndex);
                }
            }
        }
        if (tag.contains("counted")) {
            CompoundTag countedTag = tag.getCompound("counted");
            countedBlocks.addAll(countedTag.getAllKeys());
        }
    }

    public Map<Integer, String[][]> getLayers() {
        return layers;
    }

    public Set<String> getCountedBlocks() {
        return countedBlocks;
    }
}
