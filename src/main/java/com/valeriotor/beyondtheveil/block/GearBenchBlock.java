package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.container.GearBenchContainer;
import com.valeriotor.beyondtheveil.tile.GearBenchBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class GearBenchBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape SHAPE;
    public static final String GUI_GEAR_BENCH = "gui.gearbench.name";

    static {
        VoxelShape SHAPE_TOP = Shapes.box(0.0625, 0.75, 0.0625, 1 - 0.0625, 1 - 0.0625, 1 - 0.0625);
        VoxelShape SHAPE_LEG = Shapes.box(0.0625 * 6, 0, 0.0625 * 6, 0.0625 * 10, 0.5, 0.0625 * 10);
        SHAPE = Shapes.or(SHAPE_TOP, SHAPE_LEG);
    }

    public GearBenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter p_60579_, BlockPos p_60580_) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof GearBenchBE) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable(GUI_GEAR_BENCH);
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player player) {
                        return new GearBenchContainer(pContainerId, pPos, pPlayerInventory, player);
                    }
                };
                NetworkHooks.openScreen((ServerPlayer) pPlayer, containerProvider, be.getBlockPos());
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GearBenchBE(pPos, pState);
    }


}

