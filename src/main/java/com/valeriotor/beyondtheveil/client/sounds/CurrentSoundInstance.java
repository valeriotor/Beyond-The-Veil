package com.valeriotor.beyondtheveil.client.sounds;

import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class CurrentSoundInstance extends AbstractTickableSoundInstance {

    private final boolean slowStart;
    private final int soundIndex;
    private int counter;

    public CurrentSoundInstance(SoundEvent p_235076_, boolean slowStart, int soundIndex) {
        super(p_235076_, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.slowStart = slowStart;
        this.soundIndex = soundIndex;
        volume = 0.01F;
    }

    @Override
    public void tick() {
        counter++;
        if (counter < 100) {
            volume = Math.min(1, volume * (slowStart ? 1.1F : 1.55F));
        } else if (counter > 212) {
            volume *= 0.75F;
        }
        if (soundIndex == BTVSounds.CURRENTS_LIST.size() - 1 && counter > 150) {
            ClientData.getInstance().newestCurrentSoundInstance = null;
        }
    }


}
