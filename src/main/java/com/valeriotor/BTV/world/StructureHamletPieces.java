package com.valeriotor.BTV.world;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.blocks.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureHamletPieces {
	public static void registerHamletPieces() {
		MapGenStructureIO.registerStructureComponent(StructureHamletPieces.Church.class, "HaCh");
		MapGenStructureIO.registerStructureComponent(StructureHamletPieces.Start.class, "HaStart");
		MapGenStructureIO.registerStructureComponent(StructureHamletPieces.Well.class, "HaW");
	}
	
	public static List<StructureHamletPieces.PieceWeight> getStructureHamletWeightedPieceList(Random random, int size){
		List<StructureHamletPieces.PieceWeight> list = Lists.<StructureHamletPieces.PieceWeight>newArrayList();
		list.add(new StructureHamletPieces.PieceWeight(StructureHamletPieces.Church.class, 8, MathHelper.getInt(random, 0 + size, 3 + size * 2)));
		
		Iterator<StructureHamletPieces.PieceWeight> iterator = list.iterator();

        while (iterator.hasNext())
        {
            if ((iterator.next()).HamletPiecesLimit == 0)
            {
                iterator.remove();
            }
        }
		return list;
	}
	
	
	public abstract static class Hamlet extends StructureComponent{
		protected int averageGroundLvl = -1;
		private int villagersSpawned;
        protected int structureType;
        protected StructureHamletPieces.Start startPiece;
        
        public Hamlet()
        {
        }

        protected Hamlet(StructureHamletPieces.Start start, int type)
        {
            super(type);

            if (start != null)
            {
                this.structureType = start.structureType;
                startPiece = start;
            }
        }
        
        protected int getAverageGroundLevel(World worldIn, StructureBoundingBox structurebb)
        {
            int i = 0;
            int j = 0;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k)
            {
                for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l)
                {
                    blockpos$mutableblockpos.setPos(l, 64, k);

                    if (structurebb.isVecInside(blockpos$mutableblockpos))
                    {
                        i += Math.max(worldIn.getTopSolidOrLiquidBlock(blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel() - 1);
                        ++j;
                    }
                }
            }

            if (j == 0)
            {
                return -1;
            }
            else
            {
                return i / j;
            }
        }
        
        @Override
        protected void writeStructureToNBT(NBTTagCompound tagCompound) {
        	tagCompound.setInteger("HPos", this.averageGroundLvl);
            tagCompound.setInteger("VCount", this.villagersSpawned);
            tagCompound.setByte("Type", (byte)this.structureType);
        }
        
        @Override
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
        	this.averageGroundLvl = tagCompound.getInteger("HPos");
            this.villagersSpawned = tagCompound.getInteger("VCount");
            this.structureType = tagCompound.getByte("Type");
        	
        }
        
        protected void setStructureType(int p_189924_1_)
        {
            this.structureType = p_189924_1_;
        }
        
        protected static boolean canHamletGoDeeper(StructureBoundingBox structurebb)
        {
            return structurebb != null && structurebb.minY > 10;
        }
	}
	
	
	public static class PieceWeight{
		public Class<? extends StructureHamletPieces.Hamlet> HamletPieceClass;
		public final int HamletPieceWeight;
        public int HamletPiecesSpawned;
        public int HamletPiecesLimit;
        
        public PieceWeight(Class<? extends StructureHamletPieces.Hamlet> p_i2098_1_, int p_i2098_2_, int p_i2098_3_) {
        	this.HamletPieceClass = p_i2098_1_;
        	this.HamletPieceWeight = p_i2098_2_;
        	this.HamletPiecesLimit = p_i2098_3_;
        }
        
        public boolean canSpawnMoreHamletPiecesOfType(int componentType)
        {
            return this.HamletPiecesLimit == 0 || this.HamletPiecesSpawned < this.HamletPiecesLimit;
        }

        public boolean canSpawnMoreVillagePieces()
        {
            return this.HamletPiecesLimit == 0 || this.HamletPiecesSpawned < this.HamletPiecesLimit;
        }
	}
	
	public static class Well extends StructureHamletPieces.Hamlet{
		
		public Well() {
			
		}
		
		public Well(StructureHamletPieces.Start start, int type, Random rand, int x, int z) {
			super(start, type);
            this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));

            if (this.getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
            {
                this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
            }
            else
            {
                this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
            }
            
		}
		
		
		

		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
			if (this.averageGroundLvl < 0)
            {
                this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.averageGroundLvl < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 3, 0);
            }
			IBlockState iblockstate = Blocks.COBBLESTONE.getDefaultState();
            IBlockState iblockstate1 = BlockRegistry.DampWood.getDefaultState();
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 4, 12, 4, iblockstate, Blocks.FLOWING_WATER.getDefaultState(), false);
            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 12, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, 12, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 12, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, 12, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 1, 13, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 1, 14, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 4, 13, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 4, 14, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 1, 13, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 1, 14, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 4, 13, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 4, 14, 4, structureBoundingBoxIn);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 15, 1, 4, 15, 4, iblockstate, iblockstate, false);

            for (int i = 0; i <= 5; ++i)
            {
                for (int j = 0; j <= 5; ++j)
                {
                    if (j == 0 || j == 5 || i == 0 || i == 5)
                    {
                        this.setBlockState(worldIn, iblockstate, j, 11, i, structureBoundingBoxIn);
                        this.clearCurrentPositionBlocksUpwards(worldIn, j, 12, i, structureBoundingBoxIn);
                    }
                }
            }

            return true;
		}
		
	}
	
	public static class Start extends StructureHamletPieces.Well{
		public BiomeProvider biomeProvider;
        public int terrainType;
        public StructureHamletPieces.PieceWeight lastPlaced;
        
        public List<StructureHamletPieces.PieceWeight> structureHamletPiecedWeightList;
        public List<StructureComponent> pendingHouses = Lists.<StructureComponent>newArrayList();
        public List<StructureComponent> pendingRoads = Lists.<StructureComponent>newArrayList();
        public Biome biome;
        
        public Start() {
        	
        }
        
        public Start(BiomeProvider biomeProviderIn, int p_i2104_2_, Random rand, int p_i2104_4_, int p_i2104_5_, List<StructureHamletPieces.PieceWeight> p_i2104_6_, int p_i2104_7_) {
        	super((StructureHamletPieces.Start)null,0, rand, p_i2104_4_, p_i2104_5_);
        	this.biomeProvider = biomeProviderIn;
        	this.structureHamletPiecedWeightList = p_i2104_6_;
        	this.terrainType = p_i2104_7_;
            Biome biome = biomeProviderIn.getBiome(new BlockPos(p_i2104_4_, 0, p_i2104_5_), Biomes.DEFAULT);
            this.biome = biome;
            this.startPiece = this;
            this.structureType = 1;
            
        }
	}
	
	public static class Church extends StructureHamletPieces.Hamlet{
		
		public Church()
        {
        }

        public Church(StructureHamletPieces.Start start, int type, Random rand, StructureBoundingBox p_i45564_4_, EnumFacing facing)
        {
            super(start, type);
            this.setCoordBaseMode(facing);
            this.boundingBox = p_i45564_4_;
        }
        
        public static StructureHamletPieces.Church createPiece(StructureHamletPieces.Start start, List<StructureComponent> p_175854_1_, Random rand, int p_175854_3_, int p_175854_4_, int p_175854_5_, EnumFacing facing, int p_175854_7_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175854_3_, p_175854_4_, p_175854_5_, 0, 0, 0, 5, 12, 9, facing);
            return canHamletGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175854_1_, structureboundingbox) == null ? new StructureHamletPieces.Church(start, p_175854_7_, rand, structureboundingbox, facing) : null;
        }
		
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
			if (this.averageGroundLvl < 0)
            {
                this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                if (this.averageGroundLvl < 0)
                {
                    return true;
                }

                this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 12 - 1, 0);
            }
			
			IBlockState iblockstate = BlockRegistry.DampWood.getDefaultState();
            IBlockState iblockstate1 = Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
            IBlockState iblockstate2 = Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST);
            IBlockState iblockstate3 = Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 3, 0, 8, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 3, 10, 0, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 10, 3, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 10, 3, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 0, 4, 7, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 4, 4, 4, 7, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 8, 3, 4, 8, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 4, 3, 10, 4, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 5, 3, 5, 7, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, iblockstate, iblockstate, false);
            this.setBlockState(worldIn, iblockstate, 0, 11, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate, 4, 11, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate, 2, 11, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate, 2, 11, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate, 1, 1, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate, 1, 1, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate, 2, 1, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate, 3, 1, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate, 3, 1, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 1, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 2, 1, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 3, 1, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate2, 1, 2, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate3, 3, 2, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 6, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 7, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 6, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 7, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 6, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 6, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 6, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 3, 8, structureBoundingBoxIn);
            IBlockState iblockstate4 = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.WEST);
            
            for (int i = 1; i <= 9; ++i)
            {
                this.setBlockState(worldIn, iblockstate4, 3, i, 3, structureBoundingBoxIn);
            }

            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
            
            
            if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
            {
                this.setBlockState(worldIn, iblockstate1, 2, 0, -1, structureBoundingBoxIn);

                if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
                {
                    this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
                }
            }

            for (int k = 0; k < 9; ++k)
            {
                for (int j = 0; j < 5; ++j)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, j, 12, k, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j, -1, k, structureBoundingBoxIn);
                }
            }

            
            return true;
		}
		
	}
	
	
}
