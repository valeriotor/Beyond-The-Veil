package com.valeriotor.beyondtheveil.client.model.baked;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.IQuadTransformer;
import net.minecraftforge.client.model.QuadTransformers;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

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
    private final BakedModel heldForceps;
    private final ItemTransform thirdperson_righthand = new ItemTransform(new Vector3f(0, -0, 55), new Vector3f(-0.25F, 0.12F, -0.25F), new Vector3f(0.85F, 0.85F, 0.85F));
    private final ItemTransform thirdperson_lefthand = new ItemTransform(new Vector3f(0, 0, 55), new Vector3f(-0.25F, 0.12F, -0.25F), new Vector3f(0.85F, 0.85F, 0.85F));
    private final ItemTransform firstperson_righthand = new ItemTransform(new Vector3f(50, -0, -25), new Vector3f(0, 0.4F, 0), new Vector3f(1, 1, 1));
    private final ItemTransform firstperson_lefthand = new ItemTransform(new Vector3f(50, 0, -25), new Vector3f(0, 0.4F, 0), new Vector3f(1, 1, 1));
    private final ItemTransforms TRANSFORMS;

    public ForcepsBakedModel(ModelState modelState, Function<Material, TextureAtlasSprite> spriteGetter, ItemOverrides overrides, ItemStack contained, BakedModel baseForceps, BakedModel heldForceps) {
        this.modelState = modelState;
        this.spriteGetter = spriteGetter;
        this.overrides = overrides;
        this.contained = contained;
        this.baseForceps = baseForceps;
        this.heldForceps = heldForceps;
        TRANSFORMS = new ItemTransforms(thirdperson_lefthand, thirdperson_righthand, firstperson_lefthand, firstperson_righthand, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM);

    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        if (side != null) {
            return Collections.emptyList();
        }
        List<BakedQuad> quads = new ArrayList<>(heldForceps.getQuads(state, side, rand, extraData, renderType));
        if (!contained.isEmpty()) {
            BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(contained, Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
            List<BakedQuad> modelQuads = model.getQuads(state, null, rand, ModelData.EMPTY, RenderType.solid());
            Transformation translation = new Transformation(new Matrix4f().translate(0.42F, 0.075F, 0.1F).scale(0.15F));
            IQuadTransformer transformer = QuadTransformers.applying(translation);
            for (BakedQuad quad : modelQuads) {
                quads.add(transformer.process(quad));
            }
            //quads.addAll(modelQuads);
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
                return new ForcepsBakedModel(modelState, spriteGetter, overrides, ItemStack.of(tag.getCompound("contained")), baseForceps, heldForceps);
            }
            return model;
        }
    };


    @Override
    public ItemOverrides getOverrides() {
        return itemOverrides;
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        if (transformType == ItemDisplayContext.GUI) {
            return baseForceps;
        }
        return IDynamicBakedModel.super.applyTransform(transformType, poseStack, applyLeftHandTransform);
    }

    @Override
    public ItemTransforms getTransforms() {
        return TRANSFORMS;
    }
}
