package com.valeriotor.beyondtheveil.rituals;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class RitualModifierRegistry {

    private static final Map<Item, BiConsumer<RitualStatus, ItemStack>> MODIFIERS = new HashMap<>();

    static {
        MODIFIERS.put(Registration.SIGIL_PLAYER.get(), (ritualStatus, stack) -> {
            if (stack.getItem() == Registration.SIGIL_PLAYER.get()) {
                CompoundTag tag = stack.getOrCreateTag();
                if (tag.contains("player")) {
                    UUID player = tag.getUUID("player");
                    ritualStatus.setTargetPosition(sl -> {
                        Player targetPlayer = sl.getServer().getPlayerList().getPlayer(player);
                        if (targetPlayer != null) {
                            return new Tuple<>(targetPlayer.position(), targetPlayer.level().dimension());
                        }
                        return null;
                    });
                }
            };
        });
        MODIFIERS.put(Registration.SIGIL_PATHWAY.get(), (ritualStatus, stack) -> {
            if(stack.getItem() == Registration.SIGIL_PATHWAY.get()) {
                CompoundTag tag = stack.getOrCreateTag();
                if (tag.contains("area") && tag.contains("dimension")) {
                    ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("dimension")));
                    BlockPos area = BlockPos.of(tag.getLong("area"));
                    ritualStatus.setTargetPosition(sl -> new Tuple<>(area.getCenter(), dimension));
                }
            }
        });
        MODIFIERS.put(Items.EMERALD, (ritualStatus, stack) -> ritualStatus.increaseSaveItemChance(0.3));
        MODIFIERS.put(Items.GOLD_INGOT, (ritualStatus, stack) -> ritualStatus.increaseSaveItemChance(0.2));
        MODIFIERS.put(Items.QUARTZ_BLOCK, (ritualStatus, stack) -> ritualStatus.increaseSaveItemChance(0.2));
        MODIFIERS.put(Items.SCULK_CATALYST, (status, stack) -> status.decreaseInstabilityRate());
    }

    public static boolean isModifier(Item item) {
        return MODIFIERS.containsKey(item);
    }

    public static void modifierEffect(RitualStatus status, ItemStack stack) {
        BiConsumer<RitualStatus, ItemStack> consumer = MODIFIERS.get(stack.getItem());
        if (consumer != null) {
            consumer.accept(status, stack);
        }
    }

}
