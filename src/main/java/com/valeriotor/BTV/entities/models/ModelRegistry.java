package com.valeriotor.BTV.entities.models;

public class ModelRegistry {
	
	public static ModelCanoe canoe;
	public static ModelCrawlingVillager crawling_villager;
	public static ModelDeepOne deep_one;
	public static ModelWeeper weeper;
	
	public static void registerModels() {
		canoe = new ModelCanoe();
		crawling_villager = new ModelCrawlingVillager();
		deep_one = new ModelDeepOne();
		weeper = new ModelWeeper();
	}
	
}
