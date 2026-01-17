package com.valeriotor.beyondtheveil.capability.util;

import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.capability.DialogueDataProvider;
import com.valeriotor.beyondtheveil.event.ResearchEvents;
import com.valeriotor.beyondtheveil.letters.Exchange;
import com.valeriotor.beyondtheveil.letters.ExchangeTemplate;
import com.valeriotor.beyondtheveil.letters.Letter;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class LetterData {
    // IMPORTANT: THERE MUST ALWAYS BE AT MOST ONE EXCHANGE PER TEMPLATE
    public static LetterData for_(Player player) {
        return player.getCapability(LetterDataProvider.LETTER_DATA).orElse(DUMMY);
    }

    private static final LetterData DUMMY = new Dummy();
    private final List<Exchange> exchanges = new ArrayList<>();
    private final List<Letter> receivedInOrder = new ArrayList<>();
    private final List<Letter> sentInOrder = new ArrayList<>();
    private final Map<ExchangeTemplate.LetterTemplate, Integer> versions = new HashMap<>();


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

    public void removeUnstartedExchange(Player player, String templateName) {
        Iterator<Exchange> iterator = exchanges.iterator();
        while (iterator.hasNext()) {
            Exchange exchange = iterator.next();
            if (Objects.equals(exchange.getName(), templateName)) {
                if (exchange.canSendLetter() && exchange.noLettersSent()) {
                    iterator.remove();
                    return;
                }
            }
        }
    }

    public void receiveLetter(String exchangeName) {
        for (Iterator<Exchange> iterator = exchanges.iterator(); iterator.hasNext(); ) {
            Exchange exchange = iterator.next();
            if (exchange.getName().equals(exchangeName)) {
                // relies on the fact that only one exchange per template can exist at a given time
                Letter received = exchange.receiveLetter(versions);
                if (received != null) {
                    receivedInOrder.add(received);
                    versions.put(received.getTemplate(), 1 + versions.getOrDefault(received.getTemplate(), 0));
                }
                terminateExchange(iterator, exchange);
                break;
            }
        }
    }

    public void sendLetter(Player player, ExchangeTemplate template, List<Integer> chosenOptions, boolean clientSide) {
        for (Iterator<Exchange> iterator = exchanges.iterator(); iterator.hasNext(); ) {
            Exchange exchange = iterator.next();
            if (exchange.getTemplate() == template) {
                // relies on the fact that only one exchange per template can exist at a given time
                if (clientSide || exchange.hasItems(player)) {
                    Letter sent = exchange.sendLetter(player, chosenOptions, versions, clientSide);
                    if (sent != null) {
                        exchange.takeItems(player);
                        if(sent.getTemplate().getIndex() > 0) {
                            for (Letter received : receivedInOrder) {
                                if (received.matches(sent.getTemplate().getParent(), sent.getTemplate().getIndex() - 1, sent.getVersion())) {
                                    received.setCanReply(false);
                                    break;
                                }
                            }
                        }
                        sentInOrder.add(sent);
                        versions.put(sent.getTemplate(), 1 + versions.getOrDefault(sent.getTemplate(), 0));
                        ResearchEvents.sendLetterEvents(player, exchange);
                    }
                    terminateExchange(iterator, exchange);
                }
                break;
            }
        }
    }

    public void redeemItems(Player player, ExchangeTemplate template, int index, int version, boolean giveItems) {
        for (Iterator<Exchange> iterator = exchanges.iterator(); iterator.hasNext(); ) {
            Exchange exchange = iterator.next();
            if (exchange.getTemplate() == template) {
                exchange.markRedeemed(player);
                break;
            }
        }
        Letter previous = null;
        for (Letter letter : sentInOrder) {
            if (letter.matches(template, index - 1, version)) {
                previous = letter;
                break;
            }
        }
        for (Letter letter : receivedInOrder) {
            if (letter.matches(template, index, version)) {
                letter.redeem(player, previous, true);
                break;
            }
        }

    }

    public void openLetter(ServerPlayer player, ExchangeTemplate template, int index, int version) {
        for (Letter letter : receivedInOrder) {
            if (letter.matches(template, index, version)) {
                letter.setOpened(true);
                letter.getTemplate().getUnlockedData().forEach(s -> DataUtil.setBooleanOnServerAndSync(player, s, true, false));
                letter.getTemplate().getUnlockedExchanges().forEach(s -> DataUtil.addExchange(player, s));
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

        versions.clear();
        for (Letter letter : receivedInOrder) {
            versions.put(letter.getTemplate(), 1 + versions.getOrDefault(letter.getTemplate(), 0));
        }
        for (Letter letter : sentInOrder) {
            versions.put(letter.getTemplate(), 1 + versions.getOrDefault(letter.getTemplate(), 0));
        }

    }

    public void copyToNewStore(LetterData newStore) {
        newStore.receivedInOrder.clear();
        newStore.sentInOrder.clear();
        newStore.exchanges.clear();
        newStore.receivedInOrder.addAll(receivedInOrder);
        newStore.sentInOrder.addAll(sentInOrder);
        newStore.exchanges.addAll(exchanges);
    }

    private static class Dummy extends LetterData {
        @Override public boolean addExchange(Player player, ExchangeTemplate template) {return false;}

        @Override public void sendLetter(Player player, ExchangeTemplate template, List<Integer> chosenOptions, boolean clientSide) {}

        @Override public void receiveLetter(String exchangeName) {}

        @Override public void redeemItems(Player player, ExchangeTemplate template, int index, int version, boolean giveItems) {}

        @Override public void openLetter(ServerPlayer player, ExchangeTemplate template, int index, int version) {}
    }

}
