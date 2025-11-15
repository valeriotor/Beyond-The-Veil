package com.valeriotor.beyondtheveil.client.event;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.SurgeryBedBlock;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.client.gui.SurgeryBedGui;
import com.valeriotor.beyondtheveil.client.reminiscence.ReminiscenceClient;
import com.valeriotor.beyondtheveil.client.sounds.NautilusPropellerSoundInstance;
import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tile.SurgeryBedBE;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.InBedChatScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    private static NautilusPropellerSoundInstance propellerSound;

    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent event) {
        ReminiscenceClient.tickEvent(event);
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.getVehicle() instanceof NautilusEntity nautilus && event.phase == TickEvent.Phase.END) {
                nautilus.setInput(player.input.left, player.input.right, player.input.up, player.input.down, player.input.jumping, player.input.shiftKeyDown, Minecraft.getInstance().options.keySprint.isDown());
                if (player.input.left || player.input.right || player.input.up || player.input.down || player.input.jumping || Minecraft.getInstance().options.keySprint.isDown())
                    if (propellerSound == null || !Minecraft.getInstance().getSoundManager().isActive(propellerSound) || propellerSound.getCounter() >= 310) {
                        propellerSound = new NautilusPropellerSoundInstance(player);
                        Minecraft.getInstance().getSoundManager().play(propellerSound);
                    }
            }
            if (player.level().dimension() == BTVDimensions.ARCHE_LEVEL && !Minecraft.getInstance().isPaused()) {
                ClientData.getInstance().archeSavedData.tick(false);
            }
            if (Minecraft.getInstance().screen instanceof SurgeryBedGui) {
                player.attackAnim = 0;
            }
            if (Minecraft.getInstance().screen instanceof SurgeryBedGui && !player.isSleeping()) {
                Minecraft.getInstance().popGuiLayer();
            } else if (Minecraft.getInstance().screen instanceof InBedChatScreen) {
                if (player.getSleepingPos().isPresent()) {
                    BlockPos pos = player.getSleepingPos().get();
                    BlockState state = player.level().getBlockState(pos);
                    if (state.getBlock() instanceof SurgeryBedBlock b && player.level().getBlockEntity(b.findCenter(pos, state)) instanceof SurgeryBedBE be) {
                        Minecraft.getInstance().setScreen(new SurgeryBedGui());
                    }
                }
            }
        }
        ClientMethods.tick(event);
        ClientData.getInstance().tick();
    }

    @SubscribeEvent
    public static void keyInputEvent(InputEvent.Key event) {
        ReminiscenceClient.keyInputEvent(event);
    }


    @SubscribeEvent
    public static void mouseScrollEvent(InputEvent.MouseScrollingEvent event) {
        ReminiscenceClient.mouseScrollEvent(event);
    }

    @SubscribeEvent
    public static void screenOpenEvent(ScreenEvent.Opening event) {
        ReminiscenceClient.screenOpenEvent(event);
    }

}
