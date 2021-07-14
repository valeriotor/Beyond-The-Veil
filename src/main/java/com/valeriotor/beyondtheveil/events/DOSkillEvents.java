package com.valeriotor.beyondtheveil.events;

import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessagePlayerAnimation;
import com.valeriotor.beyondtheveil.network.generic.GenericMessageKey;
import com.valeriotor.beyondtheveil.network.generic.MessageGenericToClient;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import static com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider.PLAYERDATA;
import static com.valeriotor.beyondtheveil.lib.PlayerDataLib.DEEP_ONE_STUCK_CLIMBING;
import static com.valeriotor.beyondtheveil.network.generic.GenericMessageKey.DEEP_ONE_CLIMB_JUMP;

@Mod.EventBusSubscriber
public class DOSkillEvents {

    public static void jumpEvent(EntityPlayer p) {
        IPlayerData data = PlayerDataLib.getCap(p);
        if(!data.getString(PlayerDataLib.TRANSFORMED)) return;
        BlockPos ppos = p.getPosition();
        //if(p.world.getBlockState(ppos.down()).getBlock() != Blocks.AIR) return;
        //if(p.world.getBlockState(ppos.down().down()).getBlock() != Blocks.AIR) return;
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            if (climbableBlockCheck(p, facing)) {
                BTVPacketHandler.INSTANCE.sendTo(new MessageGenericToClient(DEEP_ONE_CLIMB_JUMP), (EntityPlayerMP)p);
                BTVPacketHandler.INSTANCE.sendToAll(new MessagePlayerAnimation(p.getPersistentID(), AnimationRegistry.getIdFromAnimation(AnimationRegistry.deep_one_player_climb)));
                break;
            }
        }
    }

    @SubscribeEvent
    public static void stuckClimbingEvent(PlayerTickEvent event) {

    }

    private static boolean climbableBlockCheck(EntityPlayer p, EnumFacing facing) {
        BlockPos offset = p.getPosition().offset(facing);
        BlockPos offsetTop = offset.up();
        BlockPos offsetOffset = offset.offset(facing.rotateYCCW());
        BlockPos offsetOffsetTop = offsetOffset.up();
        Block block = p.world.getBlockState(offset).getBlock();
        Block block2 = p.world.getBlockState(offsetTop).getBlock();
        Block block3 = p.world.getBlockState(offsetOffset).getBlock();
        Block block4 = p.world.getBlockState(offsetOffsetTop).getBlock();
        return !block.canSpawnInBlock() || !block2.canSpawnInBlock() || !block3.canSpawnInBlock() || !block4.canSpawnInBlock();
    }


}
