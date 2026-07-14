package com.valeriotor.beyondtheveil.lib;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public enum PlayerDataLib {
    // longs
    vein_pos,
    // ints (non temporary)
    slugs,
    currentPower,
    currentBauble,
    necro_x,
    necro_y,
    necro_fac,
    stored_water,
    arche_breath,
    change_memory_progress,
    repair_memory_progress,
    stillness_memory_progress,
    //ints temporary
    open_journal_page,
    // FLAGS (NON TEMPORARY)
    entered_arche,
    spoke_to_dagon,
    received_cult_letter,
    created_weeper,
    created_fletum,
    asked_thesis,
    received_thesis,
    performed_ritual,
    built_well,
    obtained_undead,
    spawned_undead,
    received_surgeon,
    placed_surgeon,
    grew_surgeon,
    thebeginning,
    didDream,
    made_bookmark,
    void_,
    drankmemory,
    drank_dream,
    held_dream,
    slept_in_chamber,
    spoke_keeper,
    unlocked_hamlet,
    must_commune,
    communed,
    has_abominations,
    rationalized,
    incised,
    extracted_heart,
    extracted_spine,
    extracted_bone_tiara,
    ate_slug,
    first_skull_operation,
    cultist_killed,
    received_fish,
    baptized,
    had_contact,
    spoke_gnawing,
    spoke_you,
    renamed_necronomicon,
    embraced_customs,
    used_antidote,
    used_bottle,
    filled_bottle,
    grasped_water,
    ate_delicacy,
    used_farming_technique,
    editing_report,
    reminiscing,
    sacrifice_altar,
    waypoint_ocean,
    met_mirror,
    did_binding;

    // Longs
    public static final String WATERTPDEST = "watertpdest";
    public static final String VEIN_POS = "vein_pos";

    // Ints (non-temporary)
    public static final Function<String, String> TIMES_DREAMT = s -> "times_dreamt_" + s;

    // flags (non-temporary)
    public static final Function<Memory, String> DRANK_MEMORY = m -> String.format("drank_%s", m.name().toLowerCase());
    public static final Function<String, String> REMINISCED = s -> String.format("reminisced_%s", s);
    public static final Function<Memory, String> MADE_MEMORY = m -> String.format("crafted_%s", m.name().toLowerCase());
    public static final Function<String, String> FOUND_WAYPOINT = s -> "found_" + s;
    public static final Function<String, String> GEAR_BENCH_CRAFT = s -> "crafted_" + s;
    public static final Function<Fluid, String> DISCOVERED_FLUID = f -> "fluid_" + (ForgeRegistries.FLUID_TYPES.get().getKey(f.getFluidType()) == null ? "" : ForgeRegistries.FLUID_TYPES.get().getKey(f.getFluidType()).getPath());
    public static final Function<String, String> JOURNAL_REPORT = name -> "journal_report_" + name;


    // Strings (Non-temporary)
    public static final Function<Integer, String> BOOKMARK = i -> String.format("bookmark%d", i);
    public static final String PREVIOUS_REPORT_NAME = "previous_report_name";


    // Used to clear client-side data in one fell swoop.
    public static final String ALL = "all";


    private static final Set<String> allowedKeys = new HashSet<>();

    static {
        Set<String> tempInts = new HashSet<>();
        allowedKeys.add(necro_x.name());
        allowedKeys.add(necro_y.name());
        allowedKeys.add(necro_fac.name());
        allowedKeys.add(made_bookmark.name());
        allowedKeys.add("eldritchDream");
        allowedKeys.add("LHKeeper");
        allowedKeys.add("carpenter");
        allowedKeys.add("lhbaptism");

        // TODO verify these as you go
    }

    public static PlayerData getCap(Player p) {
        return p.getCapability(PlayerDataProvider.PLAYER_DATA).orElse(PlayerData.DUMMY);
    }

    public static boolean isKeyFromClientAllowed(String key) {
        return allowedKeys.contains(key);
    }

}
