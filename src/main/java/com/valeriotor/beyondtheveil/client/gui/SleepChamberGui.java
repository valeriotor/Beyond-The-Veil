package com.valeriotor.beyondtheveil.client.gui;

import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SleepChamberGui extends Screen {

    private static final int FALL_ASLEEP_TIME = 60;
    private Button leaveBedButton;
    private int counter = 0;

    public SleepChamberGui() {
        super(Component.translatable("gui.sleep_chamber"));
    }

    protected void init() {
        super.init();
        this.leaveBedButton = Button.builder(Component.translatable("gui.sleep_chamber.wake"), (p_96074_) -> {
            this.sendWakeUp();
        }).bounds(this.width / 2 - 100, this.height - 40, 200, 20).build();
        this.addRenderableWidget(this.leaveBedButton);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int alpha = (int) (((counter + pPartialTick) / FALL_ASLEEP_TIME) * 255);
        pGuiGraphics.fill(0, 0, width, height, (alpha << 24));
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void tick() {
        counter++;
        if (counter > FALL_ASLEEP_TIME) {
            Messages.sendToServer(new GenericToServerPacket(GenericToServerPacket.MessageType.SLEEP_CHAMBER));
            sendWakeUp();
        }
    }

    private void sendWakeUp() {
        minecraft.setScreen(null);

    }
}
