package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.lib.BTVTags;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class TeleportUtil {

    public static void teleportToArche(ServerPlayer player, Level originLevel) {
        MinecraftServer server = originLevel.getServer();
        if (server != null) {
            ServerLevel archeLevel = server.getLevel(BTVDimensions.ARCHE_LEVEL);
            if (archeLevel != null) {
                if (player.getCapability(PlayerDataProvider.PLAYER_DATA, null).isPresent()) {
                    Long posLong = player.getCapability(PlayerDataProvider.PLAYER_DATA, null).resolve().get().getLong(PlayerDataLib.VEIN_POS);
                    if (posLong == null) {
                        BlockPos posToSearchFrom = new BlockPos(player.getRandom().nextInt(-500000, 500000), 70, player.getRandom().nextInt(-500000, 500000));
                        BlockPos nearestVein = archeLevel.findNearestMapStructure(BTVTags.DEEP_VEIN, posToSearchFrom, 200, false);
                        if (nearestVein == null) {
                            nearestVein = posToSearchFrom; // TODO and we generate a vein there...
                        }
                        posLong = nearestVein.asLong();
                        DataUtil.setLong(player, PlayerDataLib.VEIN_POS, posLong, false);
                    }
                    BlockPos toTeleport = BlockPos.of(posLong);
                    player.changeDimension(archeLevel, new ITeleporter() {
                        @Override
                        public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                            Entity entity1 = repositionEntity.apply(false);
                            entity.teleportTo(toTeleport.getX() + 6, 111, toTeleport.getZ() + 4);;
                            return entity;
                        }
                    });
//
                }
//
            }
//
        }
    }

}
