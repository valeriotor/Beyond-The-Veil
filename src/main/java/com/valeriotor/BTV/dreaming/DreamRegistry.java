package com.valeriotor.BTV.dreaming;

import java.util.EnumMap;
import java.util.Map;

import com.valeriotor.BTV.dreaming.dreams.Dream;
import com.valeriotor.BTV.dreaming.dreams.DreamAnimal;
import com.valeriotor.BTV.dreaming.dreams.DreamBeheading;
import com.valeriotor.BTV.dreaming.dreams.DreamChange;
import com.valeriotor.BTV.dreaming.dreams.DreamDarkness;
import com.valeriotor.BTV.dreaming.dreams.DreamDeath;
import com.valeriotor.BTV.dreaming.dreams.DreamEldritch;
import com.valeriotor.BTV.dreaming.dreams.DreamGround;
import com.valeriotor.BTV.dreaming.dreams.DreamHuman;
import com.valeriotor.BTV.dreaming.dreams.DreamIntrospection;
import com.valeriotor.BTV.dreaming.dreams.DreamLearning;
import com.valeriotor.BTV.dreaming.dreams.DreamPlant;
import com.valeriotor.BTV.dreaming.dreams.DreamPower;
import com.valeriotor.BTV.dreaming.dreams.DreamRepair;
import com.valeriotor.BTV.dreaming.dreams.DreamSentience;
import com.valeriotor.BTV.dreaming.dreams.DreamStillness;
import com.valeriotor.BTV.dreaming.dreams.DreamTool;
import com.valeriotor.BTV.dreaming.dreams.DreamVoid;

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
	}
	
	
}
