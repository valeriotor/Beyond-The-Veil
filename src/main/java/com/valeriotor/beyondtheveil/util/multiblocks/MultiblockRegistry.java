package com.valeriotor.beyondtheveil.util.multiblocks;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MultiblockRegistry {
    private static final boolean DEBUG_PRINTS = false;
    private static Map<String, MultiblockSchematic> multiblocks = new HashMap<>();

    public static MultiblockSchematic BLOOD_WELL;
    public static MultiblockSchematic BLOOD_WELL_COMPLETE;
    public static MultiblockSchematic SACRIFICE_ALTAR;
    public static MultiblockSchematic DREAM_SHRINE;

    public static void registerMultiblocks() {
        BLOOD_WELL = registerMultiblock("blood_well");
        BLOOD_WELL_COMPLETE = registerMultiblock("blood_well_complete");
        //SACRIFICE_ALTAR = registerMultiblock("sacrifice_altar");
        //DREAM_SHRINE = registerMultiblock("dream_shrine");
    }

    public static MultiblockSchematic getMultiblock(String name) {
        return multiblocks.get(name);
    }

    private static MultiblockSchematic registerMultiblock(String name) {
        MultiblockSchematic schem = null;
        try {
            String file = Resources.toString(BeyondTheVeil.class.getResource("/data/beyondtheveil/multiblock/" + name + ".json"), Charsets.UTF_8);
            schem = BeyondTheVeil.GSON.fromJson(file, MultiblockSchematic.class);
            if(schem.process(name)) {
                if(DEBUG_PRINTS)
                    System.out.println(schem.toString());
                multiblocks.put(name, schem);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return schem;
    }

}
