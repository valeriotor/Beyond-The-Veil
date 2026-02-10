package com.valeriotor.beyondtheveil.client.gui;

import com.valeriotor.beyondtheveil.block.SurgeryBedBlock;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import com.valeriotor.beyondtheveil.tile.SurgeryBedBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SurgeryBedGui extends Screen {

    public static void updatePatientStatus(PatientStatus status) {
        if (Minecraft.getInstance().screen instanceof SurgeryBedGui gui) {
            gui.patientStatus = status;
            gui.leaveBedButton.setMessage(gui.leaveButtonText());
        }
    }

    private Button leaveBedButton;
    private PatientStatus patientStatus;

    public SurgeryBedGui() {
        super(Component.translatable("gui.surgery_bed.title"));
    }

    protected void init() {

        LocalPlayer p = Minecraft.getInstance().player;
        if (p != null) {
            BlockPos pos = p.getOnPos();
            BlockState state = p.level().getBlockState(pos);
            if (state.getBlock() instanceof SurgeryBedBlock b) {
                BlockPos center = b.findCenter(pos, state);
                if (p.level().getBlockEntity(center) instanceof SurgeryBedBE be) {
                    patientStatus = be.getPatientStatus();
                }
            }

        }
        this.leaveBedButton = Button.builder(leaveButtonText(), (p_96074_) -> {
            this.onClose();
        }).bounds(this.width / 2 - 100, this.height - 40, 200, 20).build();
        this.addRenderableWidget(this.leaveBedButton);

    }

    @NotNull
    private Component leaveButtonText() {
        if (patientStatus != null && (patientStatus.isIncised() || patientStatus.getCondition().isTerminal())) {
            return Component.translatable("gui.surgery_bed.leave_die");
        }
        return Component.translatable("gui.surgery_bed.leave");
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (patientStatus != null) {
            if (patientStatus.getExposedLocation() == SurgicalLocation.BACK) {
                pGuiGraphics.fill(0, 0, width, height, 0xFF1D0400);
            }
            double currentPain = patientStatus.getCurrentPain();
            if (currentPain > 0) {
                int color = (Math.min(100, (int) currentPain) << 24) | 0xFF0000;
                pGuiGraphics.fill(0, 0, width, height, color);
            } else {
                double sedativeAmount = patientStatus.getSedativeAmount();
                if (sedativeAmount > 0) {

                    float opacity = (float) sedativeAmount / 100.0F;
                    if (opacity > 1.0F) {
                        opacity = 1.0F;
                    }
                    int color = (int) (220.0F * opacity) << 24 | 1052704;
                    pGuiGraphics.fill(RenderType.guiOverlay(), 0, 0, width, height, color);
                }
            }
        }
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public void onClose() {
        this.sendWakeUp();
        Minecraft.getInstance().popGuiLayer();
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == 256) {
            this.onClose();
            return true;
        }
        return false;
    }

    private void sendWakeUp() {
        ClientPacketListener clientpacketlistener = this.minecraft.player.connection;
        clientpacketlistener.send(new ServerboundPlayerCommandPacket(this.minecraft.player, ServerboundPlayerCommandPacket.Action.STOP_SLEEPING));
    }

    //public void onPlayerWokeUp() {
    //    if (this.input.getValue().isEmpty()) {
    //        this.minecraft.setScreen((Screen)null);
    //    } else {
    //        this.minecraft.setScreen(new ChatScreen(this.input.getValue()));
    //    }
//
    //}


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
