package com.valeriotor.beyondtheveil.world.structures;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

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
        list.add(new WeightedBuilding("house1", Weight.of(10), 20, 16, 10, 4));
        list.add(new WeightedBuilding("house2", Weight.of(10), 18, 18, 10, 3));
        list.add(new WeightedBuilding("house_two_floors", Weight.of(10), 17, 17, 1, 3));
        list.add(new WeightedBuilding("hut", Weight.of(10), 15, 15, 10, 3));
        list.add(new WeightedBuilding("inn", Weight.of(10), 25, 21, 10, 12));
        list.add(new WeightedBuilding("lighthouse", Weight.of(10), 25, 20, 1, 2));
        list.add(new WeightedBuilding("storehouse1", Weight.of(10), 25, 22, 4, 5));
        list.add(new WeightedBuilding("storehouse2", Weight.of(10), 19, 23, 4, 5));
        list.add(new WeightedBuilding("town_hall", Weight.of(10), 27, 20, 1, 9));

        //list.add(new WeightedBuilding("idol", Weight.of(10), 17, 17, 1));
        buildingTypes = WeightedRandomList.create(list);
    }

    public static class HamletBuildingPiece extends TemplateStructurePiece {
        public HamletBuildingPiece(StructureTemplateManager pStructureTemplateManager, WeightedBuilding building, Rotation rotation, BlockPos templatePosition) {
            super(Registration.HAMLET_BUILDING_PIECE.get(), 0, pStructureTemplateManager, makeResourceLocation(building.name), building.name, makeSettings(rotation), templatePosition);
        }

        public HamletBuildingPiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(Registration.HAMLET_BUILDING_PIECE.get(), pTag, pStructureTemplateManager, (p_227512_) -> makeSettings(Rotation.valueOf(pTag.getString("Rot"))));
        }

        public HamletBuildingPiece(StructurePieceSerializationContext context, CompoundTag tag) {
            this(context.structureTemplateManager(), tag);
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }

        public static ResourceLocation makeResourceLocation(String pName) {
            return new ResourceLocation(References.MODID, "hamlet/" + pName);
        }

        private static StructurePlaceSettings makeSettings(Rotation pRotation) {
            //BlockIgnoreProcessor blockignoreprocessor = pOverwrite ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
            return (new StructurePlaceSettings()).setIgnoreEntities(false).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK).setRotation(pRotation);
        }
    }

    public static class StreetPiece extends StructurePiece {

        private final int[][] streetGrid;
        private final BlockPos centerPos;

        public StreetPiece(int[][] streetGrid, Rotation rotation, BlockPos centerPos) {
            super(Registration.HAMLET_BUILDING_PIECE.get(), 0, new BoundingBox(centerPos.getX(), centerPos.getY(), centerPos.getZ(), centerPos.getX() + 128, centerPos.getY() + 250, centerPos.getZ() + 128));
            this.streetGrid = streetGrid;
            this.centerPos = centerPos;
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
        }

        @Override
        public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
            for (int z = 0; z < streetGrid.length; z++) {
                int[] row = streetGrid[z];
                for (int x = 0; x < row.length; x++) {
                    int y = -61;// pGenerator.getFirstFreeHeight(x, z, Heightmap.Types.WORLD_SURFACE, pLevel, pRandom);
                    if (streetGrid[z][x] > 0) {
                        placeBlock(pLevel, Registration.DAMP_WOOD.get().defaultBlockState(), x + centerPos.getX(), y, z + centerPos.getZ(), pBox);
                    }
                }
            }
        }
    }


    private static final int MIN_BUILDINGS_PER_QUADRANT = 5;
    private static final int MAX_BUILDINGS_PER_QUADRANT = 15;
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

    public static List<StructurePiece> layout(RandomSource rand, StructureTemplateManager manager, Map<WeightedBuilding, Integer> numbersPerType, BlockPos centerPos) {
        List<WeightedBuilding> weightedBuildings = Lists.newArrayList(buildingTypes.unwrap());
        return layoutQuadrant(rand, manager, numbersPerType, weightedBuildings, centerPos);
    }


    private static List<StructurePiece> layoutQuadrant(RandomSource rand, StructureTemplateManager manager, Map<WeightedBuilding, Integer> numbersPerType, List<WeightedBuilding> allowedBuildingTypes, BlockPos centerPos) {
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
                        quadrant[i][j] = new BuildingOnGrid(building, rot, i * QUADRANT_SQUARE_SIDE, j * QUADRANT_SQUARE_SIDE, (parallel ? building.width : building.depth) - rand.nextInt(4) - 2, (parallel ? building.depth : building.width) - rand.nextInt(4) - 2);
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
        List<StructurePiece> pieces = new ArrayList<>();
        for (BuildingOnGrid[] buildingsOnGrid : quadrant) {
            for (BuildingOnGrid buildingOnGrid : buildingsOnGrid) {
                if (buildingOnGrid != null) {
                    BlockPos offset = centerPos.offset(buildingOnGrid.x + buildingOnGrid.getXOffset(), 0, buildingOnGrid.z + buildingOnGrid.getZOffset()); // TODO OFFSET BY (WIDTH-TYPE.WIDTH)/2 AND SAME FOR HEIGHT
                    if (buildingOnGrid.rotation == Rotation.CLOCKWISE_180) {
                        offset = offset.offset(buildingOnGrid.type.width - 8, 0, buildingOnGrid.type.depth - 8);
                    } else if (buildingOnGrid.rotation == Rotation.COUNTERCLOCKWISE_90) {
                        offset = offset.offset(0, 0, buildingOnGrid.type.width - 8);
                    } else if (buildingOnGrid.rotation == Rotation.CLOCKWISE_90) {
                        offset = offset.offset(buildingOnGrid.type.depth - 8, 0, 0);
                    }
                    pieces.add(new HamletBuildingPiece(manager, buildingOnGrid.type, buildingOnGrid.rotation, offset));

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
                    while (currentStreetNode == 0) {
                        int valLeft = getGridElement(grid, z, x - 1);
                        int valRight = getGridElement(grid, z - 1, x);
                        int direction = -1;
                        if (valLeft == 0 || valLeft == 1) {
                            if (valRight <= 1) {
                                direction = rand.nextInt(2);
                            } else {
                                direction = 0;
                            }
                        } else if (valRight == 0 || valRight == 1) {
                            direction = 1;
                        }

                        if (direction == 0) {
                            streetGrid[z][x] |= 1;
                            x -= 1;
                            currentStreetNode = getGridElement(streetGrid, z, x);
                            if (x >= 0) {
                                streetGrid[z][x] |= 2;
                            }
                        } else if (direction == 1) {
                            streetGrid[z][x] |= 4;
                            z -= 1;
                            currentStreetNode = getGridElement(streetGrid, z, x);
                            if (z >= 0) {
                                streetGrid[z][x] |= 8;
                            }
                        } else if (direction == -1) {
                            currentStreetNode = -1;
                        }
                    }
                }
            }
        }

        pieces.add(new StreetPiece(streetGrid, Rotation.NONE, centerPos));

        return pieces;
    }

    private static int getGridElement(int[][] grid, int x, int z) {
        if (x < 0 || x > grid.length || z < 0 || z > grid[0].length) {
            return -1;
        }
        return grid[x][z];
    }


}
