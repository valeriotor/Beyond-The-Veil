package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class Dream {

    private static final Map<String, Dream> REGISTRY = new HashMap<>();
    private static final Map<String, Supplier<Reminiscence>> REMINISCENCE_REGISTRY = new HashMap<>();

    public static Dream getDreamFromMemory(Memory m, boolean isVoid) {
        String key = (isVoid ? "void_" : "") + m.getDataName();
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
            supplier = REMINISCENCE_REGISTRY.get(key.split("_")[1]);
        }
        if (supplier != null) {
            return supplier.get();
        }
        return null;
    }

    static {
        REMINISCENCE_REGISTRY.put("none", Reminiscence.EmptyReminiscence::new);
    }



    public static final Dream VOID = new DreamVoid();
    public static final Dream WATER = new DreamWaypoint(Memory.WATER, (sl, pos) -> sl.findNearestMapStructure(StructureTags.ON_OCEAN_EXPLORER_MAPS, pos, 100, false), 0x7F16FF);
    //public static final Dream WATER_TEST = new DreamWaypoint(Memory.WATER, true, (sl, pos) -> sl.findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, pos, 100, false), 0x7F16FF);
    public static final Dream METAL = new DreamUnderground(Memory.METAL, 5, Set.of(Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.COPPER_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_COPPER_ORE));
    public static final Dream CRYSTAL = new DreamUnderground(Memory.CRYSTAL, 5, Set.of(Blocks.AMETHYST_BLOCK, Blocks.AMETHYST_CLUSTER, Blocks.BUDDING_AMETHYST, Blocks.LARGE_AMETHYST_BUD, Blocks.MEDIUM_AMETHYST_BUD, Blocks.SMALL_AMETHYST_BUD, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.LAPIS_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_LAPIS_ORE));

    private final int priority;
    protected final Memory memory;
    protected final boolean isVoid;

    public Dream(Memory memory, int priority, Supplier<Reminiscence> reminiscenceSupplier) {
        this(memory, priority, reminiscenceSupplier, false);
    }

    public Dream(Memory memory, int priority, Supplier<Reminiscence> reminiscenceSupplier, boolean isVoid) {
        this((isVoid ? "void_" : "") + memory.getDataName(), memory, priority, reminiscenceSupplier, isVoid);
    }

    public Dream(String key, Memory memory, int priority, Supplier<Reminiscence> reminiscenceSupplier, boolean isVoid) {
        this.memory = memory;
        REGISTRY.put(key, this);
        this.priority = priority;
        if (reminiscenceSupplier != null) {
            REMINISCENCE_REGISTRY.put(key, reminiscenceSupplier);
        }
        this.isVoid = isVoid;
    }


    public final int getPriority() {
        return priority;
    }

    public abstract boolean activate(Player p, Level l);
    public abstract boolean activatePlayer(Player caster, Player target, Level l);
    public abstract boolean activatePos(Player p, Level l, BlockPos pos);

}
