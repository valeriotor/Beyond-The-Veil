package com.valeriotor.beyondtheveil.client.model.baked;

import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import com.valeriotor.beyondtheveil.tile.AlembicsBE;
import com.valeriotor.beyondtheveil.tile.FlaskBE;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.IQuadTransformer;
import net.minecraftforge.client.model.QuadTransformers;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.valeriotor.beyondtheveil.client.model.baked.FlaskShelfBakedModel.getQuadsForFlask;

public class ItemFlaskBakedModel implements IDynamicBakedModel {

    private final ModelState modelState;
    private final Function<Material, TextureAtlasSprite> spriteGetter;
    private final ItemOverrides overrides;

    public ItemFlaskBakedModel(ModelState modelState, Function<Material, TextureAtlasSprite> spriteGetter, ItemOverrides overrides) {
        this.modelState = modelState;
        this.spriteGetter = spriteGetter;
        this.overrides = overrides;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        if (side == null) {
            if (renderType == RenderType.translucent()) {
                ItemStack stack = extraData.get(FlaskBE.STACK_PROPERTY);
                if (stack != null && !stack.isEmpty()) {
                    return getQuadsFromStack(stack, state, rand);
                }
            }
        }
        return Collections.emptyList();
    }

    static List<BakedQuad> getQuadsFromStack(ItemStack stack, BlockState state, @NotNull RandomSource rand) {
        double[][] transforms = new double[][]{
                {0.427, 0.01, 0.68, 120},
                {0.53, 0.01, 0.38, -30},
                {0.35, 0.01, 0.46, 50},
                {0.62, 0.01, 0.62, 170},
                {0.34, 0.1, 0.43, 20},
                {0.55, 0.1, 0.36, -50},
                {0.48, 0.09, 0.65, 150},
                {0.62, 0.09, 0.5775, 190},
                {0.44, 0.2, 0.68, 120},
                {0.53, 0.2, 0.38, -30},
                {0.35, 0.2, 0.46, 50},
                {0.62, 0.2, 0.62, 170},
                {0.34, 0.3, 0.43, 20},
                {0.55, 0.3, 0.36, -50},
                {0.48, 0.29, 0.64, 150},
                {0.62, 0.29, 0.5775, 190},
        };
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0);
        List<BakedQuad> list = new ArrayList<>();
        for (int i = 0; i < stack.getCount() && i < transforms.length; i++) {
            Transformation translation = new Transformation(new Matrix4f().translate((float) transforms[i][0], (float) transforms[i][1], (float) transforms[i][2]).rotate(Axis.YP.rotationDegrees((float) transforms[i][3])).scale(0.15F));
            IQuadTransformer transformer = QuadTransformers.applying(translation);
            list.addAll(transformer.process(model.getQuads(state, null, rand)));
        }
        return list;
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
        return spriteGetter.apply(AlembicsModelLoader.MATERIAL_ALEMBICS);
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }
}
