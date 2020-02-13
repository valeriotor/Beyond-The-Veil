package com.valeriotor.BTV;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.IResearch;
import com.valeriotor.BTV.capabilities.PlayerDataHandler;
import com.valeriotor.BTV.capabilities.ResearchHandler;
import com.valeriotor.BTV.crafting.GearBenchRecipeRegistry;
import com.valeriotor.BTV.events.MemoryUnlocks;
import com.valeriotor.BTV.fluids.ModFluids;
import com.valeriotor.BTV.gui.container.GuiContainerHandler;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.lib.commands.ReloadResources;
import com.valeriotor.BTV.lib.commands.SetPlayerData;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.proxy.CommonProxy;
import com.valeriotor.BTV.research.ResearchRegistry;
import com.valeriotor.BTV.sacrifice.SacrificeRecipeRegistry;
import com.valeriotor.BTV.shoggoth.BuildingRegistry;
import com.valeriotor.BTV.util.RegistryHelper;
import com.valeriotor.BTV.world.BiomeRegistry;
import com.valeriotor.BTV.world.WorldGenBTV;
import com.valeriotor.BTV.world.Structures.HamletStructuresRegistry;
import com.valeriotor.BTV.worship.DGWorshipHelper;

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
	@SidedProxy(clientSide = "com.valeriotor.BTV.proxy.ClientProxy", serverSide = "com.valeriotor.BTV.proxy.ServerProxy")
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
        //ClientProxy.registerEntity();
        

    	CapabilityManager.INSTANCE.register(IPlayerData.class, new PlayerDataHandler.DataStorage(), new PlayerDataHandler.Factory());
    	CapabilityManager.INSTANCE.register(IResearch.class, new ResearchHandler.ResearchStorage(), new ResearchHandler.ResearchCapFactory());

    	MinecraftForge.EVENT_BUS.register(MemoryUnlocks.class);
    	MinecraftForge.TERRAIN_GEN_BUS.register(MemoryUnlocks.class);
        
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {	
    	
    	//CapabilityHandler.registerCapabilities();
    	new BiomeRegistry();
    	proxy.init(event);
    	proxy.registerEntities();
    	
    	
    	
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
        proxy.postInit(event);
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new ReloadResources());
        event.registerServerCommand(new SetPlayerData());
    }
}
