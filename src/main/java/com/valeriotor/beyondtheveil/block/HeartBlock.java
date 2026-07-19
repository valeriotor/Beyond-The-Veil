package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.tile.BloodWellBE;
import com.valeriotor.beyondtheveil.tile.HeartBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.GuiType;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.multiblocks.MultiblockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeartBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final double a = 0.0625;
    private static final VoxelShape SHAPE = Shapes.box(a * 7, 0, a * 7, a * 9, a * 5, a * 9);

    public HeartBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new HeartBE(pPos, pState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState p_60543_, LevelAccessor level, BlockPos pos, BlockPos p_60546_) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public FluidState getFluidState(BlockState p_56969_) {
        return p_56969_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_56969_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide())
            return (pLevel1, pPos, pState1, pBlockEntity) -> {
                if(pBlockEntity instanceof HeartBE) ((HeartBE) pBlockEntity).tickClient();
            };
        else
            return (pLevel1, pPos, pState1, pBlockEntity) -> {
                if (pBlockEntity instanceof HeartBE) ((HeartBE) pBlockEntity).tickServer();
            };
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (pLevel instanceof ServerLevel sl && pPlacer instanceof ServerPlayer sp) {
            boolean alreadyDone = !sp.getCapability(PlayerDataProvider.PLAYER_DATA).isPresent() || sp.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get().getBoolean(PlayerDataLib.met_cult.name());
            if (sp.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).isPresent()) {
                boolean isAlreadyBeingBackstabbed = sp.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).resolve().get().hasTimer("killedByCultist");
                if (!alreadyDone && !isAlreadyBeingBackstabbed && MathHelperBTV.checkForRing(this, sl, pPos, 4)) {
                    DialogueData dialogueData = DialogueData.for_(sp);
                    BloodCultistEntity cultist = new BloodCultistEntity(BTVEntities.BLOOD_CULTIST.get(), sl);
                    Vec3 lookAngle = sp.getLookAngle();
                    Vec3 cultistPos = sp.position().add(lookAngle.normalize().reverse().multiply(1.1, 0, 1.1));
                    cultist.setPos(cultistPos);
                    cultist.lookAt(sp, 360, 360);
                    if (dialogueData.getDialogue(DialogueType.BLOOD_CULTIST).getID().equals("immortal2")) {
                        sl.addFreshEntity(cultist);
                        sp.startRiding(cultist, true);
                        respawnNow(sp);
                    } else {
                        dialogueData.setDialogue(DialogueType.BLOOD_CULTIST, DialogueRegistry.getTemplate(DialogueType.BLOOD_CULTIST, "immortal"));
                        cultist.setKillingEntity(sp);
                        sl.addFreshEntity(cultist);
                        sp.startRiding(cultist, true);
                        Messages.sendToPlayer(GenericToClientPacket.hideOverlayMessage(), sp);
                        Messages.sendToPlayer(GenericToClientPacket.rotateCamera(sp.getYRot(), (float) -45, 15), sp);
                        PlayerTimer timer = new PlayerTimer.Builder("killedByCultist", 30)
                                .addContinuousAction((p, c) -> {
                                    if (c.getRemainingTime() % 2 == 0 && c.getRemainingTime() < 20) {
                                        p.setHealth(Mth.clamp(c.getRemainingTime(), 1, Math.max(1, p.getHealth())));
                                        p.level().playSound(null, p.getOnPos(), SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1, 1);
                                        if (p.level() instanceof ServerLevel serverLevel) {
                                            double xComponent = -Math.sin(Math.toRadians(p.getYRot()));
                                            double zComponent = Math.cos(Math.toRadians(p.getYRot()));
                                            serverLevel.sendParticles(BTVParticles.BLOODSPILL.get(), p.getX() + xComponent, p.getY(), p.getZ() + zComponent, 5, 1, 0, 0, 1);
                                        }
                                    } else if (c.getRemainingTime() == 29) {
                                        p.level().playSound(null, p.getOnPos(), SoundEvents.TRIDENT_RETURN, SoundSource.PLAYERS, 1, 1);
                                    }
                                })
                                .addFinalActions((p, c) -> {
                                    Messages.sendToPlayer(GenericToClientPacket.openGui(GuiType.KILLED_BY_CULTIST), (ServerPlayer) p);
                                    p.level().playSound(null, p.getOnPos(), SoundEvents.PLAYER_DEATH, SoundSource.PLAYERS, 1, 1);
                                }) // TODO this might not work on dedicated servers, test
                                .toTimer();
                        sp.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).ifPresent(c -> c.addTimer(timer));
                    }
                }

            }
            if (MultiblockRegistry.BLOOD_WELL.checksOutBottomCenter(sl, pPos.below(3))) {
                sl.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
                sl.setBlock(pPos.below(2), Registration.BLOOD_WELL.get().defaultBlockState(), 3);
                if (sl.getBlockEntity(pPos.below(2)) instanceof BloodWellBE be) {
                    be.setCreator(sp.getUUID());
                }
                sl.playSound(null, pPos, BTVSounds.HEART_RIP.get(), SoundSource.BLOCKS, 1, 1);
                DataUtil.setBooleanOnServerAndSync(sp, PlayerDataLib.built_well.name(), true, false);
            }

        }
    }

    public static void respawnNow(ServerPlayer sp) {
        respawnNow(sp, null);
    }
    public static void respawnNow(ServerPlayer sp, BloodCultistEntity bloodCultist) {
        if (bloodCultist == null) {
            if (sp.getVehicle() instanceof BloodCultistEntity bc) {
                bloodCultist = bc;
                double xComponent = -Math.sin(Math.toRadians(bc.getYRot())) * 1.2;
                double zComponent = Math.cos(Math.toRadians(bc.getYRot())) * 1.2;
                sp.dismountTo(sp.getX() + xComponent, sp.getY(), sp.getZ() + zComponent);
            } else {
                List<BloodCultistEntity> candidates = sp.level().getEntities(EntityTypeTest.forClass(BloodCultistEntity.class), AABB.ofSize(sp.position(), 4, 4, 4), bc -> bc.getKillingEntity() == sp);
                if (!candidates.isEmpty()) {
                    bloodCultist = candidates.get(0);
                }
            }

        }
        if (bloodCultist != null) {
            if (!sp.getItemInHand(InteractionHand.MAIN_HAND).getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE)) {
                for (int i = 0; i < 9; i++) {
                    if (sp.getInventory().getItem(i).getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE)) {
                        sp.connection.send(new ClientboundSetCarriedItemPacket(i));
                        break;
                    }
                }
            }
            double v = MathHelperBTV.angleBetween(sp, bloodCultist);
            Messages.sendToPlayer(GenericToClientPacket.rotateCamera((float) v, (float) 10, 20), sp);
            bloodCultist.startTalking(sp);
        }
    }



}
