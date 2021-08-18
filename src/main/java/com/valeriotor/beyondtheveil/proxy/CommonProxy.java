package com.valeriotor.beyondtheveil.proxy;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.entities.BTVEntityRegistry;
import com.valeriotor.beyondtheveil.entities.bosses.EntityArenaBoss;
import com.valeriotor.beyondtheveil.events.ClientEvents;
import com.valeriotor.beyondtheveil.events.RenderEvents;
import com.valeriotor.beyondtheveil.events.ResearchEvents;
import com.valeriotor.beyondtheveil.gui.toasts.IctyaryToast;
import com.valeriotor.beyondtheveil.gui.toasts.MemoryToast;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.util.ConfigLib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {
	
	public ClientEvents cEvents;
	public RenderEvents renderEvents;
    public ResearchEvents researchEvents;
    public static Configuration cfg;
	
	public void preInit(FMLPreInitializationEvent e) {
    	MinecraftForge.EVENT_BUS.register(researchEvents = new ResearchEvents());
    	cfg = new Configuration(e.getSuggestedConfigurationFile());
    	syncConfig();
    }

    public void init(FMLInitializationEvent e) {
	    loadCustomResources();
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

  //@SubscribeEvent
   // public static void onModelRegister(ModelRegistryEvent event){
    //	((IHasModel)BlockRegistry.DampWood).registerModels();
    //}
    
    
    public static void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(),id));
    }
    
    public static void registerEntities() {
    	BTVEntityRegistry.register();
    }
    
    public void openGui(String id, Object... args) {}
    
    public void closeGui(EntityPlayer p) {}

    public void playArenaSound(EntityArenaBoss boss) {}
    
    public String localizeMessage(String a) {
    	return a;
    }
    
    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
    	int number = BTVSounds.getNumberOfSounds();
    	for(int i = 0; i < number; i++)
    		event.getRegistry().register(BTVSounds.getSoundById(i));
    }
    
    public void loadCustomResources() {
        AnimationRegistry.loadAnimations(false);
    }
    
    public EntityPlayer getPlayer() {return null;}
    
    public void syncConfig() {
    	cfg.setCategoryComment("colors", "Some colors in the mod might be a bit too dark for certain computers. I might add more stuff here in the future (let me know if there's anything in particular you'd like!!)");
    	ConfigLib.connectionBlue = cfg.get("colors", "Necronomicon Connection Blue", 0, "Blue Color for the connections in Al Azif (default: 0)", 0, 255).getInt();
    	ConfigLib.connectionGreen = cfg.get("colors", "Necronomicon Connection Green", 32, "Green Color for the connections in Al Azif (default: 32)", 0, 255).getInt();
    	ConfigLib.connectionRed = cfg.get("colors", "Necronomicon Connection Red", 0, "Red Color for the connections in Al Azif (default: 0)", 0, 255).getInt();
    	cfg.setCategoryComment("world", "Some worldgen stuff. Will add more options in the future");
    	ConfigLib.innsmouthWeight = cfg.get("world", "Voided biome spawn weight", 4, "Min: 1, Max: 99, Default: 4", 1, 99).getInt();
    	cfg.setCategoryComment("gameplay", "Gameplay related stuff");
    	ConfigLib.crucibleDamage = cfg.get("gameplay", "The damage dealt by the Crucible", 100, "Min: 1, Max: 1000, Default: 100", 1, 1000).getInt();
    	ConfigLib.crucibleCooldown = cfg.get("gameplay", "The cooldown, in seconds, for the Crucible to be reused", 120, "Min: 5, Max: 10000, Default: 120", 1, 10000).getInt();
    	cfg.save();
    }

    public void createMemoryToast(Memory memory) {
    }


    public void createIctyaryToast(String ictya) {
    }

}
