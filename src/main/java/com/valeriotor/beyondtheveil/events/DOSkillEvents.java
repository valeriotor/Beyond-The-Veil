package com.valeriotor.beyondtheveil.events;

import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.bossfights.ArenaFightHandler;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessagePlayerAnimation;
import com.valeriotor.beyondtheveil.network.generic.MessageGenericToClient;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.worship.DOSkill;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.valeriotor.beyondtheveil.network.generic.GenericMessageKey.DEEP_ONE_CLIMB_JUMP;
import static com.valeriotor.beyondtheveil.network.generic.GenericMessageKey.UPPERCUT_ANIMATION;

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

    @SubscribeEvent
    public static void uppercutEvent(PlayerInteractEvent.EntityInteractSpecific event) {
        EntityPlayer p = event.getEntityPlayer();
        if(p.world.isRemote) return;
        IPlayerData data = PlayerDataLib.getCap(p);
        if (data != null) {
            if (data.getString(PlayerDataLib.TRANSFORMED) && DOSkill.UPPERCUT.isActive(data)) {
                ((WorldServer)p.world).getEntityTracker().sendToTrackingAndSelf(p, BTVPacketHandler.INSTANCE.getPacketFrom(new MessagePlayerAnimation(p.getPersistentID(), AnimationRegistry.getIdFromAnimation(AnimationRegistry.deep_one_player_uppercut))));
                BTVPacketHandler.INSTANCE.sendTo(new MessageGenericToClient(UPPERCUT_ANIMATION), (EntityPlayerMP) p);
                Entity target = event.getTarget();
                if (target instanceof EntityLivingBase) {
                    EntityLivingBase entity = (EntityLivingBase) target;
                    PlayerTimer pt = new PlayerTimer(p, player -> {
                        if(entity.isEntityAlive()) {
                            entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                            player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_STONE_HIT, SoundCategory.PLAYERS, 1, 1);
                            float maxHealth = entity.getMaxHealth();
                            double motion = entity.getMaxHealth() >= 100 ? 1.3 * Math.exp((-maxHealth + 80) / 30) : 1.3;
                            if(target.isNonBoss())
                                entity.motionY = motion*1.4; //TODO: check if player, then send message
                        }
                    }, 4);
                    ServerTickEvents.addPlayerTimer(pt);
                }
            }
        }
    }

    public static void doPoisonSkill(LivingHurtEvent e) {
        EntityPlayer p = (EntityPlayer) e.getSource().getTrueSource();
        if(e.getEntityLiving().isNonBoss() && DOSkill.POISON.isActive(p)) {
            if(p.world.rand.nextInt(4) == 0)
                e.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.POISON, 20*8, 3));
        }
    }

    public static void doRegenerationSkill(LivingDeathEvent event) {
        EntityPlayer p = (EntityPlayer) event.getSource().getTrueSource();
        if(DOSkill.REGENERATION.isActive(p)) {
            float maxHealth = event.getEntityLiving().getMaxHealth();
            double level = Math.log10(maxHealth);
            if (level >= 1)
                p.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, (int) (5 * 20 * level), (int) (level - 1)));
        }
    }

}
