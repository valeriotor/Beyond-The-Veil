package com.valeriotor.beyondtheveil.lib;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class PlayerDataLib {

    // Longs
    public static final String WATERTPDEST = "watertpdest";

    // Ints (Non-temporary)
    public static final String TIMESDREAMT = "timesDreamt";
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

    // Ints (Temporary)
    public static final String TALK_COUNT = "tc%s"; // Not used as a key by itself, but only with a Dweller profession added to the end e.g. tcstockpiler
    public static final String MAPPER_PLAYER_INTERACT = "mpi"; // This isn't used as a key by itself, but only with an int added to the end e.g. mpi1
    public static final String IDOLBREAK = "idol_break";

    // Booleans (Non-temporary)
    public static final String FISHQUEST = "fish_quest";
    public static final String THEBEGINNING = "thebeginning";
    public static final String DIDDREAM = "didDream";
    public static final String MADE_BOOKMARK = "made_bookmark";
    public static final String RITUALQUEST = "drowned";
    public static final String DAGONQUEST = "dagon_gold";
    public static final String DAGONQUEST2 = "dagon_elder";
    public static final String ENDBATH = "endbath";
    public static final String SEEKSKNOWLEDGE = "seeksKnowledge";
    public static final String FOUND_HAMLET = "FindHamlet";
    public static final String TRANSFORMED = "transformed";
    public static final String OLDTRUTH = "old_truth";
    public static final String VOID = "void";
    public static final String WAIT = "wait";
    public static final String IDOLFOLLY = "idolfolly";
    public static final String DEATHTELEPORT = "deathtp";
    public static final Function<Integer, String> DAGON_DIALOGUE = i -> String.format("dagonDialogue%d", i);
    public static final Function<String, String> ICTYA_BY_TYPE = s -> String.format("ictya-%s", s);

    // Booleans (Temporary)
    public static final String DREAMFOCUS = "dreamfocus";

    // Longs (Non-temporary)
    public static final String LASTDREAMTINDAY = "lastdreamday";
    public static final String LASTDREAMTINWORLD = "lastdreamworld";

    // Strings (Non-temporary)
    public static final Function<Integer, String> BOOKMARK = i -> String.format("bookmark%d", i);


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
