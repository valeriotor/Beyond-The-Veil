package com.valeriotor.beyondtheveil.block.multiblock;

import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ThinMultiBlock1by2 extends ThinMultiBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;


    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 2 - 1);
    //public static final IntegerProperty SIDE = IntegerProperty.create("side", 0, 2);

    public ThinMultiBlock1by2(Properties pProperties) {
        super(pProperties, 2, 0, 0);
    }

    @Override
    public IntegerProperty getSideProperty() {
        return null;
    }

    @Override
    public IntegerProperty getLevelProperty() {
        return LEVEL;
    }
}
