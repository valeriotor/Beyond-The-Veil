package com.valeriotor.beyondtheveil.client;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.model.entity.DeepOneModel;
import com.valeriotor.beyondtheveil.client.render.entity.DeepOneRenderer;
import com.valeriotor.beyondtheveil.client.gui.GearBenchGui;
import com.valeriotor.beyondtheveil.client.research.ResearchRegistryClient;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
            ResearchRegistryClient.registerConnectionsAndRecipes();
        });

    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DeepOneModel.LAYER_LOCATION, DeepOneModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.DEEP_ONE.get(), DeepOneRenderer::new);
    }

}
