package com.valeriotor.beyondtheveil.events;

import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.bossfights.ArenaFightHandler;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessagePlayerAnimation;
import com.valeriotor.beyondtheveil.network.generic.GenericMessageKey;
import com.valeriotor.beyondtheveil.network.generic.MessageGenericToClient;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import com.valeriotor.beyondtheveil.worship.DOSkill;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
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
        if(!DOSkill.CLIMBING.isActive(p)) return;
        if(ArenaFightHandler.isPlayerInFight(p.getPersistentID())) return;
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
    public static void doUppercutAnimation(EntityPlayer p) {
        if(DOSkill.UPPERCUT.isActive(p))
            ((WorldServer)p.world).getEntityTracker().sendToTrackingAndSelf(p, BTVPacketHandler.INSTANCE.getPacketFrom(new MessagePlayerAnimation(p.getPersistentID(), AnimationRegistry.getIdFromAnimation(AnimationRegistry.deep_one_player_uppercut))));
    }

    public static void doUppercut(EntityPlayer p) {
        if(DOSkill.UPPERCUT.isActive(p)) {
            EntityLivingBase entity = MathHelperBTV.getClosestLookedAtEntity(p, 4, e -> e != p);

            if (entity != null && entity.isNonBoss()) {
                entity.attackEntityFrom(DamageSource.causePlayerDamage(p), (float) p.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                p.world.playSound(null, p.getPosition(), SoundEvents.BLOCK_STONE_HIT, SoundCategory.PLAYERS, 1, 1);
                float maxHealth = entity.getMaxHealth();
                double motion = entity.getMaxHealth() >= 100 ? 1.3 * Math.exp((-maxHealth + 80) / 30) : 1.3;
                entity.motionY = motion; //TODO: check if player, then send message
            }
        }
    }

}
