package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DreamSeedsItem extends ItemNameBlockItem {

    public DreamSeedsItem(CropBlock pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level l = context.getLevel();
        BlockState state = l.getBlockState(context.getClickedPos());
        Block b = state.getBlock();
        if (b == Blocks.DIRT || b == Blocks.GRASS_BLOCK) {
            if (!l.isClientSide) {
                if (stack.getItem() == Registration.GHOST_WEED_SEEDS.get()) {
                    l.setBlock(context.getClickedPos(), Registration.GHOST_GRASS.get().defaultBlockState(), 3);
                } else if (stack.getItem() == Registration.REDSTONE_WEED_SEEDS.get()) {
                    l.setBlock(context.getClickedPos(), Registration.REDSTONE_GRASS.get().defaultBlockState(), 3);
                } else {
                    l.setBlock(context.getClickedPos(), Blocks.GRASS_BLOCK.defaultBlockState(), 3);
                }
            }
            l.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1, 1);
            return InteractionResult.SUCCESS;
        }
        return super.onItemUseFirst(stack, context);
    }
}
