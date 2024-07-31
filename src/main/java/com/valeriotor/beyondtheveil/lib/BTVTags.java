package com.valeriotor.beyondtheveil.lib;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class BTVTags {

    public static TagKey<Structure> DEEP_VEIN = create("deep_vein");

    private static TagKey<Structure> create(String pName) {
        return TagKey.create(Registries.STRUCTURE, new ResourceLocation(References.MODID, pName));
    }


}
