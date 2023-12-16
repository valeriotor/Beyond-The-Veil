package com.valeriotor.beyondtheveil.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.EnumMap;
import java.util.Map;

public class FlaskBlock extends Block {

    public static final Map<FlaskSize, FlaskBlock> sizeToBlock = new EnumMap<>(FlaskSize.class);
    private static final double a = 0.0625;

    private static final VoxelShape SHAPE = Shapes.box(0.0625 * 4, 0, 0.0625 * 4, 0.0625 * 12, 0.0625 * 11, 0.0625 * 12);
    private static final VoxelShape SHAPE_LARGE;
    private static final VoxelShape SHAPE_MEDIUM;
    private static final VoxelShape SHAPE_SMALL;

    static {
        VoxelShape shapeLarge = Shapes.box(5*a, 0, 5*a, 11*a, a/2, 11*a);
        shapeLarge = Shapes.or(shapeLarge, Shapes.box(5 * a, a / 2, 4.5 * a, 11 * a, 8.5*a, 11.5 * a));
        shapeLarge = Shapes.or(shapeLarge, Shapes.box(4.5 * a, a / 2, 5 * a, 11.5 * a, 8.5*a, 11 * a));
        shapeLarge = Shapes.or(shapeLarge, Shapes.box(5*a, 8.5*a, 5*a, 11*a, 9*a, 11*a));
        shapeLarge = Shapes.or(shapeLarge, Shapes.box(5.5*a, 9*a, 5.5*a, 10.5*a, 9.5*a, 10.5*a));
        shapeLarge = Shapes.or(shapeLarge, Shapes.box(6.5*a, 9.5*a, 6.5*a, 9.5*a, 10.5*a, 9.5*a));

        VoxelShape shapeMedium = Shapes.box(6*a, 0, 6*a, 10*a, a/2, 10*a);
        shapeMedium = Shapes.or(shapeMedium, Shapes.box(6 * a, a / 2, 5.5 * a, 10 * a, 6.5*a, 10.5 * a));
        shapeMedium = Shapes.or(shapeMedium, Shapes.box(5.5 * a, a / 2, 6 * a, 10.5 * a, 6.5*a, 10 * a));
        shapeMedium = Shapes.or(shapeMedium, Shapes.box(6*a, 6.5*a, 6*a, 10*a, 7*a, 10*a));
        shapeMedium = Shapes.or(shapeMedium, Shapes.box(6.5*a, 7*a, 6.5*a, 9.5*a, 7.5*a, 9.5*a));
        shapeMedium = Shapes.or(shapeMedium, Shapes.box(7*a, 7.5*a, 7*a, 9*a, 8*a, 9*a));

        VoxelShape shapeSmall = Shapes.box(7*a, 0, 7*a, 9*a, a/4, 9*a);
        shapeSmall = Shapes.or(shapeSmall, Shapes.box(7 * a, a / 4, 6.75 * a, 9 * a, 4.5*a, 9.25 * a));
        shapeSmall = Shapes.or(shapeSmall, Shapes.box(6.75 * a, a / 4, 7 * a, 9.25 * a, 4.5*a, 9 * a));
        shapeSmall = Shapes.or(shapeSmall, Shapes.box(7*a, 4.5*a, 7*a, 9*a, 4.75*a, 9*a));
        shapeSmall = Shapes.or(shapeSmall, Shapes.box(7.25*a, 4.75*a, 7.25*a, 8.75*a, 5*a, 8.75*a));
        shapeSmall = Shapes.or(shapeSmall, Shapes.box(7.5*a, 5*a, 7.5*a, 8.5*a, 5.5*a, 8.5*a));

        SHAPE_LARGE = shapeLarge;
        SHAPE_MEDIUM = shapeMedium;
        SHAPE_SMALL = shapeSmall;
    }

    public final FlaskSize size;

    public FlaskBlock(Properties properties, FlaskSize size) {
        super(properties);
        this.size = size;
        sizeToBlock.put(size, this);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return switch (size) {
            case SMALL -> SHAPE_SMALL;
            case MEDIUM -> SHAPE_MEDIUM;
            case LARGE -> SHAPE_LARGE;
        };
    }

    public enum FlaskSize {
        SMALL(100), MEDIUM(350), LARGE(1000);

        private final int capacity;

        FlaskSize(int capacity) {
            this.capacity = capacity;
        }
    }


}
