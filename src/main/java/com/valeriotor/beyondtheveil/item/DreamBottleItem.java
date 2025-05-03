package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.container.DreamBottleContainer;
import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DreamBottleItem extends Item {

    public DreamBottleItem() {
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
                inHand.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(c -> {
                    if (c.getFluidInTank(0).getAmount() > 0) {
                        DataUtil.setBooleanOnServerAndSyncIfDifferent(sp, PlayerDataLib.FILLED_BOTTLE, true, false);
                    }
                });
                NetworkHooks.openScreen(sp, new SimpleMenuProvider(DreamBottleContainer::new, Component.translatable("gui.dream_bottle.title")));
            }
            return super.use(pLevel, pPlayer, pUsedHand);
        } else {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof ServerPlayer sp) {
            DreamHandler.dreamBottle(sp, stack);
            return stack;
        }
        return super.finishUsingItem(stack, pLevel, pLivingEntity);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        FluidHandlerItemStack fluidHandlerItemStack = new FluidHandlerItemStack(stack, 4000) {
            @Override
            public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
                return stack.getFluid() == BTVFluids.FLUID_TEARS.getA().get();
            }
        };
        DreamBottleStackHandler itemStackHandler = new DreamBottleStackHandler(stack, 4);
        return new ICapabilityProvider() {

            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return cap == ForgeCapabilities.FLUID_HANDLER_ITEM ? fluidHandlerItemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).cast() : (cap == ForgeCapabilities.ITEM_HANDLER ? itemStackHandler.getCapability(ForgeCapabilities.ITEM_HANDLER).cast() : LazyOptional.empty());
            }
        };
    }

    private static class DreamBottleStackHandler extends ItemStackHandler implements ICapabilityProvider {

        private final ItemStack container;

        public DreamBottleStackHandler(ItemStack container, int size) {
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
            return stack.getItem() == Registration.MEMORY_PHIAL.get();
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
