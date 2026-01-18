package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.util.LetterDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.valeriotor.beyondtheveil.capability.util.LetterDataProvider.LETTER_DATA;

public enum PersistentPlayerTimer {
    LETTER((p, t) -> {
        p.getCapability(LetterDataProvider.LETTER_DATA).ifPresent(c -> {
            c.receiveLetter(p, t.getAdditionalData("exchange"));
            Messages.sendToPlayer(GenericToClientPacket.syncLetterData(c.saveToNBT(new CompoundTag())), (ServerPlayer) p);
        });
    }),
    DREAMT(List.of(), List.of((p, t) -> {
        p.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(c -> {
            c.setInteger(PlayerDataLib.TIMES_DREAMT.apply(t.getId()), 0, false);
        });
    }), List.of(), List.of((p, t) -> {
        String time = t.getAdditionalData("time");
        if (StringUtils.isNumeric(time)) {
            int firstTime = Integer.parseInt(time);
            return p.level().getDayTime() < firstTime;
        }
        return false;
    })),
    BLIND_COMPLETELY(List.of((player, timer) -> {
        if (player instanceof ServerPlayer sp && timer.getRemainingTime() % 20 == 0) {
            Messages.sendToPlayer(GenericToClientPacket.blindCompletely(), sp);
        }
    }), List.of(), List.of(), List.of());

    private final List<BiConsumer<Player, PlayerTimer>> continuousActions;
    private final List<BiConsumer<Player, PlayerTimer>> finalActions;
    private final List<BiPredicate<Player, PlayerTimer>> interrupts;
    private final List<BiPredicate<Player, PlayerTimer>> earlyFinish;

    PersistentPlayerTimer(BiConsumer<Player, PlayerTimer> finalAction) {
        this(List.of(), List.of(finalAction), List.of(), List.of());
    }

    PersistentPlayerTimer(List<BiConsumer<Player, PlayerTimer>> continuousActions, List<BiConsumer<Player, PlayerTimer>> finalActions, List<BiPredicate<Player, PlayerTimer>> interrupts, List<BiPredicate<Player, PlayerTimer>> earlyFinish) {
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

    public List<BiPredicate<Player, PlayerTimer>> getInterrupts() {
        return interrupts;
    }

    public List<BiPredicate<Player, PlayerTimer>> getEarlyFinish() {
        return earlyFinish;
    }
}
