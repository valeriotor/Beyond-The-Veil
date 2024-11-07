package com.valeriotor.beyondtheveil.entity;

import net.minecraft.world.entity.player.Player;

public interface Talkable {

    Player getTalkingPlayer();
    void setTalkingPlayer(Player player);

    default boolean isTalking() {
        return getTalkingPlayer() != null;
    }
}
