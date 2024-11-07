package com.valeriotor.beyondtheveil.client.util;

import com.valeriotor.beyondtheveil.entity.Talkable;
import net.minecraft.world.entity.player.Player;

public class ClientTalkable implements Talkable {


    private Player player;

    public ClientTalkable(Player player) {
        this.player = player;
    }
    @Override
    public Player getTalkingPlayer() {
        return player;
    }

    @Override
    public void setTalkingPlayer(Player player) {
        this.player = player;
    }
}
