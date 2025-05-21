package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.tile.SacrificeAltarBE;
import com.valeriotor.beyondtheveil.util.CounterType;
import com.valeriotor.beyondtheveil.util.WaypointType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WaterEvents {

    @SubscribeEvent
    public static void tickEvent(TickEvent.PlayerTickEvent event) {
        Player p = event.player;
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER) {
            p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
                if (playerData.getBoolean(PlayerDataLib.BAPTIZED)) {
                    FluidState fluidstate = p.level().getFluidState(p.blockPosition());
                    if ((p.isInWater() || (p.isInFluidType(fluidstate) && fluidstate.getFluidType() != net.minecraftforge.common.ForgeMod.LAVA_TYPE.get())) && p.isAffectedByFluids() && !p.canStandOnFluid(fluidstate)) {
                        p.setAirSupply(p.getMaxAirSupply());
                    }
                }
            });
        }
    }

}
