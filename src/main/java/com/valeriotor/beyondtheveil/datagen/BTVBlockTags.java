package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.valeriotor.beyondtheveil.Registration.*;

public class BTVBlockTags extends BlockTagsProvider {

    public BTVBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        super(output, lookupProvider, References.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(DAMP_WOOD.get())
                .add(DAMP_LOG.get())
                .add(DAMP_FILLED_CANOPY.get())
                .add(DAMP_CANOPY.get())
                .add(DAMP_WOOD_STAIRS.get())
                .add(DAMP_WOOD_FENCE.get())
                .add(SLEEP_CHAMBER.get())
                .add(FLASK_SHELF.get())
                .add(CURTAIN.get())
                .add(GEAR_BENCH.get())
                .add(LAMP.get())
                .add(SLUG_BAIT.get())
                .add(ARBOREAL_GENERATOR.get())
                .add(MEGYDREA.get())
                .add(VIJHISS.get())
                .add(FISH_BARREL.get())
                .add(ALGAE_BLOCK.get())
                .add(FLEBO.get())
                .add(SURGERY_BED.get())
        ;
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(DEBRIS_DIAMOND.get())
                .add(DEBRIS_GOLD.get())
                .add(DEBRIS_NETHERITE.get())
                .add(DARK_SAND.get())
                .add(REDSTONE_GRASS.get())
                .add(GHOST_GRASS.get())
        ;
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(WORN_BRICKS.get())
                .add(WORN_BRICK_STAIRS.get())
                .add(BLUE_BRICKS.get())
                .add(LAMP.get())
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
                .add(BLOOD_BASIN.get())
                .add(GEAR_BENCH.get())
                .add(DREAM_FOCUS.get())
                .add(DREAM_FOCUS_FLUIDS.get())
                .add(OFFER_PILLAR.get())
                .add(DEMAND_PILLAR.get())
                .add(FLASK_LARGE.get())
                .add(FLASK_MEDIUM.get())
                .add(FLASK_SMALL.get())
                .add(JAR_LARGE.get())
                .add(JAR_MEDIUM.get())
                .add(JAR_SMALL.get())
                .add(FLASK_ITEM.get())
                .add(FUME_SPREADER.get())
                .add(MEMORY_SIEVE.get())
                .add(LETTER_BOX.get())
                .add(DAMP_STONE.get())
                .add(DAMP_MOSSY_STONE.get())
                .add(WATERY_CRADLE.get())
                .add(PATIENT_POD.get())
                .add(ALEMBICS.get())
                .add(LACRYMATORY.get())
                .add(VEIN_STONE.get())
                .add(VEIN_STONE_VESSEL.get())
                .add(DARK_GLASS.get())
                .add(DEEP_CHEST.get())
        ;

        tag(BlockTags.WOODEN_FENCES)
                .add(DAMP_WOOD_FENCE.get());
        tag(BlockTags.LOGS)
                .add(DAMP_LOG.get());
        tag(BlockTags.PLANKS)
                .add(DAMP_WOOD.get());
        tag(BlockTags.STAIRS)
                .add(DAMP_WOOD_STAIRS.get())
                .add(BLOOD_BRICK_STAIRS.get())
                .add(ELDER_STONE_BRICK_STAIRS.get())
                .add(ELDER_BRICK_STAIRS.get())
                .add(WORN_BRICK_STAIRS.get());
        tag(BlockTags.SLABS)
                .add(ELDER_BRICK_SLAB.get())
                .add(ELDER_SMOOTH_STONE_SLAB.get())
                .add(ELDER_STONE_BRICK_SLAB.get())
                .add(BLOOD_BRICK_SLAB.get())
                .add(BLOOD_SMOOTH_STONE_SLAB.get());
    }

    @Override
    public String getName() {
        return "BTV Tags";
    }
}