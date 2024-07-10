package com.valeriotor.beyondtheveil.client.model.baked;

import com.mojang.math.Transformation;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.IQuadTransformer;
import net.minecraftforge.client.model.QuadTransformers;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class FlaskShelfBakedModel implements IDynamicBakedModel {

    private final ModelState modelState;
    private final Function<Material, TextureAtlasSprite> spriteGetter;
    private final ItemOverrides overrides;

    public FlaskShelfBakedModel(ModelState modelState, Function<Material, TextureAtlasSprite> spriteGetter, ItemOverrides overrides) {
        this.modelState = modelState;
        this.spriteGetter = spriteGetter;
        this.overrides = overrides;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        if (side == null) {
            if (renderType == RenderType.translucent()) {
                BlockPos pos = extraData.get(FlaskShelfBE.POS_PROPERTY);
                List<BakedQuad> quads = new ArrayList<>();
                List<FlaskShelfBE.Flask> flasks = extraData.get(FlaskShelfBE.FLASK_PROPERTY);
                for (FlaskShelfBE.Flask flask : flasks) {
                    quads.addAll(getQuadsForFlask(state, flask, rand, pos));
                }
                return quads;
            }
        }
        return Collections.emptyList();
    }

    private List<BakedQuad> getQuadsForFlask(BlockState shelfState, FlaskShelfBE.Flask flask, RandomSource rand, BlockPos pos) {
        List<BakedQuad> quads = new ArrayList<>();
        BlockState state = FlaskBlock.sizeToBlock.get(flask.getSize()).defaultBlockState();
        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(state);
        //Transformation rotation = modelState.getRotation();
        Transformation translation = new Transformation(new Matrix4f().translate((float) (flask.getX() - pos.getX() - 0.5), (float) flask.getY() - pos.getY(), (float) (flask.getZ() - pos.getZ() - 0.5)));
        IQuadTransformer transformer = QuadTransformers.applying(translation);
        List<BakedQuad> modelQuads = model.getQuads(state, null, rand, ModelData.EMPTY, RenderType.translucent());
        for (BakedQuad quad : modelQuads) {
            quads.add(transformer.process(quad));
        }
        return quads;
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
        return spriteGetter.apply(FlaskShelfModelLoader.MATERIAL_FLASK_SHELF);
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }
}
