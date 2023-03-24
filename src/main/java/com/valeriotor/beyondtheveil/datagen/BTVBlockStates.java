package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.DampCanopyBlock;
import com.valeriotor.beyondtheveil.block.FumeSpreaderBlock;
import com.valeriotor.beyondtheveil.block.SleepChamberBlock;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.loaders.MultiLayerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.valeriotor.beyondtheveil.Registration.*;
import static net.minecraft.data.models.model.ModelLocationUtils.getModelLocation;

public class BTVBlockStates extends BlockStateProvider {

    public BTVBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, References.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(DAMP_WOOD.get());
        simpleBlock(DARK_SAND.get());
        stairsBlock(DAMP_WOOD_STAIRS.get(), modLoc("block/" + DAMP_WOOD.getId().getPath()));
        fenceBlock(DAMP_WOOD_FENCE.get(), modLoc("block/" + DAMP_WOOD.getId().getPath()));
        simpleBlock(DAMP_FILLED_CANOPY.get());
        logBlock(DAMP_LOG.get());
        simpleBlock(WORN_BRICKS.get());
        horizontalBlock(IDOL.get(), new ExistingModelFile(modLoc("block/idol"), models().existingFileHelper));
        simpleBlock(FISH_BARREL.get(), new ExistingModelFile(modLoc("block/fish_barrel"), models().existingFileHelper));
        simpleBlock(SLUG_BAIT.get(), new ExistingModelFile(modLoc("block/slug_bait"), models().existingFileHelper));
        simpleBlock(LAMP.get(), new ExistingModelFile(modLoc("block/lamp"), models().existingFileHelper));
        simpleBlock(BLUE_BRICKS.get());
        stairsBlock(WORN_BRICK_STAIRS.get(), modLoc("block/" + WORN_BRICKS.getId().getPath()));
        horizontalBlock(GEAR_BENCH.get(), new ExistingModelFile(modLoc("block/" + GEAR_BENCH.getId().getPath()), models().existingFileHelper));
        simpleBlock(ELDER_BRICK.get());
        slabBlock(ELDER_BRICK_SLAB.get(), getModelLocation(ELDER_BRICK.get()), modLoc("block/" + ELDER_BRICK.getId().getPath()));
        stairsBlock(ELDER_BRICK_STAIRS.get(), modLoc("block/" + ELDER_BRICK.getId().getPath()));
        simpleBlock(ELDER_STONE_BRICK.get());
        slabBlock(ELDER_STONE_BRICK_SLAB.get(), getModelLocation(ELDER_STONE_BRICK.get()), modLoc("block/" + ELDER_STONE_BRICK.getId().getPath()));
        stairsBlock(ELDER_STONE_BRICK_STAIRS.get(), modLoc("block/" + ELDER_STONE_BRICK.getId().getPath()));
        simpleBlock(ELDER_STONE_BRICK_CHISELED.get());
        simpleBlock(ELDER_SMOOTH_STONE.get());
        registerSmoothStoneSlab(ELDER_SMOOTH_STONE_SLAB.get(), modLoc("block/" + ELDER_SMOOTH_STONE_SLAB.getId().getPath() + "_side"), modLoc("block/" + ELDER_SMOOTH_STONE.getId().getPath()));


        registerCanopy();
        registerFumeSpreader();
        registerSleepChamber();
    }

    private void registerSmoothStoneSlab(SlabBlock block, ResourceLocation side, ResourceLocation top) {
        slabBlock(block, models().slab(block.getRegistryName().getPath(), side, top, top), models().slabTop(block.getRegistryName().getPath() + "_top", side, top, top), models().orientable(block.getRegistryName().getPath() + "_double", side, side, top));

    }

    private void registerCanopy() {
        ExistingModelFile damp_canopy = new ExistingModelFile(modLoc("block/damp_canopy"), models().existingFileHelper);
        ExistingModelFile damp_canopy_flat = new ExistingModelFile(modLoc("block/damp_canopy_flat"), models().existingFileHelper);
        ExistingModelFile damp_canopy_outer = new ExistingModelFile(modLoc("block/damp_canopy_outer"), models().existingFileHelper);
        ExistingModelFile damp_canopy_inner = new ExistingModelFile(modLoc("block/damp_canopy_inner"), models().existingFileHelper);

        getVariantBuilder(DAMP_CANOPY.get())
                .forAllStatesExcept(state -> {
                    ModelFile file = null;
                    if (state.getValue(DampCanopyBlock.FLAT)) {
                        file = damp_canopy_flat;
                    } else {
                        file = switch (state.getValue(DampCanopyBlock.SHAPE)) {
                            case STRAIGHT -> damp_canopy;
                            case INNER_LEFT -> damp_canopy_inner;
                            case INNER_RIGHT -> damp_canopy_inner;
                            case OUTER_LEFT -> damp_canopy_outer;
                            case OUTER_RIGHT -> damp_canopy_outer;
                        };
                    }
                    int rotation = switch (state.getValue(DampCanopyBlock.SHAPE)) {
                        case STRAIGHT -> (int) state.getValue(DampCanopyBlock.FACING).toYRot();
                        case INNER_LEFT -> ((int) state.getValue(DampCanopyBlock.FACING).toYRot());
                        case INNER_RIGHT -> (((int) state.getValue(DampCanopyBlock.FACING).toYRot())+90);
                        case OUTER_LEFT -> (((int) state.getValue(DampCanopyBlock.FACING).toYRot()));
                        case OUTER_RIGHT -> (((int) state.getValue(DampCanopyBlock.FACING).toYRot())+90);
                    };
                    return ConfiguredModel.builder()
                            .modelFile(file) // Can show 'modelFile'
                            .rotationY(rotation) // Rotates 'modelFile' on the Y axis depending on the property
                            .build();
                }, DampCanopyBlock.WATERLOGGED);
    }

    private void registerFumeSpreader() {
        ExistingModelFile fume_spreader = new ExistingModelFile(modLoc("block/fume_spreader"), models().existingFileHelper);
        ExistingModelFile incense_filling = new ExistingModelFile(modLoc("block/fume_spreader_incense"), models().existingFileHelper);
        //MultiPartBlockStateBuilder builder = getMultipartBuilder(FUME_SPREADER.get());

        BlockModelBuilder parent = new BlockModelBuilder(modLoc("block/fume_spreader1"), models().existingFileHelper);
        parent.parent(fume_spreader);
        BlockModelBuilder incense_parent = new BlockModelBuilder(modLoc("block/fume_spreader_incense1"), models().existingFileHelper);
        incense_parent.parent(incense_filling);

        //builder.part().modelFile(incense_filling).addModel().condition(FumeSpreaderBlock.FULL, true);
        //builder.part().modelFile(fume_spreader).addModel();
        BlockModelBuilder fumeSpreaderFullModel = models().getBuilder("beyondtheveil:block/fume_spreader_full")
                .parent(models().getExistingFile(mcLoc("cube")))
                .texture("particle", modLoc("block/fume_spreader"))
                .customLoader((blockModelBuilder, helper) -> MultiLayerModelBuilder.begin(blockModelBuilder, models().existingFileHelper)
                        .submodel(RenderType.solid(), incense_parent)
                        .submodel(RenderType.translucent(), parent))
                .end();

        BlockModelBuilder fumeSpreaderEmptyModel = models().getBuilder("beyondtheveil:block/fume_spreader_empty")
                .parent(models().getExistingFile(mcLoc("cube")))
                .texture("particle", modLoc("block/fume_spreader"))
                .customLoader((blockModelBuilder, helper) -> MultiLayerModelBuilder.begin(blockModelBuilder, models().existingFileHelper)
                        .submodel(RenderType.translucent(), parent))
                .end();

        getVariantBuilder(FUME_SPREADER.get())
                .forAllStates(state -> {
                    if (state.getValue(FumeSpreaderBlock.FULL)) return ConfiguredModel.builder().modelFile(fumeSpreaderFullModel).build();
                    else return ConfiguredModel.builder().modelFile(fumeSpreaderEmptyModel).build();
                });
    }

    private void registerSleepChamber() {
        ExistingModelFile sleep_chamber_lower = new ExistingModelFile(modLoc("block/sleep_chamber_lower"), models().existingFileHelper);
        ExistingModelFile sleep_chamber_lower_open = new ExistingModelFile(modLoc("block/sleep_chamber_lower_open"), models().existingFileHelper);
        ExistingModelFile sleep_chamber_upper = new ExistingModelFile(modLoc("block/sleep_chamber_upper"), models().existingFileHelper);
        ExistingModelFile sleep_chamber_upper_open = new ExistingModelFile(modLoc("block/sleep_chamber_upper_open"), models().existingFileHelper);

        getVariantBuilder(SLEEP_CHAMBER.get()).forAllStates(state -> {
            if (state.getValue(SleepChamberBlock.HALF) == DoubleBlockHalf.UPPER) {
                ModelFile file = state.getValue(SleepChamberBlock.OPEN) ? sleep_chamber_upper_open : sleep_chamber_upper;
                return ConfiguredModel.builder().modelFile(file).rotationY((int)state.getValue(SleepChamberBlock.FACING).toYRot()).build();
            }
            ModelFile file = state.getValue(SleepChamberBlock.OPEN) ? sleep_chamber_lower_open : sleep_chamber_lower;
            return ConfiguredModel.builder().modelFile(file).rotationY((int)state.getValue(SleepChamberBlock.FACING).toYRot()).build();
        });

    }

}
