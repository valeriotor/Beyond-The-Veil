package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.valeriotor.beyondtheveil.Registration.*;

public class BTVItemModels extends ItemModelProvider {

    public BTVItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, References.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(DAMP_WOOD_ITEM.getId().getPath(), modLoc("block/damp_wood"));
        withExistingParent(DARK_SAND_ITEM.getId().getPath(), modLoc("block/dark_sand"));
        withExistingParent(DAMP_LOG_ITEM.getId().getPath(), modLoc("block/damp_log"));
        withExistingParent(DAMP_WOOD_STAIRS_ITEM.getId().getPath(), modLoc("block/damp_wood_stairs"));
        withExistingParent(DAMP_CANOPY_ITEM.getId().getPath(), modLoc("block/damp_canopy"));
        withExistingParent(DAMP_FILLED_CANOPY_ITEM.getId().getPath(), modLoc("block/damp_filled_canopy"));
        withExistingParent(WORN_BRICKS_ITEM.getId().getPath(), modLoc("block/worn_bricks"));
        withExistingParent(IDOL_ITEM.getId().getPath(), modLoc("block/idol"));
        withExistingParent(FISH_BARREL_ITEM.getId().getPath(), modLoc("block/fish_barrel"));
        withExistingParent(SLUG_BAIT_ITEM.getId().getPath(), modLoc("block/slug_bait"));
        withExistingParent(LAMP_ITEM.getId().getPath(), modLoc("block/lamp"));
        withExistingParent(BLUE_BRICKS_ITEM.getId().getPath(), modLoc("block/blue_bricks"));
        withExistingParent(WORN_BRICK_STAIRS_ITEM.getId().getPath(), modLoc("block/worn_brick_stairs"));
        withExistingParent(DAMP_WOOD_FENCE_ITEM.getId().getPath(), modLoc("block/damp_wood_fence_post"));
        withExistingParent(FUME_SPREADER_ITEM.getId().getPath(), modLoc("block/fume_spreader"));
        withExistingParent(SLEEP_CHAMBER_ITEM.getId().getPath(), modLoc("block/sleep_chamber"));
        withExistingParent(GEAR_BENCH_ITEM.getId().getPath(), modLoc("block/gear_bench"));

        singleTexture(ONIRIC_INCENSE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + ONIRIC_INCENSE.getId().getPath()));
        singleTexture(SLUG.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + SLUG.getId().getPath()));
        singleTexture(CANOE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + CANOE.getId().getPath()));
        singleTexture(RUM.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + RUM.getId().getPath()));
        singleTexture(WINE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + WINE.getId().getPath()));
        singleTexture(ALE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + ALE.getId().getPath()));
        singleTexture(VODKA.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + VODKA.getId().getPath()));
        singleTexture(MEAD.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + MEAD.getId().getPath()));
        singleTexture(CUP.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + CUP.getId().getPath()));
        singleTexture(FLUTE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + FLUTE.getId().getPath()));

    }
}
