package com.valeriotor.beyondtheveil.util.timers;

import com.valeriotor.beyondtheveil.container.DrownedContainer;
import com.valeriotor.beyondtheveil.container.dialogue.EntityDialogueMenu;
import com.valeriotor.beyondtheveil.util.PersistentPlayerTimer;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkHooks;

import java.util.HashMap;
import java.util.Map;

public class BaptismTimer extends PlayerTimer {

    private Phase phase;
    private int health;
    private int oxygen;
    private boolean done = false;
    private int time;
    private boolean toKill;

    public BaptismTimer() {
        super(Integer.MAX_VALUE, "baptism", PersistentPlayerTimer.BAPTISM, new HashMap<>());
        health = 20;
        oxygen = 20;
        time = 0;
        toKill = false;
    }

    //public BaptismTimer(CompoundTag tag) {
    //    super(tag.getInt("timer"), tag.getString("id"), PersistentPlayerTimer.valueOf(tag.getString("persistence")), mapFromNBT(tag.getCompound("additionalData")));
    //    phase = Phase.values()[tag.getInt("phase")];
    //}

    @Override
    public boolean update(Player player) {
        player.setAirSupply(oxygen);
        if (oxygen > 1) {
            oxygen--;
            return false;
        }
        time++;
        if (!(player.containerMenu instanceof DrownedContainer)) {
            if ((health > 0 || toKill) && time % 10 == 0 && player instanceof ServerPlayer sp) {
                health--;
                player.setHealth(health);
                player.level().playSound(null, player.getOnPos(), SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1, 1);
                if (health == 1) {
                    NetworkHooks.openScreen(sp, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new DrownedContainer(pContainerId, player, phase.ordinal()), Component.translatable("gui.drowned.title")), b -> {
                        b.writeInt(phase.ordinal());
                    });
                } else if (health == 0) {
                    player.level().playSound(null, player.getOnPos(), SoundEvents.PLAYER_DEATH, SoundSource.PLAYERS, 1, 1);
                    done = true;
                }
            }
        } else {
            player.setHealth(1);
        }
        return super.update(player);
    }

    @Override
    public boolean isDone() {
        return done;
    }

    public void chooseOption(Player player, int option) {
        if (option == phase.options - 1) {
            resetTime();
            player.closeContainer();
            toKill = true;
        } else if(phase != Phase.TALK2){
            phase = Phase.values()[phase.ordinal() + 1];
            player.closeContainer();
            health = 3;
        }
    }

    private void resetTime() {
        time = 0;
    }

    //@Override
    //public CompoundTag writeToNBT() {
    //    CompoundTag tag = super.writeToNBT();
    //    tag.putInt("phase", phase.ordinal());
    //    return tag;
    //}

    public enum Phase {
        SUMMON(2), PRAY(2), WHO_LISTENS(3), TALK(3), TALK2(3);

        private final int options;

        Phase(int options) {
            this.options = options;
        }

        public int getOptions() {
            return options;
        }
    }
}
