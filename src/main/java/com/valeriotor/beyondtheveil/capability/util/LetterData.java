package com.valeriotor.beyondtheveil.capability.util;

import com.valeriotor.beyondtheveil.letters.Exchange;
import com.valeriotor.beyondtheveil.letters.ExchangeTemplate;
import com.valeriotor.beyondtheveil.letters.Letter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class LetterData {

    private final List<Exchange> exchanges = new ArrayList<>();
    private final List<Letter> receivedInOrder = new ArrayList<>();
    private final List<Letter> sentInOrder = new ArrayList<>();


    public boolean addExchange(Player player, ExchangeTemplate template) {
        for (Exchange exchange : exchanges) {
            if (exchange.getName().equals(template.getName())) {
                return false;
            }
        }
        Exchange exchange = new Exchange(template);
        if (exchange.canReceiveLetter()) {
            exchange.scheduleMail(player);
        }
        exchanges.add(exchange);
        return true;
    }

    public void receiveLetter(String exchangeName) {
        for (Exchange exchange : exchanges) {
            if (exchange.getName().equals(exchangeName)) {
                Letter received = exchange.receiveLetter();
                if (received != null) {
                    receivedInOrder.add(received);
                }
                break;
            }
        }
    }

    public void sendLetter(Player player, String exchangeName, List<Integer> chosenOptions) {
        for (Exchange exchange : exchanges) {
            if (exchange.getName().equals(exchangeName)) {
                Letter sent = exchange.sendLetter(player, chosenOptions);
                if (sent != null) {
                    sentInOrder.add(sent);
                }
                break;
            }
        }
    }

    public void saveToNBT(CompoundTag compoundTag) {
        // TODO
    }

    public void loadFromNBT(CompoundTag compoundTag) {
        // NOTE: here the letters in exchanges and those in sent/receivedInOrder will no longer be the same objects, but that should be fine
        // TODO
    }

    public void copyToNewStore(PlayerTimerData newStore) {
        // TODO
    }

}
