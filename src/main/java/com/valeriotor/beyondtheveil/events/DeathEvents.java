package com.valeriotor.beyondtheveil.events;

import com.valeriotor.beyondtheveil.bossfights.ArenaFightHandler;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.entities.IPlayerGuardian;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DeathEvents {

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if(event.getEntity() instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) event.getEntity();
            IPlayerData cap = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
            endArenaFight(event, p);
            recordDeathCoordinates(event, p, cap);
        } else if(event.getEntity() instanceof EntityElderGuardian) {
            recordElderGuardianKill(event);
        }
    }

    private static void endArenaFight(LivingDeathEvent event, EntityPlayer p) {
        if(ArenaFightHandler.isPlayerInFight(p.getPersistentID())) {
            event.setCanceled(true);
            ArenaFightHandler.endFight(p.getPersistentID(), true);
            if(!p.world.isRemote) {
                for(int i = 0; i < 5; i++)
                    p.world.playSound(null, p.getPosition(), BTVSounds.dagonThump, SoundCategory.HOSTILE, 1, 1);
                SPacketTitle spackettitle1 = new SPacketTitle(SPacketTitle.Type.TITLE, new TextComponentTranslation("arena.defeat"));
                ((EntityPlayerMP)p).connection.sendPacket(spackettitle1);
            }
        }
    }

    private static void recordDeathCoordinates(LivingDeathEvent event, EntityPlayer p, IPlayerData cap) {
        BlockPos pos = p.getPosition();
        if(!event.isCanceled() && p.dimension == DimensionType.OVERWORLD.getId()) {
            cap.setInteger(PlayerDataLib.DEATH_X, pos.getX(), false);
            cap.setInteger(PlayerDataLib.DEATH_Y, pos.getY(), false);
            cap.setInteger(PlayerDataLib.DEATH_Z, pos.getZ(), false);
        }
    }

    private static void recordElderGuardianKill(LivingDeathEvent event) {
        Entity e = event.getSource().getTrueSource();
        EntityPlayer p = null;
        if(e instanceof EntityPlayer) {
            p = (EntityPlayer)e;
        } else if(e instanceof IPlayerGuardian) {
            p = ((IPlayerGuardian)e).getMaster();
        }
        if(p != null) {
            IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
            if(data.getString(PlayerDataLib.DAGON_DIALOGUE.apply(1)) && !data.getString(PlayerDataLib.DAGONQUEST2)) {
                int val = SyncUtil.incrementIntDataOnServer(p, false, PlayerDataLib.ELDER_GUARDIANS, 1, 1);
                if(val == 3) {
                    SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.DAGONQUEST2);
                }
            }
        }
    }

}
