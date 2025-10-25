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

public class PlayerDataLib {

    // Longs
    public static final String WATERTPDEST = "watertpdest";
    public static final String VEIN_POS = "vein_pos";

    // Ints (Non-temporary)
    public static final Function<String, String> TIMES_DREAMT = s -> "times_dreamt_" + s;
    public static final String SLUGS = "slugs";
    public static final String FISH_CANOE = "canoe_fish";
    public static final String DEATH_X = "deathX";
    public static final String DEATH_Y = "deathY";
    public static final String DEATH_Z = "deathZ";
    public static final String SELECTED_DEITY = "selected_deity";
    public static final String SELECTED_POWER = "currentPower";
    public static final String SELECTED_BAUBLE = "currentBauble";
    public static final String PASSIVE_BAUBLE = "passiveBauble%d"; // This isn't used as a key by itself, but only with 0-6 added to the end e.g. passiveBauble3
    public static final String POWER_COOLDOWN = "powCooldown%d"; // This isn't used as a key by itself, but only with 0-3 added to the end e.g. powCooldown1
    public static final String BAUBLE_COOLDOWN = "bauCooldown%d"; // This isn't used as a key by itself, but only with 0-3 added to the end e.g. bauCooldown1
    public static final String DAGON_GOLD = "dagonGold";
    public static final String PARASITE_PROGRESS = "parasite_progress";
    public static final String SAPLINGS_SEEN = "saplings";
    public static final String NECRO_X = "NecroX";
    public static final String NECRO_Y = "NecroY";
    public static final String NECRO_FACTOR = "NecroFac";
    public static final String ELDER_GUARDIANS = "elderguards";
    public static final String TIMESCHAMBER = "timeschamber";
    public static final String CURSE = "curse";
    public static final String WATERTPDIM = "watertpdimension";
    public static final String INTROSPECTION = "introspection";
    public static final String BONEMEALUSED = "bmealused";
    public static final String PLANTDREAM = "pdream";
    public static final Function<String, String> ICTYA_BY_SIZE = s -> String.format("ictya-%s", s);
    public static final Function<String, String> ICTYA_USED_BY_SIZE = s -> String.format("ictya-used-%s", s);
    public static final Function<String, String> ARENA_BOSSES_KILLED_BY_NAME = s -> String.format("arena-killed-%s", s);
    public static final Function<String, String> ARENA_BOSSES_USED_BY_NAME = s -> String.format("arena-used-%s", s);
    public static final String ARENA_ADVICE = "arenaadvicereceived";
    public static final String STORED_WATER = "stored_water";
    public static final String ARCHE_BREATH = "arche_breath";
    public static final String CHANGE_MEMORY_PROGRESS = "change_memory_progress";
    public static final String REPAIR_MEMORY_PROGRESS = "repair_memory_progress";
    public static final String STILLNESS_MEMORY_PROGRESS = "stillness_memory_progress";

    // Ints (Temporary)
    public static final String TALK_COUNT = "tc%s"; // Not used as a key by itself, but only with a Dweller profession added to the end e.g. tcstockpiler
    public static final String MAPPER_PLAYER_INTERACT = "mpi"; // This isn't used as a key by itself, but only with an int added to the end e.g. mpi1
    public static final String IDOLBREAK = "idol_break";
    public static final String OPEN_JOURNAL_PAGE = "open_journal_page";

    // Booleans (Non-temporary)
    public static final String FISHQUEST = "fish_quest";
    public static final String THEBEGINNING = "thebeginning";
    public static final String DIDDREAM = "didDream";
    public static final String MADE_BOOKMARK = "made_bookmark";
    public static final String RITUALQUEST = "drowned";
    public static final String DAGONQUEST = "dagon_gold";
    public static final String DAGONQUEST2 = "dagon_elder";
    public static final String SEEKS_KNOWLEDGE = "seeks_knowledge";
    public static final String OLD_TRUTH = "old_truth";
    public static final String VOID = "void";
    public static final String WAIT = "wait";
    public static final String IDOLFOLLY = "idolfolly";
    public static final String DEATHTELEPORT = "deathtp";
    public static final String DRANK_ANY_MEMORY = "drankmemory";
    public static final String DRANK_MEMORY_DREAM = "drank_dream";
    public static final String HELD_MEMORY_DREAM = "held_dream";
    public static final String SLEPT_IN_CHAMBER = "slept_in_chamber";
    public static final String SPOKE_KEEPER = "spoke_keeper";
    public static final String UNLOCKED_HAMLET = "unlocked_hamlet";
    public static final String MUST_WEEP = "must_weep";
    public static final String MIRROR_WEPT = "mirror_wept";
    public static final String MUST_COMMUNE = "must_commune";
    public static final String COMMUNED = "communed";
    public static final String HAS_ABOMINATIONS = "has_abominations";
    public static final Function<Integer, String> DAGON_DIALOGUE = i -> String.format("dagonDialogue%d", i);
    public static final Function<String, String> ICTYA_BY_TYPE = s -> String.format("ictya-%s", s);
    public static final Function<Memory, String> DRANK_MEMORY = m -> String.format("drank_%s", m.name().toLowerCase());
    public static final Function<String, String> REMINISCED = s -> String.format("reminisced_%s", s);
    public static final Function<Memory, String> MADE_MEMORY = m -> String.format("crafted_%s", m.name().toLowerCase());
    public static final Function<String, String> FOUND_WAYPOINT = s -> "found_" + s;
    public static final Function<Fluid, String> DISCOVERED_FLUID = f -> "fluid_" + (ForgeRegistries.FLUID_TYPES.get().getKey(f.getFluidType()) == null ? "" : ForgeRegistries.FLUID_TYPES.get().getKey(f.getFluidType()).getPath());
    public static final Function<String, String> JOURNAL_REPORT = name -> "journal_report_" + name;
    public static final String RATIONALIZED = "rationalized";
    public static final String INCISED = "incised";
    public static final String EXTRACTED_HEART = "extracted_heart";
    public static final String EXTRACTED_SPINE = "extracted_spine";
    public static final String EXTRACTED_BONE_TIARA = "extracted_bone_tiara";
    public static final String ATE_SLUG = "ate_slug";
    public static final String FIRST_SKULL_OPERATION = "first_skull_operation";
    public static final String CULTIST_KILLED = "cultist_killed";
    public static final String RECEIVED_FISH = "received_fish";
    public static final String BAPTIZED = "baptized";
    public static final String HAD_CONTACT = "had_contact";
    public static final String SPOKE_GNAWING = "spoke_gnawing";
    public static final String SPOKE_OCEAN = "spoke_ocean";
    public static final String SPOKE_YOU = "spoke_you";
    public static final String RENAMED_NECRONOMICON = "renamed_necronomicon";
    public static final String KILLED_KEEPER = "killed_keeper";
    public static final String READY_FOR_BAPTISM = "ready_for_baptism";
    public static final String EMBRACED_CUSTOMS = "embraced_customs";
    public static final String USED_ANTIDOTE = "used_antidote";
    public static final String USED_BOTTLE = "used_bottle";
    public static final String FILLED_BOTTLE = "filled_bottle";
    public static final String GRASPED_WATER = "grasped_water";
    public static final String ATE_DELICACY = "ate_delicacy";
    public static final String USED_FARMING_TECHNIQUE = "used_farming_technique";
    public static final String EDITING_REPORT = "editing_report";


    // Booleans (Temporary)
    public static final String DREAMFOCUS = "dreamfocus";
    public static final String REMINISCING = "reminiscing";

    // Longs (Non-temporary)
    public static final String LASTDREAMTINDAY = "lastdreamday";
    public static final String LASTDREAMTINWORLD = "lastdreamworld";
    public static final String SACRIFICE_ALTAR = "sacrifice_altar";
    public static final String WAYPOINT_OCEAN = "waypoint_ocean";


    // Strings (Non-temporary)
    public static final Function<Integer, String> BOOKMARK = i -> String.format("bookmark%d", i);
    public static final String PREVIOUS_REPORT_NAME = "previous_report_name";


    // Used to clear client-side data in one fell swoop.
    public static final String ALL = "all";


    private static final Set<String> allowedKeys = new HashSet<>();

    static {
        Set<String> tempInts = new HashSet<>();
        allowedKeys.add(SELECTED_BAUBLE);
        for(int i = 0; i < 8; i++)
            allowedKeys.add(String.format(PASSIVE_BAUBLE, i));
        for (int i = 0; i < 16; i++) {
            allowedKeys.add(BOOKMARK.apply(i));
        }
        allowedKeys.add(SELECTED_POWER);
        allowedKeys.add(NECRO_X);
        allowedKeys.add(NECRO_Y);
        allowedKeys.add(NECRO_FACTOR);
        allowedKeys.add(MADE_BOOKMARK);
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
