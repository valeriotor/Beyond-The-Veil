package com.valeriotor.beyondtheveil.world.structures;

import com.mojang.serialization.Codec;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class AlgaeShroomStructure extends Structure {
    public static final Codec<AlgaeShroomStructure> CODEC = simpleCodec(AlgaeShroomStructure::new);
    protected AlgaeShroomStructure(StructureSettings pSettings) {
        super(pSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos pos = context.chunkPos().getWorldPosition();

        //NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor(), context.randomState());
//
        //int landHeight = context.chunkGenerator().getFirstOccupiedHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        //BlockState topBlock = columnOfBlocks.getBlock(landHeight);
        return Optional.of(new GenerationStub(pos.atY(40), (piecesBuilder) -> {
            NoiseColumn baseColumn = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor(), context.randomState());
            for (int y = 40; y > 10; y--) {
                BlockState block = baseColumn.getBlock(y);
                if (block.getBlock() == Registration.DARK_SAND.get()) {
                    piecesBuilder.addPiece(new AlgaeShroomPiece(context.structureTemplateManager(), "algae_shroom", pos.atY(y)));
                    break;
                }
            }
        }));
    }

    @Override
    public StructureType<?> type() {
        return Registration.ALGAE_SHROOM.get();
    }

    public static class AlgaeShroomPiece extends TemplateStructurePiece {

        public AlgaeShroomPiece(StructureTemplateManager pStructureTemplateManager, String pTemplateName, BlockPos pTemplatePosition) {
            super(Registration.ALGAE_SHROOM_PIECE.get(), 0, pStructureTemplateManager, makeResourceLocation(), pTemplateName, makeSettings(), pTemplatePosition);
        }

        public AlgaeShroomPiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(Registration.ALGAE_SHROOM_PIECE.get(), pTag, pStructureTemplateManager, (p_227512_) -> makeSettings());
        }

        public AlgaeShroomPiece(StructurePieceSerializationContext context, CompoundTag tag) {
            this(context.structureTemplateManager(), tag);
        }

        private static StructurePlaceSettings makeSettings() {
            return (new StructurePlaceSettings()).setIgnoreEntities(false);
        }

        @Override
        protected ResourceLocation makeTemplateLocation() {
            return makeResourceLocation();
        }

        private static ResourceLocation makeResourceLocation() {
            return new ResourceLocation(References.MODID, "arche/algae_shroom");
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }
    }
}
