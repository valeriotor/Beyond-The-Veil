package com.valeriotor.BTV.shoggoth;

import com.valeriotor.BTV.gui.GuiCityMapper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Building2D {
	
	private final BuildingTemplate building;
	public int centerX = 0;
	public int centerY = 0;
	public int rotation = 0; // 0 = North, 1 = East, 2 = South, 3 = West. Defines door of the building
	
	public Building2D(int index) {
		this.building = BuildingRegistry.templates[index];
	}
	
	/*public Building2D(BuildingCustom building) {
		this.building = building;
		this.buildingIndex = -1;
	}*/
	
	public Building2D(NBTTagCompound nbt) {
		int index = nbt.getInteger("index");
		//if(index >= 0) 	// for later use with custom buildings, where this.building = player.getcapability.sumthin' (capability will only instantiate 
							// the custombuildings when asked to, and will keep them stored
		this.building = BuildingRegistry.templates[index];
		this.setCenter(nbt.getInteger("centerX"), nbt.getInteger("centerY"));
		this.rotation = nbt.getInteger("rot");
	}
	
	public int getWidth() {
		return (rotation & 1) == 0 ? building.width : building.height;
	}
	
	public int getHeight() {
		return (rotation & 1) == 1 ? building.width : building.height;
	}
	
	public boolean isLongBuilding() {
		return this.building.longBuilding;
	}
	
	public int getIndex() {
		return this.building.index;
	}
	
	
	public boolean intersects(Building2D hover, int centerX, int centerY, int width, int height) {
		if(hover == this) return false; // A building can intersect itself
		centerX -= (width/2 - 115);
		centerY -= (height / 2 - 100);
		int hTop = centerY - hover.getHeight()/2;
		int hLeft = centerX - hover.getWidth()/2;
		int hBottom = centerY + hover.getHeight()/2;
		int hRight = centerX + hover.getWidth()/2;
		int top = top(), left = left(), bottom = bottom(), right = right();
		if((hTop < top && hBottom >= top || hBottom > bottom && hTop <= bottom || hBottom < bottom && hTop > top)
		&& (hLeft < left && hRight >= left || hRight > right && hLeft <= right || hRight < right && hLeft > left)) {
			return true;
		}
		return false;
	}
	
	public boolean containsPoint(int x, int y, int width, int height) {
		x -= (width/2 - 115);
		y -= (height / 2 - 100);
		if(x > left() && x < right() && y > top() && y < bottom()) return true;
		return false;
	}
	
	public int top() {return this.centerY - this.getHeight()/2;}
	public int left() {return this.centerX - this.getWidth()/2;}
	public int bottom() {return this.centerY + this.getHeight()/2;}
	public int right() {return this.centerX + this.getWidth()/2;}
	
	public void setCenter(int x, int y) {
		this.centerX = x;
		this.centerY = y;
	}
	
	@SideOnly(Side.CLIENT)
	public void render(GuiCityMapper gui) {
		if(!this.isLongBuilding()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate((gui.width/2 - 115) + centerX, (gui.height/2 - 100) + centerY, 0);
			//GlStateManager.rotate(1, 0, 0, 1);
			GlStateManager.translate(-16, -16, 0);
			building.drawScaledTexture(gui, 0, 0, 1);
			GlStateManager.popMatrix();
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("index", this.building.index);
		nbt.setInteger("centerX", this.centerX);
		nbt.setInteger("centerY", this.centerY);
		nbt.setInteger("rot", this.rotation);
		return nbt;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof Building2D)) return false;
		Building2D b = (Building2D) obj;
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
