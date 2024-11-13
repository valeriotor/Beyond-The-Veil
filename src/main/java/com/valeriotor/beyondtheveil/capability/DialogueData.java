package com.valeriotor.beyondtheveil.capability;

import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.EnumMap;
import java.util.Map;

public class DialogueData {

    public static DialogueData for_(Player player) {
        return player.getCapability(DialogueDataProvider.DIALOGUE_DATA).orElse(DUMMY);
    }

    private static final DialogueData DUMMY = new Dummy();

    private final Map<DialogueType, DialogueTemplate> unlockedDialogues = new EnumMap<>(DialogueType.class);

    public void setDialogue(String type, String template) {
        DialogueType type1 = DialogueType.valueOf(type.toUpperCase());
        setDialogue(type1, DialogueRegistry.getTemplate(type1, template));
    }

    public void setDialogue(DialogueType type, DialogueTemplate template) {
        unlockedDialogues.put(type, template);
    }

    public DialogueTemplate getDialogue(DialogueType type) {
        return unlockedDialogues.computeIfAbsent(type, t -> DialogueRegistry.getTemplate(t, "initial"));
    }

    public void saveToNBT(CompoundTag compoundTag) {
        for (Map.Entry<DialogueType, DialogueTemplate> entry : unlockedDialogues.entrySet()) {
            compoundTag.putString(entry.getKey().name(), entry.getValue().getID());
        }
    }

    public void loadFromNBT(CompoundTag compoundTag) {
        for (String key : compoundTag.getAllKeys()) {
            setDialogue(key, compoundTag.getString(key));
        }
    }

    public void copyToNewStore(DialogueData newStore) {
        for (Map.Entry<DialogueType, DialogueTemplate> entry : unlockedDialogues.entrySet()) {
            newStore.setDialogue(entry.getKey(), entry.getValue());
        }
    }

    private static class Dummy extends DialogueData {
        @Override
        public void setDialogue(String type, String template) {}

        @Override
        public void setDialogue(DialogueType type, DialogueTemplate template) {}
    }


}
