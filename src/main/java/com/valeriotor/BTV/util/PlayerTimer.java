package com.valeriotor.BTV.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class PlayerTimer {
	public final UUID player;
	private final MinecraftServer server;
	private int timer;
	private final List<Consumer<EntityPlayer>> finalActions;
	private final List<Consumer<EntityPlayer>> continuosActions;
	private final List<Predicate<EntityPlayer>> interrupts;
	private String name = "";
	
	public PlayerTimer(EntityPlayer player) {
		this(player, null, 100);
	}
	
	public PlayerTimer(EntityPlayer player, Consumer<EntityPlayer> finalAction) {
		this(player, finalAction, 100);
	}
	
	public PlayerTimer(EntityPlayer player, Consumer<EntityPlayer> action, int timer) {
		this.player = player.getPersistentID();
		this.server = player.getServer();
		this.timer = timer;
		this.continuosActions = ImmutableList.of();
		this.interrupts = ImmutableList.of();
		if(action != null)
			this.finalActions = ImmutableList.of(action);
		else 
			this.finalActions = ImmutableList.of();
	}
	
	private PlayerTimer(EntityPlayer player, int timer, List<Consumer<EntityPlayer>> continuosActions, List<Consumer<EntityPlayer>> finalActions, List<Predicate<EntityPlayer>> interrupts) {
		this.player = player.getPersistentID();
		this.server = player.getServer();
		this.timer = timer;
		this.continuosActions = ImmutableList.copyOf(continuosActions);
		this.finalActions = ImmutableList.copyOf(finalActions);
		this.interrupts = ImmutableList.copyOf(interrupts);
	}
	
	public boolean update() {
		EntityPlayer p = this.getPlayer();
		if(p == null) return true;
		for(Predicate<EntityPlayer> interrupt : interrupts) if(interrupt.test(p)) return true;
		if(timer > 0) {
			if(!p.isDead) {
				timer--;
				for(Consumer<EntityPlayer> action : continuosActions) action.accept(p);
			}
			return false;
		} else if(timer == 0) {
			timer--;
			for(Consumer<EntityPlayer> action : finalActions) action.accept(p);
		}
		return true;
		
	}
	
	public boolean isDone() {
		return timer <= 0;
	}
	
	public int getTimer() {
		return this.timer;
	}
	
	public EntityPlayer getPlayer() {
		return this.server.getPlayerList().getPlayerByUUID(player);
	}
	
	public PlayerTimer setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean corresponds(String name, EntityPlayer player) {
		return this.name.equals(name) && this.player.equals(player.getPersistentID());
	}
	
	public boolean equals(Object object) {
		if(!(object instanceof PlayerTimer)) return false;
		PlayerTimer timer = (PlayerTimer) object;
		return timer.getName().equals(this.name) && timer.player.equals(this.player);
	}
	
	@Override
	public int hashCode() {
		int code = this.name.hashCode();
		code = 31 * code + player.hashCode();
		return code;
	}
	
	public void terminateEarly() {
		this.timer = -1;
		for(Consumer<EntityPlayer> action : finalActions) action.accept(this.getPlayer());
	}
	
	public PlayerTimer copyForNewPlayer(EntityPlayer player) {
		return new PlayerTimer(player, timer, continuosActions, finalActions, interrupts).setName(name);
	}
	
	public static class PlayerTimerBuilder {
		
		private List<Consumer<EntityPlayer>> finalActions = new ArrayList<>();
		private List<Consumer<EntityPlayer>> continuosActions = new ArrayList<>();
		private List<Predicate<EntityPlayer>> interrupts = new ArrayList<>();
		private int timer = 100;
		private final EntityPlayer player;
		private String name = "";
		
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
		
		public PlayerTimerBuilder addInterrupt(Predicate<EntityPlayer> action) {
			interrupts.add(action);
			return this;
		}
		
		public PlayerTimerBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public PlayerTimer toPlayerTimer() {
			return new PlayerTimer(this.player, this.timer, this.continuosActions, this.finalActions, this.interrupts).setName(this.name);
		}
		
	}
}
