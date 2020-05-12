package com.valeriotor.beyondtheveil.multiblock;

import java.io.IOException;
import java.util.HashMap;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;

public class MultiblockRegistry {

	private static final boolean DEBUG_PRINTS = false;
	public static HashMap<String, MultiblockSchematic> multiblocks = new HashMap<>();
	
	public static void registerMultiblocks() {
		registerMultiblock(BLOOD_WELL);
		registerMultiblock(SACRIFICE_ALTAR);
		registerMultiblock(DREAM_SHRINE);		
	}
	
	private static void registerMultiblock(String name) {
		try {
			String file = Resources.toString(BeyondTheVeil.class.getResource("/assets/beyondtheveil/multiblock/" + name + ".json"), Charsets.UTF_8);
			MultiblockSchematic schem = BeyondTheVeil.gson.fromJson(file, MultiblockSchematic.class);
			if(schem.process()) {
				if(DEBUG_PRINTS)
					System.out.println(schem.toString());
				multiblocks.put(name, schem);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static final String BLOOD_WELL = "blood_well";
	public static final String SACRIFICE_ALTAR = "sacrifice_altar";
	public static final String DREAM_SHRINE = "dream_shrine";
	
}
