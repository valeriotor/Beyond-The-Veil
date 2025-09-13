package com.valeriotor.beyondtheveil.dialogue;

import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public enum DialogueType {
    BLACK_MIRROR(() -> null),
    BLOOD_CULTIST(() -> null),
    DROWNED(() -> null),
    SHOREMAN_BARTENDER(BTVSounds.SHOREMAN_DIALOGUE),
    SHOREMAN_CARPENTER(BTVSounds.SHOREMAN_DIALOGUE),
    SHOREMAN_CLERK(BTVSounds.SHOREMAN_DIALOGUE),
    SHOREMAN_DRUNK(BTVSounds.SHOREMAN_DIALOGUE),
    SHOREMAN_FISHERMAN(BTVSounds.SHOREMAN_DIALOGUE),
    SHOREMAN_LIGHTHOUSE_KEEPER(BTVSounds.SHOREMAN_DIALOGUE),
    SHOREMAN_SCHOLAR(BTVSounds.SHOREMAN_DIALOGUE);

    private final Supplier<SoundEvent> sound;

    DialogueType(Supplier<SoundEvent> sound) {
        this.sound = sound;
    }

    public SoundEvent getSound() {
        return sound.get();
    }
}
