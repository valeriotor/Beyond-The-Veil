package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.AlembicsBlock;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlembicsBE extends BlockEntity {

    private FluidTank inputTank1;
    private ItemStackHandler stackHandler;
    private FluidTank inputTank2;
    private FluidTank outputTank;
    private FlaskShelfBE.Flask heldFlask;
    private final VoxelShape[] shapes = new VoxelShape[3];

    private final LazyOptional<IFluidHandler> inputTank1Holder = LazyOptional.of(() -> inputTank1);
    private final LazyOptional<IItemHandler> stackHolder = LazyOptional.of(() -> stackHandler);
    private final LazyOptional<IFluidHandler> inputTank2Holder = LazyOptional.of(() -> inputTank2);
    private final LazyOptional<IFluidHandler> outputTankHolder = LazyOptional.of(() -> outputTank);

    public static final ModelProperty<FlaskShelfBE.Flask> FLASK_PROPERTY = new ModelProperty<>();
    public static final ModelProperty<BlockPos> POS_PROPERTY = new ModelProperty<>();

    public AlembicsBE(BlockPos pPos, BlockState pBlockState) {
        super(Registration.ALEMBICS_BE.get(), pPos, pBlockState);
        inputTank1 = new FluidTank(2000);
        stackHandler = new ItemStackHandler(1);
        inputTank2 = new FluidTank(1000);
        outputTank = new FluidTank(1000);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag != null) {
            super.load(tag);
        }
        loadCommonData(tag);
    }

    public FlaskShelfBE.Flask getHeldFlask() {
        return heldFlask;
    }

    public boolean interactServer(Player player, InteractionHand hand, int hit) {
        ItemStack held = player.getItemInHand(hand);
        if (Block.byItem(held.getItem()) instanceof FlaskBlock flaskBlock && flaskBlock.size.getCapacity() > 0) {
            if (heldFlask == null) {
                Direction facing = getBlockState().getValue(AlembicsBlock.FACING);
                double x = worldPosition.getX() + (facing.getAxis() == Direction.Axis.X ? 0.5 : (facing == Direction.SOUTH ? 26 * 0.0625 : -10 * 0.0625));
                double z = worldPosition.getZ() + (facing.getAxis() == Direction.Axis.Z ? 0.5 : (facing == Direction.WEST ? 26 * 0.0625 : -10 * 0.0625));
                heldFlask = new FlaskShelfBE.Flask(x, worldPosition.getY(), z, held, flaskBlock);
                updateClient();
                held.shrink(1);
                updateShape();
            }
            return true;
        } else if (hit == 3 && held.isEmpty() && player.isShiftKeyDown() && heldFlask != null) {
            ItemStack flask = heldFlask.toItem();
            player.setItemInHand(hand, flask);
            heldFlask = null;
            updateClient();
            updateShape();
        } else if (hit == 1) {
            if (held.isEmpty()) {
                player.setItemInHand(hand, stackHandler.extractItem(0, 64, false));
            } else {
                player.setItemInHand(hand, stackHandler.insertItem(0, held, false));
            }
            return true;
        } else {
            if (held.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
                FluidTank tank = switch (hit) {
                    case 0 -> inputTank1;
                    case 2 -> inputTank2;
                    case 3 -> heldFlask != null ? heldFlask.getTank() : null;
                    default -> inputTank1;
                };
                if (tank == null) {
                    return false;
                }
                if (held.getItem() != Registration.SYRINGE.get()) {
                    FluidUtil.interactWithFluidHandler(player, hand, tank);
                    updateClient();
                } else {
                    IFluidHandlerItem syringe = held.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElseThrow();
                    if (!player.isShiftKeyDown()) {
                        if (syringe.fill(tank.drain(1, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.SIMULATE) == 1) {
                            syringe.fill(tank.drain(1, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                            setChanged();
                            if (level != null) {
                                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                            }
                        }
                    } else {
                        if (tank.fill(syringe.drain(1, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.SIMULATE) == 1) {
                            tank.fill(syringe.drain(1, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                            //tank.fill(new FluidStack(Registration.SOURCE_FLUID_LIQUID_BLAZE_POWDER.get(), 5), IFluidHandler.FluidAction.EXECUTE);
                            setChanged();
                            if (level != null) {
                                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                            }
                        }
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private void updateShape() {
        Direction facing = getBlockState().getValue(AlembicsBlock.FACING);
        for (int i = 0; i < shapes.length; i++) {
            VoxelShape base = AlembicsBlock.SHAPES[i][(facing.get2DDataValue() + 1) & 3];
            if (heldFlask != null) {
                double x = (facing.getAxis() == Direction.Axis.X ? 0 : (facing == Direction.SOUTH ? 2.125 - i : i - 2));
                double z = (facing.getAxis() == Direction.Axis.Z ? 0 : (facing == Direction.WEST ? 2.125 - i : i - 2));
                base = Shapes.or(base, heldFlask.computeShapeWithOffset(x, 0, z));
            }
            shapes[i] = base;
        }
    }

    public VoxelShape[] getShapes() {
        return shapes;
    }

    private void updateClient() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        }
    }

    private void loadCommonData(CompoundTag tag) {
        if (tag.contains("inputTank1")) {
            inputTank1.readFromNBT(tag.getCompound("inputTank1"));
            inputTank2.readFromNBT(tag.getCompound("inputTank2"));
            outputTank.readFromNBT(tag.getCompound("outputTank"));
        }
        if (tag.contains("stack")) {
            stackHandler.deserializeNBT(tag.getCompound("stack"));
        }
        if (tag.contains("flask")) {
            heldFlask = new FlaskShelfBE.Flask(tag.getCompound("flask"));
        } else {
            heldFlask = null;
        }
        updateShape();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        saveCommonData(tag);
    }

    private void saveCommonData(CompoundTag tag) {
        CompoundTag inputTank1Tag = new CompoundTag();
        inputTank1.writeToNBT(inputTank1Tag);
        tag.put("inputTank1", inputTank1Tag);
        CompoundTag inputTank2Tag = new CompoundTag();
        tag.put("stack", stackHandler.serializeNBT());
        inputTank2.writeToNBT(inputTank2Tag);
        tag.put("inputTank2", inputTank2Tag);
        CompoundTag outputTankTag = new CompoundTag();
        outputTank.writeToNBT(outputTankTag);
        tag.put("outputTank", outputTankTag);
        if (heldFlask != null) {
            tag.put("flask", heldFlask.toNBT());
        }
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.FLUID_HANDLER) {

            //TODO if facing == .... return holder.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        loadCommonData(tag);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveCommonData(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());

        requestModelDataUpdate();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public @NotNull ModelData getModelData() {
        return ModelData.builder()
                .with(FLASK_PROPERTY, heldFlask)
                .with(POS_PROPERTY, getBlockPos())
                .build();
    }
}
