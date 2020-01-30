package com.valeriotor.BTV.gui.container;

import com.valeriotor.BTV.events.special.DrowningRitualEvents;
import com.valeriotor.BTV.gui.GuiCityMapper;
import com.valeriotor.BTV.gui.GuiDagon;
import com.valeriotor.BTV.gui.GuiDrowned;
import com.valeriotor.BTV.gui.GuiWateryCradle;
import com.valeriotor.BTV.gui.research.GuiNecronomicon;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.items.container.ContainerDreamBottle;
import com.valeriotor.BTV.items.container.InventoryDreamBottle;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.tileEntities.TileCityMapper;
import com.valeriotor.BTV.worship.DagonDialogues;
import com.valeriotor.BTV.worship.DrowningRitual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
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
