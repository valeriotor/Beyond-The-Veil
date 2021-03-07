package com.valeriotor.beyondtheveil.bossfights;

import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.world.DimensionRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.UUID;

public class ArenaFight {
    private final UUID playerID;
    private final MinecraftServer server;
    private final BlockPos arenaStarterPos;
    private final AxisAlignedBB arenaBox;

    public ArenaFight(EntityPlayer player, BlockPos arenaStarterPos) {
        this.playerID = player.getPersistentID();
        server = player.getServer();
        this.arenaStarterPos = arenaStarterPos;
        arenaBox = new AxisAlignedBB(arenaStarterPos.getX(), arenaStarterPos.getY()+4, arenaStarterPos.getZ(),
                                     arenaStarterPos.getX()+30, arenaStarterPos.getY()+12, arenaStarterPos.getZ()-30);
        player.setHealth(player.getMaxHealth());
        player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 30*20, 0, false, false));
        //player.world.playSound(null, player.posX, player.posY, player.posZ);
    }

    public void updateFight() {
        EntityPlayer player = server.getPlayerList().getPlayerByUUID(playerID);
        applyEffects(player);
        setSaturation(player);
    }

    private void applyEffects(EntityPlayer p) {
        p.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 30*20, 0, false, false));
    }

    private void setSaturation(EntityPlayer p) {
        p.getFoodStats().setFoodLevel(17);
        p.getFoodStats().setFoodSaturationLevel(0);
    }

    public boolean equalsStarterPos(BlockPos pos) {
        return arenaStarterPos.equals(pos);
    }

    public boolean hasPlayerLeftArena() {
        EntityPlayer player = server.getPlayerList().getPlayerByUUID(playerID);
        if(player == null) return true;
        return !arenaBox.contains(player.getPositionVector()) || player.dimension != DimensionRegistry.ARCHE.getId();
    }

    public void endFight(boolean endAbruptly) {
        EntityPlayer player = server.getPlayerList().getPlayerByUUID(playerID);
        if(player != null) {
            player.setHealth(player.getMaxHealth());
            player.getFoodStats().setFoodLevel(20);
            player.getFoodStats().setFoodSaturationLevel(20);
            if(endAbruptly)
                player.setPositionAndUpdate(arenaStarterPos.getX()+2, arenaStarterPos.getY()-1, arenaStarterPos.getZ()-1);
            else {
                for(int i = 0; i < 5; i++)
                    player.world.playSound(null, player.getPosition(), BTVSounds.dagonThump, SoundCategory.HOSTILE, 1, 1);
                SPacketTitle spackettitle1 = new SPacketTitle(SPacketTitle.Type.TITLE, new TextComponentTranslation("arena.triumph"));
                ((EntityPlayerMP)player).connection.sendPacket(spackettitle1);
                PlayerTimer pt = new PlayerTimer.PlayerTimerBuilder(player)
                        .addFinalAction(p -> p.setPositionAndUpdate(arenaStarterPos.getX() + 2, arenaStarterPos.getY() - 1, arenaStarterPos.getZ() - 1))
                        .setName("duel_ended")
                        .setTimer(80)
                        .toPlayerTimer();
                ServerTickEvents.addPlayerTimer(pt);
            }
        }
    }

}
