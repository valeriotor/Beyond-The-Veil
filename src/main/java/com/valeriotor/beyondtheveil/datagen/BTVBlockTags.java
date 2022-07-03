package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.valeriotor.beyondtheveil.Registration.*;

public class BTVBlockTags extends BlockTagsProvider {

    public BTVBlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, References.MODID, helper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(DAMP_WOOD.get())
                .add(DAMP_LOG.get())
                .add(DAMP_FILLED_CANOPY.get())
                .add(DAMP_CANOPY.get())
                .add(DAMP_WOOD_STAIRS.get())
                .add(DAMP_WOOD_FENCE.get())
                .add(SLEEP_CHAMBER.get())
                .add(FISH_BARREL.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(DARK_SAND.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(WORN_BRICKS.get())
                .add(WORN_BRICK_STAIRS.get())
                .add(BLUE_BRICKS.get())
                .add(LAMP.get())
                .add(SLUG_BAIT.get())
                .add(GEAR_BENCH.get());
        tag(BlockTags.WOODEN_FENCES)
                .add(DAMP_WOOD_FENCE.get());
    }

    @Override
    public String getName() {
        return "BTV Tags";
    }
}