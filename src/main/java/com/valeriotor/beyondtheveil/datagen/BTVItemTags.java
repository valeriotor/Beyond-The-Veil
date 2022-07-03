package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BTVItemTags extends ItemTagsProvider {

    public BTVItemTags(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(generator, blockTags, References.MODID, helper);
    }

    @Override
    protected void addTags() {
        //tag(Tags.Items.STONE)
        //        .add(Registration.DAMP_STONE_ITEM.get());
    }

    @Override
    public String getName() {
        return "BTV Tags";
    }
}
