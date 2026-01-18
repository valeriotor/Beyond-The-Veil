package com.valeriotor.beyondtheveil.capability.util;

import com.valeriotor.beyondtheveil.event.ResearchEvents;
import com.valeriotor.beyondtheveil.letters.Exchange;
import com.valeriotor.beyondtheveil.letters.ExchangeRegistry;
import com.valeriotor.beyondtheveil.letters.ExchangeTemplate;
import com.valeriotor.beyondtheveil.letters.Letter;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;
import java.util.Map.Entry;

public class LetterData {
    // IMPORTANT: THERE MUST ALWAYS BE AT MOST ONE EXCHANGE PER TEMPLATE
    public static LetterData for_(Player player) {
        return player.getCapability(LetterDataProvider.LETTER_DATA).orElse(DUMMY);
    }

    private static final LetterData DUMMY = new Dummy();
    private final Map<ExchangeTemplate, Exchange> activeExchanges = new HashMap<>(); // an exchange remains active until the last letter is opened and any items redeemed
    private final Map<ExchangeTemplate, Set<Exchange>> allExchanges = new HashMap<>();
    private final List<Letter> receivedInOrder = new ArrayList<>(); // just for caching, refilled on reload from allExchanges
    private final List<Letter> sentInOrder = new ArrayList<>(); // just for caching, refilled on reload from allExchanges


    public boolean addExchange(Player player, ExchangeTemplate template) {
        if (template == null) {
            return false;
        }
        // Cannot start an exchange that is already in progress, or an exchange that has already happened and is not repeatable
        if ((!allExchanges.getOrDefault(template, new HashSet<>()).isEmpty() && !template.isRepeatable()) || activeExchanges.containsKey(template)) {
            return false;
        }
        int versionNumber = allExchanges.getOrDefault(template, new HashSet<>()).stream().mapToInt(Exchange::getVersion).max().orElse(0) + 1;
        Exchange exchange = new Exchange(template, versionNumber);
        if (exchange.canReceiveLetter() && !player.level().isClientSide) {
            exchange.scheduleMail(player);
        }
        allExchanges.computeIfAbsent(template, template1 -> new HashSet<>()).add(exchange);
        activeExchanges.put(template, exchange);
        return true;
    }

    /** Needed when the player starts a dialogue with the NPC in person, and shouldn't communicate anymore
     * TODO make it also stop already started exchanges? "interrupted" boolean variable
     *
     * @param player
     * @param templateName
     */
    public void removeUnstartedExchange(Player player, String templateName) {
        ExchangeTemplate template = ExchangeRegistry.byName(templateName);
        if (template != null) {
            Exchange exchange = activeExchanges.get(template);
            if (exchange != null) {
                exchange.setInactive();
                activeExchanges.remove(template);
            }
        }
    }

    /** Server-side only!
     */
    public void receiveLetter(Player player, String exchangeName) {
        ExchangeTemplate template = ExchangeRegistry.byName(exchangeName);
        if (template != null) {
            Exchange exchange = activeExchanges.get(template);
            if (exchange != null) {
                Letter received = exchange.receiveLetter(receivedInOrder.size());
                if (received != null) {
                    receivedInOrder.add(received);
                }
            }
        }
    }

    /** Client- and server-side (client first). Client-side needed for snappy gui update
     */
    public void sendLetter(Player player, ExchangeTemplate template, List<Integer> chosenOptions, boolean clientSide) {
        Exchange exchange = activeExchanges.get(template);
        if (exchange != null) {
            Letter sent = exchange.sendLetter(player, chosenOptions, clientSide, sentInOrder.size());
            if (sent != null) {
                if (!clientSide) {
                    sent.getTemplate().takeItems(player);
                }
                int indexWithinExchange = sent.getTemplate().getIndex();
                if (indexWithinExchange > 0) {
                    exchange.getLetters().get(indexWithinExchange - 1).setCanReply(false);
                }
                sentInOrder.add(sent);
                ResearchEvents.sendLetterEvents(player, exchange);
                tryTerminateExchange(player, exchange);
            }
        }
    }

    /** Client- and server-side, for snappy gui update.
     *
     */
    public void redeemItems(Player player, ExchangeTemplate template, int index, boolean clientSide) {
        Exchange exchange = activeExchanges.get(template);
        if (exchange != null) {
            if (clientSide) {
                exchange.markRedeemed(player);
                tryTerminateExchange(player, exchange);
            } else {
                List<Letter> letters = exchange.getLetters();
                if (!letters.isEmpty()) {
                    Letter previous = letters.size() >= 2 ? letters.get(letters.size() - 2) : null;
                    letters.get(letters.size() - 1).redeem(player, previous, true);
                    tryTerminateExchange(player, exchange);
                }
            }
        }
    }

    public void openLetter(ServerPlayer player, ExchangeTemplate template) {
        Exchange exchange = activeExchanges.get(template);
        if (exchange != null) {
            List<Letter> letters = exchange.getLetters();
            if (!letters.isEmpty()) {
                Letter letter = letters.get(letters.size() - 1);
                if (!letter.isOpened()) {
                    letter.setOpened(true);
                    letter.getTemplate().getUnlockedData().forEach(s -> DataUtil.setBooleanOnServerAndSync(player, s, true, false));
                    letter.getTemplate().getUnlockedExchanges().forEach(s -> DataUtil.addExchange(player, s));
                    tryTerminateExchange(player, exchange);
                }
            }
        }
    }


    private void tryTerminateExchange(Player player, Exchange exchange) {
        if (exchange.isFinished()) {
            activeExchanges.remove(exchange.getTemplate());
            exchange.setInactive();
            if (exchange.getTemplate().isRepeatable()) {
                addExchange(player, exchange.getTemplate());
            }
        }
    }

    public List<Letter> getReceivedInOrder() {
        return receivedInOrder;
    }

    public List<Letter> getSentInOrder() {
        return sentInOrder;
    }

    public Map<ExchangeTemplate, Exchange> getActiveExchanges() {
        return activeExchanges;
    }

    public CompoundTag saveToNBT(CompoundTag compoundTag) {
        ListTag exchanges = new ListTag();
        int i = 0;
        for (Entry<ExchangeTemplate, Set<Exchange>> entry : allExchanges.entrySet()) {
            for (Exchange exchange : entry.getValue()) {
                exchanges.addTag(i++, exchange.saveToNBT(new CompoundTag()));
            }
        }
        compoundTag.put("exchanges", exchanges);
        return compoundTag;
    }

    public void loadFromNBT(CompoundTag compoundTag) {
        // NOTE: here the letters in exchanges and those in sent/receivedInOrder will no longer be the same objects, but that should be fine
        receivedInOrder.clear();
        sentInOrder.clear();
        allExchanges.clear();
        activeExchanges.clear();
        ListTag exchanges = compoundTag.getList("exchanges", Tag.TAG_COMPOUND);
        for (int i = 0; i < exchanges.size(); i++) {
            Exchange exchange = Exchange.fromNBT(exchanges.getCompound(i));
            if (exchange != null) {
                allExchanges.computeIfAbsent(exchange.getTemplate(), template -> new HashSet<>()).add(exchange);
                if (exchange.isActive()) {
                    activeExchanges.put(exchange.getTemplate(), exchange);
                }
                for (Letter letter : exchange.getLetters()) {
                    boolean p = exchange.getTemplate().isPlayerInitiated();
                    int index = letter.getTemplate().getIndex();
                    boolean isFromPlayer = (p && index % 2 == 0) || (!p && index % 2 == 1);
                    if (isFromPlayer) {
                        sentInOrder.add(letter);
                    } else {
                        receivedInOrder.add(letter);
                    }
                }
            }
        }
        receivedInOrder.sort(Comparator.comparingInt(Letter::getGlobalIndex));
        sentInOrder.sort(Comparator.comparingInt(Letter::getGlobalIndex));
    }

    public void copyToNewStore(LetterData newStore) {
        CompoundTag tag = saveToNBT(new CompoundTag());
        newStore.loadFromNBT(tag);
    }

    private static class Dummy extends LetterData {
        @Override public boolean addExchange(Player player, ExchangeTemplate template) {return false;}

        @Override public void sendLetter(Player player, ExchangeTemplate template, List<Integer> chosenOptions, boolean clientSide) {}

        @Override public void receiveLetter(Player player, String exchangeName) {}

        @Override public void redeemItems(Player player, ExchangeTemplate template, int index, boolean clientSide) {}

        @Override public void openLetter(ServerPlayer player, ExchangeTemplate template) {}
    }

}
