package com.valeriotor.beyondtheveil;

import com.valeriotor.beyondtheveil.gui.GearBenchGui;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(Registration.GEAR_BENCH_CONTAINER.get(), GearBenchGui::new);
            ItemBlockRenderTypes.setRenderLayer(Registration.DAMP_CANOPY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.DAMP_FILLED_CANOPY.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.FISH_BARREL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.IDOL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.SLUG_BAIT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.LAMP.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.FUME_SPREADER.get(), type -> type != null && (type.equals(RenderType.solid()) || type.equals(RenderType.translucent())));
            ItemBlockRenderTypes.setRenderLayer(Registration.SLEEP_CHAMBER.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.GEAR_BENCH.get(), RenderType.cutout());
        });

    }

}
