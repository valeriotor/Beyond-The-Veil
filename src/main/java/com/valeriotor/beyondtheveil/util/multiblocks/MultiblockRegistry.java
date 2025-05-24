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

    public static String BLOOD_WELL;
    public static String SACRIFICE_ALTAR;
    public static String DREAM_SHRINE;

    public static void registerMultiblocks() {
        BLOOD_WELL = registerMultiblock("blood_well");
        //SACRIFICE_ALTAR = registerMultiblock("sacrifice_altar");
        //DREAM_SHRINE = registerMultiblock("dream_shrine");
    }

    public static MultiblockSchematic getMultiblock(String name) {
        return multiblocks.get(name);
    }

    private static String registerMultiblock(String name) {
        try {
            String file = Resources.toString(BeyondTheVeil.class.getResource("/data/beyondtheveil/multiblock/" + name + ".json"), Charsets.UTF_8);
            MultiblockSchematic schem = BeyondTheVeil.GSON.fromJson(file, MultiblockSchematic.class);
            if(schem.process(name)) {
                if(DEBUG_PRINTS)
                    System.out.println(schem.toString());
                multiblocks.put(name, schem);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return name;
    }

}
