package com.valeriotor.beyondtheveil.letters;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ExchangeTemplate {

    private String name;
    private Correspondence correspondence;
    private boolean playerInitiated;
    private List<LetterTemplate> letters;
    private boolean repeatable;

    public boolean isPlayerInitiated() {
        return playerInitiated;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public String getName() {
        return name;
    }

    public Correspondence getCorrespondence() {
        return correspondence;
    }

    int numberOfLetters() {
        return letters.size();
    }

    LetterTemplate getTemplate(int index) {
        return letters.get(index);
    }

    public void postProcess() {
        for (int i = 0; i < letters.size(); i++) {
            LetterTemplate letter = letters.get(i);
            letter.setParent(this, i);
        }
    }

    public static class LetterTemplate {
        private ExchangeTemplate parent;
        private int index;
        List<List<String>> optionsPerLine;
        private List<ExchangeItems> itemsRequired;
        private RedeemableItems itemsRedeemed;
        private List<String> unlockedData;
        private List<String> unlockedExchanges;

        public void setParent(ExchangeTemplate parent, int index) {
            this.parent = parent;
            this.index = index;
        }

        public ExchangeTemplate getParent() {
            return parent;
        }

        public int getIndex() {
            return index;
        }

        public List<List<String>> getOptionsPerLine() {
            return optionsPerLine;
        }

        public RedeemableItems getItemsRedeemed() {
            return itemsRedeemed;
        }

        public List<ExchangeItems> getItemsRequired() {
            return itemsRequired;
        }

        public List<String> getUnlockedData() {
            if (unlockedData == null) {
                unlockedData = new ArrayList<>();
            }
            return unlockedData;
        }

        public List<String> getUnlockedExchanges() {
            if (unlockedExchanges == null) {
                unlockedExchanges = new ArrayList<>();
            }
            return unlockedExchanges;
        }
    }

    public static class ExchangeItems {
        private String item;
        private int amount;
        private String nbt;
        //private ItemStack stack;

        /*public ItemStack getItem() {
            if (stack != null) {
                return stack;
            }
            Item i = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item));
            if (i != null) {
                stack = new ItemStack(i, amount);
                // TODO either figure out string nbt or set special rules
                return stack;
            }
            return null;
        }*/

        public ItemStack getItem() {
            Item i = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item));
            if (i != null) {
                return new ItemStack(i, amount);
            }
            return null;
        }

        public int getAmount() {
            return amount;
        }
    }


}
