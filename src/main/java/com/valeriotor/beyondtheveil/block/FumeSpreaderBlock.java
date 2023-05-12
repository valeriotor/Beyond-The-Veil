package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.tile.FumeSpreaderBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FumeSpreaderBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE = Shapes.box(0.0625 * 4, 0, 0.0625 * 4, 0.0625 * 12, 0.0625 * 11, 0.0625 * 12);
    public static final BooleanProperty FULL = BooleanProperty.create("full");


    public FumeSpreaderBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(FULL, false));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if(player.isShiftKeyDown() && state.getValue(FULL)) {
            level.setBlock(pos, state.setValue(FULL, false), 3);
            return InteractionResult.SUCCESS;
        } else if(!player.isShiftKeyDown()){
            ItemStack stack = player.getItemInHand(hand);
            if (!state.getValue(FULL) && stack.getItem() == Registration.ONIRIC_INCENSE.get()) {
                level.setBlock(pos, state.setValue(FULL, true), 3);
                stack.shrink(1);
                return InteractionResult.CONSUME;
            } else if (stack.getItem() == Registration.MEMORY_PHIAL.get() && state.getValue(FULL)) {
                CompoundTag tag = stack.getOrCreateTag();
                BlockEntity entity = level.getBlockEntity(pos);
                if (entity instanceof FumeSpreaderBE be) {
                    if (tag.contains("memory")) {
                        Memory memory = Memory.getMemoryFromDataName(tag.getString("memory"));
                        be.setStoredMemory(memory);
                        stack.shrink(1);
                        return InteractionResult.CONSUME;
                    } else if (be.getStoredMemory() != null) {
                        Memory m = be.getStoredMemory();
                        be.setStoredMemory(null);
                        stack.shrink(1);
                        ItemStack returned = new ItemStack(Registration.MEMORY_PHIAL.get());
                        CompoundTag returnedTag = returned.getOrCreateTag();
                        returnedTag.putString("memory", m.getDataName());
                        ItemHandlerHelper.giveItemToPlayer(player, returned);
                        return InteractionResult.SUCCESS;
                    }
                }
                stack.setTag(null);
                System.out.println("test");
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FULL);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FumeSpreaderBE(pPos, pState);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pos, Random pRandom) {
        BlockEntity entity = pLevel.getBlockEntity(pos);
        if (entity instanceof FumeSpreaderBE be) {
            Memory memory = be.getStoredMemory();
            if (memory != null) {
                int i = memory.getColor();
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i >> 0 & 255) / 255.0D;
                pLevel.addAlwaysVisibleParticle(ParticleTypes.ENTITY_EFFECT, pos.getX()+0.5D, pos.getY()+0.7D, pos.getZ()+0.5D, d0, d1, d2);
            }
        }
    }
}
