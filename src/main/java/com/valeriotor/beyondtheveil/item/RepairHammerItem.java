package com.valeriotor.beyondtheveil.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RepairHammerItem extends TieredItem {

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private static RepairHammerTier repairHammerTier;
    private final float attackDamageBaseline;

    public RepairHammerItem(int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(makeTier(), pProperties);
        this.attackDamageBaseline = pAttackDamageModifier + makeTier().getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", (double)this.attackDamageBaseline, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)pAttackSpeedModifier, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();

    }

    private static Tier makeTier() {
        if (repairHammerTier == null) {
            repairHammerTier = new RepairHammerTier();
        }
        return repairHammerTier;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof NautilusEntity nautilus) {
            if (!player.level().isClientSide && !(player.getVehicle() instanceof NautilusEntity)) {
                float v = player.getAttackStrengthScale(0.5F);
                nautilus.setDamage(Math.max(0, nautilus.getDamage() - NautilusEntity.TOTAL_HEALTH * 2 / 100F * v * v));
                player.level().playSound(null, player.getOnPos(), SoundEvents.CHAIN_HIT, SoundSource.PLAYERS);
                stack.hurtAndBreak(1, player, (p_41007_) -> {p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);});
            }
            return true;
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    private static class RepairHammerTier implements Tier {
        @Override
        public int getUses() {
            return 300;
        }

        @Override
        public float getSpeed() {
            return 8.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 3.0F;
        }

        @Override
        public int getLevel() {
            return 3;
        }

        @Override
        public int getEnchantmentValue() {
            return 10;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Items.IRON_INGOT);
        }

        @Override
        public @Nullable TagKey<Block> getTag() {
            return BlockTags.NEEDS_IRON_TOOL;
        }
    }
}
