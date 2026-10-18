package com.valeriotor.beyondtheveil.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ScalpelItem extends SurgeryItem{

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ScalpelItem(SurgeryItemType type) {
        super(type, new Properties().durability(200));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 4, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public static List<ItemStack> getExtraDropsForEntity(LivingEntity e, LivingEntity attacker, ItemStack itemInHand) {
        int enchantmentLevel = itemInHand.getEnchantmentLevel(Enchantments.MOB_LOOTING);
        float bonus = enchantmentLevel * 0.05F;
        RandomSource r = e.getRandom();
        List<ItemStack> stacks = new ArrayList<>();
        if (e instanceof AbstractSkeleton) {
            if (r.nextFloat() < 0.5F + bonus) {
                stacks.add(new ItemStack(Registration.MARROW_GLAND.get()));
            }
        }
        if (e instanceof Animal || e instanceof Piglin || e instanceof Spider) {
            if (attacker instanceof Player p && !DataUtil.getBoolean(p, PlayerDataLib.found_bladder.name())) {
                bonus = 1;
                DataUtil.setBoolean(p, PlayerDataLib.found_bladder.name(), true, false);
            }
            if (r.nextFloat() < 0.05F + bonus) {
                stacks.add(new ItemStack(Registration.EMPTY_BLADDER.get()));
            }
        }
        if (e instanceof Creeper) {
            if (r.nextFloat() < 0.75F + bonus) {
                stacks.add(new ItemStack(Registration.GUNPOWDER_BLADDER.get()));
            }
        }
        if (e instanceof Spider) {
            if (r.nextFloat() < 0.3F + bonus) {
                stacks.add(new ItemStack(Registration.SILK_GLAND.get()));
            }
        }
        return stacks;
    }

    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        if (pState.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            return pState.is(BlockTags.SWORD_EFFICIENT) ? 1.5F : 1.0F;
        }
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
            pStack.hurtAndBreak(2, pEntityLiving, (p_43276_) -> {
                p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    public boolean isCorrectToolForDrops(BlockState pBlock) {
        return pBlock.is(Blocks.COBWEB);
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category == EnchantmentCategory.WEAPON;
    }
}
