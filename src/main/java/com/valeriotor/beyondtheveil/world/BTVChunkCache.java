package com.valeriotor.beyondtheveil.world;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.valeriotor.beyondtheveil.tileEntities.TileDeepChest;
import com.valeriotor.beyondtheveil.world.Structures.loot.LootTables;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BTVChunkCache {
    private final List<ShortState> blocks = new LinkedList<>();
    private final List<LootCoords> loot = new ArrayList<>();

    public void setBlockState(int x, int y, int z, IBlockState state) {
        short coords = (short) (((y << 8) | ((z & 15) << 4) | (x & 15)) & 65535);
        blocks.add(new ShortState(coords, state));
    }

    public void setBlockState(BlockPos pos, IBlockState state) {
        setBlockState(pos.getX(), pos.getY(), pos.getZ(), state);
    }

    public void setLootEntry(int x, int y, int z, String lootName) {
        short coords = (short) (((y << 8) | ((z & 15) << 4) | (x & 15)) & 65535);
        loot.add(new LootCoords(lootName, coords));
    }

    public void setLootEntry(BlockPos pos, String lootName) {
        setLootEntry(pos.getX(), pos.getY(), pos.getZ(), lootName);
    }

    public void generate(World w, int chunkX, int chunkZ) {
        for (ShortState ss : blocks)
            ss.generate(w, chunkX, chunkZ);
        for (LootCoords lootCoords : loot) {
            lootCoords.generate(w, chunkX, chunkZ);
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        Map<String, List<Integer>> blockNamesToCoordsAndMetas = blocks.stream().collect(Collectors.groupingBy(ShortState::getRegistryName,
                LinkedHashMap::new,
                Collectors.mapping(ShortState::getCoordsAndMeta, Collectors.toCollection(ArrayList::new))));
        for (Entry<String, List<Integer>> entry : blockNamesToCoordsAndMetas.entrySet()) {
            List<Integer> coordsAndMetasList = entry.getValue();
            int[] coordsAndMetas = new int[coordsAndMetasList.size()];
            for (int i = 0; i < coordsAndMetasList.size(); i++) {
                coordsAndMetas[i] = coordsAndMetasList.get(i);
            }
            nbt.setIntArray(entry.getKey(), coordsAndMetas);
        }
        NBTTagCompound lootTag = new NBTTagCompound();
        nbt.setTag("loot", lootTag);
        for (LootCoords loot : this.loot) {
            lootTag.setString(String.valueOf(loot.coords), loot.lootName);
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        if (!blocks.isEmpty()) throw new IllegalStateException();
        for (String s : nbt.getKeySet()) {
            if (!s.equals("loot")) {
                int[] coordsAndMetas = nbt.getIntArray(s);
                for (int i : coordsAndMetas)
                    blocks.add(new ShortState(s, i));
            } else {
                NBTTagCompound lootTag = nbt.getCompoundTag("loot");
                for (String lootCoords : lootTag.getKeySet()) {
                    short coords = Short.parseShort(lootCoords);
                    this.loot.add(new LootCoords(lootTag.getString(lootCoords), coords));
                }
            }
        }
    }

    private static class ShortState {
        private final short coords; // (15 downto 8) = y | (7 downto 4) = z | (3 downto 0) = x
        private final IBlockState state;

        private ShortState(short coords, IBlockState state) {
            this.coords = coords;
            this.state = state;
        }

        private ShortState(String registryName, int coordsAndMeta) {
            Block b = Block.getBlockFromName(registryName);
            state = b.getStateFromMeta(coordsAndMeta >> 16);
            coords = (short) (coordsAndMeta & 32767);
        }

        private void generate(World w, int chunkX, int chunkZ) {
            BlockPos pos = new BlockPos(chunkX << 4 | (coords & 15), (coords >> 8) & 255, chunkZ << 4 | ((coords >> 4) & 15));
            w.setBlockState(pos, state, 18);
        }

        private Integer getCoordsAndMeta() { // 31 downto 16 = meta | 15 downto 0 = coords
            return Integer.valueOf((state.getBlock().getMetaFromState(state) << 16) | coords);
        }

        private String getRegistryName() {
            return state.getBlock().getRegistryName().toString();
        }

    }

    private static class LootCoords {
        private final String lootName;
        private final short coords;

        public LootCoords(String lootName, short coords) {
            this.lootName = lootName;
            this.coords = coords;
        }

        private void generate(World w, int chunkX, int chunkZ) {
            BlockPos pos = new BlockPos(chunkX << 4 | (coords & 15), (coords >> 8) & 255, chunkZ << 4 | ((coords >> 4) & 15));
            TileEntity t = w.getTileEntity(pos);
            if (t instanceof TileEntityLockableLoot) {
                ResourceLocation lootTable = LootTables.lootTables.get(lootName);
                ((TileEntityLockableLoot) t).setLootTable(lootTable, w.rand.nextLong());
            } else if (t instanceof TileDeepChest) {
                ResourceLocation lootTable = LootTables.lootTables.get(lootName);
                ((TileDeepChest) t).setLootTable(lootTable, w.rand.nextLong());
            }
        }

    }

}
