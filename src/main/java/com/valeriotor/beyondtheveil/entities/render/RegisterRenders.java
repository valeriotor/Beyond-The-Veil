package com.valeriotor.beyondtheveil.entities.render;

import java.util.function.Function;

import com.valeriotor.beyondtheveil.entities.*;
import com.valeriotor.beyondtheveil.entities.bosses.EntityDeepOneBrute;
import com.valeriotor.beyondtheveil.entities.bosses.EntityDeepOneMyrmidon;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamFluid;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamVillager;
import com.valeriotor.beyondtheveil.entities.ictya.*;

import com.valeriotor.beyondtheveil.entities.projectile.EntityUmancalaBall;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


public class RegisterRenders {
	public static void register() {
		
		register(EntityDeepOne.class, RenderDeepOne::new);
		register(EntityHamletDweller.class, RenderHamletDweller::new);
		register(EntityCanoe.class, RenderCanoe::new);
		register(EntityCrawlingVillager.class, RenderCrawlingVillager::new);
		register(EntityWeeper.class, RenderWeeper::new);
		register(EntityFletum.class, RenderFletum::new);
		register(EntityShoggoth.class, RenderShoggoth::new);
		register(EntityBloodZombie.class, RenderBloodZombie::new);
		register(EntityBloodSkeleton.class, RenderBloodSkeleton::new);
		register(EntityCrazedWeeper.class, RenderCrazedWeeper::new);
		register(EntityDreamFluid.class, RenderDreamFluid::new);
		register(EntityDreamVillager.class, RenderDreamVillager::new);
		register(EntitySurgeon.class, RenderSurgeon::new);
		register(EntityDreadfish.class, RenderDreadfish::new);
		register(EntityMuray.class, RenderMuray::new);
		register(EntityOctid.class, RenderOctid::new);
		register(EntityDeepAngler.class, RenderDeepAngler::new);
		register(EntitySarfin.class, RenderSarfin::new);
		register(EntityManOWar.class, RenderManOWar::new);
		register(EntityJelly.class, RenderJelly::new);
		register(EntityDeepOneBrute.class, RenderDeepOneBrute::new);
		register(EntityDeepOneMyrmidon.class, RenderDeepOneMyrmidon::new);
		register(EntityCephalopodian.class, RenderCephalopodian::new);
		register(EntitySandflatter.class, RenderSandflatter::new);
		register(EntityUmancala.class, RenderUmancala::new);
		register(EntityBoneCage.class, RenderBoneCage::new);



		register(EntityUmancalaBall.class, RenderUmancalaBall::new);

		//Starspawn
		//register(EntityStarspawn.class, new RenderFactory<EntityStarspawn>(RenderStarspawn::new));
		
	}
	
	private static <T extends Entity> void register(Class<T> entityClass, Function<RenderManager, Render<? super T>> renderFunc) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, new RenderFactory<>(renderFunc));
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
