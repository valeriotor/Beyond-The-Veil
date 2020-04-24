package com.valeriotor.beyondtheveil.gui;

import com.valeriotor.beyondtheveil.entities.EntitySurgeon;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageSurgeon;
import com.valeriotor.beyondtheveil.research.ResearchUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSurgeon extends GuiOptionWheel{
	
	private final int surgeonID;
	private boolean[] options;
	private int selected = -1;
	
	public GuiSurgeon(int surgeonID) {
		this.surgeonID = surgeonID;
		EntityPlayer p = Minecraft.getMinecraft().player;
		boolean[] options = {	ResearchUtil.isResearchOpened(p, "SPINES"), 
								ResearchUtil.isResearchOpened(p, "WEEPERS"), 
								false, 
								ResearchUtil.isResearchOpened(p, "HEARTS")};
		this.options = options;
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		Entity e = mc.player.world.getEntityByID(surgeonID);
		if(e instanceof EntitySurgeon) {
			EntitySurgeon s = (EntitySurgeon)e;
			this.selected = s.getOperation();
		} else mc.displayGuiScreen(null);
	}
	
	@Override
	public String getGuiLangName() {
		return "surgeon";
	}
	
	@Override
	protected boolean isNorthOptionAvailable() {
		return options[0];
	}
	
	@Override
	protected boolean isNorthOptionGreyedOut() {
		return this.selected == 0;
	}
	
	private static final ResourceLocation spine = new ResourceLocation(References.MODID + ":textures/items/spine.png");
	@Override
	public ResourceLocation getNorthOptionTexture() {
		return spine;
	}
	
	@Override
	protected boolean isEastOptionAvailable() {
		return options[3];
	}
	
	@Override
	protected boolean isEastOptionGreyedOut() {
		return this.selected == 3;
	}
	
	private static final ResourceLocation heart = new ResourceLocation(References.MODID + ":textures/items/heart.png");
	@Override
	public ResourceLocation getEastOptionTexture() {
		return heart;
	}
	
	@Override
	protected boolean isWestOptionAvailable() {
		return options[1];
	}
	
	@Override
	protected boolean isWestOptionGreyedOut() {
		return this.selected == 1;
	}
	
	private static final ResourceLocation tears = new ResourceLocation(References.MODID + ":textures/items/held_weeper.png");
	@Override
	public ResourceLocation getWestOptionTexture() {
		return tears;
	}
	
	@Override
	public void doAction(int option) {
		BTVPacketHandler.INSTANCE.sendToServer(new MessageSurgeon(this.surgeonID, option));
	}

}
