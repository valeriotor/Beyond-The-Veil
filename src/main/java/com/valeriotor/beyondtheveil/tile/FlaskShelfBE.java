package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.block.FlaskShelfBlock;
import com.valeriotor.beyondtheveil.item.SampleTubeItem;
import com.valeriotor.beyondtheveil.item.SurgeryIngredient;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlaskShelfBE extends BlockEntity {

    public static final ModelProperty<List<Flask>> FLASKS_PROPERTY = new ModelProperty<>();
    public final List<Flask> flasks = new ArrayList<>();
    public final VoxelShape[][] shapes = new VoxelShape[3][3]; // Shape for each of the 3x3 shelves: [0: bottom, 1: medium, 2: top][0: left, 1: center, 2: right]
    private final FlaskShelfFluidHandler fluidHandler = new FlaskShelfFluidHandler();
    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> fluidHandler);
    private final IItemHandler stackHandler = new FlaskShelfItemHandler();
    private final LazyOptional<IItemHandler> stackHolder = LazyOptional.of(() -> stackHandler);

    public static final ModelProperty<List<Flask>> FLASK_PROPERTY = new ModelProperty<>();
    public static final ModelProperty<BlockPos> POS_PROPERTY = new ModelProperty<>();


    public FlaskShelfBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BTVBlockEntities.FLASK_SHELF_BE.get(), pWorldPosition, pBlockState);
        for (int i = 0; i < shapes.length; i++) {
            for (int j = 0; j < shapes[i].length; j++) {
                shapes[i][j] = Shapes.empty();
            }
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag != null) {
            super.load(pTag);
        }
        loadClientData(pTag);
    }

    private void loadClientData(CompoundTag pTag) {
        flasks.clear();
        CompoundTag flasks1 = pTag.getCompound("flasks");
        for (String key : flasks1.getAllKeys()) {
            flasks.add(new Flask(flasks1.getCompound(key)));
        }
        computeFlasksShape();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("dummy", 0);
        saveClientData(pTag);
    }

    private void saveClientData(CompoundTag pTag) {
        CompoundTag flaskTag = new CompoundTag();
        pTag.put("flasks", flaskTag);
        int i = 0;
        for (Flask f : flasks) {
            flaskTag.put(String.valueOf(i), f.toNBT());
            i++;
        }
    }

    public boolean tryPlaceFlask(Level pLevel, BlockPos selectedShelfPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        FlaskBlock flaskBlock = (FlaskBlock) Block.byItem(stack.getItem());
        Vec3 location = pHit.getLocation();
        boolean intersects = intersects(pLevel, selectedShelfPos, pHit, flaskBlock);
        if (intersects) {
            return false;
        }
        if (!pLevel.isClientSide) {
            Flask newFlask = new Flask(location.x, location.y, location.z, stack, flaskBlock);
            flasks.add(newFlask);
            updateClient();
            computeFlasksShape();
            if (!pPlayer.isCreative())
                pPlayer.getItemInHand(pHand).shrink(1);
        }
        return true;
    }

    public void tryBreakFlask(BlockPos selectedShelfPos, Player pPlayer, Vec3 hitLocation) {
        Flask f = getLookedAtFlask(pPlayer.level(), selectedShelfPos, hitLocation);
        if (f != null) {
            flasks.remove(f);
            updateClient();
            computeFlasksShape();
            level.playSound(null, getBlockPos(), SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1, 1);
            ItemStack flask = f.toItem();
            ItemEntity itementity = new ItemEntity(level, hitLocation.x() + 0.5D, hitLocation.y() + 0.5D, hitLocation.z() + 0.5D, flask);
            itementity.setDefaultPickUpDelay();
            level.addFreshEntity(itementity);

        }
    }

    public Flask getLookedAtFlask(Level pLevel, BlockPos selectedShelfPos, Vec3 hitLocation) {
        for (Flask flask : flasks) {
            AABB flaskAABB = getAABB(flask.size, flask.x, flask.y, flask.z);
            if (containsButSlightlyLarger(hitLocation, flaskAABB)) {
                return flask;
            }
        }
        return null;
    }

    public InteractionResult interact(Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent() || itemStack.getItem() == Registration.SAMPLE_TUBE.get()) {
            if (itemStack.getItem() != Registration.SYRINGE.get()) {
                if (!pLevel.isClientSide) {
                    Flask lookedAtFlask = getLookedAtFlask(pLevel, pPos, pHit.getLocation());
                    if (lookedAtFlask != null) {
                        if (itemStack.getItem() == Registration.SAMPLE_TUBE.get()) {
                            FluidStack fluidStack = SampleTubeItem.getFluid(itemStack);
                            if (fluidStack.isEmpty()) {
                                lookedAtFlask.holder.ifPresent(h -> {
                                    if (h.drain(100, IFluidHandler.FluidAction.SIMULATE).getAmount() == 100) {
                                        Fluid fluid = h.drain(100, IFluidHandler.FluidAction.EXECUTE).getFluid();
                                        if (itemStack.getCount() == 1) {
                                            SampleTubeItem.setFluid(itemStack, fluid);
                                        } else {
                                            itemStack.shrink(1);
                                            ItemStack newStack = new ItemStack(Registration.SAMPLE_TUBE.get());
                                            SampleTubeItem.setFluid(newStack, fluid);
                                            ItemHandlerHelper.giveItemToPlayer(pPlayer, newStack);
                                        }
                                    }
                                });
                            } else {
                                lookedAtFlask.holder.ifPresent(h -> {
                                    if (h.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) == 100) {
                                        h.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                                        if (itemStack.getCount() == 1) {
                                            SampleTubeItem.setFluid(itemStack, null);
                                        } else {
                                            itemStack.shrink(1);
                                            ItemStack newStack = new ItemStack(Registration.SAMPLE_TUBE.get());
                                            SampleTubeItem.setFluid(newStack, null);
                                            ItemHandlerHelper.giveItemToPlayer(pPlayer, newStack);
                                        }
                                    }
                                });
                            }
                        } else {
                            lookedAtFlask.holder.map(handler -> FluidUtil.interactWithFluidHandler(pPlayer, pHand, handler)).orElse(false);
                        }
                        updateClient();
                    }
                }
                return InteractionResult.SUCCESS;
            } else {
                if (!pLevel.isClientSide) {
                    Flask lookedAtFlask = getLookedAtFlask(pLevel, pPos, pHit.getLocation());
                    if (lookedAtFlask != null) {
                        IFluidHandlerItem syringe = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElseThrow();
                        if (!pPlayer.isShiftKeyDown()) {
                            if (syringe.fill(lookedAtFlask.tank.drain(1, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.SIMULATE) == 1) {
                                syringe.fill(lookedAtFlask.tank.drain(1, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                                updateClient();
                            }
                        } else {
                            if (lookedAtFlask.tank.fill(syringe.drain(1, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.SIMULATE) == 1) {
                                lookedAtFlask.tank.fill(syringe.drain(1, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                                //lookedAtFlask.tank.fill(new FluidStack(Registration.SOURCE_FLUID_LIQUID_BLAZE_POWDER.get(), 1), IFluidHandler.FluidAction.EXECUTE);
                                updateClient();
                            }
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.SUCCESS;
            }
        } else if (itemStack.getItem() instanceof SurgeryIngredient) {
            if (!pLevel.isClientSide) {
                Flask lookedAtFlask = getLookedAtFlask(pLevel, pPos, pHit.getLocation());
                if (lookedAtFlask != null) {
                    pPlayer.setItemInHand(pHand, lookedAtFlask.stackHandler.insertItem(0, itemStack, false));
                    updateClient();
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.SUCCESS;
        } else if (itemStack.isEmpty()) {
            if (!pLevel.isClientSide) {
                Flask lookedAtFlask = getLookedAtFlask(pLevel, pPos, pHit.getLocation());
                if (lookedAtFlask != null) {
                    pPlayer.setItemInHand(pHand, lookedAtFlask.stackHandler.extractItem(0, 16, false));
                    updateClient();
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.SUCCESS;
        } else if (itemStack.getItem() == Registration.FORCEPS.get()) {
            if (!pLevel.isClientSide) {
                CompoundTag forcepsTag = itemStack.getOrCreateTag();
                Flask lookedAtFlask = getLookedAtFlask(pLevel, pPos, pHit.getLocation());
                if (lookedAtFlask != null) {
                    if (!forcepsTag.contains("contained")) {
                        ItemStack extracted = lookedAtFlask.stackHandler.extractItem(0, 1, true);
                        if (!extracted.isEmpty()) {
                            forcepsTag.put("contained", lookedAtFlask.stackHandler.extractItem(0, 1, false).serializeNBT());
                            updateClient();
                        }
                    } else {
                        ItemStack toInsert = ItemStack.of(forcepsTag.getCompound("contained"));
                        if (lookedAtFlask.stackHandler.insertItem(0, toInsert, true).isEmpty()) {
                            lookedAtFlask.stackHandler.insertItem(0, toInsert, false);
                            forcepsTag.remove("contained");
                            updateClient();
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    private void updateClient() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        }
    }

    private boolean containsButSlightlyLarger(Vec3 vec3, AABB aabb) {
        return containsButSlightlyLarger(vec3.x, vec3.y, vec3.z, aabb);
    }

    private boolean containsButSlightlyLarger(double pX, double pY, double pZ, AABB aabb) {
        return pX >= aabb.minX && pX <= aabb.maxX && pY >= aabb.minY && pY <= aabb.maxY && pZ >= aabb.minZ && pZ <= aabb.maxZ;
    }

    public boolean intersects(Level pLevel, BlockPos selectedShelfPos, BlockHitResult pHit, FlaskBlock flaskBlock) {
        Vec3 locRelativeToSelectedBlock = pHit.getLocation().subtract(selectedShelfPos.getX(), selectedShelfPos.getY(), selectedShelfPos.getZ());
        VoxelShape shape = pLevel.getBlockState(selectedShelfPos).getShape(pLevel, selectedShelfPos);
        FlaskBlock.FlaskSize size = flaskBlock == Registration.FLASK_SMALL.get() ? FlaskBlock.FlaskSize.SMALL : (flaskBlock == Registration.FLASK_MEDIUM.get() ? FlaskBlock.FlaskSize.MEDIUM : FlaskBlock.FlaskSize.LARGE);
        AABB newFlaskAABB = getAABB(size, locRelativeToSelectedBlock.x, locRelativeToSelectedBlock.y, locRelativeToSelectedBlock.z);
        boolean intersects = false;
        for (AABB box : shape.toAabbs()) {
            if (newFlaskAABB.intersects(box)) {
                intersects = true;
                break;
            }
        }
        return intersects;
    }

    /**
     * Coordinates should be relative to selectedShelfPos
     */
    private AABB getAABB(FlaskBlock.FlaskSize size, double x, double y, double z) {
        double[] array = size.getSimpleShape();
        x -= 0.5;
        z -= 0.5;
        return new AABB(x + array[0], y + array[1], z + array[2], x + array[3], y + array[4], z + array[5]);
    }

    private void computeFlasksShape() {
        BlockState state = this.getBlockState();
        for (int i = -1; i <= 1; i++) {
            for (int y = 0; y < 3; y++) {
                Direction facing = state.getValue(FlaskShelfBlock.FACING);
                shapes[y][i + 1] = FlaskShelfBlock.getBaseShape(y, i + 1, facing);
                int x = facing.getAxis() == Direction.Axis.X ? 0 : (facing == Direction.NORTH ? -i : i);
                int z = facing.getAxis() == Direction.Axis.Z ? 0 : (facing == Direction.EAST ? -i : i);
                for (Flask f : flasks) {
                    if (f.getY() >= worldPosition.getY() + y - 1 && f.getY() <= worldPosition.getY() + y) {
                        shapes[y][i + 1] = Shapes.or(shapes[y][i + 1], f.computeShapeWithOffset(f.getX() - worldPosition.getX() - 0.5 - x, f.getY() - worldPosition.getY() - y + 1, f.getZ() - worldPosition.getZ() - 0.5 - z));
                    }
                }
                //pLevel.setBlock(pPos.offset(x, y, z), pState.setValue(SIDE, i+1).setValue(LEVEL, y), 3);
            }
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction facing) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return holder.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return stackHolder.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        loadClientData(tag); // default behaviour, here just to remind me of that
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveClientData(tag);
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
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-1, -1, -1), getBlockPos().offset(2, 2, 2));
    }

    @Override
    public @NotNull ModelData getModelData() {
        return ModelData.builder()
                .with(FLASK_PROPERTY, Collections.synchronizedList(new ArrayList<>(flasks)))
                .with(POS_PROPERTY, getBlockPos())
                .build();
    }

    //@Nonnull
    //@Override
    //public IModelData getModelData() {
    //    return new ModelDataMap.Builder()
    //            .withInitial(FLASKS_PROPERTY, flasks)
    //            .build();
    //}


    public static class Flask {
        // flask type
        // fill level
        private final double x, y, z;
        private final FlaskBlock.FlaskSize size;
        private FluidTank tank;
        private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

        private ItemStackHandler createStackHandler(FlaskBlock.FlaskSize size) {
            return FlaskBE.createStackHandler(size);
        }

        private final ItemStackHandler stackHandler;
        private final LazyOptional<IItemHandler> stackHolder;

        Flask(double x, double y, double z, FlaskBlock.FlaskSize size) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.size = size;
            this.tank = FlaskBE.getTankByFlaskType(size);
            stackHandler = createStackHandler(size);
            stackHolder = LazyOptional.of(() -> stackHandler);
        }

        Flask(double x, double y, double z, ItemStack stack, FlaskBlock flaskBlock) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.size = flaskBlock.size;
            this.tank = FlaskBE.getTankByFlaskType(size);
            stackHandler = createStackHandler(size);
            stackHolder = LazyOptional.of(() -> stackHandler);
            CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
            if (blockEntityData != null) {
                tank.readFromNBT(blockEntityData);
                if (blockEntityData.contains("stack")) {
                    stackHandler.deserializeNBT(blockEntityData.getCompound("stack"));
                }
            }
        }

        Flask(CompoundTag tag) {
            this.x = tag.getDouble("x");
            this.y = tag.getDouble("y");
            this.z = tag.getDouble("z");
            this.size = FlaskBlock.FlaskSize.values()[tag.getInt("size")];
            this.tank = FlaskBE.getTankByFlaskType(size);
            tank.readFromNBT(tag.getCompound("tank"));
            stackHandler = createStackHandler(size);
            stackHolder = LazyOptional.of(() -> stackHandler);
            if (tag.contains("stack")) {
                stackHandler.deserializeNBT(tag.getCompound("stack"));
            }
        }

        CompoundTag toNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putDouble("x", x);
            tag.putDouble("y", y);
            tag.putDouble("z", z);
            tag.putInt("size", size.ordinal());
            tag.put("tank", tank.writeToNBT(new CompoundTag()));
            tag.put("stack", stackHandler.serializeNBT());
            return tag;
        }

        ItemStack toItem() {
            ItemStack flask = new ItemStack(FlaskBlock.sizeToBlock.get(getSize()));
            CompoundTag tag = new CompoundTag();
            tank.writeToNBT(tag);
            tag.put("stack", stackHandler.serializeNBT());
            BlockItem.setBlockEntityData(flask, BTVBlockEntities.FLASK_BE.get(), tag);
            return flask;
        }

        public FlaskBlock.FlaskSize getSize() {
            return size;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public FluidTank getTank() {
            return tank;
        }

        public ItemStackHandler getStackHandler() {
            return stackHandler;
        }

        VoxelShape computeShapeWithOffset(double offsetX, double offsetY, double offsetZ) {
            //double[][] arrays = switch (size) {
            //    case SMALL -> new double[][] {FlaskBlock.SMALL1, FlaskBlock.SMALL2, FlaskBlock.SMALL3, FlaskBlock.SMALL4, FlaskBlock.SMALL5, FlaskBlock.SMALL6};
            //    case MEDIUM -> new double[][] {FlaskBlock.MEDIUM1, FlaskBlock.MEDIUM2, FlaskBlock.MEDIUM3, FlaskBlock.MEDIUM4, FlaskBlock.MEDIUM5, FlaskBlock.MEDIUM6};
            //    case LARGE -> new double[][] {FlaskBlock.LARGE1, FlaskBlock.LARGE2, FlaskBlock.LARGE3, FlaskBlock.LARGE4, FlaskBlock.LARGE5, FlaskBlock.LARGE6};
            //};
            double[][] arrays = switch (size) {
                case SMALL -> new double[][]{FlaskBlock.SMALL_SIMPLE};
                case MEDIUM -> new double[][]{FlaskBlock.MEDIUM_SIMPLE};
                case LARGE -> new double[][]{FlaskBlock.LARGE_SIMPLE};
                case ITEM -> new double[][]{FlaskBlock.ITEM_SIMPLE};
            };
            VoxelShape shape = Shapes.empty();
            for (double[] array : arrays) {
                shape = Shapes.or(shape, Shapes.box(array[0] + offsetX, array[1] + offsetY, array[2] + offsetZ, array[3] + offsetX, array[4] + offsetY, array[5] + offsetZ));
            }
            return shape;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Flask flask = (Flask) o;

            if (Double.compare(flask.x, x) != 0) return false;
            if (Double.compare(flask.y, y) != 0) return false;
            return Double.compare(flask.z, z) == 0;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            temp = Double.doubleToLongBits(x);
            result = (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(y);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(z);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }

    private class FlaskShelfFluidHandler implements IFluidHandler {

        @Override
        public int getTanks() {
            return flasks.size();
        }

        @Override
        public @NotNull FluidStack getFluidInTank(int tank) {
            return flasks.get(tank).tank.getFluid();
        }

        @Override
        public int getTankCapacity(int tank) {
            return flasks.get(tank).tank.getCapacity();
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            return flasks.get(tank).tank.isFluidValid(stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (resource.isEmpty()) {
                return 0;
            }
            FluidStack copy = resource.copy();
            List<Flask> matchingFlasks = flasks.stream().filter(f -> f.tank.getFluid().isFluidEqual(resource)).toList();
            int filled = 0;
            for (Flask matchingFlask : matchingFlasks) {
                int newlyFilled = matchingFlask.tank.fill(copy, action);
                filled += newlyFilled;
                copy.setAmount(copy.getAmount() - newlyFilled);
                if (copy.isEmpty()) {
                    if (action.execute()) {
                        updateClient();
                    }
                    return filled;
                }
            }
            List<Flask> emptyFlasks = flasks.stream().filter(f -> f.tank.isEmpty()).toList();
            for (Flask emptyFlask : emptyFlasks) {
                int newlyFilled = emptyFlask.tank.fill(copy, action);
                filled += newlyFilled;
                copy.setAmount(copy.getAmount() - newlyFilled);
                if (copy.isEmpty()) {
                    if (action.execute()) {
                        updateClient();
                    }
                    return filled;
                }
            }
            if (action.execute()) {
                updateClient();
            }
            return filled;
        }

        @Override
        public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
            if (resource.isEmpty()) {
                return FluidStack.EMPTY;
            }
            FluidStack copy = resource.copy();
            for (Flask flask : flasks) {
                if (flask.tank.getFluid().isFluidEqual(copy)) {
                    FluidStack drained = flask.tank.drain(copy, action);
                    copy.shrink(drained.getAmount());
                    if (copy.isEmpty()) {
                        if (action.execute()) {
                            updateClient();
                        }
                        return resource.copy();
                    }
                }
            }
            if (action.execute()) {
                updateClient();
            }
            return new FluidStack(copy.getFluid(), resource.getAmount() - copy.getAmount());
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            if (maxDrain == 0) {
                return FluidStack.EMPTY;
            }
            FluidStack resource = null;
            for (Flask flask : flasks) {
                if (!flask.tank.isEmpty()) {
                    if (resource == null) {
                        resource = flask.tank.drain(maxDrain, action);
                        maxDrain -= resource.getAmount();
                        if (maxDrain == 0) {
                            if (action.execute()) {
                                updateClient();
                            }
                            return resource;
                        }
                    } else if (resource.getFluid().isSame(flask.tank.getFluid().getFluid())) {
                        FluidStack newResource = flask.tank.drain(maxDrain, action);
                        resource.grow(newResource.getAmount());
                        maxDrain -= newResource.getAmount();
                        if (maxDrain == 0) {
                            if (action.execute()) {
                                updateClient();
                            }
                            return resource;
                        }
                    }
                }
            }
            return resource == null ? FluidStack.EMPTY : resource;
        }
    }

    private class FlaskShelfItemHandler implements IItemHandler {
        @Override
        public int getSlots() {
            return flasks.size();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return flasks.get(slot).stackHandler.getStackInSlot(0);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (stack.isEmpty()) {
                return stack;
            }
            ItemStack copy = stack.copy();
            List<Flask> matchingFlasks = flasks.stream().filter(f -> ItemHandlerHelper.canItemStacksStack(f.stackHandler.getStackInSlot(0), stack)).toList();
            for (Flask matchingFlask : matchingFlasks) {
                copy = matchingFlask.stackHandler.insertItem(0, copy, simulate);
                if (copy.isEmpty()) {
                    if (!simulate) {
                        updateClient();
                    }
                    return copy;
                }
            }
            List<Flask> emptyFlasks = flasks.stream().filter(f -> f.stackHandler.getStackInSlot(0).isEmpty()).toList();
            for (Flask emptyFlask : emptyFlasks) {
                copy = emptyFlask.stackHandler.insertItem(0, copy, simulate);
                if (copy.isEmpty()) {
                    if (!simulate) {
                        updateClient();
                    }
                    return copy;
                }
            }
            if (!simulate) {
                updateClient();
            }
            return copy;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return flasks.get(slot).stackHandler.extractItem(0, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return flasks.get(slot).stackHandler.getSlotLimit(0);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return flasks.get(slot).stackHandler.isItemValid(0, stack);
        }
    }


}
