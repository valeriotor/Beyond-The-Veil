package com.valeriotor.beyondtheveil.client.model.loader;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.mojang.math.Transformation;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.SimpleModelState;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrphanLoaderRegistry {

    private static Map<String, UnbakedModel> unbakedCache = new HashMap<>();
    private static Map<String, BakedModel> cache = new HashMap<>();

    public static void cacheAllBlockModels() {

    }

    public static void cacheBlockModel(String blockName) {
        try {
            String text = Resources.readLines(BeyondTheVeil.class.getResource(String.format("/assets/beyondtheveil/models/block/%s.json", blockName)), Charsets.UTF_8).stream().collect(Collectors.joining());
            BlockModel blockModel = GsonHelper.fromJson(ModelLoaderRegistry.ExpandedBlockModelDeserializer.INSTANCE, new StringReader(text), BlockModel.class);
            unbakedCache.put(blockName, blockModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void bakeModels(ModelBakeEvent event) {
        cache.clear();
        for (Map.Entry<String, UnbakedModel> entry : unbakedCache.entrySet()) {
            cache.put(entry.getKey(), entry.getValue().bake(event.getModelLoader(), event.getModelLoader().getSpriteMap()::getSprite, new SimpleModelState(Transformation.identity()), new ResourceLocation(References.MODID, "block/flask_large")));
        }
        System.out.println(cache);
    }

    public static BakedModel getBakedModel(String name) {
        return cache.get(name);
    }


}
