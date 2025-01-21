package com.valeriotor.beyondtheveil.client.model.baked;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.math.Transformation;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class ForcepsModelLoader implements IGeometryLoader<ForcepsModelLoader.ForcepsModelGeometry> {

    public static final ResourceLocation FORCEPS_LOADER = new ResourceLocation(References.MODID, "forceps_loader");

    public static final ResourceLocation FORCEPS = new ResourceLocation(References.MODID, "item/forceps");


    @Override
    public ForcepsModelLoader.ForcepsModelGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        return new ForcepsModelLoader.ForcepsModelGeometry();
    }

    public static class ForcepsModelGeometry implements IUnbakedGeometry<ForcepsModelLoader.ForcepsModelGeometry> {

        ItemTransform thirdperson_righthand = new ItemTransform(new Vector3f(0, -90, 55), new Vector3f(0F, 4.0F, 0.5F), new Vector3f(0.85F, 0.85F, 0.85F));
        ItemTransform thirdperson_lefthand = new ItemTransform(new Vector3f(0, 90, -55), new Vector3f(0F, 4.0F, 0.5F), new Vector3f(0.85F, 0.85F, 0.85F));
        ItemTransform firstperson_righthand = new ItemTransform(new Vector3f(0, -90, 25), new Vector3f(1.13F, 3.2F, 1.13F), new Vector3f(0.68F, 0.68F, 0.68F));
        ItemTransform firstperson_lefthand = new ItemTransform(new Vector3f(0, 90, -25), new Vector3f(1.13F, 3.2F, 1.13F), new Vector3f(0.68F, 0.68F, 0.68F));

        private final BlockModel baseForcepsModel = new BlockModel(new ResourceLocation(References.MODID, "item/forceps_base"), new ArrayList<>(), new HashMap<>(), false, null, new ItemTransforms(thirdperson_lefthand, thirdperson_righthand, firstperson_lefthand, firstperson_righthand, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM), new ArrayList<>());
        private final BlockModel heldForcepsModel = new BlockModel(new ResourceLocation(References.MODID, "item/forceps_held"), new ArrayList<>(), new HashMap<>(), false, null, new ItemTransforms(thirdperson_lefthand, thirdperson_righthand, firstperson_lefthand, firstperson_righthand, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM), new ArrayList<>());


        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            //BakedModel baseForceps = baseForcepsModel.bake(baker, baseForcepsModel, spriteGetter, modelState, modelLocation, true);
            BakedModel bakeBase = baker.bake(new ResourceLocation(References.MODID, "item/forceps_base"), modelState, spriteGetter);
            BakedModel bakeHeld = baker.bake(new ResourceLocation(References.MODID, "item/forceps_held"), modelState, spriteGetter);
            return new ForcepsBakedModel(modelState, spriteGetter, overrides, ItemStack.EMPTY, bakeBase, bakeHeld);
        }

        @Override
        public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
            baseForcepsModel.resolveParents(modelGetter);
        }
    }

}
