package com.valeriotor.BTV.dreaming;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.valeriotor.BTV.dreaming.dreams.Dream;
import com.valeriotor.BTV.dreaming.dreams.DreamEldritch;
import com.valeriotor.BTV.dreaming.dreams.DreamAnimal;
import com.valeriotor.BTV.dreaming.dreams.DreamSentience;
import com.valeriotor.BTV.dreaming.dreams.DreamRepair;
import com.valeriotor.BTV.dreaming.dreams.DreamGround;
import com.valeriotor.BTV.dreaming.dreams.DreamHuman;
import com.valeriotor.BTV.dreaming.dreams.DreamTool;
import com.valeriotor.BTV.dreaming.dreams.DreamDeath;
import com.valeriotor.BTV.dreaming.dreams.DreamChange;
import com.valeriotor.BTV.dreaming.dreams.DreamPower;
import com.valeriotor.BTV.dreaming.dreams.DreamLearning;
import com.valeriotor.BTV.dreaming.dreams.DreamDarkness;
import com.valeriotor.BTV.dreaming.dreams.DreamVoid;
import com.valeriotor.BTV.dreaming.dreams.DreamStillness;

import net.minecraft.init.Blocks;

public class DreamRegistry {
	
	public static final DreamEldritch alienis = new DreamEldritch("alienis", 3);
	public static final DreamAnimal bestia = new DreamAnimal("animalsearch", 3);
	public static final DreamSentience cognitio = new DreamSentience("villagesearch", 3);
	public static final DreamRepair fabrico = new DreamRepair("repair", 3);
	public static final DreamHuman humanus = new DreamHuman("playersearch", 3);
	public static final DreamTool instrumentum = new DreamTool("playeritem", 3);
	public static final DreamDeath mortuus = new DreamDeath("deathsearch", 3);
	public static final DreamChange permutatio = new DreamChange("changeeffects", 6);
	public static final DreamPower potentia = new DreamPower("boosteffects", 6);
	public static final DreamDarkness tenebrae = new DreamDarkness("innsmouthsearch", 3);
	public static final DreamVoid vacuos = new DreamVoid("voiddream", 0);
	public static final DreamStillness vinculum = new DreamStillness("keepeffects", 6);
	
	public static final DreamLearning learning = new DreamLearning("learning", 6);
	
	public static final DreamGround metallum = new DreamGround("groundscanmetal", 3, Blocks.IRON_ORE.getDefaultState(), Blocks.GOLD_ORE.getDefaultState());
	public static final DreamGround vitreus = new DreamGround("groundscancrystal", 3, Blocks.DIAMOND_ORE.getDefaultState(), Blocks.EMERALD_ORE.getDefaultState());
	
	
	public static final Map<Memory, Dream> dreams= new EnumMap<>(Memory.class);
	
	static {
		dreams.put(Memory.ELDRITCH, alienis);
		dreams.put(Memory.ANIMAL, bestia);
		dreams.put(Memory.SENTIENCE, cognitio);
		dreams.put(Memory.REPAIR, fabrico);
		dreams.put(Memory.HUMAN, humanus);
		dreams.put(Memory.TOOL, instrumentum);
		dreams.put(Memory.DEATH, mortuus);
		dreams.put(Memory.CHANGE, permutatio);
		dreams.put(Memory.POWER, potentia);
		dreams.put(Memory.DARKNESS, tenebrae);
		dreams.put(Memory.VOID, vacuos);
		dreams.put(Memory.STILLNESS, vinculum);
		dreams.put(Memory.METAL, metallum);
		dreams.put(Memory.CRYSTAL, vitreus);
		dreams.put(Memory.LEARNING, learning);
	}
	
	
}
