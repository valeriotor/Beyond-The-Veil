package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.container.DreamBottleContainer;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
                NetworkHooks.openScreen(sp, new SimpleMenuProvider(DreamBottleContainer::new, Component.translatable("gui.dream_bottle.title")));
            }

        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        FluidHandlerItemStack fluidHandlerItemStack = new FluidHandlerItemStack(stack, 4000) {
            @Override
            public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
                return stack.getFluid() == BTVFluids.FLUID_TEARS.getA().get();
            }
        };
        DreamBottleStackHandler itemStackHandler = new DreamBottleStackHandler(4);
        return new ICapabilityProvider() {

            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return cap == ForgeCapabilities.FLUID_HANDLER_ITEM ? fluidHandlerItemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).cast() : (cap == ForgeCapabilities.ITEM_HANDLER ? itemStackHandler.getCapability(ForgeCapabilities.ITEM_HANDLER).cast() : LazyOptional.empty());
            }
        };
    }

    private static class DreamBottleStackHandler extends ItemStackHandler implements ICapabilityProvider {

        public DreamBottleStackHandler(int size) {
            super(size);
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
    }



}
