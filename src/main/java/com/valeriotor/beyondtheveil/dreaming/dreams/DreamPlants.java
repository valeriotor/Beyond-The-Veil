package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.function.Supplier;

public class DreamPlants extends Dream {

    public DreamPlants() {
        super(Memory.PLANT, 1, Reminiscence.TextReminiscence::new);
    }

    @Override
    public boolean activate(Player p, Level l) {
        boolean consumeVoid = DreamHandler.consumeVoid(p);
        Item toGive;
        String data;
        if (!consumeVoid) {
            int counter = DataUtil.incrementOrSetInteger(p, PlayerDataLib.plant_dream_counter.name(), 1, 0, false);
            switch (counter) {
                case 0 -> {
                    toGive = Registration.GRASS_WEED_SEEDS.get();
                    data = PlayerDataLib.grass_weed_seeds.name();
                }
                case 1 -> {
                    toGive = Registration.REDSTONE_WEED_SEEDS.get();
                    data = PlayerDataLib.redstone_weed_seeds.name();
                }
                default -> {
                    toGive = Registration.GHOST_WEED_SEEDS.get();
                    data = PlayerDataLib.ghost_weed_seeds.name();
                }
            }
            if (counter >= 2) {
                DataUtil.setInt(p, PlayerDataLib.plant_dream_counter.name(), -1, false);
            }
        } else {
            toGive = Registration.VIJHISS_ITEM.get();
            data = PlayerDataLib.vijhiss.name();
        }
        ItemHandlerHelper.giveItemToPlayer(p, new ItemStack(toGive));
        DataUtil.setBooleanOnServerAndSync(p, data, true);
        Reminiscence r = new Reminiscence.TextReminiscence("reminiscence.plant" + (consumeVoid ? ".vijhiss" : ""));
        DataUtil.addReminiscence(p, memory.getDataName(consumeVoid), r);
        return true;
    }

    @Override
    public boolean activatePlayer(Player caster, Player target, Level l) {
        return activate(caster, l);
    }

    @Override
    public boolean activatePos(Player p, Level l, BlockPos pos) {
        return activate(p, l);
    }
}
