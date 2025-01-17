package com.valeriotor.beyondtheveil.client.model.baked;

import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.IQuadTransformer;
import net.minecraftforge.client.model.QuadTransformers;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ForcepsBakedModel implements IDynamicBakedModel {

    private final ModelState modelState;
    private final Function<Material, TextureAtlasSprite> spriteGetter;
    private final ItemOverrides overrides;
    private final ItemStack contained;
    private final BakedModel baseForceps;

    public ForcepsBakedModel(ModelState modelState, Function<Material, TextureAtlasSprite> spriteGetter, ItemOverrides overrides, ItemStack contained, BakedModel baseForceps) {
        this.modelState = modelState;
        this.spriteGetter = spriteGetter;
        this.overrides = overrides;
        this.contained = contained;
        this.baseForceps = baseForceps;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        if (side != null) {
            return Collections.emptyList();
        }
        List<BakedQuad> quads = new ArrayList<>(baseForceps.getQuads(state, side, rand, extraData, renderType));
        if (!contained.isEmpty()) {
            BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(contained, Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
            List<BakedQuad> modelQuads = model.getQuads(state, null, rand, ModelData.EMPTY, RenderType.solid());
            //Transformation translation = new Transformation(new Matrix4f().translate((float) (x), (float) y, (float) (z)));
            //IQuadTransformer transformer = QuadTransformers.applying(translation);
            //for (BakedQuad quad : modelQuads) {
            //    quads.add(transformer.process(quad));
            //}
            quads.addAll(modelQuads);
            return quads;
        }

        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
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
        return null;
    }


    private final ItemOverrides itemOverrides = new ItemOverrides() {
        @NotNull
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel worldIn, @Nullable LivingEntity entityIn, int seed) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("contained")) {
                return new ForcepsBakedModel(modelState, spriteGetter, overrides, ItemStack.of(tag.getCompound("contained")), baseForceps);
            }
            return model;
        }
    };


    @Override
    public ItemOverrides getOverrides() {
        return itemOverrides;
    }
}
