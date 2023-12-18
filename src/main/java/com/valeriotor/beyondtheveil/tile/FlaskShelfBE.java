package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.block.FlaskShelfBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class FlaskShelfBE extends BlockEntity {

    public static final ModelProperty<List<Flask>> FLASKS_PROPERTY = new ModelProperty<>();
    public final List<Flask> flasks = new ArrayList<>();
    public final VoxelShape[][] shapes = new VoxelShape[3][3]; // Shape for each of the 3x3 shelves: [0: bottom, 1: medium, 2: top][0: left, 1: center, 2: right]


    public FlaskShelfBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.FLASK_SHELF_BE.get(), pWorldPosition, pBlockState);
        for (int i = 0; i < shapes.length; i++) {
            for (int j = 0; j < shapes[i].length; j++) {
                shapes[i][j] = Shapes.empty();
            }
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        if(pTag != null) {
            super.load(pTag);
        }
        loadClientData(pTag);
    }

    private void loadClientData(CompoundTag pTag) {
        flasks.clear();
        CompoundTag flasks1 = pTag.getCompound("flasks");
        for (String key: flasks1.getAllKeys()) {
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
        if(!pLevel.isClientSide) {
            Flask newFlask = new Flask(location.x, location.y, location.z, flaskBlock.size);
            flasks.add(newFlask);
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            }
            computeFlasksShape();
            if(!pPlayer.isCreative())
                pPlayer.getItemInHand(pHand).shrink(1);
        }
        return true;
    }

    public Flask getLookedAtFlask(Level pLevel, BlockPos selectedShelfPos, BlockHitResult pHit) {
        for (Flask flask : flasks) {
            AABB flaskAABB = getAABB(flask.size, flask.x, flask.y, flask.z);
            Vec3 loc = pHit.getLocation();
            if (containsButSlightlyLarger(loc, flaskAABB)) {
                return flask;
            }
        }
        return null;
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
        for (AABB box: shape.toAabbs()) {
            if (newFlaskAABB.intersects(box)) {
                intersects = true;
                break;
            }
        }
        return intersects;
    }

    /** Coordinates should be relative to selectedShelfPos
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
                shapes[y][i + 1] = FlaskShelfBlock.getBaseShape(y, i+1, facing);
                int x = facing.getAxis() == Direction.Axis.X ? 0 : (facing == Direction.NORTH ? -i : i);
                int z = facing.getAxis() == Direction.Axis.Z ? 0 : (facing == Direction.EAST ? -i : i);
                for (Flask f : flasks) {
                    if(f.getY() >= worldPosition.getY()+y-1 && f.getY() <= worldPosition.getY()+y) {
                        shapes[y][i + 1] = Shapes.or(shapes[y][i + 1], f.computeShapeWithOffset(f.getX()-worldPosition.getX()-0.5-x, f.getY()-worldPosition.getY()-y+1, f.getZ()-worldPosition.getZ()-0.5-z));
                    }
                }
                //pLevel.setBlock(pPos.offset(x, y, z), pState.setValue(SIDE, i+1).setValue(LEVEL, y), 3);
            }
        }
        System.out.println("done");
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
    }

    @Override
    public AABB getRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }

    //@Nonnull
    //@Override
    //public IModelData getModelData() {
    //    return new ModelDataMap.Builder()
    //            .withInitial(FLASKS_PROPERTY, flasks)
    //            .build();
    //}


    public class Flask {
        // flask type
        // fill level
        private final double x, y, z;
        private final FlaskBlock.FlaskSize size;

        private Flask(double x, double y, double z, FlaskBlock.FlaskSize size) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.size = size;
        }

        private Flask(CompoundTag tag) {
            this.x = tag.getDouble("x");
            this.y = tag.getDouble("y");
            this.z = tag.getDouble("z");
            this.size = FlaskBlock.FlaskSize.values()[tag.getInt("size")];
        }

        private CompoundTag toNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putDouble("x", x);
            tag.putDouble("y", y);
            tag.putDouble("z", z);
            tag.putInt("size", size.ordinal());
            return tag;
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

        private VoxelShape computeShapeWithOffset(double offsetX, double offsetY, double offsetZ) {
            //double[][] arrays = switch (size) {
            //    case SMALL -> new double[][] {FlaskBlock.SMALL1, FlaskBlock.SMALL2, FlaskBlock.SMALL3, FlaskBlock.SMALL4, FlaskBlock.SMALL5, FlaskBlock.SMALL6};
            //    case MEDIUM -> new double[][] {FlaskBlock.MEDIUM1, FlaskBlock.MEDIUM2, FlaskBlock.MEDIUM3, FlaskBlock.MEDIUM4, FlaskBlock.MEDIUM5, FlaskBlock.MEDIUM6};
            //    case LARGE -> new double[][] {FlaskBlock.LARGE1, FlaskBlock.LARGE2, FlaskBlock.LARGE3, FlaskBlock.LARGE4, FlaskBlock.LARGE5, FlaskBlock.LARGE6};
            //};
            double[][] arrays = switch (size) {
                case SMALL -> new double[][] {FlaskBlock.SMALL_SIMPLE};
                case MEDIUM -> new double[][] {FlaskBlock.MEDIUM_SIMPLE};
                case LARGE -> new double[][] {FlaskBlock.LARGE_SIMPLE};
            };
            VoxelShape shape = Shapes.empty();
            for (double[] array : arrays) {
                shape = Shapes.or(shape, Shapes.box(array[0] + offsetX, array[1] + offsetY, array[2] + offsetZ, array[3] + offsetX, array[4] + offsetY, array[5] + offsetZ));
            }
            return shape;
        }
    }


}
