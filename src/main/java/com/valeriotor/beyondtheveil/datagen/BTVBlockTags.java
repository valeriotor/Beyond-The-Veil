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
                .add(ELDER_BRICK.get())
                .add(ELDER_BRICK_SLAB.get())
                .add(ELDER_BRICK_STAIRS.get())
                .add(ELDER_STONE_BRICK.get())
                .add(ELDER_STONE_BRICK_SLAB.get())
                .add(ELDER_STONE_BRICK_STAIRS.get())
                .add(ELDER_STONE_BRICK_CHISELED.get())
                .add(ELDER_SMOOTH_STONE.get())
                .add(ELDER_SMOOTH_STONE_SLAB.get())
                .add(BLOOD_BRICK.get())
                .add(BLOOD_BRICK_SLAB.get())
                .add(BLOOD_BRICK_STAIRS.get())
                .add(BLOOD_SMOOTH_STONE.get())
                .add(BLOOD_SMOOTH_STONE_SLAB.get())
                .add(SACRIFICE_ALTAR.get())
                .add(GEAR_BENCH.get());
        tag(BlockTags.WOODEN_FENCES)
                .add(DAMP_WOOD_FENCE.get());
    }

    @Override
    public String getName() {
        return "BTV Tags";
    }
}