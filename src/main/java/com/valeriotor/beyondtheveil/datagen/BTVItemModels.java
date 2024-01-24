package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.valeriotor.beyondtheveil.Registration.*;

public class BTVItemModels extends ItemModelProvider {

    public BTVItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, References.MODID, existingFileHelper);
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
        withExistingParent(ELDER_BRICK_ITEM.getId().getPath(), modLoc("block/elder_brick"));
        withExistingParent(ELDER_BRICK_SLAB_ITEM.getId().getPath(), modLoc("block/elder_brick_slab"));
        withExistingParent(ELDER_BRICK_STAIRS_ITEM.getId().getPath(), modLoc("block/elder_brick_stairs"));
        withExistingParent(ELDER_STONE_BRICK_ITEM.getId().getPath(), modLoc("block/elder_stone_brick"));
        withExistingParent(ELDER_STONE_BRICK_SLAB_ITEM.getId().getPath(), modLoc("block/elder_stone_brick_slab"));
        withExistingParent(ELDER_STONE_BRICK_STAIRS_ITEM.getId().getPath(), modLoc("block/elder_stone_brick_stairs"));
        withExistingParent(ELDER_STONE_BRICK_CHISELED_ITEM.getId().getPath(), modLoc("block/elder_stone_brick_chiseled"));
        withExistingParent(ELDER_SMOOTH_STONE_ITEM.getId().getPath(), modLoc("block/elder_smooth_stone"));
        withExistingParent(ELDER_SMOOTH_STONE_SLAB_ITEM.getId().getPath(), modLoc("block/elder_smooth_stone_slab"));
        withExistingParent(BLOOD_BRICK_ITEM.getId().getPath(), modLoc("block/blood_brick"));
        withExistingParent(BLOOD_BRICK_SLAB_ITEM.getId().getPath(), modLoc("block/blood_brick_slab"));
        withExistingParent(BLOOD_BRICK_STAIRS_ITEM.getId().getPath(), modLoc("block/blood_brick_stairs"));
        withExistingParent(BLOOD_SMOOTH_STONE_ITEM.getId().getPath(), modLoc("block/blood_smooth_stone"));
        withExistingParent(BLOOD_SMOOTH_STONE_SLAB_ITEM.getId().getPath(), modLoc("block/blood_smooth_stone_slab"));
        withExistingParent(SACRIFICE_ALTAR_ITEM.getId().getPath(), modLoc("block/sacrifice_altar"));
        withExistingParent(WATERY_CRADLE_ITEM.getId().getPath(), modLoc("block/watery_cradle"));
        withExistingParent(FLASK_SHELF_ITEM.getId().getPath(), modLoc("block/flask_shelf"));
        withExistingParent(FLASK_LARGE_ITEM.getId().getPath(), modLoc("block/flask_large"));
        withExistingParent(FLASK_MEDIUM_ITEM.getId().getPath(), modLoc("block/flask_medium"));
        withExistingParent(FLASK_SMALL_ITEM.getId().getPath(), modLoc("block/flask_small"));
        withExistingParent(MEMORY_SIEVE_ITEM.getId().getPath(), modLoc("block/memory_sieve"));
        withExistingParent(SURGERY_BED_ITEM.getId().getPath(), modLoc("block/surgery_bed"));
        withExistingParent(DEEP_ONE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(BLOOD_SKELETON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(BLOOD_ZOMBIE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(BLOOD_WRAITH_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        singleTexture(ONIRIC_INCENSE.getId().getPath(),     mcLoc("item/generated"), "layer0", modLoc("item/" + ONIRIC_INCENSE.getId().getPath()));
        singleTexture(SLUG.getId().getPath(),               mcLoc("item/generated"), "layer0", modLoc("item/" + SLUG.getId().getPath()));
        singleTexture(CANOE.getId().getPath(),              mcLoc("item/generated"), "layer0", modLoc("item/" + CANOE.getId().getPath()));
        singleTexture(RUM.getId().getPath(),                mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + RUM.getId().getPath()));
        singleTexture(WINE.getId().getPath(),               mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + WINE.getId().getPath()));
        singleTexture(ALE.getId().getPath(),                mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + ALE.getId().getPath()));
        singleTexture(VODKA.getId().getPath(),              mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + VODKA.getId().getPath()));
        singleTexture(MEAD.getId().getPath(),               mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + MEAD.getId().getPath()));
        singleTexture(CUP.getId().getPath(),                mcLoc("item/generated"), "layer0", modLoc("item/beverages/" + CUP.getId().getPath()));
        singleTexture(FLUTE.getId().getPath(),              mcLoc("item/generated"), "layer0", modLoc("item/" + FLUTE.getId().getPath()));
        singleTexture(WOLF_MEDALLION.getId().getPath(),     mcLoc("item/generated"), "layer0", modLoc("item/" + WOLF_MEDALLION.getId().getPath()));
        singleTexture(TABLET.getId().getPath(),             mcLoc("item/generated"), "layer0", modLoc("item/" + TABLET.getId().getPath()));
        singleTexture(BRONZE_SPHERE.getId().getPath(),      mcLoc("item/generated"), "layer0", modLoc("item/" + BRONZE_SPHERE.getId().getPath()));
        singleTexture(REDSTONE_WEED_SEEDS.getId().getPath(),mcLoc("item/generated"), "layer0", modLoc("item/" + REDSTONE_WEED_SEEDS.getId().getPath()));
        singleTexture(GHOST_WEED_SEEDS.getId().getPath(),   mcLoc("item/generated"), "layer0", modLoc("item/" + GHOST_WEED_SEEDS.getId().getPath()));
        singleTexture(VANILLA_WEED_SEEDS.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + VANILLA_WEED_SEEDS.getId().getPath()));
        singleTexture(BLACKJACK.getId().getPath(),          mcLoc("item/generated"), "layer0", modLoc("item/" + BLACKJACK.getId().getPath()));
        singleTexture(SPINE.getId().getPath(),              mcLoc("item/generated"), "layer0", modLoc("item/" + SPINE.getId().getPath()));
        singleTexture(HELD_VILLAGER.getId().getPath(),      mcLoc("item/generated"), "layer0", modLoc("item/" + HELD_VILLAGER.getId().getPath()));
        singleTexture(HELD_WEEPER.getId().getPath(),        mcLoc("item/generated"), "layer0", modLoc("item/" + HELD_WEEPER.getId().getPath()));
        singleTexture(HELD_FLETUM.getId().getPath(),        mcLoc("item/generated"), "layer0", modLoc("item/" + HELD_FLETUM.getId().getPath()));
        singleTexture(HELD_SHOGGOTH.getId().getPath(),      mcLoc("item/generated"), "layer0", modLoc("item/" + HELD_SHOGGOTH.getId().getPath()));
        singleTexture(SURGERY_TOOLS.getId().getPath(),      mcLoc("item/generated"), "layer0", modLoc("item/" + SURGERY_TOOLS.getId().getPath()));
        singleTexture(BONE_TIARA.getId().getPath(),         mcLoc("item/generated"), "layer0", modLoc("item/" + BONE_TIARA.getId().getPath()));
        singleTexture(BLEEDING_BELT.getId().getPath(),      mcLoc("item/generated"), "layer0", modLoc("item/" + BLEEDING_BELT.getId().getPath()));
        singleTexture(DREAM_BOTTLE.getId().getPath(),       mcLoc("item/generated"), "layer0", modLoc("item/" + DREAM_BOTTLE.getId().getPath()));
        singleTexture(SHOGGOTH_MAP.getId().getPath(),       mcLoc("item/generated"), "layer0", modLoc("item/" + SHOGGOTH_MAP.getId().getPath()));
        singleTexture(BLOOD_COVENANT.getId().getPath(),     mcLoc("item/generated"), "layer0", modLoc("item/" + BLOOD_COVENANT.getId().getPath()));
        singleTexture(REVELATION_RING.getId().getPath(),    mcLoc("item/generated"), "layer0", modLoc("item/" + REVELATION_RING.getId().getPath()));
        singleTexture(AZACNO_CHARM.getId().getPath(),       mcLoc("item/generated"), "layer0", modLoc("item/" + AZACNO_CHARM.getId().getPath()));
        singleTexture(BLOOD_CROWN.getId().getPath(),        mcLoc("item/generated"), "layer0", modLoc("item/" + BLOOD_CROWN.getId().getPath()));
        singleTexture(CORAL_STAFF.getId().getPath(),        mcLoc("item/generated"), "layer0", modLoc("item/" + CORAL_STAFF.getId().getPath()));
        singleTexture(SIGIL_ZOMBIE.getId().getPath(),       mcLoc("item/generated"), "layer0", modLoc("item/" + SIGIL_ZOMBIE.getId().getPath()));
        singleTexture(SIGIL_SKELLIE.getId().getPath(),      mcLoc("item/generated"), "layer0", modLoc("item/" + SIGIL_SKELLIE.getId().getPath()));
        singleTexture(SIGIL_PLAYER.getId().getPath(),       mcLoc("item/generated"), "layer0", modLoc("item/" + SIGIL_PLAYER.getId().getPath()));
        singleTexture(SIGIL_PATHWAY.getId().getPath(),      mcLoc("item/generated"), "layer0", modLoc("item/" + SIGIL_PATHWAY.getId().getPath()));
        singleTexture(SACRIFICIAL_KNIFE.getId().getPath(),  mcLoc("item/generated"), "layer0", modLoc("item/" + SACRIFICIAL_KNIFE.getId().getPath()));
        singleTexture(MEMORY_PHIAL.getId().getPath(),       mcLoc("item/generated"), "layer0", modLoc("item/" + MEMORY_PHIAL.getId().getPath()));
        singleTexture(NECRONOMICON.getId().getPath(),       mcLoc("item/generated"), "layer0", modLoc("item/" + NECRONOMICON.getId().getPath()));
        singleTexture(GEAR.getId().getPath(),               mcLoc("item/generated"), "layer0", modLoc("item/" + GEAR.getId().getPath()));
        singleTexture(SURGEON_SUMMONS.getId().getPath(),    mcLoc("item/generated"), "layer0", modLoc("item/" + SURGEON_SUMMONS.getId().getPath()));
        singleTexture(CRUCIBLE.getId().getPath(),           mcLoc("item/generated"), "layer0", modLoc("item/" + CRUCIBLE.getId().getPath()));
        singleTexture(BLACK_MIRROR.getId().getPath(),       mcLoc("item/generated"), "layer0", modLoc("item/" + BLACK_MIRROR.getId().getPath()));
        singleTexture(FLESH_CARBON_TOKEN.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + FLESH_CARBON_TOKEN.getId().getPath()));
        singleTexture(HEART_ITEM.getId().getPath(),         mcLoc("item/generated"), "layer0", modLoc("item/" + HEART_ITEM.getId().getPath()));
        singleTexture(SYRINGE.getId().getPath(),            mcLoc("item/generated"), "layer0", modLoc("item/" + SYRINGE.getId().getPath()));
        singleTexture(SCALPEL.getId().getPath(),            mcLoc("item/generated"), "layer0", modLoc("item/" + SCALPEL.getId().getPath()));
        singleTexture(FORCEPS.getId().getPath(),            mcLoc("item/generated"), "layer0", modLoc("item/" + FORCEPS.getId().getPath()));
        singleTexture(TONGS.getId().getPath(),              mcLoc("item/generated"), "layer0", modLoc("item/" + TONGS.getId().getPath()));
        singleTexture(SEWING_NEEDLE.getId().getPath(),      mcLoc("item/generated"), "layer0", modLoc("item/" + SEWING_NEEDLE.getId().getPath()));
        singleTexture(SEDATIVE_BUCKET.getId().getPath(),    mcLoc("item/generated"), "layer0", modLoc("item/buckets/" + SEDATIVE_BUCKET.getId().getPath()));
        singleTexture(SOFTENER_BUCKET.getId().getPath(),    mcLoc("item/generated"), "layer0", modLoc("item/buckets/" + SOFTENER_BUCKET.getId().getPath()));
        singleTexture(COAGULANT_BUCKET.getId().getPath(),   mcLoc("item/generated"), "layer0", modLoc("item/buckets/" + COAGULANT_BUCKET.getId().getPath()));


    }
}
