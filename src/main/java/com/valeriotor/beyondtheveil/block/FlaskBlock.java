package com.valeriotor.beyondtheveil.block;

import com.google.common.collect.Sets;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.tile.FlaskBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FlaskBlock extends Block implements EntityBlock {

    public static final Map<FlaskSize, FlaskBlock> sizeToBlock = new EnumMap<>(FlaskSize.class);
    private static final double a = 0.0625;

    private static final VoxelShape SHAPE = Shapes.box(0.0625 * 4, 0, 0.0625 * 4, 0.0625 * 12, 0.0625 * 11, 0.0625 * 12);
    private static final VoxelShape SHAPE_LARGE;
    private static final VoxelShape SHAPE_MEDIUM;
    private static final VoxelShape SHAPE_SMALL;
    private static final VoxelShape SHAPE_ITEM;
    public static final double[] LARGE1 = {5*a, 0, 5*a, 11*a, a/2, 11*a};
    public static final double[] LARGE2 = {5 * a, a / 2, 4.5 * a, 11 * a, 8.5*a, 11.5 * a};
    public static final double[] LARGE3 = {4.5 * a, a / 2, 5 * a, 11.5 * a, 8.5*a, 11 * a};
    public static final double[] LARGE4 = {5*a, 8.5*a, 5*a, 11*a, 9*a, 11*a};
    public static final double[] LARGE5 = {5.5*a, 9*a, 5.5*a, 10.5*a, 9.5*a, 10.5*a};
    public static final double[] LARGE6 = {6.5*a, 9.5*a, 6.5*a, 9.5*a, 10.5*a, 9.5*a};
    public static final double[] MEDIUM1 = {6*a, 0, 6*a, 10*a, a/2, 10*a};
    public static final double[] MEDIUM2 = {6 * a, a / 2, 5.5 * a, 10 * a, 6.5*a, 10.5 * a};
    public static final double[] MEDIUM3 = {5.5 * a, a / 2, 6 * a, 10.5 * a, 6.5*a, 10 * a};
    public static final double[] MEDIUM4 = {6*a, 6.5*a, 6*a, 10*a, 7*a, 10*a};
    public static final double[] MEDIUM5 = {6.5*a, 7*a, 6.5*a, 9.5*a, 7.5*a, 9.5*a};
    public static final double[] MEDIUM6 = {7*a, 7.5*a, 7*a, 9*a, 8*a, 9*a};
    public static final double[] SMALL1 = {7*a, 0, 7*a, 9*a, a/4, 9*a};
    public static final double[] SMALL2 = {7 * a, a / 4, 6.75 * a, 9 * a, 4.5*a, 9.25 * a};
    public static final double[] SMALL3 = {6.75 * a, a / 4, 7 * a, 9.25 * a, 4.5*a, 9 * a};
    public static final double[] SMALL4 = {7*a, 4.5*a, 7*a, 9*a, 4.75*a, 9*a};
    public static final double[] SMALL5 = {7.25*a, 4.75*a, 7.25*a, 8.75*a, 5*a, 8.75*a};
    public static final double[] SMALL6 = {7.5*a, 5*a, 7.5*a, 8.5*a, 5.5*a, 8.5*a};
    public static final double[] ITEM_SIMPLE = FlaskSize.ITEM.simpleShape;
    public static final double[] LARGE_SIMPLE = FlaskSize.LARGE.simpleShape;
    public static final double[] MEDIUM_SIMPLE = FlaskSize.MEDIUM.simpleShape;
    public static final double[] SMALL_SIMPLE = FlaskSize.SMALL.simpleShape;

    static {
        VoxelShape shapeLarge = Shapes.box(LARGE1[0],LARGE1[1],LARGE1[2],LARGE1[3],LARGE1[4],LARGE1[5]);
        shapeLarge = Shapes.or(shapeLarge, Shapes.box(LARGE2[0],LARGE2[1],LARGE2[2],LARGE2[3],LARGE2[4],LARGE2[5]));
        shapeLarge = Shapes.or(shapeLarge, Shapes.box(LARGE3[0],LARGE3[1],LARGE3[2],LARGE3[3],LARGE3[4],LARGE3[5]));
        shapeLarge = Shapes.or(shapeLarge, Shapes.box(LARGE4[0],LARGE4[1],LARGE4[2],LARGE4[3],LARGE4[4],LARGE4[5]));
        shapeLarge = Shapes.or(shapeLarge, Shapes.box(LARGE5[0],LARGE5[1],LARGE5[2],LARGE5[3],LARGE5[4],LARGE5[5]));
        shapeLarge = Shapes.or(shapeLarge, Shapes.box(LARGE6[0],LARGE6[1],LARGE6[2],LARGE6[3],LARGE6[4],LARGE6[5]));

        VoxelShape shapeMedium = Shapes.box(MEDIUM1[0],MEDIUM1[1],MEDIUM1[2],MEDIUM1[3],MEDIUM1[4],MEDIUM1[5]);
        shapeMedium = Shapes.or(shapeMedium, Shapes.box(MEDIUM2[0],MEDIUM2[1],MEDIUM2[2],MEDIUM2[3],MEDIUM2[4],MEDIUM2[5]));
        shapeMedium = Shapes.or(shapeMedium, Shapes.box(MEDIUM3[0],MEDIUM3[1],MEDIUM3[2],MEDIUM3[3],MEDIUM3[4],MEDIUM3[5]));
        shapeMedium = Shapes.or(shapeMedium, Shapes.box(MEDIUM4[0],MEDIUM4[1],MEDIUM4[2],MEDIUM4[3],MEDIUM4[4],MEDIUM4[5]));
        shapeMedium = Shapes.or(shapeMedium, Shapes.box(MEDIUM5[0],MEDIUM5[1],MEDIUM5[2],MEDIUM5[3],MEDIUM5[4],MEDIUM5[5]));
        shapeMedium = Shapes.or(shapeMedium, Shapes.box(MEDIUM6[0],MEDIUM6[1],MEDIUM6[2],MEDIUM6[3],MEDIUM6[4],MEDIUM6[5]));

        VoxelShape shapeSmall = Shapes.box(SMALL1[0],SMALL1[1],SMALL1[2],SMALL1[3],SMALL1[4],SMALL1[5]);
        shapeSmall = Shapes.or(shapeSmall, Shapes.box(SMALL2[0],SMALL2[1],SMALL2[2],SMALL2[3],SMALL2[4],SMALL2[5]));
        shapeSmall = Shapes.or(shapeSmall, Shapes.box(SMALL3[0],SMALL3[1],SMALL3[2],SMALL3[3],SMALL3[4],SMALL3[5]));
        shapeSmall = Shapes.or(shapeSmall, Shapes.box(SMALL4[0],SMALL4[1],SMALL4[2],SMALL4[3],SMALL4[4],SMALL4[5]));
        shapeSmall = Shapes.or(shapeSmall, Shapes.box(SMALL5[0],SMALL5[1],SMALL5[2],SMALL5[3],SMALL5[4],SMALL5[5]));
        shapeSmall = Shapes.or(shapeSmall, Shapes.box(SMALL6[0],SMALL6[1],SMALL6[2],SMALL6[3],SMALL6[4],SMALL6[5]));

        SHAPE_LARGE = shapeLarge;
        SHAPE_MEDIUM = shapeMedium;
        SHAPE_SMALL = shapeSmall;
        SHAPE_ITEM = shapeSmall;
    }

    public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 2); // 0 base, 1 wrong, 2 selected


    public final FlaskSize size;

    public FlaskBlock(Properties properties, FlaskSize size) {
        super(properties);
        this.size = size;
        sizeToBlock.put(size, this);
        this.registerDefaultState(this.stateDefinition.any().setValue(COLOR, 0));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return switch (size) {
            case SMALL -> SHAPE_SMALL;
            case MEDIUM -> SHAPE_MEDIUM;
            case LARGE -> SHAPE_LARGE;
            case ITEM -> SHAPE_ITEM;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLOR);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof FlaskBE be) {
            return be.tryFillFromItem(pLevel, pPos, pPlayer, pHand, pHit);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FlaskBE(pPos, pState);
    }

    public enum FlaskSize {
        SMALL(1000, new double[]{6.75*a, 0, 6.75*a, 9.25*a, 5.5*a, 9.25*a}, a/4, 4.5*a, 7*a, 9*a, false),
        MEDIUM(2500, new double[]{5.5*a, 0, 5.5*a, 10.5*a, 8*a, 10.5*a}, a/2, 6.5*a, 6*a, 10*a, false),
        LARGE(5000, new double[]{4.5*a, 0, 4.5*a, 11.5*a, 10.5*a, 11.5*a}, a/2, 8.5*a, 5*a, 11*a, false),
        ITEM(0, new double[]{5.5*a, 0, 5.5*a, 10.5*a, 8*a, 10.5*a}, a/2, 8.5*a, 5*a, 11*a, true);

        private final int capacity;
        private final double[] simpleShape;
        private final double minRenderHeight;
        private final double maxRenderHeight;
        private final double minRenderHorizontal;
        private final double maxRenderHorizontal;
        private final boolean allowsItems;

        FlaskSize(int capacity, double[] simpleShape, double minRenderHeight, double maxRenderHeight, double minRenderHorizontal, double maxRenderHorizontal, boolean allowsItems) {
            this.capacity = capacity;
            this.simpleShape = simpleShape;
            this.minRenderHeight = minRenderHeight;
            this.maxRenderHeight = maxRenderHeight;
            this.minRenderHorizontal = minRenderHorizontal;
            this.maxRenderHorizontal = maxRenderHorizontal;
            this.allowsItems = allowsItems;
        }

        public double[] getSimpleShape() {
            return simpleShape;
        }

        public int getCapacity() {
            return capacity;
        }

        public double getMinRenderHeight() {
            return minRenderHeight;
        }

        public double getMaxRenderHeight() {
            return maxRenderHeight;
        }

        public double getMinRenderHorizontal() {
            return minRenderHorizontal;
        }

        public double getMaxRenderHorizontal() {
            return maxRenderHorizontal;
        }

        public boolean allowsItems() {
            return allowsItems;
        }
    }


}
