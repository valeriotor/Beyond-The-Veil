package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.CapabilityEvents;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FlaskShelfBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 2);
    public static final IntegerProperty SIDE = IntegerProperty.create("side", 0, 2); // 0 left, 1 center, 2 right (from perspective of player who placed it)
    private static final VoxelShape BASE_SHAPE = Shapes.box(0, 0, 0, 1, 0.125D, 1);
    private static final double a = 0.0625;
    private static final VoxelShape L0_EAST = Shapes.box(a, a*2, 0, 1, a*3, 1);
    private static final VoxelShape L0_NORTH = Shapes.box(0, a*2, 0, 1, a*3, 1-a);
    private static final VoxelShape L0_WEST = Shapes.box(0, a*2, 0, 1-a, a*3, 1);
    private static final VoxelShape L0_SOUTH = Shapes.box(0, a*2, a, 1, a*3, 1);
    private static final VoxelShape L1_EAST = Shapes.box(a, a*2, 0, 1-2*a, a*3, 1);
    private static final VoxelShape L1_NORTH = Shapes.box(0, a*2, 2*a, 1, a*3, 1-a);
    private static final VoxelShape L1_WEST = Shapes.box(2*a, a*2, 0, 1-a, a*3, 1);
    private static final VoxelShape L1_SOUTH = Shapes.box(0, a*2, a, 1, a*3, 1-2*a);
    private static final VoxelShape L1_EAST_LEFT = Shapes.box(a, a*2, 0, 1-2*a, a*3, 1-a);
    private static final VoxelShape L1_EAST_RIGHT = Shapes.box(a, a*2, a, 1-2*a, a*3, 1);
    private static final VoxelShape L1_NORTH_LEFT = Shapes.box(0, a*2, 2*a, 1-a, a*3, 1-a);
    private static final VoxelShape L1_NORTH_RIGHT = Shapes.box(a, a*2, 2*a, 1, a*3, 1-a);
    private static final VoxelShape L1_WEST_LEFT = Shapes.box(2*a, a*2, a, 1-a, a*3, 1);
    private static final VoxelShape L1_WEST_RIGHT = Shapes.box(2*a, a*2, 0, 1-a, a*3, 1-a);
    private static final VoxelShape L1_SOUTH_LEFT = Shapes.box(a, a*2, a, 1, a*3, 1-2*a);
    private static final VoxelShape L1_SOUTH_RIGHT = Shapes.box(0, a*2, a, 1-a, a*3, 1-2*a);
    private static final VoxelShape L2_EAST = Shapes.box(a, a*2, 0, 1-4*a, a*3, 1);
    private static final VoxelShape L2_NORTH = Shapes.box(0, a*2, 4*a, 1, a*3, 1-a);
    private static final VoxelShape L2_WEST = Shapes.box(4*a, a*2, 0, 1-a, a*3, 1);
    private static final VoxelShape L2_SOUTH = Shapes.box(0, a*2, a, 1, a*3, 1-4*a);
    private static final VoxelShape L2_EAST_LEFT = Shapes.box(a, a*2, 0, 1-4*a, a*3, 1-2*a);
    private static final VoxelShape L2_EAST_RIGHT = Shapes.box(a, a*2, 2*a, 1-4*a, a*3, 1);
    private static final VoxelShape L2_NORTH_LEFT = Shapes.box(0, a*2, 4*a, 1-2*a, a*3, 1-a);
    private static final VoxelShape L2_NORTH_RIGHT = Shapes.box(2*a, a*2, 4*a, 1, a*3, 1-a);
    private static final VoxelShape L2_WEST_LEFT = Shapes.box(4*a, a*2, 2*a, 1-a, a*3, 1);
    private static final VoxelShape L2_WEST_RIGHT = Shapes.box(4*a, a*2, 0, 1-a, a*3, 1-2*a);
    private static final VoxelShape L2_SOUTH_LEFT = Shapes.box(2*a, a*2, a, 1, a*3, 1-4*a);
    private static final VoxelShape L2_SOUTH_RIGHT = Shapes.box(0, a*2, a, 1-2*a, a*3, 1-4*a);

    public FlaskShelfBlock(Properties properties) {
        super(BlockBehaviour.Properties.of().strength(5.0F, 7.0F).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SIDE, 0).setValue(LEVEL, 0));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext p_60558_) {
        VoxelShape baseShape = getBaseShape(pState);
        BlockPos centerPos = findCenter(pPos, pState);
        if (pLevel.getBlockEntity(centerPos) instanceof FlaskShelfBE flaskShelfBE) {
            //baseShape = Shapes.or(baseShape, flaskShelfBE.shapes[pState.getValue(LEVEL)][pState.getValue(SIDE)]);
            baseShape = flaskShelfBE.shapes[pState.getValue(LEVEL)][pState.getValue(SIDE)];
        }
        return baseShape;
    }

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape baseShape = getBaseShape(pState);
        BlockPos centerPos = findCenter(pPos, pState);
        if (pLevel.getBlockEntity(centerPos) instanceof FlaskShelfBE flaskShelfBE) {
            baseShape = Shapes.or(baseShape, flaskShelfBE.shapes[pState.getValue(LEVEL)][pState.getValue(SIDE)]);
            //baseShape = flaskShelfBE.shapes[pState.getValue(LEVEL)][pState.getValue(SIDE)];
        }
        return baseShape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape baseShape = getBaseShape(pState);
        BlockPos centerPos = findCenter(pPos, pState);
        if (pLevel.getBlockEntity(centerPos) instanceof FlaskShelfBE flaskShelfBE) {
            //baseShape = Shapes.or(baseShape, flaskShelfBE.shapes[pState.getValue(LEVEL)][pState.getValue(SIDE)]);
            baseShape = flaskShelfBE.shapes[pState.getValue(LEVEL)][pState.getValue(SIDE)];
        }
        return baseShape;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        VoxelShape baseShape = getBaseShape(pState);
        return baseShape;
    }

    public static VoxelShape getBaseShape(BlockState pState) {
        return getBaseShape(pState.getValue(LEVEL), pState.getValue(SIDE), pState.getValue(FACING));
    }

    public static VoxelShape getBaseShape(int level, int side, Direction facing) {
        VoxelShape returnValue = null;
        if (level == 0) {
            returnValue = switch (facing) {
                case NORTH -> L0_NORTH;
                case SOUTH -> L0_SOUTH;
                case WEST -> L0_WEST;
                case EAST -> L0_EAST;
                default -> null;
            };
        } else if (level == 1) {
            returnValue = switch (facing) {
                case NORTH -> side == 1 ? L1_NORTH : (side == 0 ? L1_NORTH_LEFT : L1_NORTH_RIGHT);
                case SOUTH -> side == 1 ? L1_SOUTH : (side == 0 ? L1_SOUTH_LEFT : L1_SOUTH_RIGHT);
                case WEST -> side == 1 ? L1_WEST : (side == 0 ? L1_WEST_LEFT : L1_WEST_RIGHT);
                case EAST -> side == 1 ? L1_EAST : (side == 0 ? L1_EAST_LEFT : L1_EAST_RIGHT);
                default -> null;
            };
        } else if (level == 2) {
            returnValue = switch (facing) {
                case NORTH -> side == 1 ? L2_NORTH : (side == 0 ? L2_NORTH_LEFT : L2_NORTH_RIGHT);
                case SOUTH -> side == 1 ? L2_SOUTH : (side == 0 ? L2_SOUTH_LEFT : L2_SOUTH_RIGHT);
                case WEST -> side == 1 ? L2_WEST : (side == 0 ? L2_WEST_LEFT : L2_WEST_RIGHT);
                case EAST -> side == 1 ? L2_EAST : (side == 0 ? L2_EAST_LEFT : L2_EAST_RIGHT);
                default -> null;
            };
        }
        return returnValue;
    }

    @Override
    public boolean hasDynamicShape() {
        return super.hasDynamicShape();
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockPos centerPos = findCenter(pPos, pState);
        if (pLevel.getBlockEntity(centerPos) instanceof FlaskShelfBE be) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            Item held = itemStack.getItem();
            if (Block.byItem(held) instanceof FlaskBlock && pHit.getLocation().y() - pPos.getY() == a * 3) {
                if (be.tryPlaceFlask(pLevel, pPos, pPlayer, pHand, pHit))
                    return InteractionResult.SUCCESS;
                return InteractionResult.SUCCESS; // eh I guess I need it here too so that the flask isn't placed as an adjacent, individual block
            } else {
                return be.interactLiquid(pLevel, pPos, pPlayer, pHand, pHit);
            }

        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        super.attack(pState, pLevel, pPos, pPlayer);
        BlockPos centerPos = findCenter(pPos, pState);
        if (pLevel.isClientSide && pLevel.getBlockEntity(centerPos) instanceof FlaskShelfBE be) {
            HitResult hr = ClientData.getInstance().getClientHitResult();
            if (hr instanceof BlockHitResult blockHitResult) {
                Messages.sendToServer(new BreakFlaskMessage(hr.getLocation().x, hr.getLocation().y, hr.getLocation().z));
            }
        }
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
        builder.add(FACING).add(SIDE).add(LEVEL);
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
        return defaultBlockState().setValue(FACING, opposite).setValue(SIDE, 1).setValue(LEVEL, 0);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        for (int i = -1; i <= 1; i++) {
            for (int y = 0; y < 3; y++) {
                if(i == 0 && y == 0) continue;
                Direction facing = pState.getValue(FACING);
                int x = facing.getAxis() == Direction.Axis.X ? 0 : (facing == Direction.NORTH ? -i : i);
                int z = facing.getAxis() == Direction.Axis.Z ? 0 : (facing == Direction.EAST ? -i : i);
                pLevel.setBlock(pPos.offset(x, y, z), pState.setValue(SIDE, i+1).setValue(LEVEL, y), 3);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return super.canSurvive(pState, pLevel, pPos);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        BlockPos center = findCenter(pPos, pState);
        for (int i = -1; i <= 1; i++) {
            for (int y = -1; y <= 1; y++) {
                int x = pState.getValue(FACING).getAxis() == Direction.Axis.X ? 0 : i;
                int z = pState.getValue(FACING).getAxis() == Direction.Axis.Z ? 0 : i;
                BlockPos neighbourPos = center.offset(x, y, z);
                BlockState neighbour = pLevel.getBlockState(neighbourPos);
                if (neighbour.is(this) && neighbour.getValue(FACING) == pState.getValue(FACING)) {
                    pLevel.setBlock(neighbourPos, Blocks.AIR.defaultBlockState(), 35);
                    pLevel.levelEvent(pPlayer, 2001, neighbourPos, Block.getId(neighbour));
                }
            }
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    private BlockPos findCenter(BlockPos pPos, BlockState pState) {
        Direction facing = pState.getValue(FACING);
        int i = -pState.getValue(SIDE)+1;
        int x = facing.getAxis() == Direction.Axis.X ? 0 : (facing == Direction.NORTH ? -i : i);
        int z = facing.getAxis() == Direction.Axis.Z ? 0 : (facing == Direction.EAST ? -i : i);
        return pPos.offset(x, 1 - pState.getValue(LEVEL), z);
        //if (pState.getValue(CENTER)) {
        //    return pPos;
        //}
        //for (int i = -1; i <= 1; i++) {
        //    int y = 2 - pState.getValue(LEVEL);
        //    int x = pState.getValue(FACING).getAxis() == Direction.Axis.X ? 0 : i;
        //    int z = pState.getValue(FACING).getAxis() == Direction.Axis.Z ? 0 : i;
        //    BlockState neighbour = pLevel.getBlockState(pPos.offset(x, y, z));
        //    if (neighbour.getBlock() == this && neighbour.getValue(FACING) == pState.getValue(FACING) && neighbour.getValue(CENTER)) {
        //        return pPos.offset(x, y, z);
        //    }
        //}
        //return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(SIDE) == 1 && pState.getValue(LEVEL) == 1 ? new FlaskShelfBE(pPos, pState) : null; // TODO change second one to set it as simple
    }

    public static class BreakFlaskMessage {
        private final double x, y, z;

        public BreakFlaskMessage(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public BreakFlaskMessage(FriendlyByteBuf buf) {
            this.x = buf.readDouble();
            this.y = buf.readDouble();
            this.z = buf.readDouble();
        }

        public void toBytes(FriendlyByteBuf buf) {
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
        }

        public boolean handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context ctx = supplier.get();
            ctx.enqueueWork(() -> {
                BlockPos hitPos = new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
                ServerPlayer sender = ctx.getSender();
                Level l = sender.level();
                BlockState blockState = l.getBlockState(hitPos);
                if (blockState.getBlock() == Registration.FLASK_SHELF.get()) {
                    BlockPos centerPos = ((FlaskShelfBlock) Registration.FLASK_SHELF.get()).findCenter(hitPos, blockState);
                    if (l.getBlockEntity(centerPos) instanceof FlaskShelfBE be) {
                        be.tryBreakFlask(hitPos, sender, new Vec3(x, y, z));
                    }
                }
            });
            return true;
        }
    }

}
