package com.valeriotor.beyondtheveil.client.sounds;

import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class NautilusPropellerSoundInstance extends AbstractTickableSoundInstance {

    private final LocalPlayer player;
    private int counter;

    public NautilusPropellerSoundInstance(LocalPlayer player) {
        super(BTVSounds.PROPELLER.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;
    }

    @Override
    public void tick() {
        if ((player.input.left ||  player.input.right ||  player.input.up ||  player.input.down ||  player.input.jumping || Minecraft.getInstance().options.keySprint.isDown()) && player.getVehicle() instanceof NautilusEntity) {
            volume = 1;
            counter++;
        } else {
            volume *= 0.85;
            if (volume < 0.1) {
                stop();
                counter = 0;

            }
        }
    }

    public int getCounter() {
        return counter;
    }
}
