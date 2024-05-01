package com.valeriotor.beyondtheveil.world.structures;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.material.FluidState;

import java.util.*;

public class HamletPieces {


    private record WeightedBuilding(String name, Weight weight, int width, int depth, int max,
                                    int doorX) implements WeightedEntry {
        @Override
        public Weight getWeight() {
            return this.weight;
        }
    }

    private static final WeightedRandomList<WeightedBuilding> buildingTypes;

    static {
        List<WeightedBuilding> list = new ArrayList<>();
        list.add(new WeightedBuilding("house1", Weight.of(10), 20, 16, 7, 4));
        list.add(new WeightedBuilding("house2", Weight.of(10), 18, 18, 7, 3));
        list.add(new WeightedBuilding("house_two_floors", Weight.of(10), 17, 17, 3, 3));
        list.add(new WeightedBuilding("hut", Weight.of(10), 15, 15, 7, 3));
        list.add(new WeightedBuilding("inn", Weight.of(5), 25, 21, 2, 12));
        list.add(new WeightedBuilding("lighthouse", Weight.of(1000), 25, 20, 1, 2));
        list.add(new WeightedBuilding("storehouse1", Weight.of(5), 25, 22, 4, 5));
        list.add(new WeightedBuilding("storehouse2", Weight.of(2), 19, 23, 2, 5));
        list.add(new WeightedBuilding("town_hall", Weight.of(10), 27, 20, 1, 9));

        //list.add(new WeightedBuilding("idol", Weight.of(10), 17, 17, 1));
        buildingTypes = WeightedRandomList.create(list);
    }

    public static class HamletBuildingPiece extends TemplateStructurePiece {
        public HamletBuildingPiece(StructureTemplateManager pStructureTemplateManager, WeightedBuilding building, Rotation rotation, BlockPos templatePosition) {
            super(Registration.HAMLET_BUILDING_PIECE.get(), 0, pStructureTemplateManager, makeResourceLocation(building.name), building.name, makeSettings(rotation), templatePosition);
        }

        public HamletBuildingPiece(StructureTemplateManager pStructureTemplateManager, String name, Rotation rotation, BlockPos templatePosition) {
            super(Registration.HAMLET_BUILDING_PIECE.get(), 0, pStructureTemplateManager, makeResourceLocation(name), name, makeSettings(rotation), templatePosition);
        }

        public HamletBuildingPiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(Registration.HAMLET_BUILDING_PIECE.get(), pTag, pStructureTemplateManager, (p_227512_) -> makeSettings(Rotation.valueOf(pTag.getString("Rot"))));//Rotation.valueOf(pTag.getString("Rot"))));
        }

        public HamletBuildingPiece(StructurePieceSerializationContext context, CompoundTag tag) {
            this(context.structureTemplateManager(), tag);
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
            super.addAdditionalSaveData(pContext, pTag);
            pTag.putString("Rot", getRotation().toString());
        }

        @Override
        protected ResourceLocation makeTemplateLocation() {
            return makeResourceLocation(templateName);
        }

        public static ResourceLocation makeResourceLocation(String pName) {
            return new ResourceLocation(References.MODID, "hamlet/" + pName);
        }

        private static StructurePlaceSettings makeSettings(Rotation pRotation) {
            //BlockIgnoreProcessor blockignoreprocessor = pOverwrite ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
            return (new StructurePlaceSettings()).setIgnoreEntities(false).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK).setRotation(pRotation); // TODO add dark sand to wood planks processor
        }
    }

    public static class StreetPiece extends StructurePiece {

        private final int[][] streetGrid;
        private final Rotation streetRotation;
        private final BlockPos centerPos;
        private final List<BlockPos> blocks;

        public StreetPiece(int[][] streetGrid, Rotation rotation, BlockPos centerPos, BoundingBox box, List<BlockPos> blocks) {
            super(Registration.HAMLET_STREET_PIECE.get(), 0, box);
            this.streetGrid = streetGrid;
            streetRotation = rotation;
            this.centerPos = centerPos;
            this.boundingBox = box;
            this.blocks = blocks;
        }

        public StreetPiece(CompoundTag pTag) {
            super(Registration.HAMLET_STREET_PIECE.get(), pTag);
            CompoundTag streetTag = pTag.getCompound("streetTag");
            streetGrid = new int[pTag.getInt("streetX")][pTag.getInt("streetZ")];
            for (String key : streetTag.getAllKeys()) {
                String[] s = key.split(" ");
                int i = Integer.parseInt(s[0]);
                int j = Integer.parseInt(s[1]);
                streetGrid[i][j] = streetTag.getInt(key);
            }
            if (pTag.contains("centerPos")) {
                centerPos = BlockPos.of(pTag.getLong("centerPos"));
            } else {
                centerPos = BlockPos.ZERO;
            }
            if (pTag.contains("streetRotation")) {
                streetRotation = Rotation.valueOf(pTag.getString("streetRotation"));
            } else {
                streetRotation = Rotation.NONE;
            }
            if (pTag.contains("blocks")) {
                blocks = new ArrayList<>();
                CompoundTag blockTag = pTag.getCompound("blocks");
                for (String key : blockTag.getAllKeys()) {
                    blocks.add(BlockPos.of(blockTag.getLong(key)));
                }
            } else {
                blocks = new ArrayList<>();
            }

        }

        public StreetPiece(StructurePieceSerializationContext context, CompoundTag tag) {
            this(tag);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
            CompoundTag streetTag = new CompoundTag();
            for (int i = 0; i < streetGrid.length; i++) {
                for (int j = 0; j < streetGrid[0].length; j++) {
                    streetTag.putInt(i + " " + j, streetGrid[i][j]);
                }
            }
            pTag.put("streetTag", streetTag);
            pTag.putInt("streetX", streetGrid.length);
            pTag.putInt("streetZ", streetGrid[0].length);
            pTag.putLong("centerPos", centerPos.asLong());
            pTag.putString("streetRotation", streetRotation.toString());

            CompoundTag blockTag = new CompoundTag();
            for (BlockPos block : blocks) {
                blockTag.putLong(block.toString(), block.asLong());
            }
            pTag.put("blocks", blockTag);


        }

        @Override
        public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
            //for (int z = 0; z < streetGrid.length; z++) {
            //    int[] row = streetGrid[z];
            //    for (int x = 0; x < row.length; x++) {
            //        int y = -61;// pGenerator.getFirstFreeHeight(x, z, Heightmap.Types.WORLD_SURFACE, pLevel, pRandom);
            //        if (streetGrid[z][x] > 0) {
            //            BlockPos pos = centerPos.offset(new BlockPos(x, 0, z).rotate(streetRotation));
            //            placeBlock(pLevel, Registration.DAMP_WOOD.get().defaultBlockState(), pos.getX(), pos.getY(), pos.getZ(), pBox);
            //        }
            //    }
            //}
            for (BlockPos blockPos : blocks) {
                BlockState state = pLevel.getBlockState(blockPos);
                BlockState outputState;
                if (state.getBlock() == Blocks.WATER || state.getBlock() == Registration.DAMP_WOOD.get()) {
                    outputState = Registration.DAMP_WOOD.get().defaultBlockState();
                } else {
                    outputState = Registration.BLUE_BRICKS.get().defaultBlockState();
                }
                placeBlock(pLevel, outputState, blockPos.getX(), blockPos.getY(), blockPos.getZ(), pBox);
            }
        }
    }


    private static final int MIN_BUILDINGS_PER_QUADRANT = 6;
    private static final int MAX_BUILDINGS_PER_QUADRANT = 10;
    private static final int QUADRANT_BUILDINGS_PER_AXIS = 4;
    private static final int QUADRANT_SQUARE_SIDE = 30;

    private static class BuildingOnGrid {
        private final WeightedBuilding type;
        private final Rotation rotation;
        private int x;
        private int z;
        private final int width;
        private final int depth;

        public BuildingOnGrid(WeightedBuilding type, Rotation rotation, int x, int z, int width, int depth) {
            this.type = type;
            this.rotation = rotation;
            this.x = x;
            this.z = z;
            this.width = width;
            this.depth = depth;
        }

        int getXOffset() {
            int width = rotation == Rotation.NONE || rotation == Rotation.CLOCKWISE_180 ? this.width : this.depth;
            return (width - (type.width - 8)) / 2;
        }

        int getZOffset() {
            int depth = rotation == Rotation.NONE || rotation == Rotation.CLOCKWISE_180 ? this.depth : this.width;
            return (depth - (type.depth - 8)) / 2;
        }
    }

    private record TempBuildingPiece(StructureTemplateManager pStructureTemplateManager, WeightedBuilding building,
                                     Rotation rotation, BlockPos templatePosition) {
        HamletBuildingPiece toHamletBuildingPiece(BlockPos centerPos, Rotation rotation) {
            Rotation finalRotation = this.rotation.getRotated(rotation);
            BlockPos finalTemplatePosition = centerPos.offset(templatePosition.rotate(rotation));
            return new HamletBuildingPiece(pStructureTemplateManager, building, finalRotation, finalTemplatePosition);
        }
    }


    public static List<StructurePiece> layout(Structure.GenerationContext pContext, RandomSource rand, StructureTemplateManager manager, Map<WeightedBuilding, Integer> numbersPerType, BlockPos centerPos) {
        List<WeightedBuilding> weightedBuildings = Lists.newArrayList(buildingTypes.unwrap());
        List<StructurePiece> allPieces = new ArrayList<>();
        for (Rotation rotation : Rotation.values()) {
            BlockPos offsetCenterPos = centerPos.offset(new BlockPos(0, 0, 0).rotate(rotation));
            layoutQuadrant(pContext, rand, manager, numbersPerType, weightedBuildings, offsetCenterPos, allPieces, rotation);
        }
        allPieces.add(new HamletBuildingPiece(manager, "idol", Rotation.NONE, centerPos.offset(-4, 0, -4)));
        //return layoutQuadrant(rand, manager, numbersPerType, weightedBuildings, centerPos);
        return allPieces;
    }


    private static void layoutQuadrant(Structure.GenerationContext context, RandomSource rand, StructureTemplateManager manager, Map<WeightedBuilding, Integer> numbersPerType, List<WeightedBuilding> allowedBuildingTypes, BlockPos centerPos, List<StructurePiece> returnList, Rotation rotation) {
        WeightedRandomList<WeightedBuilding> weightedAllowedBuildingTypes = WeightedRandomList.create(allowedBuildingTypes);
        BuildingOnGrid[][] quadrant = new BuildingOnGrid[QUADRANT_BUILDINGS_PER_AXIS][QUADRANT_BUILDINGS_PER_AXIS];
        int buildingsInQuadrant = rand.nextInt(MIN_BUILDINGS_PER_QUADRANT, MAX_BUILDINGS_PER_QUADRANT);
        int buildingsSoFar = 0;
        for (int manhattanDistance = 0; manhattanDistance < QUADRANT_BUILDINGS_PER_AXIS * 2 - 1 && buildingsSoFar < buildingsInQuadrant; manhattanDistance++) {
            for (int i = 0; i < manhattanDistance + 1 && buildingsSoFar < buildingsInQuadrant; i++) {
                int j = manhattanDistance - i;
                if (i >= QUADRANT_BUILDINGS_PER_AXIS || j >= QUADRANT_BUILDINGS_PER_AXIS) {
                    continue;
                }
                if (quadrant[i][j] == null) {
                    buildingsSoFar++;
                    Optional<WeightedBuilding> random = weightedAllowedBuildingTypes.getRandom(rand);
                    if (random.isPresent()) {
                        WeightedBuilding building = random.get();
                        Rotation rot = Rotation.getRandom(rand);
                        //rot = Rotation.NONE;
                        boolean parallel = rot == Rotation.NONE || rot == Rotation.CLOCKWISE_180;
                        quadrant[i][j] = new BuildingOnGrid(building, rot, i * QUADRANT_SQUARE_SIDE + 6, j * QUADRANT_SQUARE_SIDE + 6, (parallel ? building.width : building.depth) - rand.nextInt(4) - 2, (parallel ? building.depth : building.width) - rand.nextInt(4) - 2);
                        //quadrant[i][j] = new BuildingOnGrid(building, rot, i * QUADRANT_SQUARE_SIDE, j * QUADRANT_SQUARE_SIDE, 18, 13);
                        //quadrant[i][j] = new BuildingOnGrid(manager, building, Rotation.getRandom(rand), new BlockPos(i * QUADRANT_SQUARE_SIDE, 0, j * QUADRANT_SQUARE_SIDE));
                        numbersPerType.put(building, numbersPerType.getOrDefault(building, 0) + 1);
                        if (numbersPerType.get(building) >= building.max) {
                            allowedBuildingTypes.remove(building);
                            weightedAllowedBuildingTypes = WeightedRandomList.create(allowedBuildingTypes);
                        }
                    }
                }
            }
        }

        int[][] heightMapX = new int[QUADRANT_BUILDINGS_PER_AXIS * QUADRANT_SQUARE_SIDE][QUADRANT_BUILDINGS_PER_AXIS * QUADRANT_SQUARE_SIDE];
        int[][] heightMapZ = new int[QUADRANT_BUILDINGS_PER_AXIS * QUADRANT_SQUARE_SIDE][QUADRANT_BUILDINGS_PER_AXIS * QUADRANT_SQUARE_SIDE];

        for (int i = 0; i < heightMapX.length; i++) {
            for (int j = 0; j < heightMapX.length; j++) {
                heightMapX[j][i] = i < 7 ? 5 : 3;
                heightMapZ[j][i] = i < 7 ? 5 : 3;
            }
        }

        for (int manhattanDistance = 0; manhattanDistance < QUADRANT_BUILDINGS_PER_AXIS * 2 - 1; manhattanDistance++) {
            for (int i = 0; i < manhattanDistance + 1; i++) {
                int j = manhattanDistance - i;
                if (i >= QUADRANT_BUILDINGS_PER_AXIS || j >= QUADRANT_BUILDINGS_PER_AXIS) {
                    continue;
                }
                BuildingOnGrid piece = quadrant[i][j];
                if (piece != null) {
                    while (true) {
                        boolean flagX = false;
                        boolean flagZ = false;
                        int maxX = Arrays.stream(Arrays.copyOfRange(heightMapX[Math.max(0, piece.x - 1)], piece.z + 1, piece.z + piece.depth)).max().orElse(0);
                        if (maxX < piece.x) {
                            piece.x = Math.max(0, maxX - 1);
                            flagX = true;
                        }

                        int maxZ = Arrays.stream(Arrays.copyOfRange(heightMapZ[Math.max(0, piece.z - 1)], piece.x + 1, piece.x + piece.width)).max().orElse(0);
                        if (maxZ < piece.z) {
                            piece.z = Math.max(0, maxZ - 1);
                            flagZ = true;
                        }
                        if (!flagX && !flagZ) {
                            break;
                        }
                    }
                    for (int zz = piece.z; zz < piece.z + piece.depth + 1; zz++) {
                        for (int xx = piece.x; xx < heightMapX.length; xx++) {
                            heightMapX[xx][zz] = Math.max(heightMapX[xx][zz], piece.x + piece.width + 1);
                        }
                    }

                    for (int zz = piece.z; zz < heightMapX.length; zz++) {
                        for (int xx = piece.x; xx < piece.x + piece.width + 1; xx++) {
                            heightMapZ[zz][xx] = Math.max(heightMapZ[zz][xx], piece.z + piece.depth + 1);
                        }
                    }
                }
            }
        }
        int[][] grid = new int[QUADRANT_BUILDINGS_PER_AXIS * QUADRANT_SQUARE_SIDE][QUADRANT_BUILDINGS_PER_AXIS * QUADRANT_SQUARE_SIDE];
        for (BuildingOnGrid[] buildingsOnGrid : quadrant) {
            for (BuildingOnGrid buildingOnGrid : buildingsOnGrid) {
                if (buildingOnGrid != null) {
                    BlockPos offset = new BlockPos(buildingOnGrid.x + buildingOnGrid.getXOffset(), 0, buildingOnGrid.z + buildingOnGrid.getZOffset());
                    if (buildingOnGrid.rotation == Rotation.CLOCKWISE_180) {
                        offset = offset.offset(buildingOnGrid.type.width - 8, 0, buildingOnGrid.type.depth - 8);
                    } else if (buildingOnGrid.rotation == Rotation.COUNTERCLOCKWISE_90) {
                        offset = offset.offset(0, 0, buildingOnGrid.type.width - 8);
                    } else if (buildingOnGrid.rotation == Rotation.CLOCKWISE_90) {
                        offset = offset.offset(buildingOnGrid.type.depth - 8, 0, 0);
                    }

                    Rotation finalRotation = buildingOnGrid.rotation.getRotated(rotation);
                    BlockPos finalTemplatePosition = centerPos.offset(offset.rotate(rotation));
                    int firstFreeHeight = context.chunkGenerator().getFirstFreeHeight(finalTemplatePosition.getX(), finalTemplatePosition.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
                    returnList.add(new HamletBuildingPiece(manager, buildingOnGrid.type, finalRotation, finalTemplatePosition.atY(firstFreeHeight - 1)));

                    for (int dx = 0; dx < buildingOnGrid.width + 1; dx++) {
                        grid[buildingOnGrid.z][buildingOnGrid.x + dx] = 1;
                        grid[buildingOnGrid.z + buildingOnGrid.depth][buildingOnGrid.x + dx] = 1;
                    }

                    for (int dz = 0; dz < buildingOnGrid.depth + 1; dz++) {
                        grid[buildingOnGrid.z + dz][buildingOnGrid.x] = 1;
                        grid[buildingOnGrid.z + dz][buildingOnGrid.x + buildingOnGrid.width] = 1;
                    }

                    for (int dx = 1; dx < buildingOnGrid.width; dx++) {
                        for (int dz = 1; dz < buildingOnGrid.depth; dz++) {
                            grid[buildingOnGrid.z + dz][buildingOnGrid.x + dx] = 2;
                        }
                    }
                }
            }
        }
        //for (int[] ints : grid) {
        //    System.out.println(Arrays.toString(ints));
        //}
        //for (HamletBuildingPiece piece : pieces) {
        //    System.out.println(piece.templatePosition() + " " + piece.getRotation());
        //}
        int[][] streetGrid = new int[QUADRANT_BUILDINGS_PER_AXIS * QUADRANT_SQUARE_SIDE][QUADRANT_BUILDINGS_PER_AXIS * QUADRANT_SQUARE_SIDE];
        List<BlockPos> streetBlocks = new ArrayList<>();


        for (int manhattanDistance = 0; manhattanDistance < QUADRANT_BUILDINGS_PER_AXIS * 2 - 1; manhattanDistance++) {
            for (int i = 0; i < manhattanDistance + 1; i++) {
                int j = manhattanDistance - i;
                if (i >= QUADRANT_BUILDINGS_PER_AXIS || j >= QUADRANT_BUILDINGS_PER_AXIS) {
                    continue;
                }
                BuildingOnGrid piece = quadrant[i][j];
                if (piece != null) {
                    int x = piece.x + switch (piece.rotation) {
                        case NONE -> piece.getXOffset() + piece.type.doorX;
                        case CLOCKWISE_90 -> 0;
                        case CLOCKWISE_180 -> piece.getXOffset() + piece.type.width - 8 - piece.type.doorX;
                        case COUNTERCLOCKWISE_90 -> piece.width;
                    };

                    int z = piece.z + switch (piece.rotation) {
                        case NONE -> piece.depth;
                        case CLOCKWISE_90 -> piece.getZOffset() + piece.type.doorX;
                        case CLOCKWISE_180 -> 0;
                        case COUNTERCLOCKWISE_90 -> piece.getZOffset() + piece.type.width - 8 - piece.type.doorX;
                    };
                    int currentStreetNode = getGridElement(streetGrid, z, x);
                    if (currentStreetNode == 0) {
                        setGridElement(streetGrid, z, x, 2);
                        streetBlocks.add(makeStreetBlockPos(context, centerPos, x, z, rotation));
                    }
                    while (currentStreetNode == 0 || currentStreetNode == 1) {
                        int valLeft = getGridElement(grid, z, x - 1);
                        int valUp = getGridElement(grid, z - 1, x);
                        int direction = -1;
                        if (valLeft == 0 || valLeft == 1) {
                            if (valUp == 0 || valUp == 1) {
                                direction = rand.nextInt(2);
                            } else {
                                direction = 0;
                            }
                        } else if (valUp == 0 || valUp == 1) {
                            direction = 1;
                        }

                        if (direction == 0) {
                            //int firstFreeHeight = context.chunkGenerator().getFirstFreeHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());

                            if (getGridElement(streetGrid, z + 1, x) == 0) {
                                setGridElement(streetGrid, z + 1, x, 1);
                                streetBlocks.add(makeStreetBlockPos(context, centerPos, x, z + 1, rotation));
                                //streetBlocks.add(centerPos.offset(new BlockPos(x, firstFreeHeight, z + 1).rotate(rotation)).atY(firstFreeHeight-1));//makeStreetBlockPos(context, centerPos, x, z + 1, rotation));
                            }
                            //if (getGridElement(streetGrid, z - 1, x) == 0) {
                            //    setGridElement(streetGrid, z - 1, x, 1);
                            //    streetBlocks.add(new Tuple<>(new BlockPos(x, 0, z - 1), Registration.DAMP_WOOD.get().defaultBlockState()));
                            //}
                            //streetGrid[z][x] |= 1;
                            x -= 1;
                            currentStreetNode = getGridElement(streetGrid, z, x);
                            //firstFreeHeight = context.chunkGenerator().getFirstFreeHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
                            if (currentStreetNode == 0) {
                                setGridElement(streetGrid, z, x, 2);
                                streetBlocks.add(makeStreetBlockPos(context, centerPos, x, z, rotation));
                                //streetBlocks.add(centerPos.offset(new BlockPos(x, firstFreeHeight, z).rotate(rotation)).atY(firstFreeHeight-1));//makeStreetBlockPos(context, centerPos, x, z, rotation));
                            }
                            if (getGridElement(streetGrid, z + 1, x) == 0) {
                                setGridElement(streetGrid, z + 1, x, 1);
                                streetBlocks.add(makeStreetBlockPos(context, centerPos, x, z + 1, rotation));
                                //streetBlocks.add(centerPos.offset(new BlockPos(x, firstFreeHeight, z + 1).rotate(rotation)).atY(firstFreeHeight-1));//makeStreetBlockPos(context, centerPos, x, z + 1, rotation));
                            }
                            //if (getGridElement(streetGrid, z - 1, x) == 0) {
                            //    setGridElement(streetGrid, z - 1, x, 1);
                            //    streetBlocks.add(new Tuple<>(new BlockPos(x, 0, z - 1), Registration.DAMP_WOOD.get().defaultBlockState()));
                            //}
                        } else if (direction == 1) {
                            //int firstFreeHeight = context.chunkGenerator().getFirstFreeHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());

                            //streetGrid[z][x] |= 4;
                            //z -= 1;
                            //currentStreetNode = getGridElement(streetGrid, z, x);
                            //if (z >= 0) {
                            //    streetGrid[z][x] |= 8;
                            //}
                            if (getGridElement(streetGrid, z, x + 1) == 0) {
                                setGridElement(streetGrid, z, x + 1, 1);
                                streetBlocks.add(makeStreetBlockPos(context, centerPos, x + 1, z, rotation));
                                //streetBlocks.add(centerPos.offset(new BlockPos(x + 1, firstFreeHeight, z).rotate(rotation)).atY(firstFreeHeight-1));//makeStreetBlockPos(context, centerPos, x + 1, z, rotation));
                            }
                            //if (getGridElement(streetGrid, z, x - 1) == 0) {
                            //    setGridElement(streetGrid, z, x - 1, 1);
                            //    streetBlocks.add(new Tuple<>(new BlockPos(x - 1, 0, z), Registration.DAMP_WOOD.get().defaultBlockState()));
                            //}
                            //streetGrid[z][x] |= 1;
                            z -= 1;
                            currentStreetNode = getGridElement(streetGrid, z, x);
                            //firstFreeHeight = context.chunkGenerator().getFirstFreeHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
                            if (currentStreetNode == 0) {
                                setGridElement(streetGrid, z, x, 2);
                                streetBlocks.add(makeStreetBlockPos(context, centerPos, x, z, rotation));
                                //streetBlocks.add(centerPos.offset(new BlockPos(x, firstFreeHeight, z).rotate(rotation)).atY(firstFreeHeight-1));//makeStreetBlockPos(context, centerPos, x, z, rotation));
                            }
                            if (getGridElement(streetGrid, z, x + 1) == 0) {
                                setGridElement(streetGrid, z, x + 1, 1);
                                streetBlocks.add(makeStreetBlockPos(context, centerPos, x + 1, z, rotation));
                                //streetBlocks.add(centerPos.offset(new BlockPos(x + 1, firstFreeHeight, z).rotate(rotation)).atY(firstFreeHeight-1));//makeStreetBlockPos(context, centerPos, x + 1, z, rotation));
                            }
                            //if (getGridElement(streetGrid, z, x - 1) == 0) {
                            //    setGridElement(streetGrid, z, x - 1, 1);
                            //    streetBlocks.add(new Tuple<>(new BlockPos(x - 1, 0, z), Registration.DAMP_WOOD.get().defaultBlockState()));
                            //}
                        } else if (direction == -1) {
                            currentStreetNode = -1;
                        }
                    }
                }
            }
        }
        BlockPos offsetBB = centerPos.offset(new BlockPos(128, 0, 128).rotate(rotation));
        BoundingBox box = new BoundingBox(Math.min(offsetBB.getX(), centerPos.getX()), centerPos.getY(), Math.min(offsetBB.getZ(), centerPos.getZ()), Math.max(offsetBB.getX(), centerPos.getX()), centerPos.getY() + 250, Math.max(offsetBB.getZ(), centerPos.getZ()));
        returnList.add(new StreetPiece(streetGrid, rotation, centerPos, box, streetBlocks));

    }

    private static int getGridElement(int[][] grid, int x, int z) {
        if (x < 0 || x >= grid.length || z < 0 || z >= grid[0].length) {
            return -1;
        }
        return grid[x][z];
    }

    private static void setGridElement(int[][] grid, int x, int z, int val) {
        if (x >= 0 && x < grid.length && z >= 0 && z < grid[0].length) {
            grid[x][z] = val;
        }
    }

    private static BlockPos makeStreetBlockPos(Structure.GenerationContext context, BlockPos centerPos, int offsetX, int offsetZ, Rotation rotation) {
        BlockPos offset = new BlockPos(offsetX, 0, offsetZ).rotate(rotation);
        BlockPos finalPos = centerPos.offset(offset);
        int firstFreeHeight = context.chunkGenerator().getFirstFreeHeight(finalPos.getX(), finalPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        return finalPos.atY(firstFreeHeight-1);
    }


}
