package com.valeriotor.BTV.lib;

public class PlayerDataLib {
	
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
	public static final String DAGON_DIALOGUE = "dagonDialogue";
	public static final String DAGON_GOLD = "dagonGold";
	public static final String PARASITE_PROGRESS = "parasite_progress";
	
	// Ints (Temporary)
	public static final String TALK_COUNT = "tc%s"; // Not used as a key by itself, but only with a Dweller profession added to the end e.g. tcstockpiler
	public static final String MAPPER_PLAYER_INTERACT = "mpi"; // This isn't used as a key by itself, but only with an int added to the end e.g. mpi1
	
	// Strings (Non-temporary)
	public static final String FISHQUEST = "fish_quest";
	public static final String RITUALQUEST = "drowned";
	public static final String DAGONQUEST = "dagon_gold";
	public static final String SEEKSKNOWLEDGE = "seeksKnowledge";
	public static final String FOUND_HAMLET = "found_hamlet";
	public static final String TRANSFORMED = "transformed";
	public static final String OLDTRUTH = "old_truth";
	
	// Strings (Temporary)
	
	// Used to clear client-side data in one fell swoop.
	public static final String ALL = "all";
	
}
