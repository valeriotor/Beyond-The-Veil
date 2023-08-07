package com.valeriotor.beyondtheveil.dreaming.dreams;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ReminiscenceUnderground extends Reminiscence{

    private final Map<Integer, String[][]> layers;
    private final Map<String, Integer> counter = new HashMap<>();

    public ReminiscenceUnderground() {
        layers = new HashMap<>();
    }

    public ReminiscenceUnderground(Map<Integer, String[][]> layers) {
        this.layers = layers;
        count();
    }

    private void count() {
        counter.clear();
        for (Entry<Integer, String[][]> e : layers.entrySet()) {
            for (String[] row : e.getValue()) {
                for (String block : row) {
                    if ("null".equals(block)) {
                        continue;
                    }
                    if (!counter.containsKey(block)) {
                        counter.put(block, 0);
                    }
                    counter.put(block, counter.get(block) + 1);
                }
            }
        }
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
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        layers.clear();
        int radius = tag.getInt("radius");
        for (String key : tag.getAllKeys()) {
            if ("radius".equals(key)) {
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
        count();
    }

    public Map<Integer, String[][]> getLayers() {
        return layers;
    }

    public Map<String, Integer> getCounter() {
        return counter;
    }
}
