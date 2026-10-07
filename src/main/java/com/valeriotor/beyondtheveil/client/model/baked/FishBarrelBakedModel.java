package com.valeriotor.beyondtheveil.client.model.baked;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.tile.FishBarrelBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.valeriotor.beyondtheveil.client.model.baked.BakedModelHelper.v;
import static com.valeriotor.beyondtheveil.client.model.baked.FishBarrelModelLoader.MATERIAL_FISH_BARREL;

public class FishBarrelBakedModel implements IDynamicBakedModel {

    private final Function<Material, TextureAtlasSprite> spriteGetter;
    private final ItemOverrides overrides;

    public FishBarrelBakedModel(ModelState modelState, Function<Material, TextureAtlasSprite> spriteGetter, ItemOverrides overrides) {
        //this.modelState = modelState;
        this.spriteGetter = spriteGetter;
        this.overrides = overrides;
    }


    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        if (side == Direction.UP) {
            if (renderType == RenderType.cutout()) {
                ItemStack stack = extraData.get(FishBarrelBE.STACK_PROPERTY);
                if (stack != null && !stack.isEmpty()) {
                    return getBaseQuad(stack);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<BakedQuad> getBaseQuad(ItemStack stack) {
        ResourceLocation material;
        Item i = stack.getItem();
        if (i == Items.COD) {
            material = FishBarrelModelLoader.COD_BARREL;
        } else if (i == Items.COOKED_COD) {
            material = FishBarrelModelLoader.COOKED_COD_BARREL;
        } else if (i == Items.SALMON) {
            material = FishBarrelModelLoader.SALMON_BARREL;
        } else if (i == Items.COOKED_SALMON) {
            material = FishBarrelModelLoader.COOKED_SALMON_BARREL;
        } else if (i == Items.TROPICAL_FISH) {
            material = FishBarrelModelLoader.TROPICAL_FISH_BARREL;
        } else if (i == Items.PUFFERFISH) {
            material = FishBarrelModelLoader.PUFFERFISH_BARREL;
        } else if (i == Registration.SLUG.get()) {
            material = FishBarrelModelLoader.SLUG_BARREL;
        } else {
            material = FishBarrelModelLoader.COD_BARREL;
        }
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(material);
        if (sprite == null) {
            return List.of();
        }
        double height = 0.0625 + 0.675 * stack.getCount() / 64;
        return List.of(BakedModelHelper.quad(v(0, height, 0), v(0, height, 1), v(1, height, 1), v(1, height, 0), sprite));
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
        return spriteGetter.apply(MATERIAL_FISH_BARREL);
    }

    @Override
    public ItemOverrides getOverrides() {
        return null;
    }
}
