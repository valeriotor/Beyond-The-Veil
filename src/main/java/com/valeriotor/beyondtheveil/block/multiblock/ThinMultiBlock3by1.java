package com.valeriotor.beyondtheveil.block.multiblock;

import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ThinMultiBlock3by1 extends ThinMultiBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;


    //public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 0);
    public static final IntegerProperty SIDE = IntegerProperty.create("side", 0, 2);

    public ThinMultiBlock3by1(Properties pProperties) {
        super(pProperties, 1, (2 - 1) / 2, 1);
    }

    @Override
    public IntegerProperty getSideProperty() {
        return SIDE;
    }

    @Override
    public IntegerProperty getLevelProperty() {
        return null;
    }
}
