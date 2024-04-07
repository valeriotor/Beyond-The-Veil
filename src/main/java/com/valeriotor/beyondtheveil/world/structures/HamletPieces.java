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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.*;

public class HamletPieces {

    private record WeightedBuilding(String name, Weight weight, int width, int depth,
                                    int max) implements WeightedEntry {
        @Override
        public Weight getWeight() {
            return this.weight;
        }
    }

    private static final WeightedRandomList<WeightedBuilding> buildingTypes;

    static {
        List<WeightedBuilding> list = new ArrayList<>();
        list.add(new WeightedBuilding("house1", Weight.of(10), 20, 16, 10));
        list.add(new WeightedBuilding("house2", Weight.of(10), 18, 18, 10));
        list.add(new WeightedBuilding("house_two_floors", Weight.of(10), 17, 17, 1));
        list.add(new WeightedBuilding("hut", Weight.of(10), 15, 15, 10));
        list.add(new WeightedBuilding("inn", Weight.of(10), 25, 21, 10));
        list.add(new WeightedBuilding("lighthouse", Weight.of(10), 25, 20, 1));
        list.add(new WeightedBuilding("storehouse1", Weight.of(10), 25, 22, 4));
        list.add(new WeightedBuilding("storehouse2", Weight.of(10), 19, 23, 4));
        list.add(new WeightedBuilding("town_hall", Weight.of(10), 27, 20, 1));

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
    }

    public static List<HamletBuildingPiece> layout(RandomSource rand, StructureTemplateManager manager, Map<WeightedBuilding, Integer> numbersPerType, BlockPos centerPos) {
        List<WeightedBuilding> weightedBuildings = Lists.newArrayList(buildingTypes.unwrap());
        return layoutQuadrant(rand, manager, numbersPerType, weightedBuildings, centerPos);
    }


    private static List<HamletBuildingPiece> layoutQuadrant(RandomSource rand, StructureTemplateManager manager, Map<WeightedBuilding, Integer> numbersPerType, List<WeightedBuilding> allowedBuildingTypes, BlockPos centerPos) {
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
        List<HamletBuildingPiece> pieces = new ArrayList<>();
        for (BuildingOnGrid[] buildingsOnGrid : quadrant) {
            for (BuildingOnGrid buildingOnGrid : buildingsOnGrid) {
                if (buildingOnGrid != null) {
                    BlockPos offset = centerPos.offset(buildingOnGrid.x, 0, buildingOnGrid.z);
                    if (buildingOnGrid.rotation == Rotation.CLOCKWISE_180) {
                        offset = offset.offset(buildingOnGrid.type.width - 8, 0, buildingOnGrid.type.depth - 8);
                    } else if (buildingOnGrid.rotation == Rotation.COUNTERCLOCKWISE_90) {
                        offset = offset.offset(0, 0, buildingOnGrid.type.width - 8);
                    } else if (buildingOnGrid.rotation == Rotation.CLOCKWISE_90) {
                        offset = offset.offset(buildingOnGrid.type.depth - 8, 0, 0);
                    }
                    pieces.add(new HamletBuildingPiece(manager, buildingOnGrid.type, buildingOnGrid.rotation, offset));
                }
            }
        }
        for (HamletBuildingPiece piece : pieces) {
            System.out.println(piece.templatePosition() + " " + piece.getRotation());
        }
        return pieces;
    }


}
