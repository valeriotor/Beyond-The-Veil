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
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class BloodCoralStructure extends Structure {
    public static final Codec<BloodCoralStructure> CODEC = simpleCodec(BloodCoralStructure::new);

    protected BloodCoralStructure(StructureSettings pSettings) {
        super(pSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos pos = context.chunkPos().getWorldPosition();
        final int y = 90;
        return Optional.of(new Structure.GenerationStub(pos.atY(y), (piecesBuilder) -> {
            createPiece(piecesBuilder, context, pos.atY(100), "blood_coral");
            createPiece(piecesBuilder, context, pos.offset(context.random().nextInt(7) + 12, 0, context.random().nextInt(7) + 12).atY(100), "blood_coral_small");
            createPiece(piecesBuilder, context, pos.offset(-context.random().nextInt(7) - 12, 0, -context.random().nextInt(7) - 12).atY(100), "blood_coral_small");
            createPiece(piecesBuilder, context, pos.offset(context.random().nextInt(7) + 12, 0, -context.random().nextInt(7) - 12).atY(100), "blood_coral_small");
        }));

    }

    private void createPiece(StructurePiecesBuilder piecesBuilder, GenerationContext context, BlockPos startPos, String name) {
        NoiseColumn baseColumn = context.chunkGenerator().getBaseColumn(startPos.getX(), startPos.getZ(), context.heightAccessor(), context.randomState());
        int y = 0;
        for (int i = 0; i < 50; i++) {
            y = startPos.getY() + i;
            BlockState block = baseColumn.getBlock(y);
            if (block.getBlock() != Blocks.WATER) {
                break;
            }
        }
        piecesBuilder.addPiece(new BloodCoralPiece(context.structureTemplateManager(), name, startPos.atY(y - (name.equals("blood_coral") ? 13 : 5))));

    }

    @Override
    public StructureType<?> type() {
        return Registration.BLOOD_CORAL.get();
    }

    public static class BloodCoralPiece extends TemplateStructurePiece {

        public BloodCoralPiece(StructureTemplateManager pStructureTemplateManager, String pTemplateName, BlockPos pTemplatePosition) {
            super(Registration.BLOOD_CORAL_PIECE.get(), 0, pStructureTemplateManager, makeResourceLocation(pTemplateName), pTemplateName, makeSettings(), pTemplatePosition);
        }

        public BloodCoralPiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(Registration.BLOOD_CORAL_PIECE.get(), pTag, pStructureTemplateManager, (p_227512_) -> makeSettings());
        }

        public BloodCoralPiece(StructurePieceSerializationContext context, CompoundTag tag) {
            this(context.structureTemplateManager(), tag);
        }

        private static StructurePlaceSettings makeSettings() {
            return (new StructurePlaceSettings()).setIgnoreEntities(false).setKeepLiquids(false);
        }

        @Override
        protected ResourceLocation makeTemplateLocation() {
            return makeResourceLocation(templateName);
        }

        private static ResourceLocation makeResourceLocation(String name) {
            return new ResourceLocation(References.MODID, "arche/" + name);
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }
    }

}
