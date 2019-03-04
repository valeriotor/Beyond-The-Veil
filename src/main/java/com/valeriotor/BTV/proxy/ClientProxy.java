package com.valeriotor.BTV.proxy;

import com.valeriotor.BTV.entities.render.RegisterRenders;
import com.valeriotor.BTV.events.ClientEvents;
import com.valeriotor.BTV.gui.DialogueRequirement;
import com.valeriotor.BTV.gui.GuiSleepingChamber;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.KeyHandler;
import com.valeriotor.BTV.util.RegistryHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {
	
	
	public static KeyHandler handler;
	
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        this.registerRenders();
        Guis.registerGuis();
        this.cEvents = new ClientEvents();
        MinecraftForge.EVENT_BUS.register(this.cEvents);
        handler = new KeyHandler();
        DialogueRequirement.registerRequirements();
        }

    //@SubscribeEvent
    //public static void registerModels(ModelRegistryEvent event) {
    //}
    
    @Override
    public void init(FMLInitializationEvent e) {
    	  RegistryHandler.registerColorHandlers();
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
    
    @Override
    public void closeGui(EntityPlayer p) {
    	if(p.equals(Minecraft.getMinecraft().player))
    	Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
    }
    
    
    private void registerRenders() {
    	RegisterRenders.register();
    }
    
    
    
	
	
}
