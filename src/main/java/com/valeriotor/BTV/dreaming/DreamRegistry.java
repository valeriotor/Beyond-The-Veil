package com.valeriotor.BTV.dreaming;

import java.util.HashMap;
import java.util.Map;

import com.valeriotor.BTV.dreaming.dreams.AbstractDream;
import com.valeriotor.BTV.dreaming.dreams.DreamAlienis;
import com.valeriotor.BTV.dreaming.dreams.DreamBestia;
import com.valeriotor.BTV.dreaming.dreams.DreamCognitio;
import com.valeriotor.BTV.dreaming.dreams.DreamFabrico;
import com.valeriotor.BTV.dreaming.dreams.DreamGround;
import com.valeriotor.BTV.dreaming.dreams.DreamHumanus;
import com.valeriotor.BTV.dreaming.dreams.DreamInstrumentum;
import com.valeriotor.BTV.dreaming.dreams.DreamMortuus;
import com.valeriotor.BTV.dreaming.dreams.DreamPermutatio;
import com.valeriotor.BTV.dreaming.dreams.DreamPotentia;
import com.valeriotor.BTV.dreaming.dreams.DreamPrimal;
import com.valeriotor.BTV.dreaming.dreams.DreamTenebrae;
import com.valeriotor.BTV.dreaming.dreams.DreamVacuos;
import com.valeriotor.BTV.dreaming.dreams.DreamVinculum;

import net.minecraft.init.Blocks;

public class DreamRegistry {
	
	public static final DreamAlienis alienis = new DreamAlienis("alienis", 3);
	public static final DreamBestia bestia = new DreamBestia("animalsearch", 3);
	public static final DreamCognitio cognitio = new DreamCognitio("villagesearch", 3);
	public static final DreamFabrico fabrico = new DreamFabrico("repair", 3);
	public static final DreamHumanus humanus = new DreamHumanus("playersearch", 3);
	public static final DreamInstrumentum instrumentum = new DreamInstrumentum("playeritem", 3);
	public static final DreamMortuus mortuus = new DreamMortuus("deathsearch", 3);
	public static final DreamPermutatio permutatio = new DreamPermutatio("changeeffects", 6);
	public static final DreamPotentia potentia = new DreamPotentia("boosteffects", 6);
	public static final DreamTenebrae tenebrae = new DreamTenebrae("innsmouthsearch", 3);
	public static final DreamVacuos vacuos = new DreamVacuos("voiddream", 0);
	public static final DreamVinculum vinculum = new DreamVinculum("keepeffects", 6);
	
	public static final DreamPrimal aer = new DreamPrimal("aer", 3, new int[] {7, 1, 8, 2, 2, 12});
	public static final DreamPrimal aqua = new DreamPrimal("aqua", 3, new int[] {4, 13, 8, 2, 1, 4});
	public static final DreamPrimal ignis = new DreamPrimal("ignis", 3, new int[] {7, 4, 3, 9, 7, 2});
	public static final DreamPrimal ordo = new DreamPrimal("ordo", 3, new int[] {4, 2, 12, 9, 3, 2});
	public static final DreamPrimal perditio = new DreamPrimal("perditio", 3, new int[] {2, 10, 1, 3, 8, 8});
	public static final DreamPrimal terra = new DreamPrimal("terra", 3, new int[] {7, 2, 5, 9, 9, 0});
	
	public static final DreamGround metallum = new DreamGround("groundscanmetal", 3, Blocks.IRON_ORE.getDefaultState(), Blocks.GOLD_ORE.getDefaultState());
	public static final DreamGround vitreus = new DreamGround("groundscancrystal", 3, Blocks.DIAMOND_ORE.getDefaultState(), Blocks.EMERALD_ORE.getDefaultState());
	
	
	public static final Map<String, AbstractDream> dreams = new HashMap<>();
	
	static {
		dreams.put("Alienis", alienis);
		dreams.put("Bestia", bestia);
		dreams.put("Cognitio", cognitio);
		dreams.put("Fabrico", fabrico);
		dreams.put("Humanus", humanus);
		dreams.put("Instrumentum", instrumentum);
		dreams.put("Mortuus", mortuus);
		dreams.put("Permutatio", permutatio);
		dreams.put("Potentia", potentia);
		dreams.put("Tenebrae", tenebrae);
		dreams.put("Vacuos", vacuos);
		dreams.put("Vinculum", vinculum);
		dreams.put("Aer", aer);
		dreams.put("Aqua", aqua);
		dreams.put("Ignis", ignis);
		dreams.put("Ordo", ordo);
		dreams.put("Perditio", perditio);
		dreams.put("Terra", terra);
		dreams.put("Metallum", metallum);
		dreams.put("Vitreus", vitreus);
	}
	
	
}
