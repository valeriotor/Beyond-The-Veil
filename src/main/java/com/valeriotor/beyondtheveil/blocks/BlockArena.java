package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.bossfights.ArenaFightHandler;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataHandler;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.gui.container.GuiContainerHandler;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockArena extends ModBlock {
    public static final PropertyBool IS_STARTER = PropertyBool.create("is_starter");

    public BlockArena(String name) {
        super(Material.ROCK, name);
        setBlockUnbreakable();
        setResistance(6000001.0F);
        setDefaultState(blockState.getBaseState().withProperty(IS_STARTER, true));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        if(placer.isSneaking())
            return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(IS_STARTER, false);
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if (state.getValue(IS_STARTER)) {
                chooseFight(playerIn, pos);
            } else {
                endFight(playerIn, pos);
            }
        }
        return true;
    }

    private void chooseFight(EntityPlayer p, BlockPos pos) {
        if(checkIfDeepOne(p) && checkIfArenaAvailable(p, pos))
            p.openGui(BeyondTheVeil.instance, GuiContainerHandler.ARENA, p.world, pos.getX(), pos.getY(), pos.getZ());
    }

    private boolean checkIfDeepOne(EntityPlayer p) {
        if(!p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) {
            p.sendMessage(new TextComponentTranslation("interact.arena.mustbedeep"));
            return false;
        }
        return true;
    }

    private boolean checkIfArenaAvailable(EntityPlayer p, BlockPos pos) {
        if(ArenaFightHandler.isArenaOccupied(pos)) {
            p.sendMessage(new TextComponentTranslation("interact.arena.occupied"));
            return false;
        }
        return true;
    }

    private void endFight(EntityPlayer p, BlockPos pos) {
        ArenaFightHandler.removeArenaFight(p.getPersistentID());
        ServerTickEvents.removePlayerTimer("duel_ended", p);
        p.setPositionAndUpdate(pos.getX()-10.5, pos.getY()-4, pos.getZ()+11.5);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = getDefaultState();
        return meta == 0 ? state : state.withProperty(IS_STARTER, false);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(IS_STARTER) ? 0 : 1;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {IS_STARTER});
    }
}
