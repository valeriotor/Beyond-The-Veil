package com.valeriotor.beyondtheveil.entities.render;

import java.util.function.Function;

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
import com.valeriotor.beyondtheveil.entities.ictya.EntityDreadfish;
import com.valeriotor.beyondtheveil.entities.ictya.EntityMuray;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


public class RegisterRenders {
	public static void register() {
		
		register(EntityDeepOne.class, new RenderFactory<>(RenderDeepOne::new));
		register(EntityHamletDweller.class, new RenderFactory<>(RenderHamletDweller::new));
		register(EntityCanoe.class, new RenderFactory<>(RenderCanoe::new));
		register(EntityCrawlingVillager.class, new RenderFactory<>(RenderCrawlingVillager::new));
		register(EntityWeeper.class, new RenderFactory<>(RenderWeeper::new));
		register(EntityFletum.class, new RenderFactory<>(RenderFletum::new));
		register(EntityShoggoth.class, new RenderFactory<>(RenderShoggoth::new));
		register(EntityBloodZombie.class, new RenderFactory<>(RenderBloodZombie::new));
		register(EntityBloodSkeleton.class, new RenderFactory<>(RenderBloodSkeleton::new));
		register(EntityCrazedWeeper.class, new RenderFactory<>(RenderCrazedWeeper::new));
		register(EntityDreamFluid.class, new RenderFactory<>(RenderDreamFluid::new));
		register(EntityDreamVillager.class, new RenderFactory<>(RenderDreamVillager::new));
		register(EntitySurgeon.class, new RenderFactory<>(RenderSurgeon::new));
		register(EntityDreadfish.class, new RenderFactory<>(RenderDreadfish::new));
		register(EntityMuray.class, new RenderFactory<>(RenderMuray::new));
		
		//Starspawn
		//register(EntityStarspawn.class, new RenderFactory<EntityStarspawn>(RenderStarspawn::new));
		
	}
	
	private static <T extends Entity> void register(Class<T> entityClass, IRenderFactory<? super T> renderFactory) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}
	
	private static class RenderFactory<T extends Entity> implements IRenderFactory<T> {
		private final Function<RenderManager, Render<? super T>> func;
		private RenderFactory(Function<RenderManager, Render<? super T>> func) {
			this.func = func;
		}
		
		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return func.apply(manager);
		}
		
	}
	
}
