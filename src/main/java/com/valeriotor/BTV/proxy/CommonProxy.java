package com.valeriotor.BTV.proxy;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.blocks.dampWood;
import com.valeriotor.BTV.capabilities.CapabilityHandler;
import com.valeriotor.BTV.capabilities.IWorship;
import com.valeriotor.BTV.entities.RegisterEntities;
import com.valeriotor.BTV.entities.render.RegisterRenders;
import com.valeriotor.BTV.items.TestItem;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


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
    
    
    

}
