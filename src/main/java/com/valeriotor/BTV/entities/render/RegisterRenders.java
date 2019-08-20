package com.valeriotor.BTV.entities.render;

import com.valeriotor.BTV.entities.EntityCanoe;
import com.valeriotor.BTV.entities.EntityCrawlingVillager;
import com.valeriotor.BTV.entities.EntityDeepOne;
import com.valeriotor.BTV.entities.EntityFletum;
import com.valeriotor.BTV.entities.EntityHamletDweller;
import com.valeriotor.BTV.entities.EntityStarspawn;
import com.valeriotor.BTV.entities.EntityWeeper;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


public class RegisterRenders {
	public static void register() {
		
		//Deep One
		RenderingRegistry.registerEntityRenderingHandler(EntityDeepOne.class, new IRenderFactory<EntityDeepOne>(){
		    @Override
		    public Render<EntityDeepOne> createRenderFor(RenderManager manager) 
		    {return new RenderDeepOne(manager);}
		    });
		
		//Hamlet Dweller
		RenderingRegistry.registerEntityRenderingHandler(EntityHamletDweller.class, new IRenderFactory<EntityHamletDweller>(){
		    @Override
		    public Render<EntityHamletDweller> createRenderFor(RenderManager manager) 
		    {return new RenderHamletDweller(manager);}
		    });
		
		//Canoe
		RenderingRegistry.registerEntityRenderingHandler(EntityCanoe.class, new IRenderFactory<EntityCanoe>(){
			@Override
			public Render<EntityCanoe> createRenderFor(RenderManager manager) 
			{return new RenderCanoe(manager);}
			});
		
		//Canoe
		RenderingRegistry.registerEntityRenderingHandler(EntityCrawlingVillager.class, new IRenderFactory<EntityCrawlingVillager>(){
			@Override
			public Render<EntityCrawlingVillager> createRenderFor(RenderManager manager) 
			{return new RenderCrawlingVillager(manager);}
			});
		
		//Weeper
		RenderingRegistry.registerEntityRenderingHandler(EntityWeeper.class, new IRenderFactory<EntityWeeper>(){
			@Override
			public Render<EntityWeeper> createRenderFor(RenderManager manager) 
			{return new RenderWeeper(manager);}
			});
		
		//Weeper
		RenderingRegistry.registerEntityRenderingHandler(EntityFletum.class, new IRenderFactory<EntityFletum>(){
			@Override
			public Render<EntityFletum> createRenderFor(RenderManager manager) 
			{return new RenderFletum(manager);}
			});
		
		//Starspawn
		RenderingRegistry.registerEntityRenderingHandler(EntityStarspawn.class, new IRenderFactory<EntityStarspawn>(){
			@Override
			public Render<EntityStarspawn> createRenderFor(RenderManager manager) 
			{return new RenderStarspawn(manager);}
			});
	}
}
