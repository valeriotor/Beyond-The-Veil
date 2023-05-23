package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public abstract class Dream {

    public static final Map<Memory, Dream> REGISTRY = new HashMap<>();

    public static final Dream WATER = new DreamWater();

    private final int priority;

    public Dream(Memory memory, int priority) {
        REGISTRY.put(memory, this);
        this.priority = priority;
    }

    public final int getPriority() {
        return priority;
    }

    public abstract boolean activate(Player p, Level l);
    public abstract boolean activatePlayer(Player caster, Player target, Level l);
    public abstract boolean activatePos(Player p, Level l, BlockPos pos);

}
