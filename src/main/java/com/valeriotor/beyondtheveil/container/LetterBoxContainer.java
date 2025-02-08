package com.valeriotor.beyondtheveil.container;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public class LetterBoxContainer extends AbstractContainerMenu {

    private final BlockPos pos;
    private final Player player;

    public LetterBoxContainer(int pContainerId, BlockPos pos, Player player) {
        super(Registration.LETTER_BOX_CONTAINER.get(), pContainerId);
        this.pos = pos;
        this.player = player;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(pPlayer.level(), pos), pPlayer, Registration.LETTER_BOX.get());
    }
}
