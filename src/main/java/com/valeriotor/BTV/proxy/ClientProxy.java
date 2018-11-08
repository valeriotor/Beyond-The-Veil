package com.valeriotor.BTV.proxy;

import java.awt.Color;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.entities.EntityDeepOne;
import com.valeriotor.BTV.entities.RegisterEntities;
import com.valeriotor.BTV.entities.models.ModelDeepOne;
import com.valeriotor.BTV.entities.render.RegisterRenders;
import com.valeriotor.BTV.entities.render.RenderDeepOne;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.items.TestItem;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.IHasModel;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {
	
	
	
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        this.registerRenders();
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
    
    
    private void registerRenders() {
    	RegisterRenders.register();
    }
    
    
	
	
}
