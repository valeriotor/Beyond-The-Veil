package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DreamUnderground extends Dream {

    private final Set<Block> searchedBlocks;
    private final Set<Block> allBlocks;

    public DreamUnderground(Memory memory, int priority, Set<Block> searchedBlocks) {
        super(memory, priority, ReminiscenceUnderground::new);
        this.searchedBlocks = new HashSet<>();
        this.allBlocks = new HashSet<>();
        this.searchedBlocks.addAll(searchedBlocks);
        this.allBlocks.addAll(Set.of(Blocks.STONE, Blocks.AIR, Blocks.DEEPSLATE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.GRASS, Blocks.SAND, Blocks.SANDSTONE, Blocks.WATER, Blocks.GRAVEL));
        this.allBlocks.addAll(searchedBlocks);
    }

    @Override
    public boolean activate(Player p, Level l) {
        return activatePos(p, l, p.getOnPos());
    }

    @Override
    public boolean activatePlayer(Player caster, Player target, Level l) {
        return activatePos(caster, l, target.getOnPos());
    }

    @Override
    public boolean activatePos(Player p, Level l, BlockPos pos) {
        Map<Integer, String[][]> layers = new HashMap<>();
        //Map<String, Integer> counter = new HashMap<>();
        //for (Block b : searchedBlocks) {
        //    counter.put(b.getRegistryName().toString(), 0);
        //}
        if (p != null) {
            // TODO if has dreamt of void expand radius from 1 to 4
            // TODO if a proficient dreamer display blocks as it goes down (otherwise increase counters only)
            int radius = 2;
            for (int y = pos.getY(); y > l.getMinBuildHeight(); y--) {
                String[][] layer = new String[radius * 2 + 1][radius * 2 + 1];
                layers.put(y, layer);
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block block = l.getBlockState(pos.offset(x, y - pos.getY(), z)).getBlock();
                        if (ForgeRegistries.BLOCKS.getKey(block) != null && allBlocks.contains(block)) {
                            //counter.put(block.getRegistryName().toString(), counter.get(block.getRegistryName().toString()) + 1);
                            layer[x + radius][z + radius] = ForgeRegistries.BLOCKS.getKey(block).toString();
                        } else {
                            layer[x + radius][z + radius] = "null";
                        }
                    }
                }
            }
        }
        Reminiscence reminiscence = new ReminiscenceUnderground(layers, searchedBlocks.stream().map(b -> ForgeRegistries.BLOCKS.getKey(b).toString()).collect(Collectors.toSet()));
        DataUtil.addReminiscence(p, memory, reminiscence);
        return true;
    }

}
