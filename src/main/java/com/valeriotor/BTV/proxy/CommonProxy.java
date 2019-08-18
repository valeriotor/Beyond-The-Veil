package com.valeriotor.BTV.proxy;

import com.valeriotor.BTV.entities.BTVEntityRegistry;
import com.valeriotor.BTV.events.ClientEvents;
import com.valeriotor.BTV.events.RenderEvents;
import com.valeriotor.BTV.events.ResearchEvents;
import com.valeriotor.BTV.lib.BTVSounds;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
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
	
	public void preInit(FMLPreInitializationEvent e) {
    	MinecraftForge.EVENT_BUS.register(researchEvents = new ResearchEvents());
    }

    public void init(FMLInitializationEvent e) {
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
    
    public void openGui(String id, Object... args) {
    	
    }
    
    public void closeGui(EntityPlayer p) {
    	
    }
    
    public String localizeMessage(String a) {
    	return a;
    }
    
    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
    	int number = BTVSounds.getNumberOfSounds();
    	for(int i = 0; i < number; i++)
    		event.getRegistry().register(BTVSounds.getSoundById(i));
    }
    
    public void loadCustomResources() {}
    
    
    

}
