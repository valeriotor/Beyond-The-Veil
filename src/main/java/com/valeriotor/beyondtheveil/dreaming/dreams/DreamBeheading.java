package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.function.Supplier;

public class DreamBeheading extends Dream {

    public DreamBeheading() {
        super(Memory.BEHEADING, 1, Reminiscence.TextReminiscence::new);
    }

    @Override
    public boolean activate(Player p, Level l) {
        getHead(p, p);
        return true;
    }

    @Override
    public boolean activatePlayer(Player caster, Player target, Level l) {
        getHead(caster, target);
        return true;
    }

    @Override
    public boolean activatePos(Player p, Level l, BlockPos pos) {
        getHead(p, p);
        return true;
    }

    private void getHead(Player caster, Player target) {
        ItemStack head = new ItemStack(Items.PLAYER_HEAD);
        head.getOrCreateTag().put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), target.getGameProfile()));
        ItemHandlerHelper.giveItemToPlayer(caster, head);
        Reminiscence r = new Reminiscence.TextReminiscence("reminiscence.beheading");
        DataUtil.addReminiscence(caster, memory.getDataName(false), r);
    }
}
