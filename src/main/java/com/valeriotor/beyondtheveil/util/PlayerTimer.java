package com.valeriotor.beyondtheveil.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlayerTimer {

    private final String id;
    private int timer;
    private final List<Consumer<Player>> continuousActions;
    private final List<Consumer<Player>> finalActions;
    private final List<Predicate<Player>> interrupts;
    private final List<Predicate<Player>> earlyFinish;
    private final PersistentPlayerTimer persistence;

    public PlayerTimer(Builder builder) {
        this.id = builder.id;
        this.timer = builder.timer;
        this.continuousActions = builder.continuousActions;
        this.finalActions = builder.finalActions;
        this.interrupts = builder.interrupts;
        this.earlyFinish = builder.earlyFinish;
        this.persistence = null;
    }

    public PlayerTimer(int timer, String id, PersistentPlayerTimer persistence) {
        this.timer = timer;
        this.id = id;
        this.continuousActions = persistence.getContinuousActions();
        this.finalActions = persistence.getFinalActions();
        this.interrupts = persistence.getInterrupts();
        this.earlyFinish = persistence.getEarlyFinish();
        this.persistence = persistence;
    }

    public PlayerTimer(CompoundTag tag) {
        this(tag.getInt("timer"), tag.getString("id"), PersistentPlayerTimer.valueOf(tag.getString("persistence")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerTimer that = (PlayerTimer) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean update(Player player) {
        if (isDone()) {
            return true;
        }
        for (Predicate<Player> finish : earlyFinish) {
            if (finish.test(player)) {
                for (Consumer<Player> finalAction : finalActions) {
                    finalAction.accept(player);
                }
                timer = 0;
                return true;
            }
        }
        for (Predicate<Player> interrupt : interrupts) {
            if (interrupt.test(player)) {
                timer = 0;
                return true;
            }
        }
        if (timer > 0) {
            for (Consumer<Player> continuousAction : continuousActions) {
                continuousAction.accept(player);
            }
        } else {
            for (Consumer<Player> finalAction : finalActions) {
                finalAction.accept(player);
            }
        }
        return false;
    }

    public CompoundTag writeToNBT() {
        if (persistence == null) {
            return null;
        }
        CompoundTag tag = new CompoundTag();
        tag.putInt("timer", timer);
        tag.putString("id", id);
        tag.putString("persistence", persistence.name());
        return tag;
    }

    public boolean isDone() {
        return timer <= 0;
    }

    public static class Builder {
        private final String id;
        private final int timer;
        private final List<Consumer<Player>> continuousActions = new ArrayList<>();
        private final List<Consumer<Player>> finalActions = new ArrayList<>();
        private final List<Predicate<Player>> interrupts = new ArrayList<>();
        private final List<Predicate<Player>> earlyFinish = new ArrayList<>();

        public Builder(String id, int timer) {
            this.id = id;
            this.timer = timer;
        }

        public Builder addContinuousAction(Consumer<Player> action) {
            continuousActions.add(action);
            return this;
        }

        public Builder addFinalActions(Consumer<Player> action) {
            finalActions.add(action);
            return this;
        }

        public Builder addInterrupts(Predicate<Player> predicate) {
            interrupts.add(predicate);
            return this;
        }

        public Builder addEarlyFinish(Predicate<Player> predicate) {
            earlyFinish.add(predicate);
            return this;
        }

        public PlayerTimer toTimer() {
            return new PlayerTimer(this);
        }

    }

}
