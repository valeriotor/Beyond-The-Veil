package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.event.MemoryUnlockEvents;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.BTVTags;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import com.valeriotor.beyondtheveil.world.saved.PlayerSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class DreamRegistry {
    static final Map<String, Dream> REGISTRY = new HashMap<>();
    static final Map<String, Supplier<Reminiscence>> REMINISCENCE_REGISTRY = new HashMap<>();

    public static final Dream VOID = new DreamVoid();
    public static final Dream WATER = new DreamWaypoint(Memory.WATER, (sl, pos, p) -> sl.dimension() == BTVDimensions.ARCHE_LEVEL ? sl.findNearestMapStructure(BTVTags.DEEP_CITY, pos, 100, false) : sl.findNearestMapStructure(StructureTags.ON_OCEAN_EXPLORER_MAPS, pos, 100, false), 0x7F16FF);
    public static final Dream DEATH = new DreamWaypoint(Memory.DEATH, (sl, pos, p) -> PlayerSavedData.getInstance(sl.getServer().overworld()).lastDeathFor(p), 0x7F16FF);
    public static final Dream RESPAWN = new DreamWaypoint(Memory.DEATH, true, (sl, pos, p) -> PlayerSavedData.getInstance(sl.getServer().overworld()).lastRespawnFor(p), 0x7F16FF);
    public static final Dream ELDRITCH = new DreamSound(Memory.WATER, BTVSounds.WATER_DREAM.get(), true);
    public static final Dream SENTIENCE = new DreamWaypoint(Memory.SENTIENCE, (sl, pos, p) -> sl.findNearestMapStructure(StructureTags.VILLAGE, pos, 100, false), 0x7F006E); // Todo: make player sigil version locate player
    public static final Dream SENTIENCE_VOID = new DreamWaypoint(Memory.SENTIENCE, true, (sl, pos, p) -> sl.findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, pos, 100, false), 0x7F006E);
    public static final Dream DARKNESS = new DreamWaypoint(Memory.DARKNESS, (sl, pos, p) -> sl.findNearestMapStructure(BTVTags.HAMLET, pos, 500, false), 0xFFFFFF);
    //public static final Dream WATER_TEST = new DreamWaypoint(Memory.WATER, true, (sl, pos) -> sl.findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, pos, 100, false), 0x7F16FF);
    public static final Dream METAL = new DreamUnderground(Memory.METAL, 5, Set.of(Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.COPPER_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_COPPER_ORE));
    public static final Dream CRYSTAL = new DreamUnderground(Memory.CRYSTAL, 5, MemoryUnlockEvents.CRYSTAL_BLOCKS);
    public static final Dream POWER = new DreamEffect(Memory.POWER);
    public static final Dream STILLNESS = new DreamEffect(Memory.STILLNESS);
    public static final Dream CHANGE = new DreamEffect(Memory.CHANGE);
    public static final Dream ANIMAL = new DreamAnimal();
    public static final Dream REPAIR = new DreamRepair();
    public static final Dream BEHEADING = new DreamBeheading();

    static {
        DreamRegistry.REMINISCENCE_REGISTRY.put("none", Reminiscence.EmptyReminiscence::new);
        DreamRegistry.REMINISCENCE_REGISTRY.put("darkness_player", Reminiscence.TextReminiscence::new);
        DreamRegistry.REMINISCENCE_REGISTRY.put("water_player", Reminiscence.TextReminiscence::new);
    }

    public static Dream getDreamFromMemory(Memory m, boolean isVoid) {
        String key = m.getDataName(isVoid);
        if (REGISTRY.containsKey(key)) {
            return REGISTRY.get(key);
        } else {
            return REGISTRY.get(m.getDataName());
        }
    }

    /** Used to instance the reminiscence object after serialization. Some dreams have different reminiscences for void and non-void version, the former having keys prepended by "void_".
     *  Other dreams, such as underground, use the same reminiscence in both cases, but they should still be distinguished so they can both be displayed. The void version is still prepended
     *  by "void", but is defaulted to the normal version.
     */
    public static Reminiscence getReminiscence(String key) {
        Supplier<Reminiscence> supplier = null;
        if (REMINISCENCE_REGISTRY.containsKey(key)) {
            supplier = REMINISCENCE_REGISTRY.get(key);
        } else {
            String[] s = key.split("_");
            if (s.length > 1) {
                supplier = REMINISCENCE_REGISTRY.get(s[1]);
            }
        }
        if (supplier != null) {
            return supplier.get();
        }
        return null;
    }
}
