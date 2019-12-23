package com.valeriotor.BTV.proxy;

import com.valeriotor.BTV.animations.AnimationRegistry;
import com.valeriotor.BTV.entities.models.ModelRegistry;
import com.valeriotor.BTV.entities.render.RegisterRenders;
import com.valeriotor.BTV.events.ClientEvents;
import com.valeriotor.BTV.events.RenderEvents;
import com.valeriotor.BTV.fluids.ModFluids;
import com.valeriotor.BTV.gui.DialogueRequirement;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.gui.OpenGuiSelector;
import com.valeriotor.BTV.lib.KeyHandler;
import com.valeriotor.BTV.research.ResearchRegistry;
import com.valeriotor.BTV.tileEntities.TileHeart;
import com.valeriotor.BTV.tileEntities.TileLacrymatory;
import com.valeriotor.BTV.tileEntities.TileWateryCradle;
import com.valeriotor.BTV.tileEntities.renderers.TESRHeart;
import com.valeriotor.BTV.tileEntities.renderers.TESRLacrymatory;
import com.valeriotor.BTV.tileEntities.renderers.TESRWateryCradle;
import com.valeriotor.BTV.util.RegistryHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	
	public static KeyHandler handler;
	
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ModelRegistry.registerModels();
        this.registerEntityRenders();
        handler = new KeyHandler();
        DialogueRequirement.registerRequirements();
        ModFluids.renderFluids();
        }

    //@SubscribeEvent
    //public static void registerModels(ModelRegistryEvent event) {
    //}
    
    @Override
    public void init(FMLInitializationEvent e) {
        RegistryHelper.registerColorHandlers();
        this.cEvents = new ClientEvents();
        renderEvents = new RenderEvents();
        MinecraftForge.EVENT_BUS.register(this.cEvents);
        MinecraftForge.EVENT_BUS.register(this.renderEvents);
        MinecraftForge.EVENT_BUS.register(new OpenGuiSelector(Minecraft.getMinecraft()));
        loadCustomResources();
        registerTileEntitySpecialRenderers();
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent e) {
    	super.postInit(e);
    	ResearchRegistry.registerConnections();
    }
    
    
    public static void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(),id));
    }
    
    public static void registerEntities() {
    	
    }
    
    @Override
    public void openGui(String id, Object... args) {
    	Minecraft.getMinecraft().displayGuiScreen(Guis.getGui(id, args));
    }
    
    @Override
    public void closeGui(EntityPlayer p) {
    	if(p.equals(Minecraft.getMinecraft().player))
    	Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
    }
    
    
    private void registerEntityRenders() {
    	RegisterRenders.register();
    }
    
    private void registerTileEntitySpecialRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileWateryCradle.class, new TESRWateryCradle());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLacrymatory.class, new TESRLacrymatory());
        ClientRegistry.bindTileEntitySpecialRenderer(TileHeart.class, new TESRHeart());
    }
    
    @Override
    public void loadCustomResources() {
    	AnimationRegistry.loadAnimations();
    }
    
    @Override
    public EntityPlayer getPlayer() {
    	return Minecraft.getMinecraft().player;
    }
    
    
	
	
}
