package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.block.GearBenchBlock;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static com.valeriotor.beyondtheveil.Registration.*;

public class BTVLanguageProvider extends LanguageProvider {

    public BTVLanguageProvider(DataGenerator gen, String locale) {
        super(gen, References.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + References.TAB_NAME, "Beyond The Veil");
        add(DAMP_WOOD.get(), "Damp Wood");
        add(DARK_SAND.get(), "Dark Sand");
        add(DAMP_LOG.get(), "Damp Log");
        add(DAMP_WOOD_STAIRS.get(), "Damp Wooden Stairs");
        add(DAMP_CANOPY.get(), "Damp Canopy");
        add(DAMP_FILLED_CANOPY.get(), "Damp Filled Canopy");
        add(WORN_BRICKS.get(), "Worn Bricks");
        add(IDOL.get(), "Idol");
        add(FISH_BARREL.get(), "Fish Barrel");
        add(SLUG_BAIT.get(), "Slug Bait");
        add(LAMP.get(), "Lamp");
        add(BLUE_BRICKS.get(), "Blue Bricks");
        add(WORN_BRICK_STAIRS.get(), "Worn Brick Stairs");
        add(DAMP_WOOD_FENCE.get(), "Damp Wood Fence");
        add(FUME_SPREADER.get(), "Fume Spreader");
        add(SLEEP_CHAMBER.get(), "Sleep Chamber");
        add(GEAR_BENCH.get(), "Gear Bench");

        add(ONIRIC_INCENSE.get(), "Oniric Incense");
        add(SLUG_CATCHER.get(), "Slug Catcher");
        add(SLUG.get(), "Slug");
        add(CANOE.get(), "Canoe");
        add(RUM.get(), "Cup of Rum");
        add(WINE.get(), "Cup of Wine");
        add(ALE.get(), "Cup of Ale");
        add(VODKA.get(), "Cup of Vodka");
        add(MEAD.get(), "Cup of Mead");
        add(CUP.get(), "Empty Cup");
        add(FLUTE.get(), "Flute of the Outer Gods");

        add(GearBenchBlock.GUI_GEAR_BENCH, "Gear Bench");
    }
}
