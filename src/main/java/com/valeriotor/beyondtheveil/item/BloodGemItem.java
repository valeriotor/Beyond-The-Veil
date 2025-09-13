package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.container.BloodGemContainer;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolData;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.ColorTriplet;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BloodGemItem extends Item {
    private static final int TIME_TO_WORK = 30;

    public BloodGemItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack inHand = pPlayer.getItemInHand(pUsedHand);
        if (pUsedHand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.pass(inHand);
        }
        if (pPlayer.isShiftKeyDown()) {
            if (!pLevel.isClientSide && pPlayer instanceof ServerPlayer sp) {
                NetworkHooks.openScreen(sp, new SimpleMenuProvider(BloodGemContainer::new, Component.translatable("gui.blood_gem.title")));
            }
            return super.use(pLevel, pPlayer, pUsedHand);
        } else {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
        }
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int timeLeft) {
        if (pLivingEntity instanceof Player player) {
            if (pLevel instanceof ServerLevel sl) {
                double randomOffset = 0.6;
                double distance = 0.65;
                double randomYOffset = 0.25;
                sl.sendParticles(BTVParticles.BLOODSPILL.get(), player.getX() + player.getLookAngle().x * distance, player.getY() + 1.25, player.getZ() + player.getLookAngle().z * distance, 1, player.getRandom().nextDouble() * randomOffset - randomOffset / 2, player.getRandom().nextDouble() * randomYOffset - randomYOffset / 2, player.getRandom().nextDouble() * randomOffset - randomOffset / 2, 0);
            }
            int i = this.getUseDuration(pStack) - timeLeft;
            if (i == TIME_TO_WORK) {
                if (pLevel.isClientSide) {
                    player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
                }
            }
        }
        super.onUseTick(pLevel, pLivingEntity, pStack, timeLeft);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int timeLeft) {
        if (pLivingEntity instanceof ServerPlayer sp && sp.getServer() != null) {
            int x = this.getUseDuration(pStack) - timeLeft;
            if (x >= TIME_TO_WORK) {
                pStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(c -> {
                    List<ColorTriplet> triplets = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        Item item0 = c.getStackInSlot(i * 3).getItem();
                        Item item1 = c.getStackInSlot(i * 3 + 1).getItem();
                        Item item2 = c.getStackInSlot(i * 3 + 2).getItem();

                        triplets.add(ColorTriplet.fromItems(item0, item1, item2));
                    }
                    BloodPoolData data = BloodPoolData.getInstance(sp.getServer());
                    for (ColorTriplet triplet : triplets) {
                        data.spawnFirst(sp, sp.getUUID(), triplet);
                    }
                });
                sp.level().playSound(null, sp.getOnPos(), BTVSounds.HEART_RIP.get(), SoundSource.PLAYERS);
            }
        }
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        BloodGemStackHandler itemStackHandler = new BloodGemStackHandler(stack, 15);
        return new ICapabilityProvider() {

            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return cap == ForgeCapabilities.ITEM_HANDLER ? itemStackHandler.getCapability(ForgeCapabilities.ITEM_HANDLER).cast() : LazyOptional.empty();
            }
        };
    }

    private static class BloodGemStackHandler extends ItemStackHandler implements ICapabilityProvider {

        private final ItemStack container;

        public BloodGemStackHandler(ItemStack container, int size) {
            super(size);
            this.container = container;
            initStacks();
        }

        private final LazyOptional<IItemHandler> holder = LazyOptional.of(() -> this);

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, holder);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.getItem() instanceof DyeItem;
        }

        @Override
        protected void onContentsChanged(int slot) {
            CompoundTag tag = container.getOrCreateTag();
            if (!tag.contains("contents")) {
                tag.put("contents", new CompoundTag());
            }
            tag.getCompound("contents").put(String.valueOf(slot), stacks.get(slot).save(new CompoundTag()));
        }

        private void initStacks() {
            CompoundTag tag = container.getOrCreateTag();
            if (!tag.contains("contents")) {
                tag.put("contents", new CompoundTag());
            }
            CompoundTag contents1 = tag.getCompound("contents");
            for (String contents : contents1.getAllKeys()) {
                if (StringUtils.isNumeric(contents)) {
                    int index = Integer.parseInt(contents);
                    if (index >= 0 && index < stacks.size()) {
                        stacks.set(index, ItemStack.of(contents1.getCompound(contents)));
                    }
                }
            }
        }
    }

}
