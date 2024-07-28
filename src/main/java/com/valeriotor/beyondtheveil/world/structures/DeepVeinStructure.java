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
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class DeepVeinStructure extends Structure {

    public static final Codec<DeepVeinStructure> CODEC = simpleCodec(DeepVeinStructure::new);

    protected DeepVeinStructure(StructureSettings pSettings) {
        super(pSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos pos = context.chunkPos().getWorldPosition();

        NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor(), context.randomState());

        int landHeight = context.chunkGenerator().getFirstOccupiedHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        BlockState topBlock = columnOfBlocks.getBlock(landHeight);
        int pY = 70;
        return Optional.of(new Structure.GenerationStub(pos.atY(pY), (piecesBuilder) -> {
            piecesBuilder.addPiece(new DeepVeinPiece(context.structureTemplateManager(), new ResourceLocation(References.MODID, "deep_vein"), "deep_vein", pos.atY(pY)));
        }));
    }

    @Override
    public StructureType<?> type() {
        return Registration.DEEP_VEIN.get();
    }

    public static class DeepVeinPiece extends TemplateStructurePiece {

        public DeepVeinPiece(StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, String pTemplateName, BlockPos pTemplatePosition) {
            super(Registration.DEEP_VEIN_PIECE.get(), 0, pStructureTemplateManager, pLocation, pTemplateName, makeSettings(), pTemplatePosition);
        }

        public DeepVeinPiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(Registration.DEEP_CITY_PIECE.get(), pTag, pStructureTemplateManager, (p_227512_) -> makeSettings());
        }

        public DeepVeinPiece(StructurePieceSerializationContext context, CompoundTag tag) {
            this(context.structureTemplateManager(), tag);
        }

        private static StructurePlaceSettings makeSettings() {
            return (new StructurePlaceSettings()).setIgnoreEntities(false);
        }

        @Override
        protected ResourceLocation makeTemplateLocation() {
            return new ResourceLocation(References.MODID, "deep_vein");
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }
    }

}
