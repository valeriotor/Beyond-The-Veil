package com.valeriotor.BTV.gui.container;

import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.items.container.ContainerDreamBottle;
import com.valeriotor.BTV.items.container.InventoryDreamBottle;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiContainerHandler implements IGuiHandler{
	
	public static final int DREAM_BOTTLE = 0;
	
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer p, World world, int x, int y, int z) {
		if(ID == 0) {
			if(p.getHeldItemMainhand().getItem() == ItemRegistry.dream_bottle) {
				InventoryDreamBottle db = new InventoryDreamBottle("Dream Bottle", false, 4, p.getHeldItemMainhand());
				return new ContainerDreamBottle(p.inventory, db);
			}
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
		}
		return null;
	}

}
