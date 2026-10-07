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

public class FishBarrelModelLoader implements IGeometryLoader<FishBarrelModelLoader.FishBarrelModelGeometry> {


    public static final ResourceLocation FISH_BARREL_LOADER = new ResourceLocation(References.MODID, "fish_barrel_loader");

    public static final ResourceLocation FISH_BARREL = new ResourceLocation(References.MODID, "block/barrel_side");
    public static final ResourceLocation COD_BARREL = new ResourceLocation(References.MODID, "block/barrel/barrel_cod");
    public static final ResourceLocation COOKED_COD_BARREL = new ResourceLocation(References.MODID, "block/barrel/barrel_cooked_cod");
    public static final ResourceLocation SALMON_BARREL = new ResourceLocation(References.MODID, "block/barrel/barrel_salmon");
    public static final ResourceLocation COOKED_SALMON_BARREL = new ResourceLocation(References.MODID, "block/barrel/barrel_cooked_salmon");
    public static final ResourceLocation TROPICAL_FISH_BARREL = new ResourceLocation(References.MODID, "block/barrel/barrel_tropical_fish");
    public static final ResourceLocation PUFFERFISH_BARREL = new ResourceLocation(References.MODID, "block/barrel/barrel_pufferfish");
    public static final ResourceLocation SLUG_BARREL = new ResourceLocation(References.MODID, "block/barrel/barrel_slug");
    public static final Material MATERIAL_FISH_BARREL = ForgeHooksClient.getBlockMaterial(FISH_BARREL);
    public static final Material MATERIAL_COD_BARREL = ForgeHooksClient.getBlockMaterial(COD_BARREL);
    public static final Material MATERIAL_COOKED_COD_BARREL = ForgeHooksClient.getBlockMaterial(COOKED_COD_BARREL);
    public static final Material MATERIAL_SALMON_BARREL = ForgeHooksClient.getBlockMaterial(SALMON_BARREL);
    public static final Material MATERIAL_COOKED_SALMON_BARREL = ForgeHooksClient.getBlockMaterial(COOKED_SALMON_BARREL);
    public static final Material MATERIAL_TROPICAL_FISH_BARREL = ForgeHooksClient.getBlockMaterial(TROPICAL_FISH_BARREL);
    public static final Material MATERIAL_PUFFERFISH_BARREL = ForgeHooksClient.getBlockMaterial(PUFFERFISH_BARREL);
    public static final Material MATERIAL_SLUG_BARREL = ForgeHooksClient.getBlockMaterial(SLUG_BARREL);


    @Override
    public FishBarrelModelLoader.FishBarrelModelGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        return new FishBarrelModelGeometry();
    }

    public static class FishBarrelModelGeometry implements IUnbakedGeometry<FishBarrelModelLoader.FishBarrelModelGeometry> {

        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            return new FishBarrelBakedModel(modelState, spriteGetter, overrides);
        }
    }
}
