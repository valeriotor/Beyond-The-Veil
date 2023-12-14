package com.valeriotor.beyondtheveil.client.model.loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.valeriotor.beyondtheveil.client.model.baked.FlaskShelfBakedModel;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class FlaskModelLoader implements IModelLoader<FlaskModelLoader.FlaskModelGeometry> {


    public static final ResourceLocation FLASK_LOADER = new ResourceLocation(References.MODID, "flask");


    public static final ResourceLocation FLASK = new ResourceLocation(References.MODID, "block/flask_large");

    public static final Material FLASK_MATERIAL = ForgeHooksClient.getBlockMaterial(FLASK);

    @Override
    public FlaskModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new FlaskModelGeometry();
    }

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {

    }

    public static class FlaskModelGeometry implements IModelGeometry<FlaskModelGeometry> {


        @Override
        public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
            return new FlaskShelfBakedModel(modelTransform, spriteGetter, overrides, owner.getCameraTransforms());
        }

        @Override
        public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            return List.of(FLASK_MATERIAL);
        }
    }
}
