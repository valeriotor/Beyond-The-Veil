package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), (DataProvider.Factory<BTVRecipes>) BTVRecipes::new);
        //generator.addProvider(new BTVLootTables(generator));
        DataProvider.Factory<BTVBlockTags> blockTags = output -> new BTVBlockTags(output, event.getLookupProvider(), event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        //DataProvider.Factory<BTVItemTags> itemTags = output -> new BTVItemTags(output, event.getLookupProvider(), blockTags, event.getExistingFileHelper());
        //generator.addProvider(event.includeServer(), itemTags);
        //TODO create ConfiguredStructureTagsProvider


        generator.addProvider(event.includeClient(), (DataProvider.Factory<BTVBlockStates>) output -> new BTVBlockStates(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), (DataProvider.Factory<BTVItemModels>) output -> new BTVItemModels(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), (DataProvider.Factory<BTVLanguageProvider>) output -> new BTVLanguageProvider(output, "en_us"));
    }

}
