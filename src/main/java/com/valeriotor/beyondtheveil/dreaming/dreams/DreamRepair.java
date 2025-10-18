package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DreamRepair extends Dream {

    public DreamRepair() {
        super(Memory.REPAIR, 1, () -> new RepairReminiscence("", 0));
    }

    @Override
    public boolean activate(Player p, Level l) {
        Tuple<Integer, Boolean> i = activateInternal(p);
        Reminiscence r = new RepairReminiscence("reminiscence.repair", i.getA());
        DataUtil.addReminiscence(p, memory.getDataName(i.getB()), r);
        return i.getA() > 0;
    }

    @Override
    public boolean activatePlayer(Player caster, Player target, Level l) {
        Tuple<Integer, Boolean> i = activateInternal(target, true);
        Reminiscence r = new RepairReminiscence("reminiscence.repair", i.getA());
        DataUtil.addReminiscence(caster, memory.getDataName(i.getB()), r);
        return i.getA() > 0;
    }

    @Override
    public boolean activatePos(Player p, Level l, BlockPos pos) {
        Tuple<Integer, Boolean> i = activateInternal(p);
        Reminiscence r = new RepairReminiscence("reminiscence.repair", i.getA());
        DataUtil.addReminiscence(p, memory.getDataName(i.getB()), r);
        return i.getA() > 0;
    }

    private Tuple<Integer, Boolean> activateInternal(Player player) {
        return activateInternal(player, false);
    }

    private Tuple<Integer, Boolean> activateInternal(Player player, boolean deteriorate) {
        boolean hasVoid = false;
        if (DreamHandler.consumeVoid(player)) {
            hasVoid = true;
        }
        int count = 0;
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack slot = player.getItemBySlot(equipmentSlot);
            if (!slot.isEmpty() && (slot.getDamageValue() > 0 || deteriorate)) {
                count++;
                if (!deteriorate) {
                    slot.setDamageValue(slot.getDamageValue() - 50 * (hasVoid ? 5 : 2) * (equipmentSlot.isArmor() ? 1 : 3));
                } else {
                    if (!hasVoid) {
                        slot.setDamageValue(slot.getDamageValue() - 50 * 2);
                    } else if(slot.getMaxDamage() > 0){
                        slot.hurtAndBreak(200 * (equipmentSlot.isArmor() ? 1 : 3), player, (p_21301_) -> {
                            p_21301_.broadcastBreakEvent(equipmentSlot);
                        });
                    }
                }
            }
        }
        return new Tuple<>(count, hasVoid);
    }

    private static class RepairReminiscence extends Reminiscence.TextReminiscence {

        private int number;

        public RepairReminiscence() {
        }

        public RepairReminiscence(String textKey, int number) {
            super(textKey);
            this.number = number;
        }

        @Override
        public CompoundTag save() {
            CompoundTag tag = super.save();
            tag.putInt("number", number);
            return tag;
        }

        @Override
        public void load(CompoundTag tag) {
            super.load(tag);
            number = tag.getInt("number");
        }

        @Override
        public Component getText() {
            return Component.translatable(getTextKey(), number);
        }
    }

}
