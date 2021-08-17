package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.gui.container.GuiContainerHandler;
import com.valeriotor.beyondtheveil.tileEntities.TileDeepChest;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class BlockDeepChest extends ModBlock implements ITileEntityProvider {

    private static final AxisAlignedBB BBOX;

    static {
        double a = 1/16D;
        BBOX = new AxisAlignedBB(a*2, 0, a*2, a*14, a*12.5, a*14);
    }

    public BlockDeepChest(String name) {
        super(Material.ROCK, name);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDeepChest();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand == EnumHand.MAIN_HAND) {
            playerIn.openGui(BeyondTheVeil.instance, GuiContainerHandler.DEEP_CHEST, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World w, BlockPos pos, IBlockState state) {
        TileEntity te = w.getTileEntity(pos);
        if (te instanceof TileDeepChest) {
            TileDeepChest tdc = (TileDeepChest) te;
            for (int i = 0; i < 16; i++) {
                spawnAsEntity(w, pos, tdc.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).extractItem(i, 64, false));
            }
        }

        super.breakBlock(w, pos, state);
    }


    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BBOX;
    }
}
