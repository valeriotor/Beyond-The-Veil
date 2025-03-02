package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.entity.FletumEntity;
import com.valeriotor.beyondtheveil.tile.LacrymatoryBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FletumItem extends Item {

    public FletumItem() {
        super(new Item.Properties().stacksTo(1));

    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        // TODO check if it is solid? context.getLevel().getBlockState(context.getClickedPos());
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        FletumEntity fletum = new FletumEntity(Registration.FLETUM.get(), level);
        BlockPos clickedPos = context.getClickedPos();
        Vec3 center = clickedPos.relative(context.getClickedFace()).getCenter().add(0, -0.5, 0);
        fletum.setPos(center);
        if (context.getPlayer() != null) {
            fletum.setMaster(context.getPlayer());
        }
        if (level.getBlockEntity(clickedPos) instanceof LacrymatoryBE) {
            fletum.setLacrymatoryPos(clickedPos);
        }
        level.addFreshEntity(fletum);
        stack.shrink(1);
        return InteractionResult.CONSUME;
    }
}
