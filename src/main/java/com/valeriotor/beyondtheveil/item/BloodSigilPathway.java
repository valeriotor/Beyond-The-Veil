package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BloodSigilPathway  extends Item {

    public BloodSigilPathway() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 56;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof ServerPlayer sp && sp.getServer() != null) {
            stack.getOrCreateTag().putLong("area", pLivingEntity.blockPosition().asLong());
            stack.getTag().putString("dimension", sp.level().dimension().location().getPath());
            Optional<ResourceKey<Biome>> left = sp.level().getBiome(pLivingEntity.blockPosition()).unwrap().left();
            if (left.isPresent()) {
                stack.getTag().putString("biomeNamespace", left.get().location().getNamespace());
                stack.getTag().putString("biomeName", left.get().location().getPath());
            }
            pLevel.playSound(null, sp.blockPosition(), BTVSounds.HEART_RIP.get(), SoundSource.PLAYERS, 1, 1);
        }
        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand pUsedHand) {
        ItemStack stack = player.getItemInHand(pUsedHand);
        CompoundTag tag = stack.getTag();
        if (player instanceof ServerPlayer sp && sp.getServer() != null && (tag == null || !tag.contains("area"))) {
            player.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(stack);
        }
        return super.use(pLevel, player, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();
        if (tag != null && tag.contains("area")) {
            BlockPos area = BlockPos.of(tag.getLong("area"));
            //ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("dimension")));
            pTooltipComponents.add(Component.translatable("tooltip.sigil.area", area.getX(), area.getY(), area.getZ()));
            if (tag.contains("biomeName") && tag.contains("biomeNamespace")) {
                pTooltipComponents.add(Component.translatable("biome." + tag.getString("biomeNamespace") + "." + tag.getString("biomeName")));
            }
        }
    }
}
