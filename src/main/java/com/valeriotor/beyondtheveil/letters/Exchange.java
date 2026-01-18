package com.valeriotor.beyondtheveil.letters;

import com.google.common.collect.Iterables;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.PersistentPlayerTimer;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import java.util.*;

public class Exchange {

    private final ExchangeTemplate template;
    private final int version;
    private boolean active = true;
    private final List<Letter> letters = new ArrayList<>();

    public static Exchange fromNBT(CompoundTag tag) {
        ExchangeTemplate template = ExchangeRegistry.byName(tag.getString("template"));
        int version = tag.getInt("version");
        if (template != null) {
            Exchange e = new Exchange(template, version);
            boolean active = tag.getBoolean("active");
            if (!active) {
                e.setInactive();
            }
            CompoundTag letters1 = tag.getCompound("letters");
            letters1.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::valueOf)).map(s -> Letter.fromNBT(letters1.getCompound(s), template)).filter(Objects::nonNull).forEach(e.letters::add);
            return e;
        }
        return null;
    }

    public Exchange(ExchangeTemplate template, int version) {
        this.template = template;
        this.version = version;
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public ExchangeTemplate getTemplate() {
        return template;
    }

    public int getVersion() {
        return version;
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
        return letters.size() >= template.numberOfLetters() && !letters.get(letters.size()-1).canRedeem();
    }

    public boolean isActive() {
        return active;
    }

    public void setInactive() {
        active = false;
    }

    /**
     * @param clientSide needed for downstream effects: scheduling next letter's playertimer, adding capability data and more exchanges
     */
    public Letter sendLetter(Player player, List<Integer> chosenOptions, boolean clientSide, int currentGlobalIndex) {
        if (canSendLetter() && template.getTemplate(letters.size()).hasItems(player)) {
            ExchangeTemplate.LetterTemplate template1 = template.getTemplate(letters.size());
            Letter letter = Letter.sent(template1, chosenOptions, currentGlobalIndex);
            letter.setOpened(true);
            if (player instanceof ServerPlayer sp && !clientSide) {
                template1.getUnlockedData().forEach(s -> DataUtil.setBooleanOnServerAndSync(player, s, true, false));
                template1.getUnlockedExchanges().forEach(s -> DataUtil.addExchange(sp, s));
            }
            letters.add(letter);
            if (canReceiveLetter() && !clientSide) {
                scheduleMail(player);
            }
            return letter;
        }
        return null;
    }

    public Letter receiveLetter(int currentGlobalIndex) {
        if (canReceiveLetter()) {
            ExchangeTemplate.LetterTemplate template1 = template.getTemplate(letters.size());
            Letter received = Letter.received(template1, currentGlobalIndex);
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

    /** Client-side only. Updates the last letter to be redeemed, so it doesn't show the button in the gui anymore
     */
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
        tag.putInt("version", version);
        tag.putBoolean("active", active);
        return tag;
    }

    public int size() {
        return letters.size();
    }
    // TODO for safety make a method to check that a given player still has the player timer they're waiting for
    // TODO could be combined with a method that checks if the player has the required data to receive a certain letter

}
