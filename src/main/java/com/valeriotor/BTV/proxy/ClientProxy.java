package com.valeriotor.BTV.proxy;

import com.valeriotor.BTV.entities.render.RegisterRenders;
import com.valeriotor.BTV.gui.GuiSleepingChamber;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.items.ItemRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {
	
	
	
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        this.registerRenders();
        Guis.registerGuis();
        }

    //@SubscribeEvent
    //public static void registerModels(ModelRegistryEvent event) {
    //}
    
    @Override
    public void init(FMLInitializationEvent e) {
    	  
    }
    
    
    public static void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(),id));
    }
	
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event){
    	ItemRegistry.initModels();
    }
    
    
    public static void registerEntities() {
    	
    }
    
    @Override
    public void openGui(String id) {
    	Minecraft.getMinecraft().displayGuiScreen(Guis.getGui(id));
    }
    
    
    private void registerRenders() {
    	RegisterRenders.register();
    }
    
    
	
	
}
