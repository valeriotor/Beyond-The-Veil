package com.valeriotor.beyondtheveil.capability.util;

import com.valeriotor.beyondtheveil.letters.Exchange;
import com.valeriotor.beyondtheveil.letters.ExchangeRegistry;
import com.valeriotor.beyondtheveil.letters.ExchangeTemplate;
import com.valeriotor.beyondtheveil.letters.Letter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class LetterData {

    private final List<Exchange> exchanges = new ArrayList<>();
    private final List<Letter> receivedInOrder = new ArrayList<>();
    private final List<Letter> sentInOrder = new ArrayList<>();


    public boolean addExchange(Player player, ExchangeTemplate template) {
        if (template == null) {
            return false;
        }
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
        for (Iterator<Exchange> iterator = exchanges.iterator(); iterator.hasNext(); ) {
            Exchange exchange = iterator.next();
            if (exchange.getName().equals(exchangeName)) {
                Letter received = exchange.receiveLetter();
                if (received != null) {
                    receivedInOrder.add(received);
                }
                terminateExchange(iterator, exchange);
                break;
            }
        }
    }

    public void sendLetter(Player player, String exchangeName, List<Integer> chosenOptions) {
        for (Iterator<Exchange> iterator = exchanges.iterator(); iterator.hasNext(); ) {
            Exchange exchange = iterator.next();
            if (exchange.getName().equals(exchangeName)) {
                Letter sent = exchange.sendLetter(player, chosenOptions);
                if (sent != null) {
                    sentInOrder.add(sent);
                }
                terminateExchange(iterator, exchange);
                break;
            }
        }
    }

    private static void terminateExchange(Iterator<Exchange> iterator, Exchange exchange) {
        if (exchange.isFinished() && exchange.getTemplate().isRepeatable()) {
            iterator.remove();
        }
    }

    public List<Letter> getReceivedInOrder() {
        return receivedInOrder;
    }

    public List<Letter> getSentInOrder() {
        return sentInOrder;
    }

    public List<Exchange> getExchanges() {
        return exchanges;
    }

    public CompoundTag saveToNBT(CompoundTag compoundTag) {
        CompoundTag receivedLetters = new CompoundTag();
        CompoundTag sentLetters = new CompoundTag();
        CompoundTag exchanges1 = new CompoundTag();
        for (int i = 0; i < receivedInOrder.size(); i++) {
            receivedLetters.put(String.valueOf(i), receivedInOrder.get(i).saveToNBT(new CompoundTag()));
        }
        for (int i = 0; i < sentInOrder.size(); i++) {
            sentLetters.put(String.valueOf(i), sentInOrder.get(i).saveToNBT(new CompoundTag()));
        }
        for (int i = 0; i < exchanges.size(); i++) {
            exchanges1.put(String.valueOf(i), exchanges.get(i).saveToNBT(new CompoundTag()));
        }
        compoundTag.put("received", receivedLetters);
        compoundTag.put("sent", sentLetters);
        compoundTag.put("exchanges", exchanges1);
        return compoundTag;
    }

    public void loadFromNBT(CompoundTag compoundTag) {
        // NOTE: here the letters in exchanges and those in sent/receivedInOrder will no longer be the same objects, but that should be fine
        receivedInOrder.clear();
        sentInOrder.clear();
        exchanges.clear();
        CompoundTag received = compoundTag.getCompound("received");
        received.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::valueOf)).map(s -> Letter.fromNBT(received.getCompound(s))).filter(Objects::nonNull).forEach(receivedInOrder::add);
        CompoundTag sent = compoundTag.getCompound("sent");
        sent.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::valueOf)).map(s -> Letter.fromNBT(sent.getCompound(s))).filter(Objects::nonNull).forEach(sentInOrder::add);
        CompoundTag exchanges = compoundTag.getCompound("exchanges");
        exchanges.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::valueOf)).map(s -> Exchange.fromNBT(exchanges.getCompound(s))).filter(Objects::nonNull).forEach(this.exchanges::add);

    }

    public void copyToNewStore(LetterData newStore) {
        newStore.receivedInOrder.clear();
        newStore.sentInOrder.clear();
        newStore.exchanges.clear();
        newStore.receivedInOrder.addAll(receivedInOrder);
        newStore.sentInOrder.addAll(sentInOrder);
        newStore.exchanges.addAll(exchanges);
    }

}
