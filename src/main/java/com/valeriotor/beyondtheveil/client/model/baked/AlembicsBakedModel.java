package com.valeriotor.beyondtheveil.client.model.baked;

import com.valeriotor.beyondtheveil.tile.AlembicsBE;
import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.valeriotor.beyondtheveil.client.model.baked.FlaskShelfBakedModel.getQuadsForFlask;

public class AlembicsBakedModel implements IDynamicBakedModel {

    private final ModelState modelState;
    private final Function<Material, TextureAtlasSprite> spriteGetter;
    private final ItemOverrides overrides;

    public AlembicsBakedModel(ModelState modelState, Function<Material, TextureAtlasSprite> spriteGetter, ItemOverrides overrides) {
        this.modelState = modelState;
        this.spriteGetter = spriteGetter;
        this.overrides = overrides;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        if (side == null) {
            if (renderType == RenderType.translucent()) {
                BlockPos pos = extraData.get(AlembicsBE.POS_PROPERTY);
                FlaskShelfBE.Flask flask = extraData.get(AlembicsBE.FLASK_PROPERTY);
                if (flask != null) {
                    return new ArrayList<>(getQuadsForFlask(state, flask, rand, pos, renderType));
                }
                return new ArrayList<>();
            }
        }
        return Collections.emptyList();
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
