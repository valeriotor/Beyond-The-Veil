package com.valeriotor.beyondtheveil.world.structures;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class HamletStructure extends Structure {

    public static final Codec<HamletStructure> CODEC = simpleCodec(HamletStructure::new);

    protected HamletStructure(StructureSettings pSettings) {
        super(pSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        Rotation rotation = Rotation.getRandom(context.random());
        BlockPos blockpos = this.getLowestYIn5by5BoxOffset7Blocks(context, rotation);
        return Optional.of(new GenerationStub(blockpos, (p_227538_) -> {
            this.generatePieces(p_227538_, blockpos, rotation, context);
        }));
    }

    private void generatePieces(StructurePiecesBuilder pBuilder, BlockPos pStartPos, Rotation pRotation, Structure.GenerationContext pContext) {
        List<StructurePiece> list = Lists.newArrayList();
        list.addAll(HamletPieces.layout(pContext, pContext.random(), pContext.structureTemplateManager(), new HashMap<>(), pStartPos));
        list.forEach(pBuilder::addPiece);
    }

    @Override
    public StructureType<?> type() {
        return Registration.HAMLET.get();
    }
}