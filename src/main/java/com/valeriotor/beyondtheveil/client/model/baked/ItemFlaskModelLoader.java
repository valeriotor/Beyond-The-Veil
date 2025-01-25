package com.valeriotor.beyondtheveil.client.model.baked;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

import java.util.function.Function;

public class ItemFlaskModelLoader implements IGeometryLoader<ItemFlaskModelLoader.ItemFlaskModelGeometry> {

    public static final ResourceLocation ITEM_FLASK_LOADER = new ResourceLocation(References.MODID, "item_flask_loader");

    public static final ResourceLocation ITEM_FLASK = new ResourceLocation(References.MODID, "block/flask");

    public static final Material MATERIAL_ITEM_FLASK = ForgeHooksClient.getBlockMaterial(ITEM_FLASK);

    @Override
    public ItemFlaskModelLoader.ItemFlaskModelGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        return new ItemFlaskModelLoader.ItemFlaskModelGeometry();
    }

    public static class ItemFlaskModelGeometry implements IUnbakedGeometry<ItemFlaskModelLoader.ItemFlaskModelGeometry> {

        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            return new ItemFlaskBakedModel(modelState, spriteGetter, overrides);
        }
    }
}
