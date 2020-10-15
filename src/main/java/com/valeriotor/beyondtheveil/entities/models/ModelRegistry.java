package com.valeriotor.beyondtheveil.entities.models;

public class ModelRegistry {
	
	public static ModelCanoe canoe;
	public static ModelCrawlingVillager crawling_villager;
	public static ModelDeepOne deep_one;
	public static ModelFletum fletum;
	public static ModelWeeper weeper;
	public static ModelStarspawn starspawn;
	public static ModelShoggoth shoggoth;
	public static ModelParasite parasite;
	public static ModelBloodZombie blood_zombie;
	public static ModelBloodSkeleton blood_skeleton;
	public static ModelCrazedWeeper crazed_weeper;
	public static ModelSurgeon surgeon;
	public static ModelDreadfish dreadfish;
	public static ModelMuray muray;
	public static ModelOctid octid;
	public static ModelDeepAngler deep_angler;
	public static ModelSarfin sarfin;
	
	public static void registerModels() {
		canoe = new ModelCanoe();
		crawling_villager = new ModelCrawlingVillager();
		deep_one = new ModelDeepOne();
		fletum = new ModelFletum();
		weeper = new ModelWeeper();
		starspawn = new ModelStarspawn();
		shoggoth = new ModelShoggoth();
		parasite = new ModelParasite();
		blood_zombie = new ModelBloodZombie();
		blood_skeleton = new ModelBloodSkeleton();
		crazed_weeper = new ModelCrazedWeeper();
		surgeon = new ModelSurgeon();
		dreadfish = new ModelDreadfish();
		muray = new ModelMuray();
		octid = new ModelOctid();
		deep_angler = new ModelDeepAngler();
		sarfin = new ModelSarfin();
	}
	
}
