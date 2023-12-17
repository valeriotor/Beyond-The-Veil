package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tile.HeartBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CoralStaffItem extends Item {


    public CoralStaffItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!(entity instanceof LivingEntity)) {
            return false;
        }
        if (!player.level().isClientSide()) {
            Vec3 pos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
            List<Mob> undeads = player.level().getEntities(EntityTypeTest.forClass(Mob.class), AABB.ofSize(pos, 64, 64, 64), e -> e.getMobType() == MobType.UNDEAD);
            for (Mob undead : undeads) {
                undead.setTarget(((LivingEntity) entity));
                // but not IPlayerGuardian mobs belonging to other players
            }
            ((ServerLevel)player.level()).getChunkSource().broadcastAndSend(player, new ClientboundAnimatePacket(entity, 5));
        }
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pUsedHand == InteractionHand.OFF_HAND) {
            return super.use(pLevel, pPlayer, pUsedHand);
        }
        CompoundTag tag = pPlayer.getItemInHand(pUsedHand).getOrCreateTag();
        if (pPlayer.isShiftKeyDown()) {
            tag.remove("heart");
        } else {
            //TODO send message to player
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

        if (context.getHand() == InteractionHand.MAIN_HAND) {
            BlockPos pos = context.getClickedPos();
            BlockEntity be = context.getLevel().getBlockEntity(pos);
            if (be instanceof HeartBE heart) {
                if (context.getPlayer().isShiftKeyDown()) {
                    heart.setLink(null);
                } else {
                    CompoundTag tag = stack.getOrCreateTag();
                    boolean success = true;
                    if (tag.contains("heart")) {
                        BlockPos oldPos = BlockPos.of(tag.getLong("heart"));
                        if(oldPos.distSqr(pos) > 32*32) success = false;
                        if(success) {
                            BlockEntity oldBe = context.getLevel().getBlockEntity(oldPos);
                            if (oldBe instanceof HeartBE oldHeart) {
                                oldHeart.setLink(pos);
                            }
                        }
                    }
                    if(success)
                        tag.putLong("heart", pos.asLong());
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.onItemUseFirst(stack, context);
    }
}

