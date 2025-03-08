package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.tile.PillarBE;
import com.valeriotor.beyondtheveil.tile.SlugBaitBE;
import com.valeriotor.beyondtheveil.world.saved.LifeEconomyData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PillarBlock extends Block implements EntityBlock {

    private final boolean offer;
    private static final double a = 0.0625;
    private static final VoxelShape OFFER_BOX;
    private static final VoxelShape DEMAND_BOX;

    static {
        VoxelShape base = Shapes.box(5.5 * a, 0, 5.5 * a, 1 - 5.5 * a, 2 * a, 1 - 5.5 * a);
        base = Shapes.or(base, Shapes.box(6 * a, 2 * a, 6 * a, 1 - 6 * a, 3 * a, 1 - 6 * a));

        VoxelShape o = Shapes.or(base, Shapes.box(5 * a, 2.25 * a, 7 * a, 6 * a, 13.25 * a, 9 * a));
        o = Shapes.or(o, Shapes.box(7 * a, 2.25 * a, 5 * a, 9 * a, 13.25 * a, 6 * a));
        o = Shapes.or(o, Shapes.box(7 * a, 2.25 * a, 10 * a, 9 * a, 13.25 * a, 11 * a));
        OFFER_BOX = Shapes.or(o, Shapes.box(10 * a, 2.25 * a, 7 * a, 11 * a, 13.25 * a, 9 * a));

        VoxelShape d = Shapes.or(base, Shapes.box(6.5 * a, 3 * a, 6.5 * a, 9.5 * a, 14 * a, 9.5 * a));
        DEMAND_BOX = Shapes.or(d, Shapes.box(7 * a, 14 * a, 7 * a, 9 * a, 16 * a, 9 * a));
    }


    public PillarBlock(Properties pProperties, boolean offer) {
        super(pProperties);
        this.offer = offer;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PillarBE(offer ? Registration.OFFER_PILLAR_BE.get() : Registration.DEMAND_PILLAR_BE.get(), pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return !offer ? OFFER_BOX : DEMAND_BOX;
    }

    @Override
    public InteractionResult use(BlockState pState, Level l, BlockPos pos, Player p, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = p.getItemInHand(pHand);
        boolean holdingOpposite = (offer && stack.getItem() == Registration.DEMAND_PILLAR_ITEM.get()) || (!offer && stack.getItem() == Registration.OFFER_PILLAR_ITEM.get());
        if (l.isClientSide) {
            return holdingOpposite ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
        if (holdingOpposite && l.getBlockEntity(pos) instanceof PillarBE be && l instanceof ServerLevel sl) {
            UUID connection = UUID.randomUUID();
            CompoundTag linkTag = new CompoundTag();
            linkTag.putLong("link", pos.asLong());
            linkTag.putBoolean("fromItem", true);
            linkTag.putString("connection", connection.toString());
            BlockItem.setBlockEntityData(stack, offer ? Registration.DEMAND_PILLAR_BE.get() : Registration.OFFER_PILLAR_BE.get(), linkTag);
            be.setLink(null);
            be.setConnection(connection);
            LifeEconomyData.getInstance(sl).setPillarConnectionToBe(sl, pos, connection);
            return InteractionResult.SUCCESS;
        }
        if (p.getItemInHand(pHand).isEmpty() && l.getBlockEntity(pos) instanceof PillarBE be) {
            BlockPos link = be.getLink();
            if (link != null) {
                p.teleportTo(link.getX(), link.getY(), link.getZ());
            }
            return InteractionResult.SUCCESS;
        }

        return super.use(pState, l, pos, p, pHand, pHit);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level l, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (l.isClientSide) {
            return null;
        }
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if(pBlockEntity instanceof PillarBE be) be.tickServer();
        };
    }

    @Override
    public void onRemove(BlockState pState, Level level, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, level, pPos, pNewState, pMovedByPiston);
        if (level instanceof ServerLevel sl) {
            LifeEconomyData.getInstance(sl).removePillar(pPos);
        }
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}
