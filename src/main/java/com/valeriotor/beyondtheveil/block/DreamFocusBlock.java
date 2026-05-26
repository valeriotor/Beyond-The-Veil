package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.interfaces.DyableFocus;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerData;
import com.valeriotor.beyondtheveil.entity.FletumEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.tile.DreamFocusBE;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public abstract class DreamFocusBlock extends Block implements EntityBlock, DyableFocus {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final int DREAM_FOCUS_TIME = 300;
    private final FocusType type;


    public DreamFocusBlock(Properties pProperties, FocusType type) {
        super(pProperties);
        this.type = type;
    }

    @Override
    public InteractionResult use(BlockState state, Level l, BlockPos pos, Player p, InteractionHand hand, BlockHitResult hit) {
        if (hand == InteractionHand.MAIN_HAND && !l.isClientSide && p instanceof ServerPlayer sp) {
            if (l.getBlockEntity(pos) instanceof DreamFocusBE be) {
                int fleti = type.countFleti(l, pos);
                if (p.getMainHandItem().isEmpty() && be.setPlayer(p) && p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).isPresent()) {
                    BlockPos ppos = p.blockPosition();
                    float xRot = p.getXRot();
                    float yRot = p.getYHeadRot();
                    be.clearList();
                    CrossSyncData cs = p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().get();
                    cs.getCrossSync().setDreamFocus(p, true);
                    BlockPos startPos = type.getStartPos(pos, state);
                    sp.connection.teleport(startPos.getX() + 0.5, startPos.getY() + 0.5, startPos.getZ() + 0.5, state.getValue(FACING).toYRot(), 0);

                    PlayerTimer.Builder builder = new PlayerTimer.Builder("dream_focus", DREAM_FOCUS_TIME)
                            //.addContinuousAction((player, playerTimer) -> ((ServerPlayer)player).connection.teleport())
                            .addContinuousAction((player, playerTimer) -> be.addPoint(player, player.position()))
                            .addContinuousAction((player, playerTimer) -> moveServerPlayer((ServerPlayer) player))
                            .addFinalActions((player, playerTimer) -> be.finish())
                            .addFinalActions((player, playerTimer) -> ((ServerPlayer) player).connection.teleport(ppos.getX(), ppos.getY(), ppos.getZ(), yRot, xRot))
                            .addFinalActions((player, playerTimer) -> player.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).ifPresent(data -> data.getCrossSync().setDreamFocus(player, false)))
                            .addEarlyFinish((player, playerTimer) -> player.isShiftKeyDown());

                    PlayerTimerData.for_(p).addTimer(builder.toTimer());
                    return InteractionResult.SUCCESS;
                } else if (p.getMainHandItem().getItem() == Registration.HELD_FLETUM.get() && fleti == 0 && type == FocusType.ITEM) {
                    FletumEntity fletum = BTVEntities.FLETUM.get().create(l);
                    if (fletum != null) {
                        p.getMainHandItem().shrink(1);
                        fletum.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                        fletum.setMaster(p);
                        l.addFreshEntity(fletum);
                    }
                    return InteractionResult.SUCCESS;
                } else {
                    DyeColor color = DyeColor.getColor(sp.getMainHandItem());
                    if (color != null) {
                        be.setDyeColor(color);
                    } else if (sp.getMainHandItem().getItem() == Items.CLAY_BALL) {
                        be.toggleShowPath();
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        if (l.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static void moveServerPlayer(ServerPlayer player) {
        Vec3 lookAngle = player.getLookAngle().scale(0.1);
        player.setDeltaMovement(lookAngle.x, lookAngle.y, lookAngle.z);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (type == FocusType.ITEM) {
            return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        }
        return super.getStateForPlacement(context);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DreamFocusBE(pPos, pState, type);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof DreamFocusBE be) {
                if (pLevel.isClientSide) {
                    be.tickClient();
                } else {
                    be.tickServer();
                }
            }
        };
    }

    public static class DreamFocusItemBlock extends DreamFocusBlock {

        public DreamFocusItemBlock(Properties pProperties) {
            super(pProperties, FocusType.ITEM);
            this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        }
        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING);
        }
    }

    public static class DreamFocusFluidBlock extends DreamFocusBlock {

        public DreamFocusFluidBlock(Properties pProperties) {
            super(pProperties, FocusType.FLUID);
        }
    }

    public enum FocusType {
        ITEM((l, pos) -> {
            AABB bbox = new AABB(pos.getX() + 0.3, pos.getY() + 1, pos.getZ() + 0.3, pos.getX() + 0.7, pos.getY() + 2, pos.getZ() + 0.7);
            return l.getEntities(EntityTypeTest.forClass(FletumEntity.class), bbox, f -> true).isEmpty() ? 0 : 1;
        }, (pos, state) -> pos.relative(state.getValue(FACING)), (pos, state) -> pos.relative(state.getValue(FACING).getOpposite())),
        FLUID((l, pos) -> {
            int a = 0;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos pos2 = pos.relative(direction);
                AABB bbox = new AABB(pos2.getX() + 0.3, pos2.getY(), pos2.getZ() + 0.3, pos2.getX() + 0.7, pos2.getY() + 1, pos2.getZ() + 0.7);
                if (!l.getEntities(EntityTypeTest.forClass(FletumEntity.class), bbox, f -> true).isEmpty()) a++;
            }
            return a;
        }, (pos, state) -> pos.relative(Direction.DOWN), (pos, state) -> pos.relative(Direction.UP), (pos, state) -> pos.relative(Direction.UP, 2));

        private final BiFunction<Level, BlockPos, Integer> fletumCounter;
        private final BiFunction<BlockPos, BlockState, BlockPos> startPos;
        private final BiFunction<BlockPos, BlockState, BlockPos> drainPos;
        private final BiFunction<BlockPos, BlockState, BlockPos> drainPos2;

        FocusType(BiFunction<Level, BlockPos, Integer> fletumCounter, BiFunction<BlockPos, BlockState, BlockPos> startPos, BiFunction<BlockPos, BlockState, BlockPos> drainPos) {
            this(fletumCounter, startPos, drainPos, drainPos);
        }

        FocusType(BiFunction<Level, BlockPos, Integer> fletumCounter, BiFunction<BlockPos, BlockState, BlockPos> startPos, BiFunction<BlockPos, BlockState, BlockPos> drainPos, BiFunction<BlockPos, BlockState, BlockPos> drainPos2) {
            this.fletumCounter = fletumCounter;
            this.startPos = startPos;
            this.drainPos = drainPos;
            this.drainPos2 = drainPos2;
        }

        public int countFleti(Level l, BlockPos pos) {
            return fletumCounter.apply(l, pos);
        }

        public BlockPos getStartPos(BlockPos pos, BlockState state) {
            return startPos.apply(pos, state);
        }

        public BlockPos getDrainPos(BlockPos pos, BlockState state) {
            return drainPos.apply(pos, state);
        }

        public BlockPos getDrainPos2(BlockPos pos, BlockState state) {
            return drainPos2.apply(pos, state);
        }
    }

}
