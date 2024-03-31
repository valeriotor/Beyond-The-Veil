package com.valeriotor.beyondtheveil.world.structures;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.EndCityPieces;
import net.minecraft.world.level.levelgen.structure.structures.EndCityStructure;

import java.util.List;
import java.util.Optional;

public class DeepCityStructure extends Structure {

    public static final Codec<DeepCityStructure> CODEC = simpleCodec(DeepCityStructure::new);

    protected DeepCityStructure(StructureSettings pSettings) {
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
        pStartPos = pStartPos.atY(20);
        DeepCityPiece.DeepCityLayout layout = new DeepCityPiece.DeepCityLayout(pContext.random(), pStartPos, pContext.structureTemplateManager());
        layout.generate();
        list.addAll(layout.getAsList());
        list.add(new DeepCityPiece(pContext.structureTemplateManager(), "arena_top", Rotation.NONE, pStartPos.offset(0, 48, 0), 19, 5));
        list.forEach(pBuilder::addPiece);

        pBuilder.moveInsideHeights(pContext.random(), 20, 50);
    } // 1031 -489 --- 1069 -451  // 990 -530 --- 1002 -518

    @Override
    public StructureType<?> type() {
        return Registration.DEEP_CITY.get();
    }
}
