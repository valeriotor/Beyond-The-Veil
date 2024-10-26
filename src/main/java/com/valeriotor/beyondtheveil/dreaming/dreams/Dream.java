package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public abstract class Dream {




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
        DreamRegistry.REGISTRY.put(key, this);
        this.priority = priority;
        if (reminiscenceSupplier != null) {
            DreamRegistry.REMINISCENCE_REGISTRY.put(key, reminiscenceSupplier);
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
