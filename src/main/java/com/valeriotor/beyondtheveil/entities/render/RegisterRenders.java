package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.EntityBloodSkeleton;
import com.valeriotor.beyondtheveil.entities.EntityBloodZombie;
import com.valeriotor.beyondtheveil.entities.EntityCanoe;
import com.valeriotor.beyondtheveil.entities.EntityCrawlingVillager;
import com.valeriotor.beyondtheveil.entities.EntityCrazedWeeper;
import com.valeriotor.beyondtheveil.entities.EntityDeepOne;
import com.valeriotor.beyondtheveil.entities.EntityFletum;
import com.valeriotor.beyondtheveil.entities.EntityHamletDweller;
import com.valeriotor.beyondtheveil.entities.EntityShoggoth;
import com.valeriotor.beyondtheveil.entities.EntitySurgeon;
import com.valeriotor.beyondtheveil.entities.EntityWeeper;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamFluid;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamVillager;

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
		/*RenderingRegistry.registerEntityRenderingHandler(EntityStarspawn.class, new IRenderFactory<EntityStarspawn>(){
			@Override
			public Render<EntityStarspawn> createRenderFor(RenderManager manager) 
			{return new RenderStarspawn(manager);}
			});
		*/
		//Shoggoth
		RenderingRegistry.registerEntityRenderingHandler(EntityShoggoth.class, new IRenderFactory<EntityShoggoth>(){
			@Override
			public Render<EntityShoggoth> createRenderFor(RenderManager manager) 
			{return new RenderShoggoth(manager);}
			});
		
		//Blood Zombie
		RenderingRegistry.registerEntityRenderingHandler(EntityBloodZombie.class, new IRenderFactory<EntityBloodZombie>(){
			@Override
			public Render<EntityBloodZombie> createRenderFor(RenderManager manager) 
			{return new RenderBloodZombie(manager);}
			});
		
		//Blood Skeleton
		RenderingRegistry.registerEntityRenderingHandler(EntityBloodSkeleton.class, new IRenderFactory<EntityBloodSkeleton>(){
			@Override
			public Render<EntityBloodSkeleton> createRenderFor(RenderManager manager) 
			{return new RenderBloodSkeleton(manager);}
			});
		
		//Crazed Weeper
		RenderingRegistry.registerEntityRenderingHandler(EntityCrazedWeeper.class, new IRenderFactory<EntityCrazedWeeper>(){
			@Override
			public Render<EntityCrazedWeeper> createRenderFor(RenderManager manager) 
			{return new RenderCrazedWeeper(manager);}
			});
		
		//Dream Fluid
		RenderingRegistry.registerEntityRenderingHandler(EntityDreamFluid.class, new IRenderFactory<EntityDreamFluid>(){
			@Override
			public Render<EntityDreamFluid> createRenderFor(RenderManager manager) 
			{return new RenderDreamFluid(manager);}
			});
		
		//Dream Fluid
		RenderingRegistry.registerEntityRenderingHandler(EntityDreamVillager.class, new IRenderFactory<EntityDreamVillager>(){
			@Override
			public Render<EntityDreamVillager> createRenderFor(RenderManager manager) 
			{return new RenderDreamVillager(manager);}
			});
		
		//Surgeon
		RenderingRegistry.registerEntityRenderingHandler(EntitySurgeon.class, new IRenderFactory<EntitySurgeon>(){
			@Override
			public Render<EntitySurgeon> createRenderFor(RenderManager manager) 
			{return new RenderSurgeon(manager);}
			});
	}
}
