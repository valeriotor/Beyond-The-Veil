package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.rituals.bindings.BindingEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class BloodFistItem extends Item {

    public BloodFistItem() {
        super(new Properties().stacksTo(1));
    }


    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return BindingEvents.useFistOnBlock(pContext);
    }
}
