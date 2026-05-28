package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.block.DreamFocusBlock;
import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.entity.dream_focus.DreamFocusFluidEntity;
import com.valeriotor.beyondtheveil.entity.dream_focus.DreamFocusItemEntity;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DreamFocusBE extends BlockEntity {

    private final DreamFocusBlock.FocusType type;
    private UUID usingPlayer = null;
    private List<Vec3> points = new ArrayList<>();
    private DyeColor color = DyeColor.BLUE;
    private boolean showPath = false;
    private int counter = 0;

    public DreamFocusBE(BlockPos pPos, BlockState pBlockState, DreamFocusBlock.FocusType type) {
        super(type == DreamFocusBlock.FocusType.FLUID ? BTVBlockEntities.DREAM_FOCUS_FLUID_BE.get() : BTVBlockEntities.DREAM_FOCUS_BE.get(), pPos, pBlockState);
        this.type = type;
    }

    public void setDyeColor(DyeColor color) {
        this.color = color;
        updateClient();
    }

    public void toggleShowPath() {
        showPath = !showPath;
        updateClient();
    }

    public boolean setPlayer(Player p) {
        if (usingPlayer != null) {
            return false;
        }
        usingPlayer = p.getUUID();
        return true;
    }

    public void clearList() {
        //points.clear();
        points = new ArrayList<>();
    }

    public void addPoint(Player player, Vec3 point) {
        if (player.getUUID().equals(usingPlayer)) {
            points.add(point);
        }
    }

    public void finish() {
        usingPlayer = null;
        updateClient();
    }

    public void tickServer() {
        if (level != null) {
            boolean redstone = level.hasNeighborSignal(worldPosition);
            if (!redstone) {
                int fleti = type.countFleti(level, worldPosition);
                BlockPos drainPos = type.getDrainPos(worldPosition, getBlockState());
                BlockPos startPos = type.getStartPos(worldPosition, getBlockState());
                if (fleti > 0) {
                    counter++;
                    if (counter % 150 == 0) {
                        if (type == DreamFocusBlock.FocusType.ITEM) {
                            BlockEntity src = level.getBlockEntity(drainPos);
                            if (src != null && src.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                                IItemHandler srcHandler = src.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().get();
                                for (int i = 0; i < srcHandler.getSlots(); i++) {
                                    ItemStack extracted = srcHandler.extractItem(i, 64, false);
                                    if (!extracted.isEmpty()) {
                                        DreamFocusItemEntity item = new DreamFocusItemEntity(BTVEntities.DREAM_FOCUS_ITEM.get(), level, extracted, points, worldPosition);
                                        item.setParticleColor(color.getTextColor());
                                        item.setPos(startPos.getCenter());
                                        level.addFreshEntity(item);
                                        break;
                                    }
                                }
                            }
                        } else if (type == DreamFocusBlock.FocusType.FLUID) {
                            BlockEntity src = level.getBlockEntity(drainPos);
                            BlockState state = level.getBlockState(drainPos);
                            if (src == null || !src.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent()) {
                                BlockPos drainPos2 = type.getDrainPos2(worldPosition, getBlockState());
                                src = level.getBlockEntity(drainPos2);
                                state = level.getBlockState(drainPos2);
                            }
                            if (src != null && src.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent()) {
                                IFluidHandler srcHandler = src.getCapability(ForgeCapabilities.FLUID_HANDLER).resolve().get();
                                FluidStack drained = srcHandler.drain(fletiToMb(fleti), IFluidHandler.FluidAction.EXECUTE);
                                src.setChanged();
                                level.sendBlockUpdated(drainPos, state, state, 2);
                                if (!drained.isEmpty()) {
                                    DreamFocusFluidEntity fluidEntity = new DreamFocusFluidEntity(BTVEntities.DREAM_FOCUS_FLUID.get(), level, drained, points, worldPosition);
                                    fluidEntity.setParticleColor(color.getTextColor());
                                    fluidEntity.setPos(startPos.getCenter());
                                    level.addFreshEntity(fluidEntity);
                                }
                                //DreamFocusItemEntity fluidEntity = new DreamFocusItemEntity(BTVEntities.DREAM_FOCUS_ITEM.get(), level, new ItemStack(Items.BOOK), points, worldPosition);
                                //fluidEntity.setParticleColor(color.getTextColor());
                                //fluidEntity.setPos(startPos.getCenter());
                                //level.addFreshEntity(fluidEntity);
                            }
                        }
                    }
                }
            }
        }

    }

    private int fletiToMb(int fleti) {
        return switch (fleti) {
            case 1 -> 100;
            case 2 -> 300;
            case 3 -> 600;
            case 4 -> 1000;
            default -> 0;
        };
    }

    public void tickClient() {
        if (showPath && counter++ % 2 == 0) {
            for (int i = 0; i < points.size(); i++) {
                if (i % 2 == 0) {
                    Vec3 vec3 = points.get(i);
                    if (level != null) {
                        int c = color.getTextColor();
                        ClientMethods.colorParticle(DustParticleOptions.REDSTONE, vec3.x, vec3.y, vec3.z, 0, 0, 0, c >> 16, ((c >> 8) & 255), c & 255);
                    }
                }
            }
        }
    }

    private void updateClient() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        }
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
        if (pkt != null) {
            load(pkt.getTag());
        }
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveCommonData(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        loadCommonData(pTag);
    }

    private void saveCommonData(CompoundTag tag) {
        ListTag pointsTag = savePoints(points);
        tag.put("points", pointsTag);
        tag.putInt("dye", color.getId());
        tag.putBoolean("showPath", showPath);
    }

    @NotNull
    public static ListTag savePoints(List<Vec3> points) {
        ListTag pointsTag = new ListTag();
        int i = 0;
        for (Vec3 point : points) {
            CompoundTag pointTag = new CompoundTag();
            pointTag.putDouble("x", point.x);
            pointTag.putDouble("y", point.y);
            pointTag.putDouble("z", point.z);
            pointsTag.addTag(i++, pointTag);
        }
        return pointsTag;
    }

    private void loadCommonData(CompoundTag tag) {
        if (tag.contains("points")) {
            //points.clear(); WE DON'T CLEAR IT, so that floating dreamfocusentities can still access the old list
            points = loadPoints(tag);
        }
        if (tag.contains("dye")) {
            color = DyeColor.byId(tag.getInt("dye"));
        }
        showPath = tag.getBoolean("showPath");
    }

    public static List<Vec3> loadPoints(CompoundTag tag) {
        List<Vec3> points = new ArrayList<>();
        ListTag pointsTag = tag.getList("points", Tag.TAG_COMPOUND);
        for (int i = 0; i < pointsTag.size(); i++) {
            CompoundTag pointTag = pointsTag.getCompound(i);
            points.add(new Vec3(pointTag.getDouble("x"), pointTag.getDouble("y"), pointTag.getDouble("z")));
        }
        return points;
    }
}
