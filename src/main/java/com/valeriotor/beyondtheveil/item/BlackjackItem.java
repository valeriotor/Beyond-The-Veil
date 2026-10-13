package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

public class BlackjackItem extends Item {


    public BlackjackItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pUsedHand == InteractionHand.MAIN_HAND || pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() != this) {
            if (pInteractionTarget instanceof Villager villager && !villager.isBaby()) {
                Level l = pInteractionTarget.level();
                InteractionResult success = knockDownVillager(villager);
                if (success != null) return success;
            }

        }
        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }

    @Nullable
    public static InteractionResult knockDownVillager(Villager villager) {
        LazyOptional<ConvalescentData> villagerCapability = villager.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA);
        CompoundTag convalescentData = villagerCapability.isPresent() ? villagerCapability.resolve().get().saveToNBT(new CompoundTag()) : null;
        Level l = villager.level();
        if (!l.isClientSide) {
            CrawlerEntity crawler = convertVillager(BTVEntities.CRAWLER.get(), villager, false);
            if (crawler != null) {
                crawler.setYBodyRot(villager.getYHeadRot());
                crawler.setYHeadRot(villager.getYHeadRot());
                Messages.sendToTracking(GenericToClientPacket.knockoutVillager(crawler.getId(), villager.yBodyRot), villager);
                crawler.setData(villager);
                crawler.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(crawlerCap -> {
                    if (convalescentData != null) {
                        crawlerCap.loadFromNBT(convalescentData);
                    }
                });
                l.addFreshEntity(crawler);
                villager.discard();
                ConvalescentData.applyConvalescentAttributes(crawler);
                crawler.setHealth(villager.getHealth());
                crawler.sendAnimation(AnimationRegistry.crawler_fall_down, 0);
                crawler.level().playSound(null, crawler.blockPosition(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS);
                crawler.setKONoAi(true);
                if (l instanceof ServerLevel sl) {
                    double off = 0.18;
                    sl.sendParticles(ParticleTypes.CRIT, crawler.getX(), crawler.getY() + 1.7, crawler.getZ(), 10, off, off, off, 0.2);
                }
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.SUCCESS;
        }
        return null;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (!context.getLevel().isClientSide) {
            //BlockPos nearestVein = ((ServerLevel) context.getLevel()).findNearestMapStructure(BTVTags.DEEP_VEIN, context.getClickedPos(), 200, false);
            //context.getPlayer().sendSystemMessage(Component.literal(nearestVein == null ? "null" : nearestVein.toString()));

        }
            //LegacyStructure.hamlet_storehouse2.generateStructure(context.getLevel(), context.getClickedPos());

        return super.onItemUseFirst(stack, context);
    }

    @Nullable
    private static <T extends Mob> T convertVillager(EntityType<T> pEntityType, Villager villager, boolean pTransferInventory) {
        if (villager.isRemoved()) {
            return (T)null;
        } else {
            T t = pEntityType.create(villager.level());
            if (t == null) {
                return (T)null;
            } else {
                t.copyPosition(villager);
                t.setBaby(villager.isBaby());
                t.setNoAi(villager.isNoAi());
                if (villager.hasCustomName()) {
                    t.setCustomName(villager.getCustomName());
                    t.setCustomNameVisible(villager.isCustomNameVisible());
                }

                if (villager.isPersistenceRequired()) {
                    t.setPersistenceRequired();
                }

                t.setInvulnerable(villager.isInvulnerable());
                if (pTransferInventory) {
                    t.setCanPickUpLoot(villager.canPickUpLoot());

                    for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                        ItemStack itemstack = villager.getItemBySlot(equipmentslot);
                        if (!itemstack.isEmpty()) {
                            t.setItemSlot(equipmentslot, itemstack.copyAndClear());
                            //t.setDropChance(equipmentslot, villager.getEquipmentDropChance(equipmentslot));
                        }
                    }
                }

                //villager.level().addFreshEntity(t); First change is we add to level later
                if (villager.isPassenger()) {
                    Entity entity = villager.getVehicle();
                    villager.stopRiding();
                    t.startRiding(entity, true);
                }

                //villager.discard(); second change is we discard later
                return t;
            }
        }
    }
}
