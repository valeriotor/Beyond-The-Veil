package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FlaskShelfBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty CENTER = BooleanProperty.create("center");
    private static final VoxelShape BASE_SHAPE = Shapes.box(0, 0, 0, 1, 0.125D, 1);

    public FlaskShelfBlock(Properties properties) {
        super(BlockBehaviour.Properties.of(Material.WOOD).strength(5.0F, 7.0F).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CENTER, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return getOcclusionShape(state, p_60556_, p_60557_);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return BASE_SHAPE;
    }

    @Override
    public boolean hasDynamicShape() {
        return super.hasDynamicShape();
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        System.out.println(pHit.getLocation());
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return super.getBlockSupportShape(pState, pReader, pPos);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return super.getInteractionShape(pState, pLevel, pPos);
    }

    @Override
    public StateDefinition<Block, BlockState> getStateDefinition() {
        return super.getStateDefinition();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(CENTER);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        Direction opposite = context.getHorizontalDirection().getOpposite();
        for (int i = -1; i <= 1; i++) {
            for (int y = 0; y < 3; y++) {
                int x = opposite.getAxis() == Direction.Axis.X ? 0 : i;
                int z = opposite.getAxis() == Direction.Axis.Z ? 0 : i;
                if (!level.getBlockState(blockpos.offset(x, y, z)).canBeReplaced(context)) {
                    return null;
                }
            }
        }
        return defaultBlockState().setValue(FACING, opposite).setValue(CENTER, false);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        for (int i = -1; i <= 1; i++) {
            for (int y = 0; y < 3; y++) {
                if(i == 0 && y == 0) continue;
                int x = pState.getValue(FACING).getAxis() == Direction.Axis.X ? 0 : i;
                int z = pState.getValue(FACING).getAxis() == Direction.Axis.Z ? 0 : i;
                pLevel.setBlock(pPos.offset(x, y, z), pState.setValue(CENTER, i == 0 && y == 1), 3);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(CENTER) ? new FlaskShelfBE(pPos, pState) : null; // TODO change second one to set it as simple
    }
}
