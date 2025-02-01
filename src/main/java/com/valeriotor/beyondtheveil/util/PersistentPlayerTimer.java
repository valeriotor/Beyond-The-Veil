package com.valeriotor.beyondtheveil.util;

import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public enum PersistentPlayerTimer {
    LETTER(List.of(), List.of(), List.of(), List.of()),;

    private final List<Consumer<Player>> continuousActions;
    private final List<Consumer<Player>> finalActions;
    private final List<Predicate<Player>> interrupts;
    private final List<Predicate<Player>> earlyFinish;

    PersistentPlayerTimer(List<Consumer<Player>> continuousActions, List<Consumer<Player>> finalActions, List<Predicate<Player>> interrupts, List<Predicate<Player>> earlyFinish) {
        this.continuousActions = continuousActions;
        this.finalActions = finalActions;
        this.interrupts = interrupts;
        this.earlyFinish = earlyFinish;
    }

    public List<Consumer<Player>> getContinuousActions() {
        return continuousActions;
    }

    public List<Consumer<Player>> getFinalActions() {
        return finalActions;
    }

    public List<Predicate<Player>> getInterrupts() {
        return interrupts;
    }

    public List<Predicate<Player>> getEarlyFinish() {
        return earlyFinish;
    }
}
