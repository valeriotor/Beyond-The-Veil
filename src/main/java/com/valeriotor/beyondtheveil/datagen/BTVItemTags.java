package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BTVItemTags extends ItemTagsProvider {

    public BTVItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, ExistingFileHelper helper) {
        super(output, pLookupProvider, blockTagProvider, References.MODID, helper);
    }

    //TODO @Override
    protected void addTags() {
        //tag(Tags.Items.STONE)
        //        .add(Registration.DAMP_STONE_ITEM.get());
    }

    @Override
    public String getName() {
        return "BTV Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

    }
}
