package com.valeriotor.BTV.multiblock;

import java.io.IOException;
import java.util.HashMap;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.BTV.BeyondTheVeil;

public class MultiblockRegistry {

	private static final boolean DEBUG_PRINTS = false;
	public static HashMap<String, MultiblockSchematic> multiblocks = new HashMap<>();
	
	public static void registerMultiblocks() {
		registerMultiblock("blood_well");
		registerMultiblock("sacrifice_altar");
		registerMultiblock("dream_shrine");		
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
	
}
