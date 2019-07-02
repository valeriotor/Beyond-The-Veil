package com.valeriotor.BTV.world.Structures;

import java.util.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.world.World;

public class HamletStructuresRegistry {
	
	private static List<StructureTemplate> templates = Lists.newArrayList();
	private List<StructureCounter> counters = Lists.newArrayList();
	private int totalWeight = 0;
	
	public HamletStructuresRegistry() {
		counters = templates.stream().map(StructureCounter::new).collect(Collectors.toList());
		for(StructureCounter sc : counters) totalWeight += sc.getWeight();
	}
	
	/**
	 * Returns a HamletStructure based on its weight and the number of times it was already used.
	 * @param r A Random
	 * @param w The World the Hamlet's in
	 * 
	 * @author Valeriotor
	 */
	public HamletStructure getRandom(Random r, World w){
		int selected = r.nextInt(this.totalWeight);
		int index = 0;
		for(int i = 0; i < counters.size(); i++) {
			selected -= counters.get(i).getWeight();
			if(selected <= 0) {
				index = i;
				break;
			}
		}
		StructureCounter sc = counters.get(index);
		HamletStructure returnValue = sc.getHamletStructure(w);
		sc.increase();
		if(sc.getCounter() >= sc.getMax()) {
			this.totalWeight -= sc.getWeight();
			counters.remove(index);
		}
		
		return returnValue;		
	}
	
	public static void registerStructures() {
		
		registerStructure(HamletHouse1.class, 9, 12);
		registerStructure(HamletHouse2.class, 9, 12);
		registerStructure(HamletStorehouse.class, 4, 2);
		registerStructure(HamletHouseTwoFloors.class, 7, 9);
		registerStructure(HamletSmallHut.class, 9, 10);
		registerStructure(HamletSaloon.class, 7, 1);
		registerStructure(HamletStoreHouse2.class, 2, 1);
		registerStructure(HamletTownHall.class, 1, 1);
		registerStructure(HamletLightHouse.class, 10, 1);
		
	}
	
	/**
	 * Registers a structure to spawn in the village.
	 * @param clazz Class of the structure
	 * @param name String ID of the Structure
	 * @param weight The likelihood of that structure generating against other ones
	 * @param max The max number of structures of that type that can be in the Hamlet
	 * 
	 * @author Valeriotor
	 */
	public static void registerStructure(Class<? extends HamletStructure> clazz, int weight, int max) {
		templates.add(new StructureTemplate(clazz, weight, max));
	}
	
	
	private static class StructureTemplate {
		private final Class<? extends HamletStructure> clazz;
		private final int weight;
		private final int max;
		
		public StructureTemplate(Class<? extends HamletStructure> clazz, int weight, int max) {
			this.clazz = clazz;
			this.weight = weight;
			this.max = max;
		}
		
		public Class<? extends HamletStructure> getStructure() {return this.clazz;}
		public int getWeight() {return this.weight;}
		public int getMax() {return this.max;}
		public HamletStructure getInstance(World w) {
			Constructor<? extends HamletStructure> construct = null;
			try {
				construct = this.clazz.getConstructor(World.class);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			if(construct == null) return new HamletHouse1(w);
			try {
				return construct.newInstance(w);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
			return new HamletHouse1(w);
		}
		
	}
	
	private static class StructureCounter {
		private StructureTemplate template;
		private int counter = 0;
		public StructureCounter(StructureTemplate template) {
			this.template = template;
		}
		public Class<? extends HamletStructure> getStructure() {return this.template.getStructure();}
		public int getWeight() {return this.template.getWeight();}
		public int getMax() {return this.template.getMax();}
		public void increase() {this.counter++;}
		public int getCounter() {return this.counter;}
		public HamletStructure getHamletStructure(World w) {return this.template.getInstance(w);}
		
	}	
	
}
