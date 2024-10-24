package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class Dream {

    public static final Map<Memory, Dream> REGISTRY = new HashMap<>();
    public static final Map<Memory, Supplier<Reminiscence>> REMINISCENCE_REGISTRY = new EnumMap<>(Memory.class);

    static {
        REMINISCENCE_REGISTRY.put(Memory.NULL, Reminiscence.EmptyReminiscence::new);
    }


    public static final Dream WATER = new DreamWater();
    public static final Dream METAL = new DreamUnderground(Memory.METAL, 5, Set.of(Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.COPPER_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_COPPER_ORE));
    public static final Dream CRYSTAL = new DreamUnderground(Memory.CRYSTAL, 5, Set.of(Blocks.AMETHYST_BLOCK, Blocks.AMETHYST_CLUSTER, Blocks.BUDDING_AMETHYST, Blocks.LARGE_AMETHYST_BUD, Blocks.MEDIUM_AMETHYST_BUD, Blocks.SMALL_AMETHYST_BUD, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.LAPIS_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_LAPIS_ORE));

    private final int priority;
    protected final Memory memory;

    public Dream(Memory memory, int priority) {
        this(memory, priority, null);
    }

    public Dream(Memory memory, int priority, Supplier<Reminiscence> reminiscenceSupplier) {
        this.memory = memory;
        REGISTRY.put(memory, this);
        this.priority = priority;
        if (reminiscenceSupplier != null) {
            REMINISCENCE_REGISTRY.put(memory, reminiscenceSupplier);
        }
    }

    public final int getPriority() {
        return priority;
    }

    public abstract boolean activate(Player p, Level l);
    public abstract boolean activatePlayer(Player caster, Player target, Level l);
    public abstract boolean activatePos(Player p, Level l, BlockPos pos);

}
