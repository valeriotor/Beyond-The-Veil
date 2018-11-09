package com.valeriotor.BTV.world.Structures;

import java.awt.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.world.World;

public class HamletStructuresRegistry {
	
	private static HashMap<String, int[]> map = new HashMap<String, int[]>(); 	//String is ID, int[] is weight and max
	private static HashMap<String, Class<? extends HamletStructure>> textMap = new HashMap<String, Class<? extends HamletStructure>>(); //String is ID, Class is HamletStructure
	private ArrayList<String> list = new ArrayList<String>();					//String is ID
	private HashMap<String, int[]> counterMap = new HashMap<String, int[]>();	//String is ID, int[0] is number of times the structure was used, int[1] and [2] are start and end 
	
	public HamletStructuresRegistry() {
		for(Entry<String, int[]> entry : map.entrySet()) {											// An entry for each value of map
			int[] ints = {0,0,0,0};																	// Making an array to put into counterMap
			counterMap.put(entry.getKey(), ints);													// Putting key and array into counterMap
			for(int i = 1; i<=entry.getValue()[0]; i++) {											// For 'weight' times, weight being taken from the map entry for that structure
				if(i == 1) counterMap.get(entry.getKey())[1] = list.size();							// For the first cycle, set the 'start' equal to the list's size
				if(i == entry.getValue()[0]) counterMap.get(entry.getKey())[2] = list.size();		// For the last cycle, set the 'end' equal to the list's size
				list.add(entry.getKey());															// Every cycle, add the String ID to the list
			}
		}
	}
	
	
	/**
	 * Returns a HamletStructure based on its weight and the number of times it was already used.
	 * @param r A Random
	 * @param w The World the Hamlet's in
	 * 
	 * @author Valeriotor
	 */
	public HamletStructure getRandom(Random r, World w){
		
		Constructor construct = null;
		if(list.size() != 0) {																		// If, somehow, the list was empty, we'll just return a new HamletHouse1(w);
		int b = r.nextInt(list.size());																// Get a random number based on the list's size
		String id = list.get(b);																	// Get the value (i.e. the ID) from the list based on the random number
		try {																						//
			construct = textMap.get(id).getConstructor(World.class);								// We'll attempt getting a constructor(World) from the clazz in the textMap
		} catch (NoSuchMethodException | SecurityException e) {										// Error handling
			e.printStackTrace();																	//
		}																							//
		counterMap.get(id)[0]++;																	// We'll increase the number of times the structure was used in the counterMap
		int[] temp = counterMap.get(id);															// Make a convenience array out of the counterMap
		if(temp[0] >= map.get(id)[1]) {																// If the structure was used a number of times equal to its max, do this:
			for(int i = temp[2]; i>=temp[1]; i--) {													// For every number from the higher end of the structure's spots on the list,
				try {																				// to its lower end:
					list.remove(i);																	// Remove that value from the list
				}catch(Exception e) {																// Error handling
					e.printStackTrace();															//
				}																					//
			}																						//
			counterMap.get(id)[3] = 1;																// Put the counterMap's array for that structure to 1 for easy checking
			for(Entry<String, int[]> entry : counterMap.entrySet()) {								// For every entry in the counterMap
				if(entry.getValue()[3] == 0) {														// If the structure for that entry wasn't already removed
					if(entry.getValue()[1] > counterMap.get(id)[1]) {								// If this structure's start was higher than the removed structure's start
						counterMap.get(entry.getKey())[1]-= map.get(id)[0];							// Decrease its corresponding start and end by the number of spots the
						counterMap.get(entry.getKey())[2]-= map.get(id)[0];							// removed structure was taking on the list
					}
				}
			}
		}
		try {
			return (HamletStructure) construct.newInstance(w);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		}
		return new HamletHouse1(w);
		
	}
	
	public static void registerStructures() {
		
		registerStructure(HamletHouse1.class, "HH1", 9, 12);
		registerStructure(HamletHouse2.class, "HH2", 9, 12);
		registerStructure(HamletStorehouse.class, "HStH", 4, 2);
		registerStructure(HamletHouseTwoFloors.class, "HTF", 7, 9);
		registerStructure(HamletSmallHut.class, "HSmH", 9, 10);
		registerStructure(HamletSaloon.class, "HSA", 7, 1);
		registerStructure(HamletStoreHouse2.class, "HStH2", 2, 1);
		registerStructure(HamletTownHall.class, "HTH", 1, 1);
		registerStructure(HamletLightHouse.class, "HLH", 6, 1);
		
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
	public static void registerStructure(Class<? extends HamletStructure> clazz, String name, int weight, int max) {
		map.put(name, makeArray(weight, max));
		textMap.put(name, clazz);
	}
	
	/**
	 * Returns an int array with 2 values:
	 * [0] is weight, [1] is max number of structures of that kind that can be generated.
	 * @param a weight
	 * @param b max
	 * 
	 * @author Valeriotor
	 */
	private static int[] makeArray(int a, int b) {
		int[] c = {a,b};						
		return c;
	}
	
}
