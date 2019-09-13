package com.valeriotor.BTV.shoggoth;

import java.awt.Point;

import com.valeriotor.BTV.gui.GuiCityMapper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class FlatLongBuilding extends FlatBuilding{
	
	public Point vertex1 = new Point();
	public Point vertex2 = new Point();
	
	public FlatLongBuilding(int index) {
		super(index);
	}
	
	public FlatLongBuilding(NBTTagCompound nbt) {
		super(nbt);
		vertex1.move(nbt.getInteger("v1x"), nbt.getInteger("v1y"));
		vertex2.move(nbt.getInteger("v2x"), nbt.getInteger("v2y"));
	}
	
	public FlatLongBuilding(BuildingTemplate template) {
		super(template);
	}
	
	public void setCenter() {
		if(this.horizontal())
			super.setCenter((vertex1.x + vertex2.x)/2, vertex1.y);
		else 
			super.setCenter(vertex1.x, (vertex1.y + vertex2.y)/2);
	}
	
	@Override 
	public int top() {
		boolean horizontal = horizontal();
		int value = getFirstVertex(horizontal()).y;
		if(horizontal) value -= this.getDefaultHeight() / 2;
		return value;
	}
	
	@Override 
	public int left() {
		boolean horizontal = horizontal();
		int value = getFirstVertex(horizontal()).x;
		if(!horizontal) value -= this.getDefaultWidth() / 2;
		return value;
	}
	
	@Override 
	public int bottom() {
		boolean horizontal = horizontal();
		int value = getSecondVertex(horizontal()).y;
		if(horizontal) value += this.getDefaultHeight() / 2;
		return value;
	}
	
	@Override 
	public int right() {
		boolean horizontal = horizontal();
		int value = getSecondVertex(horizontal()).x;
		if(!horizontal) value += this.getDefaultWidth() / 2;
		return value;
	}
	
	@Override
	public int getWidth() {
		return !horizontal() ? this.building.width : Math.abs(vertex1.x - vertex2.x);
	}
	
	@Override
	public int getHeight() {
		return horizontal() ? this.building.height : Math.abs(vertex1.y - vertex2.y);
	}
	
	public int getLength() {
		return horizontal() ? getWidth() : getHeight();
	}
	
	public int getDefaultWidth() {
		return this.building.width;
	}
	
	public int getDefaultHeight() {
		return this.building.height;
	}
	
	private Point getFirstVertex(boolean horizontal) {
		if(horizontal) return vertex1.x < vertex2.x ? vertex1 : vertex2;
		else return vertex1.y < vertex2.y ? vertex1 : vertex2;
	}
	
	private Point getSecondVertex(boolean horizontal) {
		if(horizontal) return vertex1.x > vertex2.x ? vertex1 : vertex2;
		else return vertex1.y > vertex2.y ? vertex1 : vertex2;
	}
	
	@Override
	public void render(GuiCityMapper gui) {
		GlStateManager.pushMatrix();
		Point p = getFirstVertex(horizontal());
		GlStateManager.translate((gui.width/2 - 115) + p.x, (gui.height/2 - 100) + p.y, 0);
		if(horizontal()) {
			building.drawScaledTexture(gui, 0, -15, 1, getWidth(), 32);
		}else {
			GlStateManager.rotate(90, 0, 0, 1);
			building.drawScaledTexture(gui, 0, -15, 1, getHeight(), 32);
		}
		
		GlStateManager.popMatrix();
	}
	
	private boolean horizontal() {
		return (this.rotation & 1) == 1;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("v1x", vertex1.x);
		nbt.setInteger("v1y", vertex1.y);
		nbt.setInteger("v2x", vertex2.x);
		nbt.setInteger("v2y", vertex2.y);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public NBTTagCompound writeToNBTCorrected(NBTTagCompound nbt, BlockPos pos) {
		nbt.setInteger("v1x", vertex1.x + pos.getX() - 100);
		nbt.setInteger("v1y", vertex1.y + pos.getZ() - 100);
		nbt.setInteger("v2x", vertex2.x + pos.getX() - 100);
		nbt.setInteger("v2y", vertex2.y + pos.getZ() - 100);
		return super.writeToNBTCorrected(nbt, pos);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof FlatLongBuilding)) return false;
		FlatLongBuilding b = (FlatLongBuilding) obj;
		return b.centerX == this.centerX && b.centerY == this.centerY && b.rotation == this.rotation && b.vertex1.equals(this.vertex1) && b.vertex2.equals(this.vertex2);
	}
	
	@Override
	public int hashCode() {
		int result = Integer.hashCode(this.centerX);			// Thanks Joshua
		result = 31 * result + Integer.hashCode(this.centerY);
		result = 31 * result + Integer.hashCode(this.rotation);
		result = 31 * result + this.vertex1.hashCode();
		result = 31 * result + this.vertex2.hashCode();
		return result;
	}

}
