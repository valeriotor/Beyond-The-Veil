package com.valeriotor.beyondtheveil.block.multiblock;

import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

/**
 * Can be as tall and as wide as you wish, but only 1 block "deep" (i.e. in the direction where the player is facing
 * when placing it)
 */
public abstract class ThinMultiBlock3by2 extends ThinMultiBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;


    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 2 - 1);
    public static final IntegerProperty SIDE = IntegerProperty.create("side", 0, 2);

    public ThinMultiBlock3by2(Properties pProperties) {
        super(pProperties, 2, (2 - 1) / 2, 1);
    }

    @Override
    public IntegerProperty getSideProperty() {
        return SIDE;
    }

    @Override
    public IntegerProperty getLevelProperty() {
        return LEVEL;
    }


}
