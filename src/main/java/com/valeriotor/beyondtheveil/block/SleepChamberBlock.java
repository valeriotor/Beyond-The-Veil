package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.lib.BTVSimpleGuis;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class SleepChamberBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape SHAPE_LOWER;
    private static final VoxelShape SHAPE_UPPER;
    private static final VoxelShape[] SHAPE_LOWER_OPEN = new VoxelShape[4];
    private static final VoxelShape[] SHAPE_UPPER_OPEN = new VoxelShape[4];

    static {
        VoxelShape shape = Shapes.empty();
        VoxelShape shape_north = Shapes.box(0, 0.0625, 0, 1, 1, 0.0625);
        VoxelShape shape_south = Shapes.box(0, 0.0625, 1-0.0625, 1, 1, 1);
        VoxelShape shape_west = Shapes.box(0, 0.0625, 0.0625, 0.0625, 1, 1-0.0625);
        VoxelShape shape_east = Shapes.box(1-0.0625, 0.0625, 0.0625, 1, 1, 1-0.0625);
        SHAPE_LOWER = Shapes.or(shape_north, shape_south, shape_east, shape_west, Shapes.box(0, 0, 0, 1, 0.0625, 1));
        SHAPE_UPPER = Shapes.or(Shapes.or(shape_north, shape_south, shape_east, shape_west).move(0,-0.0625,0), Shapes.box(0, 1-0.0625, 0, 1, 1, 1));
        VoxelShape[] shapes = {shape_north, shape_south, shape_west, shape_east};
        for (int i = 0; i < 4; i++) {
            VoxelShape base = Shapes.empty();
            for (int j = 0; j < 4; j++) {
                if (i != j) {
                    base = Shapes.or(base, shapes[j]);
                }
            }
            SHAPE_LOWER_OPEN[i] = Shapes.or(base, Shapes.box(0, 0, 0, 1, 0.0625, 1));
            SHAPE_UPPER_OPEN[i] = Shapes.or(base.move(0,-0.0625,0), Shapes.box(0, 1-0.0625, 0, 1, 1, 1));
        }
    }

    public SleepChamberBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if (state.getValue(OPEN)) {
            int direction = state.getValue(FACING).getOpposite().ordinal()-2;
            return state.getValue(HALF) == DoubleBlockHalf.LOWER ? SHAPE_LOWER_OPEN[direction] : SHAPE_UPPER_OPEN[direction];
        }
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? SHAPE_LOWER : SHAPE_UPPER;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter p_60579_, BlockPos p_60580_) {
        if (state.getValue(OPEN)) {
            int direction = state.getValue(FACING).ordinal()-2;
            return state.getValue(HALF) == DoubleBlockHalf.LOWER ? SHAPE_LOWER_OPEN[direction] : SHAPE_UPPER_OPEN[direction];
        }
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? SHAPE_LOWER : SHAPE_UPPER;
    }

    @Override
    public BlockState updateShape(BlockState thisState, Direction direction, BlockState otherState, LevelAccessor level, BlockPos thisPos, BlockPos otherPos) {
        DoubleBlockHalf doubleblockhalf = thisState.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return otherState.is(this) && otherState.getValue(HALF) != doubleblockhalf ? thisState.setValue(FACING, otherState.getValue(FACING)).setValue(OPEN, otherState.getValue(OPEN)) : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(thisState, direction, otherState, level, thisPos, otherPos);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        state = state.cycle(OPEN);
        level.setBlock(pos, state, 10);
        level.levelEvent(player, state.getValue(OPEN) ? 1006 : 1012, pos, 0);
        level.gameEvent(player, state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        if (!state.getValue(OPEN) && level.isClientSide && hand == InteractionHand.MAIN_HAND && getPlayerInside(level, pos) == player) {
            ClientMethods.openSimpleGui(BTVSimpleGuis.SLEEP_CHAMBER);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);

    }

    public Player getPlayerInside(Level w, BlockPos pos) {
        List<Player> ps = w.getEntitiesOfClass(Player.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));
        for(Player p : ps) {
            return p;
        }
        return null;
    }

    //@Override
    //public void playerWillDestroy(Level p_52755_, BlockPos p_52756_, BlockState p_52757_, Player p_52758_) {
    //    if (!p_52755_.isClientSide && p_52758_.isCreative()) {
    //        DoublePlantBlock.preventCreativeDropFromBottomPart(p_52755_, p_52756_, p_52757_, p_52758_);
    //    }
//
    //    super.playerWillDestroy(p_52755_, p_52756_, p_52757_, p_52758_);
    //}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, HALF);
    }
}
