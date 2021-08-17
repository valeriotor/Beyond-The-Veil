package com.valeriotor.beyondtheveil.entities.bosses;

import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ArenaMusic extends MovingSound {
    private final EntityArenaBoss boss;
    public ArenaMusic(EntityArenaBoss boss) {
        super(BTVSounds.arena_music, SoundCategory.RECORDS);
        xPosF = (float) boss.posX;
        yPosF = (float) boss.posY;
        zPosF = (float) boss.posZ;
        attenuationType = ISound.AttenuationType.NONE;
        volume = 15;
        this.boss = boss;
        repeat = true;
    }

    @Override
    public void update() {
        if(!boss.isEntityAlive()) {
            donePlaying = true;
        }
    }
}
