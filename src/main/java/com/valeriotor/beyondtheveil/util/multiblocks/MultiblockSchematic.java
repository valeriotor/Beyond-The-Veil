package com.valeriotor.beyondtheveil.util.multiblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map.Entry;

public class MultiblockSchematic {
    public String[][] strings;
    public HashMap<String, String> map;
    private transient ItemStack[][][] stacks;
    private transient int sideSize;
    private transient String name;

    public boolean process(String name) {
        this.name = name;
        sideSize = strings[0].length;
        stacks = new ItemStack[strings.length][sideSize][sideSize];
        HashMap<Character, ItemStack> newMap = new HashMap<>();
        for(Entry<String,String> entry : map.entrySet()) {
            Item value = ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry.getValue()));
            if (value != null) {
                newMap.put(entry.getKey().charAt(0), new ItemStack(value));
            }
        }
        for(int i = 0; i < strings.length; i++) {
            String[] layer = strings[i];
            for(int j = 0; j < layer.length; j++) {
                String s = layer[j];
                for(int k = 0; k < s.length(); k++) {
                    char c = s.charAt(k);
                    if(c != ' ') {
                        stacks[i][j][k] = newMap.get(c);
                    } else {
                        stacks[i][j][k] = ItemStack.EMPTY;
                    }
                }
            }
        }
        map = null;
        strings = null;
        return true;
    }

    public ItemStack[][][] getSchematic(){
        return this.stacks;
    }

    public int getSideSize() {
        return sideSize;
    }

    public boolean checksOutBottomNorthWest(Level l, BlockPos pos) {
        for(int x = 0; x < sideSize; x++) {
            for(int y = 0; y < stacks.length; y++) {
                for(int z = 0; z < sideSize; z++) {
                    ItemStack stack = stacks[y][x][z];
                    if(!stack.isEmpty()) {
                        BlockState s = l.getBlockState(pos.offset(x, y, z));
                        if(stack.getItem() != Item.BY_BLOCK.get(s.getBlock())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean checksOutBottomCenter(Level w, BlockPos pos) {
        return this.checksOutBottomNorthWest(w, pos.offset(-sideSize/2, 0, -sideSize/2));
    }

    public String getLocalizationKey() {
        return "multiblock.".concat(this.name.toLowerCase().concat(".name"));
    }

    public Component getTranslationComponent() {
        return Component.translatable(getLocalizationKey());
    }
}