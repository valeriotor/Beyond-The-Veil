package com.valeriotor.beyondtheveil.letters;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Letter {
    private final ExchangeTemplate.LetterTemplate template;
    private boolean opened;
    private boolean canReply;
    private boolean redeemed;
    private final int globalIndex;
    private List<String> chosenOptions = new ArrayList<>();

    public static Letter received(ExchangeTemplate.LetterTemplate template, int currentGlobalIndex) {
        Letter letter = new Letter(template, currentGlobalIndex + 1);
        for (List<String> options : template.optionsPerLine) {
            letter.chosenOptions.add(options.get(0));
        }
        return letter;
    }

    public static Letter sent(ExchangeTemplate.LetterTemplate template, List<Integer> chosen, int currentGlobalIndex) {
        Letter letter = new Letter(template, currentGlobalIndex + 1);
        List<List<String>> optionsPerLine = template.optionsPerLine;
        for (int i = 0; i < chosen.size(); i++) {
            List<String> options = optionsPerLine.get(i);
            letter.chosenOptions.add(options.get(chosen.get(i)));
        }
        return letter;
    }

    public static Letter toWrite(ExchangeTemplate.LetterTemplate template) {
        return sent(template, new ArrayList<>(), -1);
    }

    public static Letter fromNBT(CompoundTag tag, ExchangeTemplate template) {
        if (template != null) {
            Letter letter = new Letter(template.getTemplate(tag.getInt("index")), tag.getInt("globalIndex"));
            CompoundTag options = tag.getCompound("options");
            options.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::valueOf)).forEach(s -> letter.chosenOptions.add(options.getString(s)));
            letter.canReply = tag.getBoolean("canReply");
            letter.opened = tag.getBoolean("opened");
            letter.redeemed = tag.getBoolean("redeemed");
            return letter;
        }
        return null;
    }

    private Letter(ExchangeTemplate.LetterTemplate template, int globalIndex) {
        this.template = template;
        this.globalIndex = globalIndex;
    }

    public boolean isOpened() {
        return opened;
    }

    public void open() {
        opened = true;
    }

    public boolean canReply() {
        return canReply;
    }

    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
    }

    public boolean canRedeem() {
        return !redeemed && template.getItemsRedeemed() != null;
    }

    public void redeem(Player player, Letter previous, boolean giveItems) {
        if (canRedeem()) {
            redeemed = true;
            if (giveItems && !player.level().isClientSide) {
                for (ItemStack stack : template.getItemsRedeemed().getStacks(previous)) {
                    ItemHandlerHelper.giveItemToPlayer(player, stack.copy());
                }
            }
        }
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public List<String> getChosenOptions() {
        return chosenOptions;
    }

    public void chooseOption(String option) {
        chosenOptions.add(option);
    }

    public ExchangeTemplate.LetterTemplate getTemplate() {
        return template;
    }

    public int getGlobalIndex() {
        return globalIndex;
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        tag.putInt("index", template.getIndex());
        tag.putInt("globalIndex", globalIndex);
        tag.putBoolean("opened", opened);
        tag.putBoolean("canReply", canReply);
        tag.putBoolean("redeemed", redeemed);
        CompoundTag options = new CompoundTag();
        for (int i = 0; i < chosenOptions.size(); i++) {
            options.putString(String.valueOf(i), chosenOptions.get(i));
        }
        tag.put("options", options);
        return tag;
    }
}
