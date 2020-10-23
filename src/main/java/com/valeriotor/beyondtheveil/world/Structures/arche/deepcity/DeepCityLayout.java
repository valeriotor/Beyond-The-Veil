package com.valeriotor.beyondtheveil.world.Structures.arche.deepcity;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.valeriotor.beyondtheveil.world.Structures.arche.ArcheStructuresRegistry;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class DeepCityLayout {
	private static final int MAX_BRANCH_LENGTH = 6;
	private static final int INDIVIDUAL_WIDTH = 31;
	private final Map<Point, DeepCityStructure> map = new HashMap<>();
	private final Set<Connection> connections = new HashSet<>();
	private final BlockPos center;
	private final Random rand;
	private EnumFacing currentDirection;
	private int index = 0;
	private int branchLevel = 0;
	private Point currentPoint;
	
	public DeepCityLayout(Random rand, BlockPos center) {
		this.rand = rand;
		this.center = center;
	}
	
	public void generate() {
		map.put(new Point(0,0), ArcheStructuresRegistry.getRandomDeepCityStructure(rand, center));
		for(EnumFacing facing : EnumFacing.HORIZONTALS) {
			currentDirection = facing;
			currentPoint = new Point(0,0);
			index = 0;
			branchLevel++;
			generateNode(facing);
			branchLevel--;
		}
			
	}
	
	private void generateNode(EnumFacing facing) {
		Point newPoint = offsetPoint(facing);
		if(outOfGrid(newPoint)) return;
		
		index++;
		if(index > MAX_BRANCH_LENGTH) return;
		
		connections.add(new Connection(currentPoint, newPoint));
		
		if(map.containsKey(newPoint)) return;
		BlockPos newPos = center.add(newPoint.x*(INDIVIDUAL_WIDTH+25), 0, newPoint.y*(INDIVIDUAL_WIDTH+25));
		map.put(newPoint, ArcheStructuresRegistry.getRandomDeepCityStructure(rand, newPos));
		
		Point oldPoint = currentPoint;
		currentPoint = newPoint;
		
		int nextFaceIndex = rand.nextInt(3);
		EnumFacing nextFace = EnumFacing.HORIZONTALS[nextFaceIndex];
		if(nextFace == facing.getOpposite()) nextFace = EnumFacing.HORIZONTALS[nextFaceIndex+1];
		boolean branchOut = rand.nextInt((8*branchLevel - index)/4 + 1) == 0;
		
		if(branchOut) {
			generateBranch(nextFace);
			nextFace = findNextFaceAfterBranch(facing, nextFace);
		}
		generateNode(nextFace);
		index--;
		currentPoint = oldPoint;
	}
	
	private void generateBranch(EnumFacing nextFace) {
		branchLevel++;
		generateNode(nextFace);
		branchLevel--;
	}
	
	private EnumFacing findNextFaceAfterBranch(EnumFacing facing, EnumFacing hold) {
		EnumFacing nextFace = null;
		int nextFaceIndex = rand.nextInt(2);
		for(int i = 0; i < 4; i++) {
			nextFace = EnumFacing.HORIZONTALS[nextFaceIndex];
			if(nextFace == hold) 	continue;
			if(nextFace == facing.getOpposite()) 	continue;
			if(--nextFaceIndex < 0) break;
		}
		return nextFace;
	}
	
	private boolean outOfGrid(Point point) {
		return Math.abs(point.x) > 4 || Math.abs(point.y) > 4;
	}
	
	private Point offsetPoint(EnumFacing facing) {
		switch(facing) {
			case NORTH: return new Point(currentPoint.x, currentPoint.y-1);
			case EAST: return new Point(currentPoint.x+1, currentPoint.y);
			case SOUTH: return new Point(currentPoint.x, currentPoint.y+1);
			case WEST: return new Point(currentPoint.x-1, currentPoint.y);
			default: return currentPoint;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int y = -4; y <= 4; y++) {
			for(int x = -4; x <= 4; x++) {
				Point p = new Point(x,y);
				if(map.containsKey(p)) {
					sb.append("X");
				} else
					sb.append("O");
				if(x < 4) {
					Connection c = new Connection(p, new Point(x+1, y));
					if(connections.contains(c)) {
						sb.append(" — ");
					} else {
						sb.append("   ");
					}
				}
			}
			sb.append(String.format("%n"));
			if(y < 4) {
				for(int x = -4; x <= 4; x++) {
					Point p1 = new Point(x,y);
					Point p2 = new Point(x, y+1);
					Connection c = new Connection(p1, p2);
					if(connections.contains(c)) {
						sb.append("|   ");
					} else {
						sb.append("    ");
					}
				}
				sb.append(String.format("%n"));
			}
		}
		return sb.toString();
	}
	
	public List<DeepCityStructure> getAsList() {
		return this.map.values().stream().collect(Collectors.toCollection(LinkedList::new));
	}
	
	private static class Connection {
		private final Point p1;
		private final Point p2;
		
		private Connection(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Connection)) return false;
			if(obj == this) return true;
			Connection c = (Connection)obj;
			boolean a = (p1.equals(c.p1) && p2.equals(c.p2)) || (p1.equals(c.p2) && p2.equals(c.p1));
			return a;
		}
		
		@Override
		public int hashCode() {
			return p1.hashCode() + p2.hashCode();
		}
		
		@Override
		public String toString() {
			return "{" + p1.toString() + ", " + p2.toString() + "}";
		}
	}
}
