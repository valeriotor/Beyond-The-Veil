package com.valeriotor.beyondtheveil.dreaming;

import java.util.EnumMap;
import java.util.Map;

import com.valeriotor.beyondtheveil.dreaming.dreams.Dream;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamAnimal;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamBeheading;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamChange;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamDarkness;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamDeath;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamEldritch;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamGround;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamHuman;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamIntrospection;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamLearning;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamPlant;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamPower;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamRepair;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamSentience;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamStillness;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamTool;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamVoid;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamWater;

import net.minecraft.init.Blocks;

public class DreamRegistry {
	
	public static final DreamEldritch eldritch = new DreamEldritch("alienis", 3);
	public static final DreamAnimal animal = new DreamAnimal("animalsearch", 3);
	public static final DreamSentience sentience = new DreamSentience("villagesearch", 3);
	public static final DreamRepair repair = new DreamRepair("repair", 3);
	public static final DreamHuman human = new DreamHuman("playersearch", 3);
	public static final DreamTool tool = new DreamTool("playeritem", 3);
	public static final DreamDeath death = new DreamDeath("deathsearch", 3);
	public static final DreamChange change = new DreamChange("changeeffects", 5);
	public static final DreamPower power = new DreamPower("boosteffects", 6);
	public static final DreamDarkness darkness= new DreamDarkness("innsmouthsearch", 3);
	public static final DreamVoid voidd = new DreamVoid("voiddream", 0);
	public static final DreamStillness stillness = new DreamStillness("keepeffects", 6);
	public static final DreamIntrospection introspection = new DreamIntrospection("introspection", 8);
	public static final DreamBeheading beheading = new DreamBeheading("beheading", 8);
	public static final DreamPlant plant = new DreamPlant("plant", 8);
	public static final DreamWater water = new DreamWater("water", 8);
	
	public static final DreamLearning learning = new DreamLearning("learning", 6);
	
	public static final DreamGround metal = new DreamGround("groundscanmetal", 3, Blocks.IRON_ORE.getDefaultState(), Blocks.GOLD_ORE.getDefaultState());
	public static final DreamGround crystal = new DreamGround("groundscancrystal", 3, Blocks.DIAMOND_ORE.getDefaultState(), Blocks.EMERALD_ORE.getDefaultState());
	
	
	public static final Map<Memory, Dream> dreams= new EnumMap<>(Memory.class);
	
	static {
		dreams.put(Memory.ELDRITCH, eldritch);
		dreams.put(Memory.ANIMAL, animal);
		dreams.put(Memory.SENTIENCE, sentience);
		dreams.put(Memory.REPAIR, repair);
		dreams.put(Memory.HUMAN, human);
		dreams.put(Memory.TOOL, tool);
		dreams.put(Memory.DEATH, death);
		dreams.put(Memory.CHANGE, change);
		dreams.put(Memory.POWER, power);
		dreams.put(Memory.DARKNESS, darkness);
		dreams.put(Memory.VOID, voidd);
		dreams.put(Memory.STILLNESS, stillness);
		dreams.put(Memory.METAL, metal);
		dreams.put(Memory.CRYSTAL, crystal);
		dreams.put(Memory.LEARNING, learning);
		dreams.put(Memory.INTROSPECTION, introspection);
		dreams.put(Memory.BEHEADING, beheading);
		dreams.put(Memory.PLANT, plant);
		dreams.put(Memory.WATER, water);
	}
	
	
}
