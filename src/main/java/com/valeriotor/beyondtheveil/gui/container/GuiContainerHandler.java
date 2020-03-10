package com.valeriotor.beyondtheveil.gui.container;

import com.valeriotor.beyondtheveil.events.special.DrowningRitualEvents;
import com.valeriotor.beyondtheveil.gui.GuiCityMapper;
import com.valeriotor.beyondtheveil.gui.GuiDagon;
import com.valeriotor.beyondtheveil.gui.GuiDrowned;
import com.valeriotor.beyondtheveil.gui.GuiSleepingChamber;
import com.valeriotor.beyondtheveil.gui.GuiWateryCradle;
import com.valeriotor.beyondtheveil.gui.research.GuiNecronomicon;
import com.valeriotor.beyondtheveil.inventory.ContainerGearBench;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.items.container.ContainerDreamBottle;
import com.valeriotor.beyondtheveil.items.container.InventoryDreamBottle;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.tileEntities.TileCityMapper;
import com.valeriotor.beyondtheveil.tileEntities.TileGearBench;
import com.valeriotor.beyondtheveil.worship.DagonDialogues;
import com.valeriotor.beyondtheveil.worship.DrowningRitual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiContainerHandler implements IGuiHandler{
	
	public static final int DREAM_BOTTLE = 0;
	public static final int CITY_MAPPER = 1;
	public static final int DROWNED = 2;
	public static final int DAGON = 3;
	public static final int CRADLE = 4;
	public static final int NECRONOMICON = 5;
	public static final int SLEEP_CHAMBER = 6;
	public static final int GEAR_BENCH = 7;
	
	
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer p, World world, int x, int y, int z) {
		if(ID == 0) {
			if(p.getHeldItemMainhand().getItem() == ItemRegistry.dream_bottle) {
				InventoryDreamBottle db = new InventoryDreamBottle("Dream Bottle", false, 4, p.getHeldItemMainhand());
				return new ContainerDreamBottle(p.inventory, db);
			}
		} /*else if(ID == 1) {
			BlockPos pos = new BlockPos(x, y, z);
			if(world.getTileEntity(pos) instanceof TileCityMapper) {
				
			}
		}*/ else if(ID == 2) {
			return new DummyContainer();
		} else if(ID == 3) {
			return new DummyContainer();
		} else if(ID == 4) {
			return new DummyContainer();
		} else if(ID == 5) {
			return new DummyContainer();
		} else if(ID == 6) {
			return new DummyContainer();
		} else if(ID == 7) {
			TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
			if(te instanceof TileGearBench)
			return new ContainerGearBench(p.inventory, (TileGearBench)te);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer p, World world, int x, int y, int z) {
		if(ID == 0) {
			if(p.getHeldItemMainhand().getItem() == ItemRegistry.dream_bottle) {
				InventoryDreamBottle db = new InventoryDreamBottle("Dream Bottle", false, 4, p.getHeldItemMainhand());
				int amount = 0;
				IFluidHandler fh = FluidUtil.getFluidHandler(p.getHeldItemMainhand());
				if(fh instanceof FluidHandlerItemStack) {
					FluidStack fluid = ((FluidHandlerItemStack)fh).getFluid();
					if(fluid != null) amount = fluid.amount;
				}
				return new GuiDreamBottle(new ContainerDreamBottle(p.inventory, db), amount);
			}
		} else if(ID == 1) {
			BlockPos pos = new BlockPos(x, y, z);
			if(world.getTileEntity(pos) instanceof TileCityMapper) {
				return new GuiCityMapper(pos);
			}
		} else if(ID == 2) {
			DrowningRitual dr = DrowningRitualEvents.rituals.get(p.getPersistentID());
			return new GuiDrowned((byte)dr.phase.ordinal(), dr.greatDreamer, dr.ancientGods);
		} else if(ID == 3) {
			GuiDagon gui = DagonDialogues.getGui(p);
			if(gui != null) return gui;
		} else if(ID == 4) {
			boolean[] options = {	ResearchUtil.isResearchOpened(p, "SPINES"), 
									ResearchUtil.isResearchOpened(p, "WEEPERS"), 
									false, 
									ResearchUtil.isResearchOpened(p, "HEARTS")};
			GuiWateryCradle gui = new GuiWateryCradle(new BlockPos(x, y, z), options);
			if(gui != null) return gui;
		} else if(ID == 5) {
			return new GuiNecronomicon();
		} else if(ID == 6) {
			return new GuiSleepingChamber();
		} else if(ID == 7) {
			TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
			if(te instanceof TileGearBench)
			return new GuiGearBench(new ContainerGearBench(p.inventory, (TileGearBench)te));
		}
		return null;
	}
	
	public static class DummyContainer extends Container {

		@Override
		public boolean canInteractWith(EntityPlayer playerIn) {
			return true;
		}
		
	}	
	
}
