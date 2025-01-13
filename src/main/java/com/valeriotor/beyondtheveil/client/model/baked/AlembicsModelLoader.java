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

public class AlembicsModelLoader implements IGeometryLoader<AlembicsModelLoader.AlembicsModelGeometry> {

    public static final ResourceLocation ALEMBICS_LOADER = new ResourceLocation(References.MODID, "alembics_loader");

    public static final ResourceLocation ALEMBICS = new ResourceLocation(References.MODID, "block/alembics");

    public static final Material MATERIAL_ALEMBICS = ForgeHooksClient.getBlockMaterial(ALEMBICS);

    @Override
    public AlembicsModelGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        return new AlembicsModelGeometry();
    }

    public static class AlembicsModelGeometry implements IUnbakedGeometry<AlembicsModelGeometry> {

        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            return new AlembicsBakedModel(modelState, spriteGetter, overrides);
        }
    }

}
