package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new BTVRecipes(generator));
            //generator.addProvider(new BTVLootTables(generator));
            BTVBlockTags blockTags = new BTVBlockTags(generator, event.getExistingFileHelper());
            generator.addProvider(blockTags);
            generator.addProvider(new BTVItemTags(generator, blockTags, event.getExistingFileHelper()));
            //TODO create ConfiguredStructureTagsProvider
        }
        if (event.includeClient()) {
            generator.addProvider(new BTVBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(new BTVItemModels(generator, event.getExistingFileHelper()));
            generator.addProvider(new BTVLanguageProvider(generator, "en_us"));
        }
    }

}
