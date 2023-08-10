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


    public static final Dream WATER = new DreamWater();
    public static final Dream METAL = new DreamUnderground(Memory.METAL, 5, Set.of(Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.COPPER_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_COPPER_ORE));

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
