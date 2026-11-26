package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock1by2;
import com.valeriotor.beyondtheveil.tile.FleboBE;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.ColorTriplet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FleboBlock extends ThinMultiBlock1by2 implements EntityBlock {
    protected static final VoxelShape SHAPE_STICK_DOWN;
    protected static final VoxelShape SHAPE_UP_X;
    protected static final VoxelShape SHAPE_UP_Z;

    static {
        double a = 0.0625;
        VoxelShape s1 = Shapes.box(7.5 * a, a, 7.5 * a, 8.5 * a, 1, 8.5 * a);
        VoxelShape s2 = Shapes.box(7.5 * a, 0, 7.5 * a, 8.5 * a, 1, 8.5 * a);

        VoxelShape s3 = Shapes.box(1.75 * a, 2.5 * a, 6.5 * a, 4.75 * a, 7.5 * a, 9.5 * a);
        s3 = Shapes.or(s3, Shapes.box(11.25 * a, 2.5 * a, 6.5 * a, 14.25 * a, 7.5 * a, 9.5 * a));


        VoxelShape s4 = Shapes.box(6.5 * a, 2.5 * a, 1.75 * a, 9.5 * a, 7.5 * a, 4.75 * a);
        s4 = Shapes.or(s4, Shapes.box(6.5 * a, 2.5 * a, 11.25 * a, 9.5 * a, 7.5 * a, 14.25 * a));

        SHAPE_STICK_DOWN = s1;
        SHAPE_UP_X = Shapes.or(s2, s3);
        SHAPE_UP_Z = Shapes.or(s2, s4);
    }


    public FleboBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(getLevelProperty()) == 0)
            return SHAPE_STICK_DOWN;
        return pState.getValue(FACING).getAxis() == Direction.Axis.Z ? SHAPE_UP_X : SHAPE_UP_Z;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(getLevelProperty()) == 0 ? new FleboBE(pPos, pState) : null;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        CompoundTag tag = BlockItem.getBlockEntityData(pStack);
        if (tag != null) {
            if (tag.contains("first")) {
                ColorTriplet colorTriplet = ColorTriplet.fromTag(tag);
                Component[] colors = new Component[3];
                for (int i = 0; i < 3; i++) {
                    DyeColor color = colorTriplet.index(i);
                    colors[i] = color == null ? Component.translatable("tooltip.flebo.color_none") : Component.translatable("color.minecraft." + color.getName());
                }
                MutableComponent translatable = Component.translatable("tooltip.flebo.colors", colors[0], colors[1], colors[2]);
                pTooltip.add(translatable);
                if (tag.contains("username", Tag.TAG_STRING)) {
                    pTooltip.add(Component.translatable("tooltip.flebo.owner", tag.getString("username")));
                }
            }
        }
    }
}
