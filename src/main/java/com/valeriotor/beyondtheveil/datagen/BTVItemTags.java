package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BTVItemTags extends ItemTagsProvider {

    public BTVItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> pBlockTags, @org.jetbrains.annotations.Nullable net.minecraftforge.common.data.ExistingFileHelper helper) {
        super(output, lookupProvider, pBlockTags, References.MODID, helper);
    }



    @Override
    public String getName() {
        return "btv_item_tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(Registration.LARVA_FOOD).add(Items.BEEF).add(Items.PORKCHOP).add(Items.CHICKEN).add(Items.MUTTON).add(Items.RABBIT);
    }
}
