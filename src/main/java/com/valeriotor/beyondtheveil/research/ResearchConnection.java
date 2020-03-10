package com.valeriotor.beyondtheveil.research;

import java.awt.Point;
import java.util.HashMap;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ResearchConnection {
	
	private final Point startPoint;
	private final Point endPoint;
	private final Point topLeft;
	private final Point bottomRight;
	private final String startKey;
	private final String endKey;
	
	public ResearchConnection(Research start, Research end) {
		this.startPoint = new Point(start.getX(), start.getY());
		this.endPoint = new Point(end.getX(), end.getY());
		this.startKey = start.getKey();
		this.endKey = end.getKey();
		this.topLeft = new Point(Math.min(start.getX(), end.getX()), Math.min(start.getY(), end.getY()));
		this.bottomRight = new Point(Math.max(start.getX(), end.getX()), Math.max(start.getY(), end.getY()));
	}
	
	public boolean isVisible(EntityPlayer p) {
		return ResearchUtil.isResearchVisible(p, startKey) && ResearchUtil.isResearchVisible(p, endKey);
	}
	
	public boolean isVisible(HashMap<String, ResearchStatus> map, IPlayerData data) {
		return ResearchUtil.isResearchVisible(map, data, startKey) && ResearchUtil.isResearchVisible(map, data, endKey);
	}
	
	public boolean shouldRender(int topX, int topY, int width, int height) {
		if((bottomRight.x > topX && bottomRight.y > topY) || (topLeft.x < topX + width && topLeft.y < topY + height))
			return true;
		return false;
	}
	
	public Point getStartPoint() {
		return this.startPoint;
	}
	
	public Point getEndPoint() {
		return this.endPoint;
	}
	
	public Point getLeftPoint() {
		return startPoint.x < endPoint.x ? startPoint : endPoint;
	}
	
	public Point getRightPoint() {
		return startPoint.x >= endPoint.x ? startPoint : endPoint;
	}
	
	
}
