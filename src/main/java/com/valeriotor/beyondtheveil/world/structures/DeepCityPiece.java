package com.valeriotor.beyondtheveil.world.structures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.material.FluidState;

import java.awt.Point;
import java.util.*;

public class DeepCityPiece extends TemplateStructurePiece {

    public static final int INDIVIDUAL_WIDTH = 39;
    public static final int GRID_RADIUS = 3;
    private final BlockPos centerPos;
    private int radius;
    private int doorRadius;
    private boolean[] corridors = new boolean[4];

    private record WeightedBuilding(String name, Weight weight, int radius, int doorRadius) implements WeightedEntry {
        @Override
        public Weight getWeight() {
            return this.weight;
        }
    }

    private static WeightedRandomList<WeightedBuilding> buildingTypes;

    static {
        List<WeightedBuilding> list = new ArrayList<>();
        list.add(new WeightedBuilding("home1", Weight.of(10), 6, 6));
        list.add(new WeightedBuilding("beacon", Weight.of(3), 9, 9));
        buildingTypes = WeightedRandomList.create(list);
    }

    public DeepCityPiece(StructureTemplateManager pStructureTemplateManager, String name, Rotation rotation, BlockPos centerPos, int radius, int doorRadius) {
        super(Registration.DEEP_CITY_PIECE.get(), 0, pStructureTemplateManager, makeResourceLocation(name), name, makeSettings(rotation), centerPos.offset(-radius, 0, -radius));
        this.centerPos = centerPos;
        this.radius = radius;
        this.doorRadius = doorRadius;
        this.boundingBox = new BoundingBox(centerPos.getX()-((INDIVIDUAL_WIDTH-1)/2), boundingBox.minY(), centerPos.getZ()-((INDIVIDUAL_WIDTH-1)/2), centerPos.getX()+((INDIVIDUAL_WIDTH-1)/2), boundingBox.maxY(), centerPos.getZ()+((INDIVIDUAL_WIDTH-1)/2));
    }

    public DeepCityPiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
        super(Registration.DEEP_CITY_PIECE.get(), pTag, pStructureTemplateManager, (p_227512_) -> makeSettings(Rotation.valueOf(pTag.getString("Rot"))));
        this.radius = pTag.getInt("radius");
        this.centerPos = templatePosition.offset(this.radius, 0, this.radius);
        this.boundingBox = new BoundingBox(centerPos.getX()-((INDIVIDUAL_WIDTH-1)/2), boundingBox.minY(), centerPos.getZ()-((INDIVIDUAL_WIDTH-1)/2), centerPos.getX()+((INDIVIDUAL_WIDTH-1)/2), boundingBox.maxY(), centerPos.getZ()+((INDIVIDUAL_WIDTH-1)/2));
        if (pTag.contains("corridor0")) {
            for (int i = 0; i < 4; i++) {
                corridors[i] = pTag.getBoolean("corridor" + i);
            }
        }
        if (pTag.contains("doorRadius")) {
            doorRadius = pTag.getInt("doorRadius");
        }
    }

    public DeepCityPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        this(context.structureTemplateManager(), tag);
    }

    private static StructurePlaceSettings makeSettings(Rotation pRotation) {
        //BlockIgnoreProcessor blockignoreprocessor = pOverwrite ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
        return (new StructurePlaceSettings()).setIgnoreEntities(true).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK).setRotation(pRotation);
    }

    @Override
    protected ResourceLocation makeTemplateLocation() {
        return makeResourceLocation(templateName);
    }

    public static ResourceLocation makeResourceLocation(String pName) {
        return new ResourceLocation(References.MODID, "deep_city/" + pName);
    }


    @Override
    protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

    }

    @Override
    public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
        //pBox = new BoundingBox(centerPos.getX()-128, centerPos.getY()-20, centerPos.getZ()-128,centerPos.getX()+128, centerPos.getY()+220, centerPos.getZ()+128);
        //super.postProcess(pLevel, pStructureManager, pGenerator, pRandom, pBox, pChunkPos, pPos);
        this.placeSettings.setBoundingBox(pBox);
        //this.boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
        if (this.template.placeInWorld(pLevel, this.templatePosition, pPos, this.placeSettings, pRandom, 2)) {
            for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.STRUCTURE_BLOCK)) {
                if (structuretemplate$structureblockinfo.nbt() != null) {
                    StructureMode structuremode = StructureMode.valueOf(structuretemplate$structureblockinfo.nbt().getString("mode"));
                    if (structuremode == StructureMode.DATA) {
                        this.handleDataMarker(structuretemplate$structureblockinfo.nbt().getString("metadata"), structuretemplate$structureblockinfo.pos(), pLevel, pRandom, pBox);
                    }
                }
            }

            for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo1 : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.JIGSAW)) {
                if (structuretemplate$structureblockinfo1.nbt() != null) {
                    String s = structuretemplate$structureblockinfo1.nbt().getString("final_state");
                    BlockState blockstate = Blocks.AIR.defaultBlockState();

                    try {
                        blockstate = BlockStateParser.parseForBlock(pLevel.holderLookup(Registries.BLOCK), s, true).blockState();
                    } catch (CommandSyntaxException commandsyntaxexception) {
                        //LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", s, structuretemplate$structureblockinfo1.pos());
                    }

                    pLevel.setBlock(structuretemplate$structureblockinfo1.pos(), blockstate, 3);
                }
            }
        }
        //this.boundingBox = new BoundingBox(centerPos.getX()-20, boundingBox.minY(), centerPos.getZ()-20, centerPos.getX()+20, boundingBox.maxY(), centerPos.getZ()+20);
        int DOOR_HEIGHT = 18;
        //pBox = new BoundingBox(centerPos.getX()-128, centerPos.getY()+3, centerPos.getZ()-128, centerPos.getX()+128, centerPos.getY()+320, centerPos.getZ()+128);

        for (int i = 0; i < corridors.length; i++) {
            if (corridors[i]) {
                processCorridor(pLevel, pBox, new BlockPos(centerPos.getX(), pPos.getY() + DOOR_HEIGHT, centerPos.getZ()), Direction.from2DDataValue(i));
            }
        }
    }

    private void processCorridor(WorldGenLevel level, BoundingBox box, BlockPos pos, Direction direction) {
        direction = direction.getOpposite();
        int distance = INDIVIDUAL_WIDTH / 2 + 1 - doorRadius - 1;
        pos = pos.relative(direction, doorRadius + 1);
        pos = pos.offset(centerPos.offset(-((INDIVIDUAL_WIDTH-1)/2), 0, -((INDIVIDUAL_WIDTH-1)/2)).multiply(-1));
        //BlockPos from = new BlockPos(box.minX(), box.minY(), box.minZ()).offset(centerPos.multiply(-1));
        //BlockPos to = new BlockPos(box.maxX(), box.maxY(), box.maxZ()).offset(centerPos.multiply(-1));
        //generateBox(level, box, from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ(), Blocks.AMETHYST_BLOCK.defaultBlockState(), Blocks.AMETHYST_BLOCK.defaultBlockState(), false);
        //BlockPos pos = center.offset(EnumFacing.UP, DOOR_HEIGHT).offset(facing, distanceDoorFromCenter);
        BlockPos fromAir = pos.relative(direction.getCounterClockWise(), 1).above();
        BlockPos toAir = pos.relative(direction.getCounterClockWise().getOpposite(), 1).relative(direction, distance).relative(Direction.UP, 3);
        BlockPos fromLeftGlass = pos.relative(direction.getCounterClockWise(), 2).above();
        BlockPos toLeftGlass = pos.relative(direction.getCounterClockWise(), 2).relative(direction, distance).relative(Direction.UP, 3);
        BlockPos fromRightGlass = pos.relative(direction.getCounterClockWise().getOpposite(), 2).above();
        BlockPos toRightGlass = pos.relative(direction.getCounterClockWise().getOpposite(), 2).relative(direction, distance).relative(Direction.UP, 3);
        BlockPos fromGlassCeiling = pos.relative(direction.getCounterClockWise()).relative(Direction.UP, 4);
        BlockPos toGlassCeiling = pos.relative(direction.getCounterClockWise().getOpposite()).relative(direction, distance).relative(Direction.UP, 4);
        BlockPos fromPrismarineFloor = pos.relative(direction.getCounterClockWise(), 2);
        BlockPos toPrismarineFloor = pos.relative(direction.getCounterClockWise().getOpposite(), 2).relative(direction, distance);
        generateBox(level, box, Math.min(fromAir.getX(), toAir.getX()), Math.min(fromAir.getY(), toAir.getY()), Math.min(fromAir.getZ(), toAir.getZ()), Math.max(fromAir.getX(), toAir.getX()), Math.max(fromAir.getY(), toAir.getY()), Math.max(fromAir.getZ(), toAir.getZ()), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
        generateBox(level, box, Math.min(fromLeftGlass.getX(), toLeftGlass.getX()), Math.min(fromLeftGlass.getY(), toLeftGlass.getY()), Math.min(fromLeftGlass.getZ(), toLeftGlass.getZ()), Math.max(fromLeftGlass.getX(), toLeftGlass.getX()), Math.max(fromLeftGlass.getY(), toLeftGlass.getY()), Math.max(fromLeftGlass.getZ(), toLeftGlass.getZ()), Registration.DARK_GLASS.get().defaultBlockState(), Registration.DARK_GLASS.get().defaultBlockState(), false);
        generateBox(level, box, Math.min(fromRightGlass.getX(), toRightGlass.getX()), Math.min(fromRightGlass.getY(), toRightGlass.getY()), Math.min(fromRightGlass.getZ(), toRightGlass.getZ()), Math.max(fromRightGlass.getX(), toRightGlass.getX()), Math.max(fromRightGlass.getY(), toRightGlass.getY()), Math.max(fromRightGlass.getZ(), toRightGlass.getZ()), Registration.DARK_GLASS.get().defaultBlockState(), Registration.DARK_GLASS.get().defaultBlockState(), false);
        generateBox(level, box, Math.min(fromGlassCeiling.getX(), toGlassCeiling.getX()), Math.min(fromGlassCeiling.getY(), toGlassCeiling.getY()), Math.min(fromGlassCeiling.getZ(), toGlassCeiling.getZ()), Math.max(fromGlassCeiling.getX(), toGlassCeiling.getX()), Math.max(fromGlassCeiling.getY(), toGlassCeiling.getY()), Math.max(fromGlassCeiling.getZ(), toGlassCeiling.getZ()), Registration.DARK_GLASS.get().defaultBlockState(), Registration.DARK_GLASS.get().defaultBlockState(), false);
        generateBox(level, box, Math.min(fromPrismarineFloor.getX(), toPrismarineFloor.getX()), Math.min(fromPrismarineFloor.getY(), toPrismarineFloor.getY()), Math.min(fromPrismarineFloor.getZ(), toPrismarineFloor.getZ()), Math.max(fromPrismarineFloor.getX(), toPrismarineFloor.getX()), Math.max(fromPrismarineFloor.getY(), toPrismarineFloor.getY()), Math.max(fromPrismarineFloor.getZ(), toPrismarineFloor.getZ()), Blocks.DARK_PRISMARINE.defaultBlockState(), Blocks.DARK_PRISMARINE.defaultBlockState(), false);
        //generateBox(level, );
    }

    protected void placeBlock(WorldGenLevel pLevel, BlockState pBlockstate, int pX, int pY, int pZ, BoundingBox pBoundingbox) {
        BlockPos blockpos = this.getWorldPos(pX, pY, pZ);
        if (pBoundingbox.isInside(blockpos)) {
            if (this.canBeReplaced(pLevel, pX, pY, pZ, pBoundingbox)) {
                //if (this.mirror != Mirror.NONE) {
                //    pBlockstate = pBlockstate.mirror(this.mirror);
                //}
//
                //if (this.rotation != Rotation.NONE) {
                //    pBlockstate = pBlockstate.rotate(this.rotation);
                //}

                pLevel.setBlock(blockpos, pBlockstate, 2);
                FluidState fluidstate = pLevel.getFluidState(blockpos);
                if (!fluidstate.isEmpty()) {
                    pLevel.scheduleTick(blockpos, fluidstate.getType(), 0);
                }

                //if (SHAPE_CHECK_BLOCKS.contains(pBlockstate.getBlock())) {
                //    pLevel.getChunk(blockpos).markPosForPostprocessing(blockpos);
                //}
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
        super.addAdditionalSaveData(pContext, pTag);
        pTag.putString("Rot", this.placeSettings.getRotation().name());
        pTag.putInt("radius", radius);
        pTag.putInt("doorRadius", doorRadius);
        for (int i = 0; i < corridors.length; i++) {
            pTag.putBoolean("corridor" + i, corridors[i]);
        }
    }

    public void addCorridor(Direction direction) {
        corridors[direction.get2DDataValue()] = true;
    }

    public int getRadius() {
        return radius;
    }

    public BlockPos getCenterPos() {
        return centerPos;
    }

    public static class DeepCityLayout {
        private static final int MAX_BRANCH_LENGTH = 6;
        private final Map<Point, DeepCityPiece> map = new HashMap<>();
        private final Set<Connection> connections = new LinkedHashSet<>();
        private final BlockPos center;
        private final StructureTemplateManager manager;
        private final RandomSource rand;
        private Direction currentDirection;
        private int index = 0;
        private int branchLevel = 0;
        private Point currentPoint;

        public DeepCityLayout(RandomSource rand, BlockPos center, StructureTemplateManager manager) {
            this.rand = rand;
            this.center = center;
            this.manager = manager;
        }

        public void generate() {
            map.put(new Point(0, 0), new DeepCityPiece(manager, "arena", Rotation.NONE, center, 19, 5)); // TODO
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                currentDirection = facing;
                currentPoint = new Point(0, 0);
                index = 0;
                branchLevel++;
                generateNode(facing);
                branchLevel--;
            }
            makeCorridors();
        }

        private void makeCorridors() {
            for (Connection c : connections) {
                map.get(c.p1).addCorridor(c.getP1Corridor());
                map.get(c.p2).addCorridor(c.getP2Corridor());
            }
        }

        private void generateNode(Direction facing) {
            Point newPoint = offsetPoint(facing);
            if (outOfGrid(newPoint)) return;

            index++;
            if (index > MAX_BRANCH_LENGTH) return;

            connections.add(new Connection(currentPoint, newPoint));

            if (map.containsKey(newPoint)) return;
            BlockPos newPos = center.offset(newPoint.x * (INDIVIDUAL_WIDTH), 0, newPoint.y * (INDIVIDUAL_WIDTH));
            WeightedBuilding weightedBuilding = buildingTypes.getRandom(rand).get();
            DeepCityPiece piece = new DeepCityPiece(manager, weightedBuilding.name, Rotation.NONE, newPos, weightedBuilding.radius, weightedBuilding.doorRadius);
            map.put(newPoint, piece);

            Point oldPoint = currentPoint;
            currentPoint = newPoint;

            int nextFaceIndex = rand.nextInt(3);
            Direction nextFace = Direction.from2DDataValue(nextFaceIndex);
            if (nextFace == facing.getOpposite()) nextFace = Direction.from2DDataValue(nextFaceIndex + 1);
            boolean branchOut = rand.nextInt((8 * branchLevel - index) / 4 + 1) == 0;

            if (branchOut) {
                generateBranch(nextFace);
                nextFace = findNextFaceAfterBranch(facing, nextFace);
            }
            generateNode(nextFace);
            index--;
            currentPoint = oldPoint;
        }

        private void generateBranch(Direction nextFace) {
            branchLevel++;
            generateNode(nextFace);
            branchLevel--;
        }

        private Direction findNextFaceAfterBranch(Direction facing, Direction hold) {
            Direction nextFace = null;
            int nextFaceIndex = rand.nextInt(2);
            for (int i = 0; i < 4; i++) {
                nextFace = Direction.from2DDataValue(nextFaceIndex);
                if (nextFace == hold) continue;
                if (nextFace == facing.getOpposite()) continue;
                if (--nextFaceIndex < 0) break;
            }
            return nextFace;
        }

        private boolean outOfGrid(Point point) {
            return Math.abs(point.x) > GRID_RADIUS || Math.abs(point.y) > GRID_RADIUS;
        }

        private Point offsetPoint(Direction facing) {
            switch (facing) {
                case NORTH:
                    return new Point(currentPoint.x, currentPoint.y - 1);
                case EAST:
                    return new Point(currentPoint.x + 1, currentPoint.y);
                case SOUTH:
                    return new Point(currentPoint.x, currentPoint.y + 1);
                case WEST:
                    return new Point(currentPoint.x - 1, currentPoint.y);
                default:
                    return currentPoint;
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int y = -4; y <= 4; y++) {
                for (int x = -4; x <= 4; x++) {
                    Point p = new Point(x, y);
                    if (map.containsKey(p)) {
                        sb.append(switch (map.get(p).radius) {
                            case 6 -> 'H';
                            case 9 -> 'B';
                            default -> 'A';
                        });
                    } else
                        sb.append("O");
                    if (x < 4) {
                        Connection c = new Connection(p, new Point(x + 1, y));
                        if (connections.contains(c)) {
                            sb.append(" - ");
                        } else {
                            sb.append("   ");
                        }
                    }
                }
                sb.append(String.format("%n"));
                if (y < 4) {
                    for (int x = -4; x <= 4; x++) {
                        Point p1 = new Point(x, y);
                        Point p2 = new Point(x, y + 1);
                        Connection c = new Connection(p1, p2);
                        if (connections.contains(c)) {
                            sb.append("|   ");
                        } else {
                            sb.append("    ");
                        }
                    }
                    sb.append(String.format("%n"));
                }
            }
            return sb.toString();
        }

        public List<DeepCityPiece> getAsList() {
            return new LinkedList<>(this.map.values());
        }

        private static class Connection {
            private final Point p1;
            private final Point p2;

            private Connection(Point p1, Point p2) {
                this.p1 = p1;
                this.p2 = p2;
            }

            private Direction getP1Corridor() {
                return getCorridor(p1, p2);
            }

            private Direction getP2Corridor() {
                return getCorridor(p2, p1);
            }

            private Direction getCorridor(Point p1, Point p2) {
                if (p1.x < p2.x) return Direction.WEST;
                if (p1.x > p2.x) return Direction.EAST;
                if (p1.y < p2.y) return Direction.SOUTH;
                return Direction.NORTH;
            }

            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof Connection)) return false;
                if (obj == this) return true;
                Connection c = (Connection) obj;
                boolean a = (p1.equals(c.p1) && p2.equals(c.p2)) || (p1.equals(c.p2) && p2.equals(c.p1));
                return a;
            }

            @Override
            public int hashCode() {
                return p1.hashCode() + p2.hashCode();
            }

            @Override
            public String toString() {
                return "{" + p1.toString() + ", " + p2.toString() + "}";
            }
        }
    }

}
