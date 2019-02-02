package com.valeriotor.BTV.proxy;

import com.valeriotor.BTV.entities.RegisterEntities;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
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
    	RegisterEntities.register();
    }
    
    public void openGui(String id) {
    	
    }
    
    public void closeGui(EntityPlayer p) {
    	
    }
    
    public String localizeMessage(String a) {
    	return a;
    }
    
    
    

}
