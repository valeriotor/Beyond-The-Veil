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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class HangingAlgaeStructure extends Structure {
    public static final Codec<HangingAlgaeStructure> CODEC = simpleCodec(HangingAlgaeStructure::new);
    protected HangingAlgaeStructure(StructureSettings pSettings) {
        super(pSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos pos = context.chunkPos().getWorldPosition();

        //NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor(), context.randomState());
//
        //int landHeight = context.chunkGenerator().getFirstOccupiedHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        //BlockState topBlock = columnOfBlocks.getBlock(landHeight);
        int pY = 20;
        return Optional.of(new Structure.GenerationStub(pos.atY(pY), (piecesBuilder) -> {
            piecesBuilder.addPiece(new HangingAlgaePiece(context.structureTemplateManager(), "hanging_algae", pos.atY(pY)));
        }));
    }

    @Override
    public StructureType<?> type() {
        return Registration.HANGING_ALGAE.get();
    }

    public static class HangingAlgaePiece extends TemplateStructurePiece {

        public HangingAlgaePiece(StructureTemplateManager pStructureTemplateManager, String pTemplateName, BlockPos pTemplatePosition) {
            super(Registration.HANGING_ALGAE_PIECE.get(), 0, pStructureTemplateManager, makeResourceLocation(), pTemplateName, makeSettings(), pTemplatePosition);
        }

        public HangingAlgaePiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(Registration.HANGING_ALGAE_PIECE.get(), pTag, pStructureTemplateManager, (p_227512_) -> makeSettings());
        }

        public HangingAlgaePiece(StructurePieceSerializationContext context, CompoundTag tag) {
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
            return new ResourceLocation(References.MODID, "arche/hanging_algae");
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }
    }
}
