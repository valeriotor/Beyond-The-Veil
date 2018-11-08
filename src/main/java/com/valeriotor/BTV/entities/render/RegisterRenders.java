package com.valeriotor.BTV.entities.render;

import com.valeriotor.BTV.entities.EntityDeepOne;
import com.valeriotor.BTV.entities.EntityHamletDweller;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


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
		    public Render<EntityHamletDweller> createRenderFor(RenderManager manager2) 
		    {return new RenderHamletDweller(manager2);}
		    });
	}
}
