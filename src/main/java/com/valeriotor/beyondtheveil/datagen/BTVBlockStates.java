package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.block.*;
import com.valeriotor.beyondtheveil.block.multiblock.FullMultiBlock;
import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock3by2;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.loaders.CompositeModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.IntFunction;

import static com.valeriotor.beyondtheveil.Registration.*;
import static net.minecraft.data.models.model.ModelLocationUtils.getModelLocation;

public class BTVBlockStates extends BlockStateProvider {

    public BTVBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, References.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(DAMP_WOOD.get());
        simpleBlock(DARK_SAND.get());
        stairsBlock(DAMP_WOOD_STAIRS.get(), modLoc("block/" + DAMP_WOOD.getId().getPath()));
        fenceBlock(DAMP_WOOD_FENCE.get(), modLoc("block/" + DAMP_WOOD.getId().getPath()));
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
        simpleBlock(BLOOD_BRICK.get());
        registerVeinStoneVariants();
        slabBlock(BLOOD_BRICK_SLAB.get(), getModelLocation(BLOOD_BRICK.get()), modLoc("block/" + BLOOD_BRICK.getId().getPath()));
        stairsBlock(BLOOD_BRICK_STAIRS.get(), modLoc("block/" + BLOOD_BRICK.getId().getPath()));
        simpleBlock(BLOOD_SMOOTH_STONE.get());
        registerSmoothStoneSlab(BLOOD_SMOOTH_STONE_SLAB.get(), modLoc("block/" + BLOOD_SMOOTH_STONE_SLAB.getId().getPath() + "_side"), modLoc("block/" + BLOOD_SMOOTH_STONE.getId().getPath()));
        simpleBlock(HEART.get(), new ExistingModelFile(modLoc("block/heart"), models().existingFileHelper));
        //simpleBlock(SACRIFICE_ALTAR.get(), new ExistingModelFile(modLoc("block/sacrifice_altar"), models().existingFileHelper));
        simpleBlock(BLACK_KELP.get(), new ExistingModelFile(modLoc("block/black_kelp"), models().existingFileHelper));
        simpleBlock(BLACK_KELP_PLANT.get(), new ExistingModelFile(modLoc("block/black_kelp_plant"), models().existingFileHelper));
        simpleBlock(DARK_GLASS.get());
        simpleBlock(ARENA.get());
        simpleBlock(DEEP_CHEST.get(), new ExistingModelFile(modLoc("block/deep_chest"), models().existingFileHelper));

        simpleBlock(SEDATIVE_BLOCK.get(), new ExistingModelFile(mcLoc("block/water"), models().existingFileHelper));
        simpleBlock(SOFTENER_BLOCK.get(), new ExistingModelFile(mcLoc("block/water"), models().existingFileHelper));
        simpleBlock(COAGULANT_BLOCK.get(), new ExistingModelFile(mcLoc("block/water"), models().existingFileHelper));
        //simpleBlock(FLASK_LARGE.get(), new ExistingModelFile(modLoc("block/flask_large"), models().existingFileHelper));
        //simpleBlock(FLASK_MEDIUM.get(), new ExistingModelFile(modLoc("block/flask_medium"), models().existingFileHelper));
        //simpleBlock(FLASK_SMALL.get(), new ExistingModelFile(modLoc("block/flask_small"), models().existingFileHelper));
        //horizontalBlock(FLASK_SHELF.get(), new ExistingModelFile(modLoc("block/flask_shelf"), models().existingFileHelper));


        registerCanopy();
        registerFumeSpreader();
        registerSleepChamber();
        registerWateryCradle();
        registerPatientPod();
        registerSolidAndTranslucentBlock("memory_sieve", mcLoc("block/stone"), MEMORY_SIEVE.get());
        registerFlaskShelf();
        registerFlasks();
        registerThinMultiBlock("surgery_bed", "flask_shelf_empty", SURGERY_BED.get()); // TODO change empty thing to match texture
        registerFullMultiBlock("sacrifice_altar", "flask_shelf_empty", SACRIFICE_ALTAR.get()); // TODO change empty thing to match texture
    }

    private void registerSmoothStoneSlab(SlabBlock block, ResourceLocation side, ResourceLocation top) {
        slabBlock(block, models().slab(ForgeRegistries.BLOCKS.getKey(block).getPath(), side, top, top), models().slabTop(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_top", side, top, top), models().orientable(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_double", side, side, top));
    }

    private void registerVeinStoneVariants() {
        IntFunction<ConfiguredModel> func = i -> new ConfiguredModel(models().cubeAll("vein_stone" + i, modLoc("block/vein_stone" + i)));
        simpleBlock(VEIN_STONE.get(), func.apply(1), func.apply(2), func.apply(3), func.apply(4), func.apply(5));
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
                        case INNER_RIGHT -> (((int) state.getValue(DampCanopyBlock.FACING).toYRot()) + 90);
                        case OUTER_LEFT -> (((int) state.getValue(DampCanopyBlock.FACING).toYRot()));
                        case OUTER_RIGHT -> (((int) state.getValue(DampCanopyBlock.FACING).toYRot()) + 90);
                    };
                    return ConfiguredModel.builder()
                            .modelFile(file) // Can show 'modelFile'
                            .rotationY(rotation) // Rotates 'modelFile' on the Y axis depending on the property
                            .build();
                }, DampCanopyBlock.WATERLOGGED);

        ExistingModelFile damp_filled_canopy = new ExistingModelFile(modLoc("block/damp_filled_canopy"), models().existingFileHelper);
        ExistingModelFile damp_filled_canopy_outer = new ExistingModelFile(modLoc("block/damp_filled_canopy_outer"), models().existingFileHelper);
        ExistingModelFile damp_filled_canopy_inner = new ExistingModelFile(modLoc("block/damp_filled_canopy_inner"), models().existingFileHelper);

        getVariantBuilder(DAMP_FILLED_CANOPY.get())
                .forAllStatesExcept(state -> {
                    ModelFile file = null;
                    file = switch (state.getValue(DampFilledCanopyBlock.SHAPE)) {
                        case STRAIGHT -> damp_filled_canopy;
                        case INNER_LEFT -> damp_filled_canopy_inner;
                        case INNER_RIGHT -> damp_filled_canopy_inner;
                        case OUTER_LEFT -> damp_filled_canopy_outer;
                        case OUTER_RIGHT -> damp_filled_canopy_outer;
                    };

                    int rotation = switch (state.getValue(DampFilledCanopyBlock.SHAPE)) {
                        case STRAIGHT -> (int) state.getValue(DampFilledCanopyBlock.FACING).toYRot();
                        case INNER_LEFT -> ((int) state.getValue(DampFilledCanopyBlock.FACING).toYRot());
                        case INNER_RIGHT -> (((int) state.getValue(DampFilledCanopyBlock.FACING).toYRot()) + 90);
                        case OUTER_LEFT -> (((int) state.getValue(DampFilledCanopyBlock.FACING).toYRot()));
                        case OUTER_RIGHT -> (((int) state.getValue(DampFilledCanopyBlock.FACING).toYRot()) + 90);
                    };
                    return ConfiguredModel.builder()
                            .modelFile(file) // Can show 'modelFile'
                            .rotationY(rotation) // Rotates 'modelFile' on the Y axis depending on the property
                            .build();
                }, DampFilledCanopyBlock.WATERLOGGED);
    }

    private void registerFumeSpreader() {
        ExistingModelFile fume_spreader = new ExistingModelFile(modLoc("block/fume_spreader"), models().existingFileHelper);
        ExistingModelFile incense_filling = new ExistingModelFile(modLoc("block/fume_spreader_incense"), models().existingFileHelper);
        //MultiPartBlockStateBuilder builder = getMultipartBuilder(FUME_SPREADER.get());

        BlockModelBuilder parent = new BlockModelBuilder(modLoc("block/fume_spreader1"), models().existingFileHelper);
        parent.parent(fume_spreader);
        parent.renderType("translucent");
        BlockModelBuilder incense_parent = new BlockModelBuilder(modLoc("block/fume_spreader_incense1"), models().existingFileHelper);
        incense_parent.parent(incense_filling);
        incense_parent.renderType("solid");

        //builder.part().modelFile(incense_filling).addModel().condition(FumeSpreaderBlock.FULL, true);
        //builder.part().modelFile(fume_spreader).addModel();
        BlockModelBuilder fumeSpreaderFullModel = models().getBuilder("beyondtheveil:block/fume_spreader_full")
                .parent(models().getExistingFile(mcLoc("cube")))
                .texture("particle", modLoc("block/fume_spreader"))
                .customLoader((blockModelBuilder, helper) -> CompositeModelBuilder.begin(blockModelBuilder, models().existingFileHelper)
                        .child("block/fume_spreader1", incense_parent)
                        .child("block/fume_spreader_incense1", parent))
                .end();

        BlockModelBuilder fumeSpreaderEmptyModel = models().getBuilder("beyondtheveil:block/fume_spreader_empty")
                .parent(models().getExistingFile(mcLoc("cube")))
                .texture("particle", modLoc("block/fume_spreader"))
                .customLoader((blockModelBuilder, helper) -> CompositeModelBuilder.begin(blockModelBuilder, models().existingFileHelper)
                        .child("block/fume_spreader1", parent))
                .end();

        getVariantBuilder(FUME_SPREADER.get())
                .forAllStates(state -> {
                    if (state.getValue(FumeSpreaderBlock.FULL))
                        return ConfiguredModel.builder().modelFile(fumeSpreaderFullModel).build();
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
                return ConfiguredModel.builder().modelFile(file).rotationY((int) state.getValue(SleepChamberBlock.FACING).toYRot()).build();
            }
            ModelFile file = state.getValue(SleepChamberBlock.OPEN) ? sleep_chamber_lower_open : sleep_chamber_lower;
            return ConfiguredModel.builder().modelFile(file).rotationY((int) state.getValue(SleepChamberBlock.FACING).toYRot()).build();
        });

    }

    private void registerWateryCradle() {
        ExistingModelFile empty = new ExistingModelFile(modLoc("block/" + "flask_shelf_empty"), models().existingFileHelper);
        ExistingModelFile wateryCradle_solid = new ExistingModelFile(modLoc("block/watery_cradle_solid"), models().existingFileHelper);
        ExistingModelFile wateryCradle_translucent = new ExistingModelFile(modLoc("block/watery_cradle_translucent"), models().existingFileHelper);

        BlockModelBuilder parent = new BlockModelBuilder(modLoc("block/watery_cradle_solid1"), models().existingFileHelper);
        parent.parent(wateryCradle_solid);
        parent.renderType("solid");
        BlockModelBuilder translucent_parent = new BlockModelBuilder(modLoc("block/watery_cradle_translucent1"), models().existingFileHelper);
        translucent_parent.parent(wateryCradle_translucent);
        translucent_parent.renderType("translucent");

        BlockModelBuilder wateryCradle = models().getBuilder("beyondtheveil:block/watery_cradle")
                .parent(models().getExistingFile(mcLoc("cube")))
                .texture("particle", modLoc("block/watery_cradle"))
                .customLoader((blockModelBuilder, helper) -> CompositeModelBuilder.begin(blockModelBuilder, models().existingFileHelper)
                        .child("block/watery_cradle_solid1", parent))
                .child("block/watery_cradle_translucent1", translucent_parent)
                .end();

        getVariantBuilder(WATERY_CRADLE.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(state.getValue(WATERY_CRADLE.get().getSideProperty()) == 1 ? wateryCradle : empty)
                        .rotationY(((int) (state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 90) % 360)) // Rotates 'modelFile' on the Y axis depending on the property
                        .build());
    }

    private void registerPatientPod() {
        ExistingModelFile empty = new ExistingModelFile(modLoc("block/" + "flask_shelf_empty"), models().existingFileHelper);
        ExistingModelFile patientPod_solid = new ExistingModelFile(modLoc("block/patient_pod_solid"), models().existingFileHelper);
        ExistingModelFile patientPod_translucent = new ExistingModelFile(modLoc("block/patient_pod_translucent"), models().existingFileHelper);

        BlockModelBuilder parent = new BlockModelBuilder(modLoc("block/patient_pod_solid1"), models().existingFileHelper);
        parent.parent(patientPod_solid);
        parent.renderType("solid");
        BlockModelBuilder translucent_parent = new BlockModelBuilder(modLoc("block/patient_pod_translucent1"), models().existingFileHelper);
        translucent_parent.parent(patientPod_translucent);
        translucent_parent.renderType("translucent");

        BlockModelBuilder patientPod = models().getBuilder("beyondtheveil:block/patient_pod")
                .parent(models().getExistingFile(mcLoc("cube")))
                .texture("particle", modLoc("block/patient_pod"))
                .customLoader((blockModelBuilder, helper) -> CompositeModelBuilder.begin(blockModelBuilder, models().existingFileHelper)
                        .child("block/patient_pod_solid1", parent))
                .child("block/patient_pod_translucent1", translucent_parent)
                .end();

        getVariantBuilder(PATIENT_POD.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(state.getValue(PATIENT_POD.get().getLevelProperty()) == 0 ? patientPod : empty)
                        .rotationY(((int) (state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()) % 360)) // Rotates 'modelFile' on the Y axis depending on the property
                        .build());
    }

    private void registerSolidAndTranslucentBlock(String name, ResourceLocation particleTexture, Block block) {
        ExistingModelFile solid = new ExistingModelFile(modLoc("block/" + name + "_solid"), models().existingFileHelper);
        ExistingModelFile translucent = new ExistingModelFile(modLoc("block/" + name + "_translucent"), models().existingFileHelper);

        BlockModelBuilder parent = new BlockModelBuilder(modLoc("block/" + name + "_solid1"), models().existingFileHelper);
        parent.parent(solid);
        parent.renderType("solid");
        BlockModelBuilder translucent_parent = new BlockModelBuilder(modLoc("block/" + name + "_translucent1"), models().existingFileHelper);
        translucent_parent.parent(translucent);
        translucent_parent.renderType("translucent");

        BlockModelBuilder builder = models().getBuilder("beyondtheveil:block/" + name)
                .parent(models().getExistingFile(mcLoc("cube")))
                .texture("particle", particleTexture)
                .customLoader((blockModelBuilder, helper) -> CompositeModelBuilder.begin(blockModelBuilder, models().existingFileHelper)
                        .child("block/" + name + "_solid1", parent))
                .child("block/" + name + "_translucent1", translucent_parent)
                .end();

        getVariantBuilder(block)
                .forAllStates(state -> {
                    return ConfiguredModel.builder().modelFile(builder).build();
                });
    }

    private void registerFlaskShelf() {
        ExistingModelFile empty = new ExistingModelFile(modLoc("block/flask_shelf_empty"), models().existingFileHelper);
        ExistingModelFile base = new ExistingModelFile(modLoc("block/flask_shelf"), models().existingFileHelper);
        ExistingModelFile loader = new ExistingModelFile(modLoc("block/flask_shelf_to_bake"), models().existingFileHelper);
        BlockModelBuilder baseBuilder = new BlockModelBuilder(modLoc("block/flask_shelf1"), models().existingFileHelper);
        baseBuilder.parent(base);
        baseBuilder.renderType("solid");
        BlockModelBuilder loaderBuilder = new BlockModelBuilder(modLoc("block/flask_shelf_to_bake1"), models().existingFileHelper);
        loaderBuilder.parent(loader);
        loaderBuilder.renderType("translucent");

        BlockModelBuilder builder = models().getBuilder("beyondtheveil:block/flask_shelf_complete")
                .parent(models().getExistingFile(mcLoc("cube")))
                .texture("particle", modLoc("block/flask_shelf"))
                .customLoader((blockModelBuilder, helper) -> CompositeModelBuilder.begin(blockModelBuilder, models().existingFileHelper)
                        .child("base", baseBuilder).child("loader", loaderBuilder))
                .end();

        getVariantBuilder(FLASK_SHELF.get())
                .forAllStatesExcept(state -> {
                    ModelFile file = state.getValue(FlaskShelfBlock.SIDE) == 1 && state.getValue(FlaskShelfBlock.LEVEL) == 1 ? builder : empty;
                    return ConfiguredModel.builder()
                            .modelFile(file) // Can show 'modelFile'
                            .rotationY(((int) (state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)) // Rotates 'modelFile' on the Y axis depending on the property
                            .build();
                });
    }

    private void registerFlasks() {
        registerFlask("large", FLASK_LARGE.get());
        registerFlask("medium", FLASK_MEDIUM.get());
        registerFlask("small", FLASK_SMALL.get());
        registerFlask("item", FLASK_ITEM.get());
    }

    private void registerFlask(String name, Block block) {
        ExistingModelFile base = new ExistingModelFile(modLoc("block/flask_" + name), models().existingFileHelper);
        ExistingModelFile wrong = new ExistingModelFile(modLoc("block/flask_" + name + "_wrong"), models().existingFileHelper);
        ExistingModelFile selected = new ExistingModelFile(modLoc("block/flask_" + name + "_selected"), models().existingFileHelper);
        getVariantBuilder(block).forAllStates(state -> {
            ModelFile file = switch (state.getValue(FlaskBlock.COLOR)) {
                case 0 -> base;
                case 1 -> wrong;
                case 2 -> selected;
                default -> throw new IllegalStateException("Unexpected value: " + state.getValue(FlaskBlock.COLOR));
            };
            return ConfiguredModel.builder().modelFile(file).build();
        });
    }

    /**
     * The empty model is necessary for particles
     */
    private void registerThinMultiBlock(String modelName, String emptyModelName, ThinMultiBlock3by2 block) {
        ExistingModelFile empty = new ExistingModelFile(modLoc("block/" + emptyModelName), models().existingFileHelper);
        ExistingModelFile base = new ExistingModelFile(modLoc("block/" + modelName), models().existingFileHelper);
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    ModelFile file = state.getValue(block.getSideProperty()) == block.getHorizontalRadius() && state.getValue(block.getLevelProperty()) == block.getCenterY() ? base : empty;
                    if (file == empty) {

                    }
                    return ConfiguredModel.builder()
                            .modelFile(file) // Can show 'modelFile'
                            .rotationY(((int) (state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 90) % 360)) // Rotates 'modelFile' on the Y axis depending on the property
                            .build();
                });
    }

    private void registerFullMultiBlock(String modelName, String emptyModelName, FullMultiBlock block) {
        ExistingModelFile empty = new ExistingModelFile(modLoc("block/" + emptyModelName), models().existingFileHelper);
        ExistingModelFile base = new ExistingModelFile(modLoc("block/" + modelName), models().existingFileHelper);
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    ModelFile file = block.getSideValue(state) == block.getHorizontalRadius() && block.getLevelValue(state) == block.getCenterY() && block.getDepthValue(state) == 0 ? base : empty;
                    if (file == empty) {

                    }
                    return ConfiguredModel.builder()
                            .modelFile(file) // Can show 'modelFile'
                            .rotationY(((int) (state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)) // Rotates 'modelFile' on the Y axis depending on the property
                            .build();
                });
    }

}
