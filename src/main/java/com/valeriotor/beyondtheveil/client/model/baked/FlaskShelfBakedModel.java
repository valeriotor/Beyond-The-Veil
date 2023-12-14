package com.valeriotor.beyondtheveil.client.model.baked;

import com.mojang.math.Transformation;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.model.loader.FlaskModelLoader;
import com.valeriotor.beyondtheveil.client.model.loader.FlaskShelfModelLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.QuadTransformer;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class FlaskShelfBakedModel implements IDynamicBakedModel {

    private final ModelState modelState;
    private final Function<Material, TextureAtlasSprite> spriteGetter;
    //private final Map<ModelKey, List<BakedQuad>> quadCache = new HashMap<>();
    private final ItemOverrides overrides;
    private final ItemTransforms itemTransforms;


    public FlaskShelfBakedModel(ModelState modelState, Function<Material, TextureAtlasSprite> spriteGetter, ItemOverrides overrides, ItemTransforms itemTransforms) {
        this.modelState = modelState;
        this.spriteGetter = spriteGetter;
        this.overrides = overrides;
        this.itemTransforms = itemTransforms;
        //generateQuadCache();
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
        var quads = getQuadsForFlasks(rand);
        return quads;
    }

    private List<BakedQuad> getQuadsForFlasks(Random rand) {

        var quads = new ArrayList<BakedQuad>();
        BlockState state = Registration.FLASK_LARGE.get().defaultBlockState();
        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(state);

        try {
            //Direction facing = state == null ? Direction.SOUTH : state.getValue(BlockStateProperties.FACING);
            Transformation rotation = modelState.getRotation();
            //Transformation translate = transformGeneratingBlock(facing, rotation);
            QuadTransformer transformer = new QuadTransformer(Transformation.identity());

            // Get the quads for every side, transform it and add it to the list of quads
            for (Direction s : Direction.values()) {
                List<BakedQuad> modelQuads = model.getQuads(state, s, rand, EmptyModelData.INSTANCE);
                for (BakedQuad quad : modelQuads) {
                    quads.add(transformer.processOne(quad));
                }
            }
        } catch (Exception e) {
            // In case a certain mod has a bug we don't want to cause everything to crash. Instead we log the problem
            //TutorialV3.LOGGER.log(Level.ERROR, "A block '" + generatingBlock.getBlock().getRegistryName().toString() + "' caused a crash!");
        }

        return quads;
        ////UnbakedModel model;
        ////ModelLoaderRegistry.getModel()
        //var x = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("beyondtheveil", "block/flask_large"));
        //var y = Minecraft.getInstance().getModelManager().getModel(FlaskModelLoader.FLASK_LOADER);
        //System.out.println(x);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return spriteGetter.apply(FlaskShelfModelLoader.FLASK_SHELF_MATERIAL);
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }
}
