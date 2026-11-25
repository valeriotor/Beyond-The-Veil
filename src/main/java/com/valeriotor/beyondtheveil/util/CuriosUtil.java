package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

public class CuriosUtil {

    public static boolean hasItem(Player player, String identifier, Item item) {
        Optional<ICuriosItemHandler> curiosItemHandler = CuriosApi.getCuriosInventory(player).resolve();
        if (curiosItemHandler.isPresent()) {
            ICuriosItemHandler inv = curiosItemHandler.get();
            Optional<ICurioStacksHandler> slotOptional = inv.getStacksHandler(identifier);
            if (slotOptional.isPresent()) {
                ICurioStacksHandler slot = slotOptional.get();
                ItemStack stackInSlot = slot.getStacks().getStackInSlot(0);
                return stackInSlot.getItem() == item;
            }
        }
        return false;
    }


}
