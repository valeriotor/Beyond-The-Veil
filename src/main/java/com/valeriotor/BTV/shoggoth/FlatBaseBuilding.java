package com.valeriotor.BTV.shoggoth;

import com.valeriotor.BTV.gui.GuiCityMapper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FlatBaseBuilding extends FlatBuilding{
	
	public FlatBaseBuilding(int index) {
		super(index);
	}
	
	/*public Building2D(BuildingCustom building) {
		this.building = building;
		this.buildingIndex = -1;
	}*/
	
	public FlatBaseBuilding(NBTTagCompound nbt) {
		super(nbt);
	}
	
	public FlatBaseBuilding(BuildingTemplate template) {
		super(template);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof FlatBaseBuilding)) return false;
		FlatBaseBuilding b = (FlatBaseBuilding) obj;
		return b.centerX == this.centerX && b.centerY == this.centerY && b.rotation == this.rotation;
	}
	
	@Override
	public int hashCode() {
		int result = Integer.hashCode(this.centerX);			// Thanks Joshua
		result = 31 * result + Integer.hashCode(this.centerY);
		result = 31 * result + Integer.hashCode(this.rotation);
		return result;
	}
	
}
