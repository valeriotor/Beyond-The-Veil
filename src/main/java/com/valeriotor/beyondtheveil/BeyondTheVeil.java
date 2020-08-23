package com.valeriotor.beyondtheveil;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.valeriotor.beyondtheveil.blackmirror.MirrorDialogueRegistry;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.IResearch;
import com.valeriotor.beyondtheveil.capabilities.MirrorCapInstance;
import com.valeriotor.beyondtheveil.capabilities.MirrorHandler;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataHandler;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataHandler.PlayerData;
import com.valeriotor.beyondtheveil.capabilities.ResearchHandler;
import com.valeriotor.beyondtheveil.capabilities.ResearchHandler.ResearchData;
import com.valeriotor.beyondtheveil.crafting.GearBenchRecipeRegistry;
import com.valeriotor.beyondtheveil.events.MemoryUnlocks;
import com.valeriotor.beyondtheveil.fluids.ModFluids;
import com.valeriotor.beyondtheveil.gui.container.GuiContainerHandler;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.lib.commands.ChangeDimension;
import com.valeriotor.beyondtheveil.lib.commands.ReloadResources;
import com.valeriotor.beyondtheveil.lib.commands.SetPlayerData;
import com.valeriotor.beyondtheveil.multiblock.MultiblockRegistry;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.proxy.CommonProxy;
import com.valeriotor.beyondtheveil.research.ResearchRegistry;
import com.valeriotor.beyondtheveil.sacrifice.SacrificeRecipeRegistry;
import com.valeriotor.beyondtheveil.shoggoth.BuildingRegistry;
import com.valeriotor.beyondtheveil.util.RegistryHelper;
import com.valeriotor.beyondtheveil.world.DimensionRegistry;
import com.valeriotor.beyondtheveil.world.StatueChunkLoader;
import com.valeriotor.beyondtheveil.world.WorldGenBTV;
import com.valeriotor.beyondtheveil.world.Structures.HamletStructuresRegistry;
import com.valeriotor.beyondtheveil.world.biomes.BiomeRegistry;
import com.valeriotor.beyondtheveil.worship.DGWorshipHelper;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION, dependencies = References.DEPENDENCIES)
public class BeyondTheVeil
{
	@SidedProxy(clientSide = "com.valeriotor.beyondtheveil.proxy.ClientProxy", serverSide = "com.valeriotor.beyondtheveil.proxy.ServerProxy")
    public static CommonProxy proxy;
	
	@Mod.Instance
	public static BeyondTheVeil instance;
    

    private static Logger logger;
    public static Gson gson = new Gson();
    static {
    	FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	ModFluids.registerFluids();
        logger = event.getModLog();
        proxy.preInit(event);
        RegistryHelper.registerEntities();
        BTVPacketHandler.registerPackets();
        MirrorDialogueRegistry.registerMirrorDialogues();
        //ClientProxy.registerEntity();
        

    	CapabilityManager.INSTANCE.register(IPlayerData.class, new PlayerDataHandler.DataStorage(), PlayerData::new);
    	CapabilityManager.INSTANCE.register(IResearch.class, new ResearchHandler.ResearchStorage(), ResearchData::new);
    	CapabilityManager.INSTANCE.register(MirrorCapInstance.class, new MirrorHandler.MirrorStorage(), MirrorCapInstance::new);

    	MinecraftForge.EVENT_BUS.register(MemoryUnlocks.class);
    	MinecraftForge.TERRAIN_GEN_BUS.register(MemoryUnlocks.class);
        
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {	
    	
    	//CapabilityHandler.registerCapabilities();
    	BiomeRegistry.initBiomes();
    	proxy.init(event);
    	proxy.registerEntities();
    	DimensionRegistry.registerDimensions();
    	
    	
    	RegistryHelper.registerTileEntities();
    	
    	GameRegistry.registerWorldGenerator(new WorldGenBTV(), 10000);
    	HamletStructuresRegistry.registerStructures();
    	BuildingRegistry.registerBuildings();
    	NetworkRegistry.INSTANCE.registerGuiHandler(BeyondTheVeil.instance, new GuiContainerHandler());
		DGWorshipHelper.loadDreamerResearch();
		ResearchRegistry.registerResearchesFirst();
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ResearchRegistry.registerResearchesSecond();
        GearBenchRecipeRegistry.registerGearBenchRecipes();
        SacrificeRecipeRegistry.registerSacrificeRecipes();
        MultiblockRegistry.registerMultiblocks();
        ForgeChunkManager.setForcedChunkLoadingCallback(instance, new StatueChunkLoader());
        proxy.postInit(event);
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new ReloadResources());
        event.registerServerCommand(new SetPlayerData());
        event.registerServerCommand(new ChangeDimension());
    }
}
