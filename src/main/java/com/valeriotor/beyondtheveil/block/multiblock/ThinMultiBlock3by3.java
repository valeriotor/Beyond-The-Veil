package com.valeriotor.beyondtheveil.block.multiblock;

import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * Can be as tall and as wide as you wish, but only 1 block "deep" (i.e. in the direction where the player is facing
 * when placing it)
 */
public abstract class ThinMultiBlock3by3 extends ThinMultiBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;


    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 3 - 1);
    public static final IntegerProperty SIDE = IntegerProperty.create("side", 0, 2);

    public ThinMultiBlock3by3(Properties pProperties) {
        super(pProperties, 3, (2 - 1) / 2, 1);
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
