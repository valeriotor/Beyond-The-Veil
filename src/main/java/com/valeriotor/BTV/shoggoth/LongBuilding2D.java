package com.valeriotor.BTV.shoggoth;

import java.awt.Point;

import com.valeriotor.BTV.gui.GuiCityMapper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;

public class LongBuilding2D extends Building2D{
	
	public Point vertex1 = new Point();
	public Point vertex2 = new Point();
	
	public LongBuilding2D(int index) {
		super(index);
	}
	
	public LongBuilding2D(NBTTagCompound nbt) {
		super(nbt);
		vertex1.move(nbt.getInteger("v1x"), nbt.getInteger("v1y"));
		vertex2.move(nbt.getInteger("v2x"), nbt.getInteger("v2y"));
	}
	
	public LongBuilding2D(BuildingTemplate template) {
		super(template);
	}
	
	public void setCenter() {
		if(this.horizontal())
			super.setCenter((vertex1.x + vertex2.x)/2, vertex1.y);
		else 
			super.setCenter(vertex1.x, (vertex1.y + vertex2.y)/2);
	}
	
	@Override
	public int getWidth() {
		return !horizontal() ? this.building.width : Math.abs(vertex1.x - vertex2.x);
	}
	
	@Override
	public int getHeight() {
		return horizontal() ? this.building.height : Math.abs(vertex1.y - vertex2.y);
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

}
