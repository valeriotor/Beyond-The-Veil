package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock1by2;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class VijhissBlock extends ThinMultiBlock1by2 {

    protected static final VoxelShape SHAPE_LOWER;
    protected static final VoxelShape SHAPE_UPPER;

    static {
        double a = 0.0625;
        SHAPE_LOWER = Shapes.box(5*a, 5*a, 6*a, 9*a, 1, 11*a);
        SHAPE_UPPER = Shapes.box(5*a, 0, 6*a, 9*a, 4*a, 11*a);
    }

    public VijhissBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        Item item = stack.getItem();
        if (item == Items.GOLD_INGOT || item == Items.GOLD_BLOCK) {
            if (!pLevel.isClientSide) {
                boolean decrease = false;
                ItemStack toGive = ItemStack.EMPTY;
                if (item == Items.GOLD_INGOT) {
                    Iterable<Holder<Item>> tagOrEmpty = BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.VILLAGER_PLANTABLE_SEEDS);
                    List<Item> pool = new ArrayList<>();
                    tagOrEmpty.forEach(h -> pool.add(h.get()));
                    if (!pool.isEmpty()) {
                        decrease = true;
                        toGive = new ItemStack(pool.get(pPlayer.getRandom().nextInt(pool.size())));
                    }
                } else {
                    toGive = new ItemStack(Registration.MEGYDREA.get());
                }
                if (!pPlayer.getAbilities().instabuild && decrease) {
                    stack.shrink(1);
                }
                if (!toGive.isEmpty()) {
                    ItemHandlerHelper.giveItemToPlayer(pPlayer, toGive);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(getLevelProperty()) == 0 ? SHAPE_LOWER : SHAPE_UPPER;
    }
}
