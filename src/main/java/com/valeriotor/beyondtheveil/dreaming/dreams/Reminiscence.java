package com.valeriotor.beyondtheveil.dreaming.dreams;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

public abstract class Reminiscence {

    public Reminiscence() {
    }

    public abstract CompoundTag save();
    public abstract void load(CompoundTag tag);


    public static class EmptyReminiscence extends Reminiscence {
        @Override
        public CompoundTag save() {
            return new CompoundTag();
        }

        @Override
        public void load(CompoundTag tag) {

        }
    }

    public static class TextReminiscence extends Reminiscence {

        private String textKey;

        public TextReminiscence() {

        }

        public TextReminiscence(String textKey) {
            this.textKey = textKey;
        }

        @Override
        public CompoundTag save() {
            CompoundTag tag = new CompoundTag();
            tag.putString("textKey", textKey);
            return tag;
        }

        @Override
        public void load(CompoundTag tag) {
            textKey = tag.getString("textKey");
        }

        public String getTextKey() {
            return textKey;
        }

        public Component getText() {
            return Component.translatable(textKey);
        }
    }

    public static class SoundReminiscence extends Reminiscence {

        private String soundKey;

        public SoundReminiscence(String soundKey) {
            this.soundKey = soundKey;
        }

        @Override
        public CompoundTag save() {
            CompoundTag tag = new CompoundTag();
            tag.putString("soundKey", soundKey);
            return tag;
        }

        @Override
        public void load(CompoundTag tag) {
            soundKey = tag.getString("soundKey");
        }

        public String getSoundKey() {
            return soundKey;
        }
    }


}

