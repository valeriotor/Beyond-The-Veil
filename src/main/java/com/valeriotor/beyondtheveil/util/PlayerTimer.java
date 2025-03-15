package com.valeriotor.beyondtheveil.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlayerTimer {

    private final String id;
    private int timer;
    private final List<BiConsumer<Player, PlayerTimer>> continuousActions;
    private final List<BiConsumer<Player, PlayerTimer>> finalActions;
    private final List<Predicate<Player>> interrupts;
    private final List<Predicate<Player>> earlyFinish;
    private final PersistentPlayerTimer persistence;
    private final Map<String, String> additionalData;

    public PlayerTimer(Builder builder) {
        this.id = builder.id;
        this.timer = builder.timer;
        this.continuousActions = builder.continuousActions;
        this.finalActions = builder.finalActions;
        this.interrupts = builder.interrupts;
        this.earlyFinish = builder.earlyFinish;
        this.persistence = null;
        this.additionalData = builder.additionalData;
    }

    public PlayerTimer(int timer, String id, PersistentPlayerTimer persistence, Map<String, String> additionalData) {
        this.timer = timer;
        this.id = id;
        this.continuousActions = persistence.getContinuousActions();
        this.finalActions = persistence.getFinalActions();
        this.interrupts = persistence.getInterrupts();
        this.earlyFinish = persistence.getEarlyFinish();
        this.persistence = persistence;
        this.additionalData = additionalData;
    }

    public PlayerTimer(CompoundTag tag) {
        this(tag.getInt("timer"), tag.getString("id"), PersistentPlayerTimer.valueOf(tag.getString("persistence")), mapFromNBT(tag.getCompound("additionalData")));
    }

    private static Map<String, String> mapFromNBT(CompoundTag tag) {
        Map<String, String> map = new HashMap<>();
        for (String key : tag.getAllKeys()) {
            map.put(key, tag.getString(key));
        }
        return map;
    }

    private static CompoundTag mapToNBT(Map<String, String> map) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            tag.putString(entry.getKey(), entry.getValue());
        }
        return tag;
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

    public String getAdditionalData(String key) {
        return additionalData.get(key);
    }

    public boolean update(Player player) {
        if (isDone()) {
            return true;
        }
        for (Predicate<Player> finish : earlyFinish) {
            if (finish.test(player)) {
                for (BiConsumer<Player, PlayerTimer> finalAction : finalActions) {
                    finalAction.accept(player, this);
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
            for (BiConsumer<Player, PlayerTimer> continuousAction : continuousActions) {
                continuousAction.accept(player, this);
            }
        } else {
            for (BiConsumer<Player, PlayerTimer> finalAction : finalActions) {
                finalAction.accept(player, this);
            }
        }
        timer--;
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
        tag.put("additionalData", mapToNBT(additionalData));
        return tag;
    }

    public String getId() {
        return id;
    }

    public int getRemainingTime() {
        return timer;
    }

    public boolean isDone() {
        return timer < 0;
    }

    public static class Builder {
        private final String id;
        private final int timer;
        private final List<BiConsumer<Player, PlayerTimer>> continuousActions = new ArrayList<>();
        private final List<BiConsumer<Player, PlayerTimer>> finalActions = new ArrayList<>();
        private final List<Predicate<Player>> interrupts = new ArrayList<>();
        private final List<Predicate<Player>> earlyFinish = new ArrayList<>();
        private final Map<String, String> additionalData = new HashMap<>();

        public Builder(String id, int timer) {
            this.id = id;
            this.timer = timer;
        }

        public Builder addContinuousAction(BiConsumer<Player, PlayerTimer> action) {
            continuousActions.add(action);
            return this;
        }

        public Builder addFinalActions(BiConsumer<Player, PlayerTimer> action) {
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

        public Builder addAdditionalData(String key, String value) {
            additionalData.put(key, value);
            return this;
        }

        public PlayerTimer toTimer() {
            return new PlayerTimer(this);
        }

    }

}
