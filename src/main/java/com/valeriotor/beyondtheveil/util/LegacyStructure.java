package com.valeriotor.beyondtheveil.util;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.DampCanopyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LegacyStructure {

    public static LegacyStructure lighthouse;
    public static LegacyStructure deep_arena;
    public static LegacyStructure deep_beacon;
    public static LegacyStructure deep_home1;

    public static void registerLegacyStructures() {
        lighthouse = new LegacyStructure("lighthouse");
        lighthouse.registerBlocks();
        deep_arena = new LegacyStructure("deep_arena");
        deep_arena.registerBlocks();
        deep_beacon = new LegacyStructure("deep_beacon");
        deep_beacon.registerBlocks();
        deep_home1 = new LegacyStructure("deep_home1");
        deep_home1.registerBlocks();
    }

    private final String name;
    private Map<Block, byte[][]> map;

    public LegacyStructure(String name) {
        this.name = name;
    }

    public void registerBlocks() {
        String file;
        try {
            file = Resources.toString(BeyondTheVeil.class.getResource("/data/beyondtheveil/legacy/" + name + ".json"), Charsets.UTF_8);
            JSonStructure jssb = BeyondTheVeil.GSON.fromJson(file, JSonStructure.class);
            this.map = jssb.getMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateStructure(Level level, BlockPos centerPos) {
        for (Map.Entry<Block, byte[][]> entry : map.entrySet()) {
            for (byte[] coords : entry.getValue()) {
                BlockState state = entry.getKey().defaultBlockState();
                if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
                    if (entry.getKey() == Registration.DAMP_CANOPY.get() || entry.getKey() == Registration.DAMP_FILLED_CANOPY.get()) {
                        state = state.setValue(HorizontalDirectionalBlock.FACING, Direction.from2DDataValue((coords[3] + 2) & 3));
                        if (entry.getKey() == Registration.DAMP_CANOPY.get() && coords[3] >= 5) {
                            state = state.setValue(DampCanopyBlock.FLAT, true);
                        }
                    } else {
                        Direction d = switch (coords[3]) {
                            case 0, 4 -> Direction.EAST;
                            case 1, 5 -> Direction.WEST;
                            case 2, 6 -> Direction.SOUTH;
                            case 3, 7 -> Direction.NORTH;
                            default -> Direction.from2DDataValue((coords[3] + 2) & 3);
                        };
                        state = state.setValue(HorizontalDirectionalBlock.FACING, d);
                        if (entry.getKey() instanceof StairBlock && coords[3] >= 4) {
                            state = state.setValue(StairBlock.HALF, Half.TOP);
                        }
                    }
                }
                if (state.getBlock() == Blocks.PRISMARINE) {
                    state = Blocks.DARK_PRISMARINE.defaultBlockState();
                }
                level.setBlock(centerPos.offset(coords[0], coords[1], coords[2]), state, 3);
                level.updateNeighborsAt(centerPos.offset(coords[0], coords[1], coords[2]), entry.getKey());
            }
        }
    }

    private static class JSonStructure {

        HashMap<String, List<byte[]>> blockMap;
        private int xSize, ySize, zSize;
        public JSonStructure(HashMap<String, List<byte[]>> blockMap, int xSize, int ySize, int zSize) {
            this.blockMap = blockMap;
            this.xSize = xSize;
            this.ySize = ySize;
            this.zSize = zSize;
        }

        public HashMap<Block, byte[][]> getMap(){
            HashMap<Block, byte[][]> map = new HashMap<>();
            for(Map.Entry<String, List<byte[]>> entry : this.blockMap.entrySet()) {
                byte[][] bytes = new byte[entry.getValue().size()][4];
                for(int i = 0; i < entry.getValue().size(); i++) {
                    bytes[i] = entry.getValue().get(i);
                }
                if(entry.getKey().contains(":"))
                    map.put(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entry.getKey())), bytes);
            }
            return map;
        }

        public int getXSize() {
            return this.xSize;
        }

        public int getYSize() {
            return this.ySize;
        }

        public int getZSize() {
            return this.zSize;
        }
    }


}
