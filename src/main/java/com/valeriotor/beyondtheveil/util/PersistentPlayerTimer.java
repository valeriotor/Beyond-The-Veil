package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.capability.util.LetterDataProvider;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public enum PersistentPlayerTimer {
    LETTER((p, t) -> {
        p.getCapability(LetterDataProvider.LETTER_DATA).ifPresent(c -> {
            c.receiveLetter(t.getAdditionalData("exchange"));
        });
    }),;

    private final List<BiConsumer<Player, PlayerTimer>> continuousActions;
    private final List<BiConsumer<Player, PlayerTimer>> finalActions;
    private final List<Predicate<Player>> interrupts;
    private final List<Predicate<Player>> earlyFinish;

    PersistentPlayerTimer(BiConsumer<Player, PlayerTimer> finalAction) {
        this(List.of(), List.of(finalAction), List.of(), List.of());
    }

    PersistentPlayerTimer(List<BiConsumer<Player, PlayerTimer>> continuousActions, List<BiConsumer<Player, PlayerTimer>> finalActions, List<Predicate<Player>> interrupts, List<Predicate<Player>> earlyFinish) {
        this.continuousActions = continuousActions;
        this.finalActions = finalActions;
        this.interrupts = interrupts;
        this.earlyFinish = earlyFinish;
    }

    public List<BiConsumer<Player, PlayerTimer>> getContinuousActions() {
        return continuousActions;
    }

    public List<BiConsumer<Player, PlayerTimer>> getFinalActions() {
        return finalActions;
    }

    public List<Predicate<Player>> getInterrupts() {
        return interrupts;
    }

    public List<Predicate<Player>> getEarlyFinish() {
        return earlyFinish;
    }
}
