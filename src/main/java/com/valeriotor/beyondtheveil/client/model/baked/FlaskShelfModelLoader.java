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

public class FlaskShelfModelLoader implements IGeometryLoader<FlaskShelfModelLoader.FlaskShelfModelGeometry> {

    public static final ResourceLocation FLASK_SHELF_LOADER = new ResourceLocation(References.MODID, "flask_shelf_loader");

    public static final ResourceLocation FLASK_SHELF = new ResourceLocation(References.MODID, "block/flask_shelf");

    public static final Material MATERIAL_FLASK_SHELF = ForgeHooksClient.getBlockMaterial(FLASK_SHELF);

    @Override
    public FlaskShelfModelGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        return new FlaskShelfModelGeometry();
    }

    public static class FlaskShelfModelGeometry implements IUnbakedGeometry<FlaskShelfModelGeometry> {

        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            return new FlaskShelfBakedModel(modelState, spriteGetter, overrides);
        }
    }

}
