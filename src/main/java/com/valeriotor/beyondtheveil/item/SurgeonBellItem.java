package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.block.FlaskShelfBlock;
import com.valeriotor.beyondtheveil.block.PatientPodBlock;
import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.notes.PositionStep;
import com.valeriotor.beyondtheveil.surgery.notes.Report;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStep;
import com.valeriotor.beyondtheveil.surgery.surgeon.BellData;
import com.valeriotor.beyondtheveil.tile.SurgeryBedBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.UUID;

public class SurgeonBellItem extends Item {

    public SurgeonBellItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (context.getLevel() instanceof ServerLevel sl && context.getPlayer() instanceof ServerPlayer sp) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("surgeon")) {
                UUID uuid = tag.getUUID("surgeon");
                if (sl.getEntity(uuid) instanceof SurgeonEntity surgeon) {
                    BlockPos pos = context.getClickedPos();
                    BlockState state = sl.getBlockState(pos);
                    BlockEntity be = sl.getBlockEntity(pos);
                    BellData data = surgeon.bellData;
                    Block b = state.getBlock();
                    if (b == Registration.PATIENT_POD.get()) {
                        if (state.getValue(Registration.PATIENT_POD.get().getLevelProperty()) == 1) {
                            pos = pos.below();
                        }
                        if (sp.isShiftKeyDown()) {
                            data.addPosition(pos, BellData.PositionType.OUTPUT_PODS);
                        } else {
                            data.addPosition(pos, BellData.PositionType.INPUT_PODS);
                        }
                    } else if (b == Registration.FLASK_SHELF.get() || b instanceof FlaskBlock) {
                        if (b instanceof FlaskShelfBlock fsb) {
                            pos = fsb.findCenter(pos, state);
                        }
                        if (!sp.isShiftKeyDown()) {
                            data.addPosition(pos, BellData.PositionType.INPUT_CONTAINERS);
                        }
                    } else if (b == Registration.SURGERY_BED.get() || b == Registration.WATERY_CRADLE.get()) {
                        if (b == Registration.SURGERY_BED.get()) {
                            pos = Registration.SURGERY_BED.get().findCenter(pos, state);
                        } else if (b == Registration.WATERY_CRADLE.get()) {
                            pos = Registration.WATERY_CRADLE.get().findCenter(pos, state);
                        }
                        data.addPosition(pos, BellData.PositionType.BE);
                        Report r = surgeon.getReport();
                        if (r != null) {
                            for (ReportStep step : r.getSteps()) {
                                if (step instanceof PositionStep step1 && sl.getBlockEntity(pos) instanceof SurgicalBE be1 && !be1.allowedLocations().contains(step1.getLocation().getLocation())) {
                                    sp.sendSystemMessage(Component.translatable(be instanceof SurgeryBedBE ? "interact.surgeon.bad_be_location_bed" : "interact.surgeon.bad_be_location_cradle"));
                                    break;
                                }
                            }
                        }
                    } else if (be != null && be.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                        if (sp.isShiftKeyDown()) {
                            data.addPosition(pos, BellData.PositionType.OUTPUT_CONTAINERS);
                        } else {
                            data.addPosition(pos, BellData.PositionType.INPUT_CONTAINERS);
                        }
                    } else if (context.getClickedFace() == Direction.UP) {
                        if (sp.isShiftKeyDown()) {
                            data.addPosition(pos, BellData.PositionType.OUTPUT_SPOTS);
                        } else {
                            data.addPosition(pos, BellData.PositionType.INPUT_SPOTS);
                        }
                    } else {
                        return InteractionResult.PASS;
                    }
                    return InteractionResult.CONSUME;
                }
            }
        } else {
            return InteractionResult.SUCCESS;
        }
        return super.onItemUseFirst(stack, context);
    }
}
