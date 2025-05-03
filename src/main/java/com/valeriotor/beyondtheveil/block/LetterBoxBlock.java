package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock1by2;
import com.valeriotor.beyondtheveil.capability.util.LetterDataProvider;
import com.valeriotor.beyondtheveil.container.LetterBoxContainer;
import com.valeriotor.beyondtheveil.letters.ExchangeRegistry;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class LetterBoxBlock extends ThinMultiBlock1by2 {
    protected static final VoxelShape SHAPE_1;
    protected static final VoxelShape SHAPE_2;
    protected static final VoxelShape SHAPE_3;
    protected static final VoxelShape SHAPE_4;

    static {
        double a = 0.0625;
        VoxelShape s1 = Shapes.box(3 * a, 0, 4 * a, 1 - 3 * a, a, 1 - 4 * a);
        s1 = Shapes.or(s1, Shapes.box(4 * a, a, 5 * a, 1 - 4 * a, 2 * a, 1 - 5 * a));
        s1 = Shapes.or(s1, Shapes.box(6 * a, 2 * a, 7 * a, 1 - 6 * a, 3 * a, 1 - 7 * a));
        s1 = Shapes.or(s1, Shapes.box(6.5 * a, 3 * a, 7.5 * a, 1 - 6.5 * a, 4 * a, 1 - 7.5 * a));
        s1 = Shapes.or(s1, Shapes.box(6 * a, 4 * a, 7 * a, 1 - 6 * a, 15 * a, 1 - 7 * a));
        s1 = Shapes.or(s1, Shapes.box(4 * a, 15 * a, 5 * a, 1 - 4 * a, 1, 1 - 5 * a));

        VoxelShape s2 = Shapes.box(4 * a, 0, 5 * a, 1 - 4 * a, a, 1 - 5 * a);
        s2 = Shapes.or(s2, Shapes.box(6 * a, a, 7 * a, 1 - 6 * a, 2 * a, 1 - 7 * a));
        s2 = Shapes.or(s2, Shapes.box(a, 2 * a, 4 * a, 1 - a, 12 * a, 1 - 4 * a));
        s2 = Shapes.or(s2, Shapes.box(0, 12 * a, 3.5 * a, 1, 13 * a, 1 - 3.5 * a));
        s2 = Shapes.or(s2, Shapes.box(2 * a, 13 * a, 5 * a, 1 - 2 * a, 14 * a, 1 - 5 * a));
        s2 = Shapes.or(s2, Shapes.box(4 * a, 14 * a, 6 * a, 1 - 4 * a, 15 * a, 1 - 6 * a));
        s2 = Shapes.or(s2, Shapes.box(6 * a, 15 * a, 7 * a, 1 - 6 * a, 16 * a, 1 - 7 * a));

        VoxelShape s3 = Shapes.box(4 * a, 0, 3 * a, 1 - 4 * a, a, 1 - 3 * a);
        s3 = Shapes.or(s3, Shapes.box(5 * a, a, 4 * a, 1 - 5 * a, 2 * a, 1 - 4 * a));
        s3 = Shapes.or(s3, Shapes.box(7 * a, 2 * a, 6 * a, 1 - 7 * a, 3 * a, 1 - 6 * a));
        s3 = Shapes.or(s3, Shapes.box(7.5 * a, 3 * a, 6.5 * a, 1 - 7.5 * a, 4 * a, 1 - 6.5 * a));
        s3 = Shapes.or(s3, Shapes.box(7 * a, 4 * a, 6 * a, 1 - 7 * a, 15 * a, 1 - 6 * a));
        s3 = Shapes.or(s3, Shapes.box(5 * a, 15 * a, 4 * a, 1 - 5 * a, 1, 1 - 4 * a));

        VoxelShape s4 = Shapes.box(5 * a, 0, 4 * a, 1 - 5 * a, a, 1 - 4 * a);
        s4 = Shapes.or(s4, Shapes.box(7 * a, a, 6 * a, 1 - 7 * a, 2 * a, 1 - 6 * a));
        s4 = Shapes.or(s4, Shapes.box(4 * a, 2 * a, a, 1 - 4 * a, 12 * a, 1 - a));
        s4 = Shapes.or(s4, Shapes.box(3.5 * a, 12 * a, 0, 1 - 3.5 * a, 13 * a, 1));
        s4 = Shapes.or(s4, Shapes.box(5 * a, 13 * a, 2 * a, 1 - 5 * a, 14 * a, 1 - 2 * a));
        s4 = Shapes.or(s4, Shapes.box(6 * a, 14 * a, 4 * a, 1 - 6 * a, 15 * a, 1 - 4 * a));
        s4 = Shapes.or(s4, Shapes.box(7 * a, 15 * a, 6 * a, 1 - 7 * a, 16 * a, 1 - 6 * a));
        SHAPE_1 = s1;
        SHAPE_2 = s2;
        SHAPE_3 = s3;
        SHAPE_4 = s4;
    }

    public LetterBoxBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(FACING).getAxis() == Direction.Axis.Z ? (pState.getValue(getLevelProperty()) == 0 ? SHAPE_1 : SHAPE_2) : (pState.getValue(getLevelProperty()) == 0 ? SHAPE_3 : SHAPE_4);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        if (player instanceof ServerPlayer sp) {
            NetworkHooks.openScreen(sp, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new LetterBoxContainer(pContainerId, pPos, player), Component.translatable("gui.letter_box")), pPos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide && pPlacer instanceof ServerPlayer p) {
            if (ResearchUtil.getResearchStage(p, "COMMUNION") == 1) {
                p.getCapability(LetterDataProvider.LETTER_DATA).ifPresent(c -> {
                    c.addExchange(p, ExchangeRegistry.byName("scholar_offer_help"));
                });
            }
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }
}
