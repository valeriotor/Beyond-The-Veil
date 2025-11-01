package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.entity.SurgeonLarvaEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class SurgeonLarvaItem extends Item {

    public SurgeonLarvaItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if(context.getPlayer() instanceof ServerPlayer sp && !sp.isShiftKeyDown()) {
            SurgeonLarvaEntity entity = new SurgeonLarvaEntity(BTVEntities.SURGEON_LARVA.get(), context.getLevel());
            entity.setMaster(sp);
            entity.setPos(context.getClickLocation());
            sp.level().addFreshEntity(entity);
            if (!sp.isCreative()) {
                stack.shrink(1);
            }
            return InteractionResult.CONSUME;
        }
        return super.onItemUseFirst(stack, context);
    }
}
