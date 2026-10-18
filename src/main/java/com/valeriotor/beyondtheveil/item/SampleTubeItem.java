package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleTubeItem extends Item {

    public SampleTubeItem() {
        super(new Item.Properties().stacksTo(64));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        FluidStack fluid = getFluid(pStack);
        if (!fluid.isEmpty()) {
            pTooltipComponents.add(Component.translatable("tooltip.sample_tube.stored", Component.translatable(fluid.getTranslationKey())));
        }
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        pStack.getOrCreateTag();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.isShiftKeyDown() && !pLevel.isClientSide) {
            ItemStack tube = pPlayer.getItemInHand(pUsedHand);
            if (tube.hasTag() && tube.getOrCreateTag().contains("fluid")) {
                if (tube.getCount() == 1) {
                    setFluid(tube, null);
                } else {
                    tube.shrink(1);
                    ItemStack stack = new ItemStack(Registration.SAMPLE_TUBE.get());
                    setFluid(stack, null);
                    ItemHandlerHelper.giveItemToPlayer(pPlayer, stack);
                }
                pLevel.playSound(null, pPlayer.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level l = context.getLevel();
        BlockState blockState = l.getBlockState(context.getClickedPos());
        BlockEntity be = l.getBlockEntity(context.getClickedPos());
        if (be != null && blockState.getBlock() != Registration.FLASK_SHELF.get() && be.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent()) {
            if (!l.isClientSide) {
                be.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(c -> {
                    FluidStack fluidStack = SampleTubeItem.getFluid(stack);
                    if (fluidStack.isEmpty()) {
                        if (c.drain(100, IFluidHandler.FluidAction.SIMULATE).getAmount() == 100) {
                            Fluid fluid = c.drain(100, IFluidHandler.FluidAction.EXECUTE).getFluid();
                            if (stack.getCount() == 1) {
                                SampleTubeItem.setFluid(stack, fluid);
                            } else {
                                stack.shrink(1);
                                ItemStack newStack = new ItemStack(Registration.SAMPLE_TUBE.get());
                                SampleTubeItem.setFluid(newStack, fluid);
                                ItemHandlerHelper.giveItemToPlayer(context.getPlayer(), newStack);
                            }
                            be.setChanged();
                            l.sendBlockUpdated(context.getClickedPos(), blockState, blockState, 2);
                        }
                    } else {
                        if (c.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) == 100) {
                            c.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                            if (stack.getCount() == 1) {
                                SampleTubeItem.setFluid(stack, null);
                            } else {
                                stack.shrink(1);
                                ItemStack newStack = new ItemStack(Registration.SAMPLE_TUBE.get());
                                SampleTubeItem.setFluid(newStack, null);
                                ItemHandlerHelper.giveItemToPlayer(context.getPlayer(), newStack);
                            }
                            be.setChanged();
                            l.sendBlockUpdated(context.getClickedPos(), blockState, blockState, 2);
                        }
                    }
                });
            }
            return InteractionResult.SUCCESS;
        }
        return super.onItemUseFirst(stack, context);
    }

    public static FluidStack getFluid(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("fluid")) {
                return FluidStack.loadFluidStackFromNBT(tag.getCompound("fluid"));
            }
        }
        return FluidStack.EMPTY;
    }

    public static void setFluid(ItemStack stack, Fluid fluid) {
        CompoundTag tag = stack.getOrCreateTag();
        if (fluid != null) {
            tag.put("fluid", new FluidStack(fluid, 100).writeToNBT(new CompoundTag()));
        } else {
            tag.remove("fluid");
        }
    }


    public static class SampleTubeColor implements ItemColor {

        @Override
        public int getColor(ItemStack pStack, int pTintIndex) {
            FluidStack fluid = getFluid(pStack);
            if (!fluid.isEmpty()) {
                return IClientFluidTypeExtensions.of(fluid.getFluid()).getTintColor();
            }
            return 0xFFFFFFFF;
        }


    }

    public static void livingDeathEvent(LivingDeathEvent event, ServerPlayer player) {
        List<Fluid> fluids = rollForEntity(event.getEntity(), player);
        if (!fluids.isEmpty()) {
            for (Fluid fluid : fluids) {
                for (ItemStack item : player.getInventory().items) {
                    if (item.getItem() == Registration.SAMPLE_TUBE.get() && (!item.hasTag() || !item.getOrCreateTag().contains("fluid"))) {
                        ItemStack filledTube = new ItemStack(Registration.SAMPLE_TUBE.get());
                        setFluid(filledTube, fluid);
                        boolean flag = player.getInventory().contains(filledTube);
                        int freeSlot = -1;
                        if (!flag) {
                            freeSlot = player.getInventory().getFreeSlot();
                        }
                        if (flag || freeSlot != -1) {
                            item.shrink(1);
                            if (freeSlot != -1) {
                                player.getInventory().setItem(freeSlot, filledTube);
                            } else {
                                ItemHandlerHelper.giveItemToPlayer(player, filledTube);
                            }
                        }
                    }
                }
            }

        }
    }

    private static Map<EntityType<?>, List<FluidRoll>> rolls = new HashMap<>();

    public static List<Fluid> rollForEntity(LivingEntity e, ServerPlayer player) {
        if (rolls.isEmpty()) {
            rolls.computeIfAbsent(EntityType.CAVE_SPIDER, type -> List.of(new FluidRoll(0.9, BTVFluids.FLUID_POISON_SERUM.getA().get())));
            rolls.computeIfAbsent(BTVEntities.CEPHALOPODIAN.get(), type -> List.of(
                    new FluidRoll(0.9, BTVFluids.FLUID_GROWTH_STIMULANT.getA().get()),
                    new FluidRoll(0.5, BTVFluids.FLUID_ORGANOCHLORIDE.getA().get()),
                    new FluidRoll(0.9, BTVFluids.FLUID_VASOCONSTRICTOR.getA().get()),
                    new FluidRoll(0.2, BTVFluids.FLUID_DIAMOND_POWDER.getA().get())));
            rolls.computeIfAbsent(BTVEntities.BONECAGE.get(), type -> List.of(
                    new FluidRoll(0.9, BTVFluids.FLUID_GROWTH_STIMULANT.getA().get()),
                    new FluidRoll(0.5, BTVFluids.FLUID_ORGANOCHLORIDE.getA().get()),
                    new FluidRoll(0.9, BTVFluids.FLUID_VASOCONSTRICTOR.getA().get()),
                    new FluidRoll(0.2, BTVFluids.FLUID_DIAMOND_POWDER.getA().get())));
            rolls.computeIfAbsent(BTVEntities.SANDFLATTER.get(), type -> List.of(
                    new FluidRoll(0.9, BTVFluids.FLUID_GROWTH_STIMULANT.getA().get()),
                    new FluidRoll(0.5, BTVFluids.FLUID_PHEROMONES.getA().get()),
                    new FluidRoll(0.9, BTVFluids.FLUID_VASOCONSTRICTOR.getA().get()),
                    new FluidRoll(0.2, BTVFluids.FLUID_DIAMOND_POWDER.getA().get())));
            rolls.computeIfAbsent(BTVEntities.ADELINE.get(), type -> List.of(
                    new FluidRoll(0.5, BTVFluids.FLUID_VASOCONSTRICTOR.getA().get()),
                    new FluidRoll(0.1, BTVFluids.FLUID_DIAMOND_POWDER.getA().get())));
            rolls.computeIfAbsent(BTVEntities.MAN_O_WAR.get(), type -> List.of(
                    new FluidRoll(0.5, BTVFluids.FLUID_VASOCONSTRICTOR.getA().get()),
                    new FluidRoll(0.3, BTVFluids.FLUID_ORGANOCHLORIDE.getA().get()),
                    new FluidRoll(0.1, BTVFluids.FLUID_DIAMOND_POWDER.getA().get())));
            rolls.computeIfAbsent(BTVEntities.UMANCALA.get(), type -> List.of(
                    new FluidRoll(0.5, BTVFluids.FLUID_VASOCONSTRICTOR.getA().get()),
                    new FluidRoll(0.1, BTVFluids.FLUID_DIAMOND_POWDER.getA().get())));
            rolls.computeIfAbsent(BTVEntities.ANGLER.get(), type -> List.of(
                    new FluidRoll(0.3, BTVFluids.FLUID_VASOCONSTRICTOR.getA().get()),
                    new FluidRoll(0.8, BTVFluids.FLUID_PHEROMONES.getA().get()),
                    new FluidRoll(0.05, BTVFluids.FLUID_DIAMOND_POWDER.getA().get())));
            rolls.computeIfAbsent(BTVEntities.SEA_SNAKE.get(), type -> List.of(
                    new FluidRoll(0.3, BTVFluids.FLUID_VASOCONSTRICTOR.getA().get()),
                    new FluidRoll(0.05, BTVFluids.FLUID_DIAMOND_POWDER.getA().get())));
            rolls.computeIfAbsent(BTVEntities.SEPIID.get(), type -> List.of(
                    new FluidRoll(0.1, BTVFluids.FLUID_VASOCONSTRICTOR.getA().get()),
                    new FluidRoll(0.02, BTVFluids.FLUID_DIAMOND_POWDER.getA().get())));
            rolls.computeIfAbsent(BTVEntities.OCTID.get(), type -> List.of(
                    new FluidRoll(0.1, BTVFluids.FLUID_VASOCONSTRICTOR.getA().get()),
                    new FluidRoll(0.1, BTVFluids.FLUID_PHEROMONES.getA().get()),
                    new FluidRoll(0.02, BTVFluids.FLUID_DIAMOND_POWDER.getA().get())));
        }
        List<Fluid> fluids = new ArrayList<>();
        List<FluidRoll> fluidRolls = rolls.get(e.getType());
        if (fluidRolls != null) {
            for (FluidRoll fluidRoll : fluidRolls) {
                if (e.getRandom().nextDouble() < fluidRoll.chance) {
                    fluids.add(fluidRoll.fluid);
                }
            }
        }
        return fluids;
    }
    private record FluidRoll(double chance, Fluid fluid) {

    }
}
