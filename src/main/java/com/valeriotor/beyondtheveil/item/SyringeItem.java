package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.AlembicsBlock;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tile.AlembicsBE;
import com.valeriotor.beyondtheveil.tile.FlaskBE;
import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SyringeItem extends SurgeryItem {

    public SyringeItem() {
        super(SurgeryItemType.SYRINGE);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidHandlerItemStack(stack, 100);
    }

    @Override
    protected void interactWithBE(Player p, Level level, BlockPos pos, BlockState lookedAtState, BlockEntity blockEntity, BlockHitResult bhr) {
        ItemStack itemStack = p.getItemInHand(InteractionHand.MAIN_HAND);
        if (!level.isClientSide) {
            Optional<FluidStack> fluidContained = FluidUtil.getFluidContained(itemStack);
            fluidContained.ifPresent(fluidStack -> {
                String apply = PlayerDataLib.DISCOVERED_FLUID.apply(fluidStack.getFluid());
                if (!DataUtil.getBoolean(p, apply)) {
                    DataUtil.setBooleanOnServerAndSync(p, apply, true, false);
                }
            });
        }
        if (lookedAtState.getBlock() == Registration.FLASK_SHELF.get()) {
            BlockPos centerPos = Registration.FLASK_SHELF.get().findCenter(pos, lookedAtState);
            if (level.getBlockEntity(centerPos) instanceof FlaskShelfBE be) {
                Item held = itemStack.getItem();
                be.interactLiquid(level, pos, p, InteractionHand.MAIN_HAND, bhr);
            }
        } else if (lookedAtState.getBlock() == Registration.ALEMBICS.get()) {
            BlockPos centerPos = Registration.ALEMBICS.get().findCenter(pos, lookedAtState);
            if (level.getBlockEntity(centerPos) instanceof AlembicsBE be) {
                Item held = itemStack.getItem();
                int hitAlembic = AlembicsBlock.getHitAlembic(bhr, centerPos, level.getBlockState(centerPos).getValue(AlembicsBlock.FACING));
                be.interactServer(p, InteractionHand.MAIN_HAND, hitAlembic);
            }
        } else if (lookedAtState.getBlock() instanceof FlaskBlock && blockEntity instanceof FlaskBE flaskBE) {
            flaskBE.tryFillFromItem(level, pos, p, InteractionHand.MAIN_HAND, bhr);
        } else {
            super.interactWithBE(p, level, pos, lookedAtState, blockEntity, bhr);
        }
        if (!level.isClientSide) {
            Optional<FluidStack> fluidContained = FluidUtil.getFluidContained(itemStack);
            fluidContained.ifPresent(fluidStack -> {
                String apply = PlayerDataLib.DISCOVERED_FLUID.apply(fluidStack.getFluid());
                if (!DataUtil.getBoolean(p, apply)) {
                    DataUtil.setBooleanOnServerAndSync(p, apply, true, false);
                }
            });
        }
    }

    //
    //@Override
    //public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
    //    return true;
    //}
//
    //@Override
    //public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
    //    super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    //}

    //@Override
    //public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
    //    return true;
    //}
}
