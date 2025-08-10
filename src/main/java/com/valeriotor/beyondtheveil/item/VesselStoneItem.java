package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.util.TeleportUtil;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Optional;
import java.util.function.Function;

public class VesselStoneItem extends Item {
    private static final int TIME_TO_WORK = 20;

    public VesselStoneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int timeLeft) {
        if (pLivingEntity instanceof ServerPlayer player && pLevel instanceof ServerLevel sl) {
            int i = this.getUseDuration(pStack) - timeLeft;
            if (i >= TIME_TO_WORK) {
                MinecraftServer server = sl.getServer();
                BlockPos blockpos = player.getRespawnPosition();
                float f = player.getRespawnAngle();
                boolean flag = player.isRespawnForced();
                ServerLevel serverlevel = server.getLevel(player.getRespawnDimension());
                Optional<Vec3> vec3 = Player.findRespawnPositionAndUseSpawnBlock(serverlevel, blockpos, f, flag, true);
                vec3.ifPresent(v -> {
                    player.changeDimension(serverlevel, new TeleportUtil.Teleporter(v));
                    pStack.hurtAndBreak(1, player, c->{});
                });
            }
        }
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int timeLeft) {
        if (pLivingEntity instanceof Player player) {
            int i = this.getUseDuration(pStack) - timeLeft;
            if (i >= TIME_TO_WORK) {
                if (i % 40 == TIME_TO_WORK) {
                    if (pLevel.isClientSide) {
                        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
                    }
                }
            }
        }
        super.onUseTick(pLevel, pLivingEntity, pStack, timeLeft);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand pUsedHand) {
        if (player.level().dimension() != BTVDimensions.ARCHE_LEVEL) {
            if (!player.level().isClientSide) {
                player.sendSystemMessage(Component.translatable("message.vessel_stone.not_in_arche"));
            }
        } else if (player.isInWater()) {
            if (!player.level().isClientSide) {
                player.sendSystemMessage(Component.translatable("message.vessel_stone.in_water"));
            }
        } else {
            player.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(player.getItemInHand(pUsedHand));
        }
        return super.use(pLevel, player, pUsedHand);
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }


    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

}
