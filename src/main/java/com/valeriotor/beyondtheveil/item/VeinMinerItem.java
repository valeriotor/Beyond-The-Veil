package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VeinMinerItem extends PickaxeItem {
    public VeinMinerItem(int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(new VeinMinerTier(), pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        if (!player.level().isClientSide) {
            if (player.level().getBlockState(pos).is(Tags.Blocks.ORES)) {
                Block block = player.level().getBlockState(pos).getBlock();
                Set<BlockPos> toMine = new HashSet<>();
                recurse(player.level(), pos, block, new HashSet<>(), toMine, 0);
                for (BlockPos blockPos : toMine) {
                    player.level().destroyBlock(blockPos, true, player);
                }
            }
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    private static final int MAX_LAYER = 10;

    private static void recurse(Level l, BlockPos pos, Block block, Set<BlockPos> seen, Set<BlockPos> toMine, int layer) {
        if (layer > MAX_LAYER) {
            return;
        }
        List<BlockPos> toRecurse = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos newPos = pos.offset(x, y, z);
                    if (!seen.contains(newPos) && l.getBlockState(newPos).getBlock() == block && !(x == y && y == z && z == 0)) {
                        toRecurse.add(newPos);
                        toMine.add(newPos);
                        seen.add(newPos);
                    }
                }
            }
        }
        for (BlockPos blockPos : toRecurse) {
            recurse(l, blockPos, block, seen, toMine, layer + 1);
        }
    }

    private static class VeinMinerTier implements Tier {
        @Override
        public int getUses() {
            return 240;
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
            return Ingredient.of(Registration.HEART.get());
        }

        @Override
        public @Nullable TagKey<Block> getTag() {
            return BlockTags.NEEDS_DIAMOND_TOOL;
        }
    }

}
