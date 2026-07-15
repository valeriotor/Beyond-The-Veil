package com.valeriotor.beyondtheveil.client;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.crossync.PlayerTransformation;
import com.valeriotor.beyondtheveil.client.gui.DreamBottleGui;
import com.valeriotor.beyondtheveil.client.gui.DrownedGui;
import com.valeriotor.beyondtheveil.client.gui.GearBenchGui;
import com.valeriotor.beyondtheveil.client.gui.LetterBoxGui;
import com.valeriotor.beyondtheveil.client.gui.dialogue.DrownedDialogueGui;
import com.valeriotor.beyondtheveil.client.gui.dialogue.MirrorDialogueGui;
import com.valeriotor.beyondtheveil.client.gui.dialogue.EntityDialogueGui;
import com.valeriotor.beyondtheveil.client.gui.dialogue.ShoremanCultistDialogueGui;
import com.valeriotor.beyondtheveil.client.gui.pool.BloodGemGui;
import com.valeriotor.beyondtheveil.client.model.baked.AlembicsModelLoader;
import com.valeriotor.beyondtheveil.client.model.baked.FlaskShelfModelLoader;
import com.valeriotor.beyondtheveil.client.model.baked.ForcepsModelLoader;
import com.valeriotor.beyondtheveil.client.model.baked.ItemFlaskModelLoader;
import com.valeriotor.beyondtheveil.client.model.entity.*;
import com.valeriotor.beyondtheveil.client.model.entity.layer.*;
import com.valeriotor.beyondtheveil.client.particle.BloodspillParticle;
import com.valeriotor.beyondtheveil.client.render.blockentity.*;
import com.valeriotor.beyondtheveil.client.render.entity.*;
import com.valeriotor.beyondtheveil.client.research.ResearchRegistryClient;
import com.valeriotor.beyondtheveil.item.MemoryPhialItem;
import com.valeriotor.beyondtheveil.item.SampleTubeItem;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.world.dimension.ArcheCycleData;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

import static com.valeriotor.beyondtheveil.Registration.ARCHE_DIAL;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(Registration.GEAR_BENCH_CONTAINER.get(), GearBenchGui::new);
            MenuScreens.register(Registration.LETTER_BOX_CONTAINER.get(), LetterBoxGui::new);
            MenuScreens.register(Registration.DREAM_BOTTLE_CONTAINER.get(), DreamBottleGui::new);
            MenuScreens.register(Registration.BLOOD_GEM_CONTAINER.get(), BloodGemGui::new);
            MenuScreens.register(Registration.DROWNED_CONTAINER.get(), DrownedGui::new);
            MenuScreens.register(Registration.SHOREMAN_DIALOGUE_MENU.get(), EntityDialogueGui::new);
            MenuScreens.register(Registration.DOUBLE_DIALOGUE_MENU.get(), ShoremanCultistDialogueGui::new);
            MenuScreens.register(Registration.MIRROR_DIALOGUE_MENU.get(), MirrorDialogueGui::new);
            MenuScreens.register(Registration.DROWNED_DIALOGUE_MENU.get(), DrownedDialogueGui::new);
            ItemBlockRenderTypes.setRenderLayer(Registration.DAMP_CANOPY.get(), RenderType.cutout());
            //ItemBlockRenderTypes.setRenderLayer(Registration.DEEP_CHEST.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.DAMP_FILLED_CANOPY.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.FISH_BARREL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.IDOL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.SLUG_BAIT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.LAMP.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.FUME_SPREADER.get(), type -> type != null && (type.equals(RenderType.solid()) || type.equals(RenderType.translucent())));
            ItemBlockRenderTypes.setRenderLayer(Registration.SLEEP_CHAMBER.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.GEAR_BENCH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.HEART.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.BLOOD_WELL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.WATERY_CRADLE.get(), type -> type != null && (type.equals(RenderType.solid()) || type.equals(RenderType.translucent())));
            ItemBlockRenderTypes.setRenderLayer(Registration.PATIENT_POD.get(), type -> type != null && (type.equals(RenderType.solid()) || type.equals(RenderType.translucent())));
            ItemBlockRenderTypes.setRenderLayer(Registration.MEMORY_SIEVE.get(), type -> type != null && (type.equals(RenderType.solid()) || type.equals(RenderType.translucent())));
            ItemBlockRenderTypes.setRenderLayer(Registration.ALEMBICS.get(), type -> type != null && (type.equals(RenderType.solid()) || type.equals(RenderType.translucent())));
            ItemBlockRenderTypes.setRenderLayer(Registration.FLASK_LARGE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.FLASK_MEDIUM.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.FLASK_SMALL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.FLASK_ITEM.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.FLASK_SHELF.get(), type -> type != null && (type.equals(RenderType.solid()) || type.equals(RenderType.translucent())));
            ItemBlockRenderTypes.setRenderLayer(Registration.BLACK_KELP.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.BLACK_KELP_PLANT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.BLACK_SEAGRASS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.BLACK_TALL_SEAGRASS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.DARK_GLASS.get(), RenderType.translucent());
            ResearchRegistryClient.registerConnectionsAndRecipes();
            MiscModels.createInstance();
            BlockColors blockColors = Minecraft.getInstance().getBlockColors();
            blockColors.register((pState, pLevel, pPos, pTintIndex) -> 0x287082, Registration.MEMORY_SIEVE.get());
            ItemColors itemColors = Minecraft.getInstance().getItemColors();
            itemColors.register(new MemoryPhialItem.MemoryPhialColor(), Registration.MEMORY_PHIAL.get());
            itemColors.register(new SampleTubeItem.SampleTubeColor(), Registration.SAMPLE_TUBE.get());

            ItemProperties.register(ARCHE_DIAL.get(), new ResourceLocation("time"), new ClampedItemPropertyFunction() {
                @Override
                public float unclampedCall(ItemStack pStack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
                    if (pLevel != null && pLevel.dimension() == BTVDimensions.ARCHE_LEVEL) {
                        ArcheCycleData data = ClientData.getInstance().archeCycleData;
                        if (data.getCurrentIntensity() > 0) {
                            return 0.9F;
                        }
                        float moduloTicks = data.getModuloTicks();
                        float l = moduloTicks / ((ArcheCycleData.CURRENT_START));
                        return l * 9 / 10;
                    }
                    return 0;
                }
            });
        });

    }


    @SubscribeEvent
    public static void onRegisterKeybindings(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.reminisce);
        event.register(KeyBindings.transform);
    }


    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DeepOneModel.LAYER_LOCATION, DeepOneModel::createBodyLayer);
        event.registerLayerDefinition(BloodSkeletonCrawlingModel.LAYER_LOCATION, BloodSkeletonCrawlingModel::createBodyLayer);
        event.registerLayerDefinition(BloodZombieModel.LAYER_LOCATION, BloodZombieModel::createBodyLayer);
        event.registerLayerDefinition(BloodWraithModel.LAYER_LOCATION, BloodWraithModel::createBodyLayer);
        event.registerLayerDefinition(TestNautilus.LAYER_LOCATION, TestNautilus::createBodyLayer);
        event.registerLayerDefinition(CanoeModel.LAYER_LOCATION, CanoeModel::createBodyLayer);
        event.registerLayerDefinition(CrawlerModel.LAYER_LOCATION, CrawlerModel::createBodyLayer);
        event.registerLayerDefinition(WeeperModel.LAYER_LOCATION, WeeperModel::createBodyLayer);
        event.registerLayerDefinition(FletumModel.LAYER_LOCATION, FletumModel::createBodyLayer);
        event.registerLayerDefinition(Abomination0Model.LAYER_LOCATION, Abomination0Model::createBodyLayer);
        event.registerLayerDefinition(Abomination1Model.LAYER_LOCATION, Abomination1Model::createBodyLayer);
        event.registerLayerDefinition(Abomination2Model.LAYER_LOCATION, Abomination2Model::createBodyLayer);
        event.registerLayerDefinition(BloodCultistModel.LAYER_LOCATION, BloodCultistModel::createBodyLayer);
        event.registerLayerDefinition(ShoremanModel.LAYER_LOCATION, ShoremanModel::createBodyLayer);
        event.registerLayerDefinition(CephalopodianModel.LAYER_LOCATION, CephalopodianModel::createBodyLayer);
        event.registerLayerDefinition(AnglerModel.LAYER_LOCATION, AnglerModel::createBodyLayer);
        event.registerLayerDefinition(SeaSnakeModel.LAYER_LOCATION, SeaSnakeModel::createBodyLayer);
        event.registerLayerDefinition(SepiidModel.LAYER_LOCATION, SepiidModel::createBodyLayer);
        event.registerLayerDefinition(AdelineModel.LAYER_LOCATION, AdelineModel::createBodyLayer);
        event.registerLayerDefinition(BonecageModel.LAYER_LOCATION, BonecageModel::createBodyLayer);
        event.registerLayerDefinition(ManOWarModel.LAYER_LOCATION, ManOWarModel::createBodyLayer);
        event.registerLayerDefinition(OctidModel.LAYER_LOCATION, OctidModel::createBodyLayer);
        event.registerLayerDefinition(UmancalaModel.LAYER_LOCATION, UmancalaModel::createBodyLayer);
        event.registerLayerDefinition(SandflatterModel.LAYER_LOCATION, SandflatterModel::createBodyLayer);
        event.registerLayerDefinition(JellyModel.LAYER_LOCATION, JellyModel::createBodyLayer);
        event.registerLayerDefinition(LivingPortalModel.LAYER_LOCATION, LivingPortalModel::createBodyLayer);
        event.registerLayerDefinition(SurgeonModel.LAYER_LOCATION, SurgeonModel::createBodyLayer);
        event.registerLayerDefinition(SurgeonLarvaModel.LAYER_LOCATION, SurgeonLarvaModel::createBodyLayer);



        // RenderLayer models
        event.registerLayerDefinition(WoundModel.LAYER_LOCATION, WoundModel::createBodyLayer);
        event.registerLayerDefinition(ChestWoundModel.LAYER_LOCATION, ChestWoundModel::createBodyLayer);
        event.registerLayerDefinition(BrokenBodyModel.LAYER_LOCATION, BrokenBodyModel::createBodyLayer);
        event.registerLayerDefinition(AbominationFlesh1Model.LAYER_LOCATION, AbominationFlesh1Model::createBodyLayer);
        event.registerLayerDefinition(NautilusCreakModel.LAYER_LOCATION, NautilusCreakModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BTVEntities.DEEP_ONE.get(), DeepOneRenderer::new);
        event.registerEntityRenderer(BTVEntities.BLOOD_SKELETON.get(), BloodSkeletonRenderer::new);
        event.registerEntityRenderer(BTVEntities.BLOOD_ZOMBIE.get(), BloodZombieRenderer::new);
        event.registerEntityRenderer(BTVEntities.BLOOD_WRAITH.get(), BloodWraithRenderer::new);
        event.registerEntityRenderer(BTVEntities.NAUTILUS.get(), NautilusRenderer::new);
        event.registerEntityRenderer(BTVEntities.CANOE.get(), CanoeRenderer::new);
        event.registerEntityRenderer(BTVEntities.CRAWLER.get(), CrawlerRenderer::new);
        event.registerEntityRenderer(BTVEntities.WEEPER.get(), WeeperRenderer::new);
        event.registerEntityRenderer(BTVEntities.FLETUM.get(), FletumRenderer::new);
        event.registerEntityRenderer(BTVEntities.ABOMINATION_0.get(), Abomination0Renderer::new);
        event.registerEntityRenderer(BTVEntities.ABOMINATION_1.get(), Abomination1Renderer::new);
        event.registerEntityRenderer(BTVEntities.ABOMINATION_2.get(), Abomination2Renderer::new);
        event.registerEntityRenderer(BTVEntities.BLOOD_CULTIST.get(), BloodCultistRenderer::new);
        event.registerEntityRenderer(BTVEntities.SHOREMAN.get(), ShoremanRenderer::new);
        event.registerEntityRenderer(BTVEntities.CEPHALOPODIAN.get(), CephalopodianRenderer::new);
        event.registerEntityRenderer(BTVEntities.ANGLER.get(), AnglerRenderer::new);
        event.registerEntityRenderer(BTVEntities.SEA_SNAKE.get(), SeaSnakeRenderer::new);
        event.registerEntityRenderer(BTVEntities.SEPIID.get(), SepiidRenderer::new);
        event.registerEntityRenderer(BTVEntities.ADELINE.get(), AdelineRenderer::new);
        event.registerEntityRenderer(BTVEntities.BONECAGE.get(), BonecageRenderer::new);
        event.registerEntityRenderer(BTVEntities.MAN_O_WAR.get(), ManOWarRenderer::new);
        event.registerEntityRenderer(BTVEntities.OCTID.get(), OctidRenderer::new);
        event.registerEntityRenderer(BTVEntities.UMANCALA.get(), UmancalaRenderer::new);
        event.registerEntityRenderer(BTVEntities.SANDFLATTER.get(), SandflatterRenderer::new);
        event.registerEntityRenderer(BTVEntities.JELLY.get(), JellyRenderer::new);
        event.registerEntityRenderer(BTVEntities.LIVING_PORTAL.get(), LivingPortalRenderer::new);
        event.registerEntityRenderer(BTVEntities.UMANCALA_FIREBALL.get(), UmancalaFireballRenderer::new);
        event.registerEntityRenderer(BTVEntities.SURGEON.get(), SurgeonRenderer::new);
        event.registerEntityRenderer(BTVEntities.SURGEON_LARVA.get(), SurgeonLarvaRenderer::new);
        event.registerEntityRenderer(BTVEntities.DREAM_FOCUS_ITEM.get(), ItemEntityRenderer::new);
        event.registerEntityRenderer(BTVEntities.DREAM_FOCUS_FLUID.get(), DreamFocusFluidRenderer::new);

        event.registerBlockEntityRenderer(BTVBlockEntities.HEART_BE.get(), HeartBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.MEMORY_SIEVE_BE.get(), MemorySieveBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.WATERY_CRADLE_BE.get(), WateryCradleBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.FLASK_SHELF_BE.get(), FlaskShelfBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.SURGERY_BED_BE.get(), SurgeryBedBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.FLASK_BE.get(), FlaskBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.SACRIFICE_ALTAR_BE.get(), SacrificeAltarBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.BLOOD_BASIN_BE.get(), BloodBasinBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.ALEMBICS_BE.get(), AlembicsBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.LACRYMATORY_BE.get(), LacrymatoryBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.PATIENT_POD_BE.get(), PatientPodBER::new);
        event.registerBlockEntityRenderer(BTVBlockEntities.BLOOD_WELL_BE.get(), BloodWellBER::new);
    }

    public static Map<PlayerTransformation, LivingEntityRenderer<LivingEntity, ?>> moreRenderers = new EnumMap<>(PlayerTransformation.class);

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.AddLayers event) {
        // Hijacking this to add additional renderers
        // TODO might not actually be necessary
        moreRenderers.clear();
        moreRenderers.put(PlayerTransformation.ABOMINATION_0, event.getRenderer(BTVEntities.ABOMINATION_0.get()));
        moreRenderers.put(PlayerTransformation.ABOMINATION_1, event.getRenderer(BTVEntities.ABOMINATION_1.get()));
        moreRenderers.put(PlayerTransformation.ABOMINATION_2, event.getRenderer(BTVEntities.ABOMINATION_2.get()));
        moreRenderers.put(PlayerTransformation.DEEP_ONE, event.getRenderer(BTVEntities.DEEP_ONE.get()));
    }

    @SubscribeEvent
    public static void onEntityRenderersEvent(EntityRenderersEvent.AddLayers event) {
        AnimationRegistry.loadAnimations(true);
    }

    @SubscribeEvent
    public static void onRegisterParticleProvidersEvent (RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(BTVParticles.BLOODSPILL.get(), BloodspillParticle.BloodspillParticleProvider::new);
        event.registerSpriteSet(BTVParticles.TEARSPILL.get(), BloodspillParticle.BloodspillParticleProvider::new);
    }

    public static boolean isConnectionPresent() {
        return Minecraft.getInstance().getConnection() != null && Minecraft.getInstance().getConnection().getConnection() != null;
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelEvent.RegisterGeometryLoaders event) {
        event.register(FlaskShelfModelLoader.FLASK_SHELF_LOADER.getPath(), new FlaskShelfModelLoader());
        event.register(AlembicsModelLoader.ALEMBICS_LOADER.getPath(), new AlembicsModelLoader());
        event.register(ForcepsModelLoader.FORCEPS_LOADER.getPath(), new ForcepsModelLoader());
        event.register(ItemFlaskModelLoader.ITEM_FLASK_LOADER.getPath(), new ItemFlaskModelLoader());
        //ModelLoaderRegistry.registerLoader(FlaskModelLoader.FLASK_LOADER, new FlaskModelLoader());
        //ModelLoaderRegistry.registerLoader(FlaskShelfModelLoader.FLASK_SHELF_LOADER, new FlaskShelfModelLoader());
    }

    //@SubscribeEvent
    //public static void onModelBakeEvent(ModelBakeEvent event) {
    //    OrphanLoaderRegistry.cacheBlockModel("flask_large");
    //    OrphanLoaderRegistry.bakeModels(event);
    //}



}
