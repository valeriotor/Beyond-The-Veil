package com.valeriotor.beyondtheveil.proxy;

import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.blackmirror.MirrorDialogueRegistry;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.entities.render.RegisterRenders;
import com.valeriotor.beyondtheveil.events.ClientEvents;
import com.valeriotor.beyondtheveil.events.RenderEvents;
import com.valeriotor.beyondtheveil.fluids.ModFluids;
import com.valeriotor.beyondtheveil.gui.DialogueRequirement;
import com.valeriotor.beyondtheveil.gui.Guis;
import com.valeriotor.beyondtheveil.gui.OpenGuiSelector;
import com.valeriotor.beyondtheveil.items.ItemMemoryPhial;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.KeyHandler;
import com.valeriotor.beyondtheveil.research.ResearchRegistry;
import com.valeriotor.beyondtheveil.tileEntities.TileBarrel;
import com.valeriotor.beyondtheveil.tileEntities.TileHeart;
import com.valeriotor.beyondtheveil.tileEntities.TileLacrymatory;
import com.valeriotor.beyondtheveil.tileEntities.TileMegydrea;
import com.valeriotor.beyondtheveil.tileEntities.TileMemorySieve;
import com.valeriotor.beyondtheveil.tileEntities.TileWateryCradle;
import com.valeriotor.beyondtheveil.tileEntities.renderers.TESRBarrel;
import com.valeriotor.beyondtheveil.tileEntities.renderers.TESRHeart;
import com.valeriotor.beyondtheveil.tileEntities.renderers.TESRLacrymatory;
import com.valeriotor.beyondtheveil.tileEntities.renderers.TESRMegydrea;
import com.valeriotor.beyondtheveil.tileEntities.renderers.TESRMemorySieve;
import com.valeriotor.beyondtheveil.tileEntities.renderers.TESRWateryCradle;
import com.valeriotor.beyondtheveil.util.RegistryHelper;

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
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemMemoryPhial.MemoryPhialColorHandler(), ItemRegistry.memory_phial);
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent e) {
    	super.postInit(e);
    	ResearchRegistry.registerConnectionsAndRecipes();;
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
        ClientRegistry.bindTileEntitySpecialRenderer(TileMemorySieve.class, new TESRMemorySieve());
        ClientRegistry.bindTileEntitySpecialRenderer(TileBarrel.class, new TESRBarrel());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMegydrea.class, new TESRMegydrea());
    }
    
    @Override
    public void loadCustomResources() {
    	AnimationRegistry.loadAnimations();
    	DialogueRequirement.registerRequirements();
    }
    
    @Override
    public EntityPlayer getPlayer() {
    	return Minecraft.getMinecraft().player;
    }
    
    
	
	
}
