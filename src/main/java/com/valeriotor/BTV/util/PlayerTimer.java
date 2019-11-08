package com.valeriotor.BTV.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerTimer {
	public final EntityPlayer player;
	private int timer;
	private final List<Consumer<EntityPlayer>> finalActions;
	private final List<Consumer<EntityPlayer>> continuosActions;
	
	public PlayerTimer(EntityPlayer player) {
		this(player, null, 100);
	}
	
	public PlayerTimer(EntityPlayer player, Consumer<EntityPlayer> finalAction) {
		this(player, finalAction, 100);
	}
	
	public PlayerTimer(EntityPlayer player, Consumer<EntityPlayer> action, int timer) {
		this.player = player;
		this.timer = timer;
		this.continuosActions = ImmutableList.of();
		this.finalActions = ImmutableList.of(action);
	}
	
	private PlayerTimer(EntityPlayer player, int timer, List<Consumer<EntityPlayer>> continuosActions, List<Consumer<EntityPlayer>> finalActions) {
		this.player = player;
		this.timer = timer;
		this.continuosActions = ImmutableList.copyOf(continuosActions);
		this.finalActions = ImmutableList.copyOf(finalActions);
	}
	
	public boolean update() {
		if(timer > 0) {
			timer--;
			for(Consumer<EntityPlayer> action : continuosActions) action.accept(player);
			return false;
		} else if(timer == 0) {
			timer--;
			for(Consumer<EntityPlayer> action : finalActions) action.accept(player);
		}
		return true;
		
	}
	
	public boolean isDone() {
		return timer <= 0;
	}
	
	public static class PlayerTimerBuilder {
		
		private List<Consumer<EntityPlayer>> finalActions = new ArrayList<>();
		private List<Consumer<EntityPlayer>> continuosActions = new ArrayList<>();
		private int timer = 100;
		private final EntityPlayer player;
		
		public PlayerTimerBuilder(EntityPlayer player) {
			this.player = player;
		}
		
		public PlayerTimerBuilder setTimer(int timer) {
			this.timer = timer;
			return this;
		}
		
		public PlayerTimerBuilder addFinalAction(Consumer<EntityPlayer> action) {
			finalActions.add(action);
			return this;
		}
		
		public PlayerTimerBuilder addContinuosAction(Consumer<EntityPlayer> action) {
			continuosActions.add(action);
			return this;
		}
		
		public PlayerTimer toPlayerTimer() {
			return new PlayerTimer(this.player, this.timer, this.continuosActions, this.finalActions);
		}
		
	}
}
