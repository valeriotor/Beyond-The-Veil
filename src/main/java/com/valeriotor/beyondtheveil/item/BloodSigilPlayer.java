package com.valeriotor.beyondtheveil.item;

import com.mojang.authlib.GameProfile;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.world.saved.PlayerSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BloodSigilPlayer extends Item {

    public BloodSigilPlayer() {
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
            PlayerSavedData data = PlayerSavedData.getInstance(sp.getServer().overworld());
            Triple<UUID, BlockPos, Boolean> tuple = data.closestBloodPointExcluding(sp);
            if (tuple != null && tuple.getMiddle().distSqr(sp.blockPosition()) < 9) {
                if (tuple.getRight()) {
                    data.removeDeath(tuple.getLeft());
                } else {
                    data.removeRespawn(tuple.getLeft());
                }
                CompoundTag tag = stack.getOrCreateTag();
                tag.putUUID("player", tuple.getLeft());
                GameProfileCache cache = sp.getServer().getProfileCache();
                if (cache != null) {
                    Optional<GameProfile> gameProfile = cache.get(tuple.getLeft());
                    gameProfile.ifPresent(profile -> tag.putString("username", profile.getName()));
                }
                pLevel.playSound(null, sp.blockPosition(), BTVSounds.HEART_RIP.get(), SoundSource.PLAYERS, 1, 1);
            }
        }
        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand pUsedHand) {
        ItemStack stack = player.getItemInHand(pUsedHand);
        CompoundTag tag = stack.getTag();
        if (player instanceof ServerPlayer sp && sp.getServer() != null && (tag == null || !tag.contains("player"))) {
            PlayerSavedData data = PlayerSavedData.getInstance(sp.getServer().overworld());
            Triple<UUID, BlockPos, Boolean> tuple = data.closestBloodPointExcluding(sp);
            if (tuple != null && tuple.getMiddle().distSqr(sp.blockPosition()) < 9) {
                player.startUsingItem(pUsedHand);
                return InteractionResultHolder.consume(stack);
            }
        }
        return super.use(pLevel, player, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();
        if (tag != null && tag.contains("username")) {
            String name = tag.getString("username");
            pTooltipComponents.add(Component.translatable("tooltip.sigil.player", name));
        }
    }
}
