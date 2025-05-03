package com.valeriotor.beyondtheveil.letters;

import com.google.common.collect.Iterables;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.util.PersistentPlayerTimer;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import java.util.*;

public class Exchange {

    private ExchangeTemplate template;
    private List<Letter> letters = new ArrayList<>();

    public static Exchange fromNBT(CompoundTag tag) {
        ExchangeTemplate template = ExchangeRegistry.byName(tag.getString("template"));
        if (template != null) {
            Exchange e = new Exchange(template);
            CompoundTag letters1 = tag.getCompound("letters");
            letters1.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::valueOf)).map(s -> Letter.fromNBT(letters1.getCompound(s))).filter(Objects::nonNull).forEach(l -> e.letters.add(l));
            return e;
        }
        return null;
    }

    public Exchange(ExchangeTemplate template) {
        this.template = template;
    }

    public ExchangeTemplate getTemplate() {
        return template;
    }

    public ExchangeTemplate.LetterTemplate nextLetterTemplate() {
        if (isFinished()) {
            return null;
        }
        return template.getTemplate(letters.size());
    }

    public String getName() {
        return template.getName();
    }

    public boolean hasItems(Player player) {
        IItemHandler inventory = new PlayerMainInvWrapper(player.getInventory());
        if(canSendLetter() && template.getTemplate(letters.size()).getItemsRequired() != null) {
            for (ExchangeTemplate.ExchangeItems exchangeItems : template.getTemplate(letters.size()).getItemsRequired()) {
                int remaining = exchangeItems.getAmount();
                for (int i = 0; i < inventory.getSlots(); i++) {
                    remaining -= inventory.extractItem(i, remaining, true).getCount();
                }
                if (remaining > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void takeItems(Player player) {
        IItemHandler inventory = new PlayerMainInvWrapper(player.getInventory());
        if(canSendLetter()) {
            for (ExchangeTemplate.ExchangeItems exchangeItems : template.getTemplate(letters.size()).getItemsRequired()) {
                int remaining = exchangeItems.getAmount();
                for (int i = 0; i < inventory.getSlots(); i++) {
                    remaining -= inventory.extractItem(i, remaining, false).getCount();
                    if (remaining == 0) {
                        break;
                    }
                }
            }
        }

    }

    public boolean canSendLetter() {
        return isNextLetterFromPlayer() && template.numberOfLetters() > letters.size();
    }

    public boolean canReceiveLetter() {
        return !isNextLetterFromPlayer() && template.numberOfLetters() > letters.size();
    }

    public boolean noLettersSent() {
        return letters.isEmpty();
    }

    /**
     * Also works if no more letters are to be sent
     */
    private boolean isNextLetterFromPlayer() {
        if (template.isPlayerInitiated()) {
            return letters.size() % 2 == 0;
        }
        return letters.size() % 2 == 1;
    }

    public boolean isFinished() {
        return letters.size() >= template.numberOfLetters();
    }

    public Letter sendLetter(Player player, List<Integer> chosenOptions, Map<ExchangeTemplate.LetterTemplate, Integer> versions, boolean clientSide) {
        if (canSendLetter()) {
            ExchangeTemplate.LetterTemplate template1 = template.getTemplate(letters.size());
            Letter letter = Letter.sent(template1, chosenOptions, versions.getOrDefault(template1, 0));
            letter.setOpened(true);
            letters.add(letter);
            if (canReceiveLetter() && !clientSide) {
                scheduleMail(player);
            }
            return letter;
        }
        return null;
    }

    public Letter receiveLetter(Map<ExchangeTemplate.LetterTemplate, Integer> versions) {
        if (canReceiveLetter()) {
            ExchangeTemplate.LetterTemplate template1 = template.getTemplate(letters.size());
            Letter received = Letter.received(template1, versions.getOrDefault(template1, 0));
            letters.add(received);
            if (canSendLetter()) {
                received.setCanReply(true);
            }
            return received;
        }
        return null;
    }

    public void openLetter() {
        // let's just open them all instead of finding the right one
        letters.forEach(Letter::open);
    }

    public void scheduleMail(Player player) {
        int time = player.getRandom().nextInt(50, 100);
        PlayerTimer timer = new PlayerTimer(time, "letter_" + getName(), PersistentPlayerTimer.LETTER, Map.of("exchange", getName()));
        player.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).ifPresent(c -> c.addTimer(timer));
    }

    public void markRedeemed(Player player) {
        if (!letters.isEmpty() && isNextLetterFromPlayer() && Iterables.getLast(letters).canRedeem()) {
            Iterables.getLast(letters).redeem(player, null, false);
        }
    }

    public boolean pendingMail() {
        return isNextLetterFromPlayer() && letters.size() > 0 && !Iterables.getLast(letters).isOpened();
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        tag.putString("template", template.getName());
        CompoundTag letters = new CompoundTag();
        for (int i = 0; i < this.letters.size(); i++) {
            letters.put(String.valueOf(i), this.letters.get(i).saveToNBT(new CompoundTag()));
        }
        tag.put("letters", letters);
        return tag;
    }

    public int size() {
        return letters.size();
    }
    // TODO for safety make a method to check that a given player still has the player timer they're waiting for
    // TODO could be combined with a method that checks if the player has the required data to receive a certain letter

}
